package com.kkbc.hardware.channel;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.hardware.UDPServerManager;
import com.kkbc.hardware.queue.MessageQueue;
import com.kkbc.hardware.queue.QueueElement;
import com.psylife.util.HexStringUtil;

public class ChannelInboundHandlerUdpServer extends ChannelInboundHandlerAdapter {
	static final Logger logger = LoggerFactory.getLogger(ChannelInboundHandlerUdpServer.class);
	private MessageQueue messageQueue;
	public ChannelInboundHandlerUdpServer(ChannelHandlerChildUdpServer handlerChildServer){
		this.messageQueue=UDPServerManager.getInstance().getMessageQueue();
	}
	
	/**
	 * 连接成功
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		this.channelGroup.add(ctx.channel());
		System.out.println("登录总数:"+ctx);//defaultchannelhandlercontext;
	}
	
	/**
	 * 退出
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//		 this.channelGroup.remove(ctx.channel());
		 System.out.println("UDP退出");
	}
	
	private QueueElement queueElement;
	
	private ByteBuf buf;
	
	private byte[] req;
	
	private DatagramPacket packet;
	
	/**
	 * 读数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		packet=(DatagramPacket)(msg);
		buf = (ByteBuf) packet.copy().content();
	    req = new byte[buf.readableBytes()];
	    buf.readBytes(req);
	    String tt=HexStringUtil.encodeHexStr(req, false);
		String dates="\n"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//		AppendToFileUtil.appendMethodA("D:\\tyre_log.txt", "\n"+tt+dates);
		System.out.println("总输出:"+tt+dates);
	    //packet.sender().getAddress().getHostAddress()   ip
		//packet.sender().getPort()                       port
		
		queueElement=new QueueElement();
		queueElement.setUdpAddress(packet.sender());
		queueElement.setData(req);
		this.messageQueue.pushRequest(queueElement);
		packet.release();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error("UDP服务异常!"+cause.getMessage());
	}
	
	
}
