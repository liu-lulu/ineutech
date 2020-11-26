package com.kkbc.service.impl;

import javax.annotation.Resource;

import com.kkbc.dao.DeviceDao;
import com.kkbc.entity.YifuTest;
import com.kkbc.service.TestService;

public class TestServiceImpl implements TestService{
	
	@Resource
	private DeviceDao deviceDao;

	@Override
	public int saveStuScore(YifuTest info) {
		return deviceDao.saveData(info);
	}

}
