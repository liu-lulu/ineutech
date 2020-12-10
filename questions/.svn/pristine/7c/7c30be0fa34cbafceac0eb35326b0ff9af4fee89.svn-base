package com.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="answer")
public class Answer {
	
	private static final long serialVersionUID = 6338161332483928760L;
	//主键
	private Integer id;
	//学生id
	private String str_id;
	//维度1
	private String dimension1;
	//维度2
	private String dimension2;
	//题号
	private String que_no;
	//正确答案
	private String true_answer;
	//学生答案
	private String answer;
	//分数
	private int score;
	//时间戳
	private Timestamp create_time;
	//备用字段1
	private String by1;
	//备用字段2
	private String by2;
	//备用字段3
	private String by3;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name="str_id",length=100)
	public void setStr_id(String str_id) {
		this.str_id = str_id;
	}
	public String getStr_id() {
		return str_id;
	}
	
	@Column(name="dimension1",length=20)
	public String getDimension1() {
		return dimension1;
	}
	public void setDimension1(String dimension1) {
		this.dimension1 = dimension1;
	}
	
	@Column(name="dimension2",length=20)
	public String getDimension2() {
		return dimension2;
	}
	public void setDimension2(String dimension2) {
		this.dimension2 = dimension2;
	}
	
	@Column(name="que_no",length=20)
	public String getQue_no() {
		return que_no;
	}
	public void setQue_no(String que_no) {
		this.que_no = que_no;
	}
	
	@Column(name="true_answer",length=100)
	public String getTrue_answer() {
		return true_answer;
	}
	public void setTrue_answer(String true_answer) {
		this.true_answer = true_answer;
	}
	
	@Column(name="answer",length=100)
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Column(name="score")
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	@Column(name="create_time")
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	
	@Column(name="by1",length=10)
	public String getBy1() {
		return by1;
	}
	public void setBy1(String by1) {
		this.by1 = by1;
	}
	
	@Column(name="by2",length=10)
	public String getBy2() {
		return by2;
	}
	public void setBy2(String by2) {
		this.by2 = by2;
	}
	
	@Column(name="by3",length=10)
	public String getBy3() {
		return by3;
	}
	public void setBy3(String by3) {
		this.by3 = by3;
	}
	
	
	
}
