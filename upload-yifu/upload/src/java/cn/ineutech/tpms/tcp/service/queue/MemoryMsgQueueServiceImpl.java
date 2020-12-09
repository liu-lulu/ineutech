package cn.ineutech.tpms.tcp.service.queue;

import cn.ineutech.tpms.tcp.MsgQueue;
import cn.ineutech.tpms.tcp.server.TCPServerManager;
import cn.ineutech.tpms.tcp.vo.QueueElement;

/**
 * 
 * @name: MemoryMsgQueueServiceImpl 
 * @description: 对接收到的拨盘的数据进行存取
 * @date 2018年2月1日 下午2:43:24
 * @author liululu
 */
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

	@Override
	public QueueElement poll() throws InterruptedException {
		return this.msgQueue.poll();
	}

}
