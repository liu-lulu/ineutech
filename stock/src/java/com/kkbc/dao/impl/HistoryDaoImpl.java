package com.kkbc.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kkbc.cons.Constans;
import com.kkbc.dao.HistoryDao;
import com.kkbc.entity.History;
import com.kkbc.vo.GoodsHistoryVO;

public class HistoryDaoImpl extends BaseDaoImpl implements HistoryDao{

	@Override
	public int saveInfo(History info) {
		getSqlMapClientTemplate().insert("History.saveInfo", info);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsHistoryVO> getPage(Integer goodsId, Integer type,
			Date startTime, Date endTime, int pagenum) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("goodsId", goodsId);
		param.put("type", type);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("startIndex", ((pagenum-1)*Constans.PAGE_SIZE));
		param.put("pageSize", Constans.PAGE_SIZE);
		
		return getSqlMapClientTemplate().queryForList("History.getPage", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOperateDate() {
		return getSqlMapClientTemplate().queryForList("History.getDate");
	}

}
