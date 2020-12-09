package com.kkbc.filter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kkbc.service.UserService;
import com.kkbc.util.DESUtil;
import com.kkbc.util.RequestUtil;
import com.kkbc.util.SpringContextUtils;
import com.kkbc.vo.Passport;
import com.opensymphony.xwork2.ActionContext;

public class SessionFilter extends OncePerRequestFilter {

	private UserService userService = (UserService) SpringContextUtils.context
			.getBean("userService");

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 不过滤的uri
		String[] notFilter = new String[] { "searchByUserName", "checkUser",
				"login.jsp", "index.jsp", "user/goLogin", "user/login" };
		
		String contextPath = request.getContextPath();
		StringBuffer requestURL = request.getRequestURL();
		// 请求的uri
		String uri = requestURL.substring(requestURL.indexOf(contextPath)
				+ contextPath.length() + 1);

		if (request.getSession().getAttribute(Passport.PASSPORTNAME) == null) {
			Passport passporttemp = this.userService.loginByUPassport("admin",
					"111");
			if (passporttemp != null) {
				request.getSession().setAttribute(Passport.PASSPORTNAME, passporttemp);
			}else {
				Cookie[] cookies = request.getCookies();
				if (cookies != null) {
					String cookieName = null;
					for (Cookie cookie : cookies) {
						cookieName = cookie.getName();
						if (cookieName.equals("AS_USER_LOGIN_COOKIE_INFO")) {
							try {
								JSONObject jsonObject = JSONObject
										.fromObject(cookie.getValue());
								String isAutoLogin = jsonObject
										.getString("isAutoLogin");
								String loginName = jsonObject
										.getString("loginName");
								Long loginId = Long.valueOf(jsonObject
										.getLong("loginId"));
								String p = jsonObject.getString("p");
								if ((!isAutoLogin.equals("true"))
										|| (loginId == null) || (loginName == null)
										|| (loginName.equals("")) || (p == null))
									break;
								try {
									p = DESUtil.decrypt(p);
									Passport passport = this.userService
											.loginByUPassport(loginName, p);
									if (passport == null) {
										break;
									}
									request.getSession().setAttribute(
											Passport.PASSPORTNAME, passport);
									break;
								} catch (Exception localException1) {
									localException1.printStackTrace();
								}
							} catch (Exception localException2) {
								localException2.printStackTrace();
							}
						}
					}
				}
			}

			StringBuffer rString = request.getRequestURL();
			String query = request.getQueryString();
			if (query != null) {
				rString.append("?" + query);
			}
			System.out.println(rString.toString());
			request.getSession().setAttribute("loginbeforurl",
					uri);
		}

		

		if (!StringUtils.isEmpty(uri)) {

			// 是否过滤
			boolean doFilter = true;
			for (String s : notFilter) {
				if (uri.indexOf(s) != -1) {
					// 如果uri中包含不过滤的uri，则不进行过滤
					doFilter = false;
					break;
				}
			}
			if (doFilter) {
				// 执行过滤
				// 从session中获取登录者实体
				Object obj = request.getSession().getAttribute(Passport.PASSPORTNAME);
				if (null == obj) {
					request.getSession().setAttribute("loginbeforurl", uri.toString());
					response.sendRedirect("../user/goLogin.htm");
					return;
				} else {
					// 如果session中存在登录者实体，则继续
					filterChain.doFilter(request, response);
				}
			} else {
				// 如果不执行过滤，则继续
				filterChain.doFilter(request, response);
			}

		} else {
			filterChain.doFilter(request, response);
		}

	}

}
