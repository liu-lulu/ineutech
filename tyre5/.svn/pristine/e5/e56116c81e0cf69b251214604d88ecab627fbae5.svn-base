package cn.kkbc.tpms.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.kkbc.tpms.common.Consts;
import cn.kkbc.tpms.tcp.server.TCPServer;

//@WebListener
public class TCPServerListener implements ServletContextListener {

	private TCPServer server = null;

	public TCPServerListener() {
		super();
		this.server = new TCPServer(Consts.tcp_port);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		if (this.server != null)
			this.server.stopServer();
	}

	public void contextInitialized(ServletContextEvent sce) {
		try {
			this.server.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
