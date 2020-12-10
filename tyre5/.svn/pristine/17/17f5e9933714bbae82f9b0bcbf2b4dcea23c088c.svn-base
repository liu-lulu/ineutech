package cn.kkbc.tpms.tcp.processor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.TPMSConsts;
import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.service.codec.MsgDecoder;
import cn.kkbc.tpms.tcp.service.msg.TerminalMsgProcessService;
import cn.kkbc.tpms.tcp.service.queue.MemoryMsgQueueServiceImpl;
import cn.kkbc.tpms.tcp.service.queue.MsgQueueService;
import cn.kkbc.tpms.tcp.util.HexStringUtils;
import cn.kkbc.tpms.tcp.vo.PackageData;
import cn.kkbc.tpms.tcp.vo.PackageData.MsgHeader;
import cn.kkbc.tpms.tcp.vo.QueueElement;
import cn.kkbc.tpms.tcp.vo.Session;
import cn.kkbc.tpms.tcp.vo.req.LocationInfoUploadMsg;
import cn.kkbc.tpms.tcp.vo.req.TerminalAuthenticationMsg;
import cn.kkbc.tpms.tcp.vo.req.TerminalCommonRespMsg;
import cn.kkbc.tpms.tcp.vo.req.TerminalParamQueryReplyMsgItem;
import cn.kkbc.tpms.tcp.vo.req.TerminalRegisterMsg;
import cn.kkbc.tpms.tcp.vo.req.TyrePressureUploadMsg;

public class MsgQueueProcessor extends Thread implements Processor {

	private Logger log = LoggerFactory.getLogger(getClass());
	private Logger weblog = LoggerFactory.getLogger("weblog");

	private volatile boolean isRunning = false;
	private MsgQueueService msgQueueService = null;
	private MsgDecoder msgDecoder;
	private SessionManager sessionManager;
	private TerminalMsgProcessService msgProcessService;

