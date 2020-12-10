package cn.kkbc.tpms.tcp.service.msg;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.server.DelayedCommandPool;
import cn.kkbc.tpms.tcp.util.HexStringUtils;
import cn.kkbc.tpms.tcp.vo.Session;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;

public class CommandSender {
	final static Logger log = LoggerFactory.getLogger(CommandSender.class);
	final static Logger weblog = LoggerFactory.getLogger("weblog");

	private DelayedCommandPool commandPool = DelayedCommandPool.getInstance();
	private static ExecutorService service = Executors.newFixedThreadPool(2);

	public CommandSender() {

	}

	public static final void shutDownThreadPool() {
		try {
			if (service != null)
				if (!service.isShutdown())
					service.shutdown();
			log.info("<==[DelayedCommandPool] 已经停止[OK]");
		} catch (Exception e) {
			log.error("<==[DelayedCommandPool] 停止[ERROR]:{}", e.getMessage());
			e.printStackTrace();
		}
	}

	public Object doSend(Session session, byte[] arr, int flowId, int timeout, TimeUnit timeUnit)
			throws InterruptedException, ExecutionException, TimeoutException {
		log.info("resp[byts]:{}", Arrays.toString(arr));
		log.info("resp[ hex]:{}", HexStringUtils.toHexString(arr));
		weblog.info("resp[ hex]:{}", HexStringUtils.toHexString(arr));
		ChannelFuture future = session.getChannel().writeAndFlush(Unpooled.copiedBuffer(arr)).sync();
		if (!future.isSuccess()) {
			log.error("发送数据出错:{}", future.cause());
		}

		Future<Object> f = service.submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				Object ret = null;
				final String generatedKey = generateKey(session.getTerminalPhone(), flowId);
				while ((ret = commandPool.getAndRemoveCommand(generatedKey)) == null) {
					Thread.sleep(200);
				}
				return ret;
			}
		});

		return f.get(timeout, timeUnit);
	}

	public static String generateKey(String phone, int flowId) {
		return phone + "_" + flowId;
	}

}