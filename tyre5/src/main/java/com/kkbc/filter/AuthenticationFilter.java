package com.kkbc.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kkbc.entity.UserToken;
import com.kkbc.service.UserTokenService;
import com.kkbc.service.impl.UserTokenServiceImpl;
import com.psylife.util.DESUtil;
import com.psylife.util.RequestUtil;
import com.psylife.util.SpringContextUtils;
import com.psylife.util.StringHelper;

public class AuthenticationFilter extends OncePerRequestFilter {


	private UserTokenService userTokenService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		ServletContext sc = request.getSession().getServletContext();
		XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);
		if(cxt != null){
			SpringContextUtils.setApplicationContext(cxt);
			if (cxt.getBean("userTokenService") != null && userTokenService == null) {
				userTokenService = (UserTokenServiceImpl) cxt.getBean("userTokenService");
			}
		}
		response.setContentType("text/html; charset=utf-8");  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out=response.getWriter();
		JSONObject jsonObject=new JSONObject();

		// 过滤的uri
		String filter = "ServletAll.do";
		String exceptFilter="action=updateProfile";//表单提交
		
		String notFilter="action=login";

		String contextPath = request.getContextPath();
		StringBuffer requestURL = request.getRequestURL();
		// 请求的uri
		String uri = requestURL.substring(requestURL.indexOf(contextPath)
				+ contextPath.length() + 1);
		
		if (!StringUtils.isEmpty(uri)) {

			// 是否过滤
			boolean doFilter = true;
//			for (String s : notFilter) {
//				if (uri.indexOf(s) != -1) {
//					// 如果uri中包含不过滤的uri，则不进行过滤
//					doFilter = false;
//					break;
//				}
//			}
			if (uri.indexOf(filter) != -1&&request.getQueryString().indexOf(exceptFilter)==-1) {
				
				String param=RequestUtil.buildOriginalURL(request);
				System.out.println(RequestUtil.buildOriginalURL(request));
				String client_time=request.getParameter("client_time");
				
				if (StringUtils.isEmpty(client_time)) {//时间参数
					jsonObject.put("state", 100);
					out.print(jsonObject.toString());
					out.flush();
					out.close();
					return;
				}else {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					try {
						long interval_time=(sdf.parse(client_time).getTime()-System.currentTimeMillis())/1000/60;//请求时间间隔>1分钟
						if (Math.abs(interval_time)>2) {
							jsonObject.put("state", 100);
							out.print(jsonObject.toString());
							out.flush();
							out.close();
							return;
						}else {
							String singal=request.getParameter("singal");
							if (StringUtils.isNotEmpty(singal)) {//加密
								String encryptParam=DESUtil.encrypt(param);
								if (!singal.equals(encryptParam)) {//查看参数是否被修改
									jsonObject.put("state", 100);
									out.print(jsonObject.toString());
									out.flush();
									out.close();
									return;
								}else {//查看token
									if (request.getQueryString().indexOf(notFilter)==-1) {//登陆操作不验证token
										String token=request.getParameter("token");
										if (StringUtils.isNotEmpty(token)) {
											UserToken tokenInfo=userTokenService.getTokenInfo(token);
											if (tokenInfo!=null) {//暂定token信息有效时间5小时
												long token_time=(System.currentTimeMillis()-tokenInfo.getCreate_time().getTime())/1000/60/60;
												if (token_time>5) {//token失效
													jsonObject.put("state", 100);
													out.print(jsonObject.toString());
													out.flush();
													out.close();
													return;
												}else {
													request.setAttribute("user_id", tokenInfo.getUser_id());
//													request.getParameterMap().put("user_id", new String[]{tokenInfo.getUser_id()+""});
													filterChain.doFilter(request, response);
												}
											}else {//token信息失效
												jsonObject.put("state", 100);
												out.print(jsonObject.toString());
												out.flush();
												out.close();
												return;
											}
										}else {//请求参数没有token值
											jsonObject.put("state", 100);
											out.print(jsonObject.toString());
											out.flush();
											out.close();
											return;
										}
									}
								}
							}else {//请求参数没有signal值
								jsonObject.put("state", 100);
								out.print(jsonObject.toString());
								out.flush();
								out.close();
								return;
							}
						}
					} catch (ParseException e) {
						logger.error("时间参数格式不正确:"+StringHelper.getTrace(e));
						return;
					}
				}
			} else {
				// 如果不执行过滤，则继续
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}
		
//		filterChain.doFilter(request, response);
	}
	
}
