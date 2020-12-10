package com.kkbc.entity;

import java.util.Date;

public class Order {
	
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
	private Integer collection_status;
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
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	

}
