package com.kkbc.entity;

import java.util.Date;

/**
 * 两种输入方式:
 * 1、输入车牌和胎位
 * 2、输入轮胎编号
 * @author LGL
 *
 */
public class TyrePattern2 {
	
	/**
	 * 车牌号
	 */
	public String trucks_id;
	/**
	 * 轮胎位置
	 */
	public String tyrePosition;
	/**
	 * 轮胎编号
	 */
	public String tyre_id;
	/**
	 * 轮胎深度
	 */
	public float tyre_paver;

	/**
	 * 轮胎外伤 有或无,0无,1有
	 */
	public Integer tyre_trauma;
	/**
	 * 轮胎异常 正常或异常,0无,1有
	 */
	public Integer tyre_abnormal;
	/**
	 * 轮辋 正常或受损,0无,1有
	 */
	public Integer tyre_felloe;
	/**
	 * 气门 正常或受损,0正常,1是受损
	 */
	public Integer tyre_value;

	/**
	 * 测量单位
	 * 0代表  mm
	 * 1代表  inch
	 */
	public Integer unit;//mm   
	/**
	 * 单耗
	 */
	public Double dan_hao;
	/**
	 * 轮胎花纹检测时间
	 */
	private Date tyre_p_time;
	
	private String true_name;
	
	private int user_id;
	
	private float tyre_health;
	
	private long li_cheng_estimate;
	
	public String getTrucks_id() {
		return trucks_id;
	}
	public void setTrucks_id(String trucks_id) {
		this.trucks_id = trucks_id;
	}
	public String getTyrePosition() {
		return tyrePosition;
	}
	public void setTyrePosition(String tyrePosition) {
		this.tyrePosition = tyrePosition;
	}
	public String getTyre_id() {
		return tyre_id;
	}
	public void setTyre_id(String tyre_id) {
		this.tyre_id = tyre_id;
	}
	public float getTyre_paver() {
		return tyre_paver;
	}
	public void setTyre_paver(float tyre_paver) {
		this.tyre_paver = tyre_paver;
	}
	public Integer getTyre_trauma() {
		return tyre_trauma;
	}
	public void setTyre_trauma(Integer tyre_trauma) {
		this.tyre_trauma = tyre_trauma;
	}
	public Integer getTyre_abnormal() {
		return tyre_abnormal;
	}
	public void setTyre_abnormal(Integer tyre_abnormal) {
		this.tyre_abnormal = tyre_abnormal;
	}
	public Integer getTyre_felloe() {
		return tyre_felloe;
	}
	public void setTyre_felloe(Integer tyre_felloe) {
		this.tyre_felloe = tyre_felloe;
	}
	public Integer getTyre_value() {
		return tyre_value;
	}
	public void setTyre_value(Integer tyre_value) {
		this.tyre_value = tyre_value;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public Double getDan_hao() {
		return dan_hao;
	}
	public void setDan_hao(Double dan_hao) {
		this.dan_hao = dan_hao;
	}
	public Date getTyre_p_time() {
		return tyre_p_time;
	}
	public void setTyre_p_time(Date tyre_p_time) {
		this.tyre_p_time = tyre_p_time;
	}
	public String getTrue_name() {
		return true_name;
	}
	public void setTrue_name(String true_name) {
		this.true_name = true_name;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public float getTyre_health() {
		return tyre_health;
	}
	public void setTyre_health(float tyre_health) {
		this.tyre_health = tyre_health;
	}
	public long getLi_cheng_estimate() {
		return li_cheng_estimate;
	}
	public void setLi_cheng_estimate(long li_cheng_estimate) {
		this.li_cheng_estimate = li_cheng_estimate;
	}
	

}
