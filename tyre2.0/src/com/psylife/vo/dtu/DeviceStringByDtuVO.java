package com.psylife.vo.dtu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import com.psylife.entity.DeviceString;

/**
* 字符串包数据
**/
public class DeviceStringByDtuVO 
{
	private Date create_time=new Date();
	private Date caiji_time=new Date();
	private Integer dtu_tpms_status;
	private String yuliu1_phone;
	private String yuliu2_phone;
	private String yuliu3_phone;
	private String yuliu4_phone;
	private String dtu_no;
	private String dtu_trucks_id;
	private String sim;
	private Double latitude;
	private Integer latitude_type;
	private Double longitude;
	private Integer longitude_type;
	private List<TyreStringVO> fasheqi_ids_value=new ArrayList<TyreStringVO>();
	
	@SuppressWarnings("unchecked")
	public void toValue(DeviceString deviceString){
		if(deviceString==null){
			return;
		}
		List<TyreStringVO> fasheqi_ids_value2=null;
		try {
			this.dtu_tpms_status=deviceString.getDtu_tpms_status();
			this.yuliu1_phone=deviceString.getYuliu1_phone();
			this.yuliu2_phone=deviceString.getYuliu2_phone();
			this.yuliu3_phone=deviceString.getYuliu3_phone();
			this.yuliu4_phone=deviceString.getYuliu4_phone();
			this.dtu_no=deviceString.getDtu_no();
			this.dtu_trucks_id=deviceString.getDtu_trucks_id();
			this.sim=deviceString.getSim();
			this.latitude=deviceString.getLatitude();
			this.latitude_type=deviceString.getLatitude_type();
			this.longitude=deviceString.getLongitude();
			this.longitude_type=deviceString.getLongitude_type();
			this.create_time=deviceString.getCreate_time();
			this.caiji_time=deviceString.getCaiji_time();
			JSONArray jsonArray=JSONArray.fromObject(deviceString.getFasheqi_ids_value());
			fasheqi_ids_value2=(List<TyreStringVO>)JSONArray.toCollection(jsonArray, TyreStringVO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(fasheqi_ids_value2!=null){
			this.fasheqi_ids_value=fasheqi_ids_value2;
		}
	}
	
	public boolean equalsValue(DeviceStringByDtuVO obj){
		if(obj==null){
			return false;
		}
		if(this.dtu_tpms_status==null||!this.dtu_tpms_status.equals(obj.getDtu_tpms_status())){
			return false;
		}
		if(this.dtu_trucks_id==null||!this.dtu_trucks_id.equals(obj.getDtu_trucks_id())){
			return false;
		}
		if(this.latitude_type==null||!this.latitude_type.equals(obj.getLatitude_type())){
			return false;
		}
		if(this.longitude_type==null||!this.longitude_type.equals(obj.getLongitude_type())){
			return false;
		}
		if(this.latitude==null||obj.getLatitude()==null||
				Math.abs(this.latitude.doubleValue()-obj.getLatitude().doubleValue())>0.000039999d){
			return false;
		}
		if(this.longitude==null||obj.getLongitude()==null||
				Math.abs(this.longitude.doubleValue()-obj.getLongitude().doubleValue())>0.000039999d){
			return false;
		}
		if(this.fasheqi_ids_value.size()!=obj.getFasheqi_ids_value().size()){
			return false;
		}
		for(int i=0;i<this.fasheqi_ids_value.size();i++){
			if(!this.fasheqi_ids_value.get(i).equalsValue(obj.getFasheqi_ids_value().get(i))){
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
	public String getYuliu1_phone() {
		return yuliu1_phone;
	}
	public void setYuliu1_phone(String yuliu1_phone) {
		this.yuliu1_phone = yuliu1_phone;
	}
	public String getYuliu2_phone() {
		return yuliu2_phone;
	}
	public void setYuliu2_phone(String yuliu2_phone) {
		this.yuliu2_phone = yuliu2_phone;
	}
	public String getYuliu3_phone() {
		return yuliu3_phone;
	}
	public void setYuliu3_phone(String yuliu3_phone) {
		this.yuliu3_phone = yuliu3_phone;
	}
	public String getYuliu4_phone() {
		return yuliu4_phone;
	}
	public void setYuliu4_phone(String yuliu4_phone) {
		this.yuliu4_phone = yuliu4_phone;
	}
	public String getDtu_no() {
		return dtu_no;
	}
	public void setDtu_no(String dtu_no) {
		this.dtu_no = dtu_no;
	}
	public String getDtu_trucks_id() {
		return dtu_trucks_id;
	}
	public void setDtu_trucks_id(String dtu_trucks_id) {
		this.dtu_trucks_id = dtu_trucks_id;
	}
	public String getSim() {
		return sim;
	}
	public void setSim(String sim) {
		this.sim = sim;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Integer getLatitude_type() {
		return latitude_type;
	}
	public void setLatitude_type(Integer latitude_type) {
		this.latitude_type = latitude_type;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Integer getLongitude_type() {
		return longitude_type;
	}
	public void setLongitude_type(Integer longitude_type) {
		this.longitude_type = longitude_type;
	}
	public List<TyreStringVO> getFasheqi_ids_value() {
		return fasheqi_ids_value;
	}
	public void setFasheqi_ids_value(List<TyreStringVO> fasheqi_ids_value) {
		this.fasheqi_ids_value = fasheqi_ids_value;
	}
	

}
