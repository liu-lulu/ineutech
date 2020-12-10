package cn.kkbc.tpms.tcp.vo;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import cn.kkbc.tpms.tcp.vo.QueueElement;

public class MsgQueue {

	private ConcurrentLinkedQueue<QueueElement> queue;

	public MsgQueue() {
		this.queue = new ConcurrentLinkedQueue<>();
	}

	public void push(QueueElement e) throws InterruptedException {
		this.queue.add(e);
	}

	public QueueElement take() throws InterruptedException {
		return this.queue.poll();
	}
}
