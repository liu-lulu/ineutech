package cn.kkbc.tpms.common;

import java.io.IOException;
import java.util.Properties;

public class Consts {

	public static int tcp_port = 8080;
	// 客户端发呆15分钟后,服务器主动断开连接
	public static int tcp_client_idle_minutes = 30;

	static {
		Properties properties = new Properties();
		try {
			properties.load(Consts.class.getClassLoader().getResourceAsStream("tcp.properties"));
			tcp_port = Integer.parseInt(properties.getProperty("tcp.port", "8080"));
			tcp_client_idle_minutes = Integer.parseInt(properties.getProperty("tcp.client.maxIdleTime", "30"));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

}
