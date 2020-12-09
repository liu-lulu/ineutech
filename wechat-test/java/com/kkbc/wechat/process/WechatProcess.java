package com.kkbc.wechat.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.util.MessageUtil;
import com.kkbc.wechat.common.AccessToken;
import com.kkbc.wechat.common.WechatConsts;
import com.kkbc.wechat.resp.vo.Article;
import com.kkbc.wechat.resp.vo.LinkMessage;
import com.kkbc.wechat.resp.vo.NewsMessage;
import com.kkbc.wechat.resp.vo.TextMessage;

public class WechatProcess {

	protected Logger log = LoggerFactory.getLogger(getClass());
	protected MessageUtil util=new MessageUtil();
	protected WechatOperate operate=new WechatOperate();

	public String processRequest(HttpServletRequest request) {
		
		
		String respMessage = null;
		try {
			// xml请求解析
			Map<String, String> requestMap = util.parseXmlDigui(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");

			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
			String event = requestMap.get("Event");

			// 事件KEY值
			String eventKey = requestMap.get("EventKey");

			System.out.println("---fromUserName:" + fromUserName
					+ "---toUserName:" + toUserName + "---msgType:" + msgType
					+ "---event:" + event+ "---eventKey:"+eventKey);

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(WechatConsts.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			String respContent = "";

			// 文本消息
			if (msgType.equals(WechatConsts.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");

				// 创建图文消息
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(WechatConsts.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);

				List<Article> articleList = new ArrayList<Article>();

				// 单图文消息
				if ("1".equals(content)) {
					Article article = new Article();
					article.setTitle("我是一条单图文消息");
					article.setDescription("我是描述信息，哈哈哈哈哈哈哈。。。");
					article.setPicUrl("http://www.iteye.com/upload/logo/user/603624/2dc5ec35-073c-35e7-9b88-274d6b39d560.jpg");
					article.setUrl("http://tuposky.iteye.com");
					articleList.add(article);

					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合

					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息
				else if ("3".equals(content)) {

					Article article1 = new Article();
					article1.setTitle("我是一条多图文消息");
					article1.setDescription("");
					article1.setPicUrl("http://www.isic.cn/viewResourcesAction//logo/20130913/2013091314543416032.jpg");
					article1.setUrl("http://tuposky.iteye.com/blog/2008583");

					Article article2 = new Article();
					article2.setTitle("微信公众平台开发教程Java版（二）接口配置 ");
					article2.setDescription("");
					article2.setPicUrl("http://www.isic.cn/viewResourcesAction//logo/20131021/2013102111243367254.jpg");
					article2.setUrl("http://tuposky.iteye.com/blog/2008655");

					Article article3 = new Article();
					article3.setTitle("微信公众平台开发教程Java版(三) 消息接收和发送");
					article3.setDescription("");
					article3.setPicUrl("http://www.isic.cn/viewResourcesAction//logo/20131021/2013102111291287031.jpg");
					article3.setUrl("http://tuposky.iteye.com/blog/2017429");

					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());

					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				} else {
					respContent = "没有关于此信息的内容";
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}

				// 事件处理开始
			} else if (msgType.equals(WechatConsts.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");

				if (eventType.equals(WechatConsts.EVENT_TYPE_SUBSCRIBE)) {
					// 关注
					respContent = "感谢您关注偶,这里会给您提供最新的公司资讯和公告！\n";
					StringBuffer contentMsg = new StringBuffer();
					contentMsg.append("您还可以回复下列数字，体验相应服务").append("\n\n");
					contentMsg.append("1  我就是个测试的").append("\n");
					contentMsg.append("2  我木有").append("\n");
					contentMsg.append("3  我是多图文").append("\n");
					respContent = respContent + contentMsg.toString();
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				} else if (eventType.equals(WechatConsts.EVENT_TYPE_UNSUBSCRIBE)) {
					// 取消关注,用户接受不到我们发送的消息了，可以在这里记录用户取消关注的日志信息

					log.info("{}取消关注了", fromUserName);

				} else if (eventType.equals(WechatConsts.EVENT_TYPE_CLICK)) {

					// 事件KEY值，与创建自定义菜单时指定的KEY值对应

					log.info("{}事件", eventKey);

					// 自定义菜单点击事件
					if (eventKey.equals("click_key_bao_xiu")) {
						respContent = "如需呼叫流动服务车，请先点击右下角“+”号（如若没显示“+”请先点击左下角键盘图标），点击位置发送您的位置，对话框会弹出您的位置信息和服务界面，点击进入服务界面填写您的需求。自动确认金额最后提交订单即可。祝您一路平安，身体健康！";
					} else if (eventKey.equals("click_key_comment")) {
						respContent = "我要评论被点击！";
					} else if (eventKey.equals("click_key_user_info")) {
						respContent = "用户中心被点击！";
					} else if (eventKey.equals("click_key_goutai")) {
						respContent = "要购胎,请立即拨打浦东客服中心电话：18536635357,浦西客服中心电话：15221219632";
					} else if (eventKey.equals("click_key_person")) {
						respContent = "个人中心被点击！";
					} else {
						respContent = "未知点击事件！";
					}

					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}else if (eventType.equals(WechatConsts.EVENT_TYPE_VIEW)) {
					String url=requestMap.get("url");
					log.info("url:{}",url);
					respContent = eventKey;
					
					
//					NewsMessage newsMessage = new NewsMessage();
//					List<Article> articleList = new ArrayList<Article>();
//					Article article = new Article();
//					article.setTitle("我是一条单图文消息");
//					article.setDescription("我是描述信息，哈哈哈哈哈哈哈。。。");
//					article.setPicUrl("http://www.iteye.com/upload/logo/user/603624/2dc5ec35-073c-35e7-9b88-274d6b39d560.jpg");
//					article.setUrl(eventKey+"?openId="+fromUserName);
//					articleList.add(article);
//
//					// 设置图文消息个数
//					newsMessage.setArticleCount(articleList.size());
//					// 设置图文消息包含的图文集合
//
//					newsMessage.setArticles(articleList);
//					// 将图文消息对象转换成xml字符串
//					respMessage = MessageUtil.newsMessageToXml(newsMessage);
					
				}else if (eventType.equals(WechatConsts.EVENT_TYPE_SCANCODE_WAITMSG)) {
					String ScanCodeInfo=requestMap.get("ScanResult");
					respContent = ScanCodeInfo;
//					LinkMessage linkMessage=new LinkMessage();
//					linkMessage.setToUserName(fromUserName);
//					linkMessage.setFromUserName(toUserName);
//					linkMessage.setCreateTime(new Date().getTime());
//					linkMessage.setMsgType(WechatConsts.RESP_MESSAGE_TYPE_LINK);
//					linkMessage.setFuncFlag(0);
//					linkMessage.setUrl("http://hylexus.6655.la/wechat-test/user/userInfo?stuNo="+ScanCodeInfo+"&openId="+fromUserName);
//					linkMessage.setTitle("确认学号:"+ScanCodeInfo);
//					linkMessage.setDescription("绑定或编辑信息");
					
					// 创建图文消息
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(WechatConsts.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setFuncFlag(0);

					List<Article> articleList = new ArrayList<Article>();

					Article article = new Article();
					article.setTitle("确认学号:"+ScanCodeInfo);
					article.setDescription("绑定或编辑信息");
					article.setPicUrl("http://www.iteye.com/upload/logo/user/603624/2dc5ec35-073c-35e7-9b88-274d6b39d560.jpg");
					article.setUrl("http://hylexus.6655.la/wechat-test/user/userInfo?stuNo="+ScanCodeInfo+"&openId="+fromUserName);
					articleList.add(article);

					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合

					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
					
				}else if (eventType.equals(WechatConsts.EVENT_TYPE_SCANCODE_PUSH)) {
					String ScanCodeInfo=requestMap.get("ScanResult");
					respContent = "二维码:"+ScanCodeInfo;
					LinkMessage linkMessage=new LinkMessage();
					linkMessage.setUrl("http://hylexus.6655.la/wechat-test/user/userInfo.do?stuNo="+ScanCodeInfo+"&openId="+fromUserName);
//					textMessage.setContent(respContent);
					respMessage = MessageUtil.linkMessageToXml(linkMessage);
					
				}else if (eventType.equals(WechatConsts.EVENT_TYPE_PIC_PHOTO_OR_ALBUM)) {
					System.err.println("-----------");
					String mediaId=requestMap.get("MediaId");
					System.out.println("mediaId:---------"+mediaId);
					operate.saveImageToDisk(WechatConsts.param_access_token, mediaId);
					respContent = "图片保存！";
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}else {
					respContent = "未知事件！";
					textMessage.setContent(respContent);
					respMessage = MessageUtil.textMessageToXml(textMessage);
				}
				

			}else if (msgType.equals(WechatConsts.REQ_MESSAGE_TYPE_LOCATION)) {
				
				float location_X=Float.valueOf(requestMap.get("Location_X"));
				float location_Y=Float.valueOf(requestMap.get("Location_Y"));
				respContent = "你的位置经纬度信息是{"+requestMap.get("Label")+"}";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
				
				
			}else if (msgType.equals(WechatConsts.REQ_MESSAGE_TYPE_IMAGE)) {
				String picUrl=requestMap.get("PicUrl");
				String mediaId=requestMap.get("MediaId");
				
				operate.saveImageToDisk(WechatConsts.param_access_token, mediaId);
				respContent = "图片路径{"+picUrl+"}";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
				
			} else {
				
				respContent = "没有关于此消息类型的处理";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	/**
	* 根据经纬度反向解析地址，有时需要多尝试几次
	* 注意:(摘自：http://code.google.com/intl/zh-CN/apis/maps/faq.html
	* 提交的地址解析请求次数是否有限制？) 如果在 24 小时时段内收到来自一个 IP 地址超过 15,000 个地址解析请求， 或从一个 IP
	* 地址提交的地址解析请求速率过快，Google 地图 API 编码器将用 620 状态代码开始响应。 如果地址解析器的使用仍然过多，则从该 IP
	* 地址对 Google 地图 API 地址解析器的访问可能被永久阻止。
	* 
	* @param latitude
	*            纬度
	* @param longitude
	*            经度
	* @return
	*/
	public static String geocodeAddr(String latitude, String longitude) {
		String addr = "";

		// 也可以是http://maps.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s，不过解析出来的是英文地址
		// 密钥可以随便写一个key=abc
		// output=csv,也可以是xml或json，不过使用csv返回的数据最简洁方便解析
		String url = String.format("http://ditu.google.cn/maps/geo?output=csv&key=abcdef&q=%s,%s",latitude, longitude);
		URL myURL = null;
		URLConnection httpsConn = null;
		try {
		  myURL = new URL(url);
		} catch (MalformedURLException e) {
		  e.printStackTrace();
		  return null;
		}
		try {
		  httpsConn = (URLConnection) myURL.openConnection();
		  if (httpsConn != null) {
		    InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
		    BufferedReader br = new BufferedReader(insr);
		    String data = null;
		    if ((data = br.readLine()) != null) {
		      System.out.println(data);
		      String[] retList = data.split(",");
		      if (retList.length > 2 && ("200".equals(retList[0]))) {
		        addr = retList[2];
		        addr = addr.replace("\"", "");
		      } else {
		        addr = "";
		      }
		    }
		  insr.close();
		  }
		} catch (IOException e) {
		  e.printStackTrace();
		  return null;
		}
		  return addr;
		}


	public static String getAdd(String log, String lat ){  
		
		 String urlString ="http://api.map.baidu.com/geocoder/v2/?ak=pmCgmADsAsD9rEXkqWNcTzjd&location="+log+","+lat+"&output=json&pois=1";
		        //lat 小  log  大  
		        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项)  
//		        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+log+"&type=010";  
		        String res = "";     
		        try {     
		            URL url = new URL(urlString);    
		            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();    
		            conn.setDoOutput(true);    
		            conn.setRequestMethod("POST");    
		            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));    
		            String line;    
		           while ((line = in.readLine()) != null) {    
		               res += line+"\n";    
		         }    
		            in.close();    
		        } catch (Exception e) {    
		            System.out.println("error in wapaction,and e is " + e.getMessage());    
		        }   
		        System.out.println(res);  
		        return res;    
		    }  
	
	public static void main(String[] args) {
		String addr = getAdd("31.71099194", "120.4019789");// (38.9146943,121.612382);
		System.out.println(addr);
	}

}
