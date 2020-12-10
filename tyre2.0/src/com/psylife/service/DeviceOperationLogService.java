package com.psylife.service;

/**
 * 设备操作日志
 * @author xu
 *
 */
public interface DeviceOperationLogService extends BaseService {

	/**
	 * 硬件操作日志更新操作类型
	 * @param id
	 * @param type
	 * @return
	 */
	boolean updateTypeById(Long id, Integer type);
	
}
