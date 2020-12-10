package com.kkbc.vo;

import java.util.List;

public class GoodsVO {
	
	private String brand;
	private String model;
	private String img;
	private List<OrderDetailVO> goodsInfo;
	
	public GoodsVO(){
		
	}
	public GoodsVO(String brand,String model,String img,List<OrderDetailVO> goodsInfo) {
		this.brand=brand;
		this.model=model;
		this.img=img;
		this.goodsInfo=goodsInfo;
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
	public List<OrderDetailVO> getGoodsInfo() {
		return goodsInfo;
	}
	public void setGoodsInfo(List<OrderDetailVO> goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

}
