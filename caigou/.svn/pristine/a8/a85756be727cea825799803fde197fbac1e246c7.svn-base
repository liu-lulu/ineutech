package com.kkbc.dao;

import java.util.List;

import com.kkbc.entity.PackageDetail;
import com.kkbc.entity.PackageInfo;
import com.kkbc.vo.PackageDetailVO;
import com.kkbc.vo.PackageVO;

public interface PackageDao {
	
	List<PackageVO> wuliuList(int userId,int role,int pagenum);
	
	List<PackageDetailVO> packageDetails(int packageId);
	
	int updateStatus(int packageId,int status);
	
	int createPackage(PackageInfo info);
	
	int sendPackage(PackageInfo info);
	
	List<PackageInfo> baoguoList(int userId,int pagenum);
	
	int editPackageDetail(List<PackageDetail> details);
	
	int goodsToPackage(List<PackageDetail> details);

}
