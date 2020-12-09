package com.kkbc.service;

import com.kkbc.entity.User;
import com.kkbc.vo.Passport;

public interface UserService {

	Passport loginByUPassport(String loginName, String password);

	User loginByUserName(String loginName);

	User loginByUserNamePassword(String loginName, String password);

}
