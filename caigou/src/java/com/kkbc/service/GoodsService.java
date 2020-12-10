package com.kkbc.service;

import java.util.List;

import com.kkbc.entity.Goods;

public interface GoodsService {
	
	int save(List<Goods> info);
	
	List<Goods> get(String brand,String model);
	
	List<Goods> getPageInfo(String brand, int pagenum);
	
	int updImg(Goods info);

}
