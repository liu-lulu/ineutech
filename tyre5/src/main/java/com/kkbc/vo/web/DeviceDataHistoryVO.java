package com.kkbc.vo.web;

import java.util.Date;
import java.util.List;

import com.kkbc.entity.DeviceDataOffon;


/**
 * 网页显示
 * dtu设备数据历史记录
 * device_data_history关联device_data_base、device_data_dingwei、device_data_offon、device_data_yali_wendu，其中device_data_base、device_data_dingwei是通过id关联的，device_data_offon、device_data_yali_wendu是通过uuid关联的
 * @author xu
 *
 */
public class DeviceDataHistoryVO 
{	
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 创建时间
	 */
	private Date create_time;
	
	/**
	 * float采集时间
	 */
	private Date float_caiji_time;
	
	/**
	 * string采集时间
	 */
	private Date string_caiji_time;
	
	/**
	 * 开关量采集时间
	 */
	private Date offon_caiji_time;
	
	/**
	 * 设备数据基础id
	 */
	private Long base_id;
	
	/**
	 * 定位信息id
	 */
	private Long dingwei_id;
	
	/**
	 * 发射器开关量uuid
	 */
	private String offon_uuid;
	
	/**
	 * 发射器压力温度uuid
	 */
	private String yaliwendu_uuid;
	
	/**
	 * 车牌号
	 */
	private String trucks_id;
	
	/**
	 * 是否有警告信息（0表示无，1表示有）
	 */
	private Integer warn;
	
	/**
	 * DTU ID
	 */
	private String dtu_id;
	
	/**
	 * 开关量
	 */
	private List<DeviceDataOffon> deviceDataOffons;
	
	/**
	 * GPS的定位状态（0表示有效定位，1表示无效定位）
	 */
	private Integer gps_status;
	
	/**
	 * 地面速率（单位：节）
	 */
	private Float dimian_sulu;
	
	/**
	 * dtu警告信息详情
	 */
	private String warnInfo;
	
	/**
	 * 纬度
	 */
	private  Double latitude;
	
	/**
	 * 经度
	 */
	private Double longitude;

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
	public void setFloat_caiji_time(Date float_caiji_time)
	{
		this.float_caiji_time = float_caiji_time;
	}
	public Date getFloat_caiji_time()
	{
		return this.float_caiji_time;
	}
	public void setString_caiji_time(Date string_caiji_time)
	{
		this.string_caiji_time = string_caiji_time;
	}
	public Date getString_caiji_time()
	{
		return this.string_caiji_time;
	}
	public void setOffon_caiji_time(Date offon_caiji_time)
	{
		this.offon_caiji_time = offon_caiji_time;
	}
	public Date getOffon_caiji_time()
	{
		return this.offon_caiji_time;
	}
	public void setOffon_uuid(String offon_uuid)
	{
		this.offon_uuid = offon_uuid;
	}
	public String getOffon_uuid()
	{
		return this.offon_uuid;
	}
	public Long getBase_id() {
		return base_id;
	}
	public void setBase_id(Long base_id) {
		this.base_id = base_id;
	}
	public Long getDingwei_id() {
		return dingwei_id;
	}
	public void setDingwei_id(Long dingwei_id) {
		this.dingwei_id = dingwei_id;
	}
	public void setYaliwendu_uuid(String yaliwendu_uuid)
	{
		this.yaliwendu_uuid = yaliwendu_uuid;
	}
	public String getYaliwendu_uuid()
	{
		return this.yaliwendu_uuid;
	}
	public void setTrucks_id(String trucks_id)
	{
		this.trucks_id = trucks_id;
	}
	public String getTrucks_id()
	{
		return this.trucks_id;
	}
	public void setWarn(Integer warn)
	{
		this.warn = warn;
	}
	public Integer getWarn()
	{
		return this.warn;
	}
	public String getDtu_id() {
		return dtu_id;
	}
	public void setDtu_id(String dtu_id) {
		this.dtu_id = dtu_id;
	}
	public List<DeviceDataOffon> getDeviceDataOffons() {
		return deviceDataOffons;
	}
	public void setDeviceDataOffons(List<DeviceDataOffon> deviceDataOffons) {
		this.deviceDataOffons = deviceDataOffons;
	}
	public Integer getGps_status() {
		return gps_status;
	}
	public void setGps_status(Integer gps_status) {
		this.gps_status = gps_status;
	}
	public Float getDimian_sulu() {
		return dimian_sulu;
	}
	public void setDimian_sulu(Float dimian_sulu) {
		this.dimian_sulu = dimian_sulu;
	}
	public String getWarnInfo() {
		return warnInfo;
	}
	public void setWarnInfo(String warnInfo) {
		this.warnInfo = warnInfo;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
}
