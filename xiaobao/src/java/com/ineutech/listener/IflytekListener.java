package com.ineutech.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.iflytek.voicecloud.rtasr.IflytekTaskThread;
import com.ineutech.util.SpringContextUtils;

public class IflytekListener implements ServletContextListener{
	private Thread iflytekThread=null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (iflytekThread!=null) {
			iflytekThread.interrupt();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext());
		SpringContextUtils.setApplicationContext(context);
		
		iflytekThread=new IflytekTaskThread();
		
		iflytekThread.start();
		
	}

}
