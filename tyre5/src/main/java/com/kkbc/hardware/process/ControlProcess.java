package com.kkbc.hardware.process;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kkbc.entity.DeviceOperationLog;
import com.kkbc.hardware.DtuOperation;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.hardware.UDPServerManager;
import com.kkbc.hardware.send.HardwareSendManager;
import com.kkbc.hardware.send.SendManager;
import com.kkbc.service.DeviceLoginService;
import com.kkbc.service.TrucksDeviceService;
import com.kkbc.service.impl.DeviceLoginServiceImpl;
import com.kkbc.service.impl.TrucksDeviceServiceImpl;
import com.kkbc.vo.dtu.TyreFloatWenduYaliVO;
import com.kkbc.vo.dtu.TyreFloatYaliSetVO;
import com.kkbc.vo.dtu.TyreOffonVO;
import com.kkbc.vo.dtu.TyreStringVO;
import com.psylife.hardware.data.PackageData;
import com.psylife.hardware.protocol.constant.PackageCommandConstant;
import com.psylife.hardware.protocol.constant.PackageHeadConstant;
import com.psylife.util.DateUtil;
import com.psylife.util.SpringContextUtils;
import com.psylife.util.StringHelper;

public class ControlProcess {
	
//	private DeviceLoginLogService deviceLoginLogService=new DeviceLoginLogServiceImpl();
	
	private DeviceLoginService deviceLoginService;
	private TrucksDeviceService trucksDeviceService;
	
	static final Logger logger = LoggerFactory.getLogger(ControlProcess.class);
	
	public ControlProcess(){
		deviceLoginService = (DeviceLoginServiceImpl)SpringContextUtils.context.getBean("deviceLoginService");
		trucksDeviceService = (TrucksDeviceServiceImpl)SpringContextUtils.context.getBean("trucksDeviceService");
	}
	
	/**
	 * 处理分发方法
	 * @param ctx
	 * @param packageData
	 */
	public void process(PackageData packageData,InetSocketAddress udpAddress){
		if(packageData!=null){
			if(packageData.getHead()==PackageHeadConstant.PACKAGE_HEAD_NWX){//NWX包头
				try {
					processByNWX(packageData,udpAddress);
				} catch (Exception e) {
					e.printStackTrace();
					logger.debug("ControlProcess:"+StringHelper.getTrace(e));
				}
			}
		}
	}
	
