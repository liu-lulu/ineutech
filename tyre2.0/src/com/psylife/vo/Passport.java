package com.psylife.vo;

import java.io.Serializable;
import com.psylife.entity.User;

public class Passport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6480322646813226627L;

	public static final String PASSPORTNAME = "silaishiPassport";

    private Integer userId;//用户id
	
	private String loginName;//登录名
	
	private String trueName;//真实姓名
	
	private String headFace;//头像
	
	private String companyName;//车队名
	
	private Integer companyId;//车队id
	
	private Integer userRole;//角色id
	
	public Passport(){
		
	}
	
	public Passport(User user){
	    this.userId=user.getUser_id();
	    this.loginName=user.getUser_name();
	    this.trueName=user.getTrue_name();
	    this.companyName=user.getUser_company();
	    this.companyId=user.getUser_company_id();
	    this.userRole=user.getUser_role();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getHeadFace() {
		return headFace;
	}

	public void setHeadFace(String headFace) {
		this.headFace = headFace;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}
	


}
