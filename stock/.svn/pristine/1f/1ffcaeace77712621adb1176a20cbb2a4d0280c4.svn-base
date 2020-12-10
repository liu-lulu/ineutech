package com.kkbc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.kkbc.tpms.tcp.processor.FileProcessor;

public class Test {
	
	public static void AA() throws IOException, InterruptedException {
		WatchService watchService=FileSystems.getDefault().newWatchService();  
        Paths.get("D:/barcode").register(watchService,   
                StandardWatchEventKinds.ENTRY_CREATE,  
                StandardWatchEventKinds.ENTRY_DELETE,  
                StandardWatchEventKinds.ENTRY_MODIFY);  
        final Path path = FileSystems.getDefault().getPath("D:/barcode");
        while(true)  
        {  
            WatchKey key=watchService.take();  
            for(WatchEvent<?> event:key.pollEvents())  
            {  
                System.out.println(event.context()+"发生了"+event.kind()+"事件"); 
                final Path changed = (Path) event.context();
                Path absolute = path.resolve(changed);
                File configFile = absolute.toFile();
                long lastModified = configFile.lastModified();
                System.out.println(lastModified + "----------------");
               
            }

          
            if(!key.reset())  
            {  
                break;  
            }  
        }  

	}

	public static void main(String[] args) throws IOException {
		writerLog();
		FileProcessor processor=new FileProcessor();
//		processor.realtimeShowCode(new File("D:/barcode/code.txt"));
		processor.watchFile();
		
	}
	
	  /** 
     * 实时写入日志到指定文件 
     * @throws IOException 
     */  
    public static void writerLog() throws IOException {  
        final File logFile = new File("D:/barcode/code.txt");  
        if(!logFile.exists()) {  
            logFile.createNewFile();  
        }  
        //启动一个线程每2秒钟向日志文件写一次数据  
        ScheduledExecutorService exec =  Executors.newScheduledThreadPool(1);  
        exec.scheduleWithFixedDelay(new Runnable(){  
            public void run() {  
                try {  
                    if(logFile == null) {  
                        throw new IllegalStateException("logFile can not be null!");  
                    }  
                    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Writer txtWriter = new FileWriter(logFile,true);  
                    txtWriter.write(format.format(new Date()) +"\n");  
                    txtWriter.flush();  
                } catch (IOException e) {  
                    throw new RuntimeException(e);  
                }  
            }  
        }, 0, 2, TimeUnit.SECONDS);  
    }  
}
