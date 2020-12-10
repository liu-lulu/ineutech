package cn.kkbc.tpms.tcp.vo;

import io.netty.channel.Channel;

import java.net.SocketAddress;

import com.kkbc.entity.Hard;
import com.kkbc.entity.TestUser;

public class Session {
	
	//未开始
	public static final int STATE_NO_START=0;
	
	//测试中
	public static final int STATE_TESTING=1;
	
	//测试暂停
	public static final int STATE_PAUSE=2;
	
	//测试结束
	public static final int STATE_END=3;

	private String id;
	private long device_id;
	private String shefen_id;
	private Channel channel = null;
	// private ChannelGroup channelGroup = new
	// DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	// 客户端上次的连接时间，该值改变的情况:
	// 1. terminal --> server 心跳包
	// 2. terminal --> server 数据包
	private long lastCommunicateTimeStamp = 0l;
	
	private TestUser testUser;
	
	private Integer hard_no;
	
	private int mod;
	
	private int score;
	
	private int ele=-1;
	
	private int state=STATE_NO_START;
	
	//设备当前时间
	private String curTime;
	
	private byte[] data;
	
	private boolean setTimeStatus=false;


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

	public TestUser getTestUser() {
		return testUser;
	}

	public void setTestUser(TestUser testUser) {
		this.testUser = testUser;
	}

	public int getMod() {
		return mod;
	}

	public void setMod(int mod) {
		this.mod = mod;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getEle() {
		return ele;
	}

	public void setEle(int ele) {
		this.ele = ele;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getCurTime() {
		return curTime;
	}

	public void setCurTime(String curTime) {
		this.curTime = curTime;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public boolean isSetTimeStatus() {
		return setTimeStatus;
	}

	public void setSetTimeStatus(boolean setTimeStatus) {
		this.setTimeStatus = setTimeStatus;
	}

	public Integer getHard_no() {
		return hard_no;
	}

	public void setHard_no(Integer hard_no) {
		this.hard_no = hard_no;
	}



}