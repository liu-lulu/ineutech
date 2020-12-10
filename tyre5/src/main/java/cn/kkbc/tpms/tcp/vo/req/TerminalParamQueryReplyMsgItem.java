package cn.kkbc.tpms.tcp.vo.req;

/**
 * 终端参数查询 -->应答消息
 * 
 * @author hylexus
 *
 */
public class TerminalParamQueryReplyMsgItem {

	private String cmd;
	private Object value;
	private String hexValue;

	public TerminalParamQueryReplyMsgItem() {
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getHexValue() {
		return hexValue;
	}

	public void setHexValue(String hexValue) {
		this.hexValue = hexValue;
	}

	@Override
	public String toString() {
		return "[cmd=" + cmd + ", value=" + value + ", hexValue=" + hexValue + "]";
	}

}
