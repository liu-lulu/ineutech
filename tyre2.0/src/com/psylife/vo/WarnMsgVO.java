package com.psylife.vo;

import java.util.Date;

public class WarnMsgVO {
	
	private int id;
	private String dtu_id;
	private Date create_time;
	private String warn_msg;
	private int status;
	
	private String trucks_id;

	private String trucks_brand;

	private String trucks_style;

	private String trucks_type;

	private Integer trucks_flag;

	private Float trucks_health;

	private String company;

	private Integer company_id;

	private String phone;
	
	/**
	 * 纬度
	 */
	private  Double latitude;
	
	/**
	 * 经度
	 */
	private Double longitude;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public String getWarn_msg() {
		return warn_msg;
	}
	public void setWarn_msg(String warn_msg) {
		this.warn_msg = warn_msg;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getTrucks_id() {
		return trucks_id;
	}
	public void setTrucks_id(String trucks_id) {
		this.trucks_id = trucks_id;
	}
	public String getTrucks_brand() {
		return trucks_brand;
	}
	public void setTrucks_brand(String trucks_brand) {
		this.trucks_brand = trucks_brand;
	}
	public String getTrucks_style() {
		return trucks_style;
	}
	public void setTrucks_style(String trucks_style) {
		this.trucks_style = trucks_style;
	}
	public String getTrucks_type() {
		return trucks_type;
	}
	public void setTrucks_type(String trucks_type) {
		this.trucks_type = trucks_type;
	}
	public Integer getTrucks_flag() {
		return trucks_flag;
	}
	public void setTrucks_flag(Integer trucks_flag) {
		this.trucks_flag = trucks_flag;
	}
	public Float getTrucks_health() {
		return trucks_health;
	}
	public void setTrucks_health(Float trucks_health) {
		this.trucks_health = trucks_health;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Integer getCompany_id() {
		return company_id;
	}
	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	

}
