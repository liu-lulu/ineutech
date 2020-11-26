package com.kkbc.entity;

public class School {
	private Integer school_id;
	private String school_name;
	private String score_url;
	private String school_type;
	private String school_level;
	private String school_location;
	private int inout;
	private Integer ranking;
	
	
	public School() {}
	public School(String school_name,String score_url) {
		super();
		this.school_name = school_name;
		this.score_url = score_url;
	}
	public Integer getSchool_id() {
		return school_id;
	}
	public void setSchool_id(Integer school_id) {
		this.school_id = school_id;
	}
	public String getSchool_name() {
		return school_name;
	}
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	public String getScore_url() {
		return score_url;
	}
	public void setScore_url(String score_url) {
		this.score_url = score_url;
	}
	public String getSchool_type() {
		return school_type;
	}
	public void setSchool_type(String school_type) {
		this.school_type = school_type;
	}
	public String getSchool_level() {
		return school_level;
	}
	public void setSchool_level(String school_level) {
		this.school_level = school_level;
	}
	public String getSchool_location() {
		return school_location;
	}
	public void setSchool_location(String school_location) {
		this.school_location = school_location;
	}
	public int getInout() {
		return inout;
	}
	public void setInout(int inout) {
		this.inout = inout;
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

}
