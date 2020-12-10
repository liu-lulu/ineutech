package com.psylife.service.impl;

import com.psylife.dao.DeviceLoginLogDao;
import com.psylife.dao.impl.DeviceLoginLogDaoImpl;
import com.psylife.hardware.HardwareElement;
import com.psylife.service.DeviceLoginLogService;
 
public class DeviceLoginLogServiceImpl extends BaseServiceImpl implements DeviceLoginLogService{

	private DeviceLoginLogDao dao=(DeviceLoginLogDao)new DeviceLoginLogDaoImpl();
	@Override
	public void loginOut(HardwareElement element) {
		dao.loginOut(element);
	}
	@Override
	public boolean startSysDtuProcessTrucksFlag() {
		return dao.startSysDtuProcessTrucksFlag();
	}

	
}
