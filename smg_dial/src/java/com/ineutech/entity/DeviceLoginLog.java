package com.ineutech.entity;

import java.util.Date;

/**
 * 
 * @name: DeviceLoginLog 
 * @description: 日志实体类
 * @date 2018年2月1日 下午4:14:16
 * @author liululu
 */
public class DeviceLoginLog {
	
	public static final int TYPE_LOGIN=1;
	public static final int TYPE_LOGOUT=2;
	
	public static final int NORMAL_STATUS=1;
	
	private long deviceLoginLogId;
	
	private Date createTime;
	
	private int status;
	
	private String remoteIp;
	
	private int remotePort;
	
	private int type;
	
	private long hardId;

	public long getDeviceLoginLogId() {
		return deviceLoginLogId;
	}

	public void setDeviceLoginLogId(long deviceLoginLogId) {
		this.deviceLoginLogId = deviceLoginLogId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public long getHardId() {
		return hardId;
	}

	public void setHardId(long hardId) {
		this.hardId = hardId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
