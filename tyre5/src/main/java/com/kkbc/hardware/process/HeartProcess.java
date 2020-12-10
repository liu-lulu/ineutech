package com.kkbc.hardware.process;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.hardware.HardwareElement;
import com.kkbc.hardware.UDPServerManager;
import com.kkbc.hardware.send.HardwareSendManager;
import com.kkbc.service.DeviceLoginLogService;
import com.kkbc.service.impl.DeviceLoginLogServiceImpl;
import com.psylife.util.SpringContextUtils;

public class HeartProcess extends Thread {
	
	/**
	 * 在线元素
	 */
	private Map<String, HardwareElement> channelElement;
	
	private boolean isClose = true;
	
	public static final long INTERVAL_TIMEOUT_TCP=5*60*1000+25*60*1000;//UDP心跳超时时间 5分钟
	
	private final long HEART_TIME=1*60*1000;//心跳时间1分钟
	
    private DeviceLoginLogService deviceLoginLogService;
	
	static final Logger logger = LoggerFactory.getLogger(HeartProcess.class);
	
	public HeartProcess(){
		deviceLoginLogService=(DeviceLoginLogServiceImpl) SpringContextUtils.context.getBean("deviceLoginLogService");
	}

	@Override
	public synchronized void start() {
		super.start();
	}
	@Override
	public void run() {
		logger.info("开始处理,系统启动时处理dtu离线时,行驶状态为停放");
		deviceLoginLogService.startSysDtuProcessTrucksFlag();
		logger.info("结束处理,系统启动时处理dtu离线时,行驶状态为停放");
		this.channelElement=UDPServerManager.getInstance().getChannelElement();
		this.isClose=false;
		long currentTime=-1;
//		long id;
		while (!isClose) {
			try {
				currentTime=System.currentTimeMillis();
				for(Iterator<Entry<String, HardwareElement>> 
			    ite = channelElement.entrySet().iterator(); ite.hasNext();){
					try {
			            Entry<String, HardwareElement> entry = ite.next();  
//			            entry.getKey();  
//			            entry.getValue();
			            if(INTERVAL_TIMEOUT_TCP-(currentTime-entry.getValue().getLastConnectHeartTime())<0){  //超时
			            	System.out.println("心跳超时移除:"+entry.getKey());
			            	//登录成功
//			    			id=deviceLoginLogService.save(new Object[]{new Date(),Constants.STATUS_NORMAL,entry.getValue().getUdpAddress().getAddress().getHostAddress(),entry.getValue().getUdpAddress().getPort(),DeviceLoginLog.TYPE_LOGOUT,entry.getValue().getPhone()}, 
//			    					"create_time,status,remote_ip,remote_port,type,dtu_id", DeviceLoginLog.TB_N);
//			    			
//			    		    if(id>0){
//			    		    	 logger.info("退出成功Dtu id="+entry.getValue().getPhone());
//			    		    }
			            	deviceLoginLogService.loginOut(entry.getValue());
			            	ite.remove();
			            }else{
			               if(currentTime-entry.getValue().getSendHeartTime()>HEART_TIME){//发送心跳包
			            	  logger.info("发送心跳包Dtu id="+entry.getValue().getPhone());
			            	  entry.getValue().setSendHeartTime(currentTime);
			            	  HardwareSendManager.getInstance().sendGetHeart(entry.getValue());  
			               }
			            }       
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				Thread.sleep(1000);
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
