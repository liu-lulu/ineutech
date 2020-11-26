package com.kkbc.entity;


public class InviteLog {
	private Integer login_id;
	private Integer invitor_id;
	private String type;
	private String org_content;
	private String new_content;
	
	public InviteLog(){}
	public InviteLog(Integer login_id,Integer invitor_id, String type, String org_content,
			String new_content) {
		super();
		this.login_id=login_id;
		this.invitor_id = invitor_id;
		this.type = type;
		this.org_content = org_content;
		this.new_content = new_content;
	}
	
	public Integer getLogin_id() {
		return login_id;
	}
	public void setLogin_id(Integer login_id) {
		this.login_id = login_id;
	}
	public Integer getInvitor_id() {
		return invitor_id;
	}
	public void setInvitor_id(Integer invitor_id) {
		this.invitor_id = invitor_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrg_content() {
		return org_content;
	}
	public void setOrg_content(String org_content) {
		this.org_content = org_content;
	}
	public String getNew_content() {
		return new_content;
	}
	public void setNew_content(String new_content) {
		this.new_content = new_content;
	}

}
