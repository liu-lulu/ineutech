package com.psylife.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtil {
	// public static String jpush_master_secret = "6dd8d67439eacd4a93c6bb9a";
	// public static String jpush_app_key = "1df3af992199413822d318f7";


	public static boolean pushMsg2AndroidAndIOS(String app_key, String master_secret, String title, String content, String key,
			String jsonValue,String tag) throws APIConnectionException, APIRequestException {

		JPushClient jpushClient = new JPushClient(master_secret, app_key);

		PushPayload payload = buildPushMsg(title, content, key, jsonValue,tag);

		PushResult result = jpushClient.sendPush(payload);
		return PushResult.ERROR_CODE_OK == result.getResponseCode();

	}


	private static PushPayload buildPushMsg(String title, String content, String key, String jsonValue,String tag) {
		return PushPayload.newBuilder()//
				.setPlatform(Platform.all())//
				.setOptions(Options.newBuilder().setApnsProduction(true).build())
//				.setAudience(Audience.all())//
				.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tag)).build())
				.setNotification(//
						Notification.newBuilder()//
								.addPlatformNotification(IosNotification.newBuilder()//
										.setAlert(content)//
										.setSound("1107")//
										.addExtra(key, jsonValue)//
										.build())//
								.addPlatformNotification(AndroidNotification.newBuilder()//
										.setAlert(content)//
										.setTitle(title)//
										.addExtra(key, jsonValue)//
										.build()//
								)//
								.build())//
				//
				.build();
	}
}
