package com.kkbc.util;

import java.util.Scanner;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.cons.TCPConsts;

public class ScannerThread extends Thread{
	
	private Logger log = LoggerFactory.getLogger(getClass());
//	private MsgQueueService msgQueueService;
	private volatile boolean scan=false;
	
	public ScannerThread(){
//		this.msgQueueService = new MemoryMsgQueueServiceImpl();
		this.setDaemon(true);
	}
	
	public synchronized void startScan(){
		scan=true;
		super.start();
	}

	@Override
	public void run() {
		BaseMsgProcessService send=new BaseMsgProcessService();
		log.info("开始扫码");
		Scanner sc=new Scanner(System.in);
		String code;
		while (scan) {
			code=sc.nextLine();
			if (StringUtils.isNotEmpty(code)) {
				try {
//					QueueElement element = new QueueElement(TCPConsts.channel, code);
//					this.msgQueueService.push(element);
//					System.out.println(code+"****");
					if (TCPConsts.channel!=null) {
						send.send(TCPConsts.channel, send.getMsg(TCPConsts.COMMAND_CODE, code));
					}else {
						log.info("还未连接到服务器");
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public synchronized void stopScan(){
		scan=false;
		this.interrupt();
	}

}
