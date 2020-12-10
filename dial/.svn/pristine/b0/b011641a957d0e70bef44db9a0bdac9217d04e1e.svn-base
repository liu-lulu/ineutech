package cn.kkbc.tpms.tcp.vo;


public class PackageData {
	
	private int head;
	
	private int dataType;
	
	private int command;
	
	private int dataLen;
	
	private byte[] data;
	
	private int checkSum;
	
	public PackageData(){
		
	}
	public PackageData(int head,int dataType,int command,byte[] data){
		this.head=head;
		this.dataType=dataType;
		this.command=command;
		this.dataLen=data.length;
		this.data=data;
	}

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

	public int getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(int checkSum) {
		this.checkSum = checkSum;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
}
