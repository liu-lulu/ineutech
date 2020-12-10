package com.kkbc.vo;

import java.util.Date;

public class OrderDetailVO {
	private Integer order_detail_id;
	private Integer order_id;
	private String brand;
	private String model;
	private Date create_time;
	private Integer count;
	private Float purchase_price;
	private Integer remainCount;
	private String supplierName;
	
	public OrderDetailVO(String supplierName,Integer count,Float purchase_price){
		this.supplierName=supplierName;
		this.count=count;
		this.purchase_price=purchase_price;
	}
	
	public OrderDetailVO(){
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

	public Integer getOrder_detail_id() {
		return order_detail_id;
	}
	public void setOrder_detail_id(Integer order_detail_id) {
		this.order_detail_id = order_detail_id;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
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
	public Integer getRemainCount() {
		return remainCount;
	}
	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

}
