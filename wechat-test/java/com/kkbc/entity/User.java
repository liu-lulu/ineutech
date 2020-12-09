package com.kkbc.entity;

import java.util.Date;



/**
 * 用户表
 * @author xu
 *
 */
public class User 
{	
	
	/**
	 * 性别代表先生
	 */
	public static final Integer SEX_MAN = 0;

	/**
	 * 性别代表女士
	 */
	public static final Integer SEX_WOMAN = 1;
	
	/**
	 * 角色,管理员
	 */
	public static final Integer USER_ROLE_ADMIN = 2;
	
	/**
	 * 角色,普通角色
	 */
	public static final Integer USER_ROLE = 1;
	
	
	private Integer user_id;//主键
	
	private String open_id;
	private String name;
	private String password;
	private String nickname;
	
	private String stu_no;
	
	private String phone;
	private int user_type;
	private Date create_time;
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getUser_type() {
		return user_type;
	}
	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getStu_no() {
		return stu_no;
	}
	public void setStu_no(String stu_no) {
		this.stu_no = stu_no;
	}
	

}
