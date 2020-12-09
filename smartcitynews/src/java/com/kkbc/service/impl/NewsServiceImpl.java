package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;



import com.kkbc.dao.NewsDao;
import com.kkbc.entity.News;
import com.kkbc.service.NewsService;

public class NewsServiceImpl implements NewsService{
	
	@Resource
	private NewsDao newsDao;

	@Override
	public List<News> getNews(Integer pageNo) {
		return newsDao.getNews(pageNo);
	}

	@Override
	public List<News> getNewsList(Integer pageNo) {
		return newsDao.getNewsList(pageNo);
	}

	@Override
	public int save(News info) {
		return newsDao.save(info);
	}

	@Override
	public News getById(int id) {
		return newsDao.getById(id);
	}

	@Override
	public int edit(News info) {
		return newsDao.edit(info);
	}


}
