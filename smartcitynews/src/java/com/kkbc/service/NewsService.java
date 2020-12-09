package com.kkbc.service;

import java.util.List;

import com.kkbc.entity.News;


public interface NewsService {
	int save(News info);
	News getById(int id);
	List<News> getNews(Integer pageNo);
	
	List<News> getNewsList(Integer pageNo);
	
	int edit(News info);

}
