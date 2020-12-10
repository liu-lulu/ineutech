package cn.kkbc.tpms.tcp.service.msg;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.kkbc.tpms.common.exception.TerminalSessionInvalidException;
import cn.kkbc.tpms.tcp.entity.Terminal;
import cn.kkbc.tpms.tcp.server.DelayedCommandPool;
import cn.kkbc.tpms.tcp.service.codec.MsgEncoder;
import cn.kkbc.tpms.tcp.vo.PackageData;
import cn.kkbc.tpms.tcp.vo.PackageData.MsgHeader;
import cn.kkbc.tpms.tcp.vo.Session;
import cn.kkbc.tpms.tcp.vo.req.LocationInfoUploadMsg;
import cn.kkbc.tpms.tcp.vo.req.TerminalAuthenticationMsg;
import cn.kkbc.tpms.tcp.vo.req.TerminalCommonRespMsg;
import cn.kkbc.tpms.tcp.vo.req.TerminalParamQueryReplyMsgItem;
import cn.kkbc.tpms.tcp.vo.req.TerminalRegisterMsg;
import cn.kkbc.tpms.tcp.vo.req.TyrePressureUploadMsg;
import cn.kkbc.tpms.tcp.vo.resp.DelayedCommandKey;
import cn.kkbc.tpms.tcp.vo.resp.ServerCommonRespMsgBody;
import cn.kkbc.tpms.tcp.vo.resp.TerminalRegisterMsgRespBody;

