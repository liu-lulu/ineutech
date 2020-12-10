package com.psylife.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kkbc.entity.User;
import com.kkbc.entity.UserToken;
import com.kkbc.service.UserService;
import com.kkbc.service.UserTokenService;
import com.kkbc.service.impl.UserServiceImpl;
import com.kkbc.service.impl.UserTokenServiceImpl;

public class UplodImage {
	
	static final Logger logger = LoggerFactory.getLogger(UplodImage.class);
	
	//Service变量
	private static UserService userService = (UserServiceImpl)SpringContextUtils.context.getBean("userService");
	private static UserTokenService userTokenService = (UserTokenServiceImpl)SpringContextUtils.context.getBean("userTokenService");
	
	
	/**
	 * 个人信息修改
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public synchronized  static int updateProfile(HttpServletRequest request,Map<String, String> returnMap){
//		DiskFileUpload upload = new DiskFileUpload();
//		// 设置允许用户上传文件大小
//		upload.setFileSizeMax(10 * 1024 * 1024);
//		// 设置只允许在内存中存放的数据大小
//		upload.setSizeThreshold(10 * 1024 * 1024);
//		// 设置文件大小超过getSizeThreshold()的大小,数据存放入硬盘
//		upload.setRepositoryPath(Constants.UPLOADURL_TEMP);
//
//		upload.setHeaderEncoding("utf-8");
		
		logger.info("个人信息修改正在提交..");
		RequestContext requestContext = new ServletRequestContext(request);
		if(!FileUpload.isMultipartContent(requestContext)){
			logger.info("个人信息修改提交>>uploadUser>>不是表单上传");
		   return -1;
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		File filedir=new File(Constants.UPLOADURL_TEMP);
		if(!filedir.exists()){
			filedir.mkdirs();
		}
		factory.setRepository(new File(Constants.UPLOADURL_TEMP));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(30 * 1024 * 1024);
		upload.setHeaderEncoding("utf-8");

		List fileItems = null;
		try {
			fileItems = upload.parseRequest(request);
		}catch(FileSizeLimitExceededException ee){
			ee.printStackTrace();
			logger.error("个人信息修改文件过大:"+StringHelper.getTrace(ee));
			return -2;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("个人信息修改提交:"+StringHelper.getTrace(e));
			return -1;
		}
		Iterator iter = fileItems.iterator(); //
		String name = "";
		String fieldvalue;
		Map<String, String> map=new HashMap<String, String>();
		String imageName=null;
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (!item.isFormField()) {
				name = item.getName();
				long size = item.getSize();
				if ((name != null && !"".equals(name)) && size > 0) {
					name =  "show_"+UUID.randomUUID().toString()+ ".png";
					File fNew = new File(Constants.UPLOADURL, name);
					imageName=map.get("image");
					if(imageName==null){
						imageName=name;
					}else{
						imageName=imageName+ ","+name;
					}
					map.put("image",imageName);
					try {
						item.write(fNew);
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("作业提交:"+StringHelper.getTrace(e));
					}
					CompressPicDemo mypic = new CompressPicDemo();
					if (size > 1024 * 1024 * 1) {
						mypic.compressPicToSourceWH(Constants.UPLOADURL, Constants.UPLOADURL, name, name, 100,
								100, true);
					}
				}
			}else{
				try {
					fieldvalue=item.getString("utf-8");
					name=item.getFieldName();
					map.put(name, fieldvalue);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					logger.error("个人信息修改:"+StringHelper.getTrace(e));
				}
			}
		}
		UserToken tokenInfo=userTokenService.getTokenInfo(map.get("token"));
		if (tokenInfo==null||(System.currentTimeMillis()-tokenInfo.getCreate_time().getTime())/1000/60/60>5) {//token失效
			return 100;
		}
		User user=new User();
		user.setUser_id(tokenInfo.getUser_id());
		user.setUser_face(map.get("image"));
		user.setTrue_name(map.get("true_name"));
		user.setUser_phone(map.get("user_phone"));
		if(userService.updateProfile(user)==0){
			returnMap.put("image", map.get("image"));
			return 0;
		}else{
			if(map.get("image")!=null){
				File file = new File(Constants.UPLOADURL, map.get("image"));
				if(file.exists()&&file.isFile()){
					file.delete();
				}
			}
			return 1;
		}
	}

}
