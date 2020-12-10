package com.psylife.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.psylife.vo.Passport;

/**
 * 基本类
 */
public class BaseServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	private static int comeNum = 1, overNum = 1;
    /**
     * 是否启动url中的action，还是参数中的action，如:
     * 等于true时，http://localhost:8080/szhd/user/dddd.action时，urlAction=dddd,urlAction=null
     * 等于false时，http://localhost:8080/szhd/user/dddd.action?action=sdfd时，urlAction=null,urlAction=sdfd
     */
	private boolean isUrlAction=true;//
	private Map<String, Integer> noAuthFilter=new HashMap<String, Integer>();
	public BaseServlet() {
		super();
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action=null;
		String urlAction;
		if(isUrlAction){
			urlAction=request.getPathInfo();
			if(urlAction!=null&&!"".equals(urlAction)){
				if(urlAction.indexOf("/")==0){
					urlAction=urlAction.replace("/","");
					if(urlAction.lastIndexOf(".")>0){
						urlAction=urlAction.substring(0, urlAction.lastIndexOf("."));
						action=urlAction;
					}
				}
			}
		}else{
			action = request.getParameter("action");
		}
		System.out.println("action name is >" + action);
		System.out.print("<><xy><><><><><><>come in action " + comeNum++);
		if(noAuthFilter.get(action)!=null||request.getSession().getAttribute(Passport.PASSPORTNAME) != null){//权限
			doProccess(request, response,action);
		}else{
			StringBuffer rString=request.getRequestURL();
    		String query=request.getQueryString();
    		if (query!=null) {
    			rString.append("?"+query);
			}
			request.getSession().setAttribute("loginbeforurl", rString.toString());
			response.sendRedirect("../user/gologin.action");
		}
		
		System.out.println("<><><><><><><><>over out action " + (overNum++)
				+ ">>>" + (overNum - comeNum));
	}
	
	@Override
	public void init() throws ServletException {
		initConfig();	
	}
	
	/**
	 * 初始配置
	 */
	protected void initConfig(){
		
	}
	
	/**
	 * 用户拦截器
	 * @param noInterceptorAction 不需要拦截action
	 */
	protected void filterInterceptor(String[] noInterceptorAction){
		if(noInterceptorAction!=null){
			for(int i=0;i<noInterceptorAction.length;i++){
				noAuthFilter.put(noInterceptorAction[i], 1);
			}
		}
	}
	
	/**
	 * 处理类
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doProccess(HttpServletRequest request,
			HttpServletResponse response,String action) throws ServletException, IOException {
		
	}
	
	protected void parameterProccess(Map<String, Object> paramaterMap,HttpServletRequest request){
		if (paramaterMap != null) {
			for (String key : paramaterMap.keySet()) {
				request.setAttribute(key, paramaterMap.get(key));
			}
		}
	}
	
	/**
	 * 用户
	 * @return
	 */
	public Passport getPassport(HttpServletRequest request) {
		Passport passport = request.getSession().getAttribute(Passport.PASSPORTNAME) == null ? null
				: (Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		return passport;
	}
	
	/**
	 * 404
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException 
	 */
	public void error404(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		request.getRequestDispatcher("/WEB-INF/jsp/common/error.jsp").forward(request,response);
	}
	
	public boolean isUrlAction() {
		return isUrlAction;
	}
	public void setUrlAction(boolean isUrlAction) {
		this.isUrlAction = isUrlAction;
	}
}
