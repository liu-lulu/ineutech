package com.kkbc.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kkbc.dao.TrucksDao;
import com.kkbc.dao.TrucksDeviceDao;
import com.kkbc.entity.Device;
import com.kkbc.entity.DeviceDataBase;
import com.kkbc.entity.DeviceDataDingwei;
import com.kkbc.entity.DeviceDataOffon;
import com.kkbc.entity.DeviceDataYaliWendu;
import com.kkbc.entity.DeviceFasheqi;
import com.kkbc.entity.Trucks;
import com.kkbc.entity.User;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.vo.DeviceTrucksVo;
import com.kkbc.vo.ExtEntity;
import com.kkbc.vo.MessageVO;
import com.kkbc.vo.dtu.TyreFloatWenduYaliVO;
import com.kkbc.vo.dtu.TyreOffonVO;
import com.kkbc.vo.dtu.TyreStringVO;
import com.psylife.util.LonLatUtil;
import com.psylife.util.StringHelper;
import com.psylife.util.TrucksStyleUtil;
import com.psylife.util.push.Message;

@Repository
public class TrucksDeviceDaoImpl extends BaseDaoImpl implements TrucksDeviceDao {
	static final Logger logger = LoggerFactory.getLogger(TrucksDao.class);

	@Transactional
	@Override
	public void saveDeviceData(HardwareElement element){
		if((element.isFlagOffon()&&element.isFlagFloat()&&element.isFlagString())==false){
			return;
		}
		boolean OldDeviceChangeFlag=false;
		boolean dingweiFlag=false;
		boolean yaliWenduFlag=false;
		try {
			Date now=new Date();
			//对应的车牌不存时，查询
			if(element.getCarNum()==null&&System.currentTimeMillis()-element.getCarNoSearchTime()>HardwareElement.CARNO_SEARCH_GAP){
				
				DeviceTrucksVo deviceTrucks=(DeviceTrucksVo) getSqlMapClientTemplate().queryForObject("DeviceTrucksVo.queryByDtuId", element.getPhone());
				if(deviceTrucks!=null){
					element.setCarDeviceId(deviceTrucks.getId());
					element.setCarNum(deviceTrucks.getTrucks_id());
					element.setLiCheng(deviceTrucks.getLi_cheng());
					element.setCarStyle(deviceTrucks.getTrucks_style());
					if(element.getLiCheng()<0){
						element.setLiCheng(0);
					}					
					Map<String, String> map=element.getMapPositionTyreId();
					map.clear();
					if(deviceTrucks.getTrucks_A1()!=null&&!"".equals(deviceTrucks.getTrucks_A1())){
						map.put("A1",deviceTrucks.getTrucks_A1());
					}
					if(deviceTrucks.getTrucks_A2()!=null&&!"".equals(deviceTrucks.getTrucks_A2())){
						map.put("A2",deviceTrucks.getTrucks_A2());
					}
					if(deviceTrucks.getTrucks_A3()!=null&&!"".equals(deviceTrucks.getTrucks_A3())){
						map.put("A3",deviceTrucks.getTrucks_A3());
					}
					if(deviceTrucks.getTrucks_A4()!=null&&!"".equals(deviceTrucks.getTrucks_A4())){
						map.put("A4",deviceTrucks.getTrucks_A4());
					}
					if(deviceTrucks.getTrucks_A5()!=null&&!"".equals(deviceTrucks.getTrucks_A5())){
						map.put("A5",deviceTrucks.getTrucks_A5());
					}
					if(deviceTrucks.getTrucks_A6()!=null&&!"".equals(deviceTrucks.getTrucks_A6())){
						map.put("A6",deviceTrucks.getTrucks_A6());
					}
					
					if(deviceTrucks.getTrucks_B1()!=null&&!"".equals(deviceTrucks.getTrucks_B1())){
						map.put("B1",deviceTrucks.getTrucks_B1());
					}
					if(deviceTrucks.getTrucks_B2()!=null&&!"".equals(deviceTrucks.getTrucks_B2())){
						map.put("B2",deviceTrucks.getTrucks_B2());
					}
					if(deviceTrucks.getTrucks_B3()!=null&&!"".equals(deviceTrucks.getTrucks_B3())){
						map.put("B3",deviceTrucks.getTrucks_B3());
					}
					if(deviceTrucks.getTrucks_B4()!=null&&!"".equals(deviceTrucks.getTrucks_B4())){
						map.put("B4",deviceTrucks.getTrucks_B4());
					}
					if(deviceTrucks.getTrucks_B5()!=null&&!"".equals(deviceTrucks.getTrucks_B5())){
						map.put("B5",deviceTrucks.getTrucks_B5());
					}
					if(deviceTrucks.getTrucks_B6()!=null&&!"".equals(deviceTrucks.getTrucks_B6())){
						map.put("B6",deviceTrucks.getTrucks_B6());
					}
					if(deviceTrucks.getTrucks_B7()!=null&&!"".equals(deviceTrucks.getTrucks_B7())){
						map.put("B7",deviceTrucks.getTrucks_B7());
					}
					if(deviceTrucks.getTrucks_B8()!=null&&!"".equals(deviceTrucks.getTrucks_B8())){
						map.put("B8",deviceTrucks.getTrucks_B8());
					}
					
					if(deviceTrucks.getTrucks_C1()!=null&&!"".equals(deviceTrucks.getTrucks_C1())){
						map.put("C1",deviceTrucks.getTrucks_C1());
					}
					if(deviceTrucks.getTrucks_C2()!=null&&!"".equals(deviceTrucks.getTrucks_C2())){
						map.put("C2",deviceTrucks.getTrucks_C2());
					}
					if(deviceTrucks.getTrucks_C3()!=null&&!"".equals(deviceTrucks.getTrucks_C3())){
						map.put("C3",deviceTrucks.getTrucks_C3());
					}
					if(deviceTrucks.getTrucks_C4()!=null&&!"".equals(deviceTrucks.getTrucks_C4())){
						map.put("C4",deviceTrucks.getTrucks_C4());
					}
					if(deviceTrucks.getTrucks_C5()!=null&&!"".equals(deviceTrucks.getTrucks_C5())){
						map.put("C5",deviceTrucks.getTrucks_C5());
					}
					if(deviceTrucks.getTrucks_C6()!=null&&!"".equals(deviceTrucks.getTrucks_C6())){
						map.put("C6",deviceTrucks.getTrucks_C6());
					}
					if(deviceTrucks.getTrucks_C7()!=null&&!"".equals(deviceTrucks.getTrucks_C7())){
						map.put("C7",deviceTrucks.getTrucks_C7());
					}
					if(deviceTrucks.getTrucks_C8()!=null&&!"".equals(deviceTrucks.getTrucks_C8())){
						map.put("C8",deviceTrucks.getTrucks_C8());
					}
					if(deviceTrucks.getTrucks_C9()!=null&&!"".equals(deviceTrucks.getTrucks_C9())){
						map.put("C9",deviceTrucks.getTrucks_C9());
					}
					if(deviceTrucks.getTrucks_C10()!=null&&!"".equals(deviceTrucks.getTrucks_C10())){
						map.put("C10",deviceTrucks.getTrucks_C10());
					}
					if(deviceTrucks.getTrucks_C11()!=null&&!"".equals(deviceTrucks.getTrucks_C11())){
						map.put("C11",deviceTrucks.getTrucks_C11());
					}
					if(deviceTrucks.getTrucks_C12()!=null&&!"".equals(deviceTrucks.getTrucks_C12())){
						map.put("C12",deviceTrucks.getTrucks_C12());
					}
					if(deviceTrucks.getTrucks_C13()!=null&&!"".equals(deviceTrucks.getTrucks_C13())){
						map.put("C13",deviceTrucks.getTrucks_C13());
					}
					if(deviceTrucks.getTrucks_C14()!=null&&!"".equals(deviceTrucks.getTrucks_C14())){
						map.put("C14",deviceTrucks.getTrucks_C14());
					}
					if(deviceTrucks.getTrucks_C15()!=null&&!"".equals(deviceTrucks.getTrucks_C15())){
						map.put("C15",deviceTrucks.getTrucks_C15());
					}
					if(deviceTrucks.getTrucks_C16()!=null&&!"".equals(deviceTrucks.getTrucks_C16())){
						map.put("C16",deviceTrucks.getTrucks_C16());
					}
				}
				element.setCarNoSearchTime(System.currentTimeMillis());
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
						+ "gao_wen_bao_jing_set,yuliu1_phone,yuliu2_phone,yuliu3_phone,yuliu4_phone,trucks_id,dtu_no", "device_data_base");	
			    element.getDeviceDataHistory().setBase_id(id);
			}
			double dis=-2d;
			//保存dtu设备数据定位信息
			if(!element.getOldDeviceDataDingwei().equalsValue(element.getDeviceDataDingwei())){
				dingweiFlag=true;
				id=saveByDeviceData(new Object[]{element.getPhone(),now,element.getDeviceDataDingwei().getGps_status(),element.getDeviceDataDingwei().getLatitude(),element.getDeviceDataDingwei().getLatitude_type(),
						element.getDeviceDataDingwei().getLongitude(),element.getDeviceDataDingwei().getLongitude_type(),element.getDeviceDataDingwei().getDimian_sulu(),element.getDeviceDataDingwei().getDimian_hangxiang(),
						element.getCarNum()}, 
						"dtu_id,create_time,gps_status,latitude,latitude_type,longitude,longitude_type,dimian_sulu,dimian_hangxiang,trucks_id","device_data_dingwei");	
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
						updateTrucksFlag(1, element);//行驶中
					}
				}else{
					if(element.isRuning()){
						element.setRuning(false);
						updateTrucksFlag(0, element);//停止中
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
				saveByListDeviceDataOffon(element.getDeviceDataOffons(), now, uuid);
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
				saveByListDeviceDataYaliWendu(element.getDeviceDataYaliWendus(), now, uuid);
				element.getDeviceDataHistory().setYaliwendu_uuid(uuid);
			}
			
			//保存上报记录
			saveByDeviceData(new Object[]{element.getPhone(),now,element.getDeviceFloatByDtuVO().getCaiji_time(),element.getDeviceStringByDtuVO().getCaiji_time(),element.getDeviceOffonByDtuVO().getCaiji_time(),
					element.getDeviceDataHistory().getBase_id(),element.getDeviceDataHistory().getDingwei_id(),element.getDeviceDataHistory().getOffon_uuid(),element.getDeviceDataHistory().getYaliwendu_uuid(),element.getCarNum(),element.getDeviceDataHistory().getWarn()}, 
					"dtu_id,create_time,float_caiji_time,string_caiji_time,offon_caiji_time,base_id,dingwei_id,offon_uuid,yaliwendu_uuid,trucks_id,warn", "device_data_history");
			//保存报警信息
			if(flag1==false&&element.getDeviceDataHistory().getWarn().equals(1)){
				saveByDeviceData(new Object[]{element.getPhone(),now,element.getDeviceFloatByDtuVO().getCaiji_time(),element.getDeviceStringByDtuVO().getCaiji_time(),element.getDeviceOffonByDtuVO().getCaiji_time(),
						element.getDeviceDataHistory().getBase_id(),element.getDeviceDataHistory().getDingwei_id(),element.getDeviceDataHistory().getOffon_uuid(),element.getDeviceDataHistory().getYaliwendu_uuid(),element.getCarNum(),element.getDeviceDataHistory().getWarn()}, 
						"dtu_id,create_time,float_caiji_time,string_caiji_time,offon_caiji_time,base_id,dingwei_id,offon_uuid,yaliwendu_uuid,trucks_id,warn", "device_data_warn");
				
				saveDtuWarnMessage(element, now);//保存报警信息
			}
			
			//保存发射器信息
			saveFasheqi(now, element);
			
			//更新设备数据
			if(!element.getOldDevice().equalsValue(element.getDevice())){
				OldDeviceChangeFlag=true;
				
				Device updateInfo=new Device();
				updateInfo.setTrucks_id(element.getCarNum());
				updateInfo.setCaiji_time(now);
				updateInfo.setDtu_tpms_status(element.getDevice().getDtu_tpms_status());
				updateInfo.setDtu_status(element.getDevice().getDtu_status());
				updateInfo.setTpms_pinlu(element.getDevice().getTpms_pinlu());
				updateInfo.setYali_set_low_zhou_1(element.getDevice().getYali_set_low_zhou_1());
				updateInfo.setYali_set_low_zhou_2(element.getDevice().getYali_set_low_zhou_2());
				updateInfo.setYali_set_low_zhou_3(element.getDevice().getYali_set_low_zhou_3());
				updateInfo.setYali_set_low_zhou_4(element.getDevice().getYali_set_low_zhou_4());
				updateInfo.setYali_set_high_zhou_1(element.getDevice().getYali_set_high_zhou_1());
				updateInfo.setYali_set_high_zhou_2(element.getDevice().getYali_set_high_zhou_2());
				updateInfo.setYali_set_high_zhou_3(element.getDevice().getYali_set_high_zhou_3());
				updateInfo.setYali_set_high_zhou_4(element.getDevice().getYali_set_high_zhou_4());
				
				updateInfo.setGao_wen_bao_jing_set(element.getDevice().getGao_wen_bao_jing_set());
				
				updateInfo.setYuliu1_phone(element.getDevice().getYuliu1_phone());
				updateInfo.setYuliu2_phone(element.getDevice().getYuliu2_phone());
				updateInfo.setYuliu3_phone(element.getDevice().getYuliu3_phone());
				updateInfo.setYuliu4_phone(element.getDevice().getYuliu4_phone());
				
				updateInfo.setGps_status(element.getDevice().getGps_status());
				
				updateInfo.setLatitude(element.getDevice().getLatitude());
				updateInfo.setLatitude_type(element.getDevice().getLatitude_type());
				updateInfo.setLongitude(element.getDevice().getLongitude());
				updateInfo.setLongitude_type(element.getDevice().getLongitude_type());
				
				updateInfo.setDimian_sulu(element.getDevice().getDimian_sulu());
				updateInfo.setDimian_hangxiang(element.getDevice().getDimian_hangxiang());
				
				updateInfo.setLi_cheng((dis>0?dis:0d));
				
				updateInfo.setWarn(element.getDeviceDataHistory().getWarn());
				
				updateInfo.setDtu_id(element.getPhone());
				
				getSqlMapClientTemplate().update("Device.updateInfoWhenChange", updateInfo);
				
			}else{
				Device updateInfo=new Device();
				updateInfo.setCaiji_time(now);
				updateInfo.setLi_cheng(dis>0?dis:0d);
				updateInfo.setWarn(element.getDeviceDataHistory().getWarn());
				updateInfo.setDtu_id(element.getPhone());
				getSqlMapClientTemplate().update("Device.updateInfoNoChange", updateInfo);
			}
			//更新里程
			if(dis>0){
				element.setLiCheng(dis+element.getLiCheng());
				//更新胎里程
				if(element.getCarNum()!=null){
					updateTyreLicheng(dis, element);
				}
				
				//更新发射器里程
				DeviceFasheqi param=new DeviceFasheqi();
				param.setDtu_id(element.getPhone());
				param.setLi_cheng(dis);
				getSqlMapClientTemplate().update("DeviceFasheqi.updateLicheng", param);
			}
			
			logger.info("设备数据保存成功,设备号:"+element.getPhone()+",车牌号:"+element.getCarNum());
			return ;			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("设备数据保存失败,设备号:"+element.getPhone()+",车牌号:"+element.getCarNum()+StringHelper.getTrace(e));
		}finally {
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
	@Transactional
	private void updateTyreLicheng(double dis,HardwareElement element) {
		String[] tyreWhereArr=TrucksStyleUtil.TyreWhereArrByStyle(element.getCarStyle());
		if(tyreWhereArr==null){
			return;
		}
		
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("tyreWhereArr", tyreWhereArr);
		param.put("dis", dis);
		param.put("trucks_id", element.getCarNum());
		int a = getSqlMapClientTemplate().update("Trucks.updateTyreLicheng", param);
		if (a > 0) {
            logger.info("更新胎里程成功！trucks_id="+element.getCarNum());
		}
		return;
	}
	
	//更新车,是否行使还是停放
	@Transactional
	@Override
	public void updateTrucksFlag(int dtu_status,HardwareElement element) {
		Trucks param=new Trucks();
		param.setTrucks_flag(dtu_status);
		param.setTrucks_id(element.getCarNum());
		int a = getSqlMapClientTemplate().update("Trucks.updateTrucksFlag", param);
		if (a > 0) {
            logger.info("更新车,是否行使还是停放成功！trucks_id="+element.getCarNum());
		}
		return;
	}
	
	@Override
	public boolean reloadTrucksDevice(HardwareElement element){
		boolean flag=true;
		DeviceTrucksVo deviceTrucksVo=(DeviceTrucksVo) getSqlMapClientTemplate().queryForList("DeviceTrucksVo.queryByDtuId", element.getPhone());
		if(deviceTrucksVo!=null){
			element.setCarDeviceId(deviceTrucksVo.getId());
			element.setCarNum(deviceTrucksVo.getTrucks_id());
			element.setLiCheng(deviceTrucksVo.getLi_cheng());
			element.setCarStyle(deviceTrucksVo.getTrucks_style());
			if(element.getLiCheng()<0){
				element.setLiCheng(0);
			}
			
			Map<String, String> map=element.getMapPositionTyreId();
			map.clear();
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
		}
		
		logger.info("重新加载设备和车牌对应关系,设备号:"+element.getPhone()+",车牌号:"+element.getCarNum());
		return flag;
	}
	
	
	//保存
	@Transactional
	private long saveByDeviceData(final Object[] paramters, String sqlCols,String tabeName) {
		
		ExtEntity entity = new ExtEntity();
		entity.setTableName(tabeName);
		entity.setColName(sqlCols);
		entity.setColValueList(Arrays.asList(paramters));

		long id= (long) getSqlMapClientTemplate().insert("ExtEntity.saveData", entity);
		if (id > 0) {
            logger.info("保存成功！");
		}
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
	@Transactional
	private boolean saveByListDeviceDataOffon(List<DeviceDataOffon> deviceDataOffons,Date now,String uuid) {
		boolean flag = false;
//		StringBuffer columnValue = new StringBuffer();
		Integer num=-1;
		List<DeviceDataOffon> offonInfo=new ArrayList<DeviceDataOffon>();
		for(int i=0;i<deviceDataOffons.size();i++){
			DeviceDataOffon deviceDataOffon=deviceDataOffons.get(i);
			deviceDataOffon.setCreate_time(now);
			deviceDataOffon.setUuid(uuid);
			offonInfo.add(deviceDataOffon);
			
//			List<Object> paramterList=new ArrayList<Object>();
//			paramterList.addAll(Arrays.asList(new Object[]{now,deviceDataOffon.getFasheqi_id(),deviceDataOffon.getLouqi(),deviceDataOffon.getGaoya(),deviceDataOffon.getDiya(),deviceDataOffon.getGaowen(),deviceDataOffon.getDianchi(),deviceDataOffon.getFasheqidianchi(),deviceDataOffon.getZhongduan(),deviceDataOffon.getShilian(),deviceDataOffon.getWarn(),deviceDataOffon.getNo(),deviceDataOffon.getTyre_id(),uuid}));
//			if(i==0){
//				columnValue.append("("+StringUtils.strip(paramterList.toString(), "[]")+")");
//			}else{
//				columnValue.append(",("+StringUtils.strip(paramterList.toString(), "[]")+")");
//			}
		}
		getSqlMapClientTemplate().insert("DeviceDataOffon.saveList", offonInfo);
//		if(num>0){
			logger.info("批量保存开关量成功！uuid="+uuid);
			flag = true;
//		}
	
		return flag;
	}
	
	@Transactional
	private boolean saveByListDeviceDataYaliWendu(List<DeviceDataYaliWendu> deviceDataYaliWendus,Date now,String uuid) {
		boolean flag = false;
		Integer num=-1;
		List<DeviceDataYaliWendu> yaliWenduInfo=new ArrayList<DeviceDataYaliWendu>();
		for(int i=0;i<deviceDataYaliWendus.size();i++){
			DeviceDataYaliWendu deviceDataYaliWendu=deviceDataYaliWendus.get(i);
			deviceDataYaliWendu.setCreate_time(now);
			deviceDataYaliWendu.setUuid(uuid);
			yaliWenduInfo.add(deviceDataYaliWendu);
			
//			List<Object> paramterList=new ArrayList<Object>();
//			paramterList.addAll(Arrays.asList(new Object[]{now,deviceDataYaliWendu.getFasheqi_id(),deviceDataYaliWendu.getYali(),deviceDataYaliWendu.getWendu(),deviceDataYaliWendu.getNo(),deviceDataYaliWendu.getTyre_id(),uuid}));
//			if(i==0){
//				columnValue.append("("+StringUtils.strip(paramterList.toString(), "[]")+")");
//			}else{
//				columnValue.append(",("+StringUtils.strip(paramterList.toString(), "[]")+")");
//			}
		}
		getSqlMapClientTemplate().insert("DeviceDataYaliWendu.saveList", yaliWenduInfo);
//		if(num>0){
			logger.info("保存压力温度成功！uuid="+uuid);
			flag = true;
//		}
		return flag;
	}
	
	//保存发射器
	@Transactional
	private void saveFasheqi(Date now,HardwareElement element) {
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
					DeviceFasheqi deviceFasheqiParam=new DeviceFasheqi();
					deviceFasheqiParam.setCaiji_time(now);
					deviceFasheqiParam.setLouqi(element.getDeviceFasheqis().get(i).getLouqi());
					deviceFasheqiParam.setGaoya(element.getDeviceFasheqis().get(i).getGaoya());
					deviceFasheqiParam.setDiya(element.getDeviceFasheqis().get(i).getDiya());
					deviceFasheqiParam.setGaowen(element.getDeviceFasheqis().get(i).getGaowen());
					deviceFasheqiParam.setDianchi(element.getDeviceFasheqis().get(i).getDianchi());
					deviceFasheqiParam.setFasheqidianchi(element.getDeviceFasheqis().get(i).getFasheqidianchi());
					deviceFasheqiParam.setZhongduan(element.getDeviceFasheqis().get(i).getZhongduan());
					deviceFasheqiParam.setShilian(element.getDeviceFasheqis().get(i).getShilian());
					deviceFasheqiParam.setWarn(element.getDeviceFasheqis().get(i).getWarn());
					deviceFasheqiParam.setYali(element.getDeviceFasheqis().get(i).getYali());
					deviceFasheqiParam.setWendu(element.getDeviceFasheqis().get(i).getWendu());
					deviceFasheqiParam.setNo(element.getDeviceFasheqis().get(i).getNo());
					deviceFasheqiParam.setTyre_id(element.getDeviceFasheqis().get(i).getTyre_id());
					deviceFasheqiParam.setTrucks_id(element.getCarNum());
					deviceFasheqiParam.setDtu_id(element.getPhone());
					deviceFasheqiParam.setFasheqi_id(element.getDeviceFasheqis().get(i).getFasheqi_id());
					getSqlMapClientTemplate().update("DeviceFasheqi.updateInfo", deviceFasheqiParam);
				}
			}else{
//				SqlRowSet resultSet=getJdbcTemplate().queryForRowSet("SELECT id FROM device_fasheqi WHERE fasheqi_id=?", new Object[]{element.getDeviceFasheqis().get(i).getFasheqi_id()});
				@SuppressWarnings("unchecked")
				List<DeviceFasheqi> fasheqis=getSqlMapClientTemplate().queryForList("DeviceFasheqi.getByFasheqiId", element.getDeviceFasheqis().get(i).getFasheqi_id());
				if(fasheqis!=null&&fasheqis.size()>0){
					DeviceFasheqi updateParams=new DeviceFasheqi();
					updateParams.setCaiji_time(now);
					updateParams.setLouqi(element.getDeviceFasheqis().get(i).getLouqi());
					updateParams.setGaoya(element.getDeviceFasheqis().get(i).getGaoya());
					updateParams.setDiya(element.getDeviceFasheqis().get(i).getDiya());
					updateParams.setGaowen(element.getDeviceFasheqis().get(i).getGaowen());
					updateParams.setDianchi(element.getDeviceFasheqis().get(i).getDianchi());
					updateParams.setFasheqidianchi(element.getDeviceFasheqis().get(i).getFasheqidianchi());
					updateParams.setZhongduan(element.getDeviceFasheqis().get(i).getZhongduan());
					updateParams.setShilian(element.getDeviceFasheqis().get(i).getShilian());
					updateParams.setWarn(element.getDeviceFasheqis().get(i).getWarn());
					updateParams.setYali(element.getDeviceFasheqis().get(i).getYali());
					updateParams.setWendu(element.getDeviceFasheqis().get(i).getWendu());
					updateParams.setNo(element.getDeviceFasheqis().get(i).getNo());
					updateParams.setTyre_id(element.getDeviceFasheqis().get(i).getTyre_id());
					updateParams.setTrucks_id(element.getCarNum());
					updateParams.setDtu_id(element.getPhone());
					updateParams.setFasheqi_id(element.getDeviceFasheqis().get(i).getFasheqi_id());
					getSqlMapClientTemplate().update("DeviceFasheqi.updateInfo", updateParams);
				}else{
					
					DeviceFasheqi insertParams=new DeviceFasheqi();
					insertParams.setCreate_time(now);
					insertParams.setCaiji_time(now);
					insertParams.setFasheqi_id(element.getDeviceFasheqis().get(i).getFasheqi_id());
					insertParams.setLouqi(element.getDeviceFasheqis().get(i).getLouqi());
					insertParams.setGaoya(element.getDeviceFasheqis().get(i).getGaoya());
					insertParams.setDiya(element.getDeviceFasheqis().get(i).getDiya());
					insertParams.setGaowen(element.getDeviceFasheqis().get(i).getGaowen());
					insertParams.setDianchi(element.getDeviceFasheqis().get(i).getDianchi());
					insertParams.setFasheqidianchi(element.getDeviceFasheqis().get(i).getFasheqidianchi());
					insertParams.setZhongduan(element.getDeviceFasheqis().get(i).getZhongduan());
					insertParams.setShilian(element.getDeviceFasheqis().get(i).getShilian());
					insertParams.setWarn(element.getDeviceFasheqis().get(i).getWarn());
					insertParams.setYali(element.getDeviceFasheqis().get(i).getYali());
					insertParams.setWendu(element.getDeviceFasheqis().get(i).getWendu());
					insertParams.setNo(element.getDeviceFasheqis().get(i).getNo());
					insertParams.setTyre_id(element.getDeviceFasheqis().get(i).getTyre_id());
					insertParams.setTrucks_id(element.getCarNum());
					insertParams.setLi_cheng( 0d);
					insertParams.setDtu_id(element.getPhone());
					getSqlMapClientTemplate().insert("DeviceFasheqi.saveInfo", insertParams);
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
			getSqlMapClientTemplate().update("DeviceFasheqi.updateNoDtu", tt);
		}
		//更新采集时间
		if(!"".equals(caiji_timesql)){
			Map<String, Object> param=new HashMap<String, Object>();
			param.put("caiji_time", now);
			param.put("fasheqi_ids", caiji_timesql);
			getSqlMapClientTemplate().update("DeviceFasheqi.updateCaijiTime", param);
		}
		logger.info("保存发射器成功！dtu_id="+element.getPhone());
		return;
	}
	//保存车报警
	@Transactional
	private void saveDtuWarnMessage(HardwareElement element,Date now){
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
			@SuppressWarnings("unchecked")
			List<User> users=getSqlMapClientTemplate().queryForList("User.getByTrucksId", element.getCarNum());
			List<MessageVO> messageInfo=new ArrayList<MessageVO>();
			int userId = 0;
			String company="";
			for (User user : users) {
				MessageVO messageVO = new MessageVO();
				messageVO.setCreate_time(now);
				messageVO.setContent("车报警");
				messageVO.setUser_id(user.getUser_id());
				messageVO.setType(4);
				messageVO.setRemark(element.getCarNum());
				
				messageInfo.add(messageVO);
				
//				userId=user.getUser_id();
//				company=user.getUser_company();
//				List<Object> paramterList=new ArrayList<Object>();
//				paramterList.addAll(Arrays.asList(new Object[]{now,"车报警",null,userId,4,element.getCarNum()}));
//				if(b){
//					colValue.append("("+StringUtils.strip(paramterList.toString(), "[]")+")");
//					b=false;
//				}else{
//					colValue.append(",("+StringUtils.strip(paramterList.toString(), "[]")+")");
//				}
			}
			if(messageInfo.size()>0){
				getSqlMapClientTemplate().insert("MessageVO.saveToM", messageInfo);
				Message message = new Message(0, company+","+element.getCarNum()+",车报警", null, userId, 4, new Date(), element.getCarNum(), "警告信息",company);
				try {
//					JPushUtil.pushMsg2AndroidAndIOS("06d953907c2ed56fe5fd912a", "e36adaf2029fde50514c6d4e", "警告信息", company+","+element.getCarNum()+",车报警", "warn", JSON.toJSONString(message));
				} catch (Exception e) {
					logger.error("报警信息推送失败！dtu_id="+element.getPhone()+StringHelper.getTrace(e));
				}
			}
		}
	}
	
	//推送信息测试
	@Override
	public void test() {
		HardwareElement element = new HardwareElement();
		element.setCarNum("沪DE1071");
		List<DeviceDataOffon> offons = new ArrayList<DeviceDataOffon>();
		DeviceDataOffon offon = new DeviceDataOffon();
		offon.setLouqi(1);
		offon.setGaoya(1);
		offon.setDiya(1);
		offon.setGaowen(1);
		offon.setDianchi(1);
		offon.setFasheqidianchi(1);
		offons.add(offon);
		element.setDeviceDataOffons(offons);
		
		Date now=new Date();
		saveDtuWarnMessage(element, now);
	
	}
	
}
