package com.kkbc.dao.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.kkbc.dao.DeviceLoginDao;
import com.kkbc.entity.Device;
import com.kkbc.entity.DeviceDataBase;
import com.kkbc.entity.DeviceDataDingwei;
import com.kkbc.entity.DeviceDataHistory;
import com.kkbc.entity.DeviceDataOffon;
import com.kkbc.entity.DeviceDataYaliWendu;
import com.kkbc.entity.DeviceFasheqi;
import com.kkbc.entity.DeviceLoginLog;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.vo.DeviceTrucksVo;
import com.psylife.util.Constants;
import com.psylife.util.StringHelper;
 
public class DeviceLoginDaoImpl extends BaseDaoImpl implements DeviceLoginDao{	
	
	@Override
	@Transactional
    public boolean login(final HardwareElement element){
//		Connection connection = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		PreparedStatement preparedStatement1 = null;
//		ResultSet resultSet1 = null;
//		PreparedStatement preparedStatement2 = null;
//		ResultSet resultSet2 = null;
//		PreparedStatement preparedStatement3 = null;
//		ResultSet resultSet3 = null;
//		PreparedStatement preparedStatement4 = null;
//		ResultSet resultSet4 = null;
//		PreparedStatement preparedStatement5 = null;
//		ResultSet resultSet5 = null;
//		PreparedStatement preparedStatement6 = null;
//		ResultSet resultSet6 = null;
//		PreparedStatement preparedStatement7 = null;
//		ResultSet resultSet7 = null;
//		PreparedStatement preparedStatement8 = null;
		boolean flag=true;
		try {
//			connection = ConnectionPool.getConnection();
//			connection.setAutoCommit(false);
			
			//历史记录数据
//			preparedStatement = connection.prepareStatement(
//			"SELECT id,dtu_id,create_time,float_caiji_time,string_caiji_time,offon_caiji_time,base_id,dingwei_id,offon_uuid,yaliwendu_uuid,trucks_id,warn "+
//"FROM device_data_history WHERE dtu_id=? ORDER BY id DESC LIMIT 1");
//			preparedStatement.setString(1, element.getPhone());
//			resultSet = preparedStatement.executeQuery();
//			DeviceDataHistory deviceDataHistory=ResultSetUtil.getByOne(resultSet, "id,dtu_id,create_time,float_caiji_time,string_caiji_time,offon_caiji_time,base_id,dingwei_id,offon_uuid,yaliwendu_uuid,trucks_id,warn".split(","), 
//					"id,dtu_id,create_time,float_caiji_time,string_caiji_time,offon_caiji_time,base_id,dingwei_id,offon_uuid,yaliwendu_uuid,trucks_id,warn".split(","), DeviceDataHistory.class, false);
			
			DeviceDataHistory deviceDataHistory=(DeviceDataHistory) getSqlMapClientTemplate().queryForObject("DeviceDataHistory.queryByDtuid", element.getPhone());
			if(deviceDataHistory!=null){
				element.setDeviceDataHistory(deviceDataHistory);
				//设备数据基础
				if(deviceDataHistory.getBase_id()!=null){
//					preparedStatement1 = connection.prepareStatement(
//							"SELECT id,dtu_trucks_id,dtu_id,create_time,dtu_tpms_status,dtu_status,tpms_pinlu,yali_set_low_zhou_1,yali_set_low_zhou_2,yali_set_low_zhou_3,yali_set_low_zhou_4,yali_set_high_zhou_1,"
//							+ "yali_set_high_zhou_2,yali_set_high_zhou_3,yali_set_high_zhou_4,gao_wen_bao_jing_set,yuliu1_phone,yuliu2_phone,yuliu3_phone,yuliu4_phone,trucks_id,dtu_no"+
//		" FROM device_data_base WHERE id=?");
//					preparedStatement1.setLong(1, deviceDataHistory.getBase_id());
//					resultSet1 = preparedStatement1.executeQuery();
//					DeviceDataBase deviceDataBase=ResultSetUtil.getByOne(resultSet1, "id,dtu_trucks_id,dtu_id,create_time,dtu_tpms_status,dtu_status,tpms_pinlu,yali_set_low_zhou_1,yali_set_low_zhou_2,yali_set_low_zhou_3,yali_set_low_zhou_4,yali_set_high_zhou_1,yali_set_high_zhou_2,yali_set_high_zhou_3,yali_set_high_zhou_4,gao_wen_bao_jing_set,yuliu1_phone,yuliu2_phone,yuliu3_phone,yuliu4_phone,trucks_id,dtu_no".split(","), 
//							"id,dtu_trucks_id,dtu_id,create_time,dtu_tpms_status,dtu_status,tpms_pinlu,yali_set_low_zhou_1,yali_set_low_zhou_2,yali_set_low_zhou_3,yali_set_low_zhou_4,yali_set_high_zhou_1,yali_set_high_zhou_2,yali_set_high_zhou_3,yali_set_high_zhou_4,gao_wen_bao_jing_set,yuliu1_phone,yuliu2_phone,yuliu3_phone,yuliu4_phone,trucks_id,dtu_no".split(","), DeviceDataBase.class, false);
					
					DeviceDataBase deviceDataBase=(DeviceDataBase) getSqlMapClientTemplate().queryForObject("DeviceDataBase.queryById", deviceDataHistory.getBase_id());
					
					if(deviceDataBase!=null){
				    	element.setOldDeviceDataBase(deviceDataBase);
				    }
				}
				
				//dtu设备数据定位信息
				if(deviceDataHistory.getDingwei_id()!=null){
//					preparedStatement2 = connection.prepareStatement(
//							"SELECT id,dtu_id,create_time,gps_status,latitude,latitude_type,longitude,longitude_type,dimian_sulu,dimian_hangxiang,trucks_id "+
//		 "FROM device_data_dingwei WHERE id=?");
//					preparedStatement2.setLong(1, deviceDataHistory.getDingwei_id());
//					resultSet2 = preparedStatement2.executeQuery();
//					DeviceDataDingwei deviceDataDingwei=ResultSetUtil.getByOne(resultSet2, "id,dtu_id,create_time,gps_status,latitude,latitude_type,longitude,longitude_type,dimian_sulu,dimian_hangxiang,trucks_id".split(","), 
//							"id,dtu_id,create_time,gps_status,latitude,latitude_type,longitude,longitude_type,dimian_sulu,dimian_hangxiang,trucks_id".split(","), DeviceDataDingwei.class, false);
					
					DeviceDataDingwei deviceDataDingwei=(DeviceDataDingwei) getSqlMapClientTemplate().queryForObject("DeviceDataDingwei.queryById", deviceDataHistory.getDingwei_id());
					if(deviceDataDingwei!=null){
						element.setOldDeviceDataDingwei(deviceDataDingwei);
					}
				}
				
				//dtu设备数据开关量
				if(deviceDataHistory.getOffon_uuid()!=null){
//					preparedStatement3 = connection.prepareStatement(
//							"SELECT id,create_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,no,tyre_id,uuid "+
//		 "FROM device_data_offon WHERE uuid=?");
//					preparedStatement3.setString(1, deviceDataHistory.getOffon_uuid());
//					resultSet3 = preparedStatement3.executeQuery();
					@SuppressWarnings("unchecked")
					List<DeviceDataOffon> deviceDataOffons=getSqlMapClientTemplate().queryForList("DeviceDataOffon.queryByUuid", deviceDataHistory.getOffon_uuid());
					if(deviceDataOffons!=null){
						sortByDeviceDataOffon(deviceDataOffons);
						element.setOldDeviceDataOffons(deviceDataOffons);
					}
				}
				//dtu设备数据压力温度
				if(deviceDataHistory.getYaliwendu_uuid()!=null){
//					preparedStatement4 = connection.prepareStatement(
//							"SELECT id,create_time,fasheqi_id,yali,wendu,no,tyre_id,uuid "+
//		 "FROM device_data_yali_wendu WHERE uuid=?");
//					preparedStatement4.setString(1, deviceDataHistory.getYaliwendu_uuid());
//					resultSet4 = preparedStatement4.executeQuery();
					
					@SuppressWarnings("unchecked")
					List<DeviceDataYaliWendu> deviceDataYaliWendus=getSqlMapClientTemplate().queryForList("DeviceDataYaliWendu.queryByUuid", deviceDataHistory.getYaliwendu_uuid());
					if(deviceDataYaliWendus!=null){
						sortByDeviceDataYaliWendu(deviceDataYaliWendus);
						element.setOldDeviceDataYaliWendus(deviceDataYaliWendus);
					}
				}
			}
			
			//设备发射器
//			preparedStatement5 = connection.prepareStatement(
//					"SELECT id,create_time,caiji_time,fasheqi_id,louqi,gaoya,diya,gaowen,dianchi,fasheqidianchi,zhongduan,shilian,warn,yali,wendu,no,tyre_id,trucks_id,li_cheng,dtu_id "+
// "FROM device_fasheqi WHERE dtu_id=?");
//			preparedStatement5.setString(1, element.getPhone());
//			resultSet5 = preparedStatement5.executeQuery();
			@SuppressWarnings("unchecked")
			List<DeviceFasheqi> deviceFasheqis=getSqlMapClientTemplate().queryForList("DeviceFasheqi.queryByDtu", element.getPhone());
			if(deviceFasheqis!=null){
				sortByDeviceFasheqi(deviceFasheqis);
				element.setOldDeviceFasheqis(deviceFasheqis);
			}
		
			
//			preparedStatement6 = connection.prepareStatement("SELECT TD.id,TD.trucks_id,TD.li_cheng,TD.dtu_id,T.trucks_style"
//					+ ",TD.dtu_tpms_status,TD.dtu_status,TD.tpms_pinlu,"
//					+ "TD.yali_set_low_zhou_1,TD.yali_set_low_zhou_2,TD.yali_set_low_zhou_3,TD.yali_set_low_zhou_4,TD.yali_set_high_zhou_1,TD.yali_set_high_zhou_2,TD.yali_set_high_zhou_3,TD.yali_set_high_zhou_4,"
//					+ "TD.gao_wen_bao_jing_set,TD.yuliu1_phone,TD.yuliu2_phone,TD.yuliu3_phone,TD.yuliu4_phone,TD.gps_status,TD.latitude,TD.latitude_type,TD.longitude,TD.longitude_type,TD.dimian_sulu,TD.dimian_hangxiang,TD.company_id"
//					+ ",T.trucks_A1,T.trucks_A2,T.trucks_A3,T.trucks_A4,T.trucks_A5,T.trucks_A6,T.trucks_B1,T.trucks_B2,T.trucks_B3,T.trucks_B4,T.trucks_B5,T.trucks_B6,T.trucks_B7,T.trucks_B8,T.trucks_C1,T.trucks_C2,T.trucks_C3,T.trucks_C4,T.trucks_C5,T.trucks_C6,T.trucks_C7,T.trucks_C8,T.trucks_C9,T.trucks_C10,T.trucks_C11,T.trucks_C12,T.trucks_C13,T.trucks_C14,T.trucks_C15,T.trucks_C16"
//					+ " FROM device TD LEFT JOIN trucks T ON T.trucks_id=TD.trucks_id  WHERE TD.dtu_id=? LIMIT 1");
//			preparedStatement6.setString(1, element.getPhone());
//			resultSet6 = preparedStatement6.executeQuery();
			
			DeviceTrucksVo deviceTrucksVo=(DeviceTrucksVo) getSqlMapClientTemplate().queryForObject("DeviceTrucksVo.queryByDtuId", element.getPhone());
			Date now=new Date();
			Device device=new Device();
			if(deviceTrucksVo!=null){
				element.setCarDeviceId(deviceTrucksVo.getId());
				element.setCarNum(deviceTrucksVo.getTrucks_id());
				element.setLiCheng(deviceTrucksVo.getLi_cheng());
				element.setCarStyle(deviceTrucksVo.getTrucks_style());
				if(element.getLiCheng()<0){
					element.setLiCheng(0);
				}
				
				device.setId(deviceTrucksVo.getId());
				device.setTrucks_id(deviceTrucksVo.getTrucks_id());
				device.setDtu_id(element.getPhone());
				device.setDtu_tpms_status(deviceTrucksVo.getDtu_tpms_status());
				device.setDtu_status(deviceTrucksVo.getDtu_status());
				device.setTpms_pinlu(deviceTrucksVo.getTpms_pinlu());
				device.setYali_set_low_zhou_1(deviceTrucksVo.getYali_set_low_zhou_1());
				device.setYali_set_low_zhou_2(deviceTrucksVo.getYali_set_low_zhou_2());
				device.setYali_set_low_zhou_3(deviceTrucksVo.getYali_set_low_zhou_3());
				device.setYali_set_low_zhou_4(deviceTrucksVo.getYali_set_low_zhou_4());
				
				device.setYali_set_high_zhou_1(deviceTrucksVo.getYali_set_high_zhou_1());
				device.setYali_set_high_zhou_2(deviceTrucksVo.getYali_set_high_zhou_2());
				device.setYali_set_high_zhou_3(deviceTrucksVo.getYali_set_high_zhou_3());
				device.setYali_set_high_zhou_4(deviceTrucksVo.getYali_set_high_zhou_4());
				
				device.setGao_wen_bao_jing_set(deviceTrucksVo.getGao_wen_bao_jing_set());
				
				device.setYuliu1_phone(deviceTrucksVo.getYuliu1_phone());
				device.setYuliu2_phone(deviceTrucksVo.getYuliu2_phone());
				device.setYuliu3_phone(deviceTrucksVo.getYuliu3_phone());
				device.setYuliu4_phone(deviceTrucksVo.getYuliu4_phone());
				
				device.setGps_status(deviceTrucksVo.getGps_status());
				device.setLatitude(deviceTrucksVo.getLatitude());
				device.setLatitude_type(deviceTrucksVo.getLatitude_type());
				device.setLongitude(deviceTrucksVo.getLongitude());
				device.setLongitude_type(deviceTrucksVo.getLongitude_type());
				
				device.setDimian_sulu(deviceTrucksVo.getDimian_sulu());
				device.setDimian_hangxiang(deviceTrucksVo.getDimian_hangxiang());
				device.setLi_cheng(element.getLiCheng());
				
				element.setOldDevice(device);
				
				Map<String, String> map=element.getMapPositionTyreId();
				if(deviceTrucksVo.getTrucks_A1()!=null&&!"".equals(deviceTrucksVo.getTrucks_A1())){
					map.put("A1",deviceTrucksVo.getTrucks_A1());
				}
				if(deviceTrucksVo.getTrucks_A2()!=null&&!"".equals(deviceTrucksVo.getTrucks_A2())){
					map.put("A2",deviceTrucksVo.getTrucks_A2());
				}
				if(deviceTrucksVo.getTrucks_A3()!=null&&!"".equals(deviceTrucksVo.getTrucks_A3())){
					map.put("A3",deviceTrucksVo.getTrucks_A3());
				}
				if(deviceTrucksVo.getTrucks_A4()!=null&&!"".equals(deviceTrucksVo.getTrucks_A4())){
					map.put("A4",deviceTrucksVo.getTrucks_A4());
				}
				if(deviceTrucksVo.getTrucks_A5()!=null&&!"".equals(deviceTrucksVo.getTrucks_A5())){
					map.put("A5",deviceTrucksVo.getTrucks_A5());
				}
				if(deviceTrucksVo.getTrucks_A6()!=null&&!"".equals(deviceTrucksVo.getTrucks_A6())){
					map.put("A6",deviceTrucksVo.getTrucks_A6());
				}
				
				if(deviceTrucksVo.getTrucks_B1()!=null&&!"".equals(deviceTrucksVo.getTrucks_B1())){
					map.put("B1",deviceTrucksVo.getTrucks_B1());
				}
				if(deviceTrucksVo.getTrucks_B2()!=null&&!"".equals(deviceTrucksVo.getTrucks_B2())){
					map.put("B2",deviceTrucksVo.getTrucks_B2());
				}
				if(deviceTrucksVo.getTrucks_B3()!=null&&!"".equals(deviceTrucksVo.getTrucks_B3())){
					map.put("B3",deviceTrucksVo.getTrucks_B3());
				}
				if(deviceTrucksVo.getTrucks_B4()!=null&&!"".equals(deviceTrucksVo.getTrucks_B4())){
					map.put("B4",deviceTrucksVo.getTrucks_B4());
				}
				if(deviceTrucksVo.getTrucks_B5()!=null&&!"".equals(deviceTrucksVo.getTrucks_B5())){
					map.put("B5",deviceTrucksVo.getTrucks_B5());
				}
				if(deviceTrucksVo.getTrucks_B6()!=null&&!"".equals(deviceTrucksVo.getTrucks_B6())){
					map.put("B6",deviceTrucksVo.getTrucks_B6());
				}
				if(deviceTrucksVo.getTrucks_B7()!=null&&!"".equals(deviceTrucksVo.getTrucks_B7())){
					map.put("B7",deviceTrucksVo.getTrucks_B7());
				}
				if(deviceTrucksVo.getTrucks_B8()!=null&&!"".equals(deviceTrucksVo.getTrucks_B8())){
					map.put("B8",deviceTrucksVo.getTrucks_B8());
				}
				
				if(deviceTrucksVo.getTrucks_C1()!=null&&!"".equals(deviceTrucksVo.getTrucks_C1())){
					map.put("C1",deviceTrucksVo.getTrucks_C1());
				}
				if(deviceTrucksVo.getTrucks_C2()!=null&&!"".equals(deviceTrucksVo.getTrucks_C2())){
					map.put("C2",deviceTrucksVo.getTrucks_C2());
				}
				if(deviceTrucksVo.getTrucks_C3()!=null&&!"".equals(deviceTrucksVo.getTrucks_C3())){
					map.put("C3",deviceTrucksVo.getTrucks_C3());
				}
				if(deviceTrucksVo.getTrucks_C4()!=null&&!"".equals(deviceTrucksVo.getTrucks_C4())){
					map.put("C4",deviceTrucksVo.getTrucks_C4());
				}
				if(deviceTrucksVo.getTrucks_C5()!=null&&!"".equals(deviceTrucksVo.getTrucks_C5())){
					map.put("C5",deviceTrucksVo.getTrucks_C5());
				}
				if(deviceTrucksVo.getTrucks_C6()!=null&&!"".equals(deviceTrucksVo.getTrucks_C6())){
					map.put("C6",deviceTrucksVo.getTrucks_C6());
				}
				if(deviceTrucksVo.getTrucks_C7()!=null&&!"".equals(deviceTrucksVo.getTrucks_C7())){
					map.put("C7",deviceTrucksVo.getTrucks_C7());
				}
				if(deviceTrucksVo.getTrucks_C8()!=null&&!"".equals(deviceTrucksVo.getTrucks_C8())){
					map.put("C8",deviceTrucksVo.getTrucks_C8());
				}
				if(deviceTrucksVo.getTrucks_C9()!=null&&!"".equals(deviceTrucksVo.getTrucks_C9())){
					map.put("C9",deviceTrucksVo.getTrucks_C9());
				}
				if(deviceTrucksVo.getTrucks_C10()!=null&&!"".equals(deviceTrucksVo.getTrucks_C10())){
					map.put("C10",deviceTrucksVo.getTrucks_C10());
				}
				if(deviceTrucksVo.getTrucks_C11()!=null&&!"".equals(deviceTrucksVo.getTrucks_C11())){
					map.put("C11",deviceTrucksVo.getTrucks_C11());
				}
				if(deviceTrucksVo.getTrucks_C12()!=null&&!"".equals(deviceTrucksVo.getTrucks_C12())){
					map.put("C12",deviceTrucksVo.getTrucks_C12());
				}
				if(deviceTrucksVo.getTrucks_C13()!=null&&!"".equals(deviceTrucksVo.getTrucks_C13())){
					map.put("C13",deviceTrucksVo.getTrucks_C13());
				}
				if(deviceTrucksVo.getTrucks_C14()!=null&&!"".equals(deviceTrucksVo.getTrucks_C14())){
					map.put("C14",deviceTrucksVo.getTrucks_C14());
				}
				if(deviceTrucksVo.getTrucks_C15()!=null&&!"".equals(deviceTrucksVo.getTrucks_C15())){
					map.put("C15",deviceTrucksVo.getTrucks_C15());
				}
				if(deviceTrucksVo.getTrucks_C16()!=null&&!"".equals(deviceTrucksVo.getTrucks_C16())){
					map.put("C16",deviceTrucksVo.getTrucks_C16());
				}
				
			}else{
				element.setCarNum(null);
//				preparedStatement7 = connection.prepareStatement(
//						"insert into device(dtu_id,create_time,update_time,li_cheng) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
//				preparedStatement7.setString(1, element.getPhone());
//				preparedStatement7.setObject(2, now);
//				preparedStatement7.setObject(3, now);
//				preparedStatement7.setDouble(4, 0d);
//				preparedStatement7.executeUpdate();
//				resultSet7 = preparedStatement7.getGeneratedKeys(); 
				Device param=new Device();
				param.setDtu_id(element.getPhone());
				param.setCreate_time(now);
				param.setUpdate_time(now);
				param.setLi_cheng(0d);
//				int a=getJdbcTemplate().update("insert into device(dtu_id,create_time,update_time,li_cheng) values(?,?,?,?)", new Object[]{element.getPhone(),now,now,0d},keyHolder);
				Long a=(Long) getSqlMapClientTemplate().insert("Device.saveInfo", param);
				if (a>0) { 
                	element.setCarDeviceId(a);
				     device.setId(element.getCarDeviceId());
					 device.setDtu_id(element.getPhone());
					 element.setOldDevice(device);
				}else{
					flag=false;
				} 
			}
			if(flag){
//				preparedStatement8=connection.prepareStatement(
//						"insert into device_login_log(create_time,status,remote_ip,remote_port,type,dtu_id) values(?,?,?,?,?,?)");
//				preparedStatement8.setObject(1, now);
//				preparedStatement8.setObject(2, Constants.STATUS_NORMAL);
//				preparedStatement8.setObject(3, element.getUdpAddress().getAddress().getHostAddress());
//				preparedStatement8.setObject(4, element.getUdpAddress().getPort());
//				preparedStatement8.setObject(5, DeviceLoginLog.TYPE_LOGIN);
//				preparedStatement8.setObject(6, element.getPhone());
//				preparedStatement8.executeUpdate();
				DeviceLoginLog logParam=new DeviceLoginLog();
				logParam.setCreate_time(now);
				logParam.setStatus(Constants.STATUS_NORMAL);
				logParam.setRemote_ip(element.getUdpAddress().getAddress().getHostAddress());
				logParam.setRemote_port(element.getUdpAddress().getPort());
				logParam.setType(DeviceLoginLog.TYPE_LOGIN);
				logParam.setDtu_id(element.getPhone());
				
				getSqlMapClientTemplate().insert("DeviceLoginLog.saveInfo", logParam);
//				connection.commit();
				logger.info("登录成功Dtu id="+element.getPhone());
			}else{
//				try {
//					connection.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
				logger.info("登录失败Dtu id="+element.getPhone());
			}
					
		} catch (Exception e) {
			flag=false;
//			try {
//				connection.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
			logger.error("登录Dtu id="+element.getPhone()+StringHelper.getTrace(e));
		}finally {
//			try {
//				if (resultSet != null && !resultSet.isClosed()) {
//					resultSet.close();
//				}
//				if (resultSet1 != null && !resultSet1.isClosed()) {
//					resultSet1.close();
//				}
//				if (resultSet2 != null && !resultSet2.isClosed()) {
//					resultSet2.close();
//				}
//				if (resultSet3 != null && !resultSet3.isClosed()) {
//					resultSet3.close();
//				}
//				if (resultSet4 != null && !resultSet4.isClosed()) {
//					resultSet4.close();
//				}
//				if (resultSet5 != null && !resultSet5.isClosed()) {
//					resultSet5.close();
//				}
//				if (resultSet6 != null && !resultSet6.isClosed()) {
//					resultSet6.close();
//				}
//				if (resultSet7 != null && !resultSet7.isClosed()) {
//					resultSet7.close();
//				}
//				if(preparedStatement!=null && !preparedStatement.isClosed()){
//					preparedStatement.close();
//				}
//				if(preparedStatement1!=null && !preparedStatement1.isClosed()){
//					preparedStatement1.close();
//				}
//				if(preparedStatement2!=null && !preparedStatement2.isClosed()){
//					preparedStatement2.close();
//				}
//				if(preparedStatement3!=null && !preparedStatement3.isClosed()){
//					preparedStatement3.close();
//				}
//				if(preparedStatement4!=null && !preparedStatement4.isClosed()){
//					preparedStatement4.close();
//				}
//				if(preparedStatement5!=null && !preparedStatement5.isClosed()){
//					preparedStatement5.close();
//				}
//				if(preparedStatement6!=null && !preparedStatement6.isClosed()){
//					preparedStatement6.close();
//				}
//				if(preparedStatement7!=null && !preparedStatement7.isClosed()){
//					preparedStatement7.close();
//				}
//				if(preparedStatement8!=null && !preparedStatement8.isClosed()){
//					preparedStatement8.close();
//				}
//			} catch (Exception e2) {
//				e2.printStackTrace();
//			}			
//			ConnectionPool.close(connection);
		}
		return flag;
	}
    
