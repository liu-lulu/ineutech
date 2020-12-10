package com.kkbc.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 轮胎基础信息
 * @author 
 *
 */
public class TyreBase implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3071215321757779377L;

	private int id;
	/**
	 * 胎号
	 */
	private String tyre_id;
	
	private Date create_time;
	
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
	
	private String trucks_id;
	
	/**
	 * 原始深度
	 */
	private Float tyre_depth;
	
	/**
	 * 创建者id
	 */
	private Integer user_id;//创建者id 
	
	public Float getTyre_depth() {
		return tyre_depth;
	}
	public void setTyre_depth(Float tyre_depth) {
		this.tyre_depth = tyre_depth;
	}
	public String getTyre_where() {
		return tyre_where;
	}
	public void setTyre_where(String tyre_where) {
		this.tyre_where = tyre_where;
	}
	public String getTyre_type9() {
		return tyre_type9;
	}
	public void setTyre_type9(String tyre_type9) {
		this.tyre_type9 = tyre_type9;
	}
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
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getTrucks_id() {
		return trucks_id;
	}
	public void setTrucks_id(String trucks_id) {
		this.trucks_id = trucks_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
