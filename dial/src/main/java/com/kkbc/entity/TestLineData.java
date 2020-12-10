package com.kkbc.entity;

import java.util.Date;

public class TestLineData {
	
	public static final int KEY_MAN=1;
	public static final int KEY_WOMAN=2;
	public static final int KEY_AVG=3;
	
	private int id;
	private Date create_time;
	private float score;
	private int test_id;
	private int key;
	private int count;
	
	public TestLineData(int testId,float score){
		this.test_id=testId;
		this.score=score;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}

	public int getTest_id() {
		return test_id;
	}
	public void setTest_id(int test_id) {
		this.test_id = test_id;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	

}
