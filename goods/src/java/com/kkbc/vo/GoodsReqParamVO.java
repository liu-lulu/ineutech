package com.kkbc.vo;

import java.util.ArrayList;
import java.util.List;

import com.gexin.fastjson.JSONObject;

public class GoodsReqParamVO {
	
	private Integer desc_id;
	private String publishDate;
	private String menu;
	
	public GoodsReqParamVO(){}
	public GoodsReqParamVO(Integer desc_id,String publishDate,String menu){
		this.desc_id=desc_id;
		this.publishDate=publishDate;
		this.menu=menu;
	}
	
	public Integer getDesc_id() {
		return desc_id;
	}
	public void setDesc_id(Integer desc_id) {
		this.desc_id = desc_id;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}

	public static void main(String[] args) {
		List<GoodsReqParamVO> list=new ArrayList<GoodsReqParamVO>();
		GoodsReqParamVO vo1=new GoodsReqParamVO(1, "2018-01-02", "1");
		GoodsReqParamVO vo2=new GoodsReqParamVO(2, "2018-01-03", "2");
		list.add(vo1);
		list.add(vo2);
		System.out.println(JSONObject.toJSONString(list));
	}
}
