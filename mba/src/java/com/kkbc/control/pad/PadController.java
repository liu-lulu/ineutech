package com.kkbc.control.pad;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gexin.fastjson.JSONObject;
import com.kkbc.commom.MbaConstants;
import com.kkbc.service.SchoolService;
import com.kkbc.util.AppendToFileUtil;
import com.kkbc.util.Gaokaopai;
import com.kkbc.vo.FirstDegreeResult;
import com.kkbc.vo.FirstDegrees;
import com.kkbc.vo.MajorScoreResult;
import com.kkbc.vo.MemberResult;

/**
 * 用户模块
 * 
 * @author liululu
 *
 */
@Controller
@RequestMapping("/result")
public class PadController {
	
	private Logger logger = LoggerFactory.getLogger(PadController.class);
	
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
	
	@RequestMapping("score")
	public void score(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject jsonObject = new JSONObject();
		PrintWriter out = response.getWriter();
		
		String schoolName = request.getParameter("schoolName");
		String schoolUrl = request.getParameter("schoolUrl");
		String type = request.getParameter("type");
		
//		String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
//		int a = (int)(Math.random()*(9999-1000+1))+1000;
//		String filePath="";
//		if ("1".equals(type)) {
//			filePath = "D:\\"+"gaokaoscore"+"_"+time+a+".txt";
//			Gaokaopai.getScore(schoolName, schoolUrl, filePath);
//		}else {
//			filePath = "D:\\"+"gaokaomajorscore"+"_"+time+a+".txt";
//			Gaokaopai.getMajorScore(schoolName, schoolUrl, filePath);
//		}
//		jsonObject.put("msg", filePath);
		
		jsonObject.put("state", schoolService.saveScore(schoolName, schoolUrl, type));

		out.write(jsonObject.toString());
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("degreeResult")
	public ModelAndView degreeResult(HttpServletResponse response,HttpServletRequest request) throws IOException, ServletException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String memberNo = request.getParameter("memberNo");
		
		MemberResult result = schoolService.getResult1(memberNo);
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("memberNo", memberNo);
		
		if (result==null) {
			ret.put("msg", "没有对应人员");
			return new ModelAndView("login", ret);
		}
		ret.put("result", result);
		
		
		return new ModelAndView("result1", ret);
	}
	
	@RequestMapping("degreeRet")
	public ModelAndView degreeRet(HttpServletResponse response,HttpServletRequest request ) throws IOException, ServletException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String memberNo = request.getParameter("memberNo");
		
		MemberResult result = schoolService.getResult1(memberNo);
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("memberNo", memberNo);
		
