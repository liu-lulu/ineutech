package cn.ineutech.tpms.tcp.server;

import cn.ineutech.tpms.tcp.MsgQueue;

/**
 * 
 * @name: TCPServerManager 
 * @description: 拨盘tcp服务的信息管理
 * @date 2018年2月1日 下午1:56:52
 * @author liululu
 */
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
