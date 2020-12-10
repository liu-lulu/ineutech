package com.kkbc.entity;

import java.util.Date;

/**
 * 
 * @author xu
 * 设备发射器表
 */
public class DeviceFasheqi 
{	
	/**
	 * 表名
	 */
	public static final String TB_N="device_fasheqi";
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 创建时间
	 */
	private Date create_time;
	
	/**
	 * 采集时间
	 */
	private Date caiji_time;
	
	/**
	 * 发射器id
	 */
	private String fasheqi_id;
	
	/**
	 * 急漏气告警（0表示无告警，1表示有告警）
	 */
	private Integer louqi;
	
	/**
	 * 高压告警（0表示无告警，1表示有告警）
	 */
	private Integer gaoya;
	
	/**
	 * 低压告警（0表示无告警，1表示有告警）
	 */
	private Integer diya;
	
	/**
	 * 高温告警（0表示无告警，1表示有告警）
	 */
	private Integer gaowen;
	
	/**
	 * 设备电池低电（0表示无告警，1表示有告警）
	 */
	private Integer dianchi;
	
	/**
	 * 发射器低电池告警（0表示无告警，1表示有告警）
	 */
	private Integer fasheqidianchi;
	
	/**
	 * 发射器发射中断告警（0表示无告警，1表示有告警）
	 */
	private Integer zhongduan;
	
	/**
	 * 失联（0表示无，1表示失联）
	 */
	private Integer shilian;
	
	/**
	 * 是否有警告信息（0表示无，1表示有）
	 */
	private Integer warn;
	
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
	 * 车牌
	 */
	private String trucks_id;
	
	/**
	 * 里程数,单位:公里
	 */
	private Double li_cheng;
	
	/**
	 * DTU ID
	 */
	private String dtu_id;
	
	public boolean equalsValue(DeviceFasheqi obj){
		if(obj==null){
			return false;
		}
		if(this.fasheqi_id==null||!this.fasheqi_id.equals(obj.getFasheqi_id())){
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
		if(this.zhongduan==null||!this.zhongduan.equals(obj.getZhongduan())){
			return false;
		}
		if(this.shilian==null||!this.shilian.equals(obj.getShilian())){
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
		if(this.warn==null||!this.warn.equals(obj.getWarn())){
			return false;
		}
		if(this.no==null||!this.no.equals(obj.getNo())){
			return false;
		}
		if(this.dtu_id==null||!this.dtu_id.equals(obj.getDtu_id())){
			return false;
		}
		if((this.tyre_id==null&&obj.getTyre_id()!=null)||(this.tyre_id!=null&&!this.tyre_id.equals(obj.getTyre_id()))){
			return false;
		}
		if((this.trucks_id==null&&obj.getTrucks_id()!=null)||(this.trucks_id!=null&&!this.trucks_id.equals(obj.getTrucks_id()))){
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
	public String getFasheqi_id() {
		return fasheqi_id;
	}
	public void setFasheqi_id(String fasheqi_id) {
		this.fasheqi_id = fasheqi_id;
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
	public Integer getZhongduan() {
		return zhongduan;
	}
	public void setZhongduan(Integer zhongduan) {
		this.zhongduan = zhongduan;
	}
	public Integer getShilian() {
		return shilian;
	}
	public void setShilian(Integer shilian) {
		this.shilian = shilian;
	}
	public Integer getWarn() {
		return warn;
	}
	public void setWarn(Integer warn) {
		this.warn = warn;
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
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getTyre_id() {
		return tyre_id;
	}
	public void setTyre_id(String tyre_id) {
		this.tyre_id = tyre_id;
	}
	public String getTrucks_id() {
		return trucks_id;
	}
	public void setTrucks_id(String trucks_id) {
		this.trucks_id = trucks_id;
	}
	public Double getLi_cheng() {
		return li_cheng;
	}
	public void setLi_cheng(Double li_cheng) {
		this.li_cheng = li_cheng;
	}

	public String getDtu_id() {
		return dtu_id;
	}

	public void setDtu_id(String dtu_id) {
		this.dtu_id = dtu_id;
	}
	
	
}
