package com.kkbc.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.kkbc.dao.DeviceLoginLogDao;
import com.kkbc.entity.DeviceLoginLog;
import com.kkbc.service.DeviceLoginLogService;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.DeviceLoginLogVo;

public class DeviceLoginLogServiceImpl implements DeviceLoginLogService{

	@Resource
	private DeviceLoginLogDao deviceLoginLogDao;
	
	@Override
	public int saveInfo(DeviceLoginLog info) {
		return deviceLoginLogDao.saveInfo(info);
	}

	@Override
	public ListInfo<DeviceLoginLogVo> getLoginLog(Map<String, Object> param) {
		return deviceLoginLogDao.getLoginLog(param);
	}

}
