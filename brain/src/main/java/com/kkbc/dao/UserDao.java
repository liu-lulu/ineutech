package com.kkbc.dao;

import com.kkbc.entity.User;

public interface UserDao {

	 User loginByUserName(String userName);

	 User loginByUserNamePassword(String userName, String password);
	 
	 int updateLoginTime(Long userId);
}
