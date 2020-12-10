package cn.ineutech.tpms.tcp.service;

import io.netty.buffer.ByteBuf;
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

import cn.ineutech.tpms.tcp.TPMSConsts;
import cn.ineutech.tpms.tcp.server.SessionManager;
import cn.ineutech.tpms.tcp.service.msg.BaseMsgProcessService;
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

	private BaseMsgProcessService sendToDevice;
	private final SessionManager sessionManager;

	public ConsoleTCPServerHandler() {
		this.sendToDevice = new BaseMsgProcessService();
		this.sessionManager = SessionManager.getInstance();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws InterruptedException, UnsupportedEncodingException {
		try {
			ByteBuf buf = (ByteBuf) msg;
			if (buf.readableBytes() <= 0) {
				// ReferenceCountUtil.safeRelease(msg);
				return;
			}

			byte[] bs = new byte[buf.readableBytes()];
			buf.readBytes(bs);

			byte[] temp = bs;

			String content = new String(temp, "utf-8");

			logger.info("控制台--received data:{}", content);

			//控制台独立播放器视频状态切换成功，发送给监控端
			String[] result = content.split(TPMSConsts.SPLIT);
			if (result != null && result.length == 6
					&& TPMSConsts.HEAD.equals(result[0])) {
				String dataType = result[3];
				String data = result[4];
				if (TPMSConsts.UP_COMMAND_PERIOD_STATE_SUCCESS.equals(dataType)) {
					String status = TPMSConsts.STATUS_PAUSE.equals(data)?TPMSConsts.STATUS_PAUSE:TPMSConsts.STATUS_BACK;
					sessionManager.getTestInfo().setStatus(status);
					sendToDevice.sendToMonitor(sendToDevice.sendDataToMonitor(TPMSConsts.DOWN_COMMAND_PERIOD_STATE_SUCCESS, data, ""));
				}
			}else {
				for (Session session : sessionManager.playToList()) {
					sendToDevice.send2Client(session.getChannel(), temp);
				}
			}
		} finally {
			release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		logger.error("控制台--发生异常:{}", cause.getMessage());

		if (!ctx.channel().isActive()) {
			System.out.println("控制台--SimpleClient:"
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
		sessionManager.putPlay(session.getId(), session);

		logger.info("控制台--终端连接:{}", insocket.getAddress().getHostAddress());
		for (Session allLine : sessionManager.playToList()) {
			sendToDevice.send2Client(allLine.getChannel(), TPMSConsts.ACTIVE.getBytes(CharsetUtil.UTF_8));
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		InetSocketAddress insocket = (InetSocketAddress) ctx.channel()
				.remoteAddress();
		logger.info("控制台--终端断开连接:{}", insocket.getAddress().getHostAddress());
		String sessionId = ctx.channel().id().asLongText();
		sessionManager.removePlayBySessionId(sessionId);
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
				logger.error("控制台--主动断开连接:{}", insocket.getAddress()
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