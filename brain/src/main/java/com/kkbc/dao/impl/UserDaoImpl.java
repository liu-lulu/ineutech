package com.kkbc.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kkbc.dao.UserDao;
import com.kkbc.entity.User;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public User loginByUserName(String userName) {
		
		return (User) getSqlMapClientTemplate().queryForObject("User.valiLoginName", userName);
	}

	@Override
	public User loginByUserNamePassword(String userName, String password) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("loginName", userName);
		map.put("password", password);
		return (User) getSqlMapClientTemplate().queryForObject("User.login", map);
	}

	@Override
	public int updateLoginTime(Long userId) {
		User user=new User();
		user.setUser_id(userId);
		user.setLast_login_time(new Date());
		return getSqlMapClientTemplate().update("User.updLoginTime", user);
	}

}
