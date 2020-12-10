package cn.kkbc.tpms.tcp.vo;

import io.netty.channel.Channel;

import java.net.SocketAddress;

public class Session {
	

	private String id;
	private String shefen_id;
	private Channel channel = null;
	// private ChannelGroup channelGroup = new
	// DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	// 客户端上次的连接时间，该值改变的情况:
	// 1. terminal --> server 心跳包
	// 2. terminal --> server 数据包
	private long lastCommunicateTimeStamp = 0l;
	
	private byte[] data;
	
	private Integer type;
	
	private String deviceNo;

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
		return "Session [id=" + id + ", shefen_id=" + shefen_id + ", channel=" + channel + "]";
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


	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

}