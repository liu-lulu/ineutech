package com.kkbc.control.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kkbc.cons.Constans;
import com.kkbc.control.BaseController;
import com.kkbc.entity.User;
import com.kkbc.service.UserService;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.DiagramVo;
import com.kkbc.vo.Passport;
import com.kkbc.vo.UserHomeVo;
import com.psylife.util.DESUtil;
import com.psylife.util.RequestUtil;

/**
 * 用户模块
 * @author liululu
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

	public static final String USER_LOGIN_COOKIE_INFO = "R_USER_LOGIN_COOKIE_INFO"; // 用户记住密码Cookie
	
	public static final int LOGIN_COOKIE_AGE = 7 * 24 * 60 * 60; // Cookie登录记住时间
	
	@Resource
	private UserService userService;

	// 1.去登录
	@RequestMapping("/gologin")
	public void gologin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PrintWriter out = response.getWriter();
		if(RequestUtil.getPassport(request)!=null){
			Object object=request.getSession().getAttribute("loginbeforurl");
    		String loginbeforurl=object==null?null:object.toString();
			if(loginbeforurl!=null&&!"".equals(loginbeforurl)){
    			request.getSession().removeAttribute("loginbeforurl");
    			response.sendRedirect(loginbeforurl);
    		}else{
    			response.sendRedirect("../");
    		}		
			out.flush();
    		out.close();
			return;
		}else{
			Cookie cookies[] = request.getCookies();
			if (cookies != null) {
				String cookieName = null;
				for (Cookie cookie : cookies) {
					cookieName = cookie.getName();
					if (cookieName.equals(USER_LOGIN_COOKIE_INFO)) {
						try {
							JSONObject jsonObject = JSONObject.fromObject(
									cookie.getValue());
							String isAutoLogin = jsonObject
									.getString("isAutoLogin");
							String loginName = jsonObject.getString("loginName");
							Integer loginId = jsonObject.getInt("loginId");
							String p = jsonObject.getString("p");
							if (isAutoLogin.equals("true") && loginId != null
									&& loginName != null && !loginName.equals("")
									&& p != null) {
								p = DESUtil.decrypt(p);
								User user1 = userService.login(loginName , p);
								if(user1 !=null){
									Passport passport = new Passport(user1);
									
									request.getSession().setAttribute(Passport.PASSPORTNAME, passport);
									
									Cookie cookieUser = new Cookie(USER_LOGIN_COOKIE_INFO, jsonObject.toString());
				            		cookieUser.setPath("/");
				            		cookieUser.setMaxAge(LOGIN_COOKIE_AGE);
				            		response.addCookie(cookieUser);
				            		Object object=request.getSession().getAttribute("loginbeforurl");
				            		String loginbeforurl=object==null?null:object.toString();
				            		if(loginbeforurl!=null&&!"".equals(loginbeforurl)){
				            			request.getSession().removeAttribute("loginbeforurl");
				            			response.sendRedirect("../"+loginbeforurl);
				            		}else{
				            			response.sendRedirect("../");
				            		}
				            		out.flush();
				            		out.close();
				            		return;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			request.getRequestDispatcher("/views/user/login.jsp").forward(request,response);
		}
		
	}
	
	//2.验证用户是否存在(登陆)
	@RequestMapping("/valiLoginName")
	public void valiLoginName(@RequestParam("loginName") String loginName, HttpServletResponse response) throws IOException{
//		String loginName=request.getParameter("loginName");
		User user=userService.valiLoginName(loginName);
		PrintWriter out = response.getWriter();
		if(user!=null){
			out.print("true");
		}else{
			out.print("false");
		}
		out.flush();
		out.close();
	}
	
	//2.验证用户名是否存在(注册)
	@RequestMapping("/loginNameExist")
	public void loginNameExist(@RequestParam("loginName") String loginName, HttpServletResponse response) throws IOException{
//		String loginName=request.getParameter("loginName");
		User user=userService.valiLoginName(loginName);
		PrintWriter out = response.getWriter();
		if(user==null){
			out.print("true");
		}else{
			out.print("false");
		}
		out.flush();
		out.close();
	}
	
	//3.验证用户、密码是否正确
	@RequestMapping("/valiPassword")
	public void valiPassword(@RequestParam("loginName") String loginName,@RequestParam("password") String password,HttpServletResponse response) throws IOException{
//		String loginName=request.getParameter("loginName");
//		String password=request.getParameter("password");
		User user=userService.login(loginName,password);
		PrintWriter out = response.getWriter();
		if(user!=null){
			out.print("true");
		}else{
			out.print("false");
		}
		out.flush();
		out.close();
	}
	
	//4.提交登录
	@RequestMapping("/login")
	public void login(@RequestParam("loginName") String loginName,@RequestParam("password") String password,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

//		String loginName=request.getParameter("loginName");
//		String password=request.getParameter("password");
		if(loginName==null&&password==null){
			response.sendRedirect("gologin");
			return;
		}
		User user = userService.login(loginName , password);
		if(user !=null){
			Passport passport = new Passport(user);
			request.getSession().setAttribute(Passport.PASSPORTNAME, passport);//将user信息以passport保存在session中
			String remember=request.getParameter("remember");
			if(remember!=null && "1".equals(remember)){
    			try {
    				JSONObject jsonObject = new JSONObject();
        			jsonObject.put("loginName", loginName);
        			jsonObject.put("loginId", passport.getUserId());
        			jsonObject.put("isAutoLogin","true");
        			password=DESUtil.encrypt(password);
        			jsonObject.put("p",password);
            		Cookie cookieUser = new Cookie(USER_LOGIN_COOKIE_INFO, jsonObject.toString());
            		cookieUser.setPath("/");
            		cookieUser.setMaxAge(LOGIN_COOKIE_AGE);
            		response.addCookie(cookieUser);
				} catch (Exception e) {
					e.printStackTrace();
				}
        	}
			response.sendRedirect("../");
		}else{
			request.getRequestDispatcher("/views/common/error.jsp").forward(request,response);
		}
	}
	
	//退出
	@RequestMapping("/clearlogin")
	public void clearlogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		request.getSession().invalidate();
		Cookie cookies[] = request.getCookies();
		for (Cookie cookie : cookies) {
			if (USER_LOGIN_COOKIE_INFO.equals(cookie.getName())) {
				JSONObject jsonObject = JSONObject.fromObject(cookie.getValue());
				String loginName = jsonObject.getString("loginName");
				Integer loginId = jsonObject.getInt("loginId");
				String p = jsonObject.getString("p");
				jsonObject = new JSONObject();
				jsonObject.put("loginName", loginName);
				jsonObject.put("loginId", loginId);
				jsonObject.put("isAutoLogin", "false");
				jsonObject.put("p", p);
				Cookie cookieUser = new Cookie(USER_LOGIN_COOKIE_INFO,
						jsonObject.toString());
				cookieUser.setPath("/");
				cookieUser.setMaxAge(LOGIN_COOKIE_AGE);
				response.addCookie(cookieUser);
				break;
			}
		}
		response.sendRedirect("../");	
	}
	
	//我的主页
	@RequestMapping("myinfo")
	private ModelAndView myinfo(HttpServletRequest request, HttpServletResponse response){
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		UserHomeVo homeVo=new UserHomeVo();
		homeVo.setLevel(passport.getLevel());
		ListInfo<User> users=userService.getUserByStatus(passport.getLoginName(), null,1,Constans.PAGESIZE);
		homeVo.setRecommandNum(users.getSizeOfTotalList());
		
		String user_name=passport.getLoginName();
		//父节点
		DiagramVo parent=new DiagramVo();
		
		
		//A区节点
		List<User> aUser=userService.getUserByUserNameAndArea(user_name, "A");
		DiagramVo aVo=null;
		if (aUser!=null&&aUser.size()>0) {
			User userA=aUser.get(0);
			aVo=new DiagramVo();
			int Acount=0;//记录A区节点下的A区节点总数
			int Bcount=0;//记录A区节点下的B区节点总数
			//A区节点 下的A节点
			List<User> aaUser=userService.getUserByUserNameAndArea(userA.getUser_name(), "A");
			//A区节点 下的B节点
			List<User> abUser=userService.getUserByUserNameAndArea(userA.getUser_name(), "B");
			
			if (aaUser!=null&&aaUser.size()>0) {
				Acount=+1;
				List<User> aaAllUser=userService.getAllSon(aaUser.get(0).getUser_name());//A区节点 下的A节点 下的所有子节点
				if (aaAllUser!=null) {
					Acount=+aaAllUser.size();
				}
			}
			
			if (abUser!=null&&abUser.size()>0) {
				Bcount=+1;
				List<User> abAllUser=userService.getAllSon(abUser.get(0).getUser_name());//A区节点 下的B节点 下的所有子节点
				if (abAllUser!=null) {
					Bcount=+abAllUser.size();
				}
			}
			
			aVo.setSumA(Acount*Constans.DIAGRAM);
			aVo.setSumB(Bcount*Constans.DIAGRAM);
			if (Acount>=Bcount) {
				aVo.setJieyuA(aVo.getSumA()-aVo.getSumB());
				aVo.setJieyuB(0);
			}else {
				aVo.setJieyuA(0);
				aVo.setJieyuB(aVo.getSumB()-aVo.getSumA());
			}
		}
		
		//B区节点
		List<User> bUser=userService.getUserByUserNameAndArea(user_name, "B");
		DiagramVo bVo=null;
		if (bUser!=null&&bUser.size()>0) {
			User userB=bUser.get(0);
			bVo=new DiagramVo();
			int Acount=0;//记录A区节点下的A区节点总数
			int Bcount=0;//记录A区节点下的B区节点总数
			//B区节点 下的A节点
			List<User> baUser=userService.getUserByUserNameAndArea(userB.getUser_name(), "A");
			//B区节点 下的B节点
			List<User> bbUser=userService.getUserByUserNameAndArea(userB.getUser_name(), "B");
			
			if (baUser!=null&&baUser.size()>0) {
				Acount=+1;
				List<User> baAllUser=userService.getAllSon(baUser.get(0).getUser_name());//A区节点 下的A节点 下的所有子节点
				if (baAllUser!=null) {
					Acount=+baAllUser.size();
				}
			}
			
			if (bbUser!=null&&bbUser.size()>0) {
				Bcount=+1;
				List<User> bbAllUser=userService.getAllSon(bbUser.get(0).getUser_name());//A区节点 下的B节点 下的所有子节点
				if (bbAllUser!=null) {
					Bcount=+bbAllUser.size();
				}
			}
			
			bVo.setSumA(Acount*Constans.DIAGRAM);
			bVo.setSumB(Bcount*Constans.DIAGRAM);
			if (Acount>=Bcount) {
				bVo.setJieyuA(bVo.getSumA()-bVo.getSumB());
				bVo.setJieyuB(0);
			}else {
				bVo.setJieyuA(0);
				bVo.setJieyuB(bVo.getSumB()-bVo.getSumA());
			}
		}
		
		if (aVo!=null) {
			parent.setSumA(aVo.getSumA()+aVo.getSumB()+Constans.DIAGRAM);
		}
		if (bVo!=null) {
			parent.setSumB(bVo.getSumA()+bVo.getSumB()+Constans.DIAGRAM);
		}
		if (parent.getSumA()>=parent.getSumB()) {
			parent.setJieyuA(parent.getSumA()-parent.getSumB());
			parent.setJieyuB(0);
		}else {
			parent.setJieyuA(0);
			parent.setJieyuB(parent.getSumB()-parent.getSumA());
		}
		
		homeVo.setSumA(parent.getSumA());
		homeVo.setSumB(parent.getSumB());
		homeVo.setJieyuA(parent.getJieyuA());
		homeVo.setJieyuB(parent.getJieyuB());
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("user", homeVo);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("user/home");
		return mv;
	}
	//去修改用户信息
	@RequestMapping("goUpdInfo")
	private ModelAndView goUpdInfo(HttpServletRequest request, HttpServletResponse response){
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		User user=userService.valiLoginName(passport.getLoginName());
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("user", user);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("user/updInfo");
		return mv;
	}
	
	//修改用户信息
	@RequestMapping("updInfo")
	private void updInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		User user=new User();
		user.setUser_id(passport.getUserId());
		user.setTrue_name(request.getParameter("trueName"));
		user.setIDCardNo(request.getParameter("IDCardNo"));
		user.setUser_phone(request.getParameter("phone"));
		
		int result=userService.updInfo(user);
		
		PrintWriter out = response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
		if (result==1) {
			jsonObject.put("state", "true");
		} else {
			jsonObject.put("state", "false");
		}
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//去修改密码
	@RequestMapping("goUpdPassword")
	private ModelAndView goUpdPassword(HttpServletRequest request, HttpServletResponse response){
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("user/updPassword");
		return mv;
	}
	
	//修改密码
	@RequestMapping("updPassword")
	private void updPassword(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Passport user=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		
		String type=request.getParameter("type");
		String newPwd=request.getParameter("newPwd");
		int result=userService.updPass(user.getUserId(), type, newPwd);
		
		PrintWriter out = response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
		if (result==1) {
			jsonObject.put("state", "true");
		} else {
			jsonObject.put("state", "false");
		}
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//验证2级密码
	@RequestMapping("valiPwd2")
	private void valiPwd2(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		User user=userService.getByPwd2(passport.getUserId(), request.getParameter("pwd2"));
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");// request.getRequestURL()
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user==null){
			out.write("false");
		}else{
			out.write("true");
		}
		out.flush();
		out.close();
	}
	
	//验证密码
	@RequestMapping("valiPwd")
	private void valiPwd(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		String type=request.getParameter("type");
		String password=request.getParameter("oldPwd");
		User user=null;
		if ("1".equals(type)) {//验证登录密码
			user=userService.login(passport.getLoginName(),password);
		}else if ("2".equals(type)) {//验证二级密码
			user=userService.getByPwd2(passport.getUserId(),password);
		}
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");// request.getRequestURL()
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		if(user==null){
			out.write("false");
		}else{
			out.write("true");
		}
		out.flush();
		out.close();
	}
	
}
