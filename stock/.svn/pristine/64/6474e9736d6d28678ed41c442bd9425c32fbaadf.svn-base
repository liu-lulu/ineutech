package com.kkbc.control.pad;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.kkbc.cons.Constans;
import com.kkbc.entity.Goods;
import com.kkbc.entity.User;
import com.kkbc.service.GoodsService;
import com.kkbc.service.HistoryService;
import com.kkbc.service.UserService;
import com.kkbc.util.DateUtil;
import com.kkbc.util.QiniuUtil;
import com.kkbc.util.StringHelper;

/**
 * 用户模块
 * 
 * @author liululu
 *
 */
@Controller
@RequestMapping("/interface")
public class PadController {
	
	private Logger logger = LoggerFactory.getLogger(PadController.class);
	
	@Resource
	private UserService userService;
	
	
	@Resource
	private GoodsService goodsService;
	
	@Resource
	private HistoryService historyService;

	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex,HttpServletResponse response) throws IOException  {
		logger.error("请求出现异常:"+StringHelper.getTrace(ex));
		
		PrintWriter out =  response.getWriter();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", 0);
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	
	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException{//登录
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String loginName = request.getParameter("name");//名字
		String password = request.getParameter("password");//密码
		String type=request.getParameter("type");//登录类型
		logger.info("用户登录——用户名："+loginName+"&&密码："+password);
		PrintWriter out =  response.getWriter();
		
		JSONObject jsonObject = new JSONObject();
		
		if (!StringUtils.isEmpty(loginName)&&!StringUtils.isEmpty(password)) {
			int userRole=User.USER_ROLE_ADMIN;//角色
			if(type!=null&&!"".equals(type)){
				userRole=Integer.valueOf(type);
			}
			User user=userService.login(loginName, password);
			if(user!=null&&user.getRole()==userRole){
				
				jsonObject.put("state", 1);
				jsonObject.put("user", user);
			}else{
				logger.info("用户登录失败——原因:用户名或密码错误！");
				jsonObject.put("state", 2);
			}
		}else{
			logger.info("用户登录失败——原因:用户名或密码为空！");
			jsonObject.put("state", 3);
		}
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/getByCode")
	public void getByCode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String code=request.getParameter("code");
		
		jsonObject.put("info", goodsService.getByCode(code));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/saveInfo")
	public void saveInfo(MultipartFile[] files,HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String code=request.getParameter("code");
		
		Goods info=goodsService.getByCode(code);
		if (info!=null) {
			jsonObject.put("info", 2);
			
			out.print(jsonObject.toString());
			
			out.flush();
			out.close();
			return;
		}
		
		String brand=request.getParameter("brand");
		String name=request.getParameter("name");
		String model=request.getParameter("model");
		String inPriceString=request.getParameter("inPrice");
		String outPriceString=request.getParameter("outPrice");
		String countString=request.getParameter("count");
		String origin=request.getParameter("origin");
		String comment=request.getParameter("comment");
		
		Integer count=StringUtils.isEmpty(countString)?0:Integer.valueOf(countString);
		Float inPrice=StringUtils.isEmpty(inPriceString)?null:Float.valueOf(inPriceString);
		Float outPrice=StringUtils.isEmpty(outPriceString)?null:Float.valueOf(outPriceString);
		String fileNameUUID=null;
		if (files != null && files.length > 0) {
			MultipartFile file=files[0];
			if (!file.isEmpty()) {
				String fileName=file.getOriginalFilename();
				System.out.println("上传文件名:"+fileName);
				// 文件名+UUID
				fileNameUUID = fileName.substring(0,fileName.lastIndexOf("."))+"_"+UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf("."));
				
				logger.info("文件名:"+fileNameUUID);
				QiniuUtil.upload(file.getBytes(), fileNameUUID);
			}
			
		}else {
			logger.info("没有上传的图片;");
		}
		
		jsonObject.put("info", goodsService.save(new Goods(code, brand,name, model, count,origin, inPrice, outPrice, comment, fileNameUUID)));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/in")
	public void in(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String barcode=request.getParameter("barcode");
//		String goodsIdString=request.getParameter("goodsId");
		String countString=request.getParameter("count");
		
//		Integer goodsId=StringUtils.isEmpty(goodsIdString)?0:Integer.valueOf(goodsIdString);
		Integer count=StringUtils.isEmpty(countString)?0:Integer.valueOf(countString);
		
		jsonObject.put("info", goodsService.in(barcode, count));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/out")
	public void out(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String barcode=request.getParameter("barcode");
//		String goodsIdString=request.getParameter("goodsId");
		String countString=request.getParameter("count");
		
//		Integer goodsId=StringUtils.isEmpty(goodsIdString)?0:Integer.valueOf(goodsIdString);
		Integer count=StringUtils.isEmpty(countString)?0:Integer.valueOf(countString);
		
		jsonObject.put("info", goodsService.out(barcode, count));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/history")
	public void history(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String goodsIdString=request.getParameter("goodsId");
		String typeString=request.getParameter("type");
		String startTimeStr=request.getParameter("startTime");
		String endTimeStr=request.getParameter("endTime");
		int pagenum=Integer.valueOf(request.getParameter("pagenum"));
		
		Integer goodsId=StringUtils.isEmpty(goodsIdString)?null:Integer.valueOf(goodsIdString);
		Integer type=StringUtils.isEmpty(typeString)?null:Integer.valueOf(typeString);
		Date startTime=StringUtils.isEmpty(startTimeStr)?null:DateUtil.parseToDate(startTimeStr);
		Date endTime=StringUtils.isEmpty(endTimeStr)?null:DateUtil.getDayEnd(DateUtil.parseToDate(endTimeStr));
		
		jsonObject.put("info", historyService.getPage(goodsId, type, startTime, endTime, pagenum));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/goodsList")
	public void goodsList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String brand=request.getParameter("brand");
		int pagenum=Integer.valueOf(request.getParameter("pagenum"));
		
		jsonObject.put("info", goodsService.getPage(brand, pagenum));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/goodsOperateInfoPage")
	public void goodsOperateInfoPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String brand=request.getParameter("brand");
		String startTimeStr=request.getParameter("startTime");
		String endTimeStr=request.getParameter("endTime");
		String pagenum=request.getParameter("pagenum");
		String remainLowString=request.getParameter("remainLow");
		String remainHighString=request.getParameter("remainHigh");
		String priceLowString=request.getParameter("priceLow");
		String priceHighString=request.getParameter("priceHigh");
		String column = request.getParameter("column");
		String order = request.getParameter("order");
		
		Date startTime=StringUtils.isEmpty(startTimeStr)?null:DateUtil.parseToDate(startTimeStr);
		Date endTime=StringUtils.isEmpty(endTimeStr)?null:DateUtil.getDayEnd(DateUtil.parseToDate(endTimeStr));
		Integer remainLow=StringUtils.isEmpty(remainLowString)?null:Integer.valueOf(remainLowString);
		Integer remainHigh=StringUtils.isEmpty(remainHighString)?null:Integer.valueOf(remainHighString);
		Float priceLow=StringUtils.isEmpty(priceLowString)?null:Float.valueOf(priceLowString);
		Float priceHigh=StringUtils.isEmpty(priceHighString)?null:Float.valueOf(priceHighString);
		
		jsonObject.put("info", goodsService.getOperateInfoPage(brand, startTime, endTime, remainLow,remainHigh,priceLow,priceHigh,column,order,pagenum));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/goodsHistoryDayInfo")
	public void goodsHistoryDayInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String goodsIdString=request.getParameter("goodsId");
		String startTimeStr=request.getParameter("startTime");
		String endTimeStr=request.getParameter("endTime");
		String pagenum=request.getParameter("pagenum");
		
		Integer goodsId=StringUtils.isEmpty(goodsIdString)?null:Integer.valueOf(goodsIdString);
		Date startTime=StringUtils.isEmpty(startTimeStr)?null:DateUtil.parseToDate(startTimeStr);
		Date endTime=StringUtils.isEmpty(endTimeStr)?null:DateUtil.getDayEnd(DateUtil.parseToDate(endTimeStr));
		
		jsonObject.put("info", goodsService.getHistoryDayInfo(goodsId, startTime, endTime, pagenum));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/goodsHistoryInfoPage")
	public void goodsHistoryInfoPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String brand=request.getParameter("brand");
		String startTimeStr=request.getParameter("startTime");
		String endTimeStr=request.getParameter("endTime");
		String pagenum=request.getParameter("pagenum");
		
		Date startTime=StringUtils.isEmpty(startTimeStr)?null:DateUtil.parseToDate(startTimeStr);
		Date endTime=StringUtils.isEmpty(endTimeStr)?null:DateUtil.getDayEnd(DateUtil.parseToDate(endTimeStr));
		
		jsonObject.put("info", goodsService.getHistoryInfoPage(brand, startTime, endTime, pagenum));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/getDate")
	public void getDate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put("info", historyService.getOperateDate());
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/qiniu")
	public void qiniu(MultipartFile[] files,HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String fileName=file.getOriginalFilename();
			String fileNameUUID = fileName.substring(0,fileName.lastIndexOf("."))+"_"+UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf("."));
			String result=QiniuUtil.upload(file.getBytes(), fileNameUUID);
			jsonObject.put("status"+i, result);
		}
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/uploadLocalImgToQiniu")
	public void uploadLocalImgToQiniu(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		QiniuUtil.uploadFiles(Constans.IMG);
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	public static void main(String[] args) {
		
		
	}
}
