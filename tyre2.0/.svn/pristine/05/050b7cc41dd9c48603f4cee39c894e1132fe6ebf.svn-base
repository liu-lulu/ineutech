package com.psylife.service.impl;

import java.util.List;

import com.psylife.dao.CompanyDao;
import com.psylife.dao.impl.CompanyDaoImpl;
import com.psylife.entity.Company;
import com.psylife.entity.User;
import com.psylife.service.CompanyService;

public class CompanyServiceImpl extends BaseServiceImpl implements CompanyService{
	
	private CompanyDao companyDao = new CompanyDaoImpl();

	@Override
	public List<Company> allCompanyList(int companyId) {
		return companyDao.allCompanyList(companyId);
	}

	@Override
	public User getCompanyId(String companyName, int parentId) {
		return companyDao.getCompanyId(companyName, parentId);
	}

}
