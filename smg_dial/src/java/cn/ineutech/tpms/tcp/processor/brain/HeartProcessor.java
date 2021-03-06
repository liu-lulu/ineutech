package cn.ineutech.tpms.tcp.processor.brain;

import cn.ineutech.tpms.tcp.processor.Processor;
import cn.ineutech.tpms.tcp.server.SessionManager;
import cn.ineutech.tpms.tcp.service.msg.SendToBrain;
import cn.ineutech.tpms.tcp.vo.Session;
import com.ineutech.util.StringHelper;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @name: HeartProcessor 
 * @description: 给拨盘发送心跳包
 * @date 2018年2月1日 上午11:40:43
 * @author liululu
 */
public class HeartProcessor extends Thread implements Processor {

	private Logger log = LoggerFactory.getLogger(getClass());

	private volatile boolean isRunning = false;
	private SessionManager sessionManager;

	private SendToBrain sendToBrain;

	public HeartProcessor() {
		this.sessionManager = SessionManager.getInstance();
		this.sendToBrain = new SendToBrain();
		this.setName("TCP-HeartProcessor");
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
		this.log.info("心跳服务启动完毕...");
	}

	@Override
	public synchronized void stopProcess() {
		if (!this.isRunning) {
			throw new IllegalStateException(this.getName()
					+ " is not yet started .");
		}
		this.isRunning = false;
		this.interrupt();
		this.log.info("心跳服务已经停止...");
	}

	@Override
	public void run() {
		System.out.println("开始心跳发送");
		while (this.isRunning) {
			long currentTime = System.currentTimeMillis();

			long sleepTime = 1000L;

			Iterator<Entry<String, Session>> ite = sessionManager.brainEntrySet()
					.iterator();
			while (ite.hasNext()) {
				try {
					Entry<String, Session> entry = ite.next();

					Session session = (Session) entry.getValue();

					if (session.getLastCommunicateTimeStamp() > 0L) {
						long temp = 15000L - (currentTime - session
								.getLastCommunicateTimeStamp());
						if (temp < 0L) {
								System.out.println("超过15s,强制断开连接");
								session.getChannel().disconnect();
						} else {
							if ((temp < 1000L) && (sleepTime > temp)) {
								sleepTime = temp;
							}

							if (currentTime-session.getLastCommunicateTimeStamp()>1000) {
								sendToBrain.sendHeartData(session.getChannel());
//								log.info("给设备{}发送心跳包",session.getDevice_id());
								session.setLastCommunicateTimeStamp(new Date().getTime());
							}
						}

					} 

					if (sleepTime < 200L) {
						sleepTime = 200L;
					}
//					Thread.sleep(sleepTime);

				} catch (Exception e) {
					log.error("发送心跳数据包出现异常:{}", StringHelper.getTrace(e));
					e.printStackTrace();
				}
			}
	}
}
}
