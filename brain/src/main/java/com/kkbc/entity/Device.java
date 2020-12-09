package com.kkbc.entity;

import java.util.Date;

/**
 * device表
 * @author liululu
 *
 */
public class Device {

	public static final Integer DEVICE_TYPE_BRAIN = 1;

	public static final Integer DEVICE_TYPE_HAND = 2;

	public static final Integer DEVICE_TYPE_CLIENT = 3;
	
	public static final Integer NORMAL_STATUS = 1;
	
	private long device_id;
	
	private Date create_time;
	
	private Integer status;
	
	private String shefen_id;
	
	private Integer type;
	
	private Long user_id;
	
	private Date ele_time;
	
	private Integer electricity;
	
	private String label_name;
	
	private String remark;
	
	private boolean isOnline=false;

	public long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(long device_id) {
		this.device_id = device_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getShefen_id() {
		return shefen_id;
	}

	public void setShefen_id(String shefen_id) {
		this.shefen_id = shefen_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Date getEle_time() {
		return ele_time;
	}

	public void setEle_time(Date ele_time) {
		this.ele_time = ele_time;
	}

	public Integer getElectricity() {
		return electricity;
	}

	public void setElectricity(Integer electricity) {
		this.electricity = electricity;
	}

	public String getLabel_name() {
		return label_name;
	}

	public void setLabel_name(String label_name) {
		this.label_name = label_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

}
