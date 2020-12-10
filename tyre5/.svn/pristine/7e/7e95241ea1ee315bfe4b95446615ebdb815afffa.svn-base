package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kkbc.dao.TyreHistoryDao;
import com.kkbc.entity.TyreHistory;
import com.kkbc.service.TyreHistoryService;

 
public class TyreHistoryServiceImpl implements TyreHistoryService{

	@Resource
	private TyreHistoryDao tyreHistoryDao;

	@Override
	public List<TyreHistory> getTyreHistoryListByTyreId(int pagenum,
			String tyre_id) {
		return tyreHistoryDao.getTyreHistoryListByTyreId(pagenum, tyre_id);
	}
	

}
