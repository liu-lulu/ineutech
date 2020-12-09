package com.kkbc.vo;

import java.io.Serializable;
import java.util.Date;

import com.kkbc.entity.User;

public class Passport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6480322646813226627L;

	public static final String PASSPORTNAME = "passport";

    private Integer userId;//用户id
	
	private String loginName;//登录名
	
	private String trueName;//真实姓名
	
	private String headFace;//头像
	
	private String companyName;//车队名
	
	private Integer companyId;//车队id
	
	private Integer userRole;//角色id
	
	private String referral;//推荐人
	private String contactPerson;//接点人
	private String area;//区域
	private int level;//会员级别
	private Integer status;//激活状态
	private Date create_time;//注册时间
	private Date activateTime;//激活时间
	private String pwd2;//2级密码
	private String user_phone;	//联系电话
	private String IDCardNo;//身份证号
	private int user_company_id;//公司Id
	private float awardCoins;//奖金币
	private float shoppingDrill;//购物钻
	private float declarationCoins;//报单币
	
	public Passport(){
		
	}
	
	public Passport(User user){
	    this.userId=user.getUser_id();
	    this.loginName=user.getUser_name();
	    this.trueName=user.getTrue_name();
	    this.companyId=user.getUser_company_id();
	    this.level=user.getLevel();
	    this.create_time=user.getCreate_time();
	    this.awardCoins=user.getAwardCoins();
	    this.shoppingDrill=user.getShoppingDrill();
	    this.declarationCoins=user.getDeclarationCoins();
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

	public String getReferral() {
		return referral;
	}

	public void setReferral(String referral) {
		this.referral = referral;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getActivateTime() {
		return activateTime;
	}

	public void setActivateTime(Date activateTime) {
		this.activateTime = activateTime;
	}

	public String getPwd2() {
		return pwd2;
	}

	public void setPwd2(String pwd2) {
		this.pwd2 = pwd2;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getIDCardNo() {
		return IDCardNo;
	}

	public void setIDCardNo(String iDCardNo) {
		IDCardNo = iDCardNo;
	}

	public int getUser_company_id() {
		return user_company_id;
	}

	public void setUser_company_id(int user_company_id) {
		this.user_company_id = user_company_id;
	}

	public float getAwardCoins() {
		return awardCoins;
	}

	public void setAwardCoins(float awardCoins) {
		this.awardCoins = awardCoins;
	}

	public float getShoppingDrill() {
		return shoppingDrill;
	}

	public void setShoppingDrill(float shoppingDrill) {
		this.shoppingDrill = shoppingDrill;
	}

	public float getDeclarationCoins() {
		return declarationCoins;
	}

	public void setDeclarationCoins(float declarationCoins) {
		this.declarationCoins = declarationCoins;
	}

}
