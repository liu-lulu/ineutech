package com.kkbc.vo;

import java.util.List;

public class FirstDegrees {
	
	private String degreeRank;//学历等级
	private Float degreeScore;//学历评分
	private List<FirstDegreeResult> firstDegreeResults;
	public FirstDegrees(){};
	public FirstDegrees(String degreeRank, Float degreeScore,
			List<FirstDegreeResult> firstDegreeResults) {
		super();
		this.degreeRank = degreeRank;
		this.degreeScore = degreeScore;
		this.firstDegreeResults = firstDegreeResults;
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
	public List<FirstDegreeResult> getFirstDegreeResults() {
		return firstDegreeResults;
	}
	public void setFirstDegreeResults(List<FirstDegreeResult> firstDegreeResults) {
		this.firstDegreeResults = firstDegreeResults;
	}
	
	


}
