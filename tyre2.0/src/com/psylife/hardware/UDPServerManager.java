package com.psylife.hardware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.psylife.hardware.queue.MessageQueue;
import io.netty.channel.Channel;

public class UDPServerManager {

	private static UDPServerManager instance=null;
	
	public static UDPServerManager  getInstance(){
		if(instance==null){
			synchronized (UDPServerManager.class) {
				if(instance==null){
					instance=new UDPServerManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 服务通道
	 */
	private Channel serverChannel = null;	
	
	/**
	 * 在线元素
	 */
	private Map<String, HardwareElement> channelElement;
	
	/**
	 * 消息队列
	 */
	private MessageQueue messageQueue;
	
	/**
	 * dtu操作队列数据,key为dtu+&+操作日志id
	 */
	private Map<String, DtuOperation> dtuOperationMap;
	
	
	private UDPServerManager(){
		channelElement=new ConcurrentHashMap<String, HardwareElement>();
		messageQueue=new MessageQueue();
		dtuOperationMap=new ConcurrentHashMap<String, DtuOperation>();
	}


	public Channel getServerChannel() {
		return serverChannel;
	}


	public void setServerChannel(Channel serverChannel) {
		this.serverChannel = serverChannel;
	}


	/**
	 * 在线元素
	 * @return
	 */
	public Map<String, HardwareElement> getChannelElement() {
		return channelElement;
	}


	public MessageQueue getMessageQueue() {
		return messageQueue;
	}


	public void setMessageQueue(MessageQueue messageQueue) {
		this.messageQueue = messageQueue;
	}


	/**
	 * dtu操作队列数据
	 * @return
	 */
	public Map<String, DtuOperation> getDtuOperationMap() {
		return dtuOperationMap;
	}
	
}
