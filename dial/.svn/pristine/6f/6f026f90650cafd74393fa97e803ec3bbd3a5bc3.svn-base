package cn.kkbc.tpms.tcp;

import java.util.concurrent.LinkedBlockingQueue;

import cn.kkbc.tpms.tcp.vo.QueueElement;

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
}
