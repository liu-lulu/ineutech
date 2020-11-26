package com.kkbc.commom;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;




public class MbaConstants {
	
	//国内学校
	public static final int SCHOOL_IN=1;
	//国外学校
	public static final int SCHOOL_OUT=2;
	
	public static final int TYPE_1=1;
	public static final int TYPE_2=2;
	
	//清北复交
	public static final String SCHOOL_QBFJ="^(清华大学|北京大学|复旦大学|上海交通大学).*";
	
	public static final String LEARN_FORMAT_NORMAL="^(普通全日制).*";
	public static final String LEARN_FORMAT_SELFEXAM="^(自学考试).*";
	public static final String LEARN_FORMAT_OUT="境外";
//	public static final String LEARN_FORMAT_OTHER="^(其他).*";
	
	public static final String[] COMPANY_DEGREE_RANK={"A","A-","B+","B","B-"};
	
	public static final String[] FIRST_DEGREE_RANK={"A","A-","B","B-","C","C-"};
	
	public static final Map<String, Float> DEGREESCORE= new HashMap<String, Float>();
//	public static final Map<String, Float> DEGREESCORE_OUT= new HashMap<String, Float>();
	public static final String DEGREE_A_PLUS = "A+";
	public static final String DEGREE_A = "A";
	public static final String DEGREE_A_MINUS = "A-";
	public static final String DEGREE_B_PLUS = "B+";
	public static final String DEGREE_B = "B";
	public static final String DEGREE_B_MINUS = "B-";
	public static final String DEGREE_C = "C";
	public static final String DEGREE_C_MINUS = "C-";
	
	public static final String DEGREE_C_PLUS = "C+";
	public static final String DEGREE_D_PLUS = "D+";
	public static final String DEGREE_D = "D";
	public static final String DEGREE_D_MINUS = "D-";
	
	public static final double DEGREE_A_PLUS_RANK = 0.85;
	
	public static final int FULL_SCORE_SHANGHAI = 660;
	public static final int FULL_SCORE_JIANGSU = 480;
	public static final int FULL_SCORE_HAINAN = 900;
	public static final int FULL_SCORE_ZHEJIANG = 810;
	public static final int FULL_SCORE_COMMON = 750;
	
	public static final String SHANGHAI = "上海";
	public static final String JIANGSU = "江苏";
	public static final String HAINAN = "海南";
	public static final String ZHEJIANG = "浙江";
	
	public static final String DEGREE_WU="无";
	public static final String DEGREE_JUNIOR = "大专";
	public static final String DEGREE_JUNIOR_1 = "专科";
	
	public static final String DEGREE_UNDERGRADUATE = "本科";
	public static final String DEGREE_BACHELOR = "学士";
	
	public static final String DEGREE_MASTER = "硕士";
	public static final String DEGREE_MASTER_1 = "研究生";
	public static final String DEGREE_DOCTOR = "博士";
	
	public static final Map<String, Float> MAJORSCORE= new HashMap<String, Float>();
	public static final String MAJOR_A_PLUS = "A+";
	public static final String MAJOR_A = "A";
	public static final String MAJOR_A_MINUS = "A-";
	public static final String MAJOR_B_PLUS = "B+";
	public static final String MAJOR_B = "B";
	public static final String MAJOR_B_MINUS = "B-";
	public static final String MAJOR_C_PLUS = "C+";
	public static final String MAJOR_C = "C";
	public static final String MAJOR_C_MINUS = "C-";
	public static final String MAJOR_D="D";
	public static final String MAJOR_E="E";
	
	public static final Float MAJOR_A_PLUS_SCORE = 4.0f;
	public static final Float MAJOR_A_SCORE = 3.7f;
	public static final Float MAJOR_A_MINUS_SCORE = 3.3f;
	public static final Float MAJOR_B_PLUS_SCORE = 3.0f;
	public static final Float MAJOR_B_SCORE = 2.7f;
	public static final Float MAJOR_B_MINUS_SCORE = 2.3f;
	public static final Float MAJOR_C_PLUS_SCORE = 2.0f;
	public static final Float MAJOR_C_SCORE = 1.7f;
	public static final Float MAJOR_C_MINUS_SCORE = 1.3f;
	public static final Float MAJOR_D_SCORE = 1.0f;
	public static final Float MAJOR_E_SCORE = 0.5f;
	
