package com.ineutech.vo;

import com.ineutech.entity.TestUser;

/**
 * 
 * @name: TestUserSessionVO 
 * @description: 打分端使用时的用户信息
 * @date 2018年2月1日 下午4:19:13
 * @author liululu
 */
public class TestUserSessionVO {
	
	private TestUser user;
	
	//设备登陆时的编号
	private Integer seatNo;
	//登陆的设备号
	private Integer deviceId;
	
	private Integer score;
	
	//测前试卷进度
	private String beforePaperProcess="0";
	
	//测后试卷进度
	private String afterPaperProcess="0";
	
	//测前问卷完成
	private String beforeFinish = "0";
	//测后问卷完成
	private String afterFinish = "0";
	
	public TestUserSessionVO(){}

	public TestUserSessionVO(TestUser user,Integer seatNo,Integer deviceId){
		this.user=user;
		this.seatNo=seatNo;
		this.deviceId=deviceId;
	}
	public TestUser getUser() {
		return user;
	}

	public void setUser(TestUser user) {
		this.user = user;
	}

	public Integer getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(Integer seatNo) {
		this.seatNo = seatNo;
	}

	public String getBeforePaperProcess() {
		return beforePaperProcess;
	}

	public void setBeforePaperProcess(String beforePaperProcess) {
		this.beforePaperProcess = beforePaperProcess;
	}

	public String getAfterPaperProcess() {
		return afterPaperProcess;
	}

	public void setAfterPaperProcess(String afterPaperProcess) {
		this.afterPaperProcess = afterPaperProcess;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getBeforeFinish() {
		return beforeFinish;
	}

	public void setBeforeFinish(String beforeFinish) {
		this.beforeFinish = beforeFinish;
	}

	public String getAfterFinish() {
		return afterFinish;
	}

	public void setAfterFinish(String afterFinish) {
		this.afterFinish = afterFinish;
	}

	@Override
	public String toString() {
		return "deviceId="+deviceId+";humanId="+user.getHumanId()+";seatNo="+seatNo+";user_name="+user.getUserName()+";user_sex="+user.getUserSex()+";user_age="+user.getUserAge()+";user_edu="+user.getUserEdu()+";user_job="+user.getUserJob()+";income="+user.getIncome()+";address="+user.getAddress()+";";
	}
}
