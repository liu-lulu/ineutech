package com.kkbc.control.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kkbc.control.BaseController;
import com.kkbc.entity.User;
import com.kkbc.service.UserService;
import com.kkbc.util.DESUtil;
import com.kkbc.vo.Passport;
import com.opensymphony.xwork2.ActionContext;

/**
 * 用户模块
 * 
 * @author liululu
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	
	public static final String USER_LOGIN_COOKIE_INFO = "AS_USER_LOGIN_COOKIE_INFO"; // 用户记住密码Cookie
	public static final int LOGIN_COOKIE_AGE = 7 * 24 * 60 * 60; // Cookie登录记住时间
	@Resource
	private UserService userService;

	@RequestMapping("/goLogin")
	public void goLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Object object = request.getSession().getAttribute("loginbeforurl");
		String loginbeforurl = object == null ? null : object.toString();

		if (getPassport(request) != null) {
			if (loginbeforurl != null && !"".equals(loginbeforurl)) {
				request.getSession().removeAttribute("loginbeforurl");
				response.sendRedirect("../" + loginbeforurl);
			} else {
				response.sendRedirect("../");
			}
			return;
		}

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			String cookieName = null;
			for (Cookie cookie : cookies) {
				cookieName = cookie.getName();
				if (cookieName.equals("AS_USER_LOGIN_COOKIE_INFO")) {
					try {
						JSONObject jsonObject = JSONObject.fromObject(cookie
								.getValue());
						String isAutoLogin = jsonObject
								.getString("isAutoLogin");
						String loginName = jsonObject.getString("loginName");
						Integer loginId = Integer.valueOf(jsonObject
								.getInt("loginId"));
						String p = jsonObject.getString("p");
						if ((isAutoLogin.equals("true")) && (loginId != null)
								&& (loginName != null)
								&& (!loginName.equals("")) && (p != null)) {
							p = DESUtil.decrypt(p);
							Passport passport = this.userService
									.loginByUPassport(loginName, p);
							if (passport != null) {
								request.getSession().setAttribute(
										Passport.PASSPORTNAME, passport);
								if (loginbeforurl != null
										&& !"".equals(loginbeforurl)) {
									request.getSession().removeAttribute(
											"loginbeforurl");
									response.sendRedirect("../" + loginbeforurl);
								} else {
									response.sendRedirect("../");
								}
							}
							return;
						}
					} catch (Exception localException) {
						localException.printStackTrace();
					}
				}
			}
		}
		request.getRequestDispatcher("/jsp/user/login.jsp").forward(request,
				response);
	}

	@RequestMapping("/searchByUserName")
	public void searchByUserName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		User user=userService.loginByUserName(username);
		PrintWriter out = response.getWriter();
		if(user!=null){
			out.print("true");
		}else{
			out.print("false");
		}
		out.flush();
		out.close();
	}

	@RequestMapping("/checkUser")
	public void checkUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user=userService.loginByUserNamePassword(username, password);
		PrintWriter out = response.getWriter();
		if(user!=null){
			out.print("true");
		}else{
			out.print("false");
		}
		out.flush();
		out.close();
	}

	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remember=request.getParameter("remember");
		
		Object object = request.getSession().getAttribute("loginbeforurl");
		String loginbeforurl = object == null ? null : object.toString();
		
		Passport passport=this.userService.loginByUPassport(username, password);
		if (passport != null) {
			request.getSession().setAttribute(Passport.PASSPORTNAME, passport);//将user信息以passport保存在session中
			if(remember!=null && "1".equals(remember)){
    			try {
    				JSONObject jsonObject = new JSONObject();
        			jsonObject.put("loginName", username);
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
			response.sendRedirect("../device/golisttest.htm");
			return;
		}
		request.getRequestDispatcher("/jsp/user/login.jsp").forward(request,response);
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
