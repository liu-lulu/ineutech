package com.kkbc.vo;


public class WebInfo{
	private WorkBackground info;
	private WorkBackground companyInfo;
	private Integer shixin;
	public WebInfo(){};
	public WebInfo(WorkBackground info, WorkBackground companyInfo) {
		super();
		this.info = info;
		this.companyInfo = companyInfo;
	}
	
	public WorkBackground getInfo() {
		return info;
	}

	public void setInfo(WorkBackground info) {
		this.info = info;
	}

	public WorkBackground getCompanyInfo() {
		return companyInfo;
	}
	public void setCompanyInfo(WorkBackground companyInfo) {
		this.companyInfo = companyInfo;
	}
	public Integer getShixin() {
		return shixin;
	}
	public void setShixin(Integer shixin) {
		this.shixin = shixin;
	}
	
	
}