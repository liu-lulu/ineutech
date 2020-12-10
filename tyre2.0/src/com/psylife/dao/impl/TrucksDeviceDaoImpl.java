package com.psylife.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.gexin.fastjson.JSON;
import com.psylife.dao.TrucksDeviceDao;
import com.psylife.entity.Device;
import com.psylife.entity.DeviceDataBase;
import com.psylife.entity.DeviceDataDingwei;
import com.psylife.entity.DeviceDataOffon;
import com.psylife.entity.DeviceDataYaliWendu;
import com.psylife.entity.DeviceFasheqi;
import com.psylife.hardware.HardwareElement;
import com.psylife.util.ConnectionPool;
import com.psylife.util.JPushUtil;
import com.psylife.util.LonLatUtil;
import com.psylife.util.StringHelper;
import com.psylife.util.TrucksStyleUtil;
import com.psylife.util.push.Message;
import com.psylife.vo.TrucksByAdminVO;
import com.psylife.vo.dtu.TyreFloatWenduYaliVO;
import com.psylife.vo.dtu.TyreOffonVO;
import com.psylife.vo.dtu.TyreStringVO;

public class TrucksDeviceDaoImpl implements TrucksDeviceDao {

	@Override
	public void saveDeviceData(HardwareElement element){
		if((element.isFlagOffon()&&element.isFlagFloat()&&element.isFlagString())==false){
			return;
		}
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<ResultSet> rList=new ArrayList<ResultSet>();
		List<PreparedStatement> pList=new ArrayList<PreparedStatement>();
		boolean OldDeviceChangeFlag=false;
		boolean dingweiFlag=false;
		boolean yaliWenduFlag=false;
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			Date now=new Date();
			//对应的车牌不存时，查询
			if(element.getCarNum()==null&&System.currentTimeMillis()-element.getCarNoSearchTime()>HardwareElement.CARNO_SEARCH_GAP){
				preparedStatement = connection.prepareStatement("SELECT TD.id,TD.trucks_id,TD.li_cheng,TD.dtu_id,T.trucks_style"
						+ ",T.trucks_A1,T.trucks_A2,T.trucks_A3,T.trucks_A4,T.trucks_A5,T.trucks_A6,T.trucks_B1,T.trucks_B2,T.trucks_B3,T.trucks_B4,T.trucks_B5,T.trucks_B6,T.trucks_B7,T.trucks_B8,T.trucks_C1,T.trucks_C2,T.trucks_C3,T.trucks_C4,T.trucks_C5,T.trucks_C6,T.trucks_C7,T.trucks_C8,T.trucks_C9,T.trucks_C10,T.trucks_C11,T.trucks_C12,T.trucks_C13,T.trucks_C14,T.trucks_C15,T.trucks_C16"
						+ " FROM device TD LEFT JOIN trucks T ON T.trucks_id=TD.trucks_id  WHERE TD.dtu_id=? LIMIT 1");
				preparedStatement.setString(1, element.getPhone());
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					element.setCarDeviceId(resultSet.getLong("id"));
					element.setCarNum(resultSet.getString("trucks_id"));
					element.setLiCheng(resultSet.getDouble("li_cheng"));
					element.setCarStyle(resultSet.getString("trucks_style"));
					if(element.getLiCheng()<0){
						element.setLiCheng(0);
					}					
					Map<String, String> map=element.getMapPositionTyreId();
					map.clear();
					if(resultSet.getString("trucks_A1")!=null&&!"".equals(resultSet.getString("trucks_A1"))){
						map.put("A1",resultSet.getString("trucks_A1"));
					}
					if(resultSet.getString("trucks_A2")!=null&&!"".equals(resultSet.getString("trucks_A2"))){
						map.put("A2",resultSet.getString("trucks_A2"));
					}
					if(resultSet.getString("trucks_A3")!=null&&!"".equals(resultSet.getString("trucks_A3"))){
						map.put("A3",resultSet.getString("trucks_A3"));
					}
					if(resultSet.getString("trucks_A4")!=null&&!"".equals(resultSet.getString("trucks_A4"))){
						map.put("A4",resultSet.getString("trucks_A4"));
					}
					if(resultSet.getString("trucks_A5")!=null&&!"".equals(resultSet.getString("trucks_A5"))){
						map.put("A5",resultSet.getString("trucks_A5"));
					}
					if(resultSet.getString("trucks_A6")!=null&&!"".equals(resultSet.getString("trucks_A6"))){
						map.put("A6",resultSet.getString("trucks_A6"));
					}
					
					if(resultSet.getString("trucks_B1")!=null&&!"".equals(resultSet.getString("trucks_B1"))){
						map.put("B1",resultSet.getString("trucks_B1"));
					}
					if(resultSet.getString("trucks_B2")!=null&&!"".equals(resultSet.getString("trucks_B2"))){
						map.put("B2",resultSet.getString("trucks_B2"));
					}
					if(resultSet.getString("trucks_B3")!=null&&!"".equals(resultSet.getString("trucks_B3"))){
						map.put("B3",resultSet.getString("trucks_B3"));
					}
					if(resultSet.getString("trucks_B4")!=null&&!"".equals(resultSet.getString("trucks_B4"))){
						map.put("B4",resultSet.getString("trucks_B4"));
					}
					if(resultSet.getString("trucks_B5")!=null&&!"".equals(resultSet.getString("trucks_B5"))){
						map.put("B5",resultSet.getString("trucks_B5"));
					}
					if(resultSet.getString("trucks_B6")!=null&&!"".equals(resultSet.getString("trucks_B6"))){
						map.put("B6",resultSet.getString("trucks_B6"));
					}
					if(resultSet.getString("trucks_B7")!=null&&!"".equals(resultSet.getString("trucks_B7"))){
						map.put("B7",resultSet.getString("trucks_B7"));
					}
					if(resultSet.getString("trucks_B8")!=null&&!"".equals(resultSet.getString("trucks_B8"))){
						map.put("B8",resultSet.getString("trucks_B8"));
					}
					
					if(resultSet.getString("trucks_C1")!=null&&!"".equals(resultSet.getString("trucks_C1"))){
						map.put("C1",resultSet.getString("trucks_C1"));
					}
					if(resultSet.getString("trucks_C2")!=null&&!"".equals(resultSet.getString("trucks_C2"))){
						map.put("C2",resultSet.getString("trucks_C2"));
					}
					if(resultSet.getString("trucks_C3")!=null&&!"".equals(resultSet.getString("trucks_C3"))){
						map.put("C3",resultSet.getString("trucks_C3"));
					}
					if(resultSet.getString("trucks_C4")!=null&&!"".equals(resultSet.getString("trucks_C4"))){
						map.put("C4",resultSet.getString("trucks_C4"));
					}
					if(resultSet.getString("trucks_C5")!=null&&!"".equals(resultSet.getString("trucks_C5"))){
						map.put("C5",resultSet.getString("trucks_C5"));
					}
					if(resultSet.getString("trucks_C6")!=null&&!"".equals(resultSet.getString("trucks_C6"))){
						map.put("C6",resultSet.getString("trucks_C6"));
					}
					if(resultSet.getString("trucks_C7")!=null&&!"".equals(resultSet.getString("trucks_C7"))){
						map.put("C7",resultSet.getString("trucks_C7"));
					}
					if(resultSet.getString("trucks_C8")!=null&&!"".equals(resultSet.getString("trucks_C8"))){
						map.put("C8",resultSet.getString("trucks_C8"));
					}
					if(resultSet.getString("trucks_C9")!=null&&!"".equals(resultSet.getString("trucks_C9"))){
						map.put("C9",resultSet.getString("trucks_C9"));
					}
					if(resultSet.getString("trucks_C10")!=null&&!"".equals(resultSet.getString("trucks_C10"))){
						map.put("C10",resultSet.getString("trucks_C10"));
					}
					if(resultSet.getString("trucks_C11")!=null&&!"".equals(resultSet.getString("trucks_C11"))){
						map.put("C11",resultSet.getString("trucks_C11"));
					}
					if(resultSet.getString("trucks_C12")!=null&&!"".equals(resultSet.getString("trucks_C12"))){
						map.put("C12",resultSet.getString("trucks_C12"));
					}
					if(resultSet.getString("trucks_C13")!=null&&!"".equals(resultSet.getString("trucks_C13"))){
						map.put("C13",resultSet.getString("trucks_C13"));
					}
					if(resultSet.getString("trucks_C14")!=null&&!"".equals(resultSet.getString("trucks_C14"))){
						map.put("C14",resultSet.getString("trucks_C14"));
					}
					if(resultSet.getString("trucks_C15")!=null&&!"".equals(resultSet.getString("trucks_C15"))){
						map.put("C15",resultSet.getString("trucks_C15"));
					}
					if(resultSet.getString("trucks_C16")!=null&&!"".equals(resultSet.getString("trucks_C16"))){
						map.put("C16",resultSet.getString("trucks_C16"));
					}
				}
				element.setCarNoSearchTime(System.currentTimeMillis());
				rList.add(resultSet);
				pList.add(preparedStatement);
			}
			
			
			dataNewDtu(element);//保存时，组装新数据
			
