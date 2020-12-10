package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kkbc.dao.GoodsDao;
import com.kkbc.entity.Goods;
import com.kkbc.service.GoodsService;

public class GoodsServiceImpl implements GoodsService{
	
	@Resource
	private GoodsDao goodsDao;

	@Override
	public int save(List<Goods> info) {
		return goodsDao.save(info);
	}

	@Override
	public List<Goods> get(String brand,String model) {
		Goods info=new Goods(brand,model,null);
		return goodsDao.get(info);
	}

	@Override
	public List<Goods> getPageInfo(String brand, int pagenum) {
		return goodsDao.getPageInfo(brand, pagenum);
	}

	@Override
	public int updImg(Goods info) {
		return goodsDao.updImg(info);
	}

}
