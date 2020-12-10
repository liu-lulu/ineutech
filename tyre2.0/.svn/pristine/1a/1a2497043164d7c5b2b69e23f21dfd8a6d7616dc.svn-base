package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import com.psylife.dao.WorkOrderDao;
import com.psylife.entity.WorkOrder;
import com.psylife.entity.WorkOrderRecord;
import com.psylife.util.ConnectionPool;
import com.psylife.util.Constants;
import com.psylife.util.ResultSetUtil;
import com.psylife.util.StringHelper;


public class WorkOrderDaoImpl extends BaseDaoImpl implements WorkOrderDao{

	@Override
	public WorkOrder getLastWorkOrder(Integer user_id,Integer flag) {
		Connection connection = null;
		WorkOrder workOrder = null;
		try {
			connection = ConnectionPool.getConnection();
			workOrder=getLastWorkOrder(user_id, flag, connection);
		} catch (Exception e) {
			logger.error("获取最近一次工单记录："+StringHelper.getTrace(e));
		} finally {
			ConnectionPool.close(connection);
		}
		return workOrder;
	}
	
	@Override
	public WorkOrder getLastWorkOrder(Integer user_id,Integer flag,Connection connection) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet1 = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT id,create_time,start_time,end_time,user_id,`status` FROM work_order WHERE user_id=? ORDER BY id DESC LIMIT 1");
		WorkOrder workOrder = null;
		try {
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			resultSet = preparedStatement.executeQuery();
			workOrder=ResultSetUtil.getByOne(resultSet, "id,create_time,start_time,end_time,user_id,status".split(","), 
					"id,create_time,start_time,end_time,user_id,status".split(","), WorkOrder.class, false);
			if(flag!=null&&flag==1&&workOrder!=null){
				preparedStatement1 = connection.prepareStatement("SELECT id,create_time,work_order_id,action,content,tyre_id FROM work_order_record WHERE work_order_id=? and `status`=1 ");
				preparedStatement1.setInt(1, workOrder.getId());
				resultSet1 = preparedStatement1.executeQuery();
				List<WorkOrderRecord> list=ResultSetUtil.getByList(resultSet1, "id,create_time,work_order_id,action,content,tyre_id".split(","), 
						"id,create_time,work_order_id,action,content,tyre_id".split(","), WorkOrderRecord.class, false);
				workOrder.setWorkOrderRecords(list);
			}
		} catch (Exception e) {
			logger.error("获取最近一次工单记录："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (resultSet1 != null && !resultSet1.isClosed()) {
					resultSet1.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
				if (preparedStatement1 != null && !preparedStatement1.isClosed()) {
					preparedStatement1.close();
					preparedStatement1=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		SQL = null;
		return workOrder;
	}
	
	@Override
	public int createWorkOrder(WorkOrder workOrder){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("SELECT id,create_time,start_time,end_time,user_id,`status` as DR_status FROM work_order WHERE user_id=? ORDER BY id DESC LIMIT 1 ");
			preparedStatement.setInt(1, workOrder.getUser_id());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				if(WorkOrder.STATUS_CREATE==resultSet.getInt("DR_status")){//工单还未结束不能创建
        			return 1;//工单还未结束不能创建
        		}			
			}
			preparedStatement1 = connection.prepareStatement("insert into work_order(create_time,start_time,user_id,`status`) values(?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			Date now=new Date();
			preparedStatement1.setObject(1,now);
			preparedStatement1.setObject(2,now);
			preparedStatement1.setInt(3,workOrder.getUser_id());
			preparedStatement1.setInt(4,WorkOrder.STATUS_CREATE);
			int a=preparedStatement1.executeUpdate();	
			if(a<=0){
				connection.rollback();
				return 2;//保存失败
			}
			connection.commit();
			logger.info("创建工单:"+workOrder.getUser_id());
			return 0;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("创建工单:"+workOrder.getUser_id()+StringHelper.getTrace(e));
		}finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
					resultSet=null;
				}
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
					preparedStatement=null;
				}
				if(preparedStatement1!=null && !preparedStatement1.isClosed()){
					preparedStatement1.close();
					preparedStatement1=null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
		}
		return 2;
	}
	
