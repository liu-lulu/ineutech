package com.kkbc.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kkbc.cons.Constans;
import com.kkbc.dao.OrderDao;
import com.kkbc.entity.Order;
import com.kkbc.entity.OrderDetail;
import com.kkbc.entity.PayHistory;
import com.kkbc.vo.OrderDetailPackVO;
import com.kkbc.vo.OrderDetailVO;
import com.kkbc.vo.OrderVO;
import com.kkbc.vo.PackageDetailVO;

@Repository
public class OrderDaoImpl extends BaseDaoImpl implements OrderDao{

	@Override
	public int saveInfo(List<Order> info) {
		getSqlMapClientTemplate().insert("Order.saveInfo", info);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVO> orderList(int userId, String brand, int role,int pagenum,Integer collectionStatus,Integer caigouStatus,Integer deliveryStatus,Integer paymentStatus,Integer supplierId) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("brand", brand);
		param.put("role", role);
		param.put("startIndex", ((pagenum-1)*Constans.PAGE_SIZE));
		param.put("pageSize", Constans.PAGE_SIZE);
		param.put("collectionStatus", collectionStatus);
		param.put("caigouStatus", caigouStatus);
		param.put("deliveryStatus", deliveryStatus);
		param.put("paymentStatus", paymentStatus);
		param.put("supplierId", supplierId);
		
		return getSqlMapClientTemplate().queryForList("Order.orderList", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderDetailPackVO orderDetail(int orderId) {
		OrderDetailPackVO orderDetailPackVO=new OrderDetailPackVO();
		orderDetailPackVO.setOrderInfo((OrderVO)getSqlMapClientTemplate().queryForObject("Order.orderInfoById", orderId));
		orderDetailPackVO.setOrderDetails(getSqlMapClientTemplate().queryForList("Order.orderDetailById", orderId));
		List<PackageDetailVO> packageDetailVOs=getSqlMapClientTemplate().queryForList("Order.packageDetailByOrderId", orderId);
//		Map<Integer, List<PackageDetailVO>> map=new HashMap<Integer, List<PackageDetailVO>>();
//		
//		for (PackageDetailVO packageDetailVO:packageDetailVOs) {
//			if (map.get(packageDetailVO.getPackage_id())!=null) {
//				map.get(packageDetailVO.getPackage_id()).add(packageDetailVO);
//			}else {
//				List<PackageDetailVO> list=new ArrayList<PackageDetailVO>();
//				list.add(packageDetailVO);
//				map.put(packageDetailVO.getPackage_id(), list);
//			}
//		}
//		orderDetailPackVO.setPackMap(map);
		orderDetailPackVO.setPayDetails(getSqlMapClientTemplate().queryForList("PayHistory.getByType", new PayHistory(orderId, PayHistory.PAY_TYPE, null, null, null)));
		orderDetailPackVO.setCollectionDetails(getSqlMapClientTemplate().queryForList("PayHistory.getByType", new PayHistory(orderId, PayHistory.CONNECTION_TYPE, null, null, null)));
		orderDetailPackVO.setPackageDetails(packageDetailVOs);
		return orderDetailPackVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderVO> goodsList(int userId, String brand, Date startTime,
			Date endTime, int pagenum) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("brand", brand);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("startIndex", ((pagenum-1)*Constans.PAGE_SIZE));
		param.put("pageSize", Constans.PAGE_SIZE);
		
		return getSqlMapClientTemplate().queryForList("Order.goodsList", param);
	}

	@Override
	public int caigou(int orderId, int count, float purchase_price) {
		OrderDetail param=new OrderDetail();
		param.setOrder_id(orderId);
		param.setCount(count);
		param.setPurchase_price(purchase_price);
		
		OrderVO orderVO=(OrderVO) getSqlMapClientTemplate().queryForObject("Order.orderInfoById", orderId);
		if (count+orderVO.getBoughtCount()>orderVO.getNum()) {
			return 2;
		}
		
		getSqlMapClientTemplate().insert("Order.caigou", param);
		
//		OrderDetail info=(OrderDetail) getSqlMapClientTemplate().queryForObject("Order.getByCaigouPrice", param);
//		if (info!=null) {//同一采购价格的只需更新数量
//			param.setOrder_detail_id(info.getOrder_detail_id());
//			getSqlMapClientTemplate().update("Order.updCaigouNum", param);
//		}else {//第一次采购该价钱时插入
//			getSqlMapClientTemplate().insert("Order.caigou", param);
//		}
		
		return 1 ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDetailVO> remainCount(int orderId) {
		return getSqlMapClientTemplate().queryForList("Order.remainCount", orderId);
	}

	@Override
	public OrderVO delById(int orderId) {
		OrderVO info=(OrderVO) getSqlMapClientTemplate().queryForObject("Order.orderInfoById", orderId);
		if (info!=null) {
			getSqlMapClientTemplate().delete("Order.delById", orderId);
		}
		
		return info;
	}

}
