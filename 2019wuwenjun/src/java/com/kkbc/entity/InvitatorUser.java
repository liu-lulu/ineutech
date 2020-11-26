package com.kkbc.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.gexin.fastjson.JSONObject;
import com.gexin.fastjson.serializer.SerializerFeature;


public class InvitatorUser {
	private Integer inviter_id;
	private String name;
	private String sex;
	private String title;
	private String phone;
	private String email;
	private String company;
	private String attache_num;
	private String arrival_pattern;
	private String arrival_position;
	private String arrival_info;
	private Date arrival_time;
	private String return_pattern;
	private String return_position;
	private String return_info;
	private Date return_time;
	private String pick_up;
	private String id_card;
	private String lodging_select;
	private String hotel_price;
	private String hotel_select;
	private Date in_time;
	private Date out_time;
	private String room_level;
	private String room_num;
	private String room_level2;
	private String room_num2;
	private String comment;
	
	private Integer confirm;
	private Integer started;
	private Integer recived;
	private Integer returned;
	
	private Integer signed;
	private Integer dined;
	private String assign_train_user;
	private String assign_hotel_user;
	private String level;
	
	private String redcarpet;
	private String dined_level;
	private String speech;
	private String speech_position;
	private String responsible_person;
	private String responsible_person_phone;
	
	private String contact;
	private String contact_phone;
	
	private String sign;
	private String property;
	
	public InvitatorUser(){}
	public InvitatorUser(Integer inviter_id, String arrival_pattern,
			String arrival_position, Date arrival_time,String arrival_info, String return_pattern,
			String return_position, Date return_time,String return_info, String pick_up,String responsible_person,String responsible_person_phone) {
		super();
		this.inviter_id = inviter_id;
		this.arrival_pattern = arrival_pattern;
		this.arrival_position = arrival_position;
		this.arrival_time = arrival_time;
		this.arrival_info=arrival_info;
		this.return_pattern = return_pattern;
		this.return_position = return_position;
		this.return_time = return_time;
		this.return_info=return_info;
		this.pick_up = pick_up;
		this.responsible_person = responsible_person;
		this.responsible_person_phone = responsible_person_phone;
	}
	
