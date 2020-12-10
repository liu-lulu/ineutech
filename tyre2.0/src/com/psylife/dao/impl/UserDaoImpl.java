package com.psylife.dao.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.psylife.dao.UserDao;
import com.psylife.entity.User;
import com.psylife.util.ConnectionPool;
import com.psylife.util.Constants;
import com.psylife.util.ResultSetUtil;
import com.psylife.util.StringHelper;
import com.psylife.vo.MessageVO;
import com.psylife.vo.UserTrucksTyreVO;

 
public class UserDaoImpl implements UserDao{

	@Override
	public User login(String loginName, String pwd) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT U.user_id,U.user_name,C.company as user_company,C.company_id as user_company_id,U.user_phone,U.user_role,U.sex,U.true_name,U.create_time,U.`status`,U.user_face "+
                    "FROM `user` U INNER JOIN company C ON C.company_id=U.user_company_id WHERE U.user_name=? and U.user_password=? and U.`status`=?   LIMIT 1");
		User user = null;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setString(1, loginName);
			preparedStatement.setString(2, pwd);
			preparedStatement.setInt(3, Constants.STATUS_NORMAL);
//			preparedStatement.setInt(4, userRole);
			resultSet = preparedStatement.executeQuery();
			user=ResultSetUtil.getByOne(resultSet, "user_id,user_name,user_company,user_company_id,user_phone,user_role,sex,true_name,create_time,status,user_face".split(","), 
					"user_id,user_name,user_company,user_company_id,user_phone,user_role,sex,true_name,create_time,status,user_face".split(","), User.class, false);
		} catch (Exception e) {
			logger.error("登录："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return user;
	}
	
	@Override
	public User valiLoginName(String loginName) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT U.user_id,U.user_name,C.company as user_company,C.company_id as user_company_id,U.user_phone,U.user_role,U.sex,U.true_name,U.create_time,U.`status` "+
                    "FROM `user` U INNER JOIN company C ON C.company_id=U.user_company_id WHERE U.user_name=? and U.`status`=?   LIMIT 1");
		User user = null;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setString(1, loginName);
			preparedStatement.setInt(2, Constants.STATUS_NORMAL);
			resultSet = preparedStatement.executeQuery();
			user=ResultSetUtil.getByOne(resultSet, "user_id,user_name,user_company,user_company_id,user_phone,user_role,sex,true_name,create_time,status".split(","), 
					"user_id,user_name,user_company,user_company_id,user_phone,user_role,sex,true_name,create_time,status".split(","), User.class, false);
		} catch (Exception e) {
			logger.error("验证用户名是否存在："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return user;
	}
	
	@Override
	public UserTrucksTyreVO getUserCount(Integer user_id){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT (SELECT COUNT(DISTINCT T.trucks_id) FROM trucks T LEFT JOIN `user` U ON U.user_company_id=T.company_id WHERE U.user_id=?) as trucksCount,")
              .append("(SELECT COUNT(DISTINCT TB.tyre_id) FROM tyre_base TB LEFT JOIN `user` U ON U.user_id=TB.user_id LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id")
              .append(" WHERE UU.user_id=? and TB.`status`!=0) as tyreCount")
              .append(",(SELECT COUNT(DISTINCT TB.tyre_id) FROM tyre_base TB LEFT JOIN `user` U ON U.user_id=TB.user_id LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id")
              .append(" WHERE UU.user_id=? and TB.`tyre_flag`=0 and TB.`status`=1) as newTyreCount")
              .append(",(SELECT COUNT(DISTINCT TB.tyre_id) FROM tyre_base TB LEFT JOIN `user` U ON U.user_id=TB.user_id LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id")
              .append(" WHERE UU.user_id=? and TB.`tyre_flag`=0 and (TB.`status`!=0 AND TB.`status`!=1)) as oldTyreCount")
              .append(",(SELECT COUNT(DISTINCT TB.tyre_id) FROM tyre_base TB LEFT JOIN `user` U ON U.user_id=TB.user_id LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id")
              .append(" WHERE UU.user_id=? and TB.`status`=0 ) as baofeiTyreCount")
              .append(",(SELECT COUNT(DISTINCT TB.tyre_id) FROM tyre_base TB LEFT JOIN `user` U ON U.user_id=TB.user_id LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id")
              .append(" WHERE UU.user_id=? and TB.`status`=2 ) as xiubuTyreCount")
              .append(",(SELECT COUNT(DISTINCT TB.tyre_id) FROM tyre_base TB LEFT JOIN `user` U ON U.user_id=TB.user_id LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id")
              .append(" WHERE UU.user_id=? and TB.`status`=3 ) as fanxinTyreCount")
              .append(",(SELECT COUNT(DISTINCT TB.tyre_id) FROM tyre_base TB LEFT JOIN `user` U ON U.user_id=TB.user_id LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id")
              .append(" WHERE UU.user_id=? and TB.`status`=4 ) as qianzhuangTyreCount")
              .append(",(SELECT SUM(tyre_health)/COUNT(*) FROM (SELECT DISTINCT TB.tyre_id,TD.tyre_health FROM tyre_dynamic TD LEFT JOIN tyre_base TB ON TB.tyre_id=TD.tyre_id LEFT JOIN `user` U ON U.user_id=TB.user_id LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id")
              .append(" WHERE UU.user_id=? and TB.`status`!=0 and TD.tyre_health!='') T) as health")
              .append(",(SELECT P.phone FROM `user` U LEFT JOIN team T ON T.c_id=U.user_company_id LEFT JOIN person P ON T.p_id=P.id WHERE P.job='客服' AND U.user_id=? LIMIT 1) as service_phone");
		UserTrucksTyreVO userTrucksTyreVO = new UserTrucksTyreVO();
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, user_id);
			preparedStatement.setInt(3, user_id);
			preparedStatement.setInt(4, user_id);
			preparedStatement.setInt(5, user_id);
			preparedStatement.setInt(6, user_id);
			preparedStatement.setInt(7, user_id);
			preparedStatement.setInt(8, user_id);
			preparedStatement.setInt(9, user_id);
			preparedStatement.setInt(10, user_id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				userTrucksTyreVO.setTrucksCount(resultSet.getInt("trucksCount"));
				userTrucksTyreVO.setTyreCount(resultSet.getInt("tyreCount"));
				userTrucksTyreVO.setNewTyreCount(resultSet.getInt("newTyreCount"));
				userTrucksTyreVO.setOldTyreCount(resultSet.getInt("oldTyreCount"));
				userTrucksTyreVO.setBaofeiTyreCount(resultSet.getInt("baofeiTyreCount"));
				userTrucksTyreVO.setXiubuTyreCount(resultSet.getInt("xiubuTyreCount"));
				userTrucksTyreVO.setFanxinTyreCount(resultSet.getInt("fanxinTyreCount"));
				userTrucksTyreVO.setQianzhuangTyreCount(resultSet.getInt("qianzhuangTyreCount"));
				userTrucksTyreVO.setHealth(resultSet.getFloat("health"));
				userTrucksTyreVO.setService_phone(resultSet.getString("service_phone"));
			}else{
				userTrucksTyreVO.setTrucksCount(0);
				userTrucksTyreVO.setTyreCount(0);
				userTrucksTyreVO.setNewTyreCount(0);
				userTrucksTyreVO.setOldTyreCount(0);
				userTrucksTyreVO.setBaofeiTyreCount(0);
				userTrucksTyreVO.setXiubuTyreCount(0);
				userTrucksTyreVO.setFanxinTyreCount(0);
				userTrucksTyreVO.setQianzhuangTyreCount(0);
				userTrucksTyreVO.setHealth(0);
			}
			connection.commit();
			logger.info("管理员车轮胎汇总个人页面user_id:"+user_id);
		} catch (Exception e) {
			logger.error("管理员车轮胎汇总个人页面："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return userTrucksTyreVO;
	}
	
	
	@Override
	public List<MessageVO> getMessageList(Integer user_id){
		Connection connection = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		List<MessageVO> messages=null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement1 = connection.prepareStatement("SELECT type,COUNT(DISTINCT id) as count,content,remark FROM message WHERE user_id=? GROUP BY type,remark");
			preparedStatement1.setInt(1, user_id);
			
			resultSet1 = preparedStatement1.executeQuery();
			messages=ResultSetUtil.getByList(resultSet1, "type,count,content,remark".split(","), 
					"type,count,content,remark".split(","), MessageVO.class, false);
			
			if (messages != null && messages.size() > 0) {
				String sql="insert into message_del select * from message where user_id="+user_id+";";
				String dSql="delete from message where user_id="+ user_id+";";
				preparedStatement2=connection.prepareStatement(sql);
				preparedStatement2.executeUpdate();
				preparedStatement3 = connection.prepareStatement(dSql);
				preparedStatement3.executeUpdate();
			}
			connection.commit();
			logger.info("获取新消息user_id:"+user_id);
		} catch (Exception e) {
			logger.error("获取新消息："+StringHelper.getTrace(e));
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (resultSet1 != null && !resultSet1.isClosed()) {
					resultSet1.close();
				}
				if (preparedStatement1 != null && !preparedStatement1.isClosed()) {
					preparedStatement1.close();
					preparedStatement1=null;
				}
				if (preparedStatement2 != null && !preparedStatement2.isClosed()) {
					preparedStatement2.close();
					preparedStatement2=null;
				}
				if (preparedStatement3 != null && !preparedStatement3.isClosed()) {
					preparedStatement3.close();
					preparedStatement3=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		return messages;
	}
	
	@Override
	public int updateProfile(User user){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("SELECT user_id,user_face FROM `user` WHERE `status`=? AND user_id=?");
			preparedStatement.setInt(1, Constants.STATUS_NORMAL);
			preparedStatement.setInt(2, user.getUser_id());
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				String user_face=resultSet.getString("user_face");
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
			preparedStatement1 = connection.prepareStatement("update `user` set user_face=?,true_name=?,user_phone=? where user_id=?");
			preparedStatement1.setString(1,user.getUser_face());
			preparedStatement1.setString(2,user.getTrue_name());
			preparedStatement1.setString(3,user.getUser_phone());
			preparedStatement1.setInt(4, user.getUser_id());
			int a=preparedStatement1.executeUpdate();	
			if(a<=0){
				connection.rollback();
				return 2;//保存失败
			}
			connection.commit();
			logger.info("个人信息修改:"+user.getUser_id());
			return 0;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("个人信息修改:"+user.getUser_id()+StringHelper.getTrace(e));
		}finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
					resultSet=null;
				}
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
					preparedStatement=null;
				}
				if(preparedStatement1!=null && !preparedStatement1.isClosed()){
					preparedStatement1.close();
					preparedStatement1=null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
		}
		return 2;
	}

}
