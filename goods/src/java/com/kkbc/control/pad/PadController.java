package com.kkbc.control.pad;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.gexin.fastjson.JSON;
import com.kkbc.constants.WechatConsts;
import com.kkbc.service.GoodsDescService;
import com.kkbc.util.DateUtil;
import com.kkbc.util.QiniuUtil;
import com.kkbc.vo.GoodsDescVO;
import com.kkbc.vo.GoodsReqParamVO;
import com.sun.org.apache.xpath.internal.operations.And;

/**
 * 用户模块
 * 
 * @author liululu
 *
 */
@Controller
@RequestMapping("/interface")
public class PadController {
	
	private Logger logger = LoggerFactory.getLogger(PadController.class);


	@Resource
	private GoodsDescService goodsDescService;
	
	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex,HttpServletResponse response) throws IOException  {
		logger.error("请求出现异常:"+ex.getMessage());
		
		ex.printStackTrace();
		
		PrintWriter out =  response.getWriter();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", 2);
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}

	@RequestMapping("upload")
	public void filesUpload(MultipartFile[] files,MultipartFile[] smallfiles,HttpServletResponse response,HttpServletRequest request) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String[] shuxing=request.getParameterValues("shuxing");
		String content=request.getParameter("content");
		String baseString=request.getParameter("base");
		String img1_describe=request.getParameter("img1_describe");
		String img2_describe=request.getParameter("img2_describe");
		String img3_describe=request.getParameter("img3_describe");
		String img4_describe=request.getParameter("img4_describe");
		String goods_property=request.getParameter("goods_property");
		String goods_stateString=request.getParameter("goods_state");
		
		String brand=request.getParameter("brand");
		String category=request.getParameter("category");
		String other=request.getParameter("other");
		
		String url=request.getParameter("url");
		
		Integer base=StringUtils.isEmpty(baseString)?0:Integer.valueOf(baseString);
		Integer goods_state=StringUtils.isEmpty(goods_stateString)?null:Integer.valueOf(goods_stateString);
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		GoodsDescVO info=new GoodsDescVO(brand,category,other,content, new Date(),img1_describe,img2_describe,img3_describe,img4_describe,base,shuxing,goods_property,goods_state,url);
		
//		if (smallfiles != null && smallfiles.length > 0) {
//			
//			for (int i = 0; i < smallfiles.length; i++) {
//				MultipartFile file=smallfiles[i];
//				if (!file.isEmpty()) {
//					//获取上传图片的宽高
//					BufferedImage bi =ImageIO.read(file.getInputStream());
//					String fileName=file.getOriginalFilename();
//					// 文件名+UUID
//					String fileNameUUID = fileName.substring(0,fileName.lastIndexOf("."))+"_"+UUID.randomUUID().toString()+"_"+bi.getWidth()+"*"+bi.getHeight()+fileName.substring(fileName.lastIndexOf("."));
//					if (i==0) {
//						info.setSmall_img1(fileNameUUID);
//					}else if (i==1) {
//						info.setSmall_img2(fileNameUUID);
//					}else if (i==2) {
//						info.setSmall_img3(fileNameUUID);
//					}else if (i==3) {
//						info.setSmall_img4(fileNameUUID);
//					}
//					logger.info("小图文件名:"+fileNameUUID);
//					QiniuUtil.upload(file.getBytes(), fileNameUUID);
//				}
//			}
//		}
		
		if (files != null && files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				MultipartFile file=files[i];
				if (!file.isEmpty()) {
					String fileName=file.getOriginalFilename();
					// 文件名+UUID
					String fileNameUUID = fileName.substring(0,fileName.lastIndexOf("."))+"_"+UUID.randomUUID().toString();
					
					BufferedImage bi =ImageIO.read(file.getInputStream());
					byte[] imageByte=file.getBytes();
					if (file.getSize()>160*1024) {//压缩图片上传(超过160KB)
						bi=Thumbnails.of(file.getInputStream()).scale(QiniuUtil.getScale(file.getSize())).asBufferedImage();
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						Thumbnails.of(file.getInputStream()).scale(QiniuUtil.getScale(file.getSize())).toOutputStream(os);
						imageByte=os.toByteArray();
					}
					String uploadKey="460"+"*"+((int)((460f/bi.getWidth())*bi.getHeight()))+"_"+fileNameUUID+fileName.substring(fileName.lastIndexOf("."));
					logger.info("文件名:"+uploadKey);
					QiniuUtil.upload(imageByte, uploadKey);
					
			        if (i==0) {
						info.setImg1(uploadKey);
					}else if (i==1) {
						info.setImg2(uploadKey);
					}else if (i==2) {
						info.setImg3(uploadKey);
					}else if (i==3) {
						info.setImg4(uploadKey);
					}
					
				}
			}
		}
		
		jsonObject.put("state", goodsDescService.saveInfo(info));
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	//发布
	@RequestMapping("publish")
	public void publish(HttpServletResponse response,HttpServletRequest request) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String desc_id=request.getParameter("desc_id");
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("state", goodsDescService.publish(desc_id));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	

	@RequestMapping("list")
	public void list(HttpServletResponse response,HttpServletRequest request) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String menu=request.getParameter("menu");
		String pageNo=request.getParameter("pageNo");//查到other表后pageno从1开始
		String otherNumString=request.getParameter("otherNum");
		String openId = request.getParameter("openId");
		String goods_stateString = request.getParameter("goods_state");
		String goods_property = request.getParameter("goods_property");
		String brand=request.getParameter("brand");
		String category=request.getParameter("category");
		if (category!=null && category!="") {
			//byte[] bytes = category.getBytes("iso-8859-1");
			//category = new String(bytes,"utf-8");
			System.out.println("goods接收参数"+category);
		}
		if (goods_property!=null && goods_property!="") {
			//byte[] bytes = goods_property.getBytes("iso-8859-1");
			//goods_property = new String(bytes,"utf-8");
			System.out.println("goods接收参数"+goods_property);
		}
		if (brand!=null && brand!="") {
			//byte[] bytes = brand.getBytes("iso-8859-1");
			//brand = new String(bytes,"utf-8");
			System.out.println("goods接收参数"+brand);
		}
		String other=request.getParameter("other");
		
		Integer goods_state=StringUtils.isEmpty(goods_stateString)?null:Integer.valueOf(goods_stateString);
		int[] otherNum={Integer.valueOf(otherNumString)};
		
