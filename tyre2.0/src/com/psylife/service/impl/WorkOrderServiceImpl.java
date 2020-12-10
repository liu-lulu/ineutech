package com.psylife.service.impl;

import java.util.List;

import com.psylife.dao.WorkOrderDao;
import com.psylife.dao.impl.WorkOrderDaoImpl;
import com.psylife.entity.WorkOrder;
import com.psylife.entity.WorkOrderRecord;
import com.psylife.service.WorkOrderService;
 
public class WorkOrderServiceImpl extends BaseServiceImpl implements WorkOrderService{
	
	private WorkOrderDao dao=(WorkOrderDao)new WorkOrderDaoImpl();

	@Override
	public WorkOrder getLastWorkOrder(Integer user_id, Integer flag) {
		return dao.getLastWorkOrder(user_id, flag);
	}

	@Override
	public int createWorkOrder(WorkOrder workOrder) {
		return dao.createWorkOrder(workOrder);
	}

	@Override
	public int endWorkOrder(WorkOrder workOrder) {
		return dao.endWorkOrder(workOrder);
	}

	@Override
	public List<WorkOrder> workOrderList(int pagenum, Integer user_id) {
		return dao.workOrderList(pagenum, user_id);
	}

	@Override
	public int deleteWorkOrderRecord(Integer id, Integer user_id) {
		return dao.deleteWorkOrderRecord(id, user_id);
	}

	@Override
	public List<WorkOrderRecord> recordByWorkOrderId(Integer workOrderId) {
		return dao.recordByWorkOrderId(workOrderId);
	}

	
}
