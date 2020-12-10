package com.kkbc.dao.impl;

import java.util.Arrays;
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
import com.kkbc.dao.TyrePatternDao;
import com.kkbc.dao.WorkOrderDao;
import com.kkbc.entity.Trucks;
import com.kkbc.entity.TyreHistory;
import com.kkbc.entity.TyrePattern;
import com.kkbc.entity.TyrePattern2;
import com.kkbc.entity.User;
import com.kkbc.entity.WorkOrder;
import com.kkbc.entity.WorkOrderRecord;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.hardware.send.SendManager;
import com.kkbc.vo.TyreVO;
import com.psylife.util.Constants;
import com.psylife.util.TrucksStyleUtil;

@Repository
public class TyrePatternDaoImpl extends BaseDaoImpl implements TyrePatternDao{

	static final Logger logger = LoggerFactory.getLogger(TyrePatternDao.class);
	@Resource
	private WorkOrderDao workOrderDao;
	@Resource
	private TrucksDao trucksDao;
	@Resource
	private TrucksDeviceDao trucksDeviceDao; 

	@Override
	public int saveTyrePattern(TyrePattern tyrePattern){
		TyrePattern pattern=(TyrePattern) getSqlMapClientTemplate().queryForObject("TyrePattern.getUserName", tyrePattern);
		String TP_tyre_id=null;
		if (pattern!=null) {
			TP_tyre_id=pattern.getTyre_id();
			tyrePattern.setTyre_p_person(pattern.getTyre_p_person());
		}else{
			return 1;//胎不存在
		}
		int a;
		if(TP_tyre_id!=null&&!"".equals(TP_tyre_id)){
			tyrePattern.setTyre_p_time(new Date());
			a=getSqlMapClientTemplate().update("TyrePattern.saveTyrePattern", tyrePattern);	
			if(a<=0){
//					connection.rollback();
				return 2;//保存失败
			}
		}
		logger.info("录入轮胎花纹深度,胎:"+tyrePattern.getTyre_id());
		return 0;			
	}
	
