package com.kkbc.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.kkbc.dao.HistoryDao;
import com.kkbc.entity.History;
import com.kkbc.service.HistoryService;
import com.kkbc.vo.GoodsHistoryVO;

public class HistoryServiceImpl implements HistoryService{
	
	@Resource
	private HistoryDao historyDao;

	@Override
	public int saveInfo(History info) {
		return historyDao.saveInfo(info);
	}

	@Override
	public List<GoodsHistoryVO> getPage(Integer goodsId, Integer type,
			Date startTime, Date endTime, int pagenum) {
		return historyDao.getPage(goodsId, type, startTime, endTime, pagenum);
	}

	@Override
	public List<String> getOperateDate() {
		return historyDao.getOperateDate();
	}

}
