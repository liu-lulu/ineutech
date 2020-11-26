package com.ineutech.dao;

import java.util.List;

import com.ineutech.entity.delivery.DeliveryLogin;
import com.ineutech.entity.delivery.DeliveryOrder;

public interface DeliveryDao {
	DeliveryLogin login(DeliveryLogin loginInfo);
	
	int saveOrder(DeliveryOrder orderInfo);
	
	List<DeliveryOrder> getOrders(Integer deliveryman,String shop_code,String orderInfo,Integer flag,Integer pageNo);
	
	Integer orderCount(Integer deliveryman,String shop_code,String orderInfo,Integer flag);
	
	int deliveryOrder(DeliveryOrder deliveryInfo);
	
	List<DeliveryLogin> shops();
	List<DeliveryLogin> deliverymans(String shop_code);
	
	int updMan(DeliveryOrder deliveryInfo);
	
	int updOrder(DeliveryOrder deliveryInfo);

}
