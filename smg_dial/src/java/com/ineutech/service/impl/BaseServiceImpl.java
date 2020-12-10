package com.ineutech.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ineutech.dao.BaseDao;
import com.ineutech.service.BaseService;


@Service
public class BaseServiceImpl implements BaseService{
	
	@Resource
	protected BaseDao baseDao;

	@Override
	public long save(Object[] paramters, String sqlCols, String tabeName) {
		return baseDao.save(paramters, sqlCols, tabeName);
	}
	

}
