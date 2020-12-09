package com.kkbc.service.impl;

import javax.annotation.Resource;

import com.kkbc.dao.UserDao;
import com.kkbc.entity.User;
import com.kkbc.service.UserService;
import com.kkbc.vo.Passport;

public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDAO;
	
	@Override
	public Passport loginByUPassport(String loginName, String password) {
		User user=userDAO.loginByUserNamePassword(loginName, password);
		if (user!=null) {
			userDAO.updateLoginTime(user.getUser_id());
			return loginToPassport(user);
		}
		return null;
	}

	@Override
	public User loginByUserName(String loginName) {
		return userDAO.loginByUserName(loginName);
	}

	@Override
	public User loginByUserNamePassword(String loginName, String password) {
		return userDAO.loginByUserNamePassword(loginName, password);
	}
	
	private Passport loginToPassport(User user)
	  {
	    Passport passport;
	    (
	      passport = new Passport())
	      .setLoginName(user.getLogin_name());
	    passport.setUserId(user.getUser_id());
	    passport.setSchoolId(user.getSchool_id());
	    passport.setClassId(user.getUser_class_id());
	    passport.setUserName(user.getTrue_name());
	    passport.setUserSchoolDepart(user.getShool_name());
	    return passport;
	  }


}
