package cn.ineutech.tpms.tcp.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import cn.ineutech.tpms.tcp.service.BrainTCPServerHandler;
import cn.ineutech.tpms.tcp.service.BrainTCPServerHandler2;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ineutech.tpms.tcp.processor.Processor;
import cn.ineutech.tpms.tcp.processor.brain.HeartProcessor;
import cn.ineutech.tpms.tcp.processor.brain.MsgQueueProcessor;

/**
 * 
 * @name: BrainTCPServer 
 * @description: 脑电tcp服务
 * @date 2018年2月1日 下午1:52:26
 * @author liululu
 */
public class BrainTCPServer {

	private Logger log = LoggerFactory.getLogger(getClass());
	private volatile boolean isRunning = false;

	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;
	private int port;
	private List<Processor> processors;

	public BrainTCPServer() {
		this.initProcessors();
	}

	public BrainTCPServer(int port) {
		this();
		this.port = port;
	}

	private void bind() throws Exception {
		this.bossGroup = new NioEventLoopGroup();
		this.workerGroup = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(bossGroup, workerGroup)//
				.channel(NioServerSocketChannel.class) //
				.childHandler(new ChannelInitializer<SocketChannel>() { //
							@Override
							public void initChannel(SocketChannel ch)
									throws Exception {
								ch.pipeline().addLast(
										new BrainTCPServerHandler());
							}
						}).option(ChannelOption.SO_BACKLOG, 128) //
				.childOption(ChannelOption.SO_KEEPALIVE, true);

		this.log.info("脑电TCP服务启动完毕,port={}", this.port);
		ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

		channelFuture.channel().closeFuture().sync();
	}

	public synchronized void startServer() {
		if (this.isRunning) {
			throw new IllegalStateException(this.getName()
					+ " is already started .");
		}
		this.isRunning = true;

		this.processors.stream().filter(p -> p != null)
				.forEach(p -> p.startProcess());
		new Thread(() -> {
			try {
				this.bind();
			} catch (Exception e) {
				this.log.info("脑电TCP服务启动出错:{}", e.getMessage());
				e.printStackTrace();
			}
		}, this.getName()).start();
	}

	public synchronized void stopServer() {
		if (!this.isRunning) {
			throw new IllegalStateException(this.getName()
					+ " is not yet started .");
		}
		this.isRunning = false;

		this.processors.stream().filter(p -> p != null)
				.forEach(p -> p.stopProcess());

		try {
			Future<?> future = this.workerGroup.shutdownGracefully().await();
			if (!future.isSuccess()) {
				log.error("脑电workerGroup 无法正常停止:{}", future.cause());
			}

			future = this.bossGroup.shutdownGracefully().await();
			if (!future.isSuccess()) {
				log.error("脑电bossGroup 无法正常停止:{}", future.cause());
			}
			// this.channelFuture.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.log.info("脑电TCP服务已经停止...");
	}

	private String getName() {
		return "Brain-TCP-Server";
	}

	private void initProcessors() {
		this.processors = new ArrayList<>();
		this.processors.add(new MsgQueueProcessor());
		this.processors.add(new HeartProcessor());
	}

}