package com.ineutech.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ineutech.cons.Constans;
import com.ineutech.dao.DeliveryDao;
import com.ineutech.entity.delivery.DeliveryLogin;
import com.ineutech.entity.delivery.DeliveryOrder;

public class DeliveryDaoImpl extends BaseDaoImpl implements DeliveryDao{

	@Override
	public DeliveryLogin login(DeliveryLogin loginInfo) {
		return (DeliveryLogin) getSqlMapClientTemplate().queryForObject("Delivery.login", loginInfo);
	}

	@Override
	public int saveOrder(DeliveryOrder orderInfo) {
		getSqlMapClientTemplate().insert("Delivery.saveOrder", orderInfo);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryOrder> getOrders(Integer deliveryman,String shop_code,String orderInfo,Integer flag,Integer pageNo) {
		Map<String, Object> params=new HashMap<>();
		params.put("deliveryman", deliveryman);
		params.put("shop_code", shop_code);
		params.put("orderInfo", orderInfo);
		params.put("flag", flag);
		if (pageNo!=null) {
			params.put("startIndex", ((pageNo-1)*Constans.PAGE_SIZE));
			params.put("pageSize", Constans.PAGE_SIZE);
		}
		return getSqlMapClientTemplate().queryForList("Delivery.order", params);
	}

	@Override
	public Integer orderCount(Integer deliveryman,String shop_code,String orderInfo,Integer flag) {
		Map<String, Object> params=new HashMap<>();
		params.put("deliveryman", deliveryman);
		params.put("shop_code", shop_code);
		params.put("orderInfo", orderInfo);
		params.put("flag", flag);
		return (Integer) getSqlMapClientTemplate().queryForObject("Delivery.orderCount", params);
	}

	@Override
	public int deliveryOrder(DeliveryOrder deliveryInfo) {
		getSqlMapClientTemplate().update("Delivery.deliveryOrder", deliveryInfo);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryLogin> shops() {
		return getSqlMapClientTemplate().queryForList("Delivery.shops");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryLogin> deliverymans(String shop_code) {
		return getSqlMapClientTemplate().queryForList("Delivery.deliverymans", shop_code);
	}

	@Override
	public int updMan(DeliveryOrder deliveryInfo) {
		getSqlMapClientTemplate().update("Delivery.updMan", deliveryInfo);
		return 1;
	}

	@Override
	public int updOrder(DeliveryOrder deliveryInfo) {
		getSqlMapClientTemplate().update("Delivery.updOrder", deliveryInfo);
		return 1;
	}

}
