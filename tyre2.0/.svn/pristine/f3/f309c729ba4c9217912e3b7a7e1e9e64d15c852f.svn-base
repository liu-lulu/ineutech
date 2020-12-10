package com.psylife.service.impl;

import com.psylife.dao.BaseDao;
import com.psylife.dao.impl.BaseDaoImpl;
import com.psylife.service.BaseService;


 
public class BaseServiceImpl implements BaseService{
	
	protected BaseDao dao=(BaseDao)new BaseDaoImpl();

	@Override
	public long save(Object[] paramters, String sqlCols, String tabeName) {
		return dao.save(paramters, sqlCols, tabeName);
	}
	

}
