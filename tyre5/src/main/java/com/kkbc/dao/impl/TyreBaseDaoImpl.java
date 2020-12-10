
package com.kkbc.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kkbc.dao.TrucksDao;
import com.kkbc.dao.TrucksDeviceDao;
import com.kkbc.dao.TyreBaseDao;
import com.kkbc.dao.WorkOrderDao;
import com.kkbc.entity.DeviceFasheqi;
import com.kkbc.entity.Trucks;
import com.kkbc.entity.TyreBase;
import com.kkbc.entity.TyreDynamic;
import com.kkbc.entity.TyreHistory;
import com.kkbc.entity.WorkOrder;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.hardware.send.SendManager;
import com.kkbc.vo.TyreByAdminVO;
import com.kkbc.vo.TyreCountVO;
import com.kkbc.vo.TyreRemarkVO;
import com.kkbc.vo.TyreVO;
import com.kkbc.vo.tuijian.TrucksByTuiJianVO;
import com.kkbc.vo.tuijian.TyreByTuiJianVO;
import com.psylife.util.Constants;
import com.psylife.util.TyreAlgorithmUtil;

@Repository
public class TyreBaseDaoImpl extends BaseDaoImpl implements TyreBaseDao{

	@Resource
	private TrucksDeviceDao trucksDeviceDao; 
	
	@Resource
	private WorkOrderDao workOrderDao;
	
	@Resource
	private TrucksDao trucksDao;
	static final Logger logger = LoggerFactory.getLogger(TyreBaseDao.class);
	

	@Transactional
	@Override
	public int saveByList(List<TyreBase> tyreBases,Integer user_id,boolean isWorkOrder) {
		int flag = 1;
		WorkOrder workOrder=null;
		if(isWorkOrder){
			workOrder=workOrderDao.getLastWorkOrder(user_id,null);
			if(workOrder==null||workOrder.getStatus().intValue()==WorkOrder.STATUS_END.intValue()){//没有工单或已结束
				return 10;
			}
		}
		String true_name=(String) getSqlMapClientTemplate().queryForObject("User.getTrueName", user_id);
		if (StringUtils.isEmpty(true_name)) {
			return 1;
		}
		int result=-1;
		
		
		TyreBase tyreBase;
		Date now=new Date();
		int state=0;
	
		List<TyreBase> tyreInfo=new ArrayList<TyreBase>();
		List<TyreHistory> historyInfo=new ArrayList<TyreHistory>();
		List<TyreDynamic> dynamicInfo=new ArrayList<TyreDynamic>();
		for(int i=0;i<tyreBases.size();i++){
			tyreBase=tyreBases.get(i);
			tyreBase.setCreate_time(now);
			tyreBase.setUser_id(user_id);
			tyreInfo.add(tyreBase);
			
			TyreHistory history=new TyreHistory();
			history.setTyre_time(now);
			history.setTyre_id(tyreBase.getTyre_id());
			history.setTyre_action(TyreHistory.ACTION_TYPE_INPUT);
			history.setTyre_content("入库");
			history.setTyre_person(true_name);
			history.setUser_id(user_id);
			
			historyInfo.add(history);
			
			TyreDynamic dynamic=new TyreDynamic();
			dynamic.setTyre_id(tyreBase.getTyre_id());
			
			dynamicInfo.add(dynamic);
			
			if(isWorkOrder){
				state+=workOrderDao.saveWorkOrderRecord(workOrder.getId(), TyreHistory.ACTION_TYPE_INPUT,"入库", tyreBase.getTyre_id());
			}
		}
		
		getSqlMapClientTemplate().insert("TyreBase.saveList", tyreInfo);
		getSqlMapClientTemplate().insert("TyreHistory.saveList", historyInfo);
		getSqlMapClientTemplate().insert("TyreDynamic.saveList", dynamicInfo);
		
		flag = 0;
		logger.info("批量保存轮胎基本信息");
		return flag;
	}
	
	@Override
	public TyreVO tyreDetial(Integer user_id,String tyre_id,String trucks_id,String tyre_where){
		TyreVO param=new TyreVO();
		param.setUser_id(user_id);
		param.setTyre_id(tyre_id);
		param.setTrucks_id(trucks_id);
		param.setTyre_where(tyre_where);
		if (StringUtils.isEmpty(tyre_id)&&(StringUtils.isEmpty(trucks_id)||StringUtils.isEmpty(tyre_where))) {
			return null;
		}
		
		TyreVO tyreVo = (TyreVO) getSqlMapClientTemplate().queryForObject("TyreVO.tyreDetial", param);
		if (tyreVo!=null) {
			logger.info("获得胎详情");
		}
		return tyreVo;
	}
	
