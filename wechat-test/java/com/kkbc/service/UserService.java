package com.kkbc.service;

import com.kkbc.entity.User;

public interface UserService {
	
	User getByOpenid(String openId);
	
	int insert(User user);
	
	int update(User user);

}
