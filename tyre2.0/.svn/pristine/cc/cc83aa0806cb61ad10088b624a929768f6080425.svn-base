package com.psylife.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psylife.entity.User;
import com.psylife.vo.MessageVO;
import com.psylife.vo.UserTrucksTyreVO;

 
public interface UserService {
	static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	/**
	 * 登录
	 * @param userName 登录名
	 * @param pwd  密码
	 * @return
	 */
	public User login(String userName, String pwd);
	
	/**
	 * 管理员车轮胎汇总个人页面
	 * @param user_id
	 * @return
	 */
	UserTrucksTyreVO getUserCount(Integer user_id);
	
	/**
	 * 验证用户名
	 * @param loginName
	 * @return
	 */
	User valiLoginName(String loginName);
	
	/**
	 * 个人信息修改
	 * @param user
	 * @return
	 */
	int updateProfile(User user);
	
	/**
	 * 获取新消息
	 * @param user_id
	 * @return
	 */
	List<MessageVO> getMessageList(Integer user_id);

}
