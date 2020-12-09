package com.kkbc.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 
/**
 * 基础
 * @author xu
 *
 */
public interface BaseDao {
	
	static final Logger logger = LoggerFactory.getLogger(BaseDao.class);


	/**
	 * 保存
	 * @param paramters 参数
	 * @param sqlCols  插入sql字段
	 * @param tabeName 表名
	 * @return
	 */
	long save(Object[] paramters, String sqlCols,String tabeName);

}
