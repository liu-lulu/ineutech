package com.kkbc.dao;

import com.kkbc.entity.DeviceLoginLog;

public interface DeviceLoginLogDao extends BaseDao{
	
	//保存设备与服务器的连接或断开日志
	int saveInfo(DeviceLoginLog info);
}
