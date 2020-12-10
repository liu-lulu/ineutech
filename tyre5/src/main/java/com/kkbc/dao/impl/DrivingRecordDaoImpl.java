package com.kkbc.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kkbc.dao.DrivingRecordDao;
import com.kkbc.dao.TrucksDao;
import com.kkbc.dao.TrucksDeviceDao;
import com.kkbc.entity.DrivingRecord;
import com.kkbc.entity.Trucks;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.hardware.send.SendManager;
import com.kkbc.vo.DrivingRecordVO;
import com.kkbc.vo.TrucksByAdminVO;
import com.kkbc.vo.UserDrivingRecordCountVO;
import com.psylife.util.Constants;

@Repository
public class DrivingRecordDaoImpl extends BaseDaoImpl implements DrivingRecordDao{
	static final Logger logger = LoggerFactory.getLogger(DrivingRecordDao.class);
	
	@Resource
	private TrucksDeviceDao trucksDeviceDao;
	
	@Resource
	private TrucksDao trucksDao;

	@Override
	public DrivingRecord getLastDrivingRecord(Integer user_id) {
		DrivingRecord drivingRecord = (DrivingRecord) getSqlMapClientTemplate().queryForObject("DrivingRecord.getByUserId", user_id);
		logger.info("获取驾驶员最近驾驶记录");
		return drivingRecord;
	}
	
	@Override
	public DrivingRecordVO getLastDrivingRecordByTrucks_id(String trucks_id,boolean isMain) {
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("trucks_id", trucks_id);
		param.put("isMain", isMain?"true":"false");
		DrivingRecordVO drivingRecordVO = (DrivingRecordVO) getSqlMapClientTemplate().queryForObject("DrivingRecordVO.getByTrucksId", param);
		logger.info("获取车最近一条驾驶记录");
		return drivingRecordVO;
	}
	
