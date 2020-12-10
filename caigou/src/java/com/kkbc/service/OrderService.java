package com.kkbc.service;

import java.util.Date;
import java.util.List;

import com.kkbc.entity.Order;
import com.kkbc.vo.GoodsVO;
import com.kkbc.vo.OrderDetailPackVO;
import com.kkbc.vo.OrderDetailVO;
import com.kkbc.vo.OrderVO;

public interface OrderService {
	int saveInfo(List<Order> info);
	
	//采购列表
	List<OrderVO> orderList(int userId,String brand,int role,int pagenum,Integer collectionStatus,Integer caigouStatus,Integer deliveryStatus,Integer paymentStatus,Integer supplierId);
	
	//采购详情
	OrderDetailPackVO orderDetai(int orderId);
	
	List<GoodsVO> goodsList(int userId,String brand,Date startTime,Date endTime,int pagenum);
	
	int caigou(int orderId,int count,float purchase_price);
	
	//剩余的可以加入包裹的商品数量信息
	List<OrderDetailVO> remainCount(int orderId);
	
	//管理员删除下单信息
	OrderVO delById(int orderId);

}
