package cn.kkbc.tpms.tcp.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

import java.net.InetSocketAddress;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.TPMSConsts;
import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.service.msg.BaseMsgProcessService;
import cn.kkbc.tpms.tcp.service.queue.MemoryMsgQueueServiceImpl;
import cn.kkbc.tpms.tcp.service.queue.MsgQueueService;
import cn.kkbc.tpms.tcp.util.HexStringUtils;
import cn.kkbc.tpms.tcp.util.PackageUtil;
import cn.kkbc.tpms.tcp.vo.QueueElement;
import cn.kkbc.tpms.tcp.vo.Session;

import com.kkbc.entity.DeviceLoginLog;
import com.kkbc.entity.TestUser;
import com.kkbc.service.DeviceLoginLogService;
import com.kkbc.service.DeviceService;
import com.kkbc.service.impl.DeviceLoginLogServiceImpl;
import com.kkbc.service.impl.DeviceServiceImpl;
import com.kkbc.util.SpringContextUtils;

public class TCPServerHandler extends ChannelInboundHandlerAdapter { // (1)

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final SessionManager sessionManager;
	private MsgQueueService msgQueueService;
	private DeviceLoginLogService deviceLoginLogService;
	private DeviceService service;
	private BaseMsgProcessService sendToDevice;
	private PackageUtil packageUtil;

	public TCPServerHandler() {
		this.sessionManager = SessionManager.getInstance();
		this.msgQueueService = new MemoryMsgQueueServiceImpl();
		this.deviceLoginLogService=(DeviceLoginLogServiceImpl) SpringContextUtils.context.getBean("deviceLoginLogService");
		this.sendToDevice=new BaseMsgProcessService();
		this.service=(DeviceServiceImpl) SpringContextUtils.getContext().getBean("deviceService");
		this.packageUtil=new PackageUtil();
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

			byte[] temp=bs;
			Session session=sessionManager.findBySessionId(Session.buildId(ctx.channel()));
			session.setLastCommunicateTimeStamp(new Date().getTime());
			
			logger.info("received data:{}", HexStringUtils.toHexString(temp));
			
			if (session.getData()==null||session.getData().length==0) {
				session.setData(temp);
			}else {
				session.setData(packageUtil.arrayAddToAnother(session.getData(), new byte[][]{temp}));
			}
			
			int index=PackageUtil.getLastIndex(session.getData(), TPMSConsts.HEAD_ARRAY);
			
			if (index!=-1) {
				byte[] process=new byte[index];
				byte[] yiliu=new byte[session.getData().length-index];
				System.arraycopy(session.getData(), 0, process, 0, index);
				System.arraycopy(session.getData(), index, yiliu, 0, session.getData().length-index);
				
				session.setData(yiliu);
				
				QueueElement element = new QueueElement(ctx.channel(), process);
				this.msgQueueService.push(element);
			}
			
		} finally {
			release(msg);
		}
	}
	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		logger.error("发生异常:{}", cause.getMessage());
		cause.printStackTrace();
		
        if(!ctx.channel().isActive())
        System.out.println("SimpleClient:" + ctx.channel().remoteAddress()
                + "异常");
     
        cause.printStackTrace();
        ctx.close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Session session = Session.buildSession(ctx.channel());
		sessionManager.put(session.getId(), session);
		logger.info("终端连接:{}", session);
		sessionManager.getChannelGroup().add(ctx.channel());
		logger.info("当前登陆总数:"+sessionManager.getChannelGroup().size());
		
		sendToDevice.send2Client(ctx.channel(), sendToDevice.connectSuccess_data());//当设备连上设备时候，服务器下发的命令
 
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		String sessionId = ctx.channel().id().asLongText();
		Session session = sessionManager.findBySessionId(sessionId);
		this.sessionManager.removeBySessionId(sessionId);
		logger.info("终端断开连接:{}", session.getHard_no());
		sessionManager.getChannelGroup().remove(ctx.channel());
		logger.info("当前登陆总数:"+sessionManager.getChannelGroup().size());
		
		DeviceLoginLog info=new DeviceLoginLog();
		info.setCreate_time(new Date());
		info.setDevice_id(session.getDevice_id());
		InetSocketAddress insocket = (InetSocketAddress) ctx.channel()
				.remoteAddress();
		info.setRemote_ip(insocket.getAddress().getHostAddress());
		info.setRemote_port(insocket.getPort());
		info.setType(DeviceLoginLog.TYPE_LOGOUT);
		info.setStatus(DeviceLoginLog.NORMAL_STATUS);
		
//		deviceLoginLogService.saveInfo(info);
		
		TestUser testUser=new TestUser();
		testUser.setMac(session.getShefen_id());
		testUser.setDevice_status(TestUser.DEVICE_STATUS_OFFLINE);
		service.updateDeviceStatus(testUser);
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

	
}