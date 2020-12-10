package com.kkbc.service.impl;

import javax.annotation.Resource;

import com.kkbc.dao.UserTokenDao;
import com.kkbc.entity.UserToken;
import com.kkbc.service.UserTokenService;

public class UserTokenServiceImpl implements UserTokenService{
	
	@Resource
	private UserTokenDao userTokenDao;

	@Override
	public int saveToken(int user_id, String token) {
		return userTokenDao.saveToken(user_id, token);
	}

	@Override
	public int updateToken(int user_id, String token) {
		return userTokenDao.updateToken(user_id, token);
	}

	@Override
	public UserToken getTokenInfo(String token) {
		return userTokenDao.getTokenInfo(token);
	}

}
