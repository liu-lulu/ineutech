package com.kkbc.service;

import com.kkbc.entity.UserToken;

public interface UserTokenService {
	
	/**
	 * 用户首次登陆成功后,保存用户token
	 * @param user_id
	 * @param token
	 * @return
	 */
	int saveToken(int user_id,String token);
	
	/**
	 * 用户重新登陆后，更新用户token信息
	 * @param user_id
	 * @param token
	 * @return
	 */
	int updateToken(int user_id,String token);
	
	/**
	 * 获取token信息
	 * @param token
	 * @return
	 */
	UserToken getTokenInfo(String token);
	

}
