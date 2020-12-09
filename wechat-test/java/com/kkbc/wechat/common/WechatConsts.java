package com.kkbc.wechat.common;


public class WechatConsts {

	public static String CHARACTER_ENCODING = "UTF-8";

	public static final String wechat_base_url = "https://api.weixin.qq.com/cgi-bin/";
	public static final String default_echo_string = "success";
	public static String TOKEN = "access_token";
	
	public static final String APP_ID="wx1d12e90dc3b89cfb";
	public static final String APP_SECRET="b1288cae66a83b4290cc3f5bfeb99890";
	
	 
	
	public static long access_token_flush_period = (long) (1.5 * 60 * 60 * 1000);
	
	//微信请求参数access_token
	public static String param_access_token;
	
	
	/**
	     * 返回消息类型：文本
	     */
	    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	 
	    /**
	     * 返回消息类型：音乐
	     */
	    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	 
	    /**
	     * 返回消息类型：图文
	     */
	    public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	 
	    /**
	     * 返回消息类型：图片
	     */
	    public static final String RESP_MESSAGE_TYPE_Image = "image";
	 
	    /**
	     * 返回消息类型：语音
	     */
	    public static final String RESP_MESSAGE_TYPE_Voice = "voice";
	 
	    /**
	     * 返回消息类型：视频
	     */
	    public static final String RESP_MESSAGE_TYPE_Video = "video";
	    
	    /**
	     * 返回消息类型：链接
	     */
	    public static final String RESP_MESSAGE_TYPE_LINK = "link";
	 
	    /**
	     * 请求消息类型：文本
	     */
	    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	 
	    /**
	     * 请求消息类型：图片
	     */
	    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	 
	    /**
	     * 请求消息类型：链接
	     */
	    public static final String REQ_MESSAGE_TYPE_LINK = "link";
	 
	    /**
	     * 请求消息类型：地理位置
	     */
	    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	 
	    /**
	     * 请求消息类型：音频
	     */
	    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	 
	    /**
	     * 请求消息类型：视频
	     */
	    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	 
	    /**
	     * 请求消息类型：推送
	     */
	    public static final String REQ_MESSAGE_TYPE_EVENT = "event";
	 
	    /**
	     * 事件类型：subscribe(订阅)
	     */
	    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	 
	    /**
	     * 事件类型：unsubscribe(取消订阅)
	     */
	    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	    
	    /**
	     * 事件类型：scancode_waitmsg(扫码带提示)
	     */
	    public static final String EVENT_TYPE_SCANCODE_WAITMSG = "scancode_waitmsg";
	    
	    /**
	     * 事件类型：scancode_push(扫码推事件)
	     */
	    public static final String EVENT_TYPE_SCANCODE_PUSH = "scancode_push";
	 
	    /**
	     * 事件类型：CLICK(自定义菜单点击事件)
	     */
	    public static final String EVENT_TYPE_CLICK = "CLICK";
	 
	    /**
	     * 事件类型：VIEW(自定义菜单URl视图)
	     */
	    public static final String EVENT_TYPE_VIEW = "VIEW";
	 
	    /**
	     * 事件类型：LOCATION(上报地理位置事件)
	     */
	    public static final String EVENT_TYPE_LOCATION = "LOCATION";
	 
	    /**
	     * 事件类型：LOCATION(上报地理位置事件)
	     */
	    public static final String EVENT_TYPE_SCAN = "SCAN";
	    
	    public static final String EVENT_TYPE_PIC_PHOTO_OR_ALBUM="pic_photo_or_album";


}
