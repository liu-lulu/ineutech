package com.ineutech.entity;

public class Employee {
	private Integer employee_id;
	private String employee_name;
	private String employee_phone;
	private String employee_login;
	private String employee_pwd;
	private Integer leader_id;
	private Integer role;
	public Integer getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(Integer employee_id) {
		this.employee_id = employee_id;
	}
	public String getEmployee_name() {
		return employee_name;
	}
	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}
	public String getEmployee_phone() {
		return employee_phone;
	}
	public void setEmployee_phone(String employee_phone) {
		this.employee_phone = employee_phone;
	}
	public String getEmployee_login() {
		return employee_login;
	}
	public void setEmployee_login(String employee_login) {
		this.employee_login = employee_login;
	}
	public String getEmployee_pwd() {
		return employee_pwd;
	}
	public void setEmployee_pwd(String employee_pwd) {
		this.employee_pwd = employee_pwd;
	}
	public Integer getLeader_id() {
		return leader_id;
	}
	public void setLeader_id(Integer leader_id) {
		this.leader_id = leader_id;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}

}
