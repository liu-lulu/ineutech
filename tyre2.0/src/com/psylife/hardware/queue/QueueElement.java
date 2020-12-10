package com.psylife.hardware.queue;

import java.net.InetSocketAddress;

/**
 * 队列元素
 * @author xu
 *
 */
public class QueueElement {
	
	/**
	 * udp地址信息
	 */
	private InetSocketAddress udpAddress;
	
	/**
	 * 数据
	 */
	private byte[] data=null;

	public InetSocketAddress getUdpAddress() {
		return udpAddress;
	}

	public void setUdpAddress(InetSocketAddress udpAddress) {
		this.udpAddress = udpAddress;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	

}
