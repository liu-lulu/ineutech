package com.ineutech.entity;

public class LoginShop {
	
	private Integer shop_id;
	private String shop_code;
	private String shop_name;
	private String shop_brand;
	private String shop_login;
	private String shop_pwd;
	private Integer role;
	private Integer updPwd;
	
	public LoginShop(){}
	public LoginShop(String shop_login, String shop_pwd) {
		super();
		this.shop_login = shop_login;
		this.shop_pwd = shop_pwd;
	}
	public LoginShop(Integer shop_id,String shop_pwd) {
		this.shop_id=shop_id;
		this.shop_pwd=shop_pwd;
	}
	public Integer getShop_id() {
		return shop_id;
	}
	public void setShop_id(Integer shop_id) {
		this.shop_id = shop_id;
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
	public String getShop_brand() {
		return shop_brand;
	}
	public void setShop_brand(String shop_brand) {
		this.shop_brand = shop_brand;
	}
	public String getShop_login() {
		return shop_login;
	}
	public void setShop_login(String shop_login) {
		this.shop_login = shop_login;
	}
	public String getShop_pwd() {
		return shop_pwd;
	}
	public void setShop_pwd(String shop_pwd) {
		this.shop_pwd = shop_pwd;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public Integer getUpdPwd() {
		return updPwd;
	}
	public void setUpdPwd(Integer updPwd) {
		this.updPwd = updPwd;
	}

}
