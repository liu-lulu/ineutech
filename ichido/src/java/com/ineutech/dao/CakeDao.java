package com.ineutech.dao;

import java.util.Date;
import java.util.List;

import com.ineutech.entity.cake.CakeEmployee;
import com.ineutech.entity.cake.CakeLoginLog;
import com.ineutech.entity.cake.CakeShop;
import com.ineutech.entity.cake.CakeTransaction;

public interface CakeDao {
	CakeEmployee login(CakeEmployee loginInfo);
	List<CakeEmployee> getEmployees(String info);
	List<CakeEmployee> getManager();
	
	List<CakeTransaction> transactions(String shopCode,String e_number,Date beginDate,Date endDate,Integer e_id,Integer flag,Integer pageNo);
	
	Integer transactionsCount(String shopCode,String e_number,Date beginDate,Date endDate,Integer e_id,Integer flag);
	
	List<CakeTransaction> transactionsRecord(String shopCode,Date beginDate,Date endDate,Integer pageNo);
	
	Integer transactionsRecordCount(String shopCode,Date beginDate,Date endDate);
	
	List<CakeShop> shops();
	
	Integer confirm(List<Integer> cakeIdList,Integer e_id,Date confirm_time,int flag);
	
	List<CakeTransaction> managerSale(Integer e_id,Integer flag,Date beginDate,Date endDate);
	
	Integer addEmployee(CakeEmployee info);
	
	Integer delEmployee(int e_id);
	
	List<CakeTransaction> employeeSale(String shopCode,Date beginDate,Date endDate);
	
	Integer updPwd(Integer e_id,String pwd);
	
	Integer saveLoginLog(CakeLoginLog info);
	
	Integer saveTransaction(CakeTransaction info);
	Integer updTransaction(CakeTransaction info);
	Integer delTransaction(int cakeid);

}
