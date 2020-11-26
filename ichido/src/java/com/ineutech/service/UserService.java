package com.ineutech.service;

import java.util.Date;
import java.util.List;

import com.ineutech.entity.KpiLevel;
import com.ineutech.entity.KpiResult;
import com.ineutech.entity.LoginShop;
import com.ineutech.entity.TransactionHistory;


public interface UserService {
	
	LoginShop getLoginShop(String loginName,String password);
	
	List<TransactionHistory> getHistories(String shopCode,Date beginDate,Date endDate,Integer pageNo);
	int getHistoriesCount(String shopCode,Date beginDate,Date endDate);
	
	List<KpiResult> getKpiResults(String shopCode,Date beginDate,Date endDate,Integer pageNo,String order);
	int getKpiResultsCount(String shopCode,Date beginDate,Date endDate);
	
	KpiLevel getKpiLevel();
	int updPwd(LoginShop shopPwd);
	
	String getUpdDate(String shopCode);
	
	int saveKpis(List<KpiResult> kpis);
	int saveDetail(List<TransactionHistory> details);

}
