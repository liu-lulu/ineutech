package com.kkbc.hardware;

import com.kkbc.entity.DeviceOperationLog;

/**
 * dtu操作队列元素
 * @author xu
 *
 */
public class DtuOperation {

	/**
	 * 操作日志id主键
	 */
	private Long id;
	
	/**
	 * dtu id
	 */
	private String dtu_id;
	
	/**
	 * 操作时间
	 */
	private long time=System.currentTimeMillis();
	
	/**
	 * 包序号 2字节
	 */
	private int no;
	
	private int type=DeviceOperationLog.TYPE_TIMEOUT;
	
	public String getDtu_id() {
		return dtu_id;
	}

	public void setDtu_id(String dtu_id) {
		this.dtu_id = dtu_id;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
