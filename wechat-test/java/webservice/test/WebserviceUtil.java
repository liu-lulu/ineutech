package webservice.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class WebserviceUtil {
	
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
	public Date setDate(int year,int month,int day,int hour,int minute,int second){
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
	
	public String currentDateStringByNWX(){
		Date NowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		return formatter.format(NowDate);
	}
	
	public Date getDateStringByNWX(String str){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return formatter.parse("20"+str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Date getDayBegin(Date currDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public Date getDayEnd(Date currDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	
	public Date parseDateTime(String sDateTime) {
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

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/helloWord",new WebserviceUtil());
	}
	
	

}
