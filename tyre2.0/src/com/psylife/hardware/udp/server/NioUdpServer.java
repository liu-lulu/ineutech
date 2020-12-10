package com.psylife.hardware.udp.server;

import com.psylife.hardware.UDPServerManager;
import com.psylife.hardware.channel.ChannelHandlerChildUdpServer;
import com.psylife.hardware.channel.IChannelCallback;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class NioUdpServer {

	private IChannelCallback channelCallback;
	
	private Channel serverChannel = null;	

	public void bind(int port) throws Exception {
		System.out.println("UDP服务端正在开启... ...");
		EventLoopGroup group = new NioEventLoopGroup(1);
		channelCallback.connected();
		try {			
			Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioDatagramChannel.class)
             .option(ChannelOption.SO_BROADCAST, true)
             .handler(new ChannelHandlerChildUdpServer(this));            
         // 绑定端口
         			ChannelFuture f = b.bind(port).sync();

         			// 等待服务端监听端口关闭
         			serverChannel = f.channel();
         			UDPServerManager.getInstance().setServerChannel(serverChannel);
         			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
			System.out.println("UDP服务端关闭");
			channelCallback.disconnect();
		}
	}

	public void close() {
		if (serverChannel != null) {
			try {
				serverChannel.close();
			} catch (Exception ignored) {
			}
		}
	}
	
	public Channel getChannel() {
		return serverChannel;
	}

	public void setChannelCallback(IChannelCallback channelCallback) {
		this.channelCallback = channelCallback;
	}
}
