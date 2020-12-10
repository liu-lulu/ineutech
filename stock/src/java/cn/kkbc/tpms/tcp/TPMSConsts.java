package cn.kkbc.tpms.tcp;

import java.io.IOException;
import java.util.Properties;


public class TPMSConsts {
	

	//入库
	public static int TYPE_IN=1;
	//出库
	public static int TYPE_OUT=2;
	
	public static String SPLIT=",";
	public static String CODE_SPLIT=" ";
	
	//操作类型:入库/出库
	public static String COMMAND_TYPE="1";
	//扫描的条形码/条形码还没入库
	public static String COMMAND_CODE="2";
	//(手机)设备的uuid
	public static String COMMAND_UUID="3";
	//商品剩余数量
	public static String COMMAND_COUNT="5";
	//入/出库方式(直接扫码入库/扫码手机批量入库)
	public static String COMMAND_TYPE_WAY="6";
	
	//扫码批量手机入库
	public static String COMMAND_PHONE="7";
	
	//直接扫码入库
	public static int WAY_SCAN=1;
	//扫码手机批量入库
	public static int WAY_PHONE=2;
	
	public static String STATUS_SUCESS="1";
	public static String STATUS_FAUSE="2";
	
	public static int tcp_port = 8085;
	// 客户端发呆5分钟后,服务器主动断开连接
	public static int tcp_client_idle_minutes = 1;
	
	public static String CODE_FILE_PATH="D:/barcode/";
	public static String CODE_FILE="D:/barcode/code.txt";
	public static String CODE_FILE_PRENAME="code";
	public static String CODE_FILE_SUFFIX=".txt";
	
	public static String CODE_FILE_NAME="code.txt";
	public static final String END = "\r\n";

	static {
		Properties properties = new Properties();
		try {
			properties.load(TPMSConsts.class.getClassLoader().getResourceAsStream("tcp.properties"));
			tcp_port = Integer.parseInt(properties.getProperty("tcp.port", "8085"));
			tcp_client_idle_minutes = Integer.parseInt(properties.getProperty("tcp.client.maxIdleTime", "1"));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
//		Date today=new Date();
//		File dir=new File(CODE_FILE_PATH);
//		if (!dir.exists()) {
//			dir.mkdirs();
//		}
//		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
//		String fileName=CODE_FILE_PRENAME+formatter.format(today)+CODE_FILE_SUFFIX;
//		String file=CODE_FILE_PATH+fileName;
//		File codeFile=new File(file);
//		if (!codeFile.exists()) {
//			try {
//				codeFile.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		CODE_FILE=file;
//		CODE_FILE_NAME=fileName;
	}



}
