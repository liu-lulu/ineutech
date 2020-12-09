package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kkbc.dao.DeviceDao;
import com.kkbc.entity.Device;
import com.kkbc.entity.DeviceData;
import com.kkbc.service.DeviceService;

public class DeviceServiceImpl implements DeviceService {
	
	@Resource
	private DeviceDao deviceDao;

	@Override
	public List<Device> getAll() {
		return deviceDao.getAll();
	}

	@Override
	public int updRemark(Device device) {
		return deviceDao.updRemark(device);
	}

	@Override
	public Device getByDeviceId(Long deviceId) {
		return deviceDao.getByDeviceId(deviceId);
	}

	@Override
	public Device getByShenfenId(String shefenId) {
		return deviceDao.getByShenfenId(shefenId);
	}

	@Override
	public long saveDevice(Device device) {
		return deviceDao.saveDevice(device);
	}

	@Override
	public int updateEle(Device device) {
		return deviceDao.updateEle(device);
	}

	@Override
	public long saveData(DeviceData data) {
		return deviceDao.saveData(data);
	}

}
