package com.kkbc.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.kkbc.commom.MbaConstants;
import com.kkbc.control.pad.ApiController;
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
import com.kkbc.service.SchoolService;
import com.kkbc.util.Gaokaopai;
import com.kkbc.util.StringHelper;
import com.kkbc.vo.AppData;
import com.kkbc.vo.Education;
import com.kkbc.vo.FirstDegreeResult;
import com.kkbc.vo.FirstDegrees;
import com.kkbc.vo.MajorScoreResult;
import com.kkbc.vo.MemberResult;
import com.kkbc.vo.SchoolProvinceMajorScoreVO;
import com.kkbc.vo.WebInfo;
import com.kkbc.vo.WorkBackground;

public class SchoolServiceImpl implements SchoolService{
	private Logger logger = LoggerFactory.getLogger(SchoolServiceImpl.class);
	
	@Resource
	private SchoolDao schoolDao;

	@Transactional
	@Override
	public int saveScore(String schoolName, String schoolUrl, String type) {
		School school = schoolDao.getSchool(schoolName);
		if (school==null) {
			school = new School(schoolName,schoolUrl);
			school.setSchool_id(schoolDao.saveSchool(school));
		}
		if (StringUtils.isEmpty(type)) {//分数线 和 专业分数线 数据
			
			List<Score> scores = Gaokaopai.getScore(school, schoolUrl);
			if (scores.size()>0) {
				schoolDao.saveScore(scores);
			}
			
			List<MajorScore> majorScores = Gaokaopai.getMajorScore(school, schoolUrl);
			if (majorScores.size()>0) {
				schoolDao.saveMajorScore(majorScores);
			}
		}else if ("1".equals(type)) {//只要分数线数据
			List<Score> scores = Gaokaopai.getScore(school, schoolUrl);
			if (scores.size()>0) {
				schoolDao.saveScore(scores);
			}
		}else if ("2".equals(type)){//只要专业分数线数据
			List<MajorScore> majorScores = Gaokaopai.getMajorScore(school, schoolUrl);
			if (majorScores.size()>0) {
				schoolDao.saveMajorScore(majorScores);
			}
		}
		
		return 1;
	}
	
/*	@Override
	public MemberResult getResult(String memberNo) {
		
		Member member = schoolDao.getMember(memberNo);
		if (member == null) {
			return null;
		}
		
		//工作背景
		WebInfo workInfo = getWorkInfo(member);
		List<MemberDegree> allDegree=schoolDao.getMemberDegree(memberNo, null);
		List<MemberHonor> honors = schoolDao.getMemberHonors(memberNo);
		
		MemberDegree juniorDegree=null,bachelorDegree = null,masterDegree = null,doctorDegree = null;
		
		List<MajorScoreResult> degrees = new ArrayList<MajorScoreResult>();
		if (allDegree!=null) {
			Map<String, MemberDegree> degreeMap=allDegree.stream().collect(Collectors.toMap(MemberDegree::getDegree_type, Function.identity(), (key1, key2) -> key2));
			
			MemberDegree juniorDegree1 = degreeMap.get(MbaConstants.DEGREE_JUNIOR_1);
			MemberDegree juniorDegree2 = degreeMap.get(MbaConstants.DEGREE_JUNIOR);
			
			juniorDegree=(juniorDegree1!=null)?juniorDegree1:juniorDegree2;
			if (juniorDegree!=null) {
				degrees.add(new MajorScoreResult(juniorDegree.getDegree_type(), juniorDegree.getSchool_name(), juniorDegree.getMajor(), "", null));
			}
			
			MemberDegree bachelorDegree1 = degreeMap.get(MbaConstants.DEGREE_BACHELOR);
			MemberDegree bachelorDegree2 = degreeMap.get(MbaConstants.DEGREE_UNDERGRADUATE);
			
			MemberDegree masterDegree1 = degreeMap.get(MbaConstants.DEGREE_MASTER);
			MemberDegree masterDegree2 = degreeMap.get(MbaConstants.DEGREE_MASTER_1);
			
			doctorDegree = degreeMap.get(MbaConstants.DEGREE_DOCTOR);
			
			bachelorDegree=(bachelorDegree1!=null)?bachelorDegree1:bachelorDegree2;
			masterDegree=(masterDegree1!=null)?masterDegree1:masterDegree2;
		}
				
		String degree = member.getDegree_type();
		
		FirstDegreeResult degreeRank;
		Float majorScore;

		
		//平均专业水平计算
		majorScore=getMajorScore(degree, bachelorDegree,masterDegree,doctorDegree,degrees);
		
		//学历级别
		if (MbaConstants.DEGREE_JUNIOR.equals(degree)||MbaConstants.DEGREE_JUNIOR_1.equals(degree)) {// 大专 专科
			return new MemberResult(member,new FirstDegreeResult(MbaConstants.DEGREE_C,MbaConstants.MAJORSCORE.get(MbaConstants.DEGREE_C), null, null,null), null,degrees,workInfo,honors);
		}else {
			degreeRank = getDegreeRank(member,juniorDegree,bachelorDegree);
		}
		return new MemberResult(member,degreeRank, majorScore,degrees,workInfo,honors);
	}*/
	@Override
	public MemberResult getResult1(String memberNo) {
		
		Member member = schoolDao.getMember(memberNo);
		if (member == null) {
			return null;
		}
		
		//工作背景
		WebInfo workInfo = getWorkInfo(memberNo);
//		WebInfo workInfo = null;
		List<MemberDegree> allDegree=schoolDao.getMemberDegree(memberNo, null);
		List<MemberHonor> honors = schoolDao.getMemberHonors(memberNo);
		List<MemberCertificate> certificates=schoolDao.getMemberCertificates(memberNo);
		
		List<MajorScoreResult> degrees = new ArrayList<MajorScoreResult>();
		List<MemberDegree> juniorDegreeList = new ArrayList<MemberDegree>();
		List<MemberDegree> bachelorDegreeList = new ArrayList<MemberDegree>();
		List<MemberDegree> masterDegreeList = new ArrayList<MemberDegree>();
		List<MemberDegree> doctorDegreeList = new ArrayList<MemberDegree>();
		if (allDegree!=null) {
			for (MemberDegree degree : allDegree) {
				//2019数据
				if ((StringUtils.isNotEmpty(degree.getSchool_name())&&degree.getSchool_name().contains("其他国家"))||(StringUtils.isEmpty(degree.getSchool_name())&&StringUtils.isNotEmpty(degree.getComment()))) {
					degree.setSchool_name(degree.getComment());
				}
				if (StringUtils.isNotEmpty(degree.getDegree_type())) {
					if (degree.getDegree_type().startsWith(MbaConstants.DEGREE_JUNIOR_1)||degree.getDegree_type().startsWith(MbaConstants.DEGREE_JUNIOR)) {
						juniorDegreeList.add(degree);
						degrees.add(new MajorScoreResult(degree.getDegree_type(), degree.getSchool_name(), degree.getMajor(),degree.getSubject_background(), "", null,degree.getLearn_format()));
					}else if (degree.getDegree_type().startsWith(MbaConstants.DEGREE_BACHELOR)||degree.getDegree_type().startsWith(MbaConstants.DEGREE_UNDERGRADUATE)) {
						bachelorDegreeList.add(degree);
					}else if (degree.getDegree_type().startsWith(MbaConstants.DEGREE_MASTER)||degree.getDegree_type().startsWith(MbaConstants.DEGREE_MASTER_1)) {
						masterDegreeList.add(degree);
					}else if (degree.getDegree_type().startsWith(MbaConstants.DEGREE_DOCTOR)) {
						doctorDegreeList.add(degree);
					}
				}
				
			}
		}
		
		MemberResult result=new MemberResult(member,null, null,degrees,workInfo,honors,certificates);
		FirstDegrees firstDegreeResult1 = null;
			
		String degree = member.getDegree_type();

		if (doctorDegreeList.size()>0) {
			degree=MbaConstants.DEGREE_DOCTOR;
		}else if (masterDegreeList.size()>0) {
			degree=MbaConstants.DEGREE_MASTER;
		}else if (bachelorDegreeList.size()>0) {
			degree=MbaConstants.DEGREE_BACHELOR;
		}else {
			degree=MbaConstants.DEGREE_JUNIOR;
		}
		
		Float majorScore=null;
		//学历级别
		if (MbaConstants.DEGREE_JUNIOR.equals(degree)) {// 大专 专科
			firstDegreeResult1=new FirstDegrees(null,3f,null);
			majorScore=2f;//大专学历
		}else {
			firstDegreeResult1 =getDegreeRank(member, juniorDegreeList, bachelorDegreeList);
			//平均专业水平计算
			majorScore=getMajorScore(degree, juniorDegreeList,bachelorDegreeList,masterDegreeList,doctorDegreeList,degrees);
		}
		firstDegreeResult1.setDegreeScore(formatFloat(firstDegreeResult1.getDegreeScore()));
		result.setFirstDegreeResult1(firstDegreeResult1);
		result.setMajorScore(formatFloat(majorScore));
		result.setInterExam(formatFloat(calInterExamScore(member)));
		boolean interMba=member.getProgram()!=null?(member.getProgram()==1?false:true):false;
		result.setWorkExpScore(formatFloat(calWorkScore(member.getWork_time(), interMba)));
		result.setManageExpScore(formatFloat(calManageScore(member.getManage_time(), interMba)));
		result.setCertificateScore(formatFloat(calCertificateScore(certificates)));
		result.setCompanyScore(formatFloat(MbaConstants.COMPANY_DEGREE_SCORE.get(workInfo.getCompanyInfo().getTotalcount())));
		result.setJobScore(formatFloat(calJobScore(member, workInfo.getCompanyInfo().getTotalcount(), interMba)));
		
		calTotalScore(result);
		return result;
	}
	
