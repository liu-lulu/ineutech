package com.kkbc.dao.impl;

import com.kkbc.vo.AppData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kkbc.commom.MbaConstants;
import com.kkbc.dao.SchoolDao;
import com.kkbc.entity.CompanyInfo;
import com.kkbc.entity.IndustryFunction;
import com.kkbc.entity.MajorScore;
import com.kkbc.entity.Member;
import com.kkbc.entity.MemberCertificate;
import com.kkbc.entity.MemberDegree;
import com.kkbc.entity.MemberHonor;
import com.kkbc.entity.School;
import com.kkbc.entity.Score;
import com.kkbc.vo.CatchWebInfo;
import com.kkbc.vo.Certificate;
import com.kkbc.vo.Education;
import com.kkbc.vo.HonorAward;
import com.kkbc.vo.SchoolProvinceMajorScoreVO;

public class SchoolDaoImpl extends BaseDaoImpl implements SchoolDao {


	@Override
	public School getSchool(String schoolName) {
		return (School) getSqlMapClientTemplate().queryForObject("school.getSchool", schoolName);
	}

	@Override
	public int saveSchool(School info) {
		return (int) getSqlMapClientTemplate().insert("school.saveSchool", info);
	}

	@Override
	public int saveScore(List<Score> scores) {
		getSqlMapClientTemplate().insert("school.saveScore", scores);
		return 1;
	}

	@Override
	public int saveMajorScore(List<MajorScore> majorScores) {
		getSqlMapClientTemplate().insert("school.saveMajorScore", majorScores);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Float> getLocationRank(String location) {
		return (HashMap<String, Float>) getSqlMapClientTemplate().queryForMap("school.getLocationRank", location, "school_level", "rank");
	}

	@Override
	public SchoolProvinceMajorScoreVO getScoreLine(String schoolName, String highSchoolLocation,String major) {
		Map<String, String> param=new HashMap<String, String>();
		param.put("school_name", schoolName);
		param.put("province", highSchoolLocation);
		param.put("major", major);
		return (SchoolProvinceMajorScoreVO) getSqlMapClientTemplate().queryForObject("school.getScoreLine", param);
	}

	@Override
	public String getMajorRank(String schoolName, String major) {
		Map<String, String> param=new HashMap<String, String>();
		param.put("school_name", schoolName);
		param.put("major", major);
		return (String) getSqlMapClientTemplate().queryForObject("school.getMajorRank", param);
	}

	@Override
	public Member getMember(String memberNo) {
		return (Member) getSqlMapClientTemplate().queryForObject("member.getMember", memberNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberDegree> getMemberDegree(String memberNo,String[] degree) {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("member_serialno", memberNo);
		paraMap.put("degree", degree);
		return getSqlMapClientTemplate().queryForList("member.getDegree", paraMap);
	}

	@Override
	public School getSchool(String schoolName, String englishName) {
		Map<String, String> param=new HashMap<String, String>();
		param.put("school_name", schoolName);
		param.put("english_name", englishName);
		return (School) getSqlMapClientTemplate().queryForObject("school.getOutSchool", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MemberHonor> getMemberHonors(String memberNo) {
		return getSqlMapClientTemplate().queryForList("member.getHonor", memberNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Member> getMemberList(Integer pagenum,String memberNo,String name) {
		Map<String, Object> param=new HashMap<String, Object>();
		if (pagenum!=null) {
			param.put("startIndex", ((pagenum-1)*MbaConstants.PAGE_SIZE));
		}
		param.put("pageSize", MbaConstants.PAGE_SIZE);
		param.put("memberNo", memberNo);
		param.put("name", name);
		return getSqlMapClientTemplate().queryForList("member.getMemberList", param);
	}

	@Override
	public int getMemberCount(String memberNo,String name) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("memberNo", memberNo);
		param.put("name", name);
		return (int) getSqlMapClientTemplate().queryForObject("member.totalCount",param);
	}

	@Override
	public CompanyInfo getCompanyInfo(String memberNo) {
		return (CompanyInfo) getSqlMapClientTemplate().queryForObject("member.getCompanyInfo", memberNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CatchWebInfo> getWebInfo(String memberNo, Integer type) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("memberNo", memberNo);
		param.put("type", type);
		return getSqlMapClientTemplate().queryForList("member.getCompanyDetail", param);
	}

	@Override
	public List<MemberCertificate> getMemberCertificates(String memberNo) {
		return getSqlMapClientTemplate().queryForList("member.getCertificate", memberNo);
	}

	@Override
	public IndustryFunction getIndustryFunction(String industry, String function) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("industry", industry);
		param.put("job_function", function);
		return (IndustryFunction) getSqlMapClientTemplate().queryForObject("member.getIndustryFunction", param);
	}

	@Override
	public Integer getSchoolScore(String schoolName) {
		return (Integer) getSqlMapClientTemplate().queryForObject("school.getScore", schoolName);
	}

	@Override
	public int saveMember(AppData info) {
		getSqlMapClientTemplate().insert("member.saveMember", info);
		return 1;
	}

	@Override
	public int saveDegrees(String memberNo,List<Education> degrees) {
		Map<String, Object> para=new HashMap<String, Object>();
		para.put("referenceNo", memberNo);
		para.put("degrees", degrees);
		getSqlMapClientTemplate().insert("member.saveDegrees", para);
		return 1;
	}

	@Override
	public int saveCertificates(String memberNo,List<Certificate> certificates) {
		Map<String, Object> para=new HashMap<String, Object>();
		para.put("referenceNo", memberNo);
		para.put("certificates", certificates);
		getSqlMapClientTemplate().insert("member.saveCertificates", para);
		return 1;
	}
	
	@Override
	public int saveHonors(String memberNo,List<HonorAward> honorAwards) {
		Map<String, Object> para=new HashMap<String, Object>();
		para.put("referenceNo", memberNo);
		para.put("honorAwards", honorAwards);
		getSqlMapClientTemplate().insert("member.saveHonors", para);
		return 1;
	}

}
