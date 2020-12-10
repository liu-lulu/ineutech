package com.kkbc.vo;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class GoodsDescVO {
	
	public static String TABLE_NOPUBLISH="goodsdesc";
	
	public static String TABLE_PRE="publish_";
	
	public static String TABLE_GUONEI="guonei";
	public static String TABLE_MIAOSHA="miaosha";
	public static String TABLE_SUQIANG="suqiang";
	
	public static String TABLE_POS="_other";
	
	public static String TYPE_SEVEN="1";
	public static String TYPE_SEVEN_OTHER="2";
	
	public static int GOODS_STATE_HAVE=1;
	public static int GOODS_STATE_NO=2;
	
	private Integer desc_id;
	private String brand;
	private String category;
	private String other;
	private String content;
	private Date create_time;
	private String publish_time;
	private String small_img1;
	private String small_img2;
	private String small_img3;
	private String small_img4;
	private String img1;
	private String img2;
	private String img3;
	private String img4;
	private String img1_describe;
	private String img2_describe;
	private String img3_describe;
	private String img4_describe;
	private Integer heart;
	private Integer state;
	private Integer base;
	private Integer type;
	private Integer order_num;
	private Integer goods_state;
	private String goods_property;
	private String url;
	
	private String openId;
	private Integer attention;
	private Integer confirm;
	private String attention_time;
	
	private String wx_name;
	private String wx_username;
	private Integer user_sex;
	private String phone;
	private String address1;
	private String address2;
	private String address3;
	
	private String tableName;
	private String heartTableName;
	private List<String> descIdList;
	private String[] shuxing;
	
	private Integer attentionNum;
	private Integer confirmNum;
	private Integer noconfirmNum;
	
	private String time;
	
	public GoodsDescVO(){}
	
	public GoodsDescVO(Integer desc_id){
		this.desc_id=desc_id;
	}
	
	public GoodsDescVO(String brand,String category,String other,String content,Date create_time,String img1_describe,String img2_describe,String img3_describe,String img4_describe,Integer base,String[] shuxing,String goods_property,Integer goods_state,String url){
		this.brand=brand;
		this.category=category;
		this.other=other;
		this.content=content;
		this.create_time=create_time;
		this.img1_describe=img1_describe;
		this.img2_describe=img2_describe;
		this.img3_describe=img3_describe;
		this.img4_describe=img4_describe;
		this.base=base;
		this.shuxing=shuxing;
		this.goods_state=goods_state;
		this.goods_property=goods_property;
		this.url=url;
	}
	
	
	public GoodsDescVO(Integer desc_id,String brand,String category,String other,String content,Date create_time,String img1_describe,String img2_describe,String img3_describe,String img4_describe,Integer base,Integer type,String goods_property,Integer goods_state,String url){
		this.desc_id=desc_id;
		this.brand=brand;
		this.category=category;
		this.other=other;
		this.content=content;
		this.create_time=create_time;
		this.img1_describe=img1_describe;
		this.img2_describe=img2_describe;
		this.img3_describe=img3_describe;
		this.img4_describe=img4_describe;
		this.base=base;
		this.type=type;
		this.goods_state=goods_state;
		this.goods_property=goods_property;
		this.url=url;
	}
	
	public Integer getDesc_id() {
		return desc_id;
	}

	public void setDesc_id(Integer desc_id) {
		this.desc_id = desc_id;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getImg4() {
		return img4;
	}
	public void setImg4(String img4) {
		this.img4 = img4;
	}
	public String getImg1_describe() {
		return img1_describe;
	}
	public void setImg1_describe(String img1_describe) {
		this.img1_describe = img1_describe;
	}
	public String getImg2_describe() {
		return img2_describe;
	}
	public void setImg2_describe(String img2_describe) {
		this.img2_describe = img2_describe;
	}
	public String getImg3_describe() {
		return img3_describe;
	}
	public void setImg3_describe(String img3_describe) {
		this.img3_describe = img3_describe;
	}
	public String getImg4_describe() {
		return img4_describe;
	}
	public void setImg4_describe(String img4_describe) {
		this.img4_describe = img4_describe;
	}
	public Integer getHeart() {
		return heart;
	}
	public void setHeart(Integer heart) {
		this.heart = heart;
	}

	public String getSmall_img1() {
		return small_img1;
	}

	public void setSmall_img1(String small_img1) {
		this.small_img1 = small_img1;
	}

	public String getSmall_img2() {
		return small_img2;
	}

	public void setSmall_img2(String small_img2) {
		this.small_img2 = small_img2;
	}

	public String getSmall_img3() {
		return small_img3;
	}

	public void setSmall_img3(String small_img3) {
		this.small_img3 = small_img3;
	}

	public String getSmall_img4() {
		return small_img4;
	}

	public void setSmall_img4(String small_img4) {
		this.small_img4 = small_img4;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getBase() {
		return base;
	}

	public void setBase(Integer base) {
		this.base = base;
	}

	public String[] getShuxing() {
		return shuxing;
	}

	public void setShuxing(String[] shuxing) {
		this.shuxing = shuxing;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getHeartTableName() {
		return heartTableName;
	}

	public void setHeartTableName(String heartTableName) {
		this.heartTableName = heartTableName;
	}

	public List<String> getDescIdList() {
		return descIdList;
	}

	public void setDescIdList(List<String> descIdList) {
		this.descIdList = descIdList;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}

	public Integer getOrder_num() {
		return order_num;
	}

	public void setOrder_num(Integer order_num) {
		this.order_num = order_num;
	}

	public Integer getAttention() {
		return attention;
	}

	public void setAttention(Integer attention) {
		this.attention = attention;
	}

	public Integer getGoods_state() {
		return goods_state;
	}

	public void setGoods_state(Integer goods_state) {
		this.goods_state = goods_state;
	}

	public String getGoods_property() {
		return goods_property;
	}

	public void setGoods_property(String goods_property) {
		this.goods_property = goods_property;
	}

	public Integer getConfirm() {
		return confirm;
	}

	public void setConfirm(Integer confirm) {
		this.confirm = confirm;
	}

	public String getAttention_time() {
		return attention_time;
	}

	public void setAttention_time(String attention_time) {
		this.attention_time = attention_time;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Integer getAttentionNum() {
		return attentionNum;
	}

	public void setAttentionNum(Integer attentionNum) {
		this.attentionNum = attentionNum;
	}

	public Integer getConfirmNum() {
		return confirmNum;
	}

	public void setConfirmNum(Integer confirmNum) {
		this.confirmNum = confirmNum;
	}

	public Integer getNoconfirmNum() {
		return noconfirmNum;
	}

	public void setNoconfirmNum(Integer noconfirmNum) {
		this.noconfirmNum = noconfirmNum;
	}

	public String getWx_name() {
		return wx_name;
	}

	public void setWx_name(String wx_name) {
		this.wx_name = wx_name;
	}

	public String getWx_username() {
		return wx_username;
	}

	public void setWx_username(String wx_username) {
		this.wx_username = wx_username;
	}

	public Integer getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(Integer user_sex) {
		this.user_sex = user_sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
