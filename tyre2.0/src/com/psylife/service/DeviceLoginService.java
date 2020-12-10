package com.psylife.service;

import com.psylife.hardware.HardwareElement;

 
/**
 * 设备登录
 * @author xu
 *
 */
public interface DeviceLoginService extends BaseService {

	/**
	 * 设备登录
	 * @param element
	 * @return
	 */
	boolean login(HardwareElement element);	
	
}
