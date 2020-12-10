package com.kkbc.dao.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kkbc.dao.UserDao;
import com.kkbc.entity.User;
import com.kkbc.vo.MessageVO;
import com.kkbc.vo.UserTrucksTyreVO;
import com.psylife.util.Constants;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {
	static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	@Override
	public User login(String loginName, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loginName", loginName);
		map.put("pwd", pwd);
		map.put("status", Constants.STATUS_NORMAL);
		User user = (User) getSqlMapClientTemplate().queryForObject("User.login", map);
		
		return user;
	}
	
	@Override
	public User valiLoginName(String loginName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loginName", loginName);
		map.put("status", Constants.STATUS_NORMAL);
		User user = (User) getSqlMapClientTemplate().queryForObject("User.valiLoginName", map);
		
		return user;
	}
	
	@Override
	public UserTrucksTyreVO getUserCount(Integer user_id){
		UserTrucksTyreVO userTrucksTyreVO = (UserTrucksTyreVO) getSqlMapClientTemplate().queryForObject("UserTrucksTyreVO.getUserCount", user_id);
		
		logger.info("管理员车轮胎汇总个人页面user_id:"+user_id);
		return userTrucksTyreVO;
	}
	
	
	@Transactional
	@Override
	public List<MessageVO> getMessageList(Integer user_id){
		@SuppressWarnings("unchecked")
		List<MessageVO> messages=getSqlMapClientTemplate().queryForList("MessageVO.getMessageList", user_id);
		
		if (messages != null && messages.size() > 0) {
			getSqlMapClientTemplate().insert("MessageVO.saveToM_del", user_id);
			getSqlMapClientTemplate().delete("MessageVO.deleteFromM", user_id);
		}
		logger.info("获取新消息user_id:"+user_id);
		return messages;
	}
	
	@Transactional
	@Override
	public int updateProfile(User user){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("user_id", user.getUser_id());
		params.put("status", Constants.STATUS_NORMAL);
		User user2=(User) getSqlMapClientTemplate().queryForObject("User.queryUserFace", params);
		if (user2!=null) {
			String user_face=user2.getUser_face();
			if(user.getUser_face()!=null&&!"".equals(user.getUser_face())){
				if(user_face!=null&&!"".equals(user_face)){
					File file = new File(Constants.UPLOADURL, user_face);
					if(file.exists()&&file.isFile()){
						file.delete();
					}
				}
			}else{
				user.setUser_face(user_face);
			}
		}else{
			return 1;//不存在
		}
		
		int a=getSqlMapClientTemplate().update("User.updateInfo", user);
		if(a<=0){
//				connection.rollback();
			return 2;//保存失败
		}
//			connection.commit();
		logger.info("个人信息修改:"+user.getUser_id());
		return 0;			
	
	}

}
