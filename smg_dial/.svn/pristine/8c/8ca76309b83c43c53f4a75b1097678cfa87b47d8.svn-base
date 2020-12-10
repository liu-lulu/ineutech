package com.ineutech.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * 
 * @name: SpringContextUtils 
 * @description: 获取applicationContext.xml中的bean工具类
 * @date 2018年2月1日 下午4:18:00
 * @author liululu
 */
public class SpringContextUtils {

	public static ApplicationContext context;

	public static void setApplicationContext(ApplicationContext context)
			throws BeansException {
		SpringContextUtils.context = context;
	}

	public static <T> T getBean(String beanId, Class<T> clazz) {
		return context.getBean(beanId, clazz);
	}

	public static ApplicationContext getContext() {
		return context;
	}

}