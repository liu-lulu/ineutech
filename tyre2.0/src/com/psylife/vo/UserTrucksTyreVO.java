package com.psylife.vo;

import java.util.List;

/**
 * 管理员车轮胎汇总个人页面
 * @author xu
 *
 */
public class UserTrucksTyreVO {

	/**
	 * 车总数
	 */
	private Integer trucksCount;
	
	/**
	 * 胎总数
	 */
	private Integer tyreCount;
	
	/**
	 * 客服电话
	 */
	private String service_phone;
	
	/**
	 * 新消息
	 */
	private List<MessageVO> messages;
	
	/**
	 * 新胎个数(卸下状态)
	 */
	private int newTyreCount;
	
	/**
	 * 旧胎个数(卸下状态)
	 */
	private int oldTyreCount;
	
	/**
	 * 报废胎个数
	 */
	private int baofeiTyreCount;
	
	/**
	 * 修补胎个数
	 */
	private int xiubuTyreCount;
	
	/**
	 * 翻新胎个数
	 */
	private int fanxinTyreCount;
	
	/**
	 * 前装胎个数
	 */
	private int qianzhuangTyreCount;
	
	/**
	 * 平均健康度
	 */
	private float health;

	public Integer getTrucksCount() {
		return trucksCount;
	}

	public void setTrucksCount(Integer trucksCount) {
		this.trucksCount = trucksCount;
	}

	public Integer getTyreCount() {
		return tyreCount;
	}

	public void setTyreCount(Integer tyreCount) {
		this.tyreCount = tyreCount;
	}

	public String getService_phone() {
		return service_phone;
	}

	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}

	public List<MessageVO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageVO> messages) {
		this.messages = messages;
	}

	public int getNewTyreCount() {
		return newTyreCount;
	}

	public void setNewTyreCount(int newTyreCount) {
		this.newTyreCount = newTyreCount;
	}

	public int getOldTyreCount() {
		return oldTyreCount;
	}

	public void setOldTyreCount(int oldTyreCount) {
		this.oldTyreCount = oldTyreCount;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public int getBaofeiTyreCount() {
		return baofeiTyreCount;
	}

	public void setBaofeiTyreCount(int baofeiTyreCount) {
		this.baofeiTyreCount = baofeiTyreCount;
	}

	public int getXiubuTyreCount() {
		return xiubuTyreCount;
	}

	public void setXiubuTyreCount(int xiubuTyreCount) {
		this.xiubuTyreCount = xiubuTyreCount;
	}

	public int getFanxinTyreCount() {
		return fanxinTyreCount;
	}

	public void setFanxinTyreCount(int fanxinTyreCount) {
		this.fanxinTyreCount = fanxinTyreCount;
	}

	public int getQianzhuangTyreCount() {
		return qianzhuangTyreCount;
	}

	public void setQianzhuangTyreCount(int qianzhuangTyreCount) {
		this.qianzhuangTyreCount = qianzhuangTyreCount;
	}
}
