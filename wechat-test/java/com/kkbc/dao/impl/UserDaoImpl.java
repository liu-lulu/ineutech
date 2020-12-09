package com.kkbc.dao.impl;

import com.kkbc.dao.UserDao;
import com.kkbc.entity.User;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public User getByOpenid(String openId) {
		return (User) getSqlMapClientTemplate().queryForObject("User.getByOpenid", openId);
	}

	@Override
	public int insert(User user) {
		
		return (int)getSqlMapClientTemplate().insert("User.insert", user);
	}

	@Override
	public int update(User user) {
		return (int)getSqlMapClientTemplate().update("User.update", user);
	}

}
