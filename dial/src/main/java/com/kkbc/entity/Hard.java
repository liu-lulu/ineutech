package com.kkbc.entity;

import java.util.Date;

public class Hard {
	
	public static final String TYPE_DIAL="拨盘";
	
	public static final int STATUS_NOUSE=0;
	public static final int STATUS_USING=1;
	
	private Integer hard_id;
	
	//设备MAC
	private String mac;
	
	//设备编号
	private Integer hard_no=0;
	
	//设备类别,拨盘
	private String hard_type;
	
	//最后在线时间
	private Date last_time;
	
	//最后使用，测试名称
	private String test_name;
	
	//设备电量
	private Integer hard_elec;
	
	private Integer status;

	public Integer getHard_id() {
		return hard_id;
	}

	public void setHard_id(Integer hard_id) {
		this.hard_id = hard_id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getHard_no() {
		return hard_no;
	}

	public void setHard_no(Integer hard_no) {
		this.hard_no = hard_no;
	}

	public String getHard_type() {
		return hard_type;
	}

	public void setHard_type(String hard_type) {
		this.hard_type = hard_type;
	}

	public Date getLast_time() {
		return last_time;
	}

	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}

	public String getTest_name() {
		return test_name;
	}

	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}

	public Integer getHard_elec() {
		return hard_elec;
	}

	public void setHard_elec(Integer hard_elec) {
		this.hard_elec = hard_elec;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
