package com.kkbc.entity;

public class Volunteer {
	private Integer volunteer_id;
	private String name;
	private String sex;
	private String school;
	private String phone;
	private String degree;
	private String idcard;
	private String creditcard;
	private String bank;
	private String coat;
	private String pants;
	private String img;
	
	public Volunteer(){}
	public Volunteer(String phone, String idcard){
		this.phone = phone;
		this.idcard = idcard;
	}
	public Volunteer(String name, String sex, String school, String phone,
			String degree, String idcard, String creditcard, String bank,
			String coat, String pants, String img) {
		super();
		this.name = name;
		this.sex = sex;
		this.school = school;
		this.phone = phone;
		this.degree = degree;
		this.idcard = idcard;
		this.creditcard = creditcard;
		this.bank = bank;
		this.coat = coat;
		this.pants = pants;
		this.img = img;
	}
	public Integer getVolunteer_id() {
		return volunteer_id;
	}
	public void setVolunteer_id(Integer volunteer_id) {
		this.volunteer_id = volunteer_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getCreditcard() {
		return creditcard;
	}
	public void setCreditcard(String creditcard) {
		this.creditcard = creditcard;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getCoat() {
		return coat;
	}
	public void setCoat(String coat) {
		this.coat = coat;
	}
	public String getPants() {
		return pants;
	}
	public void setPants(String pants) {
		this.pants = pants;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	

}
