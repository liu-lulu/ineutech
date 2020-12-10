package com.kkbc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 设置日期
	 * @param year  年
	 * @param month 月
	 * @param day 日
	 * @param hour 时
	 * @param minute 分
	 * @param second 秒
	 * @return
	 */
	public static Date setDate(int year,int month,int day,int hour,int minute,int second){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);      //年
		cal.set(Calendar.MONTH,month-1);//月
        cal.set(Calendar.DATE,day);       //日
        
        
        cal.set(Calendar.HOUR_OF_DAY,hour);//时
        cal.set(Calendar.MINUTE,minute);//分
        cal.set(Calendar.SECOND,second);//秒
        cal.set(Calendar.MILLISECOND,0);//毫秒
        return cal.getTime();
	}
	
	public static String currentDateStringByNWX(){
		Date NowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		return formatter.format(NowDate);
	}
	
	public static Date getDateStringByNWX(String str){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return formatter.parse("20"+str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date getDayBegin(Date currDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getDayEnd(Date currDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	
	public static Date parseDateTime(String sDateTime) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		try {
			Date date = bartDateFormat.parse(sDateTime);
			return date;
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
		return null;
	}
	
	public static Date parseToDate(String sDateTime) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		try {
			Date date = bartDateFormat.parse(sDateTime);
			return date;
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(setDate(2000, 12, 1, 23, 20, 70));
//		int year = cal.get(Calendar.YEAR);      //年
//		int month = cal.get(Calendar.MONTH) + 1;//月
//        int day = cal.get(Calendar.DATE);       //日
//        
//        
//        int hour=cal.get(Calendar.HOUR_OF_DAY);//时
//        int minute=cal.get(Calendar.MINUTE);//分
//        int second=cal.get(Calendar.SECOND);//秒
//        int millisecond=cal.get(Calendar.MILLISECOND);//毫秒
//
//        System.out.println("Date: " + cal.getTime());
//        System.out.println("年: " + year);
//        System.out.println("月: " + month);
//        System.out.println("日: " + day);
//        
//        System.out.println("时: " + hour);
//        System.out.println("分: " + minute);
//        System.out.println("秒: " + second);
//        System.out.println("毫秒: " + millisecond);     
		System.out.println("时间: " + currentDateStringByNWX()); 
		
		System.out.println("时间: " + getDateStringByNWX("160524173242")); 
		Date d=getDateStringByNWX("160524173242");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println("时间2: " + formatter.format(d)); 
	}
	
	

}
