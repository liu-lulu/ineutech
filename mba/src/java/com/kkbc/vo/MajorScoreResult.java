package com.kkbc.vo;

public class MajorScoreResult {
	
	private String degree;
	
	private String school_name;
	
	private String major;
	
	private String rank;
	
	private Float score;
	
	private String degree_type;
	private String learn_format;
	private String subject_background;
	public MajorScoreResult(){};
	public MajorScoreResult(String degree, String school_name, String major,String subject_background,
			String rank, Float score,String learn_format) {
		super();
		this.degree = degree;
		this.school_name = school_name;
		this.major = major;
		this.subject_background=subject_background;
		this.rank = rank;
		this.score = score;
		this.learn_format=learn_format;
	}
	
/*	public MajorScoreResult(String degree, String school_name, String major,
			String rank, Float score,String degree_type,String learn_format) {
		super();
		this.degree = degree;
		this.school_name = school_name;
		this.major = major;
		this.rank = rank;
		this.score = score;
		this.degree_type=degree_type;
		this.learn_format=learn_format;
	}*/

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}
	public String getDegree_type() {
		return degree_type;
	}
	public void setDegree_type(String degree_type) {
		this.degree_type = degree_type;
	}
	public String getLearn_format() {
		return learn_format;
	}
	public void setLearn_format(String learn_format) {
		this.learn_format = learn_format;
	}
	public String getSubject_background() {
		return subject_background;
	}
	public void setSubject_background(String subject_background) {
		this.subject_background = subject_background;
	}

}
