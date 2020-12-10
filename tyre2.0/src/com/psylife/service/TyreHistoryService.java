package com.psylife.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psylife.entity.TyreHistory;

 
/**
 * 轮胎轨迹
 * @author xu
 *
 */
public interface TyreHistoryService {
	
	static final Logger logger = LoggerFactory.getLogger(TyreHistoryService.class);

	/**
	 * 查询轮胎轨迹列表
	 * @param pagenum
	 * @param tyre_id
	 * @return
	 */
	List<TyreHistory> getTyreHistoryListByTyreId(int pagenum, String tyre_id);
}
