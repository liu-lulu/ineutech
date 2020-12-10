package cn.kkbc.tpms.tcp.processor;

import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.TPMSConsts;
import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.service.msg.BaseMsgProcessService;
import cn.kkbc.tpms.tcp.vo.Session;

public class FileProcessor extends Thread{

	private Logger log = LoggerFactory.getLogger(getClass());
	
	
	private long lastTimeFileSize = 0; 
	
	private long lastTimeFileSize1 = 0; 

	private SessionManager sessionManager;

	private BaseMsgProcessService sendToDevice;

	public FileProcessor() {
		this.sessionManager = SessionManager.getInstance();
		this.sendToDevice = new BaseMsgProcessService();
	}

	

	@Override
	public void run() {
		lastTimeFileSize1=new File(TPMSConsts.CODE_FILE).length();
		watchFile();
	}



	public void watchFile() {
		
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(new File(TPMSConsts.CODE_FILE), "r");  
			
			WatchService watchService = FileSystems.getDefault().newWatchService();
			 Paths.get(TPMSConsts.CODE_FILE_PATH).register(watchService,   
		                StandardWatchEventKinds.ENTRY_CREATE,  
		                StandardWatchEventKinds.ENTRY_DELETE,  
		                StandardWatchEventKinds.ENTRY_MODIFY);  
		        while(true)  
		        {  
		            WatchKey key=watchService.take();  
		            for(WatchEvent<?> event:key.pollEvents())  
		            {  
//		            	log.info(event.context()+"发生了"+event.kind()+"事件"); 
		                if (event.context().toString().equals(TPMSConsts.CODE_FILE_NAME)) {
		                	List<Session> sessions=sessionManager.toList();
		                	if (sessions!=null&&sessions.size()>0) {
		                		//获得变化部分的  
			                    randomAccessFile.seek(lastTimeFileSize1);
			                    
//			                    StringBuffer content=new StringBuffer();
			                    String tmp = "";
			                    while ( ( tmp = randomAccessFile.readLine()) != null) {  
			                        // 输出文件内容  
			                    	String line=new String(tmp.getBytes("ISO-8859-1"), "utf-8")+TPMSConsts.END;
			                        log.info(line);
//			                        content.append(line);
			                        for (Session session : sessions) {
										sendToDevice.send2Client(session.getChannel(), line.getBytes(CharsetUtil.UTF_8));
									}
			                        
			                        lastTimeFileSize1 = randomAccessFile.length();
			                    }
							}
		                	
						}
		            }
		            if(!key.reset())  
		            {  
		                break;  
		            }  
		        }  
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}  
	
	}
	
	/** 
     * 实时读取指定文件的内容 
     * @param file 
     * @throws FileNotFoundException 
     */  
    public void realtimeShowCode(File file) throws FileNotFoundException {  
        //指定文件可读可写  
        final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");  
        //启动一个线程每10秒钟读取新增的日志信息  
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);  
        exec.scheduleWithFixedDelay(new Runnable() {  
            @Override  
            public void run() {  
                try {  
                    //获得变化部分的  
                    randomAccessFile.seek(lastTimeFileSize);  
                    String tmp = "";  
                    while ( (tmp = randomAccessFile.readLine()) != null) {  
                        // 输出文件内容  
                        log.info(new String(tmp.getBytes("ISO-8859-1"), "utf-8"));  
                        lastTimeFileSize = randomAccessFile.length();  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }, 0, 1, TimeUnit.SECONDS);  
        
//        exec.shutdown();
   
    }  

}
