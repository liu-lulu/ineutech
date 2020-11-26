package com.ineutech.control.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("demo")
public class DemoController {
	
	@RequestMapping("/")
	public ModelAndView login(){
		return new ModelAndView("demo/login");
	}
	
	@RequestMapping("toLogin")
	public ModelAndView toLogin(){
		return new ModelAndView("demo/login");
	}
	
	@RequestMapping("toEmployee")
	public ModelAndView toEmployee(){
		return new ModelAndView("demo/index");
	}
	
	@RequestMapping("toClient")
	public ModelAndView toClient(){
		return new ModelAndView("demo/clients");
	}

}
