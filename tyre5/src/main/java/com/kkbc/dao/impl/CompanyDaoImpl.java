package com.kkbc.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kkbc.dao.CompanyDao;
import com.kkbc.entity.Company;
import com.kkbc.entity.User;

@Repository
public class CompanyDaoImpl extends BaseDaoImpl implements CompanyDao{
	static final Logger logger = LoggerFactory.getLogger(CompanyDao.class);

	@Override
	public List<Company> allCompanyList(int companyId) {
		@SuppressWarnings("unchecked")
		List<Company> companies=getSqlMapClientTemplate().queryForList("Company.allCompanyList", companyId);
		logger.info("获取公司列表成功！");
		return companies;
	}

	@Override
	public User getCompanyId(String companyName, int parentId) {
		Company param = new Company();
		param.setCompany(companyName);
		param.setCompany_id(parentId);
		
		User user=(User) getSqlMapClientTemplate().queryForObject("User.getCompanyId", param);
		logger.info("获取公司成功！");
		return user;
	}

}
