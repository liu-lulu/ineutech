package cn.ineutech.tpms.tcp.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ineutech.tpms.common.Consts;
import cn.ineutech.tpms.tcp.service.queue.MemoryMsgQueueServiceImpl;
import cn.ineutech.tpms.tcp.service.queue.MsgQueueService;
import cn.ineutech.tpms.tcp.vo.QueueElement;
import cn.ineutech.tpms.tcp.vo.Session;

/**
 * 
 * @name: ConsoleTCPServerHandler 
 * @description: 处理tcp服务接收到的控制台数据
 * @date 2018年2月1日 下午2:13:57
 * @author liululu
 */
public class ConsoleTCPServerHandler extends ChannelInboundHandlerAdapter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private MsgQueueService msgQueueService;
	private BaseMsgProcessService sendToDevice;
	private final SessionManager sessionManager;

	public ConsoleTCPServerHandler() {
		this.msgQueueService = new MemoryMsgQueueServiceImpl();
		this.sendToDevice = new BaseMsgProcessService();
		this.sessionManager = SessionManager.getInstance();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws InterruptedException, UnsupportedEncodingException {
		try {

			String content = (String) msg;

			logger.info("YIFU--received data:{}", content);
			
//			if (!content.startsWith(Consts.HEART)) {
//				Consts.receiveMsg = content;
//			}
			
//			for (Session session : sessionManager.sessionToList()) {
//				sendToDevice.send2Client(session.getChannel(), (content+Consts.END).getBytes(CharsetUtil.UTF_8));
//			}
			
			QueueElement element = new QueueElement(ctx.channel(), content);
			this.msgQueueService.push(element);

		} finally {
			release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error("YIFU--发生异常:{}", cause.getMessage());

		if (!ctx.channel().isActive()) {
			System.out.println("YIFU--SimpleClient:"
					+ ctx.channel().remoteAddress() + "异常");
		}

		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		InetSocketAddress insocket = (InetSocketAddress) ctx.channel()
				.remoteAddress();
		Session session = Session.buildSession(ctx.channel());
		sessionManager.putSession(session.getId(), session);

		logger.info("YIFU--终端连接:{}", insocket.getAddress().getHostAddress());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		InetSocketAddress insocket = (InetSocketAddress) ctx.channel()
				.remoteAddress();
		logger.info("YIFU--终端断开连接:{}", insocket.getAddress().getHostAddress());
		String sessionId = ctx.channel().id().asLongText();
		sessionManager.removeBySessionId(sessionId);
		ctx.channel().close();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {

				InetSocketAddress insocket = (InetSocketAddress) ctx.channel()
						.remoteAddress();
				logger.error("YIFU--主动断开连接:{}", insocket.getAddress()
						.getHostAddress());
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