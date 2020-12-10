
package com.psylife.service.impl;

import com.psylife.dao.LoginLogDao;
import com.psylife.dao.impl.LoginLogDaoImpl;
import com.psylife.entity.LoginLog;
import com.psylife.service.LoginLogService;


 
public class LoginLogServiceImpl implements LoginLogService{
	
	private LoginLogDao dao=(LoginLogDao)new LoginLogDaoImpl();
	
	@Override
	public boolean insertLog(LoginLog loginLog) {
		return dao.insertLog(loginLog);
	}
}
