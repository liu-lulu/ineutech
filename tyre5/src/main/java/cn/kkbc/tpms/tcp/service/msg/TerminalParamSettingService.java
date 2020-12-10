package cn.kkbc.tpms.tcp.service.msg;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.kkbc.tpms.common.exception.ParamSettingException;
import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.service.codec.MsgEncoder;
import cn.kkbc.tpms.tcp.util.BitOperator;
import cn.kkbc.tpms.tcp.vo.Session;
import cn.kkbc.tpms.tcp.vo.resp.TerminalParamSettingMsg;
import cn.kkbc.tpms.tcp.vo.resp.TerminalParamSettingMsg.ParamItem;

public class TerminalParamSettingService extends BaseMsgProcessService {

	private MsgEncoder msgEncoder;
	private BitOperator bitOperator;
	private SessionManager sessionManager;

	public TerminalParamSettingService() {
		this.msgEncoder = new MsgEncoder();
		this.bitOperator = new BitOperator();
		this.sessionManager = SessionManager.getInstance();
	}

	public void sendMsg(TerminalParamSettingMsg msg, Session session) throws Exception {
		log.info("终端设置参数,msg:{},session:{}", JSON.toJSONString(msg, true), JSON.toJSONString(session, true));
		byte[] msgBodyBytes = msg.toMsgBodyBytes();
		byte[] bs = this.msgEncoder.encode4ParamSetting(msgBodyBytes, session);
		log.info("终端参数bytes:{}", Arrays.toString(bs));
		super.send2Client(session.getChannel(), bs);
	}

	public void processParamSettings(String idStr, String phone, String values) throws Exception {
		if (StringUtils.isEmpty(idStr)) {
			throw new ParamSettingException("参数类型为空");
		}
		if (StringUtils.isEmpty(phone)) {
			throw new ParamSettingException("终端手机号为空");
		}
		if (StringUtils.isEmpty(values)) {
			throw new ParamSettingException("参数值为空");
		}

		Session session = this.sessionManager.findByTerminalPhone(phone);
		if (session == null) {
			throw new ParamSettingException("终端手机号对应的终端尚未连接或尚未鉴权");
		}

		final TerminalParamSettingMsg msg = new TerminalParamSettingMsg();
		int id1 = 0;
		try {
			id1 = Integer.parseInt(idStr.replace("0x", ""), 16);
		} catch (NumberFormatException e) {
			throw new ParamSettingException("参数类型解析错误:" + idStr);
		}

		final int id = id1;
		// DWord
		// 端口号--客户要求按照十进制处理
		if (id == 0x0018) {
			Arrays.asList(values.split(",")).stream().filter(i -> i != null).mapToInt(Integer::parseInt).forEach(i -> {
				msg.addParam(new ParamItem(id, this.bitOperator.longToBytes(i, 4)));
			});
		}
		// DWord---其余DWord按十六进制处理
		else if (id == 0x0601 || id == 0x0602 || id == 0x0603 || id == 0x0610) {
			Arrays.asList(values.split(",")).stream().filter(i -> i != null).mapToInt(i -> Integer.parseInt(i, 16))
					.forEach(i -> {
						msg.addParam(new ParamItem(id, this.bitOperator.longToBytes(i, 4)));
					});
		}
		// String
		else if (id == 0x0013 || id == 0x0612 || id == 0x0701) {
			Arrays.asList(values.split(",")).stream().filter(s -> s != null).forEach(s -> {
				msg.addParam(new ParamItem(id, s.getBytes()));
			});
		}
		// Byte[8]--十六进制
		else if (id == 0x0611) {
			/***
			 * 7、胎位与胎ID为8 Bytes，实际使用为4 Bytes。<br>
			 * 
			 * <pre>
			 * 胎位 			ID 			填充数 
			 * 1Byte 		3Bytes 		4Bytes(0x00)
			 * </pre>
			 **/
			// Arrays.asList(values.split(",")).stream().filter(i -> i !=
			// null).mapToInt(Integer::parseInt).forEach(i -> {
			Arrays.asList(values.split(",")).stream().filter(i -> i != null).mapToInt(i -> Integer.parseInt(i, 16))
					.forEach(i -> {
						msg.addParam(new ParamItem(id, bitOperator.integerTo4Bytes(i)));
					});
		}

		this.sendMsg(msg, session);
	}

	public Object queryTerminalParam(String phone) throws Exception {
		CommandSender sender = new CommandSender();
		Session session = SessionManager.getInstance().findByTerminalPhone(phone);
		if (session == null) {
			throw new ParamSettingException("终端尚未连接,手机号:" + phone);
		}
		try {
			int flowId = session.currentDownLinkFlowId();
			return sender.doSend(session, this.msgEncoder.encode4ParamQuery(session, flowId), flowId, 30,
					TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} 
		return null;
	}
}
