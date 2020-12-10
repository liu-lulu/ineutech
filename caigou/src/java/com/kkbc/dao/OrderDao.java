package com.kkbc.dao;

import java.util.Date;
import java.util.List;

import com.kkbc.entity.Order;
import com.kkbc.vo.OrderDetailPackVO;
import com.kkbc.vo.OrderDetailVO;
import com.kkbc.vo.OrderVO;


public interface OrderDao {
	
	int saveInfo(List<Order> info);
	
	List<OrderVO> orderList(int userId,String brand,int role,int pagenum,Integer collectionStatus,Integer caigouStatus,Integer deliveryStatus,Integer paymentStatus,Integer supplierId);
	
	OrderDetailPackVO orderDetail(int orderId);
	
	List<OrderVO> goodsList(int userId,String brand,Date startTime,Date endTime,int pagenum);
	
	int caigou(int orderId,int count,float purchase_price);
	
	List<OrderDetailVO> remainCount(int orderId);
	
	OrderVO delById(int orderId);

}