	public static final int PAGE_SIZE=5;
	
	public static final Map<String, Float> COMPANY_DEGREE_SCORE= new HashMap<String, Float>();
	
	public static String HTTP_IP ="192.168.1.96";
	public static String HTTP_PORT ="8099";
	static{
		DEGREESCORE.put(DEGREE_A, 4.0f);
		DEGREESCORE.put(DEGREE_A_MINUS, 3.7f);
		DEGREESCORE.put(DEGREE_B, 3.0f);
		DEGREESCORE.put(DEGREE_B_MINUS, 2.7f);
		DEGREESCORE.put(DEGREE_C, 2.0f);
		DEGREESCORE.put(DEGREE_C_MINUS, 1.7f);
		
	/*	DEGREESCORE_OUT.put(DEGREE_A, 4.0f);
		DEGREESCORE_OUT.put(DEGREE_A_MINUS, 3.7f);
		DEGREESCORE_OUT.put(DEGREE_B_PLUS, 3.3f);
		DEGREESCORE_OUT.put(DEGREE_B, 3.0f);
		DEGREESCORE_OUT.put(DEGREE_B_MINUS, 2.0f);*/
		
		MAJORSCORE.put(MAJOR_A_PLUS, MAJOR_A_PLUS_SCORE);
		MAJORSCORE.put(MAJOR_A, MAJOR_A_SCORE);
		MAJORSCORE.put(MAJOR_A_MINUS, MAJOR_A_MINUS_SCORE);
		MAJORSCORE.put(MAJOR_B_PLUS, MAJOR_B_PLUS_SCORE);
		MAJORSCORE.put(MAJOR_B, MAJOR_B_SCORE);
		MAJORSCORE.put(MAJOR_B_MINUS, MAJOR_B_MINUS_SCORE);
		MAJORSCORE.put(MAJOR_C_PLUS, MAJOR_C_PLUS_SCORE);
		MAJORSCORE.put(MAJOR_C, MAJOR_C_SCORE);
		MAJORSCORE.put(MAJOR_C_MINUS, MAJOR_C_MINUS_SCORE);
		MAJORSCORE.put(MAJOR_D, MAJOR_D_SCORE);
		MAJORSCORE.put(MAJOR_E, MAJOR_E_SCORE);
		
		
		COMPANY_DEGREE_SCORE.put(DEGREE_A, 10f);
		COMPANY_DEGREE_SCORE.put(DEGREE_A_MINUS, 9f);
		COMPANY_DEGREE_SCORE.put(DEGREE_B_PLUS, 8f);
		COMPANY_DEGREE_SCORE.put(DEGREE_B, 7f);
		COMPANY_DEGREE_SCORE.put(DEGREE_B_MINUS, 6f);
		COMPANY_DEGREE_SCORE.put(DEGREE_C_PLUS, 5f);
		COMPANY_DEGREE_SCORE.put(DEGREE_C, 4f);
		COMPANY_DEGREE_SCORE.put(DEGREE_C_MINUS, 3f);
		COMPANY_DEGREE_SCORE.put(DEGREE_D_PLUS, 2f);
		COMPANY_DEGREE_SCORE.put(DEGREE_D, 1f);
		COMPANY_DEGREE_SCORE.put(DEGREE_D_MINUS, 0f);
		
		Properties properties = new Properties();
		try {
			properties.load(MbaConstants.class.getClassLoader().getResourceAsStream("http.properties"));
			HTTP_IP = properties.getProperty("http.ip", "192.168.1.96");
			HTTP_PORT=properties.getProperty("http.port", "8099");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	

}
