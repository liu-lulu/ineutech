package com.kkbc.filter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kkbc.vo.Passport;
import com.psylife.util.DESUtil;
import com.psylife.util.RequestUtil;

public class SessionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 不过滤的uri
		String[] notFilter = new String[] {"valiLoginName","valiPassword" ,"login.jsp", "index.jsp",
				"user/gologin","user/login","installpicture","ServletAll","terminal/" };

		String contextPath = request.getContextPath();
		StringBuffer requestURL = request.getRequestURL();
		// 请求的uri
		String uri = requestURL.substring(requestURL.indexOf(contextPath)
				+ contextPath.length() + 1);
		
//		String param=RequestUtil.buildOriginalURL(request);
//		System.out.println(RequestUtil.buildOriginalURL(request));
//		String client_time=request.getParameter("client_time");
//		if (StringUtils.isEmpty(client_time)) {
//			return;
//		}else {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//			try {
//				long interval_time=(sdf.parse(client_time).getTime()-System.currentTimeMillis())/1000/60;//请求时间间隔>1分钟
//				if (Math.abs(interval_time)>1) {
//					return;
//				}else {
//					//加密
//					String encryptParam=DESUtil.encrypt(param);
//					if (!request.getParameter("singal").equals(encryptParam)) {//查看参数是否被修改
//						return;
//					}
//				}
//			} catch (ParseException e) {
//				e.printStackTrace();
//				return;
//			}
//		}
		
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
					// 如果session中不存在登录者实体，则弹出框提示重新登录
					// 设置request和response的字符集，防止乱码
//					request.setCharacterEncoding("UTF-8");
//					response.setCharacterEncoding("UTF-8");
//					response.setContentType("text/html;charset=utf-8");
//					PrintWriter out = response.getWriter();
//					String loginPage = "../user/gologin";
//					StringBuilder builder = new StringBuilder();
//					builder.append("<script type=\"text/javascript\">");
//					builder.append("alert('网页过期，请重新登录！');");
//					builder.append("top.location.href='");
//					builder.append(loginPage);
//					builder.append("';");
//					builder.append("</script>");
//					out.print(builder.toString());
					request.getSession().setAttribute("loginbeforurl", uri.toString());
					response.sendRedirect("../user/gologin.do");
					
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
