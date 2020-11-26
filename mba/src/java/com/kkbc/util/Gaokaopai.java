package com.kkbc.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.kkbc.entity.MajorScore;
import com.kkbc.entity.School;
import com.kkbc.entity.Score;

public class Gaokaopai {
	public static final int DEF_CONN_TIMEOUT = 30000;

	    public static final int DEF_READ_TIMEOUT = 30000;

	    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	
	static String[] cnames={"11||北京 ","12||天津","13||河北","14||山西","15||内蒙古","21||辽宁","22||吉林","23||黑龙江","31||上海","32||江苏","33||浙江","34||安徽","35||福建","36||江西","37||山东","41||河南","42||湖北","43||湖南","44||广东","45||广西","46||海南","50||重庆","51||四川","52||贵州","53||云南","54||西藏","61||陕西","62||甘肃","63||青海","64||宁夏","65||新疆"};
	static String[] kms={"1||文科","2||理科"};
	static String[] years={"2016","2015","2014","2013"};
	
	public static String requestByPostMethod1(String url,String cname,String km,String year) {
		Map<String,String> map = new HashMap<>();
		map.put("cname",cname);
		map.put("km",km);
		map.put("year",year);
		map.put("timestamp",new Date().toString());

		HttpClient httpClient = new DefaultHttpClient();
		StringBuilder entityStringBuilder = new StringBuilder();
		HttpPost post = null;
		try {
			post = new HttpPost(url);
			//设置参数
	        List<NameValuePair> list = new ArrayList<NameValuePair>();
	        Iterator iterator = map.entrySet().iterator();
	        while(iterator.hasNext()){
	            Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
	            list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
	        }
	        if(list.size() > 0){
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");
	            post.setEntity(entity);
	        }
		HttpResponse httpResponse = httpClient.execute(post);
		HttpEntity entity = httpResponse.getEntity();
		if (null != entity) {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(entity.getContent(), "UTF-8"), 8 * 1024);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				entityStringBuilder.append(line);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return entityStringBuilder.toString();
	}
	
	public static String requestByPostMethod(String url,Map<String,String> map) {
		
		map.put("timestamp",new Date().toString());

		HttpClient httpClient = new DefaultHttpClient();
		StringBuilder entityStringBuilder = new StringBuilder();
		HttpPost post = null;
		try {
			post = new HttpPost(url);
			//设置参数
	        List<NameValuePair> list = new ArrayList<NameValuePair>();
	        Iterator iterator = map.entrySet().iterator();
	        while(iterator.hasNext()){
	            Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
	            list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
	        }
	        if(list.size() > 0){
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");
	            post.setEntity(entity);
	        }
		HttpResponse httpResponse = httpClient.execute(post);
		HttpEntity entity = httpResponse.getEntity();
		if (null != entity) {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(entity.getContent(), "UTF-8"), 8 * 1024);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				entityStringBuilder.append(line);
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			post.releaseConnection();
		}
		return entityStringBuilder.toString();
	}
	public static String requestByGetMethod(String url) {

		HttpClient httpClient = new DefaultHttpClient();
		StringBuilder entityStringBuilder = new StringBuilder();
		HttpGet get = null;
		try {
			get = new HttpGet(url);
			
			HttpResponse httpResponse = httpClient.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			if (null != entity) {
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(entity.getContent(), "UTF-8"), 8 * 1024);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					entityStringBuilder.append(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			get.releaseConnection();
		}
		return entityStringBuilder.toString();
	}
	
	
	public static void getScore(String schoolname,String url,String filename){
		for (String cname : cnames) {
			for (String km : kms) {
				AppendToFileUtil.appendMethodA(filename, getScore(schoolname, url,cname, km));
			}
		}
	}
	
	public static List<Score> getScore(School school,String url){
		List<Score> scores = new ArrayList<Score>();
		for (String cname : cnames) {
			for (String km : kms) {
				scores.addAll(getScoreList(school, url, cname, km));
			}
		}
		return scores;
	}
	
	public static void getMajorScore(String schoolname,String url,String filename){
		for (String cname : cnames) {
			for (String km : kms) {
				for (String year : years) {
					AppendToFileUtil.appendMethodA(filename, getMajorScore(schoolname, url,cname, km,year));
				}
			}
		}
	}
	
	public static List<MajorScore> getMajorScore(School school,String url){
		List<MajorScore> majorScores = new ArrayList<MajorScore>();
		for (String cname : cnames) {
			for (String km : kms) {
				for (String year : years) {
					majorScores.addAll(getMajorScoreList(school, url, cname, km, year));
				}
			}
		}
		return majorScores;
	}
	/**
	 * 
	 * @param schoolname 学校
	 * @param url 请求地址
	 * @param cname 地区：区号||地区
	 * @param km 文理科类型：1||文科    2||理科
	 * @return
	 */
	public static String getScore(String schoolname,String url,String cname,String km){
		StringBuffer buffer = new StringBuffer();
		
		String htmlContent = requestByPostMethod1(url,cname, km, "");
		if (StringUtils.isNotEmpty(htmlContent)) {
			Document document=Jsoup.parse(htmlContent);
			Elements scorerows = document.select("table[class=sortTable]").get(0).select("tr");
			for (int i=1;i<scorerows.size();i++) {
				Element scorerow = scorerows.get(i);
				String year = scorerow.select("td").get(0).text();
				if ("没有查到您要的数据！".equals(year)) {
					break;
				}
				String avgScore = scorerow.select("td").get(1).text();
				String minScore = scorerow.select("td").get(2).text();
				String count = scorerow.select("td").get(3).text();
				String line = scorerow.select("td").get(4).text();
				String pici = scorerow.select("td").get(5).text();
				
				
				buffer.append(schoolname+"	");
				buffer.append(year+"	");
				buffer.append(avgScore+"	");
				buffer.append(minScore+"	");
				buffer.append(count+"	");
				buffer.append(line+"	");
				buffer.append(pici+"	");
				buffer.append(cname.split("\\|\\|")[1]+"	");
				buffer.append(km.split("\\|\\|")[1]+"	");
				buffer.append("\r\n");
			}
		}
		
		return buffer.toString();
	}
	
	/**
	 * 
	 * @param schoolname 学校
	 * @param url 请求地址
	 * @param cname 地区：区号||地区
	 * @param km 文理科类型：1||文科    2||理科
	 * @return
	 */
	public static List<Score> getScoreList(School school,String url,String cname,String km){
		List<Score> scores = new ArrayList<Score>();
		String htmlContent = requestByPostMethod1(url,cname, km, "");
		if (StringUtils.isNotEmpty(htmlContent)) {
			Document document=Jsoup.parse(htmlContent);
			Elements scorerows = document.select("table[class=sortTable]").get(0).select("tr");
			for (int i=1;i<scorerows.size();i++) {
				Element scorerow = scorerows.get(i);
				String year = scorerow.select("td").get(0).text();
				if ("没有查到您要的数据！".equals(year)) {
					break;
				}
				String avgScoreString = scorerow.select("td").get(1).text();
				String minScoreString = scorerow.select("td").get(2).text();
				String countString = scorerow.select("td").get(3).text();
				String lineString = scorerow.select("td").get(4).text();
				String pici = scorerow.select("td").get(5).text();
				
				Integer avgScore = "-".equals(avgScoreString)?null:Integer.valueOf(avgScoreString);
				Integer minScore = "-".equals(minScoreString)?null:Integer.valueOf(minScoreString);
				Integer count =  "-".equals(countString)?null:Integer.valueOf(countString);
				Integer line =  "-".equals(lineString)?null:Integer.valueOf(lineString);
				
				scores.add(new Score(cname.split("\\|\\|")[1], km.split("\\|\\|")[1], year.substring(0, 4), avgScore, minScore, count, line, pici, school.getSchool_name(), school.getSchool_id()));
			}
		}
		
		return scores;
	}
	
	/**
	 * 
	 * @param schoolname 学校
	 * @param url 请求地址
	 * @param cname 地区：区号||地区
	 * @param km 文理科类型：1||文科    2||理科
	 * @param year 年份
	 * @return
	 */
	public static String getMajorScore(String schoolname,String url,String cname,String km,String year){
		StringBuffer buffer = new StringBuffer();
		
		String htmlContent = requestByPostMethod1(url,cname, km, year);
		if (StringUtils.isNotEmpty(htmlContent)) {
			Document document=Jsoup.parse(htmlContent);
			Elements scorerows = document.select("table[class=sortTable]").get(1).select("tr");
			for (int i=1;i<scorerows.size();i++) {
				Element scorerow = scorerows.get(i);
				String major = scorerow.select("td").get(0).text();
				if ("没有查到您要的数据！".equals(major)) {
					break;
				}
				String subject = scorerow.select("td").get(1).text();
				String avgScore = scorerow.select("td").get(2).text();
				String pici = scorerow.select("td").get(3).text();
				
				buffer.append(schoolname+"	");
				buffer.append(major+"	");
				buffer.append(subject+"	");
				buffer.append(avgScore+"	");
				buffer.append(pici+"	");
				buffer.append(cname.split("\\|\\|")[1]+"	");
				buffer.append(year+"	");
				buffer.append("\r\n");
			}
		}
		
		return buffer.toString();
	}
	
	/**
	 * 
	 * @param schoolname 学校
	 * @param url 请求地址
	 * @param cname 地区：区号||地区
	 * @param km 文理科类型：1||文科    2||理科
	 * @param year 年份
	 * @return
	 */
	public static List<MajorScore> getMajorScoreList(School school,String url,String cname,String km,String year){
		List<MajorScore> majorScores = new ArrayList<MajorScore>();
		
		String htmlContent = requestByPostMethod1(url,cname, km, year);
		if (StringUtils.isNotEmpty(htmlContent)) {
			Document document=Jsoup.parse(htmlContent);
			Elements scorerows = document.select("table[class=sortTable]").get(1).select("tr");
			for (int i=1;i<scorerows.size();i++) {
				Element scorerow = scorerows.get(i);
				String major = scorerow.select("td").get(0).text();
				if ("没有查到您要的数据！".equals(major)) {
					break;
				}
				String subject = scorerow.select("td").get(1).text();
				String avgScore = scorerow.select("td").get(2).text();
				String pici = scorerow.select("td").get(3).text();
				
				majorScores.add(new MajorScore(cname.split("\\|\\|")[1], subject, year.substring(0, 4), major, Integer.valueOf(avgScore), pici, school.getSchool_name(), school.getSchool_id()));
			}
		}
		
		return majorScores;
	}
	
	public static List<School> getSchools(Integer pageIndex){
		List<School> schools = new ArrayList<School>();
		String schoolListUrl = "http://www.gaokaopai.com/daxue-0-0-0-0-0-0-0--p-"+pageIndex+".html";
		String schoolListHtml=requestByGetMethod(schoolListUrl);
		System.out.println(schoolListHtml);
		if (StringUtils.isNotEmpty(schoolListHtml)) {
			Document document=Jsoup.parse(schoolListHtml);
			Elements schoolrows = document.select("ul[class=slist]").get(0).select("li");
			for (Element schoolrow : schoolrows) {
				//学校名
				String schoolName = schoolrow.selectFirst("div[class=tit]").selectFirst("h3").selectFirst("a").text();
				
				//进入学校页面，获取历年分数线url
				String schoolUrl = schoolrow.selectFirst("div[class=tit]").selectFirst("h3").selectFirst("a").attr("href");
				String schoolHtml = requestByGetMethod(schoolUrl);
				Document schoolDocument = Jsoup.parse(schoolHtml);
				String scoreUrl=schoolDocument.selectFirst("ul[class=menu]").select("li").get(3).selectFirst("strong").text();
				
				//学校类型
				Elements types=schoolrow.selectFirst("div[class=tag]").select("img");
				StringBuffer schoolType=new StringBuffer();
				for (Element type : types) {
					schoolType.append(type.attr("title")+" ");
				}
				System.out.println(schoolName+""+schoolUrl+""+schoolType.toString());
			}
		}
		
		return schools;
	}
	
	public static String httpGet(String url){
        //get请求返回结果
        String result = null;
        try {
        	HttpClient client = HttpClients.createDefault();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
 
            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                return EntityUtils.toString(response.getEntity());
            } else {
                System.out.println("get请求提交失败:" + url);
            }
        } catch (IOException e) {
        	System.out.println("get请求提交失败:" + url);
        }
        return result;
    }
	
	
	/**

	     *
	 请求url地址 

	     * @param strUrl 请求地址

	     * @param params 请求参数

	     * @param method 请求方法

	     * @return  网络请求字符串

	     * @throws Exception

	     */

	    public static void net() throws Exception {
    	URL url=new URL("http://www.gaokaopai.com/daxue-0-0-0-0-0-0-0--p-1.html");
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        connection.setReadTimeout(5000);
        connection.setRequestMethod("GET"); //请求方式 注意要大写
        connection.setConnectTimeout(5000);
     //   connection.setRequestProperty("Content-Length", String.valueOf(data.length()));
        connection.setDoOutput(true);//设置true  就可以写了
        
   //     connection.getOutputStream().write(data.getBytes());//写入数据
        int code=connection.getResponseCode();
        if(code==200){
            InputStream is=connection.getInputStream();
            //StreamTools.ReadStream(is);
            ByteArrayOutputStream  bao=new ByteArrayOutputStream();
            int len=0;
            byte[] buffer=new byte[1024];
            while((len=is.read(buffer))!=-1){
                
                bao.write(buffer, 0, len);
            }
            is.close();
            System.out.println(bao.toString());
            bao.close();
            }}

	  //将map型转为请求参数型

	        public static String urlencode(Map<String,String> data) {

	            StringBuilder sb = new StringBuilder();

	            for (Map.Entry i : data.entrySet()) {

	                try {

	                    sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");

	                } catch (UnsupportedEncodingException e) {

	                    e.printStackTrace();

	                }

	            }

	            return sb.toString();

	        }

	public static void main(String[] args) throws Exception {
		
//		AppendToFileUtil.appendMethodA("C:\\Users\\liululu\\Desktop\\新建文本文档 (2).txt","安监局的你看你上课");
		//net();
		httpGet("http://www.gaokaopai.com/daxue-0-0-0-0-0-0-0--p-1.html");
	//	getSchools(1);
		String[] cnames={"11||北京 ","12||天津","13||河北","14||山西","15||内蒙古","21||辽宁","22||吉林","23||黑龙江","31||上海","32||江苏","33||浙江","34||安徽","35||福建","36||江西","37||山东","41||河南","42||湖北","43||湖南","44||广东","45||广西","46||海南","50||重庆","51||四川","52||贵州","53||云南","54||西藏","61||陕西","62||甘肃","63||青海","64||宁夏","65||新疆"};
		String[] kms={"1||文科","2||理科"};
		String[] years={"2016","2015","2014","2013"};
		
		for (String cname : cnames) {
			for (String km : kms) {
//				AppendToFileUtil.appendMethodA("C:\\Users\\liululu\\Desktop\\新建文本文档 (2).txt", getScore("清华大学", "http://www.gaokaopai.com/daxue-luquxian-2216.html",cname, km));
//				System.out.println(getScore("清华大学", "http://www.gaokaopai.com/daxue-luquxian-2216.html",cname, km));
			}
		}
		
//		getMajorScore("复旦大学", "http://www.gaokaopai.com/daxue-luquxian-978.html","65||新疆", "1||文科","2016");
		
		//专业分数线
		for (String cname : cnames) {
			for (String km : kms) {
				
				for (String year : years) {
					//AppendToFileUtil.appendMethodA("‪D:\\score.txt", getScore("清华大学", "http://www.gaokaopai.com/daxue-luquxian-2216.html",cname, km));
					//System.out.println(getMajorScore("清华大学", "http://www.gaokaopai.com/daxue-luquxian-2216.html",cname, km,year));
				}
			}
		}
		System.out.println("结束");
		
		System.out.println(URLEncoder.encode("http://153.37.217.112:8099/search?name=俞生生&company=Western Digital","utf-8").replaceAll("\\+","%20"));
	}
	
	

}
