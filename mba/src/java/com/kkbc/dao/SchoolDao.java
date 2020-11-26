package com.kkbc.dao;

import java.util.HashMap;
import java.util.List;

import com.kkbc.entity.CompanyInfo;
import com.kkbc.entity.IndustryFunction;
import com.kkbc.entity.MajorScore;
import com.kkbc.entity.Member;
import com.kkbc.entity.MemberCertificate;
import com.kkbc.entity.MemberDegree;
import com.kkbc.entity.MemberHonor;
import com.kkbc.entity.School;
import com.kkbc.entity.Score;
import com.kkbc.vo.AppData;
import com.kkbc.vo.CatchWebInfo;
import com.kkbc.vo.Certificate;
import com.kkbc.vo.Education;
import com.kkbc.vo.HonorAward;
import com.kkbc.vo.SchoolProvinceMajorScoreVO;

public interface SchoolDao {
	
	School getSchool(String schoolName);
	School getSchool(String schoolName,String englishName);
	int saveSchool(School info);
	int saveScore(List<Score> scores);
	int saveMajorScore(List<MajorScore> majorScores);
	
	HashMap<String, Float> getLocationRank(String location);
	
	/**
	 * 该大学在该省份的录取分数线
	 * @param schoolName
	 * @param highSchoolLocation
	 * @return
	 */
	SchoolProvinceMajorScoreVO getScoreLine(String schoolName,String highSchoolLocation,String major);
	
	String getMajorRank(String schoolName,String major);
	
	Member getMember(String memberNo);
	List<MemberDegree> getMemberDegree(String memberNo,String[] degree);
	List<MemberHonor> getMemberHonors(String memberNo);
	List<MemberCertificate> getMemberCertificates(String memberNo);
	
	List<Member> getMemberList(Integer pagenum,String memberNo,String name);
	int getMemberCount(String memberNo,String name);
	
	CompanyInfo getCompanyInfo(String memberNo);
	List<CatchWebInfo> getWebInfo(String memberNo,Integer type);
	
	IndustryFunction getIndustryFunction(String industry,String function);
	
	Integer getSchoolScore(String schoolName);
	
	int saveMember(AppData info);
	int saveDegrees(String memberNo,List<Education> degrees);
	int saveCertificates(String memberNo,List<Certificate> certificates);
	int saveHonors(String memberNo,List<HonorAward> honorAwards);
}
