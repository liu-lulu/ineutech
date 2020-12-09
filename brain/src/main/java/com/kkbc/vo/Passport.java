package com.kkbc.vo;


public class Passport {
	  public static final String PASSPORTNAME = "kkbcPassport";
	  private String loginName;
	  private Long userId;
	  private Long schoolId;
	  private Long classId;
	  private String userName;
	  private String userSchoolDepart;
	  private Integer userSgp;

	  public String getLoginName()
	  {
	    return this.loginName;
	  }

	  public Long getUserId() {
	    return this.userId;
	  }

	  public Long getSchoolId() {
	    return this.schoolId;
	  }

	  public Long getClassId() {
	    return this.classId;
	  }

	  public String getUserName() {
	    return this.userName;
	  }

	  public String getUserSchoolDepart() {
	    return this.userSchoolDepart;
	  }

	  public Integer getUserSgp() {
	    return this.userSgp;
	  }

	  public void setLoginName(String loginName) {
	    this.loginName = loginName;
	  }

	  public void setUserId(Long userId) {
	    this.userId = userId;
	  }

	  public void setSchoolId(Long schoolId) {
	    this.schoolId = schoolId;
	  }

	  public void setClassId(Long classId) {
	    this.classId = classId;
	  }

	  public void setUserName(String userName) {
	    this.userName = userName;
	  }

	  public void setUserSchoolDepart(String userSchoolDepart) {
	    this.userSchoolDepart = userSchoolDepart;
	  }

	  public void setUserSgp(Integer userSgp) {
	    this.userSgp = userSgp;
	  }
	  }
