package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student {

	private static final long serialVersionUID = 6338161332483928760L;
	//主键
	private Integer str_id;
	//学号
	private String str_num;
	//学生姓名
	private String str_name;
	//年级
	private String str_grade;
	//班级
	private String str_class;
	//性别
	private int str_sex;
	//年龄
	private int str_age;
	//学校
	private String str_school;
	//家庭住址
	private String str_address;
	//联系方式
	private String str_phone;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="str_id")
	public Integer getStr_id() {
		return str_id;
	}
	public void setStr_id(Integer str_id) {
		this.str_id = str_id;
	}
	@Column(name="str_num",length=100)
	public String getStr_num() {
		return str_num;
	}
	public void setStr_num(String str_num) {
		this.str_num = str_num;
	}
	@Column(name="str_name",length=20)
	public String getStr_name() {
		return str_name;
	}
	public void setStr_name(String str_name) {
		this.str_name = str_name;
	}
	
	@Column(name="str_grade",length=20)
	public String getStr_grade() {
		return str_grade;
	}
	public void setStr_grade(String str_grade) {
		this.str_grade = str_grade;
	}
	
	@Column(name="str_class",length=20)
	public String getStr_class() {
		return str_class;
	}
	public void setStr_class(String str_class) {
		this.str_class = str_class;
	}
	
	@Column(name="str_sex")
	public int getStr_sex() {
		return str_sex;
	}
	public void setStr_sex(int str_sex) {
		this.str_sex = str_sex;
	}
	
	@Column(name="str_age")
	public int getStr_age() {
		return str_age;
	}
	public void setStr_age(int str_age) {
		this.str_age = str_age;
	}
	
	@Column(name="str_school",length=30)
	public String getStr_school() {
		return str_school;
	}
	public void setStr_school(String str_school) {
		this.str_school = str_school;
	}
	
	@Column(name="str_address",length=100)
	public String getStr_address() {
		return str_address;
	}
	public void setStr_address(String str_address) {
		this.str_address = str_address;
	}
	
	@Column(name="str_phone",length=20)
	public String getStr_phone() {
		return str_phone;
	}
	public void setStr_phone(String str_phone) {
		this.str_phone = str_phone;
	}
	
	
}
