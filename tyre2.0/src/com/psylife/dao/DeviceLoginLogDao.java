package com.psylife.dao;

import com.psylife.hardware.HardwareElement;

 
/**
 * 设备登录日志
 * @author xu
 *
 */
public interface DeviceLoginLogDao extends BaseDao {

	void loginOut(HardwareElement element);

	/**
	 * 系统启动时处理dtu离线时,行驶状态为停放
	 * @return
	 */
	boolean startSysDtuProcessTrucksFlag();	
	
}
