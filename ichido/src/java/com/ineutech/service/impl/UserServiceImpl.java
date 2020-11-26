package com.ineutech.service.impl;

import java.util.Date;
import java.util.List;

import com.ineutech.dao.UserDao;
import com.ineutech.entity.KpiLevel;
import com.ineutech.entity.KpiResult;
import com.ineutech.entity.LoginShop;
import com.ineutech.entity.TransactionHistory;
import com.ineutech.service.UserService;

import javax.annotation.Resource;

public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;

	@Override
	public LoginShop getLoginShop(String loginName, String password) {
		LoginShop loginShop=userDao.getLoginShop(new LoginShop(loginName, password));
		
		return loginShop;
	}

	@Override
	public List<TransactionHistory> getHistories(String shopCode,
			Date beginDate, Date endDate, Integer pageNo) {
		return userDao.getHistories(shopCode, beginDate, endDate, pageNo);
	}

	@Override
	public int getHistoriesCount(String shopCode, Date beginDate, Date endDate) {
		return userDao.getHistoriesCount(shopCode, beginDate, endDate);
	}

	@Override
	public List<KpiResult> getKpiResults(String shopCode, Date beginDate,
			Date endDate, Integer pageNo,String order) {
		return userDao.getKpiResults(shopCode, beginDate, endDate, pageNo,order);
	}

	@Override
	public int getKpiResultsCount(String shopCode, Date beginDate, Date endDate) {
		return userDao.getKpiResultsCount(shopCode, beginDate, endDate);
	}

	@Override
	public KpiLevel getKpiLevel() {
		return userDao.getKpiLevel();
	}

	@Override
	public int updPwd(LoginShop shopPwd) {
		return userDao.updPwd(shopPwd);
	}

	@Override
	public String getUpdDate(String shopCode) {
		return userDao.getUpdDate(shopCode);
	}

	@Override
	public int saveKpis(List<KpiResult> kpis) {
		return userDao.saveKpis(kpis);
	}

	@Override
	public int saveDetail(List<TransactionHistory> details) {
		return userDao.saveDetail(details);
	}

}
