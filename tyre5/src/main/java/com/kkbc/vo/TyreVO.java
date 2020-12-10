package com.kkbc.vo;

import java.util.Date;

/**
 * 
 * @author xu
 *
 */
public class TyreVO {
	
	//tyre_base
	private String tyre_id;
	private String tyre_brand;
	private Integer tyre_flag;
	private String tyre_type1;
	private String tyre_type2;
	private String tyre_type3;
	private Integer tyre_type4;
	private Integer tyre_type5;
	private Integer tyre_type6;
	private Integer tyre_type7;
	private String tyre_type8;
	private String tyre_type9;
	private String tyre_where;	
	private Float tyre_depth;
	private Integer user_id;//创建者id 
	/**
	 * 安全值
	 */
	private Float tyre_safe_depth;
	
	private String true_name;
	
	/**
	 * 装车码表数
	 */
	private Double mabiao_install;
	
	/**
	 * 行驶里程
	 */
	private Double li_cheng_run;
	
	//tyre_dynamic表数据
	private Double tyre_km;//公里数
	private Float tyre_health;//健康值
	
	//tyre_pattern表数据
	private Float tyre_paver;	//轮胎花纹平均深度
	private Date tyre_p_time;//轮胎花纹检测时间
	
	//trucks表数据
	private String trucks_id;//所安装的车牌号
	
	private String queryer;//查询者
	
	private Integer dtu_multi_flag;
	
	private String trucks_type;
	
	private Integer status;//状态

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

	public String getQueryer() {
		return queryer;
	}

	public void setQueryer(String queryer) {
		this.queryer = queryer;
	}

	public Float getTyre_health() {
		return tyre_health;
	}

	public void setTyre_health(Float tyre_health) {
		this.tyre_health = tyre_health;
	}

	public Double getTyre_km() {
		return tyre_km;
	}

	public void setTyre_km(Double tyre_km) {
		this.tyre_km = tyre_km;
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

	public Integer getDtu_multi_flag() {
		return dtu_multi_flag;
	}

	public void setDtu_multi_flag(Integer dtu_multi_flag) {
		this.dtu_multi_flag = dtu_multi_flag;
	}

	public String getTrucks_type() {
		return trucks_type;
	}

	public void setTrucks_type(String trucks_type) {
		this.trucks_type = trucks_type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Float getTyre_safe_depth() {
		return tyre_safe_depth;
	}

	public void setTyre_safe_depth(Float tyre_safe_depth) {
		this.tyre_safe_depth = tyre_safe_depth;
	}

	public String getTrue_name() {
		return true_name;
	}

	public void setTrue_name(String true_name) {
		this.true_name = true_name;
	}
	
}
