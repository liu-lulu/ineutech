package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.psylife.dao.VersionDao;
import com.psylife.entity.VersionBean;
import com.psylife.util.ConnectionPool;

public class VersionDaoImpl implements VersionDao {
	
	@Override
	public VersionBean getVersion(String school, String type) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet res = null;
		VersionBean temp = null;

		String sql = "select * from versions where school= '" + 
			      school + "'" + " AND modelType = " + "'" + type + "'";
		System.out.println(sql);

		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			res = preparedStatement.executeQuery();
			while (res.next()) {
				temp = new VersionBean();
		        temp.setUrl(res.getString("url"));
		        temp.setVersion(res.getString("versionName"));
		        temp.setVersionText(res.getString("versionText"));
		        temp.setIsMust(res.getString("isMust"));
		        if ("ios".equals(type)||"iostea".equals(type))
		          temp.setIosUrl(res.getString("url"));
		        else
		          temp.setAndroidUrl(res.getString("url"));
				

			}
		} catch (Exception e) {
			temp = null;
			System.out.println("Login>ERR>>> " + e + "\t" + e.getMessage());
		} finally {

			ConnectionPool.close(connection);
		}

		return temp;
	}


}