	/**
	 * 计算总分
	 * @param info
	 * @return
	 */
	private float calTotalScore(MemberResult result){
		float companyScore=result.getCompanyScore();
		Float firstDegreeScore=result.getFirstDegrees().getDegreeScore();
		Float majorScore=result.getMajorScore();
		float interScore=result.getInterExam();
		float workExpScore=result.getWorkExpScore();
		float manageExpScore=result.getManageExpScore();
		float jobScore=result.getJobScore();
		majorScore=(majorScore==null?0:majorScore);
		float certificateScore=result.getCertificateScore();
		
		float totalScore=companyScore+firstDegreeScore+majorScore+interScore+workExpScore+manageExpScore+jobScore+certificateScore;
		System.err.println(result.getInfo().getName()+"--"+totalScore+"--"+firstDegreeScore+"--"+majorScore+"--"+interScore+"--"+workExpScore+"--"+manageExpScore+"--"+jobScore);
		result.setTotalScore(totalScore);
		return totalScore;
		
	}
	/**
	 * 计算国际考试得分（附加分 共2分）
	 * GMAT 600分(含)以上	+1
	 * IELTS 6.0(含)以上/TOEFL 90(含)以上	+1
	 * @param info
	 * @return
	 */
	private int calInterExamScore(Member info){
		Integer gmatScore=info.getGmat_score();
		Float ieltsScore=info.getIelts_score();
		Integer toeflScore=info.getToefl_score();
		info.setGmat_volid(volidTestDate(info.getGmat_date(), 5));
		info.setIelts_volid(volidTestDate(info.getIelts_date(), 2));
		info.setToefl_volid(volidTestDate(info.getToefl_date(), 2));
		int score=0;
		if (gmatScore!=null&&gmatScore>=600&&info.getGmat_volid()) {
			score+=1;
		}
		if ((ieltsScore!=null&&ieltsScore>=6&&info.getIelts_volid())||(toeflScore!=null&&toeflScore>=90&&info.getToefl_volid())) {
			score+=1;
		}
		return score;
	}
	
	/**
	 * 校验语言成绩是否过期
	 * 成绩获取的日期到申请当年的3月
	 * GMAT/GRE有效期5年 IELTS/TOEFL有效期2年
	 * @param testDate
	 * @param volidYear
	 * @return
	 */
	public static boolean volidTestDate(String testDate,int volidYear) {
		if (StringUtils.isEmpty(testDate)) {
			return false;
		}
		
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM");
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.MONTH, 2);//当年3月
		endCalendar.set(Calendar.DAY_OF_MONTH, 1);//当年3月1号
		
