package cn.kkbc.tpms.tcp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.service.queue.MemoryMsgQueueServiceImpl;
import cn.kkbc.tpms.tcp.service.queue.MsgQueueService;
import cn.kkbc.tpms.tcp.util.HexStringUtils;
import cn.kkbc.tpms.tcp.util.JT808ProtocolUtils;
import cn.kkbc.tpms.tcp.vo.QueueElement;
import cn.kkbc.tpms.tcp.vo.Session;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

public class TCPServerHandler extends ChannelInboundHandlerAdapter { // (1)

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final SessionManager sessionManager;
	private MsgQueueService msgQueueService;
	private JT808ProtocolUtils jt808ProtocolUtils;

	public TCPServerHandler() {
		this.sessionManager = SessionManager.getInstance();
		this.msgQueueService = new MemoryMsgQueueServiceImpl();
		this.jt808ProtocolUtils = new JT808ProtocolUtils();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException { // (2)
		try {
			ByteBuf buf = (ByteBuf) msg;
			if (buf.readableBytes() <= 0) {
				// ReferenceCountUtil.safeRelease(msg);
				return;
			}

			byte[] bs = new byte[buf.readableBytes()];
			buf.readBytes(bs);

			byte[] escapedBytes = null;
			try {
				escapedBytes = this.jt808ProtocolUtils.doEscape4Receive(bs, 1, bs.length - 1);
			} catch (Exception e) {
				logger.error("转义处理出错:{}", e.getMessage());
				e.printStackTrace();
				return;
			}
			// QueueElement element = new QueueElement(ctx.channel(), bs);
			logger.debug("escaped:{}", HexStringUtils.toHexString(escapedBytes));
			QueueElement element = new QueueElement(ctx.channel(), escapedBytes);
			this.msgQueueService.push(element);
		} finally {
			release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		logger.error("发生异常:{}", cause.getMessage());
		cause.printStackTrace();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Session session = Session.buildSession(ctx.channel());
		sessionManager.put(session.getId(), session);
		logger.info("终端连接:{}", session);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		final String sessionId = ctx.channel().id().asLongText();
		Session session = sessionManager.findBySessionId(sessionId);
		this.sessionManager.removeBySessionId(sessionId);
		logger.info("终端断开连接:{}", session);
		ctx.channel().close();
		// ctx.close();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				Session session = this.sessionManager.removeBySessionId(Session.buildId(ctx.channel()));
				logger.error("服务器主动断开连接:{}", session);
				ctx.close();
			}
		}
	}

	private void release(Object msg) {
		try {
			ReferenceCountUtil.release(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}