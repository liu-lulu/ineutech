package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.kkbc.dao.TyreBaseDao;
import com.kkbc.entity.TyreBase;
import com.kkbc.service.TyreBaseService;
import com.kkbc.vo.TyreByAdminVO;
import com.kkbc.vo.TyreCountVO;
import com.kkbc.vo.TyreRemarkVO;
import com.kkbc.vo.TyreVO;

 
public class TyreBaseServiceImpl implements TyreBaseService{

	@Resource
	private TyreBaseDao tyreBaseDao;
	
	@Override
	public int saveByList(List<TyreBase> tyreBases, Integer user_id,boolean isWorkOrder) {
		return tyreBaseDao.saveByList(tyreBases, user_id,isWorkOrder);
	}
	
	@Override
	public TyreVO tyreDetial(Integer user_id, String tyre_id, String trucks_id,
			String tyre_where) {
		return tyreBaseDao.tyreDetial(user_id, tyre_id, trucks_id, tyre_where);
	}

	@Override
	public int tyreToTrucks(String trucks_id, String tyre_id,
			String tyre_where, Integer user_id, Double mabiao) {
		return tyreBaseDao.tyreToTrucks(trucks_id, tyre_id, tyre_where, user_id, mabiao);
	}

	@Override
	public int tyreDown(String trucks_id, String tyre_id, String tyre_where,
			Integer user_id,Double mabiao) {
		return tyreBaseDao.tyreDown(trucks_id, tyre_id, tyre_where, user_id,mabiao);
	}

	@Override
	public int tyreExchange(String tyre_id1, String tyre_id2, Integer user_id,Double mabiao1,Double mabiao2) {
		return tyreBaseDao.tyreExchange(tyre_id1, tyre_id2, user_id,mabiao1,mabiao2);
	}

	@Override
	public List<JSONObject> tuiJian(int id) {
		return tyreBaseDao.tuiJian(id);
	}

	@Override
	public List<TyreCountVO> countTyreInfo(Integer user_id, String tyre_brand,
			String tyre_type1, String tyre_type2, Integer tyre_health,String column,String order) {
		return tyreBaseDao.countTyreInfo(user_id,tyre_brand,tyre_type1,tyre_type2,tyre_health,column,order);
	}

	@Override
	public TyreByAdminVO tyreDetialByAdmin(String tyre_id) {
		return tyreBaseDao.tyreDetialByAdmin(tyre_id);
	}

	@Override
	public List<TyreVO> getTyreList(int pagenum, Integer user_id,
			String tyre_brand, String tyre_type1, String tyre_type2,
			String tyre_type3, Integer tyre_flag, Integer tyre_health,
			String keyWord) {
		return tyreBaseDao.getTyreList(pagenum, user_id, tyre_brand, tyre_type1, tyre_type2, tyre_type3, tyre_flag, tyre_health, keyWord);
	}

	@Override
	public List<TyreVO> searchByKeyWord(int pagenum, Integer user_id,
			String keyWord) {
		return tyreBaseDao.searchByKeyWord(pagenum, user_id, keyWord);
	}

	@Override
	public List<TyreRemarkVO> tyreTips(Integer user_id) {
		return tyreBaseDao.tyreTips(user_id);
	}

	@Override
	public int tyreByDriverXiuBu(String tyre_id, Integer user_id) {
		return tyreBaseDao.tyreByDriverXiuBu(tyre_id, user_id);
	}

	@Override
	public List<TyreVO> searchByKucun(int pagenum, Integer user_id,
			String keyWord, String tyre_brand,
			String tyre_type1, String tyre_type2, Integer state,Integer tyre_flag,String column,String order) {
		return tyreBaseDao.searchByKucun(pagenum, user_id, keyWord, tyre_brand,tyre_type1,tyre_type2,state,tyre_flag,column,order);
	}

}
