package com.kkbc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.web.multipart.MultipartFile;

import com.ibatis.common.jdbc.ScriptRunner;
import com.ibatis.common.resources.Resources;

public class RunSqlScript {
	private static String driver="";
	private static String url = "";
	private static String username ="";
    private static String password = "";
    private static Connection conn;
	static{
		
		 // 获取数据库相关配置信息
        Properties props;
		try {
			props = Resources.getResourceAsProperties("db.properties");
			// jdbc 连接信息: 注: 现在版本的JDBC不需要配置driver，因为不需要Class.forName手动加载驱动
	         driver=props.getProperty("db.dirverClass");
	         url = props.getProperty("db.url");
	         username = props.getProperty("db.username");
	         password = props.getProperty("db.password");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
	}
	/**
      * <p>运行指定的sql脚本
      * @param sqlFileName 需要执行的sql脚本的名字
      */
     public static boolean run(MultipartFile sqlFile) {
         try {
        	 Class.forName(driver);
             // 建立连接
             conn = DriverManager.getConnection(url, username, password);
             // 创建ScriptRunner，用于执行SQL脚本
             ScriptRunner runner = new ScriptRunner(conn, false, false);
             Resources.setCharset(Charset.forName("UTF-8"));
             runner.setErrorLogWriter(null);
             runner.setLogWriter(null);
             // 执行SQL脚本
             Reader rdReader=new BufferedReader(new InputStreamReader(sqlFile.getInputStream(),"utf-8"));
             runner.runScript(rdReader);
//             runner.runScript(Resources.getResourceAsReader("sql/" + sqlFileName + ".sql"));
 
             // 关闭连接
             conn.close();
 
             // 若成功，打印提示信息
             System.out.println("====== SUCCESS ======");
             return true;
         } catch (IOException | SQLException | ClassNotFoundException e) {
             e.printStackTrace();
             return false;
         } 
     }
     
     public static void main(String[] args) {
		
	}

}
