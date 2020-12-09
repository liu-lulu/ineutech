package cn.kkbc.tpms.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kkbc.util.SpringContextUtils;

import cn.kkbc.tpms.common.Consts;
import cn.kkbc.tpms.tcp.server.TCPServer;

//@WebListener
public class TCPServerListener implements ServletContextListener {

	private TCPServer server = null;

	public TCPServerListener() {
		super();
//		this.server = new TCPServer(Consts.tcp_port);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		if (this.server != null)
			this.server.stopServer();
	}

	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        SpringContextUtils.setApplicationContext(context);
        
        this.server = new TCPServer(Consts.tcp_port);
		try {
			this.server.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
