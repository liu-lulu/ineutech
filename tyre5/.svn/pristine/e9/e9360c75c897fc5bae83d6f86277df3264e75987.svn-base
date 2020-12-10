package com.kkbc.service.impl;

import javax.annotation.Resource;

import com.kkbc.dao.DeviceLoginDao;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.service.DeviceLoginService;

 
public class DeviceLoginServiceImpl implements DeviceLoginService{

	@Resource
	private DeviceLoginDao deviceLoginDao;
	


	@Override
	public boolean login(HardwareElement element) {
		return deviceLoginDao.login(element);
	}

}
