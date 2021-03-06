package cn.ineutech.tpms.common;

import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @name: Consts 
 * @description: TCP服务监听的端口初始化
 * @date 2018年2月1日 上午11:37:54
 * @author liululu
 */
public class Consts {

	public static int tcp_port = 8082;
	public static int brain_tcp_port = 8080;
	public static int console_tcp_port = 8083;
	// 客户端发呆50秒后,服务器主动断开连接
	public static int tcp_client_idle_second = 50;

	static {
		Properties properties = new Properties();
		try {
			properties.load(Consts.class.getClassLoader().getResourceAsStream("tcp.properties"));
			tcp_port = Integer.parseInt(properties.getProperty("tcp.port", "8082"));
			brain_tcp_port=Integer.parseInt(properties.getProperty("brain.tcp.port", "8080"));
			tcp_client_idle_second = Integer.parseInt(properties.getProperty("tcp.client.maxIdleTime", "50"));
			console_tcp_port=Integer.parseInt(properties.getProperty("console.tcp.port", "8083"));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
