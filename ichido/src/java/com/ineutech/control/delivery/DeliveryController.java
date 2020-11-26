package com.ineutech.control.delivery;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gexin.fastjson.JSONObject;
import com.ineutech.cons.Constans;
import com.ineutech.entity.delivery.DeliveryLogin;
import com.ineutech.entity.delivery.DeliveryOrder;
import com.ineutech.service.DeliveryService;

/**
 * 配送模块
 * 
 * @author liululu
 *
 */
@RequestMapping("delivery")
@Controller
public class DeliveryController {

	private Logger logger = LoggerFactory.getLogger(DeliveryController.class);

	@Resource
	private DeliveryService deliveryService;

	@RequestMapping("toLogin")
	public ModelAndView toDeliveryLogin(){
		return new ModelAndView("delivery/login");
	}
	
	@RequestMapping("toNewOrder")
	public ModelAndView toNewOrder(HttpSession session){
		DeliveryLogin delivery=(DeliveryLogin) session.getAttribute("delivery");
		if (delivery==null) {
			return new ModelAndView("delivery/login");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("shops", deliveryService.shops());
		return new ModelAndView("delivery/newOrder",paramMap);
	}

	@RequestMapping("login")
	public ModelAndView login(@RequestParam String username,
			@RequestParam String password, HttpSession session) {
		DeliveryLogin delivery = deliveryService.login(username, password);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (delivery == null) {
			paramMap.put("username", username);
			paramMap.put("password", password);
			paramMap.put("msg", "用户名或密码错误");
			return new ModelAndView("delivery/login", paramMap);
		}
		session.setAttribute("delivery", delivery);
		
		if (delivery.getRole()==1) {
			paramMap.put("shops", deliveryService.shops());
			return new ModelAndView("delivery/newOrder",paramMap);
		}else {
			return new ModelAndView("redirect:orderList.do");
		}
		
	}

	@ResponseBody
	@RequestMapping("newOrder")
	public String newOrder(String order_info,String scheduled_time,
			String receiver, String receiver_address,
			String receiver_phone, String product_name,
			String product_num, String shop_code,
			HttpSession session) {
		DeliveryLogin delivery=(DeliveryLogin) session.getAttribute("delivery");
		
		DeliveryOrder orderInfo=new DeliveryOrder(order_info,scheduled_time, receiver, receiver_address, receiver_phone, product_name, Integer.valueOf(product_num), shop_code);
		int ret=deliveryService.saveOrder(orderInfo);
		if(ret==1){
			return "success";
		}else {
			return "fail";
		}
		
	}
	@RequestMapping("orderList")
	public ModelAndView orderList(@RequestParam(required = false) String orderInfo,@RequestParam(required = false) String flag,
			@RequestParam(required = false) String currentPageNO,HttpSession session) {
		
		DeliveryLogin delivery=(DeliveryLogin) session.getAttribute("delivery");
		if (delivery==null) {
			return new ModelAndView("delivery/login");
		}
		
		Integer loginId=delivery.getLogin_id();
		String shopCode=delivery.getShop_code();
		int role = delivery.getRole();
		if (role==0) {
			loginId=null;
		}else if (role==2) {
			shopCode=null;
		}else {
			loginId=null;
			shopCode=null;
		}
		
		// 當前頁
		int curPage = StringUtils.isEmpty(currentPageNO) ? 1 : Integer
				.valueOf(currentPageNO);
				
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		// 每頁的行數
		int rowNum = Constans.PAGE_SIZE;
		
		Integer state=StringUtils.isEmpty(flag)?null:Integer.valueOf(flag);
		
		int count = deliveryService.orderCount(loginId, shopCode,orderInfo, state);
		// 頁數
		double pageNum = Math.ceil((double) count / rowNum);
		
		
		List<DeliveryOrder> orders=deliveryService.getOrders( loginId, shopCode,orderInfo, state, curPage);
		paramMap.put("pageCount", pageNum);
		paramMap.put("rowNum", rowNum);
		paramMap.put("currentPageNO", curPage);
		paramMap.put("sizeOfTotalList", count);
		
		paramMap.put("orderInfo", orderInfo);
		paramMap.put("flag", flag);
		paramMap.put("orders", orders);
		
		if (role==2) {//配送员
			return new ModelAndView("delivery/orderList",paramMap);
		}else if (role==1){//管理员
			paramMap.put("shops", deliveryService.shops());
			return new ModelAndView("delivery/adminList",paramMap);
		}else{//店铺
			paramMap.put("deliverymans", deliveryService.deliverymans(delivery.getShop_code()));
			return new ModelAndView("delivery/shopOrder",paramMap);
		}
		
	}
	
//	@ResponseBody
	@RequestMapping("complete")
	public void complete(String orderId,String curAddress,HttpServletResponse response,HttpServletRequest request,HttpSession session) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();
		
		int ret=deliveryService.deliveryOrder(new DeliveryOrder(Integer.valueOf(orderId),new Date(), curAddress, 1));
		if(ret==1){
			jsonObject.put("msg", "配送成功");
		}else {
			jsonObject.put("msg", "配送失败");
		}
		
		Writer out = response.getWriter();
		out.write(jsonObject.toString());
		 out.flush();
		 out.close();
//		return jsonObject.toString();
	}
	
	@RequestMapping("selectMan")
	public void selectMan(String orderId,String deliveryman,HttpServletResponse response,HttpServletRequest request,HttpSession session) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		JSONObject jsonObject = new JSONObject();
		
		int ret=deliveryService.updMan(new DeliveryOrder(Integer.valueOf(orderId), Integer.valueOf(deliveryman)));
		if(ret==1){
			jsonObject.put("msg", "success");
		}else {
			jsonObject.put("msg", "fail");
		}
		
		Writer out = response.getWriter();
		out.write(jsonObject.toString());
		 out.flush();
		 out.close();
	}
	
	@ResponseBody
	@RequestMapping("updOrder")
	public String updOrder(String updorderId,String order_info,String scheduled_time,
			String receiver, String receiver_address,
			String receiver_phone, String product_name,
			String product_num, String shop_code,
			HttpSession session) {
		
		DeliveryOrder orderInfo=new DeliveryOrder(Integer.valueOf(updorderId),order_info,scheduled_time, receiver, receiver_address, receiver_phone, product_name, Integer.valueOf(product_num), shop_code);
		int ret=deliveryService.updOrder(orderInfo);
		if(ret==1){
			return "success";
		}else {
			return "fail";
		}
		
	}

}
