package com.kkbc.hardware.udp.server;

import java.io.InputStream;
import java.util.Properties;

import com.kkbc.hardware.channel.IChannelCallback;
import com.kkbc.hardware.process.DtuOperationProcess;
import com.kkbc.hardware.process.HeartProcess;
import com.kkbc.hardware.process.QueueProcessControl;

public class UdpServerControl {
	
	private boolean isClose = true;
	private NioUdpServer udpServer;
	
    private int startCount=0;
	
	private final int RETRY_COUNT=2;
	
	/**
	 * 队列处理中央控制器
	 */
	private QueueProcessControl queueProcessControl;
	
	/**
	 * 心跳
	 */
	private HeartProcess heartProcess;
	
	private DtuOperationProcess dtuOperationProcess;
	
	public UdpServerControl(){
		udpServer=new NioUdpServer();
		queueProcessControl=new QueueProcessControl();
		heartProcess=new HeartProcess();
		heartProcess.setPriority(Thread.MIN_PRIORITY);
		dtuOperationProcess=new DtuOperationProcess();
		dtuOperationProcess.setPriority(Thread.MIN_PRIORITY);
	}

	/**
	 * 启动
	 * @throws Exception 
	 */
	public void start() throws Exception{
		new Thread(){
			@Override
			public void run() {
				try {
					if(isClose==true){
						InputStream stream=this.getClass().getResourceAsStream("/config.properties");  
						Properties properties=new Properties();  
				        properties.load(stream);  
				        int serverudpport=Integer.valueOf(properties.getProperty("serverudpport", "8080"));
				        udpServer.setChannelCallback(new IChannelCallback() {
							@Override
							public void disconnect() {//关闭连接
								isClose=true;
								queueProcessControl.setClose(true);
								heartProcess.setClose(true);
								dtuOperationProcess.setClose(true);
								if(RETRY_COUNT>startCount){
									try {
										UdpServerControl.this.start();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
							@Override
							public void connected() {//连接
								isClose=false;
								queueProcessControl.start();
								heartProcess.start();
								dtuOperationProcess.start();
								startCount++;
							}
						});
				        udpServer.bind(serverudpport);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}.start();
		
	}
	public void stop(){
		if(this.isClose==false){
			startCount=RETRY_COUNT;
			udpServer.close();
		}		
	}
	
	public static void main(String[] args) {
		UdpServerControl control=new UdpServerControl();
		try {
			control.start();
			Thread.sleep(10000l);
			if(control.isClose==false){
				control.startCount=control.RETRY_COUNT;
				control.udpServer.close();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
