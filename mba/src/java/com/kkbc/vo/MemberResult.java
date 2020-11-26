package com.kkbc.vo;

import java.util.ArrayList;
import java.util.List;

import com.gexin.fastjson.JSONObject;
import com.kkbc.entity.Member;
import com.kkbc.entity.MemberCertificate;
import com.kkbc.entity.MemberHonor;

public class MemberResult {
	
	private Member info;
	
	private float companyScore;
	
	private FirstDegreeResult firstDegreeResult;
	//总分
	private Float totalScore;
	
	private Float majorScore;
	
	//国际考试得分
	private float interExam;
	//工作经验得分
	private float workExpScore;
	//管理经验得分
	private float manageExpScore;
	//职务得分
	private float jobScore;
	
	private float certificateScore;
	//荣誉与奖励 得分
	private float honorScore=0;
	
	private List<MajorScoreResult> degrees = new ArrayList<MajorScoreResult>();
	private List<MemberHonor> honors;
	private List<MemberCertificate> certificates;
	
	private WebInfo workInfo;
	
	private FirstDegrees firstDegrees;

	public MemberResult(){};
	public MemberResult(Member info,FirstDegreeResult firstDegreeResult, Float majorScore,List<MajorScoreResult> degrees,WebInfo workInfo,List<MemberHonor> honors,List<MemberCertificate> certificates) {
		super();
		this.info = info;
		this.firstDegreeResult = firstDegreeResult;
		this.majorScore = majorScore;
		this.degrees = degrees;
		this.workInfo = workInfo;
		this.honors = honors;
		this.certificates=certificates;
	}

	public Member getInfo() {
		return info;
	}

	public void setInfo(Member info) {
		this.info = info;
	}

	public FirstDegreeResult getFirstDegreeResult() {
		return firstDegreeResult;
	}

	public void setFirstDegreeResult(FirstDegreeResult firstDegreeResult) {
		this.firstDegreeResult = firstDegreeResult;
	}

	public Float getMajorScore() {
		return majorScore;
	}

	public void setMajorScore(Float majorScore) {
		this.majorScore = majorScore;
	}

	public List<MajorScoreResult> getDegrees() {
		return degrees;
	}

	public void setDegrees(List<MajorScoreResult> degrees) {
		this.degrees = degrees;
	}

	public WebInfo getWorkInfo() {
		return workInfo;
	}

	public void setWorkInfo(WebInfo workInfo) {
		this.workInfo = workInfo;
	}

	public List<MemberHonor> getHonors() {
		return honors;
	}

	public void setHonors(List<MemberHonor> honors) {
		this.honors = honors;
	}

	public FirstDegrees getFirstDegrees() {
		return firstDegrees;
	}

	public void setFirstDegrees(FirstDegrees firstDegrees) {
		this.firstDegrees = firstDegrees;
	}

	public FirstDegrees getFirstDegreeResult1() {
		return firstDegrees;
	}

	public void setFirstDegreeResult1(FirstDegrees firstDegreeResult1) {
		this.firstDegrees = firstDegreeResult1;
	}

	public float getInterExam() {
		return interExam;
	}

	public void setInterExam(float interExam) {
		this.interExam = interExam;
	}

	public float getWorkExpScore() {
		return workExpScore;
	}

	public void setWorkExpScore(float workExpScore) {
		this.workExpScore = workExpScore;
	}

	public float getManageExpScore() {
		return manageExpScore;
	}

	public void setManageExpScore(float manageExpScore) {
		this.manageExpScore = manageExpScore;
	}

	public float getJobScore() {
		return jobScore;
	}

	public void setJobScore(float jobScore) {
		this.jobScore = jobScore;
	}

	public Float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Float totalScore) {
		this.totalScore = totalScore;
	}

	public float getCompanyScore() {
		return companyScore;
	}
	public void setCompanyScore(float companyScore) {
		this.companyScore = companyScore;
	}
	public String toString(){
		return JSONObject.toJSONString(this);
	}
	public float getCertificateScore() {
		return certificateScore;
	}
	public void setCertificateScore(float certificateScore) {
		this.certificateScore = certificateScore;
	}
	public List<MemberCertificate> getCertificates() {
		return certificates;
	}
	public void setCertificates(List<MemberCertificate> certificates) {
		this.certificates = certificates;
	}
	public float getHonorScore() {
		return honorScore;
	}
	public void setHonorScore(float honorScore) {
		this.honorScore = honorScore;
	}
	
}
