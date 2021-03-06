package com.kkbc.service;

import java.util.List;

import com.kkbc.entity.Hard;
import com.kkbc.entity.TestInfo;
import com.kkbc.entity.TestLineData;
import com.kkbc.entity.TestScoreVO;
import com.kkbc.entity.TestUser;

public interface DeviceService {
	TestInfo getTestInfoById(int testId);
	
	int saveData(TestScoreVO data);
	
	TestUser getUser(TestUser user);
	
	TestUser getUserFromNow(String mac);
	
	List<TestUser> getAllTestUser();
	
	int saveHardInfo(Hard hard);
	
	int updEle(Hard hard);
	
	int updLastInfo(Hard hard);
	
	//批量更新
	int updLastInfo(List<Hard> hards);
	
	Hard getByMac(String mac);
	Hard getById(int id);
	
	//更新人名确认结果
	int updateConfirmNameFlag(TestUser testUser);
	
	//更新测试人员的设备状态
	int updateDeviceStatus(TestUser testUser);
	
	//存储拨盘结束测试时上传的本地的大包数据
	int savePackageData(TestScoreVO data);
	
	//获取测试人员
	List<TestUser> getTestUserFromBind(int testId);
	
	//获取测试设备
	List<Hard> getTestDevice(int testId);
	
	//当前测试信息添加到test_now中
	Object saveNowInfo(int testId);
	
	//从test_now中删除已结束测试的信息
	int deleteFromNow(int testId);
	
	//交换设备
	int changeDevice(Hard hard1,Hard hard2);
	
	//测试划线平均值
	int saveLineData(List<TestLineData> lineDatas);
}