	public MsgQueueProcessor() {
		this.msgProcessService = new TerminalMsgProcessService();
		this.msgQueueService = new MemoryMsgQueueServiceImpl();
		this.msgDecoder = new MsgDecoder();
		this.sessionManager = SessionManager.getInstance();
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
		PackageData packageData = null;
		try {
			packageData = this.msgDecoder.queueElement2PackageData(queueElement);
		} catch (Exception e) {
			log.error("消息解析出错,已忽略本条消息:{}", e.getMessage());
			e.printStackTrace();
			return;
		}

		Session session = this.sessionManager.findBySessionId(Session.buildId(queueElement.getChannel()));

		if (session == null) {
			log.error("session is nul!!!");
			session = Session.buildSession(packageData.getChannel(), packageData.getMsgHeader().getTerminalPhone());
			this.sessionManager.put(Session.buildId(session.getChannel()), session);
		}

		final MsgHeader header = packageData.getMsgHeader();

		// 1. 终端心跳-消息体为空 ==> 平台通用应答
		if (TPMSConsts.msg_id_terminal_heart_beat == header.getMsgId()) {
			log.info(">>>>>[终端心跳],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			weblog.info(">>>>>[终端心跳],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			try {
				this.msgProcessService.processTerminalHeartBeatMsg(packageData);
				log.info("<<<<<[终端心跳],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			} catch (Exception e) {
				log.error("<<<<<[终端心跳]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
						e.getMessage());
				e.printStackTrace();
			}
		}
		// 2. 终端通用应答
		else if (TPMSConsts.msg_id_terminal_common_resp == header.getMsgId()) {
			log.info(">>>>>[终端通用应答],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			try {
				TerminalCommonRespMsg commonRespMsg = this.msgDecoder.toTerminalCommonRespMsg(packageData);
				this.msgProcessService.processCommonRespMsg(commonRespMsg);
				log.info("<<<<<[终端通用应答],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			} catch (Exception e) {
				log.error("<<<<<[终端通用应答]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
						e.getMessage());
				e.printStackTrace();
			}
		}
		// 3. 位置信息汇报 ==> 平台通用应答
		else if (TPMSConsts.msg_id_terminal_location_info_upload == header.getMsgId()) {
			log.info(">>>>>[位置信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			weblog.info(">>>>>[位置信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			try {
				LocationInfoUploadMsg locationInfoUploadMsg = this.msgDecoder.toLocationInfoUploadMsg(packageData);
				System.out.println(locationInfoUploadMsg);
				this.msgProcessService.processLocationInfoUploadMsg(locationInfoUploadMsg);
				log.info("<<<<<[位置信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			} catch (Exception e) {
				log.error("<<<<<[位置信息]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
						e.getMessage());
				e.printStackTrace();
			}
		}
		// 4. 胎温胎压信息汇报 ==> 平台通用应答
		else if (TPMSConsts.msg_id_terminal_transmission_tyre_pressure == header.getMsgId()) {
			log.info(">>>>>[胎温胎压信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			weblog.info(">>>>>[胎温胎压信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			try {
				TyrePressureUploadMsg pressureUploadMsg = this.msgDecoder.toTyrePressureUploadMsg(packageData);
				this.msgProcessService.processTyrePressureUploadMsg(pressureUploadMsg);
				log.info("<<<<<[胎温胎压信息],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			} catch (Exception e) {
				log.error("<<<<<[胎温胎压信息]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
						e.getMessage());
				e.printStackTrace();
			}
		}
		// 5. 终端鉴权 ==> 平台通用应答
		else if (TPMSConsts.msg_id_terminal_authentication == header.getMsgId()) {
			log.info(">>>>>[终端鉴权],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			weblog.info(">>>>>[终端鉴权],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			try {
				TerminalAuthenticationMsg authenticationMsg = new TerminalAuthenticationMsg(packageData);
				this.msgProcessService.processAuthMsg(authenticationMsg);
				log.info("<<<<<[终端鉴权],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			} catch (Exception e) {
				log.error("<<<<<[终端鉴权]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
						e.getMessage());
				e.printStackTrace();
			}
		}
		// 6. 终端注册 ==> 终端注册应答
		else if (TPMSConsts.msg_id_terminal_register == header.getMsgId()) {
			log.info(">>>>>[终端注册],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			weblog.info(">>>>>[终端注册],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			try {
				TerminalRegisterMsg msg = this.msgDecoder.toTerminalRegisterMsg(packageData);
				this.msgProcessService.processRegisterMsg(msg);
				log.info("<<<<<[终端注册],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			} catch (Exception e) {
				log.error("<<<<<[终端注册]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
						e.getMessage());
				e.printStackTrace();
			}
		}
		// 7. 终端注销(终端注销数据消息体为空) ==> 平台通用应答
		else if (TPMSConsts.msg_id_terminal_log_out == header.getMsgId()) {
			log.info(">>>>>[终端注销],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			weblog.info(">>>>>[终端注销],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			try {
				this.msgProcessService.processTerminalLogoutMsg(packageData);
				log.info("<<<<<[终端注销],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			} catch (Exception e) {
				log.error("<<<<<[终端注销]处理错误,phone={},flowid={},err={}", header.getTerminalPhone(), header.getFlowId(),
						e.getMessage());
				e.printStackTrace();
			}
		}
		// 8. 查询终端参数应答
		else if (TPMSConsts.msg_id_terminal_param_query_resp == header.getMsgId()) {
			log.info(">>>>>[查询终端参数应答],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			weblog.info(">>>>>[查询终端参数应答],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
			weblog.info("消息体:{}", HexStringUtils.toHexString(packageData.getMsgBodyBytes()));
			List<TerminalParamQueryReplyMsgItem> list = this.msgDecoder.toParamQueryReplyMsg(packageData);
			this.msgProcessService.processTerminalParamQueryMsg(packageData, list);
			log.info("<<<<<[查询终端参数应答],phone={},flowid={}", header.getTerminalPhone(), header.getFlowId());
		}
		// 其他情况
		else {
			log.error(">>>>>>[未知消息类型],phone={},msgId={},package={}", header.getTerminalPhone(), header.getMsgId(),
					packageData);
		}

	}

}
