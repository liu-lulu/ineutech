package com.kkbc.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kkbc.dao.DeviceDao;
import com.kkbc.entity.DeviceFasheqi;
import com.kkbc.service.DeviceService;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.web.DeviceDataHistoryVO;
import com.kkbc.vo.web.FasheqiDataHistoryVO;
import com.kkbc.vo.web.TrucksDeviceCountVO;
import com.kkbc.vo.web.TrucksFasheqiPosVO;

@Service
public class DeviceServiceImpl implements DeviceService{

	@Resource
	private DeviceDao deviceDao;

	@Override
	public TrucksDeviceCountVO getTrucksDeviceCount(Integer company_id) {
		return deviceDao.getTrucksDeviceCount(company_id);
	}

	@Override
	public TrucksFasheqiPosVO fasheqipos(String dtu_id) {
		return deviceDao.fasheqipos(dtu_id);
	}

	@Override
	public List<DeviceDataHistoryVO> getHistoryDtuList(String keyWord,
			int pagenum, Integer company_id, Date startTime, Date endTime) {
		return deviceDao.getHistoryDtuList(keyWord, pagenum, company_id, startTime, endTime);
	}

	@Override
	public List<FasheqiDataHistoryVO> getHistoryfasheqiList(String keyWord,
			int pagenum, Integer company_id, Date startTime, Date endTime) {
		return deviceDao.getHistoryfasheqiList(keyWord, pagenum, company_id, startTime, endTime);
	}

	@Override
	public ListInfo<DeviceFasheqi> searchByFasheqilist(Integer company_id,
			String keyWord, int currentPageNO, int pageSize) {
		return deviceDao.searchByFasheqilist(company_id, keyWord, currentPageNO, pageSize);
	}

}
