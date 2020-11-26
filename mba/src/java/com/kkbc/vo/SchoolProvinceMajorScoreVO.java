package com.kkbc.vo;

public class SchoolProvinceMajorScoreVO {
	
	private String school_name;
	private String province;
	private String major;
	//学校在该省的所有专业的平均录取分数线
	private Integer province_score_line;
	//学校在该省针对该专业的平均录取分数线
	private Integer score_line;
	public String getSchool_name() {
		return school_name;
	}
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public Integer getProvince_score_line() {
		return province_score_line;
	}
	public void setProvince_score_line(Integer province_score_line) {
		this.province_score_line = province_score_line;
	}
	public Integer getScore_line() {
		return score_line;
	}
	public void setScore_line(Integer score_line) {
		this.score_line = score_line;
	}

}
