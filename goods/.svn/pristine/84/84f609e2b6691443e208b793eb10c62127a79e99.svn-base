package com.kkbc.vo;

import java.util.Date;

public class UserHeartVO {
	
	public static String TABLE_PRE="user_heart_";
	
	public static String TABLE_TWODAY="_twoday";
	public static String TABLE_FIVEDAY="_fiveday";
	public static String TABLE_OTHER="_other";
	
	public static int ATTENTION_YES=1;
	public static int ATTENTION_NO=0;
	
	public static int CONFIRM_NO=1;
	public static int CONFIRM_YES=2;
	
	private String openId;
	private int desc_id;
	private Date create_time;
	private Integer attention;
	private Integer confirm;
	private String tableName;
	
	private String heartTableName;
	
	public UserHeartVO(){}
	public UserHeartVO(int desc_id,String openId,Date date,String tableName,String heartTableName,Integer attention){
		this.desc_id=desc_id;
		this.openId=openId;
		this.create_time=date;
		this.tableName=tableName;
		this.heartTableName=heartTableName;
		this.attention=attention;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public int getDesc_id() {
		return desc_id;
	}
	public void setDesc_id(int desc_id) {
		this.desc_id = desc_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getAttention() {
		return attention;
	}
	public void setAttention(Integer attention) {
		this.attention = attention;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getHeartTableName() {
		return heartTableName;
	}
	public void setHeartTableName(String heartTableName) {
		this.heartTableName = heartTableName;
	}
	public Integer getConfirm() {
		return confirm;
	}
	public void setConfirm(Integer confirm) {
		this.confirm = confirm;
	}
	

}
