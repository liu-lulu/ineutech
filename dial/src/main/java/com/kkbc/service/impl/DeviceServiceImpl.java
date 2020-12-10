package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kkbc.dao.DeviceDao;
import com.kkbc.entity.Hard;
import com.kkbc.entity.TestInfo;
import com.kkbc.entity.TestLineData;
import com.kkbc.entity.TestScoreVO;
import com.kkbc.entity.TestUser;
import com.kkbc.service.DeviceService;

public class DeviceServiceImpl implements DeviceService{
	
	@Resource
	private DeviceDao deviceDao;

	@Override
	public int saveData(TestScoreVO data) {
		return deviceDao.saveData(data);
	}

	@Override
	public TestUser getUser(TestUser user) {
		return deviceDao.getUser(user);
	}

	@Override
	public List<TestUser> getAllTestUser() {
		return deviceDao.getAllTestUser();
	}

	@Override
	public int saveHardInfo(Hard hard) {
		return deviceDao.saveHardInfo(hard);
	}

	@Override
	public int updEle(Hard hard) {
		return deviceDao.updEle(hard);
	}

	@Override
	public Hard getByMac(String mac) {
		return deviceDao.getByMac(mac);
	}

	@Override
	public int updateConfirmNameFlag(TestUser testUser) {
		return deviceDao.updateConfirmNameFlag(testUser);
	}

	@Override
	public int updateDeviceStatus(TestUser testUser) {
		return deviceDao.updateDeviceStatus(testUser);
	}

	@Override
	public int savePackageData(TestScoreVO data) {
		return deviceDao.savePackageData(data);
	}

	@Override
	public List<TestUser> getTestUserFromBind(int testId) {
		return deviceDao.getTestUserFromBind(testId);
	}

	@Override
	public int updLastInfo(Hard hard) {
		return deviceDao.updLastInfo(hard);
	}
	
	@Override
	public int updLastInfo(List<Hard> hards) {
		return deviceDao.updLastInfo(hards);
	}

	@Override
	public TestUser getUserFromNow(String mac) {
		return deviceDao.getUserFromNow(mac);
	}

	@Override
	public List<Hard> getTestDevice(int testId) {
		return deviceDao.getTestDevice(testId);
	}

	@Override
	public Object saveNowInfo(int testId) {
		return deviceDao.saveNowInfo(testId);
	}

	@Override
	public int deleteFromNow(int testId) {
		return deviceDao.deleteFromNow(testId);
	}

	@Override
	public TestInfo getTestInfoById(int testId) {
		return deviceDao.getTestInfoById(testId);
	}

	@Override
	public int changeDevice(Hard hard1, Hard hard2) {
		return deviceDao.changeDevice(hard1, hard2);
	}

	@Override
	public int saveLineData(List<TestLineData> lineDatas) {
		if (lineDatas!=null&&lineDatas.size()>0) {
			 deviceDao.saveLineData(lineDatas);
		}
		return 1;
	}

	@Override
	public Hard getById(int id) {
		return deviceDao.getById(id);
	}
	


}
