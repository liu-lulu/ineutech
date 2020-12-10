package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.psylife.dao.DeviceOperationLogDao;
import com.psylife.util.ConnectionPool;
import com.psylife.util.StringHelper;
 
public class DeviceOperationLogDaoImpl extends BaseDaoImpl implements DeviceOperationLogDao{

	@Override
	public boolean updateTypeById(Long id,Integer type) {
		boolean isSave = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("update  device_operation_log  set type=?,update_time=now() where id=?");
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			// connection =PoolManager.getInstance().getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, type);
			preparedStatement.setLong(2, id);
			int a = preparedStatement.executeUpdate();
            if(a>0){
            	isSave=true;
            	logger.info("硬件操作日志更新操作类型成功=" + id+",status="+type);
            }else{
            	logger.info("硬件操作日志更新操作类型失败=" + id+",status="+type);
            }
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			isSave = false;
			logger.error("硬件操作日志更新操作类型："+StringHelper.getTrace(e));
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
		return isSave;
	}
}
