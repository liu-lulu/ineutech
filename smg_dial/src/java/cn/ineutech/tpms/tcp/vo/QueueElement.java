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
	
	


}
