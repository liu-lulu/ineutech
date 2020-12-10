package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kkbc.dao.DrivingRecordDao;
import com.kkbc.entity.DrivingRecord;
import com.kkbc.service.DrivingRecordService;
import com.kkbc.vo.DrivingRecordVO;
import com.kkbc.vo.UserDrivingRecordCountVO;
 
public class DrivingRecordServiceImpl implements DrivingRecordService{
	
	@Resource
	private DrivingRecordDao drivingRecordDao;
	

	@Override
	public DrivingRecord getLastDrivingRecord(Integer user_id) {
		return drivingRecordDao.getLastDrivingRecord(user_id);
	}
	
	@Override
	public DrivingRecordVO getLastDrivingRecordByTrucks_id(String trucks_id,
			boolean isMain) {
		return drivingRecordDao.getLastDrivingRecordByTrucks_id(trucks_id, isMain);
	}

	@Override
	public List<DrivingRecord> getDrivingRecordList(int pagenum, Integer user_id) {
		return drivingRecordDao.getDrivingRecordList(pagenum, user_id);
	}

	@Override
	public int startDriving(DrivingRecord drivingRecord) {
		return drivingRecordDao.startDriving(drivingRecord);
	}

	@Override
	public int endDriving(DrivingRecord drivingRecord) {
		return drivingRecordDao.endDriving(drivingRecord);
	}

	@Override
	public int scoreDriving(DrivingRecord drivingRecord) {
		return drivingRecordDao.scoreDriving(drivingRecord);
	}

	@Override
	public UserDrivingRecordCountVO getDrivingRecordCount(Integer user_id) {
		return drivingRecordDao.getDrivingRecordCount(user_id);
	}

	
	
}