//		String openId="aa";
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		List<GoodsDescVO> list=goodsDescService.getList(menu,Integer.valueOf(pageNo),otherNum,openId,goods_state,goods_property,brand,category,other);
		
		
		jsonObject.put("otherNum", otherNum[0]);
//		jsonObject.put("openId", openId);
		jsonObject.put("list", list);
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("heart")
	public void heart(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String openId=request.getParameter("openId");
		String desc_id=request.getParameter("desc_id");
		String publishDate=request.getParameter("publishDate");
		String menu=request.getParameter("menu");
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("state", goodsDescService.heart(Integer.valueOf(desc_id), openId,DateUtil.stringToDate(publishDate),menu));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("noheart")
	public void noheart(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String openId=request.getParameter("openId");
		String desc_id=request.getParameter("desc_id");
		String publishDate=request.getParameter("publishDate");
		String menu=request.getParameter("menu");
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("state", goodsDescService.noheart(Integer.valueOf(desc_id), openId,DateUtil.stringToDate(publishDate),menu));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	
	@RequestMapping("nopublishlist")
	public void nopublishlist(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String pageNo=request.getParameter("pageNo");
		String typeStr=request.getParameter("type");
		String startTimeStr=request.getParameter("startTime");
		String endTimeStr=request.getParameter("endTime");
		String content=request.getParameter("content");
		
		Integer type=StringUtils.isEmpty(typeStr)?null:Integer.valueOf(typeStr);
		Date startTime=StringUtils.isEmpty(startTimeStr)?null:DateUtil.parseToDate(startTimeStr);
		Date endTime=StringUtils.isEmpty(endTimeStr)?null:DateUtil.getDayEnd(DateUtil.parseToDate(endTimeStr));
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("list", goodsDescService.nopublishList(Integer.valueOf(pageNo),startTime,endTime,type,content));
		jsonObject.put("pageNum", goodsDescService.getPageNum(GoodsDescVO.TABLE_NOPUBLISH,startTime,endTime,type,content));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("down")
	public void down(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String downInfo=request.getParameter("downInfo");
		String info = URLDecoder.decode(downInfo, "utf-8");
		JSONArray jsonarray = JSONArray.fromObject(info);
		List<GoodsReqParamVO> downList=(List<GoodsReqParamVO>) JSONArray.toCollection(jsonarray, GoodsReqParamVO.class);
		
//		String desc_id=request.getParameter("desc_id");
//		String publishDate=request.getParameter("publishDate");
//		String type=request.getParameter("menu");
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("state", goodsDescService.down(downList));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("weblist")
	public void weblist(final HttpServletResponse response,final HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String menu=request.getParameter("menu");
		String pageNo=request.getParameter("pageNo");
		String startTimeStr=request.getParameter("startTime");
		String endTimeStr=request.getParameter("endTime");
		String content=request.getParameter("content");
		String confirm=request.getParameter("confirm");
		
		Date startTime=StringUtils.isEmpty(startTimeStr)?null:DateUtil.parseToDate(startTimeStr);
		Date endTime=StringUtils.isEmpty(endTimeStr)?null:DateUtil.getDayEnd(DateUtil.parseToDate(endTimeStr));
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		List<GoodsDescVO> list=goodsDescService.webList(menu, Integer.valueOf(pageNo),startTime,endTime,content,confirm);
		jsonObject.put("pageNum", goodsDescService.webListPageNum(menu,startTime,endTime,content,confirm));
		
		
		jsonObject.put("list", list);
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	
	}
	
	@RequestMapping("changeOrder")
	public void changeOrder(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String menu=request.getParameter("menu");
		String desc_id=request.getParameter("desc_id");
		String publishDate=request.getParameter("publishDate");
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("state", goodsDescService.changeOrder(menu, Integer.valueOf(desc_id), DateUtil.stringToDate(publishDate)));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("heartUser")
	public void heartUser(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String menu=request.getParameter("menu");
		String desc_id=request.getParameter("desc_id");
		String publishDate=request.getParameter("publishDate");
		String pageNo=request.getParameter("pageNo");
		String stateString=request.getParameter("state");
		
		Integer state=StringUtils.isEmpty(stateString)?null:Integer.valueOf(stateString);
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("list", goodsDescService.heartUser(menu, Integer.valueOf(desc_id), DateUtil.stringToDate(publishDate), Integer.valueOf(pageNo),state));
		jsonObject.put("pageNum", goodsDescService.userCount(menu, Integer.valueOf(desc_id), DateUtil.stringToDate(publishDate),state));
		jsonObject.put("publishDate", publishDate);
		jsonObject.put("menu", menu);
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("getproperty")
	public void getproperty(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String menu=request.getParameter("menu");
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("goods_property", goodsDescService.getProperty(menu));
		jsonObject.put("brand", goodsDescService.getBrand(menu));
		jsonObject.put("category", goodsDescService.getCategory(menu));
		jsonObject.put("other", goodsDescService.getOther(menu));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}

	@RequestMapping("miaoshaend")
	public void miaoshaend(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String desc_id=request.getParameter("desc_id");
		String publishDate=request.getParameter("publishDate");
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("state", goodsDescService.miaoshaEnd(Integer.valueOf(desc_id), DateUtil.stringToDate(publishDate)));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("userheartlist")
	public void userheartlist(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String openId=request.getParameter("openId");
		String pageNo=request.getParameter("pageNo");
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("list", goodsDescService.getUserAttentionList(openId, Integer.valueOf(pageNo)));
		jsonObject.put("pageNum", goodsDescService.userAttentionListPageNum(openId));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("confirmUser")
	public void confirmUser(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String menu=request.getParameter("menu");
		String desc_id=request.getParameter("desc_id");
		String openId=request.getParameter("openId");
		String publishDate=request.getParameter("publishDate");
		String stateString=request.getParameter("state");
		
		Integer state=StringUtils.isEmpty(stateString)?null:Integer.valueOf(stateString);
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("state", goodsDescService.confirmUser(menu, Integer.valueOf(desc_id),openId, DateUtil.stringToDate(publishDate), state));
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}

	@RequestMapping("base")
	public void base(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String desc_id=request.getParameter("desc_id");
		String publishDate=request.getParameter("publishDate");
		String menu=request.getParameter("menu");
		String baseString=request.getParameter("base");
		
		Integer base=StringUtils.isEmpty(baseString)?0:Integer.valueOf(baseString);
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("state", goodsDescService.updBase(menu, Integer.valueOf(desc_id), DateUtil.stringToDate(publishDate), base));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("attentionList")
	public void attentionList(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String menu=request.getParameter("menu");
		String pageNo=request.getParameter("pageNo");
		String confirm=request.getParameter("confirm");
		String wx_name=request.getParameter("wx_name");
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("list", goodsDescService.attentionList(menu, Integer.valueOf(pageNo), wx_name, confirm));
		jsonObject.put("pageNum", goodsDescService.attentionListPageNum(menu,wx_name,confirm));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("del")
	public void del(HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String desc_id=request.getParameter("desc_id");
		String menu=request.getParameter("menu");
		String state=request.getParameter("state");
		
		
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("state", goodsDescService.delGoodsDesc(menu, Integer.valueOf(desc_id), Integer.valueOf(state)));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	
	@RequestMapping("edit")
	public void edit(MultipartFile[] files,HttpServletResponse response,HttpServletRequest request) throws IOException, NumberFormatException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String desc_idStr=request.getParameter("desc_id");
//		String menu=request.getParameter("menu");
//		String state=request.getParameter("state");
		
		String typeString=request.getParameter("shuxing");
		String content=request.getParameter("content");
		String baseString=request.getParameter("base");
		String img1_describe=request.getParameter("img1_describe");
		String img2_describe=request.getParameter("img2_describe");
		String img3_describe=request.getParameter("img3_describe");
		String img4_describe=request.getParameter("img4_describe");
		String goods_property=request.getParameter("goods_property");
		String goods_stateString=request.getParameter("goods_state");
		
		String brand=request.getParameter("brand");
		String category=request.getParameter("category");
		String other=request.getParameter("other");
		
		String url=request.getParameter("url");
		
		Integer desc_id=StringUtils.isEmpty(desc_idStr)?0:Integer.valueOf(desc_idStr);
		Integer base=StringUtils.isEmpty(baseString)?0:Integer.valueOf(baseString);
		Integer goods_state=StringUtils.isEmpty(goods_stateString)?null:Integer.valueOf(goods_stateString);
		Integer type=StringUtils.isEmpty(typeString)?null:Integer.valueOf(typeString);
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		
		GoodsDescVO info=new GoodsDescVO(desc_id,brand,category,other,content, new Date(),img1_describe,img2_describe,img3_describe,img4_describe,base,type,goods_property,goods_state,url);
		
		
//		if (files != null && files.length > 0) {
//			for (int i = 0; i < files.length; i++) {
//				MultipartFile file=files[i];
//				if (!file.isEmpty()) {
//					String fileName=file.getOriginalFilename();
//					// 文件名+UUID
//					String fileNameUUID = fileName.substring(0,fileName.lastIndexOf("."))+"_"+UUID.randomUUID().toString();
//					
//					BufferedImage bi =ImageIO.read(file.getInputStream());
//					byte[] imageByte=file.getBytes();
//					if (file.getSize()>160*1024) {//压缩图片上传(超过160KB)
//						bi=Thumbnails.of(file.getInputStream()).scale(getScale(file.getSize())).asBufferedImage();
//						ByteArrayOutputStream os = new ByteArrayOutputStream();
//						Thumbnails.of(file.getInputStream()).scale(getScale(file.getSize())).toOutputStream(os);
//						imageByte=os.toByteArray();
//					}
//					String uploadKey="460"+"*"+((int)((460f/bi.getWidth())*bi.getHeight()))+"_"+fileNameUUID+fileName.substring(fileName.lastIndexOf("."));
//					logger.info("文件名:"+uploadKey);
//					QiniuUtil.upload(imageByte, uploadKey);
//					
//			        if (i==0) {
//						info.setImg1(uploadKey);
//					}else if (i==1) {
//						info.setImg2(uploadKey);
//					}else if (i==2) {
//						info.setImg3(uploadKey);
//					}else if (i==3) {
//						info.setImg4(uploadKey);
//					}
//					
//				}
//			}
//		}
		
		jsonObject.put("state", goodsDescService.edit(info, files));
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}

	private String getOpenId(String code){
        String appid = WechatConsts.APP_ID;
        String secret = WechatConsts.APP_SECRET;
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
        Response response = null;
		String resp = null;
		try {
			Request request = new Request.Builder().url(requestUrl).build();
			response = new OkHttpClient().newCall(request).execute();
			if (!response.isSuccessful())
				throw new IOException("Unexpected code " + response);
			resp = response.body().string();
		} catch (IOException e) {
			e.printStackTrace();
		}

		com.gexin.fastjson.JSONObject json = JSON.parseObject(resp);
		String str = json.getString("openid");
		return str;
	}
	
//	public float getScale(long fileSize){
//		
//		float size=fileSize/(1024*1024);
//		if (size==0) {
//			return 0.5f;
//		}else {
//			return 0.5f/size;
//		}
//		
//	}
	

}
