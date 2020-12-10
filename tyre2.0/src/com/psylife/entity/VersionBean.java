package com.psylife.entity;

import java.io.Serializable;

public class VersionBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1522509429652341686L;	  
	private String school;
	private String type;
	private String url;
	private String version;
	private String androidUrl;
	public String getAndroidUrl() {
		return androidUrl;
	}
	public void setAndroidUrl(String androidUrl) {
		this.androidUrl = androidUrl;
	}
	private String iosUrl;
	private String isMust;
	private String versionText;
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getIsMust() {
		return isMust;
	}
	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}
	public String getVersionText() {
		return versionText;
	}
	public void setVersionText(String versionText) {
		this.versionText = versionText;
	}
	public String getIosUrl() {
		return iosUrl;
	}
	public void setIosUrl(String iosUrl) {
		this.iosUrl = iosUrl;
	}

}
