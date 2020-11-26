package com.kkbc.entity;

public class ManageUser {
	
	private Integer user_id;
	private String user_name;
	private String login_name;
	private String password;
	private Integer group_id;
	private Group group;
	
	public ManageUser(){}
	public ManageUser(String login_name, String password) {
		super();
		this.login_name = login_name;
		this.password = password;
	}
	public ManageUser(Integer user_id) {
		super();
		this.user_id = user_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	
}
