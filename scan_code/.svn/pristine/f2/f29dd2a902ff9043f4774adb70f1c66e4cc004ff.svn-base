package com.kkbc.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.cons.TCPConsts;

/**
 * 下行数据
 * @author liululu
 *
 */
public class BaseMsgProcessService {

	protected final Logger log = LoggerFactory.getLogger(getClass());


	public BaseMsgProcessService() {
	}

	protected ByteBuf getByteBuf(byte[] arr) {
		ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(arr.length);
		byteBuf.writeBytes(arr);
		return byteBuf;
	}

	public int send(Channel channel, byte[] arr) throws InterruptedException {
//		log.info("服务器发送给拨盘数据:{}",HexStringUtils.toHexString(arr));
//		System.out.println("isWritable:"+channel.isWritable()+";isActive:"+channel.isActive()+";isOpen:"+channel.isOpen()+";isRegistered:"+channel.isRegistered());
//		ChannelFuture future = channel.writeAndFlush(getByteBuf(arr)).sync();
		ChannelFuture future = channel.writeAndFlush(getByteBuf(arr)).sync();
		if (!future.isSuccess()) {
			log.error("发送数据出错:{}", future.cause());
			return 0;
		}
		return 1;
	}

	public byte[] getMsg(String type,String content){
		try {
			return (type+TCPConsts.SPLIT+content+TCPConsts.END).getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
