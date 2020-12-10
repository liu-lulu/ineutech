package com.psylife.dao;

import java.sql.Connection;
import java.util.List;

import com.psylife.entity.WorkOrder;
import com.psylife.entity.WorkOrderRecord;
 
/**
 * 工单
 * @author xu
 * Created on 2016年7月12日 下午6:33:49
 */
public interface WorkOrderDao {

	/**
	 * 获取最近一次工单记录
	 * @param user_id
	 * @param flag 1数据带进工单日志数据,否则不带进
	 * @return
	 */
	WorkOrder getLastWorkOrder(Integer user_id, Integer flag);
	
	/**
	 * 获取最近一次工单记录
	 * @param user_id
	 * @param flag 1数据带进工单日志数据,否则不带进
	 * @param connection 
	 * @return
	 */
	WorkOrder getLastWorkOrder(Integer user_id, Integer flag,
			Connection connection);

	/**
	 * 创建工单,开始工单记录
	 * @param workOrder
	 * @return
	 */
	int createWorkOrder(WorkOrder workOrder);

	/**
	 * 结束工单
	 * @param workOrder
	 * @return
	 */
	int endWorkOrder(WorkOrder workOrder);

	/**
	 * 创建工单记录日志
	 * @param work_order_id 工单id
	 * @param action 事件
	 * @param content 内容
	 * @param tyre_id 胎号
	 * @param connection 
	 * @return
	 */
	int saveWorkOrderRecord(Integer work_order_id, String action,
			String content, String tyre_id, Connection connection);

	/**
	 * 获取工单列表
	 * @param pagenum
	 * @param user_id
	 * @return
	 */
	List<WorkOrder> workOrderList(int pagenum, Integer user_id);

	/**
	 * 删除工单记录项
	 * @param id
	 * @param user_id
	 * @return
	 */
	int deleteWorkOrderRecord(Integer id, Integer user_id);

	/**
	 * 根据工单id获取记录项
	 * @param workOrderId
	 * @return
	 */
	List<WorkOrderRecord> recordByWorkOrderId(Integer workOrderId);

	
	
	
}
