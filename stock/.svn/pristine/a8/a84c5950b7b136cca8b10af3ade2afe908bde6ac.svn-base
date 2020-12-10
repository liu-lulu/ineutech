package com.kkbc.entity;

import java.util.Date;

public class History {
	public static final int TYPE_IN=1;//入库
	public static final int TYPE_OUT=2;//出库
	
	private Integer history_id;
	private Date create_time;
	private Integer goods_id;
	private Integer type;
	private Integer count;
	
	public History(Integer goods_id,Integer type,Integer count,Date create_time){
		this.goods_id=goods_id;
		this.type=type;
		this.count=count;
		this.create_time=create_time;
	}
	public History(){}
	public Integer getHistory_id() {
		return history_id;
	}
	public void setHistory_id(Integer history_id) {
		this.history_id = history_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
