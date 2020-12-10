package com.kkbc.vo.dtu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import com.kkbc.entity.DeviceOffon;

/**
* 开关量包数据
**/
public class DeviceOffonByDtuVO 
{	
	private Date create_time=new Date();
	private Date caiji_time=new Date();
	private Integer dtu_tpms_status;
	private Integer dtu_status;
	private Integer gps_status;
	private List<TyreOffonVO> offon_value=new ArrayList<TyreOffonVO>();
	
	@SuppressWarnings("unchecked")
	public void toValue(DeviceOffon deviceOffon){
		if(deviceOffon==null){
			return;
		}
		List<TyreOffonVO> offon_value2=null;
		try {
			this.dtu_tpms_status=deviceOffon.getDtu_tpms_status();
			this.dtu_status=deviceOffon.getDtu_status();
			this.gps_status=deviceOffon.getGps_status();
			this.create_time=deviceOffon.getCreate_time();
			this.caiji_time=deviceOffon.getCaiji_time();
			JSONArray jsonArray=JSONArray.fromObject(deviceOffon.getOffon_value());
			offon_value2=(List<TyreOffonVO>)JSONArray.toCollection(jsonArray, TyreOffonVO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(offon_value2!=null){
			offon_value=offon_value2;
		}
	}
	
	public boolean equalsValue(DeviceOffonByDtuVO obj) {
		if(obj==null){
			return false;
		}
		if(this.dtu_tpms_status==null||!this.dtu_tpms_status.equals(obj.getDtu_tpms_status())){
			return false;
		}
		if(this.dtu_status==null||!this.dtu_status.equals(obj.getDtu_status())){
			return false;
		}
		if(this.gps_status==null||!this.gps_status.equals(obj.getGps_status())){
			return false;
		}
		if(this.offon_value.size()!=obj.getOffon_value().size()){
			return false;
		}
		for(int i=0;i<this.offon_value.size();i++){
			if(!this.offon_value.get(i).equalsValue(obj.getOffon_value().get(i))){
				return false;
			}
		}
		return  true;
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
	public Integer getDtu_status() {
		return dtu_status;
	}
	public void setDtu_status(Integer dtu_status) {
		this.dtu_status = dtu_status;
	}
	public Integer getGps_status() {
		return gps_status;
	}
	public void setGps_status(Integer gps_status) {
		this.gps_status = gps_status;
	}
	public List<TyreOffonVO> getOffon_value() {
		return offon_value;
	}
	public void setOffon_value(List<TyreOffonVO> offon_value) {
		this.offon_value = offon_value;
	}
}
