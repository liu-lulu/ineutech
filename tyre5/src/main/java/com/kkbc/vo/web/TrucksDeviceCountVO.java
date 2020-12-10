package com.kkbc.vo.web;

/**
 * 硬件车汇总
 * @author xu
 *
 */
public class TrucksDeviceCountVO {

	/**
	 * 在线数
	 */
	private Integer online;
	
	/**
	 * 离线数
	 */
	private Integer offline;
	
	/**
	 * 总数
	 */
	private Integer count;
	
	/**
	 * 报警数
	 */
	private Integer warn;

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getOffline() {
		return offline;
	}

	public void setOffline(Integer offline) {
		this.offline = offline;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getWarn() {
		return warn;
	}

	public void setWarn(Integer warn) {
		this.warn = warn;
	}
	
	
}
