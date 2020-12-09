package com.kkbc.dao.impl;

import java.util.List;

import com.kkbc.dao.DeviceDao;
import com.kkbc.entity.Device;
import com.kkbc.entity.DeviceData;

public class DeviceDaoImpl extends BaseDaoImpl implements DeviceDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Device> getAll() {
		return getSqlMapClientTemplate().queryForList("Device.getAll");
	}

	@Override
	public int updRemark(Device device) {
		return getSqlMapClientTemplate().update("Device.updRemark", device);
	}

	@Override
	public Device getByDeviceId(Long deviceId) {
		return (Device) getSqlMapClientTemplate().queryForObject("Device.getByDeviceId", deviceId);
	}

	@Override
	public Device getByShenfenId(String shefenId) {
		return (Device) getSqlMapClientTemplate().queryForObject("Device.getByShefenId", shefenId);
	}

	@Override
	public long saveDevice(Device device) {
		return (long) getSqlMapClientTemplate().insert("Device.saveData", device);
	}

	@Override
	public int updateEle(Device device) {
		return getSqlMapClientTemplate().update("Device.updateEle", device);
	}

	@Override
	public long saveData(DeviceData data) {
		return (long) getSqlMapClientTemplate().insert("DeviceData.saveData", data);
	}

}
