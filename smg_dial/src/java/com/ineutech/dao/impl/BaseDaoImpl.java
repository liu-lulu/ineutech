package com.ineutech.dao.impl;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ineutech.dao.BaseDao;
import com.ineutech.vo.ExtEntity;


public class BaseDaoImpl extends SqlMapClientDaoSupport implements BaseDao{

	static final Logger logger = LoggerFactory.getLogger(BaseDao.class);
	
	@Resource(name = "sqlMapClient")
    private SqlMapClient sqlMapClient;

    @PostConstruct
    public void initSqlMapClient() {
        super.setSqlMapClient(sqlMapClient);
    }

	@Transactional
	@Override
	public long save(final Object[] paramters, String sqlCols,String tabeName) {

		ExtEntity entity = new ExtEntity();
		entity.setTableName(tabeName);
		entity.setColName(sqlCols);
		entity.setColValueList(Arrays.asList(paramters));
		long id= (long) getSqlMapClientTemplate().insert("ExtEntity.saveData", entity);
		if (id > 0) {
            logger.info("保存成功！");
		}
		return id;
	}
	
}
