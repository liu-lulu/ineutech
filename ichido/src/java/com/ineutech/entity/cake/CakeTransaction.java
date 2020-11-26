package com.ineutech.entity.cake;

import java.util.Date;

public class CakeTransaction {
	
	private Integer cake_id;
	private String e_number;
	private String e_name;
	private String shop_code;
	private String shop_name;
	private String member_code;
	private Float cake_price;
	private Integer cake_num;
	private String transaction_time;
	private Integer e_id;
	private String confirm_time;
	private Integer flag;
	
	private Integer totalCount;
	private Integer confirmCount;
	private Float totalPrice;
	private Float confirmPrice;
	
	public Integer getCake_id() {
		return cake_id;
	}
	public void setCake_id(Integer cake_id) {
		this.cake_id = cake_id;
	}
	public String getE_number() {
		return e_number;
	}
	public void setE_number(String e_number) {
		this.e_number = e_number;
	}
	public String getShop_code() {
		return shop_code;
	}
	public void setShop_code(String shop_code) {
		this.shop_code = shop_code;
	}
	public String getMember_code() {
		return member_code;
	}
	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}
	public Float getCake_price() {
		return cake_price;
	}
	public void setCake_price(Float cake_price) {
		this.cake_price = cake_price;
	}
	public String getTransaction_time() {
		return transaction_time;
	}
	public void setTransaction_time(String transaction_time) {
		this.transaction_time = transaction_time;
	}
	public String getConfirm_time() {
		return confirm_time;
	}
	public void setConfirm_time(String confirm_time) {
		this.confirm_time = confirm_time;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getE_id() {
		return e_id;
	}
	public void setE_id(Integer e_id) {
		this.e_id = e_id;
	}
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getConfirmCount() {
		return confirmCount;
	}
	public void setConfirmCount(Integer confirmCount) {
		this.confirmCount = confirmCount;
	}
	public Float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Float getConfirmPrice() {
		return confirmPrice;
	}
	public void setConfirmPrice(Float confirmPrice) {
		this.confirmPrice = confirmPrice;
	}
	public Integer getCake_num() {
		return cake_num;
	}
	public void setCake_num(Integer cake_num) {
		this.cake_num = cake_num;
	}

}
