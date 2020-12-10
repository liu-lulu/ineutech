
package com.psylife.service.impl;

import java.util.List;

import com.psylife.dao.TrucksDao;
import com.psylife.dao.impl.TrucksDaoImpl;
import com.psylife.entity.Trucks;
import com.psylife.entity.TyreBase;
import com.psylife.service.TrucksService;
import com.psylife.util.page.ListInfo;
import com.psylife.util.push.Message;
import com.psylife.vo.TrucksByAdminVO;
import com.psylife.vo.TrucksVO;
import com.psylife.vo.WarnMsgVO;
import com.psylife.vo.web.TrucksWatchVO;

 
public class TrucksServiceImpl implements TrucksService{

	private TrucksDao dao=(TrucksDao)new TrucksDaoImpl();

	@Override
	public List<TrucksVO> searchByList(int pagenum, Integer user_id,
			Integer trucks_flag, Integer trucks_health, String trucks_type,
			String keyWord, String transport_type,String column,String order) {
		return dao.searchByList(pagenum, user_id, trucks_flag, trucks_health, trucks_type, keyWord, transport_type, column, order);
	}

	@Override
	public TrucksByAdminVO getByTrucks_id(String trucks_id) {
		return dao.getByTrucks_id(trucks_id);
	}

	@Override
	public List<Trucks> trucksListByPattern(int company_id) {
		return dao.trucksListByPattern(company_id);
	}

	@Override
	public ListInfo<TrucksWatchVO> searchByWatchlist(Integer company_id,
			String keyWord, int currentPageNO, int pageSize) {
		return dao.searchByWatchlist(company_id, keyWord, currentPageNO, pageSize);
	}

	@Override
	public ListInfo<TrucksWatchVO> searchBylistBind(Integer company_id,
			String keyWord, int currentPageNO, int pageSize) {
		return dao.searchBylistBind(company_id, keyWord, currentPageNO, pageSize);
	}

	@Override
	public boolean trucksDtuBind(String trucks_id, String dtu_id,String phone) {
		return dao.trucksDtuBind(trucks_id, dtu_id,phone);
	}

	@Override
	public boolean removeBind(String dtu_id) {
		return dao.removeBind(dtu_id);
	}

	@Override
	public int goUpdateTrucksMabiao(String trucks_id, Integer user_id,
			Double mabiao) {
		return dao.goUpdateTrucksMabiao(trucks_id, user_id, mabiao);
	}

	@Override
	public int inTruckInfo(Trucks trucks, List<TyreBase> tyres, Integer userId) {
		return dao.inTruckInfo(trucks, tyres, userId);
	}

	@Override
	public int updateTrucksHealthWhenTyreChange(String tyre_id1, String tyre_id2) {
		return dao.updateTrucksHealthWhenTyreChange(tyre_id1, tyre_id2);
	}

	@Override
	public ListInfo<WarnMsgVO> searchWarnMsglist(Integer company_id,
			String keyWord, int currentPageNO, int pageSize) {
		return dao.searchWarnMsglist(company_id, keyWord, currentPageNO, pageSize);
	}

	@Override
	public List<Message> warnMsgListByApp(Integer user_id, String trucksId,
			Integer state, int pagenum) {
		return dao.warnMsgListByApp(user_id, trucksId, state, pagenum);
	}
	

}
