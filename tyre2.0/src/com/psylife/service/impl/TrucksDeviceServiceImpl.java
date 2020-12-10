package com.psylife.service.impl;

import com.psylife.dao.TrucksDeviceDao;
import com.psylife.dao.impl.TrucksDeviceDaoImpl;
import com.psylife.hardware.HardwareElement;
import com.psylife.service.TrucksDeviceService;

public class TrucksDeviceServiceImpl implements TrucksDeviceService {
	
	private TrucksDeviceDao dao=(TrucksDeviceDao)new TrucksDeviceDaoImpl();

	@Override
	public void saveDeviceData(HardwareElement element) {
		dao.saveDeviceData(element);
	}

	@Override
	public boolean reloadTrucksDevice(HardwareElement element) {
		return dao.reloadTrucksDevice(element);
	}

	@Override
	public int readWarnMsg(int id) {
		return dao.readWarnMsg(id);
	}


}
