package cn.kkbc.tpms.tcp.service.msg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.TPMSConsts;
import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.util.BitOperator;

/**
 * 下行数据
 * @author liululu
 *
 */
public class BaseMsgProcessService {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	protected final Logger weblog = LoggerFactory.getLogger("weblog");

	protected SessionManager sessionManager;
	protected BitOperator bitOperator;

	public BaseMsgProcessService() {
		this.sessionManager = SessionManager.getInstance();
		this.bitOperator=new BitOperator();
	}

	protected ByteBuf getByteBuf(byte[] arr) {
		ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(arr.length);
		byteBuf.writeBytes(arr);
		return byteBuf;
	}

	public int send2Client(Channel channel, byte[] arr) throws InterruptedException, UnsupportedEncodingException {
		log.info("服务器发送数据:{}",new String(arr, "utf-8"));
//		System.out.println("isWritable:"+channel.isWritable()+";isActive:"+channel.isActive()+";isOpen:"+channel.isOpen()+";isRegistered:"+channel.isRegistered());
//		ChannelFuture future = channel.writeAndFlush(Unpooled.copiedBuffer(arr)).sync();
		ChannelFuture future = channel.writeAndFlush(getByteBuf(arr)).sync();
		if (!future.isSuccess()) {
			log.error("发送数据出错:{}", future.cause());
			return 0;
		}
		return 1;
	}
	
	public byte[] getMsg(String type,String content){
		try {
			return (type+TPMSConsts.SPLIT+content+TPMSConsts.END).getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
