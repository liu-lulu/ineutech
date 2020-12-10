package com.kkbc.service;

import java.util.Date;
import java.util.List;

import com.kkbc.entity.Goods;
import com.kkbc.vo.GoodsHistoryVO;
import com.kkbc.vo.GoodsVO;

public interface GoodsService {
	
	Goods getByCode(String code);
	List<Goods> getPage(String brand, int pagenum);
	
	int save(Goods info);
	
	int in(Goods goods,int count);
	int in(String code, int count);
	
	int out(Goods goods,int count);
	int out(String code, int count);
	
	List<GoodsHistoryVO> getHistoryInfoPage(String brand,Date startTime,Date endTime, String pagenum);
	
	List<GoodsHistoryVO> getHistoryDayInfo(Integer goodsId,Date startTime,Date endTime, String pagenum);
	
	List<GoodsVO> getOperateInfoPage(String brand,Date startTime,Date endTime,Integer remainLow,Integer remainHigh,Float priceLow,Float priceHigh,String column,String order, String pagenum);
	

}
