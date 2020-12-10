package com.kkbc.entity;

/**
 * 品牌
 * @author xu
 *
 */
public class TyreBrand {

	private Integer id;
	
	/**
	 * 名字
	 */
	private String name;
	
	/**
	 * 拼音
	 */
	private String pin_yin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPin_yin() {
		return pin_yin;
	}

	public void setPin_yin(String pin_yin) {
		this.pin_yin = pin_yin;
	}
	
}
