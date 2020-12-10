package com.psylife.util.push;

import java.util.Date;

public class Message {
	private Integer id;

	private String content;

	private Integer senduserId;

	private Integer userId;

	private Integer type;

	private Date createTime;

	private String remark;

	private String title;
	
	private String company;

	public Message() {
	}

	public Message(Integer id, String content, Integer senduserId, Integer userId, Integer type, Date createTime,
			String remark, String title,String company) {
		super();
		this.id = id;
		this.content = content;
		this.senduserId = senduserId;
		this.userId = userId;
		this.type = type;
		this.createTime = createTime;
		this.remark = remark;
		this.title = title;
		this.company=company;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public Integer getSenduserId() {
		return senduserId;
	}

	public void setSenduserId(Integer senduserId) {
		this.senduserId = senduserId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	
}