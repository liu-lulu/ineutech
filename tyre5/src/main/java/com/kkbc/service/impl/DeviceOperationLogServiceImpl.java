
package com.kkbc.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kkbc.dao.DeviceOperationLogDao;
import com.kkbc.service.DeviceOperationLogService;

@Service
public class DeviceOperationLogServiceImpl  extends BaseServiceImpl implements DeviceOperationLogService{

	@Resource
	private DeviceOperationLogDao deviceOperationLogDao;


	@Override
	public boolean updateTypeById(Long id, Integer type) {
		return deviceOperationLogDao.updateTypeById(id, type);
	}
	
}
