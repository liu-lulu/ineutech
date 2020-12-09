package com.kkbc.control.device;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.kkbc.tpms.tcp.server.SessionManager;

import com.kkbc.control.BaseController;
import com.kkbc.entity.Device;
import com.kkbc.service.DeviceLoginLogService;
import com.kkbc.service.DeviceService;
import com.kkbc.util.Constants;
import com.kkbc.util.DateUtil;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.DeviceLoginLogVo;

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
	@Resource
	private DeviceLoginLogService deviceLoginLogService;
	
	private SessionManager sessionManager=SessionManager.getInstance();
	
	
	//硬件列表
	@RequestMapping("golisttest")
	public ModelAndView goListTest(HttpServletRequest request, HttpServletResponse response){
		String type=request.getParameter("type");//1在线 2离线
		
		List<Device> allDevices=deviceService.getAll();
		
		List<Device> devices=new ArrayList<Device>();
		
		for (Device device:allDevices) {
			if (sessionManager.findByTerminalShefenId(device.getShefen_id())!=null) {
				device.setIsOnline(true);
			}
			
			if (StringUtils.isEmpty(type)) {
				devices.add(device);
			}else if ("1".equals(type)){
				if (device.getIsOnline()) {
					devices.add(device);
				}
			}else if ("2".equals(type)) {
				if (!device.getIsOnline()) {
					devices.add(device);
				}
			}
		}
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("devices", devices);
		paramaterMap.put("type", type);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("device/golisttest");
		return mv;
	}
	
	//脑电波图表
	@RequestMapping("devicelinechat")
	public ModelAndView devicelinechat(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		
		String startTimeStr=request.getParameter("startTime");
		String startHour=request.getParameter("startHour");
		String startMinute=request.getParameter("startMinute");
		
		String endTimeStr=request.getParameter("endTime");
		String endHour=request.getParameter("endHour");
		String endMinute=request.getParameter("endMinute");
		
		String shefenId=request.getParameter("shefenId");
		String sortData=request.getParameter("sortData");
		
		Calendar calendar=Calendar.getInstance();
		
		Date startTime;
		if (startTimeStr==null) {
			startTime = new Date();
			
			calendar.setTime(startTime);
			
			startHour=calendar.get(Calendar.HOUR_OF_DAY)+"";
			startMinute=calendar.get(Calendar.MINUTE)+"";
		}else {
			calendar.setTime(DateUtil.parseDate(startTimeStr));
			calendar.set(Calendar.HOUR, Integer.valueOf(startHour));
			calendar.set(Calendar.MINUTE, Integer.valueOf(startMinute));
			
			startTime=calendar.getTime();
		}
		
		
		
		List<Device> devices=deviceService.getAll();
		
		
		paramaterMap.put("devices", devices);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("device/devicelinechat");
		return mv;
	}
	
	//脑波登录日志
	@RequestMapping("loginloglist")
	public ModelAndView loginloglist(HttpServletRequest request, HttpServletResponse response){
		String currentPageNOs=request.getParameter("currentPageNO");
		
		int currentPageNO = 1; // 当前页
		if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
			currentPageNO=Integer.valueOf(currentPageNOs);
		}
		
		String shefenId=request.getParameter("shefenId");
		String startTimeStr=request.getParameter("startTime");
		String endTimeStr=request.getParameter("endTime");
		String type=request.getParameter("type");
		String tag=request.getParameter("tag");
		
		Date startTime=null;
		Date endTime=null;
		if(startTimeStr!=null&&!"".equals(startTimeStr)){
			startTime=DateUtil.parseDate(startTimeStr);
		}
		if(endTimeStr!=null&&!"".equals(endTimeStr)){
			endTime=DateUtil.parseDate(endTimeStr);
		}
		
	    List<Device> devices = deviceService.getAll();
	    
		Map<String, Object> param=new HashMap<String, Object>();
		param.put("devices", devices);
		param.put("tag", tag);
	    param.put("shefenId", shefenId);
	    param.put("startTime", startTime);
	    param.put("endTime", endTime);
	    param.put("type", type);
	    param.put("currentPageNO", currentPageNO);
	    param.put("pageSize", Constants.PAGESIZE);
	    
	    ListInfo<DeviceLoginLogVo> listInfo = deviceLoginLogService.getLoginLog(param);
	    
	    param.put("listInfo", listInfo);
	
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(param);
		mv.setViewName("device/loginloglist");
		return mv;
	}
	
	//展示
	@RequestMapping("godeviceshowchat")
	public ModelAndView godeviceshowchat(){

		
		List<Device> devices=deviceService.getAll();
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("devices", devices);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("device/godeviceshowchat");
		return mv;
	}

	@RequestMapping("savedeviceremark")
	public void saveDeviceRemark(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Long deviceId=Long.valueOf(request.getParameter("deviceId"));
		
		Device device=new Device();
		device.setDevice_id(deviceId);
		device.setRemark(request.getParameter("labelName"));
		int result=0;
		
		if (deviceService.getByDeviceId(deviceId)!=null) {
			result=deviceService.updRemark(device);
		}
		
		PrintWriter out = response.getWriter();
		
		JSONObject object=new JSONObject();
		object.put("tag", result);
		
		out.print(object.toString());
		out.flush();
		out.close();

	}
	
	public static void main(String[] args) {
		String startTimeStr="2016-10-10";
		String startHour="13";
		String startMinute="10";
		
		Calendar calendar=Calendar.getInstance();
		
		Date startTime;
			calendar.setTime(DateUtil.parseDate(startTimeStr));
			calendar.set(Calendar.HOUR, Integer.valueOf(startHour));
			calendar.set(Calendar.MINUTE, Integer.valueOf(startMinute));
			
			startTime=calendar.getTime();
		
		System.out.println(startTime+" "+startHour+":"+startMinute);
	}
}
