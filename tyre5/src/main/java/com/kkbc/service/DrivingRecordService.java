package com.kkbc.service;

import java.util.List;

import com.kkbc.entity.DrivingRecord;
import com.kkbc.vo.DrivingRecordVO;
import com.kkbc.vo.UserDrivingRecordCountVO;
 
public interface DrivingRecordService {
	
	/**
	 * 获取最近一次驾驶记录
	 * @param user_id
	 * @return
	 */
	DrivingRecord getLastDrivingRecord(Integer user_id);
	
	/**
	 * 获取车最近一条驾驶记录
	 * @param trucks_id
	 * @param isMain
	 * @return
	 */
	DrivingRecordVO getLastDrivingRecordByTrucks_id(String trucks_id,
			boolean isMain);
	
	/**
	 * 根据用户获取架驶记录列表
	 * @param pagenum
	 * @param user_id
	 * @return
	 */
	List<DrivingRecord> getDrivingRecordList(int pagenum, Integer user_id);
	
	/**
	 * 开始架驶
	 * @param drivingRecord
	 * @return
	 */
	int startDriving(DrivingRecord drivingRecord);
	
	/**
	 * 结束架驶
	 * @param drivingRecord
	 * @return
	 */
	int endDriving(DrivingRecord drivingRecord);
	
	/**
	 * 架驶员评分
	 * @param drivingRecord
	 * @return
	 */
	int scoreDriving(DrivingRecord drivingRecord);
	
	/**
	 * 获取驾驶员驾驶记录汇总
	 * @param user_id
	 * @return
	 */
	UserDrivingRecordCountVO getDrivingRecordCount(Integer user_id);
	
}
