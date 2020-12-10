
package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kkbc.dao.TrucksDao;
import com.kkbc.entity.Trucks;
import com.kkbc.entity.TyreBase;
import com.kkbc.service.TrucksService;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.TrucksByAdminVO;
import com.kkbc.vo.TrucksVO;
import com.kkbc.vo.web.TrucksWatchVO;

@Service
public class TrucksServiceImpl implements TrucksService{

	@Resource
	private TrucksDao trucksDao;

	@Override
	public List<TrucksVO> searchByList(int pagenum, Integer user_id,
			Integer trucks_flag, Integer trucks_health, String trucks_type,
			String keyWord, String transport_type,String column,String order) {
		return trucksDao.searchByList(pagenum, user_id, trucks_flag, trucks_health, trucks_type, keyWord, transport_type, column, order);
	}

	@Override
	public TrucksByAdminVO getByTrucks_id(String trucks_id) {
		return trucksDao.getByTrucks_id(trucks_id);
	}

	@Override
	public List<Trucks> trucksListByPattern(int company_id) {
		return trucksDao.trucksListByPattern(company_id);
	}

	@Override
	public ListInfo<TrucksWatchVO> searchByWatchlist(Integer company_id,
			String keyWord, int currentPageNO, int pageSize) {
		return trucksDao.searchByWatchlist(company_id, keyWord, currentPageNO, pageSize);
	}

	@Override
	public ListInfo<TrucksWatchVO> searchBylistBind(Integer company_id,
			String keyWord, int currentPageNO, int pageSize) {
		return trucksDao.searchBylistBind(company_id, keyWord, currentPageNO, pageSize);
	}

	@Override
	public boolean trucksDtuBind(String trucks_id, String dtu_id,String phone) {
		return trucksDao.trucksDtuBind(trucks_id, dtu_id,phone);
	}

	@Override
	public boolean removeBind(String dtu_id) {
		return trucksDao.removeBind(dtu_id);
	}

	@Override
	public int goUpdateTrucksMabiao(String trucks_id, Integer user_id,
			Double mabiao) {
		return trucksDao.goUpdateTrucksMabiao(trucks_id, user_id, mabiao);
	}

	@Override
	public int inTruckInfo(Trucks trucks, List<TyreBase> tyres, Integer userId) {
		return trucksDao.inTruckInfo(trucks, tyres, userId);
	}
	

}
