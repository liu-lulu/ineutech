package com.kkbc.service.impl;

import javax.annotation.Resource;

import com.kkbc.dao.UserDao;
import com.kkbc.entity.User;
import com.kkbc.service.UserService;

public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	
	@Override
	public User getByOpenid(String openId) {
		return userDao.getByOpenid(openId);
	}

	@Override
	public int insert(User user) {
		return userDao.insert(user);
	}

	@Override
	public int update(User user) {
		return userDao.update(user);
	}

}
