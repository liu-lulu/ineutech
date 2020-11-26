package com.ineutech.entity;

import java.util.List;

public class VisitRecord extends VisitModel{
	private Integer visit_id;
	private String img_file;
	private String visit_time;
	
	private Employee employee;
	private Client client;
	private List<VisitVoice> voices;
	
	public VisitRecord(){}
	public VisitRecord(Integer visit_id,String img_file){
		this.visit_id=visit_id;
		this.img_file=img_file;
	}
	
	public Integer getVisit_id() {
		return visit_id;
	}
	public void setVisit_id(Integer visit_id) {
		this.visit_id = visit_id;
	}
	public String getImg_file() {
		return img_file;
	}
	public void setImg_file(String img_file) {
		this.img_file = img_file;
	}
	public String getVisit_time() {
		return visit_time;
	}
	public void setVisit_time(String visit_time) {
		this.visit_time = visit_time;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<VisitVoice> getVoices() {
		return voices;
	}
	public void setVoices(List<VisitVoice> voices) {
		this.voices = voices;
	}
	
}
