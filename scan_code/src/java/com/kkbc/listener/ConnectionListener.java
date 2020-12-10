package com.kkbc.listener;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.client.ScanCodeClient;

public class ConnectionListener implements ChannelFutureListener{

	private Logger log = LoggerFactory.getLogger(getClass());
	private ScanCodeClient client;
	
	public ConnectionListener(ScanCodeClient client) {
		this.client=client;
	}
	@Override
	public void operationComplete(ChannelFuture channelFuture) throws Exception {
		 if (!channelFuture.isSuccess()) {  
			 log.info("重新连接");
		      final EventLoop loop = channelFuture.channel().eventLoop();  
		      loop.schedule(new Runnable() {  
		        @Override  
		        public void run() {  
		          try {
					client.connect();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		        }  
		      }, 5L, TimeUnit.SECONDS);  
		    }  
		  }  

}
