package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.psylife.dao.BaseDao;
import com.psylife.util.ConnectionPool;
import com.psylife.util.StringHelper;


 
public class BaseDaoImpl implements BaseDao{

	@Override
	public long save(Object[] paramters, String sqlCols,String tabeName) {
		long id = 0;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("insert into ").append(tabeName).append("(").append(sqlCols).append(") ").append("values( ");
		int i=0;
		for(i=0;i<paramters.length;i++){
			if(i==0){
				SQL.append("?");
			}else{
				SQL.append(",?");
			}
		}
		SQL.append(") ");
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(SQL.toString(),Statement.RETURN_GENERATED_KEYS);
			for(i=0;i<paramters.length;i++){
				preparedStatement.setObject(i+1, paramters[i]);
			}
			int a = preparedStatement.executeUpdate();
			if (a > 0) {
				resultSet = preparedStatement.getGeneratedKeys();  
                if(resultSet.next()) {
                	id = resultSet.getLong(1); 
                }
                logger.info("保存成功！");
			}
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("保存失败："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return id;
	}
	
}
