package com.kkbc.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kkbc.dao.DeviceDao;
import com.kkbc.entity.Device;
import com.kkbc.entity.DeviceDataOffon;
import com.kkbc.entity.DeviceFasheqi;
import com.kkbc.hardware.process.HeartProcess;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.web.DeviceDataHistoryVO;
import com.kkbc.vo.web.FasheqiDataHistoryVO;
import com.kkbc.vo.web.TrucksDeviceCountVO;
import com.kkbc.vo.web.TrucksFasheqiPosVO;
import com.psylife.util.Constants;
import com.psylife.util.DateUtil;

@Repository
public class DeviceDaoImpl extends BaseDaoImpl implements DeviceDao{

	static final Logger logger = LoggerFactory.getLogger(DeviceDao.class);
	
	@Override
	public TrucksDeviceCountVO getTrucksDeviceCount(Integer company_id){
		Date caiji_time=null;
		long currentTime=System.currentTimeMillis();
		int online=0,offline=0,count=0,warn=0;
		long dateTime=DateUtil.getDayBegin(new Date()).getTime();
		@SuppressWarnings("unchecked")
		List<Device> devices=getSqlMapClientTemplate().queryForList("Device.currentDtu", company_id);
		for (Device device : devices) {
			caiji_time=device.getCaiji_time();
			if(caiji_time!=null&&currentTime-caiji_time.getTime()<HeartProcess.INTERVAL_TIMEOUT_TCP){
				online++;
			}else{
				offline++;
			}
			count++;
			if(device.getWarn()==1&&caiji_time!=null&&caiji_time.getTime()-dateTime>=0){
				warn++;
			}
		}
		TrucksDeviceCountVO vo=new TrucksDeviceCountVO();
		vo.setCount(count);
		vo.setOnline(online);
		vo.setOffline(offline);
		vo.setWarn(warn);				
		logger.info("获取当前硬件数据汇总成功！");
		return vo;
	}
	
	@Override
	public TrucksFasheqiPosVO fasheqipos(String dtu_id){
		TrucksFasheqiPosVO fasheqiPosVO=(TrucksFasheqiPosVO) getSqlMapClientTemplate().queryForObject("TrucksFasheqiPosVO.queryByDtu", dtu_id);
		if(fasheqiPosVO!=null){
			@SuppressWarnings("unchecked")
			List<DeviceFasheqi> deviceFasheqis=getSqlMapClientTemplate().queryForList("DeviceFasheqi.queryByDtu", dtu_id);
			fasheqiPosVO.setDeviceFasheqis(deviceFasheqis);
		}
		logger.info("获取传感器分布成功！");
		return fasheqiPosVO;
	}
	
	
	@Override
	public List<DeviceDataHistoryVO> getHistoryDtuList(String keyWord,int pagenum,Integer company_id,Date startTime,Date endTime){
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("keyWord", keyWord);
		params.put("company_id", company_id);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("startIndex", (pagenum-1)*Constants.PAGESIZE);
		params.put("pageSize", Constants.PAGESIZE);
		
		@SuppressWarnings("unchecked")
		List<DeviceDataHistoryVO> list=getSqlMapClientTemplate().queryForList("DeviceDataHistoryVO.queryHistoryDtu", params);
		if(list!=null&&list.size()>0){
			for(DeviceDataHistoryVO deviceDataHistoryVO:list){
				deviceDataHistoryVO.setDeviceDataOffons(getOffonByUUID(deviceDataHistoryVO.getOffon_uuid()));
				if(deviceDataHistoryVO.getWarn()==1){
					deviceDataHistoryVO.setWarnInfo(warnInfoByDTU(deviceDataHistoryVO.getDeviceDataOffons()));
				}
			}
		}
		
		logger.info("根据公司id获取车辆历史记录列表成功！");
		return list;
	}
	
	
	@Override
	public List<FasheqiDataHistoryVO> getHistoryfasheqiList(String keyWord,int pagenum,Integer company_id,Date startTime,Date endTime){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("keyWord", keyWord);
		params.put("company_id", company_id);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("startIndex", (pagenum-1)*Constants.PAGESIZE);
		params.put("pageSize", Constants.PAGESIZE);
		@SuppressWarnings("unchecked")
		List<FasheqiDataHistoryVO> list=getSqlMapClientTemplate().queryForList("FasheqiDataHistoryVO.queryHistoryfasheqi", params);
		logger.info("根据公司id获取传感器历史数据记录成功！");
		return list;
	}
	
	@Override
	public ListInfo<DeviceFasheqi> searchByFasheqilist(Integer company_id,String keyWord,int currentPageNO,int pageSize){
//		String baseSql=" FROM device_fasheqi DF LEFT JOIN device D ON D.dtu_id=DF.dtu_id ";
//		String typeSql="";//类型查询
//		String keyWordSql="";//关键字查询
//		
//		typeSql=" (D.company_id="+company_id+") ";
//		if(keyWord!=null&&!"".equals(keyWord)){
//			keyWordSql= " (DF.fasheqi_id like '%"+ keyWord + "%' or D.dtu_id like '%"+keyWord+"%' or D.dtu_id like '%"+keyWord+"%') ";
//		}
//		StringBuffer whereSql=new StringBuffer();
//		if(!"".equals(typeSql)){
//			if(!"".equals(whereSql.toString())){
//				whereSql.append(" and ");
//			}
//			whereSql.append(typeSql);
//		}
//		if(!"".equals(keyWordSql)){
//			if(!"".equals(whereSql.toString())){
//				whereSql.append(" and ");
//			}
//			whereSql.append(keyWordSql);
//		}
//		if(!"".equals(whereSql.toString())){
//			baseSql=baseSql+" where " + whereSql.toString();
//		}
//		String fields=" DF.id,DF.create_time,DF.caiji_time,DF.fasheqi_id,DF.louqi,gaoya,DF.diya,DF.gaowen,DF.dianchi,DF.fasheqidianchi,DF.zhongduan,DF.shilian,DF.warn,DF.yali,wendu,DF.`no`,DF.tyre_id,DF.trucks_id,DF.li_cheng,DF.dtu_id ";
//		String sqlBylist="select DISTINCT "+fields+baseSql+"  order by DF.id desc ";
//		String sqlByCount="select count(DISTINCT DF.id) "+baseSql;
//		System.out.println("----------"+sqlBylist+" LIMIT "+listInfo.getFirst()+"," + pageSize);
//		System.out.println("----------"+sqlByCount);

		ListInfo<DeviceFasheqi> listInfo = new ListInfo<DeviceFasheqi>(currentPageNO, pageSize);
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("keyWord", keyWord);
		params.put("company_id", company_id);
		params.put("startIndex", listInfo.getFirst());
		params.put("pageSize", pageSize);

		@SuppressWarnings("unchecked")
		List<DeviceFasheqi> eventList=getSqlMapClientTemplate().queryForList("DeviceFasheqi.queryPageByKeyWord", params);
		
		listInfo.setCurrentList(eventList);
		
		int totalCount=(int) getSqlMapClientTemplate().queryForObject("DeviceFasheqi.queryCountByKeyWord", params);
		listInfo.setSizeOfTotalList(totalCount);
			
		return listInfo;
	}
	
	
	/**
	 * 根据uuid获取开关量
	 * @param uuid
	 * @param connection
	 * @return
	 */
	private List<DeviceDataOffon> getOffonByUUID(String uuid){
		@SuppressWarnings("unchecked")
		List<DeviceDataOffon> list=getSqlMapClientTemplate().queryForList("DeviceDataOffon.queryByUuid", uuid);
		logger.error("成功获取车辆历史记录列表开关量！");
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
