package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.psylife.dao.SpecificationDao;
import com.psylife.entity.Specification;
import com.psylife.entity.TyreBrand;
import com.psylife.util.ConnectionPool;
import com.psylife.util.PinYinUtil;
import com.psylife.util.ResultSetUtil;
import com.psylife.util.StringHelper;

 
public class SpecificationDaoImpl implements SpecificationDao{

	@Override
	public List<Specification> getSpecificationList() {
		List<Specification> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT id,name FROM specification ORDER BY create_time DESC ");
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "id,name".split(","), "id,name".split(","), Specification.class, false);
			logger.info("获取轮胎规格列表成功！");
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("获取轮胎规格列表失败！："+StringHelper.getTrace(e));
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
		return list;
	}
	
	@Override
	public List<TyreBrand> getTyreBrandList() {
		List<TyreBrand> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT id,name,pin_yin FROM tyre_brand ORDER BY create_time DESC ");
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "id,name,pin_yin".split(","), "id,name,pin_yin".split(","), TyreBrand.class, false);
			logger.info("获取轮胎品牌列表成功！");
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("获取轮胎品牌列表失败！："+StringHelper.getTrace(e));
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
		return list;
	}
	
	@Override
	public List<Specification> getPatternCodeList() {
		List<Specification> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT id,name FROM pattern_code ORDER BY create_time DESC ");
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "id,name".split(","), "id,name".split(","), Specification.class, false);
			logger.info("获取轮胎花纹代码列表成功！");
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("获取轮胎花纹代码列表失败！："+StringHelper.getTrace(e));
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
		return list;
	}
	
	
	@Override
	public int addPatternCode(String name){
		if (StringUtils.isEmpty(name)) {
			return 0;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		String getByName = "SELECT id,name FROM pattern_code where name=?";
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(getByName);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				logger.info("花纹代码已存在:"+name);
				return 1;
			}
			String addPatternCode="INSERT INTO pattern_code(`name`,`create_time`) VALUES (?,?)";
			preparedStatement1 = connection.prepareStatement(addPatternCode,Statement.RETURN_GENERATED_KEYS);
			preparedStatement1.setString(1, name);
			preparedStatement1.setObject(2, new Date());
			preparedStatement1.executeUpdate();
			resultSet1=preparedStatement1.getGeneratedKeys();
			if (resultSet1.next()) {
				logger.info("花纹代码添加成功:"+name);
				return 2;
			}
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error(name+"花纹代码添加失败！："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (resultSet1 != null && !resultSet1.isClosed()) {
					resultSet1.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
				if (preparedStatement1 != null && !preparedStatement1.isClosed()) {
					preparedStatement1.close();
					preparedStatement1=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
	return 0;
	}
	
	@Override
	public int addTyreBrand(String name){
		if (StringUtils.isEmpty(name)) {
			return 0;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		String getByName = "SELECT id,name FROM tyre_brand where name=?";
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(getByName);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				logger.info("品牌已存在:"+name);
				return 1;
			}
			String addPatternCode="INSERT INTO tyre_brand(`name`,`create_time`,`pin_yin`) VALUES (?,?,?)";
			preparedStatement1 = connection.prepareStatement(addPatternCode,Statement.RETURN_GENERATED_KEYS);
			preparedStatement1.setString(1, name);
			preparedStatement1.setObject(2, new Date());
			preparedStatement1.setString(3, PinYinUtil.getFullSpell(name));
			preparedStatement1.executeUpdate();
			resultSet1=preparedStatement1.getGeneratedKeys();
			if (resultSet1.next()) {
				logger.info("品牌添加成功:"+name);
				return 2;
			}
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error(name+"品牌添加失败！："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (resultSet1 != null && !resultSet1.isClosed()) {
					resultSet1.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
				if (preparedStatement1 != null && !preparedStatement1.isClosed()) {
					preparedStatement1.close();
					preparedStatement1=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
	return 0;
	}
	

	@Override
	public int addSpecification(String name){
		if (StringUtils.isEmpty(name)) {
			return 0;
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		String getByName = "SELECT id,name FROM specification where name=?";
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(getByName);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				logger.info("轮胎规格已存在:"+name);
				return 1;
			}
			String addPatternCode="INSERT INTO specification(`name`,`create_time`) VALUES (?,?)";
			preparedStatement1 = connection.prepareStatement(addPatternCode,Statement.RETURN_GENERATED_KEYS);
			preparedStatement1.setString(1, name);
			preparedStatement1.setObject(2, new Date());
			preparedStatement1.executeUpdate();
			resultSet1=preparedStatement1.getGeneratedKeys();
			if (resultSet1.next()) {
				logger.info("轮胎规格添加成功:"+name);
				return 2;
			}
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error(name+"轮胎规格添加失败！："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (resultSet1 != null && !resultSet1.isClosed()) {
					resultSet1.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
				if (preparedStatement1 != null && !preparedStatement1.isClosed()) {
					preparedStatement1.close();
					preparedStatement1=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
	return 0;
	}

}
