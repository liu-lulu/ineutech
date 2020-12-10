package com.kkbc.hardware.process;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.hardware.UDPServerManager;
import com.kkbc.hardware.queue.MessageQueue;
import com.kkbc.hardware.queue.QueueElement;
import com.psylife.hardware.data.PackageData;
import com.psylife.hardware.data.parse.PackageDataBrainParse;
import com.psylife.util.HexStringUtil;
import com.psylife.util.StringHelper;

/**
 * 队列处理中央控制器
 * @author xu
 *
 */
public class QueueProcessControl extends Thread{
	
	static final Logger logger = LoggerFactory.getLogger(QueueProcessControl.class);

	private MessageQueue queue;
	
	private int queueType;
	
	private boolean isClose = true;
	
	/**
	 * 
	 * @param queue 要处理的队列
	 * @param queueType 队列类型
	 * @param queueProcess 队列处理器
	 */
	public QueueProcessControl(){
		this.queue=UDPServerManager.getInstance().getMessageQueue();
		this.controlProcess=new ControlProcess();
	}
	
	@Override
	public void run() {
		this.isClose=true;
		this.isClose=false;
		System.out.println("开始QueueProcessControl:"+queueType);
		QueueElement queueElement;
		while (!isClose) {
			try {
				queueElement=(QueueElement)queue.removeFirst();//交由队列处理器处理
				String tt=HexStringUtil.encodeHexStr(queueElement.getData(), false);
				String dates="\n"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				System.out.println("队列输出:"+tt+dates);
				proccessQueueElement(queueElement);//处理
			} catch (Exception e) {
				logger.debug("EEEQueueProcessControl:"+queueType+StringHelper.getTrace(e));
				e.printStackTrace();
			}
		}
		try {
			queue.notfiAllObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("结束QueueProcessControl:"+queueType);
	}
	private List<PackageData> result;
	
	/**
	 * 处理器
	 */
	private ControlProcess controlProcess;
	
	private PackageDataBrainParse dataParse=new PackageDataBrainParse();//包解析
	/**
	 * 处理
	 * @param queueElement
	 */
	private void proccessQueueElement(QueueElement queueElement){
	    result=dataParse.parseToPackageData(queueElement.getData());
	    if(result!=null){
	    	for(PackageData data:result){
	    		controlProcess.process(data,queueElement.getUdpAddress());
	    	}
	    }
	}

	public boolean isClose() {
		return isClose;
	}

	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}

}
