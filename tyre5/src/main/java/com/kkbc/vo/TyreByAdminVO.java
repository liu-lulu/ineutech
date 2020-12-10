package com.kkbc.vo;

import java.util.Date;

import com.kkbc.entity.DeviceFasheqi;
import com.kkbc.hardware.process.HeartProcess;

/**
 * 胎详情(一胎一卡)
 * @author xu
 *
 */
public class TyreByAdminVO {
	
	//tyre_base
	/**
	 * 胎号
	 */
	private String tyre_id;
	
	/**
	 * 品牌
	 */
	private String tyre_brand;
	
	/**
	 * 状态   1--装载，0--卸下
	 */
	private Integer tyre_flag;


	/**
	 * 规格
	 */
	private String tyre_type1;
	
	/**
	 * 类型(花纹代码)
	 */
	private String tyre_type2;
	
	/**
	 * 花纹
	 */
	private String tyre_type3;
	
	/**
	 * 轮毂
	 */
	private Integer tyre_type4;
	
	/**
	 * 气门帽
	 */
	private Integer tyre_type5;
	
	/**
	 * 内胎---有无
	 */
	private Integer tyre_type6;
	
	/**
	 * 性质--全钢，半钢
	 */
	private Integer tyre_type7;
	
	/**
	 * 入库人
	 */
	private String tyre_type8;
	
	/**
	 * 入库时间
	 */
	private String tyre_type9;
	
	/**
	 * 车胎具体位置
	 */
	private String tyre_where;	
	
	/**
	 * 原始深度
	 */
	private Float tyre_depth;
	
	/**
	 * 创建者id
	 */
	private Integer user_id;
	
	/**
	 * 轮胎外伤 有或无,0无,1有
	 */
	public Integer tyre_trauma;
	
	/**
	 * 轮胎异常 正常或异常,0无,1有
	 */
	public Integer tyre_abnormal;
	
	/**
	 * 胎的小贴士
	 */
	private String remark;
	
	//tyre_dynamic表数据
	/**
	 * gps公里数
	 */
	private Double tyre_km;
	
	/**
	 * 装车码表数
	 */
	private Double mabiao_install;
	
	/**
	 * 行驶里程
	 */
	private Double li_cheng_run;
	
	/**
	 * 预计里程
	 */
	private Double li_cheng_estimate;
	
	
	/**
	 * 健康值
	 */
	private Float tyre_health;
	
	//tyre_pattern表数据
	/**
	 * 轮胎花纹平均深度
	 */
	private Float tyre_paver;
	
	/**
	 * 轮胎花纹检测时间
	 */
	private Date tyre_p_time;
	
	//trucks表数据
	/**
	 * 所安装的车牌号
	 */
	private String trucks_id;
	
	/**
	 * 入库时间
	 */
	private Date create_time;
	
	/**
	 * 1是行使,0是停放
	 */
	private Integer trucks_flag;
	
	/**
	 * 厂牌
	 */
	private String trucks_brand;
	
	/**
	 * 车，当前行驶里程
	 */
	private Double trucks_li_cheng_run;
	
	/**
	 * 发射器
	 */
	private DeviceFasheqi deviceFasheqi;
	
	/**
	 * 车型
	 */
	private String trucks_style;
	
	/**
	 * 气门 正常或受损,0正常,1是受损
	 */
	private Integer tyre_value;
	
	/**
	 * 翻新 0否，1是
	 */
	private Integer tyre_fanxin;
	
	/**
	 * 车辆型号,如J6P
	 */
	private String trucks_mode;
	
	/**
	 * 码表数
	 */
	private Double mabiao;
	
	//device表数据
	/**
	 * 上报,采集时间即最新一次
	 */
	private Date caiji_time;
	
	/**
	 * dtu是否在线
	 */
	private Integer dtuOnlineStatus;
	
	/**
	 * DTU ID
	 */
	private String dtu_id;
	
	/**
	 * 单耗
	 */
	public Double dan_hao;
	

	public String getTyre_id() {
		return tyre_id;
	}

	public void setTyre_id(String tyre_id) {
		this.tyre_id = tyre_id;
	}

	public String getTyre_brand() {
		return tyre_brand;
	}

	public void setTyre_brand(String tyre_brand) {
		this.tyre_brand = tyre_brand;
	}

	public Integer getTyre_flag() {
		return tyre_flag;
	}

	public void setTyre_flag(Integer tyre_flag) {
		this.tyre_flag = tyre_flag;
	}

	public String getTyre_type1() {
		return tyre_type1;
	}

	public void setTyre_type1(String tyre_type1) {
		this.tyre_type1 = tyre_type1;
	}

	public String getTyre_type2() {
		return tyre_type2;
	}

	public void setTyre_type2(String tyre_type2) {
		this.tyre_type2 = tyre_type2;
	}

	public String getTyre_type3() {
		return tyre_type3;
	}

	public void setTyre_type3(String tyre_type3) {
		this.tyre_type3 = tyre_type3;
	}

	public Integer getTyre_type4() {
		return tyre_type4;
	}

	public void setTyre_type4(Integer tyre_type4) {
		this.tyre_type4 = tyre_type4;
	}

	public Integer getTyre_type5() {
		return tyre_type5;
	}

	public void setTyre_type5(Integer tyre_type5) {
		this.tyre_type5 = tyre_type5;
	}

	public Integer getTyre_type6() {
		return tyre_type6;
	}

