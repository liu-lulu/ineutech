package com.ineutech.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ineutech.dao.DeviceLoginLogDao;
import com.ineutech.entity.DeviceLoginLog;

public class DeviceLoginLogDaoImpl extends BaseDaoImpl implements DeviceLoginLogDao{
	
	static final Logger logger = LoggerFactory.getLogger(DeviceLoginLogDaoImpl.class);
	
	@Override
	public int saveInfo(DeviceLoginLog info) {
		long result=(long) getSqlMapClientTemplate().insert("DeviceLoginLog.saveData", info);
		if (result>0) {
			logger.info(info.getHardId()+"设备"+(info.getType()==DeviceLoginLog.TYPE_LOGIN?"登陆":"登出"));
			return 1;
		}
		return 0;
	}

}
