package com.psylife.service.impl;

import java.util.Date;
import java.util.List;

import com.psylife.dao.DeviceDao;
import com.psylife.dao.impl.DeviceDaoImpl;
import com.psylife.entity.DeviceFasheqi;
import com.psylife.service.DeviceService;
import com.psylife.util.page.ListInfo;
import com.psylife.vo.web.DeviceDataHistoryVO;
import com.psylife.vo.web.FasheqiDataHistoryVO;
import com.psylife.vo.web.TrucksDeviceCountVO;
import com.psylife.vo.web.TrucksFasheqiPosVO;

 
public class DeviceServiceImpl extends BaseServiceImpl implements DeviceService{

	private DeviceDao dao=(DeviceDao)new DeviceDaoImpl();

	@Override
	public TrucksDeviceCountVO getTrucksDeviceCount(Integer company_id) {
		return dao.getTrucksDeviceCount(company_id);
	}

	@Override
	public TrucksFasheqiPosVO fasheqipos(String dtu_id) {
		return dao.fasheqipos(dtu_id);
	}

	@Override
	public List<DeviceDataHistoryVO> getHistoryDtuList(String keyWord,
			int pagenum, Integer company_id, Date startTime, Date endTime) {
		return dao.getHistoryDtuList(keyWord, pagenum, company_id, startTime, endTime);
	}

	@Override
	public List<FasheqiDataHistoryVO> getHistoryfasheqiList(String keyWord,
			int pagenum, Integer company_id, Date startTime, Date endTime) {
		return dao.getHistoryfasheqiList(keyWord, pagenum, company_id, startTime, endTime);
	}

	@Override
	public ListInfo<DeviceFasheqi> searchByFasheqilist(Integer company_id,
			String keyWord, int currentPageNO, int pageSize) {
		return dao.searchByFasheqilist(company_id, keyWord, currentPageNO, pageSize);
	}

}
