package cn.ineutech.tpms.tcp.server;

import cn.ineutech.tpms.tcp.MsgQueue;

/**
 * 
 * @name: BrainTCPServerManager 
 * @description: 脑电tcp服务的信息管理
 * @date 2018年2月1日 下午1:53:42
 * @author liululu
 */
public class BrainTCPServerManager {

	private static volatile BrainTCPServerManager instance = null;

	private MsgQueue msgQueue = null;

	public static BrainTCPServerManager getInstance() {
		if (instance == null) {
			synchronized (BrainTCPServerManager.class) {
				if (instance == null) {
					instance = new BrainTCPServerManager();
				}
			}
		}
		return instance;
	}

	private BrainTCPServerManager() {
		this.msgQueue = new MsgQueue();
	}

	public MsgQueue getMsgQueue() {
		return msgQueue;
	}

}
