package com.kkbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.hardware.HardwareElement;

/**
 * 车胎设备
 * @author xu
 *
 */
public interface TrucksDeviceDao {
	
	static final Logger logger = LoggerFactory.getLogger(TrucksDeviceDao.class);

	/**
	 * 设备数据保存
	 * @param element
	 */
	void saveDeviceData(HardwareElement element);

	/**
	 * 重新加载设备和车牌对应关系
	 * @param element
	 * @return
	 */
	boolean reloadTrucksDevice(HardwareElement element);

	void updateTrucksFlag(int dtu_status, HardwareElement element);
	
	public void test();
	
}
