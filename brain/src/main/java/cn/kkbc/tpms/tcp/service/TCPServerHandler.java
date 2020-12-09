package cn.kkbc.tpms.tcp.service;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.constant.PackageDataConstant;
import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.service.queue.MemoryMsgQueueServiceImpl;
import cn.kkbc.tpms.tcp.service.queue.MsgQueueService;
import cn.kkbc.tpms.tcp.util.HexStringUtils;
import cn.kkbc.tpms.tcp.util.PackageUtil;
import cn.kkbc.tpms.tcp.vo.QueueElement;
import cn.kkbc.tpms.tcp.vo.Session;

import com.kkbc.entity.DeviceLoginLog;
import com.kkbc.service.DeviceLoginLogService;
import com.kkbc.service.impl.DeviceLoginLogServiceImpl;
import com.kkbc.util.AppendToFileUtil;
import com.kkbc.util.SpringContextUtils;
import com.kkbc.util.StringHelper;

public class TCPServerHandler extends ChannelInboundHandlerAdapter { // (1)

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final SessionManager sessionManager;
	private MsgQueueService msgQueueService;
	private DeviceLoginLogService deviceLoginLogService;
	private PackageUtil packageUtil;
	
	public TCPServerHandler() {
		this.sessionManager = SessionManager.getInstance();
		this.msgQueueService = new MemoryMsgQueueServiceImpl();
		this.deviceLoginLogService=(DeviceLoginLogServiceImpl) SpringContextUtils.context.getBean("deviceLoginLogService");
		this.packageUtil=new PackageUtil();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws InterruptedException { // (2)
		try {
			ByteBuf buf = (ByteBuf) msg;
			if (buf.readableBytes() <= 0) {
				return;
			}

			byte[] bs = new byte[buf.readableBytes()];
			buf.readBytes(bs);
			
			//------------------------
			
			byte[] temp=bs;
			
			Session session=sessionManager.findBySessionId(Session.buildId(ctx.channel()));
			if (session.getRowData()==null||session.getRowData().length==0) {
				session.setRowData(temp);
			}else {
				session.setRowData(packageUtil.arrayAddToAnother(session.getRowData(), new byte[][]{temp}));
			}
			
			String dates="\n"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			
			byte[] rowData=session.getRowData();
			
			logger.info(session.getDevice_id()+"本次接收数据长度:"+bs.length+";数据:"+HexStringUtils.toHexString(bs));
			logger.info(session.getDevice_id()+"总数据长度:"+rowData.length+";总数据"+HexStringUtils.toHexString(rowData));
//			logger.info("-----isStartTest----"+session.isStartTest());
			 
			AppendToFileUtil.appendMethodA("D:\\brain\\brain-data.txt","\n"+dates+" rowData:"+HexStringUtils.toHexString(rowData));
			if (!session.isStartTest()&&packageUtil.getIndex(rowData, PackageDataConstant.CMD_HEAD_ARRAY, 0)!=-1) {//命令型数据，不需重新组包;
				session.setStartTest(true);//脑电波头环在每次服务器链接完成后,自动发送,之后不再发送;isStartTest=true表明之后收到的数据为脑波的测试数据
				QueueElement element = new QueueElement(ctx.channel(), rowData);
				element.setTime(new Date());
				element.setType(QueueElement.TYPE_CMD);
				this.msgQueueService.push(element);
				session.setRowData(null);
				
				try {
					Socket socket=new Socket("127.0.0.1", 8088);
					OutputStream out = socket.getOutputStream();
					out.write(rowData);
					socket.close(); 
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}else if (session.isStartTest()&&rowData.length>=4132) {//每组数据有 512 个小包和一个大包，小包 8 字节/包，大包 36 字节/包；
				Map<String,List<byte[]>> allDataList=packageUtil.getPackageData(rowData);
//				logger.info(session.getDevice_id()+"====包总数=="+allDataList.size()+"======小包总数======="+allDataList.get("1").size());
				 if (allDataList.size()==2&&allDataList.get("1").size()>=512&&allDataList.get("2").size()>=1) {
					Map<String, byte[]> rowDataAndShengyu=packageUtil.getRowDataAndShengyu(rowData);
					QueueElement element = new QueueElement(ctx.channel(), rowDataAndShengyu.get("1"));
					element.setDevice_id(session.getDevice_id());//避免终端断掉后,未处理完的数据不知是哪个终端发来的
					element.setType(QueueElement.TYPE_DATA);
					this.msgQueueService.push(element);
					session.setRowData(rowDataAndShengyu.get("2"));
//					logger.info(session.getDevice_id()+"rowdata长度:"+rowDataAndShengyu.get("1").length+"rowdata:"+HexStringUtils.toHexString(rowDataAndShengyu.get("1"))+"\n"+"shengyu长度:"+rowDataAndShengyu.get("2").length+"shengyu:"+HexStringUtils.toHexString(rowDataAndShengyu.get("2")));
					
					try {
						Socket socket=new Socket("127.0.0.1", 8088);
						OutputStream out = socket.getOutputStream();
						out.write(rowDataAndShengyu.get("1"));
						socket.close(); 
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			//------------------------
			
			

			
		} finally {
			release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		logger.error("发生异常:{}", StringHelper.getTrace(cause));
//		cause.printStackTrace();
		ctx.channel().close();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Session session = Session.buildSession(ctx.channel());
		sessionManager.put(session.getId(), session);
		logger.info("终端连接:{}", session);
		sessionManager.getChannelGroup().add(ctx.channel());
		logger.info("登陆总数:"+sessionManager.getChannelGroup().size());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		String sessionId = Session.buildId(ctx.channel());
		Session session = sessionManager.findBySessionId(sessionId);
		this.sessionManager.removeBySessionId(sessionId);
		logger.info("终端断开连接:{}", session.getDevice_id());
		sessionManager.getChannelGroup().remove(ctx.channel());
		
		if (session.isStartTest()) {//终端连接后发送过数据
			DeviceLoginLog info=new DeviceLoginLog();
			info.setCreate_time(new Date());
			info.setDevice_id(session.getDevice_id());
			InetSocketAddress insocket = (InetSocketAddress) ctx.channel()
					.remoteAddress();
			info.setRemote_ip(insocket.getAddress().getHostAddress());
			info.setRemote_port(insocket.getPort());
			info.setType(DeviceLoginLog.TYPE_LOGOUT);
			info.setStatus(DeviceLoginLog.NORMAL_STATUS);
			
			deviceLoginLogService.saveInfo(info);
		}
		
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