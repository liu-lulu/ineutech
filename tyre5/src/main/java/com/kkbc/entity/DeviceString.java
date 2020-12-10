package com.kkbc.entity;

import java.util.Date;

/**
* 字符串包数据
**/
public class DeviceString 
{	
	/**
	 * 表名
	 */
	public static final String TB_N="device_string";
	
	private Long id;
	private String dtu_id;
	private Date create_time;
	private Date caiji_time;
	private Integer dtu_tpms_status;
	private String yuliu1_phone;
	private String yuliu2_phone;
	private String yuliu3_phone;
	private String yuliu4_phone;
	private String dtu_no;
	private String dtu_trucks_id;
	private String sim;
	private Double latitude;
	private Integer latitude_type;
	private Double longitude;
	private Integer longitude_type;
	private String fasheqi_ids_value;
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
	public String getYuliu1_phone() {
		return yuliu1_phone;
	}
	public void setYuliu1_phone(String yuliu1_phone) {
		this.yuliu1_phone = yuliu1_phone;
	}
	public String getYuliu2_phone() {
		return yuliu2_phone;
	}
	public void setYuliu2_phone(String yuliu2_phone) {
		this.yuliu2_phone = yuliu2_phone;
	}
	public String getYuliu3_phone() {
		return yuliu3_phone;
	}
	public void setYuliu3_phone(String yuliu3_phone) {
		this.yuliu3_phone = yuliu3_phone;
	}
	public String getYuliu4_phone() {
		return yuliu4_phone;
	}
	public void setYuliu4_phone(String yuliu4_phone) {
		this.yuliu4_phone = yuliu4_phone;
	}
	public String getDtu_no() {
		return dtu_no;
	}
	public void setDtu_no(String dtu_no) {
		this.dtu_no = dtu_no;
	}
	public String getDtu_trucks_id() {
		return dtu_trucks_id;
	}
	public void setDtu_trucks_id(String dtu_trucks_id) {
		this.dtu_trucks_id = dtu_trucks_id;
	}
	public String getSim() {
		return sim;
	}
	public void setSim(String sim) {
		this.sim = sim;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Integer getLatitude_type() {
		return latitude_type;
	}
	public void setLatitude_type(Integer latitude_type) {
		this.latitude_type = latitude_type;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Integer getLongitude_type() {
		return longitude_type;
	}
	public void setLongitude_type(Integer longitude_type) {
		this.longitude_type = longitude_type;
	}
	public String getFasheqi_ids_value() {
		return fasheqi_ids_value;
	}
	public void setFasheqi_ids_value(String fasheqi_ids_value) {
		this.fasheqi_ids_value = fasheqi_ids_value;
	}
	public String getTrucks_id() {
		return trucks_id;
	}
	public void setTrucks_id(String trucks_id) {
		this.trucks_id = trucks_id;
	}

}
