package com.kkbc.dao;

import java.util.Map;

import com.kkbc.entity.DeviceLoginLog;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.DeviceLoginLogVo;

public interface DeviceLoginLogDao extends BaseDao{
	
	//保存设备与服务器的连接或断开日志
	int saveInfo(DeviceLoginLog info);
	
	/**
	 * 获取登陆日志
	 * @param param 条件参数
	 * @return
	 */
	ListInfo<DeviceLoginLogVo> getLoginLog(Map<String, Object> param);
}
