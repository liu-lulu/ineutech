package com.psylife.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.psylife.hardware.udp.server.UdpServerControl;

public class HardWareContextListener implements ServletContextListener {

	/**
	 * nwx通讯
	 */
	UdpServerControl control=new UdpServerControl();
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		control.stop();
		System.out.println("硬件服务器结束运行");
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		System.out.println("硬件服务器开始启动");
		try {
			control.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
