package cn.kkbc.tpms.tcp.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.TPMSConsts;
import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.service.msg.BaseMsgProcessService;
import cn.kkbc.tpms.tcp.service.queue.MemoryMsgQueueServiceImpl;
import cn.kkbc.tpms.tcp.service.queue.MsgQueueService;
import cn.kkbc.tpms.tcp.vo.QueueElement;
import cn.kkbc.tpms.tcp.vo.Session;

import com.kkbc.entity.Goods;
import com.kkbc.service.GoodsService;
import com.kkbc.service.impl.GoodsServiceImpl;
import com.kkbc.util.SpringContextUtils;

public class MsgQueueProcessor extends Thread implements Processor {

	private Logger log = LoggerFactory.getLogger(getClass());

	private volatile boolean isRunning = false;
	private MsgQueueService msgQueueService = null;
	private SessionManager sessionManager;
//	private ScanCodeService scanCodeService;
	private GoodsService goodsService;
	private BaseMsgProcessService sendToDevice;
	

	public MsgQueueProcessor() {
		this.msgQueueService = new MemoryMsgQueueServiceImpl();
		this.sessionManager = SessionManager.getInstance();
//		this.scanCodeService=(ScanCodeServiceImpl) SpringContextUtils.getContext().getBean("scanCodeService");
		this.goodsService=(GoodsServiceImpl) SpringContextUtils.context.getBean("goodsService");
		this.sendToDevice=new BaseMsgProcessService();
		this.setName("TCP-MsgQueueProcessor");
		this.setDaemon(true);
	}

	@Override
	public synchronized void startProcess() {
		if (this.isRunning) {
			throw new IllegalStateException(this.getName() + " is already started .");
		}
		this.isRunning = true;
		super.start();
		this.log.info("队列消息处理器启动完毕...");
	}

	@Override
	public synchronized void stopProcess() {
		if (!this.isRunning) {
			throw new IllegalStateException(this.getName() + " is not yet started .");
		}
		this.isRunning = false;
		this.interrupt();
		this.log.info("队列消息处理器已经停止...");
	}

	@Override
	public void run() {
		ExecutorService cachedThreadPool = Executors.newFixedThreadPool(30);
		
		while (this.isRunning) {
			QueueElement msg = null;
			try {
				msg = this.msgQueueService.take();
			} catch (InterruptedException e1) {
				continue;
			}
			try {
				if (msg!=null) {
					cachedThreadPool.execute(DoTask(msg));
				}
			} catch (Exception e) {
				log.error("消息处理出现异常:{}", e.getMessage());
				e.printStackTrace();
			}
		}
		
		cachedThreadPool.shutdown();
	}

