package com.psylife.dao;

import java.util.Date;
import java.util.List;

import com.psylife.entity.DeviceFasheqi;
import com.psylife.util.page.ListInfo;
import com.psylife.vo.web.DeviceDataHistoryVO;
import com.psylife.vo.web.FasheqiDataHistoryVO;
import com.psylife.vo.web.TrucksDeviceCountVO;
import com.psylife.vo.web.TrucksFasheqiPosVO;

/**
 * 设备
 * @author xu
 *
 */
public interface DeviceDao extends BaseDao {

	/**
	 * 获取当前硬件数据汇总
	 * 
	 * @param company_id 车队id
	 * @return
	 */
	TrucksDeviceCountVO getTrucksDeviceCount(Integer company_id);

	/**
	 * 获取传感器分布
	 * @param dtu_id
	 * @return
	 */
	TrucksFasheqiPosVO fasheqipos(String dtu_id);

	/**
	 * dtu获取历史记录
	 * @param keyWord
	 * @param pagenum
	 * @param company_id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<DeviceDataHistoryVO> getHistoryDtuList(String keyWord, int pagenum,
			Integer company_id, Date startTime, Date endTime);

	/**
	 * 传感器历史数据记录
	 * @param keyWord
	 * @param pagenum
	 * @param company_id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<FasheqiDataHistoryVO> getHistoryfasheqiList(String keyWord,
			int pagenum, Integer company_id, Date startTime, Date endTime);

	/**
	 * 获取传感器列表
	 * @param company_id
	 * @param keyWord
	 * @param currentPageNO
	 * @param pageSize
	 * @return
	 */
	ListInfo<DeviceFasheqi> searchByFasheqilist(Integer company_id,
			String keyWord, int currentPageNO, int pageSize);
	
}