		if (result==null) {
			ret.put("msg", "没有对应人员");
			return new ModelAndView("result1", ret);
		}
		ret.put("result", result);
		
		
		return new ModelAndView("result1", ret);
	}
	
	@RequestMapping("educationRet")
	public ModelAndView educationRet(HttpServletResponse response,HttpServletRequest request ) throws IOException, ServletException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String memberNo = request.getParameter("memberNo");
		
		MemberResult result = schoolService.getResult1(memberNo);
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("memberNo", memberNo);
		
		if (result==null) {
			ret.put("msg", "没有对应人员");
			return new ModelAndView("result1", ret);
		}
		ret.put("result", result);
		ret.put("type", 1);
		
		return new ModelAndView("result1", ret);
	}
	
	@RequestMapping("professionRet")
	public ModelAndView professionRet(HttpServletResponse response,HttpServletRequest request ) throws IOException, ServletException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String memberNo = request.getParameter("memberNo");
		
		MemberResult result = schoolService.getResult1(memberNo);
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("memberNo", memberNo);
		
		if (result==null) {
			ret.put("msg", "没有对应人员");
			return new ModelAndView("result1", ret);
		}
		ret.put("result", result);
		ret.put("type", 2);
		
		return new ModelAndView("result1", ret);
	}
	
	@RequestMapping("aa")
	public void aa(HttpServletResponse response,HttpServletRequest request) throws IOException, ServletException {
		File dataFile = new File("C:\\Users\\liululu\\Desktop\\aa.xlsx");
		FileInputStream inputStream = new FileInputStream(dataFile);
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
		if (wb != null) {
			if (wb.getNumberOfSheets()>=1) {
				XSSFSheet sheet = wb.getSheetAt(0);
				int rows = sheet.getPhysicalNumberOfRows();
				for (int i = 1; i < rows; i++) {
					XSSFRow row = sheet.getRow(i);
					String cellContent=row.getCell(0)+"";
					String memberNo = cellContent.indexOf(".")!=-1?cellContent.substring(0,cellContent.indexOf(".")):cellContent;
					MemberResult result = schoolService.getResult1(memberNo);
					StringBuffer firstContent=new StringBuffer();
					firstContent.append(memberNo+"	");
					FirstDegrees first=result.getFirstDegreeResult1();
					if (first!=null) {
						
							firstContent.append(first.getDegreeScore()+"	");
							firstContent.append(first.getDegreeRank()+"	");
							List<FirstDegreeResult> results = first.getFirstDegreeResults();
							if (results!=null) {
								for (FirstDegreeResult detail : results) {
									firstContent.append(detail.getSchool_name()+"	");
									firstContent.append(detail.getMajor()+"	");
									firstContent.append(detail.getScoreRank()+"	");
									firstContent.append(detail.getRanking()+"	");
									firstContent.append(detail.getDegreeRank()+"	");
									firstContent.append(detail.getDegreeScore()+"	");
								}
							}
							
					}
					firstContent.append("\r\n");
//					AppendToFileUtil.appendMethodB("C:\\Users\\liululu\\Desktop\\mba_first.txt", firstContent.toString());
					
					StringBuffer rowContent=new StringBuffer();
					rowContent.append(memberNo+"	");
					rowContent.append(result.getMajorScore()+"	");
					for (MajorScoreResult majorScoreResult : result.getDegrees()) {
						rowContent.append(majorScoreResult.getSchool_name()+"	");
						rowContent.append(majorScoreResult.getMajor()+"	");
						rowContent.append(majorScoreResult.getDegree()+"	");
						rowContent.append(majorScoreResult.getRank()+"	");
						rowContent.append(majorScoreResult.getScore()+"	");
					}
					rowContent.append("\r\n");
//					AppendToFileUtil.appendMethodB("C:\\Users\\liululu\\Desktop\\mba_major.txt", rowContent.toString());
					
//					AppendToFileUtil.appendMethodB("C:\\Users\\liululu\\Desktop\\mba_score.txt", memberNo+"	"+result.getInfo().getName()+"	"+result.getInfo().getProvince()+"	"+result.getInfo().getWork_time()+"	"+result.getInfo().getManage_time()+"	"+result.getInfo().getCompany()+"	"+result.getInfo().getCompany_address()+"	"+result.getInfo().getJob()+"	"+result.getInfo().getJob_level()+"	"+result.getInfo().getSalary()+"	"+result.getInfo().getCompany_num()+"	"+result.getInfo().getStaff_num()+"	"+result.getInfo().getToefl_score()+"	"+result.getInfo().getIelts_score()+"	"+result.getInfo().getGmat_score()+"	"+result.getInfo().getProgram()+"	"+result.getTotalScore()+"	"+result.getMajorScore()+"	"+first.getDegreeScore()+"	"+result.getInterExam()+"	"+result.getCompanyScore()+"	"+result.getWorkExpScore()+"	"+result.getManageExpScore()+"	"+result.getJobScore()+"\r\n");
					AppendToFileUtil.appendMethodB("C:\\Users\\liululu\\Desktop\\mba_score.txt", memberNo+"	"+result.getMajorScore()+"	"+first.getDegreeScore()+"	"+result.getInterExam()+"	"+result.getCompanyScore()+"	"+result.getWorkExpScore()+"	"+result.getManageExpScore()+"	"+result.getJobScore()+"	"+result.getCertificateScore()+"\r\n");
//					AppendToFileUtil.appendMethodB("C:\\Users\\liululu\\Desktop\\mba_score-2.txt", memberNo+"	"+result.getMajorScore()+"	"+first.getDegreeScore()+"	"+result.getInterExam()+"	"+result.getCompanyScore()+"	"+result.getWorkExpScore()+"	"+result.getManageExpScore()+"	"+result.getJobScore()+"	"+result.getCertificateScore()+"\r\n");
					
//					if ((result.getMajorScore()<5&&Math.abs(result.getMajorScore()-first.getDegreeScore())>=4)||result.getCompanyScore()==0) {
//					if (result.getMajorScore()<5&&Math.abs(result.getMajorScore()/10-first.getDegreeScore()/16)>=0.2) {
					if (first==null||first.getFirstDegreeResults()==null||first.getFirstDegreeResults().size()==0) {	
//						AppendToFileUtil.appendMethodB("C:\\Users\\liululu\\Desktop\\mba_score-2.txt", memberNo+"	"+result.getMajorScore()+"	"+first.getDegreeScore()+"\r\n");
//						AppendToFileUtil.appendMethodB("C:\\Users\\liululu\\Desktop\\mba_score-2.txt", memberNo+"\r\n");
					}
					
				}
			}
		}
	}
	
	@RequestMapping("degreeRank")
	public void degreeRank(HttpServletResponse response,HttpServletRequest request) throws IOException, ServletException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject jsonObject = new JSONObject();
		PrintWriter out = response.getWriter();
		
		String degree = request.getParameter("degree");
		
		String highSchoolLocation = request.getParameter("highSchoolLocation");
		String schoolName = request.getParameter("schoolName");
		String major = request.getParameter("major");
		
		String masterSchoolName = request.getParameter("masterSchoolName");
		String masterMajor = request.getParameter("masterMajor");
		
		String doctorSchoolName = request.getParameter("doctorSchoolName");
		String doctorMajor = request.getParameter("doctorMajor");
		
		
