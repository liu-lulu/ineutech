package com.ineutech.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

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
import com.ineutech.service.UserService;

public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;

	@Override
	public Employee getLoginEmployee(String loginName, String pwd) {
		return userDao.getLoginEmployee(loginName, pwd);
	}

	@Override
	public List<Employee> getEmployees(Integer leader_id, String name) {
		return userDao.getEmployees(leader_id, name);
	}

	@Override
	public List<Client> clients(Integer leader_id,Integer employee_id, Integer client_id,
			String client_name, Integer pageNo) {
		return userDao.clients(leader_id,employee_id, client_id, client_name, pageNo);
	}

	@Override
	public Integer clientsCount(Integer leader_id,Integer employee_id, Integer client_id,
			String client_name) {
		return userDao.clientsCount(leader_id,employee_id, client_id, client_name);
	}

	@Override
	public List<VisitRecord> visitRecords(Integer leader_id,Integer employee_id,
			Integer client_id, Integer pageNo,Date beginDate,Date endDate) {
		return userDao.visitRecords(leader_id,employee_id, client_id, pageNo,beginDate,endDate);
	}

	@Override
	public Integer visitRecordsCount(Integer leader_id,Integer employee_id, Integer client_id,Date beginDate,Date endDate) {
		return userDao.visitRecordsCount(leader_id,employee_id, client_id,beginDate,endDate);
	}

	@Override
	public int saveClient(ClientModel info) {
		try {
			userDao.saveClient(info);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int saveRecord(VisitModel info) {
		try {
			return userDao.saveRecord(info);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int saveVoice(VisitVoice info) {
		
		try {
			userDao.saveVoice(info);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public int updateImg(VisitRecord info) {
		return userDao.updateImg(info);
	}

	@Override
	public int updVoiceContent(VisitVoice info) {
		return userDao.updVoiceContent(info);
	}

	@Override
	public Map<String, Object> visitLine(Integer leader_id,Integer employee_id,Integer client_id,Date beginDate,Date endDate) {
		List<VisitRecord> visitRecords=userDao.visitRecords(leader_id, employee_id, client_id, null, beginDate, endDate);
		List<String> visitDate=visitRecords.stream().map(v->v.getVisit_time().substring(0, 10)).collect(Collectors.toList()).stream().distinct().sorted().collect(Collectors.toList());
		Map<String, List<VisitRecord>> purposeRecords=visitRecords.stream().collect(Collectors.groupingBy(VisitRecord::getPurpose));
		
		Map<String, Map<String, Long>> purposeDate=new HashMap<String, Map<String,Long>>(purposeRecords.size());
		for (String purpose : purposeRecords.keySet()) {
			purposeDate.put(purpose, purposeRecords.get(purpose).stream().collect(Collectors.groupingBy(v->v.getVisit_time().substring(0, 10), Collectors.counting())));
		}
		Map<String, List<Long>> purposeNum=new LinkedHashMap<String, List<Long>>();
		for (String date : visitDate) {
			for (String purpose : purposeDate.keySet()) {
				long dateNum=purposeDate.get(purpose).get(date)==null?0:purposeDate.get(purpose).get(date);
				if (purposeNum.get(purpose)==null) {
					List<Long> purposeDateNum=new ArrayList<Long>(visitDate.size());
					purposeNum.put(purpose, purposeDateNum);
				}
				purposeNum.get(purpose).add(dateNum);
			}
			
		}
		
		Map<String, Object> resultMap=new HashMap<String, Object>();
		resultMap.put("visitDate", visitDate);
		resultMap.put("purposeNum", purposeNum);
		
		return resultMap;
	}

	@Override
	public List<String> taskIdNoVoiceContent() {
		return userDao.taskIdNoVoiceContent();
	}

	@Override
	public int appendWechat(int visit_id, String wechatContent) {
		VisitRecord record=new VisitRecord();
		record.setVisit_id(visit_id);
		record.setWechat_content(wechatContent);
		try {
			userDao.updateWechat(record);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public VisitVoice getVoiceContent(int voiceId) {
		return userDao.getVoiceContent(voiceId);
	}

	@Override
	public String getWechatContent(int visitId) {
		return userDao.getWechatContent(visitId);
	}

	@Override
	public Integer updClient(Client client) {
		try {
			userDao.updClient(client);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List<Keywords> keywords() {
		return userDao.keywords();
	}

	@Override
	public int delKeyword(int keyword_id) {
		try {
			userDao.delKeyword(keyword_id);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public Integer addKeyword(Keywords info) {
		try {
			return userDao.addKeyword(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Integer delClient(Integer client_id) {
		List<VisitRecord> records=userDao.visitRecords(null, null, client_id, null, null, null);
		List<VisitVoice> visitVoices=new ArrayList<VisitVoice>();
		for (VisitRecord visitRecord : records) {
			visitVoices.addAll(visitRecord.getVoices());
		}
		System.out.println(records.size());
		
		try {
			
			userDao.delClient(client_id);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		for (VisitRecord visitRecord : records) {
			String img=visitRecord.getImg_file();
			if (StringUtils.isNotEmpty(img)) {
				File imgFile=new File(Constans.IMG_PATH+img);
				imgFile.delete();
			}
			System.out.println(visitRecord.getVoices().size());
			for (VisitVoice voice : visitRecord.getVoices()) {
				File voiceFile=new File(Constans.VOICE_PATH+voice.getVoice_file());
				voiceFile.delete();
			}
			
		}
		return 1;
	}

	@Override
	public Map<Employee, List<GroupVisit>> getGroupVisit() {
		List<Employee> groups=userDao.getGroup();
		List<GroupVisit> visits=userDao.getGroupVisits();
		Map<Integer, List<GroupVisit>> groupVisitMap=visits.stream().collect(Collectors.groupingBy(GroupVisit::getGroup_id));
		
		Map<Employee, List<GroupVisit>> ret=new LinkedHashMap<Employee, List<GroupVisit>>();
		for (Employee group : groups) {
			ret.put(group, groupVisitMap.get(group.getEmployee_id()));
		}
		
		return ret;
	}

	@Override
	public int voiceComment(VisitVoice info) {
		try {
			userDao.voiceComment(info);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List<VisitVoice> getVoiceByVisitId(int visitId) {
		return userDao.getVoiceByVisitId(visitId);
	}

}
