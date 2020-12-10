package com.psylife.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psylife.entity.LoginLog;

 
public interface LoginLogDao {
	
	static final Logger logger = LoggerFactory.getLogger(LoginLogDao.class);

	/**
	 * 插入登录日志
	 * @param loginLog
	 * @return
	 */
	boolean insertLog(LoginLog loginLog);
	
}
