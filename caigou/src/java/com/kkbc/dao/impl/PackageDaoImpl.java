package com.kkbc.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.kkbc.cons.Constans;
import com.kkbc.dao.PackageDao;
import com.kkbc.entity.PackageDetail;
import com.kkbc.entity.PackageInfo;
import com.kkbc.vo.PackageDetailVO;
import com.kkbc.vo.PackageVO;

public class PackageDaoImpl extends BaseDaoImpl implements PackageDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<PackageVO> wuliuList(int userId, int role, int pagenum) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("role", role);
		param.put("startIndex", ((pagenum-1)*Constans.PAGE_SIZE));
		param.put("pageSize", Constans.PAGE_SIZE);
		
		return getSqlMapClientTemplate().queryForList("Package.wuliuInfo", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PackageDetailVO> packageDetails(int packageId) {
		return getSqlMapClientTemplate().queryForList("Package.packageDetail", packageId);
	}

	@Override
	public int updateStatus(int packageId, int status) {
		PackageInfo packageInfo=new PackageInfo();
		packageInfo.setPackage_id(packageId);
		packageInfo.setStatus(status);
		packageInfo.setSign_time(new Date());
		
		return getSqlMapClientTemplate().update("Package.updateState", packageInfo);
	}

	@Override
	public int createPackage(PackageInfo info) {
		return (int) getSqlMapClientTemplate().insert("Package.savePackage", info);
	}

	@Override
	public int sendPackage(PackageInfo info) {
		return getSqlMapClientTemplate().update("Package.send", info);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PackageInfo> baoguoList(int userId, int pagenum) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startIndex", ((pagenum-1)*Constans.PAGE_SIZE));
		param.put("pageSize", Constans.PAGE_SIZE);
		
		return getSqlMapClientTemplate().queryForList("Package.baoguoList", param);
	}

	@Transactional
	@Override
	public int editPackageDetail(List<PackageDetail> details) {
		List<PackageDetail> saveList=new ArrayList<PackageDetail>();
		List<PackageDetail> updList=new ArrayList<PackageDetail>();
		List<PackageDetail> deleteList=new ArrayList<PackageDetail>();
		
		for (PackageDetail detail:details) {
			PackageDetail info=(PackageDetail) getSqlMapClientTemplate().queryForObject("Package.getDetailByIds", detail);
			if (info==null&&detail.getCount()!=0) {//加入包裹
				saveList.add(detail);
			}else if (info!=null) {
				detail.setPackageDetail_id(info.getPackageDetail_id());
				if (detail.getCount()==0) {//从包裹里删除
					deleteList.add(detail);
				}else if (detail.getCount()!=info.getCount()) {//更新数量
//					updList.add(detail);
					getSqlMapClientTemplate().update("Package.updCount", detail);
				}
			}
		}
		if (saveList.size()>0) {
			getSqlMapClientTemplate().insert("Package.savePackageDetail", saveList);
		}
//		if (updList.size()>0) {
//			getSqlMapClientTemplate().update("Package.updCount", updList);
//		}
		if (deleteList.size()>0) {
			getSqlMapClientTemplate().delete("Package.deleteInfo", deleteList);
		}
		
		return 1;
	}

	@Override
	public int goodsToPackage(List<PackageDetail> details) {
		List<PackageDetail> saveList=new ArrayList<PackageDetail>();
		for (PackageDetail detail:details) {
			PackageDetail info=(PackageDetail) getSqlMapClientTemplate().queryForObject("Package.getDetailByIds", detail);
			if (info==null&&detail.getCount()>0) {
				saveList.add(detail);
			}else if (info!=null) {
				detail.setPackageDetail_id(info.getPackageDetail_id());
				detail.setCount(info.getCount()+detail.getCount());
				getSqlMapClientTemplate().update("Package.updCount", detail);
			}
		}
		
		if (saveList.size()>0) {
			getSqlMapClientTemplate().insert("Package.savePackageDetail", saveList);
		}
		return 1;
	}


}
