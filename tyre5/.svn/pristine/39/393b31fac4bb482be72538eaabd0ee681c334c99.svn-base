package com.kkbc.vo;

import java.util.Date;

import com.kkbc.hardware.process.HeartProcess;

public class TrucksVO {

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 车牌
	 */
	private String trucks_id;
	
	/**
	 * 厂牌
	 */
	private String trucks_brand;
	
	/**
	 * 车型
	 */
	private String trucks_style;
	
	/**
	 * 1是行使,0是停放
	 */
	private Integer trucks_flag;
	
	/**
	 * 车辆安全指数
	 */
	private Float trucks_health;
	
	/**
	 * 类型--主车/挂车
	 */
	private String trucks_type;
	
	/**
	 * 运输类型,分别：危险品、快递、公交、冷链、客运、其他
	 */
	private String transport_type;
	
	/**
	 * 挂车保存标志,0表示挂车信息是保存在主车上的，司机是不可以选择挂其他挂车的，1表示挂车信息是单独保存的，司机可以选择挂其他挂车的
	 */
	private Integer guache_save_flag;
	
	/**
	 * 挂车车牌
	 */
	private String guache_trucks_id;
	
	/**
	 * 行驶里程
	 */
	private Double li_cheng_run;
	
	/**
	 * 车辆型号,如J6P
	 */
	private String trucks_mode;
	
	/**
	 * dtu 采集时间
	 */
	private Date caiji_time;
	

	

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

	public Integer getTrucks_flag() {
		if(caiji_time!=null&&System.currentTimeMillis()-caiji_time.getTime()<HeartProcess.INTERVAL_TIMEOUT_TCP){
			return trucks_flag;
		}
		return 0;
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

	public String getTrucks_type() {
		return trucks_type;
	}

	public void setTrucks_type(String trucks_type) {
		this.trucks_type = trucks_type;
	}

	public String getTransport_type() {
		return transport_type;
	}

	public void setTransport_type(String transport_type) {
		this.transport_type = transport_type;
	}

	public Integer getGuache_save_flag() {
		return guache_save_flag;
	}

	public void setGuache_save_flag(Integer guache_save_flag) {
		this.guache_save_flag = guache_save_flag;
	}

	public String getGuache_trucks_id() {
		return guache_trucks_id;
	}

	public void setGuache_trucks_id(String guache_trucks_id) {
		this.guache_trucks_id = guache_trucks_id;
	}

	public Double getLi_cheng_run() {
		return li_cheng_run;
	}

	public void setLi_cheng_run(Double li_cheng_run) {
		this.li_cheng_run = li_cheng_run;
	}

	public String getTrucks_mode() {
		return trucks_mode;
	}

	public void setTrucks_mode(String trucks_mode) {
		this.trucks_mode = trucks_mode;
	}
	public Date getCaiji_time() {
		return caiji_time;
	}

	public void setCaiji_time(Date caiji_time) {
		this.caiji_time = caiji_time;
	}

	
}