	public InvitatorUser(Integer inviter_id, String sign,String lodging_select,
			String hotel_select, Date in_time, Date out_time,
			String room_level, String room_num, String room_level2,
			String room_num2) {
		super();
		this.inviter_id = inviter_id;
		this.sign=sign;
		this.lodging_select = lodging_select;
		this.hotel_select = hotel_select;
		this.in_time = in_time;
		this.out_time = out_time;
		this.room_level = room_level;
		this.room_num = room_num;
		this.room_level2 = room_level2;
		this.room_num2 = room_num2;
	}
	public Integer getInviter_id() {
		return inviter_id;
	}
	public void setInviter_id(Integer inviter_id) {
		this.inviter_id = inviter_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAttache_num() {
		return attache_num;
	}
	public void setAttache_num(String attache_num) {
		this.attache_num = attache_num;
	}
	public String getArrival_pattern() {
		return arrival_pattern;
	}
	public void setArrival_pattern(String arrival_pattern) {
		this.arrival_pattern = arrival_pattern;
	}
	public String getArrival_position() {
		return arrival_position;
	}
	public void setArrival_position(String arrival_position) {
		this.arrival_position = arrival_position;
	}
	public Date getArrival_time() {
		return arrival_time;
	}
	public void setArrival_time(Date arrival_time) {
		this.arrival_time = arrival_time;
	}
	public String getReturn_pattern() {
		return return_pattern;
	}
	public void setReturn_pattern(String return_pattern) {
		this.return_pattern = return_pattern;
	}
	public String getReturn_position() {
		return return_position;
	}
	public void setReturn_position(String return_position) {
		this.return_position = return_position;
	}
	public Date getReturn_time() {
		return return_time;
	}
	public void setReturn_time(Date return_time) {
		this.return_time = return_time;
	}
	public String getPick_up() {
		return pick_up;
	}
	public void setPick_up(String pick_up) {
		this.pick_up = pick_up;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getLodging_select() {
		return lodging_select;
	}
	public void setLodging_select(String lodging_select) {
		this.lodging_select = lodging_select;
	}
	public String getHotel_price() {
		return hotel_price;
	}
	public void setHotel_price(String hotel_price) {
		this.hotel_price = hotel_price;
	}
	public String getHotel_select() {
		return hotel_select;
	}
	public void setHotel_select(String hotel_select) {
		this.hotel_select = hotel_select;
	}
	public Date getIn_time() {
		return in_time;
	}
	public void setIn_time(Date in_time) {
		this.in_time = in_time;
	}
	public Date getOut_time() {
		return out_time;
	}
	public void setOut_time(Date out_time) {
		this.out_time = out_time;
	}
	public String getRoom_level() {
		return room_level;
	}
	public void setRoom_level(String room_level) {
		this.room_level = room_level;
	}
	public String getRoom_num() {
		return room_num;
	}
	public void setRoom_num(String room_num) {
		this.room_num = room_num;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAssign_train_user() {
		return assign_train_user;
	}
	public void setAssign_train_user(String assign_train_user) {
		this.assign_train_user = assign_train_user;
	}
	public String getAssign_hotel_user() {
		return assign_hotel_user;
	}
	public void setAssign_hotel_user(String assign_hotel_user) {
		this.assign_hotel_user = assign_hotel_user;
	}
	public String getRoom_level2() {
		return room_level2;
	}
	public void setRoom_level2(String room_level2) {
		this.room_level2 = room_level2;
	}
	public String getRoom_num2() {
		return room_num2;
	}
	public void setRoom_num2(String room_num2) {
		this.room_num2 = room_num2;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getRedcarpet() {
		return redcarpet;
	}
	public void setRedcarpet(String redcarpet) {
		this.redcarpet = redcarpet;
	}
	public String getDined_level() {
		return dined_level;
	}
	public void setDined_level(String dined_level) {
		this.dined_level = dined_level;
	}
	public String getSpeech() {
		return speech;
	}
	public void setSpeech(String speech) {
		this.speech = speech;
	}
	public String getArrival_info() {
		return arrival_info;
	}
	public void setArrival_info(String arrival_info) {
		this.arrival_info = arrival_info;
	}
	public String getReturn_info() {
		return return_info;
	}
	public void setReturn_info(String return_info) {
		this.return_info = return_info;
	}
	public String getSpeech_position() {
		return speech_position;
	}
	public void setSpeech_position(String speech_position) {
		this.speech_position = speech_position;
	}
	public Integer getStarted() {
		return started;
	}
	public void setStarted(Integer started) {
		this.started = started;
	}
	public Integer getConfirm() {
		return confirm;
	}
	public void setConfirm(Integer confirm) {
		this.confirm = confirm;
	}
	
	public Integer getRecived() {
		return recived;
	}
	public void setRecived(Integer recived) {
		this.recived = recived;
	}
	public Integer getReturned() {
		return returned;
	}
	public void setReturned(Integer returned) {
		this.returned = returned;
	}
	public Integer getSigned() {
		return signed;
	}
	public void setSigned(Integer signed) {
		this.signed = signed;
	}
	public Integer getDined() {
		return dined;
	}
	public void setDined(Integer dined) {
		this.dined = dined;
	}
	
	public String getResponsible_person() {
		return responsible_person;
	}
	public void setResponsible_person(String responsible_person) {
		this.responsible_person = responsible_person;
	}
	
	public String getResponsible_person_phone() {
		return responsible_person_phone;
	}
	public void setResponsible_person_phone(String responsible_person_phone) {
		this.responsible_person_phone = responsible_person_phone;
	}
	
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String toTrafficInfo(){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("是否需要接送站",pick_up);
		jsonObject.put("到达方式",arrival_pattern);
		jsonObject.put("到达地点", arrival_position);
		jsonObject.put("到达航班号/车次",arrival_info);
		jsonObject.put("到达时间", arrival_time!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm").format(arrival_time):"");
		jsonObject.put("返程方式",return_pattern );
		jsonObject.put("返程地点", return_position);
		jsonObject.put("返回航班号/车次",return_info);
		jsonObject.put("返程时间", return_time!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm").format(return_time):"");
		jsonObject.put("对接人",responsible_person);
		jsonObject.put("对接人电话",responsible_person_phone);
		
		return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
	}
	
	public String toHotelInfo(){
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("住宿选择",lodging_select);
		jsonObject.put("酒店信息",hotel_select);
		jsonObject.put("入住日期",in_time!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm").format(in_time):"");
		jsonObject.put("退房日期",out_time!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm").format(out_time):"");
		jsonObject.put("房间标准1",room_level);
		jsonObject.put("需求数量1",room_num);
		jsonObject.put("房间标准2",room_level2);
		jsonObject.put("需求数量2",room_num2);
				
		return JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue);
	}

}
