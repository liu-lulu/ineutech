package com.kkbc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.kkbc.dao.GoodsDao;
import com.kkbc.dao.OrderDao;
import com.kkbc.entity.Goods;
import com.kkbc.entity.Order;
import com.kkbc.entity.PackageDetail;
import com.kkbc.service.OrderService;
import com.kkbc.vo.GoodsVO;
import com.kkbc.vo.OrderDetailPackVO;
import com.kkbc.vo.OrderDetailVO;
import com.kkbc.vo.OrderVO;
import com.kkbc.vo.PackageDetailVO;
import com.kkbc.vo.PackageVO;

public class OrderServiceImpl implements OrderService{
	
	@Resource
	private OrderDao orderDao;
	
	@Resource
	private GoodsDao goodsDao;

	@Override
	public int saveInfo(List<Order> info) {
		return orderDao.saveInfo(info);
	}

	@Override
	public List<OrderVO> orderList(int userId, String brand, int role,int pagenum,Integer collectionStatus,Integer caigouStatus,Integer deliveryStatus,Integer paymentStatus,Integer supplierId) {
		return orderDao.orderList(userId, brand, role, pagenum, collectionStatus, caigouStatus, deliveryStatus, paymentStatus,supplierId);
	}

	@Override
	public OrderDetailPackVO orderDetai(int orderId) {
		OrderDetailPackVO vo=orderDao.orderDetail(orderId);
		List<PackageDetailVO> listVos=vo.getPackageDetails();
		
		List<PackageVO> packageList=new ArrayList<PackageVO>();
		
		for (PackageDetailVO detailVO : listVos) {
			int packageId=detailVO.getPackage_id();
			PackageVO packageInfo = null;
			for (PackageVO packageVO : packageList) {
				if (packageVO.getPackage_id()==packageId) {
					packageInfo=packageVO;
					break;
				}
			}
			if (packageInfo==null) {
				packageInfo=new PackageVO();
				packageInfo.setPackage_id(detailVO.getPackage_id());
				packageInfo.setExpressNo(detailVO.getExpressNo());
				packageInfo.setCreate_time(detailVO.getCreate_time());
				packageInfo.setSend_time(detailVO.getSend_time());
				packageInfo.setSign_time(detailVO.getSign_time());
				packageInfo.setStatus(detailVO.getStatus());
				List<PackageDetail> details=new ArrayList<PackageDetail>();
				packageInfo.setDetails(details);
				packageList.add(packageInfo);
			}
			PackageDetail detail=new PackageDetail();
			detail.setCount(detailVO.getCount());
			detail.setPurchase_price(detailVO.getPurchase_price());
			packageInfo.getDetails().add(detail);
		}
		vo.setPackageInfo(packageList);
		vo.setPackageDetails(null);
		return vo;
	}

	@Override
	public List<GoodsVO> goodsList(int userId, String brand,
			Date startTime, Date endTime, int pagenum) {
		List<OrderVO> goodsInfoList=orderDao.goodsList(userId, brand, startTime, endTime, pagenum);
//		Map<String, List<OrderVO>> map=null;
		List<GoodsVO> info=null;
		if (goodsInfoList!=null&goodsInfoList.size()>0) {
//			map=new LinkedHashMap<String, List<OrderVO>>();
//			goodsListToMap(map, goodsInfoList);
			
			info=new ArrayList<GoodsVO>();
			for (OrderVO orderVO:goodsInfoList) {
				boolean exist=false;
				for (GoodsVO goodsVO : info) {
					if (goodsVO.getBrand().equals(orderVO.getBrand())&&goodsVO.getModel().equals(orderVO.getModel())) {
						exist=true;
						goodsVO.getGoodsInfo().add(new OrderDetailVO(orderVO.getSupplierName(), orderVO.getBoughtCount(), orderVO.getPurchase_price()));
						break;
					}
				}
				if (!exist) {
					List<OrderDetailVO> caigouList=new ArrayList<OrderDetailVO>();
					caigouList.add(new OrderDetailVO(orderVO.getSupplierName(), orderVO.getBoughtCount(), orderVO.getPurchase_price()));
					GoodsVO goodsVO=new GoodsVO(orderVO.getBrand(), orderVO.getModel(), orderVO.getImg(),caigouList);
					info.add(goodsVO);
				}
			}
		}
		return info;
	}
	
	public Map<String, List<OrderVO>> goodsListToMap(Map<String, List<OrderVO>> map,List<OrderVO> goodsInfoList){
		for (OrderVO orderVO:goodsInfoList) {
			boolean exist=false;
			for (String goodsKey : map.keySet()) {
				JSONObject jsonObject=JSONObject.fromObject(goodsKey);
				Goods goods=(Goods) JSONObject.toBean(jsonObject, Goods.class);
				if (goods.getBrand().equals(orderVO.getBrand())&goods.getModel().equals(orderVO.getModel())) {
					exist=true;
					map.get(goodsKey).add(orderVO);
					break;
				}
			}
			if (!exist) {
				String jsonValue = "{\"brand\":\""+orderVO.getBrand()+"\",\"model\":\""+orderVO.getModel()+"\",\"img\":\""+orderVO.getImg()+"\"}";
				Goods goods=new Goods(orderVO.getBrand(),orderVO.getModel(),orderVO.getImg());
				JSONObject jsonObject1=JSONObject.fromObject(goods);
				List<OrderVO> orderVOs=new ArrayList<OrderVO>();
				orderVOs.add(orderVO);
				map.put(jsonObject1.toString(), orderVOs);
			}
		}
		
		return map;
	}

	@Override
	public int caigou(int orderId, int count, float purchase_price) {
		return orderDao.caigou(orderId, count, purchase_price);
	}

	@Override
	public List<OrderDetailVO> remainCount(int orderId) {
		return orderDao.remainCount(orderId);
	}

	@Override
	public OrderVO delById(int orderId) {
		OrderVO info=orderDao.delById(orderId);
		if (StringUtils.isNotEmpty(info.getImg())) {
			List<Goods> goods=goodsDao.get(new Goods(null, null, info.getImg()));
			if (goods==null||goods.size()==0) {
//				String imgpath = Constans.IMG+info.getImg();
//				File imgFile=new File(imgpath);
//				imgFile.delete();
//				QiniuUtil.delFile(info.getImg());
			}
		}
		return info;
	}

}
