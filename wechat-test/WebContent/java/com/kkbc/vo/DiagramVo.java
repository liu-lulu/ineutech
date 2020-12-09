package com.kkbc.vo;

import java.util.Date;

/**
 * 关系图vo
 * @author liululu
 *
 */
public class DiagramVo {
	
	private String user_name;
	private int level;
	private int sumA;
	private int sumB;
	private int jieyuA;
	private int jieyuB;
	private Date create_time;
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getSumA() {
		return sumA;
	}
	public void setSumA(int sumA) {
		this.sumA = sumA;
	}
	public int getSumB() {
		return sumB;
	}
	public void setSumB(int sumB) {
		this.sumB = sumB;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public int getJieyuA() {
		return jieyuA;
	}
	public void setJieyuA(int jieyuA) {
		this.jieyuA = jieyuA;
	}
	public int getJieyuB() {
		return jieyuB;
	}
	public void setJieyuB(int jieyuB) {
		this.jieyuB = jieyuB;
	}

}
