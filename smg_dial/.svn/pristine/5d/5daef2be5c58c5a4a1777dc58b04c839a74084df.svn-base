package cn.ineutech.tpms.tcp.service.queue;

import cn.ineutech.tpms.tcp.vo.QueueElement;

/**
 * 
 * @name: MsgQueueService 
 * @description: 对接收到的tcp客户端的数据进行存取
 * @date 2018年2月1日 下午2:43:37
 * @author liululu
 */
public interface MsgQueueService {

	void push(QueueElement queueElement) throws InterruptedException;

	QueueElement take() throws InterruptedException;
	
	QueueElement poll() throws InterruptedException;

}
