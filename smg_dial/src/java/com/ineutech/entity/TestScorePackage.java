package com.ineutech.entity;

import java.util.Date;

/**
 * 
 * @name: TestScorePackage 
 * @description: 打分结束时打包的数据实体类
 * @date 2018年2月1日 下午4:15:21
 * @author liululu
 */
public class TestScorePackage {

	private Integer recordId;
	private Date createTime;// 时间戳

	private String caijiTime;
	private Integer testId;// 测试id
	private String mac;// 设备MAC

	private Integer score;// 值

	private Integer hardId;// 拨盘id
	private Integer humanId;// 人员id
	private Integer seatNo;// 座位号

	public TestScorePackage() {
	}
	
	public TestScorePackage(String caijiTime,Integer testId,String mac,Integer score,Integer humanId,Integer hardId,Integer seatNo) {
		this.caijiTime=caijiTime;
		this.testId=testId;
		this.mac=mac;
		this.score=score;
		this.humanId=humanId;
		this.hardId=hardId;
		this.seatNo=seatNo;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCaijiTime() {
		return caijiTime;
	}

	public void setCaijiTime(String caijiTime) {
		this.caijiTime = caijiTime;
	}

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public Integer getHardId() {
		return hardId;
	}

	public void setHardId(Integer hardId) {
		this.hardId = hardId;
	}

	public Integer getHumanId() {
		return humanId;
	}

	public void setHumanId(Integer humanId) {
		this.humanId = humanId;
	}

	public Integer getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(Integer seatNo) {
		this.seatNo = seatNo;
	}

}
