package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kkbc.dao.UserDao;
import com.kkbc.entity.ConvertHistory;
import com.kkbc.entity.PrizeHistory;
import com.kkbc.entity.TransferHistory;
import com.kkbc.entity.User;
import com.kkbc.service.UserService;
import com.kkbc.util.page.ListInfo;

@Service
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;

	@Override
	public User login(String userName, String pwd) {
		return userDao.login(userName, pwd);
	}

	@Override
	public User valiLoginName(String loginName) {
		return userDao.valiLoginName(loginName);
	}

	@Override
	public List<User> getUserByUserNameAndArea(String userName, String area) {
		return userDao.getUserByUserNameAndArea(userName, area);
	}

	@Override
	public List<User> getAllSon(String userName) {
		return userDao.getAllSon(userName);
	}

	@Override
	public User getByPwd2(int userId, String pwd2) {
		return userDao.getByPwd2(userId, pwd2);
	}

	@Override
	public int coinConvert(String user_name, float convertMoney) {
		return userDao.coinConvert(user_name, convertMoney);
	}

	@Override
	public ListInfo<ConvertHistory> convertHistories(String user_name,
			int currentPageNO, int pageSize) {
		return userDao.convertHistories(user_name, currentPageNO, pageSize);
	}

	@Override
	public int updPass(int userid, String type, String newPassword) {
		return userDao.updPass(userid, type, newPassword);
	}

	@Override
	public int updInfo(User userinfo) {
		return userDao.updInfo(userinfo);
	}

	@Override
	public int registerUser(User userInfo) {
		return userDao.registerUser(userInfo);
	}

	@Override
	public ListInfo<User> getUserByStatus(String referral, String status,int currentPageNO, int pageSize) {
		return userDao.getUserByStatus(referral, status,currentPageNO,pageSize);
	}

	@Override
	public int transfer(String from_user_name, String to_user_name,
			String coin_type, float money) {
		return userDao.transfer(from_user_name, to_user_name, coin_type, money);
	}

	@Override
	public ListInfo<TransferHistory> transferHistories(String userName,
			int currentPageNO, int pageSize) {
		return userDao.transferHistories(userName, currentPageNO, pageSize);
	}

	@Override
	public ListInfo<PrizeHistory> prizeHistories(String userName,
			int currentPageNO, int pageSize) {
		return userDao.prizeHistories(userName, currentPageNO, pageSize);
	}
	

}
