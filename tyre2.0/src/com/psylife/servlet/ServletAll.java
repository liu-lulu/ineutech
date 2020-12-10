package com.psylife.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gexin.fastjson.JSON;
import com.psylife.dao.impl.TrucksDeviceDaoImpl;
import com.psylife.entity.DeviceOperationLog;
import com.psylife.entity.DrivingRecord;
import com.psylife.entity.LoginLog;
import com.psylife.entity.TyreBase;
import com.psylife.entity.TyrePattern;
import com.psylife.entity.TyrePattern2;
import com.psylife.entity.User;
import com.psylife.entity.VersionBean;
import com.psylife.entity.WorkOrder;
import com.psylife.hardware.DtuOperation;
import com.psylife.hardware.HardwareElement;
import com.psylife.hardware.UDPServerManager;
import com.psylife.hardware.process.DtuOperationProcess;
import com.psylife.hardware.send.HardwareSendManager;
import com.psylife.hardware.send.SendManager;
import com.psylife.service.DeviceOperationLogService;
import com.psylife.service.DrivingRecordService;
import com.psylife.service.LoginLogService;
import com.psylife.service.SpecificationService;
import com.psylife.service.TrucksDeviceService;
import com.psylife.service.TrucksService;
import com.psylife.service.TyreBaseService;
import com.psylife.service.TyreHistoryService;
import com.psylife.service.TyrePatternService;
import com.psylife.service.UserService;
import com.psylife.service.VersionService;
import com.psylife.service.WorkOrderService;
import com.psylife.service.impl.DeviceOperationLogServiceImpl;
import com.psylife.service.impl.DrivingRecordServiceImpl;
import com.psylife.service.impl.LoginLogServiceImpl;
import com.psylife.service.impl.SpecificationServiceImpl;
import com.psylife.service.impl.TrucksDeviceServiceImpl;
import com.psylife.service.impl.TrucksServiceImpl;
import com.psylife.service.impl.TyreBaseServiceImpl;
import com.psylife.service.impl.TyreHistoryServiceImpl;
import com.psylife.service.impl.TyrePatternServiceImpl;
import com.psylife.service.impl.UserServiceImpl;
import com.psylife.service.impl.VersionServiceImpl;
import com.psylife.service.impl.WorkOrderServiceImpl;
import com.psylife.util.DateUtil;
import com.psylife.util.JsonConfigUtil;
import com.psylife.util.StringHelper;
import com.psylife.util.UplodImage;
import com.psylife.vo.TreadPattern;
import com.psylife.vo.TrucksByAdminVO;
import com.psylife.vo.TyreVO;

/**
 * app接口
 */
@WebServlet(urlPatterns = { "/ServletAll" }, loadOnStartup = 1)
public class ServletAll extends HttpServlet {
	public static final Logger logger = LoggerFactory.getLogger(ServletAll.class);
	
	private static final long serialVersionUID = 1L;
	

	private UserService usersService = new UserServiceImpl();
	
	private LoginLogService loginLogService= new LoginLogServiceImpl();
	
	private VersionService versionService=new VersionServiceImpl();
	
	private SpecificationService specificationService=new SpecificationServiceImpl();
	
	private TyreBaseService tyreBaseService=new TyreBaseServiceImpl();
	
	private TyrePatternService tyrePatternService=new TyrePatternServiceImpl();
	
	private TrucksService trucksService=new TrucksServiceImpl();
	
	private TyreHistoryService tyreHistoryService=new TyreHistoryServiceImpl();
	
	private TrucksDeviceService trucksDeviceService=new TrucksDeviceServiceImpl();
	
	private DeviceOperationLogService deviceOperationLogService=new DeviceOperationLogServiceImpl();
	
	private DrivingRecordService drivingRecordService=new DrivingRecordServiceImpl();
	
	private WorkOrderService workOrderService=new WorkOrderServiceImpl();
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAll() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		
		//1.登录
		if ("login".equals(action)) {//登录
			JSONObject jsonObject;
			String loginName = request.getParameter("name");//名字
			String password = request.getParameter("password");//密码
			String type=request.getParameter("type");//登录类型
			logger.info("用户登录——用户名："+loginName+"&&密码："+password);
			if (!StringHelper.isEmptyObject(loginName)&&!StringHelper.isEmptyObject(password)) {
				int userRole=User.USER_ROLE_ADMIN;//角色
				if(type!=null&&!"".equals(type)){
					userRole=Integer.valueOf(type);
				}
				User user=usersService.login(loginName, password);
				if(user!=null&&user.getUser_role()==userRole){
					jsonObject = JSONObject.fromObject(user,JsonConfigUtil.getJsonDateValueFormatConfig());
					out.print("{\"state\":0,\"user\":"+jsonObject.toString()+"}");
					LoginLog loginLog=new LoginLog();
					loginLog.setUserId(user.getUser_id());
					loginLog.setIpAddress(request.getRemoteAddr());
					loginLog.setRemark(request.getParameter("remark"));
					loginLogService.insertLog(loginLog);
				}else{
					System.out.println("用户登录失败——原因:用户名或密码错误！");
					out.print("{\"state\":1}");
				}
			}else{
				logger.info("用户登录失败——原因:用户名或密码为空！");
				out.print("{\"state\":2}");
			}
		}
		
