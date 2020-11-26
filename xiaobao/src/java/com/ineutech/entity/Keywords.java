package com.ineutech.entity;

public class Keywords {
	private Integer keyword_id;
	private String keyword;
	
	public Keywords(){}
	public Keywords(String keyword){
		this.keyword=keyword;
	}
	public Integer getKeyword_id() {
		return keyword_id;
	}
	public void setKeyword_id(Integer keyword_id) {
		this.keyword_id = keyword_id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
}
