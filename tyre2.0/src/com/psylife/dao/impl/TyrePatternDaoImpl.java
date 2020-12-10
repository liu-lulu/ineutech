package com.psylife.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.psylife.dao.TrucksDao;
import com.psylife.dao.TrucksDeviceDao;
import com.psylife.dao.TyrePatternDao;
import com.psylife.dao.WorkOrderDao;
import com.psylife.entity.TyreHistory;
import com.psylife.entity.TyrePattern;
import com.psylife.entity.TyrePattern2;
import com.psylife.entity.WorkOrder;
import com.psylife.entity.WorkOrderRecord;
import com.psylife.hardware.HardwareElement;
import com.psylife.hardware.send.SendManager;
import com.psylife.util.ConnectionPool;
import com.psylife.util.Constants;
import com.psylife.util.StringHelper;
import com.psylife.util.TrucksStyleUtil;
import com.psylife.vo.TreadPattern;
import com.psylife.vo.TyreVO;
 
public class TyrePatternDaoImpl extends BaseDaoImpl implements TyrePatternDao{

	private WorkOrderDao workOrderDao=new WorkOrderDaoImpl();
	
	private TrucksDao trucksDao=new TrucksDaoImpl();
	
	private TrucksDeviceDao trucksDeviceDao=(TrucksDeviceDao)new TrucksDeviceDaoImpl(); 
	
