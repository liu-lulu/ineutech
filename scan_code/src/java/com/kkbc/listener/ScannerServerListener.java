package com.kkbc.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.kkbc.client.ScanCodeClient;
import com.kkbc.cons.TCPConsts;
import com.kkbc.util.ScannerThread;

//@WebListener
public class ScannerServerListener implements ServletContextListener {

	
	private ScanCodeClient client;
	
	private ScannerThread thread;

	public ScannerServerListener() {
		super();
		client=new ScanCodeClient(TCPConsts.tcp_ip, TCPConsts.tcp_port);
		thread=new ScannerThread();
	}

	
	public void contextDestroyed(ServletContextEvent sce) {
		if (this.client != null)
			this.client.stopServer();
		
		if (this.thread != null)
			this.thread.stopScan();
	}

	public void contextInitialized(ServletContextEvent sce) {
        client.startServer();
        thread.startScan();
	}
	
	

}
