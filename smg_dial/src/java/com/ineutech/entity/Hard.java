package com.ineutech.entity;

import java.util.Date;

/**
 * 
 * @name: Hard 
 * @description: 硬件设备实体类
 * @date 2018年2月1日 下午4:14:47
 * @author liululu
 */
public class Hard {

	public static final String TYPE_DIAL = "拨盘";
	public static final String TYPE_BRAIN = "脑电";

	public static final int STATUS_NOUSE = 0;
	public static final int STATUS_USING = 1;

	private Integer hardId;

	// 设备MAC
	private String mac;

	// 设备编号
	private Integer hardNo = 0;

	// 设备类别,拨盘
	private String hardType;

	// 最后在线时间
	private Date lastTime;

	// 最后使用，测试名称
	private String testName;

	// 设备电量
	private Integer hardElec;

	private Integer status;

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getHardId() {
		return hardId;
	}

	public void setHardId(Integer hardId) {
		this.hardId = hardId;
	}

	public Integer getHardNo() {
		return hardNo;
	}

	public void setHardNo(Integer hardNo) {
		this.hardNo = hardNo;
	}

	public String getHardType() {
		return hardType;
	}

	public void setHardType(String hardType) {
		this.hardType = hardType;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Integer getHardElec() {
		return hardElec;
	}

	public void setHardElec(Integer hardElec) {
		this.hardElec = hardElec;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
