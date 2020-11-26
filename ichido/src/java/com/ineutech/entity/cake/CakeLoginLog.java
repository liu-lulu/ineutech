package com.ineutech.entity.cake;

public class CakeLoginLog {
	
	private String e_number;
	private String nickname;
	public CakeLoginLog(){}
	public CakeLoginLog(String e_number, String nickname) {
		super();
		this.e_number = e_number;
		this.nickname = nickname;
	}
	public String getE_number() {
		return e_number;
	}
	public void setE_number(String e_number) {
		this.e_number = e_number;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

}
