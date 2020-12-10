package com.psylife.service.impl;

import java.util.List;

import com.psylife.dao.DrivingRecordDao;
import com.psylife.dao.impl.DrivingRecordDaoImpl;
import com.psylife.entity.DrivingRecord;
import com.psylife.service.DrivingRecordService;
import com.psylife.vo.DrivingRecordVO;
import com.psylife.vo.UserDrivingRecordCountVO;
 
public class DrivingRecordServiceImpl extends BaseServiceImpl implements DrivingRecordService{
	
	private DrivingRecordDao dao=(DrivingRecordDao)new DrivingRecordDaoImpl();

	@Override
	public DrivingRecord getLastDrivingRecord(Integer user_id) {
		return dao.getLastDrivingRecord(user_id);
	}
	
	@Override
	public DrivingRecordVO getLastDrivingRecordByTrucks_id(String trucks_id,
			boolean isMain) {
		return dao.getLastDrivingRecordByTrucks_id(trucks_id, isMain);
	}

	@Override
	public List<DrivingRecord> getDrivingRecordList(int pagenum, Integer user_id) {
		return dao.getDrivingRecordList(pagenum, user_id);
	}

	@Override
	public int startDriving(DrivingRecord drivingRecord) {
		return dao.startDriving(drivingRecord);
	}

	@Override
	public int endDriving(DrivingRecord drivingRecord) {
		return dao.endDriving(drivingRecord);
	}

	@Override
	public int scoreDriving(DrivingRecord drivingRecord) {
		return dao.scoreDriving(drivingRecord);
	}

	@Override
	public UserDrivingRecordCountVO getDrivingRecordCount(Integer user_id) {
		return dao.getDrivingRecordCount(user_id);
	}

	@Override
	public List<DrivingRecordVO> getDrivingRecordListByTrucksId(int pagenum,
			String trucksId) {
		return dao.getDrivingRecordListByTrucksId(pagenum, trucksId);
	}

	
	
}