		Date startDate=null;
		try {
			startDate=format.parse(testDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (startDate==null) {
			return false;
		}
		
		Calendar startCalendar=Calendar.getInstance();
		startCalendar.setTime(startDate);
		startCalendar.set(Calendar.DAY_OF_MONTH, startCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		
//		SimpleDateFormat format1 = new SimpleDateFormat(
//				"yyyy-MM-dd");
//		System.out.println(format1.format(endCalendar.getTime()));
//		System.out.println(format1.format(startCalendar.getTime()));
		
		startCalendar.add(Calendar.YEAR, volidYear);
		
//		System.out.println(format1.format(startCalendar.getTime()));
		
		if (startCalendar.after(endCalendar)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 工作经验得分
	 * @param workTime 工作时间
	 * @param interMba true国际MBA false在职MBA
	 * @return
	 */
	private int calWorkScore(Float workTime,boolean interMba){
		if (interMba) {//国际MBA
			if ((workTime>=3&&workTime<4)||workTime>=14) {//3年／14年及以上 1分
				return 1;
			}else if ((workTime>=4&&workTime<5)||(workTime>=12&&workTime<14)) {// 4年/12-13年 2分
				return 2;
			}else if ((workTime>=5&&workTime<6)||(workTime>=10&&workTime<12)) {// 5年／10-11年 3分
				return 3;
			}else if (workTime>=6&&workTime<10) {// 6-9年 4分
				return 4;
			}
		}else {//在职MBA
			if ((workTime>=3&&workTime<5)||workTime>=21) {//3-4年／21年及以上 1分
				return 1;
			}else if ((workTime>=5&&workTime<7)||(workTime>=18&&workTime<21)) {// 5-6年/18-20年 2分
				return 2;
			}else if ((workTime>=7&&workTime<9)||(workTime>=15&&workTime<18)) {// 7-8年/15-17年 3分
				return 3;
			}else if (workTime>=9&&workTime<15) {// 9-14年 4分
				return 4;
			}
		}
		return 0;
	}
	
	/**
	 * 管理经验得分
	 * @param manageTime 工作时间
	 * @param interMba true国际MBA false在职MBA
	 * @return
	 */
	private int calManageScore(Float manageTime,boolean interMba){
		if (interMba) {//国际MBA
			if (manageTime>=5) {//5年 10分
				return 10;
			}else if (manageTime>=4) {//4年 8分
				return 8;
			}else if (manageTime>=3) {//3年 6分
				return 6;
			}else if (manageTime>=2) {//2年 4分
				return 4;
			}else if (manageTime>=1) {//1年 2分
				return 2;
			}
			
		}else {//在职MBA
			if (manageTime>=7) {//7年及以上 10分
				return 10;
			}else if (manageTime>=6) {//6年 8分
				return 8;
			}else if (manageTime>=5) {//5年 7分
				return 7;
			}else if (manageTime>=4) {//4年 6分
				return 6;
			}else if (manageTime>=3) {//3年 5分
				return 5;
			}else if (manageTime>=2) {//2年 4分
				return 4;
			}else if (manageTime>=1) {//1年 3分
				return 3;
			}
		}
		return 0;
	}
	
	/**
	 * 获取职务得分
	 * @param jobLevel 职务级别:总经理 副总经理 部门经理 部门主管 大学教师
	 * @param companyLevel 大型公司:A A- 中型公司:B+ 小型公司: B B-
	 * @param interMba true国际MBA false在职MBA
	 * @return
	 */
	private float calJobScore(Member info,String companyLevel,boolean interMba){
		float basicScore=MbaConstants.COMPANY_DEGREE_SCORE.get(companyLevel);
		float extraScore=0f;
		String jobLevel = info.getJob_level();
		IndustryFunction industryFunction=schoolDao.getIndustryFunction(info.getIndustry(), info.getJob_function());
		if (!MbaConstants.DEGREE_D_MINUS.equals(companyLevel)) {
			String highLevel="^(总经理/副总经理).*";
			String middleHighLevel="^(高级经理|总监|经理).*";
			String middleLevel="^(主管).*";
			boolean isBig=MbaConstants.DEGREE_A.equals(companyLevel)||MbaConstants.DEGREE_A_MINUS.equals(companyLevel)||MbaConstants.DEGREE_B_PLUS.equals(companyLevel);
			boolean isMiddle=MbaConstants.DEGREE_B.equals(companyLevel)||MbaConstants.DEGREE_B_MINUS.equals(companyLevel)||MbaConstants.DEGREE_C_PLUS.equals(companyLevel);
			boolean isSmall=MbaConstants.DEGREE_C.equals(companyLevel)||MbaConstants.DEGREE_C_MINUS.equals(companyLevel)||MbaConstants.DEGREE_D_PLUS.equals(companyLevel)||MbaConstants.DEGREE_D.equals(companyLevel);
			
			boolean isHighLevel=StringUtils.isNotEmpty(jobLevel)?jobLevel.matches(highLevel):false;
			boolean isMiddleHighLevel=StringUtils.isNotEmpty(jobLevel)?jobLevel.matches(middleHighLevel):false;
			boolean isMiddleLevel=StringUtils.isNotEmpty(jobLevel)?jobLevel.matches(middleLevel):false;
			if (interMba) {//国际MBA
				if (isBig) {
					if (isHighLevel||isMiddleHighLevel||isMiddleLevel) {
						basicScore=8f;
					}else {
						basicScore=6f;
					}
				}else if (isMiddle) {
					if (isHighLevel||isMiddleHighLevel) {
						basicScore=8f;
					}else if (isMiddleLevel) {
						basicScore=6f;
					}else {
						basicScore=4f;
					}
				}else if (isSmall) {
					if (isHighLevel) {
						basicScore=8f;
					}else if (isMiddleHighLevel) {
						basicScore=6f;
					}else{
						basicScore=4f;
					}
				}
				
			}else {//在职MBA
				if (isBig) {
					if (isHighLevel||isMiddleHighLevel) {
						basicScore=8f;
					}else if (isMiddleLevel) {
						basicScore=6f;
					}else {
						basicScore=4f;
					}
				}else if (isMiddle) {
					if (isHighLevel) {
						basicScore=8f;
					}else if (isMiddleHighLevel) {
						basicScore=6f;
					}else if (isMiddleLevel) {
						basicScore=4f;
					}else {
						basicScore=2f;
					}
				}else if (isSmall) {
					if (isHighLevel) {
						basicScore=6f;
					}else if (isMiddleHighLevel) {
						basicScore=4f;
					}else{
						basicScore=2f;
					}
				}
			}
		}
		
		if (industryFunction!=null) {
			if (interMba){//国际MBA额外加2
				extraScore=2;
			}else {//在职MBA额外加1
				extraScore=1;
			}
			
		}
		float totalScore=basicScore+extraScore;
		if (totalScore>8) {
			totalScore=8;
		}
		return totalScore;
	}
	
	private FirstDegrees getDegreeRank(Member member,List<MemberDegree> juniorDegreeList,List<MemberDegree> bachelorDegreeList){
		
		//如果不是大专学历，获取第一学历（可能是大专或本科）
		//获取大专学历
		if (juniorDegreeList.size()>0) {
			return new FirstDegrees(null,3f, null);
		}
		//获取本科学历
		if (bachelorDegreeList.size()>0) {
			List<FirstDegreeResult> results = new ArrayList<FirstDegreeResult>();
			
			float score = 0;
			for (MemberDegree bachelorDegree : bachelorDegreeList) {
				String learnFormat=bachelorDegree.getLearn_format();
				boolean hasBachelorDegree=(StringUtils.isEmpty(bachelorDegree.getDegree())||bachelorDegree.getDegree().startsWith("无"))?false:true;
				FirstDegreeResult degreeResult=null;
				if (StringUtils.isNotEmpty(learnFormat)) {
					if (learnFormat.matches(MbaConstants.LEARN_FORMAT_NORMAL)||learnFormat.contains(MbaConstants.LEARN_FORMAT_OUT)) {//普通全日制本科学历（包括海外院校）
						degreeResult=getDegreeRank(bachelorDegree.getSchool_name(), bachelorDegree.getMajor(), member.getProvince(),bachelorDegree.getSchool_location());
						degreeResult.setLearn_format(bachelorDegree.getLearn_format());
						degreeResult.setSchool_name(bachelorDegree.getSchool_name());
						degreeResult.setMajor(bachelorDegree.getMajor());
						
						if (hasBachelorDegree) {//有学位 R*4
							degreeResult.setDegreeScore(degreeResult.getDegreeScore()*4);
						}else {//无学位 R*4-2
							degreeResult.setDegreeScore(degreeResult.getDegreeScore()*4-2);
						}
						
					}else if (learnFormat.matches(MbaConstants.LEARN_FORMAT_SELFEXAM)) {//自考形式的本科学历
						if (hasBachelorDegree) {//有学位 5
							degreeResult=new FirstDegreeResult(null, 5f, null, null, null);
						}else {//无学位 3
							degreeResult=new FirstDegreeResult(null, 3f, null, null, null);
						}
					}else {//其他情况 3
						degreeResult=new FirstDegreeResult(null, 3f, null, null, null);
					}
				}else {
					degreeResult=new FirstDegreeResult(null, 3f, null, null, null);
				}
			
				if (StringUtils.isNotEmpty(bachelorDegree.getSchool_name())) {
					Integer schoolScore=schoolDao.getSchoolScore(bachelorDegree.getSchool_name());
					if (schoolScore!=null&&degreeResult.getDegreeScore()>schoolScore) {
						degreeResult.setDegreeScore(Float.valueOf(schoolScore));
					}
				}
				
				if (degreeResult.getDegreeScore()>score) {
					score=degreeResult.getDegreeScore();
				}
//				score+=(degreeResult.getDegreeScore());
				degreeResult.setLearn_format(learnFormat);
				degreeResult.setSchool_name(bachelorDegree.getSchool_name());
				degreeResult.setMajor(bachelorDegree.getMajor());
				degreeResult.setDegree(bachelorDegree.getDegree());
				results.add(degreeResult);
			}
			
//			score=score/bachelorDegreeList.size();
			
			return new FirstDegrees(null,score, results);
		}else {
			return new FirstDegrees(null,3f, null);
		}
	}
/*	private Float calDegreeAvgScore(Float inSumScore,Float outSumScore,float inCount,float outCount){
		Float score = null;
		if (inCount>0&&outCount>0) {//既有国内，又有国外，则国内0.4：国外0.6
			score=(float) (inSumScore*0.5/inCount+outSumScore*0.5/outCount);
		}else if (inCount>0) {
			score=inSumScore/inCount;
		}else if (outCount>0) {
			score=outSumScore/outCount;
		}
		return score;
	}*/
	
/*	private String getDegreeByScore(Float score){
		for (int i = 0; i < MbaConstants.DEGREE_RANK.length; i++) {
			Float scoreLine=MbaConstants.MAJORSCORE.get(MbaConstants.DEGREE_RANK[i]);
			if (!MbaConstants.DEGREE_C.equals(MbaConstants.DEGREE_RANK[i])&&score>=scoreLine) {
				return MbaConstants.DEGREE_RANK[i];
			}
		}
		
		return MbaConstants.DEGREE_B_MINUS;
	}*/
	
	private Float getMajorScore(String degree, List<MemberDegree> juniorDegreeList,List<MemberDegree> bachelorDegreeList,List<MemberDegree> masterDegreeList,List<MemberDegree> doctorDegreeList,List<MajorScoreResult> degrees) {
		Float bachelorScore=0f,masterScore=0f,doctorScore=0f;
		
		float sumScore=0;
		boolean hasBachelorDegree=false,hasMasterDegree=false;
		boolean hasBachelorExp=false,hasMasterExp=false,hasDoctorExp=false;
		boolean hasBachelorLearnFormat=false,hasMasterLearnFormat=false,hasDoctorLearnFormat=false;
		String bachelorRank,masterRank;
		if (bachelorDegreeList.size()==0) {
//			if (juniorDegreeList.size()==0) {
//				bachelorScore=1f;
//			}else {
//				bachelorScore=0.5f;
//			}
			hasBachelorExp=true;
			
			MajorScoreResult majorResult = new MajorScoreResult("", "", "", "","", null,"");
			majorResult.setDegree_type(MbaConstants.DEGREE_BACHELOR);
			degrees.add(majorResult);
		}else {
			for (MemberDegree memberDegree : bachelorDegreeList) {
				boolean thisBachelorDegree=(StringUtils.isNotEmpty(memberDegree.getDegree())&&!memberDegree.getDegree().startsWith("无"))?true:false;
				MajorScoreResult majorResult=null;
				if (thisBachelorDegree) {
					hasBachelorDegree=true;
				}
				String learnFormat=memberDegree.getLearn_format();
				if (StringUtils.isNotEmpty(learnFormat)&&(learnFormat.matches(MbaConstants.LEARN_FORMAT_NORMAL)||learnFormat.contains(MbaConstants.LEARN_FORMAT_OUT))) {//正规本科学历
					hasBachelorLearnFormat=true;
					majorResult = getMajorScore(memberDegree);
				}else {
					majorResult=new MajorScoreResult(memberDegree.getDegree(), memberDegree.getSchool_name(), memberDegree.getMajor(), memberDegree.getSubject_background(), MbaConstants.MAJOR_E, MbaConstants.MAJOR_E_SCORE,memberDegree.getLearn_format());
				}
				
				
				majorResult.setDegree_type(MbaConstants.DEGREE_BACHELOR);
				degrees.add(majorResult);
				
				if (majorResult.getScore()>bachelorScore) {//多学位中按最大
					bachelorScore=majorResult.getScore();
				}
				
//				sumScore+=majorResult.getScore();
			}
			
			
//			bachelorScore=sumScore/bachelorDegreeList.size();
		}
		
		if (MbaConstants.DEGREE_MASTER.equals(degree)||MbaConstants.DEGREE_DOCTOR.equals(degree)) {
			sumScore=0;
			if (masterDegreeList.size()==0) {
//				masterScore=1f;
				hasMasterExp=true;
				MajorScoreResult majorResult = new MajorScoreResult("", "", "", "","", null,"");
				majorResult.setDegree_type(MbaConstants.DEGREE_MASTER);
				degrees.add(majorResult);
			}else {
				for (MemberDegree memberDegree : masterDegreeList) {
					if (StringUtils.isNotEmpty(memberDegree.getDegree())&&!memberDegree.getDegree().startsWith("无")) {
						hasMasterDegree=true;
					}
					
					MajorScoreResult majorResult = getMajorScore(memberDegree);
					majorResult.setDegree_type(MbaConstants.DEGREE_MASTER);
					degrees.add(majorResult);
					
					if (majorResult.getScore()>masterScore) {
						masterScore=majorResult.getScore();
					}
//					sumScore+=majorResult.getScore();
					
				}
//					masterScore=sumScore/masterDegreeList.size();
				
			}
			
		}
		
		if (MbaConstants.DEGREE_DOCTOR.equals(degree)) {
			sumScore=0;
			if (doctorDegreeList.size()==0) {
				hasDoctorExp=true;
				
				MajorScoreResult majorResult = new MajorScoreResult("", "", "", "","", null,"");
				majorResult.setDegree_type(MbaConstants.DEGREE_DOCTOR);
				degrees.add(majorResult);
				
			}else {
				for (MemberDegree memberDegree : doctorDegreeList) {
					
					MajorScoreResult majorResult = getMajorScore(memberDegree);
					majorResult.setDegree_type(MbaConstants.DEGREE_DOCTOR);
					degrees.add(majorResult);
					
					if (majorResult.getScore()>doctorScore) {
						doctorScore=majorResult.getScore();
					}
//					sumScore+=majorResult.getScore();
				}
				
//					doctorScore=sumScore/doctorDegreeList.size();
				
				
			}
		}
		
		if (MbaConstants.DEGREE_UNDERGRADUATE.equals(degree)||MbaConstants.DEGREE_BACHELOR.equals(degree)) {//本科学历
			if (bachelorDegreeList.size()>1) {//双学士学位
				return bachelorScore+3.5f;
			}
			if (hasBachelorLearnFormat) {//正规本科学历
				if (hasBachelorDegree) {//有学位
					if (bachelorDegreeList.size()==1) {
						MemberDegree schoolInfo=bachelorDegreeList.get(0);
						if (StringUtils.isNotEmpty(schoolInfo.getSchool_name())&&schoolInfo.getSchool_name().matches(MbaConstants.SCHOOL_QBFJ)) {//清北复交
							logger.info("保底1："+schoolInfo.getMember_serialno());
							return Math.max(bachelorScore+3f,5.5f);
						}
						if (StringUtils.isNotEmpty(schoolInfo.getSchool_type())) {
							if (schoolInfo.getSchool_type().contains("985")) {//985高校
								logger.info("保底2："+schoolInfo.getMember_serialno());
								return Math.max(bachelorScore+3f,5f);
							}
							if (schoolInfo.getSchool_type().contains("211")) {//211高校
								logger.info("保底3："+schoolInfo.getMember_serialno());
								return Math.max(bachelorScore+3f,4.5f);
							}
						}
					}
					return bachelorScore+3f;
				}else {//无学位
					if (bachelorDegreeList.size()==1) {
						MemberDegree schoolInfo=bachelorDegreeList.get(0);
						if (StringUtils.isNotEmpty(schoolInfo.getSchool_name())&&schoolInfo.getSchool_name().matches(MbaConstants.SCHOOL_QBFJ)) {//清北复交
							logger.info("保底4："+schoolInfo.getMember_serialno());
							return Math.max(bachelorScore+2f,4.5f);
						}
						if (StringUtils.isNotEmpty(schoolInfo.getSchool_type())) {
							if (schoolInfo.getSchool_type().contains("985")) {//985高校
								logger.info("保底5："+schoolInfo.getMember_serialno());
								return Math.max(bachelorScore+2f,4f);
							}
							if (schoolInfo.getSchool_type().contains("211")) {//211高校
								logger.info("保底6："+schoolInfo.getMember_serialno());
								return Math.max(bachelorScore+2f,3.5f);
							}
						}
					}
					return bachelorScore+2f;
				}
			}
			if (juniorDegreeList.size()>0||!hasBachelorLearnFormat) {//专升本/非正规本科学历
				return 3f;
			}
		} else if (MbaConstants.DEGREE_MASTER.equals(degree)||MbaConstants.DEGREE_MASTER_1.equals(degree)) {//硕士学历
			if (bachelorDegreeList.size()==0) {//如果无本科学历
				if (juniorDegreeList.size()==0) {//如果没有专科学历，则本科学历按照硕士学历分数
					bachelorScore=masterScore;
				}else {//如果有专科学历，则本科学历0.5
					bachelorScore=0.5f;
				}
			}
			float totalDoctorScore=0;
			
			if (hasMasterDegree) {//有学位
				totalDoctorScore= (float) (bachelorScore*0.6+masterScore*0.8+5);
			}else {//无学位
				totalDoctorScore= (float) (bachelorScore*0.6+masterScore*0.6+5);
			}
			
			if (totalDoctorScore>10) {//超出10以10计
				return 10f;
			}
			return totalDoctorScore;
			
		}else if (MbaConstants.DEGREE_DOCTOR.equals(degree)) {//博士学历
			if (masterDegreeList.size()==0) {//如果无硕士学历，则硕士学历按照博士学历分数（硕博连读）
				masterScore=doctorScore;
			}
			if (bachelorDegreeList.size()==0) {//如果无本科学历
				if (juniorDegreeList.size()==0) {//如果没有专科学历，则本科学历按照硕士学历分数
					bachelorScore=masterScore;
				}else {//如果有专科学历，则本科学历0.5
					bachelorScore=0.5f;
				}
			}
			float totalDoctorScore=(float) (bachelorScore*0.4+masterScore*0.5+doctorScore*0.6+5.5);
			
			if (totalDoctorScore>10) {//超出10以10计
				return 10f;
			}
			return totalDoctorScore;
		}
		
		return 3f;
	}
	
/*	private Float getMajorScoreOld(String degree, List<MemberDegree> bachelorDegreeList,List<MemberDegree> masterDegreeList,List<MemberDegree> doctorDegreeList,List<MajorScoreResult> degrees) {
		if (MbaConstants.DEGREE_JUNIOR_1.equals(degree)||MbaConstants.DEGREE_JUNIOR.equals(degree)) {
			return null;
		}
		Float bachelorScore=null,masterScore=null,doctorScore=null;
		
		Float inSumScore=null,outSumScore=null;//国内学历总分，国外学历总分
		float inCount=0,outCount=0;//国内学历个数，国外学历个数
		for (MemberDegree memberDegree : bachelorDegreeList) {
			MajorScoreResult majorResult = getMajorScore(memberDegree);
			majorResult.setDegree(MbaConstants.DEGREE_BACHELOR);
			degrees.add(majorResult);
			if (majorResult.getScore()!=null) {
				if ("其他国家".equals(memberDegree.getSchool_location())) {
					if (outSumScore==null) {
						outSumScore=0f;
					}
					outCount+=1;
					outSumScore+=majorResult.getScore();
				}else if(majorResult.getScore()!=null) {
					if (inSumScore==null) {
						inSumScore=0f;
					}
					inCount+=1;
					inSumScore+=majorResult.getScore();
				}
			}
		}
		
		bachelorScore=calDegreeAvgScore(inSumScore, outSumScore, inCount, outCount);
		
		if (MbaConstants.DEGREE_MASTER.equals(degree)||MbaConstants.DEGREE_MASTER_1.equals(degree)||MbaConstants.DEGREE_DOCTOR.equals(degree)) {
			inSumScore=null;outSumScore=null;//国内学历总分，国外学历总分
			inCount=0;outCount=0;//国内学历个数，国外学历个数
			for (MemberDegree memberDegree : masterDegreeList) {
				MajorScoreResult majorResult = getMajorScore(memberDegree);
				majorResult.setDegree(MbaConstants.DEGREE_MASTER);
				degrees.add(majorResult);
				if (majorResult.getScore()!=null) {
					if ("其他国家".equals(memberDegree.getSchool_location())) {
						if (outSumScore==null) {
							outSumScore=0f;
						}
						outCount+=1;
						outSumScore+=majorResult.getScore();
					}else if(majorResult.getScore()!=null) {
						if (inSumScore==null) {
							inSumScore=0f;
						}
						inCount+=1;
						inSumScore+=majorResult.getScore();
					}
				}
			}
			masterScore=calDegreeAvgScore(inSumScore, outSumScore, inCount, outCount);
		}
		
		if (MbaConstants.DEGREE_DOCTOR.equals(degree)) {
			inSumScore=null;outSumScore=null;//国内学历总分，国外学历总分
			inCount=0;outCount=0;//国内学历个数，国外学历个数
			for (MemberDegree memberDegree : doctorDegreeList) {
				MajorScoreResult majorResult = getMajorScore(memberDegree);
				majorResult.setDegree(MbaConstants.DEGREE_DOCTOR);
				degrees.add(majorResult);
				if (majorResult.getScore()!=null) {
					if ("其他国家".equals(memberDegree.getSchool_location())) {
						if (outSumScore==null) {
							outSumScore=0f;
						}
						outCount+=1;
						outSumScore+=majorResult.getScore();
					}else if(majorResult.getScore()!=null) {
						if (inSumScore==null) {
							inSumScore=0f;
						}
						inCount+=1;
						inSumScore+=majorResult.getScore();
					}
				}
			}
			doctorScore=calDegreeAvgScore(inSumScore, outSumScore, inCount, outCount);
		}
		

		System.out.println("本科:"+bachelorScore+"硕士:"+masterScore+"博士:"+doctorScore);
		return getAvgMajorScore(degree, bachelorScore, masterScore, doctorScore);
	}*/
	
	/**
	 * 获取申请人的第一学历的级别
	 * @param member 申请人信息
	 * @return
	 */
/*	private FirstDegreeResult getDegreeRank(Member member,MemberDegree juniorDegree,MemberDegree bachelorDegree){
		
		//如果不是大专学历，获取第一学历（可能是大专或本科）
		//获取大专学历
		if (juniorDegree!=null) {
			return new FirstDegreeResult(MbaConstants.DEGREE_C,MbaConstants.MAJORSCORE.get(MbaConstants.DEGREE_C), null, null,null);
		}
		
		//获取本科学历
		if (bachelorDegree!=null) {
			MemberDegree firstDegree = bachelorDegree;
			return getDegreeRank(firstDegree.getSchool_name(), firstDegree.getMajor(), member.getProvince(),firstDegree.getSchool_location());
		}else {
			return new FirstDegreeResult(MbaConstants.DEGREE_B,MbaConstants.MAJORSCORE.get(MbaConstants.DEGREE_B), null, null,null);
		}
	}*/

	/**
	 * 根据高考得分率获取考取的该学校专业的级别
	 * @param schoolName 学校
	 * @param major 专业
	 * @param highSchoolLocation 出生地
	 * @return
	 */
	private FirstDegreeResult getDegreeRank(String schoolName, String major,String highSchoolLocation,String schoolLocation) {
		if (StringUtils.isEmpty(schoolName)) {
			return new FirstDegreeResult(MbaConstants.DEGREE_C_MINUS,MbaConstants.DEGREESCORE.get(MbaConstants.DEGREE_C_MINUS), null, null,null);
		}
		
		School school = getSchool(schoolName);
		
		if (school == null) {//如果学校不在本库中，默认设置最低级别
			
			if (StringUtils.isNotEmpty(schoolLocation)&&schoolLocation.contains("其他国家")) {
				return new FirstDegreeResult(MbaConstants.DEGREE_C_MINUS,MbaConstants.DEGREESCORE.get(MbaConstants.DEGREE_C_MINUS), null, " ",null);
			}else {
				return getDegreeResultWhenNoScore(schoolName);
			}
		}
		
		String ranking=school.getRanking()==null?" ":school.getRanking()+"";
		if (MbaConstants.SCHOOL_OUT==school.getInout()) {//国外学校按照排名
			String degreeRank=StringUtils.isEmpty(school.getSchool_level())?MbaConstants.DEGREE_C_MINUS:school.getSchool_level();
			return new FirstDegreeResult(degreeRank,MbaConstants.DEGREESCORE.get(degreeRank), null, ranking, null);
		}else if (MbaConstants.DEGREE_A.equals(school.getSchool_level())) {//清北复交 A
			return new FirstDegreeResult(MbaConstants.DEGREE_A,MbaConstants.DEGREESCORE.get(MbaConstants.DEGREE_A), null, null, null);
		}
		
		//录取分数线
		SchoolProvinceMajorScoreVO scoreLineVO=schoolDao.getScoreLine(school.getSchool_name(), highSchoolLocation,getMainMajor(major));
		if (scoreLineVO!=null) {
			Integer scoreLine = scoreLineVO.getScore_line()==null?scoreLineVO.getProvince_score_line():scoreLineVO.getScore_line();
			//得分率
			Float scoreRank = (float)scoreLine/getFullScore(highSchoolLocation);
			
			Map<String, Float> locationRank = schoolDao.getLocationRank(highSchoolLocation);
			LinkedHashMap<String, Float> orderLocationRank = new LinkedHashMap<String, Float>(locationRank.size());
			System.out.println("平均录取分数线:"+scoreLine+" 得分率:"+scoreRank+" 级别线:"+net.sf.json.JSONObject.fromObject(locationRank).toString());
			String degreeRank = calcDegreeRank(scoreRank, locationRank,orderLocationRank);
			return new FirstDegreeResult( degreeRank,MbaConstants.DEGREESCORE.get(degreeRank), scoreRank, null,orderLocationRank);
		}else {//没有录取分数线，清北复交为A,学校是985是A-,211是B,其他为C
			return getDegreeResultWhenNoScore(school);
		}
	}
	
	private School getSchool(String schoolName){
		String chineseName=StringHelper.containChinese(schoolName)?StringHelper.getChinese(schoolName):schoolName;
		String englishName=StringHelper.containEnglish(schoolName)?StringHelper.getEnglish(schoolName).toLowerCase():null;
		
		if (chineseName.startsWith("中国科学院")) {
			chineseName="中国科学院大学";
		}
		return schoolDao.getSchool(chineseName,englishName);
	}
	
	private String getMainMajor(String major){
		if (StringUtils.isEmpty(major)) {
			return "";
		}
		String mainMajor=major.trim();
		if (mainMajor.contains("(")) {//截取专业名
			return mainMajor.substring(0, mainMajor.indexOf("("));
		}else if (mainMajor.contains("（")) {
			return mainMajor.substring(0, mainMajor.indexOf("（"));
		}
		return mainMajor;
	}
	
	/**
	 * 得分率缺损时,第一学历级别参照学校级别
	 * 清北复交为A+,985学校是A,211学校是B+,其他为C
	 * 如若是XX大学XX学院,则按照学校级别降两档处理
	 * @return
	 */
	private FirstDegreeResult getDegreeResultWhenNoScore(School school){
		String schoolName = school.getSchool_name();
		String schoolType = school.getSchool_type();
		String degreeRank = MbaConstants.DEGREE_C;
		if (schoolName.matches("^(清华大学|北京大学|复旦大学|上海交通大学).*")) {
			degreeRank=MbaConstants.DEGREE_A;
			
		}else {
			degreeRank = StringUtils.isNotEmpty(schoolType)?(schoolType.contains("985")?MbaConstants.DEGREE_A_MINUS:(schoolType.contains("211")?MbaConstants.DEGREE_B:MbaConstants.DEGREE_C)):MbaConstants.DEGREE_C;
		}
		if (schoolName.matches(".+(大学).*(学院)$")) {//学校级别降两档处理
			if (MbaConstants.DEGREE_C.equals(degreeRank)||MbaConstants.DEGREE_C_MINUS.equals(degreeRank)) {//最低级别是C-
				degreeRank=MbaConstants.DEGREE_C_MINUS;
			}else {
				for (int i = 0; i < MbaConstants.FIRST_DEGREE_RANK.length-2; i++) {
					if (MbaConstants.FIRST_DEGREE_RANK[i].equals(degreeRank)) {
						degreeRank=MbaConstants.FIRST_DEGREE_RANK[i+2];
						break;
					}
					
				}
			}
			
		}
		return new FirstDegreeResult(degreeRank,MbaConstants.DEGREESCORE.get(degreeRank), null, null,null);
	}
	
	private FirstDegreeResult getDegreeResultWhenNoScore(String orgSchoolName){
		String schoolName=StringHelper.containChinese(orgSchoolName)?StringHelper.getChinese(orgSchoolName):orgSchoolName;
		String degreeRank = MbaConstants.DEGREE_C;
		if (schoolName.matches("^(清华大学|北京大学|复旦大学|上海交通大学).*")) {
			degreeRank=MbaConstants.DEGREE_A;
			
		}else if (schoolName.matches(".+(大学).*(学院)$")) {
				School school=getSchool(schoolName.substring(0,schoolName.indexOf("大学")+2));
				if (school!=null) {
					String schoolType = school.getSchool_type();
					degreeRank = StringUtils.isNotEmpty(schoolType)?(schoolType.contains("985")?MbaConstants.DEGREE_A_MINUS:(schoolType.contains("211")?MbaConstants.DEGREE_B:MbaConstants.DEGREE_C)):MbaConstants.DEGREE_C;
				}
			
		}
		if (schoolName.matches(".+(大学).*(学院)$")) {//xx大学xx学院 学校级别降两档处理
			if (MbaConstants.DEGREE_C.equals(degreeRank)||MbaConstants.DEGREE_C_MINUS.equals(degreeRank)) {//最低级别是C-
				degreeRank=MbaConstants.DEGREE_C_MINUS;
			}else {
				for (int i = 0; i < MbaConstants.FIRST_DEGREE_RANK.length-2; i++) {
					if (MbaConstants.FIRST_DEGREE_RANK[i].equals(degreeRank)) {
						degreeRank=MbaConstants.FIRST_DEGREE_RANK[i+2];
						break;
					}
					
				}
			}
			
		}
		return new FirstDegreeResult(degreeRank,MbaConstants.DEGREESCORE.get(degreeRank), null, null,null);
	}
	
	/**
	 * 平均专业水平计算
	 * @param degree 最高学历
	 * @param bachelorSchool 本科学校
	 * @param bachelorMajor 本科专业
	 * @param masterSchool 硕士学校
	 * @param masterMajor 硕士专业
	 * @param doctorSchool 博士学校
	 * @param doctorMajor 博士专业
	 * @param degrees 保存各阶段学历的得分结果
	 * @return
	 */
/*	private Float getMajorScore(String degree, MemberDegree bachelorDegree,MemberDegree masterDegree,MemberDegree doctorDegree,List<MajorScoreResult> degrees) {
		if (MbaConstants.DEGREE_JUNIOR_1.equals(degree)||MbaConstants.DEGREE_JUNIOR.equals(degree)) {
			return null;
		}
		MajorScoreResult bachelorResult = getMajorScore(bachelorDegree);
		bachelorResult.setDegree(MbaConstants.DEGREE_BACHELOR);
		degrees.add(bachelorResult);
		
		Float bachelorScore=bachelorResult.getScore(),
			masterScore=null,
			doctorScore=null;
		if (MbaConstants.DEGREE_MASTER.equals(degree)||MbaConstants.DEGREE_MASTER_1.equals(degree)||MbaConstants.DEGREE_DOCTOR.equals(degree)) {
			MajorScoreResult masterResult = getMajorScore(masterDegree);
			masterResult.setDegree(MbaConstants.DEGREE_MASTER);
			degrees.add(masterResult);
			
			masterScore=masterResult.getScore();
		}
		if (MbaConstants.DEGREE_DOCTOR.equals(degree)) {
			MajorScoreResult doctorResult = getMajorScore(doctorDegree);
			doctorResult.setDegree(MbaConstants.DEGREE_DOCTOR);
			degrees.add(doctorResult);
			
			doctorScore=doctorResult.getScore();
		}

		System.out.println("本科:"+bachelorScore+"硕士:"+masterScore+"博士:"+doctorScore);
		return getAvgMajorScore(degree, bachelorScore, masterScore, doctorScore);
	}*/
	
	/**
	 * 根据得分率获取第一学历级别
	 * @param schoolLevel
	 * @param scoreRank 自己的得分率
	 * @param locationRank
	 * @return
	 */
	private String calcDegreeRank(double scoreRank,Map<String, Float> locationRank,LinkedHashMap<String, Float> orderLocationRank){
		
		String degreeRank=MbaConstants.DEGREE_C_MINUS;
		
		boolean ret = false;
		for (int i = 0; i < MbaConstants.FIRST_DEGREE_RANK.length-1; i++) {
			Float rank=locationRank.get(MbaConstants.FIRST_DEGREE_RANK[i]);// 获取该级别学校录取分数的得分率
			if (rank!=null) {
				if (scoreRank>=rank.floatValue()) {
					if (!ret) {
						degreeRank = MbaConstants.FIRST_DEGREE_RANK[i];
						ret=true;
					}
				}
				
				orderLocationRank.put(MbaConstants.FIRST_DEGREE_RANK[i], rank);
			}
			
		}
		
		return degreeRank;
	}
	
	/**
	 * 获取各地区的高考满分
	 * @param highSchoolLocation
	 * @return
	 */
	private int getFullScore(String highSchoolLocation){
		if (MbaConstants.SHANGHAI.equals(highSchoolLocation)) {
			return MbaConstants.FULL_SCORE_SHANGHAI;
		}
		if (MbaConstants.JIANGSU.equals(highSchoolLocation)) {
			return MbaConstants.FULL_SCORE_JIANGSU;
		}
		if (MbaConstants.ZHEJIANG.equals(highSchoolLocation)) {
			return MbaConstants.FULL_SCORE_ZHEJIANG;
		}
		if (MbaConstants.HAINAN.equals(highSchoolLocation)) {
			return MbaConstants.FULL_SCORE_HAINAN;
		}
		
		return MbaConstants.FULL_SCORE_COMMON;
	}

	/**
	 * 获取学校专业的评估结果
	 * @param schoolName
	 * @param major
	 * @return
	 */
	private MajorScoreResult getMajorScore(MemberDegree degreeInfo){
		
		String schoolName=degreeInfo.getSchool_name(), major=degreeInfo.getMajor(),subject_background=degreeInfo.getSubject_background(), province=degreeInfo.getSchool_location(),learnFormat=degreeInfo.getLearn_format();
		
		String majorRank;
		float majorScore;
		
		School school = null;
		if (StringUtils.isNotEmpty(schoolName)) {
			school = getSchool(schoolName);
		}
		
		if ((school != null && MbaConstants.SCHOOL_OUT==school.getInout())||(StringUtils.isNotEmpty(province)&&province.contains("其他国家"))||(StringUtils.isNotEmpty(learnFormat)&&learnFormat.contains(MbaConstants.LEARN_FORMAT_OUT))) {
			if (StringUtils.isEmpty(schoolName)) {
				return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major,subject_background, MbaConstants.MAJOR_B_MINUS, MbaConstants.MAJOR_B_MINUS_SCORE,degreeInfo.getLearn_format());
			}
			//国外学校按照学校排名
			if (school != null && MbaConstants.SCHOOL_OUT==school.getInout()) {//国外学校按照全球排名
				if (school.getRanking()!=null) {
					if (school.getRanking()>=1&school.getRanking()<=50) {
						return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major,subject_background, MbaConstants.MAJOR_A_PLUS, MbaConstants.MAJOR_A_PLUS_SCORE,degreeInfo.getLearn_format());
					}else if (school.getRanking()>=51&school.getRanking()<=100) {
						return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major,subject_background, MbaConstants.MAJOR_A, MbaConstants.MAJOR_A_SCORE,degreeInfo.getLearn_format());
					}else if (school.getRanking()>=101&school.getRanking()<=150) {
						return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major,subject_background, MbaConstants.MAJOR_A_MINUS, MbaConstants.MAJOR_A_MINUS_SCORE,degreeInfo.getLearn_format());
					}else if (school.getRanking()>=151&school.getRanking()<=200) {
						return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major,subject_background, MbaConstants.MAJOR_B_PLUS, MbaConstants.MAJOR_B_PLUS_SCORE,degreeInfo.getLearn_format());
					}else if (school.getRanking()>=201&school.getRanking()<=300) {
						return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major,subject_background, MbaConstants.MAJOR_B, MbaConstants.MAJOR_B_SCORE,degreeInfo.getLearn_format());
					}else {
						return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major,subject_background, MbaConstants.MAJOR_B_MINUS, MbaConstants.MAJOR_B_MINUS_SCORE,degreeInfo.getLearn_format());
					}
				}
			}
			
			return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major,subject_background, MbaConstants.MAJOR_B_MINUS, MbaConstants.MAJOR_B_MINUS_SCORE,degreeInfo.getLearn_format());
		}
		if (StringUtils.isEmpty(schoolName)||StringUtils.isEmpty(major)) {
			return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major,subject_background, MbaConstants.MAJOR_D, MbaConstants.MAJOR_D_SCORE,degreeInfo.getLearn_format());
		}
		
		String chineseName=StringHelper.containChinese(schoolName)?StringHelper.getChinese(schoolName):schoolName;
		if (chineseName.startsWith("中国科学院")) {
			chineseName="中国科学院大学";
		}
		
		majorRank=schoolDao.getMajorRank(chineseName, subject_background);
		if (StringUtils.isNotEmpty(majorRank)) {//获取专业评估结果
			majorScore= MbaConstants.MAJORSCORE.get(majorRank);
			return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major,subject_background, majorRank, majorScore,degreeInfo.getLearn_format());
		} else {//没有获取专业评估结果 D
			return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major,subject_background, MbaConstants.MAJOR_D, MbaConstants.MAJOR_D_SCORE,degreeInfo.getLearn_format());
			
			//如果没有学校名 专业对应的评级，则只看该专业是否存在评级
			/*String onlyMajorRank=schoolDao.getMajorRank(null, getMainMajor(major));
			if (StringUtils.isNotEmpty(onlyMajorRank)) {//若该专业有参与过评级，评估结果 D
				return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major, MbaConstants.MAJOR_D, MbaConstants.MAJOR_D_SCORE,degreeInfo.getLearn_format());
			}else {//若该专业没有参与过评级，则给E,分数按最低分D级算
				return new MajorScoreResult(degreeInfo.getDegree(), schoolName, major, MbaConstants.MAJOR_E, MbaConstants.MAJOR_E_SCORE,degreeInfo.getLearn_format());
			}*/
		}
		
	}
	
	/**
	 * 平均专业水平计算
	 * @param degree 学历:本科 博士 硕士
	 * @param bachelorScore 本科学校专业得分
	 * @param masterScore 博士学校专业得分
	 * @param doctorScore 硕士学校专业得分
	 * @return
	 */
