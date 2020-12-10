
package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.psylife.dao.TrucksDao;
import com.psylife.dao.TrucksDeviceDao;
import com.psylife.dao.TyreBaseDao;
import com.psylife.dao.WorkOrderDao;
import com.psylife.entity.DeviceFasheqi;
import com.psylife.entity.Trucks;
import com.psylife.entity.TyreBase;
import com.psylife.entity.TyreHistory;
import com.psylife.entity.WorkOrder;
import com.psylife.hardware.HardwareElement;
import com.psylife.hardware.send.SendManager;
import com.psylife.util.ConnectionPool;
import com.psylife.util.Constants;
import com.psylife.util.ResultSetUtil;
import com.psylife.util.StringHelper;
import com.psylife.util.TyreAlgorithmUtil;
import com.psylife.vo.TyreByAdminVO;
import com.psylife.vo.TyreCountVO;
import com.psylife.vo.TyreRemarkVO;
import com.psylife.vo.TyreVO;
import com.psylife.vo.tuijian.TrucksByTuiJianVO;
import com.psylife.vo.tuijian.TyreByTuiJianVO;
 
public class TyreBaseDaoImpl extends BaseDaoImpl implements TyreBaseDao{
	
	private TrucksDeviceDao trucksDeviceDao=(TrucksDeviceDao)new TrucksDeviceDaoImpl(); 
	
	private WorkOrderDao workOrderDao=(WorkOrderDao)new WorkOrderDaoImpl();
	
	private TrucksDao trucksDao=(TrucksDao)new TrucksDaoImpl();
	