	@Override
	public List<DrivingRecord> getDrivingRecordList(int pagenum,Integer user_id){
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("user_id", user_id);
		param.put("startIndex", (pagenum-1)*Constants.PAGESIZE);
		param.put("pageSize", Constants.PAGESIZE);
		@SuppressWarnings("unchecked")
		List<DrivingRecord> list=getSqlMapClientTemplate().queryForList("DrivingRecord.getPageByUserId", param);
		logger.info("根据用户获取架驶记录列表成功！");
		
		return list;
	}
	
	
	@Transactional
	@Override
	public int startDriving(DrivingRecord drivingRecord){
		
		//检查司机是否有已开始的行程
		DrivingRecord startParam=new DrivingRecord();
		startParam.setDriver_id(drivingRecord.getDriver_id());
		startParam.setStatus(DrivingRecord.STATUS_CREATE);
		DrivingRecord startedInfo=(DrivingRecord) getSqlMapClientTemplate().queryForObject("DrivingRecord.getByUserIdAndState", startParam);
		if (startedInfo!=null) {
			return 8;
		}
		
		TrucksByAdminVO trucksInfo=(TrucksByAdminVO) getSqlMapClientTemplate().queryForObject("TrucksByAdminVO.getDriveInfo", drivingRecord.getTrucks_id());
		
		int a;
		boolean flag=false;
//		Map<String , Object> trucksInfo=null;
		if (trucksInfo!=null&&"主车".equals(trucksInfo.getTrucks_type())) {//检查是否为主车不然表示不能驾驶
//			trucksInfo=truckRow.get(0);
			if(DrivingRecord.STATUS_CREATE==trucksInfo.getStatus().intValue()){
				return 3;//还正在开始，不能创建
			}
			if(trucksInfo.getMabiao().doubleValue()>drivingRecord.getLi_cheng_run().doubleValue()){
			    return 2;//码表数不能低于原本的码表数	
			}
//				if(!drivingRecord.getTransport_type().equals(resultSet.getString("transport_type"))){
//					return 4;//运输类型不一致
//				}
			a=trucksDao.updateTrucksMabiao(drivingRecord.getTrucks_id(), drivingRecord.getLi_cheng_run());//更新车码表数
			if(a!=0){
//					connection.rollback();
				return 7;//保存失败
			}
			if(trucksInfo.getTrucks_style().indexOf("+")>0){//车型为挂车
	            if(drivingRecord.getGuache_trucks_id()!=null&&!"".equals(drivingRecord.getGuache_trucks_id())&&!drivingRecord.getGuache_trucks_id().equals(trucksInfo.getGuache_trucks_id())){//挂车不在当前主车上
	            	TrucksByAdminVO guacheInfo=(TrucksByAdminVO) getSqlMapClientTemplate().queryForObject("TrucksByAdminVO.getGuacheDriveInfo", drivingRecord.getGuache_trucks_id());
//	            	SqlRowSet resultSet1=getJdbcTemplate().queryForRowSet("SELECT GT.trucks_style,GT.trucks_type,GT.trucks_id as guache_trucks_id,T.trucks_id,DR.`status` as DR_status,"
//	            			+ "GT.trucks_C1,GT.trucks_C2,GT.trucks_C3,GT.trucks_C4,GT.trucks_C5,GT.trucks_C6,GT.trucks_C7,GT.trucks_C8,GT.trucks_C9,GT.trucks_C10,GT.trucks_C11,GT.trucks_C12,GT.trucks_C13,GT.trucks_C14,GT.trucks_C15,GT.trucks_C16"
//	            			+ " FROM trucks GT LEFT JOIN trucks T ON T.guache_trucks_id=GT.trucks_id LEFT JOIN driving_record DR ON DR.id=T.last_driving_record_id WHERE GT.trucks_id=? LIMIT 1",
//	            			new Object[]{drivingRecord.getGuache_trucks_id()});
	            	if(guacheInfo==null||StringUtils.isEmpty(guacheInfo.getGuache_trucks_id())||guacheInfo.getTrucks_style().indexOf("+")<0||guacheInfo.getTrucks_type().indexOf("挂车")<0){//挂车不存在
	            		return 5;//挂车不存在
	            	}
//	            	Map<String, Object> guacheInfo=guacheRow.get(0);
	            	if(StringUtils.isNotEmpty(guacheInfo.getTrucks_id())){//挂车在主车上
	            		if(DrivingRecord.STATUS_CREATE==guacheInfo.getStatus().intValue()){//挂车还在正在驾驶的车上
	            			return 6;//挂车还在正在驾驶的其他车上
	            		}
	            		getSqlMapClientTemplate().update("Trucks.downGuache", drivingRecord.getGuache_trucks_id());
	            		//更新主车健康度
	            		trucksDao.updateTrucksHealthWhenDriving(guacheInfo.getTrucks_id(), true, null);
	            	}
	            	if(guacheInfo.getDtu_multi_flag()!=1){//挂车和主车共用一个dtu
	            		guacheInfo.setTrucks_id(drivingRecord.getTrucks_id());
	            		a=getSqlMapClientTemplate().update("TrucksByAdminVO.updateGuchequTyre", guacheInfo);	
	            		if(a>0){
	            			flag=true;
	            		}
	            	}
	            	String trucks_style=trucksInfo.getTrucks_style().substring(0,trucksInfo.getTrucks_style().indexOf("+"))+guacheInfo.getTrucks_style().substring(guacheInfo.getTrucks_style().indexOf("+"));
	            	
	            	TrucksByAdminVO param=new TrucksByAdminVO();
            		param.setTrucks_id(drivingRecord.getTrucks_id());
            		param.setGuache_trucks_id(drivingRecord.getGuache_trucks_id());
            		param.setTrucks_style(trucks_style);
            		param.setDtu_multi_flag(guacheInfo.getDtu_multi_flag());
	            	//挂在主车上
            		getSqlMapClientTemplate().update("TrucksByAdminVO.updateGuacheAndStyle", param);
            		
            		//更新主车健康度
	            	trucksDao.updateTrucksHealthWhenDriving(drivingRecord.getTrucks_id(), false, drivingRecord.getGuache_trucks_id());
	            }else if((drivingRecord.getGuache_trucks_id()==null||"".equals(drivingRecord.getGuache_trucks_id()))&&StringUtils.isNotEmpty(trucksInfo.getGuache_trucks_id())){//只有一个车头
	            	if(trucksInfo.getDtu_multi_flag()!=1&&trucksInfo.getGuache_save_flag()==1){//挂车和主车共用一个dtu
	            		a=getSqlMapClientTemplate().update("TrucksByAdminVO.clearGuchequTyre", drivingRecord.getTrucks_id());
	            		if(a>0){
	            			flag=true;
	            		}		            		
	            	}
	            	getSqlMapClientTemplate().update("TrucksByAdminVO.downGuache", drivingRecord.getTrucks_id());
	            	//更新主车健康度
	            	trucksDao.updateTrucksHealthWhenDriving(drivingRecord.getTrucks_id(), true, null);
	            }
			}else if(drivingRecord.getGuache_trucks_id()!=null&&!"".equals(drivingRecord.getGuache_trucks_id())){//此主车不能挂车，不合法的挂车,
//					connection.rollback();
				return 7;//保存失败
			}				
		}else{
			return 1;//车不存在
		}
		
		drivingRecord.setCreate_time(new Date());
		drivingRecord.setStatus(DrivingRecord.STATUS_CREATE);
		a=(int) getSqlMapClientTemplate().insert("DrivingRecord.saveInfo", drivingRecord);
		if(a<=0){
//				connection.rollback();
			return 7;//保存失败
		}
        if(a>0) {
        	int id = a; 
        	Trucks param=new Trucks();
        	param.setTrucks_id(drivingRecord.getTrucks_id());
        	param.setLast_driving_record_id(id);
        	
        	getSqlMapClientTemplate().update("Trucks.updateLastDriveId", param);
        }
		if(flag){
			HardwareElement element=SendManager.getInstance().getHardwareElementByCarNum(drivingRecord.getTrucks_id());
			if(element!=null){
				trucksDeviceDao.reloadTrucksDevice(element);
			}
		}
		logger.info("架驶员开始行程:"+drivingRecord.getTrucks_id());
		return 0;			
	}
	
