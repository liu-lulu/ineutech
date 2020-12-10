package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.psylife.dao.DeviceLoginLogDao;
import com.psylife.dao.TrucksDeviceDao;
import com.psylife.entity.DeviceLoginLog;
import com.psylife.hardware.HardwareElement;
import com.psylife.hardware.process.HeartProcess;
import com.psylife.util.ConnectionPool;
import com.psylife.util.Constants;
import com.psylife.util.StringHelper;
 
public class DeviceLoginLogDaoImpl extends BaseDaoImpl implements DeviceLoginLogDao{

	private TrucksDeviceDao trucksDeviceDao=(TrucksDeviceDao)new TrucksDeviceDaoImpl();
	
	@Override
	public void loginOut(HardwareElement element){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<ResultSet> rList=new ArrayList<ResultSet>();
		List<PreparedStatement> pList=new ArrayList<PreparedStatement>();
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			saveByDeviceData(new Object[]{new Date(),Constants.STATUS_NORMAL,element.getUdpAddress().getAddress().getHostAddress(),element.getUdpAddress().getPort(),DeviceLoginLog.TYPE_LOGOUT,element.getPhone()}, 
					"create_time,status,remote_ip,remote_port,type,dtu_id", DeviceLoginLog.TB_N, connection, rList, pList);	

			trucksDeviceDao.updateTrucksFlag(0, element, connection, rList, pList);//停止中
			connection.commit();
			logger.info("设备退出保存成功,设备号:"+element.getPhone()+",车牌号:"+element.getCarNum());
			return ;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("设备退出保存失败,设备号:"+element.getPhone()+",车牌号:"+element.getCarNum()+StringHelper.getTrace(e));
		}finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
				}
				for(ResultSet set:rList){
					try {
						if (set!=null && !set.isClosed()) {
							set.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				for(PreparedStatement statement:pList){
					try {
						if (statement!=null && !statement.isClosed()) {
							statement.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
			
		}
		return;
	}
	
	//保存
	private long saveByDeviceData(Object[] paramters, String sqlCols,String tabeName,Connection connection,List<ResultSet> rList,List<PreparedStatement> pList) {
			long id = 0;
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
			} catch (Exception e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				logger.error("保存失败："+StringHelper.getTrace(e));
			} finally {
				if (resultSet != null) {
					rList.add(resultSet);
				}
				if (preparedStatement != null) {
					pList.add(preparedStatement);
				}
			}
			SQL = null;
			return id;
		}
	
	@Override
	public boolean startSysDtuProcessTrucksFlag() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int isOK = 0;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement("UPDATE trucks INNER JOIN device ON device.trucks_id=trucks.trucks_id SET trucks.trucks_flag=0 WHERE device.caiji_time<? ");
			preparedStatement.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()-HeartProcess.INTERVAL_TIMEOUT_TCP));
			isOK = preparedStatement.executeUpdate();
			logger.info("系统启动时处理dtu离线时,行驶状态为停放");
		} catch (Exception e) {
			logger.error("系统启动时处理dtu离线时,行驶状态为停放："+StringHelper.getTrace(e));
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
