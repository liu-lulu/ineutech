package cn.kkbc.tpms.tcp.server;

import cn.kkbc.tpms.tcp.MsgQueue;

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
