package com.ineutech.service;

import java.util.List;

import com.ineutech.entity.delivery.DeliveryLogin;
import com.ineutech.entity.delivery.DeliveryOrder;

public interface DeliveryService {
	DeliveryLogin login(String loginName,String password);
	
	int saveOrder(DeliveryOrder orderInfo);
	
	List<DeliveryOrder> getOrders(Integer deliveryman,String shop_code,String orderInfo,Integer flag,Integer pageNo);
	
	Integer orderCount(Integer deliveryman,String shop_code,String orderInfo,Integer flag);
	
	int deliveryOrder(DeliveryOrder deliveryInfo);
	
	List<DeliveryLogin> shops();
	
	//本店配送员
	List<DeliveryLogin> deliverymans(String shop_code);
	
	//更新配送员
	int updMan(DeliveryOrder deliveryInfo);
	
	int updOrder(DeliveryOrder deliveryInfo);
}
