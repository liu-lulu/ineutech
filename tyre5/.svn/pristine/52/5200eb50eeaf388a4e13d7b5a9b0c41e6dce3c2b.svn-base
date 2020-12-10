package com.kkbc.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kkbc.entity.DeviceOperationLog;
import com.kkbc.entity.DrivingRecord;
import com.kkbc.entity.LoginLog;
import com.kkbc.entity.TyreBase;
import com.kkbc.entity.TyrePattern;
import com.kkbc.entity.TyrePattern2;
import com.kkbc.entity.User;
import com.kkbc.entity.VersionBean;
import com.kkbc.entity.WorkOrder;
import com.kkbc.hardware.DtuOperation;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.hardware.UDPServerManager;
import com.kkbc.hardware.process.DtuOperationProcess;
import com.kkbc.hardware.send.HardwareSendManager;
import com.kkbc.hardware.send.SendManager;
import com.kkbc.service.DeviceOperationLogService;
import com.kkbc.service.DrivingRecordService;
import com.kkbc.service.LoginLogService;
import com.kkbc.service.SpecificationService;
import com.kkbc.service.TrucksDeviceService;
import com.kkbc.service.TrucksService;
import com.kkbc.service.TyreBaseService;
import com.kkbc.service.TyreHistoryService;
import com.kkbc.service.TyrePatternService;
import com.kkbc.service.UserService;
import com.kkbc.service.VersionService;
import com.kkbc.service.WorkOrderService;
import com.psylife.util.DateUtil;
import com.psylife.util.JsonConfigUtil;
import com.psylife.util.StringHelper;
import com.psylife.util.UplodImage;

/**
 * 接口
 * @author liululu
 *
 */
@Controller
@RequestMapping("/ServletAll")
public class InterfaceController extends BaseController{
	public static final Logger logger = LoggerFactory.getLogger(InterfaceController.class);
	
	public InterfaceController(){
		System.out.println("---------interface");
	}
	@Resource
	private UserService userService;
	
	@Resource
	private LoginLogService loginLogService;
	
	@Resource
	private VersionService versionService;
	
	@Resource
	private SpecificationService specificationService;
	
	@Resource
	private TyreBaseService tyreBaseService;
	
	@Resource
	private TyrePatternService tyrePatternService;
	
	@Resource
	private TrucksService trucksService;
	
	@Resource
	private TyreHistoryService tyreHistoryService;
	
	@Resource
	private TrucksDeviceService trucksDeviceService;
	
	@Resource
	private DeviceOperationLogService deviceOperationLogService;
	
	@Resource
	private DrivingRecordService drivingRecordService;
	