	@Transactional
	@Override
	public JSONArray saveTyrePatternList(List<TyrePattern2> list,Integer user_id,int flag){
		Date now=new Date();
		int state;
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject;
		for(TyrePattern2 tyrePattern2:list){
			state=saveUpdateTyrePattern(tyrePattern2, user_id, now,flag);
			jsonObject=new JSONObject();
			jsonObject.put("trucks_id", tyrePattern2.getTrucks_id());//车牌号
			jsonObject.put("tyrePosition", tyrePattern2.getTyrePosition());//轮胎位置
			jsonObject.put("tyre_id", tyrePattern2.getTyre_id());//轮胎号
			jsonObject.put("state", state);
			jsonArray.add(jsonObject);
			if(state!=0){
//				connection.rollback();
			}
			
		}			
		logger.info("批量保存花纹信息成功,user_id="+user_id);
		return jsonArray;			
  }
	
	
	@Transactional
	@Override
	public int saveTyrePatternItem(TyrePattern2 tyrePattern2,Integer user_id,int flag,String tyre_id,String item,String remark,String repaircontent,Double mabiao){
		TyreVO param=new TyreVO();
		param.setUser_id(user_id);
		param.setTyre_id(tyre_id);
		if(tyre_id==null||"".equals(tyre_id)){
			return 3;
		}
		WorkOrder workOrder=workOrderDao.getLastWorkOrder(user_id,null);
		if(workOrder==null||workOrder.getStatus().intValue()==WorkOrder.STATUS_END.intValue()){//没有工单或已结束
			return 10;
		}
		Date now=new Date();
		int state=0;
//			if(tyrePattern2!=null){//因为计算预计里程需要移到下面了
//				state=saveUpdateTyrePattern(tyrePattern2, user_id, now, connection, rList, pList,flag);
//			}
		String upsql="";
		if(remark!=null&&!"".equals(remark)){//温馨提示
			upsql="remark="+remark;
		}
		String[] arr=item.split(",");
		int state1=0;
		String fanxin=null;
		String baofei="";//报废
		Integer status=null;
		for(int i=0;i<arr.length;i++){
			if(!"".equals(arr[i])){
				if("修补".equals(arr[i])){
					state1=workOrderDao.saveWorkOrderRecord(workOrder.getId(), arr[i],repaircontent , tyre_id);
					status=Constants.TYRE_STATUS_REPAIR;
				}else if("报废".equals(arr[i])){
					baofei="报废";
					continue;
				}else if("检测".equals(arr[i])){
					String c=getRemarkByTyrePattern(tyrePattern2);
					if(c==null){
						continue;
					}
					state1=workOrderDao.saveWorkOrderRecord(workOrder.getId(), WorkOrderRecord.ACTION_TYPE_CHECK,c , tyre_id);//检测具体项
				}else{
					state1=workOrderDao.saveWorkOrderRecord(workOrder.getId(), WorkOrderRecord.ACTION_TYPE_CHECK,arr[i] , tyre_id);
				}
				if(state1!=0){
					break;
				}
				if("翻新".equals(arr[i])){
					fanxin="翻新";
					status=Constants.TYRE_STATUS_FANXIN;
				}
			}
		}
		if(status!=null){//胎状态,修补,翻新
			if("".equals(upsql)){
				upsql="`status`="+status;
			}else{
				upsql+=",`status`="+status;
			}
		}
		if(fanxin!=null){//是否翻新胎
			if("".equals(upsql)){
				upsql="tyre_fanxin=1";
			}else{
				upsql+=",tyre_fanxin=1";
			}
			
			//保存消息 温馨提示
			@SuppressWarnings("unchecked")
			List<User> users=getSqlMapClientTemplate().queryForList("User.getSameCompanyUsers", user_id);
			
			StringBuffer colValue = new StringBuffer();
			boolean b=true;
			for(User user:users){
				String value=Arrays.toString(new Object[]{now,"温馨提示",user_id,user.getUser_id(),1,tyre_id});
				if(b){
					colValue.append("("+value+")");
					b=false;
				}else{
					colValue.append(",("+value+")");
				}
			}
			if(colValue.length()>0){
				getSqlMapClientTemplate().insert("MessageVO.saveToM", colValue);
			}
		}
		if(!"".equals(upsql)){
			Map<String, Object> paraMap=new HashMap<String, Object>();
			paraMap.put("updCol", upsql);
			paraMap.put("tyre_id", tyre_id);
			getSqlMapClientTemplate().update("TyreBase.updateTyreInfo", paraMap);
		}
		if(state1==0){
			if(mabiao!=null||"报废".equals(baofei)){
				String trucks_id=null;
				TyreVO tyreState =(TyreVO) getSqlMapClientTemplate().queryForObject("TyreVO.getTyreState", param);
				if(tyreState==null){
//					connection.rollback();
					return 3;//保存失败
				}
//				Map<String, Object> tyre=tyreInfo.get(0);
				if(mabiao!=null){
					if(tyreState.getTyre_flag()==1){//状态   1--装载，0--卸下
						
						Trucks trucks=(Trucks) getSqlMapClientTemplate().queryForObject("Trucks.getMabiaoByTyreWhere", tyreState);
						
						if(trucks!=null&&trucks.getMabiao()<=mabiao.doubleValue()){
							if(trucks.getMabiao()<mabiao.doubleValue()){
								int a=trucksDao.updateTrucksMabiao(trucks.getTrucks_id(), mabiao);//更新车码表数
								if(a!=0){
//									connection.rollback();
									return 3;//保存失败
								}
								trucks_id=trucks.getTrucks_id();
							}
						}else{
//								connection.rollback();
							return 3;//保存失败
						}
					}else{
//							connection.rollback();
						return 3;//保存失败
					}	
				}
				if("报废".equals(baofei)){
					//报废
					state=tyreBaoFei(user_id, now, tyreState.getTyre_flag(), tyre_id, tyreState.getTyre_where(), trucks_id, tyreState.getTrue_name(), workOrder.getId());		
					if(state!=0){
//						connection.rollback();
						return 3;//保存失败
					}
				}
			}
			if(tyrePattern2!=null){
				state=saveUpdateTyrePattern(tyrePattern2, user_id, now,flag);
				if(state!=0){
//						connection.rollback();
					return 3;//保存失败
				}
			}
//				connection.commit();
			logger.info("保存花纹信息及检测项成功,user_id="+user_id);
			return 0;
		}else{
			return 1;
		}
  }
	
