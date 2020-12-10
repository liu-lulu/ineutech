package com.psylife.vo.dtu;

public class TyreFloatWenduYaliVO {

	/**
	 * 轴号
	 */
	private Integer no;
	
	/**
	 * 压力,现按小数点后1位处理
	 */
	private Float yali;
	
	/**
	 * 温度,现按4度相差精度处理
	 */
	private Float wendu;
	
	public boolean equalsValue(TyreFloatWenduYaliVO obj){
		if(obj==null){
			return false;
		}
		if(this.yali==null||obj.getYali()==null||
				Math.abs(this.yali.floatValue()-obj.getYali().floatValue())>0.0999){
			return false;
		}
		if(this.wendu==null||obj.getWendu()==null||
				Math.abs(this.wendu.floatValue()-obj.getWendu().floatValue())>3.9999){
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

	public Float getYali() {
		return yali;
	}

	public void setYali(Float yali) {
		this.yali = yali;
	}

	public Float getWendu() {
		return wendu;
	}

	public void setWendu(Float wendu) {
		this.wendu = wendu;
	}

}
