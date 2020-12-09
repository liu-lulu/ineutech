package com.kkbc.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kkbc.service.UserService;
import com.kkbc.util.DESUtil;
import com.kkbc.vo.Passport;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

public class AdminLoginInterceptor implements HandlerInterceptor 
{
	@Resource
	  private UserService userService;

	  public void destroy()
	  {
	  }

	  public void init()
	  {
	  }

	  public String intercept(ActionInvocation invocation)
	    throws Exception{
	    if (ActionContext.getContext().getSession().get("kkbcPassport") == null){
	      {
	        Passport passporttemp= this.userService.loginByUPassport("admin", "111");
	        if (passporttemp != null){
	          ActionContext.getContext().getSession().put("kkbcPassport", passporttemp);
	          invocation.invoke();
	          return null;
	        }
	        Cookie[] cookies=ServletActionContext.getRequest().getCookies();
	        if (cookies != null){
	        	String cookieName = null;
	        	for (Cookie cookie : cookies){
	        	  cookieName = cookie.getName();
	        	  if (cookieName.equals("AS_USER_LOGIN_COOKIE_INFO")){
	            	try {
						JSONObject jsonObject = JSONObject.fromObject(
									cookie.getValue());
		                String isAutoLogin = jsonObject.getString("isAutoLogin");
		                String loginName = jsonObject.getString("loginName");
		                Long loginId = Long.valueOf(jsonObject.getLong("loginId"));
		                String p = jsonObject.getString("p");
		                if ((!isAutoLogin.equals("true")) || (loginId == null) || (loginName == null) || (loginName.equals("")) || (p == null)) break;
		                try {
		                  p = DESUtil.decrypt(p);
		                  Passport passport = this.userService.loginByUPassport(loginName, p);
		                  if (passport == null) {
		                    break;
		                  }
		                  ActionContext.getContext().getSession().put("kkbcPassport",passport);
		                  invocation.invoke();
		                } catch (Exception localException1) {
		                   localException1.printStackTrace();
		                }
	              }
	              catch (Exception localException2) {
	                localException2.printStackTrace();
	              }
	          }
	        }
	      }
	      HttpServletRequest request=ServletActionContext.getRequest();
	      StringBuffer rString = request .getRequestURL();
	      String query=request.getQueryString();
	      if (query != null){
	        rString.append("?" + query);
	      }
	      System.out.println(rString.toString());
	      ActionContext.getContext().getSession().put("loginbeforurl", rString.toString());
	      return "login";
	    }
	  }
		return null;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {

	    if (ActionContext.getContext().getSession().get("kkbcPassport") == null){
	      {
	        Passport passporttemp= this.userService.loginByUPassport("admin", "111");
	        if (passporttemp != null){
	          ActionContext.getContext().getSession().put("kkbcPassport", passporttemp);
//	          invocation.invoke();
	          return false;
	        }
	        Cookie[] cookies=ServletActionContext.getRequest().getCookies();
	        if (cookies != null){
	        	String cookieName = null;
	        	for (Cookie cookie : cookies){
	        	  cookieName = cookie.getName();
	        	  if (cookieName.equals("AS_USER_LOGIN_COOKIE_INFO")){
	            	try {
						JSONObject jsonObject = JSONObject.fromObject(
									cookie.getValue());
		                String isAutoLogin = jsonObject.getString("isAutoLogin");
		                String loginName = jsonObject.getString("loginName");
		                Long loginId = Long.valueOf(jsonObject.getLong("loginId"));
		                String p = jsonObject.getString("p");
		                if ((!isAutoLogin.equals("true")) || (loginId == null) || (loginName == null) || (loginName.equals("")) || (p == null)) break;
		                try {
		                  p = DESUtil.decrypt(p);
		                  Passport passport = this.userService.loginByUPassport(loginName, p);
		                  if (passport == null) {
		                    break;
		                  }
		                  ActionContext.getContext().getSession().put("kkbcPassport",passport);
		                  break;
//		                  invocation.invoke();
		                } catch (Exception localException1) {
		                   localException1.printStackTrace();
		                }
	              }
	              catch (Exception localException2) {
	                localException2.printStackTrace();
	              }
	          }
	        }
	      }
	      HttpServletRequest request=ServletActionContext.getRequest();
	      StringBuffer rString = request .getRequestURL();
	      String query=request.getQueryString();
	      if (query != null){
	        rString.append("?" + query);
	      }
	      System.out.println(rString.toString());
	      ActionContext.getContext().getSession().put("loginbeforurl", rString.toString());
	    }
	  }
	
		return false;
	}
}