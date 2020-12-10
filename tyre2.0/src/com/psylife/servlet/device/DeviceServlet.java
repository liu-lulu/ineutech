package com.psylife.servlet.device;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.psylife.entity.DeviceFasheqi;
import com.psylife.service.DeviceService;
import com.psylife.service.impl.DeviceServiceImpl;
import com.psylife.servlet.BaseServlet;
import com.psylife.util.Constants;
import com.psylife.util.DateUtil;
import com.psylife.util.TrucksStyleUtil;
import com.psylife.util.page.ListInfo;
import com.psylife.vo.Passport;
import com.psylife.vo.web.DeviceDataHistoryVO;
import com.psylife.vo.web.FasheqiDataHistoryVO;
import com.psylife.vo.web.TrucksFasheqiPosVO;

/**
 * dtu设备
 * @author xu
 *
 */
@WebServlet(urlPatterns = { "/device/*" }, loadOnStartup = 1)
public class DeviceServlet extends BaseServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7405960801021243960L;

	private DeviceService deviceService=new DeviceServiceImpl();
	 
	public DeviceServlet() {
		super();
	}
	
	/**
	 * 初始配置
	 */
	@Override
	protected void initConfig() {
		filterInterceptor(new String[]{"installpicture"});//不需要用户权限的action
	}
	
	@Override
	protected void doProccess(HttpServletRequest request,
			HttpServletResponse response,String action) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");// request.getRequestURL()
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		Passport passport=getPassport(request);
		// 1.获取当前硬件数据汇总
		if ("gettdtucount".equals(action)) {
			Map<String, Object> paramaterMap=new HashMap<String, Object>();
			paramaterMap.put("trucksDeviceCountVO", deviceService.getTrucksDeviceCount(passport.getCompanyId()));
			parameterProccess(paramaterMap, request);
			request.getRequestDispatcher("/WEB-INF/jsp/device/gettdtucount.jsp").forward(request,response);
		}
		//查看传感器分布图
		else if("fasheqipos".equals(action)){
			Map<String, Object> paramaterMap=new HashMap<String, Object>();
//			paramaterMap.put("trucksDeviceCountVO", deviceService.getTrucksDeviceCount(passport.getCompanyId()));
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
			parameterProccess(paramaterMap, request);
			request.getRequestDispatcher("/WEB-INF/jsp/device/fasheqipos.jsp").forward(request,response);
		}
		//dtu历史数据记录
		else if("historydtulist".equals(action)){
			String keyWord=request.getParameter("keyWord");//标题
			String currentPageNOs=request.getParameter("currentPageNO");//标题
			String startTimeStr=request.getParameter("startTime");
			String endTimeStr=request.getParameter("endTime");
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
			parameterProccess(paramaterMap, request);
			request.getRequestDispatcher("/WEB-INF/jsp/device/historydtulist.jsp").forward(request,response);
		}
		//传感器历史数据记录
		else if("historyfasheqilist".equals(action)){
			String keyWord=request.getParameter("keyWord");//标题
			String currentPageNOs=request.getParameter("currentPageNO");//标题
			String startTimeStr=request.getParameter("startTime");
			String endTimeStr=request.getParameter("endTime");
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
			parameterProccess(paramaterMap, request);
			request.getRequestDispatcher("/WEB-INF/jsp/device/historyfasheqilist.jsp").forward(request,response);
		}
		//1.传感器列表
		else if("fasheqilist".equals(action)){
			String keyWord=request.getParameter("keyWord");//标题
			String currentPageNOs=request.getParameter("currentPageNO");//标题
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
			parameterProccess(paramaterMap, request);
			request.getRequestDispatcher("/WEB-INF/jsp/device/fasheqilist.jsp").forward(request,response);
		}
		//对码器安装图
		else if("installpicture".equals(action)){
			request.getRequestDispatcher("/WEB-INF/jsp/device/installpicture.jsp").forward(request,response);
		}
		//地图
		else if("map".equals(action)){
			request.getRequestDispatcher("/WEB-INF/jsp/device/map.jsp").forward(request,response);
		}
			
			
		else{
			error404(request, response);	
		}	
		
		out.flush();
		out.close();
	}
	
}
