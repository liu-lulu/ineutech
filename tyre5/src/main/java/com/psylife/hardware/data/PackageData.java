package com.psylife.hardware.data;

/**
 * 数据包
 * @author xu
 *
 */
public class PackageData {

	/**
	 * 包头 3字节
	 */
	private int head;
	
	/**
	 * 配置命令 1字节
	 */
	private int command;
	
	/**
	 * 数据长度 2字节
	 */
	private int dataLen;
	
	/**
	 * 包序号 2字节
	 */
	private int no;
	
	/**
	 * 手机号数据  8个字节DTU ID
	 */
	private String phone;
	
	/**
	 * 数据
	 */
	private byte[] data;
	
	/**
	 * 校验和 2字节
	 */
	private int checkSum;

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

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public void setCheckSum(int checkSum) {
		this.checkSum = checkSum;
	}

	public int getCheckSum() {
		return checkSum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
