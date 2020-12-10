package com.psylife.entity;

import java.util.Date;

/**
 * dtu设备表
 * @author xu
 *
 */
public class Device 
{	
	/**
	 * 表名
	 */
	public static final String TB_N="device";
	
	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 当前绑定的车牌
	 */
	private String trucks_id;
	
	/**
	 * DTU ID
	 */
	private String dtu_id;
	
	/**
	 * 创建时间
	 */
	private Date create_time;
	
	/**
	 * 更新时间
	 */
	private Date update_time;
	
	/**
	 * 采集时间
	 */
	private Date caiji_time;

	/**
	 * DTU与TPMS通信状态（1-正常，0-中断,注意不要写反了，与其他不同）
	 */
	private Integer dtu_tpms_status;
	
	/**
	 * DTU的工作状态（0表示汽车运行，1表示关闭）
	 */
	private Integer dtu_status;
	
	/**
	 * TPMS发送频率(单位：分钟，可配置)
	 */
	private Integer tpms_pinlu;
	
	/**
	 * 压力设置,第一轴低压
	 */
	private Float yali_set_low_zhou_1;
	
	/**
	 * 压力设置,第二轴低压
	 */
	private Float yali_set_low_zhou_2;
	
	/**
	 * 压力设置,第三轴低压
	 */
	private Float yali_set_low_zhou_3;
	
	/**
	 * 压力设置,第四轴低压
	 */
	private Float yali_set_low_zhou_4;
	
	/**
	 * 压力设置,第一轴高压
	 */
	private Float yali_set_high_zhou_1;
	
	/**
	 * 压力设置,第二轴高压
	 */
	private Float yali_set_high_zhou_2;
	
	/**
	 * 压力设置,第三轴高压
	 */
	private Float yali_set_high_zhou_3;
	
	/**
	 * 压力设置,第四轴高压
	 */
	private Float yali_set_high_zhou_4;
	
	/**
	 * 高温报警设置（单位摄氏度）
	 */
	private Float gao_wen_bao_jing_set;
	
	/**
	 * 预留拖卡1的手机号码（用于车头，车身请填写-99.99  如果车头暂时不能提供此数据，也请填写-99.99）
	 */
	private String yuliu1_phone;
	
	/**
	 * 预留拖卡2的手机号码（用于车头，车身请填写-99.99  如果车头暂时不能提供此数据，也请填写-99.99）
	 */
	private String yuliu2_phone;
	
	/**
	 * 预留拖卡3的手机号码（用于车头，车身请填写-99.99  如果车头暂时不能提供此数据，也请填写-99.99）
	 */
	private String yuliu3_phone;
	
	/**
	 * 预留拖卡4的手机号码（用于车头，车身请填写-99.99  如果车头暂时不能提供此数据，也请填写-99.99）
	 */
	private String yuliu4_phone;
	
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
	 * 里程数,单位:公里
	 */
	private Double li_cheng;
	
	/**
	 * 公司id
	 */
	private Integer company_id;
	
