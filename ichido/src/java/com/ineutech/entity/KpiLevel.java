package com.ineutech.entity;

import java.util.Date;

public class KpiLevel {
	private Date kpi_time;
	private Float member_money;
	private Float member_num;
	private Float wechat_money;
	private Float fc;
	public Date getKpi_time() {
		return kpi_time;
	}
	public void setKpi_time(Date kpi_time) {
		this.kpi_time = kpi_time;
	}
	public Float getMember_money() {
		return member_money;
	}
	public void setMember_money(Float member_money) {
		this.member_money = member_money;
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
	public Float getFc() {
		return fc;
	}
	public void setFc(Float fc) {
		this.fc = fc;
	}

}
