package com.kkbc.dao;

import java.util.List;

import com.kkbc.entity.Bus;
import com.kkbc.entity.Group;
import com.kkbc.entity.InvitatorUser;
import com.kkbc.entity.InviteLog;
import com.kkbc.entity.ManageUser;
import com.kkbc.entity.Volunteer;



public interface UserDao {
	
	ManageUser getUser(ManageUser loginInfo);
	
	List<InvitatorUser> getInvitators(Integer groupId,String type,String name,String assignUser,String position,String info,String pickup,String lodging,String started,String recived,String returned,String signed,String menu,Integer pageNo);
	
	int getCount(Integer groupId,String type,String name,String assignUser,String position,String info,String pickup,String lodging,String started,String recived,String returned,String signed,String menu);
	
	int updState(Integer inviter_id,String type, String state);
	
	int updInfo(InvitatorUser user);
	
	int updHotelInfo(InvitatorUser user);
	
	int saveLog(InviteLog log);
	InvitatorUser getInvitorById(int id);
	
	Group getGroup(int groupId);
	
	int saveVolunteer(Volunteer info);
	Volunteer getVolunteer(Volunteer info);
	
	int saveBus(Bus info);
	
}
