package com.kkbc.entity;

import java.util.Date;

/**
 * convert_history表
 * 币种转换记录
 * @author liululu
 *
 */
public class ConvertHistory {
	
	public static final int TYPE=1;//账户类型 1:奖金币
	
	private int id;
	
	private Date create_time;
	
	private String user;//操作用户
	
	private float money;//转换金额
	
	private int type;//账户类型 1:奖金币

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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public float getMoney() {
		return money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
