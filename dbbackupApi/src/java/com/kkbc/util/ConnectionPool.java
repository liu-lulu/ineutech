package com.kkbc.util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionPool {

	private ConnectionPool() {

	}

	public static synchronized Connection getConnection() {
		return getNewConnection();
	}

	private static Connection getNewConnection() {

		Connection con = null;
		try {
			Context initial = new InitialContext();
			// 其中mysql为数据源jndi名称
			DataSource ds = (DataSource) initial
					.lookup("java:comp/env/jdbc/DBPOOL_tyre");
			con = ds.getConnection();

		} catch (Exception e) {
			System.out.println("JNDI link Database ERR " + e.getMessage());
		}
		 System.out.println(" GET Link ["+(con==null?"NULL":"OK")+"] stat");

		return con;
	}

	public static synchronized void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