	public void setTyre_type6(Integer tyre_type6) {
		this.tyre_type6 = tyre_type6;
	}

	public Integer getTyre_type7() {
		return tyre_type7;
	}

	public void setTyre_type7(Integer tyre_type7) {
		this.tyre_type7 = tyre_type7;
	}

	public String getTyre_type8() {
		return tyre_type8;
	}

	public void setTyre_type8(String tyre_type8) {
		this.tyre_type8 = tyre_type8;
	}

	public String getTyre_type9() {
		return tyre_type9;
	}

	public void setTyre_type9(String tyre_type9) {
		this.tyre_type9 = tyre_type9;
	}

	public String getTyre_where() {
		return tyre_where;
	}

	public void setTyre_where(String tyre_where) {
		this.tyre_where = tyre_where;
	}

	public Float getTyre_depth() {
		return tyre_depth;
	}

	public void setTyre_depth(Float tyre_depth) {
		this.tyre_depth = tyre_depth;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Double getTyre_km() {
		return tyre_km;
	}

	public void setTyre_km(Double tyre_km) {
		this.tyre_km = tyre_km;
	}

	public Float getTyre_health() {
		return tyre_health;
	}

	public void setTyre_health(Float tyre_health) {
		this.tyre_health = tyre_health;
	}

	public Float getTyre_paver() {
		return tyre_paver;
	}

	public void setTyre_paver(Float tyre_paver) {
		this.tyre_paver = tyre_paver;
	}

	public Date getTyre_p_time() {
		return tyre_p_time;
	}

	public void setTyre_p_time(Date tyre_p_time) {
		this.tyre_p_time = tyre_p_time;
	}

	public String getTrucks_id() {
		return trucks_id;
	}

	public void setTrucks_id(String trucks_id) {
		this.trucks_id = trucks_id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getTrucks_flag() {
		return trucks_flag;
	}

	public void setTrucks_flag(Integer trucks_flag) {
		this.trucks_flag = trucks_flag;
	}

	public String getTrucks_brand() {
		return trucks_brand;
	}

	public void setTrucks_brand(String trucks_brand) {
		this.trucks_brand = trucks_brand;
	}

	public DeviceFasheqi getDeviceFasheqi() {
		return deviceFasheqi;
	}

	public void setDeviceFasheqi(DeviceFasheqi deviceFasheqi) {
		this.deviceFasheqi = deviceFasheqi;
	}

	public String getTrucks_style() {
		return trucks_style;
	}

	public void setTrucks_style(String trucks_style) {
		this.trucks_style = trucks_style;
	}

	public Integer getTyre_trauma() {
		return tyre_trauma;
	}

	public void setTyre_trauma(Integer tyre_trauma) {
		this.tyre_trauma = tyre_trauma;
	}

	public Integer getTyre_abnormal() {
		return tyre_abnormal;
	}

	public void setTyre_abnormal(Integer tyre_abnormal) {
		this.tyre_abnormal = tyre_abnormal;
	}

	public Integer getTyre_value() {
		return tyre_value;
	}

	public void setTyre_value(Integer tyre_value) {
		this.tyre_value = tyre_value;
	}

	public Double getMabiao_install() {
		return mabiao_install;
	}

	public void setMabiao_install(Double mabiao_install) {
		this.mabiao_install = mabiao_install;
	}

	public Double getLi_cheng_run() {
		return li_cheng_run;
	}

	public void setLi_cheng_run(Double li_cheng_run) {
		this.li_cheng_run = li_cheng_run;
	}

	public Double getLi_cheng_estimate() {
		return li_cheng_estimate;
	}

	public void setLi_cheng_estimate(Double li_cheng_estimate) {
		this.li_cheng_estimate = li_cheng_estimate;
	}

	public Double getTrucks_li_cheng_run() {
		return trucks_li_cheng_run;
	}

	public void setTrucks_li_cheng_run(Double trucks_li_cheng_run) {
		this.trucks_li_cheng_run = trucks_li_cheng_run;
	}
	
	public Integer getDtuOnlineStatus() {
		if(caiji_time!=null&&System.currentTimeMillis()-caiji_time.getTime()<HeartProcess.INTERVAL_TIMEOUT_TCP){
			return 1;
		}
		return 0;
	}

	public void setDtuOnlineStatus(Integer dtuOnlineStatus) {
		this.dtuOnlineStatus = dtuOnlineStatus;
	}

	public Date getCaiji_time() {
		return caiji_time;
	}

	public void setCaiji_time(Date caiji_time) {
		this.caiji_time = caiji_time;
	}

	public String getDtu_id() {
		return dtu_id;
	}

	public void setDtu_id(String dtu_id) {
		this.dtu_id = dtu_id;
	}

	public String getTrucks_mode() {
		return trucks_mode;
	}

	public void setTrucks_mode(String trucks_mode) {
		this.trucks_mode = trucks_mode;
	}

	public Double getMabiao() {
		return mabiao;
	}

	public void setMabiao(Double mabiao) {
		this.mabiao = mabiao;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getTyre_fanxin() {
		return tyre_fanxin;
	}

	public void setTyre_fanxin(Integer tyre_fanxin) {
		this.tyre_fanxin = tyre_fanxin;
	}

	public Double getDan_hao() {
		return dan_hao;
	}

	public void setDan_hao(Double dan_hao) {
		this.dan_hao = dan_hao;
	}

}
