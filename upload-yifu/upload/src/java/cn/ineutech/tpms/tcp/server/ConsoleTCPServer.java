package cn.ineutech.tpms.tcp.server;

import java.util.ArrayList;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ineutech.tpms.tcp.processor.MsgQueueProcessor;
import cn.ineutech.tpms.tcp.processor.Processor;


/**
 * 
 * @name: ConsoleTCPServer 
 * @description: 控制台和独立播放器的tcp服务
 * @date 2018年2月1日 下午1:54:25
 * @author liululu
 */
public class ConsoleTCPServer {

	private Logger log = LoggerFactory.getLogger(getClass());
	private volatile boolean isRunning = false;

	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;
	private int port;
	private List<Processor> processors;

	public ConsoleTCPServer() {
		this.initProcessors();
	}

	public ConsoleTCPServer(int port) {
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
								// LineBasedFrameDecoder按行分割消息 
								ch.pipeline().addLast(new LineBasedFrameDecoder(1024)); 
		                        // 再按UTF-8编码转成字符串 
								ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
								ch.pipeline().addLast(
										new ConsoleTCPServerHandler());
							}
						}).option(ChannelOption.SO_BACKLOG, 128) //
				.childOption(ChannelOption.SO_KEEPALIVE, true);

		this.log.info("YIFU--TCP服务启动完毕,port={}", this.port);
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
				this.log.info("YIFU--TCP服务启动出错:{}", e.getMessage());
				e.printStackTrace();
			}
		}, this.getName()).start();
	}

	public synchronized void stopServer() {
		if (!this.isRunning) {
			throw new IllegalStateException("YIFU--" + this.getName()
					+ " is not yet started .");
		}
		this.isRunning = false;

		this.processors.stream().filter(p -> p != null)
				.forEach(p -> p.stopProcess());

		try {
			Future<?> future = this.workerGroup.shutdownGracefully().await();
			if (!future.isSuccess()) {
				log.error("YIFU--workerGroup 无法正常停止:{}", future.cause());
			}

			future = this.bossGroup.shutdownGracefully().await();
			if (!future.isSuccess()) {
				log.error("YIFU--bossGroup 无法正常停止:{}", future.cause());
			}
			// this.channelFuture.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.log.info("YIFU--TCP服务已经停止...");
	}

	private String getName() {
		return "TCP-Server";
	}

	private void initProcessors() {
		this.processors = new ArrayList<>();
		this.processors.add(new MsgQueueProcessor());
	}
}