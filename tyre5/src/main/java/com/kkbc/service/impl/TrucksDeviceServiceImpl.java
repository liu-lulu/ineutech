package com.kkbc.service.impl;

import javax.annotation.Resource;

import com.kkbc.dao.TrucksDeviceDao;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.service.TrucksDeviceService;

public class TrucksDeviceServiceImpl implements TrucksDeviceService {
	
	@Resource
	private TrucksDeviceDao trucksDeviceDao;

	@Override
	public void saveDeviceData(HardwareElement element) {
		trucksDeviceDao.saveDeviceData(element);
	}

	@Override
	public boolean reloadTrucksDevice(HardwareElement element) {
		return trucksDeviceDao.reloadTrucksDevice(element);
	}

	@Override
	public void test() {
		trucksDeviceDao.test();
		
	}


}
