package com.kkbc.vo;

/**
 * 胎规格列表
 * 
 * @author xu
 *
 */
public class TyreCountVO {
	
	/**
	 * 品牌
	 */
	private String tyre_brand;
	
	/**
	 * 规格
	 */
	private String tyre_type1;
	
	/**
	 * 类型(花纹代码)
	 */
	private String tyre_type2;
	
	
	/**
	 * 花纹
	 */
	private String tyre_type3;
	
	/**
	 * 健康值
	 */
	private Float tyre_health;
	
	/**
	 * 胎总数
	 */
	private Integer count;
	
	public String getTyre_brand() {
		return tyre_brand;
	}
	public void setTyre_brand(String tyre_brand) {
		this.tyre_brand = tyre_brand;
	}
	public String getTyre_type1() {
		return tyre_type1;
	}
	public void setTyre_type1(String tyre_type1) {
		this.tyre_type1 = tyre_type1;
	}
	public String getTyre_type3() {
		return tyre_type3;
	}
	public void setTyre_type3(String tyre_type3) {
		this.tyre_type3 = tyre_type3;
	}
	public Float getTyre_health() {
		return tyre_health;
	}
	public void setTyre_health(Float tyre_health) {
		this.tyre_health = tyre_health;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getTyre_type2() {
		return tyre_type2;
	}
	public void setTyre_type2(String tyre_type2) {
		this.tyre_type2 = tyre_type2;
	}
	
	
}
