package cn.ineutech.tpms.tcp.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @name: BaseMsgProcessService 
 * @description: 给tcp客户端(拨盘用户端/检测端)发送数据
 * @date 2018年2月1日 下午2:16:18
 * @author liululu
 */
public class BaseMsgProcessService {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	private SessionManager sessionManager;

	public BaseMsgProcessService() {
		this.sessionManager = SessionManager.getInstance();
	}

	protected ByteBuf getByteBuf(byte[] arr) {
		ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(arr.length);
		byteBuf.writeBytes(arr);
		return byteBuf;
	}

	/**
	 * 给tcp客户端发送数据
	 * @param channel 发送管道
	 * @param arr 发送的数据
	 * @return
	 * @throws InterruptedException
	 */
	public int send2Client(Channel channel, byte[] arr) throws InterruptedException {
		try {
			log.info("服务器发送数据:{}",new String(arr, "UTF-8"));
			ChannelFuture future = channel.writeAndFlush(getByteBuf(arr)).sync();
			if (!future.isSuccess()) {
				log.error("发送数据出错:{}", future.cause());
				return 0;
			}
//			log.info("服务器发送数据结束:{}",new String(arr, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	
}
