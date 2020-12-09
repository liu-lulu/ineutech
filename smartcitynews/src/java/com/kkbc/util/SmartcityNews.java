package com.kkbc.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SmartcityNews {
	
	
	public static String requestByPostMethod1(String url) {
		Map<String,String> map = new HashMap<>();
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
	
	public static String requestByGetMethod1(String url) {
		Map<String,String> map = new HashMap<>();
		map.put("timestamp",new Date().toString());

		HttpClient httpClient = new DefaultHttpClient();
		StringBuilder entityStringBuilder = new StringBuilder();
		HttpGet get = null;
		try {
			get = new HttpGet(url);
			//设置参数
	        List<NameValuePair> list = new ArrayList<NameValuePair>();
	        Iterator iterator = map.entrySet().iterator();
	        while(iterator.hasNext()){
	            Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
	            list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
	        }
	        if(list.size() > 0){
	            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");
//	            get.setEntity(entity);
	        }
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
	
	
	public static String readToString(String fileName) {  
        String encoding = "UTF-8";  
        File file = new File(fileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
    }  
	
	public static void download(String urlString, String filename,String savePath) throws Exception {
	    // 构造URL
	    URL url = new URL(urlString);
	    // 打开连接
	    URLConnection con = url.openConnection();
	    //设置请求超时为5s
	    con.setConnectTimeout(5*1000);
	    // 输入流
	    InputStream is = con.getInputStream();
	
	    // 1K的数据缓冲
	    byte[] bs = new byte[1024];
	    // 读取到的数据长度
	    int len;
	    // 输出的文件流
	   File sf=new File(savePath);
	   if(!sf.exists()){
		   sf.mkdirs();
	   }
	   OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
	    // 开始读取
	    while ((len = is.read(bs)) != -1) {
	      os.write(bs, 0, len);
	    }
	    // 完毕，关闭所有链接
	    os.close();
	    is.close();
	} 
	
	public static void main(String[] args) throws Exception {
		Document document1=Jsoup.parse(readToString("D:\\homepage.html"));
		Elements appmsgList1 = document1.select("a[class=list_item js_post]");
		for (int i=0;i<appmsgList1.size();i++) {
			Element element=appmsgList1.get(i);
			String imgurl=element.selectFirst("div").attr("data-cover");
			String href=element.attr("href");
			String title=element.selectFirst("h2").text();
			String imgName=(i+1)+".jpg";
//			download(imgurl, imgName, "D:\\img\\smartcitynews\\list");
//			System.out.println(title+"	"+imgName);
			
			String content = requestByGetMethod1(href);
			System.out.println(content);
			Document document2=Jsoup.parse(content);
			
			System.out.println(title+"	"+document2.selectFirst("div[id=js_content]").toString());
			break;
		}
		
//		String htmlString=readToString("D:\\微信公众平台4.html");
//		SimpleDateFormat format = new SimpleDateFormat("yyyy年mm月dd日");
//		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
//		SimpleDateFormat format2 = new SimpleDateFormat("yyyymmdd");
//		Document document=Jsoup.parse(htmlString);
//		Elements appmsgList = document.select("tr[class=js_appmsgitem]");
//		for (Element newEle : appmsgList) {
//			String imgurl=newEle.selectFirst("i").attr("style");
//			Elements titles=newEle.child(0).select("span");
//			
//			try {
//				Date date=format.parse(newEle.child(1).text());
//				String publishDate = format1.format(date);
//				String imgName=format2.format(date)+".jpg";
//				download(imgurl.split("\"")[1], imgName, "D:\\img\\smartcitynews");
//				for (Element element : titles) {
//					String title=element.text();
//					if (title.startsWith("活动预告")||title.startsWith("智慧学堂")) {
//						continue;
//					}
//					System.out.println(title+"	"+imgName+"	"+publishDate);
//				}
//			} catch (ParseException e) {
//			}
			
// 		}
	}
	
	

}
