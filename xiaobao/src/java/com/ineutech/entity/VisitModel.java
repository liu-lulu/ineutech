package com.ineutech.entity;

public class VisitModel {
	private Integer employee_id;
	private Integer client_id;
	private String sign_address;
	private String purpose;
	private String wechat_content;
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public Integer getClient_id() {
		return client_id;
	}
	public void setClient_id(Integer client_id) {
		this.client_id = client_id;
	}
	public String getSign_address() {
		return sign_address;
	}
	public void setSign_address(String sign_address) {
		this.sign_address = sign_address;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getWechat_content() {
		return wechat_content;
	}
	public void setWechat_content(String wechat_content) {
		this.wechat_content = wechat_content;
	}
	
}
