package com.kkbc.control.pad;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gexin.fastjson.JSONObject;
import com.kkbc.cons.Constans;
import com.kkbc.entity.Bus;
import com.kkbc.entity.InvitatorUser;
import com.kkbc.entity.ManageUser;
import com.kkbc.entity.Volunteer;
import com.kkbc.service.UserService;
import com.kkbc.util.DateUtil;

/**
 * 用户模块
 * 
 * @author liululu
 *
 */
@Controller
public class PadController {

	private Logger logger = LoggerFactory.getLogger(PadController.class);

	@Resource
	private UserService userService;

	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex,HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		logger.error("请求出现异常:" + ex.getMessage());
		ex.printStackTrace();

		
		StringBuilder builder = new StringBuilder();    
        builder.append("<script>");    
        builder.append("alert('登录已过期，请重新登录！');");
        builder.append("window.location.href='"+request.getContextPath()+"';");
        builder.append("</script>");
        
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(builder.toString());    

		out.flush();
		out.close();
	}

	@RequestMapping("loginuser")
	public ModelAndView login(@RequestParam String username,
			@RequestParam String password, HttpSession session) {
		ManageUser user = userService.getLoginUser(username, password);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (user == null) {
			paramMap.put("username", username);
			paramMap.put("password", password);
			paramMap.put("msg", "用户名或密码错误");
			return new ModelAndView("login", paramMap);
		}
		session.setAttribute("manager", user);
		// List<InvitatorUser>
		// invitatorUsers=userService.getInvitators(user.getUser_id(),null,null,null,
		// null);
		// paramMap.put("users", invitatorUsers);
		// return new ModelAndView("list",paramMap);

		return new ModelAndView("redirect:invitators.do");
	}

	@RequestMapping("invitators")
	public ModelAndView getInvitators(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String position,
			@RequestParam(required = false) String info,
			@RequestParam(required = false) String assignUser,
			@RequestParam(required = false) String pickup,
			@RequestParam(required = false) String lodging,
			@RequestParam(required = false) String userState,
			@RequestParam(required = false) String recived,
			@RequestParam(required = false) String returned,
			@RequestParam(required = false) String signed,
			@RequestParam(required = false) String currentPageNO,
			@RequestParam(required = false) String menu, HttpSession session) {
		
		ManageUser managerUser = ((ManageUser) session.getAttribute("manager"));
		
		Integer userStateInteger=(StringUtils.isEmpty(userState))?0:Integer.valueOf(userState);
		String started="0";
		if (userStateInteger==1) {
			started="1";
		}else if (userStateInteger==2) {
			recived="1";
		}else if (userStateInteger==3) {
			signed="1";
		}else if (userStateInteger==4){
			returned="1";
		}

		// 當前頁
		int curPage = StringUtils.isEmpty(currentPageNO) ? 1 : Integer
				.valueOf(currentPageNO);
		Integer managerId = managerUser.getUser_id();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<InvitatorUser> invitatorUsers = userService.getInvitators(
				managerUser, name,assignUser,position,info, pickup, lodging,started,recived,returned,signed,menu, curPage);
		paramMap.put("users", invitatorUsers);
		paramMap.put("assignUser", assignUser);
		paramMap.put("name", name);
		paramMap.put("position", position);
		paramMap.put("info", info);
		paramMap.put("pickup", pickup);
		paramMap.put("lodging", lodging);
		paramMap.put("userState", userState);
		
		paramMap.put("recived", recived);
		paramMap.put("returned", returned);
		paramMap.put("signed", signed);
		paramMap.put("menu", menu);

		// 每頁的行數
		int rowNum = Constans.PAGE_SIZE;
		int count = userService.getCount(managerUser, name,assignUser,position,info, pickup,
				lodging,started,recived,returned,signed,menu);
		// 頁數
		double pageNum = Math.ceil((double) count / rowNum);
		paramMap.put("pageCount", pageNum);
//		paramMap.put("pageCount", 10);
		paramMap.put("rowNum", rowNum);
		paramMap.put("currentPageNO", curPage);
		paramMap.put("sizeOfTotalList", count);
		return new ModelAndView("list1", paramMap);
	}

	@RequestMapping("updState")
	public ModelAndView updState(@RequestParam String state,
			@RequestParam String type, @RequestParam Integer userId,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String position,
			@RequestParam(required = false) String info,
			@RequestParam(required = false) String assignUser,
			@RequestParam(required = false) String pickup,
			@RequestParam(required = false) String lodging,
			@RequestParam(required = false) String userState,
			@RequestParam(required = false) String recived,
			@RequestParam(required = false) String returned,
			@RequestParam(required = false) String signed,
			@RequestParam(required = false) String menu,
			@RequestParam(required = false) String currentPageNO,HttpSession session) {
		ManageUser managerUser = ((ManageUser) session.getAttribute("manager"));
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		userService.updState(managerUser.getUser_id(),userId, type, state);
		paramMap.put("assignUser", assignUser);
		paramMap.put("name", name);
		paramMap.put("position", position);
		paramMap.put("info", info);
		paramMap.put("pickup", pickup);
		paramMap.put("lodging", lodging);
		paramMap.put("userState", userState);
		paramMap.put("recived", recived);
		paramMap.put("returned", returned);
		paramMap.put("signed", signed);
		paramMap.put("menu", menu);
		paramMap.put("currentPageNO", currentPageNO);
		return new ModelAndView("redirect:invitators.do", paramMap);
	}
	
	@ResponseBody
	@RequestMapping("updInfo")
	public String updInfo(@RequestParam Integer inviter_id,
			@RequestParam(required = false) String arrival_pattern,
			@RequestParam(required = false) String arrival_position,
			@RequestParam(required = false) String arrival_time,
			@RequestParam(required = false) String arrival_info,
			@RequestParam(required = false) String return_pattern,
			@RequestParam(required = false) String return_position,
			@RequestParam(required = false) String return_time,
			@RequestParam(required = false) String return_info,
			@RequestParam(required = false) String pick_up,
			@RequestParam(required = false) String responsible_person,
			@RequestParam(required = false) String responsible_person_phone,HttpSession session) {
		ManageUser managerUser = ((ManageUser) session.getAttribute("manager"));
		/*if (managerUser==null) {
			return "fail";
		}*/
		InvitatorUser invitatorUser = new InvitatorUser(inviter_id, arrival_pattern, arrival_position, DateUtil.parseDateTime(arrival_time),arrival_info, return_pattern, return_position, DateUtil.parseDateTime(return_time),return_info, pick_up,responsible_person,responsible_person_phone);
		userService.updInfo(managerUser.getUser_id(),invitatorUser);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("updHotelInfo")
	public String updHotelInfo(@RequestParam Integer hotelinviter_id,
			@RequestParam(required = false) String sign,
			@RequestParam(required = false) String lodging_select,
			@RequestParam(required = false) String room_level,
			@RequestParam(required = false) String room_num,
			@RequestParam(required = false) String room_level2,
			@RequestParam(required = false) String room_num2,
			@RequestParam(required = false) String in_time,
			@RequestParam(required = false) String out_time,
			@RequestParam(required = false) String newhotel_select, HttpSession session) {
		ManageUser managerUser = ((ManageUser) session.getAttribute("manager"));
		/*if (managerUser==null) {
			return "fail";
		}*/
		InvitatorUser invitatorUser = new InvitatorUser(hotelinviter_id,sign, lodging_select, newhotel_select, DateUtil.getDate(in_time), DateUtil.getDate(out_time), room_level, room_num, room_level2, room_num2);
		userService.updHotelInfo(managerUser.getUser_id(),invitatorUser);
		return "success";
	}
	@ResponseBody
	@RequestMapping("saveVolunteer")
	public String saveVolunteer(MultipartFile[] file,String name,String sex,String school,String phone,String degree,
			String idcard,String creditcard,String bank,String coat, String pants) throws IllegalStateException, IOException {
		String img="";
		if (file!=null && file.length==1) {
			MultipartFile multipartFile = file[0];
			if (multipartFile.getSize()>0) {
				String fileName=multipartFile.getOriginalFilename();
				String suffix=fileName.substring(fileName.lastIndexOf("."), fileName.length());
				img=idcard+name+suffix;
				multipartFile.transferTo(new File("D:/2019wuwenjun/"+img));
			}
		}
		Volunteer volunteer=new Volunteer(name, sex, school, phone, degree, idcard, creditcard, bank, coat, pants, img);
		int ret=userService.saveVolunteer(volunteer);
		if(ret==1){
			return "success";
		}else {
			return "fail";
		}
	}
	
	@ResponseBody
	@RequestMapping("bus")
	public String bus(String name,String phone,String start_pos,String start_time,String end_pos) throws IllegalStateException, IOException {


		Bus bus=new Bus(name, phone,start_pos, start_time, end_pos);
		int ret=userService.saveBus(bus);
		if(ret==1){
			return "success";
		}else {
			return "fail";
		}
	}
	
	@ResponseBody
	@PostMapping("check")
	public String checkIdcard(String idcard,String phone){
		Volunteer volunteer=userService.getVolunteer(new Volunteer(phone, idcard));
		if (volunteer==null) {
			return "{\"valid\":true}";
		}
		return "{\"valid\":false}";
	}

	public static void main(String[] args) {
		int rowNum = Constans.PAGE_SIZE;
		int count = 50;
		// 頁數
		double pageNum = Math.ceil((double) count / rowNum);
		System.out.println(pageNum);
	}

}
