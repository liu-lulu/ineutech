package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kkbc.dao.SpecificationDao;
import com.kkbc.entity.Specification;
import com.kkbc.entity.TyreBrand;
import com.kkbc.service.SpecificationService;

 
public class SpecificationServiceImpl implements SpecificationService{

	@Resource
	private SpecificationDao specificationDao;

	@Override
	public List<Specification> getSpecificationList() {
		return specificationDao.getSpecificationList();
	}

	@Override
	public List<TyreBrand> getTyreBrandList() {
		return specificationDao.getTyreBrandList();
	}

	@Override
	public List<Specification> getPatternCodeList() {
		return specificationDao.getPatternCodeList();
	}
	

}
