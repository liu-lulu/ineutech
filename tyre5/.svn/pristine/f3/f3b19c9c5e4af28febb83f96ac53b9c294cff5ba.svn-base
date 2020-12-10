package com.kkbc.dao.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kkbc.dao.DeviceLoginLogDao;
import com.kkbc.dao.TrucksDeviceDao;
import com.kkbc.entity.DeviceLoginLog;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.hardware.process.HeartProcess;
import com.psylife.util.Constants;

@Repository
public class DeviceLoginLogDaoImpl extends BaseDaoImpl implements DeviceLoginLogDao{

	@Resource
	private TrucksDeviceDao trucksDeviceDao;
	
	@Transactional
	@Override
	public void loginOut(HardwareElement element){
		save(new Object[]{new Date(),Constants.STATUS_NORMAL,element.getUdpAddress().getAddress().getHostAddress(),element.getUdpAddress().getPort(),DeviceLoginLog.TYPE_LOGOUT,element.getPhone()}, 
				"create_time,status,remote_ip,remote_port,type,dtu_id", DeviceLoginLog.TB_N);	

		trucksDeviceDao.updateTrucksFlag(0, element);//停止中
		logger.info("设备退出保存成功,设备号:"+element.getPhone()+",车牌号:"+element.getCarNum());
		return;
	}
	
	//保存
//	@Transactional
//	private long saveByDeviceData(final Object[] paramters, String sqlCols,String tabeName) {
//			long id = 0;
//			final StringBuffer SQL = new StringBuffer();
//			SQL.append("insert into ").append(tabeName).append("(").append(sqlCols).append(") ").append("values( ");
//			int i=0;
//			for(i=0;i<paramters.length;i++){
//				if(i==0){
//					SQL.append("?");
//				}else{
//					SQL.append(",?");
//				}
//			}
//			SQL.append(") ");
//			
//			KeyHolder keyHolder = new GeneratedKeyHolder();
//			int a = getJdbcTemplate().update(new PreparedStatementCreator() {
//				
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn)
//						throws SQLException {
//					PreparedStatement ps = conn.prepareStatement(SQL.toString(), Statement.RETURN_GENERATED_KEYS);
//					for(int i=0;i<paramters.length;i++){
//						ps.setObject(i+1, paramters[i]);
//					}
//					return ps;
//				}
//			},keyHolder);
//			if (a > 0) {
//                if(keyHolder.getKey().intValue()>0) {
//                	id = keyHolder.getKey().intValue(); 
//                }
//                logger.info("保存成功！");
//			}
//			return id;
//		}
//	
	@Transactional
	@Override
	public boolean startSysDtuProcessTrucksFlag() {
		Timestamp endTime=new Timestamp(System.currentTimeMillis()-HeartProcess.INTERVAL_TIMEOUT_TCP);
		int isOK = getSqlMapClientTemplate().update("Trucks.updateTrucksToStop", endTime);
		
		logger.info("系统启动时处理dtu离线时,行驶状态为停放");
		return isOK == 1 ? true : false;
	}
	
	@Transactional
	@Override
	public void endSysDtuProcessTrucksFlag() {
		getSqlMapClientTemplate().update("Trucks.updateTrucksToStopWhenServerClose");
		
		logger.info("服务器停止,所有监视车辆行驶状态为停放");
	}
	
}
