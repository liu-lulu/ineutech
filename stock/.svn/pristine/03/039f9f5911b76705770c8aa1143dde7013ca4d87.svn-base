package cn.kkbc.tpms.tcp.service;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.TPMSConsts;
import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.service.msg.BaseMsgProcessService;
import cn.kkbc.tpms.tcp.service.queue.MemoryMsgQueueServiceImpl;
import cn.kkbc.tpms.tcp.service.queue.MsgQueueService;
import cn.kkbc.tpms.tcp.vo.QueueElement;
import cn.kkbc.tpms.tcp.vo.Session;

public class TCPServerHandler extends ChannelInboundHandlerAdapter { // (1)

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final SessionManager sessionManager;
	private MsgQueueService msgQueueService;
	private BaseMsgProcessService sendToDevice;

	public TCPServerHandler() {
		this.sessionManager = SessionManager.getInstance();
		this.msgQueueService = new MemoryMsgQueueServiceImpl();
		this.sendToDevice=new BaseMsgProcessService();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException { // (2)
		try {
			
			String data=(String)msg;
			logger.info("received data:{}", data);
			
			QueueElement element = new QueueElement(ctx.channel(), data);
			this.msgQueueService.push(element);
			
		} finally {
			release(msg);
		}
	}
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		logger.error("发生异常:{}", cause.getMessage());
		
        if(!ctx.channel().isActive())
//        System.out.println("SimpleClient:" + ctx.channel().remoteAddress()
//                + "异常");
     
        cause.printStackTrace();
        ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Session session = Session.buildSession(ctx.channel());
		sessionManager.put(session.getId(), session);
		SocketAddress address=ctx.channel().remoteAddress();
		logger.info("终端连接:{}", address);
		sessionManager.getChannelGroup().add(ctx.channel());
		logger.info("当前登陆总数:"+sessionManager.getChannelGroup().size());
		
//		sendToDevice.send2Client(ctx.channel(), sendToDevice.connectSuccess_data());//当设备连上设备时候，服务器下发的命令
 
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		String sessionId = ctx.channel().id().asLongText();
		Session session = sessionManager.findBySessionId(sessionId);
		this.sessionManager.removeBySessionId(sessionId);
		SocketAddress address=ctx.channel().remoteAddress();
		logger.info("终端断开连接:{}", address);
		sessionManager.getChannelGroup().remove(ctx.channel());
		logger.info("当前登陆总数:"+sessionManager.getChannelGroup().size());
		
		if (StringUtils.isNotEmpty(session.getShefen_id())) {//只有手机端连接成功时会发送uuid,若手机端断开连接，扫码枪扫码无效，不进行操作
			sessionManager.setType(null);
			sessionManager.setType_way(TPMSConsts.WAY_SCAN);
		}
		
		ctx.channel().close();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
//				Session session = this.sessionManager.removeBySessionId(Session.buildId(ctx.channel()));
				Session session=this.sessionManager.findBySessionId(ctx.channel().id().asLongText());
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

	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("localhost", 8085); 
        OutputStream out = socket.getOutputStream(); 
        int i=0;
        while (true) {
        	i++;
        	if (i==0) {
        		 // 请求服务器 
                String lines = "床前明月光\r\n疑是地上霜\r\n举头望明月\r\n低头思故乡\r\n"; 
                byte[] outputBytes = lines.getBytes("UTF-8"); 
                out.write(outputBytes);
                out.flush(); 
			}
		}

       
	}
	
}