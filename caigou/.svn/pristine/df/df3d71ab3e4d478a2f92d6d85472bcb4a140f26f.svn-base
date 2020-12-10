package com.kkbc.control.pad;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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
import com.kkbc.entity.Order;
import com.kkbc.entity.PackageDetail;
import com.kkbc.entity.PackageInfo;
import com.kkbc.entity.PayHistory;
import com.kkbc.entity.User;
import com.kkbc.service.GoodsService;
import com.kkbc.service.OrderService;
import com.kkbc.service.PackageService;
import com.kkbc.service.PayHistoryService;
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
	private OrderService orderService;
	
	@Resource
	private PackageService packageService;
	
	@Resource
	private GoodsService goodsService;
	
	@Resource
	private PayHistoryService payHistoryService;

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
	
	
	@RequestMapping("/caigou")
	public void caigou(MultipartFile[] files,HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String caigouInfo = request.getParameter("caigouInfo");
		
		PrintWriter out =  response.getWriter();
		
		JSONObject jsonObject = new JSONObject();
		
		if(StringUtils.isNotEmpty(caigouInfo)){
			String info = URLDecoder.decode(caigouInfo, "utf-8");
			JSONArray jsonarray = JSONArray.fromObject(info);
			List<Order> caigouList=(List<Order>) JSONArray.toCollection(jsonarray, Order.class);
			
			if (caigouList!=null&&caigouList.size()>0) {
				List<Goods> goodsList=new ArrayList<Goods>();
				for (Order order : caigouList) {
					if (files != null && files.length > 0) {
						for (MultipartFile file : files) {
							if (!file.isEmpty()) {
								String fileName=file.getOriginalFilename();
								System.out.println("上传文件名:"+fileName);
								if (StringUtils.isNotEmpty(order.getImg())&&fileName.equals(order.getImg())) {
									// 文件名+UUID
									String fileNameUUID = fileName.substring(0,fileName.lastIndexOf("."))+"_"+UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf("."));
									
									logger.info("文件名:"+fileNameUUID);
									// 转存文件
//									file.transferTo(new File(Constans.IMG+fileNameUUID));
									QiniuUtil.upload(file.getBytes(), fileNameUUID);
									order.setImg(fileNameUUID);
									break;
								}
								
							}
						}
						
					}else {
						logger.info("没有上传的图片;");
					}
					
					List<Goods> goodsInfo=goodsService.get(order.getBrand(), order.getModel());
					if (goodsInfo==null||goodsInfo.size()==0) {
						boolean exist=false;
						for (Goods goods : goodsList) {//商品避免重复添加
							if (goods.getBrand().equals(order.getBrand())&goods.getModel().equals(order.getModel())) {
								exist=true;
								goods.setImg(order.getImg());
								break;
							}
						}
						if (!exist) {
							goodsList.add(new Goods(order.getBrand(), order.getModel(),order.getImg()));
						}
					}else if (StringUtils.isNotEmpty(order.getImg())) {//更新商品图片为最新的
						for (Goods goods : goodsInfo) {
							if (!goods.getImg().equals(order.getImg())) {
								goods.setImg(order.getImg());
								goodsService.updImg(goods);
							}
						}
					}
				}
				
				orderService.saveInfo(caigouList);
				if (goodsList.size()>0) {//将新商品加入到商品列表中
					goodsService.save(goodsList);
				}
				jsonObject.put("state", 2);
			}else {
				jsonObject.put("state", 1);//没有商品信息
			}
		}else {
			jsonObject.put("state", 1);//没有商品信息
		}
		
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	@RequestMapping("/caigouList")
	public void caigouList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String userIdString = request.getParameter("userId");
		String roleString = request.getParameter("role");
		int pagenum=Integer.valueOf(request.getParameter("pagenum"));
		String brand = request.getParameter("brand");
		int role="1".equals(roleString)?User.USER_ROLE_ADMIN:User.USER_ROLE_SUPPLIER;
		String supplierIdString = request.getParameter("supplierId");//采购员id
		Integer supplierId=StringUtils.isEmpty(supplierIdString)?null:Integer.valueOf(supplierIdString);
		
		String collectionStatusString=request.getParameter("collectionStatus");//收款状态
		String caigouStatusString=request.getParameter("caigouStatus");//采购状态
		String deliveryStatusString=request.getParameter("deliveryStatus");//发货状态
		String paymentStatusString=request.getParameter("paymentStatus");//付款状态

		Integer collectionStatus=StringUtils.isEmpty(collectionStatusString)?null:Integer.valueOf(collectionStatusString);
		Integer caigouStatus=StringUtils.isEmpty(caigouStatusString)?null:Integer.valueOf(caigouStatusString);
		Integer deliveryStatus=StringUtils.isEmpty(deliveryStatusString)?null:Integer.valueOf(deliveryStatusString);
		Integer paymentStatus=StringUtils.isEmpty(paymentStatusString)?null:Integer.valueOf(paymentStatusString);
		
		jsonObject.put("info", orderService.orderList(Integer.valueOf(userIdString),brand, role,pagenum,collectionStatus,caigouStatus,deliveryStatus,paymentStatus,supplierId));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/caigouDetail")
	public void caigouDetail(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String orderIdString = request.getParameter("orderId");
		
		jsonObject.put("info", orderService.orderDetai(Integer.valueOf(orderIdString)));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/supplierList")
	public void supplierList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		
		jsonObject.put("info", userService.supplierList());
		
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
		
		int userId=Integer.valueOf(request.getParameter("userId"));
		int pagenum=Integer.valueOf(request.getParameter("pagenum"));
		String brand = request.getParameter("brand");
		String startTimeStr=request.getParameter("startTime");
		String endTimeStr=request.getParameter("endTime");
		
		Date startTime=null;
		Date endTime=null;
		if(startTimeStr!=null&&!"".equals(startTimeStr)){
			startTime=DateUtil.parseToDate(startTimeStr);
		}
		if(endTimeStr!=null&&!"".equals(endTimeStr)){
			endTime=DateUtil.parseToDate(endTimeStr);
		}
		JSONObject jsonObject = new JSONObject();
		
//		JSONArray jsonArray = JSONArray.fromObject(orderService.goodsList(userId, brand, startTime, DateUtil.getDayEnd(endTime), pagenum));
		
//		Map<String, List<OrderVO>> infoMap=orderService.goodsList(userId, brand, startTime, endTime, pagenum);
//		Set<String> ite=infoMap.keySet();
//		List<String> infoList=new ArrayList<String>();
//		for (String key : ite) {
//			List<OrderVO> tList=infoMap.get(key);
//			JSONArray jsonObject2=JSONArray.fromObject(tList);
//			infoList.add(key+":"+jsonObject2.toString());
//		}
//		jsonObject.put("infoList", infoList);
		
		jsonObject.put("info", orderService.goodsList(userId, brand, startTime, DateUtil.getDayEnd(endTime), pagenum));
		
		out.print(jsonObject);
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/wuliuList")
	public void wuliuList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String userIdString = request.getParameter("userId");
		String roleString = request.getParameter("role");
		int pagenum=Integer.valueOf(request.getParameter("pagenum"));
		int role="1".equals(roleString)?User.USER_ROLE_ADMIN:User.USER_ROLE_SUPPLIER;
		
		
		jsonObject.put("info", packageService.wuliuList(Integer.valueOf(userIdString), role, pagenum));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/packageDetail")
	public void packageDetail(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String packageIdString=request.getParameter("packageId");
		
		jsonObject.put("info", packageService.packageDetails(Integer.valueOf(packageIdString)));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/packageOperate")
	public void packageOperate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String packageIdString=request.getParameter("packageId");
		String statusString=request.getParameter("type");
		int status="1".equals(statusString)?1:("2".equals(statusString)?2:0);
		
		jsonObject.put("info", packageService.updateStatus(Integer.valueOf(packageIdString), status));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/supplierCaigou")
	public void supplierCaigou(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String orderIdString=request.getParameter("orderId");
		String count=request.getParameter("count");
		String purchase_price=request.getParameter("purchase_price");
		
		jsonObject.put("status", orderService.caigou(Integer.valueOf(orderIdString), Integer.valueOf(count), Float.valueOf(purchase_price)));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/createPackage")
	public void createPackage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String userIdString=request.getParameter("userId");
		String packageName=request.getParameter("packageName");
		String expressNo=request.getParameter("expressNo");
		String comment=request.getParameter("comment");
		
		PackageInfo info=new PackageInfo();
		info.setSupplier_id(Integer.valueOf(userIdString));
		info.setPackage_name(packageName);
		info.setExpressNo(expressNo);
		info.setComment(comment);
		jsonObject.put("info", packageService.createPackage(info));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/baoguoList")
	public void baoguoList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String userIdString=request.getParameter("userId");
		int pagenum=Integer.valueOf(request.getParameter("pagenum"));
		
		jsonObject.put("info", packageService.baoguoList(Integer.valueOf(userIdString), Integer.valueOf(pagenum)));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/remainGood")
	public void remainGood(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String orderId=request.getParameter("orderId");
		
		jsonObject.put("info", orderService.remainCount(Integer.valueOf(orderId)));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/editPackage")
	public void editPackage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String goodsInfo=request.getParameter("goodsInfo");
		System.out.println(goodsInfo);
		
		if(StringUtils.isNotEmpty(goodsInfo)){
			String info = URLDecoder.decode(goodsInfo, "utf-8");
			JSONArray jsonarray = JSONArray.fromObject(info);
			List<PackageDetail> detailList=(List<PackageDetail>) JSONArray.toCollection(jsonarray, PackageDetail.class);
			if (detailList!=null&&detailList.size()>0) {
				packageService.editPackageDetail(detailList);
				jsonObject.put("state", 2);
			}else {
				jsonObject.put("state", 1);//没有商品信息
			}
		}else {
			jsonObject.put("state", 1);//没有商品信息
		}
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/goodsToPackage")
	public void goodsToPackage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String goodsInfo=request.getParameter("goodsInfo");
		System.out.println(goodsInfo);
		if(StringUtils.isNotEmpty(goodsInfo)){
			String info = URLDecoder.decode(goodsInfo, "utf-8");
			JSONArray jsonarray = JSONArray.fromObject(info);
			List<PackageDetail> detailList=(List<PackageDetail>) JSONArray.toCollection(jsonarray, PackageDetail.class);
			if (detailList!=null&&detailList.size()>0) {
				packageService.goodsToPackage(detailList);
				jsonObject.put("state", 2);
			}else {
				jsonObject.put("state", 1);//没有商品信息
			}
		}else {
			jsonObject.put("state", 1);//没有商品信息
		}
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/sendPackage")
	public void sendPackage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String packageIdString=request.getParameter("packageId");
		String expressNo=request.getParameter("expressNo");
		String comment=request.getParameter("comment");
		
		PackageInfo info=new PackageInfo();
		info.setPackage_id(Integer.valueOf(packageIdString));
		info.setExpressNo(expressNo);
		info.setComment(comment);
		info.setSend_time(new Date());
		jsonObject.put("info", packageService.sendPackage(info));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/payOperate")
	public void payOperate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String orderIdString = request.getParameter("orderId");
		String payMoneyString=request.getParameter("money");
		String typeString=request.getParameter("type");//1:收款 2:付款
		String payTypeString=request.getParameter("payType");//付款类型: 0未付 1已付 2付完 3定金
		String comment=request.getParameter("comment");
		

		jsonObject.put("state", payHistoryService.saveInfo(new PayHistory(Integer.valueOf(orderIdString), Integer.valueOf(typeString), Integer.valueOf(payTypeString), Float.valueOf(payMoneyString), comment)));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/delOrder")
	public void delOrder(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String orderIdString = request.getParameter("orderId");
		
		jsonObject.put("info", orderService.delById(Integer.valueOf(orderIdString)));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/goodsExistList")
	public void goodsExistList(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String brand = request.getParameter("brand");
		int pagenum=Integer.valueOf(request.getParameter("pagenum"));
		
		jsonObject.put("info", goodsService.getPageInfo(brand, pagenum));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/oneKeyToPack")
	public void oneKeyToPack(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out =  response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		String orderIdString = request.getParameter("orderId");
		String packageIdString=request.getParameter("packageId");
		
		jsonObject.put("status", packageService.oneKeyToPack(Integer.valueOf(orderIdString), Integer.valueOf(packageIdString)));
		
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
		Order order1=new Order();
		order1.setAdmin_id(1);
		order1.setSupplier_id(2);
		order1.setBrand("香奈儿Chanel");
		order1.setModel("XX1");
		order1.setNum(10);
		order1.setComment("--香奈儿备注--");
		
		Order order2=new Order();
		order2.setAdmin_id(1);
		order2.setSupplier_id(2);
		order2.setBrand("兰蔻Lancome");
		order2.setModel("XX2");
		order2.setNum(30);
		order2.setComment("--兰蔻备注--");
		
		
		
		PackageDetail packageDetail1=new PackageDetail();
		packageDetail1.setPackage_id(1);
		packageDetail1.setOrder_id(5);
		packageDetail1.setOrder_detail_id(1);
		packageDetail1.setBrand("香奈儿Chanel");
		packageDetail1.setModel("XX1");
		packageDetail1.setCount(1);
		packageDetail1.setPurchase_price(500f);
		
		PackageDetail packageDetail2=new PackageDetail();
		packageDetail2.setPackage_id(1);
		packageDetail2.setOrder_id(5);
		packageDetail2.setOrder_detail_id(2);
		packageDetail2.setBrand("兰蔻Lancome");
		packageDetail2.setModel("XX2");
		packageDetail2.setCount(2);
		packageDetail2.setPurchase_price(510f);
		
		JSONArray jsonArray=new JSONArray();
		jsonArray.add(packageDetail1);
		jsonArray.add(packageDetail2);
		
		System.out.println(jsonArray.toString());
		
		File aFile=new File("D:\\img\\caigou_img\\image1497940468_5c95d7df-93b5-4670-b76f-262bf75323b9.png");
			aFile.deleteOnExit();
		
	}
}
