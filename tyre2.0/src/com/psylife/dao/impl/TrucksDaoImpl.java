package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.psylife.dao.TrucksDao;
import com.psylife.dao.TrucksDeviceDao;
import com.psylife.dao.TyreBaseDao;
import com.psylife.entity.DeviceFasheqi;
import com.psylife.entity.Trucks;
import com.psylife.entity.TyreBase;
import com.psylife.hardware.HardwareElement;
import com.psylife.hardware.UDPServerManager;
import com.psylife.hardware.process.HeartProcess;
import com.psylife.util.ConnectionPool;
import com.psylife.util.Constants;
import com.psylife.util.ResultSetUtil;
import com.psylife.util.StringHelper;
import com.psylife.util.TrucksStyleUtil;
import com.psylife.util.page.ListInfo;
import com.psylife.util.push.Message;
import com.psylife.vo.TrucksByAdminVO;
import com.psylife.vo.TrucksVO;
import com.psylife.vo.TyreVO;
import com.psylife.vo.WarnMsgVO;
import com.psylife.vo.web.TrucksWatchVO;
 
public class TrucksDaoImpl implements TrucksDao{
	
	private TrucksDeviceDao trucksDeviceDao=(TrucksDeviceDao)new TrucksDeviceDaoImpl();
	//private TyreBaseDao tyreBaseDao = (TyreBaseDao)new TyreBaseDaoImpl(); 

