package com.kkbc.control.finance;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kkbc.cons.Constans;
import com.kkbc.control.BaseController;
import com.kkbc.entity.ConvertHistory;
import com.kkbc.entity.PrizeHistory;
import com.kkbc.entity.TransferHistory;
import com.kkbc.entity.User;
import com.kkbc.service.UserService;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.Passport;

/**
 * 财务管理
 * @author liululu
 *
 */
@Controller
@RequestMapping("/finance")
public class FinanceController extends BaseController{
	@Resource
	private UserService userService;
	
	//我的账户
	@RequestMapping("myAccount")
	private ModelAndView myinfo(HttpServletRequest request, HttpServletResponse response){
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		
		User user=userService.valiLoginName(passport.getLoginName());
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("user", user);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("finance/myAccount");
		return mv;
	}
	
	//奖金记录
	@RequestMapping("prizeRecord")
	private ModelAndView prizeRecord(HttpServletRequest request, HttpServletResponse response){
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		
		String currentPageNOs=request.getParameter("currentPageNO");
		
		int currentPageNO = 1; // 当前页
		if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
			currentPageNO=Integer.valueOf(currentPageNOs);
		}
		
		ListInfo<PrizeHistory> historyInfo=userService.prizeHistories(passport.getLoginName(), currentPageNO, Constans.PAGESIZE);
		
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("currentPageNO", currentPageNO);
		paramaterMap.put("pageSize",  Constans.PAGESIZE);
		paramaterMap.put("listInfo", historyInfo);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("finance/prizeRecord");
		return mv;
	}
	//去会员转账
	@RequestMapping("goTransfer")
	private ModelAndView goTransfer(HttpServletRequest request, HttpServletResponse response){
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		
		User user=userService.valiLoginName(passport.getLoginName());
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("user", user);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("finance/transfer");
		return mv;
	}
	//会员转账
	@RequestMapping("transfer")
	private void transfer(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Passport user=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		
		String to_user_name=request.getParameter("receiveName");
		String coin_type="1";
		if ("购物钻".equals(request.getParameter("coin_type"))) {
			coin_type="2";
		}
		float money=Float.valueOf(request.getParameter("money"));
		
		int result=userService.transfer(user.getLoginName(), to_user_name, coin_type, money);
		PrintWriter out = response.getWriter();
		JSONObject jsonObject=new JSONObject();
		if (result==1) {
			jsonObject.put("state", "true");
		} else {
			jsonObject.put("state", "false");
		}
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	//转账记录
	@RequestMapping("transferRecord")
	private ModelAndView transferRecord(HttpServletRequest request, HttpServletResponse response){
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		
		String currentPageNOs=request.getParameter("currentPageNO");
		
		int currentPageNO = 1; // 当前页
		if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
			currentPageNO=Integer.valueOf(currentPageNOs);
		}
		
		ListInfo<TransferHistory> historyInfo=userService.transferHistories(passport.getLoginName(), currentPageNO, Constans.PAGESIZE);
		
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("currentPageNO", currentPageNO);
		paramaterMap.put("pageSize",  Constans.PAGESIZE);
		paramaterMap.put("listInfo", historyInfo);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("finance/transferRecord");
		return mv;
	}
	
	//去币种转换jsp
	@RequestMapping("goCoinConvert")
	private ModelAndView goCoinConvert(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		
		User user=userService.valiLoginName(passport.getLoginName());
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("user", user);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("finance/coinConvert");
		return mv;
		
	}
		
	//币种转换
	@RequestMapping("coinConvert")
	private void coinConvert(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");// request.getRequestURL()
		response.setCharacterEncoding("utf-8");
		int result=userService.coinConvert(passport.getLoginName(), Float.valueOf(request.getParameter("convertMoney")));
		PrintWriter out = response.getWriter();
		JSONObject jsonObject=new JSONObject();
		if (result==1) {
			jsonObject.put("state", "true");
		} else {
			jsonObject.put("state", "false");
		}
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//转换记录
	@RequestMapping("convertRecord")
	private ModelAndView convertRecord(HttpServletRequest request, HttpServletResponse response){
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		
		String currentPageNOs=request.getParameter("currentPageNO");
		
		int currentPageNO = 1; // 当前页
		if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
			currentPageNO=Integer.valueOf(currentPageNOs);
		}
		
		ListInfo<ConvertHistory> historyInfo=userService.convertHistories(passport.getLoginName(), currentPageNO, Constans.PAGESIZE);
		
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("currentPageNO", currentPageNO);
		paramaterMap.put("pageSize",  Constans.PAGESIZE);
		paramaterMap.put("listInfo", historyInfo);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("finance/convertRecord");
		return mv;
	}
	

}
