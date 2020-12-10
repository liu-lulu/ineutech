package com.kkbc.vo.web;

import java.util.List;

import com.kkbc.entity.DeviceFasheqi;

public class TrucksFasheqiPosVO {

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 车牌
	 */
	private String trucks_id;
	
	/**
	 * DTU ID
	 */
	private String dtu_id;

	/**
	 * 厂牌
	 */
	private String trucks_brand;

	/**
	 * 车型
	 */
	private String trucks_style;

	/**
	 * 类型--主车/挂车
	 */
	private String trucks_type;

	/**
	 * 车队
	 */
	private String company;

	/**
	 * 车队id
	 */
	private Integer company_id;

	/**
	 * 发射器
	 */
	private List<DeviceFasheqi> deviceFasheqis;

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

	public List<DeviceFasheqi> getDeviceFasheqis() {
		return deviceFasheqis;
	}

	public void setDeviceFasheqis(List<DeviceFasheqi> deviceFasheqis) {
		this.deviceFasheqis = deviceFasheqis;
	}

	public String getDtu_id() {
		return dtu_id;
	}

	public void setDtu_id(String dtu_id) {
		this.dtu_id = dtu_id;
	}


}
