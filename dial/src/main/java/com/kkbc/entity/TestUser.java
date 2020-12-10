package com.kkbc.entity;


public class TestUser {
	
	public static final int CONFIRM_NAME_SUCCESS=1;
	public static final int CONFIRM_NAME_ERROR=2;
	
	public static final int DEVICE_STATUS_OFFLINE=0;
	public static final int DEVICE_STATUS_ONLINE=1;
	public static final int DEVICE_STATUS_TEST=2;
	public static final int DEVICE_STATUS_PAUSE=3;
	public static final int DEVICE_STATUS_ENDTEST=4;
	
	private int process_id;
	private int test_id;
	
	private String test_name;
	private int HUMAN_id;
	
	private int user_id;
	
	private String user_name;
	
	private int user_sex;
	
	private String user_age;
	
	private String user_edu;
	
	private String user_job;
	
	private String income;
	
	private String address;
	
	private String yuliu1;
	
	private String yuliu2;
	
	private String yuliu3;
	
	private String yuliu4;
	
	private String yuliu5;
	
	private Integer seat_no;
	
	private int hard_id;
	
	private String mac;
	
	private String user_group;
	
	private int confirm_name;
	
	private int device_status;

	public int getProcess_id() {
		return process_id;
	}

	public void setProcess_id(int process_id) {
		this.process_id = process_id;
	}

	public int getHUMAN_id() {
		return HUMAN_id;
	}

	public void setHUMAN_id(int hUMAN_id) {
		HUMAN_id = hUMAN_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(int user_sex) {
		this.user_sex = user_sex;
	}

	public String getUser_age() {
		return user_age;
	}

	public void setUser_age(String user_age) {
		this.user_age = user_age;
	}

	public String getUser_edu() {
		return user_edu;
	}

	public void setUser_edu(String user_edu) {
		this.user_edu = user_edu;
	}

	public String getUser_job() {
		return user_job;
	}

	public void setUser_job(String user_job) {
		this.user_job = user_job;
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

	public int getTest_id() {
		return test_id;
	}

	public void setTest_id(int test_id) {
		this.test_id = test_id;
	}

	public Integer getSeat_no() {
		return seat_no;
	}

	public void setSeat_no(Integer seat_no) {
		this.seat_no = seat_no;
	}

	public int getHard_id() {
		return hard_id;
	}

	public void setHard_id(int hard_id) {
		this.hard_id = hard_id;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getUser_group() {
		return user_group;
	}

	public void setUser_group(String user_group) {
		this.user_group = user_group;
	}

	public int getConfirm_name() {
		return confirm_name;
	}

	public void setConfirm_name(int confirm_name) {
		this.confirm_name = confirm_name;
	}

	public int getDevice_status() {
		return device_status;
	}

	public void setDevice_status(int device_status) {
		this.device_status = device_status;
	}

	public String getTest_name() {
		return test_name;
	}

	public void setTest_name(String test_name) {
		this.test_name = test_name;
	}
	

}
