package com.kkbc.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kkbc.commom.SmartcityConsts;
import com.kkbc.dao.NewsDao;
import com.kkbc.entity.News;

public class NewsDaoImpl extends BaseDaoImpl implements NewsDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<News> getNews(Integer pageNo) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("startIndex", ((pageNo-1)*SmartcityConsts.PAGE_SIZE));
		param.put("pageSize", SmartcityConsts.PAGE_SIZE);
		return getSqlMapClientTemplate().queryForList("news.getNews", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> getNewsList(Integer pageNo) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("startIndex", ((pageNo-1)*SmartcityConsts.PAGE_SIZE));
		param.put("pageSize", SmartcityConsts.PAGE_SIZE);
		return getSqlMapClientTemplate().queryForList("news.getNewsList", param);
	}

	@Override
	public int save(News info) {
		getSqlMapClientTemplate().insert("news.save", info);
		return 1;
	}

	@Override
	public News getById(int id) {
		return (News) getSqlMapClientTemplate().queryForObject("news.getById", id);
	}

	@Override
	public int edit(News info) {
		getSqlMapClientTemplate().update("news.update", info);
		return 1;
	}



}
