package cn.ineutech.tpms.tcp;

import java.util.concurrent.LinkedBlockingQueue;

import cn.ineutech.tpms.tcp.vo.QueueElement;

/**
 * 
 * @name: MsgQueue 
 * @description: 数据队列
 * @date 2018年2月1日 上午11:38:32
 * @author liululu
 */
public class MsgQueue {

	private LinkedBlockingQueue<QueueElement> queue;

	public MsgQueue() {
		this.queue = new LinkedBlockingQueue<>();
	}

	public void push(QueueElement e) throws InterruptedException {
		this.queue.put(e);
	}

	public QueueElement take() throws InterruptedException {
		return this.queue.take();
	}
	
	public QueueElement poll() throws InterruptedException {
		return this.queue.poll();
	}
}
