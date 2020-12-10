package com.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.AnswerDao;
import com.entity.Answer;
import com.service.AnswerService;

public class AnswerServiceImpl extends BaseServiceImpl<Answer> implements AnswerService {
	
	private AnswerDao answerDao;  

	@Override
	public void addAnswerOne(Answer ans) {
		answerDao.addAnswerOne(ans);
	}

	public AnswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(AnswerDao answerDao) {
		this.answerDao = answerDao;
	}
	
	


}
