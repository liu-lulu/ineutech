package com.ineutech.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class DataBaseUtil {
	
	public static void main(String[] args) {
		backUp();
	}
	public static boolean backUp() {
		boolean isSuccess = false;
		try {
			long current = System.currentTimeMillis();
			Runtime runtime = Runtime.getRuntime();
			String command = getCommand(getConfig());
			System.out.println(command);
			Process exec = runtime.exec(new String[]{"cmd","/C",command});
			
			   BufferedReader br = new BufferedReader(new InputStreamReader(exec
                       .getInputStream()));
               String inline;
               while ((inline = br.readLine()) != null) {
                   System.out.println(inline);
                   
               }
               br.close();
           
           
			exec.waitFor();
			int status = exec.exitValue();
			if (status == 0) {
				isSuccess = true;
			}
			System.out.println(status);
			System.out.println("数据库备份用时:" + (float)(System.currentTimeMillis() - current) / 1000.0F + "秒");
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	/**
	 * 获取数据库备份命令
	 * @param properties
	 * @return
	 */
	private static String getCommand(Properties properties) {

		StringBuffer command = new StringBuffer("");
		String host = "localhost";
		String port = properties.getProperty("db.port");
		String username = properties.getProperty("db.username");
		String password = properties.getProperty("db.password");
		String dbname = properties.getProperty("db.dbname");

		String backupHost = properties.getProperty("backup.db.host");
		String backupPort = properties.getProperty("backup.db.port");
		String backupUsername = properties.getProperty("backup.db.username");
		String backupPassword = properties
				.getProperty("backup.db.password");
		String backupuserdbname = properties.getProperty("backup.db.dbname");

		command.append("mysqldump --host=").append(host).append(" --port=").append(port).append(" -u").append(username).append(" -p").append(password)
				.append(" --opt ")
				.append(dbname).append("| mysql --host=").append(backupHost).append(" --port=").append(backupPort)
				.append(" -u").append(backupUsername).append(" -p")
				.append(backupPassword).append(" -C ")
				.append(backupuserdbname);
		return command.toString();
	}

	/**
	 * 获取数据库属性
	 * @return
	 */
	public static Properties getConfig() {
		Properties properties = null;
		try {
			InputStream is = DataBaseUtil.class.getClassLoader()
					.getResourceAsStream("db.properties");
			properties = new Properties();
			properties.load(is);
		} catch (Exception e) {
			e.printStackTrace();
			properties = null;
		}
		return properties;
	}

}
