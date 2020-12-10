package com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.entity.Student;
import com.service.StudentService;

public class StudentAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String str_num;
	
	private Student Student;
	
	private StudentService studentService;
	
	/**
	 * µ«»Î
	 * @return
	 * @throws IOException 
	 */
	public String login() throws IOException{
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		HttpSession session = request.getSession();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			Student = studentService.login(str_num);
			if(Student!=null){
				session.setAttribute("loginstu", Student);
				return "loginok";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "loginof";
	}

	public String getStr_num() {
		return str_num;
	}

	public void setStr_num(String str_num) {
		this.str_num = str_num;
	}


	public Student getStudent() {
		return Student;
	}

	public void setStudent(Student student) {
		Student = student;
	}

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	
	
	
	
}
