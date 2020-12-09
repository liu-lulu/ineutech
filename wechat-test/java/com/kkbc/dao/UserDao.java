package com.kkbc.dao;

import com.kkbc.entity.User;

public interface UserDao {
	User getByOpenid(String openId);
	
	int insert(User user);
	
	int update(User user);
	
}
