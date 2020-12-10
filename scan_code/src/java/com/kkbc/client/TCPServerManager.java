package com.kkbc.client;

import cn.kkbc.tpms.tcp.vo.MsgQueue;

public class TCPServerManager {

	private static volatile TCPServerManager instance = null;

	private MsgQueue msgQueue = null;

	public static TCPServerManager getInstance() {
		if (instance == null) {
			synchronized (TCPServerManager.class) {
				if (instance == null) {
					instance = new TCPServerManager();
				}
			}
		}
		return instance;
	}

	private TCPServerManager() {
		this.msgQueue = new MsgQueue();
	}

	public MsgQueue getMsgQueue() {
		return msgQueue;
	}

}
