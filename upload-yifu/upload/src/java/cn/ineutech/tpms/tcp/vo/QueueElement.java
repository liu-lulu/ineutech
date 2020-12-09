package cn.ineutech.tpms.tcp.vo;

import java.util.Date;

import io.netty.channel.Channel;

/**
 * 
 * @name: QueueElement 
 * @description: 客户端数据
 * @date 2018年2月1日 下午3:00:54
 * @author liululu
 */
public class QueueElement {

	public static int TYPE_CMD = 1;
	public static int TYPE_DATA = 2;

	// 设备id
	private Integer deviceId;

	// 获取数据的时间
	private Date time;

	// 数据类型:1：命令型数据 2：数据包
	private int type;

	// 数据
	private byte[] data = null;

	private Channel channel;

	private String content;

	public QueueElement() {
	}

	public QueueElement(Channel channel, String content) {
		super();
		this.channel = channel;
		this.content = content;
	}

	public QueueElement(Channel channel, byte[] data) {
		super();
		this.channel = channel;
		this.data = data;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "QueueElement [channel=" + channel + ", data=" + content + "]";
	}

}
