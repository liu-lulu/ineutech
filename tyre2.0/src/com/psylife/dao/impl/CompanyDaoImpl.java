package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.psylife.dao.CompanyDao;
import com.psylife.entity.Company;
import com.psylife.entity.User;
import com.psylife.util.ConnectionPool;
import com.psylife.util.ResultSetUtil;
import com.psylife.util.StringHelper;

public class CompanyDaoImpl extends BaseDaoImpl implements CompanyDao{

	@Override
	public List<Company> allCompanyList(int companyId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT * FROM  company c1 where c1.company_id=? union SELECT * FROM company c2  where c2.parent_id=?";
		List<Company> companies=null;
		
		try {
			connection=ConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, companyId);
			preparedStatement.setInt(2, companyId);
			resultSet=preparedStatement.executeQuery();
			companies=ResultSetUtil.getByList(resultSet, "company_id,company,create_time,status,remark,parent_id,tyre_safe_depth".split(","), "company_id,company,create_time,status,remark,parent_id,tyre_safe_depth".split(","), Company.class, false);
			
			logger.info("获取公司列表成功！");
		} catch (SQLException e) {
			logger.info("获取公司列表失败："+StringHelper.getTrace(e));
		}
		return companies;
	}

	@Override
	public User getCompanyId(String companyName, int parentId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		String sql = "select U.* from company C LEFT JOIN user U on U.user_company_id=C.company_id WHERE (C.company_id="+parentId+" or C.parent_id="+parentId+") and C.company='"+companyName+"' and U.user_role=1 limit 1";
		User user=null;
		
		try {
			connection=ConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			resultSet=preparedStatement.executeQuery();
			user=ResultSetUtil.getByOne(resultSet, "user_id,user_name,user_company,user_company_id".split(","), "user_id,user_name,user_company,user_company_id".split(","), User.class, false);
			if (user!=null) {
				logger.info("获取公司成功！");
			}
		} catch (SQLException e) {
			logger.info("获取公司失败："+StringHelper.getTrace(e));
		}
		return user;
	}
	
	

}
