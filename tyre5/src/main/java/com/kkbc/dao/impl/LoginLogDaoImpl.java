
package com.kkbc.dao.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kkbc.dao.LoginLogDao;
import com.kkbc.entity.LoginLog;


@Repository
public class LoginLogDaoImpl extends BaseDaoImpl implements LoginLogDao{
	static final Logger logger = LoggerFactory.getLogger(LoginLogDao.class);
	
	@Override
	public boolean insertLog(LoginLog loginLog) {
		loginLog.setCreateTime(new java.sql.Timestamp(new Date().getTime()));
		int isOK = (int) getSqlMapClientTemplate().insert("LoginLog.saveInfo", loginLog);
		
		logger.info("登录日志插入");
		return isOK >0 ? true : false;
	}
}
