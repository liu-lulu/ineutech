package com.ineutech.control.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gexin.fastjson.JSON;
import com.ineutech.entity.cake.CakeEmployee;
import com.ineutech.entity.cake.CakeLoginLog;
import com.ineutech.entity.cake.CakeShop;
import com.ineutech.entity.cake.CakeTransaction;
import com.ineutech.service.CakeService;
import com.ineutech.util.DateUtil;

@Controller
@RequestMapping("api")
public class ApiController {
	@Resource
	private CakeService cakeService;
	
	@ApiOperation(value = "获取所有店铺", notes = "")
	@ResponseBody
	@RequestMapping(value="shops", method = RequestMethod.GET,produces = "application/json;charset=utf-8")
	public String getShops(){
		List<CakeShop> shops=cakeService.shops();
		return JSON.toJSONString(shops);
	}
	
	@ApiOperation(value = "获取员工信息", notes = "根据工号获取员工信息")
	@ApiImplicitParam(paramType="query",name = "e_number",value = "员工工号 ",required=true)
	@ResponseBody
	@RequestMapping(value="employee", method = RequestMethod.GET,produces = "application/json;charset=utf-8")
	public String getEmployee(String e_number){
		CakeEmployee info=cakeService.login(new CakeEmployee(e_number));
		return JSON.toJSONString(info);
	}
	
	@ApiOperation(value = "记录登录日志", notes = "记录小程序登录日志")
	@ApiImplicitParams(
			{@ApiImplicitParam(paramType="query",name = "e_number",value = "员工工号 ",required=true),
			@ApiImplicitParam(paramType="query",name = "nickname",value = "微信名 ",required=true)})
	@ResponseBody
	@RequestMapping(value="saveLog", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public String saveLog(String e_number,String nickname){
		return JSON.toJSONString(cakeService.saveLoginLog(new CakeLoginLog(e_number, nickname)));
	}
	
	@ApiOperation(value = "记录蛋糕成交信息", notes = "传递参数e_number[工号],e_name[员工姓名],shop_code[门店编号],shop_name[门店名称],member_code[会员号],cake_num[蛋糕数量],cake_price[蛋糕价钱]")
	@ApiImplicitParam(name = "info",value = "交易信息 ",required=true,dataType = "CakeTransaction")
	@ResponseBody
	@RequestMapping(value="saveTransaction", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public String saveTransaction(@RequestBody CakeTransaction info){
		System.out.println("-------------"+info.getCake_price());
		return JSON.toJSONString(cakeService.saveTransaction(info));
	}
	
	@ApiOperation(value = "修改蛋糕成交信息", notes = "传递参数cake_id,cake_num[蛋糕数量],cake_price[蛋糕价钱]")
	@ApiImplicitParam(name = "info",value = "修改信息 ",required=true,dataType = "CakeTransaction")
	@ResponseBody
	@RequestMapping(value="updTransaction", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
	public String updTransaction(@RequestBody CakeTransaction info){
		return JSON.toJSONString(cakeService.updTransaction(info));
	}
	
	@ApiOperation(value = "删除蛋糕成交信息", notes = "")
	@ApiImplicitParam(paramType="query",name = "cake_id",value = "交易id ",required=true)
	@ResponseBody
	@RequestMapping(value="delTransaction", method = RequestMethod.DELETE,produces = "application/json;charset=utf-8")
	public String delTransaction(Integer cake_id){
		return JSON.toJSONString(cakeService.delTransaction(cake_id));
	}
	
	@ApiOperation(value = "获取蛋糕成交信息", notes = "")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name = "e_number",value = "工号 ",required=true),
		@ApiImplicitParam(paramType="query",name = "beginDate",value = "开始日期 ",required=false),
		@ApiImplicitParam(paramType="query",name = "endDate",value = "截止日期 ",required=false)})
	@ResponseBody
	@RequestMapping(value="transaction", method = RequestMethod.GET,produces = "application/json;charset=utf-8")
	public String transaction(String e_number,String beginDate,String endDate){
		Date curDate=new Date();
		Date begin=DateUtil.getDayBegin(Optional.ofNullable(beginDate).map(u ->DateUtil.getDate(u)).orElse(curDate));
		Date end=DateUtil.getDayEnd(Optional.ofNullable(endDate).map(u ->DateUtil.getDate(u)).orElse(curDate));
		return JSON.toJSONString(cakeService.transactions(null, e_number,begin, end, null,null,null));
	}

}
