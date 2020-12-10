package com.psylife.dao;

import com.psylife.entity.VersionBean;

public interface VersionDao {

	/*
	 * 获取版本
	 */
	public VersionBean getVersion(String school,String type);
		
	
}
