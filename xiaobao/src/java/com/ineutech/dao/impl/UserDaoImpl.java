package com.ineutech.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ineutech.cons.Constans;
import com.ineutech.dao.UserDao;
import com.ineutech.entity.Client;
import com.ineutech.entity.ClientModel;
import com.ineutech.entity.Employee;
import com.ineutech.entity.GroupVisit;
import com.ineutech.entity.Keywords;
import com.ineutech.entity.VisitModel;
import com.ineutech.entity.VisitRecord;
import com.ineutech.entity.VisitVoice;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public Employee getLoginEmployee(String loginName, String pwd) {
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("employee_login", loginName);
		paraMap.put("employee_pwd", pwd);
		return (Employee) getSqlMapClientTemplate().queryForObject("Visit.getLoginEmployee", paraMap);
	}

	@Override
	public List<Employee> getEmployees(Integer leader_id, String name) {
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("leader_id", leader_id);
		paraMap.put("employee_name", name);
		return getSqlMapClientTemplate().queryForList("Visit.getEmployee", paraMap);
	}

	@Override
	public List<Client> clients(Integer leader_id,Integer employee_id, Integer client_id,
			String client_name, Integer pageNo) {
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("leader_id", leader_id);
		paraMap.put("employee_id", employee_id);
		paraMap.put("client_id", client_id);
		paraMap.put("client_name", client_name);
		if (pageNo!=null) {
			paraMap.put("startIndex", ((pageNo-1)*Constans.PAGE_SIZE));
			paraMap.put("pageSize", Constans.PAGE_SIZE);
		}
		return getSqlMapClientTemplate().queryForList("Visit.getClient", paraMap);
	}

	@Override
	public Integer clientsCount(Integer leader_id,Integer employee_id, Integer client_id,
			String client_name) {
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("leader_id", leader_id);
		paraMap.put("employee_id", employee_id);
		paraMap.put("client_id", client_id);
		paraMap.put("client_name", client_name);
		return (Integer) getSqlMapClientTemplate().queryForObject("Visit.clientCount", paraMap);
	}

	@Override
	public List<VisitRecord> visitRecords(Integer leader_id,Integer employee_id,
			Integer client_id, Integer pageNo,Date beginDate, Date endDate) {
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("leader_id", leader_id);
		paraMap.put("employee_id", employee_id);
		paraMap.put("client_id", client_id);
		paraMap.put("beginDate", beginDate);
		paraMap.put("endDate", endDate);
		if (pageNo!=null) {
			paraMap.put("startIndex", ((pageNo-1)*Constans.PAGE_SIZE));
			paraMap.put("pageSize", Constans.PAGE_SIZE);
		}
		return getSqlMapClientTemplate().queryForList("Visit.getVisit", paraMap);
	}

	@Override
	public Integer visitRecordsCount(Integer leader_id,Integer employee_id, Integer client_id,Date beginDate, Date endDate) {
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("leader_id", leader_id);
		paraMap.put("employee_id", employee_id);
		paraMap.put("client_id", client_id);
		paraMap.put("beginDate", beginDate);
		paraMap.put("endDate", endDate);
		return (Integer) getSqlMapClientTemplate().queryForObject("Visit.visitCount", paraMap);
	}

	@Override
	public Integer saveClient(ClientModel info) {
		return (Integer) getSqlMapClientTemplate().insert("Visit.saveClient", info);
	}

	@Override
	public Integer saveRecord(VisitModel info) {
		return (Integer) getSqlMapClientTemplate().insert("Visit.saveVisit", info);
	}

	@Override
	public Integer saveVoice(VisitVoice info) {
		return (Integer) getSqlMapClientTemplate().insert("Visit.saveVoice", info);
	}

	@Override
	public int updateImg(VisitRecord info) {
		return getSqlMapClientTemplate().update("Visit.updImg", info);
	}

	@Override
	public int updVoiceContent(VisitVoice info) {
		return getSqlMapClientTemplate().update("Visit.updVoiceContent", info);
	}

	@Override
	public List<String> taskIdNoVoiceContent() {
		return getSqlMapClientTemplate().queryForList("Visit.taskIdNoVoiceContent");
	}

	@Override
	public int updateWechat(VisitRecord info) {
		return getSqlMapClientTemplate().update("Visit.updWechat", info);
	}

	@Override
	public VisitVoice getVoiceContent(int voiceId) {
		return (VisitVoice) getSqlMapClientTemplate().queryForObject("Visit.getVoiceContentByVoiceId", voiceId);
	}

	@Override
	public String getWechatContent(int visitId) {
		return (String) getSqlMapClientTemplate().queryForObject("Visit.getWechatContent", visitId);
	}

	@Override
	public Integer updClient(Client client) {
		return getSqlMapClientTemplate().update("Visit.updClient", client);
	}

	@Override
	public List<Keywords> keywords() {
		return getSqlMapClientTemplate().queryForList("Visit.keywords");
	}

	@Override
	public int delKeyword(int keyword_id) {
		return getSqlMapClientTemplate().delete("Visit.delKeywords", keyword_id);
	}

	@Override
	public Integer addKeyword(Keywords info) {
		return (Integer)getSqlMapClientTemplate().insert("Visit.saveKeywords", info);
	}

	@Override
	public Integer delClient(Integer client_id) {
		return getSqlMapClientTemplate().delete("Visit.delClient",client_id);
	}

	@Override
	public List<Employee> getGroup() {
		return getSqlMapClientTemplate().queryForList("Visit.getGroup");
	}

	@Override
	public List<GroupVisit> getGroupVisits() {
		return getSqlMapClientTemplate().queryForList("Visit.groupVisit");
	}

	@Override
	public int voiceComment(VisitVoice info) {
		return getSqlMapClientTemplate().update("Visit.updVoiceComment", info);
	}

	@Override
	public List<VisitVoice> getVoiceByVisitId(int visitId) {
		return getSqlMapClientTemplate().queryForList("Visit.getVoiceByVisitId", visitId);
	}

}
