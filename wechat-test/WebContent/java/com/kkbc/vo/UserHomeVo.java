package com.kkbc.vo;




/**
 * 用户表
 * @author xu
 *
 */
public class UserHomeVo 
{	
	
	private int level;//会员级别
	
	private int recommandNum;//推荐个数
	private int sumA;
	private int sumB;
	private int jieyuA;
	private int jieyuB;
	
	private int amountToday;//今日已量碰
	
	private int pointSum;//累计见点奖
	private int amountSum;//累计量碰奖
	private int layerSum;//累计层碰奖
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getRecommandNum() {
		return recommandNum;
	}
	public void setRecommandNum(int recommandNum) {
		this.recommandNum = recommandNum;
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
	public int getAmountToday() {
		return amountToday;
	}
	public void setAmountToday(int amountToday) {
		this.amountToday = amountToday;
	}
	public int getPointSum() {
		return pointSum;
	}
	public void setPointSum(int pointSum) {
		this.pointSum = pointSum;
	}
	public int getAmountSum() {
		return amountSum;
	}
	public void setAmountSum(int amountSum) {
		this.amountSum = amountSum;
	}
	public int getLayerSum() {
		return layerSum;
	}
	public void setLayerSum(int layerSum) {
		this.layerSum = layerSum;
	}
}
