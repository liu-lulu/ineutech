package cn.kkbc.tpms.tcp.service.queue;

import cn.kkbc.tpms.tcp.MsgQueue;
import cn.kkbc.tpms.tcp.server.TCPServerManager;
import cn.kkbc.tpms.tcp.vo.QueueElement;

public class MemoryMsgQueueServiceImpl implements MsgQueueService {

	private MsgQueue msgQueue = null;

	public MemoryMsgQueueServiceImpl() {
		msgQueue = TCPServerManager.getInstance().getMsgQueue();
	}

	@Override
	public void push(QueueElement queueElement) throws InterruptedException {
		this.msgQueue.push(queueElement);
	}

	@Override
	public QueueElement take() throws InterruptedException {
		return this.msgQueue.take();
	}

}
