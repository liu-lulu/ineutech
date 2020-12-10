package com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport{
	
	/**
	 * 获得HttpServletRequest
	 * @return
	 */
	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
		
	}
	/**
	 * 获得HttpServletResponse
	 * @return
	 */
	public HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	/**
	 * 获得session
	 * @return
	 */
	public HttpSession getSession(){
		return ServletActionContext.getRequest().getSession();
	}
	public Map<String, Object> getStrutsSession() {
		return ActionContext.getContext().getSession();
	}
	
	public PrintWriter getWriteOut() throws IOException{
		return ServletActionContext.getResponse().getWriter();
	}

}