	/**
	 * nwx处理
	 * @throws Exception 
	 */
	public void processByNWX(PackageData packageData,InetSocketAddress udpAddress) throws Exception{
		boolean isConfirm=false;
		HardwareElement element=UDPServerManager.getInstance().getChannelElement().get(packageData.getPhone());
		if(element==null){
			element=new HardwareElement();
			element.setUdpAddress(udpAddress);
			element.setPhone(packageData.getPhone());
			if(deviceLoginService.login(element)){//设备登录处理
				UDPServerManager.getInstance().getChannelElement().put(packageData.getPhone(),element);
			}
//			//登录成功
//			int id=deviceLoginLogService.save(new Object[]{new Date(),Constants.STATUS_NORMAL,udpAddress.getAddress().getHostAddress(),udpAddress.getPort(),DeviceLoginLog.TYPE_LOGIN,element.getPhone()}, 
//					"create_time,status,remote_ip,remote_port,type,dtu_id", DeviceLoginLog.TB_N);
//		    if(id>0){
//		    	 logger.info("登录成功Dtu id="+element.getPhone());
//		    }
		}
		if(element!=null){
			element.setUdpAddress(udpAddress);
		}
		//NWX,NWX上送开关量实时数据
		if(PackageCommandConstant.CMD_NWX_GET_OFFON_VALUE==packageData.getCommand()){
			if(packageData.getData().length==0){//试探包，相当于登录包
				isConfirm=true;
			}else{
				isConfirm=true;
				byte[] pData=new byte[12];
				//获取采集时间
				System.arraycopy(packageData.getData(), 3, pData, 0, 12);
				String dateStr=new String(pData,"ascii");
				System.out.println("采集时间:"+dateStr);
				Date caijitime=DateUtil.getDateStringByNWX(dateStr);//获取采集时间
				
				//起始通道号
				pData=new byte[1];
				System.arraycopy(packageData.getData(), 15, pData, 0, 1);
				System.out.println("起始通道号:"+pData[0]);
				
				//DTU与TPMS通信状态（1-正常，0-中断,注意不要写反了，与其他不同）
				pData=new byte[1];
				System.arraycopy(packageData.getData(), 16, pData, 0, 1);
				System.out.println("DTU与TPMS通信状态:"+pData[0]);
				int dtutpmsstatus=pData[0];//DTU与TPMS通信状态
				
				//DTU的工作状态（0表示汽车运行，1表示关闭）
				pData=new byte[1];
				System.arraycopy(packageData.getData(), 17, pData, 0, 1);
				System.out.println("DTU的工作状态:"+pData[0]);
				int dtustatus=pData[0];//DTU的工作状态
				
				//GPS的定位状态（0表示有效定位，1表示无效定位）
				pData=new byte[1];
				System.arraycopy(packageData.getData(), 18, pData, 0, 1);
				System.out.println("GPS的定位状态:"+pData[0]);
				int gpsstatus=pData[0];//GPS的定位状态
				int j=0;
				int i=0;
//				List<TyreOffonVO> offonVOs=new ArrayList<TyreOffonVO>();
				List<TyreOffonVO> offonVOs=element.getDeviceOffonByDtuVO().getOffon_value();
				offonVOs.clear();
				TyreOffonVO vo;
				for(i=18+1;i+7<=packageData.getData().length;i=i+7){
					vo=new TyreOffonVO();
					j++;
					vo.setNo(j);
					//轮胎急漏气告警（0表示无告警，1表示有告警）
					System.arraycopy(packageData.getData(), i, pData, 0, 1);
					System.out.println(j+"号轮胎急漏气告警:"+pData[0]);
					vo.setLouqi((int)pData[0]);
					
					//轮胎高压告警（0表示无告警，1表示有告警）
					System.arraycopy(packageData.getData(), i+1, pData, 0, 1);
					System.out.println(j+"号轮胎高压告警:"+pData[0]);
					vo.setGaoya((int)pData[0]);
					
					//轮胎低压告警（0表示无告警，1表示有告警）
					System.arraycopy(packageData.getData(), i+2, pData, 0, 1);
					System.out.println(j+"号轮胎低压告警:"+pData[0]);
					vo.setDiya((int)pData[0]);
					
					//轮胎高温告警（0表示无告警，1表示有告警）
					System.arraycopy(packageData.getData(), i+3, pData, 0, 1);
					System.out.println(j+"号轮胎高温告警:"+pData[0]);
					vo.setGaowen((int)pData[0]);
					
					//设备电池低电（0表示无告警，1表示有告警）
					System.arraycopy(packageData.getData(), i+4, pData, 0, 1);
					System.out.println(j+"号轮胎设备电池低电:"+pData[0]);
					vo.setDianchi((int)pData[0]);
					
					//设备发射器低电池告警（0表示无告警，1表示有告警）
					System.arraycopy(packageData.getData(), i+5, pData, 0, 1);
					System.out.println(j+"号轮胎发射器低电池告警:"+pData[0]);
					vo.setFasheqidianchi((int)pData[0]);
					
					//设备发射器发射中断告警（0表示无告警，1表示有告警）
					System.arraycopy(packageData.getData(), i+6, pData, 0, 1);
					System.out.println(j+"号轮胎发射器发射中断告警:"+pData[0]);
					vo.setFasheqizhongduan((int)pData[0]);
					offonVOs.add(vo);
				}
				//JSONArray.fromObject(offonVOs).toString()
//				System.out.println("i="+i+",length="+packageData.getData().length);
				//保存开关量
//				int id=deviceOffonService.save(new Object[]{element.getPhone(),new Date(),caijitime,dtutpmsstatus,
//						dtustatus,gpsstatus,JSONArray.fromObject(offonVOs).toString()}, 
//						"dtu_id,create_time,caiji_time,dtu_tpms_status,dtu_status,gps_status,offon_value", DeviceOffon.TB_N);
//			    if(id>0){
//			    	 logger.info("保存开关量成功Dtu id="+element.getPhone());
//			    }	
			    element.getDeviceOffonByDtuVO().getCaiji_time().setTime(caijitime.getTime());
			    element.getDeviceOffonByDtuVO().getCreate_time().setTime(System.currentTimeMillis());
			    element.getDeviceOffonByDtuVO().setDtu_tpms_status(dtutpmsstatus);
			    element.getDeviceOffonByDtuVO().setDtu_status(dtustatus);
			    element.getDeviceOffonByDtuVO().setGps_status(gpsstatus);
			    element.setFlagOffon(true);
			}
			
		}
		//NWX上送float格式模拟量实时数据
        else if(PackageCommandConstant.CMD_NWX_GET_REALTIME_VALUE==packageData.getCommand()){
        	isConfirm=true;
        	byte[] pData=new byte[12];
			//获取采集时间
			System.arraycopy(packageData.getData(), 3, pData, 0, 12);
			String dateStr=new String(pData,"ascii");
			System.out.println("采集时间:"+dateStr);
			Date caijitime=DateUtil.getDateStringByNWX(dateStr);//获取采集时间
			
			//起始通道号
			pData=new byte[1];
			System.arraycopy(packageData.getData(), 15, pData, 0, 1);
			System.out.println("起始通道号:"+pData[0]);
			
			int j=4;
			int i=0;
			pData=new byte[4];
			
			System.arraycopy(packageData.getData(), 16, pData, 0, 4);
			float f=Float.intBitsToFloat((( (pData[3] & 0xFF) << 24) + ((pData[2] & 0xFF) << 16) + ((pData[1] & 0xFF) << 8) + (pData[0] & 0xFF)));
			int dtutpmsstatus=(int)f;//DTU与TPMS通信状态
			
			System.arraycopy(packageData.getData(), 20, pData, 0, 4);
			f=Float.intBitsToFloat((( (pData[3] & 0xFF) << 24) + ((pData[2] & 0xFF) << 16) + ((pData[1] & 0xFF) << 8) + (pData[0] & 0xFF)));
			float tpms_pinlu=f;//TPMS发送频率(单位：分钟，可配置)
			
			System.arraycopy(packageData.getData(), 24, pData, 0, 4);
			f=Float.intBitsToFloat((( (pData[3] & 0xFF) << 24) + ((pData[2] & 0xFF) << 16) + ((pData[1] & 0xFF) << 8) + (pData[0] & 0xFF)));
			float dimian_sulu=f;//TPMS发送频率(单位：分钟，可配置)
			
			System.arraycopy(packageData.getData(), 28, pData, 0, 4);
			f=Float.intBitsToFloat((( (pData[3] & 0xFF) << 24) + ((pData[2] & 0xFF) << 16) + ((pData[1] & 0xFF) << 8) + (pData[0] & 0xFF)));
			float dimian_hangxiang=f;//地面航向（单位：度）
			
			float gao_wen_bao_jing_set=0;//高温报警设置（单位摄氏度）
//			List<TyreFloatYaliSetVO> floatSetVOs=new ArrayList<TyreFloatYaliSetVO>();
			List<TyreFloatYaliSetVO> floatSetVOs=element.getDeviceFloatByDtuVO().getYali_set();
			TyreFloatYaliSetVO setVo;
//			List<TyreFloatWenduYaliVO> floatVOs=new ArrayList<TyreFloatWenduYaliVO>();
			List<TyreFloatWenduYaliVO> floatVOs=element.getDeviceFloatByDtuVO().getWendu_yali_value();
			TyreFloatWenduYaliVO vo;
			floatSetVOs.clear();
			floatVOs.clear();
			for(i=28+4;i+4<=packageData.getData().length;i=i+4){
				j++;
				//轮胎急漏气告警（0表示无告警，1表示有告警）
				System.arraycopy(packageData.getData(), i, pData, 0, 4);
				f=Float.intBitsToFloat((( (pData[3] & 0xFF) << 24) + ((pData[2] & 0xFF) << 16) + ((pData[1] & 0xFF) << 8) + (pData[0] & 0xFF)));
				  System.out.println("通道"+j+",值:"+f);
				if(j<13){//轴高压设置 低压设置
					setVo=new TyreFloatYaliSetVO();
					setVo.setZhouno((j-3)/2);
					setVo.setH(f);
					
					j++;
					i+=4;
					System.arraycopy(packageData.getData(), i, pData, 0, 4);
					f=Float.intBitsToFloat((( (pData[3] & 0xFF) << 24) + ((pData[2] & 0xFF) << 16) + ((pData[1] & 0xFF) << 8) + (pData[0] & 0xFF)));
					System.out.println("通道"+j+",值:"+f);
					setVo.setL(f);
					floatSetVOs.add(setVo);
				}
				else if(j==13){//高温报警设置（单位摄氏度）
					System.arraycopy(packageData.getData(), i, pData, 0, 4);
					gao_wen_bao_jing_set=f;
				}else{
					vo=new TyreFloatWenduYaliVO();
					vo.setNo((j-12)/2);
					vo.setYali(f);
					
					j++;
					i+=4;
					System.arraycopy(packageData.getData(), i, pData, 0, 4);
					f=Float.intBitsToFloat((( (pData[3] & 0xFF) << 24) + ((pData[2] & 0xFF) << 16) + ((pData[1] & 0xFF) << 8) + (pData[0] & 0xFF)));
					System.out.println("通道"+j+",值:"+f);
					vo.setWendu(f);
					floatVOs.add(vo);
				}
			}
			//保存float格式模拟量实时数据
//			int id=deviceFloatService.save(new Object[]{element.getPhone(),new Date(),caijitime,dtutpmsstatus,
//					tpms_pinlu,dimian_sulu,dimian_hangxiang,gao_wen_bao_jing_set,
//					JSONArray.fromObject(floatSetVOs,JsonConfigUtil.getJsonFloatValueFormatConfig()).toString(),
//					JSONArray.fromObject(floatVOs,JsonConfigUtil.getJsonFloatValueFormatConfig()).toString()}, 
//					"dtu_id,create_time,caiji_time,dtu_tpms_status,tpms_pinlu,dimian_sulu,dimian_hangxiang,gao_wen_bao_jing_set,yali_set,wendu_yali_value", DeviceFloat.TB_N);
//		    if(id>0){
//		    	 logger.info("保存float格式数据成功Dtu id="+element.getPhone());
//		    }
		    element.getDeviceFloatByDtuVO().getCaiji_time().setTime(caijitime.getTime());
		    element.getDeviceFloatByDtuVO().getCreate_time().setTime(System.currentTimeMillis());
		    element.getDeviceFloatByDtuVO().setDtu_tpms_status(dtutpmsstatus);
		    element.getDeviceFloatByDtuVO().setTpms_pinlu((int)tpms_pinlu);
		    element.getDeviceFloatByDtuVO().setDimian_sulu(dimian_sulu);
		    element.getDeviceFloatByDtuVO().setDimian_hangxiang(dimian_hangxiang);
		    element.getDeviceFloatByDtuVO().setGao_wen_bao_jing_set(gao_wen_bao_jing_set);
		    element.setFlagFloat(true);
		    
			System.out.println("NWX上送模拟量实时数据");
		}
		//NWX上送字符串格式模拟量实时数据
		else if(PackageCommandConstant.CMD_NWX_GET_STRING_REALTIME_VALUE==packageData.getCommand()){
			isConfirm=true;
			byte[] pData=new byte[12];
			//获取采集时间
			System.arraycopy(packageData.getData(), 3, pData, 0, 12);
			String dateStr=new String(pData,"ascii");
			System.out.println("采集时间:"+dateStr);
			Date caijitime=DateUtil.getDateStringByNWX(dateStr);//获取采集时间
			
			//起始通道号
			pData=new byte[1];
			System.arraycopy(packageData.getData(), 15, pData, 0, 1);
			System.out.println("起始通道号:"+pData[0]);
			
			byte[] strData=new byte[packageData.getData().length-16];
			System.arraycopy(packageData.getData(), 16, strData, 0, strData.length);
			String str=new String(strData, "utf-8");
			System.out.println("NWX上送字符串格式模拟量实时数据各通道:"+str);
			String[] strArr=str.split(",");
			int dtutpmsstatus=Integer.parseInt(strArr[0]);//DTU与TPMS通信状态
			String yuliu1_phone=strArr[1];//预留拖卡1的手机号码
			String yuliu2_phone=strArr[2];//预留拖卡2的手机号码
			String yuliu3_phone=strArr[3];//预留拖卡3的手机号码
			String yuliu4_phone=strArr[4];//预留拖卡4的手机号码
			String dtu_no=strArr[5];//预留拖卡4的手机号码
			String trucks_id=strArr[6];//车牌
			String sim=strArr[7];//sim
			double latitude=Double.parseDouble(strArr[8]);//纬度
			int latitude_type=Integer.parseInt(strArr[9]);//北纬或南纬(0表示北纬,1表示南纬)
			double longitude=Double.parseDouble(strArr[10]);//经度
			int longitude_type=Integer.parseInt(strArr[11]);//东经或西经(0表示东经,1表示西经)
			int j=0;
//		    List<TyreStringVO> vos=new ArrayList<TyreStringVO>();
			List<TyreStringVO> vos=element.getDeviceStringByDtuVO().getFasheqi_ids_value();
		    TyreStringVO vo;
		    vos.clear();
			for(int i=12;i<strArr.length;i++){
				vo=new TyreStringVO();
				j++;
				vo.setNo(j);
				vo.setFasheqiid(strArr[i]);
				vos.add(vo);
			}
			//保存字符串格式模拟量
//			int id=deviceStringService.save(new Object[]{element.getPhone(),new Date(),caijitime,dtutpmsstatus,
//					yuliu1_phone,yuliu2_phone,yuliu3_phone,yuliu4_phone,
//					dtu_no,trucks_id,sim,latitude,latitude_type,longitude,longitude_type,
//					JSONArray.fromObject(vos).toString()}, 
//					"dtu_id,create_time,caiji_time,dtu_tpms_status,yuliu1_phone,yuliu2_phone,yuliu3_phone,yuliu4_phone,dtu_no,dtu_trucks_id,sim,latitude,latitude_type,longitude,longitude_type,fasheqi_ids_value", DeviceString.TB_N);
//		    if(id>0){
//		    	 logger.info("保存字符串格式模拟量成功Dtu id="+element.getPhone());
//		    }
		    
		    element.getDeviceStringByDtuVO().getCaiji_time().setTime(caijitime.getTime());
		    element.getDeviceStringByDtuVO().getCreate_time().setTime(System.currentTimeMillis());
		    element.getDeviceStringByDtuVO().setDtu_tpms_status(dtutpmsstatus);
		    element.getDeviceStringByDtuVO().setYuliu1_phone(yuliu1_phone);
		    element.getDeviceStringByDtuVO().setYuliu2_phone(yuliu2_phone);
		    element.getDeviceStringByDtuVO().setYuliu3_phone(yuliu3_phone);
		    element.getDeviceStringByDtuVO().setYuliu4_phone(yuliu4_phone);
		    element.getDeviceStringByDtuVO().setDtu_no(dtu_no);
		    element.getDeviceStringByDtuVO().setDtu_trucks_id(trucks_id);
		    element.getDeviceStringByDtuVO().setSim(sim);
		    element.getDeviceStringByDtuVO().setLatitude(latitude);
		    element.getDeviceStringByDtuVO().setLatitude_type(latitude_type);
		    element.getDeviceStringByDtuVO().setLongitude(longitude);
		    element.getDeviceStringByDtuVO().setLongitude_type(longitude_type);
		    element.setFlagString(true);
		    //保存数据
		    trucksDeviceService.saveDeviceData(element);
		    
			
		}
		//服务器操作DTU控制回复（DTU到服务器）
		else if(PackageCommandConstant.CMD_NWX_GET_OPERATION_DTU_REPLY==packageData.getCommand()){
			byte[] pData=new byte[2];
			//获取控制命令帧序号
			System.arraycopy(packageData.getData(), 0, pData, 0, 2);
			//命令帧序号
			int nos= (((int)(pData[0] & 0xFF) << 8) + (int)(pData[1] & 0xFF));
			
			pData=new byte[1];
			System.arraycopy(packageData.getData(), 2, pData, 0, 1);
			DtuOperation dtuOperation=SendManager.getInstance().getDtuOperationByDtuIdNo(packageData.getPhone(), nos);
			int type=DeviceOperationLog.TYPE_FAIL;
			if(pData[0]!=0){//服务器操作DTU成功
				type=DeviceOperationLog.TYPE_SUCCESS;
			}
			if(dtuOperation!=null){
				dtuOperation.setType(type);
			}
			logger.info("服务器控制DTU回复Dtu id="+element.getPhone());
		}
		//DTU心跳信号（DTU到服务器）,不需要回复
		else if(PackageCommandConstant.CMD_NWX_GET_HEART_TO_SERVER==packageData.getCommand()){
			element.setLastConnectHeartTime(System.currentTimeMillis());
			logger.info("DTU到服务器心跳包Dtu id="+element.getPhone());
		}
//		element.setLastConnectHeartTime(System.currentTimeMillis());
		if(isConfirm){
			int flag=HardwareSendManager.getInstance().sendGetConfirm(element, packageData.getCommand(), packageData.getNo());//发送确认收到包
			System.out.println("发送确认收到包返回状态:"+flag);
			logger.info("服务器发送确认收到包Dtu id="+element.getPhone());
		}
	}

}
