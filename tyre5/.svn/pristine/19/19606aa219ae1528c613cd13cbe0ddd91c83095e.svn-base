package com.kkbc.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.annotation.Transactional;

import com.kkbc.dao.TrucksDao;
import com.kkbc.dao.WorkOrderDao;
import com.kkbc.entity.WorkOrder;
import com.kkbc.entity.WorkOrderRecord;
import com.psylife.util.Constants;


public class WorkOrderDaoImpl extends JdbcDaoSupport implements WorkOrderDao{
	static final Logger logger = LoggerFactory.getLogger(TrucksDao.class);

//	@Override
//	public WorkOrder getLastWorkOrder(Integer user_id,Integer flag) {
//		Connection connection = null;
//		WorkOrder workOrder = null;
//		try {
//			connection = ConnectionPool.getConnection();
//			workOrder=getLastWorkOrder(user_id, flag, connection);
//		} catch (Exception e) {
//			logger.error("获取最近一次工单记录："+StringHelper.getTrace(e));
//		} finally {
//			ConnectionPool.close(connection);
//		}
//		return workOrder;
//	}
	
	@Override
	public WorkOrder getLastWorkOrder(Integer user_id,Integer flag) {
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT id,create_time,start_time,end_time,user_id,`status` FROM work_order WHERE user_id=? ORDER BY id DESC LIMIT 1");
		WorkOrder workOrder = null;
		List<WorkOrder> workOrders=getJdbcTemplate().query(SQL.toString(), new Object[]{user_id}, new BeanPropertyRowMapper<WorkOrder>(WorkOrder.class));
		if (workOrders!=null&&workOrders.size()>0) {
			workOrder=workOrders.get(0);
		}
		if(flag!=null&&flag==1&&workOrder!=null){
			String sql="SELECT id,create_time,work_order_id,action,content,tyre_id FROM work_order_record WHERE work_order_id=? and `status`=1 ";
			List<WorkOrderRecord> list=getJdbcTemplate().query(sql, new Object[]{workOrder.getId()}, new BeanPropertyRowMapper<WorkOrderRecord>(WorkOrderRecord.class));
			workOrder.setWorkOrderRecords(list);
			logger.info("获取最近一次工单记录");
		}
		SQL = null;
		return workOrder;
	}
	
	@Transactional
	@Override
	public int createWorkOrder(WorkOrder workOrder){
		String workOrderSql="SELECT id,create_time,start_time,end_time,user_id,status FROM work_order WHERE user_id=? ORDER BY id DESC LIMIT 1 ";
		List<WorkOrder> workOrders=getJdbcTemplate().query(workOrderSql, new Object[]{workOrder.getUser_id()}, new BeanPropertyRowMapper<WorkOrder>(WorkOrder.class));
		if (workOrders!=null&&workOrders.size()>0) {
			if(WorkOrder.STATUS_CREATE==workOrders.get(0).getStatus()){//工单还未结束不能创建
    			return 1;//工单还未结束不能创建
    		}			
		}
		String createSql="insert into work_order(create_time,start_time,user_id,`status`) values(?,?,?,?)";
		Date now=new Date();
		int a=getJdbcTemplate().update(createSql, new Object[]{now,now,workOrder.getUser_id(),WorkOrder.STATUS_CREATE});
		if(a<=0){
//				connection.rollback();
			return 2;//保存失败
		}
//			connection.commit();
		logger.info("创建工单:"+workOrder.getUser_id());
		return 0;			
	}
	
	@Transactional
	@Override
	public int endWorkOrder(WorkOrder workOrder){
		String workOrderSql="SELECT id,create_time,start_time,end_time,user_id,status FROM work_order WHERE user_id=? ORDER BY id DESC LIMIT 1 ";
		List<WorkOrder> workOrders=getJdbcTemplate().query(workOrderSql, new Object[]{workOrder.getUser_id()}, new BeanPropertyRowMapper<WorkOrder>(WorkOrder.class));
		WorkOrder workOrderInDB;
		if (workOrders!=null&&workOrders.size()>0) {
			workOrderInDB=workOrders.get(0);
			if(WorkOrder.STATUS_CREATE!=workOrderInDB.getStatus()){//没有要结束的工单
    			return 1;//没有要结束的工单
    		}		
		}else{
			return 1;//没有要结束的工单
		}
		
		int a=getJdbcTemplate().update("update work_order set end_time=?,`status`=? where id=?", new Object[]{new Date(),WorkOrder.STATUS_END,workOrderInDB.getId()});	
		
		
		if(a<=0){
//				connection.rollback();
			return 2;//保存失败
		}
//			connection.commit();
		logger.info("结束工单:"+workOrder.getUser_id());
		return 0;			
	}
	
	@Transactional
	@Override
	public int saveWorkOrderRecord(Integer work_order_id,String action,String content,String tyre_id){
			
		String sql="insert into work_order_record(create_time,work_order_id,action,content,tyre_id) values(?,?,?,?,?)";
		int a=getJdbcTemplate().update(sql, new Object[]{new Date(),work_order_id,action,content,tyre_id});
		if(a<=0){
			return 2;//保存失败
		}
		logger.info("创建工单记录日志:"+tyre_id);
		return 0;
	}
	
	
	@Override
	public List<WorkOrder> workOrderList(int pagenum,Integer user_id){
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT id,create_time,start_time,end_time,user_id,`status` FROM work_order WHERE user_id=? ");
		SQL.append(" ORDER BY id DESC LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
			
		List<WorkOrder> list=getJdbcTemplate().query(SQL.toString(), new Object[]{user_id}, new BeanPropertyRowMapper<WorkOrder>(WorkOrder.class));
		logger.info("获取工单列表成功！");
		SQL = null;
		return list;
	}
	
	@Transactional
	@Override
	public int deleteWorkOrderRecord(Integer id,Integer user_id){
			
		String stateSql="SELECT W.`status` as W_status,WR.`status` as DR_status FROM work_order W INNER JOIN work_order_record WR ON WR.work_order_id=W.id WHERE user_id=? AND WR.id=? ";
		SqlRowSet resultSet=getJdbcTemplate().queryForRowSet(stateSql, new Object[]{user_id,id});
		if (resultSet.next()) {
			if(WorkOrder.STATUS_CREATE!=resultSet.getInt("W_status")){//没有要结束的工单
    			return 1;//工单已结束不能删除记录项
    		}else if(resultSet.getInt("DR_status")!=1){
    			return 3;//记录项已删除
    		}		
		}else{
			return 2;//不存在
		}
		int a=getJdbcTemplate().update("update work_order_record set `status`=? where id=?", new Object[]{0,id});	
		if(a<=0){
//				connection.rollback();
			return 4;//保存失败
		}
		logger.info("删除工单记录项:"+id);
		return 0;			
	}
	
	@Override
	public List<WorkOrderRecord> recordByWorkOrderId(Integer workOrderId) {
		String sql="SELECT id,create_time,work_order_id,action,content,tyre_id FROM work_order_record WHERE work_order_id=? and `status`=1 ";
		List<WorkOrderRecord> list=getJdbcTemplate().query(sql, new Object[]{workOrderId}, new BeanPropertyRowMapper<WorkOrderRecord>(WorkOrderRecord.class));
		logger.info("根据工单id获取记录项:"+workOrderId);
		return list;
	}
	
}
