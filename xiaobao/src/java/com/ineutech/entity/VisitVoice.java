package com.ineutech.entity;

import java.util.Date;

public class VisitVoice {
	
	private Integer voice_id;
	private Integer visit_id;
	private String voice_file;
	private String voice_task;
	private String voice_content;
	private String voice_comment;
	private Date voice_time;
	private Boolean analyze; 
	
	public VisitVoice(){}
	public VisitVoice(Integer voice_id,String voice_comment){
		this.voice_id=voice_id;
		this.voice_comment=voice_comment;
	}
	public VisitVoice(String voice_task,String voice_content){
		this.voice_task=voice_task;
		this.voice_content=voice_content;
	}
	public VisitVoice(Integer visit_id,String voice_file,String voice_task,String voice_comment){
		this.visit_id=visit_id;
		this.voice_file=voice_file;
		this.voice_task=voice_task;
		this.voice_comment=voice_comment;
	}
	public Integer getVoice_id() {
		return voice_id;
	}
	public void setVoice_id(Integer voice_id) {
		this.voice_id = voice_id;
	}
	public String getVoice_file() {
		return voice_file;
	}
	
	public Integer getVisit_id() {
		return visit_id;
	}
	public void setVisit_id(Integer visit_id) {
		this.visit_id = visit_id;
	}
	public void setVoice_file(String voice_file) {
		this.voice_file = voice_file;
	}
	public String getVoice_task() {
		return voice_task;
	}
	public void setVoice_task(String voice_task) {
		this.voice_task = voice_task;
	}
	public String getVoice_content() {
		return voice_content;
	}
	public void setVoice_content(String voice_content) {
		this.voice_content = voice_content;
	}
	public Date getVoice_time() {
		return voice_time;
	}
	public void setVoice_time(Date voice_time) {
		this.voice_time = voice_time;
	}
	public Boolean getAnalyze() {
		return analyze;
	}
	public void setAnalyze(Boolean analyze) {
		this.analyze = analyze;
	}
	public String getVoice_comment() {
		return voice_comment;
	}
	public void setVoice_comment(String voice_comment) {
		this.voice_comment = voice_comment;
	}

}
