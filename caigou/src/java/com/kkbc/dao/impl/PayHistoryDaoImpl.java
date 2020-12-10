package com.kkbc.dao.impl;

import com.kkbc.dao.PayHistoryDao;
import com.kkbc.entity.PayHistory;

public class PayHistoryDaoImpl extends BaseDaoImpl implements PayHistoryDao{

	@Override
	public int saveInfo(PayHistory info) {
		getSqlMapClientTemplate().insert("PayHistory.saveInfo", info);
		if (info.getPay_type()!=0) {
			getSqlMapClientTemplate().update("Order.updPayInfo", info);//更新收付款，定金状态，总金额
		}
		return 1;
	}

}
