package cn.kkbc.tpms.tcp.vo.resp;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayedCommandKey implements Delayed {

	private long startTime;
	private long timeout;

	private String commandKey;

	public DelayedCommandKey() {
		this.startTime = System.currentTimeMillis();
	}

	public DelayedCommandKey(long timeout, String commandKey) {
		this();
		this.setTimeout(timeout);
		this.commandKey = commandKey;
	}

	public DelayedCommandKey(long timeout) {
		this();
		this.setTimeout(timeout);
	}

	@Override
	public int compareTo(Delayed o) {
		if (o == this)
			return 0;
		long distance = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
		return distance < 0 ? -1 : (distance == 0 ? 0 : 1);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.startTime + timeout - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout + 2000;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setCommandKey(String commandKey) {
		this.commandKey = commandKey;
	}

	public String getCommandKey() {
		return commandKey;
	}

	@Override
	public String toString() {
		return "DelayedCommand [startTime=" + startTime + ", timeout=" + timeout + ", commandKey=" + commandKey + "]";
	}

}
