package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.psylife.dao.TyreHistoryDao;
import com.psylife.entity.TyreHistory;
import com.psylife.util.ConnectionPool;
import com.psylife.util.Constants;
import com.psylife.util.ResultSetUtil;
import com.psylife.util.StringHelper;

 
public class TyreHistoryDaoImpl implements TyreHistoryDao{

	@Override
	public List<TyreHistory> getTyreHistoryListByTyreId(int pagenum,String tyre_id){
		List<TyreHistory> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT id,tyre_id,tyre_action,tyre_content,tyre_person,user_id,tyre_time FROM ")
		   .append("(SELECT id,tyre_id,tyre_action,tyre_content,tyre_person,user_id,tyre_time FROM tyre_history WHERE tyre_id=? ")
		   .append("UNION ALL ")
		   .append("SELECT W.id,W.tyre_id,W.action as tyre_action,W.content as tyre_content,'工单记录' as tyre_person,WO.user_id,W.create_time as tyre_time ")
		   .append("FROM work_order_record W LEFT JOIN work_order WO ON WO.id=W.work_order_id WHERE tyre_id=? AND (W.action='检测' OR W.action='修补')) as T ")
           .append(" ORDER BY tyre_time DESC LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setString(1, tyre_id);
			preparedStatement.setString(2, tyre_id);
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "id,tyre_id,tyre_action,tyre_content,tyre_person,user_id,tyre_time".split(","), "id,tyre_id,tyre_action,tyre_content,tyre_person,user_id,tyre_time".split(","), TyreHistory.class, false);
			logger.info("查询轮胎轨迹列表成功！");
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("查询轮胎轨迹列表失败！："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return list;
	}
}
