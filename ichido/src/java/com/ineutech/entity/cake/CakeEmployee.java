package com.ineutech.entity.cake;

public class CakeEmployee {
	public static final int ROLE_EMPLOYEE=0;
	public static final int ROLE_ADMIN=1;
	public static final int ROLE_MANAGER=2;
	
	private Integer e_id;
	private String e_number;
	private String e_name;
	private String pwd;
	private Integer role;
	private String openid;
	
	public CakeEmployee(){}
	public CakeEmployee(String e_number) {
		super();
		this.e_number = e_number;
	}
	public Integer getE_id() {
		return e_id;
	}
	public void setE_id(Integer e_id) {
		this.e_id = e_id;
	}
	public String getE_number() {
		return e_number;
	}
	public void setE_number(String e_number) {
		this.e_number = e_number;
	}
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
