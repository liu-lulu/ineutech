package com.kkbc.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kkbc.dao.TrucksDao;
import com.kkbc.dao.TrucksDeviceDao;
import com.kkbc.dao.TyreBaseDao;
import com.kkbc.entity.Device;
import com.kkbc.entity.DeviceFasheqi;
import com.kkbc.entity.Trucks;
import com.kkbc.entity.TyreBase;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.hardware.UDPServerManager;
import com.kkbc.hardware.process.HeartProcess;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.TrucksByAdminVO;
import com.kkbc.vo.TrucksVO;
import com.kkbc.vo.TyreByAdminVO;
import com.kkbc.vo.TyreVO;
import com.kkbc.vo.web.TrucksWatchVO;
import com.psylife.util.Constants;
import com.psylife.util.TrucksStyleUtil;

@Repository
public class TrucksDaoImpl extends BaseDaoImpl implements TrucksDao{
	
	@Resource
	private TrucksDeviceDao trucksDeviceDao;
	@Resource
	private TyreBaseDao tyreBaseDao ;
	static final Logger logger = LoggerFactory.getLogger(TrucksDao.class);

	@Override
	public List<TrucksVO> searchByList(int pagenum,Integer user_id,Integer trucks_flag,Integer trucks_health,String trucks_type,String keyWord,String transport_type,String column,String order){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("timeOut", HeartProcess.INTERVAL_TIMEOUT_TCP);
		params.put("user_id", user_id);
		params.put("trucks_flag", trucks_flag);
		params.put("trucks_health", trucks_health);
		params.put("trucks_type", trucks_type);
		params.put("keyWord", keyWord);
		params.put("transport_type", transport_type);
		params.put("column", column);
		params.put("order", order);
		params.put("startIndex", (pagenum-1)*Constants.PAGESIZE);
		params.put("pageSize", Constants.PAGESIZE);
		
//		
//		StringBuffer SQL = new StringBuffer();
//		SQL.append("SELECT * FROM (");
//		SQL.append("SELECT DISTINCT T.id,T.trucks_id,T.trucks_brand,T.trucks_style,case when TIMESTAMPDIFF(SECOND, caiji_time, CURRENT_TIMESTAMP())*1000<"+HeartProcess.INTERVAL_TIMEOUT_TCP+" then T.trucks_flag else 0 END as trucks_flag,T.trucks_health,T.trucks_type,T.transport_type,T.guache_save_flag,T.guache_trucks_id,T.li_cheng_run,T.trucks_mode,TD.caiji_time ")
//		.append(" FROM trucks T LEFT JOIN device TD ON TD.trucks_id=T.trucks_id LEFT JOIN `user` U ON U.user_company_id=T.company_id WHERE U.user_id=? ");
//		SQL.append(")T where 1=1");
//		List<Object> paramater=new ArrayList<Object>();
//		paramater.add(user_id);
//		if(trucks_flag!=null){//状态0停,1行
//			SQL.append(" and T.trucks_flag=?");
//			paramater.add(trucks_flag);
//		}
//		
//		if(trucks_health!=null){
//			if(trucks_health.intValue()==1){
//				SQL.append(" and T.trucks_health>?");
//			}else{
//				SQL.append(" and T.trucks_health<=?");
//			}
//			paramater.add(30);
//		}
//		if(trucks_type!=null&&!"".equals(trucks_type)){
//			SQL.append(" and T.trucks_type=? ");
//			paramater.add(trucks_type);
//		}
//		
//		if(transport_type!=null&&!"".equals(transport_type)){
//			SQL.append(" and T.transport_type=? ");
//			paramater.add(transport_type);
//		}
//		
//		if(keyWord!=null&&!"".equals(keyWord)){
//			SQL.append(" and T.trucks_id like ? ");
//			paramater.add("%"+keyWord+"%");
//		}
//		String orderSQL="";
//		if (StringUtils.isNotEmpty(column)) {
//			
//			orderSQL+=" order by ";
//			
//			if ("1".equals(column)) {
//				//车牌
//				orderSQL+=" T.trucks_id ";
//			}else if ("2".equals(column)) {
//				//厂牌
//				orderSQL+=" T.trucks_brand ";
//			}else if ("3".equals(column)) {
//				//车型
//				orderSQL+=" T.trucks_style ";
//			}else if ("4".equals(column)) {
//				//健康
//				orderSQL+=" T.trucks_health ";
//			}else if ("5".equals(column)) {
//				//状态
//				orderSQL+=" T.trucks_flag ";
//			}
//			
//			
//			//排列顺序
//			if (StringUtils.isNotEmpty(order)){
//				if ("2".equals(order)) {
//					order="DESC";
//				}else {
//					order="ASC";
//				}
//			}else {
//				order="";
//			}
//			orderSQL+=order;
//			
//		}
//		SQL.append(orderSQL);
//		
//		SQL.append(" LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		

		@SuppressWarnings("unchecked")
		List<TrucksVO> list=getSqlMapClientTemplate().queryForList("TrucksVO.searchByList", params);
		logger.info("根据用户获取车辆列表成功！");
		return list;
	}
	
