package com.ineutech.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ineutech.dao.DeliveryDao;
import com.ineutech.entity.delivery.DeliveryLogin;
import com.ineutech.entity.delivery.DeliveryOrder;
import com.ineutech.service.DeliveryService;

public class DeliveryServiceImpl implements DeliveryService{
	
	@Resource
	private DeliveryDao deliveryDao;

	@Override
	public DeliveryLogin login(String loginName,String password) {
		return deliveryDao.login(new DeliveryLogin(loginName, password));
	}

	@Override
	public int saveOrder(DeliveryOrder orderInfo) {
		return deliveryDao.saveOrder(orderInfo);
	}

	@Override
	public List<DeliveryOrder> getOrders(Integer deliveryman,String shop_code,String orderInfo,Integer flag,Integer pageNo) {
		return deliveryDao.getOrders(deliveryman,shop_code,orderInfo,flag,pageNo);
	}

	@Override
	public Integer orderCount(Integer deliveryman,String shop_code,String orderInfo,Integer flag) {
		return deliveryDao.orderCount(deliveryman,shop_code,orderInfo,flag);
	}

	@Override
	public int deliveryOrder(DeliveryOrder deliveryInfo) {
		return deliveryDao.deliveryOrder(deliveryInfo);
	}

	@Override
	public List<DeliveryLogin> shops() {
		return deliveryDao.shops();
	}

	@Override
	public List<DeliveryLogin> deliverymans(String shop_code) {
		return deliveryDao.deliverymans(shop_code);
	}

	@Override
	public int updMan(DeliveryOrder deliveryInfo) {
		return deliveryDao.updMan(deliveryInfo);
	}

	@Override
	public int updOrder(DeliveryOrder deliveryInfo) {
		return deliveryDao.updOrder(deliveryInfo);
	}

}
