package com.kkbc.entity;


public class MajorScore {

	private Integer majorscore_id;
	private String province;
	private String subject;
	private String year;
	private String major;
	private Integer avg_score;
	private String pici;
	private String school_name;
	private Integer school_id;
	
	public MajorScore(String province, String subject, String year,
			String major, Integer avg_score, String pici, String school_name,
			Integer school_id) {
		super();
		this.province = province;
		this.subject = subject;
		this.year = year;
		this.major = major;
		this.avg_score = avg_score;
		this.pici = pici;
		this.school_name = school_name;
		this.school_id = school_id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public Integer getMajorscore_id() {
		return majorscore_id;
	}
	public void setMajorscore_id(Integer majorscore_id) {
		this.majorscore_id = majorscore_id;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public Integer getAvg_score() {
		return avg_score;
	}
	public void setAvg_score(Integer avg_score) {
		this.avg_score = avg_score;
	}
	public String getPici() {
		return pici;
	}
	public void setPici(String pici) {
		this.pici = pici;
	}
	public String getSchool_name() {
		return school_name;
	}
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	public Integer getSchool_id() {
		return school_id;
	}
	public void setSchool_id(Integer school_id) {
		this.school_id = school_id;
	}
	
}