	@Transactional
	private int saveUpdateTyrePattern(TyrePattern2 tyrePattern2,Integer user_id,Date now,int flag){
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("user_id", user_id);
		param.put("tyre_id", tyrePattern2.getTyre_id());
		param.put("trucks_id", tyrePattern2.getTrucks_id());
		TyreVO vo=new TyreVO();
		vo.setTyre_id(tyrePattern2.getTyre_id());
		vo.setUser_id(user_id);
		vo.setTrucks_id(tyrePattern2.getTrucks_id());
		String sql="";
		if(tyrePattern2!=null&&tyrePattern2.getTyre_id()!=null&&!"".equals(tyrePattern2.getTyre_id())){
			sql="TyreVO.getByTyreId";
		}else if(tyrePattern2.getTrucks_id()!=null&&!"".equals(tyrePattern2.getTrucks_id())){
			String style=(String) getSqlMapClientTemplate().queryForObject("Trucks.getTrucksStyle", param);
			if(StringUtils.isNotEmpty(style)){
				String trucks_style=style;
				String tyre_where=tyrePattern2.getTyrePosition();
				if(flag==1){
					tyre_where=TrucksStyleUtil.TyreWhereByNo(trucks_style, Integer.valueOf(tyrePattern2.getTyrePosition()));
				}
				if(tyre_where==null){
					return 4;
				}
				vo.setTyre_where(tyre_where);
				sql="TyreVO.getByTruckAndWhere";
			}else{
				return 1;
			}
			
			
		}else{
			return 4;
		}			
		TyreVO info=(TyreVO) getSqlMapClientTemplate().queryForObject(sql, vo);
		String TP_tyre_id=null;
		String true_name=null;
		String tyre_id=null;
		String trucksId=null;
		if (info!=null) {
			TP_tyre_id=info.getTyre_id();
			true_name=info.getTrue_name();
			tyre_id=info.getTyre_id();
			trucksId=info.getTrucks_id();
		}else{
			return 1;//胎不存在
		}
		if(tyre_id==null){
			return 1;
		}
		int a;
		float paver=Float.valueOf(tyrePattern2.getTyre_paver());
//		final String tyre_idTemp=tyre_id;
		
//		float tyre_health=paver/resultSet.getFloat("tyre_depth")*100;
//		
//		if(tyre_health>100f){
//			tyre_health=100f;
//		}
		
		//修改轮胎健康度获取算法
		//当花纹深度低于2毫米时，轮胎健康度低于10分。
		float tyre_health=8;
		if (paver>=2) {
			tyre_health=(paver-2)*80/(info.getTyre_depth()-2)+20;
		}
		if(tyre_health>100f){
			tyre_health=100f;
		}
		
//			double li_cheng_run=resultSet.getDouble("li_cheng_run");
//			float t=resultSet.getFloat("tyre_depth")-paver;
//			long li_cheng_estimate=0;
//			long dan_hao=0;
//			if(t>0){
//				li_cheng_estimate=(long)(li_cheng_run/t*(paver-resultSet.getFloat("tyre_safe_depth")));
//				dan_hao=(long)(li_cheng_run/t);
//			}
		
		double li_cheng_run=info.getLi_cheng_run();
		float t=info.getTyre_depth()-paver;
		long li_cheng_estimate=0;
		double dan_hao=0;
		if(t>0){
			dan_hao=(double)(li_cheng_run/t);
			li_cheng_estimate=(long)(info.getTyre_depth()*dan_hao);
		}
		
		if(TP_tyre_id!=null&&!"".equals(TP_tyre_id)){
			tyrePattern2.setTyre_paver(paver);
			tyrePattern2.setTyre_p_time(now);
			tyrePattern2.setTrue_name(true_name);
			tyrePattern2.setUser_id(user_id);
			tyrePattern2.setDan_hao(dan_hao);
			tyrePattern2.setTyre_health(tyre_health);
			tyrePattern2.setLi_cheng_estimate(li_cheng_estimate);
			tyrePattern2.setTyre_id(tyre_id);
			a=getSqlMapClientTemplate().update("TyreByAdminVO.updatePattern", tyrePattern2);
			if(a<=0){
				return 2;//保存失败
			}
			logger.info("录入轮胎花纹深度,胎:"+tyrePattern2.getTyre_id());
			
		}
		
		//获取轮胎所在的车牌号
//		String trucksId=getJdbcTemplate().execute(new CallableStatementCreator() {
//			
//			@Override
//			public CallableStatement createCallableStatement(Connection con)
//					throws SQLException {
//				CallableStatement cs = con.prepareCall("{ call Get_trucks_id('"+tyre_idTemp+"') }"); 
//				return cs;
//			}
//			}, new CallableStatementCallback<String>() {
//			public String doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {   
//	               cs.execute();   
//	               return cs.getString(1);// 获取输出参数的值
//	         }   
//		});
		
		//更新车的健康度(车上所有轮胎的健康度平均值)
		@SuppressWarnings("unchecked")
		List<String> trucks=getSqlMapClientTemplate().queryForList("TyreVO.Get_trucks_id", tyre_id);
		if (trucks!=null) {
			for (String trucks_id : trucks) {
				if (StringUtils.isNotEmpty(trucks_id)) {
					TyreVO tyreVO=new TyreVO();
					tyreVO.setTyre_id(tyre_id);
					tyreVO.setTyre_health(tyre_health);
					a=trucksDao.updateTrucksHealth( trucks_id, 4, tyreVO, null, info.getTyre_where());
					if (a==0) {
						return 2;//保存失败
					}
				}
			}
		}
		
		return 0;			
	}
	
