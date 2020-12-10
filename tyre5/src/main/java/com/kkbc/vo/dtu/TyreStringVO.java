package com.kkbc.vo.dtu;

public class TyreStringVO {

	/**
	 * 轮胎号
	 */
	private Integer no;
	
	/**
	 * 轮胎发射器ID
	 */
	private String fasheqiid;
	
	public boolean equalsValue(TyreStringVO obj){
		if(obj==null){
			return false;
		}
		if(this.fasheqiid==null||!this.fasheqiid.equals(obj.getFasheqiid())){
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

	public String getFasheqiid() {
		return fasheqiid;
	}

	public void setFasheqiid(String fasheqiid) {
		this.fasheqiid = fasheqiid;
	}
	
}
