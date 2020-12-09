package cn.ineutech.tpms.tcp.vo;

import io.netty.channel.Channel;

import java.net.SocketAddress;

/**
 * 
 * @name: Session 
 * @description: 客户端连接过程中其对应的数据信息
 * @date 2018年2月1日 下午3:01:12
 * @author liululu
 */
public class Session {

	private String id;
	private Integer deviceId;
	private String uuid;
	
	private String clientType;
	private Channel channel = null;
	
	

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

	public static Session buildSession(Channel channel, String uuid) {
		Session session = new Session();
		session.setChannel(channel);
		session.setId(buildId(channel));
		session.setUuid(uuid);
//		session.setLastCommunicateTimeStamp(System.currentTimeMillis());
		return session;
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Session other = (Session) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", device_id=" + deviceId+ ", uuid=" + uuid + ", channel=" + channel + "]";
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}