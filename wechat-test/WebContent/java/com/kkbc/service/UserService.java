package com.kkbc.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.entity.ConvertHistory;
import com.kkbc.entity.PrizeHistory;
import com.kkbc.entity.TransferHistory;
import com.kkbc.entity.User;
import com.kkbc.util.page.ListInfo;

 
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
	 * 验证用户名
	 * @param loginName
	 * @return
	 */
	User valiLoginName(String loginName);
	
	/**
	 * 获取某用户下的A/B区节点
	 * @param userName
	 * @param area
	 * @return
	 */
	List<User> getUserByUserNameAndArea(String userName,String area);
	
	/**
	 * 获取用户下的所有子节点
	 * @return
	 */
	List<User> getAllSon(String userName);
	
	
	/**
	 * 根据二级密码获取用户信息
	 * @param userId
	 * @param pwd2
	 * @return
	 */
	User getByPwd2(int userId,String pwd2);
	
	/**
	 * 币种转换
	 * @param user_name
	 * @param convertMoney
	 * @return
	 */
	int coinConvert(String user_name,float convertMoney );
	
	/**
	 * 币种转换记录
	 * @param user_name
	 * @param currentPageNO
	 * @param pageSize
	 * @return
	 */
	ListInfo<ConvertHistory> convertHistories(String user_name,int currentPageNO,int pageSize);
	
	/**
	 * 修改密码
	 * @param userid
	 * @param type 1:登录密码 2:二级密码
	 * @param newPassword
	 * @return
	 */
	int updPass(int userid,String type,String newPassword);
	
	/**
	 * 更新用户信息
	 * @param userinfo
	 * @return
	 */
	int updInfo(User userinfo);
	
	/**
	 * 注册用户
	 * @param userInfo
	 * @return
	 */
	int registerUser(User userInfo);
	
	/**
	 * 获取推荐的用户
	 * @param referral 推荐人
	 * @param status 0：未激活  1：激活
	 * @return
	 */
	ListInfo<User> getUserByStatus(String referral, String status,int currentPageNO, int pageSize);
	
	/**
	 * 转账
	 * @param from_user_name 转账人用户名
	 * @param to_user_name 接收人用户名
	 * @param coin_type 1:奖金币 2:购物钻
	 * @param money 转账金额
	 * @return
	 */
	int transfer(String from_user_name,String to_user_name,String coin_type,float money);
	
	/**
	 * 转账记录
	 * @param userName 用户名
	 * @param currentPageNO
	 * @param pageSize
	 * @return
	 */
	ListInfo<TransferHistory> transferHistories(String userName,int currentPageNO, int pageSize);
	
	/**
	 * 奖金记录
	 * @param userName
	 * @param currentPageNO
	 * @param pageSize
	 * @return
	 */
	ListInfo<PrizeHistory> prizeHistories(String userName,int currentPageNO, int pageSize);

}
