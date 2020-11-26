package com.ineutech.service.impl;

import com.ineutech.dao.BaseDao;
import com.ineutech.service.BaseService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


@Service
public class BaseServiceImpl implements BaseService{
	
	@Resource
	protected BaseDao baseDao;

	@Override
	public long save(Object[] paramters, String sqlCols, String tabeName) {
		return baseDao.save(paramters, sqlCols, tabeName);
	}
	

}
