package com.kkbc.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.kkbc.dao.UserDao;
import com.kkbc.entity.Bus;
import com.kkbc.entity.Group;
import com.kkbc.entity.InvitatorUser;
import com.kkbc.entity.InviteLog;
import com.kkbc.entity.ManageUser;
import com.kkbc.entity.Volunteer;
import com.kkbc.service.UserService;

public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;

	@Override
	public ManageUser getLoginUser(String loginName, String password) {
		ManageUser loginUser=userDao.getUser(new ManageUser(loginName, password));
		if (loginUser!=null&&loginUser.getGroup_id()!=null) {//志愿者
			Group group=userDao.getGroup(loginUser.getGroup_id());
			loginUser.setGroup(group);
		}
		return loginUser;
	}

	@Override
	public List<InvitatorUser> getInvitators(ManageUser loginUser,String name,String assignUser,String position,String info,String pickup,String lodging,String started,String recived,String returned,String signed,String menu, Integer pageNo) {
		
		return userDao.getInvitators(loginUser.getGroup().getGroup_id(),loginUser.getGroup().getType(),name,assignUser,position,info,pickup,lodging,started,recived,returned,signed,menu, pageNo);
	}

	@Transactional
	@Override
	public int updState(Integer login_id,Integer inviter_id,String type, String state) {
		userDao.updState(inviter_id, type, state);
		userDao.saveLog(new InviteLog(login_id,inviter_id, type, null, state));
		return 1;
	}

	@Override
	public int getCount(ManageUser loginUser,String name,String assignUser,String position,String info, String pickup,String lodging,String started,String recived,String returned,String signed,String menu) {
		
		return userDao.getCount(loginUser.getGroup().getGroup_id(),loginUser.getGroup().getType(),name,assignUser,position,info, pickup,lodging,started,recived,returned,signed,menu);
	}

	@Transactional
	@Override
	public int updInfo(Integer login_id,InvitatorUser user) {
		InvitatorUser orgInfo=userDao.getInvitorById(user.getInviter_id());
		userDao.updInfo(user);
		userDao.saveLog(new InviteLog(login_id,user.getInviter_id(), "6", orgInfo.toTrafficInfo(), user.toTrafficInfo()));
		return 1;
	}

	@Transactional
	@Override
	public int updHotelInfo(Integer login_id,InvitatorUser user) {
		InvitatorUser orgInfo=userDao.getInvitorById(user.getInviter_id());
		userDao.updHotelInfo(user);
		userDao.saveLog(new InviteLog(login_id,user.getInviter_id(), "7", orgInfo.toHotelInfo(), user.toHotelInfo()));
		return 1;
	}

	@Override
	public int saveVolunteer(Volunteer info) {
		return userDao.saveVolunteer(info);
	}

	@Override
	public Volunteer getVolunteer(Volunteer info) {
		return userDao.getVolunteer(info);
	}

	@Override
	public int saveBus(Bus info) {
		return userDao.saveBus(info);
	}

}
