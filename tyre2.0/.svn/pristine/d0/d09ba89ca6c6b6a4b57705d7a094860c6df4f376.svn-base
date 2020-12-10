package com.psylife.service.impl;

import java.util.List;

import com.psylife.dao.TyreHistoryDao;
import com.psylife.dao.impl.TyreHistoryDaoImpl;
import com.psylife.entity.TyreHistory;
import com.psylife.service.TyreHistoryService;

 
public class TyreHistoryServiceImpl implements TyreHistoryService{

	private TyreHistoryDao dao=(TyreHistoryDao)new TyreHistoryDaoImpl();

	@Override
	public List<TyreHistory> getTyreHistoryListByTyreId(int pagenum,
			String tyre_id) {
		return dao.getTyreHistoryListByTyreId(pagenum, tyre_id);
	}
	

}
