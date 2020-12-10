package com.kkbc.vo;

import java.util.Date;

public class DeviceTrucksVo {
	
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
	
	/**
	 * 警告信息
	 */
	private Integer warn;

	private String trucks_style;


	private String trucks_A1;

	private String trucks_A2;

	private String trucks_A3;

	private String trucks_A4;

	private String trucks_A5;

	private String trucks_A6;

	private String trucks_B1;

	private String trucks_B2;

	private String trucks_B3;

	private String trucks_B4;

	private String trucks_B5;

	private String trucks_B6;

	private String trucks_B7;

	private String trucks_B8;

	private String trucks_C1;

	private String trucks_C2;

	private String trucks_C3;

	private String trucks_C4;

	private String trucks_C5;

	private String trucks_C6;

	private String trucks_C7;

	private String trucks_C8;

	private String trucks_C9;

	private String trucks_C10;

	private String trucks_C11;

	private String trucks_C12;

	private String trucks_C13;

	private String trucks_C14;

	private String trucks_C15;

	private String trucks_C16;

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

	public Integer getWarn() {
		return warn;
	}

	public void setWarn(Integer warn) {
		this.warn = warn;
	}

	public String getTrucks_style() {
		return trucks_style;
	}

	public void setTrucks_style(String trucks_style) {
		this.trucks_style = trucks_style;
	}

	public String getTrucks_A1() {
		return trucks_A1;
	}

	public void setTrucks_A1(String trucks_A1) {
		this.trucks_A1 = trucks_A1;
	}

	public String getTrucks_A2() {
		return trucks_A2;
	}

	public void setTrucks_A2(String trucks_A2) {
		this.trucks_A2 = trucks_A2;
	}

	public String getTrucks_A3() {
		return trucks_A3;
	}

	public void setTrucks_A3(String trucks_A3) {
		this.trucks_A3 = trucks_A3;
	}

	public String getTrucks_A4() {
		return trucks_A4;
	}

	public void setTrucks_A4(String trucks_A4) {
		this.trucks_A4 = trucks_A4;
	}

	public String getTrucks_A5() {
		return trucks_A5;
	}

	public void setTrucks_A5(String trucks_A5) {
		this.trucks_A5 = trucks_A5;
	}

	public String getTrucks_A6() {
		return trucks_A6;
	}

	public void setTrucks_A6(String trucks_A6) {
		this.trucks_A6 = trucks_A6;
	}

	public String getTrucks_B1() {
		return trucks_B1;
	}

	public void setTrucks_B1(String trucks_B1) {
		this.trucks_B1 = trucks_B1;
	}

	public String getTrucks_B2() {
		return trucks_B2;
	}

	public void setTrucks_B2(String trucks_B2) {
		this.trucks_B2 = trucks_B2;
	}

	public String getTrucks_B3() {
		return trucks_B3;
	}

	public void setTrucks_B3(String trucks_B3) {
		this.trucks_B3 = trucks_B3;
	}

	public String getTrucks_B4() {
		return trucks_B4;
	}

	public void setTrucks_B4(String trucks_B4) {
		this.trucks_B4 = trucks_B4;
	}

	public String getTrucks_B5() {
		return trucks_B5;
	}

	public void setTrucks_B5(String trucks_B5) {
		this.trucks_B5 = trucks_B5;
	}

	public String getTrucks_B6() {
		return trucks_B6;
	}

	public void setTrucks_B6(String trucks_B6) {
		this.trucks_B6 = trucks_B6;
	}

	public String getTrucks_B7() {
		return trucks_B7;
	}

	public void setTrucks_B7(String trucks_B7) {
		this.trucks_B7 = trucks_B7;
	}

	public String getTrucks_B8() {
		return trucks_B8;
	}

	public void setTrucks_B8(String trucks_B8) {
		this.trucks_B8 = trucks_B8;
	}

	public String getTrucks_C1() {
		return trucks_C1;
	}

	public void setTrucks_C1(String trucks_C1) {
		this.trucks_C1 = trucks_C1;
	}

	public String getTrucks_C2() {
		return trucks_C2;
	}

	public void setTrucks_C2(String trucks_C2) {
		this.trucks_C2 = trucks_C2;
	}

	public String getTrucks_C3() {
		return trucks_C3;
	}

	public void setTrucks_C3(String trucks_C3) {
		this.trucks_C3 = trucks_C3;
	}

	public String getTrucks_C4() {
		return trucks_C4;
	}

	public void setTrucks_C4(String trucks_C4) {
		this.trucks_C4 = trucks_C4;
	}

	public String getTrucks_C5() {
		return trucks_C5;
	}

	public void setTrucks_C5(String trucks_C5) {
		this.trucks_C5 = trucks_C5;
	}

	public String getTrucks_C6() {
		return trucks_C6;
	}

	public void setTrucks_C6(String trucks_C6) {
		this.trucks_C6 = trucks_C6;
	}

	public String getTrucks_C7() {
		return trucks_C7;
	}

	public void setTrucks_C7(String trucks_C7) {
		this.trucks_C7 = trucks_C7;
	}

	public String getTrucks_C8() {
		return trucks_C8;
	}

	public void setTrucks_C8(String trucks_C8) {
		this.trucks_C8 = trucks_C8;
	}

	public String getTrucks_C9() {
		return trucks_C9;
	}

	public void setTrucks_C9(String trucks_C9) {
		this.trucks_C9 = trucks_C9;
	}

	public String getTrucks_C10() {
		return trucks_C10;
	}

	public void setTrucks_C10(String trucks_C10) {
		this.trucks_C10 = trucks_C10;
	}

	public String getTrucks_C11() {
		return trucks_C11;
	}

	public void setTrucks_C11(String trucks_C11) {
		this.trucks_C11 = trucks_C11;
	}

	public String getTrucks_C12() {
		return trucks_C12;
	}

	public void setTrucks_C12(String trucks_C12) {
		this.trucks_C12 = trucks_C12;
	}

	public String getTrucks_C13() {
		return trucks_C13;
	}

	public void setTrucks_C13(String trucks_C13) {
		this.trucks_C13 = trucks_C13;
	}

	public String getTrucks_C14() {
		return trucks_C14;
	}

	public void setTrucks_C14(String trucks_C14) {
		this.trucks_C14 = trucks_C14;
	}

	public String getTrucks_C15() {
		return trucks_C15;
	}

	public void setTrucks_C15(String trucks_C15) {
		this.trucks_C15 = trucks_C15;
	}

	public String getTrucks_C16() {
		return trucks_C16;
	}

	public void setTrucks_C16(String trucks_C16) {
		this.trucks_C16 = trucks_C16;
	}

	
}
