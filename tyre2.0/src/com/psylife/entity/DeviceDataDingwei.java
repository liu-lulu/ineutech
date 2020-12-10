package com.psylife.entity;

import java.util.Date;

/**
 * dtu设备数据定位信息表
 * @author xu
 *
 */
public class DeviceDataDingwei 
{	
	/**
	 * 表名
	 */
	public static final String TB_N="device_data_dingwei";
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * DTU ID
	 */
	private String dtu_id;
	
	/**
	 * 创建时间
	 */
	private Date create_time;
	
	/**
	 * GPS的定位状态（0表示有效定位，1表示无效定位）
	 */
	private Integer gps_status;
	
	/**
	 * 纬度
	 */
	private  Double latitude;
	
	/**
	 * 北纬或南纬(0表示北纬,1表示南纬)
	 */
	private Integer latitude_type;
	
	/**
	 * 经度
	 */
	private Double longitude;
	
	/**
	 * 东经或西经(0表示东经,1表示西经)
	 */
	private Integer longitude_type;
	
	/**
	 * 地面速率（单位：节）
	 */
	private Float dimian_sulu;
	
	/**
	 * 地面航向（单位：度）
	 */
	private Float dimian_hangxiang;
	
	/**
	 * 车牌号
	 */
	private String trucks_id;
	
	public boolean equalsValue(DeviceDataDingwei obj){
		if(obj==null){
			return false;
		}
		if(this.gps_status==null||!this.gps_status.equals(obj.getGps_status())){
			return false;
		}
		//经纬度
		if(this.latitude_type==null||!this.latitude_type.equals(obj.getLatitude_type())){
			return false;
		}
		if(this.longitude_type==null||!this.longitude_type.equals(obj.getLongitude_type())){
			return false;
		}
		if(this.latitude==null||obj.getLatitude()==null||
				Math.abs(this.latitude.doubleValue()-obj.getLatitude().doubleValue())>0.000059999d){//0.000039999d
			return false;
		}
		if(this.longitude==null||obj.getLongitude()==null||
				Math.abs(this.longitude.doubleValue()-obj.getLongitude().doubleValue())>0.000059999d){//0.000039999d
			return false;
		}
		
		if(this.dimian_sulu==null||obj.getDimian_sulu()==null||Math.abs(this.dimian_sulu.floatValue()-obj.getDimian_sulu().floatValue())>0.1999){//0.0999
			return false;
		}
		if(this.dimian_hangxiang==null||obj.getDimian_hangxiang()==null||Math.abs(this.dimian_hangxiang.floatValue()-obj.getDimian_hangxiang().floatValue())>0.1999){//0.0999
			return false;
		}	
		
		if((this.trucks_id==null&&obj.getTrucks_id()!=null)||(this.trucks_id!=null&&!this.trucks_id.equals(obj.getTrucks_id()))){
			return false;
		}
		
		if(this.dtu_id==null||!this.dtu_id.equals(obj.getDtu_id())){
			return false;
		}
		
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDtu_id() {
		return dtu_id;
	}

	public void setDtu_id(String dtu_id) {
		this.dtu_id = dtu_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getGps_status() {
		return gps_status;
	}

	public void setGps_status(Integer gps_status) {
		this.gps_status = gps_status;
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

	public String getTrucks_id() {
		return trucks_id;
	}

	public void setTrucks_id(String trucks_id) {
		this.trucks_id = trucks_id;
	}

}
