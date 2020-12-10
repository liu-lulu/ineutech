package com.kkbc.cons;

import io.netty.channel.Channel;

import java.io.IOException;
import java.util.Properties;




public class TCPConsts {
	
	//扫描的条形码/条形码还没入库
	public static final String COMMAND_CODE = "2";
	//商品剩余数量
	public static String COMMAND_COUNT="5";
	//扫码批量手机入库
	public static String COMMAND_PHONE="7";
	
	//数据包结尾符
	public static final String END = "\r\n";
	
	public static final String SPLIT = ",";
	
	public static String tcp_ip = "localhost";
	public static int tcp_port = 8085;
	
	public static Channel channel;
	
	
	static {
		Properties properties = new Properties();
		try {
			properties.load(TCPConsts.class.getClassLoader().getResourceAsStream("tcp.properties"));
			tcp_ip = properties.getProperty("tcp.ip", "localhost");
			tcp_port = Integer.parseInt(properties.getProperty("tcp.port", "8085"));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