/*	private Float getAvgMajorScore(String degree,Float bachelorScore,Float masterScore,Float doctorScore){
		if (MbaConstants.DEGREE_UNDERGRADUATE.equals(degree)||MbaConstants.DEGREE_BACHELOR.equals(degree)) {//本科学历，最终得分=本科得分*1.0；
			return bachelorScore;
		} else if (MbaConstants.DEGREE_MASTER.equals(degree)||MbaConstants.DEGREE_MASTER_1.equals(degree)) {//硕士学历，最终得分=本科得分*0.4 +硕士得分*0.6；
			if (bachelorScore!=null&&masterScore!=null) {
				return (float) (bachelorScore*0.4+masterScore*0.6);
			}else {
				return null;
			}
			
		}else if (MbaConstants.DEGREE_DOCTOR.equals(degree)) {//博士学历，最终得分=本科得分*0.3 +硕士得分*0.3+博士得分*0.4
			if (bachelorScore!=null&&masterScore!=null&&doctorScore!=null) {
				return (float) (bachelorScore*0.3+masterScore*0.3+doctorScore*0.4);
			}else {
				return null;
			}
			
		}
		return null;
	}*/

/*	private WebInfo getWorkInfo(Member member){
		
		String name=member.getName(), company=member.getCompany();
		Integer workers=member.getCompany_num();
		Float years=member.getWork_time();
		
		String httpUrl=("http://"+MbaConstants.HTTP_IP+":"+MbaConstants.HTTP_PORT+"/search/?name="+name+"&company="+company+"&workers="+workers+"&years="+years).replaceAll(" ", "%20");
		String companyHttpUrl=("http://"+MbaConstants.HTTP_IP+":"+MbaConstants.HTTP_PORT+"/search/?company="+company+"&workers="+workers+"&years="+years).replaceAll(" ", "%20");
		System.out.println("请求"+httpUrl);
		WorkBackground workInfo = null;
		WorkBackground companyInfo = null;
		
		try {
			String httpResult=Gaokaopai.requestByGetMethod(httpUrl);
			String companyHttpResult=Gaokaopai.requestByGetMethod(companyHttpUrl);
			
			if (StringUtils.isNotEmpty(httpResult)) {
				workInfo = JSONObject.parseObject(httpResult, WorkBackground.class);
			}
			
			if (StringUtils.isNotEmpty(companyHttpResult)) {
				companyInfo = JSONObject.parseObject(companyHttpResult, WorkBackground.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//公司级别:A A- B+ B B-
		//经贸委、招商局、高新区/开发区管委会 升一档
		//大学、学院 委员会、厅、局、处 降一档
		//学校、中学、小学 银行分行支行、营业部、储蓄所 降二档
		String addOneReg=".*(经贸委|招商局|高新区管委会|开发区管委会)$";
		String oneReg=".*(大学|学院|委员会|厅|局|处|分行)$";
		String twoReg=".*(学校|中学|小学|支行|营业部|储蓄所)$";
		if (companyInfo==null) {
			companyInfo=new WorkBackground();
			companyInfo.setTotalcount(MbaConstants.DEGREE_B_MINUS);
		}else {
			String companyLevel=companyInfo.getTotalcount();
			System.out.println("公司级别:"+companyLevel);
			if (!MbaConstants.DEGREE_A.equals(companyLevel)&&company.matches(addOneReg)) {//升一档
				for (int i = 4; (i>0 &&i < MbaConstants.COMPANY_DEGREE_RANK.length); i--) {
					if (MbaConstants.COMPANY_DEGREE_RANK[i].equals(companyLevel)) {
						companyLevel=MbaConstants.COMPANY_DEGREE_RANK[i-1];
						break;
					}
				}
				
			}else if (!MbaConstants.DEGREE_B_MINUS.equals(companyLevel)) {//降级处理,最低B-
				if (company.matches(oneReg)) {//降1级
					for (int i = 0; i < MbaConstants.COMPANY_DEGREE_RANK.length-1; i++) {
						if (MbaConstants.COMPANY_DEGREE_RANK[i].equals(companyLevel)) {
							companyLevel=MbaConstants.COMPANY_DEGREE_RANK[i+1];
							break;
						}
					}
				}else if (company.matches(twoReg)) {//降2级
					if (MbaConstants.DEGREE_B.equals(companyLevel)) {
						companyLevel=MbaConstants.DEGREE_B_MINUS;
					}else {
						for (int i = 0; i < MbaConstants.COMPANY_DEGREE_RANK.length-2; i++) {
							if (MbaConstants.COMPANY_DEGREE_RANK[i].equals(companyLevel)) {
								companyLevel=MbaConstants.COMPANY_DEGREE_RANK[i+2];
								break;
							}
						}
					}
				}
			}
			
			
			System.out.println("公司级别:"+companyLevel);
			companyInfo.setTotalcount(companyLevel);
		}

		return new WebInfo(workInfo, companyInfo);
	}*/
	
	private WebInfo getWorkInfo(String memberNo){
		
		WorkBackground workWebInfo = new WorkBackground();
		WorkBackground companyWebInfo = new WorkBackground();
		
		WebInfo webInfo=new WebInfo(workWebInfo, companyWebInfo);
		
		CompanyInfo companyInfo=schoolDao.getCompanyInfo(memberNo);
		if (companyInfo==null) {
			companyWebInfo.setTotalcount(MbaConstants.DEGREE_D_MINUS);
			
		}else {
			webInfo.setShixin(companyInfo.getShixin());
			workWebInfo.setTotalcount(companyInfo.getMember_s_count());
			workWebInfo.setResult(schoolDao.getWebInfo(memberNo, 2));
			
			companyWebInfo.setTotalcount(companyInfo.getCompany_s_rank());
			companyWebInfo.setResult(schoolDao.getWebInfo(memberNo, 1));
			
			/*//公司级别:A A- B+ B B-
			//经贸委、招商局、高新区/开发区管委会 升一档
			//大学、学院 委员会、厅、局、处 降一档
			//学校、中学、小学 银行分行支行、营业部、储蓄所 降二档
			String addOneReg=".*(经贸委|招商局|高新区管委会|开发区管委会)$";
			String oneReg=".*(大学|学院|委员会|厅|局|处|分行)$";
			String twoReg=".*(学校|中学|小学|支行|营业部|储蓄所)$";
			String company=companyInfo.getCompany_name();
			String companyLevel=companyWebInfo.getTotalcount();
			System.out.println("公司级别:"+companyLevel);
			if (!MbaConstants.DEGREE_A.equals(companyLevel)&&company.matches(addOneReg)) {//升一档
				for (int i = 4; (i>0 &&i < MbaConstants.COMPANY_DEGREE_RANK.length); i--) {
					if (MbaConstants.COMPANY_DEGREE_RANK[i].equals(companyLevel)) {
						companyLevel=MbaConstants.COMPANY_DEGREE_RANK[i-1];
						break;
					}
				}
				
			}else if (!MbaConstants.DEGREE_B_MINUS.equals(companyLevel)) {//降级处理,最低B-
				if (company.matches(oneReg)) {//降1级
					for (int i = 0; i < MbaConstants.COMPANY_DEGREE_RANK.length-1; i++) {
						if (MbaConstants.COMPANY_DEGREE_RANK[i].equals(companyLevel)) {
							companyLevel=MbaConstants.COMPANY_DEGREE_RANK[i+1];
							break;
						}
					}
				}else if (company.matches(twoReg)) {//降2级
					if (MbaConstants.DEGREE_B.equals(companyLevel)) {
						companyLevel=MbaConstants.DEGREE_B_MINUS;
					}else {
						for (int i = 0; i < MbaConstants.COMPANY_DEGREE_RANK.length-2; i++) {
							if (MbaConstants.COMPANY_DEGREE_RANK[i].equals(companyLevel)) {
								companyLevel=MbaConstants.COMPANY_DEGREE_RANK[i+2];
								break;
							}
						}
					}
				}
			}
				
				
			System.out.println("公司级别:"+companyLevel);
			companyWebInfo.setTotalcount(companyLevel);*/
		}
		


		return webInfo;
	}
	
	private int calCertificateScore(List<MemberCertificate> certificates){
		if (certificates!=null&&certificates.size()>0) {
			if (certificates.stream().filter(a -> a.isCalculate()).collect(Collectors.toList()).size()>0) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public Map<Member,MemberResult> getList(Integer pagenum,String memberNo,String name) {
		List<Member> members = schoolDao.getMemberList(pagenum,memberNo,name);
		Map<Member,MemberResult> ret=new LinkedHashMap<Member, MemberResult>();
		for (Member member : members) {
			MemberResult scoResult=getResult1(member.getMember_serialno());
			ret.put(member, scoResult);
		}
		return ret;
	}

	@Override
	public int getMemberCount(String memberNo,String name) {
		return schoolDao.getMemberCount(memberNo,name);
	}

	@Override
	public List<Member> getList() {
		return schoolDao.getMemberList(null,null,null);
	}
	
	private float formatFloat(float data){
		DecimalFormat decimalFormat=new DecimalFormat(".0");//构造方法的字符格式这里如果小数不足1位,会以0补足.
		return Float.valueOf(decimalFormat.format(data));
	}

	@Transactional
	@Override
	public int saveAppData(AppData data) {
		if (schoolDao.getMember(data.getReferenceNo())!=null) {
			return 2;
		}
		try{
			data.setWorkExperience(String.valueOf(Float.valueOf(data.getWorkExperience())));
		}catch(Exception e) {
			data.setWorkExperience(null);
		}
		
		try{
			data.setManagementExperience(String.valueOf(Float.valueOf(data.getManagementExperience())));
		}catch(Exception e) {
			data.setManagementExperience(null);
		}
		
		try{
			data.setAnnualSalary(String.valueOf(Float.valueOf(data.getAnnualSalary())));
		}catch(Exception e) {
			data.setAnnualSalary(null);
		}
		
		try{
			data.setNumberOfEmployees(String.valueOf(Integer.valueOf(data.getNumberOfEmployees())));
		}catch(Exception e) {
			data.setNumberOfEmployees(null);
		}
		
		try{
			data.setSubordinateNumber(String.valueOf(Integer.valueOf(data.getSubordinateNumber())));
		}catch(Exception e) {
			data.setSubordinateNumber(null);
		}
		
		try {
			data.setIeltsTotalScore(String.valueOf(Float.valueOf(data.getIeltsTotalScore())));
		} catch (Exception e) {
			data.setIeltsTotalScore(null);
		}
		
		try {
			data.setToeflTotalScore(String.valueOf(Float.valueOf(data.getToeflTotalScore())));
		} catch (Exception e) {
			data.setToeflTotalScore(null);
		}
		
		try {
			data.setGmatTotalScore(String.valueOf(Float.valueOf(data.getGmatTotalScore())));
		} catch (Exception e) {
			data.setGmatTotalScore(null);
		}
		
		try {
			data.setGreTotalScore(String.valueOf(Float.valueOf(data.getGreTotalScore())));
		} catch (Exception e) {
			data.setGreTotalScore(null);
		}
		
		if (data.getEducations()!=null&&data.getEducations().size()>0) {
			for (Education education : data.getEducations()) {
				try{
					education.setGpa(String.valueOf(Float.valueOf(education.getGpa())));
				}catch(Exception e) {
					education.setGpa(null);
				}
			}
		}
		
		schoolDao.saveMember(data);
		if (data.getEducations()!=null&&data.getEducations().size()>0) {
			schoolDao.saveDegrees(data.getReferenceNo(), data.getEducations());
		}
		if (data.getCertificates()!=null&&data.getCertificates().size()>0) {
			schoolDao.saveCertificates(data.getReferenceNo(), data.getCertificates());
		}
		
		if (data.getHonorAwards()!=null&&data.getHonorAwards().size()>0) {
			schoolDao.saveHonors(data.getReferenceNo(), data.getHonorAwards());
		}
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("iden", StringUtils.isNotEmpty(data.getPassportNo())?data.getPassportNo():"");
		jsonObject.put("member_serial", StringUtils.isNotEmpty(data.getReferenceNo())?data.getReferenceNo():"");
		jsonObject.put("name", StringUtils.isNotEmpty(data.getName())?data.getName():"");
		jsonObject.put("company", StringUtils.isNotEmpty(data.getCompany())?data.getCompany():"");
		jsonObject.put("unit_character", StringUtils.isNotEmpty(data.getCompanyNature())?data.getCompanyNature():"");
		jsonObject.put("unit_classification", StringUtils.isNotEmpty(data.getCompanyIndustry())?data.getCompanyIndustry():"");
		jsonObject.put("total_assets", StringUtils.isNotEmpty(data.getAsset())?data.getAsset():"0");
		jsonObject.put("annual_income", StringUtils.isNotEmpty(data.getAnnualSales())?data.getAnnualSales():"0");
		jsonObject.put("member_count", StringUtils.isNotEmpty(data.getNumberOfEmployees())?data.getNumberOfEmployees():"0");

		try {
			//192.168.1.55
			String httpUrl = "http://"+MbaConstants.HTTP_IP+":"+MbaConstants.HTTP_PORT+"/cmpsearch?data="+URLEncoder.encode(jsonObject.toString(SerializerFeature.WriteMapNullValue),"UTF-8");
			
			System.out.println(httpUrl);
			String ret=Gaokaopai.requestByGetMethod(httpUrl);
			System.out.println(ret);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public void apiResult(MemberResult result,JSONObject ret){
		JSONObject expMsg=new JSONObject();
		float majorScore=result.getMajorScore();
		float firstDegreeScore=result.getFirstDegrees().getDegreeScore();
		JSONObject detail = new JSONObject();
		detail.put("degree", majorScore);
		detail.put("university", firstDegreeScore);
		detail.put("g_i_t", result.getInterExam());
		detail.put("company", result.getCompanyScore());
		detail.put("work_exp", result.getWorkExpScore());
		detail.put("manager_exp", result.getManageExpScore());
		detail.put("title", result.getJobScore());
		detail.put("training", result.getCertificateScore());
		
		detail.put("honor", result.getHonorScore());
		 
		ret.put("result", detail);
		 
		System.out.println(Math.abs(majorScore/10-firstDegreeScore/16));
		if (majorScore<5&&Math.abs(majorScore/10-firstDegreeScore/16)>=0.2) {
			expMsg.put("degreeExpMsg", "学位学历与第一学历得分差距较大，请多加留意。");
		}
		if (result.getCompanyScore()==0) {
			expMsg.put("companyExpMsg", "公司情况得分为零，请多加留意。");
		}
		 
		if (result.getWorkInfo().getShixin()!=null&&result.getWorkInfo().getShixin()==1) {
			expMsg.put("dishonestExpMsg", "该人员疑有失信记录，请多加留意。");
		}
		if (result.getJobScore()==0) {
			expMsg.put("jobExpMsg", "职务得分为零，请多加留意。");
		}
		 
		ret.put("expMsg", expMsg);
	}
	
	public static void main(String[] args) {
		
		/*JSONObject jsonObject=new JSONObject();
		jsonObject.put("member_serial", "11");
		jsonObject.put("name", "钱学胜");
		jsonObject.put("company","复旦大学");
		jsonObject.put("unit_character", "22");
		jsonObject.put("unit_classification", "33");
		jsonObject.put("total_assets", "44");
		jsonObject.put("annual_income", "55");
		jsonObject.put("member_count", null);
		System.out.println(jsonObject.toJSONString());
		System.out.println(jsonObject.toString(SerializerFeature.WriteMapNullValue));*/
		
		volidTestDate("2015-02", 5);
		
		

	}
}
