package com.kkbc.dao;

import java.util.Date;
import java.util.List;

import com.kkbc.entity.GoodsDesc;
import com.kkbc.vo.CustomBindingVO;
import com.kkbc.vo.GoodsDescVO;
import com.kkbc.vo.UserHeartVO;

public interface GoodsDescDao {
	int saveInfo(GoodsDescVO info);
	
	int saveInfo(List<GoodsDesc> list);
	
	GoodsDescVO getById(int desc_id);
	
	GoodsDescVO getPublishById(String tableName,int desc_id);
	
	int delInfo(int desc_id);
	
	//信息下架时，相关信息转移后删除本表数据（发布信息和关注信息）
	int delPublishInfo(String tableName,String heartTableName,int desc_id);
	
	int getOrderNum(String tableName);
	int savePublishInfo(GoodsDescVO info);
	
	List<GoodsDescVO> getList(String tableName,String heartTableName,List<GoodsDescVO> descId,String openId,Integer goods_state,String goods_property,String brand,String category,String other,Integer startIndex,int pageSize);
	
	//获取关注信息，看是否之前关注过
	UserHeartVO getHeartInfo(UserHeartVO param);
	
	//更新关注状态
	int updAttention(UserHeartVO param);
	
	//更新发布信息的点赞值
	int updHeart(UserHeartVO param);
	
	int saveHeartInfo(UserHeartVO info);
	
	List<GoodsDesc> nopublishList(Integer pageNo,Date startTime,Date endTime,Integer type,String content);
	
	//将下架的信息转存到原始表中
	int saveDownInfo(GoodsDescVO info);
	
	//下架时将点赞信息转移到other表
	int saveTranferHeartInfo(String fromTable,String toTable,int desc_id);
	
	int updOrderNum(GoodsDescVO info);
	
	List<CustomBindingVO> getHeartUser(String tableName,int desc_id,Integer pageNo);
	
	int getUserCount(String tableName,int desc_id);
	
	int getCount(String tableName,Date startTime,Date endTime,Integer type,String content);
	
	List<GoodsDescVO> webList(String tableName,String heartTableNamePre,Integer startIndex,Date startTime,Date endTime,String content,String confirm);
	int webListCount(String tableName,String heartTableNamePre,Date startTime, Date endTime, String content,String confirm);
	
	GoodsDescVO getLastOrderInfo(String tableName,Integer orderNum,Date currentPublishDate);
	
	List<String> getProperty(String tableName);
	List<String> getBrand(String tableName);
	List<String> getCategory(String tableName);
	List<String> getOther(String tableName);
	
	int updGoodsState(String tableName,int desc_id,int goodsState);
	
	//获取用户的关注列表
	List<GoodsDescVO> getUserAttentionList(String openid,Integer startIndex);
	int getUserAttentionCount(String openid);
	
	//更新确认状态
	int updConfirm(UserHeartVO param);
	
	//修改关注基数
	int updBase(String tableName,int desc_id,Integer base);
	
	List<GoodsDescVO> attentionList(String tableName,String heartTableNamePre,Integer startIndex,String wx_name,String confirm);
	int attentionListCount(String tableName,String heartTableNamePre, String wx_name,String confirm);
	
	int delGoodsInfo(String tableName,String heartTableName, int desc_id);
	
	int updDescInfo(GoodsDescVO newInfo);
	
	int delHeartInfo(String heartTableName, int desc_id);
	

}
