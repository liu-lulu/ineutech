package com.kkbc.service.impl;

import javax.annotation.Resource;

import com.kkbc.dao.VersionDao;
import com.kkbc.entity.VersionBean;
import com.kkbc.service.VersionService;

public class VersionServiceImpl implements VersionService {
	
	@Resource
	private VersionDao versionDao;

	@Override
	public VersionBean getVersion(String school, String type) {
		return versionDao.getVersion(school, type);
	}


}
