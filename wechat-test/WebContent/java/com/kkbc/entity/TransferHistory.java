package com.kkbc.entity;

import java.util.Date;

/**
 * transfer_history表
 * 转账记录
 * @author liululu
 *
 */
public class TransferHistory {
	
	public static final int IN=1;//转入
	public static final int OUT=2;//转出
	
	private int id;
	
	private Date create_time;
	
	private String coin_type;//币种： 1:奖金币 2:购物钻
	
	private String from;//转出账户
	
	private String to;//接收账户
	
	private int type;//转账类型：1转入 2转出
	
	private float money;//金额
	
	private String remark="";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getCoin_type() {
		return coin_type;
	}

	public void setCoin_type(String coin_type) {
		this.coin_type = coin_type;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
