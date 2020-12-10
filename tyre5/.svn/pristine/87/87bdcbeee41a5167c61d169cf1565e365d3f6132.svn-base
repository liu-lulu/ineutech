package com.psylife.util.push;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;

public class WarningPusher {
	public static String appId;
	public static String appKey;
	public static String masterSecret;
	public static String host;

	static {
		Properties properties = new Properties();
		try {
			properties.load(WarningPusher.class.getClassLoader().getResourceAsStream("push.properties"));
			appId = properties.getProperty("ipush.appId", "zGFpegs9mV8mpVE6YdMpb1");
			appKey = properties.getProperty("ipush.appKey", "y72cwgDGyj9PfBBBI6Nc83");
			masterSecret = properties.getProperty("ipush.masterSecret", "nBMR5j7xdIAfcNRylLSpM8");
			host = properties.getProperty("ipush.host", "http://sdk.open.api.igexin.com/apiex.htm");

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public IPushResult push(Message warningMsg) {
		IGtPush push = new IGtPush(host, appKey, masterSecret);

		AppMessage message = new AppMessage();
		message.setData(buildNotificationMsg(appId, appKey, warningMsg));

		message.setOffline(true);
		// 离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 1000 * 3600);
		List<String> appIdList = new ArrayList<String>();
		appIdList.add(appId);
		message.setAppIdList(appIdList);

		IPushResult ret = null;
		try {
			ret = push.pushMessageToApp(message);
		} catch (RequestException e) {
			e.printStackTrace();
			ret = push.pushMessageToApp(message, e.getRequestId());
		}

		return ret;
	}

	private NotificationTemplate buildNotificationMsg(String appId, String appkey, Message warningMsg) {
		NotificationTemplate template = new NotificationTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appkey);
		// 设置通知栏标题与内容

		template.setTitle(StringUtils.isEmpty(warningMsg.getTitle()) ? "通知" : warningMsg.getTitle());
		template.setText(warningMsg.getContent());
		// 配置通知栏网络图标

		// template.setLogoUrl("https://www.baidu.com/img/baidu_jgylogo3.gif");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);

		// 配置通知栏图标
		template.setLogo("ic_launcher.png");

		template.setIsVibrate(true);
		template.setIsClearable(false);
		// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
		template.setTransmissionType(1);

		template.setTransmissionContent(JSON.toJSONString(warningMsg));
		// 设置定时展示时间
		// template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
		return template;
	}

	public static void main(String[] args) {
		WarningPusher pusher = new WarningPusher();
		Message message = new Message(123, "车报警", 15, 1, 1, new Date(), "苏D63056", "警告信息","测试公司");
		IPushResult result = pusher.push(message);
		System.out.println(result.getResponse());
	}
}
