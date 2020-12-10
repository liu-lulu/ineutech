package com.kkbc.entity;

import java.util.Date;

/**
* 设备登录日志表
**/
public class DeviceLoginLog
{	
	
	/**
	 * 表名
	 */
	public static final String TB_N="device_login_log";
	
	/**
	 * 类型，登录
	 */
	public static final Integer TYPE_LOGIN =1;
	
	/**类型，登出
	 * 
	 */
	public static final Integer TYPE_LOGOUT =2;

	/**
	 * 主键
	 */
	private Long device_login_log_id;
	
	/**
	 * 创建时间
	 */
	private Date create_time;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 设备dtu_id
	 */
	private String dtu_id;
	
	/**
	 * 登录日志类型，是退出还是登录连接
	 */
	private Integer type;
	
	/**
	 * 服务器看到的ip
	 */
	private String remote_ip;
	
	/**
	 * 服务器看到的port
	 */
	private Integer remote_port;

	public Long getDevice_login_log_id() {
		return device_login_log_id;
	}

	public void setDevice_login_log_id(Long device_login_log_id) {
		this.device_login_log_id = device_login_log_id;
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

	public String getDtu_id() {
		return dtu_id;
	}

	public void setDtu_id(String dtu_id) {
		this.dtu_id = dtu_id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRemote_ip() {
		return remote_ip;
	}

	public void setRemote_ip(String remote_ip) {
		this.remote_ip = remote_ip;
	}

	public Integer getRemote_port() {
		return remote_port;
	}

	public void setRemote_port(Integer remote_port) {
		this.remote_port = remote_port;
	}

}
