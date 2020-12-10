package com.psylife.service.impl;

import java.util.List;

import net.sf.json.JSONArray;

import com.psylife.dao.TyrePatternDao;
import com.psylife.dao.impl.TyrePatternDaoImpl;
import com.psylife.entity.TyrePattern;
import com.psylife.entity.TyrePattern2;
import com.psylife.service.TyrePatternService;
import com.psylife.vo.TreadPattern;

 
public class TyrePatternServiceImpl extends BaseServiceImpl implements TyrePatternService{

	private TyrePatternDao dao=(TyrePatternDao)new TyrePatternDaoImpl();
	@Override
	public int saveTyrePattern(TyrePattern tyrePattern) {
		return dao.saveTyrePattern(tyrePattern);
	}
	@Override
	public JSONArray saveTyrePatternList(List<TyrePattern2> list,
			Integer user_id,int flag) {
		return dao.saveTyrePatternList(list, user_id,flag);
	}
	@Override
	public int saveTyrePatternItem(TyrePattern2 tyrePattern2, Integer user_id,
			int flag, String tyre_id, String item, String remark,
			String repaircontent, Double mabiao) {
		return dao.saveTyrePatternItem(tyrePattern2, user_id, flag, tyre_id, item, remark, repaircontent, mabiao);
	}
	@Override
	public JSONArray saveTyrePatternListByTool(List<TreadPattern> patternList) {
		return dao.saveTyrePatternListByTool(patternList);
	}
	@Override
	public int mabiaoWork(Integer user_id, Double mabiao, String trucksId) {
		return dao.mabiaoWork(user_id, mabiao, trucksId);
	}

}
