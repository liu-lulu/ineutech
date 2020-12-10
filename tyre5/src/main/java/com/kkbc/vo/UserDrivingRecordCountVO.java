package com.kkbc.vo;

/**
 * 
 * @author xu
 * Created on 2016年7月12日 上午11:51:30
 */
public class UserDrivingRecordCountVO 
{	
	
	
	/**
	 * 总数
	 */
	private Integer count;
	
	/**
	 * 行驶里程
	 */
	private Double li_cheng;
	
	/**
	 * 客服电话
	 */
	private String service_phone;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getLi_cheng() {
		return li_cheng;
	}

	public void setLi_cheng(Double li_cheng) {
		this.li_cheng = li_cheng;
	}

	public String getService_phone() {
		return service_phone;
	}

	public void setService_phone(String service_phone) {
		this.service_phone = service_phone;
	}
	
	

}
