package com.kkbc.dao.impl;

import com.kkbc.dao.DeviceDao;
import com.kkbc.entity.YifuTest;

public class DeviceDaoImpl extends BaseDaoImpl implements DeviceDao {

	@Override
	public int saveData(YifuTest info) {
		getSqlMapClientTemplate().insert("YifuTest.saveData", info);
		return 1;
	}

}
