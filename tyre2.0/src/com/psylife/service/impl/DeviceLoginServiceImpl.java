package com.psylife.service.impl;

import com.psylife.dao.DeviceLoginDao;
import com.psylife.dao.impl.DeviceLoginDaoImpl;
import com.psylife.hardware.HardwareElement;
import com.psylife.service.DeviceLoginService;

 
public class DeviceLoginServiceImpl extends BaseServiceImpl implements DeviceLoginService{

	private DeviceLoginDao dao=(DeviceLoginDao)new DeviceLoginDaoImpl();
	
	@Override
	public boolean login(HardwareElement element) {
		return dao.login(element);
	}

}
