package com.ineutech.control.view;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ineutech.entity.Client;
import com.ineutech.entity.Employee;
import com.ineutech.entity.VisitRecord;
import com.ineutech.service.UserService;
import com.ineutech.util.DateUtil;

@Controller
@RequestMapping("visit")
public class VisitController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping("toLogin")
	public ModelAndView toLogin(HttpSession session){
		session.removeAttribute("employee");
		return new ModelAndView("visit/login");
	}
	
	@RequestMapping("login")
	public ModelAndView login(String loginUsername,String loginPassword,HttpSession session){
		if (StringUtils.isEmpty(loginUsername)||StringUtils.isEmpty(loginPassword)) {
			return new ModelAndView("visit/login");
		}
		Employee info=userService.getLoginEmployee(loginUsername, loginPassword);
		if (info==null||info.getRole()==1) {
			Map<String, String> paraMap=new HashMap<String, String>();
			paraMap.put("loginUsername", loginUsername);
			paraMap.put("loginPassword", loginPassword);
			paraMap.put("msg", "用户名或密码错误");
			return new ModelAndView("visit/login",paraMap);
		}
		
		session.setAttribute("employee", info);
		return new ModelAndView("redirect:toEmployee");
	}
	
	@RequestMapping("toEmployee")
	public ModelAndView toEmployee(HttpSession session){
		Employee leader=(Employee) session.getAttribute("employee");
		Integer leaderId=leader.getRole()==3?null:leader.getEmployee_id();
		List<Employee> employees=userService.getEmployees(leaderId, null);
		//客户拜访次数
		int visitClientNum=userService.visitRecordsCount(leaderId, null, null, null, null);
		Date curDate=new Date();
		//本月拜访次数
		int visitClientCurMonth=userService.visitRecordsCount(leaderId, null, null, DateUtil.getDayBegin(DateUtil.getFirstDayCurMonth()), DateUtil.getDayEnd(curDate));
		List<VisitRecord> records=userService.visitRecords(leaderId, null, null, null, null, null);
		List<String> taskList=records.stream().map(VisitRecord::getPurpose).distinct().collect(Collectors.toList());
		Map<String, Long> purposeCount=records.stream().collect(Collectors.groupingBy(VisitRecord::getPurpose, Collectors.counting()));
		Map<String, Object> purposeDateCount=userService.visitLine(leaderId, null, null, null, null);
		
		List<VisitRecord> curDateRecords=userService.visitRecords(leaderId, null, null, null, DateUtil.getDayBegin(curDate), DateUtil.getDayEnd(curDate));
		
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("employees", employees);
		paraMap.put("visitClientNum", visitClientNum);
		paraMap.put("visitClientCurMonth", visitClientCurMonth);
		paraMap.put("keywords", userService.keywords());
		paraMap.put("taskList", taskList);
		paraMap.put("purposeCount", JSONObject.toJSONString(purposeCount));
		paraMap.put("purposeDateCount", JSONObject.toJSONString(purposeDateCount));
		paraMap.put("curDateRecords", curDateRecords);
		if(leaderId==null){
			paraMap.put("groups", userService.getGroupVisit());
		}
		
		return new ModelAndView("visit/index",paraMap);
	}
	
	@RequestMapping("toClient")
	public ModelAndView toClient(HttpSession session){
		Employee leader=(Employee) session.getAttribute("employee");
		Integer leaderId=leader.getRole()==3?null:leader.getEmployee_id();
		List<Client> clients=userService.clients(leaderId, null, null, null, null);
		Map<String, Long> sexCount=clients.stream().filter(c->c.getClient_sex()!=null).collect(Collectors.groupingBy(Client::getClient_sex, Collectors.counting()));
		Map<String, Long> ageCount=clients.stream().filter(c->c.getAge()!=null).collect(Collectors.groupingBy(c->c.getAge()<=9?"0~9":String.valueOf(c.getAge()).substring(0,1)+StringUtils.join(new String[String.valueOf(c.getAge()).length()], "0")+"~"+String.valueOf(c.getAge()).substring(0,1)+StringUtils.join(new String[String.valueOf(c.getAge()).length()], "9"), Collectors.counting()));
		Map<String,Long> sortageCount = new LinkedHashMap<>();
		ageCount.entrySet().stream().sorted((e1, e2) -> Integer.valueOf(e1.getKey().substring(0, e1.getKey().indexOf("~"))).compareTo(Integer.valueOf(e2.getKey().substring(0, e2.getKey().indexOf("~")))))
        .forEachOrdered(x -> sortageCount.put(x.getKey(), x.getValue()));
		
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("clients", clients);
		paraMap.put("sexCount", JSONObject.toJSONString(sexCount));
		paraMap.put("ageCount", JSONObject.toJSONString(sortageCount));
		return new ModelAndView("visit/clients",paraMap);
	}
	
	public static void main(String[] args) {
		
	}

}
