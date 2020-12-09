package cn.kkbc.tpms.tcp.vo;

import io.netty.channel.Channel;

import java.net.SocketAddress;
import java.util.Date;

public class Session {

	private String id;
	private long device_id;
	private String shefen_id;
	private Channel channel = null;
	
	//server->脑波发送心跳时间
	private long lastCommunicateTimeStamp = 0l;

	
	private SocketAddress socketAddress;
	
	private long userId;
	private Integer electricity;
	private Integer type=Integer.valueOf(-1);;
	private Date loginTime;
	private String ip;
	private String networkName;
	private boolean isStartTest= false;
	private long startTestTime= System.currentTimeMillis();
	
	private byte[] rowData;

	public Session() {
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public static String buildId(Channel channel) {
		return channel.id().asLongText();
	}

	public static Session buildSession(Channel channel) {
		return buildSession(channel, null);
	}

	public static Session buildSession(Channel channel, String shefen_id) {
		Session session = new Session();
		session.setChannel(channel);
		session.setId(buildId(channel));
		session.setShefen_id(shefen_id);
//		session.setLastCommunicateTimeStamp(System.currentTimeMillis());
		return session;
	}

	public long getLastCommunicateTimeStamp() {
		return lastCommunicateTimeStamp;
	}

	public void setLastCommunicateTimeStamp(long lastCommunicateTimeStamp) {
		this.lastCommunicateTimeStamp = lastCommunicateTimeStamp;
	}

	public SocketAddress getRemoteAddr() {
		System.out.println(this.channel.remoteAddress().getClass());

		return this.channel.remoteAddress();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", device_id=" + device_id+ ", shefen_id=" + shefen_id + ", channel=" + channel + "]";
	}

	public long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(long device_id) {
		this.device_id = device_id;
	}

	public String getShefen_id() {
		return shefen_id;
	}

	public void setShefen_id(String shefen_id) {
		this.shefen_id = shefen_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SocketAddress getSocketAddress() {
		return socketAddress;
	}

	public void setSocketAddress(SocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Integer getElectricity() {
		return electricity;
	}

	public void setElectricity(Integer electricity) {
		this.electricity = electricity;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public boolean isStartTest() {
		return isStartTest;
	}

	public void setStartTest(boolean isStartTest) {
		this.isStartTest = isStartTest;
	}

	public long getStartTestTime() {
		return startTestTime;
	}

	public void setStartTestTime(long startTestTime) {
		this.startTestTime = startTestTime;
	}

	public byte[] getRowData() {
		return rowData;
	}

	public void setRowData(byte[] rowData) {
		this.rowData = rowData;
	}

}