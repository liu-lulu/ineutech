package com.kkbc.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.kkbc.entity.Member;
import com.kkbc.vo.AppData;
import com.kkbc.vo.MemberResult;


public interface SchoolService {
	
	int saveScore(String schoolName, String schoolUrl, String type);
	
	/*String getDegreeRank(String schoolName, String major,String highSchoolLocation);
	
	float getMajorScore(String degree,String bachelorSchool,String bachelorMajor,String masterSchool,String masterMajor,String doctorSchool,String doctorMajor);
	
	MemberResult getResult(String memberNo);*/
	MemberResult getResult1(String memberNo);
	
	Map<Member,MemberResult> getList(Integer pagenum,String memberNo,String name);
	int getMemberCount(String memberNo,String name);
	List<Member> getList();
	
	int saveAppData(AppData data);
	
	void apiResult(MemberResult result,JSONObject ret);

}
