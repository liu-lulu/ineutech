package com.ineutech.dao;

import java.util.Date;
import java.util.List;

import com.ineutech.entity.Client;
import com.ineutech.entity.ClientModel;
import com.ineutech.entity.Employee;
import com.ineutech.entity.GroupVisit;
import com.ineutech.entity.Keywords;
import com.ineutech.entity.VisitModel;
import com.ineutech.entity.VisitRecord;
import com.ineutech.entity.VisitVoice;


public interface UserDao {
	Employee getLoginEmployee(String loginName,String pwd);
	List<Employee> getEmployees(Integer leader_id,String name);
	
	List<Client> clients(Integer leader_id,Integer employee_id,Integer client_id,String client_name,Integer pageNo);
	Integer clientsCount(Integer leader_id,Integer employee_id,Integer client_id,String client_name);
	
	List<VisitRecord> visitRecords(Integer leader_id,Integer employee_id,Integer client_id,Integer pageNo,
			Date beginDate, Date endDate);
	Integer visitRecordsCount(Integer leader_id,Integer employee_id,Integer client_id,
			Date beginDate, Date endDate);
	
	Integer saveClient(ClientModel info);
	Integer delClient(Integer client_id);
	Integer saveRecord(VisitModel info);
	Integer saveVoice(VisitVoice info);
	
	int updateImg(VisitRecord info);
	int updateWechat(VisitRecord info);
	int updVoiceContent(VisitVoice info);
	
	List<String> taskIdNoVoiceContent();
	
	VisitVoice getVoiceContent(int voiceId);
	String getWechatContent(int visitId);
	Integer updClient(Client client);
	
	List<Keywords> keywords();
	int delKeyword(int keyword_id);
	Integer addKeyword(Keywords info);
	
	List<Employee> getGroup();
	List<GroupVisit> getGroupVisits();
	
	int voiceComment(VisitVoice info);
	
	List<VisitVoice> getVoiceByVisitId(int visitId);
	
}
