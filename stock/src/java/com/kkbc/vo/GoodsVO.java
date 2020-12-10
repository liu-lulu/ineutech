package com.kkbc.vo;


public class GoodsVO {
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
	
	private String last_time;//最近入/出库时间
	private Integer incount;
	private Integer outcount;
	
	private String operate_time;
	
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
	public Integer getIncount() {
		return incount;
	}
	public void setIncount(Integer incount) {
		this.incount = incount;
	}
	public Integer getOutcount() {
		return outcount;
	}
	public void setOutcount(Integer outcount) {
		this.outcount = outcount;
	}

}
