package com.kkbc.entity;

public class Bus {
	private String name;
	private String phone;
	private String start_pos;
	private String start_time;
	private String end_pos;
	
	public Bus(String name, String phone, String start_pos, String start_time, String end_pos) {
		super();
		this.name = name;
		this.phone = phone;
		this.start_pos = start_pos;
		this.start_time = start_time;
		this.end_pos = end_pos;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStart_pos() {
		return start_pos;
	}
	public void setStart_pos(String start_pos) {
		this.start_pos = start_pos;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_pos() {
		return end_pos;
	}
	public void setEnd_pos(String end_pos) {
		this.end_pos = end_pos;
	}
	
	

}
