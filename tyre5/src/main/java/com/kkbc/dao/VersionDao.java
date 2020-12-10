package com.kkbc.dao;

import com.kkbc.entity.VersionBean;

public interface VersionDao {

	/*
	 * 获取版本
	 */
	public VersionBean getVersion(String school,String type);
		
	
}
