package cn.kkbc.tpms.tcp.vo;

import java.util.Arrays;

import io.netty.channel.Channel;

public class QueueElement {
	private Channel channel;
	
	private String content;

	public QueueElement() {
	}

	public QueueElement(Channel channel, String content) {
		super();
		this.channel = channel;
		this.content = content;
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

	@Override
	public String toString() {
		return "QueueElement [channel=" + channel + ", data=" + content + "]";
	}



}
