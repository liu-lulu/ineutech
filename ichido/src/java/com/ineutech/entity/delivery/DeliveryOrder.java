package com.ineutech.entity.delivery;

import java.util.Date;

public class DeliveryOrder {
	private Integer order_id;
	private String order_info;
	private String receiver;
	private String receiver_address;
	private String receiver_phone;
	private String product_name;
	private Integer product_num;
	private String shop_code;
	private Date time;
	private Date delivery_time;
	private String delivery_address;
	private Integer deliveryman;
	private Integer flag;
	private String deliverymanName;
	private String shop_name;
	private String scheduled_time;
	
	public DeliveryOrder(){}
	public DeliveryOrder(Integer order_id,Integer deliveryman){
		this.order_id=order_id;
		this.deliveryman=deliveryman;
	}
	public DeliveryOrder(String order_info,String scheduled_time, String receiver, String receiver_address, String receiver_phone,
			String product_name, Integer product_num, String shop_code) {
		super();
		this.order_info = order_info;
		this.scheduled_time=scheduled_time;
		this.receiver = receiver;
		this.receiver_address = receiver_address;
		this.receiver_phone = receiver_phone;
		this.product_name = product_name;
		this.product_num = product_num;
		this.shop_code = shop_code;
	}
	
	public DeliveryOrder(Integer order_id, String order_info,String scheduled_time, String receiver, String receiver_address,
			String receiver_phone, String product_name, Integer product_num, String shop_code) {
		super();
		this.order_id = order_id;
		this.order_info = order_info;
		this.scheduled_time=scheduled_time;
		this.receiver = receiver;
		this.receiver_address = receiver_address;
		this.receiver_phone = receiver_phone;
		this.product_name = product_name;
		this.product_num = product_num;
		this.shop_code = shop_code;
	}
	public DeliveryOrder(Integer order_id,Date delivery_time, String delivery_address, Integer flag) {
		this.order_id=order_id;
		this.delivery_time = delivery_time;
		this.delivery_address = delivery_address;
		this.flag = flag;
	}
	public Integer getOrder_id() {
		return order_id;
	}
	public void setOrder_id(Integer order_id) {
		this.order_id = order_id;
	}
	public String getOrder_info() {
		return order_info;
	}
	public void setOrder_info(String order_info) {
		this.order_info = order_info;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReceiver_address() {
		return receiver_address;
	}
	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}
	public String getReceiver_phone() {
		return receiver_phone;
	}
	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getProduct_num() {
		return product_num;
	}
	public void setProduct_num(Integer product_num) {
		this.product_num = product_num;
	}
	public String getShop_code() {
		return shop_code;
	}
	public void setShop_code(String shop_code) {
		this.shop_code = shop_code;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Date getDelivery_time() {
		return delivery_time;
	}
	public void setDelivery_time(Date delivery_time) {
		this.delivery_time = delivery_time;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getDelivery_address() {
		return delivery_address;
	}
	public void setDelivery_address(String delivery_address) {
		this.delivery_address = delivery_address;
	}
	public Integer getDeliveryman() {
		return deliveryman;
	}
	public void setDeliveryman(Integer deliveryman) {
		this.deliveryman = deliveryman;
	}
	public String getDeliverymanName() {
		return deliverymanName;
	}
	public void setDeliverymanName(String deliverymanName) {
		this.deliverymanName = deliverymanName;
	}
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public String getScheduled_time() {
		return scheduled_time;
	}
	public void setScheduled_time(String scheduled_time) {
		this.scheduled_time = scheduled_time;
	}
	

}
