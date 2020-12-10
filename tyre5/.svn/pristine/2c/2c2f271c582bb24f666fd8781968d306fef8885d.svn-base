package com.kkbc.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kkbc.dao.SpecificationDao;
import com.kkbc.entity.Specification;
import com.kkbc.entity.TyreBrand;

@Repository
public class SpecificationDaoImpl extends BaseDaoImpl implements SpecificationDao{
	static final Logger logger = LoggerFactory.getLogger(SpecificationDao.class);

	@Override
	public List<Specification> getSpecificationList() {
		@SuppressWarnings("unchecked")
		List<Specification> list=getSqlMapClientTemplate().queryForList("Specification.getSpecificationList");
		logger.info("获取轮胎规格列表成功！");
		return list;
	}
	
	@Override
	public List<TyreBrand> getTyreBrandList() {
		
		@SuppressWarnings("unchecked")
		List<TyreBrand> list=getSqlMapClientTemplate().queryForList("TyreBrand.getTyreBrandList");
		logger.info("获取轮胎品牌列表成功！");
		return list;
	}
	
	@Override
	public List<Specification> getPatternCodeList() {
		@SuppressWarnings("unchecked")
		List<Specification> list=getSqlMapClientTemplate().queryForList("Specification.getPatternCodeList");
		logger.info("获取轮胎花纹代码列表成功！");
		return list;
	}

}
