package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kkbc.dao.UserDao;
import com.kkbc.entity.User;
import com.kkbc.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao; 

	@Override
	public User login(String loginName, String pwd) {
		return userDao.login(loginName, pwd);
	}

	@Override
	public List<User> supplierList() {
		return userDao.supplierList();
	}

}
