package com.psylife.vo.dtu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import com.psylife.entity.DeviceFloat;


/**
* float包数据
**/
public class DeviceFloatByDtuVO 
{	
	private Date create_time=new Date();
	private Date caiji_time=new Date();
	private Integer dtu_tpms_status;
	private Integer tpms_pinlu;
	private Float dimian_sulu;
	private Float dimian_hangxiang;
	private Float gao_wen_bao_jing_set;
	private List<TyreFloatYaliSetVO> yali_set=new ArrayList<TyreFloatYaliSetVO>();
	private List<TyreFloatWenduYaliVO> wendu_yali_value=new ArrayList<TyreFloatWenduYaliVO>();
	
	@SuppressWarnings("unchecked")
	public void toValue(DeviceFloat deviceFloat){
		if(deviceFloat==null){
			return;
		}
		List<TyreFloatYaliSetVO> yali_set2=null;
		List<TyreFloatWenduYaliVO> wendu_yali_value2=null;
		try {
			this.dtu_tpms_status=deviceFloat.getDtu_tpms_status();
			this.tpms_pinlu=deviceFloat.getTpms_pinlu();
			this.dimian_sulu=deviceFloat.getDimian_sulu();
			this.dimian_hangxiang=deviceFloat.getDimian_hangxiang();
			this.gao_wen_bao_jing_set=deviceFloat.getGao_wen_bao_jing_set();
			this.create_time=deviceFloat.getCreate_time();
			this.caiji_time=deviceFloat.getCaiji_time();
			JSONArray jsonArray=JSONArray.fromObject(deviceFloat.getYali_set());
			yali_set2=(List<TyreFloatYaliSetVO>)JSONArray.toCollection(jsonArray, TyreFloatYaliSetVO.class);
			jsonArray=JSONArray.fromObject(deviceFloat.getWendu_yali_value());
			wendu_yali_value2=(List<TyreFloatWenduYaliVO>)JSONArray.toCollection(jsonArray, TyreFloatWenduYaliVO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(wendu_yali_value2!=null){
			this.wendu_yali_value=wendu_yali_value2;
		}
		if(yali_set2!=null){
			this.yali_set=yali_set2;
		}
	}
	
	public boolean equalsValue(DeviceFloatByDtuVO obj){
		if(obj==null){
			return false;
		}
		if(this.dtu_tpms_status==null||!this.dtu_tpms_status.equals(obj.getDtu_tpms_status())){
			return false;
		}
		if(this.tpms_pinlu==null||!this.tpms_pinlu.equals(obj.getTpms_pinlu())){
			return false;
		}
//		if(this.dimian_sulu==null||obj.getDimian_sulu()==null||
//				Math.abs(this.dimian_sulu.floatValue()-obj.getDimian_sulu().floatValue())>0.0999){
//			return false;
//		}
//		if(this.dimian_hangxiang==null||obj.getDimian_hangxiang()==null||
//				Math.abs(this.dimian_hangxiang.floatValue()-obj.getDimian_hangxiang().floatValue())>0.0999){
//			return false;
//		}
		if(this.gao_wen_bao_jing_set==null||obj.getGao_wen_bao_jing_set()==null||
				Math.abs(this.gao_wen_bao_jing_set.floatValue()-obj.getGao_wen_bao_jing_set().floatValue())>0.0999){
			return false;
		}
		if(this.yali_set.size()!=obj.getYali_set().size()){
			return false;
		}
		int i;
		for(i=0;i<this.yali_set.size();i++){
			if(!this.yali_set.get(i).equalsValue(obj.getYali_set().get(i))){
				return false;
			}
		}
		
		if(this.wendu_yali_value.size()!=obj.getWendu_yali_value().size()){
			return false;
		}
		for(i=0;i<this.wendu_yali_value.size();i++){
			if(!this.wendu_yali_value.get(i).equalsValue(obj.getWendu_yali_value().get(i))){
				return false;
			}
		}
		
		return true;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getCaiji_time() {
		return caiji_time;
	}

	public void setCaiji_time(Date caiji_time) {
		this.caiji_time = caiji_time;
	}

	public Integer getDtu_tpms_status() {
		return dtu_tpms_status;
	}

	public void setDtu_tpms_status(Integer dtu_tpms_status) {
		this.dtu_tpms_status = dtu_tpms_status;
	}

	public Integer getTpms_pinlu() {
		return tpms_pinlu;
	}

	public void setTpms_pinlu(Integer tpms_pinlu) {
		this.tpms_pinlu = tpms_pinlu;
	}

	public Float getDimian_sulu() {
		return dimian_sulu;
	}

	public void setDimian_sulu(Float dimian_sulu) {
		this.dimian_sulu = dimian_sulu;
	}

	public Float getDimian_hangxiang() {
		return dimian_hangxiang;
	}

	public void setDimian_hangxiang(Float dimian_hangxiang) {
		this.dimian_hangxiang = dimian_hangxiang;
	}

	public Float getGao_wen_bao_jing_set() {
		return gao_wen_bao_jing_set;
	}

	public void setGao_wen_bao_jing_set(Float gao_wen_bao_jing_set) {
		this.gao_wen_bao_jing_set = gao_wen_bao_jing_set;
	}

	public List<TyreFloatYaliSetVO> getYali_set() {
		return yali_set;
	}

	public void setYali_set(List<TyreFloatYaliSetVO> yali_set) {
		this.yali_set = yali_set;
	}

	public List<TyreFloatWenduYaliVO> getWendu_yali_value() {
		return wendu_yali_value;
	}

	public void setWendu_yali_value(List<TyreFloatWenduYaliVO> wendu_yali_value) {
		this.wendu_yali_value = wendu_yali_value;
	}
	
	
}
