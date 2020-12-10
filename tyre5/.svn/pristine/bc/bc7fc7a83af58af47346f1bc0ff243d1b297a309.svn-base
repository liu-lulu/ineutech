package com.kkbc.hardware.process;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.hardware.DtuOperation;
import com.kkbc.hardware.UDPServerManager;
import com.kkbc.service.DeviceOperationLogService;
import com.kkbc.service.impl.DeviceOperationLogServiceImpl;
import com.psylife.util.SpringContextUtils;

/**
 * dtu操作队列处理线程
 * @author xu
 *
 */
public class DtuOperationProcess extends Thread {
	
	/**
	 * dtu操作队列数据
	 */
	private Map<String, DtuOperation> dtuOperationMap;
	
	private boolean isClose = true;
	
	/**
	 * 操作dtu控制,UDP超时时间是DTU回复服务器最晚时间长度,单位s
	 */
	public static final long INTERVAL_SEND_TIMEOUT=6;//UDP超时时间是DTU回复服务器最晚时间长度,单位s
	
	/**
	 * 操作dtu控制,服务器响应用户超时
	 */
	public static final long INTERVAL_TIME=(INTERVAL_SEND_TIMEOUT+4)*1000;//服务器响应用户超时
	
	/**
	 * 操作dtu控制,队列超时,时间
	 */
	private static final long INTERVAL_QUEUE_TIMEOUT=(INTERVAL_SEND_TIMEOUT+4+5)*1000;//队列超时,时间
	
    private DeviceOperationLogService deviceOperationLogService;
	
	static final Logger logger = LoggerFactory.getLogger(DtuOperationProcess.class);
	
	public DtuOperationProcess(){

	}
	public void init(){
		deviceOperationLogService=(DeviceOperationLogServiceImpl) SpringContextUtils.context.getBean("deviceOperationLogService");
	}
	

	@Override
	public synchronized void start() {
		super.start();
	}
	@Override
	public void run() {
		this.dtuOperationMap=UDPServerManager.getInstance().getDtuOperationMap();
		this.isClose=false;
		long currentTime=-1;
//		boolean flag=false;
		while (!isClose) {
			try {
				currentTime=System.currentTimeMillis();
				for(Iterator<Entry<String, DtuOperation>> 
			    ite = dtuOperationMap.entrySet().iterator(); ite.hasNext();){
					try {
			            Entry<String, DtuOperation> entry = ite.next();  
			            if(INTERVAL_QUEUE_TIMEOUT-(currentTime-entry.getValue().getTime())<0){  //超时
			            	System.out.println("队列超时移除:"+entry.getKey());
			            	deviceOperationLogService.updateTypeById(entry.getValue().getId(), entry.getValue().getType());
			            	ite.remove();
			            }     
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(dtuOperationMap.isEmpty()){
					Thread.sleep(INTERVAL_QUEUE_TIMEOUT);
				}else{
					Thread.sleep(1000);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
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
