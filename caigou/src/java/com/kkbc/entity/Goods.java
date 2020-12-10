package com.kkbc.entity;

public class Goods {
	
	private Integer goods_id;
	private String brand;
	private String model;
	private String img;
	
	public Goods(){
		
	}
	public Goods(String brand,String model,String img) {
		this.brand=brand;
		this.model=model;
		this.img=img;
	}
	
	public Integer getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(Integer goods_id) {
		this.goods_id = goods_id;
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

}
