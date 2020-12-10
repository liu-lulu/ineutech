package com.kkbc.entity;

import java.util.Date;


/**
 * dtu设备数据压力温度表
 * @author xu
 *
 */
public class DeviceDataYaliWendu 
{	
	/**
	 * 表名
	 */
	public static final String TB_N="device_data_yali_wendu";
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 创建时间
	 */
	private Date create_time;
	
	/**
	 * 发射器ID
	 */
	private String fasheqi_id;
	
	/**
	 * 压力
	 */
	private Float yali;
	
	/**
	 * 温度
	 */
	private Float wendu;
	
	/**
	 * 发射器序号
	 */
	private Integer no;
	
	/**
	 * 胎号
	 */
	private String tyre_id;
	
	/**
	 * UUID
	 */
	private String uuid;
	
	public boolean equalsValue(DeviceDataYaliWendu obj){
		if(obj==null){
			return false;
		}
		if(this.fasheqi_id==null||!this.fasheqi_id.equals(obj.getFasheqi_id())){
			return false;
		}
		if(this.yali==null||obj.getYali()==null||
				Math.abs(this.yali.floatValue()-obj.getYali().floatValue())>0.0999){
			return false;
		}
		if(this.wendu==null||obj.getWendu()==null||
				Math.abs(this.wendu.floatValue()-obj.getWendu().floatValue())>2.9999){
			return false;
		}
		if(this.no==null||!this.no.equals(obj.getNo())){
			return false;
		}
		if((this.tyre_id==null&&obj.getTyre_id()!=null)||(this.tyre_id!=null&&!this.tyre_id.equals(obj.getTyre_id()))){
			return false;
		}
		return true;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getId()
	{
		return this.id;
	}
	public void setCreate_time(Date create_time)
	{
		this.create_time = create_time;
	}
	public Date getCreate_time()
	{
		return this.create_time;
	}
	public void setFasheqi_id(String fasheqi_id)
	{
		this.fasheqi_id = fasheqi_id;
	}
	public String getFasheqi_id()
	{
		return this.fasheqi_id;
	}
	public void setYali(Float yali)
	{
		this.yali = yali;
	}
	public Float getYali()
	{
		return this.yali;
	}
	public void setWendu(Float wendu)
	{
		this.wendu = wendu;
	}
	public Float getWendu()
	{
		return this.wendu;
	}
	public void setNo(Integer no)
	{
		this.no = no;
	}
	public Integer getNo()
	{
		return this.no;
	}
	public void setTyre_id(String tyre_id)
	{
		this.tyre_id = tyre_id;
	}
	public String getTyre_id()
	{
		return this.tyre_id;
	}
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}
	public String getUuid()
	{
		return this.uuid;
	}
}
