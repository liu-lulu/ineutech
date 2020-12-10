
package com.kkbc.service.impl;

import javax.annotation.Resource;

import com.kkbc.dao.LoginLogDao;
import com.kkbc.entity.LoginLog;
import com.kkbc.service.LoginLogService;


 
public class LoginLogServiceImpl implements LoginLogService{
	
	@Resource
	private LoginLogDao loginLogDao;
	

	@Override
	public boolean insertLog(LoginLog loginLog) {
		return loginLogDao.insertLog(loginLog);
	}
}
