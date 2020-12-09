package cn.kkbc.tpms.tcp.processor;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.entity.Device;
import com.kkbc.entity.DeviceData;
import com.kkbc.entity.DeviceLoginLog;
import com.kkbc.service.DeviceLoginLogService;
import com.kkbc.service.DeviceService;
import com.kkbc.service.impl.DeviceLoginLogServiceImpl;
import com.kkbc.service.impl.DeviceServiceImpl;
import com.kkbc.util.SpringContextUtils;

import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.service.codec.MsgDecoder;
import cn.kkbc.tpms.tcp.service.queue.MemoryMsgQueueServiceImpl;
import cn.kkbc.tpms.tcp.service.queue.MsgQueueService;
import cn.kkbc.tpms.tcp.util.BitOperator;
import cn.kkbc.tpms.tcp.util.HexStringUtils;
import cn.kkbc.tpms.tcp.vo.BrainPackageData;
import cn.kkbc.tpms.tcp.vo.QueueElement;
import cn.kkbc.tpms.tcp.vo.Session;

public class MsgQueueProcessor extends Thread implements Processor {

	private Logger log = LoggerFactory.getLogger(getClass());
	private Logger weblog = LoggerFactory.getLogger("weblog");

	private volatile boolean isRunning = false;
	private MsgQueueService msgQueueService = null;
	private MsgDecoder msgDecoder;
	private SessionManager sessionManager;
	private DeviceService deviceService;
	private DeviceLoginLogService deviceLoginLogService;
	private BitOperator bitOperator;

	public MsgQueueProcessor() {
		this.msgQueueService = new MemoryMsgQueueServiceImpl();
		this.msgDecoder = new MsgDecoder();
		this.sessionManager = SessionManager.getInstance();
		this.setName("TCP-MsgQueueProcessor");
		this.deviceService=(DeviceServiceImpl) SpringContextUtils.context.getBean("deviceService");
		this.deviceLoginLogService=(DeviceLoginLogServiceImpl) SpringContextUtils.context.getBean("deviceLoginLogService");
		this.bitOperator = new BitOperator();
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
		while (this.isRunning) {
			QueueElement msg = null;
			try {
				msg = this.msgQueueService.take();
			} catch (InterruptedException e1) {
				continue;
			}
			if (msg == null) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			try {
				this.processMsg(msg);
			} catch (Exception e) {
				log.error("消息处理出现异常:{}", e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void processMsg(QueueElement queueElement) {
		log.info("开始处理数据:{}",queueElement.getData());
		
		byte[] data=queueElement.getData();
		
		int type=msgDecoder.analyzeData(data);
//		log.info("----------type:"+type+";queueElementType:"+queueElement.getType());
		Session session=sessionManager.findBySessionId(queueElement.getChannel().id().asLongText());
		
		if (queueElement.getType()==1) {
			List<BrainPackageData> cmdData=msgDecoder.parse2CmdData(data);
			
			for (BrainPackageData brainPackageData : cmdData) {
				int cmd=brainPackageData.getCommand();
				if (cmd==1){//脑电波头环在每次服务器链接完成后，自动发送；之后不再发送；
					String shefenId=HexStringUtils.toHexString(brainPackageData.getData());
					System.out.println("获取脑电ID:" + shefenId);
					Device device=deviceService.getByShenfenId(shefenId);
					if (device==null) {
						device=new Device();
						device.setCreate_time(new Date());
						int index=shefenId.length() - 4;
						if (index<0) {
							index=0;
						}
						device.setLabel_name(shefenId.substring(index));
						device.setShefen_id(shefenId);
						device.setStatus(Device.NORMAL_STATUS);
						device.setType(Device.DEVICE_TYPE_BRAIN);
						long deviceId=deviceService.saveDevice(device);
						device.setDevice_id(deviceId);
					}
					DeviceLoginLog log=new DeviceLoginLog();
					log.setCreate_time(new Date());
					log.setDevice_id(device.getDevice_id());
					InetSocketAddress insocket = (InetSocketAddress) session.getChannel().remoteAddress();
					log.setRemote_ip(insocket.getAddress().getHostAddress());
					log.setRemote_port(insocket.getPort());
					log.setStatus(DeviceLoginLog.NORMAL_STATUS);
					log.setType(DeviceLoginLog.TYPE_LOGIN);
					deviceLoginLogService.saveInfo(log);
					
					session.setDevice_id(device.getDevice_id());
					session.setShefen_id(device.getShefen_id());
					session.setLastCommunicateTimeStamp(new Date().getTime());
					
				}else if (cmd==7) {//上送电量
					Device device=deviceService.getByDeviceId(session.getDevice_id());
					if (device==null) {
						System.out.println("上送电量失败");
					}else {
						device.setEle_time(new Date());
						device.setElectricity(bitOperator.byteToInteger(brainPackageData.getData()));
						deviceService.updateEle(device);
						log.info("{}上送电量成功:{}",device.getDevice_id(),device.getElectricity());
					}
					
				}else {
					log.error(">>>>>>[未知消息命令类型],cmd={},data={}", brainPackageData.getCommand(), brainPackageData.getData());
				}
			}
			
		}else if (queueElement.getType()==2) {//上传处理后的数据包
			log.info("脑电数据"+Arrays.toString(queueElement.getData()));
			DeviceData brainData=msgDecoder.parse2Data(queueElement.getData());
			brainData.setCreate_time(new Date());
			brainData.setDevice_id(queueElement.getDevice_id());
			brainData.setStatus(DeviceData.NORMAL_STATUS);
			brainData.setType(DeviceData.TYPE_BRAIN);
			
			deviceService.saveData(brainData);
			
			log.info(brainData.toString());
			
		}else if (type==3) {
			log.info(">>>>>>[原始数据,不进行处理],data={}", queueElement.getData());
		}else {
			log.error(">>>>>>[未知消息类型],data={}", queueElement.getData());
		}

	}

	public static void main(String[] args) {
		System.out.println(Arrays.toString("973".getBytes()));
	}
}
