package com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.entity.Answer;
import com.service.AnswerService;
public class AnswerAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AnswerService answerService;
	//学号
	private String str_num;
	//维度1
	private String dimension1;
	//维度2
	private String dimension2;
	//题号
	private String que_no;
	//正确答案
	private String true_answer;
	//学生答案
	private String answers;
	//分数
	private int score;
	
	/**
	 * 登入
	 * @return
	 * @throws IOException 
	 */
	public String addAnswerOne() throws IOException{
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		HttpSession session = request.getSession();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			Answer answer = new Answer();
			answer.setStr_id(session.getAttribute("str_num")+"");
			answer.setDimension1(dimension1);
			answer.setDimension2(dimension2);
			answer.setQue_no(que_no);
			answer.setTrue_answer(true_answer);
			answer.setAnswer(answers);
			answer.setScore(score);
			answerService.addAnswerOne(answer);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String login(){
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		HttpSession session = request.getSession();
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			session.setAttribute("str_num", str_num.trim());
		}catch(Exception e){
			e.printStackTrace();
		}
		return "loginok";
	}
	
	

	public String getStr_num() {
		return str_num;
	}

	public void setStr_num(String str_num) {
		this.str_num = str_num;
	}

	public String getDimension1() {
		return dimension1;
	}

	public void setDimension1(String dimension1) {
		this.dimension1 = dimension1;
	}

	public String getDimension2() {
		return dimension2;
	}

	public void setDimension2(String dimension2) {
		this.dimension2 = dimension2;
	}

	public String getQue_no() {
		return que_no;
	}

	public void setQue_no(String que_no) {
		this.que_no = que_no;
	}

	public String getTrue_answer() {
		return true_answer;
	}

	public void setTrue_answer(String true_answer) {
		this.true_answer = true_answer;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public AnswerService getAnswerService() {
		return answerService;
	}

	public void setAnswerService(AnswerService answerService) {
		this.answerService = answerService;
	}

	
	
	
	
}
