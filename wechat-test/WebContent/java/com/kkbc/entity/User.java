package com.kkbc.entity;

import java.util.Date;



/**
 * 用户表
 * @author xu
 *
 */
public class User 
{	
	
	/**
	 * 性别代表先生
	 */
	public static final Integer SEX_MAN = 0;

	/**
	 * 性别代表女士
	 */
	public static final Integer SEX_WOMAN = 1;
	
	/**
	 * 角色,管理员
	 */
	public static final Integer USER_ROLE_ADMIN = 1;
	
	/**
	 * 角色,修理工
	 */
	public static final Integer USER_ROLE_REPAIRMAN = 2;
	
	/**
	 * 角色,司机
	 */
	public static final Integer USER_ROLE_DRIVER = 3;
	
	private Integer user_id;//主键
	private String user_name;//登录名
	private String user_password;//密码
	private String true_name;//真实名
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
	
	private String user_company;//公司
	
	
	private Integer user_role;//角色
	private Integer sex;//姓别

	
	
	private String user_face;//用户头像
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_company() {
		return user_company;
	}
	public void setUser_company(String user_company) {
		this.user_company = user_company;
	}
	public int getUser_company_id() {
		return user_company_id;
	}
	public void setUser_company_id(int user_company_id) {
		this.user_company_id = user_company_id;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public Integer getUser_role() {
		return user_role;
	}
	public void setUser_role(Integer user_role) {
		this.user_role = user_role;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getTrue_name() {
		return true_name;
	}
	public void setTrue_name(String true_name) {
		this.true_name = true_name;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUser_face() {
		return user_face;
	}
	public void setUser_face(String user_face) {
		this.user_face = user_face;
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
	public String getIDCardNo() {
		return IDCardNo;
	}
	public void setIDCardNo(String iDCardNo) {
		IDCardNo = iDCardNo;
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
