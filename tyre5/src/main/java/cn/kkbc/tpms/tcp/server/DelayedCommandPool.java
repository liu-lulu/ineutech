package cn.kkbc.tpms.tcp.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

import cn.kkbc.tpms.tcp.vo.resp.DelayedCommandKey;

public class DelayedCommandPool {

	private volatile static DelayedCommandPool instance;

	private Map<String, Object> commands;
	private static DelayQueue<DelayedCommandKey> delayedCommandKeys;

	private DelayedCommandPool() {
		this.commands = new ConcurrentHashMap<>();
		delayedCommandKeys = new DelayQueue<>();
	}

	public static DelayedCommandPool getInstance() {
		if (instance == null) {
			synchronized (DelayedCommandPool.class) {
				if (instance == null) {
					instance = new DelayedCommandPool();
				}
			}
		}
		return instance;
	}

	public void submitCommand(String generateKey, Object val) {
		this.commands.put(generateKey, val);
	}

	public Object getAndRemoveCommand(String generateKey) {
		return this.commands.remove(generateKey);
	}

	public static String generateKey(String phone, int flowId) {
		return (phone + "_" + flowId).intern();
	}

	/**
	 * 记录已经发送给客户端的命令的key到阻塞队列<br>
	 * 超时后专用线程将负责从阻塞队列取出key,并尝试从{@code this.commands}中删除key
	 * 
	 * @param delayedCommandKey
	 */
	public void registryDelayedCommandKey(DelayedCommandKey delayedCommandKey) {
		delayedCommandKeys.put(delayedCommandKey);
	}

	/**
	 * 从阻塞队列首部获取已经超时的元素
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public DelayedCommandKey unRegistryDelayedCommandKey() throws InterruptedException {
		return delayedCommandKeys.take();
	}
}
