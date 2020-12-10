package com.kkbc.service.impl;

import javax.annotation.Resource;

import com.kkbc.dao.DeviceLoginLogDao;
import com.kkbc.entity.DeviceLoginLog;
import com.kkbc.service.DeviceLoginLogService;

public class DeviceLoginLogServiceImpl implements DeviceLoginLogService{

	@Resource
	private DeviceLoginLogDao deviceLoginLogDao;
	
	@Override
	public int saveInfo(DeviceLoginLog info) {
		return deviceLoginLogDao.saveInfo(info);
	}

}