	/**
	 * 
	 * @param queueElement
	 * @return
	 */
	private Runnable DoTask(final QueueElement queueElement) {
		return new Runnable() {
			@Override
			public void run() {
				try {
					processMsg(queueElement);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		};
	}
	public void processMsg(QueueElement queueElement) throws InterruptedException, ParseException, UnsupportedEncodingException {
		Session session=sessionManager.findBySessionId(queueElement.getChannel().id().asLongText());
		log.info("process data:{}", queueElement.getContent());
		
		String[] content=queueElement.getContent().split(TPMSConsts.SPLIT);
		
		if (content!=null&&content.length>0) {
			
			String dataType=content[0];
			
			if (TPMSConsts.COMMAND_TYPE.equals(dataType)) {
				log.info("操作-->{}",Integer.valueOf(content[1])==TPMSConsts.TYPE_IN?"入库":"出库");
				session.setType(Integer.valueOf(content[1]));
				sessionManager.setType(Integer.valueOf(content[1]));
			}else if (TPMSConsts.COMMAND_TYPE_WAY.equals(dataType)) {
				log.info("操作方式-->{}",Integer.valueOf(content[1])==TPMSConsts.WAY_SCAN?"直接扫码":"手机批量");
				sessionManager.setType_way(Integer.valueOf(content[1]));
			}else if (TPMSConsts.COMMAND_CODE.equals(dataType)) {
				
				String data=content[1];
				String[] device_code=data.split(TPMSConsts.CODE_SPLIT);
				String deviceNo=null;
				String barcode=null;
				if (device_code.length==1) {
					barcode=device_code[0];
					log.info("条形码:"+barcode);
					
				}else if (device_code.length==2) {
					deviceNo=device_code[0];
					barcode=device_code[1];
					log.info("设备号:"+deviceNo+";条形码:"+barcode);
//					session.setDeviceNo(deviceNo);
//					Session bindSession=sessionManager.getBindInfoByDeviceNo(deviceNo);
//					if (bindSession!=null&&session.getType()!=null) {
//						if (session.getType()==TPMSConsts.TYPE_IN) {
//							goodsService.in(barcode);
//						}else if (session.getType()==TPMSConsts.TYPE_OUT) {
//							goodsService.out(barcode);
//						}
//					}
				}
				Integer type=sessionManager.getType();
				if (type!=null) {
					byte[] sendMsg = null;
					Goods goods=goodsService.getByCode(barcode);
					if (goods==null) {//手机端新建入库
						sendMsg=sendToDevice.getMsg(TPMSConsts.COMMAND_CODE, barcode);
					}else {
						if (sessionManager.getType_way()==TPMSConsts.WAY_SCAN) {//直接扫码入/出库
							boolean result=false;
							if (type==TPMSConsts.TYPE_IN) {//入库
								int ret=goodsService.in(barcode, 1);
								if (ret!=0) {
									result=true;
								}
							}else if (type==TPMSConsts.TYPE_OUT) {//出库
								if (goods.getRemain_count()>0) {
									int ret=goodsService.out(barcode, 1);
									if (ret!=0) {
										result=true;
									}
								}
							}
							Goods newInfo=goodsService.getByCode(barcode);
							
							//获取返回的信息
							StringBuffer name=new StringBuffer(barcode);
							if (StringUtils.isNotEmpty(newInfo.getBrand())||StringUtils.isNotEmpty(newInfo.getModel())) {
								name.append("(");
								if(StringUtils.isNotEmpty(newInfo.getBrand())){
									name.append(newInfo.getBrand());
									if (StringUtils.isNotEmpty(newInfo.getModel())) {
										name.append(":");
										name.append(newInfo.getModel());
									}
								}else {
									if (StringUtils.isNotEmpty(newInfo.getModel())) {
										name.append(newInfo.getModel());
									}
								}
								name.append(")");
							}
							String msg=name.toString()+(type==TPMSConsts.TYPE_IN?"入库":"出库")+(result?"成功":"失败")+"/剩余数量:"+newInfo.getRemain_count();
							sendMsg=sendToDevice.getMsg(TPMSConsts.COMMAND_COUNT, (result?TPMSConsts.STATUS_SUCESS:TPMSConsts.STATUS_FAUSE)+TPMSConsts.SPLIT+msg);
						}else {//手机端批量操作,返回条形码
							sendMsg=sendToDevice.getMsg(TPMSConsts.COMMAND_PHONE, barcode+TPMSConsts.SPLIT+goods.getBrand()+TPMSConsts.SPLIT+goods.getName()+TPMSConsts.SPLIT+goods.getModel()+TPMSConsts.SPLIT+goods.getRemain_count());
						}
					}
					List<Session> sessions=sessionManager.toList();
					for (Session client : sessions) {
						sendToDevice.send2Client(client.getChannel(), sendMsg);
					}
				}
			}else if (TPMSConsts.COMMAND_UUID.equals(dataType)) {
				log.info("uuid-->"+content[1]);
				session.setShefen_id(content[1]);
			}else {
				log.info("数据类型未知");
			}
			
		}else {
			log.info("{}:数据不完整",queueElement.getContent());
		}

	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		
		Socket socket = new Socket("localhost", 8085);
		OutputStream outputStream = socket.getOutputStream();
		while (true) {
			outputStream.write("1,1\r\n".getBytes());
			outputStream.flush();
			Thread.sleep(1000);
		}
		
	}

}
