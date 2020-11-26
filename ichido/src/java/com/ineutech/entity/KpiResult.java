package com.ineutech.entity;

import java.util.Date;


public class KpiResult {
	
	private String shop_code;
	private String shop_name;
	private String shop_brand;
	private Date date;
	private Float total_sales;
	private Integer total_num;
	private Float member_sales;
	private Float member_num;
	private Float wechat_money;
	private Integer ms_rk;
	private Integer mn_rk;
	private Integer wm_rk;
	private Float fc;
	private Integer fc_rk;
	private Float score;
	private String missing;
	
	private String kpiDate;
	
	public KpiResult(){}
	public KpiResult(String shop_code, String shop_name, String shop_brand,
			String kpiDate, Float total_sales, Integer total_num,
			Float member_sales, Float member_num, Float wechat_money) {
		this.shop_code = shop_code;
		this.shop_name = shop_name;
		this.shop_brand = shop_brand;
		this.kpiDate = kpiDate;
		this.total_sales = total_sales;
		this.total_num = total_num;
		this.member_sales = member_sales;
		this.member_num = member_num;
		this.wechat_money = wechat_money;
	}
	public String getShop_code() {
		return shop_code;
	}
	public void setShop_code(String shop_code) {
		this.shop_code = shop_code;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public String getShop_brand() {
		return shop_brand;
	}
	public void setShop_brand(String shop_brand) {
		this.shop_brand = shop_brand;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Float getMember_sales() {
		return member_sales;
	}
	public void setMember_sales(Float member_sales) {
		this.member_sales = member_sales;
	}
	public Float getMember_num() {
		return member_num;
	}
	public void setMember_num(Float member_num) {
		this.member_num = member_num;
	}
	public Float getWechat_money() {
		return wechat_money;
	}
	public void setWechat_money(Float wechat_money) {
		this.wechat_money = wechat_money;
	}
	public Integer getMs_rk() {
		return ms_rk;
	}
	public void setMs_rk(Integer ms_rk) {
		this.ms_rk = ms_rk;
	}
	public Integer getMn_rk() {
		return mn_rk;
	}
	public void setMn_rk(Integer mn_rk) {
		this.mn_rk = mn_rk;
	}
	public Integer getWm_rk() {
		return wm_rk;
	}
	public void setWm_rk(Integer wm_rk) {
		this.wm_rk = wm_rk;
	}
	public Float getFc() {
		return fc;
	}
	public void setFc(Float fc) {
		this.fc = fc;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public String getMissing() {
		return missing;
	}
	public void setMissing(String missing) {
		this.missing = missing;
	}
	public Integer getFc_rk() {
		return fc_rk;
	}
	public void setFc_rk(Integer fc_rk) {
		this.fc_rk = fc_rk;
	}
	public Float getTotal_sales() {
		return total_sales;
	}
	public void setTotal_sales(Float total_sales) {
		this.total_sales = total_sales;
	}
	public Integer getTotal_num() {
		return total_num;
	}
	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}
	public String getKpiDate() {
		return kpiDate;
	}
	public void setKpiDate(String kpiDate) {
		this.kpiDate = kpiDate;
	}
	
	

}
