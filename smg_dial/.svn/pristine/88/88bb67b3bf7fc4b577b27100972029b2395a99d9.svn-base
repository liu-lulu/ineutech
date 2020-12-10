package cn.ineutech.tpms.tcp.service.queue;

import cn.ineutech.tpms.tcp.MsgQueue;
import cn.ineutech.tpms.tcp.server.BrainTCPServerManager;
import cn.ineutech.tpms.tcp.vo.QueueElement;

/**
 * 
 * @name: BrainMemoryMsgQueueServiceImpl 
 * @description: 对接收到的脑电的数据进行存取
 * @date 2018年2月1日 下午2:42:37
 * @author liululu
 */
public class BrainMemoryMsgQueueServiceImpl implements MsgQueueService {

	private MsgQueue msgQueue = null;

	public BrainMemoryMsgQueueServiceImpl() {
		msgQueue = BrainTCPServerManager.getInstance().getMsgQueue();
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
