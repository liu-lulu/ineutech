package com.psylife.service.impl;

import com.psylife.dao.VersionDao;
import com.psylife.dao.impl.VersionDaoImpl;
import com.psylife.entity.VersionBean;
import com.psylife.service.VersionService;

public class VersionServiceImpl implements VersionService {
	
	private VersionDao dao=(VersionDao)new VersionDaoImpl();
	
	@Override
	public VersionBean getVersion(String school, String type) {
		return dao.getVersion(school, type);
	}


}
