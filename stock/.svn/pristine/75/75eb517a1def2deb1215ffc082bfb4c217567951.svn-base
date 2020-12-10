package com.kkbc.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kkbc.dao.UserDao;
import com.kkbc.entity.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao{

	@Override
	public User login(String loginName, String pwd) {
		User user=new User();
		user.setUser_name(loginName);
		user.setUser_pwd(pwd);
		
		return (User) getSqlMapClientTemplate().queryForObject("User.login", user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> supplierList() {
		return getSqlMapClientTemplate().queryForList("User.supplierList");
	}

}
