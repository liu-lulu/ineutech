package com.kkbc.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.processor.MsgQueueProcessor;
import cn.kkbc.tpms.tcp.vo.QueueElement;

import com.kkbc.cons.TCPConsts;
import com.kkbc.util.BaseMsgProcessService;
import com.kkbc.util.ScannerThread;

public class ScanCodeHandler extends SimpleChannelInboundHandler<String>{
	private Logger log = LoggerFactory.getLogger(getClass());
	
//	private ScannerThread scanner;
//	private MsgQueueProcessor processor;
	
	private ScanCodeClient client;
	
	public ScanCodeHandler(ScanCodeClient client){
		this.client=client;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, String msg)
			throws Exception {
		System.out.println(msg);
	}


	@Override
	public void channelRead(ChannelHandlerContext arg0, Object msg)
			throws Exception {
		
		ByteBuf buf = (ByteBuf) msg;
		if (buf.readableBytes() <= 0) {
			return;
		}

		byte[] bs = new byte[buf.readableBytes()];
		buf.readBytes(bs);
		
		String answer=new String(bs,"utf-8");
		
		String[] content=answer.replace(TCPConsts.END, "").split(TCPConsts.SPLIT);
		String command=content[0];
		if (TCPConsts.COMMAND_CODE.equals(command)) {
			log.info("{}请在手机/PC端新建商品入库",content[1]);
		}else if (TCPConsts.COMMAND_COUNT.equals(command)) {
			log.info(content[2]);
		}else if (TCPConsts.COMMAND_PHONE.equals(command)) {
			log.info("{}请在手机/PC端操作",content[1]);
		}else {
			log.info(answer);
		}
		
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		log.info("连接成功");
		TCPConsts.channel=ctx.channel();
//		processor=new MsgQueueProcessor();
//		scanner=new ScannerThread();
//		processor.startProcess();
//		scanner.startScan();
		
		
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		
		log.info("断开连接");
		TCPConsts.channel=null;
//		scanner.stopScan();
//		processor.stopProcess();
		 EventLoop eventLoop = ctx.channel().eventLoop();  
	     eventLoop.schedule(new Runnable() {  
	       @Override  
	       public void run() {  
	         try {
				client.connect();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	       }  
	     }, 5L, TimeUnit.SECONDS);  
		ctx.channel().close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		log.error(cause.getMessage());
		
        if(!ctx.channel().isActive())
        System.out.println("SimpleClient:" + ctx.channel().remoteAddress()
                + "异常");
     
        cause.printStackTrace();
        ctx.close();
	}


}
