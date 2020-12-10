package com.kkbc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.kkbc.dao.OrderDao;
import com.kkbc.dao.PackageDao;
import com.kkbc.entity.PackageDetail;
import com.kkbc.entity.PackageInfo;
import com.kkbc.service.PackageService;
import com.kkbc.vo.OrderDetailVO;
import com.kkbc.vo.PackageDetailVO;
import com.kkbc.vo.PackageVO;

public class PackageServiceImpl implements PackageService{
	
	@Resource
	private PackageDao packageDao;
	
	@Resource
	private OrderDao orderDao;

	@Override
	public List<PackageVO> wuliuList(int userId, int role, int pagenum) {
		return packageDao.wuliuList(userId, role, pagenum);
	}

	@Override
	public List<PackageDetailVO> packageDetails(int packageId) {
		return packageDao.packageDetails(packageId);
	}

	@Override
	public int updateStatus(int packageId, int status) {
		return packageDao.updateStatus(packageId, status);
	}

	@Override
	public int createPackage(PackageInfo info) {
		return packageDao.createPackage(info);
	}

	@Override
	public int sendPackage(PackageInfo info) {
		return packageDao.sendPackage(info);
	}

	@Override
	public List<PackageInfo> baoguoList(int userId, int pagenum) {
		return packageDao.baoguoList(userId, pagenum);
	}

	@Override
	public int editPackageDetail(List<PackageDetail> details) {
		return packageDao.editPackageDetail(details);
	}

	@Override
	public int goodsToPackage(List<PackageDetail> details) {
		return packageDao.goodsToPackage(details);
	}

	@Override
	public int oneKeyToPack(Integer orderId, Integer packageId) {
		List<PackageDetail> inDetails=new ArrayList<PackageDetail>();
		List<OrderDetailVO> remainDetails=orderDao.remainCount(orderId);
		if (remainDetails==null||remainDetails.size()==0) {//没有剩余的商品
			return 2;
		}else {
			for (OrderDetailVO orderDetailVO : remainDetails) {
				PackageDetail packageDetail=new PackageDetail(packageId, orderDetailVO.getOrder_id(), orderDetailVO.getOrder_detail_id(), orderDetailVO.getBrand(), orderDetailVO.getModel(), orderDetailVO.getRemainCount(), orderDetailVO.getPurchase_price());
				inDetails.add(packageDetail);
			}
		}
		
		return packageDao.goodsToPackage(inDetails);
	}

}
