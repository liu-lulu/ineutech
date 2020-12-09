package com.kkbc.entity;

import java.util.Date;

/**
 * prize-history表
 * 奖金币历史信息
 * @author liululu
 *
 */
public class PrizeHistory {
	public static final int ADD_TYPE=1;
	
	public static final int POINT_PRIZE=10;//见点奖
	
	public static final int AMOUNT_PRIZE=150;//量碰奖
	
	public static final int LAYER_PRIZE=600;//层碰奖
	
	private int id;
	private String user_name;
	private Date createTime;
	private int type;
	private String trigger_user_name;//触发用户
	private int pointPrize;
	private int amountPrize;
	private int layerPrize;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getTrigger_user_name() {
		return trigger_user_name;
	}
	public void setTrigger_user_name(String trigger_user_name) {
		this.trigger_user_name = trigger_user_name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPointPrize() {
		return pointPrize;
	}
	public void setPointPrize(int pointPrize) {
		this.pointPrize = pointPrize;
	}
	public int getAmountPrize() {
		return amountPrize;
	}
	public void setAmountPrize(int amountPrize) {
		this.amountPrize = amountPrize;
	}
	public int getLayerPrize() {
		return layerPrize;
	}
	public void setLayerPrize(int layerPrize) {
		this.layerPrize = layerPrize;
	}

}
