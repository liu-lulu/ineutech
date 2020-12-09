package com.kkbc.entity;

import java.util.Date;

/**
 * device_data表
 * @author liululu
 *
 */
public class DeviceData {
	
	public static final Integer NORMAL_STATUS = 1;
	
	public static final Integer TYPE_BRAIN = 3;
	
	private long device_data_id;
	
	private Date create_time;
	
	private int status;
	
	private int attention;
	
	private int delta;
	
	private int high_alpha;
	
	private int high_beta;
	
	private int low_alpha;
	
	private int low_beta;
	
	private int low_gamma;
	
	private int meditation;
	
	private int middle_gamma;
	
	private int min_count;
	
	private String rawdata;
	
	private String remark;
	
	private int signal;
	
	private int theta;
	
	private int type;
	
	private long device_id;
	
	private long user_id;

	public long getDevice_data_id() {
		return device_data_id;
	}

	public void setDevice_data_id(long device_data_id) {
		this.device_data_id = device_data_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAttention() {
		return attention;
	}

	public void setAttention(int attention) {
		this.attention = attention;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

	public int getHigh_alpha() {
		return high_alpha;
	}

	public void setHigh_alpha(int high_alpha) {
		this.high_alpha = high_alpha;
	}

	public int getHigh_beta() {
		return high_beta;
	}

	public void setHigh_beta(int high_beta) {
		this.high_beta = high_beta;
	}

	public int getLow_alpha() {
		return low_alpha;
	}

	public void setLow_alpha(int low_alpha) {
		this.low_alpha = low_alpha;
	}

	public int getLow_beta() {
		return low_beta;
	}

	public void setLow_beta(int low_beta) {
		this.low_beta = low_beta;
	}

	public int getLow_gamma() {
		return low_gamma;
	}

	public void setLow_gamma(int low_gamma) {
		this.low_gamma = low_gamma;
	}

	public int getMeditation() {
		return meditation;
	}

	public void setMeditation(int meditation) {
		this.meditation = meditation;
	}

	public int getMiddle_gamma() {
		return middle_gamma;
	}

	public void setMiddle_gamma(int middle_gamma) {
		this.middle_gamma = middle_gamma;
	}

	public int getMin_count() {
		return min_count;
	}

	public void setMin_count(int min_count) {
		this.min_count = min_count;
	}

	public String getRawdata() {
		return rawdata;
	}

	public void setRawdata(String rawdata) {
		this.rawdata = rawdata;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSignal() {
		return signal;
	}

	public void setSignal(int signal) {
		this.signal = signal;
	}

	public int getTheta() {
		return theta;
	}

	public void setTheta(int theta) {
		this.theta = theta;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getDevice_id() {
		return device_id;
	}

	public void setDevice_id(long device_id) {
		this.device_id = device_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "上送数据>>>deviceId:"+device_id+";attention:"+attention+";delta:"+delta+";high_alpha:"+high_alpha+";high_beta:"+high_beta+";low_alpha:"+low_alpha+";low_beta:"+low_beta+";low_gamma:"+low_gamma+";meditation:"+meditation+";middle_gamma:"+middle_gamma+";signal:"+signal+";theta:"+theta;
	}
	
	

}