		//2.获取轮胎规格列表
		else if("getSpecificationList".equals(action)){//获取轮胎规格列表
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(specificationService.getSpecificationList()));
			out.print(jsonObject.toString());
		}
		
		//3.获取轮胎品牌列表
		else if("getTyreBrandList".equals(action)){//获取轮胎品牌列表
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(specificationService.getTyreBrandList()));
			out.print(jsonObject.toString());
		}
		
		//4.根据用户获取胎汇总列表
		else if("countTyreInfo".equals(action)){//根据用户获取胎汇总列表
			int user_id = Integer.valueOf(request.getParameter("user_id"));
			String tyre_brand=request.getParameter("tyre_brand");
			String tyre_type1=request.getParameter("tyre_type1");
			String tyre_type3=request.getParameter("tyre_type3");
			String tyre_healths=request.getParameter("tyre_health");
			String column = request.getParameter("column");
			String order = request.getParameter("order");
//			int pagenum=Integer.valueOf(request.getParameter("pagenum"));
			Integer tyre_health=(tyre_healths==null||"".equals(tyre_healths)?null:Integer.valueOf(tyre_healths));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(tyreBaseService.countTyreInfo(user_id, tyre_brand, tyre_type1,tyre_type3,tyre_health,column,order)));
			out.print(jsonObject.toString());
		}	
		
		//5.根据用户品牌规格花纹获取胎列表
		else if("getTyreList".equals(action)){//根据用户品牌规格花纹获取胎列表
			int user_id = Integer.valueOf(request.getParameter("user_id"));
			int pagenum=Integer.valueOf(request.getParameter("pagenum"));
			String tyre_brand=request.getParameter("tyre_brand");
			String tyre_type1=request.getParameter("tyre_type1");
			String tyre_type2=request.getParameter("tyre_type2");
			String tyre_type3=request.getParameter("tyre_type3");
			String tyre_flags=request.getParameter("tyre_flag");
			String tyre_healths=request.getParameter("tyre_health");
			String keyWord=request.getParameter("keyWord");
			Integer tyre_flag=(tyre_flags==null||"".equals(tyre_flags)?null:Integer.valueOf(tyre_flags));
			Integer tyre_health=(tyre_healths==null||"".equals(tyre_healths)?null:Integer.valueOf(tyre_healths));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(tyreBaseService.getTyreList(pagenum, user_id, tyre_brand, tyre_type1,tyre_type2, tyre_type3,tyre_flag,tyre_health,keyWord),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}
		
		//6.轮胎详情管理员
		else if("tyreDetialByAdmin".equals(action)){//轮胎详情管理员
			String tyre_id = request.getParameter("tyre_id");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONObject.fromObject(tyreBaseService.tyreDetialByAdmin(tyre_id),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}
		
		//7.根据用户获取车辆列表
		else if("searchTrucksList".equals(action)){//根据用户获取车辆列表
			int user_id = Integer.valueOf(request.getParameter("user_id"));
			int pagenum=Integer.valueOf(request.getParameter("pagenum"));
			String trucks_flags=request.getParameter("trucks_flag");//状态
			String trucks_healths=request.getParameter("trucks_health");//健康
			String trucks_type=request.getParameter("trucks_type");//类型--主车/挂车
			String keyWord=request.getParameter("keyWord");//关键字查询
			String transport_type=request.getParameter("transport_type");//运输类型,分别：危险品、快递、公交、冷链、客运、其他
			String column = request.getParameter("column");
			String order = request.getParameter("order");
			
			Integer trucks_flag=(trucks_flags==null||"".equals(trucks_flags)?null:Integer.valueOf(trucks_flags));
			Integer trucks_health=(trucks_healths==null||"".equals(trucks_healths)?null:Integer.valueOf(trucks_healths));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(trucksService.searchByList(pagenum, user_id, trucks_flag, trucks_health, trucks_type, keyWord,transport_type,column,order)));
			out.print(jsonObject.toString());
		}
		
		//8.根据车牌号获取车信息
		else if("getByTrucks_id".equals(action)){//根据车牌号获取车信息
			String trucks_id=request.getParameter("trucks_id");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			if(trucks_id==null||"".equals(trucks_id)){
				jsonObject.put("info", JSONObject.fromObject(null));
			}else{
				jsonObject.put("info", JSONObject.fromObject(trucksService.getByTrucks_id(trucks_id),JsonConfigUtil.getJsonDateValueFormatConfig()));
			}
			out.print(jsonObject.toString());
		}
		
		//9.获取最近一次驾驶记录
		else if("getLastDrivingRecord".equals(action)){//获取最近一次驾驶记录
			int user_id = Integer.valueOf(request.getParameter("user_id"));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONObject.fromObject(drivingRecordService.getLastDrivingRecord(user_id),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}
		
		//10.根据用户获取架驶记录列表
		else if("getDrivingRecordList".equals(action)){//根据用户获取架驶记录列表
			int user_id = Integer.valueOf(request.getParameter("user_id"));
			int pagenum=Integer.valueOf(request.getParameter("pagenum"));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(drivingRecordService.getDrivingRecordList(pagenum, user_id),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}
		
		//11.开始架驶
		else if("startDriving".equals(action)){
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			DrivingRecord drivingRecord=new DrivingRecord();
			drivingRecord.setDriver_id(user_id);
			drivingRecord.setTrucks_id(request.getParameter("trucks_id"));
			drivingRecord.setFrom_adress(request.getParameter("from_adress"));
			drivingRecord.setTo_adress(request.getParameter("to_adress"));
			drivingRecord.setStart_time(DateUtil.parseDateTime(request.getParameter("start_time")));
			drivingRecord.setTransport_type(request.getParameter("transport_type"));
			drivingRecord.setLi_cheng_run(Double.valueOf(request.getParameter("mabao")));
			drivingRecord.setGuache_trucks_id(request.getParameter("guache_trucks_id"));
			JSONObject jsonObject=new JSONObject();
			int state=drivingRecordService.startDriving(drivingRecord);//0成功,1车不存在,2码表数不能低于原本的码表数,3还正在开始，不能创建,4运输类型不一致,5挂车不存在,6挂车还在正在驾驶的其他车上,7其他
			jsonObject.put("state", state);
			if(state==3){
				jsonObject.put("info", JSONObject.fromObject(drivingRecordService.getLastDrivingRecordByTrucks_id(drivingRecord.getTrucks_id(), true),JsonConfigUtil.getJsonDateValueFormatConfig()));
			}else if(state==6){
				jsonObject.put("info", JSONObject.fromObject(drivingRecordService.getLastDrivingRecordByTrucks_id(drivingRecord.getGuache_trucks_id(), false),JsonConfigUtil.getJsonDateValueFormatConfig()));
			}
			out.print(jsonObject.toString());
		}
		
		//12.结束架驶
		else if("endDriving".equals(action)){
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			DrivingRecord drivingRecord=new DrivingRecord();
			drivingRecord.setDriver_id(user_id);
			drivingRecord.setLi_cheng_end(Double.valueOf(request.getParameter("mabao")));
			JSONObject jsonObject=new JSONObject();
			int state=drivingRecordService.endDriving(drivingRecord);//0成功,1没有要开始的,2结束码表数小于开始码表数,3.其他
			jsonObject.put("state", state);
			out.print(jsonObject.toString());
		}
		
		//13.架驶员评分
		else if("scoreDriving".equals(action)){
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			DrivingRecord drivingRecord=new DrivingRecord();
			drivingRecord.setDriver_id(user_id);
			drivingRecord.setStar_unobstructed(Integer.valueOf(request.getParameter("star_unobstructed")));
			drivingRecord.setStar_heart(Integer.valueOf(request.getParameter("star_heart")));
			JSONObject jsonObject=new JSONObject();
			int state=drivingRecordService.scoreDriving(drivingRecord);//0成功,1还未结束不能评分,2已评分,3没有架驶记录,4其他
			jsonObject.put("state", state);
			out.print(jsonObject.toString());
		}
		
		//14.获取驾驶员驾驶记录汇总
		else if("getDrivingRecordCount".equals(action)){//获取驾驶员驾驶记录汇总
			int user_id = Integer.valueOf(request.getParameter("user_id"));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONObject.fromObject(drivingRecordService.getDrivingRecordCount(user_id)));
			out.print(jsonObject.toString());
		}
		
		//15.获取轮胎花纹代码列表
		else if("getPatternCodeList".equals(action)){//获取轮胎花纹代码列表
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(specificationService.getPatternCodeList()));
			out.print(jsonObject.toString());
		}
		
		//16.根据关键字获取胎列表
		else if("tyreListByKeyWord".equals(action)){//根据关键字获取胎列表
			int user_id = Integer.valueOf(request.getParameter("user_id"));
			int pagenum=Integer.valueOf(request.getParameter("pagenum"));
			String keyWord=request.getParameter("keyWord");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(tyreBaseService.searchByKeyWord(pagenum, user_id, keyWord),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}
		
		//16-1.根据关键字等获取胎库存列表
		else if("tyreListByKucun".equals(action)){//根据关键字等获取胎库存列表
			int user_id = Integer.valueOf(request.getParameter("user_id"));
			int pagenum=Integer.valueOf(request.getParameter("pagenum"));
			String keyWord=request.getParameter("keyWord");
			String states=request.getParameter("state");
			Integer state=((states==null||"".equals(states))?null:Integer.valueOf(states));
			String tyre_brand=request.getParameter("tyre_brand");
			String tyre_type1=request.getParameter("tyre_type1");
			String tyre_type3=request.getParameter("tyre_type3");
			String tyre_flags=request.getParameter("tyre_flag");
			Integer tyre_flag=((tyre_flags==null||"".equals(tyre_flags))?null:Integer.valueOf(tyre_flags));
			String column = request.getParameter("column");
			String order = request.getParameter("order");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(tyreBaseService.searchByKucun(pagenum, user_id, keyWord, tyre_brand, tyre_type1,tyre_type3,state,tyre_flag,column,order),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}
		
		//17.获取最近一次工单
		else if("getLastWorkOrder".equals(action)){//获取最近一次驾驶记录
			int user_id = Integer.valueOf(request.getParameter("user_id"));
			String flags=request.getParameter("flag");
			Integer flag=null;
			if(flags!=null&&!"".equals(flags)){
				flag=Integer.valueOf(flags);
			}
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONObject.fromObject(workOrderService.getLastWorkOrder(user_id, flag),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}		
		
		//18.创建工单,开始工单记录
		else if("createWorkOrder".equals(action)){
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			WorkOrder workOrder=new WorkOrder();
			workOrder.setUser_id(user_id);
			JSONObject jsonObject=new JSONObject();
			int state=workOrderService.createWorkOrder(workOrder);//0成功,1工单还未结束不能创建,2其他
			jsonObject.put("state", state);
			out.print(jsonObject.toString());
		}
		
		//19.结束工单
		else if("endWorkOrder".equals(action)){
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			WorkOrder workOrder=new WorkOrder();
			workOrder.setUser_id(user_id);
			JSONObject jsonObject=new JSONObject();
			int state=workOrderService.endWorkOrder(workOrder);//0成功,1没有要结束的工单,2其他
			jsonObject.put("state", state);
			out.print(jsonObject.toString());
		}
		
		//20.录入轮胎花纹深度及其他检测数据信息
		else if("saveTyrePatternItem".equals(action)){//录入轮胎花纹深度及其他检测数据信息
			TyrePattern2 tyrePattern2=null;
			Integer user_id = Integer.valueOf(request.getParameter("user_id"));
			String anslistJsonEcod = request.getParameter("tyrePattern");
			if(anslistJsonEcod!=null&&!"".equals(anslistJsonEcod)){
				String anslistJson = URLDecoder.decode(anslistJsonEcod, "utf-8");
				JSONObject jsonarray = JSONObject.fromObject(anslistJson);
				tyrePattern2=(TyrePattern2) JSONObject.toBean(jsonarray, TyrePattern2.class);
			}
			String remark = request.getParameter("remark");//温馨提示
			String item = request.getParameter("item");//检测项
			String tyre_id = request.getParameter("tyre_id");//胎号
			String mabiaos=request.getParameter("mabiao");//码表数
			Double mabiao=(mabiaos==null||"".equals(mabiaos)?null:Double.valueOf(mabiaos));
			JSONObject jsonObject=new JSONObject();
			String flags = request.getParameter("flag");
			int flag=(flags==null||"".equals(flags)?1:Integer.valueOf(flags));
			int state=tyrePatternService.saveTyrePatternItem(tyrePattern2, user_id, flag, tyre_id, item, remark,request.getParameter("repaircontent"),mabiao);
			jsonObject.put("state", state);//0成功,1失败,10没有工单或已结束,3其他
			out.print(jsonObject.toString());
		}
		
		//21.批量保存轮胎基本信息
		else if("saveTyreBaseByList".equals(action)){//批量保存轮胎基本信息
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			//System.out.println(company_id);
			String tyres = request.getParameter("tyres");
			tyres=URLDecoder.decode(tyres, "utf-8");
			JSONArray jsonArray=JSONArray.fromObject(tyres);
			@SuppressWarnings("unchecked")
			List<TyreBase> tyreBases=(List<TyreBase>)JSONArray.toCollection(jsonArray, TyreBase.class);
			
			boolean exist=false;//检测轮胎是否已存库,只要有一个已入库,则所有轮胎都不入库
			JSONObject jsonObject=new JSONObject();
			for (int i=0;i<tyreBases.size();i++) {
				TyreVO tyre = tyreBaseService.tyreDetial(user_id, tyreBases.get(i).getTyre_id(), null, null);
				if (tyre!=null) {
					exist=true;
					jsonObject.put("state", 3);
					jsonObject.put("info", tyreBases.get(i).getTyre_id());
					break;
				}
			}
			if (!exist) {
				int state=tyreBaseService.saveByList(tyreBases, user_id,true);
				jsonObject.put("state", state);	
			}
			out.print(jsonObject.toString());
		}
		
		//21.批量保存轮胎基本信息入库工具专用
		else if("saveTyreBaseByListTool".equals(action)){//批量保存轮胎基本信息
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			//System.out.println(company_id);
			String tyres = request.getParameter("tyres");
			tyres=URLDecoder.decode(tyres, "utf-8");
			JSONArray jsonArray=JSONArray.fromObject(tyres);
			@SuppressWarnings("unchecked")
			List<TyreBase> tyreBases=(List<TyreBase>)JSONArray.toCollection(jsonArray, TyreBase.class);
			
			boolean exist=false;//检测轮胎是否已存库,只要有一个已入库,则所有轮胎都不入库
			JSONObject jsonObject=new JSONObject();
			for (int i=0;i<tyreBases.size();i++) {
				TyreVO tyre = tyreBaseService.tyreDetial(user_id, tyreBases.get(i).getTyre_id(), null, null);
				if (tyre!=null) {
					exist=true;
					jsonObject.put("state", 3);
					jsonObject.put("info", tyreBases.get(i).getTyre_id());
					break;
				}
			}
			if (!exist) {
				int state=tyreBaseService.saveByList(tyreBases, user_id,false);
				jsonObject.put("state", state);
			}
			out.print(jsonObject.toString());
		}
		
		//22.轮胎详情  根据轮胎号或车牌胎位置
		else if("tyreDetial".equals(action)){//轮胎详情  根据轮胎号或车牌胎位置
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			String tyre_id = request.getParameter("tyre_id");
			String trucks_id = request.getParameter("trucks_id");
			String tyre_where = request.getParameter("tyre_where");
			if(tyre_id!=null&&!"".equals(tyre_id)){
				trucks_id=null;
				tyre_where=null;
			}else{
				tyre_id=null;
			}
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONObject.fromObject(tyreBaseService.tyreDetial(user_id, tyre_id, trucks_id, tyre_where),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}
		
		//23.胎装载
		else if("tyreToTrucks".equals(action)){//胎装载
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			String trucks_id = request.getParameter("trucks_id");
			String tyre_id = request.getParameter("tyre_id");
			String updateColName = request.getParameter("updateColName");
			String mabiaos=request.getParameter("mabiao");//码表数
			Double mabiao=(mabiaos==null||"".equals(mabiaos)?null:Double.valueOf(mabiaos));
			JSONObject jsonObject=new JSONObject();
			int state=tyreBaseService.tyreToTrucks(trucks_id, tyre_id, updateColName, user_id,mabiao);//0成功,1车牌不存在,2该位置存在胎,3保存失败,4轮胎不存在,5保存胎日志失败,6其他
			//重新加载设备和车牌对应关系
			if(state==0){
				HardwareElement element=SendManager.getInstance().getHardwareElementByCarNum(trucks_id);
				if(element!=null){
					trucksDeviceService.reloadTrucksDevice(element);
				}
			}
			jsonObject.put("state", state);
			out.print(jsonObject.toString());
		}		
		
		//24.胎卸下
		else if("tyreDown".equals(action)){//胎卸下
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			String trucks_id = request.getParameter("trucks_id");
			String tyre_id = request.getParameter("tyre_id");
			String updateColName = request.getParameter("updateColName");
			String mabiaos=request.getParameter("mabiao");//码表数
			Double mabiao=(mabiaos==null||"".equals(mabiaos)?null:Double.valueOf(mabiaos));
			JSONObject jsonObject=new JSONObject();
			int state=tyreBaseService.tyreDown(trucks_id, tyre_id, updateColName, user_id,mabiao);//0成功,1不存在车牌或位置上没有此胎,2保存失败,3保存胎日志失败,4其他
			//重新加载设备和车牌对应关系
			if(state==0){
				HardwareElement element=SendManager.getInstance().getHardwareElementByCarNum(trucks_id);
				if(element!=null){
					trucksDeviceService.reloadTrucksDevice(element);
				}
			}
			jsonObject.put("state", state);
			out.print(jsonObject.toString());
		}
		
		//25.轮胎交换
		else if("tyreExchange".equals(action)){//轮胎交换
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			String tyre_id1 = request.getParameter("tyre_id1");
			String tyre_id2 = request.getParameter("tyre_id2");
			String mabiao1s=request.getParameter("mabiao1");//码表数
			Double mabiao1=(mabiao1s==null||"".equals(mabiao1s)?null:Double.valueOf(mabiao1s));//胎号1的码表数
			String mabiao2s=request.getParameter("mabiao2");//码表数
			Double mabiao2=(mabiao2s==null||"".equals(mabiao2s)?null:Double.valueOf(mabiao2s));//胎号2的码表数
			JSONObject jsonObject=new JSONObject();
			int state=tyreBaseService.tyreExchange(tyre_id1, tyre_id2, user_id,mabiao1,mabiao2);//0成功,1不存在两个胎信息,2两个胎都不在车上，交换没有意义,3保存失败,4其他
			if (state==0) {
				trucksService.updateTrucksHealthWhenTyreChange(tyre_id1, tyre_id2);
			}
			jsonObject.put("state", state);
			out.print(jsonObject.toString());
		}
		
		//26.获取工单列表
		else if("workOrderList".equals(action)){//获取工单列表
			int user_id = Integer.valueOf(request.getParameter("user_id"));
			int pagenum=Integer.valueOf(request.getParameter("pagenum"));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(workOrderService.workOrderList(pagenum, user_id),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}	
		
		//27.删除工单记录项
		else if("deleteWorkOrderRecord".equals(action)){
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			JSONObject jsonObject=new JSONObject();
			int state=workOrderService.deleteWorkOrderRecord(Integer.parseInt(request.getParameter("id")), user_id);//0成功,1工单已结束不能删除记录项,2不存在,3记录项已删除,4其他
			jsonObject.put("state", state);
			out.print(jsonObject.toString());
		}
		
		//28.根据工单id获取记录项
		else if("recordByWorkOrderId".equals(action)){//根据工单id获取记录项
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(workOrderService.recordByWorkOrderId(Integer.valueOf(request.getParameter("workOrderId"))),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}
		
		//29.个人信息修改
		else if("updateProfile".equals(action)){//个人信息修改
			JSONObject jsonObject=new JSONObject();
			Map<String, String> returnMap=new HashMap<String, String>();
			int state=UplodImage.updateProfile(request,returnMap);//0成功,-2文件过大,-1表单提出问题,1保存数据时不成功
			jsonObject.put("state", state);
			if(state==0&&returnMap.get("image")!=null){
				jsonObject.put("user_face", returnMap.get("image"));
			}
			out.print(jsonObject.toString());
		}
		
		//30.管理员车轮胎汇总个人页面
		else if("getUserCountByAdmin".equals(action)){//管理员车轮胎汇总个人页面
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONObject.fromObject(usersService.getUserCount(user_id)));
			out.print(jsonObject.toString());
		}
		
		//30-1.获取新消息推送
		else if("getMessageList".equals(action)){//获取新消息推送
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(usersService.getMessageList(user_id)));
			out.print(jsonObject.toString());
		}
		
		//31.温馨提示小贴士列表
		else if("tyreTips".equals(action)){//温馨提示小贴士列表
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(tyreBaseService.tyreTips(Integer.valueOf(request.getParameter("user_id")),Integer.valueOf(request.getParameter("pagenum"))),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}			
		
		//32.查询轮胎轨迹列表
		else if("getTyreHistoryListByTyreId".equals(action)){//查询轮胎轨迹列表
			int pagenum=Integer.valueOf(request.getParameter("pagenum"));
			String tyre_id=request.getParameter("tyre_id");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(tyreHistoryService.getTyreHistoryListByTyreId(pagenum, tyre_id),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}
		
		//33.胎,司机自行修补
		else if("tyreByDriverXiuBu".equals(action)){//胎,司机自行修补
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			String tyre_id = request.getParameter("tyre_id");
			JSONObject jsonObject=new JSONObject();
			int state=tyreBaseService.tyreByDriverXiuBu(tyre_id, user_id);//0成功,1轮胎不存在,2保存失败,3其他
			jsonObject.put("state", state);
			out.print(jsonObject.toString());
		}
		
		//34.更新车码表数
		else if("goUpdateTrucksMabiao".equals(action)){
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			Double mabiao=Double.valueOf(request.getParameter("mabiao"));
			String trucks_id=request.getParameter("trucks_id");
			JSONObject jsonObject=new JSONObject();
			int state=trucksService.goUpdateTrucksMabiao(trucks_id, user_id, mabiao);//0成功,1车不存在或码表数小于当前值,2其他
			jsonObject.put("state", state);
			out.print(jsonObject.toString());
		}
		
		
		
		
		
		//android  版本检测0是没有新版本,1是有新版本
		else if ("AndroidCheckVersion".equals(action)) {
			VersionBean versionBean = this.versionService.getVersion(request.getParameter("appname"), "android");
			JSONObject jsonobject = JSONObject.fromObject(versionBean);
			out.print(jsonobject.toString());
		}
		
		//8.胎推荐
		else if("tyreTuiJian".equals(action)){//轮胎交换
			int id = Integer.parseInt(request.getParameter("company_id"));
			List<JSONObject> list=tyreBaseService.tuiJian(id);
			out.write(JSONArray.fromObject(list).toString());
		}
		//9.录入轮胎花纹深度
		else if("saveTyrePattern".equals(action)){//录入轮胎花纹深度
			String  tyre_id=request.getParameter("tyre_id");
			int state;
			if(tyre_id==null||"".equals(tyre_id)){
				state=2;
			}else{
				int user_id = Integer.parseInt(request.getParameter("user_id"));
				String  tyre_p1=request.getParameter("tyre_p1");
				String  tyre_p2=request.getParameter("tyre_p2");
				String  tyre_p3=request.getParameter("tyre_p3");
				String  tyre_p4=request.getParameter("tyre_p4");
				String  tyre_p5=request.getParameter("tyre_p5");
				String  tyre_p6=request.getParameter("tyre_p6");
				String  tyre_paver=request.getParameter("tyre_paver");
				
				TyrePattern tp=new TyrePattern();
				tp.setTyre_id(tyre_id);
				tp.setTyre_p1(Float.parseFloat(tyre_p1));
				tp.setTyre_p2(Float.parseFloat(tyre_p2));
				tp.setTyre_p3(Float.parseFloat(tyre_p3));
				tp.setTyre_p4(Float.parseFloat(tyre_p4));
				tp.setTyre_p5(Float.parseFloat(tyre_p5));
				tp.setTyre_p6(Float.parseFloat(tyre_p6));
				tp.setTyre_paver(Float.parseFloat(tyre_paver));
				tp.setUser_id(user_id);
				
				state=tyrePatternService.saveTyrePattern(tp);
			}
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", state);//0成功,1胎不存在,2保存失败,3其他
			out.print(jsonObject.toString());
		}
		
		
		
		
		//17.花纹深度测试,车列表
		else if("trucksListByPattern".equals(action)){//花纹深度测试,车列表
			String id = request.getParameter("company_id");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(trucksService.trucksListByPattern(Integer.valueOf(id)),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}
		//18.批量录入轮胎花纹深度及其他信息
		else if("saveTyrePatternList".equals(action)){//批量录入轮胎花纹深度及其他信息
			Integer user_id = Integer.valueOf(request.getParameter("user_id"));
			String anslistJsonEcod = request.getParameter("tyrePatternlist");
			String anslistJson = URLDecoder.decode(anslistJsonEcod, "utf-8");
			JSONArray jsonarray = JSONArray.fromObject(anslistJson);
			JSONObject jsonObject=new JSONObject();
			
			String flags = request.getParameter("flag");
			int flag=(flags==null||"".equals(flags)?1:Integer.valueOf(flags));
			
			if (anslistJson == null) {
				jsonObject.put("state", 1);
			}else{
				jsonObject.put("state", 0);
				@SuppressWarnings("unchecked")
				List<TyrePattern2> anslist = (List<TyrePattern2>) JSONArray.toCollection(
						jsonarray, TyrePattern2.class);
				jsonObject.put("info",tyrePatternService.saveTyrePatternList(anslist, user_id,flag));
			}
			
			out.print(jsonObject.toString());
		}
		
		else if ("ByTool".equals(action)) {//花纹尺测试
			List<TreadPattern> patterns=new ArrayList<TreadPattern>();
			for (int i = 1; i <= 12; i++) {
				TreadPattern pattern=new TreadPattern();
				pattern.setCarNo("浙FD5626");
				pattern.setInjuryType(i);
				pattern.setTyrePosition("C"+i);
				pattern.setTyreDeep(10f+i);
				pattern.setAbrasionValue(4+i);
				patterns.add(pattern);
			}
//			String patternlistJsonEcod = request.getParameter("treadPatternlist");
			String patternlistJsonEcod = JSON.toJSONString(patterns);
			String patternlistJson = URLDecoder.decode(patternlistJsonEcod, "utf-8");
			JSONArray jsonarray = JSONArray.fromObject(patternlistJson);
			JSONObject jsonObject=new JSONObject();
			
			if (patternlistJson == null) {
				jsonObject.put("state", 1);
			}else{
				jsonObject.put("state", 0);
				@SuppressWarnings("unchecked")
				List<TreadPattern> patternlist = (List<TreadPattern>) JSONArray.toCollection(
						jsonarray, TreadPattern.class);
				jsonObject.put("info",tyrePatternService.saveTyrePatternListByTool(patternlist));
			}
			
			out.print(jsonObject.toString());
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//小工具,重新加载dtu与车牌号对应关系
		else if("reloadTrucksDevice".equals(action)){//小工具,重新加载dtu与车牌号对应关系
			String dtuid = request.getParameter("dtuid");
			HardwareElement element=UDPServerManager.getInstance().getChannelElement().get(dtuid);
			if(element==null){
				out.print(false);
			}else{
				out.print(trucksDeviceService.reloadTrucksDevice(element));
			}
			
		}
		
		//小工具,服务器操作dtu命令
		else if("operationdtu".equals(action)){//小工具,服务器操作dtu命令
			String dtuid = request.getParameter("dtuid");
			HardwareElement element=UDPServerManager.getInstance().getChannelElement().get(dtuid);
			if(element==null){
				out.print("不在线");
			}else{
				String info = request.getParameter("info");
				if(info==null||"".equals(info.trim())){
					out.print("操作内容为空");
				}else{
					info=info.trim();
					int no=element.getPackageNo();
					Date now=new Date();
					long id=deviceOperationLogService.save(new Object[]{element.getPhone(),now,now,DeviceOperationLog.TYPE_SEND,14}, 
							"dtu_id,create_time,update_time,type,user_id", DeviceOperationLog.TB_N);
					DtuOperation dtuOperation=new DtuOperation();
					dtuOperation.setId(id);;
					dtuOperation.setDtu_id(element.getPhone());
					dtuOperation.setNo(no);
					dtuOperation.setTime(now.getTime());
					String key=element.getPhone()+"&"+no;
					Map<String, DtuOperation> dtuOperationMap=UDPServerManager.getInstance().getDtuOperationMap();
					dtuOperationMap.put(key, dtuOperation);
					HardwareSendManager.getInstance().sendGetOperationDtu(element, info, no);
					String tt="DtuId="+dtuid;
					while(true){
						try {
							if(dtuOperation.getType()==DeviceOperationLog.TYPE_FAIL){//操作失败
								tt+=",操作失败";
								break;
							}else if(dtuOperation.getType()==DeviceOperationLog.TYPE_SUCCESS){//操作成功
								tt+=",操作成功";
								break;
							}else if(System.currentTimeMillis()-dtuOperation.getTime()>DtuOperationProcess.INTERVAL_TIME){
								tt+=",操作超时了";
								break;
							}
							Thread.sleep(200);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					out.print(tt);
				}
			}
			
		}
		
		
		//版本更新模块
		// ios版本检测0是没有新版本,1是有新版本
		else if ("CheckVersion".equals(action)) {
			VersionBean versionBean = this.versionService.getVersion("tyre", "ios");
			JSONObject jsonobject = JSONObject.fromObject(versionBean);
			out.print(jsonobject.toString());
		} 
		
		//测试推送信息
		else if ("testSendWarn".equals(action)) {
			String trucks_id=request.getParameter("trucks_id");
			JSONObject jsonObject=new JSONObject();
			if (StringUtils.isEmpty(trucks_id)) {
				jsonObject.put("state", 2);
			}else {
				TrucksDeviceDaoImpl dao=new TrucksDeviceDaoImpl();
				TrucksByAdminVO trucksByAdminVO=trucksService.getByTrucks_id(trucks_id);
				if (trucksByAdminVO==null) {
					jsonObject.put("state", 3);
				}else {
					dao.test(trucksByAdminVO);
					jsonObject.put("state", 1);
				}
				
			}
			out.print(jsonObject.toString());
		}
		
		//报警信息已读
		else if ("readWarn".equals(action)) {
			String msg_id=request.getParameter("msg_id");
			JSONObject jsonObject=new JSONObject();
			if (StringUtils.isEmpty(msg_id)) {
				jsonObject.put("state", 4);
			}else {
				jsonObject.put("state", trucksDeviceService.readWarnMsg(Integer.valueOf(msg_id)));
				
			}
			out.print(jsonObject.toString());
		}
		
		//根据车牌号获取该车的报警信息
		else if("warnMsgList".equals(action)){
			int user_id = Integer.valueOf(request.getParameter("user_id"));
			int pagenum=Integer.valueOf(request.getParameter("pagenum"));
			String trucksId=request.getParameter("trucksId");
			String states=request.getParameter("state");
			Integer state=((states==null||"".equals(states))?null:Integer.valueOf(states));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(trucksService.warnMsgListByApp(user_id, trucksId, state, pagenum),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}
		//根据车辆获取架驶记录列表
		else if("getDrivingRecordListByTrucksId".equals(action)){//根据车牌号获取架驶记录列表
			String trucksId = request.getParameter("trucksId");
			int pagenum=Integer.valueOf(request.getParameter("pagenum"));
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", 0);
			jsonObject.put("info", JSONArray.fromObject(drivingRecordService.getDrivingRecordListByTrucksId(pagenum, trucksId),JsonConfigUtil.getJsonDateValueFormatConfig()));
			out.print(jsonObject.toString());
		}
		
		//添加花纹代码
		else if("addPatternCode".equals(action)){
			String name = request.getParameter("name");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", specificationService.addPatternCode(name));
			out.print(jsonObject.toString());
		}
		//添加轮胎品牌
		else if("addTyreBrand".equals(action)){
			String name = request.getParameter("name");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", specificationService.addTyreBrand(name));
			out.print(jsonObject.toString());
		}
		//添加轮胎规格
		else if("addSpecification".equals(action)){
			String name = request.getParameter("name");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", specificationService.addSpecification(name));
			out.print(jsonObject.toString());
		}
		//胎工端更新码表数
		else if("mabiaoWork".equals(action)){
			Integer user_id = Integer.valueOf(request.getParameter("user_id"));
			String mabiaos=request.getParameter("mabiao");//码表数
			Double mabiao=(mabiaos==null||"".equals(mabiaos)?null:Double.valueOf(mabiaos));
			String trucksId = request.getParameter("trucksId");
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", tyrePatternService.mabiaoWork(user_id, mabiao, trucksId));
			out.print(jsonObject.toString());
		}
		out.flush();
		out.close();
	}
	
	public static void main(String[] args) {
		List<TreadPattern> patterns=new ArrayList<TreadPattern>();
		for (int i = 0; i <= 16; i++) {
			TreadPattern pattern=new TreadPattern();
			pattern.setCarNo("AAA");
			pattern.setInjuryType(i);
			pattern.setTyrePosition("C"+i);
			pattern.setTyreDeep(10f);
			pattern.setAbrasionValue(11);
			patterns.add(pattern);
		}
		System.out.println(JSON.toJSONString(patterns));
		
	}

}
