package com.ineutech.service.impl;

import javax.annotation.Resource;

import com.ineutech.dao.DeviceLoginLogDao;
import com.ineutech.entity.DeviceLoginLog;
import com.ineutech.service.DeviceLoginLogService;

public class DeviceLoginLogServiceImpl implements DeviceLoginLogService{

	@Resource
	private DeviceLoginLogDao deviceLoginLogDao;
	
	@Override
	public int saveInfo(DeviceLoginLog info) {
		return deviceLoginLogDao.saveInfo(info);
	}

}
