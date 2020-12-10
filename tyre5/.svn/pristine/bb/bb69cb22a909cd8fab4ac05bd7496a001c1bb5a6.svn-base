package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kkbc.dao.WorkOrderDao;
import com.kkbc.entity.WorkOrder;
import com.kkbc.entity.WorkOrderRecord;
import com.kkbc.service.WorkOrderService;
 
public class WorkOrderServiceImpl implements WorkOrderService{
	
	@Resource
	private WorkOrderDao workOrderDao;

	@Override
	public WorkOrder getLastWorkOrder(Integer user_id, Integer flag) {
		return workOrderDao.getLastWorkOrder(user_id, flag);
	}

	@Override
	public int createWorkOrder(WorkOrder workOrder) {
		return workOrderDao.createWorkOrder(workOrder);
	}

	@Override
	public int endWorkOrder(WorkOrder workOrder) {
		return workOrderDao.endWorkOrder(workOrder);
	}

	@Override
	public List<WorkOrder> workOrderList(int pagenum, Integer user_id) {
		return workOrderDao.workOrderList(pagenum, user_id);
	}

	@Override
	public int deleteWorkOrderRecord(Integer id, Integer user_id) {
		return workOrderDao.deleteWorkOrderRecord(id, user_id);
	}

	@Override
	public List<WorkOrderRecord> recordByWorkOrderId(Integer workOrderId) {
		return workOrderDao.recordByWorkOrderId(workOrderId);
	}

	
}
