package com.kkbc.control.pad;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gexin.fastjson.JSONObject;
import com.kkbc.entity.News;
import com.kkbc.service.NewsService;
import com.kkbc.util.SmartcityNews;
import com.psylife.util.a;

/**
 * 用户模块
 * 
 * @author liululu
 *
 */
@Controller
public class PadController {
	
	private Logger logger = LoggerFactory.getLogger(PadController.class);
	
	@Resource
	private NewsService newsService;

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
	
	
	@RequestMapping("news")
	public ModelAndView news(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String pagenoString = request.getParameter("pageno");
		Integer pageno = StringUtils.isEmpty(pagenoString)?1:Integer.valueOf(pagenoString);
		
//		paraMap.put("list", newsService.getNews(pageno));
		
		paraMap.put("list", newsService.getNews(pageno).stream().collect(Collectors.groupingBy(News::getPublish_time,LinkedHashMap::new,Collectors.toList())));
		paraMap.put("pageno", pageno);
		return new ModelAndView("news", paraMap);
		
	}
	
	@ResponseBody
	@RequestMapping("more")
	public Map<String, Object> more(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String pagenoString = request.getParameter("pageno");
		Integer pageno = StringUtils.isEmpty(pagenoString)?1:Integer.valueOf(pagenoString);
		
//		paraMap.put("list", newsService.getNews(pageno));
		paraMap.put("list", newsService.getNews(pageno).stream().collect(Collectors.groupingBy(News::getPublish_time,LinkedHashMap::new,Collectors.toList())));
		paraMap.put("pageno", pageno);
		return paraMap;
		
	}
	
	@RequestMapping("newslist")
	public ModelAndView newslist(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String pagenoString = request.getParameter("pageno");
		Integer pageno = StringUtils.isEmpty(pagenoString)?1:Integer.valueOf(pagenoString);
		
		paraMap.put("list", newsService.getNewsList(pageno));
		
		paraMap.put("pageno", pageno);
		return new ModelAndView("news1", paraMap);
		
	}
	
	@RequestMapping("adminnewslist")
	public ModelAndView adminnewslist(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String pagenoString = request.getParameter("pageno");
		Integer pageno = StringUtils.isEmpty(pagenoString)?1:Integer.valueOf(pagenoString);
		
		paraMap.put("list", newsService.getNewsList(pageno));
		
		paraMap.put("pageno", pageno);
		return new ModelAndView("adminnews", paraMap);
		
	}
	
	@ResponseBody
	@RequestMapping("morelist")
	public Map<String, Object> morelist(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String pagenoString = request.getParameter("pageno");
		Integer pageno = StringUtils.isEmpty(pagenoString)?1:Integer.valueOf(pagenoString);
		
		paraMap.put("list", newsService.getNewsList(pageno));
		paraMap.put("pageno", pageno);
		return paraMap;
		
	}
	
	@ResponseBody
	@RequestMapping("add")
	public Map<String, Object> add(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String img_url = request.getParameter("img_url");
		paraMap.put("ret", newsService.save(new News(title, img_url, content)));
		return paraMap;
		
	}
	
	@RequestMapping("detail")
	public ModelAndView detail(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String id = request.getParameter("id");
		paraMap.put("info", newsService.getById(Integer.valueOf(id)));
		return new ModelAndView("detail", paraMap);
		
	}
	
	@RequestMapping("toedit")
	public ModelAndView toedit(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String id = request.getParameter("id");
		paraMap.put("info", newsService.getById(Integer.valueOf(id)));
		return new ModelAndView("editnews", paraMap);
		
	}
	
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String img_url = request.getParameter("img_url");
		newsService.edit(new News(Integer.valueOf(id),title, img_url, content));
		paraMap.put("id", id);
		
		return new ModelAndView("redirect:/detail.do", paraMap);
		
	}
	
	
	@RequestMapping("iframe")
	public ModelAndView iframe(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String pagenoString = request.getParameter("pageno");
		Integer pageno = StringUtils.isEmpty(pagenoString)?1:Integer.valueOf(pagenoString);
		
		paraMap.put("list", newsService.getNewsList(pageno));
		
		paraMap.put("pageno", pageno);
		return new ModelAndView("../test", paraMap);
		
	}
	public static void main(String[] args) {
		List<Person> list = new ArrayList<>();
		list.add(new Person("jack", "20"));
		list.add(new Person("mike", "25"));
		list.add(new Person("tom", "30"));
		
		System.out.println(list.stream().mapToInt(a -> Integer.valueOf(a.getAge())).sum());
		System.out.println(list.stream().map(Person::getAge).collect(Collectors.toList()).stream().mapToInt(Integer::valueOf).sum());
	}
	

}

class Person{
	private String name;
	private String age;
	
	public Person(String name, String age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAge() {
		return age;
	}
	
	public void setAge(String age) {
		this.age = age;
	}
	
}
