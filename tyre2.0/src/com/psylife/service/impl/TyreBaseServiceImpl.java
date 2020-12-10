package com.psylife.service.impl;

import java.util.List;

import net.sf.json.JSONObject;

import com.psylife.dao.TyreBaseDao;
import com.psylife.dao.impl.TyreBaseDaoImpl;
import com.psylife.entity.TyreBase;
import com.psylife.service.TyreBaseService;
import com.psylife.vo.TyreByAdminVO;
import com.psylife.vo.TyreCountVO;
import com.psylife.vo.TyreRemarkVO;
import com.psylife.vo.TyreVO;

 
public class TyreBaseServiceImpl extends BaseServiceImpl implements TyreBaseService{

	private TyreBaseDao dao=(TyreBaseDao)new TyreBaseDaoImpl();
	
	@Override
	public int saveByList(List<TyreBase> tyreBases, Integer user_id,boolean isWorkOrder) {
		return dao.saveByList(tyreBases, user_id,isWorkOrder);
	}
	
	@Override
	public TyreVO tyreDetial(Integer user_id, String tyre_id, String trucks_id,
			String tyre_where) {
		return dao.tyreDetial(user_id, tyre_id, trucks_id, tyre_where);
	}

	@Override
	public int tyreToTrucks(String trucks_id, String tyre_id,
			String tyre_where, Integer user_id, Double mabiao) {
		return dao.tyreToTrucks(trucks_id, tyre_id, tyre_where, user_id, mabiao);
	}

	@Override
	public int tyreDown(String trucks_id, String tyre_id, String tyre_where,
			Integer user_id,Double mabiao) {
		return dao.tyreDown(trucks_id, tyre_id, tyre_where, user_id,mabiao);
	}

	@Override
	public int tyreExchange(String tyre_id1, String tyre_id2, Integer user_id,Double mabiao1,Double mabiao2) {
		return dao.tyreExchange(tyre_id1, tyre_id2, user_id,mabiao1,mabiao2);
	}

	@Override
	public List<JSONObject> tuiJian(int id) {
		return dao.tuiJian(id);
	}

	@Override
	public List<TyreCountVO> countTyreInfo(Integer user_id, String tyre_brand,
			String tyre_type1, String tyre_type3, Integer tyre_health,String column,String order) {
		return dao.countTyreInfo(user_id,tyre_brand,tyre_type1,tyre_type3,tyre_health,column,order);
	}

	@Override
	public TyreByAdminVO tyreDetialByAdmin(String tyre_id) {
		return dao.tyreDetialByAdmin(tyre_id);
	}

	@Override
	public List<TyreVO> getTyreList(int pagenum, Integer user_id,
			String tyre_brand, String tyre_type1, String tyre_type2,
			String tyre_type3, Integer tyre_flag, Integer tyre_health,
			String keyWord) {
		return dao.getTyreList(pagenum, user_id, tyre_brand, tyre_type1, tyre_type2, tyre_type3, tyre_flag, tyre_health, keyWord);
	}

	@Override
	public List<TyreVO> searchByKeyWord(int pagenum, Integer user_id,
			String keyWord) {
		return dao.searchByKeyWord(pagenum, user_id, keyWord);
	}

	@Override
	public List<TyreRemarkVO> tyreTips(Integer user_id) {
		return dao.tyreTips(user_id);
	}

	@Override
	public int tyreByDriverXiuBu(String tyre_id, Integer user_id) {
		return dao.tyreByDriverXiuBu(tyre_id, user_id);
	}

	@Override
	public List<TyreVO> searchByKucun(int pagenum, Integer user_id,
			String keyWord, String tyre_brand,
			String tyre_type1, String tyre_type3, Integer state,Integer tyre_flag,String column,String order) {
		return dao.searchByKucun(pagenum, user_id, keyWord, tyre_brand,tyre_type1,tyre_type3,state,tyre_flag,column,order);
	}

	@Override
	public List<TyreRemarkVO> tyreTips(Integer user_id, int pagenum) {
		return dao.tyreTips(user_id, pagenum);
	}

}
