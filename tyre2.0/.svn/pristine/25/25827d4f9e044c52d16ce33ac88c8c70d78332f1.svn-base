package com.psylife.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.psylife.entity.User;
import com.psylife.service.UserService;
import com.psylife.service.impl.UserServiceImpl;
import com.psylife.servlet.BaseServlet;
import com.psylife.util.DESUtil;
import com.psylife.vo.Passport;

/**
 * 用户模块
 * @author xu
 *
 */
@WebServlet(urlPatterns = { "/user/*" }, loadOnStartup = 1)
public class UserServlet extends BaseServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8359962057712728909L;
	
	public static final String USER_LOGIN_COOKIE_INFO = "R_USER_LOGIN_COOKIE_INFO"; // 用户记住密码Cookie
	
	public static final int LOGIN_COOKIE_AGE = 7 * 24 * 60 * 60; // Cookie登录记住时间
	
	private UserService userService=new UserServiceImpl();
	
	public UserServlet() {
		super();
	}
	
	/**
	 * 初始配置
	 */
	@Override
	protected void initConfig() {
		filterInterceptor(new String[]{"gologin","valiLoginName","valiPassword","login"});//不需要用户权限的action
	}
	
	@Override
	protected void doProccess(HttpServletRequest request,
			HttpServletResponse response,String action) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");// request.getRequestURL()
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		// 1.去登录
		if ("gologin".equals(action)) {
			if(getPassport(request)!=null){
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
					            			response.sendRedirect(loginbeforurl);
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
				request.getRequestDispatcher("/WEB-INF/jsp/user/login.jsp").forward(request,response);
			}
			
		}
		
		//2.验证用户是否存在
		else if("valiLoginName".equals(action)){
			String loginName=request.getParameter("loginName");
			User user=userService.valiLoginName(loginName);
			if(user!=null&&user.getUser_role()==User.USER_ROLE_ADMIN){
				out.print("true");
			}else{
				out.print("false");
			}
		}
		
		//3.验证用户、密码是否正确
		else if("valiPassword".equals(action)){
			String loginName=request.getParameter("loginName");
			String password=request.getParameter("password");
			User user=userService.login(loginName,password);
			if(user!=null&&user.getUser_role()==User.USER_ROLE_ADMIN){
				out.print("true");
			}else{
				out.print("false");
			}
		}	
		
		//4.提交登录
		else if("login".equals(action)){
			String loginName=request.getParameter("loginName");
			String password=request.getParameter("password");
			if(loginName==null&&password==null){
				response.sendRedirect("gologin");
				out.flush();
				out.close();
				return;
			}
			User user1 = userService.login(loginName , password);
			if(user1 !=null&&user1.getUser_role()==User.USER_ROLE_ADMIN){
				Passport passport = new Passport(user1);
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
				request.getRequestDispatcher("/WEB-INF/jsp/common/error.jsp").forward(request,response);
			}
		}
		//退出
		else if("clearlogin".equals(action)){
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
		//登录成功处理
		else{
			error404(request, response);
		}
		out.flush();
		out.close();
	}
	
}