	@Transactional
	@Override
	public int tyreToTrucks(String trucks_id,String tyre_id,String tyre_where,Integer user_id,Double mabiao) {
		WorkOrder workOrder=workOrderDao.getLastWorkOrder(user_id,null);
		if(workOrder==null||workOrder.getStatus().intValue()==WorkOrder.STATUS_END.intValue()){//没有工单或已结束
			return 10;
		}
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("tyre_id", tyre_id);
		param.put("tyre_where", tyre_where);
		param.put("trucks_id", trucks_id);
		param.put("user_id", user_id);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> infos=getSqlMapClientTemplate().queryForList("TyreByAdminVO.getByTruckIdAndWhere", param);
		String true_name="";
		String trucks_id_flag="false";
		String trucks_id2=trucks_id;
		String gua_trucks_id="";
		Map<String, Object> info;
		if (infos!=null&&infos.size()>0) {
			info=infos.get(0);
			String temp=(String) info.get("Twhere");
			gua_trucks_id=(String) info.get("guache_trucks_id");
			if(info.get("GTtrucks_id")!=null){//为挂车
				temp=(String) info.get("GTwhere");
				if(info.get("trucks_id")!=null&&(int)info.get("dtu_multi_flag")!=1&&tyre_where.indexOf("C")>=0){
					trucks_id_flag="true";
					trucks_id2=(String) info.get("GTtrucks_id");
				}else if((int)info.get("dtu_multi_flag")==1&&tyre_where.indexOf("C")>=0){
					trucks_id2=(String) info.get("GTtrucks_id");
				}
			}				
			if(temp!=null&&!"".equals(temp)){
				return 2;//该位置存在胎
			}
			true_name=(String) info.get("true_name");
		}else{
			return 1;//车牌不存在
		}
		TyreVO tyreParam=new TyreVO();
		tyreParam.setUser_id(user_id);
		tyreParam.setTyre_id(tyre_id);
		TyreVO tyreStateInfo=(TyreVO) getSqlMapClientTemplate().queryForObject("TyreVO.getTyreState", tyreParam);
		if (tyreStateInfo==null) {
			return 4;//轮胎不存在
		}
		if(tyreStateInfo.getTyre_flag()==1){
			return 4;
		}
		double m=(double) info.get("mabiao");
		if(mabiao!=null){
			if(m<=mabiao.doubleValue()&&info.get("mabiao")!=null){
				if(m<mabiao.doubleValue()){
					int a=trucksDao.updateTrucksMabiao(trucks_id, mabiao);//更新车码表数
					if(a!=0){
//							connection.rollback();
						return 6;
					}
					m=mabiao;
				}
			}else{
				return 6;
			}	
		}
		param.put("trucks_id2", trucks_id2);
		param.put("trucks_id_flag", trucks_id_flag);
		int a=getSqlMapClientTemplate().update("Trucks.tyreToTruck", param);
		if(a<=0){
//				connection.rollback();
			return 3;//保存失败
		}
		Map<String, Object> updateParam=new HashMap<String, Object>();
		updateParam.put("tyreWhere", tyre_where);
		updateParam.put("trucksId", tyre_where.indexOf("C")<0?trucks_id2:gua_trucks_id);
		updateParam.put("mabiao", m);
		updateParam.put("tyreId", tyre_id);
		
		a=getSqlMapClientTemplate().update("TyreVO.updateBaseDynamicInfoAfterChange", updateParam);
		if(a<=0){
//				connection.rollback();
			return 3;//保存失败
		}
		
		TyreHistory historyInfo=new TyreHistory();
		historyInfo.setTyre_time(new Date());
		historyInfo.setTyre_id(tyre_id);
		historyInfo.setTyre_action(TyreHistory.ACTION_TYPE_UP);
		historyInfo.setTyre_content(trucks_id+":"+tyre_where);
		historyInfo.setTyre_person(true_name);
		historyInfo.setUser_id(user_id);
		a=(int) getSqlMapClientTemplate().insert("TyreHistory.saveInfo", historyInfo);
		if(a<=0){
//				connection.rollback();
			return 5;//保存胎日志失败
		}
		a=workOrderDao.saveWorkOrderRecord(workOrder.getId(), TyreHistory.ACTION_TYPE_UP,trucks_id+":"+tyre_where,tyre_id);
		if(a!=0){
//				connection.rollback();
			return 5;//保存胎工单失败
		}
//			connection.commit();
		//更新车的健康度
		TyreVO tyreInfo=new TyreVO();
		tyreInfo.setTyre_id(tyre_id);
		tyreInfo.setTyre_health(tyreStateInfo.getTyre_health());
		if (tyreInfo.getTyre_health()!=null&&tyreInfo.getTyre_health().floatValue()!=0) {
			a=trucksDao.updateTrucksHealth( trucks_id, 1, tyreInfo, null, tyre_where);
			if (a==0) {
				return 7;
			}
		}
		logger.info("胎装载保存成功,胎:"+tyre_id+",车牌号:"+trucks_id+",位置:"+tyre_where);
		return 0;			
	}
	
	@Transactional
	@Override
	public int tyreDown(String trucks_id,String tyre_id,String tyre_where,Integer user_id,Double mabiao) {
		if (StringUtils.isEmpty(tyre_where)) {
			return 1;//不存在车牌或位置上没有此胎
		}
		WorkOrder workOrder=workOrderDao.getLastWorkOrder(user_id,null);
		if(workOrder==null||workOrder.getStatus().intValue()==WorkOrder.STATUS_END.intValue()){//没有工单或已结束
			return 10;
		}
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("tyre_id", tyre_id);
		param.put("tyre_where", tyre_where);
		param.put("trucks_id", trucks_id);
		param.put("user_id", user_id);
//		SqlRowSet resultSet=getJdbcTemplate().queryForRowSet("SELECT GT.trucks_"+tyre_where+",T.trucks_"+tyre_where+",T.dtu_multi_flag,T.trucks_id,GT.trucks_id,U.true_name,T.mabiao "+
//				"FROM  trucks GT LEFT JOIN Trucks T  ON (GT.trucks_id=T.guache_trucks_id AND T.trucks_type='主车') LEFT JOIN `user` U ON U.user_company_id=GT.company_id  "+
//				"WHERE ((T.trucks_id=? AND T.guache_save_flag=1) or (GT.trucks_id=? AND GT.trucks_type='挂车')) and U.user_id=?  "+
//				"UNION  "+
//				"SELECT GT.trucks_"+tyre_where+",T.trucks_"+tyre_where+",T.dtu_multi_flag,T.trucks_id,GT.trucks_id,U.true_name,T.mabiao  "+
//				"FROM   Trucks T LEFT JOIN `user` U ON U.user_company_id=T.company_id LEFT JOIN trucks GT ON (GT.trucks_id=T.guache_trucks_id) "+
//				"WHERE ((T.trucks_id=?"+(tyre_where.indexOf("C")>=0?" AND T.guache_save_flag=0":"")+" ) or (GT.trucks_id=? AND GT.trucks_type='挂车')) and U.user_id=? AND T.trucks_type='主车'", 
//				new Object[]{trucks_id,trucks_id,user_id,trucks_id,trucks_id,user_id});
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> infos=getSqlMapClientTemplate().queryForList("TyreByAdminVO.getByTruckIdAndWhere", param);
		Map<String, Object> info;
		String true_name="";
		if (infos==null||infos.size()==0) {
			return 1;//不存在车牌或位置上没有此胎
		}else{
			info=infos.get(0);
			true_name=(String) info.get("true_name");
		}
		if(mabiao!=null){
			if(info.get("mabiao")!=null&&(double)info.get("mabiao")<=mabiao.doubleValue()){
				if((double)info.get("mabiao")<mabiao.doubleValue()){
					int a=trucksDao.updateTrucksMabiao(trucks_id, mabiao);//更新车码表数
					if(a!=0){
//							connection.rollback();
						return 4;
					}
				}
			}else{
				return 4;
			}	
		}
		
		int a=getSqlMapClientTemplate().update("Trucks.downTyreFromTruck", param);
		if(a<=0){
//				connection.rollback();
			return 2;//保存失败
		}
		a=getSqlMapClientTemplate().update("TyreVO.downTyre", tyre_id);
		if(a<=0){
//				connection.rollback();
			return 2;//保存失败
		}
		TyreHistory historyInfo=new TyreHistory();
		historyInfo.setTyre_time(new Date());
		historyInfo.setTyre_id(tyre_id);
		historyInfo.setTyre_action(TyreHistory.ACTION_TYPE_DOWN);
		historyInfo.setTyre_content(trucks_id+":"+tyre_where);
		historyInfo.setTyre_person(true_name);
		historyInfo.setUser_id(user_id);
		a=(int) getSqlMapClientTemplate().insert("TyreHistory.saveInfo", historyInfo);
		if(a<=0){
//				connection.rollback();
			return 3;//保存胎日志失败
		}
		a=workOrderDao.saveWorkOrderRecord(workOrder.getId(), TyreHistory.ACTION_TYPE_DOWN,trucks_id+":"+tyre_where,tyre_id);
		if(a!=0){
//				connection.rollback();
			return 3;//保存胎工单失败
		}
//			connection.commit();
		//更新车的健康度
		TyreVO tyreInfo=new TyreVO();
		tyreInfo.setTyre_id(tyre_id);
		a=trucksDao.updateTrucksHealth( trucks_id, 2, tyreInfo, null, tyre_where);
		if (a==0) {
			return 7;
		}
		logger.info("胎卸下保存成功,胎:"+tyre_id+",车牌号:"+trucks_id+",位置:"+tyre_where);
		return 0;			
	}
	
