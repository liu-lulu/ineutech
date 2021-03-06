package com.ineutech.entity;

/**
 * 
 * @name: TestUser 
 * @description: 用户信息实体类
 * @date 2018年2月1日 下午4:16:38
 * @author liululu
 */
public class TestUser {
	
	public static final Integer CONFIRM_NAME_SUCCESS=1;
	public static final Integer CONFIRM_NAME_ERROR=2;
	
	public static final Integer DEVICE_STATUS_OFFLINE=0;
	public static final Integer DEVICE_STATUS_ONLINE=1;
	public static final Integer DEVICE_STATUS_BEFORE_END=2;
	public static final Integer DEVICE_STATUS_AFTER_END=3;
	public static final Integer DEVICE_STATUS_PACKAGE_END=4;
	
	public static final Integer DEVICE_STATUS_SCORE=5;
	public static final Integer DEVICE_STATUS_DIAL_END=6;
	
	private Integer processId;
	private Integer testId;
	
	private String testName;
	private Integer humanId;
	
	private Integer userId;
	
	private String userName;
	
	private Integer userSex;
	
	private String userAge;
	
	private String userEdu;
	
	private String userJob;
	
	private String income;
	
	private String address;
	
	private String yuliu1;
	
	private String yuliu2;
	
	private String yuliu3;
	
	private String yuliu4;
	
	private String yuliu5;
	
	private Integer seatNo;
	
	private Integer hardId;
	
	private String mac;
	
	private String userGroup;
	
	private Integer deviceStatus;
	
	private Integer threshold;
	
	public TestUser(){}
	
	public TestUser(Integer testId,Integer seatNo){
		this.testId=testId;
		this.seatNo=seatNo;
	}
	
	public TestUser(Integer testId, Integer humanId, Integer hardId, String mac){
		this.testId=testId;
		this.humanId=humanId;
		this.hardId=hardId;
		this.mac=mac;
	}


	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getYuliu1() {
		return yuliu1;
	}

	public void setYuliu1(String yuliu1) {
		this.yuliu1 = yuliu1;
	}

	public String getYuliu2() {
		return yuliu2;
	}

	public void setYuliu2(String yuliu2) {
		this.yuliu2 = yuliu2;
	}

	public String getYuliu3() {
		return yuliu3;
	}

	public void setYuliu3(String yuliu3) {
		this.yuliu3 = yuliu3;
	}

	public String getYuliu4() {
		return yuliu4;
	}

	public void setYuliu4(String yuliu4) {
		this.yuliu4 = yuliu4;
	}

	public String getYuliu5() {
		return yuliu5;
	}

	public void setYuliu5(String yuliu5) {
		this.yuliu5 = yuliu5;
	}


	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	
	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Integer getHumanId() {
		return humanId;
	}

	public void setHumanId(Integer humanId) {
		this.humanId = humanId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserSex() {
		return userSex;
	}

	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserEdu() {
		return userEdu;
	}

	public void setUserEdu(String userEdu) {
		this.userEdu = userEdu;
	}

	public String getUserJob() {
		return userJob;
	}

	public void setUserJob(String userJob) {
		this.userJob = userJob;
	}

	public Integer getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(Integer seatNo) {
		this.seatNo = seatNo;
	}

	public Integer getHardId() {
		return hardId;
	}

	public void setHardId(Integer hardId) {
		this.hardId = hardId;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	@Override
	public String toString() {
		return "humanId="+humanId+";user_name="+userName+";user_sex="+userSex+";user_age="+userAge+";user_edu="+userEdu+";user_job="+userJob+";income="+income+";address="+address+";";
	}

}
