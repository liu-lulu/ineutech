package cn.kkbc.tpms.tcp.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.service.queue.MemoryMsgQueueServiceImpl;
import cn.kkbc.tpms.tcp.service.queue.MsgQueueService;
import cn.kkbc.tpms.tcp.vo.QueueElement;

import com.kkbc.cons.TCPConsts;
import com.kkbc.util.BaseMsgProcessService;

public class MsgQueueProcessor extends Thread implements Processor {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	private MsgQueueService msgQueueService;
	private BaseMsgProcessService send;

	private volatile boolean isRunning = false;
	

	public MsgQueueProcessor() {
		this.msgQueueService = new MemoryMsgQueueServiceImpl();
		this.send=new BaseMsgProcessService();
		this.setDaemon(true);
	}

	@Override
	public synchronized void startProcess() {
		if (this.isRunning) {
			throw new IllegalStateException(this.getName() + " is already started .");
		}
		this.isRunning = true;
		super.start();
		this.log.info("队列消息处理器启动完毕...");
	}

	@Override
	public synchronized void stopProcess() {
		if (!this.isRunning) {
			throw new IllegalStateException(this.getName() + " is not yet started .");
		}
		this.isRunning = false;
		this.interrupt();
		this.log.info("队列消息处理器已经停止...");
	}

	@Override
	public void run() {
		
		while (this.isRunning) {
			QueueElement msg = null;
			try {
				msg = this.msgQueueService.take();
			} catch (InterruptedException e1) {
				continue;
			}
			try {
				if (msg!=null) {
					processMsg(msg);
				}
			} catch (Exception e) {
				log.error("消息处理出现异常:{}", e.getMessage());
				e.printStackTrace();
			}
		}
		
	}
	
//	@Override
//	public void run() {
//		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(30);
//		
//		while (this.isRunning) {
//			QueueElement msg = null;
//			try {
//				msg = this.msgQueueService.take();
//			} catch (InterruptedException e1) {
//				continue;
//			}
//			try {
//				if (msg!=null) {
//					cachedThreadPool.execute(DoTask(msg));
//				}
//			} catch (Exception e) {
//				log.error("消息处理出现异常:{}", e.getMessage());
//				e.printStackTrace();
//			}
//		}
//		
//		cachedThreadPool.shutdown();
//	}

	/**
	 * 
	 * @param queueElement
	 * @return
	 */
	private Runnable DoTask(final QueueElement queueElement) {
		return new Runnable() {
			@Override
			public void run() {
					try {
						processMsg(queueElement);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		};
	}
	public void processMsg(QueueElement queueElement) throws InterruptedException  {
		send.send(TCPConsts.channel, send.getMsg(TCPConsts.COMMAND_CODE, queueElement.getContent()));
	}
	

}