	@Resource
	private WorkOrderService workOrderService;
	
	
	//1.登录
	//@RequestMapping("/login")
	@RequestMapping(params={"action=login"})
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{//登录
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject jsonObject;
		String loginName = request.getParameter("name");//名字
		String password = request.getParameter("password");//密码
		String type=request.getParameter("type");//登录类型
		logger.info("用户登录——用户名："+loginName+"&&密码："+password);
		PrintWriter out=response.getWriter();
		if (!StringHelper.isEmptyObject(loginName)&&!StringHelper.isEmptyObject(password)) {
			int userRole=User.USER_ROLE_ADMIN;//角色
			if(type!=null&&!"".equals(type)){
				userRole=Integer.valueOf(type);
			}
			User user=userService.login(loginName, password);
			
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
		out.flush();
		out.close();
		
	}
	//2.获取轮胎规格列表
	//@RequestMapping("/getSpecificationList")
	@RequestMapping(params={"action=getSpecificationList"})
	public void getSpecificationList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{//获取轮胎规格列表
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONArray.fromObject(specificationService.getSpecificationList()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//3.获取轮胎品牌列表
	//@RequestMapping("/getTyreBrandList")
	@RequestMapping(params={"action=getTyreBrandList"})
	public void getTyreBrandList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{//获取轮胎规格列表
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONArray.fromObject(specificationService.getTyreBrandList()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//4.根据用户获取胎汇总列表
	//@RequestMapping("/countTyreInfo")
	@RequestMapping(params={"action=countTyreInfo"})
	public void countTyreInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{//获取轮胎规格列表
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.valueOf(request.getParameter("user_id"));
		String tyre_brand=request.getParameter("tyre_brand");
		String tyre_type1=request.getParameter("tyre_type1");
		String tyre_type3=request.getParameter("tyre_type3");
		String tyre_healths=request.getParameter("tyre_health");
		String column = request.getParameter("column");
		String order = request.getParameter("order");
//		int pagenum=Integer.valueOf(request.getParameter("pagenum"));
		Integer tyre_health=(tyre_healths==null||"".equals(tyre_healths)?null:Integer.valueOf(tyre_healths));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONArray.fromObject(tyreBaseService.countTyreInfo(user_id, tyre_brand, tyre_type1,tyre_type3,tyre_health,column,order)));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//5.根据用户品牌规格花纹获取胎列表
//	@RequestMapping("/getTyreList")
	@RequestMapping(params={"action=getTyreList"})
	public void getTyreList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{//获取轮胎规格列表
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
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
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	
	//6.轮胎详情管理员
//	@RequestMapping("/tyreDetialByAdmin")
	@RequestMapping(params={"action=tyreDetialByAdmin"})
	public void tyreDetialByAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String tyre_id = request.getParameter("tyre_id");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONObject.fromObject(tyreBaseService.tyreDetialByAdmin(tyre_id),JsonConfigUtil.getJsonDateValueFormatConfig()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}

	//7.根据用户获取车辆列表
	//@RequestMapping("/searchTrucksList")
	@RequestMapping(params={"action=searchTrucksList"}, produces = "application/json;charset=UTF-8")
	public void searchTrucksList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
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
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
		
	}
	
	//8.根据车牌号获取车信息
//	@RequestMapping("/getByTrucks_id")
	@RequestMapping(params={"action=getByTrucks_id"}, produces = "text/html;charset=UTF-8")
	public void getByTrucks_id(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String trucks_id=request.getParameter("trucks_id");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		if(trucks_id==null||"".equals(trucks_id)){
			jsonObject.put("info", JSONObject.fromObject(null));
		}else{
			jsonObject.put("info", JSONObject.fromObject(trucksService.getByTrucks_id(trucks_id),JsonConfigUtil.getJsonDateValueFormatConfig()));
		}
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//9.获取最近一次驾驶记录
	//@RequestMapping("/getLastDrivingRecord")
	@RequestMapping(params={"action=getLastDrivingRecord"})
	public void getLastDrivingRecord(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.valueOf(request.getParameter("user_id"));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONObject.fromObject(drivingRecordService.getLastDrivingRecord(user_id),JsonConfigUtil.getJsonDateValueFormatConfig()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//10.根据用户获取架驶记录列表
//	@RequestMapping("/getDrivingRecordList")
	@RequestMapping(params={"action=getDrivingRecordList"})
	public void getDrivingRecordList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.valueOf(request.getParameter("user_id"));
		int pagenum=Integer.valueOf(request.getParameter("pagenum"));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONArray.fromObject(drivingRecordService.getDrivingRecordList(pagenum, user_id),JsonConfigUtil.getJsonDateValueFormatConfig()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//11.开始架驶
//	@RequestMapping("/startDriving")
	@RequestMapping(params={"action=startDriving"})
	public void startDriving(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
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
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//12.结束架驶
	//@RequestMapping("/endDriving")
	@RequestMapping(params={"action=endDriving"})
	public void endDriving(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		DrivingRecord drivingRecord=new DrivingRecord();
		drivingRecord.setDriver_id(user_id);
		drivingRecord.setLi_cheng_end(Double.valueOf(request.getParameter("mabao")));
		JSONObject jsonObject=new JSONObject();
		int state=drivingRecordService.endDriving(drivingRecord);//0成功,1没有要开始的,2结束码表数小于开始码表数,3.其他
		jsonObject.put("state", state);
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//13.架驶员评分
//	@RequestMapping("/scoreDriving")
	@RequestMapping(params={"action=scoreDriving"})
	public void scoreDriving(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		DrivingRecord drivingRecord=new DrivingRecord();
		drivingRecord.setDriver_id(user_id);
		drivingRecord.setStar_unobstructed(Integer.valueOf(request.getParameter("star_unobstructed")));
		drivingRecord.setStar_heart(Integer.valueOf(request.getParameter("star_heart")));
		JSONObject jsonObject=new JSONObject();
		int state=drivingRecordService.scoreDriving(drivingRecord);//0成功,1还未结束不能评分,2已评分,3没有架驶记录,4其他
		jsonObject.put("state", state);
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//14.获取驾驶员驾驶记录汇总
	//@RequestMapping("/getDrivingRecordCount")
	@RequestMapping(params={"action=getDrivingRecordCount"})
	public void getDrivingRecordCount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.valueOf(request.getParameter("user_id"));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONObject.fromObject(drivingRecordService.getDrivingRecordCount(user_id)));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//15.获取轮胎花纹代码列表
	//@RequestMapping("/getPatternCodeList")
	@RequestMapping(params={"action=getPatternCodeList"})
	public void getPatternCodeList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONArray.fromObject(specificationService.getPatternCodeList()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//16.根据关键字获取胎列表
//	@RequestMapping("/tyreListByKeyWord")
	@RequestMapping(params={"action=tyreListByKeyWord"})
	public void tyreListByKeyWord(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.valueOf(request.getParameter("user_id"));
		int pagenum=Integer.valueOf(request.getParameter("pagenum"));
		String keyWord=request.getParameter("keyWord");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONArray.fromObject(tyreBaseService.searchByKeyWord(pagenum, user_id, keyWord),JsonConfigUtil.getJsonDateValueFormatConfig()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//16-1.根据关键字等获取胎库存列表
//	@RequestMapping("/tyreListByKucun")
	@RequestMapping(params={"action=tyreListByKucun"})
	public void tyreListByKucun(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
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
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//17.获取最近一次工单
//	@RequestMapping("/getLastWorkOrder")
	@RequestMapping(params={"action=getLastWorkOrder"})
	public void getLastWorkOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.valueOf(request.getParameter("user_id"));
		String flags=request.getParameter("flag");
		Integer flag=null;
		if(flags!=null&&!"".equals(flags)){
			flag=Integer.valueOf(flags);
		}
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONObject.fromObject(workOrderService.getLastWorkOrder(user_id, flag),JsonConfigUtil.getJsonDateValueFormatConfig()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//18.创建工单,开始工单记录
//	@RequestMapping("/createWorkOrder")
	@RequestMapping(params={"action=createWorkOrder"})
	public void createWorkOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		WorkOrder workOrder=new WorkOrder();
		workOrder.setUser_id(user_id);
		JSONObject jsonObject=new JSONObject();
		int state=workOrderService.createWorkOrder(workOrder);//0成功,1工单还未结束不能创建,2其他
		jsonObject.put("state", state);
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//19.结束工单
//	@RequestMapping("/endWorkOrder")
	@RequestMapping(params={"action=endWorkOrder"})
	public void endWorkOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		WorkOrder workOrder=new WorkOrder();
		workOrder.setUser_id(user_id);
		JSONObject jsonObject=new JSONObject();
		int state=workOrderService.endWorkOrder(workOrder);//0成功,1没有要结束的工单,2其他
		jsonObject.put("state", state);
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//20.录入轮胎花纹深度及其他检测数据信息
//	@RequestMapping("/saveTyrePatternItem")
	@RequestMapping(params={"action=saveTyrePatternItem"})
	public void saveTyrePatternItem(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
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
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//21.批量保存轮胎基本信息
//	@RequestMapping("/saveTyreBaseByList")
	@RequestMapping(params={"action=saveTyreBaseByList"})
	public void saveTyreBaseByList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		//System.out.println(company_id);
		String tyres = request.getParameter("tyres");
		tyres=URLDecoder.decode(tyres, "utf-8");
		JSONArray jsonArray=JSONArray.fromObject(tyres);
		@SuppressWarnings("unchecked")
		List<TyreBase> tyreBases=(List<TyreBase>)JSONArray.toCollection(jsonArray, TyreBase.class);
		int state=tyreBaseService.saveByList(tyreBases, user_id,true);
		PrintWriter out=response.getWriter();
		out.print("{\"state\":"+state+"}");	
		out.flush();
		out.close();
	}
	//21.批量保存轮胎基本信息入库工具专用
//	@RequestMapping("/saveTyreBaseByListTool")
	@RequestMapping(params={"action=saveTyreBaseByListTool"})
	public void saveTyreBaseByListTool(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		//System.out.println(company_id);
		String tyres = request.getParameter("tyres");
		tyres=URLDecoder.decode(tyres, "utf-8");
		JSONArray jsonArray=JSONArray.fromObject(tyres);
		@SuppressWarnings("unchecked")
		List<TyreBase> tyreBases=(List<TyreBase>)JSONArray.toCollection(jsonArray, TyreBase.class);
		int state=tyreBaseService.saveByList(tyreBases, user_id,false);
		PrintWriter out=response.getWriter();
		out.print("{\"state\":"+state+"}");	
		out.flush();
		out.close();
	}
	//22.轮胎详情  根据轮胎号或车牌胎位置
//	@RequestMapping("/tyreDetial")
	@RequestMapping(params={"action=tyreDetial"})
	public void tyreDetial(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
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
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
				
		
	//23.胎装载
//	@RequestMapping("/tyreToTrucks")
	@RequestMapping(params={"action=tyreToTrucks"})
	public void tyreToTrucks(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
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
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//24.胎卸下
//	@RequestMapping("/tyreDown")
	@RequestMapping(params={"action=tyreDown"})
	public void tyreDown(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
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
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//25.轮胎交换
	//@RequestMapping("/tyreExchange")
	@RequestMapping(params={"action=tyreExchange"})
	public void tyreExchange(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		String tyre_id1 = request.getParameter("tyre_id1");
		String tyre_id2 = request.getParameter("tyre_id2");
		String mabiao1s=request.getParameter("mabiao1");//码表数
		Double mabiao1=(mabiao1s==null||"".equals(mabiao1s)?null:Double.valueOf(mabiao1s));//胎号1的码表数
		String mabiao2s=request.getParameter("mabiao2");//码表数
		Double mabiao2=(mabiao2s==null||"".equals(mabiao2s)?null:Double.valueOf(mabiao2s));//胎号2的码表数
		JSONObject jsonObject=new JSONObject();
		int state=tyreBaseService.tyreExchange(tyre_id1, tyre_id2, user_id,mabiao1,mabiao2);//0成功,1不存在两个胎信息,2两个胎都不在车上，交换没有意义,3保存失败,4其他
		jsonObject.put("state", state);
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//26.获取工单列表
//	@RequestMapping("/workOrderList")
	@RequestMapping(params={"action=workOrderList"})
	public void workOrderList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.valueOf(request.getParameter("user_id"));
		int pagenum=Integer.valueOf(request.getParameter("pagenum"));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONArray.fromObject(workOrderService.workOrderList(pagenum, user_id),JsonConfigUtil.getJsonDateValueFormatConfig()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//27.删除工单记录项
//	@RequestMapping("/deleteWorkOrderRecord")
	@RequestMapping(params={"action=deleteWorkOrderRecord"})
	public void deleteWorkOrderRecord(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		JSONObject jsonObject=new JSONObject();
		int state=workOrderService.deleteWorkOrderRecord(Integer.parseInt(request.getParameter("id")), user_id);//0成功,1工单已结束不能删除记录项,2不存在,3记录项已删除,4其他
		jsonObject.put("state", state);
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//28.根据工单id获取记录项
//	@RequestMapping("/recordByWorkOrderId")
	@RequestMapping(params={"action=recordByWorkOrderId"})
	public void recordByWorkOrderId(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONArray.fromObject(workOrderService.recordByWorkOrderId(Integer.valueOf(request.getParameter("workOrderId"))),JsonConfigUtil.getJsonDateValueFormatConfig()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//29.个人信息修改
//	@RequestMapping("/updateProfile")
	@RequestMapping(params={"action=updateProfile"})
	public void updateProfile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject jsonObject=new JSONObject();
		Map<String, String> returnMap=new HashMap<String, String>();
		int state=UplodImage.updateProfile(request,returnMap);//0成功,-2文件过大,-1表单提出问题,1保存数据时不成功
		jsonObject.put("state", state);
		if(state==0&&returnMap.get("image")!=null){
			jsonObject.put("user_face", returnMap.get("image"));
		}
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//30.管理员车轮胎汇总个人页面
	//@RequestMapping("/getUserCountByAdmin")
	@RequestMapping(params={"action=getUserCountByAdmin"})
	public void getUserCountByAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONObject.fromObject(userService.getUserCount(user_id)));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//30-1.获取新消息推送
//	@RequestMapping("/getMessageList")
	@RequestMapping(params={"action=getMessageList"})
	public void getMessageList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONArray.fromObject(userService.getMessageList(user_id)));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//31.温馨提示小贴士列表
//	@RequestMapping("/tyreTips")
	@RequestMapping(params={"action=tyreTips"})
	public void tyreTips(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONArray.fromObject(tyreBaseService.tyreTips(Integer.valueOf(request.getParameter("user_id"))),JsonConfigUtil.getJsonDateValueFormatConfig()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//32.查询轮胎轨迹列表
	//@RequestMapping("/getTyreHistoryListByTyreId")
	@RequestMapping(params={"action=getTyreHistoryListByTyreId"})
	public void getTyreHistoryListByTyreId(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int pagenum=Integer.valueOf(request.getParameter("pagenum"));
		String tyre_id=request.getParameter("tyre_id");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONArray.fromObject(tyreHistoryService.getTyreHistoryListByTyreId(pagenum, tyre_id),JsonConfigUtil.getJsonDateValueFormatConfig()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//33.胎,司机自行修补
//	@RequestMapping("/tyreByDriverXiuBu")
	@RequestMapping(params={"action=tyreByDriverXiuBu"})
	public void tyreByDriverXiuBu(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		String tyre_id = request.getParameter("tyre_id");
		JSONObject jsonObject=new JSONObject();
		int state=tyreBaseService.tyreByDriverXiuBu(tyre_id, user_id);//0成功,1轮胎不存在,2保存失败,3其他
		jsonObject.put("state", state);
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//34.更新车码表数
//	@RequestMapping("/goUpdateTrucksMabiao")
	@RequestMapping(params={"action=goUpdateTrucksMabiao"})
	public void goUpdateTrucksMabiao(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8"); 
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		Double mabiao=Double.valueOf(request.getParameter("mabiao"));
		String trucks_id=request.getParameter("trucks_id");
		JSONObject jsonObject=new JSONObject();
		int state=trucksService.goUpdateTrucksMabiao(trucks_id, user_id, mabiao);//0成功,1车不存在或码表数小于当前值,2其他
		jsonObject.put("state", state);
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//android  版本检测0是没有新版本,1是有新版本
//	@RequestMapping("/AndroidCheckVersion")
	@RequestMapping(params={"action=AndroidCheckVersion"})
	public void AndroidCheckVersion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		VersionBean versionBean = this.versionService.getVersion(request.getParameter("appname"), "android");
		JSONObject jsonobject = JSONObject.fromObject(versionBean);
		PrintWriter out=response.getWriter();
		out.print(jsonobject.toString());
		out.flush();
		out.close();
	}
	//8.胎推荐
//	@RequestMapping("/tyreTuiJian")
	@RequestMapping(params={"action=tyreTuiJian"})
	public void tyreTuiJian(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		int id = Integer.parseInt(request.getParameter("company_id"));
		List<JSONObject> list=tyreBaseService.tuiJian(id);
		PrintWriter out=response.getWriter();
		out.write(JSONArray.fromObject(list).toString());
		out.flush();
		out.close();
	}
	//9.录入轮胎花纹深度
	//@RequestMapping("/saveTyrePattern")
	@RequestMapping(params={"action=saveTyrePattern"})
	public void saveTyrePattern(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
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
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//17.花纹深度测试,车列表
	//	@RequestMapping("/trucksListByPattern")
	@RequestMapping(params={"action=trucksListByPattern"})
	public void trucksListByPattern(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("company_id");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		jsonObject.put("info", JSONArray.fromObject(trucksService.trucksListByPattern(Integer.valueOf(id)),JsonConfigUtil.getJsonDateValueFormatConfig()));
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//18.批量录入轮胎花纹深度及其他信息
	//@RequestMapping("/saveTyrePatternList")
	@RequestMapping(params={"action=saveTyrePatternList"})
	public void saveTyrePatternList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8"); 
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
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
		PrintWriter out=response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//小工具,重新加载dtu与车牌号对应关系
//	@RequestMapping("/reloadTrucksDevice")
	@RequestMapping(params={"action=reloadTrucksDevice"})
	public void reloadTrucksDevice(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8"); 
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String dtuid = request.getParameter("dtuid");
		HardwareElement element=UDPServerManager.getInstance().getChannelElement().get(dtuid);
		PrintWriter out=response.getWriter();
		if(element==null){
			out.print(false);
		}else{
			out.print(trucksDeviceService.reloadTrucksDevice(element));
		}
		out.flush();
		out.close();
	}
	//小工具,服务器操作dtu命令
	//@RequestMapping("/operationdtu")
	@RequestMapping(params={"action=operationdtu"})
	public void operationdtu(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String dtuid = request.getParameter("dtuid");
		HardwareElement element=UDPServerManager.getInstance().getChannelElement().get(dtuid);
		PrintWriter out=response.getWriter();
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
		out.flush();
		out.close();
	}
	//版本更新模块
	// ios版本检测0是没有新版本,1是有新版本
	//@RequestMapping("/CheckVersion")
	@RequestMapping(params={"action=CheckVersion"})
	public void CheckVersion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		VersionBean versionBean = this.versionService.getVersion("tyre", "ios");
		JSONObject jsonobject = JSONObject.fromObject(versionBean);
		PrintWriter out=response.getWriter();
		out.print(jsonobject.toString());
		out.flush();
		out.close();
	}
	//测试推送信息
	//@RequestMapping("/testSendWarn")
	@RequestMapping(params={"action=testSendWarn"})
	public void testSendWarn(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		trucksDeviceService.test();
	}
	

}
