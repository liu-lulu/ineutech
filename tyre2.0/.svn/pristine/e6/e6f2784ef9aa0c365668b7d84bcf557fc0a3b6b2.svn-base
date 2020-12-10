package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.psylife.dao.DrivingRecordDao;
import com.psylife.dao.TrucksDao;
import com.psylife.dao.TrucksDeviceDao;
import com.psylife.entity.DrivingRecord;
import com.psylife.hardware.HardwareElement;
import com.psylife.hardware.send.SendManager;
import com.psylife.util.ConnectionPool;
import com.psylife.util.Constants;
import com.psylife.util.ResultSetUtil;
import com.psylife.util.StringHelper;
import com.psylife.vo.DrivingRecordVO;
import com.psylife.vo.UserDrivingRecordCountVO;


public class DrivingRecordDaoImpl extends BaseDaoImpl implements DrivingRecordDao{
	
	private TrucksDeviceDao trucksDeviceDao=(TrucksDeviceDao)new TrucksDeviceDaoImpl();
	
	private TrucksDao trucksDao=new TrucksDaoImpl();
	
	@Override
	public DrivingRecord getLastDrivingRecord(Integer user_id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,`status`,li_cheng_run,li_cheng_end,guache_trucks_id") 
           .append(" FROM driving_record WHERE driver_id=? ORDER BY id DESC LIMIT 1");
		DrivingRecord drivingRecord = null;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			resultSet = preparedStatement.executeQuery();
			drivingRecord=ResultSetUtil.getByOne(resultSet, "id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,status,li_cheng_run,li_cheng_end,guache_trucks_id".split(","), 
					"id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,status,li_cheng_run,li_cheng_end,guache_trucks_id".split(","), DrivingRecord.class, false);
		} catch (Exception e) {
			logger.error("获取驾驶员最近驾驶记录："+StringHelper.getTrace(e));
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
		return drivingRecord;
	}
	
