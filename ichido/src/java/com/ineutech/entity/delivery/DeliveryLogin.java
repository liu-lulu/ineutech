package com.ineutech.entity.delivery;

public class DeliveryLogin {
	private Integer login_id;
	private String shop_code;
	private String shop_name;
	private String login_name;
	private String login_pwd;
	private Integer role;
	
	public DeliveryLogin(){}
	public DeliveryLogin(String login_name, String login_pwd) {
		this.login_name = login_name;
		this.login_pwd = login_pwd;
	}
	public Integer getLogin_id() {
		return login_id;
	}
	public void setLogin_id(Integer login_id) {
		this.login_id = login_id;
	}
	public String getShop_code() {
		return shop_code;
	}
	public void setShop_code(String shop_code) {
		this.shop_code = shop_code;
	}
	
	public String getShop_name() {
		return shop_name;
	}
	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getLogin_pwd() {
		return login_pwd;
	}
	public void setLogin_pwd(String login_pwd) {
		this.login_pwd = login_pwd;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}

}
