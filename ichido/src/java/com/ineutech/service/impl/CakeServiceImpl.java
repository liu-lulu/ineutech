package com.ineutech.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ineutech.dao.CakeDao;
import com.ineutech.entity.cake.CakeEmployee;
import com.ineutech.entity.cake.CakeLoginLog;
import com.ineutech.entity.cake.CakeShop;
import com.ineutech.entity.cake.CakeTransaction;
import com.ineutech.service.CakeService;

public class CakeServiceImpl implements CakeService{
	@Resource
	private CakeDao cakeDao;

	@Override
	public CakeEmployee login(CakeEmployee loginInfo) {
		return cakeDao.login(loginInfo);
	}

	@Override
	public List<CakeTransaction> transactions(String shopCode,String e_number, Date beginDate,
			Date endDate,Integer e_id,Integer flag, Integer pageNo) {
		return cakeDao.transactions(shopCode,e_number, beginDate, endDate,e_id,flag, pageNo);
	}

	@Override
	public Integer transactionsCount(String shopCode,String e_number, Date beginDate,
			Date endDate,Integer e_id,Integer flag) {
		return cakeDao.transactionsCount(shopCode,e_number, beginDate, endDate,e_id,flag);
	}

	@Override
	public List<CakeShop> shops() {
		return cakeDao.shops();
	}

	@Override
	public Integer confirm(List<Integer> cakeIdList, Integer e_id,
			Date confirm_time, int flag) {
		try {
			return cakeDao.confirm(cakeIdList, e_id, confirm_time, flag);
		} catch (Exception e) {
			return 0;
		}
	}



	@Override
	public Integer addEmployee(CakeEmployee info) {
		try {
			cakeDao.addEmployee(info);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Integer delEmployee(int e_id) {
		try {
			return cakeDao.delEmployee(e_id);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<CakeEmployee> getEmployees(String info) {
		return cakeDao.getEmployees(info);
	}

	@Override
	public List<CakeTransaction> employeeSale(String shopCode, Date beginDate,
			Date endDate) {
		return cakeDao.employeeSale(shopCode, beginDate, endDate);
	}

	@Override
	public Integer updPwd(Integer e_id, String pwd) {
		try {
			return cakeDao.updPwd(e_id, pwd);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<CakeTransaction> transactionsRecord(String shopCode,
			Date beginDate, Date endDate, Integer pageNo) {
		return cakeDao.transactionsRecord(shopCode, beginDate, endDate, pageNo);
	}

	@Override
	public Integer transactionsRecordCount(String shopCode, Date beginDate,
			Date endDate) {
		return cakeDao.transactionsRecordCount(shopCode, beginDate, endDate);
	}

	@Override
	public Integer saveLoginLog(CakeLoginLog info) {
		try {
			return cakeDao.saveLoginLog(info);
		} catch (Exception e) {
			return 0;
		}
		
	}

	@Override
	public Integer saveTransaction(CakeTransaction info) {
		try {
			cakeDao.saveTransaction(info);
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Integer updTransaction(CakeTransaction info) {
		try {
			return cakeDao.updTransaction(info);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Integer delTransaction(int cakeid) {
		try {
			return cakeDao.delTransaction(cakeid);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<CakeEmployee> getManager() {
		return cakeDao.getManager();
	}

	@Override
	public List<CakeTransaction> managerSale(Integer e_id, Integer flag,
			Date beginDate, Date endDate) {
		return cakeDao.managerSale(e_id, flag, beginDate, endDate);
	}

}