	@Override
	public DrivingRecordVO getLastDrivingRecordByTrucks_id(String trucks_id,boolean isMain) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT DR.id,DR.trucks_id,DR.from_adress,DR.to_adress,DR.create_time,DR.start_time,DR.end_time,DR.driver_id,DR.transport_type,DR.star_unobstructed,DR.star_heart,DR.`status`,DR.li_cheng_run,")
.append(" DR.li_cheng_end,DR.guache_trucks_id,U.true_name,U.user_phone,U.user_id")
.append(" FROM driving_record DR LEFT JOIN trucks T ON T.last_driving_record_id=DR.id LEFT JOIN `user` U ON U.user_id=DR.driver_id WHERE ");
		if(isMain){
			SQL.append("T.trucks_id=?");
		}else{
			SQL.append("T.guache_trucks_id=?");
		}
		SQL.append(" LIMIT 1 ");
		DrivingRecordVO drivingRecordVO = null;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setString(1, trucks_id);
			resultSet = preparedStatement.executeQuery();
			drivingRecordVO=ResultSetUtil.getByOne(resultSet, "id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,status,li_cheng_run,li_cheng_end,guache_trucks_id,true_name,user_phone,user_id".split(","), 
					"id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,status,li_cheng_run,li_cheng_end,guache_trucks_id,true_name,user_phone,user_id".split(","), DrivingRecordVO.class, false);
		} catch (Exception e) {
			logger.error("获取车最近一条驾驶记录："+StringHelper.getTrace(e));
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
		return drivingRecordVO;
	}
	
	@Override
	public List<DrivingRecord> getDrivingRecordList(int pagenum,Integer user_id){
		List<DrivingRecord> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,`status`,li_cheng_run,li_cheng_end,guache_trucks_id") 
        .append(" FROM driving_record WHERE driver_id=? ");
		List<Object> paramater=new ArrayList<Object>();
		paramater.add(user_id);
		SQL.append(" ORDER BY id DESC LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			for(int i=0;i<paramater.size();i++){
				preparedStatement.setObject(i+1, paramater.get(i));
			}
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,status,li_cheng_run,li_cheng_end,guache_trucks_id".split(","), 
					"id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,status,li_cheng_run,li_cheng_end,guache_trucks_id".split(","), DrivingRecord.class, false);
			logger.info("根据用户获取架驶记录列表成功！");
		} catch (Exception e) {
			logger.error("根据用户获取架驶记录列表失败！："+StringHelper.getTrace(e));
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
	public int startDriving(DrivingRecord drivingRecord){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet1 = null;
		ResultSet resultSet2 = null;
		ResultSet resultSet3 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement4 = null;
		PreparedStatement preparedStatement5 = null;
		PreparedStatement preparedStatement6 = null;
		PreparedStatement preparedStatement7 = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement7=connection.prepareStatement("SELECT * from driving_record where status=? and driver_id=?");//检查司机是否有已开始的行程
			preparedStatement7.setInt(1, DrivingRecord.STATUS_CREATE);
			preparedStatement7.setInt(2, drivingRecord.getDriver_id());
			resultSet3=preparedStatement7.executeQuery();
			if (resultSet3.next()) {
				return 8;
			}
			
			preparedStatement = connection.prepareStatement("SELECT T.trucks_id,T.dtu_multi_flag,T.mabiao,T.guache_trucks_id,T.transport_type,T.trucks_style,DR.`status` as DR_status,T.guache_save_flag,T.trucks_type FROM trucks T LEFT JOIN driving_record DR ON DR.id=T.last_driving_record_id WHERE T.trucks_id=? LIMIT 1");
			preparedStatement.setString(1, drivingRecord.getTrucks_id());
			resultSet = preparedStatement.executeQuery();
			int a;
			boolean flag=false;
			if (resultSet.next()&&"主车".equals(resultSet.getString("trucks_type"))) {//检查是否为主车不然表示不能驾驶
				if(DrivingRecord.STATUS_CREATE==resultSet.getInt("DR_status")){
					return 3;//还正在开始，不能创建
				}
				if(resultSet.getDouble("mabiao")>drivingRecord.getLi_cheng_run().doubleValue()){
				    return 2;//码表数不能低于原本的码表数	
				}
//				if(!drivingRecord.getTransport_type().equals(resultSet.getString("transport_type"))){
//					return 4;//运输类型不一致
//				}
				a=trucksDao.updateTrucksMabiao(drivingRecord.getTrucks_id(), drivingRecord.getLi_cheng_run(), connection);//更新车码表数
				if(a!=0&&a!=4){//码表数没有增加也可以开启
					connection.rollback();
					return 7;//保存失败
				}
				if(resultSet.getString("trucks_style").indexOf("+")>0){//车型为挂车
		            if(drivingRecord.getGuache_trucks_id()!=null&&!"".equals(drivingRecord.getGuache_trucks_id())&&!drivingRecord.getGuache_trucks_id().equals(resultSet.getString("guache_trucks_id"))){//挂车不在当前主车上
		            	preparedStatement1 = connection.prepareStatement("SELECT GT.trucks_style,GT.trucks_type,GT.trucks_id as guache_trucks_id,GT.dtu_multi_flag,T.trucks_id,DR.`status` as DR_status,"
		            			+ "GT.trucks_C1,GT.trucks_C2,GT.trucks_C3,GT.trucks_C4,GT.trucks_C5,GT.trucks_C6,GT.trucks_C7,GT.trucks_C8,GT.trucks_C9,GT.trucks_C10,GT.trucks_C11,GT.trucks_C12,GT.trucks_C13,GT.trucks_C14,GT.trucks_C15,GT.trucks_C16"
		            			+ " FROM trucks GT LEFT JOIN trucks T ON T.guache_trucks_id=GT.trucks_id LEFT JOIN driving_record DR ON DR.id=T.last_driving_record_id WHERE GT.trucks_id=? LIMIT 1");
		            	preparedStatement1.setString(1, drivingRecord.getGuache_trucks_id());
		            	resultSet1 = preparedStatement1.executeQuery();
		            	if(resultSet1.next()==false||resultSet1.getString("guache_trucks_id")==null||resultSet1.getString("trucks_style").indexOf("+")<0||resultSet1.getString("trucks_type").indexOf("挂车")<0){//挂车不存在
		            		return 5;//挂车不存在
		            	}
		            	if(resultSet1.getString("trucks_id")!=null){//挂车在主车上
		            		if(DrivingRecord.STATUS_CREATE==resultSet1.getInt("DR_status")){//挂车还在正在驾驶的车上
		            			return 6;//挂车还在正在驾驶的其他车上
		            		}
		            		preparedStatement2 = connection.prepareStatement("update trucks set trucks_style=left(trucks_style,4),guache_trucks_id=null,dtu_multi_flag=0 where guache_trucks_id=?");
		            		preparedStatement2.setString(1,drivingRecord.getGuache_trucks_id());
		            		preparedStatement2.executeUpdate();	
		            		
		            		//更新主车健康度
		            		trucksDao.updateTrucksHealthWhenDriving(resultSet1.getString("trucks_id"), true, null, connection);
		            	}
		            	if(resultSet1.getInt("dtu_multi_flag")!=1){//挂车和主车共用一个dtu
		            		preparedStatement3 = connection.prepareStatement("update trucks set trucks_C1=?,trucks_C2=?,trucks_C3=?,trucks_C4=?,trucks_C5=?,trucks_C6=?,trucks_C7=?,trucks_C8=?,trucks_C9=?,trucks_C10=?,trucks_C11=?,trucks_C12=?,trucks_C13=?,trucks_C14=?,trucks_C15=?,trucks_C16=? where trucks_id=?");
		            		preparedStatement3.setString(1,resultSet1.getString("trucks_C1"));
		            		preparedStatement3.setString(2,resultSet1.getString("trucks_C2"));
		            		preparedStatement3.setString(3,resultSet1.getString("trucks_C3"));
		            		preparedStatement3.setString(4,resultSet1.getString("trucks_C4"));
		            		preparedStatement3.setString(5,resultSet1.getString("trucks_C5"));
		            		preparedStatement3.setString(6,resultSet1.getString("trucks_C6"));
		            		preparedStatement3.setString(7,resultSet1.getString("trucks_C7"));
		            		preparedStatement3.setString(8,resultSet1.getString("trucks_C8"));
		            		preparedStatement3.setString(9,resultSet1.getString("trucks_C9"));
		            		preparedStatement3.setString(10,resultSet1.getString("trucks_C10"));
		            		preparedStatement3.setString(11,resultSet1.getString("trucks_C11"));
		            		preparedStatement3.setString(12,resultSet1.getString("trucks_C12"));
		            		preparedStatement3.setString(13,resultSet1.getString("trucks_C13"));
		            		preparedStatement3.setString(14,resultSet1.getString("trucks_C14"));
		            		preparedStatement3.setString(15,resultSet1.getString("trucks_C15"));
		            		preparedStatement3.setString(16,resultSet1.getString("trucks_C16"));
		            		preparedStatement3.setString(17,drivingRecord.getTrucks_id());
		            		a=preparedStatement3.executeUpdate();	
		            		if(a>0){
		            			flag=true;
		            		}		            		
		            	}
		            	String trucks_style=resultSet.getString("trucks_style").substring(0, resultSet.getString("trucks_style").indexOf("+"))+resultSet1.getString("trucks_style").substring(resultSet1.getString("trucks_style").indexOf("+"));
		            	preparedStatement4 = connection.prepareStatement("update trucks set guache_trucks_id=?,trucks_style=?,dtu_multi_flag=? where trucks_id=?");//挂在主车上
		            	preparedStatement4.setString(1,drivingRecord.getGuache_trucks_id());
		            	preparedStatement4.setString(2,trucks_style);
		            	preparedStatement4.setInt(3,resultSet1.getInt("dtu_multi_flag"));
		            	preparedStatement4.setString(4,drivingRecord.getTrucks_id());
		            	preparedStatement4.executeUpdate();
		            	//更新主车健康度
		            	trucksDao.updateTrucksHealthWhenDriving(drivingRecord.getTrucks_id(), false, drivingRecord.getGuache_trucks_id(), connection);
		            	
		            }else if((drivingRecord.getGuache_trucks_id()==null||"".equals(drivingRecord.getGuache_trucks_id()))&&resultSet.getString("guache_trucks_id")!=null){//只有一个车头
		            	if(resultSet.getInt("dtu_multi_flag")!=1&&resultSet.getInt("guache_save_flag")==1){//挂车和主车共用一个dtu
		            		preparedStatement3 = connection.prepareStatement("update trucks set trucks_C1=?,trucks_C2=?,trucks_C3=?,trucks_C4=?,trucks_C5=?,trucks_C6=?,trucks_C7=?,trucks_C8=?,trucks_C9=?,trucks_C10=?,trucks_C11=?,trucks_C12=?,trucks_C13=?,trucks_C14=?,trucks_C15=?,trucks_C16=? where trucks_id=?");
		            		preparedStatement3.setString(1,null);
		            		preparedStatement3.setString(2,null);
		            		preparedStatement3.setString(3,null);
		            		preparedStatement3.setString(4,null);
		            		preparedStatement3.setString(5,null);
		            		preparedStatement3.setString(6,null);
		            		preparedStatement3.setString(7,null);
		            		preparedStatement3.setString(8,null);
		            		preparedStatement3.setString(9,null);
		            		preparedStatement3.setString(10,null);
		            		preparedStatement3.setString(11,null);
		            		preparedStatement3.setString(12,null);
		            		preparedStatement3.setString(13,null);
		            		preparedStatement3.setString(14,null);
		            		preparedStatement3.setString(15,null);
		            		preparedStatement3.setString(16,null);
		            		preparedStatement3.setString(17,drivingRecord.getTrucks_id());
		            		a=preparedStatement3.executeUpdate();	
		            		if(a>0){
		            			flag=true;
		            		}		            		
		            	}
		            	preparedStatement4 = connection.prepareStatement("update trucks set trucks_style=left(trucks_style,4),guache_trucks_id=null,dtu_multi_flag=0 where trucks_id=?");//挂在主车上
		            	preparedStatement4.setString(1,drivingRecord.getTrucks_id());
		            	preparedStatement4.executeUpdate();
		            	//更新主车健康度
		            	trucksDao.updateTrucksHealthWhenDriving(drivingRecord.getTrucks_id(), true, null, connection);
		            }
				}else if(drivingRecord.getGuache_trucks_id()!=null&&!"".equals(drivingRecord.getGuache_trucks_id())){//此主车不能挂车，不合法的挂车,
					connection.rollback();
					return 7;//保存失败
				}				
			}else{
				return 1;//车不存在
			}
			preparedStatement5 = connection.prepareStatement("insert into driving_record(trucks_id,from_adress,to_adress,create_time,start_time,driver_id,transport_type,`status`,li_cheng_run,guache_trucks_id) values(?,?,?,?,?,?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement5.setString(1,drivingRecord.getTrucks_id());
			preparedStatement5.setString(2,drivingRecord.getFrom_adress());
			preparedStatement5.setString(3,drivingRecord.getTo_adress());
			preparedStatement5.setObject(4,new Date());
			preparedStatement5.setObject(5,drivingRecord.getStart_time());
			preparedStatement5.setInt(6,drivingRecord.getDriver_id());
			preparedStatement5.setString(7,drivingRecord.getTransport_type());
			preparedStatement5.setInt(8,DrivingRecord.STATUS_CREATE);
			preparedStatement5.setDouble(9,drivingRecord.getLi_cheng_run());
			preparedStatement5.setString(10,drivingRecord.getGuache_trucks_id());
			a=preparedStatement5.executeUpdate();	
			if(a<=0){
				connection.rollback();
				return 7;//保存失败
			}
			resultSet2 = preparedStatement5.getGeneratedKeys();  
            if(resultSet2.next()) {
            	int id = resultSet2.getInt(1); 
            	preparedStatement6 = connection.prepareStatement("update trucks set last_driving_record_id=? where trucks_id=?");//挂在主车上
            	preparedStatement6.setInt(1,id);
            	preparedStatement6.setString(2,drivingRecord.getTrucks_id());
            	preparedStatement6.executeUpdate();		
            }
			if(flag){
				HardwareElement element=SendManager.getInstance().getHardwareElementByCarNum(drivingRecord.getTrucks_id());
				if(element!=null){
					trucksDeviceDao.reloadTrucksDevice(element);
				}
			}
			connection.commit();
			logger.info("架驶员开始行程:"+drivingRecord.getTrucks_id());
			return 0;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("架驶员开始行程:"+drivingRecord.getTrucks_id()+StringHelper.getTrace(e));
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
				if(preparedStatement5!=null && !preparedStatement5.isClosed()){
					preparedStatement5.close();
				}
				if(preparedStatement6!=null && !preparedStatement6.isClosed()){
					preparedStatement6.close();
				}
				if(preparedStatement7!=null && !preparedStatement7.isClosed()){
					preparedStatement7.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
		}
		return 7;
	}
	
	@Override
	public int endDriving(DrivingRecord drivingRecord){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,`status`,li_cheng_run,li_cheng_end,guache_trucks_id") 
           .append(" FROM driving_record WHERE driver_id=? ORDER BY id DESC LIMIT 1");
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, drivingRecord.getDriver_id());
			resultSet = preparedStatement.executeQuery();
			DrivingRecord drivingRecord2=ResultSetUtil.getByOne(resultSet, "id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,status,li_cheng_run,li_cheng_end,guache_trucks_id".split(","), 
					"id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,status,li_cheng_run,li_cheng_end,guache_trucks_id".split(","), DrivingRecord.class, false);
			if(drivingRecord2!=null){
				if(drivingRecord2.getStatus()==DrivingRecord.STATUS_CREATE){
					if(drivingRecord.getLi_cheng_end().doubleValue()>drivingRecord2.getLi_cheng_run().doubleValue()){
						int status=DrivingRecord.STATUS_END;
						if(drivingRecord2.getStart_time().getTime()>System.currentTimeMillis()){
							status=DrivingRecord.STATUS_STOP;
						}
						preparedStatement1 = connection.prepareStatement
						("update driving_record INNER JOIN `user` ON `user`.user_id=driving_record.driver_id set driving_record.`status`=?,driving_record.li_cheng_end=?,driving_record.end_time=?,`user`.li_cheng=`user`.li_cheng+? where driving_record.id=?");//挂在主车上
						preparedStatement1.setInt(1,status);
						preparedStatement1.setDouble(2,drivingRecord.getLi_cheng_end());
						preparedStatement1.setObject(3,new Date());
						preparedStatement1.setDouble(4,drivingRecord.getLi_cheng_end().doubleValue()-drivingRecord2.getLi_cheng_run().doubleValue());
						preparedStatement1.setInt(5,drivingRecord2.getId());
						int i=preparedStatement1.executeUpdate();		
						if(i>0){
							int a=trucksDao.updateTrucksMabiao(drivingRecord2.getTrucks_id(), drivingRecord.getLi_cheng_end(), connection);//更新车码表数
							if(a!=0){
								connection.rollback();
								return 3;//保存失败
							}else{
								connection.commit();
							}
						}else{
							connection.rollback();
							return 3;
						}
					}else{//结束码表数小于开始码表数
						return 2;
					}
					
				}else{
					return 1;//没有要开始的
				}
			}else{
				return 1;//没有要结束的
			}
			logger.info("架驶员结束行程:"+drivingRecord.getDriver_id());
			return 0;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("架驶员结束行程："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
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
		return 3;
	}
	
	@Override
	public int scoreDriving(DrivingRecord drivingRecord){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,`status`,li_cheng_run,li_cheng_end,guache_trucks_id") 
           .append(" FROM driving_record WHERE driver_id=? ORDER BY id DESC LIMIT 1");
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, drivingRecord.getDriver_id());
			resultSet = preparedStatement.executeQuery();
			DrivingRecord drivingRecord2=ResultSetUtil.getByOne(resultSet, "id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,status,li_cheng_run,li_cheng_end,guache_trucks_id".split(","), 
					"id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,status,li_cheng_run,li_cheng_end,guache_trucks_id".split(","), DrivingRecord.class, false);
			if(drivingRecord2!=null){
				if(drivingRecord2.getStatus()!=DrivingRecord.STATUS_CREATE){
					if(drivingRecord2.getStar_heart()==null||drivingRecord2.getStar_heart()<=0){
						preparedStatement1 = connection.prepareStatement("update driving_record set star_unobstructed=?,star_heart=? where id=?");//挂在主车上
						preparedStatement1.setInt(1,drivingRecord.getStar_unobstructed());
						preparedStatement1.setInt(2,drivingRecord.getStar_heart());
						preparedStatement1.setInt(3,drivingRecord2.getId());
						int i=preparedStatement1.executeUpdate();		
						if(i>0){
							connection.commit();
						}else{
							connection.rollback();
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
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("架驶员评分行程："+StringHelper.getTrace(e));
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
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
		return 4;
	}
	
	@Override
	public UserDrivingRecordCountVO getDrivingRecordCount(Integer user_id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
//		SQL.append("SELECT U.li_cheng,COUNT(DR.id) as count,U.user_id FROM `user` U LEFT JOIN driving_record DR ON (DR.`status`!=1 AND DR.driver_id=U.user_id) WHERE U.user_id=? GROUP BY U.user_id ");
		SQL.append("SELECT * FROM (SELECT U.li_cheng,COUNT(DR.id) as count,U.user_id FROM `user` U LEFT JOIN driving_record DR ON (DR.`status`!=1 AND DR.driver_id=U.user_id) WHERE U.user_id=? GROUP BY U.user_id) as T1 ")
           .append("LEFT JOIN (SELECT P.phone as service_phone,U.user_id FROM `user` U LEFT JOIN team T ON T.c_id=U.user_company_id LEFT JOIN person P ON T.p_id=P.id WHERE P.job='客服' AND U.user_id=? LIMIT 1) as T2 ")
		   .append("on T1.user_id=T2.user_id");
		UserDrivingRecordCountVO  userDrivingRecordCountVO= null;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			preparedStatement.setInt(2, user_id);
			resultSet = preparedStatement.executeQuery();
			userDrivingRecordCountVO=ResultSetUtil.getByOne(resultSet, "li_cheng,count,service_phone".split(","), 
					"li_cheng,count,service_phone".split(","), UserDrivingRecordCountVO.class, false);
		} catch (Exception e) {
			logger.error("获取驾驶员驾驶记录汇总："+StringHelper.getTrace(e));
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
		return userDrivingRecordCountVO;
	}

	@Override
	public List<DrivingRecordVO> getDrivingRecordListByTrucksId(int pagenum, String trucksId) {
		List<DrivingRecordVO> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT DR.id,DR.trucks_id,DR.from_adress,DR.to_adress,DR.create_time,DR.start_time,DR.end_time,DR.driver_id,DR.transport_type,DR.star_unobstructed,DR.star_heart,DR.`status`,DR.li_cheng_run,")
		.append(" DR.li_cheng_end,DR.guache_trucks_id,U.true_name,U.user_phone,U.user_id")
		.append(" FROM driving_record DR LEFT JOIN `user` U ON U.user_id=DR.driver_id WHERE DR.trucks_id=? or DR.guache_trucks_id=? ");
		List<Object> paramater=new ArrayList<Object>();
		paramater.add(trucksId);
		paramater.add(trucksId);
		SQL.append(" ORDER BY id DESC LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			for(int i=0;i<paramater.size();i++){
				preparedStatement.setObject(i+1, paramater.get(i));
			}
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,status,li_cheng_run,li_cheng_end,guache_trucks_id,true_name,user_phone,user_id".split(","), 
					"id,trucks_id,from_adress,to_adress,create_time,start_time,end_time,driver_id,transport_type,star_unobstructed,star_heart,status,li_cheng_run,li_cheng_end,guache_trucks_id,true_name,user_phone,user_id".split(","), DrivingRecordVO.class, false);
			logger.info("根据车牌号获取架驶记录列表成功！");
		} catch (Exception e) {
			logger.error("根据车牌号获取架驶记录列表失败！："+StringHelper.getTrace(e));
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
	
	
	
}
