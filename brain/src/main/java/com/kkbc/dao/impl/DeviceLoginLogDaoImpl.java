package com.kkbc.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.dao.DeviceLoginLogDao;
import com.kkbc.entity.DeviceLoginLog;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.DeviceLoginLogVo;

public class DeviceLoginLogDaoImpl extends BaseDaoImpl implements DeviceLoginLogDao{
	
	static final Logger logger = LoggerFactory.getLogger(DeviceLoginLogDaoImpl.class);
	
	@Override
	public int saveInfo(DeviceLoginLog info) {
		long result=(long) getSqlMapClientTemplate().insert("DeviceLoginLog.saveData", info);
		if (result>0) {
			logger.info(info.getDevice_id()+"设备"+(info.getType()==DeviceLoginLog.TYPE_LOGIN?"登陆":"登出"));
			return 1;
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListInfo<DeviceLoginLogVo> getLoginLog(Map<String, Object> param) {
		int currentPageNO=(int)param.get("currentPageNO");
		int pageSize=(int)param.get("pageSize");
		ListInfo<DeviceLoginLogVo> listInfo=new ListInfo<DeviceLoginLogVo>(currentPageNO, pageSize);
		
		List<DeviceLoginLogVo> loginLogs=getSqlMapClientTemplate().queryForList("DeviceLoginLog.getLog", param, (currentPageNO-1)*pageSize, pageSize);
		listInfo.setCurrentList(loginLogs);
		listInfo.setSizeOfTotalList(0);
		
		List<DeviceLoginLogVo> totalloginLogs=getSqlMapClientTemplate().queryForList("DeviceLoginLog.getLog", param);
		if (totalloginLogs!=null) {
			listInfo.setSizeOfTotalList(totalloginLogs.size());
		}
		return listInfo;
	}

}