			long id;
			
			//保存上报日志
			//保存dtu设备数据基础
			if(!element.getOldDeviceDataBase().equalsValue(element.getDeviceDataBase())){
				id=saveByDeviceData(new Object[]{element.getDeviceDataBase().getDtu_trucks_id(),element.getPhone(),now,element.getDeviceDataBase().getDtu_tpms_status(),element.getDeviceDataBase().getDtu_status(),element.getDeviceDataBase().getTpms_pinlu(),
						element.getDeviceDataBase().getYali_set_low_zhou_1(),element.getDeviceDataBase().getYali_set_low_zhou_2(),element.getDeviceDataBase().getYali_set_low_zhou_3(),element.getDeviceDataBase().getYali_set_low_zhou_4(),element.getDeviceDataBase().getYali_set_high_zhou_1(),element.getDeviceDataBase().getYali_set_high_zhou_2(),element.getDeviceDataBase().getYali_set_high_zhou_3(),element.getDeviceDataBase().getYali_set_high_zhou_4(),
						element.getDeviceDataBase().getGao_wen_bao_jing_set(),element.getDeviceDataBase().getYuliu1_phone(),element.getDeviceDataBase().getYuliu2_phone(),element.getDeviceDataBase().getYuliu3_phone(),element.getDeviceDataBase().getYuliu4_phone(),element.getCarNum(),element.getDeviceDataBase().getDtu_no()}, 
						"dtu_trucks_id,dtu_id,create_time,dtu_tpms_status,dtu_status,tpms_pinlu,"
						+ "yali_set_low_zhou_1,yali_set_low_zhou_2,yali_set_low_zhou_3,yali_set_low_zhou_4,yali_set_high_zhou_1,yali_set_high_zhou_2,yali_set_high_zhou_3,yali_set_high_zhou_4,"
						+ "gao_wen_bao_jing_set,yuliu1_phone,yuliu2_phone,yuliu3_phone,yuliu4_phone,trucks_id,dtu_no", "device_data_base", connection, rList, pList);	
			    element.getDeviceDataHistory().setBase_id(id);
			}
			double dis=-2d;
			//保存dtu设备数据定位信息
			if(!element.getOldDeviceDataDingwei().equalsValue(element.getDeviceDataDingwei())){
				dingweiFlag=true;
				id=saveByDeviceData(new Object[]{element.getPhone(),now,element.getDeviceDataDingwei().getGps_status(),element.getDeviceDataDingwei().getLatitude(),element.getDeviceDataDingwei().getLatitude_type(),
						element.getDeviceDataDingwei().getLongitude(),element.getDeviceDataDingwei().getLongitude_type(),element.getDeviceDataDingwei().getDimian_sulu(),element.getDeviceDataDingwei().getDimian_hangxiang(),
						element.getCarNum()}, 
						"dtu_id,create_time,gps_status,latitude,latitude_type,longitude,longitude_type,dimian_sulu,dimian_hangxiang,trucks_id","device_data_dingwei", connection, rList, pList);	
			    element.getDeviceDataHistory().setDingwei_id(id);
				try {
					if(element.getDeviceDataDingwei().getGps_status().equals(0)&&element.getOldDeviceDataDingwei().getGps_status()!=null&&element.getOldDeviceDataDingwei().getGps_status().equals(0)){
						dis=LonLatUtil.getDistance(element.getDeviceDataDingwei().getLongitude(), element.getDeviceDataDingwei().getLongitude_type(),
								element.getDeviceDataDingwei().getLatitude(), element.getDeviceDataDingwei().getLatitude_type(), 
								element.getOldDeviceDataDingwei().getLongitude(), element.getOldDeviceDataDingwei().getLongitude_type(),
								element.getOldDeviceDataDingwei().getLatitude(), element.getOldDeviceDataDingwei().getLatitude_type());
					}
					
				} catch (Exception e) {
					dis=-2d;
					e.printStackTrace();
				}
			}
			
			if(element.getCarNum()!=null){
				if(element.getDeviceDataDingwei().getDimian_sulu().floatValue()>0.0009){
					if(!element.isRuning()){
						element.setRuning(true);
						updateTrucksFlag(1, element, connection, rList, pList);//行驶中
					}
				}else{
					if(element.isRuning()){
						element.setRuning(false);
						updateTrucksFlag(0, element, connection, rList, pList);//停止中
					}
				}				
			}
			
			//保存dtu设备数据开关量
			boolean flag1=true;
			if(element.getOldDeviceDataOffons().size()!=element.getDeviceDataOffons().size()){
				flag1=false;
			}else{
				for(int i=0;i<element.getOldDeviceDataOffons().size();i++){
					if(!element.getOldDeviceDataOffons().get(i).equalsValue(element.getDeviceDataOffons().get(i))){
						flag1=false;
						break;
					}
				}
			}
			if(flag1==false){
				String uuid=UUID.randomUUID().toString();
				//批量保存开关量
				saveByListDeviceDataOffon(element.getDeviceDataOffons(), now, uuid, connection, rList, pList);
				element.getDeviceDataHistory().setOffon_uuid(uuid);
			}
			
			//保存dtu设备数据压力温度
			boolean flag=true;
			if(element.getOldDeviceDataYaliWendus().size()!=element.getDeviceDataYaliWendus().size()){
				flag=false;
			}else{
				for(int i=0;i<element.getOldDeviceDataYaliWendus().size();i++){
					if(!element.getOldDeviceDataYaliWendus().get(i).equalsValue(element.getDeviceDataYaliWendus().get(i))){
						flag=false;
						break;
					}
				}
			}
			if(flag==false){
				yaliWenduFlag=true;
				String uuid=UUID.randomUUID().toString();
				//批量保存dtu设备数据压力温度
				saveByListDeviceDataYaliWendu(element.getDeviceDataYaliWendus(), now, uuid, connection, rList, pList);
				element.getDeviceDataHistory().setYaliwendu_uuid(uuid);
			}
			
			//保存上报记录
			saveByDeviceData(new Object[]{element.getPhone(),now,element.getDeviceFloatByDtuVO().getCaiji_time(),element.getDeviceStringByDtuVO().getCaiji_time(),element.getDeviceOffonByDtuVO().getCaiji_time(),
					element.getDeviceDataHistory().getBase_id(),element.getDeviceDataHistory().getDingwei_id(),element.getDeviceDataHistory().getOffon_uuid(),element.getDeviceDataHistory().getYaliwendu_uuid(),element.getCarNum(),element.getDeviceDataHistory().getWarn()}, 
					"dtu_id,create_time,float_caiji_time,string_caiji_time,offon_caiji_time,base_id,dingwei_id,offon_uuid,yaliwendu_uuid,trucks_id,warn", "device_data_history", connection, rList, pList);
			//保存报警信息
			if(flag1==false&&element.getDeviceDataHistory().getWarn().equals(1)){
				saveByDeviceData(new Object[]{element.getPhone(),now,element.getDeviceFloatByDtuVO().getCaiji_time(),element.getDeviceStringByDtuVO().getCaiji_time(),element.getDeviceOffonByDtuVO().getCaiji_time(),
						element.getDeviceDataHistory().getBase_id(),element.getDeviceDataHistory().getDingwei_id(),element.getDeviceDataHistory().getOffon_uuid(),element.getDeviceDataHistory().getYaliwendu_uuid(),element.getCarNum(),element.getDeviceDataHistory().getWarn()}, 
						"dtu_id,create_time,float_caiji_time,string_caiji_time,offon_caiji_time,base_id,dingwei_id,offon_uuid,yaliwendu_uuid,trucks_id,warn", "device_data_warn", connection, rList, pList);
				
				saveDtuWarnMessage(element, now, connection, rList, pList);//保存报警信息
			}
			
			//保存发射器信息
			saveFasheqi(now, element, connection);
			
