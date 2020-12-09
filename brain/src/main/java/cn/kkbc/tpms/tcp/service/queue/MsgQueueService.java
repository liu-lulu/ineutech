package cn.kkbc.tpms.tcp.service.queue;

import cn.kkbc.tpms.tcp.vo.QueueElement;

public interface MsgQueueService {

	void push(QueueElement queueElement) throws InterruptedException;

	QueueElement take() throws InterruptedException;

}
