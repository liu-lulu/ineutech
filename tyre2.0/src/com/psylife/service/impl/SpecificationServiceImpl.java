package com.psylife.service.impl;

import java.util.List;

import com.psylife.dao.SpecificationDao;
import com.psylife.dao.impl.SpecificationDaoImpl;
import com.psylife.entity.Specification;
import com.psylife.entity.TyreBrand;
import com.psylife.service.SpecificationService;

 
public class SpecificationServiceImpl implements SpecificationService{

	private SpecificationDao dao=(SpecificationDao)new SpecificationDaoImpl();

	@Override
	public List<Specification> getSpecificationList() {
		return dao.getSpecificationList();
	}

	@Override
	public List<TyreBrand> getTyreBrandList() {
		return dao.getTyreBrandList();
	}

	@Override
	public List<Specification> getPatternCodeList() {
		return dao.getPatternCodeList();
	}

	@Override
	public int addPatternCode(String name) {
		return dao.addPatternCode(name);
	}

	@Override
	public int addTyreBrand(String name) {
		return dao.addTyreBrand(name);
	}

	@Override
	public int addSpecification(String name) {
		return dao.addSpecification(name);
	}
	

}
