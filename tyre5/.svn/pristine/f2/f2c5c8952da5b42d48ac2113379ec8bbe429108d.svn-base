package com.kkbc.control.device;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kkbc.control.BaseController;
import com.kkbc.entity.DeviceFasheqi;
import com.kkbc.service.DeviceService;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.Passport;
import com.kkbc.vo.web.DeviceDataHistoryVO;
import com.kkbc.vo.web.FasheqiDataHistoryVO;
import com.kkbc.vo.web.TrucksFasheqiPosVO;
import com.psylife.util.Constants;
import com.psylife.util.DateUtil;
import com.psylife.util.RequestUtil;
import com.psylife.util.TrucksStyleUtil;

/**
 * dtu设备
 * @author liululu
 *
 */
@Controller
@RequestMapping("/device")
public class DeviceController extends BaseController{
	
	@Resource
	private DeviceService deviceService;

	// 1.获取当前硬件数据汇总
	@RequestMapping("/gettdtucount")
	public void gettdtucount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		Passport passport=RequestUtil.getPassport(request);
		paramaterMap.put("trucksDeviceCountVO", deviceService.getTrucksDeviceCount(passport.getCompanyId()));
		RequestUtil.parameterProccess(paramaterMap, request);
		request.getRequestDispatcher("/views/device/gettdtucount.jsp").forward(request,response);
	}
	
	//查看传感器分布图
	@RequestMapping("/fasheqipos")
	public void fasheqipos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		String dtu_id=request.getParameter("dtu_id");//dtu_id
		if(dtu_id!=null&&!"".equals(dtu_id)){
			TrucksFasheqiPosVO fasheqiPosVO=deviceService.fasheqipos(dtu_id);	
			paramaterMap.put("fasheqiPosVO", fasheqiPosVO);
			List<DeviceFasheqi> fasheqis=new ArrayList<DeviceFasheqi>();
			List<DeviceFasheqi> fasheqis2=new ArrayList<DeviceFasheqi>();
			for(int i=0;i<22;i++){
				fasheqis2.add(null);
			}
			int nos=TrucksStyleUtil.TyreMaxNum(fasheqiPosVO.getTrucks_style());
			for(int i=0;i<nos;i++){
				fasheqis.add(null);
			}
			int t;
			if (fasheqiPosVO.getDeviceFasheqis()!=null) {
				for(DeviceFasheqi fasheqi:fasheqiPosVO.getDeviceFasheqis()){
					if(fasheqi.getNo()!=null){
						fasheqis2.set(TrucksStyleUtil.getFasheqiPosIndexByWeb(fasheqi.getNo())-1, fasheqi);
						t=TrucksStyleUtil.noByFasheqiNo(fasheqiPosVO.getTrucks_style(), fasheqi.getNo());
						if(t>0){
							fasheqis.set(t-1, fasheqi);
						}
					}
				}
			}
			
			String[] hs=TrucksStyleUtil.TyreWhereArrByStyle(fasheqiPosVO.getTrucks_style());
			Map<String, Integer> map=new HashMap<String, Integer>();
			if(hs!=null){
				for(int i=1;i<=hs.length;i++){
					if("B1".equals(hs[i-1])||"C1".equals(hs[i-1])){
						map.put(i+"", 1);
					}
				}
			}else{
				map.put("11", 1);
			}
			paramaterMap.put("style", fasheqiPosVO.getTrucks_style());
			paramaterMap.put("verticalSep", TrucksStyleUtil.verticalSeparatorByWeb(fasheqiPosVO.getTrucks_style()));
			paramaterMap.put("map", map);
			paramaterMap.put("mapbk", TrucksStyleUtil.FasheqiPosShowByWeb(fasheqiPosVO.getTrucks_style()));
			paramaterMap.put("fasheqis", fasheqis);//实际胎位置传感器
			paramaterMap.put("fasheqis2", fasheqis2);//对码器位置传感器
			paramaterMap.put("numarr", TrucksStyleUtil.FASHEQI_POS_ARR);//对码器位置号
		}
		RequestUtil.parameterProccess(paramaterMap, request);
		request.getRequestDispatcher("/views/device/fasheqipos.jsp").forward(request,response);
	}
	
	//dtu历史数据记录
	@RequestMapping("/historydtulist")
	public ModelAndView historydtulist(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String keyWord=request.getParameter("keyWord");//标题
		String currentPageNOs=request.getParameter("currentPageNO");//标题
		String startTimeStr=request.getParameter("startTime");
		String endTimeStr=request.getParameter("endTime");
		Passport passport=RequestUtil.getPassport(request);
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		int currentPageNO = 1; // 当前页
		if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
			currentPageNO=Integer.valueOf(currentPageNOs);
		}
		paramaterMap.put("currentPageNO", currentPageNO);
		paramaterMap.put("pageSize",  Constants.PAGESIZE);
		paramaterMap.put("keyWord", keyWord);
		Date startTime=null;
		Date endTime=null;
		if(startTimeStr!=null&&!"".equals(startTimeStr)){
			startTime=DateUtil.parseDateTime(startTimeStr);
		}
		if(endTimeStr!=null&&!"".equals(endTimeStr)){
			endTime=DateUtil.parseDateTime(endTimeStr);
		}
		if(startTime==null){
			startTime=DateUtil.getDayBegin(new Date());
		}
		paramaterMap.put("startTime", startTime);
		paramaterMap.put("endTime", endTime);
		ListInfo<DeviceDataHistoryVO> listInfo = new ListInfo<DeviceDataHistoryVO>(currentPageNO, Constants.PAGESIZE);
		listInfo.setCurrentList(deviceService.getHistoryDtuList(keyWord, currentPageNO, passport.getCompanyId(), startTime, endTime));
		if(listInfo.getCurrentList()!=null&&listInfo.getCurrentList().size()>0){
			if(listInfo.getCurrentList().size()==Constants.PAGESIZE){
				listInfo.setSizeOfTotalList(currentPageNO*Constants.PAGESIZE+1);
			}else{
				listInfo.setSizeOfTotalList(currentPageNO*Constants.PAGESIZE);
			}
		}else{
			listInfo.setSizeOfTotalList(currentPageNO*Constants.PAGESIZE);
		}
		paramaterMap.put("listInfo", listInfo);
