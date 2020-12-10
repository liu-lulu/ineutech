package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import com.kkbc.dao.TyrePatternDao;
import com.kkbc.entity.TyrePattern;
import com.kkbc.entity.TyrePattern2;
import com.kkbc.service.TyrePatternService;

 
public class TyrePatternServiceImpl implements TyrePatternService{

	@Resource
	private TyrePatternDao tyrePatternDao;
	
	@Override
	public int saveTyrePattern(TyrePattern tyrePattern) {
		return tyrePatternDao.saveTyrePattern(tyrePattern);
	}
	@Override
	public JSONArray saveTyrePatternList(List<TyrePattern2> list,
			Integer user_id,int flag) {
		return tyrePatternDao.saveTyrePatternList(list, user_id,flag);
	}
	@Override
	public int saveTyrePatternItem(TyrePattern2 tyrePattern2, Integer user_id,
			int flag, String tyre_id, String item, String remark,
			String repaircontent, Double mabiao) {
		return tyrePatternDao.saveTyrePatternItem(tyrePattern2, user_id, flag, tyre_id, item, remark, repaircontent, mabiao);
	}

}
