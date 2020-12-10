package com.psylife.vo.dtu;

public class TyreFloatYaliSetVO {

	/**
	 * 轴号
	 */
	private Integer zhouno;
	
	/**
	 * 高压设置,现按小数点后1位处理
	 */
	private Float h;
	
	/**
	 * 低压设置,现按小数点后1位处理
	 */
	private Float l;
	
	public boolean equalsValue(TyreFloatYaliSetVO obj){
		if(obj==null){
			return false;
		}
		if(this.h==null||obj.getH()==null||
				Math.abs(this.h.floatValue()-obj.getH().floatValue())>0.0999){
			return false;
		}
		if(this.l==null||obj.getL()==null||
				Math.abs(this.l.floatValue()-obj.getL().floatValue())>0.0999){
			return false;
		}
		return true;
	}

	public Integer getZhouno() {
		return zhouno;
	}

	public void setZhouno(Integer zhouno) {
		this.zhouno = zhouno;
	}

	public Float getH() {
		return h;
	}

	public void setH(Float h) {
		this.h = h;
	}

	public Float getL() {
		return l;
	}

	public void setL(Float l) {
		this.l = l;
	}
	
}
