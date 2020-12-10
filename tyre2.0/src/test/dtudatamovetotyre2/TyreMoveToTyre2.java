package test.dtudatamovetotyre2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.List;

import com.psylife.entity.DeviceFloat;
import com.psylife.entity.DeviceOffon;
import com.psylife.entity.DeviceString;
import com.psylife.hardware.HardwareElement;
import com.psylife.util.DateUtil;
import com.psylife.util.ResultSetUtil;
import com.psylife.vo.dtu.DeviceFloatByDtuVO;
import com.psylife.vo.dtu.DeviceOffonByDtuVO;
import com.psylife.vo.dtu.DeviceStringByDtuVO;

/**
 * 从1.0到2.0版本，数据处理
 * @author xu
 * Created on 2016年7月14日 下午11:55:29
 */
public class TyreMoveToTyre2 {

	public static void main(String[] args) {
		proccess(new String[]{"00000643","00000642"});
	}
	
	private static void proccess(String[] dtuids){
		System.out.println("开始处理");
		Connection conn = null;  
		DtuDataSave dtuDataSave=new DtuDataSave();
		DtuLogin dtuLogin=new DtuLogin();
		
		Connection conn2 = null;  
		try {
			
			//源数据库
	    	//指定连接类型 
	    	Class.forName("com.mysql.jdbc.Driver");
	    	//获取连接  
	        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tyretest1?useUnicode=true&amp;characterEncoding=UTF-8", "root", "root");
	        
	        //目标数据库
	      //指定连接类型 
	    	Class.forName("com.mysql.jdbc.Driver");
	    	//获取连接  
	        conn2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/tyretest2?useUnicode=true&amp;characterEncoding=UTF-8", "root", "root");
	        
	        conn.setAutoCommit(false);
	        conn2.setAutoCommit(false);
	        //准备执行语句 
			for(String dtuid:dtuids){
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
				PreparedStatement preparedStatement1 = null;
				ResultSet resultSet1 = null;
				PreparedStatement preparedStatement2 = null;
				ResultSet resultSet2 = null;
				PreparedStatement preparedStatement3 = null;
				ResultSet resultSet3 = null;
				PreparedStatement preparedStatement4 = null;
				ResultSet resultSet4 = null;
				PreparedStatement preparedStatement5 = null;
				try {
					HardwareElement element=new HardwareElement();
					element.setPhone(dtuid);
					
					if(!dtuLogin.login(element, conn2)){
						break;
					}
					
					//开关量包数据
					preparedStatement = conn.prepareStatement(
					"SELECT id,dtu_id,create_time,caiji_time,dtu_tpms_status,dtu_status,gps_status,offon_value,trucks_id "+
		"FROM device_offon_log WHERE dtu_id=? ORDER BY id ASC");
					preparedStatement.setString(1, element.getPhone());
					resultSet = preparedStatement.executeQuery();
					List<DeviceOffon> deviceOffons=ResultSetUtil.getByList(resultSet, "dtu_id,create_time,caiji_time,dtu_tpms_status,dtu_status,gps_status,offon_value,trucks_id".split(","), 
							"dtu_id,create_time,caiji_time,dtu_tpms_status,dtu_status,gps_status,offon_value,trucks_id".split(","), DeviceOffon.class, false);
					
					//float包数据
					preparedStatement1 = conn.prepareStatement(
							"SELECT id,dtu_id,create_time,caiji_time,dtu_tpms_status,tpms_pinlu,dimian_sulu,dimian_hangxiang,gao_wen_bao_jing_set,"+
		"yali_set,wendu_yali_value,trucks_id FROM device_float_log WHERE dtu_id=? ORDER BY id ASC");
					preparedStatement1.setString(1, element.getPhone());
					resultSet1 = preparedStatement1.executeQuery();
					List<DeviceFloat> deviceFloats=ResultSetUtil.getByList(resultSet1, "dtu_id,create_time,caiji_time,dtu_tpms_status,tpms_pinlu,dimian_sulu,dimian_hangxiang,gao_wen_bao_jing_set,yali_set,wendu_yali_value,trucks_id".split(","), 
							"dtu_id,create_time,caiji_time,dtu_tpms_status,tpms_pinlu,dimian_sulu,dimian_hangxiang,gao_wen_bao_jing_set,yali_set,wendu_yali_value,trucks_id".split(","), DeviceFloat.class, false);
					
					//字符串包数据
					preparedStatement2 = conn.prepareStatement(
							"SELECT dtu_id,create_time,caiji_time,dtu_tpms_status,yuliu1_phone,yuliu2_phone,yuliu3_phone,yuliu4_phone,"+
		"dtu_no,dtu_trucks_id,sim,latitude,latitude_type,longitude,longitude_type,fasheqi_ids_value,trucks_id "+
		 "FROM device_string_log WHERE dtu_id=? ORDER BY id ASC");
					preparedStatement2.setString(1, element.getPhone());
					resultSet2 = preparedStatement2.executeQuery();
					List<DeviceString> deviceStrings=ResultSetUtil.getByList(resultSet2, "dtu_id,create_time,caiji_time,dtu_tpms_status,yuliu1_phone,yuliu2_phone,yuliu3_phone,yuliu4_phone,dtu_no,dtu_trucks_id,sim,latitude,latitude_type,longitude,longitude_type,fasheqi_ids_value,trucks_id".split(","), 
							"dtu_id,create_time,caiji_time,dtu_tpms_status,yuliu1_phone,yuliu2_phone,yuliu3_phone,yuliu4_phone,dtu_no,dtu_trucks_id,sim,latitude,latitude_type,longitude,longitude_type,fasheqi_ids_value,trucks_id".split(","), DeviceString.class, false);
					
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					for(int i=0;i<deviceStrings.size()&&i<deviceOffons.size()&&i<deviceFloats.size();i++){
						DeviceOffonByDtuVO deviceOffonByDtuVO=new DeviceOffonByDtuVO();
						deviceOffonByDtuVO.toValue(deviceOffons.get(i));
						
						DeviceFloatByDtuVO deviceFloatByDtuVO=new DeviceFloatByDtuVO();
						deviceFloatByDtuVO.toValue(deviceFloats.get(i));
						
						DeviceStringByDtuVO deviceStringByDtuVO=new DeviceStringByDtuVO();
						deviceStringByDtuVO.toValue(deviceStrings.get(i));

						
						element.setDeviceOffonByDtuVO(deviceOffonByDtuVO);
						element.setDeviceStringByDtuVO(deviceStringByDtuVO);
						element.setDeviceFloatByDtuVO(deviceFloatByDtuVO);
						if(i==deviceStrings.size()-1){
							System.out.println();
						}
						dtuDataSave.saveDeviceData(element, conn2, deviceOffonByDtuVO.getCaiji_time());
						System.out.println("正处理,dtuid="+dtuid+",共="+deviceStrings.size()+",当前="+(i+1)+formatter.format(deviceFloats.get(i).getCreate_time()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
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
						if (resultSet4 != null && !resultSet4.isClosed()) {
							resultSet4.close();
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
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
				
				
				
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null&&!conn.isClosed()){
					conn.close();
				}
				if(conn2!=null&&!conn2.isClosed()){
					conn2.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		System.out.println("结束处理");
		
	}

}
