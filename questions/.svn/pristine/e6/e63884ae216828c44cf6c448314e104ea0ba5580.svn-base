package com.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;

import com.dao.BaseDao;

/*@SuppressWarnings("unchecked")
@Repository("baseDao")*/
public class BaseDaoImpl<T>  implements BaseDao<T> {

	private SessionFactory sessionFactory;
	
	private Class<T> entityClass;
	
	private Map<String, String> annotationMap;
	
	protected String entityID;
	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	//@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
    
	private Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
	}
	@Override
	public Serializable save(T t) {
		return this.getCurrentSession().save(t);	
	}

	@Override
	public void delete(T t) {
		this.getCurrentSession().delete(t);
		
	}

	@Override
	public void update(String hql, String[]params) {
		Query query = this.getCurrentSession().createQuery(hql);
		//query.setParameter(0, params[0]).setParameter(1, params[1]);
		query.setString(0, params[0]);
		query.setInteger(1, Integer.valueOf(params[1]));
		query.executeUpdate();
	}

	@Override
	public void saveOrUpdate(T t) {
		this.getCurrentSession().saveOrUpdate(t);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(Class<T> c, Serializable id) {
		return (T)this.getCurrentSession().get(c, id);
	}

	@Override
	public Long count(String hql) {
		return (Long)this.getCurrentSession().createQuery(hql).uniqueResult();
	}

	@Override
	public Integer executeHql(String hql) {
		return this.getCurrentSession().createQuery(hql).executeUpdate();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query query=this.getCurrentSession().createQuery(hql);
		if(params!=null&&!params.isEmpty()){
			for(String key:params.keySet()){
				query.setParameter(key, params.get(key));
			}
		}
		return query.list();
	}

	@Override
	public List<T> find(String hql, Map<String, Object> params, Integer page,
			Integer rows) {
		if(page==null||page<1){
			page=1;
		}
		if(rows==null||rows<1){
			rows=5;
		}
		Query q=this.getCurrentSession().createQuery(hql);
		if(params!=null && !params.isEmpty()){
			for(String key:params.keySet()){
				q.setParameter(key, params.get(key));
			}
			
		}
		return q.setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}

	@Override
	public List<T> find(String hql, Integer[] params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if(params!=null && params.length>0){
			for(int i=0;i<params.length;i++){
				q.setInteger(i, params[i]);
			}
		}
		return q.list();
	}

	@Override
	public List<T> find(String hql, String[] params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if(params!=null && params.length>0){
			for(int i=0;i<params.length;i++){
				q.setString(i, params[i]);
			}
		}
		return q.list();
	}

	@Override
	public Long count(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if(params != null && !params.isEmpty()){
        	for(String key:params.keySet()){
        		q.setParameter(key, params.get(key));
        	}
        }
		return (Long)q.uniqueResult();
	}

	@Override
	public Integer executeHql(String hql, Map<String, Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if(params != null && !params.isEmpty()){
			for(String key:params.keySet()){
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	@Override
	public List<T> findBySQL(String sql) {
		return this.getCurrentSession().createSQLQuery(sql).list();
	}

	@Override
	public void deleteToUpdate(T t) {
		this.getCurrentSession().update(t);
	}

	@Override
	public List<T> findBySQL(String sql, String[] params) {
		Query q = this.getCurrentSession().createSQLQuery(sql);
		if(params != null && params.length>0){
			for(int i=0;i<params.length;i++){
				q.setString(i, params[i]);
			}
		}
		return q.list();
	}


	@Override
	public T get(String hql, Map<String, Object> params) {
		List<T> list = this.find(hql, params);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByMapAlias(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> inMap, Map<String, Object> startMap,
			Map<String, Object> endMap, String[] fetchNames, String orderName,
			boolean isDesc, int start, int limit, Map<String, String> aliasMap) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		if (fetchNames != null) {
			for (String fetchName : fetchNames) {
				criteria = criteria.setFetchMode(fetchName, FetchMode.JOIN);
			}
		}
		if (aliasMap != null) {
			for (String key : aliasMap.keySet()) {
				if (aliasMap.get(key) != null) {
					criteria = criteria.createAlias(key, aliasMap.get(key));
				} else {
					criteria = criteria.createAlias(key, key);
				}

			}
		}
		if (equalMap != null) {
			for (String key : equalMap.keySet()) {
				if (equalMap.get(key) != null) {
					criteria = criteria.add(Restrictions.eq(key,
							equalMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNull(key));
				}

			}
		}
		if (notEqualMap != null) {
			for (String key : notEqualMap.keySet()) {
				if (notEqualMap.get(key) != null) {
					criteria = criteria.add(Restrictions.ne(key,
							notEqualMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNotNull(key));
				}
			}
		}
		if (likeMap != null) {
			for (String key : likeMap.keySet()) {
				if ((likeMap.get(key) instanceof String)
						|| annotationMap.get(key) == null) {
					criteria = criteria.add(Restrictions.like(key,
							likeMap.get(key).toString(), MatchMode.ANYWHERE));
				} else if (likeMap.get(key) instanceof Long
						|| likeMap.get(key) instanceof Integer) {
					criteria = criteria.add(Restrictions.sqlRestriction(
							"this_." + annotationMap.get(key) + " like(?)", "%"
									+ likeMap.get(key) + "%",
							StringType.INSTANCE));
				}

			}
		}
		if (inMap != null) {
			for (String key : inMap.keySet()) {
				criteria = criteria.add(Restrictions.in(key, inMap.get(key)));
			}
		}
		if (startMap != null) {
			for (String key : startMap.keySet()) {
				criteria = criteria
						.add(Restrictions.ge(key, startMap.get(key)));
			}
		}
		if (endMap != null) {
			for (String key : endMap.keySet()) {
				criteria = criteria.add(Restrictions.le(key, endMap.get(key)));
			}
		}

		if (orderName != null) {
			if (isDesc) {
				criteria = criteria.addOrder(Order.desc(orderName));
			} else {
				criteria = criteria.addOrder(Order.asc(orderName));
			}
		}
		return (List<T>) criteria.setFirstResult(start).setMaxResults(limit)
				.list();
	}
	
	@Override
	public int countByMapAlias(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> inMap, Map<String, Object> startMap,
			Map<String, Object> endMap, String[] fetchNames,
			Map<String, String> aliasMap) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass,
				entityClass.getSimpleName().toLowerCase());
		if (fetchNames != null) {
			for (String fetchName : fetchNames) {
				criteria = criteria.setFetchMode(fetchName, FetchMode.JOIN);
			}
		}
		if (aliasMap != null) {
			for (String key : aliasMap.keySet()) {
				if (aliasMap.get(key) != null) {
					criteria = criteria.createAlias(key, aliasMap.get(key));
				} else {
					criteria = criteria.createAlias(key, key);
				}

			}
		}
		if (equalMap != null) {
			for (String key : equalMap.keySet()) {
				if (equalMap.get(key) != null) {
					criteria = criteria.add(Restrictions.eq(key,
							equalMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNull(key));
				}

			}
		}
		if (notEqualMap != null) {
			for (String key : notEqualMap.keySet()) {
				if (notEqualMap.get(key) != null) {
					criteria = criteria.add(Restrictions.ne(key,
							notEqualMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNotNull(key));
				}
			}
		}
		if (likeMap != null) {
			for (String key : likeMap.keySet()) {
				if ((likeMap.get(key) instanceof String)
						|| annotationMap.get(key) == null) {
					criteria = criteria.add(Restrictions.like(key,
							likeMap.get(key).toString(), MatchMode.ANYWHERE));
				} else if (likeMap.get(key) instanceof Long
						|| likeMap.get(key) instanceof Integer) {
					criteria = criteria.add(Restrictions.sqlRestriction(
							"this_." + annotationMap.get(key) + " like(?)", "%"
									+ likeMap.get(key) + "%",
							StringType.INSTANCE));
				}
			}
		}
		if (inMap != null) {
			for (String key : inMap.keySet()) {
				criteria = criteria.add(Restrictions.in(key, inMap.get(key)));
			}
		}
		if (startMap != null) {
			for (String key : startMap.keySet()) {
				criteria = criteria
						.add(Restrictions.ge(key, startMap.get(key)));
			}
		}
		if (endMap != null) {
			for (String key : endMap.keySet()) {
				criteria = criteria.add(Restrictions.le(key, endMap.get(key)));
			}
		}
		Object object = criteria.setProjection(
				Projections.countDistinct(entityID)).uniqueResult();
		return object == null ? 0 : ((Long) object).intValue();
	}
	@Override
	public int update(String sql) {
		return this.getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	@Override
	public int delete(String sql) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		return query.executeUpdate();
	}
	@Override
	public void update(T t) {
			this.getCurrentSession().update(t);		
		}
	@Override
	public List<T> queryListObjectAllForPage(String queryString,int page, int pageSize
			) {
		Query query = this.getCurrentSession().createQuery(queryString);
		query.setFirstResult((page-1)*pageSize); 
		query.setMaxResults(pageSize);
		return (List<T>)query.list();
	}
	
}
