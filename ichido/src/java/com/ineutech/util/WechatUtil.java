package com.ineutech.util;

import net.sf.json.JSONObject;

public class WechatUtil {
	
	//小程序唯一标识   (在微信小程序管理后台获取)
    private static String wxspAppid = "wx0ad6c43c275f1a1c";
    //小程序的 app secret (在微信小程序管理后台获取)
    private static String wxspSecret = "3be3f4bcef83caa6501eb4f6b7a22608";
    //授权（必填）
    private static String grant_type = "***************************";
    
    public static String getOpenId(String code){
		//1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid ////////////////
		//请求参数
		String params = "appid=" + wxspAppid + "&secret=" + wxspSecret + "&js_code=" + code + "&grant_type=" + grant_type;
		//发送请求
		String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
		//解析相应内容（转换成json对象）
		JSONObject json = JSONObject.fromObject(sr);
		//获取会话密钥（session_key）
		String session_key = json.get("session_key").toString();
		//用户的唯一标识（openid）
		String openid = (String) json.get("openid");
		return openid;
    }


}
