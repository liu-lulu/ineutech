package com.kkbc.control.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kkbc.control.BaseController;
import com.kkbc.entity.User;
import com.kkbc.service.UserService;
import com.kkbc.vo.Passport;
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
								if(user1 !=null&&user1.getUser_role()==User.USER_ROLE_ADMIN){
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
	
	//2.验证用户是否存在
	@RequestMapping("/valiLoginName")
	public void valiLoginName(@RequestParam("loginName") String loginName, HttpServletResponse response) throws IOException{
//		String loginName=request.getParameter("loginName");
		User user=userService.valiLoginName(loginName);
		PrintWriter out = response.getWriter();
		if(user!=null&&user.getUser_role().intValue()==User.USER_ROLE_ADMIN){
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
		if(user!=null&&user.getUser_role().intValue()==User.USER_ROLE_ADMIN){
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
		if(user !=null&&user.getUser_role().intValue()==User.USER_ROLE_ADMIN){
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
	
}
