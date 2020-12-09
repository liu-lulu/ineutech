package com.kkbc.entity;

import java.util.Date;
import java.util.Set;

import org.apache.ibatis.mapping.FetchType;

/**
 * 用户表
 * 
 * @author xu
 *
 */
public class User {

	/**
	 * 性别代表先生
	 */
	public static final Integer SEX_MAN = 0;

	/**
	 * 性别代表女士
	 */
	public static final Integer SEX_WOMAN = 1;

	private Long user_id;

	private Date create_time;

	private int status;
	private String class_name;

	private String head_face;

	private Integer health_type;
	private Date last_login_time;

	private String login_name;

	private String password;

	private String shool_name;

	private Integer sex;

	private Integer sport_limit;

	private String true_name;

	private Integer type;

	private Long school_id;

	private Long user_class_id;

	private Set<DeviceData> deviceDatas;

	private Device device;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getHead_face() {
		return head_face;
	}

	public void setHead_face(String head_face) {
		this.head_face = head_face;
	}

	public Integer getHealth_type() {
		return health_type;
	}

	public void setHealth_type(Integer health_type) {
		this.health_type = health_type;
	}

	public Date getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(Date last_login_time) {
		this.last_login_time = last_login_time;
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

	public String getShool_name() {
		return shool_name;
	}

	public void setShool_name(String shool_name) {
		this.shool_name = shool_name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getSport_limit() {
		return sport_limit;
	}

	public void setSport_limit(Integer sport_limit) {
		this.sport_limit = sport_limit;
	}

	public String getTrue_name() {
		return true_name;
	}

	public void setTrue_name(String true_name) {
		this.true_name = true_name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getSchool_id() {
		return school_id;
	}

	public void setSchool_id(Long school_id) {
		this.school_id = school_id;
	}

	public Long getUser_class_id() {
		return user_class_id;
	}

	public void setUser_class_id(Long user_class_id) {
		this.user_class_id = user_class_id;
	}

	public Set<DeviceData> getDeviceDatas() {
		return deviceDatas;
	}

	public void setDeviceDatas(Set<DeviceData> deviceDatas) {
		this.deviceDatas = deviceDatas;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

}
