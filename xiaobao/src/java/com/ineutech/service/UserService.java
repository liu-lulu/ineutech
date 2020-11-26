package com.ineutech.service;

import com.ineutech.entity.VisitModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ineutech.entity.Client;
import com.ineutech.entity.ClientModel;
import com.ineutech.entity.Employee;
import com.ineutech.entity.GroupVisit;
import com.ineutech.entity.Keywords;
import com.ineutech.entity.VisitRecord;
import com.ineutech.entity.VisitVoice;


public interface UserService {
	
	Employee getLoginEmployee(String loginName,String pwd);
	List<Employee> getEmployees(Integer leader_id,String name);
	
	List<Client> clients(Integer leader_id,Integer employee_id,Integer client_id,String client_name,Integer pageNo);
	Integer clientsCount(Integer leader_id,Integer employee_id,Integer client_id,String client_name);
	
	List<VisitRecord> visitRecords(Integer leader_id,Integer employee_id,Integer client_id,Integer pageNo,Date beginDate,Date endDate);
	Integer visitRecordsCount(Integer leader_id,Integer employee_id,Integer client_id,Date beginDate,Date endDate);
	
	int saveClient(ClientModel info);
	Integer delClient(Integer client_id);
	int saveRecord(VisitModel info);
	int saveVoice(VisitVoice info);
	
	int updateImg(VisitRecord info);
	int updVoiceContent(VisitVoice info);
	int appendWechat(int visit_id,String wechatContent);
	
	Map<String, Object> visitLine(Integer leader_id,Integer employee_id,Integer client_id,Date beginDate,Date endDate);
	List<String> taskIdNoVoiceContent();
	
	VisitVoice getVoiceContent(int voiceId);
	String getWechatContent(int visitId);
	Integer updClient(Client client);
	
	List<Keywords> keywords();
	int delKeyword(int keyword_id);
	Integer addKeyword(Keywords info);
	
	Map<Employee, List<GroupVisit>> getGroupVisit();
	
	int voiceComment(VisitVoice info);
	List<VisitVoice> getVoiceByVisitId(int visitId);
}