    /**
	 * 从小到大排序
	 * @param list
	 */
	private static void sortByDeviceDataOffon(List<DeviceDataOffon> list){
		if(list==null||list.size()==0){
			return;
		}
		Collections.sort(list, new Comparator<DeviceDataOffon>() {
			public int compare(DeviceDataOffon obj1, DeviceDataOffon obj2) {
				if(obj1!=null&&obj1.getNo()!=null&&obj2!=null&&obj2.getNo()!=null){
					int t=obj1.getNo().intValue()-obj2.getNo().intValue();
					if(t>0){
						return 1;
					}else if(t==0){
						return 0;
					}else{
						return -1;
					}
				}else if(obj1!=null&&obj2!=null){
					if(obj1.getNo()==null&&obj2.getNo()!=null){
						return -1;
					}else if(obj1.getNo()==null&&obj2.getNo()==null){
						return 0;
					}else if(obj1.getNo()!=null){
						return 1;
					}
				}
				else if(obj1==null&&obj2!=null){
					return -1;
				}else if(obj1==null&&obj2==null){
					return 0;
				}else if(obj1!=null){
					return 1;
				}
				return 0;
			};
		});
	}
	
	/**
	 * 从小到大排序
	 * @param list
	 */
	private static void sortByDeviceDataYaliWendu(List<DeviceDataYaliWendu> list){
		if(list==null||list.size()==0){
			return;
		}
		Collections.sort(list, new Comparator<DeviceDataYaliWendu>() {
			public int compare(DeviceDataYaliWendu obj1, DeviceDataYaliWendu obj2) {
				if(obj1!=null&&obj1.getNo()!=null&&obj2!=null&&obj2.getNo()!=null){
					int t=obj1.getNo().intValue()-obj2.getNo().intValue();
					if(t>0){
						return 1;
					}else if(t==0){
						return 0;
					}else{
						return -1;
					}
				}else if(obj1!=null&&obj2!=null){
					if(obj1.getNo()==null&&obj2.getNo()!=null){
						return -1;
					}else if(obj1.getNo()==null&&obj2.getNo()==null){
						return 0;
					}else if(obj1.getNo()!=null){
						return 1;
					}
				}
				else if(obj1==null&&obj2!=null){
					return -1;
				}else if(obj1==null&&obj2==null){
					return 0;
				}else if(obj1!=null){
					return 1;
				}
				return 0;
			};
		});
	}
	
	/**
	 * 从小到大排序
	 * @param list
	 */
	private static void sortByDeviceFasheqi(List<DeviceFasheqi> list){
		if(list==null||list.size()==0){
			return;
		}
		Collections.sort(list, new Comparator<DeviceFasheqi>() {
			public int compare(DeviceFasheqi obj1, DeviceFasheqi obj2) {
				if(obj1!=null&&obj1.getNo()!=null&&obj2!=null&&obj2.getNo()!=null){
					int t=obj1.getNo().intValue()-obj2.getNo().intValue();
					if(t>0){
						return 1;
					}else if(t==0){
						return 0;
					}else{
						return -1;
					}
				}else if(obj1!=null&&obj2!=null){
					if(obj1.getNo()==null&&obj2.getNo()!=null){
						return -1;
					}else if(obj1.getNo()==null&&obj2.getNo()==null){
						return 0;
					}else if(obj1.getNo()!=null){
						return 1;
					}
				}
				else if(obj1==null&&obj2!=null){
					return -1;
				}else if(obj1==null&&obj2==null){
					return 0;
				}else if(obj1!=null){
					return 1;
				}
				return 0;
			};
		});
	}
}
