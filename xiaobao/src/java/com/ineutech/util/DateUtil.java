package com.ineutech.util;

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
	
	public static Date getDate(String sDateTime) {
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
	
	//获取本周的周一日期
	public static String getMondy(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if (isMondy(date)) {
			return sdf.format(getLastWeekMonday(date));
		}
		
		Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayWeek) {  
           cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期  
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        cal.setFirstDayOfWeek(Calendar.MONDAY);  
        // 获得当前日期是一个星期的第几天  
        int day = cal.get(Calendar.DAY_OF_WEEK);  
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);  
        String imptimeBegin = sdf.format(cal.getTime());  
        // System.out.println("所在周星期一的日期：" + imptimeBegin);  
        cal.add(Calendar.DATE, 6);  
        String imptimeEnd = sdf.format(cal.getTime());  
        // System.out.println("所在周星期日的日期：" + imptimeEnd);  
        return imptimeBegin;  
	}
	
	public static Date getLastWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getThisWeekMonday(date));
		cal.add(Calendar.DATE, -7);
		return cal.getTime();
	}

	public static Date getThisWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}


	
	public static boolean isMondy(Date date){
		
		Calendar cal=Calendar.getInstance();
		cal.setTime(date); 
		int week=cal.get(Calendar.DAY_OF_WEEK)-1;
		if (week==1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取当月1号
	 * @return
	 */
	public static Date getFirstDayCurMonth(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	public static void main(String[] args) throws ParseException {
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
		String date="2019-12-16";
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		System.out.println(getMondy(bartDateFormat.parse(date)));
		
		System.out.println(getFirstDayCurMonth());

	}
	
	

}
