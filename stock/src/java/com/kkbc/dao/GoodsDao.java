package com.kkbc.dao;

import java.util.Date;
import java.util.List;

import com.kkbc.entity.Goods;
import com.kkbc.vo.GoodsHistoryVO;
import com.kkbc.vo.GoodsVO;

public interface GoodsDao {
	
	Goods getInfo(Goods info);
	List<Goods> getPage(String brand, int pagenum);
	
	int save(Goods info);
	
	int inCount(Goods info);
	
	int outCount(Goods info);

	List<GoodsHistoryVO> getHistoryInfoPage(String brand,Date startTime,Date endTime, String pagenum);
	
	List<GoodsHistoryVO> getHistoryDayInfo(Integer goodsId,Date startTime,Date endTime, String pagenum);
	
	List<GoodsVO> getOperateInfoPage(String brand,Date startTime,Date endTime,Integer remainLow,Integer remainHigh,Float priceLow,Float priceHigh,String column,String order, String pagenum);
}
