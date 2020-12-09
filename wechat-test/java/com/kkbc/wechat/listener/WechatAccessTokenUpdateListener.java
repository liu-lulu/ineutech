package com.kkbc.wechat.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kkbc.wechat.process.WechatAccessTokenTimer;

@WebListener
public class WechatAccessTokenUpdateListener implements ServletContextListener {

	private Logger log = LoggerFactory.getLogger(getClass());
	private WechatAccessTokenTimer timer = null;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(sce.getServletContext());
//		this.service = context.getBean(WechatAccessTokenService.class);
		this.timer=new WechatAccessTokenTimer();
		this.timer.start();
		this.log.info("access_token 刷新定时器正常启动");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		this.timer.cancel();
		this.log.info("access_token 刷新定时器正常启动");
	}

}
