package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestDBxuanba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;  
		try {
			//源数据库
	    	//指定连接类型 
	    	Class.forName("com.mysql.jdbc.Driver");
	    	//获取连接  
	    	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/xuanbatest?useUnicode=true&amp;characterEncoding=UTF-8", "root", "root");
	    	long c=System.currentTimeMillis();
	    	saveDB("C:\\Users\\xu\\Desktop\\test-speed\\files", conn);
	    	System.out.println("总用时:"+(System.currentTimeMillis()-c));
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null&&!conn.isClosed()){
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	public static void saveDB(String filePath,Connection conn){
		PreparedStatement preparedStatement = null;
		StringBuffer insertSql = new StringBuffer();
		try {
			
			String value=readFilePath(filePath);
			if("".equals(value)){
				System.out.println("文件夹下为空");
				return;
			}
			insertSql.append("insert into `tongyongbiao` ")
			.append("( `xiangmu`, `xuexiao`, `userinfo1`, `userinfo2` , `userinfo3`,")
			.append(" `weidu1`, `weidu2`,`tihao`,`daan`, `shijianchuo`,")
			.append(" `fenshu`, `suijitihao`, `tiweiyibiaoshi`, `beiyong1`, `beiyong2`) ")
			.append(" values ");
			insertSql.append(value);
			long c=System.currentTimeMillis();
			preparedStatement = conn.prepareStatement(insertSql.toString());
			int isok = preparedStatement.executeUpdate();
			if(isok>0){
				System.out.println("保存成功,保存用时:"+(System.currentTimeMillis()-c));
			}else{
				System.out.println("保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (SQLException e) {
			}
		}
	}
	
	public static String readFilePath(String filePath){
		StringBuffer result=new StringBuffer();
		long c=System.currentTimeMillis();
		try {
			String encoding = "utf-8";
			File fileP = new File(filePath);
			boolean f=false;
			for(File file:fileP.listFiles()){
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if(f){
						result.append(",");
					}else{
						f=true;
					}
					result.append("('").append(lineTxt).append("')");
				}
				read.close();
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		System.out.println("读取文件用时:"+(System.currentTimeMillis()-c));
		return result.toString().replaceAll("auto\t", "").replaceAll("\t", "','");
	}
}
