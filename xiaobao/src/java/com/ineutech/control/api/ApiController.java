package com.ineutech.control.api;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gexin.fastjson.JSONObject;
import com.gexin.fastjson.serializer.SerializerFeature;
import com.hankcs.hanlp.HanLP;
import com.ineutech.cons.Constans;
import com.ineutech.entity.Client;
import com.ineutech.entity.ClientModel;
import com.ineutech.entity.Employee;
import com.ineutech.entity.Keywords;
import com.ineutech.entity.VisitModel;
import com.ineutech.entity.VisitRecord;
import com.ineutech.entity.VisitVoice;
import com.ineutech.service.UserService;
import com.ineutech.util.BaiduUtil;
import com.ineutech.util.DateUtil;
import com.ineutech.util.IflytekUtil;
import com.ineutech.util.TencentNLPUtil;
import com.ineutech.util.WeblfasrUtil;
import com.ineutech.vo.ResponseMsg;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
//@Api(value ="app 接口", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("api")
public class ApiController {

	@Resource
	private UserService userService;
	
	@ApiOperation(value = "提取关键字", notes = "根据提交内容提取关键字")
    @ApiImplicitParam(paramType="body",name = "content",value = "提交内容",required=true)
	@ResponseBody
	@RequestMapping(value="keywords", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String keywords(@RequestBody String content) throws UnsupportedEncodingException{
		System.out.println(content);
		JSONObject ret=new JSONObject();
		ret.put("HanLP", HanLP.extractKeyword(content, 10));
		ret.put("Tencent", TencentNLPUtil.getKeyWords(content));
        return ret.toJSONString();
    }
	
	
	@ApiOperation(value = "提取摘要", notes = "根据提交内容提取摘要")
    @ApiImplicitParam(paramType="body",name = "content",value = "提交内容",required=true)
	@ResponseBody
	@RequestMapping(value="summary", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String summary(@RequestBody String content) throws UnsupportedEncodingException{
		System.out.println(content);
		JSONObject ret=new JSONObject();
		ret.put("HanLP", HanLP.extractSummary(content, 1));
		ret.put("Tencent", TencentNLPUtil.getSummary(content));
		 return ret.toJSONString();
    }
	
	@ApiOperation(value = "通用文字识别（含位置信息版）", notes = "识别图片上的文字")
	@ResponseBody
	@RequestMapping(value="general", consumes="multipart/*",headers="content-type=multipart/form-data",method = RequestMethod.POST,produces = "application/json;charset=utf-8")//
    public String general(@ApiParam(value = "上传图片",required=true) MultipartFile imgFile) throws IOException{
		return BaiduUtil.general(imgFile.getBytes());
    }
	
	@ApiOperation(value = "通用文字识别（高精度含位置版）", notes = "识别图片上的文字")
	@ResponseBody
	@RequestMapping(value="accurate", consumes="multipart/*",headers="content-type=multipart/form-data",method = RequestMethod.POST,produces = "application/json;charset=utf-8")//
    public String accurate(@ApiParam(value = "上传图片",required=true) MultipartFile imgFile) throws IOException{
		return BaiduUtil.accurate(imgFile.getBytes());
    }
	
	@ApiOperation(value = "实时语音转写", notes = "实时语音转写")
	@ResponseBody
	@RequestMapping(value="rtasr", consumes="multipart/*",headers="content-type=multipart/form-data",method = RequestMethod.POST,produces = "application/json;charset=utf-8")//
    public String rtasr(@ApiParam(value = "上传语音文件",required=true) MultipartFile speechFile) throws Exception{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("result", IflytekUtil.rtasr(speechFile.getInputStream()));
		return jsonObject.toString();
    }
	

	
	@ApiOperation(value = "登录", notes = "用户登录")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query",name = "loginName",value = "登录名",required=true),
	@ApiImplicitParam(paramType="query",name = "loginPwd",value = "登录密码",required=true)
	})
	@ResponseBody
	@RequestMapping(value="login", method = RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String login(@RequestParam String loginName,@RequestParam String loginPwd){
		Employee employee=userService.getLoginEmployee(loginName, loginPwd);
		if (employee==null||employee.getRole()!=1) {
			return JSONObject.toJSONString(ResponseMsg.fail("用户名或密码错误"));
		}
		return JSONObject.toJSONString(ResponseMsg.success(employee),SerializerFeature.WriteMapNullValue);
	}
	
	@ApiOperation(value = "新增客户", notes = "添加新客户")
	@ApiImplicitParam(name = "clientInfo",value = "客户信息 ",required=true,dataType = "ClientModel")
	@ResponseBody
	@RequestMapping(value="client",method=RequestMethod.POST,produces = "application/json;charset=utf-8")
	public String addClient(@RequestBody ClientModel clientInfo){
		int ret=userService.saveClient(clientInfo);
		if (ret==1) {
			return JSONObject.toJSONString(ResponseMsg.success(),SerializerFeature.WriteMapNullValue);
		}else {
			return JSONObject.toJSONString(ResponseMsg.fail("请联系技术人员"),SerializerFeature.WriteMapNullValue);
		}
	}
	
	@ApiOperation(value = "修改客户", notes = "修改客户信息")
	@ApiImplicitParam(name = "clientInfo",value = "客户信息 ",required=true,dataType = "Client")
	@ResponseBody
	@RequestMapping(value="client",method=RequestMethod.PUT,produces = "application/json;charset=utf-8")
	public String updClient(@RequestBody Client clientInfo){
		int ret=userService.updClient(clientInfo);
		if (ret==1) {
			return JSONObject.toJSONString(ResponseMsg.success(),SerializerFeature.WriteMapNullValue);
		}else {
			return JSONObject.toJSONString(ResponseMsg.fail("请联系技术人员"),SerializerFeature.WriteMapNullValue);
		}
	}
	@ApiOperation(value = "删除客户", notes = "删除客户信息")
	@ApiImplicitParam(paramType="query",name = "client_id",value = "客户id ",required=true,dataType = "int")
	@ResponseBody
	@RequestMapping(value="client",method=RequestMethod.DELETE,produces = "application/json;charset=utf-8")
	public String delClient(Integer client_id){
		int ret=userService.delClient(client_id);
		if (ret==1) {
			return JSONObject.toJSONString(ResponseMsg.success(),SerializerFeature.WriteMapNullValue);
		}else {
			return JSONObject.toJSONString(ResponseMsg.fail("请联系技术人员"),SerializerFeature.WriteMapNullValue);
		}
	}
	
	@ApiOperation(value = "客户列表", notes = "获取销售人员下的客户")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query",name = "employeeId",dataType="int",value = "员工id",required=true),
	@ApiImplicitParam(paramType="query",name = "clientId",dataType="int",value = "客户id",required=false),
	@ApiImplicitParam(paramType="query",name = "clientName",value = "客户名",required=false),
	@ApiImplicitParam(paramType="query",name = "pageNo",dataType="int",value = "页码",required=false)
	})
	@ResponseBody
	@RequestMapping(value="client",method=RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String client(@RequestParam Integer employeeId,@RequestParam(required=false) Integer clientId,@RequestParam(required=false) String clientName,@RequestParam(required=false) Integer pageNo){
		JSONObject ret=new JSONObject();
		List<VisitRecord> records=userService.visitRecords(null,employeeId, clientId, null,null,null);
		List<Client> clients=userService.clients(null,employeeId, clientId, clientName, pageNo);
		ret.put("clients", clients);
		ret.put("totalNum", userService.visitRecordsCount(null,employeeId, clientId,null,null));
		ret.put("curMonthNum", userService.visitRecordsCount(null,employeeId, clientId,DateUtil.getDayBegin(DateUtil.getFirstDayCurMonth()), DateUtil.getDayEnd(new Date())));
		ret.put("latestRecords", records!=null&&records.size()>0?records.get(0):null);
		return JSONObject.toJSONString(ResponseMsg.success(ret),SerializerFeature.WriteMapNullValue);
	}
	
	@ApiOperation(value = "客户拜访记录", notes = "客户拜访记录")
	@ApiImplicitParams({
	@ApiImplicitParam(paramType="query",name = "employeeId",dataType="int",value = "员工id",required=false),
	@ApiImplicitParam(paramType="query",name = "clientId",dataType="int",value = "客户id",required=false),
	@ApiImplicitParam(paramType="query",name = "pageNo",dataType="int",value = "页码",required=false)
	})
	@ResponseBody
	@RequestMapping(value="visit",method=RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String visit(@RequestParam(required=false) Integer employeeId,@RequestParam(required=false) Integer clientId,@RequestParam(required=false) Integer pageNo){
		JSONObject ret=new JSONObject();
		List<VisitRecord> records=userService.visitRecords(null,employeeId, clientId, pageNo,null,null);
		ret.put("records", records);
		ret.put("totalNum", userService.visitRecordsCount(null,employeeId, clientId,null,null));
		ret.put("curMonthNum", userService.visitRecordsCount(null,employeeId, clientId,DateUtil.getDayBegin(DateUtil.getFirstDayCurMonth()), DateUtil.getDayEnd(new Date())));
		if (clientId!=null) {
			List<Client> clients=userService.clients(null, null, clientId, null, null);
			if (clients!=null&&clients.size()==1) {
				ret.put("client", clients.get(0));
			}
		}
		return JSONObject.toJSONString(ResponseMsg.success(ret),SerializerFeature.WriteMapNullValue);
	}
	
	@ApiOperation(value = "拜访客户", notes = "新增拜访记录")
	@ApiImplicitParam(name = "visitInfo",value = "拜访信息 ",required=true,dataType = "VisitModel")
	@ResponseBody
	@RequestMapping(value="visit",method=RequestMethod.POST,produces = "application/json;charset=utf-8")
	public String visitClient(@RequestBody VisitModel visitInfo){
		int ret=userService.saveRecord(visitInfo);
		if (ret!=0) {
			return JSONObject.toJSONString(ResponseMsg.success(ret),SerializerFeature.WriteMapNullValue);
		}else {
			return JSONObject.toJSONString(ResponseMsg.fail("请联系技术人员"),SerializerFeature.WriteMapNullValue);
		}
	}
	
	@ApiOperation(value = "追加微信聊天记录", notes = "追加微信聊天记录")
	@ResponseBody
	@RequestMapping(value="wechat",method=RequestMethod.POST,produces = "application/json;charset=utf-8")
	public String appendWechat(@RequestParam(value = "visit_id",required=true)Integer visit_id,@RequestParam(value = "wechatContent",required=true)String wechatContent){
		int ret=userService.appendWechat(visit_id, wechatContent);
		if (ret!=0) {
			return JSONObject.toJSONString(ResponseMsg.success(),SerializerFeature.WriteMapNullValue);
		}else {
			return JSONObject.toJSONString(ResponseMsg.fail("请联系技术人员"),SerializerFeature.WriteMapNullValue);
		}
	}
	
	@ApiOperation(value = "上传图片", notes = "上传此次拜访的客户的合照")
	@ResponseBody
	@RequestMapping(value="uploadImg", consumes="multipart/*",headers="content-type=multipart/form-data",method = RequestMethod.POST,produces = "application/json;charset=utf-8")//
    public String uploadImg(@RequestParam(value = "visit_id",required=true)Integer visit_id, @ApiParam(value = "上传图片",required=true) MultipartFile imgFile) throws IOException{
		String fileName=visit_id+"_"+imgFile.getOriginalFilename();
		imgFile.transferTo(new File(Constans.IMG_PATH+fileName));
		userService.updateImg(new VisitRecord(visit_id, fileName));
		return JSONObject.toJSONString(ResponseMsg.success(),SerializerFeature.WriteMapNullValue);
		
    }
	
	@ApiOperation(value = "上传语音文件", notes = "上传语音文件")
	@ResponseBody
	@RequestMapping(value="uploadVoice", consumes="multipart/*",headers="content-type=multipart/form-data",method = RequestMethod.POST,produces = "text/html;charset=utf-8")//
    public String lfa(@RequestParam(value = "visit_id",required=true) Integer visit_id,@ApiParam(value = "上传语音文件",required=true) MultipartFile voiceFile,@RequestParam(value = "voiceComment",required=false)String voiceComment) throws Exception{
		String taskId=WeblfasrUtil.upload(voiceFile);
		if (StringUtils.isEmpty(taskId)) {
			return JSONObject.toJSONString(ResponseMsg.fail("上传失败"),SerializerFeature.WriteMapNullValue);
		}
		String fileName=visit_id+"_"+taskId+"_"+voiceFile.getOriginalFilename();
		voiceFile.transferTo(new File(Constans.VOICE_PATH+fileName));
		userService.saveVoice(new VisitVoice(visit_id,fileName,taskId,voiceComment));
		return JSONObject.toJSONString(ResponseMsg.success(),SerializerFeature.WriteMapNullValue);
    }
	
	@ApiOperation(value = "获取微信聊天记录", notes = "获取微信聊天记录")
	@ApiImplicitParam(paramType="query",name = "visit_id",dataType="int",value = "拜访记录id",required=true)
	@ResponseBody
	@RequestMapping(value="wechat",method=RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String getWechat(Integer visit_id){
		
		return JSONObject.toJSONString(ResponseMsg.success(userService.getWechatContent(visit_id)),SerializerFeature.WriteMapNullValue);
	}
	
	@ApiOperation(value = "获取语音内容", notes = "获取语音文件内容")
	@ApiImplicitParam(paramType="query",name = "voice_id",dataType="int",value = "语音文件id",required=true)
	@ResponseBody
	@RequestMapping(value="voiceContent",method=RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String getVoiceContent(Integer voice_id){
		
		return JSONObject.toJSONString(ResponseMsg.success(userService.getVoiceContent(voice_id)),SerializerFeature.WriteMapNullValue);
	}
	
	@ApiOperation(value = "所有关建字", notes = "所有关键字")
	@ResponseBody
	@RequestMapping(value="keyword",method=RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String keywords(){
		List<Keywords> keywords=userService.keywords();
		return JSONObject.toJSONString(ResponseMsg.success(keywords),SerializerFeature.WriteMapNullValue);
		
	}
	
	@ApiOperation(value = "删除关建字", notes = "删除关键字")
	@ApiImplicitParam(paramType="query",name = "keyword_id",dataType="int",value = "关键字id",required=true)
	@ResponseBody
	@RequestMapping(value="keyword",method=RequestMethod.DELETE,produces = "text/html;charset=utf-8")
	public String delKeyword(Integer keyword_id){
		int ret=userService.delKeyword(keyword_id);
		if (ret!=0) {
			return JSONObject.toJSONString(ResponseMsg.success(),SerializerFeature.WriteMapNullValue);
		}else {
			return JSONObject.toJSONString(ResponseMsg.fail("请联系技术人员"),SerializerFeature.WriteMapNullValue);
		}
	}
	
	@ApiOperation(value = "新增关建字", notes = "新增关键字")
	@ApiImplicitParam(paramType="query",name = "keyword",dataType="String",value = "关键字",required=true)
	@ResponseBody
	@RequestMapping(value="keyword",method=RequestMethod.POST,produces = "text/html;charset=utf-8")
	public String addKeyword(String keyword){
		int ret=userService.addKeyword(new Keywords(keyword));
		if (ret!=0) {
			return JSONObject.toJSONString(ResponseMsg.success(ret),SerializerFeature.WriteMapNullValue);
		}else {
			return JSONObject.toJSONString(ResponseMsg.fail("请联系技术人员"),SerializerFeature.WriteMapNullValue);
		}
	}
	
	@ApiOperation(value = "语音文件备注", notes = "语音文件备注")
	@ResponseBody
	@RequestMapping(value="voiceComment",method=RequestMethod.POST,produces = "application/json;charset=utf-8")
	public String voiceComment(@RequestParam(value = "voice_id",required=true)Integer voice_id,@RequestParam(value = "voiceComment",required=true)String voiceComment){
		int ret=userService.voiceComment(new VisitVoice(voice_id, voiceComment));
		if (ret!=0) {
			return JSONObject.toJSONString(ResponseMsg.success(),SerializerFeature.WriteMapNullValue);
		}else {
			return JSONObject.toJSONString(ResponseMsg.fail("请联系技术人员"),SerializerFeature.WriteMapNullValue);
		}
	}
	
	@ApiOperation(value = "获取拜访记录的录音文件", notes = "获取拜访记录的所有录音文件")
	@ApiImplicitParam(paramType="query",name = "visit_id",dataType="int",value = "拜访记录id",required=true)
	@ResponseBody
	@RequestMapping(value="voices",method=RequestMethod.GET,produces = "text/html;charset=utf-8")
	public String getVoices(Integer visit_id){
		
		return JSONObject.toJSONString(ResponseMsg.success(userService.getVoiceByVisitId(visit_id)),SerializerFeature.WriteMapNullValue);
	}


}