			//更新设备数据
			if(!element.getOldDevice().equalsValue(element.getDevice())){
				OldDeviceChangeFlag=true;
				preparedStatement = connection.prepareStatement(
						"UPDATE device SET trucks_id=?,caiji_time=?,dtu_tpms_status=?,dtu_status=?,tpms_pinlu=?,"
						+ "yali_set_low_zhou_1=?,yali_set_low_zhou_2=?,yali_set_low_zhou_3=?,yali_set_low_zhou_4=?,yali_set_high_zhou_1=?,yali_set_high_zhou_2=?,yali_set_high_zhou_3=?,yali_set_high_zhou_4=?,gao_wen_bao_jing_set=?,"
						+ "yuliu1_phone=?,yuliu2_phone=?,yuliu3_phone=?,yuliu4_phone=?,gps_status=?,latitude=?,latitude_type=?,longitude=?,longitude_type=?,dimian_sulu=?,dimian_hangxiang=?,li_cheng=li_cheng+?,warn=? WHERE dtu_id=?");
				preparedStatement.setString(1, element.getCarNum());
				preparedStatement.setObject(2, now);
				preparedStatement.setInt(3, element.getDevice().getDtu_tpms_status());
				preparedStatement.setInt(4, element.getDevice().getDtu_status());
				preparedStatement.setInt(5, element.getDevice().getTpms_pinlu());
				preparedStatement.setFloat(6, element.getDevice().getYali_set_low_zhou_1());
				preparedStatement.setFloat(7, element.getDevice().getYali_set_low_zhou_2());
				preparedStatement.setFloat(8, element.getDevice().getYali_set_low_zhou_3());
				preparedStatement.setFloat(9, element.getDevice().getYali_set_low_zhou_4());
				preparedStatement.setFloat(10, element.getDevice().getYali_set_high_zhou_1());
				preparedStatement.setFloat(11, element.getDevice().getYali_set_high_zhou_2());
				preparedStatement.setFloat(12, element.getDevice().getYali_set_high_zhou_3());
				preparedStatement.setFloat(13, element.getDevice().getYali_set_high_zhou_4());
				
				preparedStatement.setFloat(14, element.getDevice().getGao_wen_bao_jing_set());
				
				preparedStatement.setString(15, element.getDevice().getYuliu1_phone());
				preparedStatement.setString(16, element.getDevice().getYuliu2_phone());
				preparedStatement.setString(17, element.getDevice().getYuliu3_phone());
				preparedStatement.setString(18, element.getDevice().getYuliu4_phone());
				
				preparedStatement.setInt(19, element.getDevice().getGps_status());
				
				preparedStatement.setDouble(20, element.getDevice().getLatitude());
				preparedStatement.setInt(21, element.getDevice().getLatitude_type());
				preparedStatement.setDouble(22, element.getDevice().getLongitude());
				preparedStatement.setInt(23, element.getDevice().getLongitude_type());
				
				preparedStatement.setFloat(24, element.getDevice().getDimian_sulu());
				preparedStatement.setFloat(25, element.getDevice().getDimian_hangxiang());
				
				preparedStatement.setDouble(26, (dis>0?dis:0d));
				
				preparedStatement.setInt(27, element.getDeviceDataHistory().getWarn());
				
				preparedStatement.setString(28, element.getPhone());
				preparedStatement.executeUpdate();
				pList.add(preparedStatement);
				
//				if(element.getDevice().getDtu_status()!=null&&!element.getDeviceOffonByDtuVO().getDtu_status().equals(element.getOldDevice().getDtu_status())){//更新下汽车状态
//					if(element.getCarNum()!=null){
//						updateTrucksFlag(element.getDeviceOffonByDtuVO().getDtu_status(), element, connection, rList, pList);
//					}
//				}
			}else{
				preparedStatement = connection.prepareStatement(
						"UPDATE device SET caiji_time=?,li_cheng=li_cheng+?,warn=? WHERE dtu_id=?");
				preparedStatement.setObject(1, now);				
				preparedStatement.setDouble(2, (dis>0?dis:0d));
				
				preparedStatement.setInt(3, element.getDeviceDataHistory().getWarn());
				
				preparedStatement.setString(4, element.getPhone());
				preparedStatement.executeUpdate();
				pList.add(preparedStatement);
			}
			//更新里程
			if(dis>0){
				element.setLiCheng(dis+element.getLiCheng());
				//更新胎里程
				if(element.getCarNum()!=null){
					updateTyreLicheng(dis, element, connection, rList, pList);
				}
				
				//更新发射器里程
				preparedStatement = connection.prepareStatement(
						"UPDATE device_fasheqi SET li_cheng=li_cheng+? WHERE dtu_id=?");
				preparedStatement.setDouble(1, dis);
				preparedStatement.setString(2, element.getPhone());
				preparedStatement.executeUpdate();
				pList.add(preparedStatement);
				
			}
			