	@Transactional
	@Override
	public int tyreExchange(String tyre_id1,String tyre_id2,Integer user_id,Double mabiao1,Double mabiao2){
		if(tyre_id1==null||tyre_id2==null||"".equals(tyre_id1)||"".equals(tyre_id2)||tyre_id1.equalsIgnoreCase(tyre_id2)){
			return 4;
		}
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("user_id", user_id);
		param.put("tyre_id1", tyre_id1);
		param.put("tyre_id2", tyre_id2);
		
		
		WorkOrder workOrder=workOrderDao.getLastWorkOrder(user_id,null);
		if(workOrder==null||workOrder.getStatus().intValue()==WorkOrder.STATUS_END.intValue()){//没有工单或已结束
			return 10;
		}
		@SuppressWarnings("unchecked")
		List<TyreVO> tyreVos=getSqlMapClientTemplate().queryForList("TyreVO.getTwoTyreInfo", param);
		if(tyreVos!=null&&tyreVos.size()>=2){
			TyreVO tyreVO1=null,tyreVO2=null;
			for(TyreVO tyreVO :tyreVos){
				if(tyre_id1.equals(tyreVO.getTyre_id())){
					if(tyreVO1==null){
						tyreVO1=tyreVO;
                        if(!"主车".equals(tyreVO.getTrucks_type())){
                        	tyreVO1.setLi_cheng_run(null);
						}
					}else{
						if("主车".equals(tyreVO.getTrucks_type())){
							tyreVO1.setLi_cheng_run(tyreVO.getLi_cheng_run());
						}else{//若是挂车
							tyreVO.setLi_cheng_run(tyreVO1.getLi_cheng_run());
							tyreVO1=tyreVO;
						}
					}
				}else if(tyre_id2.equals(tyreVO.getTyre_id())){
					if(tyreVO2==null){
						tyreVO2=tyreVO;
                        if(!"主车".equals(tyreVO.getTrucks_type())){
                        	tyreVO2.setLi_cheng_run(null);
						}
					}else{
						if("主车".equals(tyreVO.getTrucks_type())){
							tyreVO2.setLi_cheng_run(tyreVO.getLi_cheng_run());
						}else{//若是挂车
							tyreVO.setLi_cheng_run(tyreVO2.getLi_cheng_run());
							tyreVO2=tyreVO;
						}
					}
				}
			}
			tyreVos=new ArrayList<TyreVO>();
			if(tyreVO1!=null){
				tyreVos.add(tyreVO1);
			}
			if(tyreVO2!=null){
				tyreVos.add(tyreVO2);
			}
		}
		if(tyreVos==null||tyreVos.size()!=2){
			return 1;//不存在两个胎信息
		}
		if(tyreVos.get(0).getTrucks_id()==null&&tyreVos.get(1).getTrucks_id()==null){
			return 2;//两个胎都不在车上，交换没有意义
		}
		String true_name=tyreVos.get(0).getQueryer();
		int a;
		String action0="";
		String content0="";
		String action1="";
		String content1="";
		if(tyreVos.get(0).getTrucks_id()!=null&&tyreVos.get(1).getTrucks_id()!=null){//两个胎都在车上
			double m1=tyreVos.get(0).getLi_cheng_run()==null?0:tyreVos.get(0).getLi_cheng_run(),
					m2=tyreVos.get(1).getLi_cheng_run()==null?0:tyreVos.get(1).getLi_cheng_run();
			if(mabiao1!=null){
				TyreVO t;
				if(tyre_id1.equals(tyreVos.get(0).getTyre_id())){
					t=tyreVos.get(0);
				}else{
					t=tyreVos.get(1);
				}
				if(t.getLi_cheng_run().doubleValue()<=mabiao1.doubleValue()){
					if(t.getLi_cheng_run().doubleValue()<mabiao1.doubleValue()){
						a=trucksDao.updateTrucksMabiao(t.getTrucks_id(), mabiao1);//更新车码表数
						if(a!=0){
							return 4;
						}
						if(t.equals(tyreVos.get(0).getTyre_id())){
							m1=mabiao1;
						}else{
							m2=mabiao1;
						}
					}
				}else{
					return 4;
				}
			}
			if(mabiao2!=null){
				TyreVO t;
				if(tyre_id2.equals(tyreVos.get(0).getTyre_id())){
					t=tyreVos.get(0);
				}else{
					t=tyreVos.get(1);
				}
				if(t.getLi_cheng_run().doubleValue()<=mabiao2.doubleValue()){
					if(t.getLi_cheng_run().doubleValue()<mabiao2.doubleValue()){
						a=trucksDao.updateTrucksMabiao(t.getTrucks_id(), mabiao2);//更新车码表数
						if(a!=0){
//								connection.rollback();
							return 4;
						}
						if(t.equals(tyreVos.get(0).getTyre_id())){
							m1=mabiao1;
						}else{
							m2=mabiao1;
						}
					}
				}else{
					return 4;
				}
			}
			
			//为了避免不同车的同一胎位的轮胎进行交换，采用中间变量
			String temp="X";
			Map<String, Object> param1=new HashMap<String, Object>();
			param1.put("tyreWhere", tyreVos.get(1).getTyre_where());
			param1.put("srcTyreId", tyreVos.get(1).getTyre_id());
			param1.put("desTyreId", temp);
			a=getSqlMapClientTemplate().update("TyreVO.changeTyre", param1);
			if(a<=0){
//					connection.rollback();
				return 3;//保存失败
			}
			
			param1.put("tyreWhere", tyreVos.get(0).getTyre_where());
			param1.put("srcTyreId", tyreVos.get(0).getTyre_id());
			param1.put("desTyreId", tyreVos.get(1).getTyre_id());
			a=getSqlMapClientTemplate().update("TyreVO.changeTyre", param1);
			if(a<=0){
//					connection.rollback();
				return 3;//保存失败
			}
			param1.put("tyreWhere", tyreVos.get(1).getTyre_where());
			param1.put("srcTyreId", temp);
			param1.put("desTyreId", tyreVos.get(0).getTyre_id());
			a=getSqlMapClientTemplate().update("TyreVO.changeTyre", param1);
			if(a<=0){
				return 3;//保存失败
			}
			Map<String, Object> param2=new HashMap<String, Object>();
			param2.put("tyreWhere", tyreVos.get(1).getTyre_where());
			param2.put("trucksId", tyreVos.get(1).getTrucks_id());
			param2.put("mabiao", m2);
			param2.put("tyreId", tyreVos.get(0).getTyre_id());
			
			a=getSqlMapClientTemplate().update("TyreVO.updateBaseDynamicInfoAfterChange", param2);
			if(a<=0){
				return 3;//保存失败
			}
			
			param2.put("tyreWhere", tyreVos.get(0).getTyre_where());
			param2.put("trucksId", tyreVos.get(0).getTrucks_id());
			param2.put("mabiao", m1);
			param2.put("tyreId", tyreVos.get(1).getTyre_id());
			a=getSqlMapClientTemplate().update("TyreVO.updateBaseDynamicInfoAfterChange", param2);
			if(a<=0){
				return 3;//保存失败
			}	
			action0=TyreHistory.ACTION_TYPE_EXCHANGE;
			action1=TyreHistory.ACTION_TYPE_EXCHANGE;
			content0=tyreVos.get(0).getTrucks_id()+":"+tyreVos.get(0).getTyre_where()+"<>"+tyreVos.get(1).getTrucks_id()+":"+tyreVos.get(1).getTyre_where();
			content1=tyreVos.get(1).getTrucks_id()+":"+tyreVos.get(1).getTyre_where()+"<>"+tyreVos.get(0).getTrucks_id()+":"+tyreVos.get(0).getTyre_where();
			
			//更新车的健康度
			TyreVO tyreInfo=new TyreVO();
			tyreInfo.setTyre_id(tyreVos.get(0).getTyre_id());
			tyreInfo.setTyre_health(tyreVos.get(0).getTyre_health());
			
			TyreVO changeTyreVO=new TyreVO();
			changeTyreVO.setTyre_id(tyreVos.get(1).getTyre_id());
			changeTyreVO.setTyre_health(tyreVos.get(1).getTyre_health());
			
			a=trucksDao.updateTrucksHealth( tyreVos.get(0).getTrucks_id(), 3, tyreInfo, changeTyreVO, tyreVos.get(0).getTyre_where());
			if (a==0) {
				return 7;
			}
			a=trucksDao.updateTrucksHealth( tyreVos.get(1).getTrucks_id(), 3, changeTyreVO, tyreInfo, tyreVos.get(1).getTyre_where());
			if (a==0) {
				return 7;
			}
		}else if(tyreVos.get(0).getTrucks_id()!=null&&tyreVos.get(1).getTrucks_id()==null){//一个在车上，另一个不在车上
			
			
			Double m=null;
			if(tyre_id1.equals(tyreVos.get(0).getTyre_id())){
				m=mabiao1;
			}else{
				m=mabiao2;
			}
			if(m!=null){
				TyreVO t=tyreVos.get(0);
				if(t.getLi_cheng_run().doubleValue()<=m.doubleValue()){
					if(t.getLi_cheng_run().doubleValue()<m.doubleValue()){
						a=trucksDao.updateTrucksMabiao(t.getTrucks_id(), m);//更新车码表数
						if(a!=0){
							return 4;
						}
						t.setLi_cheng_run(m);
					}
				}else{
					return 4;
				}
			}
			Map<String, Object> param1=new HashMap<String, Object>();
			param1.put("tyreWhere", tyreVos.get(0).getTyre_where());
			param1.put("srcTyreId", tyreVos.get(0).getTyre_id());
			param1.put("desTyreId", tyreVos.get(1).getTyre_id());
			a=getSqlMapClientTemplate().update("TyreVO.changeTyre", param1);
			if(a<=0){
				return 3;//保存失败
			}
			
			a=getSqlMapClientTemplate().update("TyreVO.downTyre", tyreVos.get(0).getTyre_id());
			if(a<=0){
				return 3;//保存失败
			}
			Map<String, Object> param2=new HashMap<String, Object>();
			param2.put("tyreWhere", tyreVos.get(0).getTyre_where());
			param2.put("trucksId", tyreVos.get(0).getTrucks_id());
			param2.put("mabiao", tyreVos.get(0).getLi_cheng_run());
			param2.put("tyreId", tyreVos.get(1).getTyre_id());
			
			a=getSqlMapClientTemplate().update("TyreVO.updateBaseDynamicInfoAfterChange", param2);
			if(a<=0){
				return 3;//保存失败
			}	
			action0=TyreHistory.ACTION_TYPE_DOWN;
			action1=TyreHistory.ACTION_TYPE_UP;
			content0=tyreVos.get(0).getTrucks_id()+":"+tyreVos.get(0).getTyre_where();
			content1=tyreVos.get(0).getTrucks_id()+":"+tyreVos.get(0).getTyre_where();
			
			//更新车的健康度
			TyreVO tyreInfo=new TyreVO();
			tyreInfo.setTyre_id(tyreVos.get(0).getTyre_id());
			tyreInfo.setTyre_health(tyreVos.get(0).getTyre_health());
			
			TyreVO changeTyreVO=new TyreVO();
			changeTyreVO.setTyre_id(tyreVos.get(1).getTyre_id());
			changeTyreVO.setTyre_health(tyreVos.get(1).getTyre_health());
			
			a=trucksDao.updateTrucksHealth( tyreVos.get(0).getTrucks_id(), 3, tyreInfo, changeTyreVO, tyreVos.get(0).getTyre_where());
			if (a==0) {
				return 7;
			}
		}else{//一个在车上，另一个不在车上
			
			
			
			Double m=null;
			if(tyre_id1.equals(tyreVos.get(1).getTyre_id())){
				m=mabiao1;
			}else{
				m=mabiao2;
			}
			if(m!=null){
				TyreVO t=tyreVos.get(1);
				if(t.getLi_cheng_run().doubleValue()<=m.doubleValue()){
					if(t.getLi_cheng_run().doubleValue()<m.doubleValue()){
						a=trucksDao.updateTrucksMabiao(t.getTrucks_id(), m);//更新车码表数
						if(a!=0){
							return 4;
						}
						t.setLi_cheng_run(m);
					}
				}else{
					return 4;
				}
			}
			Map<String, Object> param1=new HashMap<String, Object>();
			param1.put("tyreWhere", tyreVos.get(1).getTyre_where());
			param1.put("srcTyreId", tyreVos.get(1).getTyre_id());
			param1.put("desTyreId", tyreVos.get(0).getTyre_id());
			a=getSqlMapClientTemplate().update("TyreVO.changeTyre", param1);
			if(a<=0){
				return 3;//保存失败
			}
			a=getSqlMapClientTemplate().update("TyreVO.downTyre", tyreVos.get(1).getTyre_id());
			if(a<=0){
				return 3;//保存失败
			}
			Map<String, Object> param2=new HashMap<String, Object>();
			param2.put("tyreWhere", tyreVos.get(1).getTyre_where());
			param2.put("trucksId", tyreVos.get(1).getTrucks_id());
			param2.put("mabiao", tyreVos.get(1).getLi_cheng_run());
			param2.put("tyreId", tyreVos.get(0).getTyre_id());
			a=getSqlMapClientTemplate().update("TyreVO.updateBaseDynamicInfoAfterChange", param2);;
			if(a<=0){
				return 3;//保存失败
			}	
			action0=TyreHistory.ACTION_TYPE_UP;
			action1=TyreHistory.ACTION_TYPE_DOWN;
			content0=tyreVos.get(1).getTrucks_id()+":"+tyreVos.get(1).getTyre_where();
			content1=tyreVos.get(1).getTrucks_id()+":"+tyreVos.get(1).getTyre_where();
			
			//更新车的健康度
			TyreVO tyreInfo=new TyreVO();
			tyreInfo.setTyre_id(tyreVos.get(1).getTyre_id());
			tyreInfo.setTyre_health(tyreVos.get(1).getTyre_health());
			
			TyreVO changeTyreVO=new TyreVO();
			changeTyreVO.setTyre_id(tyreVos.get(0).getTyre_id());
			changeTyreVO.setTyre_health(tyreVos.get(0).getTyre_health());
			
			a=trucksDao.updateTrucksHealth( tyreVos.get(1).getTrucks_id(), 3, tyreInfo, changeTyreVO, tyreVos.get(1).getTyre_where());
			if (a==0) {
				return 7;
			}
		}
		Date now=new Date();
//		String newsql="("+StringUtils.strip(Arrays.asList(new Object[]{now,tyreVos.get(0).getTyre_id(),action0,content0,true_name,user_id}).toString(), "[]")+"),"
//						+"("+StringUtils.strip(Arrays.asList(new Object[]{now,tyreVos.get(1).getTyre_id(),action1,content1,true_name,user_id}).toString(), "[]")+")";
		
		List<TyreHistory> historyInfo=new ArrayList<TyreHistory>();
		TyreHistory history1=new TyreHistory();
		history1.setTyre_time(now);
		history1.setTyre_id(tyreVos.get(0).getTyre_id());
		history1.setTyre_action(action0);
		history1.setTyre_content(content0);
		history1.setTyre_person(true_name);
		history1.setUser_id(user_id);
		
		TyreHistory history2=new TyreHistory();
		history2.setTyre_time(now);
		history2.setTyre_id(tyreVos.get(1).getTyre_id());
		history2.setTyre_action(action1);
		history2.setTyre_content(content1);
		history2.setTyre_person(true_name);
		history2.setUser_id(user_id);
		
		historyInfo.add(history1);
		getSqlMapClientTemplate().insert("TyreHistory.saveList", historyInfo);
//		if(a<=0){
//			return 3;//保存胎日志失败
//		}
		a=workOrderDao.saveWorkOrderRecord(workOrder.getId(), action0,content0,tyreVos.get(0).getTyre_id());
		if(a!=0){
			return 3;//保存胎工单失败
		}
		a=workOrderDao.saveWorkOrderRecord(workOrder.getId(), action1,content1,tyreVos.get(1).getTyre_id());
		if(a!=0){
			return 3;//保存胎工单失败
		}
		logger.info("换胎保存成功,胎:"+tyreVos.get(0).getTyre_id()+",车牌号:"+tyreVos.get(0).getTrucks_id()+",位置:"+tyreVos.get(0).getTyre_where()+"<>胎:"+tyreVos.get(1).getTyre_id()+",车牌号:"+tyreVos.get(1).getTrucks_id()+",位置:"+tyreVos.get(1).getTyre_where());
		
		//重新加载设备和车牌对应关系
		HardwareElement element=SendManager.getInstance().getHardwareElementByCarNum(tyreVos.get(0).getTrucks_id());
		if(element!=null){
			trucksDeviceDao.reloadTrucksDevice(element);
		}
		
		element=SendManager.getInstance().getHardwareElementByCarNum(tyreVos.get(1).getTrucks_id());
		if(element!=null){
			trucksDeviceDao.reloadTrucksDevice(element);
		}
		return 0;			
	}
	
	
	@Override
	public List<JSONObject> tuiJian(int id) {
		System.out.println("开始查询推荐");
		TrucksByTuiJianVO t = null;
		List<JSONObject> result=new ArrayList<JSONObject>();
		JSONArray json;
		@SuppressWarnings("unchecked")
		List<Trucks> trucksInfo = getSqlMapClientTemplate().queryForList("Trucks.getListByCompany", id);
		for (Trucks trucks : trucksInfo) {
			t = new TrucksByTuiJianVO();
			t.setTrucks_id(trucks.getTrucks_id());
			t.setTrucks_A1(trucks.getTrucks_A1());
			t.setTrucks_A2(trucks.getTrucks_A2());
			t.setTrucks_A3(trucks.getTrucks_A3());
			t.setTrucks_A4(trucks.getTrucks_A4());
			t.setTrucks_A5(trucks.getTrucks_A5());
			t.setTrucks_A6(trucks.getTrucks_A6());
			
			t.setTrucks_B1(trucks.getTrucks_B1());
			t.setTrucks_B2(trucks.getTrucks_B2());
			t.setTrucks_B3(trucks.getTrucks_B3());
			t.setTrucks_B4(trucks.getTrucks_B4());
			t.setTrucks_B5(trucks.getTrucks_B5());
			t.setTrucks_B6(trucks.getTrucks_B6());
			t.setTrucks_B7(trucks.getTrucks_B7());
			t.setTrucks_B8(trucks.getTrucks_B8());
			
			t.setTrucks_C1(trucks.getTrucks_C1());
			t.setTrucks_C2(trucks.getTrucks_C2());
			t.setTrucks_C3(trucks.getTrucks_C3());
			t.setTrucks_C4(trucks.getTrucks_C4());
			t.setTrucks_C5(trucks.getTrucks_C5());
			t.setTrucks_C6(trucks.getTrucks_C6());
			
			t.setTrucks_C7(trucks.getTrucks_C7());
			t.setTrucks_C8(trucks.getTrucks_C8());
			t.setTrucks_C9(trucks.getTrucks_C9());
			t.setTrucks_C10(trucks.getTrucks_C10());
			t.setTrucks_C11(trucks.getTrucks_C11());
			t.setTrucks_C12(trucks.getTrucks_C12());
			
			t.setTrucks_C13(trucks.getTrucks_C13());
			t.setTrucks_C14(trucks.getTrucks_C14());
			t.setTrucks_C15(trucks.getTrucks_C15());
			t.setTrucks_C16(trucks.getTrucks_C16());
			try {
				@SuppressWarnings("unchecked")
				List<TyreVO> tyreInfo = getSqlMapClientTemplate().queryForList("TyreVO.getByTrucksId", trucks.getTrucks_id());
				TyreByTuiJianVO tyreVO;
				for (TyreVO tyre : tyreInfo) {
					tyreVO=new TyreByTuiJianVO();
					tyreVO.setTyre_id(tyre.getTyre_id());
					if(tyreVO.getTyre_id()!=null){
						tyreVO.setPattern(tyre.getTyre_type3());
						tyreVO.setStandard(tyre.getTyre_type1());
						tyreVO.setTyre_paver(tyre.getTyre_paver());
						tyreVO.setTyre_where(tyre.getTyre_where());
						tyreVO.setTrucks_id(t.getTrucks_id());
						if(tyreVO.getPattern()==null){
							tyreVO.setPattern("");
						}
						if(tyreVO.getStandard()==null){
							tyreVO.setStandard("");
						}
						if(tyreVO.getTyre_paver()==null){
							tyreVO.setTyre_paver(0f);
						}
						if(tyreVO.getTyre_where()==null){
							tyreVO.setTyre_where("");
						}
						t.getTyreVOs().add(tyreVO);
					}
					
				}
				try {
					System.out.println("计算推荐tyre_id="+t.getTrucks_id());
					json=algorithm(t);
					if(json!=null){
//							result.addAll(c)
						result.addAll(json);
					}
					System.out.println("计算推荐tyre_id="+t.getTrucks_id()+"结束");
				} catch (Exception e) {
					e.printStackTrace();
				}							
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		System.out.println("结束查询推荐");
		return result;
	}
	
	/**
	 * 计算是否推荐
	 * 
	 * @param trucksVO
	 */
	private JSONArray algorithm(TrucksByTuiJianVO trucksVO){
//		JSONObject result=new JSONObject();
		Map<String, TyreByTuiJianVO> map=new HashMap<String, TyreByTuiJianVO>();
		for(TyreByTuiJianVO t:trucksVO.getTyreVOs()){
			map.put(t.getTyre_id(), t);
		}	
		
		
		//A类
		List<TyreByTuiJianVO> aList=new ArrayList<TyreByTuiJianVO>();
		if(trucksVO.getTrucks_A1()!=null&&!"".equals(trucksVO.getTrucks_A1())){
			if(map.get(trucksVO.getTrucks_A1())!=null){
				aList.add(map.get(trucksVO.getTrucks_A1()));
			}
		}
		if(trucksVO.getTrucks_A2()!=null&&!"".equals(trucksVO.getTrucks_A2())){
			if(map.get(trucksVO.getTrucks_A2())!=null){
				aList.add(map.get(trucksVO.getTrucks_A2()));
			}
		}
		if(trucksVO.getTrucks_A3()!=null&&!"".equals(trucksVO.getTrucks_A3())){
			if(map.get(trucksVO.getTrucks_A3())!=null){
				aList.add(map.get(trucksVO.getTrucks_A3()));
			}
		}
		if(trucksVO.getTrucks_A4()!=null&&!"".equals(trucksVO.getTrucks_A4())){
			if(map.get(trucksVO.getTrucks_A4())!=null){
				aList.add(map.get(trucksVO.getTrucks_A4()));
			}
		}
		if(trucksVO.getTrucks_A5()!=null&&!"".equals(trucksVO.getTrucks_A5())){
			if(map.get(trucksVO.getTrucks_A5())!=null){
				aList.add(map.get(trucksVO.getTrucks_A5()));
			}
		}
		if(trucksVO.getTrucks_A6()!=null&&!"".equals(trucksVO.getTrucks_A6())){
			if(map.get(trucksVO.getTrucks_A6())!=null){
				aList.add(map.get(trucksVO.getTrucks_A6()));
			}
		}
		
		//B类
		List<TyreByTuiJianVO> bList=new ArrayList<TyreByTuiJianVO>();
		if(trucksVO.getTrucks_B1()!=null&&!"".equals(trucksVO.getTrucks_B1())){
			if(map.get(trucksVO.getTrucks_B1())!=null){
				bList.add(map.get(trucksVO.getTrucks_B1()));
			}
		}
		if(trucksVO.getTrucks_B2()!=null&&!"".equals(trucksVO.getTrucks_B2())){
			if(map.get(trucksVO.getTrucks_B2())!=null){
				bList.add(map.get(trucksVO.getTrucks_B2()));
			}
		}
		if(trucksVO.getTrucks_B3()!=null&&!"".equals(trucksVO.getTrucks_B3())){
			if(map.get(trucksVO.getTrucks_B3())!=null){
				bList.add(map.get(trucksVO.getTrucks_B3()));
			}
		}
		if(trucksVO.getTrucks_B4()!=null&&!"".equals(trucksVO.getTrucks_B4())){
			if(map.get(trucksVO.getTrucks_B4())!=null){
				bList.add(map.get(trucksVO.getTrucks_B4()));
			}
		}
		if(trucksVO.getTrucks_B5()!=null&&!"".equals(trucksVO.getTrucks_B5())){
			if(map.get(trucksVO.getTrucks_B5())!=null){
				bList.add(map.get(trucksVO.getTrucks_B5()));
			}
		}
		if(trucksVO.getTrucks_B6()!=null&&!"".equals(trucksVO.getTrucks_B6())){
			if(map.get(trucksVO.getTrucks_B6())!=null){
				bList.add(map.get(trucksVO.getTrucks_B6()));
			}
		}
		if(trucksVO.getTrucks_B7()!=null&&!"".equals(trucksVO.getTrucks_B7())){
			if(map.get(trucksVO.getTrucks_B7())!=null){
				bList.add(map.get(trucksVO.getTrucks_B7()));
			}
		}
		if(trucksVO.getTrucks_B8()!=null&&!"".equals(trucksVO.getTrucks_B8())){
			if(map.get(trucksVO.getTrucks_B8())!=null){
				bList.add(map.get(trucksVO.getTrucks_B8()));
			}
		}
		
		//C类
		List<TyreByTuiJianVO> cList=new ArrayList<TyreByTuiJianVO>();
		if(trucksVO.getTrucks_C1()!=null&&!"".equals(trucksVO.getTrucks_C1())){
			if(map.get(trucksVO.getTrucks_C1())!=null){
				cList.add(map.get(trucksVO.getTrucks_C1()));
			}
		}
		if(trucksVO.getTrucks_C2()!=null&&!"".equals(trucksVO.getTrucks_C2())){
			if(map.get(trucksVO.getTrucks_C2())!=null){
				cList.add(map.get(trucksVO.getTrucks_C2()));
			}
		}
		if(trucksVO.getTrucks_C3()!=null&&!"".equals(trucksVO.getTrucks_C3())){
			if(map.get(trucksVO.getTrucks_C3())!=null){
				cList.add(map.get(trucksVO.getTrucks_C3()));
			}
		}
		if(trucksVO.getTrucks_C4()!=null&&!"".equals(trucksVO.getTrucks_C4())){
			if(map.get(trucksVO.getTrucks_C4())!=null){
				cList.add(map.get(trucksVO.getTrucks_C4()));
			}
		}
		if(trucksVO.getTrucks_C5()!=null&&!"".equals(trucksVO.getTrucks_C5())){
			if(map.get(trucksVO.getTrucks_C5())!=null){
				cList.add(map.get(trucksVO.getTrucks_C5()));
			}
		}
		if(trucksVO.getTrucks_C6()!=null&&!"".equals(trucksVO.getTrucks_C6())){
			if(map.get(trucksVO.getTrucks_C6())!=null){
				cList.add(map.get(trucksVO.getTrucks_C6()));
			}
		}
		
		if(trucksVO.getTrucks_C7()!=null&&!"".equals(trucksVO.getTrucks_C7())){
			if(map.get(trucksVO.getTrucks_C7())!=null){
				cList.add(map.get(trucksVO.getTrucks_C7()));
			}
		}
		
		if(trucksVO.getTrucks_C8()!=null&&!"".equals(trucksVO.getTrucks_C8())){
			if(map.get(trucksVO.getTrucks_C8())!=null){
				cList.add(map.get(trucksVO.getTrucks_C8()));
			}
		}
		
		if(trucksVO.getTrucks_C9()!=null&&!"".equals(trucksVO.getTrucks_C9())){
			if(map.get(trucksVO.getTrucks_C9())!=null){
				cList.add(map.get(trucksVO.getTrucks_C9()));
			}
		}
		
		if(trucksVO.getTrucks_C10()!=null&&!"".equals(trucksVO.getTrucks_C10())){
			if(map.get(trucksVO.getTrucks_C10())!=null){
				cList.add(map.get(trucksVO.getTrucks_C10()));
			}
		}
		
		if(trucksVO.getTrucks_C11()!=null&&!"".equals(trucksVO.getTrucks_C11())){
			if(map.get(trucksVO.getTrucks_C11())!=null){
				cList.add(map.get(trucksVO.getTrucks_C11()));
			}
		}
		
		if(trucksVO.getTrucks_C12()!=null&&!"".equals(trucksVO.getTrucks_C12())){
			if(map.get(trucksVO.getTrucks_C12())!=null){
				cList.add(map.get(trucksVO.getTrucks_C12()));
			}
		}
		
		if(trucksVO.getTrucks_C13()!=null&&!"".equals(trucksVO.getTrucks_C13())){
			if(map.get(trucksVO.getTrucks_C13())!=null){
				cList.add(map.get(trucksVO.getTrucks_C13()));
			}
		}
		
		if(trucksVO.getTrucks_C14()!=null&&!"".equals(trucksVO.getTrucks_C14())){
			if(map.get(trucksVO.getTrucks_C14())!=null){
				cList.add(map.get(trucksVO.getTrucks_C14()));
			}
		}
		
		if(trucksVO.getTrucks_C15()!=null&&!"".equals(trucksVO.getTrucks_C15())){
			if(map.get(trucksVO.getTrucks_C15())!=null){
				cList.add(map.get(trucksVO.getTrucks_C15()));
			}
		}
		
		if(trucksVO.getTrucks_C16()!=null&&!"".equals(trucksVO.getTrucks_C16())){
			if(map.get(trucksVO.getTrucks_C16())!=null){
				cList.add(map.get(trucksVO.getTrucks_C16()));
			}
		}
		
		JSONArray json=TyreAlgorithmUtil.algorithm(aList, bList, cList);
		if(json==null){
			return null;
		}
//		List<TyreVO> allList=new ArrayList<TyreVO>();
//		allList.addAll(aList);
//		allList.addAll(bList);
//		allList.addAll(cList);
//		trucksVO.setTyreVOs(allList);
//		result.put("trucks", trucksVO);
//		result.put("tuijian", json);	
		return json;
	}
	
	@Override
	public List<TyreCountVO> countTyreInfo(Integer user_id, String tyre_brand,
			String tyre_type1, String tyre_type3, Integer tyre_health,String column,String order){
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("user_id", user_id);
		param.put("tyre_brand", tyre_brand);
		param.put("tyre_type1", tyre_type1);
		param.put("tyre_type3", tyre_type3);
		param.put("tyre_health", tyre_health);
		param.put("status", Constants.TYRE_STATUS_DELETE);
		param.put("column", column);
		param.put("order", order);

		@SuppressWarnings("unchecked")
		List<TyreCountVO> list=getSqlMapClientTemplate().queryForList("TyreCountVO.getList", param);
		
		logger.info("根据用户获取胎汇总列表成功！");
		return list;
	}
	
	@Override
	public List<TyreVO> getTyreList(int pagenum,Integer user_id,String tyre_brand,String tyre_type1,String tyre_type2,String tyre_type3,Integer tyre_flag,Integer tyre_health,String keyWord){
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("user_id", user_id);
		params.put("tyre_brand", tyre_brand);
		params.put("tyre_type1", tyre_type1);
		params.put("tyre_type2", tyre_type2);
		params.put("tyre_type3", tyre_type3);
		params.put("tyre_flag", tyre_flag);
		params.put("tyre_health", tyre_health);
		params.put("keyWord", keyWord);
		params.put("startIndex", (pagenum-1)*Constants.PAGESIZE);
		params.put("pageSize", Constants.PAGESIZE);
		params.put("status", Constants.TYRE_STATUS_DELETE);
		
		@SuppressWarnings("unchecked")
		List<TyreVO> list=getSqlMapClientTemplate().queryForList("TyreVO.getPageList", params);
		logger.info("根据用户品牌规格花纹获取胎列表成功！");
		return list;
	}
	
	@Override
	public List<TyreVO> searchByKeyWord(int pagenum,Integer user_id,String keyWord){
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("user_id", user_id);
		params.put("keyWord", keyWord);
		params.put("startIndex", (pagenum-1)*Constants.PAGESIZE);
		params.put("pageSize", Constants.PAGESIZE);
		params.put("status", Constants.TYRE_STATUS_DELETE);
		@SuppressWarnings("unchecked")
		List<TyreVO> list=getSqlMapClientTemplate().queryForList("TyreVO.searchByKeyWord", params);
		
		logger.info("根据关键字获取胎列表成功！");
		return list;
	}
	
	@Override
	public List<TyreVO> searchByKucun(int pagenum,Integer user_id,String keyWord, String tyre_brand,
			String tyre_type1, String tyre_type3,Integer state,Integer tyre_flag,String column,String order){
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("user_id", user_id);
		params.put("keyWord", keyWord);
		params.put("tyre_brand", tyre_brand);
		params.put("tyre_type1", tyre_type1);
		params.put("tyre_type3", tyre_type3);
		params.put("tyre_flag", tyre_flag);
		params.put("column", column);
		params.put("order", order);
		params.put("startIndex", (pagenum-1)*Constants.PAGESIZE);
		params.put("pageSize", Constants.PAGESIZE);
		params.put("status",state);
		@SuppressWarnings("unchecked")
		List<TyreVO> list=getSqlMapClientTemplate().queryForList("TyreVO.searchByKucun", params);
		logger.info("根据关键字等获取胎库存列表成功！");
		return list;
	}
	
	
	@Override
	public TyreByAdminVO tyreDetialByAdmin(String tyre_id){
		TyreByAdminVO tyreVo = (TyreByAdminVO) getSqlMapClientTemplate().queryForObject("TyreByAdminVO.tyreDetialByAdmin", tyre_id);
	    if(tyreVo!=null){
	    	if (tyreVo.getDtuOnlineStatus()==0) {//DTU掉线，车辆行驶状态改为停止
				tyreVo.setTrucks_flag(0);
			}
	    	//设备发射器
			DeviceFasheqi deviceFasheqi=(DeviceFasheqi) getSqlMapClientTemplate().queryForObject("DeviceFasheqi.getByTyreId", tyreVo.getTyre_id());
			
			tyreVo.setDeviceFasheqi(deviceFasheqi);
			if(deviceFasheqi!=null){
				if (tyreVo.getDtuOnlineStatus()==0) {//DTU掉线，轮胎检测状态改为未知
					deviceFasheqi.setGaowen(-1);
					deviceFasheqi.setGaoya(-1);
					deviceFasheqi.setDiya(-1);
					deviceFasheqi.setLouqi(-1);
				}
				tyreVo.setCaiji_time(deviceFasheqi.getCaiji_time());
			}
	    }
		TyreHistory param=new TyreHistory();
		param.setTyre_id(tyre_id);
		param.setTyre_action(TyreHistory.ACTION_TYPE_INPUT);
	    TyreHistory actionInfo=(TyreHistory) getSqlMapClientTemplate().queryForObject("TyreHistory.getActionInfoByTyreId", param);
	    if(actionInfo!=null){
	    	tyreVo.setCreate_time(actionInfo.getTyre_time());
	    }
		
		logger.info("管理员查车详情："+tyre_id);
		return tyreVo;
	}
	
	@Override
	public List<TyreRemarkVO> tyreTips(Integer user_id){
		
		WorkOrder param=new WorkOrder();
		param.setUser_id(user_id);
		param.setStatus(WorkOrder.STATUS_END);
		WorkOrder info=(WorkOrder) getSqlMapClientTemplate().queryForObject("WorkOrder.getInfo", param);
		if(info!=null){
			
			@SuppressWarnings("unchecked")
			List<TyreRemarkVO> tyreRemarkVOs=getSqlMapClientTemplate().queryForList("TyreRemarkVO.getList", info);
			logger.info("胎的小贴士列表："+user_id);
			return tyreRemarkVOs;
		}else{
			return null;
		}
		
		
	}
	
	@Transactional
	@Override
	public int tyreByDriverXiuBu(String tyre_id,Integer user_id) {
		TyreVO param=new TyreVO();
		param.setTyre_id(tyre_id);
		param.setUser_id(user_id);
		
		TyreVO info=(TyreVO) getSqlMapClientTemplate().queryForObject("TyreVO.getTyreState", param);
		if (info==null) {
			return 1;//轮胎不存在
		}		
		TyreHistory historyInfo=new TyreHistory();
		historyInfo.setTyre_time(new Date());
		historyInfo.setTyre_id(tyre_id);
		historyInfo.setTyre_action(TyreHistory.ACTION_TYPE_REPAIR);
		historyInfo.setTyre_content("自行修补:"+info.getTrue_name());
		historyInfo.setTyre_person(info.getTrue_name());
		historyInfo.setUser_id(user_id);
		int a=getSqlMapClientTemplate().update("TyreHistory.saveInfo", historyInfo);
		if(a<=0){
//				connection.rollback();
			return 2;//保存胎失败
		}
		logger.info("自行修补保存成功,胎:"+tyre_id);
		return 0;			
	}
	
	@Transactional
	public int saveByList(List<TyreBase> tyreBases,Trucks trucks,Integer user_id) {
		int flag = 0;
		
		String true_name=(String) getSqlMapClientTemplate().queryForObject("User.getTrueName", user_id);
		if (StringUtils.isEmpty(true_name)) {
			return 1;
		}
		TyreBase tyreBase;
		Date now=new Date();
		List<TyreBase> tyreInfo=new ArrayList<TyreBase>();
		List<TyreHistory> historyInfo=new ArrayList<TyreHistory>();
		List<TyreDynamic> dynamicInfo=new ArrayList<TyreDynamic>();
		for(int i=0;i<tyreBases.size();i++){
			tyreBase=tyreBases.get(i);
			tyreBase.setCreate_time(now);
			tyreBase.setTrucks_id(tyreBase.getTyre_where().indexOf("C")>=0?trucks.getGuache_trucks_id():trucks.getTrucks_id());
			
			tyreInfo.add(tyreBase);
			
			TyreHistory history=new TyreHistory();
			history.setTyre_time(now);
			history.setTyre_id(tyreBase.getTyre_id());
			history.setTyre_action(TyreHistory.ACTION_TYPE_INPUT);
			history.setTyre_content("入库");
			history.setTyre_person(true_name);
			history.setUser_id(user_id);
			
			historyInfo.add(history);
			
			TyreDynamic dynamic=new TyreDynamic();
			dynamic.setTyre_id(tyreBase.getTyre_id());
			dynamic.setMabiao_install(trucks.getMabiao_ruku());
			dynamic.setLi_cheng_run(trucks.getLi_cheng_run());
			
			dynamicInfo.add(dynamic);
			
		}
		
		getSqlMapClientTemplate().insert("TyreBase.saveList", tyreInfo);
		getSqlMapClientTemplate().insert("TyreHistory.saveList", historyInfo);
		getSqlMapClientTemplate().insert("TyreDynamic.saveList", dynamicInfo);
		
		flag = 2;
		logger.error("批量保存轮胎基本信息");
		return flag;
	}

}
