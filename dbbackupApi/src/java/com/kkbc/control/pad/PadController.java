package com.kkbc.control.pad;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.gexin.fastjson.JSONObject;
import com.kkbc.util.RunSqlScript;

/**
 * 用户模块
 * 
 * @author liululu
 *
 */
@Controller
@RequestMapping("/backup")
public class PadController {
	
	private Logger logger = LoggerFactory.getLogger(PadController.class);
	
	/*@Resource
	private TestService testService;*/

	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex,HttpServletResponse response) throws IOException  {
		logger.error("请求出现异常:"+ex.getMessage());
		
		PrintWriter out =  response.getWriter();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", 2);
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	

	
	@RequestMapping("upload")
	public void filesUpload(MultipartFile file,HttpServletResponse response,HttpServletRequest request) throws IOException {
		
		PrintWriter out = response.getWriter();
		
		JSONObject jsonObject = new JSONObject();
		
		
		// 判断file数组不能为空并且长度大于0
		if (!file.isEmpty()) {
			logger.info("备份开始");
			boolean state=RunSqlScript.run(file);
			logger.info("备份结束");
			jsonObject.put("state", state?1:0);
		}else {
			jsonObject.put("state", 3);
		}
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	

	



}