	@Override
	public TrucksByAdminVO getByTrucks_id(String trucks_id){
		TrucksByAdminVO vo=null;
		vo=getTrucksFasheqiByTrucks_id(trucks_id);
		if(vo!=null&&"主车".equals(vo.getTrucks_type())&&1==vo.getGuache_save_flag()&&vo.getGuache_trucks_id()!=null&&!"".equals(vo.getGuache_trucks_id())){//主车 可以选择挂其他挂车的
			TrucksByAdminVO vo2=getTrucksFasheqiByTrucks_id(vo.getGuache_trucks_id());
			vo.setGuache(vo2);
			
			vo.setTrucks_C1(vo2.getTrucks_C1());
			vo.setTrucks_C2(vo2.getTrucks_C2());
			vo.setTrucks_C3(vo2.getTrucks_C3());
			vo.setTrucks_C4(vo2.getTrucks_C4());
			vo.setTrucks_C5(vo2.getTrucks_C5());
			vo.setTrucks_C6(vo2.getTrucks_C6());
			
			vo.setTrucks_C7(vo2.getTrucks_C7());
			vo.setTrucks_C8(vo2.getTrucks_C8());
			vo.setTrucks_C9(vo2.getTrucks_C9());
			vo.setTrucks_C10(vo2.getTrucks_C10());
			vo.setTrucks_C11(vo2.getTrucks_C11());
			vo.setTrucks_C12(vo2.getTrucks_C12());
			
			vo.setTrucks_C13(vo2.getTrucks_C13());
			vo.setTrucks_C14(vo2.getTrucks_C14());
			vo.setTrucks_C15(vo2.getTrucks_C15());
			vo.setTrucks_C16(vo2.getTrucks_C16());
			if(vo2!=null&&vo2.getDeviceFasheqis()!=null){
				List<DeviceFasheqi> deviceFasheqis2=vo.getDeviceFasheqis();
				try {
					if(deviceFasheqis2==null){
						deviceFasheqis2=new ArrayList<DeviceFasheqi>();
						vo.setDeviceFasheqis(deviceFasheqis2);
					}
					
					int t,k;
					for(t=1;t<=vo2.getDeviceFasheqis().size();t++){
						if(vo2.getDeviceFasheqis().get(t-1)!=null){
							if(t>deviceFasheqis2.size()){
								for(k=deviceFasheqis2.size()+1;k<=t;k++){
									deviceFasheqis2.add(null);
								}
							}
							deviceFasheqis2.set(t-1, vo2.getDeviceFasheqis().get(t-1));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					deviceFasheqis2=null;
				}
			}
		}
		if(vo!=null){
			String tyreIds=tyreInArrByTrucks(vo);
			if(!"".equals(tyreIds)){
//				String sql="SELECT TD.tyre_id,TD.tyre_health,TB.tyre_paver FROM tyre_dynamic TD LEFT JOIN tyre_base TB ON TB.tyre_id=TD.tyre_id WHERE TD.tyre_id in("+insql+")";
				final Map<String, Float> mapHealth=new HashMap<String, Float>();
				final Map<String, Float> mapPattern=new HashMap<String, Float>();
				@SuppressWarnings("unchecked")
				List<TyreByAdminVO> tyreVos=getSqlMapClientTemplate().queryForList("TyreByAdminVO.getTyreInfo", tyreIds);
				for(TyreByAdminVO tyreVo:tyreVos){
					mapHealth.put(tyreVo.getTyre_id(), tyreVo.getTyre_health());
					mapPattern.put(tyreVo.getTyre_id(), tyreVo.getTyre_paver());
				}
				vo.setMapHealth(mapHealth);
				vo.setMapPattern(mapPattern);
			}
		}
		logger.info("根据车牌号获取车信息成功！"+trucks_id);
		return vo;
	}
	
	/**
	 * 根据车牌查询
	 * @param trucks_id
	 * @param connection
	 * @return
	 */
	private TrucksByAdminVO getTrucksFasheqiByTrucks_id(String trucks_id){
		TrucksByAdminVO vo=null;
//		StringBuffer SQL = new StringBuffer();
//		SQL.append("SELECT DISTINCT T.id,T.trucks_id,T.trucks_brand,T.trucks_style,T.trucks_flag,T.trucks_health,TD.dtu_id,T.li_cheng,TD.warn,TD.caiji_time,T.li_cheng_run,T.mabiao,T.transport_type,T.guache_trucks_id,T.trucks_mode,T.guache_save_flag,TD.dimian_sulu,T.trucks_type,TD.gps_status,TD.latitude,TD.latitude_type,TD.longitude,TD.longitude_type")
//		.append(",T.trucks_A1,T.trucks_A2,T.trucks_A3,T.trucks_A4,T.trucks_A5,T.trucks_A6")
//		.append(",T.trucks_B1,T.trucks_B2,T.trucks_B3,T.trucks_B4,T.trucks_B5,T.trucks_B6,T.trucks_B7,T.trucks_B8")
//		.append(",T.trucks_C1,T.trucks_C2,T.trucks_C3,T.trucks_C4,T.trucks_C5,T.trucks_C6,T.trucks_C7,T.trucks_C8,T.trucks_C9,T.trucks_C10,T.trucks_C11,T.trucks_C12,T.trucks_C13,T.trucks_C14,T.trucks_C15,T.trucks_C16")
//		.append(",U.user_phone,U.true_name,DR.from_adress,DR.to_adress,U.user_id ")
//		.append(" FROM trucks T LEFT JOIN device TD ON TD.trucks_id=T.trucks_id LEFT JOIN driving_record DR ON DR.id=T.last_driving_record_id LEFT JOIN `user` U ON U.user_id=DR.driver_id  WHERE T.trucks_id=? LIMIT 1 ");
		@SuppressWarnings("unchecked")
		List<TrucksByAdminVO> trucksVOs = getSqlMapClientTemplate().queryForList("TrucksByAdminVO.queryByTrucksId", trucks_id);
		if (trucksVOs!=null&&trucksVOs.size()>0) {
			vo=trucksVOs.get(0);
			if (vo.getDtuOnlineStatus()==0) {
				vo.setTrucks_flag(0);
			}
		}
		
		if(vo!=null&&vo.getDtu_id()!=null&&!"".equals(vo.getDtu_id())){
			//设备发射器
			@SuppressWarnings("unchecked")
			List<DeviceFasheqi> deviceFasheqis=getSqlMapClientTemplate().queryForList("DeviceFasheqi.queryByDtu", vo.getDtu_id());
			if(deviceFasheqis!=null){
				List<DeviceFasheqi> deviceFasheqis2=null;
				try {
					deviceFasheqis2=new ArrayList<DeviceFasheqi>();
					int t,k;
					for(DeviceFasheqi deviceFasheqi:deviceFasheqis){
						if(deviceFasheqi.getNo()!=null){
							t=TrucksStyleUtil.noByFasheqiNo(vo.getTrucks_style(), deviceFasheqi.getNo());
							if(t>0){
								if(t>deviceFasheqis2.size()){
									for(k=deviceFasheqis2.size()+1;k<=t;k++){
										deviceFasheqis2.add(null);
									}
								}
								deviceFasheqis2.set(t-1, deviceFasheqi);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					deviceFasheqis2=null;
				}
				vo.setDeviceFasheqis(deviceFasheqis2);
			}
		}
		return vo;
	}
	
	@Override
	public List<Trucks> trucksListByPattern(int company_id) {
		@SuppressWarnings("unchecked")
		List<Trucks> result= getSqlMapClientTemplate().queryForList("Trucks.getListByCompany", company_id);
		return result;
	} 
	
	
	@Override
	public ListInfo<TrucksWatchVO> searchByWatchlist(Integer company_id,String keyWord,int currentPageNO,int pageSize){
		ListInfo<TrucksWatchVO> listInfo = new ListInfo<TrucksWatchVO>(currentPageNO, pageSize);
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("company_id", company_id);
		param.put("keyWord", keyWord);
		param.put("startIndex", listInfo.getFirst());
		param.put("pageSize", pageSize);
		@SuppressWarnings("unchecked")
		List<TrucksWatchVO> eventList=getSqlMapClientTemplate().queryForList("TrucksWatchVO.searchWatchlist", param);
		
		listInfo.setCurrentList(eventList);
		
		int totalCount =(int) getSqlMapClientTemplate().queryForObject("TrucksWatchVO.searchWatchlistCount", param);
		listInfo.setSizeOfTotalList(totalCount);
		return listInfo;
	}
	
	@Override
	public ListInfo<TrucksWatchVO> searchBylistBind(Integer company_id,String keyWord,int currentPageNO,int pageSize){
		ListInfo<TrucksWatchVO> listInfo = new ListInfo<TrucksWatchVO>(currentPageNO, pageSize);
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("company_id", company_id);
		param.put("keyWord", keyWord);
		param.put("startIndex", listInfo.getFirst());
		param.put("pageSize", pageSize);
		@SuppressWarnings("unchecked")
		List<TrucksWatchVO> eventList=getSqlMapClientTemplate().queryForList("TrucksWatchVO.searchlistBind", param);
		
		listInfo.setCurrentList(eventList);
		int totalCount=(int) getSqlMapClientTemplate().queryForObject("TrucksWatchVO.searchlistBindCount", param);
		listInfo.setSizeOfTotalList(totalCount);
			
		return listInfo;
	}
	
	@Transactional
	@Override
	public boolean trucksDtuBind(String trucks_id,String dtu_id,String phone){
		if(trucks_id==null||dtu_id==null||"".equals(dtu_id)||"".equals(trucks_id)){
			return false;
		}
		@SuppressWarnings("unchecked")
		List<Device> devices =getSqlMapClientTemplate().queryForList("Device.getByTrucksid", trucks_id);
		if(devices!=null&&devices.size()>0){
			String dtu_id2=devices.get(0).getDtu_id();
			if(dtu_id2==null){
				@SuppressWarnings("unchecked")
				List<Device> dtuInfo=getSqlMapClientTemplate().queryForList("Device.getByDtuid", dtu_id);
				if(dtuInfo!=null&&dtuInfo.size()>0){
					if(StringUtils.isEmpty(dtuInfo.get(0).getTrucks_id())){
						Device param=new Device();
						param.setTrucks_id(trucks_id);
						param.setDtu_id(dtu_id);
						param.setPhone(phone);
						param.setCompany_id(devices.get(0).getCompany_id());
						int a=getSqlMapClientTemplate().update("Device.trucksDtuBind", param);
						if(a>0){
							logger.info("车辆绑定dtu提交成功,dtu="+dtu_id+",trucks_id="+trucks_id);
							HardwareElement element=UDPServerManager.getInstance().getChannelElement().get(dtu_id);
							if(element!=null){
								trucksDeviceDao.reloadTrucksDevice(element);
							}
							return true;
						}
					}
				}
			}
		}
		logger.info("车辆绑定dtu提交失败,dtu="+dtu_id+",trucks_id="+trucks_id);
		return false;
	}
	
	@Transactional
	@Override
	public boolean removeBind(String dtu_id){
		if(dtu_id==null||"".equals(dtu_id)){
			return false;
		}
		int a = getSqlMapClientTemplate().update("Device.removeBind", dtu_id);
		if(a>0){
			logger.info("车辆解除dtu绑定提交成功,dtu="+dtu_id);
			HardwareElement element=UDPServerManager.getInstance().getChannelElement().get(dtu_id);
			if(element!=null){
				trucksDeviceDao.reloadTrucksDevice(element);
			}
			return true;
		}
		logger.info("车辆解除dtu绑定提交失败,dtu="+dtu_id);
		return false;
	}
	
	private String tyreInArrByTrucks(TrucksByAdminVO vo){
		StringBuffer result=new StringBuffer();
		boolean flag=false;
		if(vo.getTrucks_A1()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_A1());
			result.append("'");
		}
		if(vo.getTrucks_A2()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_A2());
			result.append("'");
		}
		if(vo.getTrucks_A3()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_A3());
			result.append("'");
		}
		if(vo.getTrucks_A4()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_A4());
			result.append("'");
		}
		if(vo.getTrucks_A5()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_A5());
			result.append("'");
		}
		if(vo.getTrucks_A6()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_A6());
			result.append("'");
		}
		if(vo.getTrucks_B1()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_B1());
			result.append("'");
		}
		if(vo.getTrucks_B2()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_B2());
			result.append("'");
		}
		if(vo.getTrucks_B3()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_B3());
			result.append("'");
		}
		if(vo.getTrucks_B4()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_B4());
			result.append("'");
		}
		if(vo.getTrucks_B5()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_B5());
			result.append("'");
		}
		if(vo.getTrucks_B6()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_B6());
			result.append("'");
		}
		if(vo.getTrucks_B7()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_B7());
			result.append("'");
		}
		if(vo.getTrucks_B8()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_B8());
			result.append("'");
		}
		if(vo.getTrucks_C1()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C1());
			result.append("'");
		}
		if(vo.getTrucks_C2()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C2());
			result.append("'");
		}
		if(vo.getTrucks_C3()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C3());
			result.append("'");
		}
		if(vo.getTrucks_C4()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C4());
			result.append("'");
		}
		if(vo.getTrucks_C5()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C5());
			result.append("'");
		}
		if(vo.getTrucks_C6()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C6());
			result.append("'");
		}
		if(vo.getTrucks_C7()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C7());
			result.append("'");
		}
		if(vo.getTrucks_C8()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C8());
			result.append("'");
		}
		if(vo.getTrucks_C9()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C9());
			result.append("'");
		}
		if(vo.getTrucks_C10()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C10());
			result.append("'");
		}
		if(vo.getTrucks_C11()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C11());
			result.append("'");
		}
		if(vo.getTrucks_C12()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C12());
			result.append("'");
		}
		if(vo.getTrucks_C13()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C13());
			result.append("'");
		}
		if(vo.getTrucks_C14()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C14());
			result.append("'");
		}
		if(vo.getTrucks_C15()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C15());
			result.append("'");
		}
		if(vo.getTrucks_C16()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_C16());
			result.append("'");
		}
		return result.toString();
	}
	
	@Transactional
	@Override
	public int updateTrucksMabiao(String trucks_id,Double mabiao) {
			Trucks trucks = (Trucks) getSqlMapClientTemplate().queryForObject("Trucks.getByTrucks_id", trucks_id);
			if(trucks!=null){
				
				double oldmabiao=trucks.getMabiao();//原始码表数
				String oldtrucks_id=trucks.getTrucks_id();//车牌
				String oldguache_trucks_id=trucks.getGuache_trucks_id();//挂车车牌
				int oldguache_save_flag=trucks.getGuache_save_flag();//挂车保存标志,0表示挂车信息是保存在主车上的，司机是不可以选择挂其他挂车的，1表示挂车信息是单独保存的，司机可以选择挂其他挂车的
				int olddtu_multi_flag=trucks.getDtu_multi_flag();//0是主挂dtu是一个,1为主挂是分开的
				double dis=mabiao-oldmabiao;//要加的差值
				String oldtrucks_style=trucks.getTrucks_style();//车型--4*2+3 
				int a = 0;
				if (dis>0) {
					if(olddtu_multi_flag==1&&oldguache_trucks_id!=null&&!"".equals(oldguache_trucks_id)){//主挂是分开的保存的
						a=updateTyreTrucksLichengRun(dis, mabiao, oldguache_trucks_id, oldtrucks_style);
						if(a!=0){
							return 2;//保存失败
						}
					}
					a=updateTyreTrucksLichengRun(dis, mabiao, oldtrucks_id, oldtrucks_style);
					//更新挂车的里程数（主挂分开，挂车信息单独保存；共用一个dtu时，挂车的轮胎信息已保存到主车上，因此，只需更新挂车里程数）
					if (oldguache_save_flag==1&&olddtu_multi_flag==0&&StringUtils.isNotEmpty(oldguache_trucks_id)) {
						a=updateTrucksLiChengRun(dis, mabiao, oldguache_trucks_id);
						if(a!=0){
							return 2;//保存失败
						}
					}
				}
				
				if(a!=0){
					return 3;//保存失败
				}
			}else{
				return 1;//不存在
			}
			return 0;
	}
	
	//更新车的行驶里程
	@Transactional
	private int updateTrucksLiChengRun(double dis,double mabiao,String trucks_id){
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("dis", dis);
		param.put("mabiao", mabiao);
		param.put("trucks_id", trucks_id);
		int a =getSqlMapClientTemplate().update("Trucks.updateLiChengRun", param);
		if (a > 0) {
            logger.info("更新车行驶里程成功！trucks_id="+trucks_id);
            return 0;
		}
		return 1;
	}
	
	@Transactional
	//更新胎和车行驶里程
	private int updateTyreTrucksLichengRun(double dis,double mabiao,String trucks_id,String trucks_style) {
		String[] tyreWhereArr=TrucksStyleUtil.TyreWhereArrByStyle(trucks_style);
		if(tyreWhereArr==null){
			return 1;
		}
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("tyreWhereArr", tyreWhereArr);
		param.put("dis", dis);
		param.put("mabiao", mabiao);
		param.put("trucks_id", trucks_id);
		
		int a = getSqlMapClientTemplate().update("Trucks.updateTyreTrucksLichengRun", param);
		if (a > 0) {
            logger.info("更新胎和车行驶里程成功！trucks_id="+trucks_id);
            return 0;
		}
		return 1;
	}
	
	@Transactional
	@Override
	public int goUpdateTrucksMabiao(String trucks_id,Integer user_id,Double mabiao){
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("trucks_id", trucks_id);
		param.put("user_id", user_id);
		param.put("mabiao", mabiao);
		
		Trucks trucks=(Trucks) getSqlMapClientTemplate().queryForObject("Trucks.getMabiao", param);
		if (trucks!=null) {
			int a=updateTrucksMabiao(trucks_id,mabiao);//更新车码表数
			if(a!=0){
				return 2;//保存失败
			}
		}else{
			return 1;//车不存在
		}
		logger.info("更新车码表数:"+trucks_id);
		return 0;			
	}

	@Transactional
	@Override
	public int inTruckInfo(Trucks trucks, List<TyreBase> tyres,Integer userId) {
//		TyreBaseDao tyreBaseDao = (TyreBaseDao)new TyreBaseDaoImpl();
		
		TrucksByAdminVO trucksByAdminVO = getTrucksFasheqiByTrucks_id(trucks.getTrucks_id());
		if (trucksByAdminVO!=null) {//车已入库
			logger.info(trucks.getTrucks_id()+"车辆已入库");
			return 31;
		}
		
		StringBuffer tyreColumn = new StringBuffer();
		String sql2="";
		List<TyreBase> notCtyre = new ArrayList<>();
		List<TyreBase> Ctyre = new ArrayList<>();
		List<String> temptyre = new ArrayList<>();
		
		for (int i=0;i<tyres.size();i++) {
			TyreBase tyre = tyres.get(i);
			TyreVO tyreVO = tyreBaseDao.tyreDetial(userId, tyre.getTyre_id(), null, null);
			if (tyreVO!=null) {//轮胎已入库
				logger.info(tyre.getTyre_id()+"轮胎已入库,不能重复入库");
				return i;
			}
			if (tyre.getTyre_where().indexOf("C")<0) {
				notCtyre.add(tyre);
			}else {
				Ctyre.add(tyre);
			}
			if ("主车".equals(trucks.getTrucks_type())&&trucks.getDtu_multi_flag()==1) {//挂车信息单独保存(主车信息只保存导向和驱动的轮胎信息)
				if (tyre.getTyre_where().indexOf("C")<0){
					tyreColumn.append(",trucks_"+tyre.getTyre_where());
					temptyre.add(tyre.getTyre_id());
				}
			}else {//挂车信息是保存在主车上(所有的轮胎信息都保存到主车上)
				tyreColumn.append(",trucks_"+tyre.getTyre_where());
				temptyre.add(tyre.getTyre_id());
			}
			
		}
		Map<String,Object> trucksParams = new HashMap<String,Object>();
		trucksParams.put("tyreColumn",tyreColumn);
		trucksParams.put("trucks_id",trucks.getTrucks_id());
		trucksParams.put("trucks_brand",trucks.getTrucks_brand());
		trucksParams.put("trucks_style",trucks.getTrucks_style());
		trucksParams.put("trucks_type",trucks.getTrucks_type());
		trucksParams.put("trucks_flag",0);
		trucksParams.put("trucks_health",99);
		trucksParams.put("company",trucks.getCompany());
		trucksParams.put("company_id",trucks.getCompany_id());
		trucksParams.put("li_cheng",trucks.getLi_cheng());
		trucksParams.put("li_cheng_run",trucks.getLi_cheng_run());
		trucksParams.put("mabiao",trucks.getMabiao());
		trucksParams.put("mabiao_ruku",trucks.getMabiao_ruku());
		trucksParams.put("transport_type",trucks.getTransport_type());
		trucksParams.put("guache_trucks_id",trucks.getGuache_trucks_id());
		trucksParams.put("trucks_mode",trucks.getTrucks_mode());
		trucksParams.put("guache_save_flag",trucks.getGuache_save_flag());
		trucksParams.put("dtu_multi_flag",trucks.getDtu_multi_flag());
		trucksParams.put("tyreIdValue",temptyre);
		int a= (int) getSqlMapClientTemplate().insert("Trucks.saveInfo", trucksParams);
		
		if (a==0) {//车辆信息入库失败
			logger.error(trucks.getTrucks_id()+"车辆信息入库失败!");
			return 32;
		}
		String style=trucks.getTrucks_style();
		if ("主车".equals(trucks.getTrucks_type())&&(0<style.indexOf("+")&&style.substring(style.indexOf("+")+1).length()>0)&&trucks.getGuache_save_flag()==1) {//单独添加挂车信息
			List<String> CTyreId=new ArrayList<String>();
			for (int i=0;i<Ctyre.size();i++) {
				sql2+=",trucks_"+Ctyre.get(i).getTyre_where();
				CTyreId.add(Ctyre.get(i).getTyre_id());
			}
			
			Map<String,Object> guacheParams = new HashMap<String,Object>();
			guacheParams.put("tyreColumn",sql2);
			guacheParams.put("trucks_id",trucks.getGuache_trucks_id());
			guacheParams.put("trucks_brand",trucks.getTrucks_brand());
			guacheParams.put("trucks_style",trucks.getTrucks_style());
			guacheParams.put("trucks_type","挂车");
			guacheParams.put("trucks_flag",0);
			guacheParams.put("trucks_health",99);
			guacheParams.put("company",trucks.getCompany());
			guacheParams.put("company_id",trucks.getCompany_id());
			guacheParams.put("li_cheng",trucks.getLi_cheng());
			guacheParams.put("li_cheng_run",trucks.getLi_cheng_run());
			guacheParams.put("mabiao",trucks.getMabiao());
			guacheParams.put("mabiao_ruku",trucks.getMabiao_ruku());
			guacheParams.put("transport_type",trucks.getTransport_type());
			guacheParams.put("guache_trucks_id","");
			guacheParams.put("trucks_mode",trucks.getTrucks_mode());
			guacheParams.put("guache_save_flag",trucks.getGuache_save_flag());
			guacheParams.put("dtu_multi_flag",trucks.getDtu_multi_flag());
			guacheParams.put("tyreIdValue",CTyreId);
			int b= (int) getSqlMapClientTemplate().insert("Trucks.saveInfo", guacheParams);
			
			if (b==0) {//车辆信息入库失败
				logger.error(trucks.getGuache_trucks_id()+"挂车信息入库失败!");
				return 33;
			}
			logger.info(trucks.getGuache_trucks_id()+"挂车信息入库成功!");
		}
		
		int b = tyreBaseDao.saveByList(tyres,trucks, userId);
		if (b!=2) {
			logger.error(trucks.getTrucks_id()+"轮胎信息入库失败!");
			return 34;
		}
		
		logger.info(trucks.getTrucks_id()+"车辆轮胎信息入库成功!");
		return 35;
	}
	
	/**
	 * 操作轮胎时,更新车的健康度
	 * @param connection
	 * @param trucksId 车牌号
	 * @param flag 1:装载  2:卸下 3:交换 4:轮胎在车上,只是检测了花纹深度
	 * @param tyreInfo 轮胎信息
	 * @param changTyre flag:3时,tyreInfo与changTyre互相交换
	 * @param tyre_where 轮位
	 * @return
	 */
	@Transactional
	@Override
	public int updateTrucksHealth(String trucksId, int flag, TyreVO tyreInfo,
			TyreVO changTyre, String tyre_where) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("trucksId", trucksId);
		if (tyre_where.indexOf("C")>=0) {
			param.put("isCtyre", "true");
		}
		@SuppressWarnings("unchecked")
		List<Trucks> trucks=getSqlMapClientTemplate().queryForList("Trucks.getByTrucksId", param);
		for (Trucks trucksInfo : trucks) {
			if (StringUtils.isEmpty(trucksInfo.getTrucks_id())) {
				continue;
			}
			Map<String, Object> tyreParam=new HashMap<String, Object>();
			tyreParam.put("trucks_id", trucksInfo.getTrucks_id());
			tyreParam.put("tyre_id", tyreInfo.getTyre_id());
			
			if ("主车".equals(trucksInfo.getTrucks_type())&&trucksInfo.getDtu_multi_flag()==1&&StringUtils.isNotEmpty(trucksInfo.getGuache_trucks_id())) {//挂车区轮胎只保存在挂车上
				tyreParam.put("getGucheTyre", "true");
			}else {
				tyreParam.put("getGucheTyre", "false");
			}
			
			@SuppressWarnings("unchecked")
			HashMap<String, Object> tyreHealthMap=(HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("TyreVO.getTyreHealth", tyreParam);
			float trucksHealth = 0;
			if (tyreHealthMap!=null) {

				Double totalHealth=(Double) tyreHealthMap.get("totalHealth");
				Long count=(Long) tyreHealthMap.get("count");
				
				if (totalHealth!=0&&count!=0) {
					trucksHealth=(float) (totalHealth/count);
				}
			
			}
			
			Trucks healthInfo=new Trucks();
			healthInfo.setTrucks_id(trucksInfo.getTrucks_id());
			healthInfo.setTrucks_health(trucksHealth);
			int a=getSqlMapClientTemplate().update("Trucks.updateHealth", healthInfo);
			if (a<=0) {
				return 0;
			}
			logger.info(trucksInfo.getTrucks_id()+"健康度更新成功");
			
		}
		return 1;
	}

	/**
	 * 操作轮胎时,更新车的健康度
	 * @param connection
	 * @param trucksId 车牌号
	 * @param flag 1:装载  2:卸下 3:交换 4:轮胎在车上,只是检测了花纹深度
	 * @param tyreInfo 轮胎信息
	 * @param changTyre flag:3时,tyreInfo与changTyre互相交换
	 * @param tyre_where 轮位
	 * @return
	 */
	@Transactional
	public int updateTrucksHealth1(String trucksId, int flag, TyreVO tyreInfo,
			TyreVO changTyre, String tyre_where) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("trucksId", trucksId);
		if (tyre_where.indexOf("C")>=0) {
			param.put("isCtyre", "true");
		}
		@SuppressWarnings("unchecked")
		List<Trucks> trucks=getSqlMapClientTemplate().queryForList("Trucks.getByTrucksId", param);
		for (Trucks trucksInfo : trucks) {
			if (StringUtils.isEmpty(trucksInfo.getTrucks_id())) {
				continue;
			}
			Map<String, Object> tyreParam=new HashMap<String, Object>();
			tyreParam.put("trucks_id", trucksInfo.getTrucks_id());
			tyreParam.put("tyre_id", tyreInfo.getTyre_id());
			
			if ("主车".equals(trucksInfo.getTrucks_type())&&trucksInfo.getDtu_multi_flag()==1&&StringUtils.isNotEmpty(trucksInfo.getGuache_trucks_id())) {//挂车区轮胎只保存在挂车上
				tyreParam.put("getGucheTyre", "true");
			}else {
				tyreParam.put("getGucheTyre", "false");
			}
			
			@SuppressWarnings("unchecked")
			HashMap<String, Object> tyreHealthMap=(HashMap<String, Object>) getSqlMapClientTemplate().queryForObject("TyreVO.getTyreHealthExceptTyreId", tyreParam);
			float trucksHealth = 0;
			if (tyreHealthMap!=null) {

				Double totalHealth=(Double) tyreHealthMap.get("totalHealth");
				Long count=(Long) tyreHealthMap.get("count");
				if (flag==1) {//装载的轮胎参与车的健康度计算
					if (tyreInfo.getTyre_health()!=null&&tyreInfo.getTyre_health().floatValue()!=0) {
						totalHealth+=tyreInfo.getTyre_health();
						count+=1;
					}
				}else if (flag==2) {//卸下的轮胎不参与车的健康度计算
					
				}else if (flag==3) {//交换的轮胎,要将最终安装到车上的轮胎参与车的健康度计算
					if (changTyre.getTyre_health()!=null&&changTyre.getTyre_health().floatValue()!=0) {
						totalHealth+=changTyre.getTyre_health();
						count+=1;
					}
				}else if (flag==4) {//检测的轮胎，最新的轮胎健康度参与计算
					if (tyreInfo.getTyre_health()!=null&&tyreInfo.getTyre_health().floatValue()!=0) {
						totalHealth+=tyreInfo.getTyre_health();
						count+=1;
					}
				}
				
				if (totalHealth!=0&&count!=0) {
					trucksHealth=(float) (totalHealth/count);
				}
			
			}
			
			Trucks healthInfo=new Trucks();
			healthInfo.setTrucks_id(trucksInfo.getTrucks_id());
			healthInfo.setTrucks_health(trucksHealth);
			int a=getSqlMapClientTemplate().update("Trucks.updateHealth", healthInfo);
			if (a<=0) {
				return 0;
			}
			logger.info(trucksInfo.getTrucks_id()+"健康度更新成功");
			
		}
		return 1;
	}

	@Override
	public int updateTrucksHealthWhenDriving(String trucks_id,
			boolean onlyZhuche, String guacheId) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("trucks_id", trucks_id);
		param.put("onlyZhuche","true");
		
		Double totalHealth = 0d;
		Long count = 0l;
		//获取主车的A,B区轮胎的健康度
		@SuppressWarnings("unchecked")
		Map<String, Object> truckTyreHealth=(Map<String, Object>) getSqlMapClientTemplate().queryForObject("TyreVO.getTyreHealthWhenDriving", param);
		if (truckTyreHealth!=null) {
			totalHealth=(Double) truckTyreHealth.get("totalHealth");
			count=(Long) truckTyreHealth.get("count");
		}
		
		if (!onlyZhuche) {
			Map<String, Object> guaParam=new HashMap<String, Object>();
			guaParam.put("trucks_id", guacheId);
			guaParam.put("onlyZhuche","false");
			@SuppressWarnings("unchecked")
			Map<String, Object> guaTyreHealth=(Map<String, Object>) getSqlMapClientTemplate().queryForObject("TyreVO.getTyreHealthWhenDriving", guaParam);
			if (guaTyreHealth!=null) {
				totalHealth+=(Double)guaTyreHealth.get("totalHealth");
				count+=(Long)guaTyreHealth.get("count");
			}
		}
		Trucks healthInfo=new Trucks();
		healthInfo.setTrucks_id(trucks_id);
		healthInfo.setTrucks_health((totalHealth==0||count==0)?0:(float)(totalHealth/count));
		int a=getSqlMapClientTemplate().update("Trucks.updateHealth", healthInfo);
		if (a<=0) {
			return 0;
		}
		logger.info(trucks_id+"健康度更新成功");
		
		return 1;
	}
	
}
