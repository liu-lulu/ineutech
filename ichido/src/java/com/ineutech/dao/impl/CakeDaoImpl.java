package com.ineutech.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ineutech.cons.Constans;
import com.ineutech.dao.CakeDao;
import com.ineutech.entity.cake.CakeEmployee;
import com.ineutech.entity.cake.CakeLoginLog;
import com.ineutech.entity.cake.CakeShop;
import com.ineutech.entity.cake.CakeTransaction;

public class CakeDaoImpl extends BaseDaoImpl implements CakeDao{

	@Override
	public CakeEmployee login(CakeEmployee loginInfo) {
		return (CakeEmployee) getSqlMapClientTemplate().queryForObject("Cake.login", loginInfo);
	}

	@Override
	public List<CakeTransaction> transactions(String shopCode,String e_number, Date beginDate,
			Date endDate,Integer e_id,Integer flag, Integer pageNo) {
		Map<String, Object> params=new HashMap<>();
		params.put("shop_code", shopCode);
		params.put("e_number", e_number);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("flag", flag);
		params.put("e_id", e_id);
		if (pageNo!=null) {
			params.put("startIndex", ((pageNo-1)*Constans.PAGE_SIZE));
			params.put("pageSize", Constans.PAGE_SIZE);
		}
		return getSqlMapClientTemplate().queryForList("Cake.transactions", params);
	}

	@Override
	public Integer transactionsCount(String shopCode,String e_number, Date beginDate,
			Date endDate,Integer e_id,Integer flag) {
		Map<String, Object> params=new HashMap<>();
		params.put("shop_code", shopCode);
		params.put("e_number", e_number);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("flag", flag);
		params.put("e_id", e_id);
		return (Integer) getSqlMapClientTemplate().queryForObject("Cake.transactionsCount", params);
	}

	@Override
	public List<CakeShop> shops() {
		return getSqlMapClientTemplate().queryForList("Cake.shops");
	}

	@Override
	public Integer confirm(List<Integer> cakeIdList, Integer e_id,
			Date confirm_time, int flag) {
		Map<String, Object> params=new HashMap<>();
		params.put("cakeIdList", cakeIdList);
		params.put("e_id", e_id);
		params.put("confirm_time", confirm_time);
		params.put("flag", flag);
		
		return getSqlMapClientTemplate().update("Cake.confirm", params);
	}

	@Override
	public List<CakeTransaction> managerSale(Integer e_id,Integer flag,Date beginDate,Date endDate) {
		Map<String, Object> params=new HashMap<>();
		params.put("e_id", e_id);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		params.put("flag", flag);
		return getSqlMapClientTemplate().queryForList("Cake.managerSale", params);
	}


	@Override
	public Integer addEmployee(CakeEmployee info) {
		return (Integer) getSqlMapClientTemplate().insert("Cake.saveEmployee", info);
	}

	@Override
	public Integer delEmployee(int e_id) {
		return getSqlMapClientTemplate().delete("Cake.delEmployee", e_id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CakeEmployee> getEmployees(String info) {
		return getSqlMapClientTemplate().queryForList("Cake.getEmployee", info);
	}

	@Override
	public List<CakeTransaction> employeeSale(String shopCode, Date beginDate,
			Date endDate) {
		Map<String, Object> params=new HashMap<>();
		params.put("shop_code", shopCode);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		return getSqlMapClientTemplate().queryForList("Cake.employeeSale", params);
	}

	@Override
	public Integer updPwd(Integer e_id, String pwd) {
		Map<String, Object> params=new HashMap<>();
		params.put("e_id", e_id);
		params.put("pwd", pwd);
		return getSqlMapClientTemplate().update("Cake.updPwd", params);
	}

	@Override
	public List<CakeTransaction> transactionsRecord(String shopCode,
			Date beginDate, Date endDate, Integer pageNo) {
		Map<String, Object> params=new HashMap<>();
		params.put("shop_code", shopCode);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		if (pageNo!=null) {
			params.put("startIndex", ((pageNo-1)*Constans.PAGE_SIZE));
			params.put("pageSize", Constans.PAGE_SIZE);
		}
		return getSqlMapClientTemplate().queryForList("Cake.transactionsRecord", params);
	}

	@Override
	public Integer transactionsRecordCount(String shopCode, Date beginDate,
			Date endDate) {
		Map<String, Object> params=new HashMap<>();
		params.put("shop_code", shopCode);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		return (Integer) getSqlMapClientTemplate().queryForObject("Cake.transactionsRecordCount", params);
	}

	@Override
	public Integer saveLoginLog(CakeLoginLog info) {
		return (Integer) getSqlMapClientTemplate().insert("Cake.saveLoginlog", info);
	}

	@Override
	public Integer saveTransaction(CakeTransaction info) {
		return (Integer) getSqlMapClientTemplate().insert("Cake.saveTrans",info);
	}

	@Override
	public Integer updTransaction(CakeTransaction info) {
		return getSqlMapClientTemplate().update("Cake.updTrans", info);
	}

	@Override
	public Integer delTransaction(int cakeid) {
		return getSqlMapClientTemplate().delete("Cake.delTrans", cakeid);
	}

	@Override
	public List<CakeEmployee> getManager() {
		return getSqlMapClientTemplate().queryForList("Cake.getManager");
	}



}
