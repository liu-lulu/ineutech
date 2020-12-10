package com.kkbc.entity;

import java.util.Date;

/**
* 设备操作日志表
**/
public class DeviceOperationLog
{	
	
	/**
	 * 表名
	 */
	public static final String TB_N="device_operation_log";
	
	/**
	 * 类型，已发送
	 */
	public static final int TYPE_SEND =1;
	
	/**
	 * 类型，设置成功
	 */
	public static final int TYPE_SUCCESS =2;
	
	/**
	 * 类型，设置失败
	 */
	public static final int TYPE_FAIL =3;
	
	/**
	 * 类型，超时
	 */
	public static final int TYPE_TIMEOUT =4;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 设备dtu_id
	 */
	private String dtu_id;
	
	/**
	 * 创建时间
	 */
	private Date create_time;
	
	/**
	 * 更新时间
	 */
	private Date update_time;
	
	/**
	 * 设备操作类型，是已发送,设置成功,设置失败,超时
	 */
	private Integer type;
	
	/**
	 * 用户id
	 */
	private Integer user_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDtu_id() {
		return dtu_id;
	}

	public void setDtu_id(String dtu_id) {
		this.dtu_id = dtu_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	
	
	
}
