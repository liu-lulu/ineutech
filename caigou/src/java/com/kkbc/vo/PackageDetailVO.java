package com.kkbc.vo;

import java.util.Date;

public class PackageDetailVO {
	private Integer package_id;
	private Integer status;
	private Integer order_id;
	private Integer order_detail_id;
	private String expressNo;
	private Date create_time;
	private Date send_time;
	private Date sign_time;
	private String brand;
	private String model;
	private Integer count;
	private Float purchase_price;
	private String img;
	private Integer remainCount;
	private String comment;
	
	public Integer getPackage_id() {
		return package_id;
	}
	public void setPackage_id(Integer package_id) {
		this.package_id = package_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Integer getOrder_detail_id() {
		return order_detail_id;
	}
	public void setOrder_detail_id(Integer order_detail_id) {
		this.order_detail_id = order_detail_id;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getSend_time() {
		return send_time;
	}
	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}
	public Date getSign_time() {
		return sign_time;
	}
	public void setSign_time(Date sign_time) {
		this.sign_time = sign_time;
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Float getPurchase_price() {
		return purchase_price;
	}
	public void setPurchase_price(Float purchase_price) {
		this.purchase_price = purchase_price;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Integer getRemainCount() {
		return remainCount;
	}
	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
