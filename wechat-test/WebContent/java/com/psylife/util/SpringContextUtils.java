package com.psylife.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

public class SpringContextUtils {
	     
	    public static ApplicationContext context;
	 
	    public static void setApplicationContext(ApplicationContext context)
	            throws BeansException {
	    	SpringContextUtils.context = context;
	    }
	     
	    public static <T> T getBean(String beanId,Class<T> clazz){
	        return context.getBean(beanId, clazz);
	    }
	     
	    public static ApplicationContext getContext(){
	        return context;
	    }
	 
	}