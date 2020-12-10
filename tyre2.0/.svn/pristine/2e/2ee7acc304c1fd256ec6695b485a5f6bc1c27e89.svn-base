package com.psylife.service.impl;

import java.util.List;

import com.psylife.dao.UserDao;
import com.psylife.dao.impl.UserDaoImpl;
import com.psylife.entity.User;
import com.psylife.service.UserService;
import com.psylife.vo.MessageVO;
import com.psylife.vo.UserTrucksTyreVO;

 
public class UserServiceImpl implements UserService{

	private UserDao dao=(UserDao)new UserDaoImpl();

	@Override
	public User login(String userName, String pwd) {
		return dao.login(userName, pwd);
	}

	@Override
	public UserTrucksTyreVO getUserCount(Integer user_id) {
		return dao.getUserCount(user_id);
	}

	@Override
	public User valiLoginName(String loginName) {
		return dao.valiLoginName(loginName);
	}

	@Override
	public int updateProfile(User user) {
		return dao.updateProfile(user);
	}

	@Override
	public List<MessageVO> getMessageList(Integer user_id) {
		return dao.getMessageList(user_id);
	}
	

}
