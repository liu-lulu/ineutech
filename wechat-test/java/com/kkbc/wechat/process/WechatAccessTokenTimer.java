package com.kkbc.wechat.process;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.kkbc.wechat.common.AccessToken;
import com.kkbc.wechat.common.WechatConsts;

public class WechatAccessTokenTimer extends TimerTask {

	private Logger log = LoggerFactory.getLogger(getClass());

	private WechatOperate operate=new WechatOperate();
	private AccessToken tokenConstant;

	public String getAccessToken()  {
		return this.tokenConstant.getAccess_token();
	}

	private String getAccessTokenFromWechatServer() {
		AccessToken token = operate.getAccessToken(WechatConsts.APP_ID, WechatConsts.APP_SECRET);
		String newToken = token.getAccess_token();
		return newToken;
	}

	private Timer timer;
	private long delay = 0;

	public WechatAccessTokenTimer() {
		this.timer = new Timer();
	}

	public void start() {
		log.info("token定时器首次执行延迟时间:delay= {} minutes .", this.delay / 1000 / 60);
		this.timer.schedule(this, this.delay, WechatConsts.access_token_flush_period);
	}

	public void initDelayValue() {
//		Constant constant = this.constantMapper.getAccessToken();
//		log.info("系统加载,从数据库获取accessToken:\n{}",JSON.toJSONString(constant, true));
		if (tokenConstant == null) {
			this.delay = 0;
			return;
		}

		long distance = System.currentTimeMillis() - tokenConstant.getUpdateTime().getTime();

		if (distance >= WechatConsts.access_token_flush_period) {
			this.delay = 0;
			return;
		}

		this.delay = WechatConsts.access_token_flush_period - distance;
//		this.tokenConstant = constant;
	}

	@Override
	public void run() {
		this.saveOrUpdateAccessToken();
	}

	@Transactional
	private void saveOrUpdateAccessToken() {
//		Constant constant = this.constantMapper.getAccessToken();
		String tfw = null;
		try {
			tfw = this.getAccessTokenFromWechatServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (tokenConstant == null) {
			tokenConstant = new AccessToken();
//			constant.setKey(Consts.access_token_key_in_db);
//			constant.setValue(tfw);
//			tokenConstant.setUpdateTime(new Date());
//
//			this.tokenConstant = constant;
//			this.constantMapper.insert(constant);
			log.info("第一次获取accessToken");
		}
//		else {
//			constant.setValue(tfw);
//			constant.setUpdateTime(new Date());
//
//			this.tokenConstant = constant;
//			this.constantMapper.updateAccessToken(constant);
//			log.info("accessToken已经更新");
//		}
		WechatConsts.param_access_token=tfw;
		
		tokenConstant.setAccess_token(tfw);
		tokenConstant.setUpdateTime(new Date());
	}

}
