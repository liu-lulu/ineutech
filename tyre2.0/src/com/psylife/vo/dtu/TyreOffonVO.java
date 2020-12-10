package com.psylife.vo.dtu;

public class TyreOffonVO {

	/**
	 * 轮胎号
	 */
	private Integer no;
	
	/**
	 * 轮胎急漏气告警（0表示无告警，1表示有告警）
	 */
	private Integer louqi;
	
	/**
	 * 轮胎高压告警（0表示无告警，1表示有告警）
	 */
	private Integer gaoya;
	
	/**
	 * 轮胎低压告警（0表示无告警，1表示有告警）
	 */
	private Integer diya;
	
	/**
	 * 轮胎高温告警（0表示无告警，1表示有告警）
	 */
	private Integer gaowen;
	
	/**
	 * 设备电池低电（0表示无告警，1表示有告警）
	 */
	private Integer dianchi;
	
	/**
	 * 轮胎发射器低电池告警（0表示无告警，1表示有告警）
	 */
	private Integer fasheqidianchi;
	
	/**
	 * 轮胎发射器发射中断告警（0表示无告警，1表示有告警）
	 */
	private Integer fasheqizhongduan;
	
	public boolean equalsValue(TyreOffonVO obj){
		if(obj==null){
			return false;
		}
		if(this.louqi==null||!this.louqi.equals(obj.getLouqi())){
			return false;
		}
		if(this.gaoya==null||!this.gaoya.equals(obj.getGaoya())){
			return false;
		}
		if(this.diya==null||!this.diya.equals(obj.getDiya())){
			return false;
		}
		if(this.gaowen==null||!this.gaowen.equals(obj.getGaowen())){
			return false;
		}
		if(this.dianchi==null||!this.dianchi.equals(obj.getDianchi())){
			return false;
		}
		if(this.fasheqidianchi==null||!this.fasheqidianchi.equals(obj.getFasheqidianchi())){
			return false;
		}
		if(this.fasheqizhongduan==null||!this.fasheqizhongduan.equals(obj.getFasheqizhongduan())){
			return false;
		}
		return true;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Integer getLouqi() {
		return louqi;
	}

	public void setLouqi(Integer louqi) {
		this.louqi = louqi;
	}

	public Integer getGaoya() {
		return gaoya;
	}

	public void setGaoya(Integer gaoya) {
		this.gaoya = gaoya;
	}

	public Integer getDiya() {
		return diya;
	}

	public void setDiya(Integer diya) {
		this.diya = diya;
	}

	public Integer getGaowen() {
		return gaowen;
	}

	public void setGaowen(Integer gaowen) {
		this.gaowen = gaowen;
	}

	public Integer getDianchi() {
		return dianchi;
	}

	public void setDianchi(Integer dianchi) {
		this.dianchi = dianchi;
	}

	public Integer getFasheqidianchi() {
		return fasheqidianchi;
	}

	public void setFasheqidianchi(Integer fasheqidianchi) {
		this.fasheqidianchi = fasheqidianchi;
	}

	public Integer getFasheqizhongduan() {
		return fasheqizhongduan;
	}

	public void setFasheqizhongduan(Integer fasheqizhongduan) {
		this.fasheqizhongduan = fasheqizhongduan;
	}
	
	
}
