package com.kkbc.control.wechat;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kkbc.control.BaseController;
import com.kkbc.entity.User;
import com.kkbc.service.UserService;
import com.kkbc.wechat.common.WechatConsts;
import com.kkbc.wechat.process.WechatOperate;

@Controller
@RequestMapping(value="user")
public class WechatUserController extends BaseController{
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	private WechatOperate util=new WechatOperate();
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value="/userInfo",method=RequestMethod.GET)
	public ModelAndView userInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String openId=request.getParameter("openId");
		if (StringUtils.isEmpty(openId)) {
			String code=request.getParameter("code");
			
			openId=util.getOpenId(WechatConsts.APP_ID, WechatConsts.APP_SECRET, code);
		}
		
		
//		String openId=request.getParameter("openId");
				
		User user=userService.getByOpenid(openId);
		
		System.out.println("----------"+openId);
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("userInfo", user);
		paramaterMap.put("binded", user==null?false:true);
		paramaterMap.put("openId", openId);
		paramaterMap.put("stuNo", request.getParameter("stuNo"));
		
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("wechat/user-info");
		return mv;
		
	}
	
	@RequestMapping(value="/toBind",method=RequestMethod.GET)
	public ModelAndView toBind(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		String code=request.getParameter("code");
		
		String openId=request.getParameter("openId");
		
//		String openId=request.getParameter("openId");
				
		User user=userService.getByOpenid(openId);
		
		System.out.println("----------"+openId);
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("userInfo", user);
		paramaterMap.put("binded", user==null?false:true);
		paramaterMap.put("openId", openId);
		paramaterMap.put("stuNo", request.getParameter("stuNo"));
		
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("wechat/bind");
		return mv;
		
	}
	
	@RequestMapping(value="/bind")
	public ModelAndView bindUser(//
			@RequestParam("nickname") String nickname, //
			@RequestParam("name") String name, //
			@RequestParam("openId") String open_id, //
			@RequestParam("phone") String phone,
			@RequestParam("stu_no") String stu_no) {
		
		User user=userService.getByOpenid(open_id);
		
		if (user==null) {
			user=new User();
			user.setNickname(nickname);
			user.setName(name);
			user.setOpen_id(open_id);
			user.setUser_type(User.USER_ROLE);
			user.setCreate_time(new Date());
			user.setPhone(phone);
			user.setStu_no(stu_no);
			
			userService.insert(user);
		}else {
			user.setNickname(nickname);
			user.setName(name);
			user.setPhone(phone);
			user.setStu_no(stu_no);
			userService.update(user);
		}
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("openId", open_id);
		paramaterMap.put("userInfo", user);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("wechat/user-info");
		return mv;
	}

}
