package cn.kkbc.tpms.tcp.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.server.DelayedCommandPool;
import cn.kkbc.tpms.tcp.vo.resp.DelayedCommandKey;

/**
 * 下发给终端的命令有可能收不到回复<br>
 * 因此{@code cn.kkbc.tpms.udp.server.DelayedCommandPool}中可能会遗留大量的命令历史<br>
 * 该线程负责清除超时了的名利记录
 * 
 * @author hylexus
 *
 */
public class DownlinkCommandTimeoutChecker extends Thread implements Processor {

	final private Logger log = LoggerFactory.getLogger(getClass());
	private volatile boolean isRunning = false;

	private DelayedCommandPool commandPool = DelayedCommandPool.getInstance();

	public DownlinkCommandTimeoutChecker() {
		this.setName("TCP-CommandTimeoutChecker");
		this.setDaemon(true);
	}

	@Override
	public synchronized void startProcess() {
		if (this.isRunning) {
			throw new IllegalStateException(this.getName() + " is already started .");
		}
		this.isRunning = true;
		super.start();
		this.log.info("==>[下发指令超时检测器] 启动完毕[OK]");
	}

	@Override
	public synchronized void stopProcess() {
		if (!this.isRunning) {
			throw new IllegalStateException(this.getName() + " is not yet started .");
		}
		this.isRunning = false;
		// 在此处中断线程,在InterruptedException异常的时候再次去检测 isRunning
		// 以免该线程被阻塞队列阻塞而无法正常终止
		this.interrupt();
		this.log.info("<==[下发指令超时检测器] 已经停止[OK]");
	}

	@Override
	public void run() {
		while (this.isRunning) {
			DelayedCommandKey delayedCommandKey = null;
			try {
				delayedCommandKey = this.commandPool.unRegistryDelayedCommandKey();
			} catch (InterruptedException e1) {
				// 外部中断该线程,再次检测isRunning
				continue;
			}

			this.destroyCommand(delayedCommandKey);

		}
	}

	private void destroyCommand(DelayedCommandKey delayedCommandKey) {
		try {
			if (delayedCommandKey != null) {
				log.debug("commandKey destroyed:{}", delayedCommandKey);
				this.commandPool.getAndRemoveCommand(delayedCommandKey.getCommandKey());
			}
		} catch (Exception e) {
			log.error("destroyCommand 出现异常:{}", e.getMessage());
			e.printStackTrace();
		}
	}

}