	//胎报废
	@Transactional
	private int tyreBaoFei(Integer user_id,Date now,int tyre_flag,String tyre_id,String tyre_where,String trucks_id,String true_name,Integer workOrderId){
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("tyre_where", tyre_where);
		param.put("tyre_id", tyre_id);
		int a;
		if(tyre_flag==1){//在车上
			a=getSqlMapClientTemplate().update("Trucks.downTyreFromTruck", param);
			if(a<=0){
				return 2;//保存失败
			}
			a=getSqlMapClientTemplate().update("TyreBase.baofeiFromTruck", tyre_id);
			if(a<=0){
//					connection.rollback();
				return 2;//保存失败
			}
			TyreHistory info=new TyreHistory();
			info.setTyre_time(new Date());
			info.setTyre_id(tyre_id);
			info.setTyre_action(TyreHistory.ACTION_TYPE_DOWN);
			info.setTyre_content(trucks_id+":"+tyre_where);
			info.setTyre_person(true_name);
			info.setUser_id(user_id);
			a=(int) getSqlMapClientTemplate().insert("TyreHistory.saveInfo", info);
			if(a<=0){
				return 3;//保存胎日志失败
			}
			try {
				//重新加载设备和车牌对应关系
				HardwareElement element=SendManager.getInstance().getHardwareElementByCarNum(trucks_id);
				if(element!=null){
					trucksDeviceDao.reloadTrucksDevice(element);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			a=getSqlMapClientTemplate().update("TyreBase.baofei", tyre_id);
			if(a<=0){
				return 2;//保存失败
			}
		}
		String s="";
		if(tyre_flag==1){
			s=" "+trucks_id+":"+tyre_where;
		}
		a=workOrderDao.saveWorkOrderRecord(workOrderId, WorkOrderRecord.ACTION_TYPE_CHECK,"报废"+s,tyre_id);
		if(a!=0){
			return 3;//保存胎工单失败
		}
		logger.info("胎报废保存成功,胎:"+tyre_id+",车牌号:"+trucks_id+",位置:"+tyre_where);
		return 0;			

	}
	
	//获取花纹深度等备注信息
	private String getRemarkByTyrePattern(TyrePattern2 tyrePattern2){
		if(tyrePattern2==null){
			return null;
		}
		StringBuffer result=new StringBuffer();
		result.append("花纹深度:").append(tyrePattern2.getTyre_paver()).append(",")
		.append("外伤:").append(((tyrePattern2.getTyre_trauma()==null||tyrePattern2.getTyre_trauma().intValue()==0)?"无":"有")).append(",")
		.append("轮辋:").append(((tyrePattern2.getTyre_felloe()==null||tyrePattern2.getTyre_felloe().intValue()==0)?"无":"有")).append(",")
		.append("气门:").append(((tyrePattern2.getTyre_value()==null||tyrePattern2.getTyre_value().intValue()==0)?"无":"有")).append(",")
		.append("异常:").append(((tyrePattern2.getTyre_abnormal()==null||tyrePattern2.getTyre_abnormal().intValue()==0)?"无":"有"));
		return result.toString();
	}
	
}
