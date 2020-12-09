package com.kkbc.wechat.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.kkbc.util.MessageUtil;
import com.kkbc.wechat.common.AccessToken;
import com.kkbc.wechat.common.WechatConsts;

public class WechatOperate {
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	protected OkHttpClient client = null;

	public WechatOperate() {
		this.client = new OkHttpClient();
	}
	
	public void createMenu(String accessToken, InputStream inputStream) {
		String url = WechatConsts.wechat_base_url + "menu/create?access_token=" + accessToken;
		String menuJsonString = MessageUtil.loadStringContentFromInputStream(inputStream);

		RequestBody jsonBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), menuJsonString);
		Request request = new Request.Builder()//
				.url(url)//
				.post(jsonBody)//
				.build();
		Response response = null;
		String resp = null;
		try {
			response = this.client.newCall(request).execute();
			if (!response.isSuccessful()){
				log.error("创建微信menu请求失败:{}",response.toString());
				throw new IOException("Unexpected code " + response);
			}
			resp = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONObject json = JSON.parseObject(resp);
		if (!new Integer(0).equals(json.get("errcode"))) {
			log.error("创建微信menu失败:{}",request.toString());
		}

	}
	
	public AccessToken getAccessToken(String appId, String appSecret) {
		String url = WechatConsts.wechat_base_url + "token?grant_type=client_credential&appid=_APPID_&secret=_APPSECRET_";
		url = url.replace("_APPID_", appId).replace("_APPSECRET_", appSecret);
		Response response = null;
		String resp = null;
		try {
			Request request = new Request.Builder().url(url).build();
			response = this.client.newCall(request).execute();
			if (!response.isSuccessful())
				throw new IOException("Unexpected code " + response);
			resp = response.body().string();
		} catch (IOException e) {
			log.info("获取access_token异常:{}"+e.getMessage());
		}

		JSONObject json = JSON.parseObject(resp);
		if (json.get("errmsg") != null) {
			log.info("获取access_token失败:{}"+response.toString());
		}
		return JSON.parseObject(resp, AccessToken.class);
	}
	
	protected void doPostJsonString(String url, String jsonString) throws IOException {
		RequestBody jsonBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
		Request request = new Request.Builder()//
				.url(url)//
				.post(jsonBody)//
				.build();
		Response response = null;
		String resp = null;
		try {
			response = this.client.newCall(request).execute();
			if (!response.isSuccessful())
				throw new IOException("post失败" + response);
			resp = response.body().string();
		} catch (IOException e) {
			throw new IOException("post请求异常 " + response);
		}

		JSONObject json = JSON.parseObject(resp);
		log.debug("{}", resp);
		if (!new Integer(0).equals(json.get("errcode"))) {
			throw new IOException("post请求错误 " + response);
		}
	}
	
	//网页授权--微信在直接跳转到网页时可获取openId
	protected String encode2OAuthUrl(String scope, String appId, String url) {
		String en = null;
		try {
			en = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String s = "https://open.weixin.qq.com/connect/oauth2/authorize?" + //
				"appid=" + appId//
				+ "&redirect_uri=" + en//
				+ "&response_type=code"//
				+ "&scope=" + scope//
				+ "&state=my-param1"//
				+ "#wechat_redirect";
		return s;
	}

	protected String encode2OAuthUrl(String appId, String url) {
		return this.encode2OAuthUrl("snsapi_base", appId, url);
	}
	
	//网页授权获取openid
	public String getOpenId(String appId, String appSecret, String code) throws IOException {
		String host = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String url = host + "?appid=_APPID_&secret=_APPSECRET_&code=_CODE_&grant_type=authorization_code";
		url = url.replace("_APPID_", appId).replace("_APPSECRET_", appSecret).replace("_CODE_",
				code == null ? "" : code);
		Response response = null;
		String resp = null;
		try {
			Request request = new Request.Builder().url(url).build();
			response = this.client.newCall(request).execute();
			if (!response.isSuccessful())
				throw new IOException("Unexpected code " + response);
			resp = response.body().string();
		} catch (IOException e) {
			throw e;
		}

		JSONObject json = JSON.parseObject(resp);
		String str = json.getString("openid");
		if (str != null)
			return str;

		throw new IOException(
				"调用微信接口获取openId失败:errcode=" + json.getString("errcode") + ",errmsg=" + json.getString("errmsg") + "");
	}
	
	/**
	    * 根据文件id下载文件
	    * 
	    * @param mediaId
	    *            媒体id
	    * @throws Exception
	    */
	   public  InputStream getInputStream(String accessToken,String mediaId) {
//	       GetExistAccessToken getExistAccessToken = GetExistAccessToken.getInstance();
//	       String accessToken = getExistAccessToken.getExistAccessToken();
	       InputStream is = null;
	       String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="
	               + accessToken + "&media_id=" + mediaId;
	       try {
	           URL urlGet = new URL(url);
	           HttpURLConnection http = (HttpURLConnection) urlGet
	                   .openConnection();
	           http.setRequestMethod("GET"); // 必须是get方式请求
	           http.setRequestProperty("Content-Type",
	                   "application/x-www-form-urlencoded");
	           http.setDoOutput(true);
	           http.setDoInput(true);
	           System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
	           System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
	           http.connect();
	           // 获取文件转化为byte流
	           is = http.getInputStream();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       return is;
	   }

	   /**
	    * 获取下载图片信息（jpg）
	    * 
	    * @param mediaId
	    *            文件的id
	    * @throws Exception
	    */
	   public  void saveImageToDisk(String accessToken,String mediaId) throws Exception {
	       InputStream inputStream = getInputStream(accessToken,mediaId);
	       byte[] data = new byte[1024];
	       int len = 0;
	       FileOutputStream fileOutputStream = null;
	       SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
	       File file=new File("D:/img/"+format.format(new Date())+".jpg");
	       if(!file.exists()){
	    	   file.createNewFile();
	       }
	       try {
	           fileOutputStream = new FileOutputStream(file);
	           while ((len = inputStream.read(data)) != -1) {
	               fileOutputStream.write(data, 0, len);
	           }
	       } catch (IOException e) {
	           e.printStackTrace();
	       } finally {
	           if (inputStream != null) {
	               try {
	                   inputStream.close();
	               } catch (IOException e) {
	                   e.printStackTrace();
	               }
	           }
	           if (fileOutputStream != null) {
	               try {
	                   fileOutputStream.close();
	               } catch (IOException e) {
	                   e.printStackTrace();
	               }
	           }
	       }
	   }
	
	public static void main(String[] args) throws IOException {
//		String accesstoken="FrNHclIPUXOE7pu2QpLAK_1Ab1ZyxmA_IxPz_anQS3SwCzqeilq1W-S6_zLXocbUCf1FJKSfgRKXJFF9RKLGB8AHY8gvhxJXj5lzAwF_188EcNCMClVEI9R0wVth9llkNZHjAHABNV";
//		WechatOperate create=new WechatOperate();
//		AccessToken token=create.getAccessToken(WechatConsts.APP_ID, WechatConsts.APP_SECRET);
//		System.out.println(token.getAccess_token());
//		create.createMenu(accesstoken,WechatOperate.class.getResourceAsStream("tyre-menu.json"));
//		
//		System.out.println(create.encode2OAuthUrl(WechatConsts.APP_ID, "http://hylexus.6655.la/wechat-test/user/userInfo.do"));
		
		 String filePath="‪C:\\Users\\liululu\\Desktop\\硬件\\拨盘\\投票器功能样机演示资料\\bmp\\1.txt";
//		 filePath="‪C:/Users/liululu/Desktop/img/1.txt";
//		 File dir=new File("‪C:/Users/liululu/Desktop/img/");
//		 if(!dir.exists()){
//			 dir.mkdirs();
//	    	   }
	       File file=new File("C:/1.txt");
	       
	       if(!file.exists()){
	    	   file.createNewFile();
	       }
		
	}

}