	@Override
	public int saveByList(List<TyreBase> tyreBases,Integer user_id,boolean isWorkOrder) {
		int flag = 0;
		String sql ="insert into tyre_base(create_time,tyre_id,tyre_brand,tyre_flag,tyre_type1,tyre_type2,tyre_type3,tyre_type4,tyre_type5,tyre_type6,tyre_type7,tyre_type8,tyre_type9,user_id,tyre_depth) values";
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		ResultSet resultSet = null;
		try {
			StringBuffer newsql = new StringBuffer();
			StringBuffer newsql2 = new StringBuffer();
			StringBuffer newsql3 = new StringBuffer();
			newsql.append(sql);
			newsql2.append("insert into tyre_history(tyre_time,tyre_id,tyre_action,tyre_content,tyre_person,user_id) values");
			newsql3.append("insert into tyre_dynamic(tyre_id) values");
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			WorkOrder workOrder=null;
			if(isWorkOrder){
				workOrder=workOrderDao.getLastWorkOrder(user_id,null, connection);
				if(workOrder==null||workOrder.getStatus().intValue()==WorkOrder.STATUS_END.intValue()){//没有工单或已结束
					return 10;
				}
			}
			preparedStatement2 = connection.prepareStatement("SELECT true_name FROM `user` WHERE user_id=?");
			preparedStatement2.setInt(1, user_id);
			resultSet = preparedStatement2.executeQuery();
			String true_name="";
			if (resultSet.next()) {
				true_name=resultSet.getString(1);
			}else{
				return 1;
			}
			int result=-1;
			List<Object> paramterList=new ArrayList<Object>();
			List<Object> paramterList2=new ArrayList<Object>();
			List<Object> paramterList3=new ArrayList<Object>();
			TyreBase tyreBase;
			Date now=new Date();
			int state=0;
			for(int i=0;i<tyreBases.size();i++){
				tyreBase=tyreBases.get(i);
				paramterList.addAll(Arrays.asList(new Object[]{now,tyreBase.getTyre_id(),tyreBase.getTyre_brand(),tyreBase.getTyre_flag(),
						tyreBase.getTyre_type1(),tyreBase.getTyre_type2(),tyreBase.getTyre_type3(),tyreBase.getTyre_type4(),tyreBase.getTyre_type5(),tyreBase.getTyre_type6(),
						tyreBase.getTyre_type7(),tyreBase.getTyre_type8(),tyreBase.getTyre_type9(),user_id,tyreBase.getTyre_depth()}));
				paramterList2.addAll(Arrays.asList(new Object[]{now,tyreBase.getTyre_id(),TyreHistory.ACTION_TYPE_INPUT,"入库",true_name,user_id}));
				paramterList3.addAll(Arrays.asList(new Object[]{tyreBase.getTyre_id()}));
				if(i==0){
					newsql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					newsql2.append("(?,?,?,?,?,?)");
					newsql3.append("(?)");
				}else{
					newsql.append(",(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					newsql2.append(",(?,?,?,?,?,?)");
					newsql3.append(",(?)");
				}
				if(isWorkOrder){
					state+=workOrderDao.saveWorkOrderRecord(workOrder.getId(), TyreHistory.ACTION_TYPE_INPUT,"入库", tyreBase.getTyre_id(), connection);
				}
			}
			preparedStatement = connection.prepareStatement(newsql.toString());
			for(int i=0;i<paramterList.size();i++){
				preparedStatement.setObject(i+1, paramterList.get(i));
			}
			preparedStatement1 = connection.prepareStatement(newsql2.toString());
			for(int i=0;i<paramterList2.size();i++){
				preparedStatement1.setObject(i+1, paramterList2.get(i));
			}
			preparedStatement3 = connection.prepareStatement(newsql3.toString());
			for(int i=0;i<paramterList3.size();i++){
				preparedStatement3.setObject(i+1, paramterList3.get(i));
			}
			result= preparedStatement.executeUpdate();
			int result2=preparedStatement1.executeUpdate();
			int result3=preparedStatement3.executeUpdate();
			
			if(result>0&&result2>0&&result3>0&&state==0){
				flag = 0;
				connection.commit();
			}else{
				try {
					connection.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
			logger.error("批量保存轮胎基本信息:"+StringHelper.getTrace(e));
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
				if(preparedStatement2!=null && !preparedStatement2.isClosed()){
					preparedStatement2.close();
				}
				if(preparedStatement3!=null && !preparedStatement3.isClosed()){
					preparedStatement3.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
		}
		return flag;
	}
	
	@Override
	public TyreVO tyreDetial(Integer user_id,String tyre_id,String trucks_id,String tyre_where){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		String sql1="";
		String sql2="";
		boolean flag=false;
		if(tyre_id!=null&&!"".equals(tyre_id)){//按照轮胎号查询
			sql1=" AND TB.tyre_id=?";
			sql2=" (T.trucks_X1=TB.tyre_id OR T.trucks_Y1=TB.tyre_id OR T.trucks_A1=TB.tyre_id OR T.trucks_A2=TB.tyre_id OR T.trucks_A3=TB.tyre_id OR T.trucks_A4=TB.tyre_id OR T.trucks_A5=TB.tyre_id OR T.trucks_A6=TB.tyre_id OR  "+
					"T.trucks_B1=TB.tyre_id OR T.trucks_B2=TB.tyre_id OR T.trucks_B3=TB.tyre_id OR T.trucks_B4=TB.tyre_id OR T.trucks_B5=TB.tyre_id OR T.trucks_B6=TB.tyre_id OR T.trucks_B7=TB.tyre_id OR T.trucks_B8=TB.tyre_id OR  "+
					"T.trucks_C1=TB.tyre_id OR T.trucks_C2=TB.tyre_id OR T.trucks_C3=TB.tyre_id OR T.trucks_C4=TB.tyre_id OR T.trucks_C5=TB.tyre_id OR T.trucks_C6=TB.tyre_id OR  "+
					"T.trucks_C7=TB.tyre_id OR T.trucks_C8=TB.tyre_id OR T.trucks_C9=TB.tyre_id OR T.trucks_C10=TB.tyre_id OR T.trucks_C11=TB.tyre_id OR T.trucks_C12=TB.tyre_id OR  "+
					"T.trucks_C13=TB.tyre_id OR T.trucks_C14=TB.tyre_id OR T.trucks_C15=TB.tyre_id OR T.trucks_C16=TB.tyre_id ) ";
		}else if(trucks_id!=null&&!"".equals(trucks_id)&&tyre_where!=null&&!"".equals(tyre_where)){//按照车牌号和位置查询
			sql1=" AND T.trucks_id=? ";
			sql2=" (T.trucks_"+tyre_where+"=TB.tyre_id) ";
			flag=true;
		}else{
			return null;
		}
		SQL.append("SELECT DISTINCT TB.tyre_id,TB.tyre_brand,TB.tyre_flag,TB.tyre_type1,TB.tyre_type2,TB.tyre_type3,TB.tyre_type4,TB.tyre_type5,TB.tyre_type6,TB.tyre_type7,TB.tyre_type8,TB.tyre_type9,TB.tyre_where,TB.tyre_depth,TB.user_id "+
",TD.tyre_km,TD.tyre_health,TP.tyre_paver,TP.tyre_p_time,T.trucks_id,TD.mabiao_install,TD.li_cheng_run "+
"FROM tyre_base TB  "+
"LEFT JOIN `user` U ON U.user_id=TB.user_id "+
"LEFT JOIN company C ON C.company_id=U.user_company_id "+
"LEFT JOIN `user` UU ON UU.user_company_id=C.company_id "+
"LEFT JOIN tyre_dynamic TD ON TD.tyre_id=TB.tyre_id "+
"LEFT JOIN tyre_pattern TP ON TP.tyre_id=TB.tyre_id  "+
"LEFT JOIN trucks T ON  "+
 sql2+
"WHERE UU.user_id=? "+sql1+" LIMIT 1");
		TyreVO tyreVo = null;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			if(flag){
				preparedStatement.setString(2, trucks_id);
			}else{
				preparedStatement.setString(2, tyre_id);
			}
			resultSet = preparedStatement.executeQuery();
			tyreVo=ResultSetUtil.getByOne(resultSet, "tyre_id,tyre_brand,tyre_flag,tyre_type1,tyre_type2,tyre_type3,tyre_type4,tyre_type5,tyre_type6,tyre_type7,tyre_type8,tyre_type9,tyre_where,tyre_depth,user_id,tyre_km,tyre_health,tyre_paver,tyre_p_time,trucks_id,mabiao_install,li_cheng_run".split(","), 
					"tyre_id,tyre_brand,tyre_flag,tyre_type1,tyre_type2,tyre_type3,tyre_type4,tyre_type5,tyre_type6,tyre_type7,tyre_type8,tyre_type9,tyre_where,tyre_depth,user_id,tyre_km,tyre_health,tyre_paver,tyre_p_time,trucks_id,mabiao_install,li_cheng_run".split(","), TyreVO.class, false);
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("胎详情："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return tyreVo;
	}
	
	@Override
	public int tyreToTrucks(String trucks_id,String tyre_id,String tyre_where,Integer user_id,Double mabiao) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement4 = null;
		ResultSet resultSet3 = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			WorkOrder workOrder=workOrderDao.getLastWorkOrder(user_id,null, connection);
			if(workOrder==null||workOrder.getStatus().intValue()==WorkOrder.STATUS_END.intValue()){//没有工单或已结束
				return 10;
			}
//			preparedStatement = connection.prepareStatement("SELECT trucks_"+tyre_where+",trucks_id,U.true_name,mabiao FROM  Trucks T LEFT JOIN `user` U ON U.user_company_id=T.company_id WHERE T.trucks_id=? and U.user_id=?");
//			preparedStatement.setString(1, trucks_id);
//			preparedStatement.setInt(2, user_id);
			preparedStatement = connection.prepareStatement("SELECT GT.trucks_"+tyre_where+",T.trucks_"+tyre_where+",T.dtu_multi_flag,T.trucks_id,GT.trucks_id,U.true_name,T.mabiao "+
"FROM  trucks GT LEFT JOIN Trucks T  ON (GT.trucks_id=T.guache_trucks_id AND T.trucks_type='主车') LEFT JOIN `user` U ON U.user_company_id=GT.company_id  "+
"WHERE ((T.trucks_id=? AND T.guache_save_flag=1) or (GT.trucks_id=? AND GT.trucks_type='挂车')) and U.user_id=?  "+
"UNION  "+
"SELECT GT.trucks_"+tyre_where+",T.trucks_"+tyre_where+",T.dtu_multi_flag,T.trucks_id,GT.trucks_id,U.true_name,T.mabiao  "+
"FROM   Trucks T LEFT JOIN `user` U ON U.user_company_id=T.company_id LEFT JOIN trucks GT ON (GT.trucks_id=T.guache_trucks_id) "+
"WHERE ((T.trucks_id=?"+(tyre_where.indexOf("C")>=0?" AND T.guache_save_flag=0":"")+") or (GT.trucks_id=? AND GT.trucks_type='挂车')) and U.user_id=? AND T.trucks_type='主车'");
			preparedStatement.setString(1, trucks_id);
			preparedStatement.setString(2, trucks_id);
			preparedStatement.setInt(3, user_id);
			preparedStatement.setString(4, trucks_id);
			preparedStatement.setString(5, trucks_id);
			preparedStatement.setInt(6, user_id);
			resultSet = preparedStatement.executeQuery();
			String true_name="";
			boolean trucks_id_flag=false;
			String trucks_id2=trucks_id;
			if (resultSet.next()) {
				String temp=resultSet.getString(2);
				if(resultSet.getString(5)!=null){//为挂车
					temp=resultSet.getString(1);
					if(resultSet.getString(4)!=null&&resultSet.getInt("dtu_multi_flag")!=1&&tyre_where.indexOf("C")>=0){
						trucks_id_flag=true;
						trucks_id2=resultSet.getString(5);
					}else if(resultSet.getInt("dtu_multi_flag")==1&&tyre_where.indexOf("C")>=0){
						trucks_id2=resultSet.getString(5);
					}
				}				
				if(temp!=null&&!"".equals(temp)){
					return 2;//该位置存在胎
				}
				true_name=resultSet.getString(6);
			}else{
				return 1;//车牌不存在
			}
			preparedStatement3 = connection.prepareStatement("SELECT TB.tyre_id,TB.tyre_flag,TD.tyre_health FROM tyre_base TB LEFT JOIN tyre_dynamic TD ON TD.tyre_id=TB.tyre_id LEFT JOIN  `user` U ON U.user_id=TB.user_id LEFT JOIN company C ON C.company_id=U.user_company_id "+
"LEFT JOIN `user` UU ON UU.user_company_id=C.company_id WHERE TB.tyre_id=? AND UU.user_id=? LIMIT 1");
			preparedStatement3.setString(1, tyre_id);
			preparedStatement3.setInt(2, user_id);
			resultSet3=preparedStatement3.executeQuery();
			if (!resultSet3.next()) {
				return 4;//轮胎不存在
			}
			if(resultSet3.getInt("tyre_flag")==1){
				return 4;
			}
			double m=resultSet.getDouble("mabiao");
			if(mabiao!=null){
				if(m<=mabiao.doubleValue()&&resultSet.getObject("mabiao")!=null){
					if(m<mabiao.doubleValue()){
						int a=trucksDao.updateTrucksMabiao(trucks_id, mabiao, connection);//更新车码表数
						if(a!=0){
							connection.rollback();
							return 6;
						}
						m=mabiao;
					}
				}else{
					return 6;
				}	
			}
			
			if(trucks_id_flag){
				preparedStatement1 = connection.prepareStatement("update trucks SET trucks_"+tyre_where+"=? where trucks_id=? or guache_trucks_id=?");
				preparedStatement1.setString(1, tyre_id);
				preparedStatement1.setString(2, trucks_id2);
				preparedStatement1.setString(3, trucks_id2);
			}else{
				preparedStatement1 = connection.prepareStatement("update trucks SET trucks_"+tyre_where+"=? where trucks_id=?");
				preparedStatement1.setString(1, tyre_id);
				preparedStatement1.setString(2, trucks_id2);
			}			
			int a=preparedStatement1.executeUpdate();
			if(a<=0){
				connection.rollback();
				return 3;//保存失败
			}
			preparedStatement2 = connection.prepareStatement("UPDATE tyre_base LEFT JOIN tyre_dynamic on tyre_dynamic.tyre_id=tyre_base.tyre_id  SET tyre_base.tyre_flag=1,tyre_base.tyre_where=?,tyre_dynamic.mabiao_install=? WHERE tyre_base.tyre_id=?");
			preparedStatement2.setString(1, tyre_where);
			preparedStatement2.setDouble(2, m);
			preparedStatement2.setString(3, tyre_id);
			a=preparedStatement2.executeUpdate();
			if(a<=0){
				connection.rollback();
				return 3;//保存失败
			}
			preparedStatement4 = connection.prepareStatement("insert into tyre_history(tyre_time,tyre_id,tyre_action,tyre_content,tyre_person,user_id) values(?,?,?,?,?,?)");
			preparedStatement4.setObject(1, new Date());
			preparedStatement4.setString(2, tyre_id);
			preparedStatement4.setString(3, TyreHistory.ACTION_TYPE_UP);
			preparedStatement4.setString(4, trucks_id+":"+tyre_where);
			preparedStatement4.setString(5, true_name);
			preparedStatement4.setInt(6, user_id);
			a=preparedStatement4.executeUpdate();
			if(a<=0){
				connection.rollback();
				return 5;//保存胎日志失败
			}
			a=workOrderDao.saveWorkOrderRecord(workOrder.getId(), TyreHistory.ACTION_TYPE_UP,trucks_id+":"+tyre_where,tyre_id, connection);
			if(a!=0){
				connection.rollback();
				return 5;//保存胎工单失败
			}
			//更新车的健康度
			TyreVO tyreInfo=new TyreVO();
			tyreInfo.setTyre_id(tyre_id);
			tyreInfo.setTyre_health(resultSet3.getFloat("tyre_health"));
			if (tyreInfo.getTyre_health()!=null&&tyreInfo.getTyre_health().floatValue()!=0) {
				a=trucksDao.updateTrucksHealth(connection, trucks_id, 1, tyreInfo, tyre_where);
				if (a==0) {
					connection.rollback();
					return 7;
				}
			}
			connection.commit();
			logger.info("胎装载保存成功,胎:"+tyre_id+",车牌号:"+trucks_id+",位置:"+tyre_where);
			return 0;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("胎装载保存成功,胎:"+tyre_id+",车牌号:"+trucks_id+",位置:"+tyre_where+StringHelper.getTrace(e));
		}finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
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
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
		}
		return 6;
	}
	
	@Override
	public int tyreDown(String trucks_id,String tyre_id,String tyre_where,Integer user_id,Double mabiao) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		ResultSet resultSet3 = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			WorkOrder workOrder=workOrderDao.getLastWorkOrder(user_id,null, connection);
			if(workOrder==null||workOrder.getStatus().intValue()==WorkOrder.STATUS_END.intValue()){//没有工单或已结束
				return 10;
			}
//			preparedStatement = connection.prepareStatement("SELECT trucks_"+tyre_where+",trucks_id,U.true_name,mabiao FROM  Trucks T LEFT JOIN `user` U ON U.user_company_id=T.company_id WHERE T.trucks_id=? and U.user_id=? and trucks_"+tyre_where+"=?");
			preparedStatement = connection.prepareStatement("SELECT GT.trucks_"+tyre_where+",T.trucks_"+tyre_where+",T.dtu_multi_flag,T.trucks_id,GT.trucks_id,U.true_name,T.mabiao "+
					"FROM  trucks GT LEFT JOIN Trucks T  ON (GT.trucks_id=T.guache_trucks_id AND T.trucks_type='主车') LEFT JOIN `user` U ON U.user_company_id=GT.company_id  "+
					"WHERE ((T.trucks_id=? AND T.guache_save_flag=1) or (GT.trucks_id=? AND GT.trucks_type='挂车')) and U.user_id=?  "+
					"UNION  "+
					"SELECT GT.trucks_"+tyre_where+",T.trucks_"+tyre_where+",T.dtu_multi_flag,T.trucks_id,GT.trucks_id,U.true_name,T.mabiao  "+
					"FROM   Trucks T LEFT JOIN `user` U ON U.user_company_id=T.company_id LEFT JOIN trucks GT ON (GT.trucks_id=T.guache_trucks_id) "+
					"WHERE ((T.trucks_id=?"+(tyre_where.indexOf("C")>=0?" AND T.guache_save_flag=0":"")+" ) or (GT.trucks_id=? AND GT.trucks_type='挂车')) and U.user_id=? AND T.trucks_type='主车'");
			preparedStatement.setString(1, trucks_id);
			preparedStatement.setString(2, trucks_id);
			preparedStatement.setInt(3, user_id);
			preparedStatement.setString(4, trucks_id);
			preparedStatement.setString(5, trucks_id);
			preparedStatement.setInt(6, user_id);
			resultSet = preparedStatement.executeQuery();
			String true_name="";
			if (!resultSet.next()) {
				return 1;//不存在车牌或位置上没有此胎
			}else{
				true_name=resultSet.getString(6);
			}
			if(mabiao!=null){
				if(resultSet.getObject("mabiao")!=null&&resultSet.getDouble("mabiao")<=mabiao.doubleValue()){
					if(resultSet.getDouble("mabiao")<mabiao.doubleValue()){
						int a=trucksDao.updateTrucksMabiao(trucks_id, mabiao, connection);//更新车码表数
						if(a!=0){
							connection.rollback();
							return 4;
						}
					}
				}else{
					return 4;
				}	
			}
			preparedStatement1 = connection.prepareStatement("update trucks SET trucks_" + tyre_where+ "=? where trucks_" + tyre_where+ "=?");
			preparedStatement1.setString(1, null);
			preparedStatement1.setString(2, tyre_id);
			int a=preparedStatement1.executeUpdate();
			if(a<=0){
				connection.rollback();
				return 2;//保存失败
			}
			preparedStatement2 = connection.prepareStatement("UPDATE tyre_base SET tyre_flag=0,tyre_where=null WHERE tyre_id=?");
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
				connection.rollback();
				return 3;//保存胎日志失败
			}
			a=workOrderDao.saveWorkOrderRecord(workOrder.getId(), TyreHistory.ACTION_TYPE_DOWN,trucks_id+":"+tyre_where,tyre_id, connection);
			if(a!=0){
				connection.rollback();
				return 3;//保存胎工单失败
			}
			//更新车的健康度
			TyreVO tyreInfo=new TyreVO();
			tyreInfo.setTyre_id(tyre_id);
			a=trucksDao.updateTrucksHealth(connection, trucks_id, 2, tyreInfo, tyre_where);
			if (a==0) {
				connection.rollback();
				return 7;
			}
			connection.commit();
			logger.info("胎卸下保存成功,胎:"+tyre_id+",车牌号:"+trucks_id+",位置:"+tyre_where);
			return 0;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("胎卸下保存成功,胎:"+tyre_id+",车牌号:"+trucks_id+",位置:"+tyre_where+StringHelper.getTrace(e));
		}finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
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
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
		}
		return 4;
	}
	
	@Override
	public int tyreExchange(String tyre_id1,String tyre_id2,Integer user_id,Double mabiao1,Double mabiao2){
		if(tyre_id1==null||tyre_id2==null||"".equals(tyre_id1)||"".equals(tyre_id2)||tyre_id1.equalsIgnoreCase(tyre_id2)){
			return 4;
		}
		StringBuffer SQL = new StringBuffer();
		String sql1=" AND (TB.tyre_id=? or TB.tyre_id=?)";
		String sql2=" (T.trucks_X1=TB.tyre_id OR T.trucks_Y1=TB.tyre_id OR T.trucks_A1=TB.tyre_id OR T.trucks_A2=TB.tyre_id OR T.trucks_A3=TB.tyre_id OR T.trucks_A4=TB.tyre_id OR T.trucks_A5=TB.tyre_id OR T.trucks_A6=TB.tyre_id OR  "+
				"T.trucks_B1=TB.tyre_id OR T.trucks_B2=TB.tyre_id OR T.trucks_B3=TB.tyre_id OR T.trucks_B4=TB.tyre_id OR T.trucks_B5=TB.tyre_id OR T.trucks_B6=TB.tyre_id OR T.trucks_B7=TB.tyre_id OR T.trucks_B8=TB.tyre_id OR  "+
				"T.trucks_C1=TB.tyre_id OR T.trucks_C2=TB.tyre_id OR T.trucks_C3=TB.tyre_id OR T.trucks_C4=TB.tyre_id OR T.trucks_C5=TB.tyre_id OR T.trucks_C6=TB.tyre_id OR  "+
				"T.trucks_C7=TB.tyre_id OR T.trucks_C8=TB.tyre_id OR T.trucks_C9=TB.tyre_id OR T.trucks_C10=TB.tyre_id OR T.trucks_C11=TB.tyre_id OR T.trucks_C12=TB.tyre_id OR  "+
				"T.trucks_C13=TB.tyre_id OR T.trucks_C14=TB.tyre_id OR T.trucks_C15=TB.tyre_id OR T.trucks_C16=TB.tyre_id ) ";
		SQL.append("SELECT DISTINCT TB.tyre_id,TB.tyre_brand,TB.tyre_flag,TB.tyre_type1,TB.tyre_type2,TB.tyre_type3,TB.tyre_type4,TB.tyre_type5,TB.tyre_type6,TB.tyre_type7,TB.tyre_type8,TB.tyre_type9,TB.tyre_where,TB.tyre_depth,TB.user_id "+
				",TD.tyre_km,TD.tyre_health,TP.tyre_paver,TP.tyre_p_time,T.trucks_id,UU.true_name as queryer,GT.mabiao as li_cheng_run,T.dtu_multi_flag,T.trucks_type "+
				"FROM tyre_base TB  "+
				"LEFT JOIN `user` U ON U.user_id=TB.user_id "+
				"LEFT JOIN company C ON C.company_id=U.user_company_id "+
				"LEFT JOIN `user` UU ON UU.user_company_id=C.company_id "+
				"LEFT JOIN tyre_dynamic TD ON TD.tyre_id=TB.tyre_id "+
				"LEFT JOIN tyre_pattern TP ON TP.tyre_id=TB.tyre_id  "+
				"LEFT JOIN trucks T ON  "+
				 sql2+
				" LEFT JOIN trucks GT ON (GT.trucks_id=T.trucks_id or GT.guache_trucks_id=T.trucks_id) WHERE UU.user_id=? "+sql1);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement11 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement22 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement4 = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			WorkOrder workOrder=workOrderDao.getLastWorkOrder(user_id,null, connection);
			if(workOrder==null||workOrder.getStatus().intValue()==WorkOrder.STATUS_END.intValue()){//没有工单或已结束
				return 10;
			}
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			preparedStatement.setString(2, tyre_id1);
			preparedStatement.setString(3, tyre_id2);
			resultSet = preparedStatement.executeQuery();
			List<TyreVO> tyreVos=ResultSetUtil.getByList(resultSet, "tyre_id,tyre_brand,tyre_flag,tyre_type1,tyre_type2,tyre_type3,tyre_type4,tyre_type5,tyre_type6,tyre_type7,tyre_type8,tyre_type9,tyre_where,tyre_depth,user_id,tyre_km,tyre_health,tyre_paver,tyre_p_time,trucks_id,queryer,li_cheng_run,dtu_multi_flag,trucks_type".split(","), 
					"tyre_id,tyre_brand,tyre_flag,tyre_type1,tyre_type2,tyre_type3,tyre_type4,tyre_type5,tyre_type6,tyre_type7,tyre_type8,tyre_type9,tyre_where,tyre_depth,user_id,tyre_km,tyre_health,tyre_paver,tyre_p_time,trucks_id,queryer,li_cheng_run,dtu_multi_flag,trucks_type".split(","), TyreVO.class, false);
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
							a=trucksDao.updateTrucksMabiao(t.getTrucks_id(), mabiao1, connection);//更新车码表数
							if(a!=0){
								connection.rollback();
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
							a=trucksDao.updateTrucksMabiao(t.getTrucks_id(), mabiao2, connection);//更新车码表数
							if(a!=0){
								connection.rollback();
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
				preparedStatement1 = connection.prepareStatement("update trucks SET trucks_"+tyreVos.get(1).getTyre_where()+"=? where trucks_"+tyreVos.get(1).getTyre_where()+"=?");
				preparedStatement1.setString(1, temp);
				preparedStatement1.setString(2, tyreVos.get(1).getTyre_id());
				a=preparedStatement1.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
				}
				preparedStatement11 = connection.prepareStatement("update trucks SET trucks_"+tyreVos.get(0).getTyre_where()+"=? where trucks_"+tyreVos.get(0).getTyre_where()+"=?");
				preparedStatement11.setString(1, tyreVos.get(1).getTyre_id());
				preparedStatement11.setString(2, tyreVos.get(0).getTyre_id());
				a=preparedStatement11.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
				}
				
				preparedStatement4=connection.prepareStatement("update trucks SET trucks_"+tyreVos.get(1).getTyre_where()+"=? where trucks_"+tyreVos.get(1).getTyre_where()+"=?");
				preparedStatement4.setString(1, tyreVos.get(0).getTyre_id());
				preparedStatement4.setString(2, temp);
				a=preparedStatement4.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
				}
				preparedStatement2 = connection.prepareStatement("UPDATE tyre_base LEFT JOIN tyre_dynamic on tyre_dynamic.tyre_id=tyre_base.tyre_id  SET tyre_base.tyre_flag=1,tyre_base.tyre_where=?,tyre_dynamic.mabiao_install=? WHERE tyre_base.tyre_id=?");
				preparedStatement2.setString(1, tyreVos.get(1).getTyre_where());
				preparedStatement2.setDouble(2, m2);
				preparedStatement2.setString(3, tyreVos.get(0).getTyre_id());
				a=preparedStatement2.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
				}
				preparedStatement22 = connection.prepareStatement("UPDATE tyre_base LEFT JOIN tyre_dynamic on tyre_dynamic.tyre_id=tyre_base.tyre_id  SET tyre_base.tyre_flag=1,tyre_base.tyre_where=?,tyre_dynamic.mabiao_install=? WHERE tyre_base.tyre_id=?");
				preparedStatement22.setString(1, tyreVos.get(0).getTyre_where());
				preparedStatement22.setDouble(2, m1);
				preparedStatement22.setString(3, tyreVos.get(1).getTyre_id());
				a=preparedStatement22.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
				}	
				action0=TyreHistory.ACTION_TYPE_EXCHANGE;
				action1=TyreHistory.ACTION_TYPE_EXCHANGE;
				content0=tyreVos.get(0).getTrucks_id()+":"+tyreVos.get(0).getTyre_where()+"<>"+tyreVos.get(1).getTrucks_id()+":"+tyreVos.get(1).getTyre_where();
				content1=tyreVos.get(1).getTrucks_id()+":"+tyreVos.get(1).getTyre_where()+"<>"+tyreVos.get(0).getTrucks_id()+":"+tyreVos.get(0).getTyre_where();
			
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
							a=trucksDao.updateTrucksMabiao(t.getTrucks_id(), m, connection);//更新车码表数
							if(a!=0){
								connection.rollback();
								return 4;
							}
							t.setLi_cheng_run(m);
						}
					}else{
						return 4;
					}
				}
				preparedStatement11 = connection.prepareStatement("update trucks SET trucks_"+tyreVos.get(0).getTyre_where()+"=? where trucks_"+tyreVos.get(0).getTyre_where()+"=?");
				preparedStatement11.setString(1, tyreVos.get(1).getTyre_id());
				preparedStatement11.setString(2, tyreVos.get(0).getTyre_id());
				a=preparedStatement11.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
				}
				preparedStatement2 = connection.prepareStatement("UPDATE tyre_base SET tyre_flag=0,tyre_where=? WHERE tyre_id=?");
				preparedStatement2.setString(1, null);
				preparedStatement2.setString(2, tyreVos.get(0).getTyre_id());
				a=preparedStatement2.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
				}
				preparedStatement22 = connection.prepareStatement("UPDATE tyre_base LEFT JOIN tyre_dynamic on tyre_dynamic.tyre_id=tyre_base.tyre_id  SET tyre_base.tyre_flag=1,tyre_base.tyre_where=?,tyre_dynamic.mabiao_install=? WHERE tyre_base.tyre_id=?");
				preparedStatement22.setString(1, tyreVos.get(0).getTyre_where());
				preparedStatement22.setDouble(2, tyreVos.get(0).getLi_cheng_run());
				preparedStatement22.setString(3, tyreVos.get(1).getTyre_id());
				a=preparedStatement22.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
				}	
				action0=TyreHistory.ACTION_TYPE_DOWN;
				action1=TyreHistory.ACTION_TYPE_UP;
				content0=tyreVos.get(0).getTrucks_id()+":"+tyreVos.get(0).getTyre_where();
				content1=tyreVos.get(0).getTrucks_id()+":"+tyreVos.get(0).getTyre_where();
				
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
							a=trucksDao.updateTrucksMabiao(t.getTrucks_id(), m, connection);//更新车码表数
							if(a!=0){
								connection.rollback();
								return 4;
							}
							t.setLi_cheng_run(m);
						}
					}else{
						return 4;
					}
				}
				preparedStatement11 = connection.prepareStatement("update trucks SET trucks_"+tyreVos.get(1).getTyre_where()+"=? where trucks_"+tyreVos.get(1).getTyre_where()+"=?");
				preparedStatement11.setString(1, tyreVos.get(0).getTyre_id());
				preparedStatement11.setString(2, tyreVos.get(1).getTyre_id());
				a=preparedStatement11.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
				}
				preparedStatement2 = connection.prepareStatement("UPDATE tyre_base SET tyre_flag=0,tyre_where=? WHERE tyre_id=?");
				preparedStatement2.setString(1, null);
				preparedStatement2.setString(2, tyreVos.get(1).getTyre_id());
				a=preparedStatement2.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
				}
				preparedStatement22 = connection.prepareStatement("UPDATE tyre_base LEFT JOIN tyre_dynamic on tyre_dynamic.tyre_id=tyre_base.tyre_id  SET tyre_base.tyre_flag=1,tyre_base.tyre_where=?,tyre_dynamic.mabiao_install=? WHERE tyre_base.tyre_id=?");
				preparedStatement22.setString(1, tyreVos.get(1).getTyre_where());
				preparedStatement22.setDouble(2, tyreVos.get(1).getLi_cheng_run());
				preparedStatement22.setString(3, tyreVos.get(0).getTyre_id());
				a=preparedStatement22.executeUpdate();
				if(a<=0){
					connection.rollback();
					return 3;//保存失败
				}	
				action0=TyreHistory.ACTION_TYPE_UP;
				action1=TyreHistory.ACTION_TYPE_DOWN;
				content0=tyreVos.get(1).getTrucks_id()+":"+tyreVos.get(1).getTyre_where();
				content1=tyreVos.get(1).getTrucks_id()+":"+tyreVos.get(1).getTyre_where();
			}
			
			preparedStatement3 = connection.prepareStatement("insert into tyre_history(tyre_time,tyre_id,tyre_action,tyre_content,tyre_person,user_id) values(?,?,?,?,?,?),(?,?,?,?,?,?)");
			Date now=new Date();
			preparedStatement3.setObject(1,now);
			preparedStatement3.setString(2, tyreVos.get(0).getTyre_id());
			preparedStatement3.setString(3, action0);
			preparedStatement3.setString(4, content0);
			preparedStatement3.setString(5, true_name);
			preparedStatement3.setInt(6, user_id);
			preparedStatement3.setObject(7,now);
			preparedStatement3.setString(8, tyreVos.get(1).getTyre_id());
			preparedStatement3.setString(9, action1);
			preparedStatement3.setString(10, content1);
			preparedStatement3.setString(11, true_name);
			preparedStatement3.setInt(12, user_id);
			a=preparedStatement3.executeUpdate();
			if(a<=0){
				connection.rollback();
				return 3;//保存胎日志失败
			}
			a=workOrderDao.saveWorkOrderRecord(workOrder.getId(), action0,content0,tyreVos.get(0).getTyre_id(), connection);
			if(a!=0){
				connection.rollback();
				return 3;//保存胎工单失败
			}
			a=workOrderDao.saveWorkOrderRecord(workOrder.getId(), action1,content1,tyreVos.get(1).getTyre_id(), connection);
			if(a!=0){
				connection.rollback();
				return 3;//保存胎工单失败
			}
			connection.commit();
			logger.info("换胎保存成功,胎:"+tyreVos.get(0).getTyre_id()+",车牌号:"+tyreVos.get(0).getTrucks_id()+",位置:"+tyreVos.get(0).getTyre_where()+"<>胎:"+tyreVos.get(1).getTyre_id()+",车牌号:"+tyreVos.get(1).getTrucks_id()+",位置:"+tyreVos.get(1).getTyre_where());
			try {
				//重新加载设备和车牌对应关系
				HardwareElement element=SendManager.getInstance().getHardwareElementByCarNum(tyreVos.get(0).getTrucks_id());
				if(element!=null){
					trucksDeviceDao.reloadTrucksDevice(element);
				}
				
				element=SendManager.getInstance().getHardwareElementByCarNum(tyreVos.get(1).getTrucks_id());
				if(element!=null){
					trucksDeviceDao.reloadTrucksDevice(element);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("换胎保存成功,胎:"+tyre_id1+"<>胎:"+tyre_id2+"\n"+StringHelper.getTrace(e));
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
				if(preparedStatement2!=null && !preparedStatement2.isClosed()){
					preparedStatement2.close();
				}
				if(preparedStatement11!=null && !preparedStatement11.isClosed()){
					preparedStatement11.close();
				}
				if(preparedStatement22!=null && !preparedStatement22.isClosed()){
					preparedStatement22.close();
				}
				if(preparedStatement3!=null && !preparedStatement3.isClosed()){
					preparedStatement3.close();
				}
				if(preparedStatement4!=null && !preparedStatement4.isClosed()){
					preparedStatement4.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
		}
		return 4;
	}
	
	
	@Override
	public List<JSONObject> tuiJian(int id) {
		System.out.println("开始查询推荐");
		Connection conn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		PreparedStatement pt1 = null;
		ResultSet rs1 = null;
		TrucksByTuiJianVO t = null;
		List<JSONObject> result=new ArrayList<JSONObject>();
		JSONArray json;
		try {
			conn = ConnectionPool.getConnection();
			String sql = "SELECT * from trucks where company_id="+id;
			pt = conn.prepareStatement(sql);
			rs = pt.executeQuery();
			while (rs.next()) {
				t = new TrucksByTuiJianVO();
				t.setTrucks_id(rs.getString("trucks_id"));
				t.setTrucks_A1(rs.getString("trucks_A1"));
				t.setTrucks_A2(rs.getString("trucks_A2"));
				t.setTrucks_A3(rs.getString("trucks_A3"));
				t.setTrucks_A4(rs.getString("trucks_A4"));
				t.setTrucks_A5(rs.getString("trucks_A5"));
				t.setTrucks_A6(rs.getString("trucks_A6"));
				
				t.setTrucks_B1(rs.getString("trucks_B1"));
				t.setTrucks_B2(rs.getString("trucks_B2"));
				t.setTrucks_B3(rs.getString("trucks_B3"));
				t.setTrucks_B4(rs.getString("trucks_B4"));
				t.setTrucks_B5(rs.getString("trucks_B5"));
				t.setTrucks_B6(rs.getString("trucks_B6"));
				t.setTrucks_B7(rs.getString("trucks_B7"));
				t.setTrucks_B8(rs.getString("trucks_B8"));
				
				t.setTrucks_C1(rs.getString("trucks_C1"));
				t.setTrucks_C2(rs.getString("trucks_C2"));
				t.setTrucks_C3(rs.getString("trucks_C3"));
				t.setTrucks_C4(rs.getString("trucks_C4"));
				t.setTrucks_C5(rs.getString("trucks_C5"));
				t.setTrucks_C6(rs.getString("trucks_C6"));
				
				t.setTrucks_C7(rs.getString("trucks_C7"));
				t.setTrucks_C8(rs.getString("trucks_C8"));
				t.setTrucks_C9(rs.getString("trucks_C9"));
				t.setTrucks_C10(rs.getString("trucks_C10"));
				t.setTrucks_C11(rs.getString("trucks_C11"));
				t.setTrucks_C12(rs.getString("trucks_C12"));
				
				t.setTrucks_C13(rs.getString("trucks_C13"));
				t.setTrucks_C14(rs.getString("trucks_C14"));
				t.setTrucks_C15(rs.getString("trucks_C15"));
				t.setTrucks_C16(rs.getString("trucks_C16"));
				try {
					String sql2 = " SELECT T.trucks_id,TB.id,TB.tyre_id,TB.tyre_brand,TB.tyre_flag,TB.tyre_type1,TB.tyre_type3,TB.tyre_where,TP.tyre_paver "+
							" FROM tyre_base TB LEFT JOIN tyre_pattern TP ON TP.tyre_id=TB.tyre_id LEFT JOIN trucks T  "+
							" ON (TB.tyre_id=T.trucks_A1 OR TB.tyre_id=T.trucks_A2 OR TB.tyre_id=T.trucks_A3 OR TB.tyre_id=T.trucks_A4 OR TB.tyre_id=T.trucks_A5 OR TB.tyre_id=T.trucks_A6 "+
							"  OR TB.tyre_id=T.trucks_B1 OR TB.tyre_id=T.trucks_B2 OR TB.tyre_id=T.trucks_B3 OR TB.tyre_id=T.trucks_B4 OR TB.tyre_id=T.trucks_B5 OR TB.tyre_id=T.trucks_B6 OR TB.tyre_id=T.trucks_B7 OR TB.tyre_id=T.trucks_B8 "+
							" OR TB.tyre_id=T.trucks_C1 OR TB.tyre_id=T.trucks_C2 OR TB.tyre_id=T.trucks_C3 OR TB.tyre_id=T.trucks_C4 OR TB.tyre_id=T.trucks_C5 OR TB.tyre_id=T.trucks_C6 "+
							"  OR TB.tyre_id=T.trucks_C7 OR TB.tyre_id=T.trucks_C8 OR TB.tyre_id=T.trucks_C9 OR TB.tyre_id=T.trucks_C10 OR TB.tyre_id=T.trucks_C11 OR TB.tyre_id=T.trucks_C12 "+
							"  OR TB.tyre_id=T.trucks_C13 OR TB.tyre_id=T.trucks_C14 OR TB.tyre_id=T.trucks_C15 OR TB.tyre_id=T.trucks_C16) "+
							" WHERE T.trucks_id=? GROUP BY TB.tyre_id ";
					pt1 = conn.prepareStatement(sql2);
					pt1.setString(1,rs.getString("trucks_id") );
					rs1 = pt1.executeQuery();
					TyreByTuiJianVO tyreVO;
					while (rs1.next()) {
						tyreVO=new TyreByTuiJianVO();
						tyreVO.setTyre_id(rs1.getString("tyre_id"));
						if(tyreVO.getTyre_id()!=null){
							tyreVO.setPattern(rs1.getString("tyre_type3"));
							tyreVO.setStandard(rs1.getString("tyre_type1"));
							tyreVO.setTyre_paver(rs1.getFloat("tyre_paver"));
							tyreVO.setTyre_where(rs1.getString("tyre_where"));
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
						if (rs1 != null && !rs1.isClosed()) {
							rs1.close();
						}
						if (pt1 != null && !pt1.isClosed()) {
							pt1.close();
						}
						System.out.println("计算推荐tyre_id="+t.getTrucks_id()+"结束");
					} catch (Exception e) {
						e.printStackTrace();
					}							
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}

				if (pt != null && !pt.isClosed()) {
					pt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("结束查询推荐");
		}
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
		List<TyreCountVO> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT * FROM (");
		SQL.append("SELECT count(DISTINCT TB.id) as tyrecount,TB.tyre_brand,TB.tyre_type1,TB.tyre_type2,TB.tyre_type3,AVG(TD.tyre_health) AS tyre_health")
           .append(" FROM tyre_base TB LEFT JOIN tyre_dynamic TD ON TB.tyre_id=TD.tyre_id")
           .append(" LEFT JOIN `user` U ON TB.user_id=U.user_id LEFT JOIN `user` UU ON U.user_company_id=UU.user_company_id")
           .append(" WHERE UU.user_id=? and TB.`status`!=?  GROUP BY TB.tyre_brand,TB.tyre_type1,TB.tyre_type2 ");
		// .append(" WHERE UU.user_id=? GROUP BY TB.tyre_brand,TB.tyre_type1,TB.tyre_type3 LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		SQL.append(")t where 1=1");
		if (StringUtils.isNotEmpty(tyre_brand)) {
			SQL.append(" and tyre_brand='").append(tyre_brand).append("' ");
		}
		if (StringUtils.isNotEmpty(tyre_type1)) {
			SQL.append(" and tyre_type1='").append(tyre_type1).append("' ");
		}
		if (StringUtils.isNotEmpty(tyre_type3)) {
			SQL.append(" and tyre_type3='").append(tyre_type3).append("' ");
		}
		if(tyre_health!=null){
			if(tyre_health.intValue()==1){
				SQL.append(" and tyre_health>").append(30).append(" ");
			}else{
				SQL.append(" and tyre_health<=").append(30).append(" ");
			}
		}
		String orderSQL="";
		if (StringUtils.isNotEmpty(column)) {
			
			orderSQL+=" order by ";
			
			if ("1".equals(column)) {
				//品牌
				orderSQL+=" tyre_brand ";
			}else if ("2".equals(column)) {
				//规格
				orderSQL+=" tyre_type1 ";
			}else if ("3".equals(column)) {
				//花纹代码
				orderSQL+=" tyre_type2 ";
			}else if ("4".equals(column)) {
				//数量
				orderSQL+=" tyrecount ";
			}else if ("5".equals(column)) {
				//健康
				orderSQL+=" tyre_health ";
			}else if ("6".equals(column)) {
				//花纹类型
				orderSQL+=" tyre_type3 ";
			}
			
			//排列顺序
			if (StringUtils.isNotEmpty(order)){
				if ("2".equals(order)) {
					order="DESC";
				}else {
					order="ASC";
				}
			}else {
				order="";
			}
			orderSQL+=order;
			
		}
		SQL.append(orderSQL);
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, Constants.TYRE_STATUS_DELETE);
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "count,tyre_brand,trucks_brand,tyre_type1,tyre_type2,tyre_type3,tyre_health".split(","), "tyrecount,tyre_brand,trucks_brand,tyre_type1,tyre_type2,tyre_type3,tyre_health".split(","), TyreCountVO.class, false);
			logger.info("根据用户获取胎汇总列表成功！");
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("根据用户获取胎汇总列表失败！："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return list;
	}
	
	@Override
	public List<TyreVO> getTyreList(int pagenum,Integer user_id,String tyre_brand,String tyre_type1,String tyre_type2,String tyre_type3,Integer tyre_flag,Integer tyre_health,String keyWord){
		List<TyreVO> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT DISTINCT TB.id,TB.tyre_id,TB.tyre_flag,TD.tyre_health,TD.li_cheng_run as tyre_km")
           .append(" FROM tyre_base TB LEFT JOIN tyre_dynamic TD ON TB.tyre_id=TD.tyre_id")
           .append(" LEFT JOIN `user` U ON TB.user_id=U.user_id LEFT JOIN `user` UU ON U.user_company_id=UU.user_company_id")
           .append(" WHERE UU.user_id=? AND TB.tyre_brand=? AND TB.tyre_type1=? AND TB.tyre_type2=? AND TB.tyre_type3=?  and TB.`status`!=").append(Constants.TYRE_STATUS_DELETE).append("  ");
		if(tyre_flag!=null){
			SQL.append(" and TB.tyre_flag=").append(tyre_flag);
		}
		if(tyre_health!=null){
			if(tyre_health.intValue()==1){
				SQL.append(" and TD.tyre_health>").append(30);
			}else{
				SQL.append(" and TD.tyre_health<=").append(30);
			}
		}
		if(keyWord!=null&&!"".equals(keyWord)){
			SQL.append(" and TB.tyre_id like ? ");
		}
		SQL.append(" LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			preparedStatement.setString(2, tyre_brand);
			preparedStatement.setString(3, tyre_type1);
			preparedStatement.setString(4, tyre_type2);
			preparedStatement.setString(5, tyre_type3);
			if(keyWord!=null&&!"".equals(keyWord)){
				preparedStatement.setString(6, "%"+keyWord+"%");
			}
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "tyre_id,tyre_flag,tyre_health,tyre_km".split(","), "tyre_id,tyre_flag,tyre_health,tyre_km".split(","), TyreVO.class, false);
			logger.info("根据用户品牌规格花纹获取胎列表成功！");
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("根据用户品牌规格花纹获取胎列表失败！："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return list;
	}
	
	@Override
	public List<TyreVO> searchByKeyWord(int pagenum,Integer user_id,String keyWord){
		List<TyreVO> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT DISTINCT TB.id,TB.tyre_id,TB.tyre_flag,TD.tyre_health,TD.li_cheng_run as tyre_km")
           .append(" FROM tyre_base TB LEFT JOIN tyre_dynamic TD ON TB.tyre_id=TD.tyre_id")
           .append(" LEFT JOIN `user` U ON TB.user_id=U.user_id LEFT JOIN `user` UU ON U.user_company_id=UU.user_company_id")
           .append(" WHERE UU.user_id=? ");
		if(keyWord!=null&&!"".equals(keyWord)){
			SQL.append(" and TB.tyre_id like ? ");
		}
		SQL.append(" LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			if(keyWord!=null&&!"".equals(keyWord)){
				preparedStatement.setString(2, "%"+keyWord+"%");
			}
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "tyre_id,tyre_flag,tyre_health,tyre_km".split(","), "tyre_id,tyre_flag,tyre_health,tyre_km".split(","), TyreVO.class, false);
			logger.info("根据关键字获取胎列表成功！");
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("根据关键字获取胎列表失败！："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return list;
	}
	
	@Override
	public List<TyreVO> searchByKucun(int pagenum,Integer user_id,String keyWord, String tyre_brand,
			String tyre_type1, String tyre_type3,Integer state,Integer tyre_flag,String column,String order){
		List<TyreVO> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT DISTINCT TB.id,TB.tyre_id,TB.tyre_flag,TD.tyre_health,TD.li_cheng_run as tyre_km,TB.tyre_brand,TB.tyre_type1,TB.tyre_type2,TB.tyre_type3,TB.status")
           .append(" FROM tyre_base TB LEFT JOIN tyre_dynamic TD ON TB.tyre_id=TD.tyre_id")
           .append(" LEFT JOIN `user` U ON TB.user_id=U.user_id LEFT JOIN `user` UU ON U.user_company_id=UU.user_company_id")
           .append(" WHERE UU.user_id=? ");
		if(StringUtils.isNotEmpty(tyre_brand)){
			SQL.append(" and TB.tyre_brand='").append(tyre_brand).append("' ");
		}
		if(StringUtils.isNotEmpty(tyre_type1)){
			SQL.append(" and TB.tyre_type1='").append(tyre_type1).append("' ");
		}
		if(StringUtils.isNotEmpty(tyre_type3)){
			SQL.append(" and TB.tyre_type3='").append(tyre_type3).append("' ");
		}
		if(state!=null){
			SQL.append(" and TB.`status`=").append(state).append(" ");
		}
		if(tyre_flag!=null){
			SQL.append(" and TB.tyre_flag=").append(tyre_flag).append(" ");
		}
		if(keyWord!=null&&!"".equals(keyWord)){
			SQL.append(" and TB.tyre_id like ? ");
		}
		String orderSQL="";
		if (StringUtils.isNotEmpty(column)) {
			
			orderSQL+=" order by ";
			
			if ("1".equals(column)) {
				//品牌
				orderSQL+=" TB.tyre_brand ";
			}else if ("2".equals(column)) {
				//规格
				orderSQL+=" TB.tyre_type1 ";
			}else if ("3".equals(column)) {
				//花纹代码
				orderSQL+=" TB.tyre_type2 ";
			}else if ("4".equals(column)) {
				//总里程
				orderSQL+=" TD.li_cheng_run ";
			}else if ("5".equals(column)) {
				//健康值
				orderSQL+=" TD.tyre_health ";
			}else if ("6".equals(column)) {
				//花纹类型
				orderSQL+=" TB.tyre_type3 ";
			}else if ("7".equals(column)) {
				//状态
				orderSQL+=" TB.tyre_flag ";
			}else if ("8".equals(column)) {
				//轮胎类型
				orderSQL+=" TB.status ";
			}else if ("9".equals(column)) {
				//轮胎号
				orderSQL+=" TB.tyre_id ";
			}
			
			
			//排列顺序
			if (StringUtils.isNotEmpty(order)){
				if ("2".equals(order)) {
					order="DESC";
				}else {
					order="ASC";
				}
			}else {
				order="";
			}
			orderSQL+=order;
			
		}
		SQL.append(orderSQL);
		SQL.append(" LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			if(keyWord!=null&&!"".equals(keyWord)){
				preparedStatement.setString(2, "%"+keyWord+"%");
			}
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "tyre_id,tyre_flag,tyre_health,tyre_km,tyre_brand,tyre_type1,tyre_type2,tyre_type3,status".split(","), "tyre_id,tyre_flag,tyre_health,tyre_km,tyre_brand,tyre_type1,tyre_type2,tyre_type3,status".split(","), TyreVO.class, false);
			logger.info("根据关键字等获取胎库存列表成功！");
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("根据关键字等获取胎库存列表失败！："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return list;
	}
	
	
	@Override
	public TyreByAdminVO tyreDetialByAdmin(String tyre_id){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet1 = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet resultSet2 = null;
		StringBuffer SQL = new StringBuffer();
		String sql2="";
		sql2=" ((T.trucks_X1=TB.tyre_id OR T.trucks_A1=TB.tyre_id OR T.trucks_A2=TB.tyre_id OR T.trucks_A3=TB.tyre_id OR T.trucks_A4=TB.tyre_id OR T.trucks_A5=TB.tyre_id OR T.trucks_A6=TB.tyre_id OR  "+
				"T.trucks_B1=TB.tyre_id OR T.trucks_B2=TB.tyre_id OR T.trucks_B3=TB.tyre_id OR T.trucks_B4=TB.tyre_id OR T.trucks_B5=TB.tyre_id OR T.trucks_B6=TB.tyre_id OR T.trucks_B7=TB.tyre_id OR T.trucks_B8=TB.tyre_id) OR  "+
				"((T.trucks_Y1=TB.tyre_id OR T.trucks_C1=TB.tyre_id OR T.trucks_C2=TB.tyre_id OR T.trucks_C3=TB.tyre_id OR T.trucks_C4=TB.tyre_id OR T.trucks_C5=TB.tyre_id OR T.trucks_C6=TB.tyre_id OR  "+
				"T.trucks_C7=TB.tyre_id OR T.trucks_C8=TB.tyre_id OR T.trucks_C9=TB.tyre_id OR T.trucks_C10=TB.tyre_id OR T.trucks_C11=TB.tyre_id OR T.trucks_C12=TB.tyre_id OR  "+
				"T.trucks_C13=TB.tyre_id OR T.trucks_C14=TB.tyre_id OR T.trucks_C15=TB.tyre_id OR T.trucks_C16=TB.tyre_id ) and ((T.trucks_type='主车' and T.guache_save_flag=0) or T.trucks_type='挂车'))) ";
		SQL.append("SELECT DISTINCT TB.tyre_id,TB.tyre_brand,TB.tyre_flag,TB.tyre_type1,TB.tyre_type2,TB.tyre_type3,TB.tyre_type4,TB.tyre_type5,TB.tyre_type6,TB.tyre_type7,TB.tyre_type8,TB.tyre_type9,TB.tyre_where,TB.tyre_depth,TB.user_id,TB.tyre_trauma,TB.tyre_abnormal "+
",TD.tyre_km,TD.mabiao_install,TD.li_cheng_run,TD.li_cheng_estimate,TD.tyre_health,TP.tyre_paver,TP.tyre_p_time,T.trucks_id,T.trucks_flag,T.trucks_brand,T.trucks_style,TB.tyre_value,T.li_cheng_run as trucks_li_cheng_run,D.dtu_id,T.trucks_mode,T.mabiao,TB.remark,TB.tyre_fanxin,TB.dan_hao,D.caiji_time "+
"FROM tyre_base TB  "+
"LEFT JOIN tyre_dynamic TD ON TD.tyre_id=TB.tyre_id "+
"LEFT JOIN tyre_pattern TP ON TP.tyre_id=TB.tyre_id  "+
"LEFT JOIN trucks T ON  "+
 sql2+"  LEFT JOIN device D ON  D.trucks_id=T.trucks_id "+
"WHERE TB.tyre_id=? LIMIT 1");
		TyreByAdminVO tyreVo = null;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setString(1, tyre_id);
			resultSet = preparedStatement.executeQuery();
			tyreVo=ResultSetUtil.getByOne(resultSet, "tyre_value,tyre_id,tyre_brand,tyre_flag,tyre_type1,tyre_type2,tyre_type3,tyre_type4,tyre_type5,tyre_type6,tyre_type7,tyre_type8,tyre_type9,tyre_where,tyre_depth,user_id,tyre_km,mabiao_install,li_cheng_run,li_cheng_estimate,tyre_health,tyre_paver,tyre_p_time,trucks_id,trucks_flag,trucks_brand,trucks_style,tyre_trauma,tyre_abnormal,trucks_li_cheng_run,dtu_id,trucks_mode,mabiao,remark,tyre_fanxin,dan_hao,caiji_time".split(","), 
					"tyre_value,tyre_id,tyre_brand,tyre_flag,tyre_type1,tyre_type2,tyre_type3,tyre_type4,tyre_type5,tyre_type6,tyre_type7,tyre_type8,tyre_type9,tyre_where,tyre_depth,user_id,tyre_km,mabiao_install,li_cheng_run,li_cheng_estimate,tyre_health,tyre_paver,tyre_p_time,trucks_id,trucks_flag,trucks_brand,trucks_style,tyre_trauma,tyre_abnormal,trucks_li_cheng_run,dtu_id,trucks_mode,mabiao,remark,tyre_fanxin,dan_hao,caiji_time".split(","), TyreByAdminVO.class, false);

		    if(tyreVo!=null){
				if (tyreVo.getDtuOnlineStatus()==0) {//DTU掉线，车辆行驶状态改为停止
					tyreVo.setTrucks_flag(0);
				}
		    	//设备发射器
				preparedStatement2 = connection.prepareStatement(
						"SELECT id,create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,no,tyre_id,trucks_id,li_cheng,dtu_id "+
	 "FROM device_fasheqi WHERE tyre_id=? LIMIT 1 ");
				preparedStatement2.setString(1, tyreVo.getTyre_id());
				resultSet2 = preparedStatement2.executeQuery();
				DeviceFasheqi deviceFasheqi=ResultSetUtil.getByOne(resultSet2, "id,create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,no,tyre_id,trucks_id,li_cheng,dtu_id".split(","), 
						"id,create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,no,tyre_id,trucks_id,li_cheng,dtu_id".split(","), DeviceFasheqi.class, false);
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
		    preparedStatement1 = connection.prepareStatement("SELECT * FROM tyre_history WHERE tyre_id=? AND tyre_action=? limit 1");
			preparedStatement1.setString(1, tyre_id);
			preparedStatement1.setString(2, TyreHistory.ACTION_TYPE_INPUT);
			resultSet1 = preparedStatement1.executeQuery();
		    if(resultSet1.next()){
		    	tyreVo.setCreate_time(resultSet1.getTimestamp("tyre_time"));
		    }
			
		} catch (Exception e) {
			logger.error("管理员查车详情："+StringHelper.getTrace(e));
		} finally {
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
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
				if (preparedStatement1 != null && !preparedStatement1.isClosed()) {
					preparedStatement1.close();
					preparedStatement1=null;
				}
				if (preparedStatement2 != null && !preparedStatement2.isClosed()) {
					preparedStatement2.close();
					preparedStatement2=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return tyreVo;
	}
	
	@Override
	public List<TyreRemarkVO> tyreTips(Integer user_id){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet1 = null;
		StringBuffer SQL = new StringBuffer();
		String sql2=" (T.trucks_X1=TB.tyre_id OR T.trucks_Y1=TB.tyre_id OR T.trucks_A1=TB.tyre_id OR T.trucks_A2=TB.tyre_id OR T.trucks_A3=TB.tyre_id OR T.trucks_A4=TB.tyre_id OR T.trucks_A5=TB.tyre_id OR T.trucks_A6=TB.tyre_id OR  "+
					"T.trucks_B1=TB.tyre_id OR T.trucks_B2=TB.tyre_id OR T.trucks_B3=TB.tyre_id OR T.trucks_B4=TB.tyre_id OR T.trucks_B5=TB.tyre_id OR T.trucks_B6=TB.tyre_id OR T.trucks_B7=TB.tyre_id OR T.trucks_B8=TB.tyre_id OR  "+
					"T.trucks_C1=TB.tyre_id OR T.trucks_C2=TB.tyre_id OR T.trucks_C3=TB.tyre_id OR T.trucks_C4=TB.tyre_id OR T.trucks_C5=TB.tyre_id OR T.trucks_C6=TB.tyre_id OR  "+
					"T.trucks_C7=TB.tyre_id OR T.trucks_C8=TB.tyre_id OR T.trucks_C9=TB.tyre_id OR T.trucks_C10=TB.tyre_id OR T.trucks_C11=TB.tyre_id OR T.trucks_C12=TB.tyre_id OR  "+
					"T.trucks_C13=TB.tyre_id OR T.trucks_C14=TB.tyre_id OR T.trucks_C15=TB.tyre_id OR T.trucks_C16=TB.tyre_id ) ";
		SQL.append("SELECT DISTINCT TB.tyre_id,TB.tyre_where,T.trucks_id,TB.remark "+
"FROM tyre_base TB  "+
"LEFT JOIN `user` U ON U.user_id=TB.user_id "+
"LEFT JOIN company C ON C.company_id=U.user_company_id "+
"LEFT JOIN `user` UU ON UU.user_company_id=C.company_id "+
"INNER JOIN work_order_record WOR ON (WOR.tyre_id=TB.tyre_id AND WOR.`status`=1) "+
"LEFT JOIN trucks T ON  "+
 sql2+
"WHERE (TB.remark!='' OR TB.remark!=NULL) and TB.`status`=1 and UU.user_id=? and WOR.work_order_id=? GROUP BY TB.tyre_id ORDER BY WOR.create_time DESC ");
		List<TyreRemarkVO> tyreRemarkVOs = null;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement1 = connection.prepareStatement("SELECT * FROM work_order WO LEFT JOIN `user` U ON U.user_id=WO.user_id LEFT JOIN company C ON C.company_id=U.user_company_id LEFT JOIN `user` UU ON UU.user_company_id=C.company_id "+
 "WHERE WO.`status`=? AND UU.user_id=? ORDER BY WO.create_time DESC LIMIT 1");
			preparedStatement1.setInt(1, WorkOrder.STATUS_END);
			preparedStatement1.setInt(2, user_id);
			resultSet1 = preparedStatement1.executeQuery();
			int id=0;
			if(resultSet1.next()){
				id=resultSet1.getInt("id");
			}else{
				return null;
			}
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, id);
			resultSet = preparedStatement.executeQuery();
			tyreRemarkVOs=ResultSetUtil.getByList(resultSet, "tyre_id,tyre_where,trucks_id,remark".split(","), 
					"tyre_id,tyre_where,trucks_id,remark".split(","), TyreRemarkVO.class, false);
		} catch (Exception e) {
			logger.error("胎的小贴士列表："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (resultSet1 != null && !resultSet1.isClosed()) {
					resultSet1.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
				if (preparedStatement1 != null && !preparedStatement1.isClosed()) {
					preparedStatement1.close();
					preparedStatement1=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return tyreRemarkVOs;
	}
	
	@Override
	public List<TyreRemarkVO> tyreTips(Integer user_id,int pagenum){
		Connection connection = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet1 = null;
		StringBuffer SQL = new StringBuffer();
		String sql2=" (T.trucks_X1=TB.tyre_id OR T.trucks_Y1=TB.tyre_id OR T.trucks_A1=TB.tyre_id OR T.trucks_A2=TB.tyre_id OR T.trucks_A3=TB.tyre_id OR T.trucks_A4=TB.tyre_id OR T.trucks_A5=TB.tyre_id OR T.trucks_A6=TB.tyre_id OR  "+
					"T.trucks_B1=TB.tyre_id OR T.trucks_B2=TB.tyre_id OR T.trucks_B3=TB.tyre_id OR T.trucks_B4=TB.tyre_id OR T.trucks_B5=TB.tyre_id OR T.trucks_B6=TB.tyre_id OR T.trucks_B7=TB.tyre_id OR T.trucks_B8=TB.tyre_id OR  "+
					"T.trucks_C1=TB.tyre_id OR T.trucks_C2=TB.tyre_id OR T.trucks_C3=TB.tyre_id OR T.trucks_C4=TB.tyre_id OR T.trucks_C5=TB.tyre_id OR T.trucks_C6=TB.tyre_id OR  "+
					"T.trucks_C7=TB.tyre_id OR T.trucks_C8=TB.tyre_id OR T.trucks_C9=TB.tyre_id OR T.trucks_C10=TB.tyre_id OR T.trucks_C11=TB.tyre_id OR T.trucks_C12=TB.tyre_id OR  "+
					"T.trucks_C13=TB.tyre_id OR T.trucks_C14=TB.tyre_id OR T.trucks_C15=TB.tyre_id OR T.trucks_C16=TB.tyre_id ) ";
		SQL.append("SELECT DISTINCT TB.tyre_id,TB.tyre_where,T.trucks_id,TB.remark "+
"FROM tyre_base TB  "+
"LEFT JOIN `user` U ON U.user_id=TB.user_id "+
"LEFT JOIN company C ON C.company_id=U.user_company_id "+
"LEFT JOIN `user` UU ON UU.user_company_id=C.company_id "+
"LEFT JOIN trucks T ON  "+
 sql2+
"WHERE (TB.remark!='' OR TB.remark!=NULL) and UU.user_id=? ORDER BY TB.tyre_id ASC ");
		
		SQL.append(" LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		List<TyreRemarkVO> tyreRemarkVOs = null;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement1 = connection.prepareStatement(SQL.toString());
			preparedStatement1.setInt(1, user_id);
			resultSet1 = preparedStatement1.executeQuery();
			tyreRemarkVOs=ResultSetUtil.getByList(resultSet1, "tyre_id,tyre_where,trucks_id,remark".split(","), 
					"tyre_id,tyre_where,trucks_id,remark".split(","), TyreRemarkVO.class, false);
		} catch (Exception e) {
			logger.error("胎的小贴士列表："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet1 != null && !resultSet1.isClosed()) {
					resultSet1.close();
				}
				if (preparedStatement1 != null && !preparedStatement1.isClosed()) {
					preparedStatement1.close();
					preparedStatement1=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		SQL = null;
		return tyreRemarkVOs;
	}
	
	@Override
	public int tyreByDriverXiuBu(String tyre_id,Integer user_id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("SELECT TB.tyre_id,TB.tyre_flag,UU.true_name FROM tyre_base TB LEFT JOIN `user` U ON U.user_id=TB.user_id LEFT JOIN company C ON C.company_id=U.user_company_id "+
"LEFT JOIN `user` UU ON UU.user_company_id=C.company_id WHERE TB.tyre_id=? AND UU.user_id=? LIMIT 1");
			preparedStatement.setString(1, tyre_id);
			preparedStatement.setInt(2, user_id);
			resultSet=preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return 1;//轮胎不存在
			}			
			preparedStatement1 = connection.prepareStatement("insert into tyre_history(tyre_time,tyre_id,tyre_action,tyre_content,tyre_person,user_id) values(?,?,?,?,?,?)");
			preparedStatement1.setObject(1, new Date());
			preparedStatement1.setString(2, tyre_id);
			preparedStatement1.setString(3, TyreHistory.ACTION_TYPE_REPAIR);
			preparedStatement1.setString(4, "自行修补:"+resultSet.getString("true_name"));
			preparedStatement1.setString(5, resultSet.getString("true_name"));
			preparedStatement1.setInt(6, user_id);
			int a=preparedStatement1.executeUpdate();
			if(a<=0){
				connection.rollback();
				return 2;//保存胎失败
			}
			connection.commit();
			logger.info("自行修补保存成功,胎:"+tyre_id);
			return 0;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("自行修补,胎:"+tyre_id+StringHelper.getTrace(e));
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
	
	public int saveByList(List<TyreBase> tyreBases,Trucks trucks,Integer user_id,Connection connection) {
		int flag = 0;
		String sql ="insert into tyre_base(create_time,tyre_id,tyre_brand,tyre_flag,tyre_type1,tyre_type2,tyre_type3,tyre_type4,tyre_type5,tyre_type6,tyre_type7,tyre_type8,tyre_type9,tyre_where,tyre_depth,user_id) values";
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		ResultSet resultSet = null;
		try {
			StringBuffer newsql = new StringBuffer();
			StringBuffer newsql2 = new StringBuffer();
			StringBuffer newsql3 = new StringBuffer();
			newsql.append(sql);
			newsql2.append("insert into tyre_history(tyre_time,tyre_id,tyre_action,tyre_content,tyre_person,user_id) values");
			newsql3.append("insert into tyre_dynamic(tyre_id,mabiao_install,li_cheng_run) values");
			
			preparedStatement2 = connection.prepareStatement("SELECT true_name FROM `user` WHERE user_id=?");
			preparedStatement2.setInt(1, user_id);
			resultSet = preparedStatement2.executeQuery();
			String true_name="";
			if (resultSet.next()) {
				true_name=resultSet.getString(1);
			}else{
				return 1;
			}
			int result=-1;
			List<Object> paramterList=new ArrayList<Object>();
			List<Object> paramterList2=new ArrayList<Object>();
			List<Object> paramterList3=new ArrayList<Object>();
			TyreBase tyreBase;
			Date now=new Date();
			for(int i=0;i<tyreBases.size();i++){
				tyreBase=tyreBases.get(i);
				paramterList.addAll(Arrays.asList(new Object[]{now,tyreBase.getTyre_id(),tyreBase.getTyre_brand(),tyreBase.getTyre_flag(),
						tyreBase.getTyre_type1(),tyreBase.getTyre_type2(),tyreBase.getTyre_type3(),tyreBase.getTyre_type4(),tyreBase.getTyre_type5(),tyreBase.getTyre_type6(),
						tyreBase.getTyre_type7(),tyreBase.getTyre_type8(),tyreBase.getTyre_type9(),tyreBase.getTyre_where(),tyreBase.getTyre_depth(),tyreBase.getUser_id()}));
				paramterList2.addAll(Arrays.asList(new Object[]{now,tyreBase.getTyre_id(),TyreHistory.ACTION_TYPE_INPUT,"入库",true_name,user_id}));
				paramterList3.addAll(Arrays.asList(new Object[]{tyreBase.getTyre_id(),trucks.getMabiao_ruku(),trucks.getLi_cheng_run()}));
				if(i==0){
					newsql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					newsql2.append("(?,?,?,?,?,?)");
					newsql3.append("(?,?,?)");
				}else{
					newsql.append(",(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					newsql2.append(",(?,?,?,?,?,?)");
					newsql3.append(",(?,?,?)");
				}
			}
			preparedStatement = connection.prepareStatement(newsql.toString());
			for(int i=0;i<paramterList.size();i++){
				preparedStatement.setObject(i+1, paramterList.get(i));
			}
			preparedStatement1 = connection.prepareStatement(newsql2.toString());
			for(int i=0;i<paramterList2.size();i++){
				preparedStatement1.setObject(i+1, paramterList2.get(i));
			}
			preparedStatement3 = connection.prepareStatement(newsql3.toString());
			for(int i=0;i<paramterList3.size();i++){
				preparedStatement3.setObject(i+1, paramterList3.get(i));
			}
			result= preparedStatement.executeUpdate();
			int result2=preparedStatement1.executeUpdate();
			int result3=preparedStatement3.executeUpdate();
			
			
			if(result>0&&result2>0&&result3>0){
				flag = 2;
			}else{
				try {
					connection.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
			logger.error("批量保存轮胎基本信息:"+StringHelper.getTrace(e));
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
				if(preparedStatement2!=null && !preparedStatement2.isClosed()){
					preparedStatement2.close();
				}
				if(preparedStatement3!=null && !preparedStatement3.isClosed()){
					preparedStatement3.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
		}
		return flag;
	}
	

	
}
