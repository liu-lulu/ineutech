package com.kkbc.vo;

import java.util.Date;

import org.omg.CORBA.PRIVATE_MEMBER;

/**
 * device_login_log表
 * @author liululu
 *
 */
public class DeviceLoginLogVo {
	
	public static final int TYPE_LOGIN=1;
	public static final int TYPE_LOGOUT=2;
	
	public static final int NORMAL_STATUS=1;
	
	private long device_login_log_id;
	
	private Date create_time;
	
	private int status;
	
	private String remote_ip;
	
	private int remote_port;
	
	private int type;
	
	private long device_id;
	
	private String label_name;

	public long getDevice_login_log_id() {
		return device_login_log_id;
	}

	public void setDevice_login_log_id(long device_login_log_id) {
		this.device_login_log_id = device_login_log_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemote_ip() {
		return remote_ip;
	}

	public void setRemote_ip(String remote_ip) {
		this.remote_ip = remote_ip;
	}

	public int getRemote_port() {
		return remote_port;
	}

	public void setRemote_port(int remote_port) {
		this.remote_port = remote_port;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(long device_id) {
		this.device_id = device_id;
	}

	public String getLabel_name() {
		return label_name;
	}

	public void setLabel_name(String label_name) {
		this.label_name = label_name;
	}

}
