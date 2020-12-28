package com.kkbc.util;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ImportDataByJdbc {
	private static Connection localcon = null;// 创建一个数据库连接
	private static Connection orgDatacon = null;// 创建一个数据库连接
	
		
	public static void main(String[] args) {
		String localmsg=initLocalSource();
		
        // 1. 创建一个顶层容器（窗口）
        JFrame jf = new JFrame("数据备份");          // 创建窗口        
        jf.setLayout(null);
        jf.setSize(300, 300);                       // 设置窗口大小
        jf.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        jf.setResizable(false);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jf.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				closeConnection();
				System.exit(0);
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}
        	
        });

               
      

        // 3. 创建基本组件，并添加到 面板容器 中
        JLabel ipLabel=new JLabel("ip",JLabel.LEFT);          
        JTextField ipTextField=new JTextField(10);
        ipLabel.setBounds(45, 20, 100, 25);
        ipTextField.setBounds(100, 20, 150, 25);
        
        JLabel idLabel=new JLabel("测试id",JLabel.LEFT);        
        JTextField idTextField=new JTextField(10);
        idLabel.setBounds(45, 60, 100, 25);
        idTextField.setBounds(100, 60, 150, 25);
        
        
        JLabel tableLabel=new JLabel("表名",JLabel.LEFT);       
        String[] option = { "affdextable","braintotalscore","device_data","dialtotalscore","expressionactualscore",
        		"expressiontotalscore","test_history","test_info","test_score","test_score_history",
        		"test_score_package","test_user_bind","time_seg","time_seg_history" };
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox tablebox=new JComboBox(option);    //创建JComboBox
        tableLabel.setBounds(45, 100, 100, 25);
        tablebox.setBounds(100, 100, 150, 25);
               
       
        JButton btn = new JButton("开始同步");
        btn.setBounds(150, 140, 100, 25);
        
        JTextArea msgArea=new JTextArea(localmsg);
        msgArea.setBounds(5, 200, 280, 50);
        msgArea.setLineWrap(true);        //激活自动换行功能 
        msgArea.setWrapStyleWord(true);            // 激活断行不断字功能
        if (localmsg==null) {
        	 msgArea.setVisible(false);
		}
        
        btn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) { 
        		String table=tablebox.getSelectedItem().toString();
        		
        		msgArea.setVisible(true);
        		msgArea.setText("同步中:"+table);
        		
        		System.out.println("开始同步"); 	
        		
        		String msg=importData(ipTextField.getText(), idTextField.getText(),table );
        		if (msg!=null) {
        			msgArea.setText("同步失败:"+table+"\r\n"+msg);
				}else {
					msgArea.setText("同步成功:"+table);
				}
        		
        		System.out.println("结束同步");
        		
        		
        		
        	}
        });
        
       
        
        jf.add(ipLabel);
        jf.add(ipTextField);
        jf.add(idLabel);
        jf.add(idTextField);
        jf.add(tableLabel);
        jf.add(tablebox);
        jf.add(btn);
        jf.add(msgArea);
        
        // 5. 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);
    }

	private static String initLocalSource(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/smg_data?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true";
			String user = "root";// 用户名,系统默认的账户名
			String password = "root";// 你安装时选设置的密码
			localcon = DriverManager.getConnection(url, user, password);// 获取连接
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
		return null;
		
	}
	
	
 
	private static String importData(String ip,String testId,String table) {
		
		
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet columnResult = null;// 创建一个结果集对象
		
		PreparedStatement datapre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet dataResult = null;// 创建一个结果集对象
		
		PreparedStatement insertpre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet insertResult = null;// 创建一个结果集对象

		try {
			Class.forName("net.sf.log4jdbc.DriverSpy");// 加载驱动程序
			String url = "jdbc:mysql://"+ip+":3306/smg_data?useUnicode=true&amp;characterEncoding=utf-8&amp;autoReconnect=true";
			String user = "root";// 用户名,系统默认的账户名
			String password = "root";// 你安装时选设置的密码
			orgDatacon = DriverManager.getConnection(url, user, password);// 获取连接
			
			//获取该表的所有列名
			String columnNameSql="select group_concat(CONCAT('`',COLUMN_NAME,'`') Separator ',') as COLUMN_NAME from information_schema.COLUMNS where table_name = '"+table+"' and table_schema = 'smg_data' ";
			pre = localcon.prepareStatement(columnNameSql);//
			columnResult=pre.executeQuery();
			String columnName="";
			while (columnResult.next()) {
				columnName=columnResult.getString(1);
			}
			
			String[] columnNameArray=columnName.split(",");
			
			
			
			//String columnNameSql="select COLUMN_NAME from information_schema.COLUMNS where table_name = '"+table+"' and table_schema = 'smg_data' ";
			
			//获取该表的数据
			String dataSql="select "+columnName+" from "+table+" where test_id="+testId;
			datapre=orgDatacon.prepareStatement(dataSql);
			dataResult=datapre.executeQuery();
			
			
			//插入到本库
			localcon.setAutoCommit(false);
			String insertSql="insert into "+table+" ("+columnName+") values(";
			for (int i=0;i<columnNameArray.length;i++) {
				insertSql+="?";
				if(i!=columnNameArray.length-1){
					insertSql+=",";
				}
				
			}
			insertSql+=")";
			System.out.println(insertSql);
			insertpre=localcon.prepareStatement(insertSql);
			
			while (dataResult.next()) {
				for (int i=0;i<columnNameArray.length;i++) {
					String columnNameTmp=columnNameArray[i];
					String column=columnNameTmp.substring(1, columnNameTmp.length()-1);
					insertpre.setString(i+1, dataResult.getString(column));
				}
				insertpre.addBatch();
				
			}
			
			insertpre.executeBatch();
			localcon.commit();
				
 
 
		} catch (Exception e) {
			e.printStackTrace();
			try {
				localcon.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return e.getMessage();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (columnResult != null)
					columnResult.close();
				if (dataResult != null)
					dataResult.close();
				if (insertResult != null)
					insertResult.close();
				if (pre != null)
					pre.close();
				if (datapre != null)
					datapre.close();
				if (insertpre != null)
					insertpre.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	
	/**释放数据库链接资源：注意数据库资源关闭的顺序*/
	public static void closeConnection(){
		
		try { //注意关闭数据库资源的先后顺序
			if(localcon!=null){
				localcon.close();
			}
			if(orgDatacon!=null){
				orgDatacon.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
 


	

}
