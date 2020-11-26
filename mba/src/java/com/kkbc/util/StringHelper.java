/**
 * StringHelper.java
 * com.xyy.util
 * author      date      	
 * ──────────────────────────────────
 * xiao    2015年4月22日 		
 * Copyright (c)2015, All Rights Reserved.
 * Java源代码,未经许可禁止任何人、任何组织通过任何
 * 渠道使用、修改源代码.
 */
package com.kkbc.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:StringHelper
 *
 * TODO(个人工具类)
 *
 * @project HttpServlet
 *
 * @author xiao
 *
 * @date 2015年4月22日 上午10:03:44
 *
 * @class com.xyy.util.StringHelper
 *
 */
public final class StringHelper {
	
	public static boolean containEnglish(String str){
		
		return str.matches(".*[a-zA-z].*");
	}
	public static boolean containChinese(String str) {
		return str.matches(".*[\u4e00-\u9fa5].*");
	}
	public static void main(String[] args) {
		String a="addb大 哈 hjh";
		if (StringHelper.containChinese(a)) {
			System.out.println("--"+StringHelper.getChinese(a));
		}else {
			System.out.println("不包含");
		}
	}
	public static String getEnglish(String str){
		
		int startIndex=-1;
		int endIndex=str.length()-1;
		
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, i+1).matches("[a-zA-z]")) {
				startIndex=i;
				break;
			}
		}
		
		for (int i = str.length(); i >=0; i--) {
			if (str.substring(i-1, i).matches("[a-zA-z]")) {
				endIndex=i;
				break;
			}
		}
		
		return str.substring(startIndex,endIndex);
	}
	
	public static String getChinese(String str){
		
		int startIndex=-1;
		int endIndex=str.length()-1;
		
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, i+1).matches("[\u4e00-\u9fa5]")) {
				startIndex=i;
				break;
			}
		}
		
		for (int i = str.length(); i >=0; i--) {
			if ("）".equals(str.substring(i-1, i))||")".equals(str.substring(i-1, i))||str.substring(i-1, i).matches("[\u4e00-\u9fa5]")) {
				endIndex=i;
				break;
			}
		}
		
		return str.substring(startIndex,endIndex);
	}

	/**
	 * TODO(发送接口是否成功)
	 * 
	 * @param type
	 * @return
	 */
	public static String isSuccByType(boolean type) {
		if (type) {
			return "success";
		} else {
			return "error";
		}
	}

	public static boolean isChinese(String str) {
		String strTemp = null;
		try {
			strTemp = new String(str.getBytes("ISO-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 如果值为空，通过校验
		if ("".equals(str))
			return true;
		Pattern p = Pattern.compile("/[^\u4E00-\u9FA5]/g,''");
		Matcher m = p.matcher(strTemp);
		return m.matches();
	}

	public static boolean isEmptyObject(Object obj) {
		if (StringHelper.toString(obj).equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static String toString(Object obj) {
		if (obj == null || "".equals(obj.toString())
				|| "null".equals(obj.toString())) {
			return "";
		} else {
			String objValue = obj.toString().trim();
			return objValue;
		}
	}

	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}
	
	public static int getAge(String start){
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String stop =  sim.format(new Date());
        int age = (Integer.parseInt(stop.substring(0,4)) - Integer.parseInt(start.substring(0,4))+1);
        System.out.println(age + "岁");
        return age;
	}
	
	public static int getAge(String start,Date endTime){
        try {
        	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
            String stop =  sim.format(endTime);
            int age = (Integer.parseInt(stop.substring(0,4)) - Integer.parseInt(start.substring(0,4))+1);
            return age;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return -1;
	}
	
	public static String getWeekDay(){
		int weekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		System.out.println(weekDay);
		String weekDay1 = String.valueOf(weekDay);
		return weekDay1;
	}
	
}
