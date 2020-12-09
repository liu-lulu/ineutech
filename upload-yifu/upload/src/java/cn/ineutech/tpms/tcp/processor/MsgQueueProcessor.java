package cn.ineutech.tpms.tcp.processor;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ineutech.tpms.common.Consts;
import cn.ineutech.tpms.tcp.server.BaseMsgProcessService;
import cn.ineutech.tpms.tcp.server.SessionManager;
import cn.ineutech.tpms.tcp.service.queue.MemoryMsgQueueServiceImpl;
import cn.ineutech.tpms.tcp.service.queue.MsgQueueService;
import cn.ineutech.tpms.tcp.vo.QueueElement;
import cn.ineutech.tpms.tcp.vo.Session;

/**
 * 
 * @name: MsgQueueProcessor 
 * @description: 处理接收到的拨盘的数据
 * @date 2018年2月1日 上午11:39:30
 * @author liululu
 */
public class MsgQueueProcessor extends Thread implements Processor {

	private Logger log = LoggerFactory.getLogger(getClass());

	private volatile boolean isRunning = false;
	private MsgQueueService msgQueueService = null;
	private SessionManager sessionManager;
	private BaseMsgProcessService sendToDevice;

	public MsgQueueProcessor() {
		this.msgQueueService = new MemoryMsgQueueServiceImpl();
		this.sessionManager = SessionManager.getInstance();
		this.setName("TCP-MsgQueueProcessor");
		this.sendToDevice = new BaseMsgProcessService();
		this.setDaemon(true);
	}

	@Override
	public synchronized void startProcess() {
		if (this.isRunning) {
			throw new IllegalStateException(this.getName()
					+ " is already started .");
		}
		this.isRunning = true;
		super.start();
		this.log.info("队列消息处理器启动完毕...");
	}

	@Override
	public synchronized void stopProcess() {
		if (!this.isRunning) {
			throw new IllegalStateException(this.getName()
					+ " is not yet started .");
		}
		this.isRunning = false;
		this.interrupt();
		this.log.info("队列消息处理器已经停止...");
	}

	@Override
	public void run() {
		
		ThreadPoolExecutor executor=new ThreadPoolExecutor(20, 30, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		while (this.isRunning) {
			QueueElement msg = null;
			try {
				msg = this.msgQueueService.poll();
			} catch (InterruptedException e1) {
				continue;
			}
			try {
				if (msg != null) {
					executor.execute(doTask(msg));
				}
			} catch (Exception e) {
				log.error("消息处理出现异常:{}", e.getMessage());
				e.printStackTrace();
			}
		}

		executor.shutdown();
	}

	/**
	 * 进行消息处理
	 * @param queueElement
	 * @return
	 */
	private Runnable doTask(QueueElement queueElement) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					processMsg(queueElement);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		};
	}

	/**
	 * 解析接收到的消息
	 * @param queueElement
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public void processMsg(QueueElement queueElement)
			throws InterruptedException, ParseException {
		String content=queueElement.getContent();
		if (!content.startsWith(Consts.HEART)) {
			Consts.receiveMsg = content;
			for (Session session : sessionManager.sessionToList()) {
				sendToDevice.send2Client(session.getChannel(), (content+Consts.END).getBytes(CharsetUtil.UTF_8));
			}
		}else{
			sendToDevice.send2Client(queueElement.getChannel(), (content+Consts.END).getBytes(CharsetUtil.UTF_8));
		}
			
		
		
	}
	
	/**
	 * 更新测试项目的实际测试时间和时长
	 */
//	public void updTestInfo(){
//		TestInfo testInfo = service.getTestInfoById(sessionManager.getTestInfo().getTestId());
//		sessionManager.getTestInfo().setActualTime(testInfo.getActualTime());
//		sessionManager.getTestInfo().setProLength(testInfo.getProLength());
//	}

	public static void main(String[] args) throws UnknownHostException,
			IOException, InterruptedException {

		// Socket socket = new Socket("localhost", 8082);
		// OutputStream outputStream = socket.getOutputStream();
		// while (true) {
		// outputStream.write("smg,1,uuid,1,85,2017-08-11 13:29:30\r\n".getBytes());
		// outputStream.flush();
		// Thread.sleep(1000);
		// }

	}

}
