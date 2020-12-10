package com.kkbc.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kkbc.dao.VersionDao;
import com.kkbc.entity.VersionBean;

@Repository
public class VersionDaoImpl extends BaseDaoImpl implements VersionDao {
	static final Logger logger = LoggerFactory.getLogger(VersionDao.class);
	
	@Override
	public VersionBean getVersion(String school, String type) {
		VersionBean param=new VersionBean();
		param.setSchool(school);
		param.setType(type);
		
		VersionBean temp= (VersionBean) getSqlMapClientTemplate().queryForObject("VersionBean.getVersion", param);

		String tempType=type;

		if ("ios".equals(tempType)||"iostea".equals(tempType))
	          temp.setIosUrl(temp.getUrl());
        else
	          temp.setAndroidUrl(temp.getUrl());

		return temp;
	}


}
