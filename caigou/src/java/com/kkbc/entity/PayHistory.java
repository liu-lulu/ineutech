package com.kkbc.entity;

import java.util.Date;

public class PayHistory {
	public static final int CONNECTION_TYPE=1;//收款
	public static final int PAY_TYPE=2;//付款
	
	private Integer pay_id;
	private Date create_time;
	private Integer order_id;
	private Integer type;
	private Integer pay_type;
	private Float money;
	private String pay_comment;
	
	public PayHistory(Integer order_id,Integer type,Integer pay_type,Float money,String pay_comment){
		this.order_id=order_id;
		this.type=type;
		this.pay_type=pay_type;
		this.money=money;
		this.pay_comment=pay_comment;
	}
	public PayHistory(){}
	
	public Integer getPay_id() {
		return pay_id;
	}
	public void setPay_id(Integer pay_id) {
		this.pay_id = pay_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPay_type() {
		return pay_type;
	}
	public void setPay_type(Integer pay_type) {
		this.pay_type = pay_type;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public String getPay_comment() {
		return pay_comment;
	}
	public void setPay_comment(String pay_comment) {
		this.pay_comment = pay_comment;
	}
	
}
