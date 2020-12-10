package com.kkbc.vo.web;

import java.util.Date;

import com.kkbc.hardware.process.HeartProcess;

public class TrucksWatchVO {

	private Integer id;

	private String trucks_id;

	private String trucks_brand;

	private String trucks_style;

	private String trucks_type;

	private Integer trucks_flag;

	private Float trucks_health;

	private String company;

	private Integer company_id;

	private String phone;

	private Integer status;
	
	private String dtu_id;
	
	/**
	 * dtu 采集时间
	 */
	private Date caiji_time;
	
	/**
	 *  创建时间
	 */
	private Date create_time;
	
	/**
	 * GPS的定位状态（0表示有效定位，1表示无效定位）
	 */
	private Integer gps_status;
	
	/**
	 * 纬度
	 */
	private  Double latitude;
	
	/**
	 * 经度
	 */
	private Double longitude;
	
	/**
	 * 是否有警告信息（0表示无，1表示有）
	 */
	private Integer warn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getStatus() {
		if(caiji_time!=null&&System.currentTimeMillis()-caiji_time.getTime()<HeartProcess.INTERVAL_TIMEOUT_TCP){
			return 1;
		}
		return 0;
//		return status;
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

	public Date getCaiji_time() {
		return caiji_time;
	}

	public void setCaiji_time(Date caiji_time) {
		this.caiji_time = caiji_time;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getGps_status() {
		return gps_status;
	}

	public void setGps_status(Integer gps_status) {
		this.gps_status = gps_status;
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

	public Integer getWarn() {
		return warn;
	}

	public void setWarn(Integer warn) {
		this.warn = warn;
	}
	
	

}
