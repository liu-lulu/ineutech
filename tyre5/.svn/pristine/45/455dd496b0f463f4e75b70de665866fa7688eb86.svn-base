package com.kkbc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kkbc.dao.DeviceLoginLogDao;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.service.DeviceLoginLogService;

@Service
public class DeviceLoginLogServiceImpl implements DeviceLoginLogService{

	@Resource
	private DeviceLoginLogDao deviceLoginLogDao;
	
	@Override
	public void loginOut(HardwareElement element) {
		deviceLoginLogDao.loginOut(element);
	}
	@Override
	public boolean startSysDtuProcessTrucksFlag() {
		return deviceLoginLogDao.startSysDtuProcessTrucksFlag();
	}
	@Override
	public void endSysDtuProcessTrucksFlag() {
		deviceLoginLogDao.endSysDtuProcessTrucksFlag();
		
	}

	
}
