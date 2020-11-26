package com.kkbc.vo;

import java.util.Map;

public class FirstDegreeResult {
	private String degree;
	private String degreeRank;//学历等级
	private Float degreeScore;//学历评分
	private Float scoreRank;//得分率
	private String ranking;//世界排名
	private Map<String, Float> locationRank;
	
	private String school_name;
	private String major;
	private String learn_format;
	public FirstDegreeResult(){};
	public FirstDegreeResult(String degreeRank,Float degreeScore, Float scoreRank,String ranking,
			Map<String, Float> locationRank) {
		this.degreeRank = degreeRank;
		this.degreeScore = degreeScore;
		this.scoreRank = scoreRank;
		this.ranking = ranking;
		this.locationRank = locationRank;
	}
	
	public FirstDegreeResult(String school_name,String major,String degreeRank,Float degreeScore, Float scoreRank,String ranking,
			Map<String, Float> locationRank) {
		this.school_name=school_name;
		this.major=major;
		this.degreeRank = degreeRank;
		this.degreeScore = degreeScore;
		this.scoreRank = scoreRank;
		this.ranking = ranking;
		this.locationRank = locationRank;
	}
	
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getDegreeRank() {
		return degreeRank;
	}
	public void setDegreeRank(String degreeRank) {
		this.degreeRank = degreeRank;
	}
	
	public Float getDegreeScore() {
		return degreeScore;
	}

	public void setDegreeScore(Float degreeScore) {
		this.degreeScore = degreeScore;
	}

	public Float getScoreRank() {
		return scoreRank;
	}
	public void setScoreRank(Float scoreRank) {
		this.scoreRank = scoreRank;
	}
	public Map<String, Float> getLocationRank() {
		return locationRank;
	}
	public void setLocationRank(Map<String, Float> locationRank) {
		this.locationRank = locationRank;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
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
	public String getLearn_format() {
		return learn_format;
	}
	public void setLearn_format(String learn_format) {
		this.learn_format = learn_format;
	}
	

}
