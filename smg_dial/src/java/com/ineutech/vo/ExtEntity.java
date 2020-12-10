package com.ineutech.vo;

import java.util.List;

/**
 * 
 * @name: ExtEntity 
 * @description: 对于数据库操作的通用的实体类
 * @date 2018年2月1日 下午4:19:00
 * @author liululu
 */
public class ExtEntity {
	private long id;
	private String tableName;
	private String colName;
	private List<Object> colValueList;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public List<Object> getColValueList() {
		return colValueList;
	}
	public void setColValueList(List<Object> colValueList) {
		this.colValueList = colValueList;
	}
	

}
