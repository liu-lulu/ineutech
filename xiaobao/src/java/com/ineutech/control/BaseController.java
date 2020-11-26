package com.ineutech.control;

import com.ineutech.util.StringHelper;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class BaseController {
	static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	 /** 
	     * 使用@ExceptionHandler注解，继承此类的Controller发生异常时会自动执行该方法 
	     * @param request 
	     * @param e 
	     * @return 
	     */  
	    @ExceptionHandler  
	    public String exception(HttpServletRequest request, Exception e) {  
	        //对异常进行判断做相应的处理  
	    	logger.error(StringHelper.getTrace(e));
            return "/views/common/error.jsp";
	    }  

}