	@Override
	public int endDriving(DrivingRecord drivingRecord){
			
		DrivingRecord drivingRecord2=(DrivingRecord) getSqlMapClientTemplate().queryForObject("DrivingRecord.getByUserId", drivingRecord.getDriver_id());
		
		if(drivingRecord2!=null){
			if(drivingRecord2.getStatus().intValue()==DrivingRecord.STATUS_CREATE){
				if(drivingRecord.getLi_cheng_end().doubleValue()>drivingRecord2.getLi_cheng_run().doubleValue()){
					int status=DrivingRecord.STATUS_END;
					if(drivingRecord2.getStart_time().getTime()>System.currentTimeMillis()){
						status=DrivingRecord.STATUS_STOP;
					}
					Map<String, Object> param=new HashMap<String, Object>();
					param.put("status", status);
					param.put("endLicheng", drivingRecord.getLi_cheng_end());
					param.put("end_time", new Date());
					param.put("dis", drivingRecord.getLi_cheng_end().doubleValue()-drivingRecord2.getLi_cheng_run().doubleValue());
					param.put("id", drivingRecord2.getId());
					int i=getSqlMapClientTemplate().update("DrivingRecord.updateInfo", param);		
					if(i>0){
						int a=trucksDao.updateTrucksMabiao(drivingRecord2.getTrucks_id(), drivingRecord.getLi_cheng_end());//更新车码表数
						if(a!=0){
//								connection.rollback();
							return 3;//保存失败
						}
					}else{
//						connection.rollback();
						return 3;
					}
				}else{//结束码表数小于开始码表数
					return 2;
				}
				
			}else{
				return 1;//没有已开始的
			}
		}else{
			return 1;//没有要结束的
		}
		logger.info("架驶员结束行程:"+drivingRecord.getDriver_id());
		return 0;
		
	}
	
	@Override
	public int scoreDriving(DrivingRecord drivingRecord){
		DrivingRecord drivingRecord2=(DrivingRecord) getSqlMapClientTemplate().queryForObject("DrivingRecord.getByUserId", drivingRecord.getDriver_id());
		if(drivingRecord2!=null){
			drivingRecord.setId(drivingRecord2.getId());
			if(drivingRecord2.getStatus()!=DrivingRecord.STATUS_CREATE){
				if(drivingRecord2.getStar_heart()==null||drivingRecord2.getStar_heart()<=0){
					int i=getSqlMapClientTemplate().update("DrivingRecord.scoreDriving", drivingRecord);		
					if(i<=0){
//							connection.rollback();
						return 4;
					}
				}else{
					return 2;//已评分
				}
				
			}else{//还未结束不能评分
				return 1;//没有结束的
			}
		}else{
			return 3;//没有架驶记录
		}
		logger.info("架驶员评分行程:"+drivingRecord.getDriver_id());
		return 0;
		
	}
	
	@Override
	public UserDrivingRecordCountVO getDrivingRecordCount(Integer user_id) {
		UserDrivingRecordCountVO  userDrivingRecordCountVO= (UserDrivingRecordCountVO) getSqlMapClientTemplate().queryForObject("UserDrivingRecordCountVO.getCount", user_id);
		
		logger.info("获取驾驶员驾驶记录汇总："+user_id);
		return userDrivingRecordCountVO;
	}
	
	
	
}
