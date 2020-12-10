package com.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<T> {
	
	/**
	 * 保存
	 * @param t
	 * @return
	 */
	public Serializable save(T t);
	/**
	 * 删除
	 * @param t
	 */
	public void delete(T t);
	
	/**
	 * 
	 * 更新
	 * @param t
	 */
	public void update(String sql,String[]params);
    /**
     * 保存或�?更新
     * @param t
     */
	public void saveOrUpdate(T t);
	
	/**
	 * 查找全部
	 * @param hql
	 * @return
	 */
	public List<T> find(String hql);
	
	/**
	 * 根据ID查找
	 * @param c
	 * @param id
	 * @return
	 */
	public T get(Class<T> c,Serializable id);
	
	/**
	 * 查找记录
	 * @param hql
	 * @return
	 */
	public Long count(String hql);
	
	/**
	 * 执行hql
	 * @param hql
	 * @return
	 */
	public Integer executeHql(String hql);
	
	/**
	 * 根据集合条件查找
	 * @param hql
	 * @param params
	 * @return
	 */
	List<T> find(String hql,Map<String,Object> params);
	 
	/**
	 * 根据集合条件查找分页
	 * @param hql
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 */
	List<T> find(String hql,Map<String, Object> params,Integer page,Integer rows);
	
	/**
	 * 根据Integer集合查找
	 * @param hql
	 * @param params
	 * @return
	 */
	List<T> find(String hql,Integer []params);
	/**
	 * 根据String集合查找
	 * @param hql
	 * @param params
	 * @return
	 */
	List<T> find(String hql,String []params);
	
	/**
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	T get(String hql,Map<String ,Object> params);
	
	Long count(String hql,Map<String, Object> params);
	
	Integer executeHql(String hql,Map<String ,Object>params);
	
	List<T> findBySQL(String sql);
	
	void deleteToUpdate(T t);
	
	List<T> findBySQL(String sql,String[] params);
	
	/*ListInfo<T> searchByMapAlias(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> inMap, Map<String, Object> startMap,
			Map<String, Object> endMap, String[] fetchNames, String orderName,
			boolean isDesc, int currentPageNO, int pageSize,
			Map<String, String> aliasMap);*/
	
}
