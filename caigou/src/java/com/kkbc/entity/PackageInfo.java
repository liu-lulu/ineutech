package com.kkbc.entity;

import java.util.Date;

public class PackageInfo {
	public static int STATUS_WEIFA=0;//未发
	public static int STATUS_YIFA=1;//已发
	public static int STATUS_YIQIANSHOU=2;//已签收
	
	private Integer package_id;
	private Integer supplier_id;
	private String package_name;
	private Date create_time;
	private Date send_time;
	private Date sign_time;
	private String expressNo;
	private Integer status;
	private String comment;
	public Date getSend_time() {
		return send_time;
	}
	public void setSend_time(Date send_time) {
		this.send_time = send_time;
	}
	public Date getSign_time() {
		return sign_time;
	}
	public void setSign_time(Date sign_time) {
		this.sign_time = sign_time;
	}
	public Integer getPackage_id() {
		return package_id;
	}
	public void setPackage_id(Integer package_id) {
		this.package_id = package_id;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public String getPackage_name() {
		return package_name;
	}
	public void setPackage_name(String package_name) {
		this.package_name = package_name;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
