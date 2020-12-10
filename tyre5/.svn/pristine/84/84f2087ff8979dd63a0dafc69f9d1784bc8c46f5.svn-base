package cn.kkbc.tpms.tcp.service.msg;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.util.HexStringUtils;
import cn.kkbc.tpms.tcp.vo.Session;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class BaseMsgProcessService {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	protected final Logger weblog = LoggerFactory.getLogger("weblog");

	protected SessionManager sessionManager;

	public BaseMsgProcessService() {
		this.sessionManager = SessionManager.getInstance();
	}

	protected ByteBuf getByteBuf(byte[] arr) {
		ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(arr.length);
		byteBuf.writeBytes(arr);
		return byteBuf;
	}

	public void send2Client(Channel channel, byte[] arr) throws InterruptedException {
		log.info("resp[byts]:{}",Arrays.toString(arr));
		log.info("resp[ hex]:{}",HexStringUtils.toHexString(arr));
		weblog.info("resp[ hex]:{}",HexStringUtils.toHexString(arr));
		ChannelFuture future = channel.writeAndFlush(Unpooled.copiedBuffer(arr)).sync();
		if (!future.isSuccess()) {
			log.error("发送数据出错:{}", future.cause());
		}
	}

	protected int getFlowId(Channel channel, int defaultValue) {
		Session session = this.sessionManager.findBySessionId(Session.buildId(channel));
		if (session == null) {
			return defaultValue;
		}

		return session.currentFlowId();
	}

	protected int getFlowId(Channel channel) {
		return this.getFlowId(channel, 0);
	}

}
