package com.kkbc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.listener.ConnectionListener;



public class ScanCodeClient {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	public static void main(String[] args) throws Exception{
        new ScanCodeClient("localhost", 8085).connect();
    }

	private volatile boolean isRunning = false;
    private final String host;
    private final int port;
    private EventLoopGroup group=null;

    public ScanCodeClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void connect() throws InterruptedException {
        	group = new NioEventLoopGroup();
        	Bootstrap bootstrap  = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ScanCodeHandler(this));
            
            ChannelFuture channelFuture = bootstrap.connect(host, port).addListener(new ConnectionListener(this)).sync();
            channelFuture.channel().closeFuture().sync();

    }
    
    private String getName() {
		return "this-client";
	}

	public synchronized void startServer() {
		if (this.isRunning) {
			throw new IllegalStateException(this.getName() + " is already started .");
		}
		this.isRunning = true;
		new Thread( 
			new Runnable() {
			@Override
			public void run() {
				try {
					connect();
				} catch (Exception e) {
					log.info("启动出错:{}", e.getMessage());
				}
			}
		}).start();
		
	}

	public synchronized void stopServer() {
		if (!this.isRunning) {
			throw new IllegalStateException(this.getName() + " is not yet started .");
		}
		this.isRunning = false;


		try {
			Future<?> future = this.group.shutdownGracefully().await();
			if (!future.isSuccess()) {
				log.error("workerGroup 无法正常停止:{}", future.cause());
			}

			// this.channelFuture.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.log.info("TCP服务已经停止...");
	}
}
