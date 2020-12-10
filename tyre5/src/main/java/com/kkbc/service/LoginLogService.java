package com.kkbc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.entity.LoginLog;

 
public interface LoginLogService {
	
	static final Logger logger = LoggerFactory.getLogger(LoginLogService.class);

	/**
	 * 插入登录日志
	 * @param loginLog
	 * @return
	 */
	boolean insertLog(LoginLog loginLog);
	
}
