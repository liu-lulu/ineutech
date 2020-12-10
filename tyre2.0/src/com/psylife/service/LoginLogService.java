package com.psylife.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psylife.entity.LoginLog;

 
public interface LoginLogService {
	
	static final Logger logger = LoggerFactory.getLogger(LoginLogService.class);

	/**
	 * 插入登录日志
	 * @param loginLog
	 * @return
	 */
	boolean insertLog(LoginLog loginLog);
	
}
