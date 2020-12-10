package com.kkbc.hardware.channel;

import com.kkbc.hardware.udp.server.NioUdpServer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.DatagramChannel;

public class ChannelHandlerChildUdpServer extends
		ChannelInitializer<DatagramChannel> {
	
	 
	 public ChannelHandlerChildUdpServer(NioUdpServer nioUdpServer){

	 }

	@Override
	protected void initChannel(DatagramChannel sc) throws Exception {
		System.out.println("UDP服务端开启... ...");
		ChannelPipeline p = sc.pipeline();
		p.addLast(new ChannelInboundHandlerUdpServer(this));
		System.out.println(sc.id());
	}
	
}
