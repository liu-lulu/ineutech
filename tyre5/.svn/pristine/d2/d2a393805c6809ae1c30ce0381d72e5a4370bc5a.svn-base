package com.kkbc.entity;

import java.util.Date;
import java.util.List;

/**
 * 工单表
 * @author xu
 * Created on 2016年7月12日 下午6:26:00
 */
public class WorkOrder 
{	
	
	/**
	 * 状态,创建成功并进入架驶状态
	 */
	public static final Integer STATUS_CREATE = 1;
	
	/**
	 * 状态,完成
	 */
	public static final Integer STATUS_END = 2;
	
	private Integer id;
	
	/**
	 * 创建时间
	 */
	private Date create_time;
	
	/**
	 * 开始时间
	 */
	private Date start_time;
	
	/**
	 * 结束时间
	 */
	private Date end_time;
	
	/**
	 * 创建者
	 */
	private Integer user_id;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 工单记录日志
	 */
	private List<WorkOrderRecord> workOrderRecords;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<WorkOrderRecord> getWorkOrderRecords() {
		return workOrderRecords;
	}

	public void setWorkOrderRecords(List<WorkOrderRecord> workOrderRecords) {
		this.workOrderRecords = workOrderRecords;
	}

	
}
