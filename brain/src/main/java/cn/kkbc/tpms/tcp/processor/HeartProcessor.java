package cn.kkbc.tpms.tcp.processor;

import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.service.msg.SendToBrain;
import cn.kkbc.tpms.tcp.vo.Session;

import com.kkbc.entity.Device;
import com.kkbc.util.StringHelper;

public class HeartProcessor extends Thread implements Processor {

	private Logger log = LoggerFactory.getLogger(getClass());
	private Logger weblog = LoggerFactory.getLogger("weblog");

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
		long sendHeartTime = System.currentTimeMillis() / 1000L;
		while (this.isRunning) {
			long currentTime = System.currentTimeMillis();
//			boolean heartFlag;
//			if (currentTime / 1000L - sendHeartTime >= 2L) {
//				sendHeartTime = currentTime / 1000L;
//				heartFlag = true;
//			} else {
//				heartFlag = false;
//			}
//			

			long sleepTime = 1000L;

			Iterator<Entry<String, Session>> ite = sessionManager.entrySet()
					.iterator();
			while (ite.hasNext()) {
				try {
					Entry<String, Session> entry = ite.next();

					Session session = (Session) entry.getValue();

					if (session.getLastCommunicateTimeStamp() > 0L) {
						long temp = 15000L - (currentTime - session
								.getLastCommunicateTimeStamp());
						if (temp < 0L) {
							if ((session.getType() != null)
									&& (session.getType() != Device.DEVICE_TYPE_CLIENT)) {
								System.out.println("超过15s,强制断开连接");
								session.getChannel().disconnect();
							}
						} else {
							if ((temp < 1000L) && (sleepTime > temp)) {
								sleepTime = temp;
							}

							if (currentTime-session.getLastCommunicateTimeStamp()>1000) {
								sendToBrain.sendHeartData(session.getChannel());
								log.info("给设备{}发送心跳包",session.getDevice_id());
								session.setLastCommunicateTimeStamp(new Date().getTime());
							}
						}

					} 
//					else if ((session.getType() == -1)
//							|| (session.getType() == Device.DEVICE_TYPE_BRAIN)) {
//						sendToBrain.sendHeartData(session.getChannel());
//						log.info("首次给设备{}发送心跳包",session.getDevice_id());
//						session.setLastCommunicateTimeStamp(new Date().getTime());
//						System.out.println("脑波发送标识码返回状态:");
//					}

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
