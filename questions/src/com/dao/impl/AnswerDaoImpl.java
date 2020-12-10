package com.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.AnswerDao;
import com.entity.Answer;

public class AnswerDaoImpl extends BaseDaoImpl<Answer> implements AnswerDao {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfjq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	@Override
	public void addAnswerOne(Answer ans) {
		this.save(ans);
	}
	
	
	
	
	
	
}
