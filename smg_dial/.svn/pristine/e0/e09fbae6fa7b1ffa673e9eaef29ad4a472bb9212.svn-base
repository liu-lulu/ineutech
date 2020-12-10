package cn.ineutech.tpms.tcp.vo;

import io.netty.channel.Channel;

import java.net.SocketAddress;
import java.util.Date;

import com.ineutech.entity.DeviceData;
import com.ineutech.entity.TestUser;

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
	
	private TestUser testUser;
	
	private Integer hardNo;
	
	private Integer score;
	
	private DeviceData brainData;
	
	private Integer ele;
	
//	private int state=STATE_NO_START;

	private Date lastChuliDate;
	
	//拨盘本地数据发送完标记
	private boolean sendData=false;
	
	//登陆时绑定的座位号
	private Integer seatNo;
	
	private Integer threshold;
	private boolean isStartTest= false;
	private byte[] rowData;
	//server->脑波发送心跳时间
	private long lastCommunicateTimeStamp = 0L;

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

	public TestUser getTestUser() {
		return testUser;
	}

	public void setTestUser(TestUser testUser) {
		this.testUser = testUser;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getEle() {
		return ele;
	}

	public void setEle(Integer ele) {
		this.ele = ele;
	}

//	public int getState() {
//		return state;
//	}
//
//	public void setState(int state) {
//		this.state = state;
//	}

	public Integer getHardNo() {
		return hardNo;
	}

	public void setHardNo(Integer hardNo) {
		this.hardNo = hardNo;
	}

	public Date getLastChuliDate() {
		return lastChuliDate;
	}

	public void setLastChuliDate(Date lastChuliDate) {
		this.lastChuliDate = lastChuliDate;
	}

	public boolean isSendData() {
		return sendData;
	}

	public void setSendData(boolean sendData) {
		this.sendData = sendData;
	}

	public Integer getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(Integer seatNo) {
		this.seatNo = seatNo;
	}
	public byte[] getRowData() {
		return rowData;
	}

	public void setRowData(byte[] rowData) {
		this.rowData = rowData;
	}
	public boolean isStartTest() {
		return isStartTest;
	}

	public void setStartTest(boolean isStartTest) {
		this.isStartTest = isStartTest;
	}
	public long getLastCommunicateTimeStamp() {
		return lastCommunicateTimeStamp;
	}

	public void setLastCommunicateTimeStamp(long lastCommunicateTimeStamp) {
		this.lastCommunicateTimeStamp = lastCommunicateTimeStamp;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public DeviceData getBrainData() {
		return brainData;
	}

	public void setBrainData(DeviceData brainData) {
		this.brainData = brainData;
	}
	
}