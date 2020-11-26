package com.kkbc.service;

import java.util.List;

import com.kkbc.entity.Bus;
import com.kkbc.entity.InvitatorUser;
import com.kkbc.entity.ManageUser;
import com.kkbc.entity.Volunteer;


public interface UserService {
	
	ManageUser getLoginUser(String loginName,String password);
	List<InvitatorUser> getInvitators(ManageUser loginUser,String name,String assignUser,String position,String info,String pickup,String lodging,String started,String recived,String returned,String signed,String menu, Integer pageNo);
	int getCount(ManageUser loginUser,String name,String assignUser,String position,String info, String pickup,String lodging,String started,String recived,String returned,String signed,String menu);
	int updState(Integer login_id,Integer inviter_id,String type,String state);
	int updInfo(Integer login_id,InvitatorUser user);
	int updHotelInfo(Integer login_id,InvitatorUser user);
	int saveVolunteer(Volunteer info);
	Volunteer getVolunteer(Volunteer info);
	int saveBus(Bus info);

}
