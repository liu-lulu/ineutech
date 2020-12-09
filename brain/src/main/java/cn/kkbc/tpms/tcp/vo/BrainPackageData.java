package cn.kkbc.tpms.tcp.vo;



public class BrainPackageData {
	
	
	private int head;
	
	private int command;
	
	private int dataLen;
	
	private byte[] data;
	
	private byte checkSum;

	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public int getDataLen() {
		return dataLen;
	}

	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(byte checkSum) {
		this.checkSum = checkSum;
	}

}
