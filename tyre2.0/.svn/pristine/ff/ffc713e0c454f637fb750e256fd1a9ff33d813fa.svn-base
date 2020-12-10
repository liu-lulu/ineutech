
package com.psylife.service.impl;

import com.psylife.dao.DeviceOperationLogDao;
import com.psylife.dao.impl.DeviceOperationLogDaoImpl;
import com.psylife.service.DeviceOperationLogService;

 
public class DeviceOperationLogServiceImpl extends BaseServiceImpl implements DeviceOperationLogService{

	private DeviceOperationLogDao dao=(DeviceOperationLogDao)new DeviceOperationLogDaoImpl();

	@Override
	public boolean updateTypeById(Long id, Integer type) {
		return dao.updateTypeById(id, type);
	}
	
}