public class TerminalMsgProcessService extends BaseMsgProcessService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	private final Logger weblog = LoggerFactory.getLogger("weblog");
	private MsgEncoder msgEncoder;
	private DataBaseService dataBaseService;

	public TerminalMsgProcessService() {
		this.msgEncoder = new MsgEncoder();
		this.dataBaseService = new DataBaseService();
	}

	public void processRegisterMsg(TerminalRegisterMsg msg) throws Exception {
		log.debug("终端注册:{}", JSON.toJSONString(msg, true));
		weblog.info("终端注册:{}", JSON.toJSONString(msg, true));

		final String sessionId = Session.buildId(msg.getChannel());
		final String sessionPhone = msg.getMsgHeader().getTerminalPhone();

		Session session = updateSession(msg, sessionId, sessionPhone);

		session.setTerminalId(msg.getTerminalRegInfo().getTerminalId());
		session.setLastCommunicateTimeStamp(System.currentTimeMillis());
		session.setAuthenticated(true);
		sessionManager.put(session.getId(), session);

		// 终端信息入库
		Terminal terminal = this.dataBaseService.processRegisterMsg(msg, session);

		byte result = TerminalRegisterMsgRespBody.success;
		if (terminal == null) {
			result = TerminalRegisterMsgRespBody.server_error;
		}
		TerminalRegisterMsgRespBody respMsgBody = new TerminalRegisterMsgRespBody();
		respMsgBody.setReplyCode(result);
		respMsgBody.setReplyFlowId(msg.getMsgHeader().getFlowId());
		respMsgBody.setReplyToken(terminal.getAuthenticationkey());
		int flowId = super.getFlowId(msg.getChannel());
		byte[] bs = this.msgEncoder.encode4TerminalRegisterResp(msg, respMsgBody, flowId);

		super.send2Client(msg.getChannel(), bs);
	}

	public void processAuthMsg(TerminalAuthenticationMsg msg) throws Exception {

		log.debug("终端鉴权:{}", JSON.toJSONString(msg, true));
		weblog.info("终端鉴权:{}", JSON.toJSONString(msg, true));

		final String sessionId = Session.buildId(msg.getChannel());
		final String sessionPhone = msg.getMsgHeader().getTerminalPhone();

		Session session = updateSession(msg, sessionId, sessionPhone);

		session.setLastCommunicateTimeStamp(System.currentTimeMillis());
		session.setAuthenticated(true);

		byte result = ServerCommonRespMsgBody.failure;
		if (this.dataBaseService.processAuth(msg, session)) {
			result = ServerCommonRespMsgBody.success;
			// 加载上一次数据
			this.dataBaseService.loadLatestData4Session(session);
		}
		// TODO else body added @2016-12-22 11:50
		else {
			session.setAuthenticated(false);
		}

		sessionManager.put(session.getId(), session);

		ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody();
		respMsgBody.setReplyCode(result);
		respMsgBody.setReplyFlowId(msg.getMsgHeader().getFlowId());
		respMsgBody.setReplyId(msg.getMsgHeader().getMsgId());
		int flowId = super.getFlowId(msg.getChannel());
		byte[] bs = this.msgEncoder.encode4ServerCommonRespMsg(msg, respMsgBody, flowId);
		super.send2Client(msg.getChannel(), bs);
	}

	private Session updateSession(PackageData msg, final String sessionId, final String sessionPhone) {
		Session session = this.sessionManager.findByTerminalPhone(sessionPhone);
		// 没有注册过
		if (session == null) {
			session = sessionManager.findBySessionId(sessionId);
			if (session == null) {
				session = Session.buildSession(msg.getChannel(), msg.getMsgHeader().getTerminalPhone());
			} else {
				session.setTerminalPhone(sessionPhone);
			}

			session.setAuthenticated(true);
			sessionManager.put(session.getId(), session);
		} else {
			Session tmp = this.sessionManager.findBySessionId(sessionId);
			if (!tmp.getId().equals(session.getId())) {
				this.sessionManager.removeBySessionId(tmp.getId());
			}
		}

		return session;
	}

	public void processCommonRespMsg(TerminalCommonRespMsg msg) {
		log.debug("终端通用回复:{}", JSON.toJSONString(msg, true));
	}

	public void processTerminalHeartBeatMsg(PackageData req) throws Exception {
		log.debug("心跳信息:{}", JSON.toJSONString(req, true));
		final MsgHeader reqHeader = req.getMsgHeader();
		ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody(reqHeader.getFlowId(), reqHeader.getMsgId(),
				ServerCommonRespMsgBody.success);
		int flowId = super.getFlowId(req.getChannel());
		byte[] bs = this.msgEncoder.encode4ServerCommonRespMsg(req, respMsgBody, flowId);
		super.send2Client(req.getChannel(), bs);
	}

	public void processLocationInfoUploadMsg(LocationInfoUploadMsg req) throws Exception {

		log.debug("位置信息:{}", JSON.toJSONString(req, true));
		weblog.info("位置信息:{}", JSON.toJSONString(req, true));

		final String phone = req.getMsgHeader().getTerminalPhone();
		if (phone == null) {
			log.error("解析出错,终端手机号为空,req={}", JSON.toJSONString(req, true));
			throw new TerminalSessionInvalidException("终端手机号为空");
		}
		Session session = super.sessionManager.findByTerminalPhone(phone);
		if (session == null) {
			log.error("session不存在,phone={}", phone);
			throw new TerminalSessionInvalidException("session不存在,phone=" + phone);
		}

		this.dataBaseService.processLocationDataChanged(req, session);

		session.setLastLocationInfoMsg(req);
		session.setLastCommunicateTimeStamp(System.currentTimeMillis());
		this.sessionManager.put(session.getId(), session);

		final MsgHeader reqHeader = req.getMsgHeader();
		ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody(reqHeader.getFlowId(), reqHeader.getMsgId(),
				ServerCommonRespMsgBody.success);
		int flowId = super.getFlowId(req.getChannel());
		byte[] bs = this.msgEncoder.encode4ServerCommonRespMsg(req, respMsgBody, flowId);
		super.send2Client(req.getChannel(), bs);
	}

	public void processTyrePressureUploadMsg(TyrePressureUploadMsg req) throws Exception {
		log.debug("胎温胎压信息:{}", JSON.toJSONString(req, true));
		weblog.info("胎温胎压信息:{}", JSON.toJSONString(req, true));

		final String phone = req.getMsgHeader().getTerminalPhone();
		if (phone == null) {
			log.error("解析出错,终端手机号为空,req={}", JSON.toJSONString(req, true));
			throw new TerminalSessionInvalidException("终端手机号为空");
		}
		Session session = super.sessionManager.findByTerminalPhone(phone);
		if (session == null) {
			log.error("session不存在,phone={}", phone);
			throw new TerminalSessionInvalidException("session不存在,phone=" + phone);
		}

		this.dataBaseService.processTemperatureAndPressureDataChanged(req, session);

		session.setLastCommunicateTimeStamp(System.currentTimeMillis());
		this.sessionManager.put(session.getId(), session);

		final MsgHeader reqHeader = req.getMsgHeader();
		ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody(reqHeader.getFlowId(), reqHeader.getMsgId(),
				ServerCommonRespMsgBody.success);
		int flowId = super.getFlowId(req.getChannel());
		byte[] bs = this.msgEncoder.encode4ServerCommonRespMsg(req, respMsgBody, flowId);
		super.send2Client(req.getChannel(), bs);
	}

	public void processTerminalLogoutMsg(PackageData req) throws Exception {
		log.info("终端注销:{}", JSON.toJSONString(req, true));
		final MsgHeader reqHeader = req.getMsgHeader();
		ServerCommonRespMsgBody respMsgBody = new ServerCommonRespMsgBody(reqHeader.getFlowId(), reqHeader.getMsgId(),
				ServerCommonRespMsgBody.success);
		int flowId = super.getFlowId(req.getChannel());
		byte[] bs = this.msgEncoder.encode4ServerCommonRespMsg(req, respMsgBody, flowId);
		super.send2Client(req.getChannel(), bs);
	}

	public void processTerminalParamQueryMsg(PackageData packageData, List<TerminalParamQueryReplyMsgItem> list) {
		int flowId = packageData.getMsgHeader().getFlowId();
		String key = DelayedCommandPool.generateKey(packageData.getMsgHeader().getTerminalPhone(), flowId);

		DelayedCommandPool commandPool = DelayedCommandPool.getInstance();
		commandPool.submitCommand(key, list);
		commandPool.registryDelayedCommandKey(new DelayedCommandKey(120000, key));

		list.stream().forEach(e -> weblog.info(e.toString()));
	}

}