			connection.commit();
			logger.info("设备数据保存成功,设备号:"+element.getPhone()+",车牌号:"+element.getCarNum());
			return ;			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			logger.error("设备数据保存失败,设备号:"+element.getPhone()+",车牌号:"+element.getCarNum()+StringHelper.getTrace(e));
		}finally {
			try {
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if(preparedStatement!=null && !preparedStatement.isClosed()){
					preparedStatement.close();
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
			
			element.setFlagOffon(false);
			element.setFlagFloat(false);
			element.setFlagString(false);
			
			if(OldDeviceChangeFlag){
				//新值变成旧值,新旧值交换
				Device device=element.getDevice();
				element.setDevice(element.getOldDevice());
				element.setOldDevice(device);
			}	
			
			//新值变成旧值,新旧值交换
			DeviceDataBase deviceDataBase=element.getDeviceDataBase();
			element.setDeviceDataBase(element.getOldDeviceDataBase());
			element.setOldDeviceDataBase(deviceDataBase);
			
			if(dingweiFlag){
				//新值变成旧值,新旧值交换
				DeviceDataDingwei deviceDataDingwei=element.getDeviceDataDingwei();
				element.setDeviceDataDingwei(element.getOldDeviceDataDingwei());
				element.setOldDeviceDataDingwei(deviceDataDingwei);
			}	
			
			//新值变成旧值,新旧值交换
			List<DeviceDataOffon> deviceDataOffons=element.getDeviceDataOffons();
			element.setDeviceDataOffons(element.getOldDeviceDataOffons());
			element.setOldDeviceDataOffons(deviceDataOffons);
			
			if(yaliWenduFlag){
				//新值变成旧值,新旧值交换
				List<DeviceDataYaliWendu> deviceDataYaliWendus=element.getDeviceDataYaliWendus();
				element.setDeviceDataYaliWendus(element.getOldDeviceDataYaliWendus());
				element.setOldDeviceDataYaliWendus(deviceDataYaliWendus);
			}	
			
			//新值变成旧值,新旧值交换
			List<DeviceFasheqi> deviceFasheqis=element.getDeviceFasheqis();
			element.setDeviceFasheqis(element.getOldDeviceFasheqis());
			element.setOldDeviceFasheqis(deviceFasheqis);
		}
		return;
	}
	//更新胎里程
	private void updateTyreLicheng(double dis,HardwareElement element,Connection connection,List<ResultSet> rList,List<PreparedStatement> pList) {
		String[] tyreWhereArr=TrucksStyleUtil.TyreWhereArrByStyle(element.getCarStyle());
		if(tyreWhereArr==null){
			return;
		}
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		try {
			SQL.append("UPDATE tyre_dynamic INNER JOIN trucks ON (");
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
				return;
			}
			SQL.append(") SET tyre_dynamic.tyre_km=tyre_dynamic.tyre_km+?,trucks.li_cheng=trucks.li_cheng+? WHERE trucks.trucks_id=?");
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setDouble(1, dis);
			preparedStatement.setDouble(2, dis);
			preparedStatement.setString(3, element.getCarNum());
			int a = preparedStatement.executeUpdate();
			if (a > 0) {
                logger.info("更新胎里程成功！trucks_id="+element.getCarNum());
			}
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("更新胎里程失败！trucks_id="+element.getCarNum()+StringHelper.getTrace(e));
		} finally {
			if (resultSet != null) {
				rList.add(resultSet);
			}
			if (preparedStatement != null) {
				pList.add(preparedStatement);
			}
		}
		SQL = null;
		return;
	}
	
	//更新车,是否行使还是停放
	@Override
	public void updateTrucksFlag(int dtu_status,HardwareElement element,Connection connection,List<ResultSet> rList,List<PreparedStatement> pList) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		try {
//			if(dtu_status==0){//行使
//				dtu_status=1;
//			}else{//停放
//				dtu_status=0;
//			}
			SQL.append("UPDATE trucks SET trucks_flag=? WHERE trucks_id=?");
			preparedStatement = connection.prepareStatement(SQL.toString());
			preparedStatement.setInt(1, dtu_status);
			preparedStatement.setString(2, element.getCarNum());
			int a = preparedStatement.executeUpdate();
			if (a > 0) {
                logger.info("更新车,是否行使还是停放成功！trucks_id="+element.getCarNum());
			}
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("更新车,是否行使还是停放失败！trucks_id="+element.getCarNum()+StringHelper.getTrace(e));
		} finally {
			if (resultSet != null) {
				rList.add(resultSet);
			}
			if (preparedStatement != null) {
				pList.add(preparedStatement);
			}
		}
		SQL = null;
		return;
	}
	
	@Override
	public boolean reloadTrucksDevice(HardwareElement element){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean flag=true;
		try {
			connection = ConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement("SELECT TD.id,TD.trucks_id,TD.li_cheng,TD.dtu_id,T.trucks_style"
					+ ",T.trucks_A1,T.trucks_A2,T.trucks_A3,T.trucks_A4,T.trucks_A5,T.trucks_A6,T.trucks_B1,T.trucks_B2,T.trucks_B3,T.trucks_B4,T.trucks_B5,T.trucks_B6,T.trucks_B7,T.trucks_B8,T.trucks_C1,T.trucks_C2,T.trucks_C3,T.trucks_C4,T.trucks_C5,T.trucks_C6,T.trucks_C7,T.trucks_C8,T.trucks_C9,T.trucks_C10,T.trucks_C11,T.trucks_C12,T.trucks_C13,T.trucks_C14,T.trucks_C15,T.trucks_C16"
					+ " FROM device TD LEFT JOIN trucks T ON T.trucks_id=TD.trucks_id  WHERE TD.dtu_id=? LIMIT 1");
			preparedStatement.setString(1, element.getPhone());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				element.setCarDeviceId(resultSet.getLong("id"));
				element.setCarNum(resultSet.getString("trucks_id"));
				element.setLiCheng(resultSet.getDouble("li_cheng"));
				element.setCarStyle(resultSet.getString("trucks_style"));
				if(element.getLiCheng()<0){
					element.setLiCheng(0);
				}					
				Map<String, String> map=element.getMapPositionTyreId();
				map.clear();
				if(resultSet.getString("trucks_A1")!=null&&!"".equals(resultSet.getString("trucks_A1"))){
					map.put("A1",resultSet.getString("trucks_A1"));
				}
				if(resultSet.getString("trucks_A2")!=null&&!"".equals(resultSet.getString("trucks_A2"))){
					map.put("A2",resultSet.getString("trucks_A2"));
				}
				if(resultSet.getString("trucks_A3")!=null&&!"".equals(resultSet.getString("trucks_A3"))){
					map.put("A3",resultSet.getString("trucks_A3"));
				}
				if(resultSet.getString("trucks_A4")!=null&&!"".equals(resultSet.getString("trucks_A4"))){
					map.put("A4",resultSet.getString("trucks_A4"));
				}
				if(resultSet.getString("trucks_A5")!=null&&!"".equals(resultSet.getString("trucks_A5"))){
					map.put("A5",resultSet.getString("trucks_A5"));
				}
				if(resultSet.getString("trucks_A6")!=null&&!"".equals(resultSet.getString("trucks_A6"))){
					map.put("A6",resultSet.getString("trucks_A6"));
				}
				
				if(resultSet.getString("trucks_B1")!=null&&!"".equals(resultSet.getString("trucks_B1"))){
					map.put("B1",resultSet.getString("trucks_B1"));
				}
				if(resultSet.getString("trucks_B2")!=null&&!"".equals(resultSet.getString("trucks_B2"))){
					map.put("B2",resultSet.getString("trucks_B2"));
				}
				if(resultSet.getString("trucks_B3")!=null&&!"".equals(resultSet.getString("trucks_B3"))){
					map.put("B3",resultSet.getString("trucks_B3"));
				}
				if(resultSet.getString("trucks_B4")!=null&&!"".equals(resultSet.getString("trucks_B4"))){
					map.put("B4",resultSet.getString("trucks_B4"));
				}
				if(resultSet.getString("trucks_B5")!=null&&!"".equals(resultSet.getString("trucks_B5"))){
					map.put("B5",resultSet.getString("trucks_B5"));
				}
				if(resultSet.getString("trucks_B6")!=null&&!"".equals(resultSet.getString("trucks_B6"))){
					map.put("B6",resultSet.getString("trucks_B6"));
				}
				if(resultSet.getString("trucks_B7")!=null&&!"".equals(resultSet.getString("trucks_B7"))){
					map.put("B7",resultSet.getString("trucks_B7"));
				}
				if(resultSet.getString("trucks_B8")!=null&&!"".equals(resultSet.getString("trucks_B8"))){
					map.put("B8",resultSet.getString("trucks_B8"));
				}
				
				if(resultSet.getString("trucks_C1")!=null&&!"".equals(resultSet.getString("trucks_C1"))){
					map.put("C1",resultSet.getString("trucks_C1"));
				}
				if(resultSet.getString("trucks_C2")!=null&&!"".equals(resultSet.getString("trucks_C2"))){
					map.put("C2",resultSet.getString("trucks_C2"));
				}
				if(resultSet.getString("trucks_C3")!=null&&!"".equals(resultSet.getString("trucks_C3"))){
					map.put("C3",resultSet.getString("trucks_C3"));
				}
				if(resultSet.getString("trucks_C4")!=null&&!"".equals(resultSet.getString("trucks_C4"))){
					map.put("C4",resultSet.getString("trucks_C4"));
				}
				if(resultSet.getString("trucks_C5")!=null&&!"".equals(resultSet.getString("trucks_C5"))){
					map.put("C5",resultSet.getString("trucks_C5"));
				}
				if(resultSet.getString("trucks_C6")!=null&&!"".equals(resultSet.getString("trucks_C6"))){
					map.put("C6",resultSet.getString("trucks_C6"));
				}
				if(resultSet.getString("trucks_C7")!=null&&!"".equals(resultSet.getString("trucks_C7"))){
					map.put("C7",resultSet.getString("trucks_C7"));
				}
				if(resultSet.getString("trucks_C8")!=null&&!"".equals(resultSet.getString("trucks_C8"))){
					map.put("C8",resultSet.getString("trucks_C8"));
				}
				if(resultSet.getString("trucks_C9")!=null&&!"".equals(resultSet.getString("trucks_C9"))){
					map.put("C9",resultSet.getString("trucks_C9"));
				}
				if(resultSet.getString("trucks_C10")!=null&&!"".equals(resultSet.getString("trucks_C10"))){
					map.put("C10",resultSet.getString("trucks_C10"));
				}
				if(resultSet.getString("trucks_C11")!=null&&!"".equals(resultSet.getString("trucks_C11"))){
					map.put("C11",resultSet.getString("trucks_C11"));
				}
				if(resultSet.getString("trucks_C12")!=null&&!"".equals(resultSet.getString("trucks_C12"))){
					map.put("C12",resultSet.getString("trucks_C12"));
				}
				if(resultSet.getString("trucks_C13")!=null&&!"".equals(resultSet.getString("trucks_C13"))){
					map.put("C13",resultSet.getString("trucks_C13"));
				}
				if(resultSet.getString("trucks_C14")!=null&&!"".equals(resultSet.getString("trucks_C14"))){
					map.put("C14",resultSet.getString("trucks_C14"));
				}
				if(resultSet.getString("trucks_C15")!=null&&!"".equals(resultSet.getString("trucks_C15"))){
					map.put("C15",resultSet.getString("trucks_C15"));
				}
				if(resultSet.getString("trucks_C16")!=null&&!"".equals(resultSet.getString("trucks_C16"))){
					map.put("C16",resultSet.getString("trucks_C16"));
				}
			}
			
			logger.info("重新加载设备和车牌对应关系,设备号:"+element.getPhone()+",车牌号:"+element.getCarNum());
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
			logger.error("重新加载设备和车牌对应关系,设备号:"+element.getPhone()+",车牌号:"+element.getCarNum()+StringHelper.getTrace(e));
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
		return flag;
	}
	//保存
	private long saveByDeviceData(Object[] paramters, String sqlCols,String tabeName,Connection connection,List<ResultSet> rList,List<PreparedStatement> pList) {
		long id = 0;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		StringBuffer SQL = new StringBuffer();
		SQL.append("insert into ").append(tabeName).append("(").append(sqlCols).append(") ").append("values( ");
		int i=0;
		for(i=0;i<paramters.length;i++){
			if(i==0){
				SQL.append("?");
			}else{
				SQL.append(",?");
			}
		}
		SQL.append(") ");
		try {
			preparedStatement = connection.prepareStatement(SQL.toString(),Statement.RETURN_GENERATED_KEYS);
			for(i=0;i<paramters.length;i++){
				preparedStatement.setObject(i+1, paramters[i]);
			}
			int a = preparedStatement.executeUpdate();
			if (a > 0) {
				resultSet = preparedStatement.getGeneratedKeys();  
                if(resultSet.next()) {
                	id = resultSet.getLong(1); 
                }
                logger.info("保存成功！");
			}
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			logger.error("保存失败："+StringHelper.getTrace(e));
		} finally {
			if (resultSet != null) {
				rList.add(resultSet);
			}
			if (preparedStatement != null) {
				pList.add(preparedStatement);
			}
		}
		SQL = null;
		return id;
	}
	
	/**
	 * 保存时，组装新数据
	 * @param element
	 */
	private void dataNewDtu(HardwareElement element){
		
		//dtu设备
		element.getDevice().setCaiji_time(element.getDeviceOffonByDtuVO().getCaiji_time());
		element.getDevice().setDtu_tpms_status(element.getDeviceOffonByDtuVO().getDtu_tpms_status());
		element.getDevice().setDtu_status(element.getDeviceOffonByDtuVO().getDtu_status());
		element.getDevice().setTpms_pinlu(element.getDeviceFloatByDtuVO().getTpms_pinlu());
		
		element.getDevice().setYali_set_low_zhou_1(element.getDeviceFloatByDtuVO().getYali_set().get(0).getL());
		element.getDevice().setYali_set_high_zhou_1(element.getDeviceFloatByDtuVO().getYali_set().get(0).getH());
		element.getDevice().setYali_set_low_zhou_2(element.getDeviceFloatByDtuVO().getYali_set().get(1).getL());
		element.getDevice().setYali_set_high_zhou_2(element.getDeviceFloatByDtuVO().getYali_set().get(1).getH());
		element.getDevice().setYali_set_low_zhou_3(element.getDeviceFloatByDtuVO().getYali_set().get(2).getL());
		element.getDevice().setYali_set_high_zhou_3(element.getDeviceFloatByDtuVO().getYali_set().get(2).getH());
		element.getDevice().setYali_set_low_zhou_4(element.getDeviceFloatByDtuVO().getYali_set().get(3).getL());
		element.getDevice().setYali_set_high_zhou_4(element.getDeviceFloatByDtuVO().getYali_set().get(3).getH());
		
		element.getDevice().setGao_wen_bao_jing_set(element.getDeviceFloatByDtuVO().getGao_wen_bao_jing_set());
		
		element.getDevice().setYuliu1_phone(element.getDeviceStringByDtuVO().getYuliu1_phone());
		element.getDevice().setYuliu2_phone(element.getDeviceStringByDtuVO().getYuliu2_phone());
		element.getDevice().setYuliu3_phone(element.getDeviceStringByDtuVO().getYuliu3_phone());
		element.getDevice().setYuliu4_phone(element.getDeviceStringByDtuVO().getYuliu4_phone());
		
		element.getDevice().setGps_status(element.getDeviceOffonByDtuVO().getGps_status());
		element.getDevice().setLatitude(element.getDeviceStringByDtuVO().getLatitude());
		element.getDevice().setLatitude_type(element.getDeviceStringByDtuVO().getLatitude_type());
		element.getDevice().setLongitude(element.getDeviceStringByDtuVO().getLongitude());
		element.getDevice().setLongitude_type(element.getDeviceStringByDtuVO().getLongitude_type());
		
		element.getDevice().setDimian_sulu(element.getDeviceFloatByDtuVO().getDimian_sulu());
		element.getDevice().setDimian_hangxiang(element.getDeviceFloatByDtuVO().getDimian_hangxiang());
		
		//dtu设备数据基础
		element.getDeviceDataBase().setDtu_id(element.getPhone());
		element.getDeviceDataBase().setDtu_trucks_id(element.getDeviceStringByDtuVO().getDtu_trucks_id());
		element.getDeviceDataBase().setDtu_tpms_status(element.getDeviceOffonByDtuVO().getDtu_tpms_status());
		element.getDeviceDataBase().setDtu_status(element.getDeviceOffonByDtuVO().getDtu_status());
		element.getDeviceDataBase().setTpms_pinlu(element.getDeviceFloatByDtuVO().getTpms_pinlu());
		
		element.getDeviceDataBase().setYali_set_low_zhou_1(element.getDeviceFloatByDtuVO().getYali_set().get(0).getL());
		element.getDeviceDataBase().setYali_set_high_zhou_1(element.getDeviceFloatByDtuVO().getYali_set().get(0).getH());
		element.getDeviceDataBase().setYali_set_low_zhou_2(element.getDeviceFloatByDtuVO().getYali_set().get(1).getL());
		element.getDeviceDataBase().setYali_set_high_zhou_2(element.getDeviceFloatByDtuVO().getYali_set().get(1).getH());
		element.getDeviceDataBase().setYali_set_low_zhou_3(element.getDeviceFloatByDtuVO().getYali_set().get(2).getL());
		element.getDeviceDataBase().setYali_set_high_zhou_3(element.getDeviceFloatByDtuVO().getYali_set().get(2).getH());
		element.getDeviceDataBase().setYali_set_low_zhou_4(element.getDeviceFloatByDtuVO().getYali_set().get(3).getL());
		element.getDeviceDataBase().setYali_set_high_zhou_4(element.getDeviceFloatByDtuVO().getYali_set().get(3).getH());
		
		element.getDeviceDataBase().setGao_wen_bao_jing_set(element.getDeviceFloatByDtuVO().getGao_wen_bao_jing_set());
		
		element.getDeviceDataBase().setYuliu1_phone(element.getDeviceStringByDtuVO().getYuliu1_phone());
		element.getDeviceDataBase().setYuliu2_phone(element.getDeviceStringByDtuVO().getYuliu2_phone());
		element.getDeviceDataBase().setYuliu3_phone(element.getDeviceStringByDtuVO().getYuliu3_phone());
		element.getDeviceDataBase().setYuliu4_phone(element.getDeviceStringByDtuVO().getYuliu4_phone());
		
		element.getDeviceDataBase().setTrucks_id(element.getCarNum());
		element.getDeviceDataBase().setDtu_no(element.getDeviceStringByDtuVO().getDtu_no());
		
		//dtu设备数据定位信息
		element.getDeviceDataDingwei().setDtu_id(element.getPhone());
		
		element.getDeviceDataDingwei().setGps_status(element.getDeviceOffonByDtuVO().getGps_status());
		element.getDeviceDataDingwei().setLatitude(element.getDeviceStringByDtuVO().getLatitude());
		element.getDeviceDataDingwei().setLatitude_type(element.getDeviceStringByDtuVO().getLatitude_type());
		element.getDeviceDataDingwei().setLongitude(element.getDeviceStringByDtuVO().getLongitude());
		element.getDeviceDataDingwei().setLongitude_type(element.getDeviceStringByDtuVO().getLongitude_type());
		
		element.getDeviceDataDingwei().setDimian_sulu(element.getDeviceFloatByDtuVO().getDimian_sulu());
		element.getDeviceDataDingwei().setDimian_hangxiang(element.getDeviceFloatByDtuVO().getDimian_hangxiang());
		
		element.getDeviceDataDingwei().setTrucks_id(element.getCarNum());
		
		DeviceDataOffon dataOffon;
		
		List<TyreStringVO> fasheqi_ids_value=element.getDeviceStringByDtuVO().getFasheqi_ids_value();
		List<TyreOffonVO> offon_value=element.getDeviceOffonByDtuVO().getOffon_value();
		List<TyreFloatWenduYaliVO> wendu_yali_value=element.getDeviceFloatByDtuVO().getWendu_yali_value();
		
		DeviceDataYaliWendu dataYaliWendu;
		DeviceFasheqi deviceFasheqi;
		
		element.getDeviceDataYaliWendus().clear();
		element.getDeviceDataOffons().clear();
		element.getDeviceFasheqis().clear();
		element.getDeviceDataHistory().setWarn(0);
		for(int i=0;i<offon_value.size();i++){
			if(!"-99.99".equals(fasheqi_ids_value.get(i).getFasheqiid())){
				//dtu设备数据开关量
				dataOffon=new DeviceDataOffon();
				dataOffon.setFasheqi_id(fasheqi_ids_value.get(i).getFasheqiid());
				dataOffon.setLouqi(offon_value.get(i).getLouqi());
				dataOffon.setGaoya(offon_value.get(i).getGaoya());
				dataOffon.setDiya(offon_value.get(i).getDiya());
				dataOffon.setGaowen(offon_value.get(i).getGaowen());
				dataOffon.setDianchi(offon_value.get(i).getDianchi());
				dataOffon.setFasheqidianchi(offon_value.get(i).getFasheqidianchi());
				dataOffon.setZhongduan(offon_value.get(i).getFasheqizhongduan());
//				if(Math.abs(wendu_yali_value.get(i).getWendu().floatValue()+99.99f)<0.001){
//					dataOffon.setShilian(1);
//				}else{
//					dataOffon.setShilian(0);
//				}
				dataOffon.setShilian(dataOffon.getZhongduan());
				dataOffon.setWarn(offon_value.get(i).getLouqi()|offon_value.get(i).getGaoya()
				|offon_value.get(i).getDiya()|offon_value.get(i).getGaowen()
				|offon_value.get(i).getDianchi()|offon_value.get(i).getFasheqidianchi()|
				dataOffon.getShilian());
				dataOffon.setNo(offon_value.get(i).getNo());
				dataOffon.setTyre_id(null);
				if(element.getCarNum()!=null){//轮胎号
					String tyreWhere=TrucksStyleUtil.TyreWhereByFasheqiNo(element.getCarStyle(), dataOffon.getNo());
					if(tyreWhere!=null){
						dataOffon.setTyre_id(element.getMapPositionTyreId().get(tyreWhere));
					}					
				}
				element.getDeviceDataOffons().add(dataOffon);
				
				//dtu设备数据压力温度
				dataYaliWendu=new DeviceDataYaliWendu();
				dataYaliWendu.setFasheqi_id(fasheqi_ids_value.get(i).getFasheqiid());
				dataYaliWendu.setYali(wendu_yali_value.get(i).getYali());
				dataYaliWendu.setWendu(wendu_yali_value.get(i).getWendu());
				dataYaliWendu.setNo(offon_value.get(i).getNo());
				dataYaliWendu.setTyre_id(dataOffon.getTyre_id());
				element.getDeviceDataYaliWendus().add(dataYaliWendu);
				
				//设备发射器
				deviceFasheqi=new DeviceFasheqi();
				deviceFasheqi.setFasheqi_id(fasheqi_ids_value.get(i).getFasheqiid());
				deviceFasheqi.setLouqi(offon_value.get(i).getLouqi());
				deviceFasheqi.setGaoya(offon_value.get(i).getGaoya());
				deviceFasheqi.setDiya(offon_value.get(i).getDiya());
				deviceFasheqi.setGaowen(offon_value.get(i).getGaowen());
				deviceFasheqi.setDianchi(offon_value.get(i).getDianchi());
				deviceFasheqi.setFasheqidianchi(offon_value.get(i).getFasheqidianchi());
				deviceFasheqi.setZhongduan(offon_value.get(i).getFasheqizhongduan());
				deviceFasheqi.setShilian(dataOffon.getShilian());
				
				deviceFasheqi.setWarn(dataOffon.getWarn());
				deviceFasheqi.setYali(wendu_yali_value.get(i).getYali());
				deviceFasheqi.setWendu(wendu_yali_value.get(i).getWendu());
				
				deviceFasheqi.setNo(offon_value.get(i).getNo());
				deviceFasheqi.setTyre_id(dataOffon.getTyre_id());
				
				deviceFasheqi.setTrucks_id(element.getCarNum());
				deviceFasheqi.setDtu_id(element.getPhone());
				element.getDeviceFasheqis().add(deviceFasheqi);
				
				element.getDeviceDataHistory().setWarn(element.getDeviceDataHistory().getWarn()|dataOffon.getWarn());
			}
			
		}
		
		
	}
	
	/**
	 * 批量保存开关量
	 * @param deviceDataOffons
	 * @param now
	 * @param uuid
	 * @param connection
	 * @param rList
	 * @param pList
	 * @return
	 */
	private boolean saveByListDeviceDataOffon(List<DeviceDataOffon> deviceDataOffons,Date now,String uuid,Connection connection,List<ResultSet> rList,List<PreparedStatement> pList) {
		boolean flag = false;
		String sql ="insert into device_data_offon(create_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,no,tyre_id,uuid) values";
		PreparedStatement preparedStatement = null;
		try {
			StringBuffer newsql = new StringBuffer();
			newsql.append(sql);
			Integer num=-1;
			List<Object> paramterList=new ArrayList<Object>();
			for(int i=0;i<deviceDataOffons.size();i++){
				DeviceDataOffon deviceDataOffon=deviceDataOffons.get(i);
				paramterList.addAll(Arrays.asList(new Object[]{now,deviceDataOffon.getFasheqi_id(),deviceDataOffon.getLouqi(),deviceDataOffon.getGaoya(),deviceDataOffon.getDiya(),deviceDataOffon.getGaowen(),deviceDataOffon.getDianchi(),deviceDataOffon.getFasheqidianchi(),deviceDataOffon.getZhongduan(),deviceDataOffon.getShilian(),deviceDataOffon.getWarn(),deviceDataOffon.getNo(),deviceDataOffon.getTyre_id(),uuid}));
				if(i==0){
					newsql.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				}else{
					newsql.append(",(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				}
			}
			preparedStatement = connection.prepareStatement(newsql.toString());
			for(int i=0;i<paramterList.size();i++){
				preparedStatement.setObject(i+1, paramterList.get(i));
			}
			num= preparedStatement.executeUpdate();
			if(num>0){
				logger.info("批量保存开关量成功！uuid="+uuid);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("批量保存开关量失败！uuid="+uuid+StringHelper.getTrace(e));
		}finally {
			try {
				if (preparedStatement != null) {
					pList.add(preparedStatement);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
		}
		return flag;
	}
	
	private boolean saveByListDeviceDataYaliWendu(List<DeviceDataYaliWendu> deviceDataYaliWendus,Date now,String uuid,Connection connection,List<ResultSet> rList,List<PreparedStatement> pList) {
		boolean flag = false;
		String sql ="insert into device_data_yali_wendu(create_time,fasheqi_id,yali,wendu,no,tyre_id,uuid) values";
		PreparedStatement preparedStatement = null;
		try {
			StringBuffer newsql = new StringBuffer();
			newsql.append(sql);
			Integer num=-1;
			List<Object> paramterList=new ArrayList<Object>();
			for(int i=0;i<deviceDataYaliWendus.size();i++){
				DeviceDataYaliWendu deviceDataOffon=deviceDataYaliWendus.get(i);
				paramterList.addAll(Arrays.asList(new Object[]{now,deviceDataOffon.getFasheqi_id(),deviceDataOffon.getYali(),deviceDataOffon.getWendu(),deviceDataOffon.getNo(),deviceDataOffon.getTyre_id(),uuid}));
				if(i==0){
					newsql.append("(?,?,?,?,?,?,?)");
				}else{
					newsql.append(",(?,?,?,?,?,?,?)");
				}
			}
			preparedStatement = connection.prepareStatement(newsql.toString());
			for(int i=0;i<paramterList.size();i++){
				preparedStatement.setObject(i+1, paramterList.get(i));
			}
			num= preparedStatement.executeUpdate();
			if(num>0){
				logger.info("保存压力温度成功！uuid="+uuid);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存压力温度失败！uuid="+uuid+StringHelper.getTrace(e));
		}finally {
			try {
				if (preparedStatement != null) {
					pList.add(preparedStatement);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
		}
		return flag;
	}
	
	//保存发射器
	private void saveFasheqi(Date now,HardwareElement element,Connection connection) {
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;
			try {
				int i;
				Map<String, DeviceFasheqi> oldmap=new HashMap<String, DeviceFasheqi>();
				for(i=0;i<element.getOldDeviceFasheqis().size();i++){
					oldmap.put(element.getOldDeviceFasheqis().get(i).getFasheqi_id(), element.getOldDeviceFasheqis().get(i));
				}
				Map<String, DeviceFasheqi> map=new HashMap<String, DeviceFasheqi>();
				DeviceFasheqi deviceFasheqi;
				String caiji_timesql="";
				for(i=0;i<element.getDeviceFasheqis().size();i++){
					deviceFasheqi=oldmap.get(element.getDeviceFasheqis().get(i).getFasheqi_id());
					if(deviceFasheqi!=null){
						if(deviceFasheqi.equalsValue(element.getDeviceFasheqis().get(i))){
							if("".equals(caiji_timesql)){
								caiji_timesql="'"+element.getDeviceFasheqis().get(i).getFasheqi_id()+"'";
							}else{
								caiji_timesql=caiji_timesql+",'"+element.getDeviceFasheqis().get(i).getFasheqi_id()+"'";
							}
							element.getDeviceFasheqis().get(i).setWendu(deviceFasheqi.getWendu());
							element.getDeviceFasheqis().get(i).setYali(deviceFasheqi.getYali());
						}else{
							preparedStatement = connection.prepareStatement(
									"UPDATE device_fasheqi SET caiji_time=?,louqi=?,gaoya=?,diya=?,gaowen=?,dianchi=?,fasheqidianchi=?,zhongduan=?,shilian=?,warn=?,yali=?,wendu=?,no=?,tyre_id=?,trucks_id=?,dtu_id=? WHERE fasheqi_id=?");
							preparedStatement.setObject(1, now);
							preparedStatement.setInt(2, element.getDeviceFasheqis().get(i).getLouqi());
							preparedStatement.setInt(3, element.getDeviceFasheqis().get(i).getGaoya());
							preparedStatement.setInt(4, element.getDeviceFasheqis().get(i).getDiya());
							preparedStatement.setInt(5, element.getDeviceFasheqis().get(i).getGaowen());
							preparedStatement.setInt(6, element.getDeviceFasheqis().get(i).getDianchi());
							preparedStatement.setInt(7, element.getDeviceFasheqis().get(i).getFasheqidianchi());
							preparedStatement.setInt(8, element.getDeviceFasheqis().get(i).getZhongduan());
							preparedStatement.setInt(9, element.getDeviceFasheqis().get(i).getShilian());
							preparedStatement.setInt(10, element.getDeviceFasheqis().get(i).getWarn());
							preparedStatement.setFloat(11, element.getDeviceFasheqis().get(i).getYali());
							preparedStatement.setFloat(12, element.getDeviceFasheqis().get(i).getWendu());
							preparedStatement.setInt(13, element.getDeviceFasheqis().get(i).getNo());
							preparedStatement.setString(14, element.getDeviceFasheqis().get(i).getTyre_id());
							preparedStatement.setString(15, element.getCarNum());
							preparedStatement.setString(16, element.getPhone());
							preparedStatement.setString(17, element.getDeviceFasheqis().get(i).getFasheqi_id());
							preparedStatement.executeUpdate();
							try {
								if(preparedStatement!=null && !preparedStatement.isClosed()){
									preparedStatement.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}else{
						preparedStatement = connection.prepareStatement(
								"SELECT id FROM device_fasheqi WHERE fasheqi_id=?");
						preparedStatement.setString(1, element.getDeviceFasheqis().get(i).getFasheqi_id());
						resultSet = preparedStatement.executeQuery();
						if(resultSet.next()){
							try {
								if (resultSet != null && !resultSet.isClosed()) {
									resultSet.close();
								}
								if(preparedStatement!=null && !preparedStatement.isClosed()){
									preparedStatement.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							preparedStatement = connection.prepareStatement(
									"UPDATE device_fasheqi SET caiji_time=?,louqi=?,gaoya=?,diya=?,gaowen=?,dianchi=?,fasheqidianchi=?,zhongduan=?,shilian=?,warn=?,yali=?,wendu=?,no=?,tyre_id=?,trucks_id=?,dtu_id=? WHERE fasheqi_id=?");
							preparedStatement.setObject(1, now);
							preparedStatement.setInt(2, element.getDeviceFasheqis().get(i).getLouqi());
							preparedStatement.setInt(3, element.getDeviceFasheqis().get(i).getGaoya());
							preparedStatement.setInt(4, element.getDeviceFasheqis().get(i).getDiya());
							preparedStatement.setInt(5, element.getDeviceFasheqis().get(i).getGaowen());
							preparedStatement.setInt(6, element.getDeviceFasheqis().get(i).getDianchi());
							preparedStatement.setInt(7, element.getDeviceFasheqis().get(i).getFasheqidianchi());
							preparedStatement.setInt(8, element.getDeviceFasheqis().get(i).getZhongduan());
							preparedStatement.setInt(9, element.getDeviceFasheqis().get(i).getShilian());
							preparedStatement.setInt(10, element.getDeviceFasheqis().get(i).getWarn());
							preparedStatement.setFloat(11, element.getDeviceFasheqis().get(i).getYali());
							preparedStatement.setFloat(12, element.getDeviceFasheqis().get(i).getWendu());
							preparedStatement.setInt(13, element.getDeviceFasheqis().get(i).getNo());
							preparedStatement.setString(14, element.getDeviceFasheqis().get(i).getTyre_id());
							preparedStatement.setString(15, element.getCarNum());
							preparedStatement.setString(16, element.getPhone());
							preparedStatement.setString(17, element.getDeviceFasheqis().get(i).getFasheqi_id());
							preparedStatement.executeUpdate();
							try {
								if(preparedStatement!=null && !preparedStatement.isClosed()){
									preparedStatement.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else{
							try {
								if (resultSet != null && !resultSet.isClosed()) {
									resultSet.close();
								}
								if(preparedStatement!=null && !preparedStatement.isClosed()){
									preparedStatement.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							preparedStatement = connection.prepareStatement(
									"insert into device_fasheqi(create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,no,tyre_id,trucks_id,li_cheng,dtu_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							preparedStatement.setObject(1, now);
							preparedStatement.setObject(2, now);
							preparedStatement.setString(3, element.getDeviceFasheqis().get(i).getFasheqi_id());
							preparedStatement.setInt(4, element.getDeviceFasheqis().get(i).getLouqi());
							preparedStatement.setInt(5, element.getDeviceFasheqis().get(i).getGaoya());
							preparedStatement.setInt(6, element.getDeviceFasheqis().get(i).getDiya());
							preparedStatement.setInt(7, element.getDeviceFasheqis().get(i).getGaowen());
							preparedStatement.setInt(8, element.getDeviceFasheqis().get(i).getDianchi());
							preparedStatement.setInt(9, element.getDeviceFasheqis().get(i).getFasheqidianchi());
							preparedStatement.setInt(10, element.getDeviceFasheqis().get(i).getZhongduan());
							preparedStatement.setInt(11, element.getDeviceFasheqis().get(i).getShilian());
							preparedStatement.setInt(12, element.getDeviceFasheqis().get(i).getWarn());
							preparedStatement.setFloat(13, element.getDeviceFasheqis().get(i).getYali());
							preparedStatement.setFloat(14, element.getDeviceFasheqis().get(i).getWendu());
							preparedStatement.setInt(15, element.getDeviceFasheqis().get(i).getNo());
							preparedStatement.setString(16, element.getDeviceFasheqis().get(i).getTyre_id());
							preparedStatement.setString(17, element.getCarNum());
							preparedStatement.setDouble(18, 0d);
							preparedStatement.setString(19, element.getPhone());
							preparedStatement.executeUpdate();
							try {
								if(preparedStatement!=null && !preparedStatement.isClosed()){
									preparedStatement.close();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					}
					map.put(element.getDeviceFasheqis().get(i).getFasheqi_id(), element.getDeviceFasheqis().get(i));
				}
				String tt="";
				for(i=0;i<element.getOldDeviceFasheqis().size();i++){
					if(map.get(element.getOldDeviceFasheqis().get(i).getFasheqi_id())==null){
						if(element.getPhone().equals(element.getOldDeviceFasheqis().get(i).getDtu_id())){
							if("".equals(tt)){
								tt="'"+element.getOldDeviceFasheqis().get(i).getFasheqi_id()+"'";
							}else{
								tt=tt+",'"+element.getOldDeviceFasheqis().get(i).getFasheqi_id()+"'";
							}
							
						}
					}
				}
				//更新dtu现在不存在发射器
				if(!"".equals(tt)){
					preparedStatement = connection.prepareStatement("UPDATE device_fasheqi SET tyre_id=null,trucks_id=null,dtu_id=null WHERE fasheqi_id in("+tt+")");
					preparedStatement.executeUpdate();
					try {
						if(preparedStatement!=null && !preparedStatement.isClosed()){
							preparedStatement.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				//更新采集时间
				if(!"".equals(caiji_timesql)){
					preparedStatement = connection.prepareStatement("UPDATE device_fasheqi SET caiji_time=? WHERE fasheqi_id in("+caiji_timesql+")");
					preparedStatement.setObject(1, now);
					preparedStatement.executeUpdate();
					try {
						if(preparedStatement!=null && !preparedStatement.isClosed()){
							preparedStatement.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				logger.info("保存发射器成功！dtu_id="+element.getPhone());
			} catch (Exception e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				logger.error("保存发射器失败！dtu_id="+element.getPhone()+StringHelper.getTrace(e));
			} finally {
				try {
					if (resultSet != null && !resultSet.isClosed()) {
						resultSet.close();
					}
					if(preparedStatement!=null && !preparedStatement.isClosed()){
						preparedStatement.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			return;
	}
	//保存车报警
	private void saveDtuWarnMessage(HardwareElement element,Date now,Connection connection,List<ResultSet> rList,List<PreparedStatement> pList){
		PreparedStatement preparedStatement4=null;
		PreparedStatement preparedStatement3=null;
		PreparedStatement preparedStatement5=null;
		ResultSet resultSet3=null;
		ResultSet resultSet4=null;
		try {
			if(element.getCarNum()==null){
				return;
			}
			int k=0;
			for(DeviceDataOffon offon_value:element.getDeviceDataOffons()){
				k=offon_value.getLouqi()|offon_value.getGaoya()
				|offon_value.getDiya()|offon_value.getGaowen()
				|offon_value.getDianchi()|offon_value.getFasheqidianchi();
				if(k==1){
					break;
				}
			}
			if(k==1){
				//保存消息 报警提示
				preparedStatement3 = connection.prepareStatement("SELECT U.user_id,U.true_name,U.user_company,U.user_company_id FROM `user` U LEFT JOIN trucks T ON T.company_id=U.user_company_id WHERE T.trucks_id=? AND U.user_role=1");
				preparedStatement3.setString(1, element.getCarNum());
				resultSet3=preparedStatement3.executeQuery();
				StringBuffer newsql = new StringBuffer();
				newsql.append("insert into message(create_time,content,senduser_id,user_id,type,remark) values");
				List<Object> paramterList=new ArrayList<Object>();
				boolean b=true;
				int userId = 0;
				String company="";
				int company_id = 0 ;
				while(resultSet3.next()){
					userId=resultSet3.getInt("user_id");
					company=resultSet3.getString("user_company");
					company_id=resultSet3.getInt("user_company_id");
					paramterList.addAll(Arrays.asList(new Object[]{now,"车报警",null,userId,4,element.getCarNum()}));
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
				
				StringBuffer sendMsg=new StringBuffer();
				int flag=0;
				for(DeviceDataOffon offon_value:element.getDeviceDataOffons()){//推送警告信息
					flag=offon_value.getLouqi()|offon_value.getGaoya()
					|offon_value.getDiya()|offon_value.getGaowen()
					|offon_value.getDianchi()|offon_value.getFasheqidianchi();
					
					if (flag==1) {
						sendMsg.append("\n");
						String where=TrucksStyleUtil.TyreWhereByFasheqiNo(element.getCarStyle(), offon_value.getNo());
						sendMsg.append(where+"位置轮胎报警:");
						if(offon_value.getLouqi()==1){
							sendMsg.append("漏气;");
						}
						if (offon_value.getGaoya()==1) {
							sendMsg.append("高压;");
						}
						if (offon_value.getDiya()==1) {
							sendMsg.append("低压;");
						}
						if (offon_value.getGaowen()==1) {
							sendMsg.append("高温;");
						}
						if (offon_value.getDianchi()==1) {
							sendMsg.append("设备低电量;");
						}
						if (offon_value.getFasheqidianchi()==1) {
							sendMsg.append("发射器低电量;");
						}
					}
				}
				if (StringUtils.isNotEmpty(sendMsg.toString())) {
					preparedStatement5=connection.prepareStatement("insert into warn_msg(dtu_id,create_time,warn_msg,status,trucks_id) values(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
					preparedStatement5.setString(1, element.getPhone());
					preparedStatement5.setObject(2, now);
					preparedStatement5.setString(3, sendMsg.toString().startsWith("\n")?sendMsg.substring(sendMsg.toString().indexOf("\n")+1):sendMsg.toString());
					preparedStatement5.setInt(4, 0);//未读
					preparedStatement5.setString(5, element.getCarNum());
					preparedStatement5.executeUpdate();
					
					resultSet4=preparedStatement5.getGeneratedKeys();
					
					if(resultSet4.next()) {
		            	int msgId = resultSet4.getInt(1); 
		            	
		            	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Message message = new Message(element.getPhone(), company+","+element.getCarNum()+",车报警", null, userId, 0, now, element.getCarNum(), "警告信息",company,msgId);
						//司机端推送
						JPushUtil.pushMsg2AndroidAndIOS("6ccac899e5a2d5c2a1627839", "c9f193b9fc487fa23712bb76", "警告信息", format.format(now)+"\n"+company+","+element.getCarNum()+",车报警:"+"\n"+sendMsg.toString(), "warn", JSON.toJSONString(message),element.getCarNum());
						//云管家端推送
						JPushUtil.pushMsg2AndroidAndIOS("8eab9dfddb197e208e82842d", "d0837f03d143643ed73eac13", "警告信息", format.format(now)+"\n"+company+","+element.getCarNum()+",车报警:"+"\n"+sendMsg.toString(), "warn", JSON.toJSONString(message),company_id+"");
		            }
					
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (preparedStatement3 != null) {
					pList.add(preparedStatement3);
				}
				if (preparedStatement4 != null) {
					pList.add(preparedStatement4);
				}
				if (preparedStatement5 != null) {
					pList.add(preparedStatement5);
				}
				if (resultSet3 != null) {
					rList.add(resultSet3);
				}
				if (resultSet4 != null) {
					rList.add(resultSet4);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}	
		}
	}
	
	//推送信息测试
	public  void test(TrucksByAdminVO trucks) {
		HardwareElement element = new HardwareElement();
		element.setCarNum(trucks.getTrucks_id());
		element.setCarStyle(trucks.getTrucks_style());
		element.setPhone(trucks.getDtu_id());
		List<DeviceDataOffon> offons = new ArrayList<DeviceDataOffon>();
		DeviceDataOffon offon1 = new DeviceDataOffon();
		offon1.setLouqi(1);
		offon1.setGaoya(1);
		offon1.setDiya(0);
		offon1.setGaowen(1);
		offon1.setDianchi(1);
		offon1.setFasheqidianchi(1);
		offon1.setNo(1);
		
		DeviceDataOffon offon2 = new DeviceDataOffon();
		offon2.setLouqi(1);
		offon2.setGaoya(0);
		offon2.setDiya(1);
		offon2.setGaowen(1);
		offon2.setDianchi(1);
		offon2.setFasheqidianchi(1);
		offon2.setNo(2);
		
		DeviceDataOffon offon3 = new DeviceDataOffon();
		offon3.setLouqi(0);
		offon3.setGaoya(0);
		offon3.setDiya(0);
		offon3.setGaowen(0);
		offon3.setDianchi(0);
		offon3.setFasheqidianchi(0);
		offon3.setNo(3);
		
		offons.add(offon1);
		offons.add(offon2);
		offons.add(offon3);
		element.setDeviceDataOffons(offons);
		
		Connection connection = null;
		List<ResultSet> rList=new ArrayList<ResultSet>();
		List<PreparedStatement> pList=new ArrayList<PreparedStatement>();
		try {
			connection = ConnectionPool.getConnection();
			connection.setAutoCommit(false);
			Date now=new Date();
			saveDtuWarnMessage(element, now, connection, rList, pList);
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
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
		
	}
	@Override
	public int readWarnMsg(int id) {
		Connection connection=null;
		PreparedStatement pst1=null;
		PreparedStatement pst2=null;
		ResultSet resultSet=null;
		try {
			connection=ConnectionPool.getConnection();
			pst1=connection.prepareStatement("select id,status from warn_msg where id=?");
			pst1.setInt(1, id);
			resultSet=pst1.executeQuery();
			if (!resultSet.next()) {
				return 1;//没有对应的报警信息
			}
			if (resultSet.getInt("status")==1) {//已读
				return 2;//报警信息已读
			}
			pst2=connection.prepareStatement("update warn_msg set status=? where id=?");
			pst2.setInt(1, 1);//已读
			pst2.setInt(2, id);
			int result=pst2.executeUpdate();
			if (result>0) {
				return 3;//更新为已读成功
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (pst1 != null && !pst1.isClosed()) {
					pst1.close();
				}
				if (pst2 != null && !pst2.isClosed()) {
					pst2.close();
				}
				if (resultSet != null && !resultSet.isClosed()) {
					resultSet.close();
				}
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	
	public static void main(String[] args) {
		String sendMsg="A2位置轮胎报警:漏气;高压;高温;设备低电量;发射器低电量;\nA1位置轮胎报警:漏气;低压;高温;设备低电量;发射器低电量;";
		System.out.println(sendMsg.startsWith("\n")?sendMsg.substring(sendMsg.indexOf("\n")+1):sendMsg);
	}
}
