package com.psylife.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psylife.entity.Specification;
import com.psylife.entity.TyreBrand;

public interface SpecificationDao {
	
	static final Logger logger = LoggerFactory.getLogger(SpecificationDao.class);

	/**
	 * 获取轮胎规格列表
	 * @return
	 */
	List<Specification> getSpecificationList();

	/**
	 * 获取轮胎品牌列表
	 * @return
	 */
	List<TyreBrand> getTyreBrandList();

	/**
	 * 获取轮胎花纹代码列表
	 * @return
	 */
	List<Specification> getPatternCodeList();
	
	//添加轮胎花纹代码
	int addPatternCode(String name);
	
	//添加轮胎品牌
	int addTyreBrand(String name);
	
	//添加轮胎规格
	int addSpecification(String name);

}
