package com.kkbc.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kkbc.cons.Constans;
import com.kkbc.dao.GoodsDao;
import com.kkbc.entity.Goods;

public class GoodsDaoImpl extends BaseDaoImpl implements GoodsDao{

	@Override
	public int save(List<Goods> info) {
		getSqlMapClientTemplate().insert("Goods.saveInfo", info);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> get(Goods info) {
		return getSqlMapClientTemplate().queryForList("Goods.getInfo", info);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> getPageInfo(String brand, int pagenum) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("brand", brand);
		param.put("startIndex", ((pagenum-1)*Constans.PAGE_SIZE));
		param.put("pageSize", Constans.PAGE_SIZE);
		
		return getSqlMapClientTemplate().queryForList("Goods.getPage", param);
	}

	@Override
	public int updImg(Goods info) {
		return getSqlMapClientTemplate().update("Goods.updImg", info);
	}

}
