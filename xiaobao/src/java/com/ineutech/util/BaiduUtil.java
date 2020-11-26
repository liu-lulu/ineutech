package com.ineutech.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.baidu.aip.util.Base64Util;

public class BaiduUtil {
	
	public static final String BAIDU_CLIENTID="wsAS1htt5RrzuXCWlSisZgR7";
	
	public static final String BAIDU_CLIENTSECRET="Qrr1TqSSGydWKfHLPTg5WfUxG6zlajpF";
	
	//token的过期时间
	public static Calendar expireDate;
	
	//Access Token
	public static String token;
	
	static{
		getAuth();
	}
	
	public static String toGetAuth() {
		Calendar c = Calendar.getInstance();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("当前时间:"+sdf.format(c.getTime())+"--过期时间:"+sdf.format(expireDate.getTime()));
		
		if (StringUtils.isNotEmpty(token)&&c.before(expireDate)) {
			return token;
		}
		return getAuth();
	}
	
	/**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public static String getAuth() {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + BAIDU_CLIENTID
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + BAIDU_CLIENTSECRET;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.err.println(key + "--->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            
            Integer expires_in = Integer.valueOf(jsonObject.getInt("expires_in"));
			Calendar c = Calendar.getInstance();
			c.add(13, expires_in.intValue());
			expireDate = c;
			token=access_token;
			
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }
    
    /**
     * 通用文字识别（含位置信息版）
     * @param imgData
     * @return
     */
	public static String general(byte[] imgData) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general";
        try {
            // 本地文件路径
//            String filePath = "‪C:\\Users\\liululu\\Desktop\\44.jpg";
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = toGetAuth();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/**
	 * 通用文字识别（高精度含位置版）
	 * @return
	 */
	public static String accurate(byte[] imgData) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate";
        try {
            // 本地文件路径
//            String filePath = "[本地文件路径]";
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = toGetAuth();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	 public static void main(String[] args) {
		 BaiduUtil.toGetAuth();
	    }

}