//		RequestUtil.parameterProccess(paramaterMap, request);
//		request.getRequestDispatcher("/views/device/historydtulist.jsp").forward(request,response);
		
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("device/historydtulist");
		return mv;
	}
	
	//传感器历史数据记录
	@RequestMapping("/historyfasheqilist")
	public void historyfasheqilist(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String keyWord=request.getParameter("keyWord");//标题
		String currentPageNOs=request.getParameter("currentPageNO");//标题
		String startTimeStr=request.getParameter("startTime");
		String endTimeStr=request.getParameter("endTime");
		Passport passport=RequestUtil.getPassport(request);
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		int currentPageNO = 1; // 当前页
		if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
			currentPageNO=Integer.valueOf(currentPageNOs);
		}
		paramaterMap.put("currentPageNO", currentPageNO);
		paramaterMap.put("pageSize",  Constants.PAGESIZE);
		paramaterMap.put("keyWord", keyWord);
		Date startTime=null;
		Date endTime=null;
		if(startTimeStr!=null&&!"".equals(startTimeStr)){
			startTime=DateUtil.parseDateTime(startTimeStr);
		}
		if(endTimeStr!=null&&!"".equals(endTimeStr)){
			endTime=DateUtil.parseDateTime(endTimeStr);
		}
		if(startTime==null){
			startTime=DateUtil.getDayBegin(new Date());
		}
		paramaterMap.put("startTime", startTime);
		paramaterMap.put("endTime", endTime);
		ListInfo<FasheqiDataHistoryVO> listInfo = new ListInfo<FasheqiDataHistoryVO>(currentPageNO, Constants.PAGESIZE);
		listInfo.setCurrentList(deviceService.getHistoryfasheqiList(keyWord, currentPageNO, passport.getCompanyId(), startTime, endTime));
		if(listInfo.getCurrentList()!=null&&listInfo.getCurrentList().size()>0){
			if(listInfo.getCurrentList().size()==Constants.PAGESIZE){
				listInfo.setSizeOfTotalList(currentPageNO*Constants.PAGESIZE+1);
			}else{
				listInfo.setSizeOfTotalList(currentPageNO*Constants.PAGESIZE);
			}
		}else{
			listInfo.setSizeOfTotalList(currentPageNO*Constants.PAGESIZE);
		}
		paramaterMap.put("listInfo", listInfo);
		RequestUtil.parameterProccess(paramaterMap, request);
		request.getRequestDispatcher("/views/device/historyfasheqilist.jsp").forward(request,response);
	}
	
	//1.传感器列表
	@RequestMapping("/fasheqilist")
	public void fasheqilist(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String keyWord=request.getParameter("keyWord");//标题
		String currentPageNOs=request.getParameter("currentPageNO");//标题
		Passport passport=RequestUtil.getPassport(request);
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		int currentPageNO = 1; // 当前页
		if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
			currentPageNO=Integer.valueOf(currentPageNOs);
		}
		paramaterMap.put("currentPageNO", currentPageNO);
		paramaterMap.put("pageSize",  Constants.PAGESIZE);
		paramaterMap.put("keyWord", keyWord);
		ListInfo<DeviceFasheqi> listInfo=deviceService.searchByFasheqilist(passport.getCompanyId(), keyWord, currentPageNO, Constants.PAGESIZE);
		paramaterMap.put("listInfo", listInfo);
		RequestUtil.parameterProccess(paramaterMap, request);
		request.getRequestDispatcher("/views/device/fasheqilist.jsp").forward(request,response);
	}
	
	//对码器安装图
	@RequestMapping("/installpicture")
	public void installpicture(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/views/device/installpicture.jsp").forward(request,response);
	}
	
	//地图
	@RequestMapping("/map")
	public void map(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getRequestDispatcher("/views/device/map.jsp").forward(request,response);
	}
	
}
