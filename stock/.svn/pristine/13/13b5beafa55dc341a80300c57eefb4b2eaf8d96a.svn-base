package com.kkbc.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.kkbc.dao.GoodsDao;
import com.kkbc.dao.HistoryDao;
import com.kkbc.entity.Goods;
import com.kkbc.entity.History;
import com.kkbc.service.GoodsService;
import com.kkbc.vo.GoodsHistoryVO;
import com.kkbc.vo.GoodsVO;

public class GoodsServiceImpl implements GoodsService{
	
	@Resource
	private GoodsDao goodsDao;
	
	@Resource
	private HistoryDao historyDao;

	@Override
	public Goods getByCode(String code) {
		Goods goods=new Goods();
		goods.setBarcode(code);
		return goodsDao.getInfo(goods);
	}

	@Override
	public int save(Goods info) {
		int goodsId=goodsDao.save(info);
		if (goodsId!=0&&info.getRemain_count()!=0) {
			History historyInfo=new History(goodsId, History.TYPE_IN, info.getRemain_count(), new Date());
			historyDao.saveInfo(historyInfo);
		}
		return 1;
	}

	@Override
	public int in(Goods goods, int count) {
		if (count>0) {
//			Goods goods=new Goods();
//			goods.setGoods_id(goodsId);
			goods.setRemain_count(count);
			int ret=goodsDao.inCount(goods);
			if (ret!=0) {
				History history=new History(goods.getGoods_id(), History.TYPE_IN, count, new Date());
				historyDao.saveInfo(history);
				return 1;
			}
		}
		return 0;
	}

	@Override
	public int out(Goods param, int count) {
		
		if (count>0) {
//			Goods param=new Goods();
//			param.setGoods_id(goodsId);
			Goods info=goodsDao.getInfo(param);
			if (info.getRemain_count()>=count) {
//				Goods goods=new Goods();
//				goods.setGoods_id(goodsId);
				param.setRemain_count(count);
				int ret=goodsDao.outCount(param);
				if (ret!=0) {
					History history=new History(param.getGoods_id(), History.TYPE_OUT, count, new Date());
					historyDao.saveInfo(history);
					return 1;
				}
			}
		}
		return 0;
	}

	@Override
	public List<Goods> getPage(String brand, int pagenum) {
		return goodsDao.getPage(brand, pagenum);
	}

	@Override
	public int in(String code, int count) {
		Goods info=getByCode(code);
		if (info!=null) {
			return in(info, count);
		}
		
		return 0;
	}

	@Override
	public int out(String code, int count) {
		Goods info=getByCode(code);
		if (info!=null) {
			return out(info, count);
		}
		return 0;
	}

	@Override
	public List<GoodsHistoryVO> getHistoryInfoPage(String brand, Date startTime,
			Date endTime, String pagenum) {
		return goodsDao.getHistoryInfoPage(brand, startTime, endTime, pagenum);
	}

	@Override
	public List<GoodsVO> getOperateInfoPage(String brand,Date startTime,Date endTime,Integer remainLow,Integer remainHigh,Float priceLow,Float priceHigh,String column,String order, String pagenum) {
		return goodsDao.getOperateInfoPage(brand, startTime, endTime,remainLow,remainHigh,priceLow,priceHigh,column,order, pagenum);
	}

	@Override
	public List<GoodsHistoryVO> getHistoryDayInfo(Integer goodsId,
			Date startTime, Date endTime, String pagenum) {
		return goodsDao.getHistoryDayInfo(goodsId, startTime, endTime, pagenum);
	}

}
