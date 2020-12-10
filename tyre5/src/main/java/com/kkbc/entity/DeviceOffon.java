package com.kkbc.entity;

import java.util.Date;


/**
* 开关量包数据
**/
public class DeviceOffon 
{	
	
	/**
	 * 表名
	 */
	public static final String TB_N="device_offon";
	
	private Long id;
	private String dtu_id;
	private Date create_time;
	private Date caiji_time;
	private Integer dtu_tpms_status;
	private Integer dtu_status;
	private Integer gps_status;
	private String offon_value;
	private String trucks_id;
	
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
	public Date getCaiji_time() {
		return caiji_time;
	}
	public void setCaiji_time(Date caiji_time) {
		this.caiji_time = caiji_time;
	}
	public Integer getDtu_tpms_status() {
		return dtu_tpms_status;
	}
	public void setDtu_tpms_status(Integer dtu_tpms_status) {
		this.dtu_tpms_status = dtu_tpms_status;
	}
	public Integer getDtu_status() {
		return dtu_status;
	}
	public void setDtu_status(Integer dtu_status) {
		this.dtu_status = dtu_status;
	}
	public Integer getGps_status() {
		return gps_status;
	}
	public void setGps_status(Integer gps_status) {
		this.gps_status = gps_status;
	}
	public String getOffon_value() {
		return offon_value;
	}
	public void setOffon_value(String offon_value) {
		this.offon_value = offon_value;
	}
	public String getTrucks_id() {
		return trucks_id;
	}
	public void setTrucks_id(String trucks_id) {
		this.trucks_id = trucks_id;
	}

}
