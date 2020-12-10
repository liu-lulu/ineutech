package com.kkbc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kkbc.dao.DeviceOperationLogDao;
import com.kkbc.entity.DeviceOperationLog;

@Repository
public class DeviceOperationLogDaoImpl extends BaseDaoImpl implements DeviceOperationLogDao{
	static final Logger logger = LoggerFactory.getLogger(DeviceOperationLogDao.class);

	@Transactional
	@Override
	public boolean updateTypeById(Long id,Integer type) {
		boolean isSave = false;
		DeviceOperationLog operationLog = new DeviceOperationLog();
		operationLog.setId(id);
		operationLog.setType(type);
		int a = getSqlMapClientTemplate().update("DeviceOperationLog.updateTypeById", operationLog);
        if(a>0){
        	isSave=true;
        	logger.info("硬件操作日志更新操作类型成功=" + id+",status="+type);
        }else{
        	logger.info("硬件操作日志更新操作类型失败=" + id+",status="+type);
        }
		return isSave;
	}
}