	@Override
	public int saveTyrePattern(TyrePattern tyrePattern){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("SELECT T.tyre_id,TP.tyre_id,UU.true_name FROM tyre_base T LEFT JOIN `user` U ON U.user_id=T.user_id LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id LEFT JOIN tyre_pattern TP ON TP.tyre_id=T.tyre_id "+
"WHERE UU.user_id=? AND T.tyre_id=?");
			preparedStatement.setInt(1, tyrePattern.getUser_id());
			preparedStatement.setString(2, tyrePattern.getTyre_id());
			resultSet = preparedStatement.executeQuery();
			String TP_tyre_id=null;
			String true_name=null;
			if (resultSet.next()) {
				TP_tyre_id=resultSet.getString(2);
				true_name=resultSet.getString(3);
			}else{
				return 1;//胎不存在
			}
			int a;
			if(TP_tyre_id!=null&&!"".equals(TP_tyre_id)){
				preparedStatement1 = connection.prepareStatement("update  tyre_pattern set tyre_p1=?,tyre_p2=?,tyre_p3=?,tyre_p4=?,tyre_p5=?,tyre_p6=?,tyre_p_time=?,tyre_p_person=?,tyre_paver=?,user_id=?  where tyre_id=?");
				preparedStatement1.setFloat(1, tyrePattern.getTyre_p1());
				preparedStatement1.setFloat(2, tyrePattern.getTyre_p2());
				preparedStatement1.setFloat(3, tyrePattern.getTyre_p3());
				preparedStatement1.setFloat(4, tyrePattern.getTyre_p4());
				preparedStatement1.setFloat(5, tyrePattern.getTyre_p5());
				preparedStatement1.setFloat(6, tyrePattern.getTyre_p6());
				
				preparedStatement1.setObject(7, new Date());
				preparedStatement1.setString(8, true_name);
				preparedStatement1.setFloat(9, tyrePattern.getTyre_paver());
				preparedStatement1.setInt(10, tyrePattern.getUser_id());
				
				preparedStatement1.setString(11, tyrePattern.getTyre_id());
				a=preparedStatement1.executeUpdate();	
				if(a<=0){
					connection.rollback();
					return 2;//保存失败
				}
			}else{
				String sql = "insert into tyre_pattern(tyre_id,tyre_p1,tyre_p2,tyre_p3,tyre_p4,tyre_p5,tyre_p6,tyre_p_time,tyre_p_person,tyre_paver,user_id) values(?,?,?,?,?,?,?,?,?,?,?);";
				preparedStatement1 = connection.prepareStatement(sql);
				preparedStatement1.setString(1, tyrePattern.getTyre_id());
				preparedStatement1.setFloat(2,  tyrePattern.getTyre_p1());
				preparedStatement1.setFloat(3,  tyrePattern.getTyre_p2());
				preparedStatement1.setFloat(4,  tyrePattern.getTyre_p3());
				preparedStatement1.setFloat(5,  tyrePattern.getTyre_p4());
				preparedStatement1.setFloat(6,  tyrePattern.getTyre_p5());
				preparedStatement1.setFloat(7,  tyrePattern.getTyre_p6());
				preparedStatement1.setObject(8, new Date());
				preparedStatement1.setString(9, true_name);
				preparedStatement1.setFloat(10, tyrePattern.getTyre_paver());
				preparedStatement1.setInt(11, tyrePattern.getUser_id());
				a= preparedStatement1.executeUpdate();	
				if(a<=0){
					connection.rollback();
					return 2;//保存失败
				}
			}
			connection.commit();
			logger.info("录入轮胎花纹深度,胎:"+tyrePattern.getTyre_id());
			return 0;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("录入轮胎花纹深度,胎:"+tyrePattern.getTyre_id()+StringHelper.getTrace(e));
		}finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
				}
				if(preparedStatement1!=null && !preparedStatement1.isClosed()){
					preparedStatement1.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
		}
		return 3;
	}
	
	@Override
	public JSONArray saveTyrePatternList(List<TyrePattern2> list,Integer user_id,int flag){
		Connection connection = null;
		List<ResultSet> rList=new ArrayList<ResultSet>();
		List<PreparedStatement> pList=new ArrayList<PreparedStatement>();
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			Date now=new Date();
			int state;
			JSONArray jsonArray=new JSONArray();
			JSONObject jsonObject;
			for(TyrePattern2 tyrePattern2:list){
				state=saveUpdateTyrePattern(tyrePattern2, user_id, now, connection, rList, pList,flag);
				jsonObject=new JSONObject();
				jsonObject.put("trucks_id", tyrePattern2.getTrucks_id());//车牌号
				jsonObject.put("tyrePosition", tyrePattern2.getTyrePosition());//轮胎位置
				jsonObject.put("tyre_id", tyrePattern2.getTyre_id());//车牌号
				jsonObject.put("state", state);
				jsonArray.add(jsonObject);
				if(state==0){
					connection.commit();
				}else{
					try {
						connection.rollback();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}			
//			connection.commit();
			logger.info("批量保存花纹信息成功,user_id="+user_id);
			return jsonArray;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("批量保存花纹信息失败,user_id="+user_id+StringHelper.getTrace(e));
		}finally {
			try {
				for(ResultSet set:rList){
					try {
						if (set!=null && !set.isClosed()) {
							set.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				for(PreparedStatement statement:pList){
					try {
						if (statement!=null && !statement.isClosed()) {
							statement.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}				
			ConnectionPool.close(connection);
	   }
	   return null;
  }
	
	@Override
	public int mabiaoWork(Integer user_id,Double mabiao,String trucksId){
		Connection connection = null;
		
		try {
			if(StringUtils.isEmpty(trucksId)){
				return 0;
			}
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			WorkOrder workOrder=workOrderDao.getLastWorkOrder(user_id,null, connection);
			if(workOrder==null||workOrder.getStatus().intValue()==WorkOrder.STATUS_END.intValue()){//没有工单或已结束
				return 10;
			}
			
			int a=trucksDao.updateTrucksMabiao(trucksId, mabiao, connection);//更新车码表数
			if(a!=0){
				connection.rollback();
			}else {
				a=workOrderDao.saveWorkOrderRecord(workOrder.getId(), WorkOrderRecord.ACTION_TYPE_CHECK,"更新码表数:"+mabiao , trucksId, connection);
				if(a==0){
					connection.commit();
					return 1;
				}
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionPool.close(connection);
		}
		return 0;
	}
	
	@Override
	public int saveTyrePatternItem(TyrePattern2 tyrePattern2,Integer user_id,int flag,String tyre_id,String item,String remark,String repaircontent,Double mabiao){
		Connection connection = null;
		List<ResultSet> rList=new ArrayList<ResultSet>();
		List<PreparedStatement> pList=new ArrayList<PreparedStatement>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet1 = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet resultSet2 = null;
		PreparedStatement preparedStatement3 = null;
		ResultSet resultSet3 = null;
		PreparedStatement preparedStatement4 = null;
		try {
			if(tyre_id==null||"".equals(tyre_id)){
				return 3;
			}
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			WorkOrder workOrder=workOrderDao.getLastWorkOrder(user_id,null, connection);
			if(workOrder==null||workOrder.getStatus().intValue()==WorkOrder.STATUS_END.intValue()){//没有工单或已结束
				return 10;
			}
			Date now=new Date();
			int state=0;
//			if(tyrePattern2!=null){//因为计算预计里程需要移到下面了
//				state=saveUpdateTyrePattern(tyrePattern2, user_id, now, connection, rList, pList,flag);
//			}
			String upsql="";
			List<Object> listP=new ArrayList<Object>();
			if(remark!=null&&!"".equals(remark)){//温馨提示
				upsql="remark=?";
				listP.add(remark);
			}
			String[] arr=item.split(",");
			int state1=0;
			String fanxin=null;
			String baofei="";//报废
			Integer status=null;
			for(int i=0;i<arr.length;i++){
				if(!"".equals(arr[i])){
					if("修补".equals(arr[i])){
						state1=workOrderDao.saveWorkOrderRecord(workOrder.getId(), arr[i],repaircontent , tyre_id, connection);
						status=Constants.TYRE_STATUS_REPAIR;
					}else if("报废".equals(arr[i])){
						baofei="报废";
						continue;
					}else if("检测".equals(arr[i])){
						String c=getRemarkByTyrePattern(tyrePattern2);
						if(c==null){
							continue;
						}
						state1=workOrderDao.saveWorkOrderRecord(workOrder.getId(), WorkOrderRecord.ACTION_TYPE_CHECK,c , tyre_id, connection);//检测具体项
					}else{
						state1=workOrderDao.saveWorkOrderRecord(workOrder.getId(), WorkOrderRecord.ACTION_TYPE_CHECK,arr[i] , tyre_id, connection);
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
					upsql="`status`=?";
				}else{
					upsql+=",`status`=?";
				}
				listP.add(status);
			}
			if(fanxin!=null){//是否翻新胎
				if("".equals(upsql)){
					upsql="tyre_fanxin=?";
				}else{
					upsql+=",tyre_fanxin=?";
				}
				listP.add(1);
				
				//保存消息 温馨提示
				preparedStatement3 = connection.prepareStatement("SELECT U.user_id,U.true_name FROM `user` U LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id WHERE UU.user_id=? AND U.user_role=1");
				preparedStatement3.setInt(1, user_id);
				resultSet3=preparedStatement3.executeQuery();
				StringBuffer newsql = new StringBuffer();
				newsql.append("insert into message(create_time,content,senduser_id,user_id,type,remark) values");
				List<Object> paramterList=new ArrayList<Object>();
				boolean b=true;
				while(resultSet3.next()){
					paramterList.addAll(Arrays.asList(new Object[]{now,"温馨提示",user_id,resultSet3.getInt("user_id"),1,tyre_id}));
					if(b){
						newsql.append("(?,?,?,?,?,?)");
						b=false;
					}else{
						newsql.append(",(?,?,?,?,?,?)");
					}
				}
				if(paramterList.size()>0){
					preparedStatement4 = connection.prepareStatement(newsql.toString());
					for(int i=0;i<paramterList.size();i++){
						preparedStatement4.setObject(i+1, paramterList.get(i));
					}
					preparedStatement4.executeUpdate();
				}
			}
			if(!"".equals(upsql)){
				preparedStatement = connection.prepareStatement("update tyre_base set "+upsql+" where tyre_id=?");
				listP.add(tyre_id);
				for(int i=0;i<listP.size();i++){
					preparedStatement.setObject(i+1, listP.get(i));
				}
				preparedStatement.executeUpdate();	
			}
			if(state1==0){
				if(mabiao!=null||"报废".equals(baofei)){
					String trucks_id=null;
					preparedStatement1 = connection.prepareStatement("SELECT T.tyre_id,T.tyre_flag,T.tyre_where,UU.true_name FROM tyre_base T LEFT JOIN `user` U ON U.user_id=T.user_id LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id "+
							"WHERE UU.user_id=? AND T.tyre_id=?");
					preparedStatement1.setInt(1, user_id);
					preparedStatement1.setString(2, tyre_id);
					resultSet1=preparedStatement1.executeQuery();
					if(!resultSet1.next()){
						connection.rollback();
						return 3;//保存失败
					}
					if(mabiao!=null){
						if(resultSet1.getInt("tyre_flag")==1){//状态   1--装载，0--卸下
							StringBuffer sql=new StringBuffer();
							sql.append("SELECT T.trucks_id,T.mabiao FROM trucks T LEFT JOIN trucks GT ON GT.trucks_id=T.guache_trucks_id WHERE T.trucks_type='主车' ");
							sql.append(" and (T.trucks_").append(resultSet1.getString("tyre_where")).append("=? or GT.trucks_").append(resultSet1.getString("tyre_where")).append("=?)");
							sql.append(" LIMIT 1");
							preparedStatement2 = connection.prepareStatement(sql.toString());
							preparedStatement2.setString(1, tyre_id);
							preparedStatement2.setString(2, tyre_id);
							resultSet2 = preparedStatement2.executeQuery();
							if(resultSet2.next()&&resultSet2.getDouble("mabiao")<=mabiao.doubleValue()){
								if(resultSet2.getDouble("mabiao")<mabiao.doubleValue()){
									int a=trucksDao.updateTrucksMabiao(resultSet2.getString("trucks_id"), mabiao, connection);//更新车码表数
									if(a!=0){
										connection.rollback();
										return 3;//保存失败
									}
									trucks_id=resultSet2.getString("trucks_id");
								}
							}else{
								connection.rollback();
								return 3;//保存失败
							}
						}else{
							connection.rollback();
							return 3;//保存失败
						}	
					}
					if("报废".equals(baofei)){
						//报废
						state=tyreBaoFei(user_id, now, connection, rList, pList, resultSet1.getInt("tyre_flag"), tyre_id, resultSet1.getString("tyre_where"), trucks_id, resultSet1.getString("true_name"), workOrder.getId());		
						if(state!=0){
							connection.rollback();
							return 3;//保存失败
						}
					}
				}
				if(tyrePattern2!=null){
					state=saveUpdateTyrePattern(tyrePattern2, user_id, now, connection, rList, pList,flag);
					if(state!=0){
						connection.rollback();
						return 3;//保存失败
					}
				}
				connection.commit();
				logger.info("保存花纹信息及检测项成功,user_id="+user_id);
				return 0;
			}else{
				try {
					connection.rollback();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return 1;
			}		
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("保存花纹信息及检测项失败,user_id="+user_id+StringHelper.getTrace(e));
		}finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (resultSet1 != null && !resultSet1.isClosed()) {
					resultSet1.close();
				}
				if (resultSet2 != null && !resultSet2.isClosed()) {
					resultSet2.close();
				}
				if (resultSet3 != null && !resultSet3.isClosed()) {
					resultSet3.close();
				}
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
				}
				if(preparedStatement1!=null && !preparedStatement1.isClosed()){
					preparedStatement1.close();
				}
				if(preparedStatement2!=null && !preparedStatement2.isClosed()){
					preparedStatement2.close();
				}
				if(preparedStatement3!=null && !preparedStatement3.isClosed()){
					preparedStatement3.close();
				}
				if(preparedStatement4!=null && !preparedStatement4.isClosed()){
					preparedStatement4.close();
				}
				for(ResultSet set:rList){
					try {
						if (set!=null && !set.isClosed()) {
							set.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				for(PreparedStatement statement:pList){
					try {
						if (statement!=null && !statement.isClosed()) {
							statement.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}					
			ConnectionPool.close(connection);
	   }
	   return 3;
  }
	
	private int saveUpdateTyrePattern(TyrePattern2 tyrePattern2,Integer user_id,Date now,Connection connection,List<ResultSet> rList,List<PreparedStatement> pList,int flag){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		CallableStatement stmt = null;
		ResultSet resultSet1 = null;
		try {
			if(tyrePattern2!=null&&tyrePattern2.getTyre_id()!=null&&!"".equals(tyrePattern2.getTyre_id())){
				preparedStatement = connection.prepareStatement("SELECT T.tyre_id,TP.tyre_id,UU.true_name,T.tyre_depth,TD.li_cheng_run,C.tyre_safe_depth,T.tyre_where FROM tyre_base T LEFT JOIN `user` U ON U.user_id=T.user_id LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id LEFT JOIN tyre_pattern TP ON TP.tyre_id=T.tyre_id LEFT JOIN tyre_dynamic TD ON TD.tyre_id=T.tyre_id LEFT JOIN company C ON C.company_id=UU.user_company_id "+
						"WHERE UU.user_id=? AND T.tyre_id=?");
									preparedStatement.setInt(1, user_id);
									preparedStatement.setString(2, tyrePattern2.getTyre_id());
			}else if(tyrePattern2.getTrucks_id()!=null&&!"".equals(tyrePattern2.getTrucks_id())){
				preparedStatement = connection.prepareStatement("SELECT T.trucks_style FROM trucks T LEFT JOIN `user` U ON U.user_company_id=T.company_id WHERE T.trucks_id=? AND U.user_id=?");
				preparedStatement.setString(1, tyrePattern2.getTrucks_id());
				preparedStatement.setInt(2, user_id);
				resultSet = preparedStatement.executeQuery();
				rList.add(resultSet);
				pList.add(preparedStatement);
				if(resultSet.next()){
					String trucks_style=resultSet.getString(1);
					String tyre_where=tyrePattern2.getTyrePosition();
					if(flag==1){
						tyre_where=TrucksStyleUtil.TyreWhereByNo(trucks_style, Integer.valueOf(tyrePattern2.getTyrePosition()));
					}					
					if(tyre_where==null){
						return 4;
					}
					
					preparedStatement = connection.prepareStatement("SELECT T.tyre_id,TP.tyre_id,UU.true_name,T.tyre_depth,TD.li_cheng_run,C.tyre_safe_depth,T.tyre_where FROM tyre_base T LEFT JOIN `user` U ON U.user_id=T.user_id LEFT JOIN `user` UU ON UU.user_company_id=U.user_company_id LEFT JOIN tyre_pattern TP ON TP.tyre_id=T.tyre_id LEFT JOIN tyre_dynamic TD ON TD.tyre_id=T.tyre_id LEFT JOIN company C ON C.company_id=UU.user_company_id "
							+ " LEFT JOIN trucks TT ON TT.trucks_"+tyre_where+"=T.tyre_id "+
							"WHERE TT.trucks_id=? AND UU.user_id=?");
					
					preparedStatement.setString(1, tyrePattern2.getTrucks_id());
					preparedStatement.setInt(2, user_id);
				}else{
					return 1;
				}
				
				
			}else{
				return 4;
			}			
			resultSet = preparedStatement.executeQuery();
			String TP_tyre_id=null;
			String true_name=null;
			String tyre_id=null;
			if (resultSet.next()) {
				TP_tyre_id=resultSet.getString(2);
				true_name=resultSet.getString(3);
				tyre_id=resultSet.getString(1);
			}else{
				return 1;//胎不存在
			}
			if(tyre_id==null){
				return 1;
			}
			int a;
			float paver=Float.valueOf(tyrePattern2.getTyre_paver());
			if(TP_tyre_id!=null&&!"".equals(TP_tyre_id)){
				preparedStatement1 = connection.prepareStatement("update  tyre_pattern set tyre_p1=?,tyre_p2=?,tyre_p3=?,tyre_p4=?,tyre_p5=?,tyre_p6=?,tyre_p_time=?,tyre_p_person=?,tyre_paver=?,user_id=?,unit=?  where tyre_id=?");
				preparedStatement1.setFloat(1,paver);
				preparedStatement1.setFloat(2, paver);
				preparedStatement1.setFloat(3, paver);
				preparedStatement1.setFloat(4, paver);
				preparedStatement1.setFloat(5, paver);
				preparedStatement1.setFloat(6, paver);
				
				preparedStatement1.setObject(7, now);
				preparedStatement1.setString(8, true_name);
				preparedStatement1.setFloat(9, paver);
				preparedStatement1.setInt(10, user_id);
				preparedStatement1.setInt(11, tyrePattern2.getUnit());
				
				preparedStatement1.setString(12, tyre_id);
				a=preparedStatement1.executeUpdate();	
				if(a<=0){
					return 2;//保存失败
				}
				
			}else{
				String sql = "insert into tyre_pattern(tyre_id,tyre_p1,tyre_p2,tyre_p3,tyre_p4,tyre_p5,tyre_p6,tyre_p_time,tyre_p_person,tyre_paver,user_id,unit) values(?,?,?,?,?,?,?,?,?,?,?,?);";
				preparedStatement1 = connection.prepareStatement(sql);
				preparedStatement1.setString(1, tyre_id);
				preparedStatement1.setFloat(2,  paver);
				preparedStatement1.setFloat(3,  paver);
				preparedStatement1.setFloat(4,  paver);
				preparedStatement1.setFloat(5,  paver);
				preparedStatement1.setFloat(6,  paver);
				preparedStatement1.setFloat(7,  paver);
				preparedStatement1.setObject(8, now);
				preparedStatement1.setString(9, true_name);
				preparedStatement1.setFloat(10, paver);
				preparedStatement1.setInt(11, user_id);
				preparedStatement1.setInt(12, tyrePattern2.getUnit());
				a= preparedStatement1.executeUpdate();	
				if(a<=0){
					return 2;//保存失败
				}
			}
//			float tyre_health=paver/resultSet.getFloat("tyre_depth")*100;
//			
//			if(tyre_health>100f){
//				tyre_health=100f;
//			}
			
			//修改轮胎健康度获取算法
			//当花纹深度低于2毫米时，轮胎健康度低于10分。
			float tyre_health=8;
			 if (paver>=2) {
				if (resultSet.getObject("tyre_depth")==null||resultSet.getFloat("tyre_depth")==0) {//轮胎没有原始花纹深度,健康值99
					tyre_health=99;
				}else {
					tyre_health=(paver-2)*80/(resultSet.getFloat("tyre_depth")-2)+20;
				}
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
			
			double li_cheng_run=resultSet.getDouble("li_cheng_run");
			float t=resultSet.getFloat("tyre_depth")-paver;
			long li_cheng_estimate=0;
			long dan_hao=0;
			if(t>0){
				dan_hao=(long)(li_cheng_run/t);
				li_cheng_estimate=(long)(resultSet.getFloat("tyre_depth")*dan_hao);
			}
			
			String sql = "UPDATE tyre_base LEFT JOIN tyre_dynamic ON tyre_dynamic.tyre_id=tyre_base.tyre_id SET tyre_base.tyre_trauma=?,tyre_base.tyre_abnormal=?,tyre_base.tyre_type4=?,tyre_base.tyre_value=?,tyre_base.dan_hao=?,tyre_dynamic.tyre_health=?,tyre_dynamic.li_cheng_estimate=? where tyre_base.tyre_id=?";
			preparedStatement2 = connection.prepareStatement(sql);
			preparedStatement2.setInt(1, tyrePattern2.getTyre_trauma());
			preparedStatement2.setInt(2,  tyrePattern2.getTyre_abnormal());
			preparedStatement2.setInt(3,  tyrePattern2.getTyre_felloe());
			preparedStatement2.setInt(4,  tyrePattern2.getTyre_value());
//			preparedStatement2.setFloat(5,  tyrePattern2.getDan_hao());
			preparedStatement2.setDouble(5,  dan_hao);
			preparedStatement2.setFloat(6,  tyre_health);//健康度
			preparedStatement2.setDouble(7,  li_cheng_estimate);//预计里程
			preparedStatement2.setString(8, tyre_id);
			a= preparedStatement2.executeUpdate();	
			if(a<=0){
				return 2;//保存失败
			}
			
			//获取轮胎所在的车牌号
			stmt=connection.prepareCall("{ call Get_trucks_id('"+tyre_id+"') }"); 
			resultSet1=stmt.executeQuery();
			rList.add(resultSet1);
			pList.add(stmt);
			
			//更新车的健康度(车上所有轮胎的健康度平均值)
			while (resultSet1.next()) {
				if (StringUtils.isNotEmpty(resultSet1.getString(1))) {
					TyreVO tyreVO=new TyreVO();
					tyreVO.setTyre_id(tyre_id);
					tyreVO.setTyre_health(tyre_health);
					a=trucksDao.updateTrucksHealth(connection, resultSet1.getString(1), 4, tyreVO, resultSet.getString("tyre_where"));
					if (a==0) {
						return 2;//保存失败
					}
				}
				
//				String trucksId=resultSet.getString(1);
//				String updateTrucksHealth="UPDATE trucks "
//					+"SET trucks_health=("
//					+"SELECT health from ("
//					+"SELECT SUM(TD.tyre_health)/COUNT(*) AS health FROM trucks T "
//					+"LEFT JOIN tyre_dynamic TD ON (T.trucks_A1=TD.tyre_id OR T.trucks_A2=TD.tyre_id OR T.trucks_A3=TD.tyre_id OR T.trucks_A4=TD.tyre_id OR T.trucks_A5=TD.tyre_id OR T.trucks_A6=TD.tyre_id OR  "
//					+"T.trucks_B1=TD.tyre_id OR T.trucks_B2=TD.tyre_id OR T.trucks_B3=TD.tyre_id OR T.trucks_B4=TD.tyre_id OR T.trucks_B5=TD.tyre_id OR T.trucks_B6=TD.tyre_id OR T.trucks_B7=TD.tyre_id OR T.trucks_B8=TD.tyre_id OR  "
//					+"T.trucks_C1=TD.tyre_id OR T.trucks_C2=TD.tyre_id OR T.trucks_C3=TD.tyre_id OR T.trucks_C4=TD.tyre_id OR T.trucks_C5=TD.tyre_id OR T.trucks_C6=TD.tyre_id OR  "
//					+"T.trucks_C7=TD.tyre_id OR T.trucks_C8=TD.tyre_id OR T.trucks_C9=TD.tyre_id OR T.trucks_C10=TD.tyre_id OR T.trucks_C11=TD.tyre_id OR T.trucks_C12=TD.tyre_id OR  "
//					+"T.trucks_C13=TD.tyre_id OR T.trucks_C14=TD.tyre_id OR T.trucks_C15=TD.tyre_id OR T.trucks_C16=TD.tyre_id) "
//					+"WHERE T.trucks_id=? AND TD.tyre_health!=''"
//					+")t"
//					+") where trucks_id=?";
//				preparedStatement3=connection.prepareStatement(updateTrucksHealth);
//				preparedStatement3.setString(1, trucksId);
//				preparedStatement3.setString(2, trucksId);
//				a=preparedStatement3.executeUpdate();
				
			}
			
			logger.info("录入轮胎花纹深度,胎:"+tyrePattern2.getTyre_id());
			return 0;			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("录入轮胎花纹深度,胎:"+tyrePattern2.getTyre_id()+StringHelper.getTrace(e));
		}finally {
			if (resultSet != null) {
				rList.add(resultSet);
			}
			if (preparedStatement != null) {
				pList.add(preparedStatement);
			}
			if (preparedStatement1 != null) {
				pList.add(preparedStatement1);
			}
			if (preparedStatement2 != null) {
				pList.add(preparedStatement2);
			}
			if (preparedStatement3 != null) {
				pList.add(preparedStatement3);
			}
			if (stmt != null) {
				pList.add(stmt);
			}
		}
		return 3;
	}
	
	//胎报废
	private int tyreBaoFei(Integer user_id,Date now,Connection connection,List<ResultSet> rList,List<PreparedStatement> pList,int tyre_flag,String tyre_id,String tyre_where,String trucks_id,String true_name,Integer workOrderId){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		try {
			int a;
			if(tyre_flag==1){//在车上
				preparedStatement1 = connection.prepareStatement("update trucks SET trucks_" + tyre_where+ "=? where trucks_" + tyre_where+ "=?");
				preparedStatement1.setString(1, null);
				preparedStatement1.setString(2, tyre_id);
				a=preparedStatement1.executeUpdate();
				if(a<=0){
					return 2;//保存失败
				}
				preparedStatement2 = connection.prepareStatement("UPDATE tyre_base SET tyre_flag=0,tyre_where=null,`status`=0 WHERE tyre_id=?");
				preparedStatement2.setString(1, tyre_id);
				a=preparedStatement2.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 2;//保存失败
				}
				preparedStatement3 = connection.prepareStatement("insert into tyre_history(tyre_time,tyre_id,tyre_action,tyre_content,tyre_person,user_id) values(?,?,?,?,?,?)");
				preparedStatement3.setObject(1, new Date());
				preparedStatement3.setString(2, tyre_id);
				preparedStatement3.setString(3, TyreHistory.ACTION_TYPE_DOWN);
				preparedStatement3.setString(4, trucks_id+":"+tyre_where);
				preparedStatement3.setString(5, true_name);
				preparedStatement3.setInt(6, user_id);
				a=preparedStatement3.executeUpdate();
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
				preparedStatement2 = connection.prepareStatement("UPDATE tyre_base SET `status`=0 WHERE tyre_id=?");
				preparedStatement2.setString(1, tyre_id);
				a=preparedStatement2.executeUpdate();
				if(a<=0){
					return 2;//保存失败
				}
			}
			String s="";
			if(tyre_flag==1){
				s=" "+trucks_id+":"+tyre_where;
			}
			a=workOrderDao.saveWorkOrderRecord(workOrderId, WorkOrderRecord.ACTION_TYPE_CHECK,"报废"+s,tyre_id, connection);
			if(a!=0){
				return 3;//保存胎工单失败
			}
			logger.info("胎报废保存成功,胎:"+tyre_id+",车牌号:"+trucks_id+",位置:"+tyre_where);
			return 0;			

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("胎报废,胎:"+tyre_id+StringHelper.getTrace(e));
		}finally {
			if (resultSet != null) {
				rList.add(resultSet);
			}
			if (preparedStatement != null) {
				pList.add(preparedStatement);
			}
			if (preparedStatement1 != null) {
				pList.add(preparedStatement1);
			}
			if (preparedStatement2 != null) {
				pList.add(preparedStatement2);
			}
			if (preparedStatement3 != null) {
				pList.add(preparedStatement3);
			}
		}
		return 3;
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

	@Override
	public JSONArray saveTyrePatternListByTool(List<TreadPattern> patternList) {
		Connection connection = null;
		
		List<ResultSet> rList=new ArrayList<ResultSet>();
		List<PreparedStatement> pList=new ArrayList<PreparedStatement>();
		
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject;
		
		try {
			connection=ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			for (TreadPattern treadPattern : patternList) {
				int state=saveTyrePatternByTool(treadPattern, connection, rList, pList);
				jsonObject=new JSONObject();
				jsonObject.put("trucks_id", treadPattern.getCarNo());//车牌号
				jsonObject.put("tyrePosition", treadPattern.getTyrePosition());//轮胎位置
				jsonObject.put("state", state);
				jsonArray.add(jsonObject);
				if(state==4){
					connection.commit();
				}else{
					try {
						connection.rollback();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("批量保存花纹尺检测信息失败"+StringHelper.getTrace(e));
		}finally {
			try {
				for(ResultSet set:rList){
					try {
						if (set!=null && !set.isClosed()) {
							set.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				for(PreparedStatement statement:pList){
					try {
						if (statement!=null && !statement.isClosed()) {
							statement.close();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}				
			ConnectionPool.close(connection);
	   }
		
		return jsonArray;
	}
	
	public int saveTyrePatternByTool(TreadPattern pattern,Connection connection,List<ResultSet> rList,List<PreparedStatement> pList){

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement4 = null;
		PreparedStatement preparedStatement5 = null;
		CallableStatement stmt = null;
		ResultSet resultSet2 = null;
		String tyre_id=null;
		try {
			if (StringUtils.isEmpty(pattern.getCarNo())||StringUtils.isEmpty(pattern.getTyrePosition())) {
				return 0;
			}
			
			preparedStatement = connection.prepareStatement("SELECT T.trucks_style,T.trucks_"+pattern.getTyrePosition()+" FROM trucks T WHERE T.trucks_id=? ");
			preparedStatement.setString(1, pattern.getCarNo());
			
			resultSet = preparedStatement.executeQuery();
			rList.add(resultSet);
			pList.add(preparedStatement);
			
			if (resultSet.next()) {
				tyre_id=resultSet.getString("trucks_"+pattern.getTyrePosition());
			}else{
				return 1;//车不存在
			}
			if(StringUtils.isEmpty(tyre_id)){
				return 2;//该位置没有轮胎
			}
			int a;
			float paver=Float.valueOf(pattern.getTyreDeep());
			preparedStatement1=connection.prepareStatement("select TB.tyre_id,TB.tyre_depth,TB.tyre_flag,TB.tyre_where,TP.tyre_id AS TP_tyre_id,TD.li_cheng_run from tyre_base TB left join tyre_pattern TP on TB.tyre_id=TP.tyre_id left join tyre_dynamic TD ON TB.tyre_id=TD.tyre_id WHERE TB.tyre_id=?");
			preparedStatement1.setString(1, tyre_id);
			resultSet1=preparedStatement1.executeQuery();
			rList.add(resultSet1);
			pList.add(preparedStatement1);
			Date now=new Date();
			if (resultSet1.next()) {
				String TP_tyre_id=resultSet1.getString("TP_tyre_id");
				if(TP_tyre_id!=null&&!"".equals(TP_tyre_id)){
					preparedStatement2 = connection.prepareStatement("update  tyre_pattern set tyre_p1=?,tyre_p2=?,tyre_p3=?,tyre_p4=?,tyre_p5=?,tyre_p6=?,tyre_p_time=?,tyre_paver=? where tyre_id=?");
					preparedStatement2.setFloat(1,paver);
					preparedStatement2.setFloat(2, paver);
					preparedStatement2.setFloat(3, paver);
					preparedStatement2.setFloat(4, paver);
					preparedStatement2.setFloat(5, paver);
					preparedStatement2.setFloat(6, paver);
					
					preparedStatement2.setObject(7, now);
					preparedStatement2.setFloat(8, paver);
					
					preparedStatement2.setString(9, tyre_id);
					a=preparedStatement2.executeUpdate();	
					if(a<=0){
						return 3;//保存失败
					}
					
				}else{
					String sql = "insert into tyre_pattern(tyre_id,tyre_p1,tyre_p2,tyre_p3,tyre_p4,tyre_p5,tyre_p6,tyre_p_time,tyre_paver) values(?,?,?,?,?,?,?,?,?)";
					preparedStatement2 = connection.prepareStatement(sql);
					preparedStatement2.setString(1, tyre_id);
					preparedStatement2.setFloat(2,  paver);
					preparedStatement2.setFloat(3,  paver);
					preparedStatement2.setFloat(4,  paver);
					preparedStatement2.setFloat(5,  paver);
					preparedStatement2.setFloat(6,  paver);
					preparedStatement2.setFloat(7,  paver);
					preparedStatement2.setObject(8, now);
					preparedStatement2.setFloat(9, paver);
					a= preparedStatement2.executeUpdate();	
					if(a<=0){
						return 3;//保存失败
					}
				}
				
				pList.add(preparedStatement2);
			}
			
			//修改轮胎健康度获取算法
			//当花纹深度低于2毫米时，轮胎健康度低于10分。
			float tyre_health=8;
			 if (paver>=2) {
				if (resultSet1.getObject("tyre_depth")==null||resultSet1.getFloat("tyre_depth")==0) {//轮胎没有原始花纹深度,健康值99
					tyre_health=99;
				}else {
					tyre_health=(paver-2)*80/(resultSet1.getFloat("tyre_depth")-2)+20;
				}
			}
			if(tyre_health>100f){
				tyre_health=100f;
			}
			
			double li_cheng_run=resultSet1.getDouble("li_cheng_run");
			float t=resultSet1.getFloat("tyre_depth")-paver;
			long li_cheng_estimate=0;
			float dan_hao=pattern.getAbrasionValue();
			if(t>0){
//				dan_hao=(long)(li_cheng_run/t);
				li_cheng_estimate=(long)(resultSet1.getFloat("tyre_depth")*dan_hao);
			}
			
			int type=pattern.getInjuryType();
			if (type!=5) {
				//外伤，轮胎异常，网伤，气门
				String sql = "UPDATE tyre_base SET tyre_base.tyre_trauma=?,tyre_base.tyre_abnormal=?,tyre_base.tyre_type4=?,tyre_base.tyre_value=? where tyre_base.tyre_id=?";
				preparedStatement3=connection.prepareStatement(sql);
				if (type==0) {//无损伤
					preparedStatement3.setInt(1, 0);
					preparedStatement3.setInt(2, 0);
					preparedStatement3.setInt(3, 0);
					preparedStatement3.setInt(4, 0);
				}else if (type==1) {//外伤
						preparedStatement3.setInt(1, 1);
						preparedStatement3.setInt(2, 0);
						preparedStatement3.setInt(3, 0);
						preparedStatement3.setInt(4, 0);
					}else if (type==2) {//网伤
						preparedStatement3.setInt(1, 0);
						preparedStatement3.setInt(2, 0);
						preparedStatement3.setInt(3, 1);
						preparedStatement3.setInt(4, 0);
					}else if (type==3) {//缺帽
						preparedStatement3.setInt(1, 0);
						preparedStatement3.setInt(2, 0);
						preparedStatement3.setInt(3, 0);
						preparedStatement3.setInt(4, 1);
					}else if (type==4) {//异常磨损
						preparedStatement3.setInt(1, 0);
						preparedStatement3.setInt(2, 1);
						preparedStatement3.setInt(3, 0);
						preparedStatement3.setInt(4, 0);
					}else if (type==6) {//外伤  网伤
						preparedStatement3.setInt(1, 1);
						preparedStatement3.setInt(2, 0);
						preparedStatement3.setInt(3, 1);
						preparedStatement3.setInt(4, 0);
					}else if (type==7) {//缺帽  外伤
						preparedStatement3.setInt(1, 1);
						preparedStatement3.setInt(2, 0);
						preparedStatement3.setInt(3, 0);
						preparedStatement3.setInt(4, 1);
					}else if (type==8) {//缺帽  网伤
						preparedStatement3.setInt(1, 0);
						preparedStatement3.setInt(2, 0);
						preparedStatement3.setInt(3, 1);
						preparedStatement3.setInt(4, 1);
					}else if (type==9) {//异常磨损   外伤
						preparedStatement3.setInt(1, 1);
						preparedStatement3.setInt(2, 1);
						preparedStatement3.setInt(3, 0);
						preparedStatement3.setInt(4, 0);
					}else if (type==10) {//异常磨损  网伤
						preparedStatement3.setInt(1, 0);
						preparedStatement3.setInt(2, 1);
						preparedStatement3.setInt(3, 1);
						preparedStatement3.setInt(4, 0);
					}else if (type==11) {//异常磨损  缺帽
						preparedStatement3.setInt(1, 1);
						preparedStatement3.setInt(2, 0);
						preparedStatement3.setInt(3, 0);
						preparedStatement3.setInt(4, 1);
					}else if (type==12) {//缺帽      外伤  网伤
						preparedStatement3.setInt(1, 1);
						preparedStatement3.setInt(2, 0);
						preparedStatement3.setInt(3, 1);
						preparedStatement3.setInt(4, 1);
					}else if (type==13) {//异常磨损  网伤  外伤
						preparedStatement3.setInt(1, 1);
						preparedStatement3.setInt(2, 1);
						preparedStatement3.setInt(3, 1);
						preparedStatement3.setInt(4, 0);
					}else if (type==14) {//异常磨损  缺帽  外伤
						preparedStatement3.setInt(1, 1);
						preparedStatement3.setInt(2, 1);
						preparedStatement3.setInt(3, 0);
						preparedStatement3.setInt(4, 1);
					}else if (type==15) {//异常磨损  缺帽  网伤
						preparedStatement3.setInt(1, 0);
						preparedStatement3.setInt(2, 1);
						preparedStatement3.setInt(3, 1);
						preparedStatement3.setInt(4, 1);
					}else if (type==16) {//异常磨损  缺帽  网伤  外伤
						preparedStatement3.setInt(1, 1);
						preparedStatement3.setInt(2, 1);
						preparedStatement3.setInt(3, 1);
						preparedStatement3.setInt(4, 1);
					}
				 preparedStatement3.setString(5, tyre_id);
				 a=preparedStatement3.executeUpdate();
				 if (a<0) {
					return 3;
				}
				pList.add(preparedStatement3);
			}else {//报废
				
				//在车上
				preparedStatement3 = connection.prepareStatement("update trucks SET trucks_" + pattern.getTyrePosition()+ "=? where trucks_" + pattern.getTyrePosition()+ "=?");
				preparedStatement3.setString(1, null);
				preparedStatement3.setString(2, tyre_id);
				a=preparedStatement3.executeUpdate();
				if(a<=0){
					return 3;//保存失败
				}
				preparedStatement4 = connection.prepareStatement("UPDATE tyre_base SET tyre_flag=0,tyre_where=null,`status`=0 WHERE tyre_id=?");
				preparedStatement4.setString(1, tyre_id);
				a=preparedStatement4.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
				}
				try {
					//重新加载设备和车牌对应关系
					HardwareElement element=SendManager.getInstance().getHardwareElementByCarNum(pattern.getCarNo());
					if(element!=null){
						trucksDeviceDao.reloadTrucksDevice(element);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}
			
			String sql = "UPDATE tyre_base LEFT JOIN tyre_dynamic ON tyre_dynamic.tyre_id=tyre_base.tyre_id SET tyre_base.dan_hao=?,tyre_dynamic.tyre_health=?,tyre_dynamic.li_cheng_estimate=? where tyre_base.tyre_id=?";
			preparedStatement5=connection.prepareStatement(sql);
			preparedStatement5.setFloat(1, dan_hao);
			preparedStatement5.setFloat(2, tyre_health);
			preparedStatement5.setDouble(3, li_cheng_estimate);
			preparedStatement5.setString(4, tyre_id);
			a=preparedStatement5.executeUpdate();
			if(a<=0){
				connection.rollback();
				return 3;//保存失败
			}
			
			//获取轮胎所在的车牌号
			stmt=connection.prepareCall("{ call Get_trucks_id('"+tyre_id+"') }"); 
			resultSet2=stmt.executeQuery();
			rList.add(resultSet2);
			pList.add(stmt);
			
			//更新车的健康度(车上所有轮胎的健康度平均值)
			while (resultSet2.next()) {
				if (StringUtils.isNotEmpty(resultSet2.getString(1))) {
					TyreVO tyreVO=new TyreVO();
					tyreVO.setTyre_id(tyre_id);
					tyreVO.setTyre_health(tyre_health);
					a=trucksDao.updateTrucksHealth(connection, resultSet2.getString(1), 4, tyreVO, resultSet1.getString("tyre_where"));
					if (a==0) {
						return 3;//保存失败
					}
				}
			}
			
			logger.info("录入花纹尺监测数据,胎:"+tyre_id);
			return 4;			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("录入花纹尺监测数据,胎:"+tyre_id+StringHelper.getTrace(e));
		}finally {
			if (resultSet != null) {
				rList.add(resultSet);
			}
			if (preparedStatement != null) {
				pList.add(preparedStatement);
			}
			if (preparedStatement1 != null) {
				pList.add(preparedStatement1);
			}
			if (preparedStatement2 != null) {
				pList.add(preparedStatement2);
			}
			if (preparedStatement3 != null) {
				pList.add(preparedStatement3);
			}
			if (stmt != null) {
				pList.add(stmt);
			}
		}
		return 0;
		
	}
	
	//胎报废
	private int tyreBaoFeiByTool(Date now,Connection connection,List<ResultSet> rList,List<PreparedStatement> pList,int tyre_flag,String tyre_id,String tyre_where,String trucks_id,float danhao,float paver){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		try {
			int a;
			if(tyre_flag==1){//在车上
				preparedStatement1 = connection.prepareStatement("update trucks SET trucks_" + tyre_where+ "=? where trucks_" + tyre_where+ "=?");
				preparedStatement1.setString(1, null);
				preparedStatement1.setString(2, tyre_id);
				a=preparedStatement1.executeUpdate();
				if(a<=0){
					return 3;//保存失败
				}
				preparedStatement2 = connection.prepareStatement("UPDATE tyre_base SET tyre_flag=0,tyre_where=null,`status`=0,dan_hao="+danhao+" WHERE tyre_id=?");
				preparedStatement2.setString(1, tyre_id);
				a=preparedStatement2.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
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
				preparedStatement2 = connection.prepareStatement("UPDATE tyre_base SET `status`=0,dan_hao="+danhao+" WHERE tyre_id=?");
				preparedStatement2.setString(1, tyre_id);
				a=preparedStatement2.executeUpdate();
				if(a<=0){
					return 3;//保存失败
				}
			}
			logger.info("胎报废保存成功,胎:"+tyre_id+",车牌号:"+trucks_id+",位置:"+tyre_where);
			return 1;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("胎报废,胎:"+tyre_id+StringHelper.getTrace(e));
		}finally {
			if (resultSet != null) {
				rList.add(resultSet);
			}
			if (preparedStatement != null) {
				pList.add(preparedStatement);
			}
			if (preparedStatement1 != null) {
				pList.add(preparedStatement1);
			}
			if (preparedStatement2 != null) {
				pList.add(preparedStatement2);
			}
			if (preparedStatement3 != null) {
				pList.add(preparedStatement3);
			}
		}
		return 0;
	}
	
}
