package com.kkbc.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.kkbc.cons.Constans;
import com.kkbc.dao.GoodsDao;
import com.kkbc.entity.Goods;
import com.kkbc.vo.GoodsHistoryVO;
import com.kkbc.vo.GoodsVO;

public class GoodsDaoImpl extends BaseDaoImpl implements GoodsDao{

	@Override
	public Goods getInfo(Goods info) {
		return (Goods) getSqlMapClientTemplate().queryForObject("Goods.getInfo", info);
	}

	@Override
	public int save(Goods info) {
		return (int) getSqlMapClientTemplate().insert("Goods.saveInfo", info);
	}

	@Override
	public int inCount(Goods info) {
		return getSqlMapClientTemplate().update("Goods.inCount", info);
	}

	@Override
	public int outCount(Goods info) {
		return getSqlMapClientTemplate().update("Goods.outCount", info);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> getPage(String brand, int pagenum) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("brand", brand);
		param.put("startIndex", ((pagenum-1)*Constans.PAGE_SIZE));
		param.put("pageSize", Constans.PAGE_SIZE);
		return getSqlMapClientTemplate().queryForList("Goods.getPage", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsHistoryVO> getHistoryInfoPage(String brand, Date startTime,
			Date endTime, String pagenum) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("brand", brand);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		if (StringUtils.isNotEmpty(pagenum)) {
			int pageNo=Integer.valueOf(pagenum);
			param.put("startIndex", ((pageNo-1)*Constans.PAGE_SIZE));
		}else {
			param.put("startIndex", null);
		}
		param.put("pageSize", Constans.PAGE_SIZE);
		return getSqlMapClientTemplate().queryForList("History.getDetailPage", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsVO> getOperateInfoPage(String brand,Date startTime,Date endTime,Integer remainLow,Integer remainHigh,Float priceLow,Float priceHigh,String column,String order, String pagenum) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("brand", brand);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("remainLow", remainLow);
		param.put("remainHigh", remainHigh);
		param.put("priceLow", priceLow);
		param.put("priceHigh", priceHigh);
		param.put("column", column);
		param.put("order", order);
		if (StringUtils.isNotEmpty(pagenum)) {
			int pageNo=Integer.valueOf(pagenum);
			param.put("startIndex", ((pageNo-1)*Constans.PAGE_SIZE));
		}else {
			param.put("startIndex", null);
		}
		param.put("pageSize", Constans.PAGE_SIZE);
		return getSqlMapClientTemplate().queryForList("Goods.getDetailPage", param);
	}

	@Override
	public List<GoodsHistoryVO> getHistoryDayInfo(Integer goodsId,
			Date startTime, Date endTime, String pagenum) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("goodsId", goodsId);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		if (StringUtils.isNotEmpty(pagenum)) {
			int pageNo=Integer.valueOf(pagenum);
			param.put("startIndex", ((pageNo-1)*Constans.PAGE_SIZE));
		}else {
			param.put("startIndex", null);
		}
		param.put("pageSize", Constans.PAGE_SIZE);
		return getSqlMapClientTemplate().queryForList("Goods.getDayDetail", param);
	}

}
