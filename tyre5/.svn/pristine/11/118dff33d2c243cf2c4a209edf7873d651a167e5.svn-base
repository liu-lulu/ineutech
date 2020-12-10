package cn.kkbc.tpms.tcp.vo.req;

import java.util.Arrays;

import cn.kkbc.tpms.tcp.vo.PackageData;

/**
 * 终端通用应答
 * 
 * @author hylexus
 *
 */
public class TerminalCommonRespMsg extends PackageData {

	public static final byte succcess_ack = 0;
	public static final byte failure = 1;
	public static final byte error_msg = 2;
	public static final byte unsupported = 3;

	// byte[0-1] 应答流水号 对应的平台消息的流水号
	private int replyFlowId;
	// byte[2-3] 应答ID 对应的平台消息的ID
	private int replyId;

	/****
	 * byte[4] 结果<br>
	 * 0：成功∕确认 <br>
	 * 1：失败 <br>
	 * 2：消息有误 <br>
	 * 3：不支持
	 ***/
	private byte replyCode;

	public TerminalCommonRespMsg() {
	}

	public TerminalCommonRespMsg(PackageData packageData) {
		this.channel = packageData.getChannel();
		this.checkSum = packageData.getCheckSum();
		this.msgBodyBytes = packageData.getMsgBodyBytes();
		this.msgHeader = packageData.getMsgHeader();
	}

	public int getReplyFlowId() {
		return replyFlowId;
	}

	public void setReplyFlowId(int replyFlowId) {
		this.replyFlowId = replyFlowId;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public byte getReplyCode() {
		return replyCode;
	}

	public void setReplyCode(byte replyCode) {
		this.replyCode = replyCode;
	}

	@Override
	public String toString() {
		return "TerminalCommonRespMsg [replyFlowId=" + replyFlowId + ", replyId=" + replyId + ", replyCode=" + replyCode
				+ ", msgHeader=" + msgHeader + ", msgBodyBytes=" + Arrays.toString(msgBodyBytes) + ", checkSum="
				+ checkSum + ", channel=" + channel + "]";
	}

}
