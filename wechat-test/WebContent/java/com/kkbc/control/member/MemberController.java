package com.kkbc.control.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kkbc.cons.Constans;
import com.kkbc.control.BaseController;
import com.kkbc.entity.User;
import com.kkbc.service.UserService;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.DiagramVo;
import com.kkbc.vo.Passport;

/**
 * 会员管理
 * @author liululu
 *
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController{
	
	@Resource
	private UserService userService;
	
	//去会员注册
	@RequestMapping("goRegister")
	private ModelAndView goRegister(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("referral", passport.getLoginName());
		paramaterMap.put("contactPerson", request.getParameter("contactPerson"));
		paramaterMap.put("area", request.getParameter("area"));
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("member/register");
		return mv;
	}
	//会员注册
	@RequestMapping("register")
	private void register(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		
		User user=new User();
		user.setUser_name(request.getParameter("loginName"));
		user.setUser_password(request.getParameter("password"));
		user.setReferral(request.getParameter("referral"));
		user.setContactPerson(request.getParameter("contactPerson"));
		user.setPwd2(request.getParameter("pwd2"));
		user.setTrue_name(request.getParameter("trueName"));
		user.setUser_phone(request.getParameter("phone"));
		user.setIDCardNo(request.getParameter("IDCardNo"));
		user.setArea(request.getParameter("area"));
		user.setCreate_time(new Date());
		user.setUser_company_id(passport.getCompanyId());
		
		int result=userService.registerUser(user);
		
		PrintWriter out = response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
		if (result>0) {
			jsonObject.put("state", "true");
		} else {
			jsonObject.put("state", "false");
		}
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//激活会员(待激活会员)
	@RequestMapping("activate")
	private ModelAndView activate(HttpServletRequest request, HttpServletResponse response){
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		String currentPageNOs=request.getParameter("currentPageNO");
		
		int currentPageNO = 1; // 当前页
		if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
			currentPageNO=Integer.valueOf(currentPageNOs);
		}
		ListInfo<User> users=userService.getUserByStatus(passport.getLoginName(), "0",currentPageNO,Constans.PAGESIZE);
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("currentPageNO", currentPageNO);
		paramaterMap.put("pageSize",  Constans.PAGESIZE);
		paramaterMap.put("listInfo", users);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("member/activate");
		return mv;
	}
	
	//激活列表
	@RequestMapping("activatelist")
	private ModelAndView activatelist(HttpServletRequest request, HttpServletResponse response){
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		String currentPageNOs=request.getParameter("currentPageNO");
		
		int currentPageNO = 1; // 当前页
		if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
			currentPageNO=Integer.valueOf(currentPageNOs);
		}
		ListInfo<User> users=userService.getUserByStatus(passport.getLoginName(), "1",currentPageNO,Constans.PAGESIZE);
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("currentPageNO", currentPageNO);
		paramaterMap.put("pageSize",  Constans.PAGESIZE);
		paramaterMap.put("listInfo", users);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("member/activatelist");
		return mv;
	}
	
	//我的推荐
	@RequestMapping("myRecommendation")
	private ModelAndView myRecommendation(HttpServletRequest request, HttpServletResponse response){
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		String currentPageNOs=request.getParameter("currentPageNO");
		
		int currentPageNO = 1; // 当前页
		if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
			currentPageNO=Integer.valueOf(currentPageNOs);
		}
		ListInfo<User> users=userService.getUserByStatus(passport.getLoginName(), null,currentPageNO,Constans.PAGESIZE);
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("currentPageNO", currentPageNO);
		paramaterMap.put("pageSize",  Constans.PAGESIZE);
		paramaterMap.put("listInfo", users);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("member/myRecommendation");
		return mv;
	}
	
	//关系图
	@RequestMapping("diagram")
	private ModelAndView diagram(HttpServletRequest request, HttpServletResponse response){
		Passport passport=(Passport) request.getSession().getAttribute(Passport.PASSPORTNAME);
		
		String user_name=passport.getLoginName();
		
		//父节点
		DiagramVo parent=new DiagramVo();
		parent.setUser_name(passport.getLoginName());
		parent.setCreate_time(passport.getCreate_time());
		parent.setLevel(passport.getLevel());
		
		if (StringUtils.isNotEmpty(request.getParameter("user_name"))) {
			user_name=request.getParameter("user_name");
			User user=userService.valiLoginName(user_name);
			if (user!=null) {
				parent.setUser_name(user.getUser_name());
				parent.setCreate_time(user.getCreate_time());
				parent.setLevel(user.getLevel());
			}
		}
		
		//A区节点
		List<User> aUser=userService.getUserByUserNameAndArea(user_name, "A");
		DiagramVo aVo=null;
		if (aUser!=null&&aUser.size()>0) {
			User userA=aUser.get(0);
			aVo=new DiagramVo();
			aVo.setCreate_time(userA.getCreate_time());
			aVo.setLevel(userA.getLevel());
			aVo.setUser_name(userA.getUser_name());
			int Acount=0;//记录A区节点下的A区节点总数
			int Bcount=0;//记录A区节点下的B区节点总数
			//A区节点 下的A节点
			List<User> aaUser=userService.getUserByUserNameAndArea(userA.getUser_name(), "A");
			//A区节点 下的B节点
			List<User> abUser=userService.getUserByUserNameAndArea(userA.getUser_name(), "B");
			
			if (aaUser!=null&&aaUser.size()>0) {
				Acount=+1;
				List<User> aaAllUser=userService.getAllSon(aaUser.get(0).getUser_name());//A区节点 下的A节点 下的所有子节点
				if (aaAllUser!=null) {
					Acount+=aaAllUser.size();
				}
			}
			
			if (abUser!=null&&abUser.size()>0) {
				Bcount=+1;
				List<User> abAllUser=userService.getAllSon(abUser.get(0).getUser_name());//A区节点 下的B节点 下的所有子节点
				if (abAllUser!=null) {
					Bcount+=abAllUser.size();
				}
			}
			
			aVo.setSumA(Acount*Constans.DIAGRAM);
			aVo.setSumB(Bcount*Constans.DIAGRAM);
			if (Acount>=Bcount) {
				aVo.setJieyuA(aVo.getSumA()-aVo.getSumB());
				aVo.setJieyuB(0);
			}else {
				aVo.setJieyuA(0);
				aVo.setJieyuB(aVo.getSumB()-aVo.getSumA());
			}
		}
		
		//B区节点
		List<User> bUser=userService.getUserByUserNameAndArea(user_name, "B");
		DiagramVo bVo=null;
		if (bUser!=null&&bUser.size()>0) {
			User userB=bUser.get(0);
			bVo=new DiagramVo();
			bVo.setCreate_time(userB.getCreate_time());
			bVo.setLevel(userB.getLevel());
			bVo.setUser_name(userB.getUser_name());
			int Acount=0;//记录A区节点下的A区节点总数
			int Bcount=0;//记录A区节点下的B区节点总数
			//B区节点 下的A节点
			List<User> baUser=userService.getUserByUserNameAndArea(userB.getUser_name(), "A");
			//B区节点 下的B节点
			List<User> bbUser=userService.getUserByUserNameAndArea(userB.getUser_name(), "B");
			
			if (baUser!=null&&baUser.size()>0) {
				Acount=+1;
				List<User> baAllUser=userService.getAllSon(baUser.get(0).getUser_name());//A区节点 下的A节点 下的所有子节点
				if (baAllUser!=null) {
					Acount+=baAllUser.size();
				}
			}
			
			if (bbUser!=null&&bbUser.size()>0) {
				Bcount=+1;
				List<User> bbAllUser=userService.getAllSon(bbUser.get(0).getUser_name());//A区节点 下的B节点 下的所有子节点
				if (bbAllUser!=null) {
					Bcount+=bbAllUser.size();
				}
			}
			
			bVo.setSumA(Acount*Constans.DIAGRAM);
			bVo.setSumB(Bcount*Constans.DIAGRAM);
			if (Acount>=Bcount) {
				bVo.setJieyuA(bVo.getSumA()-bVo.getSumB());
				bVo.setJieyuB(0);
			}else {
				bVo.setJieyuA(0);
				bVo.setJieyuB(bVo.getSumB()-bVo.getSumA());
			}
		}
		
		if (aVo!=null) {
			parent.setSumA(aVo.getSumA()+aVo.getSumB()+Constans.DIAGRAM);
		}
		if (bVo!=null) {
			parent.setSumB(bVo.getSumA()+bVo.getSumB()+Constans.DIAGRAM);
		}
		if (parent.getSumA()>=parent.getSumB()) {
			parent.setJieyuA(parent.getSumA()-parent.getSumB());
			parent.setJieyuB(0);
		}else {
			parent.setJieyuA(0);
			parent.setJieyuB(parent.getSumB()-parent.getSumA());
		}
		
		
		
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("parent", parent);
		paramaterMap.put("a", aVo);
		paramaterMap.put("b", bVo);
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("member/diagram");
		return mv;
	}

}
