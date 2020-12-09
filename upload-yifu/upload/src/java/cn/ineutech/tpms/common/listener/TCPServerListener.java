package cn.ineutech.tpms.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.ineutech.tpms.common.Consts;
import cn.ineutech.tpms.tcp.server.ConsoleTCPServer;

import com.kkbc.util.SpringContextUtils;

/**
 * 
 * @name: TCPServerListener 
 * @description: 监听器:开启tcp服务
 * @date 2018年2月1日 上午11:38:12
 * @author liululu
 */
public class TCPServerListener implements ServletContextListener {

	private ConsoleTCPServer consoleServer = null;

	public TCPServerListener() {
		super();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		if (this.consoleServer != null) {
			this.consoleServer.stopServer();
		}
	}

	public void contextInitialized(ServletContextEvent sce) {
//		WebApplicationContext context = WebApplicationContextUtils
//				.getWebApplicationContext(sce.getServletContext());
//		SpringContextUtils.setApplicationContext(context);

		this.consoleServer = new ConsoleTCPServer(Consts.console_tcp_port);

		try {
			this.consoleServer.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
