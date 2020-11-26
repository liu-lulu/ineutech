package com.kkbc.control.interceptor;

import java.io.PrintWriter;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kkbc.entity.ManageUser;

public class SystemSessionInterceptor implements HandlerInterceptor{

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
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
 Object arg2) throws Exception {
		ManageUser managerUser = ((ManageUser) request.getSession()
				.getAttribute("manager"));
		if (managerUser == null) {
			// 如果request.getHeader("X-Requested-With")
			// 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理 否则直接重定向就可以了
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				// 告诉ajax我是重定向
				response.setHeader("REDIRECT", "REDIRECT");
				// 告诉ajax我重定向的路径
				response.setHeader("CONTENTPATH", request.getContextPath());
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			} else {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				StringBuilder builder = new StringBuilder();
				builder.append("<script>");
				builder.append("alert('登录已过期，请重新登录哟！');");
				builder.append("window.location.href='"
						+ request.getContextPath() + "';");
				builder.append("</script>");
				out.print(builder.toString());
				out.close();
			}
			return false;
		}
		return true;

	}

}
