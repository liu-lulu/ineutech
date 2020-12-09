package com.kkbc.control.pad;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.kkbc.cons.Constans;

/**
 * 用户模块
 * 
 * @author liululu
 *
 */
@Controller
@RequestMapping("/pad")
public class PadController {
	
	private Logger logger = LoggerFactory.getLogger(PadController.class);

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
	
	@PostConstruct
	public void init() {
		File file = new File(Constans.UPLOAD_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		File yifuStudentNote = new File(Constans.YIFU_STUDENT_NOTE);
		if (!yifuStudentNote.exists()) {
			yifuStudentNote.mkdirs();
		}
		
		File yifuStudentResult = new File(Constans.YIFU_STUDENT_RESULT);
		if (!yifuStudentResult.exists()) {
			yifuStudentResult.mkdirs();
		}
		
		File yifuTeacher = new File(Constans.YIFU_TEACHER);
		if (!yifuTeacher.exists()) {
			yifuTeacher.mkdirs();
		}
		
		File yifuScore = new File(Constans.YIFU_SCORE);
		if (!yifuScore.exists()) {
			yifuScore.mkdirs();
		}
		
		File yifuBind = new File(Constans.YIFU_BIND);
		if (!yifuBind.exists()) {
			yifuBind.mkdirs();
		}
		
		File shengdaStudentNote = new File(Constans.SHENGDA_STUDENT_NOTE);
		if (!shengdaStudentNote.exists()) {
			shengdaStudentNote.mkdirs();
		}
		
		File shengdaStudentResult = new File(Constans.SHENGDA_STUDENT_RESULT);
		if (!shengdaStudentResult.exists()) {
			shengdaStudentResult.mkdirs();
		}
		
		File shengdaTeacher = new File(Constans.SHENGDA_TEACHER);
		if (!shengdaTeacher.exists()) {
			shengdaTeacher.mkdirs();
		}
	}
	
	@RequestMapping("upload")
	public void filesUpload(MultipartFile[] files,HttpServletResponse response,HttpServletRequest request) throws IOException {
		String type=request.getParameter("type");
		
		PrintWriter out = response.getWriter();
		
		JSONObject jsonObject = new JSONObject();
		
		boolean state=true;
		
		// 判断file数组不能为空并且长度大于0
		if (files != null && files.length > 0) {
			logger.info("上传文件个数:"+files.length+";type="+type);
			
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				// 保存文件
				if (!saveFile(file,type)) {
					state=false;
				}
			}
			jsonObject.put("state", state?1:0);
		}else {
			logger.info("没有上传的文件;type="+type);
			jsonObject.put("state", 3);
		}
		
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}

	/***
	 * 保存文件
	 * 
	 * @param file
	 * @return
	 */
	private boolean saveFile(MultipartFile file,String type) {
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				String fileName=file.getOriginalFilename();
				// 文件保存路径
				String filePath = getPath(type,fileName)
						+ fileName.substring(0,fileName.lastIndexOf("."))+format.format(new Date())+fileName.substring(fileName.lastIndexOf("."));
				
				logger.info("文件路径:"+filePath);
				// 转存文件
				file.transferTo(new File(filePath));
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	private String getPath(String type,String fileName){
		if ("1".equals(type)) {
			if (fileName.contains(Constans.NOTE)) {
				return Constans.YIFU_STUDENT_NOTE;
			}else if (fileName.contains(Constans.RESULT)) {
				return Constans.YIFU_STUDENT_RESULT;
			}
			return Constans.YIFU_STUDENT;
		}
		if ("2".equals(type)) {
			return Constans.YIFU_TEACHER;
		}
		if ("3".equals(type)) {
			return Constans.YIFU_SCORE;
		}
		if ("4".equals(type)) {
			return Constans.YIFU_BIND;
		}
		if ("5".equals(type)) {
			if (fileName.contains(Constans.NOTE)) {
				return Constans.SHENGDA_STUDENT_NOTE;
			}else if (fileName.contains(Constans.RESULT)) {
				return Constans.SHENGDA_STUDENT_RESULT;
			}
			return Constans.SHENGDA_STUDENT;
		}
		if ("6".equals(type)) {
			return Constans.SHENGDA_TEACHER;
		}
		
		return Constans.UPLOAD_PATH;
	}
	
	public static void main(String[] args) {
		String aaString="qwee.txt";
		System.out.println(aaString.substring(0,aaString.lastIndexOf("."))+"-----"+aaString.substring(aaString.lastIndexOf(".")));
	}

}
