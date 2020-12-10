package com.kkbc.vo;

import java.util.Date;

public class GoodsHistoryVO {
	private Integer goods_id;
	private String barcode;
	private String brand;
	private String name;
	private String model;
	private Integer remain_count;
	private String in_price;
	private String out_price;
	private String origin;
	private String comment;
	private String img;
	
	private Date create_time;
	private Integer type;
	private Integer count;
	
	private String operate_time;
	private String last_time;//最近入/出库时间
	
	public Integer getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getRemain_count() {
		return remain_count;
	}
	public void setRemain_count(Integer remain_count) {
		this.remain_count = remain_count;
	}
	public String getIn_price() {
		return in_price;
	}
	public void setIn_price(String in_price) {
		this.in_price = in_price;
	}
	public String getOut_price() {
		return out_price;
	}
	public void setOut_price(String out_price) {
		this.out_price = out_price;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
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
	public String getOperate_time() {
		return operate_time;
	}
	public void setOperate_time(String operate_time) {
		this.operate_time = operate_time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getLast_time() {
		return last_time;
	}
	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}

}
