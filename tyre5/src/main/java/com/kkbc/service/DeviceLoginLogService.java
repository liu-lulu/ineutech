package com.kkbc.service;

import com.kkbc.hardware.HardwareElement;

 
/**
 * 设备登录日志
 * @author xu
 *
 */
public interface DeviceLoginLogService {

	void loginOut(HardwareElement element);	
	
	/**
	 * 系统启动时处理dtu离线时,行驶状态为停放
	 * @return
	 */
	boolean startSysDtuProcessTrucksFlag();	
	
	/**
	 * 服务器关闭时,所有监视车辆行驶状态为停放
	 */
	public void endSysDtuProcessTrucksFlag();
}
