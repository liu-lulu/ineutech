package com.kkbc.control.pad;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kkbc.entity.Member;
import com.kkbc.service.SchoolService;
import com.kkbc.vo.AppData;
import com.kkbc.vo.Certificate;
import com.kkbc.vo.Education;
import com.kkbc.vo.MemberResult;

/**
 * 用户模块
 * 
 * @author liululu
 *
 */
@Controller
@RequestMapping("/api")
public class ApiController {
	
	private Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Resource
	private SchoolService schoolService;

	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex,HttpServletResponse response) throws IOException  {
		logger.error("请求出现异常:"+ex.getMessage());
		ex.printStackTrace();
		
		PrintWriter out =  response.getWriter();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", 2);
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	

	
	@RequestMapping("degreeRet")
	public void degreeResult(HttpServletResponse response,HttpServletRequest request) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String memberNo = request.getParameter("memberNo");
		
		MemberResult result = schoolService.getResult1(memberNo);
		
		JSONObject ret = new JSONObject();
		if (result==null) {//没有对应人员
			ret.put("state", 1);
		}else {
			ret.put("state", 3);
			schoolService.apiResult(result, ret);
//			StringBuffer expMsg=new StringBuffer();
//			float majorScore=result.getMajorScore();
//			float firstDegreeScore=result.getFirstDegrees().getDegreeScore();
//			JSONObject detail = new JSONObject();
//			detail.put("degree", majorScore);
//			detail.put("university", firstDegreeScore);
//			detail.put("g_i_t", result.getInterExam());
//			detail.put("company", result.getCompanyScore());
//			detail.put("work_exp", result.getWorkExpScore());
//			detail.put("manager_exp", result.getManageExpScore());
//			detail.put("title", result.getJobScore());
//			detail.put("training", result.getCertificateScore());
//			
//			ret.put("result", detail);
//			
//			System.out.println(Math.abs(majorScore/10-firstDegreeScore/16));
//			if (majorScore<5&&Math.abs(majorScore/10-firstDegreeScore/16)>=0.2) {
//				expMsg.append("学位学历与第一学历得分差距较大，请多加留意。");
//			}
//			if (result.getCompanyScore()==0) {
//				expMsg.append("公司情况得分为零，请多加留意。");
//			}
//			
//			if (result.getWorkInfo().getShixin()==1) {
//				expMsg.append("该人员失信，请多加留意。");
//			}
//			ret.put("expMsg", expMsg.toString());
		}
		
		PrintWriter out =  response.getWriter();
		System.out.println(ret.toString());
		out.print(ret.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("member")
	public void member(HttpServletResponse response,HttpServletRequest request) throws IOException, ServletException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject ret = new JSONObject();
		
		/*Map<Member, MemberResult> allResult=schoolService.getList(null, null, null);
		JSONArray jsonArray = new JSONArray();
		for (Entry<Member, MemberResult> entry: allResult.entrySet()) {
			MemberResult result=entry.getValue();
			JSONObject detail = new JSONObject();
			detail.put("memberNo", entry.getKey().getMember_serialno());
			detail.put("degree", result.getMajorScore());
			detail.put("university", result.getFirstDegrees().getDegreeScore());
			detail.put("g_i_t", result.getInterExam());
			detail.put("company", result.getCompanyScore());
			detail.put("work_exp", result.getWorkExpScore());
			detail.put("manager_exp", result.getManageExpScore());
			detail.put("title", result.getJobScore());
			detail.put("training", result.getCertificateScore());
			jsonArray.add(detail);
		}
		ret.put("result", jsonArray);*/
		
		List<Member> members=schoolService.getList();
		ret.put("result", members.stream().map(Member::getMember_serialno).collect(Collectors.toList()));
		ret.put("state", 3);
		PrintWriter out =  response.getWriter();
		out.print(ret.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("saveData")
	public void saveData(HttpServletResponse response,HttpServletRequest request) throws IOException, ServletException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject ret = new JSONObject();
		
		String data=request.getParameter("appData");
		logger.info(data);
		
//		String data="{\"annualSalary\":null,\"annualSales\":null,\"asset\":null,\"certificates\":[{\"accreditingOrganization\":null,\"certification\":null}],\"company\":null,\"companyAbbreviation\":null,\"companyAddress\":null,\"companyIndustry\":null,\"companyNature\":null,\"educations\":[{\"dateOfAward\":null,\"degree\":null,\"educationLevel\":null,\"firstLevelDiscipline\":null,\"gpa\":null,\"major\":null,\"modeOfStudy\":null,\"schoolCountry\":null,\"schoolLocation\":null,\"schoolName\":null,\"schoolNameOther\":null}],\"gender\":null,\"gmatTotleScore\":1,\"ieltsTotleScore\":null,\"jobTitleLevel\":null,\"managementExperience\":null,\"name\":null,\"numberOfEmployees\":null,\"passportNo\":null,\"placeOfBirth\":null,\"position\":null,\"program\":null,\"referenceNo\":\"111\",\"subordinateNumber\":null,\"toeflTotleScore\":null,\"workExperience\":null,\"workFunction\":null}";
		AppData appData=JSONObject.parseObject(data, AppData.class);
		int flag=schoolService.saveAppData(appData);
		if (flag==2) {//该人员数据已存在
			ret.put("state", 4);
		}else {
			ret.put("state", 3);
			
			MemberResult result = schoolService.getResult1(appData.getReferenceNo());
			schoolService.apiResult(result, ret);
//			float majorScore=result.getMajorScore();
//			float firstDegreeScore=result.getFirstDegrees().getDegreeScore();
//			JSONObject detail = new JSONObject();
//			detail.put("degree", majorScore);
//			detail.put("university", firstDegreeScore);
//			detail.put("g_i_t", result.getInterExam());
//			detail.put("company", result.getCompanyScore());
//			detail.put("work_exp", result.getWorkExpScore());
//			detail.put("manager_exp", result.getManageExpScore());
//			detail.put("title", result.getJobScore());
//			detail.put("training", result.getCertificateScore());
//			
//			ret.put("result", detail);
//			
//			StringBuffer expMsg=new StringBuffer();
//			System.out.println(Math.abs(majorScore/10-firstDegreeScore/16));
//			if (majorScore<5&&Math.abs(majorScore/10-firstDegreeScore/16)>=0.2) {
//				expMsg.append("学位学历与第一学历得分差距较大，请多加留意。");
//			}
//			if (result.getCompanyScore()==0) {
//				expMsg.append("公司情况得分为零，请多加留意。");
//			}
//			if (result.getWorkInfo().getShixin()==1) {
//				expMsg.append("该人员失信，请多加留意。");
//			}
//			ret.put("expMsg", expMsg.toString());
		}
		
		PrintWriter out =  response.getWriter();
		out.print(ret.toString());
		
		out.flush();
		out.close();
	}
	
	public static void main(String[] args) {
		AppData data = new AppData();
		List<Education> degreeInfos=new ArrayList<Education>();
		Education degree1=new Education();
		degreeInfos.add(degree1);
		List<Certificate> certificates=new ArrayList<Certificate>();
		Certificate certificate1=new Certificate();
		certificates.add(certificate1);
		
		data.setEducations(degreeInfos);
		data.setCertificates(certificates);
		
		System.out.println(JSONObject.toJSONString(data,SerializerFeature.WriteMapNullValue));
		
		String aa="{\"annualSalary\":null,\"annualSales\":null,\"asset\":null,\"certificates\":[{\"accreditingOrganization\":null,\"certification\":null}],\"company\":null,\"companyAbbreviation\":null,\"companyAddress\":null,\"companyIndustry\":null,\"companyNature\":null,\"educations\":[{\"dateOfAward\":null,\"degree\":null,\"educationLevel\":null,\"firstLevelDiscipline\":null,\"gpa\":null,\"major\":null,\"modeOfStudy\":null,\"schoolCountry\":null,\"schoolLocation\":null,\"schoolName\":null,\"schoolNameOther\":null}],\"gender\":null,\"gmatTotleScore\":\"1分\",\"ieltsTotleScore\":null,\"jobTitleLevel\":null,\"managementExperience\":null,\"name\":null,\"numberOfEmployees\":null,\"passportNo\":null,\"placeOfBirth\":null,\"position\":null,\"program\":null,\"referenceNo\":null,\"subordinateNumber\":null,\"toeflTotleScore\":null,\"workExperience\":null,\"workFunction\":null}";
		AppData retAppData=JSONObject.parseObject(aa, AppData.class);
//		System.out.println("--"+Float.valueOf(retAppData.getWorkExperience()));
//		data.setWorkExperience(String.valueOf(Float.valueOf(retAppData.getWorkExperience())));
//		System.out.println(com.gexin.fastjson.JSONObject.toJSONString(retAppData));
	}
}
