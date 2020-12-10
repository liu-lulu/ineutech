package cn.kkbc.tpms.tcp.vo;

import java.util.Arrays;

import io.netty.channel.Channel;

public class QueueElement {
	private Channel channel;

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

	@Override
	public String toString() {
		return "QueueElement [channel=" + channel + ", data=" + Arrays.toString(data) + "]";
	}



}
