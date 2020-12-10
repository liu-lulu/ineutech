package cn.kkbc.tpms.tcp.entity;



/**
 * dtu操作队列元素
 * @author xu
 *
 */
public class DialOperation {
	
	/**
	 * 类型，已发送
	 */
	public static final int TYPE_SEND =1;
	
	/**
	 * 类型，设置成功
	 */
	public static final int TYPE_SUCCESS =2;
	
	/**
	 * 类型，设置失败
	 */
	public static final int TYPE_FAIL =3;
	
	/**
	 * 类型，超时
	 */
	public static final int TYPE_TIMEOUT =4;
	
	/**
	 * 操作dtu控制,服务器响应用户超时
	 */
	public static final long INTERVAL_TIME=10*1000;//服务器响应用户超时10s
	
	/**
	 * 用户确认人名时间定为1分钟，若人名在1分钟内未确认，则默认为人名错误
	 */
	public static final long CONFIRM_NAME_TIME=60*1000;//服务器响应用户超时10s
	
	

	/**
	 * 操作日志id主键
	 */
	private Long id;
	
	/**
	 * mac
	 */
	private String mac;
	
	/**
	 * 操作命令
	 */
	private int command;
	
	/**
	 * 操作时间
	 */
	private long time=System.currentTimeMillis();
	
	private int type=TYPE_SEND;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}
	
	

}
