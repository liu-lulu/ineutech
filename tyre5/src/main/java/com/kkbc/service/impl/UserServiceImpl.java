package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kkbc.dao.UserDao;
import com.kkbc.entity.User;
import com.kkbc.service.UserService;
import com.kkbc.vo.MessageVO;
import com.kkbc.vo.UserTrucksTyreVO;

@Service
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;

	@Override
	public User login(String userName, String pwd) {
		return userDao.login(userName, pwd);
	}

	@Override
	public UserTrucksTyreVO getUserCount(Integer user_id) {
		return userDao.getUserCount(user_id);
	}

	@Override
	public User valiLoginName(String loginName) {
		return userDao.valiLoginName(loginName);
	}

	@Override
	public int updateProfile(User user) {
		return userDao.updateProfile(user);
	}

	@Override
	public List<MessageVO> getMessageList(Integer user_id) {
		return userDao.getMessageList(user_id);
	}
	

}
