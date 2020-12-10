package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.psylife.dao.DeviceDao;
import com.psylife.entity.DeviceDataOffon;
import com.psylife.entity.DeviceFasheqi;
import com.psylife.hardware.process.HeartProcess;
import com.psylife.util.ConnectionPool;
import com.psylife.util.Constants;
import com.psylife.util.DateUtil;
import com.psylife.util.ResultSetUtil;
import com.psylife.util.StringHelper;
import com.psylife.util.page.ListInfo;
import com.psylife.vo.web.DeviceDataHistoryVO;
import com.psylife.vo.web.FasheqiDataHistoryVO;
import com.psylife.vo.web.TrucksDeviceCountVO;
import com.psylife.vo.web.TrucksFasheqiPosVO;
 
public class DeviceDaoImpl extends BaseDaoImpl implements DeviceDao{

	@Override
	public TrucksDeviceCountVO getTrucksDeviceCount(Integer company_id){
		TrucksDeviceCountVO vo=null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT DISTINCT T.trucks_id,D.caiji_time,D.warn FROM trucks T INNER JOIN device D ON D.trucks_id=T.trucks_id LEFT JOIN company C ON C.company_id=T.company_id WHERE C.company_id=? OR C.parent_id=?");
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, company_id);
			preparedStatement.setInt(2, company_id);
			resultSet = preparedStatement.executeQuery();
			Date caiji_time=null;
			long currentTime=System.currentTimeMillis();
			int online=0,offline=0,count=0,warn=0;
			long dateTime=DateUtil.getDayBegin(new Date()).getTime();
			while(resultSet.next()){
				caiji_time=resultSet.getTimestamp(2);
				if(caiji_time!=null&&currentTime-caiji_time.getTime()<HeartProcess.INTERVAL_TIMEOUT_TCP){
					online++;
				}else{
					offline++;
				}
				count++;
				if(resultSet.getInt(3)==1&&caiji_time!=null&&caiji_time.getTime()-dateTime>=0){
					warn++;
				}
			}
			vo=new TrucksDeviceCountVO();
			vo.setCount(count);
			vo.setOnline(online);
			vo.setOffline(offline);
			vo.setWarn(warn);			
			logger.info("获取当前硬件数据汇总成功！");
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("获取当前硬件数据汇总失败！："+StringHelper.getTrace(e));
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
		return vo;
	}
	
	@Override
	public TrucksFasheqiPosVO fasheqipos(String dtu_id){
		TrucksFasheqiPosVO fasheqiPosVO=null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement1 = null;
		ResultSet resultSet1 = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("SELECT DISTINCT T.id,T.trucks_id,T.trucks_brand,T.trucks_style,T.company_id,C.company,D.dtu_id"
				+ " FROM device D LEFT JOIN trucks T ON D.trucks_id=T.trucks_id LEFT JOIN company C ON C.company_id=T.company_id WHERE D.dtu_id=?");
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setString(1, dtu_id);
			resultSet = preparedStatement.executeQuery();
			fasheqiPosVO=ResultSetUtil.getByOne(resultSet, "id,trucks_id,trucks_brand,trucks_style,company_id,company,dtu_id".split(","),
					"id,trucks_id,trucks_brand,trucks_style,company_id,company,dtu_id".split(","), TrucksFasheqiPosVO.class, false);
			if(fasheqiPosVO!=null){
				preparedStatement1 = connection.prepareStatement("SELECT id,create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,no,tyre_id,trucks_id,li_cheng,dtu_id "+
						 "FROM device_fasheqi WHERE dtu_id=? ");
				preparedStatement1.setString(1, dtu_id);
				resultSet1 = preparedStatement1.executeQuery();
				List<DeviceFasheqi> deviceFasheqis=ResultSetUtil.getByList(resultSet1, "id,create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,no,tyre_id,trucks_id,li_cheng,dtu_id".split(","), 
						"id,create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,no,tyre_id,trucks_id,li_cheng,dtu_id".split(","), DeviceFasheqi.class, false);
				fasheqiPosVO.setDeviceFasheqis(deviceFasheqis);
			}
			logger.info("获取传感器分布成功！");
		} catch (Exception e) {
			logger.error("获取传感器分布失败！："+StringHelper.getTrace(e));
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
		return fasheqiPosVO;
	}
	
	
	@Override
	public List<DeviceDataHistoryVO> getHistoryDtuList(String keyWord,int pagenum,Integer company_id,Date startTime,Date endTime){
		List<DeviceDataHistoryVO> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		List<Object> plist=new ArrayList<Object>();
		SQL.append("SELECT DDH.id,DDH.dtu_id,DDH.create_time,DDH.float_caiji_time,DDH.string_caiji_time,DDH.offon_caiji_time,base_id,DDH.dingwei_id,DDH.offon_uuid,DDH.yaliwendu_uuid,DDH.trucks_id,DDH.warn,DDD.gps_status,DDD.dimian_sulu,DDD.longitude,DDD.latitude") 
           .append(" FROM device_data_history DDH LEFT JOIN device D ON D.dtu_id=DDH.dtu_id LEFT JOIN device_data_dingwei DDD ON DDD.id=DDH.dingwei_id LEFT JOIN company CD ON CD.company_id=D.company_id WHERE (CD.company_id=? or CD.parent_id=?)  ");
		plist.add(company_id);
		plist.add(company_id);
		if(startTime!=null){
			SQL.append(" and DDH.create_time>=? ");
			plist.add(startTime);
		}
		if(endTime!=null){
			SQL.append(" and DDH.create_time<=? ");
			plist.add(endTime);
		}
		if(keyWord!=null&&!"".equals(keyWord)){
			SQL.append(" and (D.dtu_id=? or DDH.trucks_id=?) ");
			plist.add(keyWord);
			plist.add(keyWord);
		}
		SQL.append(" ORDER BY id DESC LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			for(int i=1;i<=plist.size();i++){
				preparedStatement.setObject(i, plist.get(i-1));
			}
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "id,dtu_id,create_time,float_caiji_time,string_caiji_time,offon_caiji_time,base_id,dingwei_id,offon_uuid,yaliwendu_uuid,trucks_id,warn,gps_status,dimian_sulu,longitude,latitude".split(","), 
					"id,dtu_id,create_time,float_caiji_time,string_caiji_time,offon_caiji_time,base_id,dingwei_id,offon_uuid,yaliwendu_uuid,trucks_id,warn,gps_status,dimian_sulu,longitude,latitude".split(","), DeviceDataHistoryVO.class, false);
			if(list!=null){
				for(DeviceDataHistoryVO deviceDataHistoryVO:list){
					deviceDataHistoryVO.setDeviceDataOffons(getOffonByUUID(deviceDataHistoryVO.getOffon_uuid(), connection));
					if(deviceDataHistoryVO.getWarn()==1){
						deviceDataHistoryVO.setWarnInfo(warnInfoByDTU(deviceDataHistoryVO.getDeviceDataOffons()));
					}
				}
			}
			
			logger.info("根据公司id获取车辆历史记录列表成功！");
		} catch (Exception e) {
			logger.error("根据公司id获取车辆历史记录列表失败！："+StringHelper.getTrace(e));
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
	public List<FasheqiDataHistoryVO> getHistoryfasheqiList(String keyWord,int pagenum,Integer company_id,Date startTime,Date endTime){
		List<FasheqiDataHistoryVO> list = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		List<Object> plist=new ArrayList<Object>();
		SQL.append("SELECT DDH.id,DDH.dtu_id,DDH.trucks_id,DDH.create_time,DDO.fasheqi_id,DDO.louqi,DDO.gaoya,DDO.diya,DDO.gaowen,DDO.dianchi,DDO.fasheqidianchi,DDO.zhongduan,DDO.shilian,DDO.warn,DDO.`no`,DDO.tyre_id,DDYW.yali,DDYW.wendu ")
.append("FROM device_data_history DDH LEFT JOIN device D ON D.dtu_id=DDH.dtu_id ")
.append("INNER JOIN device_data_offon DDO ON DDO.uuid=DDH.offon_uuid LEFT JOIN device_data_yali_wendu DDYW ON (DDYW.uuid=DDH.yaliwendu_uuid and DDYW.fasheqi_id=DDO.fasheqi_id) LEFT JOIN company CD ON CD.company_id=D.company_id ")
 .append("WHERE (CD.company_id=? or CD.parent_id=?)   ");
		plist.add(company_id);
		plist.add(company_id);
		if(startTime!=null){
			SQL.append(" and DDH.create_time>=? ");
			plist.add(startTime);
		}
		if(endTime!=null){
			SQL.append(" and DDH.create_time<=? ");
			plist.add(endTime);
		}
		if(keyWord!=null&&!"".equals(keyWord)){
			SQL.append(" and (DDO.fasheqi_id=? or DDO.tyre_id=?) ");
			plist.add(keyWord);
			plist.add(keyWord);
		}
		SQL.append(" ORDER BY id DESC LIMIT "+((pagenum-1)*Constants.PAGESIZE)+","+Constants.PAGESIZE);
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(SQL.toString());
			for(int i=1;i<=plist.size();i++){
				preparedStatement.setObject(i, plist.get(i-1));
			}
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "id,dtu_id,trucks_id,create_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,`no`,tyre_id,yali,wendu".split(","), 
					"id,dtu_id,trucks_id,create_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,`no`,tyre_id,yali,wendu".split(","), FasheqiDataHistoryVO.class, false);			
			logger.info("根据公司id获取传感器历史数据记录成功！");
		} catch (Exception e) {
			logger.error("根据公司id获取传感器历史数据记录失败！："+StringHelper.getTrace(e));
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
	public ListInfo<DeviceFasheqi> searchByFasheqilist(Integer company_id,String keyWord,int currentPageNO,int pageSize){
		String baseSql=" FROM device_fasheqi DF LEFT JOIN device D ON D.dtu_id=DF.dtu_id LEFT JOIN company CD ON CD.company_id=D.company_id ";
		String typeSql="";//类型查询
		String keyWordSql="";//关键字查询
		
		typeSql=" (CD.company_id="+company_id+" or CD.parent_id="+company_id+") ";
		if(keyWord!=null&&!"".equals(keyWord)){
			keyWordSql= " (DF.fasheqi_id like '%"+ keyWord + "%' or D.dtu_id like '%"+keyWord+"%' or D.trucks_id like '%"+keyWord+"%') ";
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
		String fields=" DF.id,DF.create_time,DF.caiji_time,DF.fasheqi_id,DF.louqi,gaoya,DF.diya,DF.gaowen,DF.dianchi,DF.fasheqidianchi,DF.zhongduan,DF.shilian,DF.warn,DF.yali,wendu,DF.`no`,DF.tyre_id,DF.trucks_id,DF.li_cheng,DF.dtu_id ";
		String sqlBylist="select DISTINCT "+fields+baseSql+"  order by DF.id desc ";
		String sqlByCount="select count(DISTINCT DF.id) "+baseSql;
		ListInfo<DeviceFasheqi> listInfo = new ListInfo<DeviceFasheqi>(currentPageNO, pageSize);
		
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
			
			List<DeviceFasheqi> eventList=ResultSetUtil.getByList(resultSet, "id,create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,`no`,tyre_id,trucks_id,li_cheng,dtu_id".split(","),
					"id,create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,`no`,tyre_id,trucks_id,li_cheng,dtu_id".split(","), DeviceFasheqi.class, false);
			
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
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 根据uuid获取开关量
	 * @param uuid
	 * @param connection
	 * @return
	 */
	private List<DeviceDataOffon> getOffonByUUID(String uuid,Connection connection){
		List<DeviceDataOffon> list = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT id,create_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,no,tyre_id,uuid "+
 "FROM device_data_offon WHERE uuid=?");
			preparedStatement.setString(1, uuid);
			resultSet = preparedStatement.executeQuery();
			list=ResultSetUtil.getByList(resultSet, "id,create_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,no,tyre_id,uuid".split(","), 
					"id,create_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,no,tyre_id,uuid".split(","), DeviceDataOffon.class, false);
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("根据公司id获取车辆历史记录列表开关量失败！："+StringHelper.getTrace(e));
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
		}
		return list;
	}
	
	/**
	 * dtu,警告信息
	 * @param dataOffons
	 * @return
	 */
	private String warnInfoByDTU(List<DeviceDataOffon> dataOffons){
		if(dataOffons==null||dataOffons.size()<1){
			return null;
		}
		StringBuffer louqi=new StringBuffer();//急漏气告警（0表示无告警，1表示有告警）
		StringBuffer gaoya=new StringBuffer();//高压告警（0表示无告警，1表示有告警）
		StringBuffer diya=new StringBuffer();//低压告警（0表示无告警，1表示有告警）
		StringBuffer gaowen=new StringBuffer();//高温告警（0表示无告警，1表示有告警）
		StringBuffer dianchi=new StringBuffer();//设备电池低电（0表示无告警，1表示有告警）
		StringBuffer fasheqidianchi=new StringBuffer();//发射器低电池告警（0表示无告警，1表示有告警）
		StringBuffer shilian=new StringBuffer();//失联（0表示无，1表示失联）warn
		StringBuffer result=new StringBuffer();
		for(DeviceDataOffon deviceDataOffon:dataOffons){
		    if(deviceDataOffon.getWarn()==1){
		    	if(deviceDataOffon.getShilian()==1){//
		    		if(shilian.length()==0){
		    			shilian.append("<b>失联:</b>传感器").append(deviceDataOffon.getFasheqi_id());
		    		}else{
		    			shilian.append(";传感器").append(deviceDataOffon.getFasheqi_id());
		    		}
		    		if(deviceDataOffon.getTyre_id()!=null){
		    			shilian.append(",胎号").append(deviceDataOffon.getTyre_id());
		    		}
		    		continue;
		    	}
		    	if(deviceDataOffon.getLouqi()==1){//
		    		if(louqi.length()==0){
		    			louqi.append("<b>漏气:</b>传感器").append(deviceDataOffon.getFasheqi_id());
		    		}else{
		    			louqi.append(";传感器").append(deviceDataOffon.getFasheqi_id());
		    		}
		    		if(deviceDataOffon.getTyre_id()!=null){
		    			louqi.append(",胎号").append(deviceDataOffon.getTyre_id());
		    		}
		    	}
		    	if(deviceDataOffon.getGaoya()==1){//
		    		if(gaoya.length()==0){
		    			gaoya.append("<b>高压:</b>传感器").append(deviceDataOffon.getFasheqi_id());
		    		}else{
		    			gaoya.append(";传感器").append(deviceDataOffon.getFasheqi_id());
		    		}
		    		if(deviceDataOffon.getTyre_id()!=null){
		    			gaoya.append(",胎号").append(deviceDataOffon.getTyre_id());
		    		}
		    	}
		    	if(deviceDataOffon.getDiya()==1){//
		    		if(diya.length()==0){
		    			diya.append("<b>低压:</b>传感器").append(deviceDataOffon.getFasheqi_id());
		    		}else{
		    			diya.append(";传感器").append(deviceDataOffon.getFasheqi_id());
		    		}
		    		if(deviceDataOffon.getTyre_id()!=null){
		    			diya.append(",胎号").append(deviceDataOffon.getTyre_id());
		    		}
		    	}
		    	if(deviceDataOffon.getGaowen()==1){//
		    		if(gaowen.length()==0){
		    			gaowen.append("<b>高温:</b>传感器").append(deviceDataOffon.getFasheqi_id());
		    		}else{
		    			gaowen.append(";传感器").append(deviceDataOffon.getFasheqi_id());
		    		}
		    		if(deviceDataOffon.getTyre_id()!=null){
		    			gaowen.append(",胎号").append(deviceDataOffon.getTyre_id());
		    		}
		    	}
		    	if(deviceDataOffon.getDianchi()==1){//
		    		if(dianchi.length()==0){
		    			dianchi.append("<b>设备电池低电:</b>传感器").append(deviceDataOffon.getFasheqi_id());
		    		}else{
		    			dianchi.append(";传感器").append(deviceDataOffon.getFasheqi_id());
		    		}
		    		if(deviceDataOffon.getTyre_id()!=null){
		    			dianchi.append(",胎号").append(deviceDataOffon.getTyre_id());
		    		}
		    	}
		    	if(deviceDataOffon.getFasheqidianchi()==1){//
		    		if(fasheqidianchi.length()==0){
		    			fasheqidianchi.append("<b>发射器低电池:</b>传感器").append(deviceDataOffon.getFasheqi_id());
		    		}else{
		    			fasheqidianchi.append(";传感器").append(deviceDataOffon.getFasheqi_id());
		    		}
		    		if(deviceDataOffon.getTyre_id()!=null){
		    			fasheqidianchi.append(",胎号").append(deviceDataOffon.getTyre_id());
		    		}
		    	}
		    	
		    }
		}	
//		result.append("信息");
		if(louqi.length()>0){
			result.append("<br/>").append(louqi);	
		}
		if(gaoya.length()>0){
			result.append("<br/>").append(gaoya);		
		}
		if(diya.length()>0){
			result.append("<br/>").append(diya);			
		}
		if(gaowen.length()>0){
			result.append("<br/>").append(gaowen);		
		}
		if(dianchi.length()>0){
			result.append("<br/>").append(dianchi);			
		}
		if(fasheqidianchi.length()>0){
			result.append("<br/>").append(fasheqidianchi);		
		}
		if(shilian.length()>0){
			result.append("<br/>").append(shilian);			
		}
		return result.toString().replaceFirst("<br/>", "");
	}
	
}
