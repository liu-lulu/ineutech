package com.kkbc.dao.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.kkbc.dao.BaseDao;


 
public class BaseDaoImpl extends SqlMapClientDaoSupport implements BaseDao{

	static final Logger logger = LoggerFactory.getLogger(BaseDao.class);
	
	@Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @PostConstruct
    public void initSqlMapClient() {
        super.setSqlMapClient(sqlMapClient);
    }

	
}
