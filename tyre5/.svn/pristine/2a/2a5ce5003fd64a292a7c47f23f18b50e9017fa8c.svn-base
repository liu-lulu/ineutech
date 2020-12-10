package com.kkbc.dao.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.kkbc.dao.UserTokenDao;
import com.kkbc.entity.UserToken;

public class UserTokenDaoImpl extends BaseDaoImpl implements UserTokenDao{

	@Transactional
	@Override
	public int saveToken(int user_id, String token) {
		UserToken param=new UserToken();
		param.setUser_id(user_id);
		param.setToken(token);
		param.setCreate_time(new Date());
		UserToken userToken=(UserToken) getSqlMapClientTemplate().queryForObject("UserToken.getInfoByUserId", user_id);
		int id;
		if (userToken==null) {//首次登陆
			id=(int) getSqlMapClientTemplate().insert("UserToken.saveInfo", param);
		}else {//再次登陆,更新token信息
			id=getSqlMapClientTemplate().update("UserToken.updateInfo", param);
		}
		if (id>0) {
			logger.info("登陆用户token信息保存成功:"+user_id);
		}
		return id;
	}
	
	@Transactional
	@Override
	public int updateToken(int user_id, String token) {
		UserToken userToken=new UserToken();
		userToken.setUser_id(user_id);
		userToken.setToken(token);
		userToken.setCreate_time(new Date());
		int result=getSqlMapClientTemplate().update("UserToken.updateInfo", userToken);
		if (result>0) {
			logger.info("登陆用户token信息更新成功:"+user_id);
		}
		return result;
	}

	@Override
	public UserToken getTokenInfo(String token) {
		UserToken tokenInfo=(UserToken) getSqlMapClientTemplate().queryForObject("UserToken.getInfo", token);
		if (tokenInfo!=null) {
			logger.info("获取token信息成功:"+token);
		}
		return tokenInfo;
	}
	
}
