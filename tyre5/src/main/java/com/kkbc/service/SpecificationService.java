package com.kkbc.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.entity.Specification;
import com.kkbc.entity.TyreBrand;

 
public interface SpecificationService {
	
	static final Logger logger = LoggerFactory.getLogger(SpecificationService.class);
	
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
}
