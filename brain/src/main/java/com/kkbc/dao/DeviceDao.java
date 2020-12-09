package com.kkbc.dao;

import java.util.List;

import com.kkbc.entity.Device;
import com.kkbc.entity.DeviceData;

public interface DeviceDao {
	
	//获取所有设备
	List<Device> getAll();
	
	int updRemark(Device device);
	
	Device getByDeviceId(Long deviceId);
	
	//根据MAC地址获取设备
	Device getByShenfenId(String shefenId);
	
	long saveDevice(Device device);
	
	//更新设备电量
	int updateEle(Device device);
	
	//保存脑电上送数据
	long saveData(DeviceData data);

}
