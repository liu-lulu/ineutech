package com.kkbc.service;

import java.util.List;

import com.kkbc.entity.User;

public interface UserService {
	User login(String loginName,String pwd);
	
	//所有的供应商
	List<User> supplierList();

}
