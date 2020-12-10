
package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.psylife.dao.LoginLogDao;
import com.psylife.entity.LoginLog;
import com.psylife.util.ConnectionPool;
import com.psylife.util.StringHelper;


 
public class LoginLogDaoImpl implements LoginLogDao{
	
	@Override
	public boolean insertLog(LoginLog loginLog) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String sql = "INSERT INTO loginlog (user_id,create_time,ip_address,remark) VALUES (?,?,?,?) ";
		int isOK = 0;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, loginLog.getUserId());
			preparedStatement.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.setString(3, loginLog.getIpAddress());
			preparedStatement.setString(4, loginLog.getRemark());
			isOK = preparedStatement.executeUpdate();
			logger.info("登录日志插入");
		} catch (Exception e) {
			logger.error("登录日志："+StringHelper.getTrace(e));
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {				
			}
			
			ConnectionPool.close(connection);
		}

		return isOK == 1 ? true : false;
	}
}
