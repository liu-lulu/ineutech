package com.kkbc.service.impl;

import javax.annotation.Resource;

import com.kkbc.dao.PayHistoryDao;
import com.kkbc.entity.PayHistory;
import com.kkbc.service.PayHistoryService;

public class PayHistoryServiceImpl implements PayHistoryService{
	
	@Resource
	private PayHistoryDao payHistoryDao;

	@Override
	public int saveInfo(PayHistory info) {
		return payHistoryDao.saveInfo(info);
	}

}
