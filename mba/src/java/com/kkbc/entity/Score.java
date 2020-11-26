package com.kkbc.entity;


public class Score {

	private Integer score_id;
	private String province;
	private String subject;
	private String year;
	private Integer avg_score;
	private Integer min_score;
	private Integer admission_num;
	private Integer province_line;
	private String pici;
	private String school_name;
	private Integer school_id;
	
	public Score(String province, String subject, String year,
			Integer avg_score, Integer min_score, Integer admission_num,
			Integer province_line, String pici, String school_name,
			Integer school_id) {
		super();
		this.province = province;
		this.subject = subject;
		this.year = year;
		this.avg_score = avg_score;
		this.min_score = min_score;
		this.admission_num = admission_num;
		this.province_line = province_line;
		this.pici = pici;
		this.school_name = school_name;
		this.school_id = school_id;
	}
	public Integer getScore_id() {
		return score_id;
	}
	public void setScore_id(Integer score_id) {
		this.score_id = score_id;
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
	public Integer getAvg_score() {
		return avg_score;
	}
	public void setAvg_score(Integer avg_score) {
		this.avg_score = avg_score;
	}
	public Integer getMin_score() {
		return min_score;
	}
	public void setMin_score(Integer min_score) {
		this.min_score = min_score;
	}
	public Integer getAdmission_num() {
		return admission_num;
	}
	public void setAdmission_num(Integer admission_num) {
		this.admission_num = admission_num;
	}
	public Integer getProvince_line() {
		return province_line;
	}
	public void setProvince_line(Integer province_line) {
		this.province_line = province_line;
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
