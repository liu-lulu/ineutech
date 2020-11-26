package com.ineutech.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ineutech.entity.KpiLevel;
import com.ineutech.entity.KpiResult;
import com.ineutech.entity.LoginShop;
import com.ineutech.entity.TransactionHistory;
import com.ineutech.cons.Constans;
import com.ineutech.dao.UserDao;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public LoginShop getLoginShop(LoginShop loginInfo) {
		return (LoginShop) getSqlMapClientTemplate().queryForObject("Shop.login", loginInfo);
		
	}

	@Override
	public List<TransactionHistory> getHistories(String shopCode,
			Date beginDate, Date endDate,Integer pageNo) {
		
		Map<String, Object> paraMap=new HashMap<String, Object>();
		if (pageNo!=null) {
			paraMap.put("startIndex", ((pageNo-1)*Constans.PAGE_SIZE));
			paraMap.put("pageSize", Constans.PAGE_SIZE);
		}
		paraMap.put("shop_code", shopCode);
		paraMap.put("beginDate", beginDate);
		paraMap.put("endDate", endDate);
		return getSqlMapClientTemplate().queryForList("Shop.history", paraMap);
	}

	@Override
	public int getHistoriesCount(String shopCode, Date beginDate, Date endDate) {
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("shop_code", shopCode);
		paraMap.put("beginDate", beginDate);
		paraMap.put("endDate", endDate);
		return (int) getSqlMapClientTemplate().queryForObject("Shop.historyCount", paraMap);
	}

	@Override
	public List<KpiResult> getKpiResults(String shopCode, Date beginDate,
			Date endDate, Integer pageNo,String order) {
		Map<String, Object> paraMap=new HashMap<String, Object>();
		if (pageNo!=null) {
			paraMap.put("startIndex", ((pageNo-1)*Constans.PAGE_SIZE));
			paraMap.put("pageSize", Constans.PAGE_SIZE);
		}
		paraMap.put("shop_code", shopCode);
		paraMap.put("beginDate", beginDate);
		paraMap.put("endDate", endDate);
		paraMap.put("order", order);
		return getSqlMapClientTemplate().queryForList("Shop.kpiResult", paraMap);
	}

	@Override
	public int getKpiResultsCount(String shopCode, Date beginDate, Date endDate) {
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("shop_code", shopCode);
		paraMap.put("beginDate", beginDate);
		paraMap.put("endDate", endDate);
		return (int) getSqlMapClientTemplate().queryForObject("Shop.kpiResultCount", paraMap);
	}

	@Override
	public KpiLevel getKpiLevel() {
		
		return (KpiLevel) getSqlMapClientTemplate().queryForObject("Shop.kpiLevel");
	}

	@Override
	public int updPwd(LoginShop shopPwd) {
		 getSqlMapClientTemplate().update("Shop.updPwd", shopPwd);
		 return 1;
	}

	@Override
	public String getUpdDate(String shopCode) {
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("shop_code", shopCode);
		return (String) getSqlMapClientTemplate().queryForObject("Shop.getUpdDate", paraMap);
	}

	@Override
	public int saveKpis(List<KpiResult> kpis) {
		getSqlMapClientTemplate().insert("Shop.saveKpi", kpis);
		return 1;
	}

	@Override
	public int saveDetail(List<TransactionHistory> details) {
		getSqlMapClientTemplate().insert("Shop.saveDetail", details);
		return 1;
	}

}
