package com.psylife.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kkbc.hardware.udp.server.UdpServerControl;
import com.kkbc.service.DeviceLoginLogService;
import com.kkbc.service.impl.DeviceLoginLogServiceImpl;
import com.psylife.util.SpringContextUtils;
import com.psylife.util.StringHelper;


public class HardWareContextListener implements ServletContextListener {
	static final Logger logger = LoggerFactory.getLogger(HardWareContextListener.class);
	private DeviceLoginLogService deviceLoginLogService;
	
	/**
	 * nwx通讯
	 */
	UdpServerControl control;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		control.stop();
		System.out.println("硬件服务器结束运行");
//		deviceLoginLogService.endSysDtuProcessTrucksFlag();
	}
	
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(contextEvent.getServletContext());
        SpringContextUtils.setApplicationContext(context);
        deviceLoginLogService = (DeviceLoginLogServiceImpl)context.getBean("deviceLoginLogService");
        
        control=new UdpServerControl();
		System.out.println("硬件服务器开始启动");
//		Thread JVMEnd=new Thread(){
//			public void run() {  
//                System.out.println("shutdownHook one...");  
//                deviceLoginLogService.endSysDtuProcessTrucksFlag();
//            }  
//		};
//		Runtime.getRuntime().addShutdownHook(JVMEnd);
		try {
			control.start();
		} catch (Exception e) {
			logger.error("硬件服务器异常："+StringHelper.getTrace(e));
		}
	}

}
