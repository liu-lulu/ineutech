package com.kkbc.vo;

import java.util.Date;

public class OrderVO {
	
	private Integer order_id;
	private Integer admin_id;
	private Integer supplier_id;
	private Date create_time;
	private String brand;
	private String model;
	private String img;
	private Integer num;
	private String comment;
	private Float price;
	
	private Integer caigou_status;
	private Integer delivery_status;
	private Integer payment_status;
	private Float pay_money;
	private Float pay_dingjin;
	private Integer collection_status;
	private Float collection_money;
	private Float collection_dingjin;
	
	private Float purchase_price;
	private Integer boughtCount;//已购数量
	private String supplierName;//供应商名字
	private Integer sendCount;//已发货数量
	private Integer remainCount;//剩余数量
	
	private Integer userId;
	private Integer role;
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public Integer getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(Integer admin_id) {
		this.admin_id = admin_id;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getCaigou_status() {
		return caigou_status;
	}
	public void setCaigou_status(Integer caigou_status) {
		this.caigou_status = caigou_status;
	}
	public Integer getDelivery_status() {
		return delivery_status;
	}
	public void setDelivery_status(Integer delivery_status) {
		this.delivery_status = delivery_status;
	}
	public Integer getPayment_status() {
		return payment_status;
	}
	public void setPayment_status(Integer payment_status) {
		this.payment_status = payment_status;
	}
	public Integer getCollection_status() {
		return collection_status;
	}
	public void setCollection_status(Integer collection_status) {
		this.collection_status = collection_status;
	}
	public Integer getBoughtCount() {
		return boughtCount;
	}
	public void setBoughtCount(Integer boughtCount) {
		this.boughtCount = boughtCount;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public Float getPurchase_price() {
		return purchase_price;
	}
	public void setPurchase_price(Float purchase_price) {
		this.purchase_price = purchase_price;
	}
	public Integer getSendCount() {
		return sendCount;
	}
	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}
	public Float getPay_money() {
		return pay_money;
	}
	public void setPay_money(Float pay_money) {
		this.pay_money = pay_money;
	}
	public Float getPay_dingjin() {
		return pay_dingjin;
	}
	public void setPay_dingjin(Float pay_dingjin) {
		this.pay_dingjin = pay_dingjin;
	}
	public Float getCollection_money() {
		return collection_money;
	}
	public void setCollection_money(Float collection_money) {
		this.collection_money = collection_money;
	}
	public Float getCollection_dingjin() {
		return collection_dingjin;
	}
	public void setCollection_dingjin(Float collection_dingjin) {
		this.collection_dingjin = collection_dingjin;
	}
	public Integer getRemainCount() {
		return remainCount;
	}
	public void setRemainCount(Integer remainCount) {
		this.remainCount = remainCount;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
}
