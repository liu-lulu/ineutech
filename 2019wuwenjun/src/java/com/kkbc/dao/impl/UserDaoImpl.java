package com.kkbc.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kkbc.cons.Constans;
import com.kkbc.dao.UserDao;
import com.kkbc.entity.Bus;
import com.kkbc.entity.Group;
import com.kkbc.entity.InvitatorUser;
import com.kkbc.entity.InviteLog;
import com.kkbc.entity.ManageUser;
import com.kkbc.entity.Volunteer;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public ManageUser getUser(ManageUser loginInfo) {
		return (ManageUser) getSqlMapClientTemplate().queryForObject("User.login", loginInfo);
	}

	@Override
	public List<InvitatorUser> getInvitators(Integer groupId,String type,String name,String assignUser,String position,String info,String pickup,String lodging,String started,String recived,String returned,String signed,String menu, Integer pageNo) {
		Map<String, Object> param= new HashMap<String, Object>();
		if (pageNo!=null) {
			param.put("startIndex", ((pageNo-1)*Constans.PAGE_SIZE));
			param.put("pageSize", Constans.PAGE_SIZE);
		}
		param.put("groupId", groupId);
		param.put("type", type);
		param.put("name", name);
		param.put("assignUser", assignUser);
		param.put("position", position);
		param.put("info", info);
		param.put("pickup", pickup);
		param.put("lodging", lodging);
		param.put("started", started);
		param.put("recived", recived);
		param.put("returned", returned);
		param.put("signed", signed);
		param.put("menu", menu);
		return getSqlMapClientTemplate().queryForList("User.getUser", param);
	}

	@Override
	public int updState(Integer inviter_id,String type, String state) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("inviter_id", inviter_id);
		param.put("type", type);
		param.put("state", state);
		getSqlMapClientTemplate().update("User.updState", param);
		return 1;
	}

	@Override
	public int getCount(Integer groupId,String type,String name,String assignUser,String position,String info, String pickup,String lodging,String started,String recived,String returned,String signed,String menu) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("groupId", groupId);
		param.put("type", type);
		param.put("name", name);
		param.put("assignUser", assignUser);
		param.put("position", position);
		param.put("info", info);
		param.put("pickup", pickup);
		param.put("lodging", lodging);
		param.put("started", started);
		param.put("recived", recived);
		param.put("returned", returned);
		param.put("signed", signed);
		param.put("menu", menu);
		return (int) getSqlMapClientTemplate().queryForObject("User.getCount", param);
	}

	@Override
	public int updInfo(InvitatorUser user) {
		return getSqlMapClientTemplate().update("User.updInfo", user);
	}

	@Override
	public int updHotelInfo(InvitatorUser user) {
		return getSqlMapClientTemplate().update("User.updHotelInfo", user);
	}

	@Override
	public int saveLog(InviteLog log) {
		getSqlMapClientTemplate().insert("User.saveLog", log);
		return 1;
	}

	@Override
	public InvitatorUser getInvitorById(int id) {
		return (InvitatorUser) getSqlMapClientTemplate().queryForObject("User.getInvitorById", id);
	}

	@Override
	public Group getGroup(int groupId) {
		return (Group) getSqlMapClientTemplate().queryForObject("User.getGroup", groupId);
	}

	@Override
	public int saveVolunteer(Volunteer info) {
		 getSqlMapClientTemplate().insert("User.saveVolunteer", info);
		 return 1;
	}

	@Override
	public Volunteer getVolunteer(Volunteer info) {
		return (Volunteer) getSqlMapClientTemplate().queryForObject("User.getVolunteer", info);
	}

	@Override
	public int saveBus(Bus info) {
		getSqlMapClientTemplate().insert("User.saveBus", info);
		return 1;
	}



}