	@Override
	public List<TrucksVO> searchByList(int pagenum,Integer user_id,Integer trucks_flag,Integer trucks_health,String trucks_type,String keyWord,String transport_type,String column,String order){
		List<TrucksVO> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT * FROM (");
		SQL.append("SELECT DISTINCT T.id,T.trucks_id,T.trucks_brand,T.trucks_style,case when TIMESTAMPDIFF(SECOND, caiji_time, CURRENT_TIMESTAMP())*1000<"+HeartProcess.INTERVAL_TIMEOUT_TCP+" then T.trucks_flag else 0 END as trucks_flag,T.trucks_health,T.trucks_type,T.transport_type,T.guache_save_flag,T.guache_trucks_id,T.li_cheng_run,T.trucks_mode,TD.caiji_time ")
		.append(" FROM trucks T LEFT JOIN device TD ON TD.trucks_id=T.trucks_id LEFT JOIN `user` U ON U.user_company_id=T.company_id WHERE U.user_id=? ");
		SQL.append(")T where 1=1");
		List<Object> paramater=new ArrayList<Object>();
		paramater.add(user_id);
		if(trucks_flag!=null){//状态0停,1行
			SQL.append(" and T.trucks_flag=?");
			paramater.add(trucks_flag);
		}
		
		if(trucks_health!=null){
			if(trucks_health.intValue()==1){
				SQL.append(" and T.trucks_health>?");
			}else{
				SQL.append(" and T.trucks_health<=?");
			}
			paramater.add(30);
		}
		if(trucks_type!=null&&!"".equals(trucks_type)){
			SQL.append(" and T.trucks_type=? ");
			paramater.add(trucks_type);
		}
		
		if(transport_type!=null&&!"".equals(transport_type)){
			SQL.append(" and T.transport_type=? ");
			paramater.add(transport_type);
		}
		
		if(keyWord!=null&&!"".equals(keyWord)){
			SQL.append(" and T.trucks_id like ? ");
			paramater.add("%"+keyWord+"%");
		}
		String orderSQL="";
		if (StringUtils.isNotEmpty(column)) {
			
			orderSQL+=" order by ";
			
			if ("1".equals(column)) {
				//车牌
				orderSQL+=" T.trucks_id ";
			}else if ("2".equals(column)) {
				//厂牌
				orderSQL+=" T.trucks_brand ";
			}else if ("3".equals(column)) {
				//车型
				orderSQL+=" T.trucks_style ";
			}else if ("4".equals(column)) {
				//健康
				orderSQL+=" T.trucks_health ";
			}else if ("5".equals(column)) {
				//状态
				orderSQL+=" T.trucks_flag ";
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
			
			if (!"1".equals(column)) {
				orderSQL+=",T.trucks_id ASC";
			}
			
		}
		SQL.append(orderSQL);
		
		SQL.append(" LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			for(int i=0;i<paramater.size();i++){
				preparedStatement.setObject(i+1, paramater.get(i));
			}
			
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "id,trucks_id,trucks_brand,trucks_style,trucks_flag,trucks_health,trucks_type,transport_type,guache_save_flag,guache_trucks_id,li_cheng_run,trucks_mode,caiji_time".split(","),
					"id,trucks_id,trucks_brand,trucks_style,trucks_flag,trucks_health,trucks_type,transport_type,guache_save_flag,guache_trucks_id,li_cheng_run,trucks_mode,caiji_time".split(","), TrucksVO.class, false);
			logger.info("根据用户获取车辆列表成功！");
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("根据用户获取车辆列表失败！："+StringHelper.getTrace(e));
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
	public TrucksByAdminVO getByTrucks_id(String trucks_id){
		logger.info("车牌号："+trucks_id);
		TrucksByAdminVO vo=null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getConnection();
			vo=getByTrucks_id(trucks_id, connection);
			if(vo!=null&&"主车".equals(vo.getTrucks_type())&&1==vo.getGuache_save_flag()&&vo.getGuache_trucks_id()!=null&&!"".equals(vo.getGuache_trucks_id())){//主车 可以选择挂其他挂车的
				TrucksByAdminVO vo2=getByTrucks_id(vo.getGuache_trucks_id(),connection);
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
				vo.setTrucks_Y1(vo2.getTrucks_Y1());
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
				String insql=tyreInArrByTrucks(vo);
				if(!"".equals(insql)){
					preparedStatement = connection.prepareStatement("SELECT TD.tyre_id,TD.tyre_health,TP.tyre_paver FROM tyre_dynamic TD LEFT JOIN tyre_pattern TP ON TP.tyre_id=TD.tyre_id WHERE TD.tyre_id in("+insql+")");
					resultSet = preparedStatement.executeQuery();
					Map<String, Float> mapHealth=new HashMap<String, Float>();
					Map<String, Float> mapPattern=new HashMap<String, Float>();
					while(resultSet.next()){
						mapHealth.put(resultSet.getString("tyre_id"), resultSet.getFloat("tyre_health"));
						mapPattern.put(resultSet.getString("tyre_id"), resultSet.getFloat("tyre_paver"));
					}
					vo.setMapHealth(mapHealth);
					vo.setMapPattern(mapPattern);
				}
			}
			logger.info("根据车牌号获取车信息成功！"+trucks_id);
		} catch (Exception e) {
			logger.error("根据车牌号获取车信息失败！："+StringHelper.getTrace(e));
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
		return vo;
	}
	
	/**
	 * 根据车牌查询
	 * @param trucks_id
	 * @param connection
	 * @return
	 */
	private TrucksByAdminVO getByTrucks_id(String trucks_id,Connection connection){
		TrucksByAdminVO vo=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet1 = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT DISTINCT T.id,T.trucks_id,T.trucks_brand,T.trucks_style,T.trucks_flag,T.trucks_health,TD.dtu_id,T.li_cheng,TD.warn,TD.caiji_time,T.li_cheng_run,T.mabiao,T.transport_type,T.guache_trucks_id,T.trucks_mode,T.guache_save_flag,TD.dimian_sulu,T.trucks_type,TD.gps_status,TD.latitude,TD.latitude_type,TD.longitude,TD.longitude_type")
		.append(",T.trucks_X1,T.trucks_A1,T.trucks_A2,T.trucks_A3,T.trucks_A4,T.trucks_A5,T.trucks_A6")
		.append(",T.trucks_B1,T.trucks_B2,T.trucks_B3,T.trucks_B4,T.trucks_B5,T.trucks_B6,T.trucks_B7,T.trucks_B8")
		.append(",T.trucks_C1,T.trucks_C2,T.trucks_C3,T.trucks_C4,T.trucks_C5,T.trucks_C6,T.trucks_C7,T.trucks_C8,T.trucks_C9,T.trucks_C10,T.trucks_C11,T.trucks_C12,T.trucks_C13,T.trucks_C14,T.trucks_C15,T.trucks_C16,T.trucks_Y1")
		.append(",U.user_phone,U.true_name,DR.from_adress,DR.to_adress,U.user_id ")
		.append(" FROM trucks T LEFT JOIN device TD ON TD.trucks_id=T.trucks_id LEFT JOIN driving_record DR ON DR.id=T.last_driving_record_id LEFT JOIN `user` U ON U.user_id=DR.driver_id  WHERE T.trucks_id=? LIMIT 1 ");
		try {
			String trucks_ABC=",trucks_X1,trucks_A1,trucks_A2,trucks_A3,trucks_A4,trucks_A5,trucks_A6,trucks_B1,trucks_B2,trucks_B3,trucks_B4,trucks_B5,trucks_B6,trucks_B7,trucks_B8,trucks_C1,trucks_C2,trucks_C3,trucks_C4,trucks_C5,trucks_C6,trucks_C7,trucks_C8,trucks_C9,trucks_C10,trucks_C11,trucks_C12,trucks_C13,trucks_C14,trucks_C15,trucks_C16,trucks_Y1";
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setString(1, trucks_id);
			resultSet = preparedStatement.executeQuery();
			vo=ResultSetUtil.getByOne(resultSet, ("id,trucks_id,trucks_brand,trucks_style,trucks_flag,trucks_health,dtu_id,li_cheng,warn,caiji_time,li_cheng_run,mabiao,transport_type,guache_trucks_id,trucks_mode,guache_save_flag,dimian_sulu,trucks_type,gps_status,latitude,latitude_type,longitude,longitude_type,user_phone,true_name,from_adress,to_adress,user_id"+trucks_ABC).split(","),
					("id,trucks_id,trucks_brand,trucks_style,trucks_flag,trucks_health,dtu_id,li_cheng,warn,caiji_time,li_cheng_run,mabiao,transport_type,guache_trucks_id,trucks_mode,guache_save_flag,dimian_sulu,trucks_type,gps_status,latitude,latitude_type,longitude,longitude_type,user_phone,true_name,from_adress,to_adress,user_id"+trucks_ABC).split(","), TrucksByAdminVO.class, false);
			if (vo!=null) {
				if (vo.getDtuOnlineStatus()==0) {
					vo.setTrucks_flag(0);
				}
			}
			if(vo!=null&&vo.getDtu_id()!=null&&!"".equals(vo.getDtu_id())){
				//设备发射器
				preparedStatement1 = connection.prepareStatement(
						"SELECT id,create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,no,tyre_id,trucks_id,li_cheng,dtu_id "+
	 "FROM device_fasheqi WHERE dtu_id=?");
				preparedStatement1.setString(1, vo.getDtu_id());
				resultSet1 = preparedStatement1.executeQuery();
				List<DeviceFasheqi> deviceFasheqis=ResultSetUtil.getByList(resultSet1, "id,create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,no,tyre_id,trucks_id,li_cheng,dtu_id".split(","), 
						"id,create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,no,tyre_id,trucks_id,li_cheng,dtu_id".split(","), DeviceFasheqi.class, false);
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
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("根据车牌号获取车信息失败！："+StringHelper.getTrace(e));
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
		}
		SQL = null;
		return vo;
	}
	
	@Override
	public List<Trucks> trucksListByPattern(int company_id) {
		Connection conn = null;
		PreparedStatement pt = null;
		ResultSet rs = null;
		Trucks t = null;
		List<Trucks> result=new ArrayList<Trucks>();
		try {
			conn = ConnectionPool.getConnection();
			String sql = "SELECT * from trucks where company_id="+company_id;
			pt = conn.prepareStatement(sql);
			rs = pt.executeQuery();
			while (rs.next()) {
				t = new Trucks();
				t.setCompany(rs.getString("company"));
				t.setCompany_id(rs.getInt("company_id"));
				t.setTrucks_type(rs.getString("trucks_type"));
				
				t.setTrucks_id(rs.getString("trucks_id"));
				t.setTrucks_X1(rs.getString("trucks_X1"));
				t.setTrucks_Y1(rs.getString("trucks_Y1"));
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
				
				result.add(t);
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
			System.out.println("花纹深度测试,车列表");
		}
		return result;
	} 
	
	
	@Override
	public ListInfo<TrucksWatchVO> searchByWatchlist(Integer company_id,String keyWord,int currentPageNO,int pageSize){
		String baseSql=" FROM device D LEFT JOIN trucks T  ON D.trucks_id=T.trucks_id LEFT JOIN company C ON C.company_id=T.company_id  LEFT JOIN company CD ON CD.company_id=D.company_id ";
		String typeSql="";//类型查询
		String keyWordSql="";//关键字查询
		
		typeSql=" (CD.company_id="+company_id+" or CD.parent_id="+company_id+") ";
		if(keyWord!=null&&!"".equals(keyWord)){
			keyWordSql= " (T.trucks_id like '%"+ keyWord + "%' or D.dtu_id like '%"+keyWord+"%') ";
		}
		StringBuffer whereSql=new StringBuffer();
		if(!"".equals(typeSql)){
			if(!"".equals(whereSql.toString())){
				whereSql.append(" and ");
			}
			whereSql.append(typeSql);
		}
		if(!"".equals(keyWordSql)){
			if(!"".equals(whereSql.toString())){
				whereSql.append(" and ");
			}
			whereSql.append(keyWordSql);
		}
		if(!"".equals(whereSql.toString())){
			baseSql=baseSql+" where " + whereSql.toString();
		}
		String fields=" T.id,T.trucks_id,T.trucks_brand,T.trucks_style,T.trucks_flag,T.trucks_health,T.company_id,C.company,D.phone,D.dtu_id,D.caiji_time,D.create_time,D.gps_status,D.longitude,D.latitude,D.warn ";
		String sqlBylist="select DISTINCT "+fields+baseSql+"  order by T.id desc ";
		String sqlByCount="select count(DISTINCT D.id) "+baseSql;
		ListInfo<TrucksWatchVO> listInfo = new ListInfo<TrucksWatchVO>(currentPageNO, pageSize);
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatementCount = null;
		ResultSet resultSetCount = null;
		try {
			connection = ConnectionPool.getConnection();
			System.out.println(sqlBylist+" LIMIT "+listInfo.getFirst()+"," + pageSize);
			preparedStatement = connection.prepareStatement(sqlBylist+" LIMIT "+listInfo.getFirst()+"," + pageSize);
			resultSet = preparedStatement.executeQuery();
			
			List<TrucksWatchVO> eventList=ResultSetUtil.getByList(resultSet, "id,trucks_id,trucks_brand,trucks_style,trucks_flag,trucks_health,company_id,company,phone,dtu_id,caiji_time,create_time,gps_status,longitude,latitude,warn".split(","),
					"id,trucks_id,trucks_brand,trucks_style,trucks_flag,trucks_health,company_id,company,phone,dtu_id,caiji_time,create_time,gps_status,longitude,latitude,warn".split(","), TrucksWatchVO.class, false);
			
			listInfo.setCurrentList(eventList);
			preparedStatementCount = connection.prepareStatement(sqlByCount);
			resultSetCount = preparedStatementCount.executeQuery();
			if (resultSetCount.next()) {
				listInfo.setSizeOfTotalList(resultSetCount.getInt(1));
			}else{
				listInfo.setSizeOfTotalList(0);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
				if (resultSetCount != null && !resultSetCount.isClosed()) {
					resultSetCount.close();
				}
				if (preparedStatementCount != null && !preparedStatementCount.isClosed()) {
					preparedStatementCount.close();
					preparedStatementCount=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		return listInfo;
	}
	
	@Override
	public ListInfo<TrucksWatchVO> searchBylistBind(Integer company_id,String keyWord,int currentPageNO,int pageSize){
		String baseSql=" FROM trucks T LEFT JOIN company C ON C.company_id=T.company_id ";
		String typeSql="";//类型查询
		String keyWordSql="";//关键字查询
		
		typeSql=" (C.company_id="+company_id+" or C.parent_id="+company_id+") ";
		if(keyWord!=null&&!"".equals(keyWord)){
			keyWordSql= " (T.trucks_id like '%"+keyWord+"%') ";
		}
		StringBuffer whereSql=new StringBuffer();
		whereSql.append(" not EXISTS(SELECT 1 FROM device D WHERE D.trucks_id=T.trucks_id) ");
		if(!"".equals(typeSql)){
			if(!"".equals(whereSql.toString())){
				whereSql.append(" and ");
			}
			whereSql.append(typeSql);
		}
		if(!"".equals(keyWordSql)){
			if(!"".equals(whereSql.toString())){
				whereSql.append(" and ");
			}
			whereSql.append(keyWordSql);
		}
		if(!"".equals(whereSql.toString())){
			baseSql=baseSql+" where " + whereSql.toString();
		}
		String fields=" T.id,T.trucks_id,T.company_id,C.company ";
		String sqlBylist="select DISTINCT "+fields+baseSql+"  order by T.id desc ";
		String sqlByCount="select count(DISTINCT T.id) "+baseSql;
		ListInfo<TrucksWatchVO> listInfo = new ListInfo<TrucksWatchVO>(currentPageNO, pageSize);
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatementCount = null;
		ResultSet resultSetCount = null;
		try {
			connection = ConnectionPool.getConnection();
			System.out.println(sqlBylist+" LIMIT "+listInfo.getFirst()+"," + pageSize);
			preparedStatement = connection.prepareStatement(sqlBylist+" LIMIT "+listInfo.getFirst()+"," + pageSize);
			resultSet = preparedStatement.executeQuery();
			
			List<TrucksWatchVO> eventList=ResultSetUtil.getByList(resultSet, "id,trucks_id,company_id,company".split(","),
					"id,trucks_id,company_id,company".split(","), TrucksWatchVO.class, false);
			
			listInfo.setCurrentList(eventList);
			preparedStatementCount = connection.prepareStatement(sqlByCount);
			resultSetCount = preparedStatementCount.executeQuery();
			if (resultSetCount.next()) {
				listInfo.setSizeOfTotalList(resultSetCount.getInt(1));
			}else{
				listInfo.setSizeOfTotalList(0);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
				if (resultSetCount != null && !resultSetCount.isClosed()) {
					resultSetCount.close();
				}
				if (preparedStatementCount != null && !preparedStatementCount.isClosed()) {
					preparedStatementCount.close();
					preparedStatementCount=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		return listInfo;
	}
	
	@Override
	public boolean trucksDtuBind(String trucks_id,String dtu_id,String phone){
		if(trucks_id==null||dtu_id==null||"".equals(dtu_id)||"".equals(trucks_id)){
			return false;
		}
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement2 = null;
		ResultSet resultSet2 = null;
		PreparedStatement preparedStatement3 = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("SELECT T.trucks_id as trucks_id,D.dtu_id as dtu_id,T.company_id as company_id FROM trucks T LEFT JOIN device D ON D.trucks_id=T.trucks_id WHERE T.trucks_id=? ");
			preparedStatement.setString(1, trucks_id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				String dtu_id2=resultSet.getString("dtu_id");
				if(dtu_id2==null){
					preparedStatement2 = connection.prepareStatement("SELECT * FROM device D WHERE D.dtu_id=? ");
					preparedStatement2.setString(1, dtu_id);
					resultSet2 = preparedStatement2.executeQuery();
					if(resultSet2.next()){
						if(resultSet2.getString("trucks_id")==null){
							preparedStatement3 = connection.prepareStatement("UPDATE device SET trucks_id=?,company_id=?,phone=? WHERE dtu_id=?");
							preparedStatement3.setString(1, trucks_id);
							preparedStatement3.setInt(2, resultSet.getInt("company_id"));
							preparedStatement3.setString(3, phone);
							preparedStatement3.setString(4, dtu_id);
							int a = preparedStatement3.executeUpdate();
							if(a>0){
								connection.commit();
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
			connection.rollback();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (resultSet2 != null && !resultSet2.isClosed()) {
					resultSet2.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
				if (preparedStatement2 != null && !preparedStatement2.isClosed()) {
					preparedStatement2.close();
					preparedStatement2=null;
				}
				if (preparedStatement3 != null && !preparedStatement3.isClosed()) {
					preparedStatement3.close();
					preparedStatement3=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		logger.info("车辆绑定dtu提交失败,dtu="+dtu_id+",trucks_id="+trucks_id);
		return false;
	}
	
	@Override
	public boolean removeBind(String dtu_id){
		if(dtu_id==null||"".equals(dtu_id)){
			return false;
		}
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("UPDATE device SET trucks_id=? WHERE dtu_id=?");
			preparedStatement.setString(1, null);
			preparedStatement.setString(2, dtu_id);
			int a = preparedStatement.executeUpdate();
			if(a>0){
				connection.commit();
				logger.info("车辆解除dtu绑定提交成功,dtu="+dtu_id);
				HardwareElement element=UDPServerManager.getInstance().getChannelElement().get(dtu_id);
				if(element!=null){
					trucksDeviceDao.reloadTrucksDevice(element);
				}
				return true;
			}
			connection.rollback();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		logger.info("车辆解除dtu绑定提交失败,dtu="+dtu_id);
		return false;
	}
	
	private String tyreInArrByTrucks(TrucksByAdminVO vo){
		StringBuffer result=new StringBuffer();
		boolean flag=false;
		if(vo.getTrucks_X1()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_X1());
			result.append("'");
		}
		if(vo.getTrucks_Y1()!=null){
			if(flag){
				result.append(",");
			}else{
				flag=true;	
			}
			result.append("'");
			result.append(vo.getTrucks_Y1());
			result.append("'");
		}
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
	
	@Override
	public int updateTrucksMabiao(String trucks_id,Double mabiao,Connection connection) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM trucks T WHERE trucks_type='主车' AND (trucks_id=? OR guache_trucks_id=? ) LIMIT 1");
			preparedStatement.setString(1, trucks_id);
			preparedStatement.setString(2, trucks_id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				double oldmabiao=resultSet.getDouble("mabiao");//原始码表数
//				double oldli_cheng_run=resultSet.getDouble("li_cheng_run");//行驶里程
				String oldtrucks_id=resultSet.getString("trucks_id");//车牌
				String oldguache_trucks_id=resultSet.getString("guache_trucks_id");//挂车车牌
//				int oldguache_save_flag=resultSet.getInt("guache_save_flag");//挂车保存标志,0表示挂车信息是保存在主车上的，司机是不可以选择挂其他挂车的，1表示挂车信息是单独保存的，司机可以选择挂其他挂车的
				int olddtu_multi_flag=resultSet.getInt("dtu_multi_flag");//0是主挂dtu是一个,1为主挂是分开的
				double dis=mabiao-oldmabiao;//要加的差值
				String oldtrucks_style=resultSet.getString("trucks_style");//车型--4*2+3 
				int a;
				if (dis>0) {
					if(olddtu_multi_flag==1&&oldguache_trucks_id!=null&&!"".equals(oldguache_trucks_id)){//主挂是分开的保存的
						a=updateTyreTrucksLichengRun(dis, mabiao, oldguache_trucks_id, oldtrucks_style, connection);
						if(a!=0){
							return 2;//保存失败
						}
					}
					a=updateTyreTrucksLichengRun(dis, mabiao, oldtrucks_id, oldtrucks_style, connection);
					if(a!=0){
						return 3;//保存失败
					}
				}else {
					return 4;
				}
			}else{
				return 1;//不存在
			}
			return 0;
		} catch (Exception e) {
			logger.error("更新码表数："+StringHelper.getTrace(e));
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
				e.printStackTrace();
			}
		}
		return 5;
	}
	
	//更新胎和车行驶里程
	private int updateTyreTrucksLichengRun(double dis,double mabiao,String trucks_id,String trucks_style,Connection connection) {
		String[] tyreWhereArr=TrucksStyleUtil.TyreWhereArrByStyle(trucks_style);
		if(tyreWhereArr==null){
			return 1;
		}
		PreparedStatement preparedStatement = null;
		StringBuffer SQL = new StringBuffer();
		try {
			SQL.append("UPDATE tyre_dynamic RIGHT JOIN trucks ON (");
			boolean f=false;
			for(int i=0;i<tyreWhereArr.length;i++){
				if(f){
					SQL.append(" or ");
				}else{
					f=true;
				}
				SQL.append("trucks.trucks_")
				.append(tyreWhereArr[i])
				.append("=tyre_dynamic.tyre_id");
			}
			if(!f){
				SQL=null;
				return 1;
			}
			SQL.append(") SET tyre_dynamic.li_cheng_run=tyre_dynamic.li_cheng_run+?,trucks.li_cheng_run=trucks.li_cheng_run+?,trucks.mabiao=? WHERE trucks.trucks_id=?");
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setDouble(1, dis);
			preparedStatement.setDouble(2, dis);
			preparedStatement.setDouble(3, mabiao);
			preparedStatement.setString(4, trucks_id);
			int a = preparedStatement.executeUpdate();
			if (a > 0) {
                logger.info("更新胎和车行驶里程成功！trucks_id="+trucks_id);
                return 0;
			}
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("更新胎和车行驶里程失败！trucks_id="+trucks_id+StringHelper.getTrace(e));
		} finally {
			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		SQL = null;
		return 1;
	}
	
	@Override
	public int goUpdateTrucksMabiao(String trucks_id,Integer user_id,Double mabiao){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("SELECT T.trucks_id,T.mabiao FROM trucks T  LEFT JOIN `user` U ON U.user_company_id=T.company_id WHERE (T.trucks_id=? or T.guache_trucks_id=?) and  U.user_id=? and T.trucks_type='主车' and T.mabiao<? LIMIT 1");
			preparedStatement.setString(1, trucks_id);
			preparedStatement.setString(2, trucks_id);
			preparedStatement.setInt(3, user_id);
			preparedStatement.setDouble(4, mabiao);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int a=updateTrucksMabiao(trucks_id,mabiao, connection);//更新车码表数
				if(a!=0){
					connection.rollback();
					return 2;//保存失败
				}			
			}else{
				return 1;//车不存在
			}
			connection.commit();
			logger.info("更新车码表数:"+trucks_id);
			return 0;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("更新车码表数:"+trucks_id+StringHelper.getTrace(e));
		}finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
			ConnectionPool.close(connection);
		}
		return 2;
	}

	@Override
	public int inTruckInfo(Trucks trucks, List<TyreBase> tyres,Integer userId) {
		TyreBaseDao tyreBaseDao = (TyreBaseDao)new TyreBaseDaoImpl();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = ConnectionPool.getConnection();
			
			TrucksByAdminVO trucksByAdminVO = getByTrucks_id(trucks.getTrucks_id(), connection);
			if (trucksByAdminVO!=null) {//车已入库
				logger.info(trucks.getTrucks_id()+"车辆已入库");
				return 31;
			}
			
			connection.setAutoCommit(false);
			
			StringBuffer SQL = new StringBuffer("insert into trucks(trucks_id,trucks_brand,trucks_style,trucks_type,trucks_flag,trucks_health,company,company_id,li_cheng,li_cheng_run,mabiao,mabiao_ruku,transport_type,guache_trucks_id,trucks_mode,guache_save_flag,dtu_multi_flag");
			String sql1="";
			String sql2="";
			List<TyreBase> notCtyre = new ArrayList<>();
			List<TyreBase> Ctyre = new ArrayList<>();
			List<TyreBase> temptyre = new ArrayList<>();
			
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
						SQL.append(",trucks_"+tyre.getTyre_where());
						sql1+=",?";
						temptyre.add(tyre);
					}
				}else {//挂车信息是保存在主车上(所有的轮胎信息都保存到主车上)
					SQL.append(",trucks_"+tyre.getTyre_where());
					sql1+=",?";
					temptyre.add(tyre);
				}
				
			}
			SQL.append(") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"+sql1+")");
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setString(1, trucks.getTrucks_id());
			preparedStatement.setString(2, trucks.getTrucks_brand());
			preparedStatement.setString(3, trucks.getTrucks_style());
			preparedStatement.setString(4, trucks.getTrucks_type());
			preparedStatement.setInt(5, 0);
			preparedStatement.setFloat(6, 99);
			preparedStatement.setString(7, trucks.getCompany());
			preparedStatement.setFloat(8, trucks.getCompany_id());
			preparedStatement.setDouble(9, trucks.getLi_cheng());
			preparedStatement.setDouble(10, trucks.getLi_cheng_run());
			preparedStatement.setDouble(11, trucks.getMabiao());
			preparedStatement.setDouble(12, trucks.getMabiao_ruku());
			preparedStatement.setString(13, trucks.getTransport_type());
			preparedStatement.setString(14, trucks.getGuache_trucks_id());
			preparedStatement.setString(15, trucks.getTrucks_mode());
			preparedStatement.setInt(16, trucks.getGuache_save_flag());
			preparedStatement.setInt(17, trucks.getDtu_multi_flag());
			for (int i=0;i<temptyre.size();i++ ) {
				preparedStatement.setString(18+i, temptyre.get(i).getTyre_id());
			}
			int a = preparedStatement.executeUpdate();
			if (a==0) {//车辆信息入库失败
				logger.error(trucks.getTrucks_id()+"车辆信息入库失败!");
				return 32;
			}
			String style=trucks.getTrucks_style();
			if ("主车".equals(trucks.getTrucks_type())&&(0<style.indexOf("+")&&style.substring(style.indexOf("+")+1).length()>0)&&trucks.getGuache_save_flag()==1) {//单独添加挂车信息
				sql2="insert into trucks(trucks_id,trucks_brand,trucks_style,trucks_type,trucks_flag,trucks_health,company,company_id,li_cheng,li_cheng_run,mabiao,mabiao_ruku,transport_type,guache_trucks_id,trucks_mode,guache_save_flag,dtu_multi_flag";
				String tempSql="";
				for (int i=0;i<Ctyre.size();i++) {
					sql2+=",trucks_"+Ctyre.get(i).getTyre_where();
					tempSql+=",?";
				}
				sql2+=") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"+tempSql+")";
				PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
				preparedStatement2.setString(1, trucks.getGuache_trucks_id());
				preparedStatement2.setString(2, trucks.getTrucks_brand());
				preparedStatement2.setString(3, trucks.getTrucks_style());
				preparedStatement2.setString(4, "挂车");
				preparedStatement2.setInt(5, 0);
				preparedStatement2.setFloat(6, 99);
				preparedStatement2.setString(7, trucks.getCompany());
				preparedStatement2.setFloat(8, trucks.getCompany_id());
				preparedStatement2.setDouble(9, trucks.getLi_cheng());
				preparedStatement2.setDouble(10, trucks.getLi_cheng_run());;
				preparedStatement2.setDouble(11, trucks.getMabiao());
				preparedStatement2.setDouble(12, trucks.getMabiao_ruku());
				preparedStatement2.setString(13, trucks.getTransport_type());
				preparedStatement2.setString(14, "");
				preparedStatement2.setString(15, trucks.getTrucks_mode());
				preparedStatement2.setInt(16, trucks.getGuache_save_flag());
				preparedStatement2.setInt(17, trucks.getDtu_multi_flag());
				for (int i=0;i<Ctyre.size();i++ ) {
					preparedStatement2.setString(18+i, Ctyre.get(i).getTyre_id());
				}
				int b = preparedStatement2.executeUpdate();
				if (b==0) {//车辆信息入库失败
					logger.error(trucks.getGuache_trucks_id()+"挂车信息入库失败!");
					connection.rollback();
					return 33;
				}
				logger.info(trucks.getGuache_trucks_id()+"挂车信息入库成功!");
			}
			
			int b = tyreBaseDao.saveByList(tyres,trucks, userId,connection);
			if (b!=2) {
				logger.error(trucks.getTrucks_id()+"轮胎信息入库失败!");
				connection.rollback();
				return 34;
			}
			
			connection.commit();
			logger.info(trucks.getTrucks_id()+"车辆轮胎信息入库成功!");
			return 35;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("车辆信息入库失败:"+StringHelper.getTrace(e));
		}

		
		return 0;
	}
	
	/**
	 * 操作轮胎时,更新车的健康度
	 * @param connection
	 * @param trucksId 车牌号
	 * @param flag 1:装载  2:卸下  4:轮胎在车上,只是检测了花纹深度
	 * @param tyreInfo 轮胎信息
	 * @param changTyre flag:3时,tyreInfo与changTyre互相交换
	 * @param tyre_where 轮位
	 * @return
	 */
	@Override
	public int updateTrucksHealth(Connection connection,String trucksId,int flag,TyreVO tyreInfo,String tyre_where){
		
		PreparedStatement preparedStatement=null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1=null;
		ResultSet resultSet1 = null;
		PreparedStatement preparedStatement2=null;
		
		
		String trucksSql="SELECT T.trucks_id,T.trucks_style,T.trucks_type,T.guache_trucks_id,T.guache_save_flag,T.dtu_multi_flag "
						+" FROM trucks T WHERE T.trucks_id='"+trucksId+"'";
		if (tyre_where.indexOf("C")>=0) {
			trucksSql+=" OR T.guache_trucks_id='"+trucksId+"'"+"UNION "
					+ "SELECT GT.trucks_id,GT.trucks_style,GT.trucks_type,GT.guache_trucks_id,GT.guache_save_flag,GT.dtu_multi_flag "
					+ "FROM trucks T LEFT JOIN trucks GT ON T.guache_trucks_id=GT.trucks_id "
					+ "WHERE T.trucks_id='"+trucksId+"' or T.guache_trucks_id='"+trucksId+"'";
		}
		
		try {
			preparedStatement=connection.prepareStatement(trucksSql);
			resultSet = preparedStatement.executeQuery();
			List<Trucks> trucks = ResultSetUtil.getByList(resultSet, "trucks_id,trucks_style,trucks_type,guache_trucks_id,guache_save_flag,dtu_multi_flag".split(","), 
					"trucks_id,trucks_style,trucks_type,guache_trucks_id,guache_save_flag,dtu_multi_flag".split(","), Trucks.class, false);
			
			for (Trucks trucksInfo : trucks) {
				if (StringUtils.isEmpty(trucksInfo.getTrucks_id())) {
					continue;
				}
				
				StringBuffer tyreTotalHealth=new StringBuffer();
				
				tyreTotalHealth.append("SELECT SUM(TD.tyre_health) as totalHealth,COUNT(*) AS count FROM trucks T LEFT JOIN trucks GT ON T.guache_trucks_id=GT.trucks_id "
						+"LEFT JOIN tyre_dynamic TD ON (T.trucks_A1=TD.tyre_id OR T.trucks_A2=TD.tyre_id OR T.trucks_A3=TD.tyre_id OR T.trucks_A4=TD.tyre_id OR T.trucks_A5=TD.tyre_id OR T.trucks_A6=TD.tyre_id OR  "
						+"T.trucks_B1=TD.tyre_id OR T.trucks_B2=TD.tyre_id OR T.trucks_B3=TD.tyre_id OR T.trucks_B4=TD.tyre_id OR T.trucks_B5=TD.tyre_id OR T.trucks_B6=TD.tyre_id OR T.trucks_B7=TD.tyre_id OR T.trucks_B8=TD.tyre_id OR ");
				if ("主车".equals(trucksInfo.getTrucks_type())&&trucksInfo.getDtu_multi_flag()==1&&StringUtils.isNotEmpty(trucksInfo.getGuache_trucks_id())) {//挂车区轮胎只保存在挂车上
					tyreTotalHealth.append("GT.trucks_C1=TD.tyre_id OR GT.trucks_C2=TD.tyre_id OR GT.trucks_C3=TD.tyre_id OR GT.trucks_C4=TD.tyre_id OR GT.trucks_C5=TD.tyre_id OR GT.trucks_C6=TD.tyre_id OR  "
										+"GT.trucks_C7=TD.tyre_id OR GT.trucks_C8=TD.tyre_id OR GT.trucks_C9=TD.tyre_id OR GT.trucks_C10=TD.tyre_id OR GT.trucks_C11=TD.tyre_id OR GT.trucks_C12=TD.tyre_id OR  "
										+"GT.trucks_C13=TD.tyre_id OR GT.trucks_C14=TD.tyre_id OR GT.trucks_C15=TD.tyre_id OR GT.trucks_C16=TD.tyre_id) "
							);
				}else {
					tyreTotalHealth.append("T.trucks_C1=TD.tyre_id OR T.trucks_C2=TD.tyre_id OR T.trucks_C3=TD.tyre_id OR T.trucks_C4=TD.tyre_id OR T.trucks_C5=TD.tyre_id OR T.trucks_C6=TD.tyre_id OR  "
										+"T.trucks_C7=TD.tyre_id OR T.trucks_C8=TD.tyre_id OR T.trucks_C9=TD.tyre_id OR T.trucks_C10=TD.tyre_id OR T.trucks_C11=TD.tyre_id OR T.trucks_C12=TD.tyre_id OR  "
										+"T.trucks_C13=TD.tyre_id OR T.trucks_C14=TD.tyre_id OR T.trucks_C15=TD.tyre_id OR T.trucks_C16=TD.tyre_id) ");
				}
			
				tyreTotalHealth.append(" WHERE T.trucks_id='"+trucksInfo.getTrucks_id()+"' AND TD.tyre_health!=''");
				tyreTotalHealth.append(" AND TD.tyre_id!='"+tyreInfo.getTyre_id()+"'");
				preparedStatement1=connection.prepareStatement(tyreTotalHealth.toString());
				resultSet1=preparedStatement1.executeQuery();
				float trucksHealth = 0;
				if (resultSet1.next()) {
					float totalHealth=resultSet1.getFloat(1);
					int count=resultSet1.getInt(2);
					if (flag==1) {//装载的轮胎参与车的健康度计算
						if (tyreInfo.getTyre_health()!=null&&tyreInfo.getTyre_health().floatValue()!=0) {
							totalHealth+=tyreInfo.getTyre_health();
							count+=1;
						}
					}else if (flag==2) {//卸下的轮胎不参与车的健康度计算
						
					}else if (flag==4) {//检测的轮胎，最新的轮胎健康度参与计算
						if (tyreInfo.getTyre_health()!=null&&tyreInfo.getTyre_health().floatValue()!=0) {
							totalHealth+=tyreInfo.getTyre_health();
							count+=1;
						}
					}
					
					if (totalHealth!=0&&count!=0) {
						trucksHealth=totalHealth/count;
					}
				}
		
				
				String updateTrucksHealth="UPDATE trucks SET trucks_health=? where trucks_id=?";
				preparedStatement2=connection.prepareStatement(updateTrucksHealth);
				preparedStatement2.setFloat(1, trucksHealth);
				preparedStatement2.setString(2, trucksInfo.getTrucks_id());
				int a=preparedStatement2.executeUpdate();
				if (a<=0) {
					return 0;
				}
				logger.info(trucksInfo.getTrucks_id()+"健康度更新成功");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (resultSet1 != null && !resultSet1.isClosed()) {
					resultSet1.close();
				}
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
					preparedStatement=null;
				}
				if(preparedStatement1!=null && !preparedStatement1.isClosed()){
					preparedStatement1.close();
					preparedStatement1=null;
				}
				if(preparedStatement2!=null && !preparedStatement2.isClosed()){
					preparedStatement2.close();
					preparedStatement2=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}
	
	@Override
	public int updateTrucksHealthWhenTyreChange(String tyre_id1,String tyre_id2){
		Connection connection = null;
		PreparedStatement pst1 = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3=null;
		ResultSet resultSet1=null;
		ResultSet resultSet2=null;
		List<String> trucksId=new ArrayList<String>();
		int result = 0;
		
		try {
			connection= ConnectionPool.getConnection();
			//获取轮胎所在的车牌号
			pst1=connection.prepareCall("{ call Get_trucks_id('"+tyre_id1+"') }");
			pst2=connection.prepareCall("{ call Get_trucks_id('"+tyre_id2+"') }");
			resultSet1=pst1.executeQuery();
			resultSet2=pst2.executeQuery();
			String sql="(";
			boolean temp=true;
			while (resultSet1.next()) {
				if (StringUtils.isNotEmpty(resultSet1.getString(1))) {
					trucksId.add(resultSet1.getString(1));
					if (temp) {
						sql+="?";
						temp=false;
					}else {
						sql+=",?";
					}
				}
			}
			while (resultSet2.next()) {
				if (StringUtils.isNotEmpty(resultSet2.getString(1))) {
					trucksId.add(resultSet2.getString(1));
					if (temp) {
						sql+="?";
						temp=false;
					}else {
						sql+=",?";
					}
				}
			}
			sql+=")";
			//挂车健康度更新时，主车健康度也更新
			String onSql="(T.trucks_A1=TD.tyre_id OR T.trucks_A2=TD.tyre_id OR T.trucks_A3=TD.tyre_id OR T.trucks_A4=TD.tyre_id OR T.trucks_A5=TD.tyre_id OR T.trucks_A6=TD.tyre_id OR "
					+ "T.trucks_B1=TD.tyre_id OR T.trucks_B2=TD.tyre_id OR T.trucks_B3=TD.tyre_id OR T.trucks_B4=TD.tyre_id OR T.trucks_B5=TD.tyre_id OR T.trucks_B6=TD.tyre_id OR T.trucks_B7=TD.tyre_id OR T.trucks_B8=TD.tyre_id OR "
					+ "T.trucks_C1=TD.tyre_id OR T.trucks_C2=TD.tyre_id OR T.trucks_C3=TD.tyre_id OR T.trucks_C4=TD.tyre_id OR T.trucks_C5=TD.tyre_id OR T.trucks_C6=TD.tyre_id OR "
					+ "T.trucks_C7=TD.tyre_id OR T.trucks_C8=TD.tyre_id OR T.trucks_C9=TD.tyre_id OR T.trucks_C10=TD.tyre_id OR T.trucks_C11=TD.tyre_id OR T.trucks_C12=TD.tyre_id OR "
					+ "T.trucks_C13=TD.tyre_id OR T.trucks_C14=TD.tyre_id OR T.trucks_C15=TD.tyre_id OR T.trucks_C16=TD.tyre_id "
					+ "OR GT.trucks_C1=TD.tyre_id OR GT.trucks_C2=TD.tyre_id OR GT.trucks_C3=TD.tyre_id OR GT.trucks_C4=TD.tyre_id OR GT.trucks_C5=TD.tyre_id OR GT.trucks_C6=TD.tyre_id OR "
					+ "GT.trucks_C7=TD.tyre_id OR GT.trucks_C8=TD.tyre_id OR GT.trucks_C9=TD.tyre_id OR GT.trucks_C10=TD.tyre_id OR GT.trucks_C11=TD.tyre_id OR GT.trucks_C12=TD.tyre_id OR "
					+ "GT.trucks_C13=TD.tyre_id OR GT.trucks_C14=TD.tyre_id OR GT.trucks_C15=TD.tyre_id OR GT.trucks_C16=TD.tyre_id ) ";
			String updateSql="UPDATE trucks T RIGHT JOIN "
					+ "(select T.trucks_id,sum(TD.tyre_health)/COUNT(1) as trucks_health from trucks T LEFT JOIN trucks GT ON T.guache_trucks_id=GT.trucks_id LEFT  JOIN tyre_dynamic TD ON "
					+ onSql
					+"WHERE  TD.tyre_health is not null GROUP BY T.trucks_id) TD ON T.trucks_id=TD.trucks_id "
					+"set T.trucks_health=TD.trucks_health WHERE T.trucks_id in"+sql+" OR T.guache_trucks_id in"+sql;
			pst3=connection.prepareStatement(updateSql);
			for (int i = 0; i < trucksId.size(); i++) {
				pst3.setString(i+1, trucksId.get(i));
			}
			for (int i = 0; i < trucksId.size(); i++) {
				pst3.setString(trucksId.size()+i+1, trucksId.get(i));
			}
			result=pst3.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				if (resultSet1 != null && !resultSet1.isClosed()) {
					resultSet1.close();
				}
				if (resultSet2 != null && !resultSet2.isClosed()) {
					resultSet2.close();
				}
				if(pst1!=null && !pst1.isClosed()){
					pst1.close();
					pst1=null;
				}
				if(pst2!=null && !pst2.isClosed()){
					pst2.close();
					pst2=null;
				}
				if(pst3!=null && !pst3.isClosed()){
					pst3.close();
					pst3=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			ConnectionPool.close(connection);
		}
		
		return result;
	}
	
	/**
	 * 驾驶甩挂时，更新主车的健康度
	 * @param trucks_id 主车车牌号
	 * @param onlyZhuche true:没有挂车,健康度只根据A,B轮位计算 false:换了挂车,健康度根据所有轮位计算
	 * @param guacheId 挂车车牌号
	 * @return
	 */
	public int updateTrucksHealthWhenDriving(String trucks_id,boolean onlyZhuche,String guacheId,Connection conn){
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		PreparedStatement preparedStatement2=null;
		ResultSet resultSet2=null;
		PreparedStatement preparedStatement3=null;
		
		String tyreTotalHealth="SELECT SUM(TD.tyre_health) as totalHealth,COUNT(*) AS count FROM trucks T LEFT JOIN tyre_dynamic TD ON ";
				
		
		String zhucheOnSql="(T.trucks_A1=TD.tyre_id OR T.trucks_A2=TD.tyre_id OR T.trucks_A3=TD.tyre_id OR T.trucks_A4=TD.tyre_id OR T.trucks_A5=TD.tyre_id OR T.trucks_A6=TD.tyre_id OR  "
				+"T.trucks_B1=TD.tyre_id OR T.trucks_B2=TD.tyre_id OR T.trucks_B3=TD.tyre_id OR T.trucks_B4=TD.tyre_id OR T.trucks_B5=TD.tyre_id OR T.trucks_B6=TD.tyre_id OR T.trucks_B7=TD.tyre_id OR T.trucks_B8=TD.tyre_id) ";
		
		String guacheOnSql="(T.trucks_C1=TD.tyre_id OR T.trucks_C2=TD.tyre_id OR T.trucks_C3=TD.tyre_id OR T.trucks_C4=TD.tyre_id OR T.trucks_C5=TD.tyre_id OR T.trucks_C6=TD.tyre_id OR  "
				+"T.trucks_C7=TD.tyre_id OR T.trucks_C8=TD.tyre_id OR T.trucks_C9=TD.tyre_id OR T.trucks_C10=TD.tyre_id OR T.trucks_C11=TD.tyre_id OR T.trucks_C12=TD.tyre_id OR  "
				+"T.trucks_C13=TD.tyre_id OR T.trucks_C14=TD.tyre_id OR T.trucks_C15=TD.tyre_id OR T.trucks_C16=TD.tyre_id) ";
		
		String where=" WHERE T.trucks_id=? AND TD.tyre_health!=''";
		
		try {
			preparedStatement=conn.prepareStatement(tyreTotalHealth+zhucheOnSql+where);
			preparedStatement.setString(1, trucks_id);
			resultSet=preparedStatement.executeQuery();
			float totalHealth = 0;
			int count = 0;
			//获取主车的A,B区轮胎的健康度
			if (resultSet.next()) {
				totalHealth=resultSet.getFloat("totalHealth");
				count=resultSet.getInt("count");
			}
			if (!onlyZhuche) {
				//获取挂车的轮胎的健康度
				preparedStatement2=conn.prepareStatement(tyreTotalHealth+guacheOnSql+where);
				preparedStatement2.setString(1, guacheId);
				resultSet2=preparedStatement2.executeQuery();
				if (resultSet2.next()) {
					totalHealth+=resultSet2.getFloat("totalHealth");
					count+=resultSet2.getFloat("count");
				}
			}
			String updateTrucksHealth="UPDATE trucks SET trucks_health=? where trucks_id=?";
			preparedStatement3=conn.prepareStatement(updateTrucksHealth);
			preparedStatement3.setFloat(1, (totalHealth==0||count==0)?0:totalHealth/count);
			preparedStatement3.setString(2, trucks_id);
			int a=preparedStatement3.executeUpdate();
			if (a<=0) {
				return 0;
			}
			logger.info(trucks_id+"健康度更新成功");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (resultSet2 != null && !resultSet2.isClosed()) {
					resultSet2.close();
				}
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
					preparedStatement=null;
				}
				if(preparedStatement2!=null && !preparedStatement2.isClosed()){
					preparedStatement2.close();
					preparedStatement2=null;
				}
				if(preparedStatement3!=null && !preparedStatement3.isClosed()){
					preparedStatement3.close();
					preparedStatement3=null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return 1;
	}

	@Override
	public ListInfo<WarnMsgVO> searchWarnMsglist(Integer company_id,
			String keyWord, int currentPageNO, int pageSize) {
		String baseSql=" FROM warn_msg WM LEFT JOIN device D ON WM.dtu_id=D.dtu_id LEFT JOIN trucks T ON WM.trucks_id=T.trucks_id LEFT JOIN company C ON C.company_id=T.company_id  LEFT JOIN company CD ON CD.company_id=D.company_id ";
		String typeSql="";//类型查询
		String keyWordSql="";//关键字查询
		
		typeSql=" (CD.company_id="+company_id+" or CD.parent_id="+company_id+") ";
		if(keyWord!=null&&!"".equals(keyWord)){
			keyWordSql= " (WM.trucks_id like '%"+ keyWord + "%' or WM.dtu_id like '%"+keyWord+"%') ";
		}
		StringBuffer whereSql=new StringBuffer();
		if(!"".equals(typeSql)){
			if(!"".equals(whereSql.toString())){
				whereSql.append(" and ");
			}
			whereSql.append(typeSql);
		}
		if(!"".equals(keyWordSql)){
			if(!"".equals(whereSql.toString())){
				whereSql.append(" and ");
			}
			whereSql.append(keyWordSql);
		}
		if(!"".equals(whereSql.toString())){
			baseSql=baseSql+" where " + whereSql.toString();
		}
//		String fields=" T.id,T.trucks_id,T.trucks_brand,T.trucks_style,T.trucks_flag,T.trucks_health,T.company_id,C.company,D.phone,D.dtu_id,D.caiji_time,D.create_time,D.gps_status,D.longitude,D.latitude,D.warn ";
		String fields=" WM.id,WM.dtu_id,WM.create_time,WM.warn_msg,WM.status,WM.trucks_id,T.trucks_brand,T.trucks_style,T.trucks_flag,T.trucks_health,T.company_id,C.company,D.phone,D.longitude,D.latitude ";
		String sqlBylist="select DISTINCT "+fields+baseSql+"  order by WM.id desc ";
		String sqlByCount="select count(DISTINCT WM.id) "+baseSql;
		ListInfo<WarnMsgVO> listInfo = new ListInfo<WarnMsgVO>(currentPageNO, pageSize);
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatementCount = null;
		ResultSet resultSetCount = null;
		try {
			connection = ConnectionPool.getConnection();
			System.out.println(sqlBylist+" LIMIT "+listInfo.getFirst()+"," + pageSize);
			preparedStatement = connection.prepareStatement(sqlBylist+" LIMIT "+listInfo.getFirst()+"," + pageSize);
			resultSet = preparedStatement.executeQuery();
			
			List<WarnMsgVO> eventList=ResultSetUtil.getByList(resultSet, "id,dtu_id,create_time,warn_msg,status,trucks_id,trucks_brand,trucks_style,trucks_flag,trucks_health,company_id,company,phone,longitude,latitude".split(","),
					"id,dtu_id,create_time,warn_msg,status,trucks_id,trucks_brand,trucks_style,trucks_flag,trucks_health,company_id,company,phone,longitude,latitude".split(","), WarnMsgVO.class, false);
			
			listInfo.setCurrentList(eventList);
			preparedStatementCount = connection.prepareStatement(sqlByCount);
			resultSetCount = preparedStatementCount.executeQuery();
			if (resultSetCount.next()) {
				listInfo.setSizeOfTotalList(resultSetCount.getInt(1));
			}else{
				listInfo.setSizeOfTotalList(0);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
					preparedStatement=null;
				}
				if (resultSetCount != null && !resultSetCount.isClosed()) {
					resultSetCount.close();
				}
				if (preparedStatementCount != null && !preparedStatementCount.isClosed()) {
					preparedStatementCount.close();
					preparedStatementCount=null;
				}
			} catch (SQLException e) {
			}
			ConnectionPool.close(connection);
		}
		return listInfo;
	}
	
	@Override
	public List<Message> warnMsgListByApp(Integer user_id,String trucksId,Integer state,int pagenum){
		List<Message> list=null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT  WM.id,WM.dtu_id,WM.create_time,WM.warn_msg,WM.status,WM.trucks_id,T.company ")
			.append(" FROM warn_msg WM LEFT JOIN device D ON WM.dtu_id=D.dtu_id LEFT JOIN trucks T ON WM.trucks_id=T.trucks_id LEFT JOIN `user` U ON U.user_company_id=T.company_id")
			.append(" WHERE U.user_id=?");
		
		if(trucksId!=null&&!"".equals(trucksId)){
			SQL.append(" and WM.trucks_id like '%"+ trucksId + "%' ");
		}
		
		if(state!=null){
			SQL.append(" and WM.status="+state);
		}
		SQL.append(" order by WM.create_time DESC");
		SQL.append(" LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, user_id);
			
			resultSet = preparedStatement.executeQuery();
			
			list=ResultSetUtil.getByList(resultSet, "msg_id,dtu_id,createTime,warn_msg,status,trucks_id,company".split(","),
					"id,dtu_id,create_time,warn_msg,status,trucks_id,company".split(","), Message.class, false);
			
			
		} catch (Exception e) {
			e.printStackTrace();
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
		return list;
	}
	
}
