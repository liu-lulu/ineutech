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
	
	public static Date stringToDate(String date) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(date);
	}
	
	public static int differentDaysByMillisecond(Date date1,Date date2){
		return (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
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
	
	public static boolean dayEqual(Date date1,Date date2) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (formatter.format(date1).equals(formatter.format(date2))) {
			return true;
		}
		return false;
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
	
	public static void main(String[] args) throws ParseException {
		System.out.println(differentDaysByMillisecond(stringToDate("2017-12-01"),stringToDate("2017-12-07")));
	}

}
