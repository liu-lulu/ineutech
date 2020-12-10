package com.kkbc.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kkbc.dao.TyreHistoryDao;
import com.kkbc.entity.TyreHistory;
import com.psylife.util.Constants;

@Repository
public class TyreHistoryDaoImpl extends BaseDaoImpl implements TyreHistoryDao{
	static final Logger logger = LoggerFactory.getLogger(TyreHistoryDao.class);

	@Override
	public List<TyreHistory> getTyreHistoryListByTyreId(int pagenum,String tyre_id){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("tyre_id", tyre_id);
		params.put("startIndex", (pagenum-1)*Constants.PAGESIZE);
		params.put("pageSize", Constants.PAGESIZE);
		@SuppressWarnings("unchecked")
		List<TyreHistory> list=getSqlMapClientTemplate().queryForList("TyreHistory.getListByTyreId", params);
		logger.info("查询轮胎轨迹列表成功！");
		return list;
	}
}
