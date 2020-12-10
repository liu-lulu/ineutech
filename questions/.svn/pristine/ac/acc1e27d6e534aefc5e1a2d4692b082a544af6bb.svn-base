package com.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.dao.BaseDao;
import com.service.BaseService;

@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {

	private BaseDao<T> baseDao;

	@Override
	public Serializable save(T t) {
		return baseDao.save(t);
	}

	@Override
	public void delete(T t) {
		baseDao.delete(t);		
	}

	@Override
	public void update(String sql,String[]params) {
		 baseDao.update(sql,params);
	}

	@Override
	public void saveOrUpdate(T t) {
		baseDao.saveOrUpdate(t);
	}

	@Override
	public List<T> find(String hql) {
		return baseDao.find(hql);
	}

	@Override
	public T get(Class<T> c, Serializable id) {
		return baseDao.get(c, id);
	}

	@Override
	public Long count(String hql) {
		return baseDao.count(hql);
	}

	@Override
	public Integer executeHql(String hql) {
		return baseDao.executeHql(hql);
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		return baseDao.find(hql, params);
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, Integer page,
			Integer rows) {
		return baseDao.find(hql, params, page, rows);
	}

	@Override
	public List<T> find(String hql, Integer[] params) {
		return baseDao.find(hql, params);
	}

	@Override
	public List<T> find(String hql, String[] params) {
		return baseDao.find(hql, params);
	}

	@Override
	public T get(String hql, Map<String, Object> params) {
		return baseDao.get(hql, params);
	}

	@Override
	public Long count(String hql, Map<String, Object> params) {
		return baseDao.count(hql, params);
	}

	@Override
	public Integer executeHql(String hql, Map<String, Object> params) {
		return baseDao.executeHql(hql, params);
	}

	@Override
	public List<T> findBySQL(String sql) {
		return baseDao.findBySQL(sql);
	}

	@Override
	public void deleteToUpdate(T t) {
		baseDao.deleteToUpdate(t);
	}

	@Override
	public List<T> findBySQL(String sql, String[] params) {
		return baseDao.findBySQL(sql, params);
	}
	
	
/*	@Override
	public ListInfo<T> searchByMapAlias(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> inMap, Map<String, Object> startMap,
			Map<String, Object> endMap, String[] fetchNames, String orderName,
			boolean isDesc, int currentPageNO, int pageSize,
			Map<String, String> aliasMap) {
		ListInfo<T> listInfo = new ListInfo<T>(currentPageNO, pageSize);
		listInfo.setCurrentList(baseDao.findByMapAlias(equalMap, notEqualMap,
				likeMap, inMap, startMap, endMap, fetchNames, orderName,
				isDesc, listInfo.getFirst(), pageSize, aliasMap));
		listInfo.setSizeOfTotalList(baseDao.countByMapAlias(equalMap,
				notEqualMap, likeMap, inMap, startMap, endMap, fetchNames,
				aliasMap));
		return listInfo;
	}*/

	public BaseDao<T> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}
	
}
