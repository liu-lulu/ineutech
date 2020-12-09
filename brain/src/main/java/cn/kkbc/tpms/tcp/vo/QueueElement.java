package cn.kkbc.tpms.tcp.vo;

import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Date;

public class QueueElement {
	public static int TYPE_CMD=1;
	public static int TYPE_DATA=2;
	
	private Channel channel;
	
	//设备id
	private long device_id;
	
	//获取数据的时间
	private Date time;
	
	//数据类型:1：命令型数据  2：数据包
	private int type;

	/**
	 * 数据
	 */
	private byte[] data = null;

	public QueueElement() {
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

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(long device_id) {
		this.device_id = device_id;
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

	@Override
	public String toString() {
		return "QueueElement [channel=" + channel + ", data=" + Arrays.toString(data) + "]";
	}



}
