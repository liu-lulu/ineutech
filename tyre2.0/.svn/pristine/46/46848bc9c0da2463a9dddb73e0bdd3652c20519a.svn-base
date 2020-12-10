package com.psylife.service;

import java.util.List;

import com.psylife.entity.Company;
import com.psylife.entity.User;

public interface CompanyService extends BaseService{

	/**
	 * 获取公司以及其子公司
	 * @param companyId 公司id
	 * @return
	 */
	List<Company> allCompanyList(int companyId);
	
	/**
	 * 根据公司名获取公司负责人id
	 * @param companyName 公司名
	 * @param parentId 父公司id
	 * @return
	 */
	User getCompanyId(String companyName, int parentId);

}
