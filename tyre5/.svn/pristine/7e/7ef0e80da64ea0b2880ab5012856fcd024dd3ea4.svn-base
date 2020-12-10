package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kkbc.dao.CompanyDao;
import com.kkbc.entity.Company;
import com.kkbc.entity.User;
import com.kkbc.service.CompanyService;

public class CompanyServiceImpl implements CompanyService{
	
	@Resource
	private CompanyDao companyDao;


	@Override
	public List<Company> allCompanyList(int companyId) {
		return companyDao.allCompanyList(companyId);
	}

	@Override
	public User getCompanyId(String companyName, int parentId) {
		return companyDao.getCompanyId(companyName, parentId);
	}

}