	public boolean equalsValue(Device obj){
		if(obj==null){
			return false;
		}
		if(this.dtu_tpms_status==null||!this.dtu_tpms_status.equals(obj.getDtu_tpms_status())){
			return false;
		}
		
		if(this.dtu_status==null||!this.dtu_status.equals(obj.getDtu_status())){
			return false;
		}
		
		if(this.tpms_pinlu==null||!this.tpms_pinlu.equals(obj.getTpms_pinlu())){
			return false;
		}
		//低压
		if(this.yali_set_low_zhou_1==null||obj.getYali_set_low_zhou_1()==null||Math.abs(this.yali_set_low_zhou_1.floatValue()-obj.getYali_set_low_zhou_1().floatValue())>0.0999){
			return false;
		}
		if(this.yali_set_low_zhou_2==null||obj.getYali_set_low_zhou_2()==null||Math.abs(this.yali_set_low_zhou_2.floatValue()-obj.getYali_set_low_zhou_2().floatValue())>0.0999){
			return false;
		}
		if(this.yali_set_low_zhou_3==null||obj.getYali_set_low_zhou_3()==null||Math.abs(this.yali_set_low_zhou_3.floatValue()-obj.getYali_set_low_zhou_3().floatValue())>0.0999){
			return false;
		}
		if(this.yali_set_low_zhou_4==null||obj.getYali_set_low_zhou_4()==null||Math.abs(this.yali_set_low_zhou_4.floatValue()-obj.getYali_set_low_zhou_4().floatValue())>0.0999){
			return false;
		}
		//高压
		if(this.yali_set_high_zhou_1==null||obj.getYali_set_high_zhou_1()==null||Math.abs(this.yali_set_high_zhou_1.floatValue()-obj.getYali_set_high_zhou_1().floatValue())>0.0999){
			return false;
		}
		if(this.yali_set_high_zhou_2==null||obj.getYali_set_high_zhou_2()==null||Math.abs(this.yali_set_high_zhou_2.floatValue()-obj.getYali_set_high_zhou_2().floatValue())>0.0999){
			return false;
		}
		if(this.yali_set_high_zhou_3==null||obj.getYali_set_high_zhou_3()==null||Math.abs(this.yali_set_high_zhou_3.floatValue()-obj.getYali_set_high_zhou_3().floatValue())>0.0999){
			return false;
		}
		if(this.yali_set_high_zhou_4==null||obj.getYali_set_high_zhou_4()==null||Math.abs(this.yali_set_high_zhou_4.floatValue()-obj.getYali_set_high_zhou_4().floatValue())>0.0999){
			return false;
		}
		
		if(this.gao_wen_bao_jing_set==null||obj.getGao_wen_bao_jing_set()==null||
				Math.abs(this.gao_wen_bao_jing_set.floatValue()-obj.getGao_wen_bao_jing_set().floatValue())>0.0999){
			return false;
		}
		
		//预留拖卡1的手机号码
		if(this.yuliu1_phone==null||!this.yuliu1_phone.equals(obj.getYuliu1_phone())){
			return false;
		}
		if(this.yuliu2_phone==null||!this.yuliu2_phone.equals(obj.getYuliu2_phone())){
			return false;
		}
		if(this.yuliu3_phone==null||!this.yuliu3_phone.equals(obj.getYuliu3_phone())){
			return false;
		}
		if(this.yuliu4_phone==null||!this.yuliu4_phone.equals(obj.getYuliu4_phone())){
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
				Math.abs(this.latitude.doubleValue()-obj.getLatitude().doubleValue())>0.000039999d){
			return false;
		}
		if(this.longitude==null||obj.getLongitude()==null||
				Math.abs(this.longitude.doubleValue()-obj.getLongitude().doubleValue())>0.000039999d){
			return false;
		}
		
		if(this.dimian_sulu==null||obj.getDimian_sulu()==null||Math.abs(this.dimian_sulu.floatValue()-obj.getDimian_sulu().floatValue())>0.0999){
			return false;
		}
		if(this.dimian_hangxiang==null||obj.getDimian_hangxiang()==null||Math.abs(this.dimian_hangxiang.floatValue()-obj.getDimian_hangxiang().floatValue())>0.0999){
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

	public String getTrucks_id() {
		return trucks_id;
	}

	public void setTrucks_id(String trucks_id) {
		this.trucks_id = trucks_id;
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

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
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

	public Integer getTpms_pinlu() {
		return tpms_pinlu;
	}

	public void setTpms_pinlu(Integer tpms_pinlu) {
		this.tpms_pinlu = tpms_pinlu;
	}

	public Float getYali_set_low_zhou_1() {
		return yali_set_low_zhou_1;
	}

	public void setYali_set_low_zhou_1(Float yali_set_low_zhou_1) {
		this.yali_set_low_zhou_1 = yali_set_low_zhou_1;
	}

	public Float getYali_set_low_zhou_2() {
		return yali_set_low_zhou_2;
	}

	public void setYali_set_low_zhou_2(Float yali_set_low_zhou_2) {
		this.yali_set_low_zhou_2 = yali_set_low_zhou_2;
	}

	public Float getYali_set_low_zhou_3() {
		return yali_set_low_zhou_3;
	}

	public void setYali_set_low_zhou_3(Float yali_set_low_zhou_3) {
		this.yali_set_low_zhou_3 = yali_set_low_zhou_3;
	}

	public Float getYali_set_low_zhou_4() {
		return yali_set_low_zhou_4;
	}

	public void setYali_set_low_zhou_4(Float yali_set_low_zhou_4) {
		this.yali_set_low_zhou_4 = yali_set_low_zhou_4;
	}

	public Float getYali_set_high_zhou_1() {
		return yali_set_high_zhou_1;
	}

	public void setYali_set_high_zhou_1(Float yali_set_high_zhou_1) {
		this.yali_set_high_zhou_1 = yali_set_high_zhou_1;
	}

	public Float getYali_set_high_zhou_2() {
		return yali_set_high_zhou_2;
	}

	public void setYali_set_high_zhou_2(Float yali_set_high_zhou_2) {
		this.yali_set_high_zhou_2 = yali_set_high_zhou_2;
	}

	public Float getYali_set_high_zhou_3() {
		return yali_set_high_zhou_3;
	}

	public void setYali_set_high_zhou_3(Float yali_set_high_zhou_3) {
		this.yali_set_high_zhou_3 = yali_set_high_zhou_3;
	}

	public Float getYali_set_high_zhou_4() {
		return yali_set_high_zhou_4;
	}

	public void setYali_set_high_zhou_4(Float yali_set_high_zhou_4) {
		this.yali_set_high_zhou_4 = yali_set_high_zhou_4;
	}

	public Float getGao_wen_bao_jing_set() {
		return gao_wen_bao_jing_set;
	}

	public void setGao_wen_bao_jing_set(Float gao_wen_bao_jing_set) {
		this.gao_wen_bao_jing_set = gao_wen_bao_jing_set;
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

	public Double getLi_cheng() {
		return li_cheng;
	}

	public void setLi_cheng(Double li_cheng) {
		this.li_cheng = li_cheng;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

}
