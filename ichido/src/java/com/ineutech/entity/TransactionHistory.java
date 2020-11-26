package com.ineutech.entity;

import java.util.Date;


public class TransactionHistory {
	private Integer history_id;
	private String shop_code;
	private String shop_name;
	private Date history_date;
	private Date history_time;
	private String cashier_code;
	private String member_code;
	private String member_name;
	private Float transaction_amount;
	private Float discount_amount;
	private Integer daily_num;
	private Float daily_transactions;
	private String historyDate;
	private String time;
	
	public TransactionHistory(){}
	public TransactionHistory(String shop_code, String shop_name,
			String historyDate, String history_time, String cashier_code,
			String member_code, String member_name, Float transaction_amount,
			Float discount_amount, Integer daily_num, Float daily_transactions) {
		this.shop_code = shop_code;
		this.shop_name = shop_name;
		this.historyDate = historyDate;
		this.time = history_time;
		this.cashier_code = cashier_code;
		this.member_code = member_code;
		this.member_name = member_name;
		this.transaction_amount = transaction_amount;
		this.discount_amount = discount_amount;
		this.daily_num = daily_num;
		this.daily_transactions = daily_transactions;
	}
	public Integer getHistory_id() {
		return history_id;
	}
	public void setHistory_id(Integer history_id) {
		this.history_id = history_id;
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
	public Date getHistory_date() {
		return history_date;
	}
	public void setHistory_date(Date history_date) {
		this.history_date = history_date;
	}
	public Date getHistory_time() {
		return history_time;
	}
	public void setHistory_time(Date history_time) {
		this.history_time = history_time;
	}
	public String getCashier_code() {
		return cashier_code;
	}
	public void setCashier_code(String cashier_code) {
		this.cashier_code = cashier_code;
	}
	public String getMember_code() {
		return member_code;
	}
	public void setMember_code(String member_code) {
		this.member_code = member_code;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public Float getTransaction_amount() {
		return transaction_amount;
	}
	public void setTransaction_amount(Float transaction_amount) {
		this.transaction_amount = transaction_amount;
	}
	public Float getDiscount_amount() {
		return discount_amount;
	}
	public void setDiscount_amount(Float discount_amount) {
		this.discount_amount = discount_amount;
	}
	public Integer getDaily_num() {
		return daily_num;
	}
	public void setDaily_num(Integer daily_num) {
		this.daily_num = daily_num;
	}
	public Float getDaily_transactions() {
		return daily_transactions;
	}
	public void setDaily_transactions(Float daily_transactions) {
		this.daily_transactions = daily_transactions;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getHistoryDate() {
		return historyDate;
	}
	public void setHistoryDate(String historyDate) {
		this.historyDate = historyDate;
	}
	
	
}