//		jsonObject.put("degreeRank", schoolService.getDegreeRank(schoolName, major,degree,highSchoolLocation));
//		jsonObject.put("majorScore", schoolService.getMajorScore(degree, schoolName, major, masterSchoolName, masterMajor, doctorSchoolName, doctorMajor));
		out.write(jsonObject.toString());
		out.flush();
		out.close();
	}

	@RequestMapping("xuexin")
	public ModelAndView xuexin(HttpServletResponse response,HttpServletRequest request) throws IOException, ServletException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String code = request.getParameter("code");
		
		String httpUrl="http://153.37.217.112:8088/search/?yzm="+code;
		String httpResult=Gaokaopai.requestByGetMethod(httpUrl);
		
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("code", code);
		
		JSONObject info=JSONObject.parseObject(httpResult);
		if (info.getIntValue("status")==0) {
			ret.put("msg", "没有相关信息");
			return new ModelAndView("../xuexinweb", ret);
		}else {
			String detail=info.getString("content");
			JSONObject detailJsonObject=JSONObject.parseObject(detail);
			ret.put("name", detailJsonObject.getString("姓名"));
			ret.put("sex", detailJsonObject.getString("性别"));
			ret.put("num", detailJsonObject.getString("证件号码"));
			ret.put("nation", detailJsonObject.getString("民族"));
			ret.put("birthday", detailJsonObject.getString("出生日期"));
			ret.put("academy", detailJsonObject.getString("院校"));
			ret.put("level", detailJsonObject.getString("层次"));
			ret.put("faculty", detailJsonObject.getString("院系"));
			ret.put("classInfo", detailJsonObject.getString("班级"));
			ret.put("major", detailJsonObject.getString("专业"));
			ret.put("studentNo", detailJsonObject.getString("学号"));
			ret.put("form", detailJsonObject.getString("形式"));
			ret.put("inTime", detailJsonObject.getString("入学时间"));
			ret.put("educational", detailJsonObject.getString("学制"));
			ret.put("type", detailJsonObject.getString("类型"));
			ret.put("status", detailJsonObject.getString("学籍状态"));
			
		}
		ret.put("result", httpResult);
		
		
		
		return new ModelAndView("xuexinweb/xuexinret", ret);
	}
	
	@RequestMapping("scoreList")
	public ModelAndView scoreList(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String curPageStr = request.getParameter("currentPageNO");
		String memberNo = request.getParameter("memberNo");
		String name = request.getParameter("name");
		
		//當前頁
		int curPage = StringUtils.isEmpty(curPageStr) ? 1 : Integer.valueOf(curPageStr);
		
		Map<String, Object> mvMap = new HashMap<String, Object>();
		mvMap.put("datas", schoolService.getList(curPage,memberNo,name));
		mvMap.put("memberNo", memberNo);
		mvMap.put("name", name);
		int count=schoolService.getMemberCount(memberNo,name);
		//頁數
		int pageNum = count % MbaConstants.PAGE_SIZE == 0 ? count / MbaConstants.PAGE_SIZE : (count / MbaConstants.PAGE_SIZE + 1);
		request.setAttribute("pageCount", pageNum);
		request.setAttribute("rowNum", MbaConstants.PAGE_SIZE);
		request.setAttribute("currentPageNO", curPage);
		request.setAttribute("sizeOfTotalList", count);
		return new ModelAndView("memberlist", mvMap);
		
	}
	
	
	@RequestMapping("detail")
	public ModelAndView detail(HttpServletResponse response,HttpServletRequest request) throws IOException, ServletException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String resultDetail = request.getParameter("resultDetail");
		
		MemberResult result = JSONObject.parseObject(resultDetail, MemberResult.class);
		
		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("result", result);
		
		
		return new ModelAndView("result1", ret);
	}
}