	@Override
	public int endWorkOrder(WorkOrder workOrder){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("SELECT id,create_time,start_time,end_time,user_id,`status` as DR_status FROM work_order WHERE user_id=? ORDER BY id DESC LIMIT 1 ");
			preparedStatement.setInt(1, workOrder.getUser_id());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				if(WorkOrder.STATUS_CREATE!=resultSet.getInt("DR_status")){//没有要结束的工单
        			return 1;//没有要结束的工单
        		}		
			}else{
				return 1;//没有要结束的工单
			}
			preparedStatement1 = connection.prepareStatement("update work_order set end_time=?,`status`=? where id=?");
			preparedStatement1.setObject(1,new Date());
			preparedStatement1.setInt(2,WorkOrder.STATUS_END);
			preparedStatement1.setInt(3,resultSet.getInt("id"));
			int a=preparedStatement1.executeUpdate();	
			if(a<=0){
				connection.rollback();
				return 2;//保存失败
			}
			connection.commit();
			logger.info("创建工单:"+workOrder.getUser_id());
			return 0;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("创建工单:"+workOrder.getUser_id()+StringHelper.getTrace(e));
		}finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
					resultSet=null;
				}
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
					preparedStatement=null;
				}
				if(preparedStatement1!=null && !preparedStatement1.isClosed()){
					preparedStatement1.close();
					preparedStatement1=null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
		}
		return 2;
	}
	
	@Override
	public int saveWorkOrderRecord(Integer work_order_id,String action,String content,String tyre_id,Connection connection){
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement("insert into work_order_record(create_time,work_order_id,action,content,tyre_id) values(?,?,?,?,?)");
			preparedStatement.setObject(1,new Date());
			preparedStatement.setObject(2,work_order_id);
			preparedStatement.setString(3,action);
			preparedStatement.setString(4,content);
			preparedStatement.setString(5,tyre_id);
			int a=preparedStatement.executeUpdate();	
			if(a<=0){
//				connection.rollback();
				return 2;//保存失败
			}
//			connection.commit();
			logger.info("创建工单记录日志:"+tyre_id);
			return 0;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("创建工单记录日志:"+tyre_id+StringHelper.getTrace(e));
		}finally {
			try {
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
		}
		return 2;
	}
	
	
	@Override
	public List<WorkOrder> workOrderList(int pagenum,Integer user_id){
		List<WorkOrder> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT id,create_time,start_time,end_time,user_id,`status` FROM work_order WHERE user_id=? ");
		SQL.append(" ORDER BY id DESC LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "id,create_time,start_time,end_time,user_id,status".split(","), 
					"id,create_time,start_time,end_time,user_id,status".split(","), WorkOrder.class, false);
			logger.info("获取工单列表成功！");
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("获取工单列表失败！："+StringHelper.getTrace(e));
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
	
	@Override
	public int deleteWorkOrderRecord(Integer id,Integer user_id){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("SELECT W.`status` as W_status,WR.`status` as DR_status FROM work_order W INNER JOIN work_order_record WR ON WR.work_order_id=W.id WHERE user_id=? AND WR.id=? ");
			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, id);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				if(WorkOrder.STATUS_CREATE!=resultSet.getInt("W_status")){//没有要结束的工单
        			return 1;//工单已结束不能删除记录项
        		}else if(resultSet.getInt("DR_status")!=1){
        			return 3;//记录项已删除
        		}		
			}else{
				return 2;//不存在
			}
			preparedStatement1 = connection.prepareStatement("update work_order_record set `status`=? where id=?");
			preparedStatement1.setInt(1,0);
			preparedStatement1.setInt(2,id);
			int a=preparedStatement1.executeUpdate();	
			if(a<=0){
				connection.rollback();
				return 4;//保存失败
			}
			connection.commit();
			logger.info("删除工单记录项:"+id);
			return 0;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("删除工单记录项:"+id+StringHelper.getTrace(e));
		}finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
					resultSet=null;
				}
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
					preparedStatement=null;
				}
				if(preparedStatement1!=null && !preparedStatement1.isClosed()){
					preparedStatement1.close();
					preparedStatement1=null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
		}
		return 4;
	}
	
	@Override
	public List<WorkOrderRecord> recordByWorkOrderId(Integer workOrderId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<WorkOrderRecord> list=null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("SELECT id,create_time,work_order_id,action,content,tyre_id FROM work_order_record WHERE work_order_id=? and `status`=1 ");
			preparedStatement.setInt(1, workOrderId);
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "id,create_time,work_order_id,action,content,tyre_id".split(","), 
					"id,create_time,work_order_id,action,content,tyre_id".split(","), WorkOrderRecord.class, false);
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("根据工单id获取记录项:"+workOrderId+StringHelper.getTrace(e));
		}finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
					resultSet=null;
				}
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
		}
		return list;
	}
	
}
