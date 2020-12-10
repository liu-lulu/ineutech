package com.kkbc.dao;

import java.util.Date;
import java.util.List;

import com.kkbc.entity.History;
import com.kkbc.vo.GoodsHistoryVO;

public interface HistoryDao {
	int saveInfo(History info);
	
	List<GoodsHistoryVO> getPage(Integer goodsId,Integer type,Date startTime,Date endTime, int pagenum);
	
	List<String> getOperateDate();

}
