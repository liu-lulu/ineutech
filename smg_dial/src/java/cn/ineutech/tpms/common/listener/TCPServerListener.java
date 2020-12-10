package cn.ineutech.tpms.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.ineutech.tpms.common.Consts;
import cn.ineutech.tpms.tcp.server.BrainTCPServer;
import cn.ineutech.tpms.tcp.server.ConsoleTCPServer;
import cn.ineutech.tpms.tcp.server.TCPServer;

import com.ineutech.util.SpringContextUtils;

/**
 * 
 * @name: TCPServerListener 
 * @description: 监听器:开启tcp服务
 * @date 2018年2月1日 上午11:38:12
 * @author liululu
 */
public class TCPServerListener implements ServletContextListener {

	private TCPServer server = null;
	private BrainTCPServer brainServer = null;
	private ConsoleTCPServer consoleServer = null;

	public TCPServerListener() {
		super();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		if (this.server != null) {
			this.server.stopServer();
		}
		if (this.brainServer != null) {
			this.brainServer.stopServer();
		}
		if (this.consoleServer != null) {
			this.consoleServer.stopServer();
		}
	}

	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext());
		SpringContextUtils.setApplicationContext(context);

		this.server = new TCPServer(Consts.tcp_port);
		this.brainServer = new BrainTCPServer(Consts.brain_tcp_port);
		this.consoleServer = new ConsoleTCPServer(Consts.console_tcp_port);

		try {
			this.server.startServer();
			this.brainServer.startServer();
			this.consoleServer.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
