package com.psylife.entity;

import java.util.Date;

/**
 * 工单记录日志
 * @author xu
 * Created on 2016年7月12日 下午6:29:09
 */
public class WorkOrderRecord 
{	
	
	/**
	 * 事件类型,检测包括检测、修补、动平衡、四轮定位、充氮气、气压调整
	 */
	public static final String ACTION_TYPE_CHECK = "检测";
	
	private Integer id;
	
	/**
	 * 创建时间
	 */
	private Date create_time;
	
	/**
	 * 工单id
	 */
	private Integer work_order_id;
	
	/**
	 * 事件属性
	 */
	private String action;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 胎号
	 */
	private String tyre_id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getWork_order_id() {
		return work_order_id;
	}
	public void setWork_order_id(Integer work_order_id) {
		this.work_order_id = work_order_id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTyre_id() {
		return tyre_id;
	}
	public void setTyre_id(String tyre_id) {
		this.tyre_id = tyre_id;
	}

}
