package com.kkbc.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kkbc.entity.GoodsDesc;
import com.kkbc.vo.CustomBindingVO;
import com.kkbc.vo.GoodsDescVO;
import com.kkbc.vo.GoodsReqParamVO;

public interface GoodsDescService {
	int saveInfo(GoodsDescVO info);
	
	int publish(String desc_id);
	
	List<GoodsDescVO> getList(String menu,Integer pageNum,int[] otherNum,String openId,Integer goods_state,String goods_property,String brand,String category,String other);
	
	int heart(int desc_id,String openId ,Date publishDate,String menu);
	
	int noheart(int desc_id,String openId ,Date publishDate,String menu);
	
	List<GoodsDesc> nopublishList(Integer pageNo,Date startTime,Date endTime,Integer type,String title);
	
	int down(List<GoodsReqParamVO> downInfo);
	
	List<GoodsDescVO> webList(String menu,Integer pageNum,Date startTime,Date endTime,String content,String confirm);
	
	int changeOrder(String menu, int desc_id, Date publishDate);
	
	List<CustomBindingVO> heartUser(String menu,int desc_id,Date publishDate,Integer pageNo,Integer state);
	
	int userCount(String menu, int desc_id,Date publishDate,Integer state);
	
	int getPageNum(String tableName,Date startTime,Date endTime,Integer type,String title);
	
	String getPublishTable(String menu,String type);
	
	int webListPageNum(String menu,Date startTime,Date endTime,String content,String confirm);
	
	List<String> getProperty(String menu);
	List<String> getBrand(String menu);
	List<String> getCategory(String menu);
	List<String> getOther(String menu);
	
	int miaoshaEnd(int desc_id,Date publishDate);
	
	List<GoodsDescVO> getUserAttentionList(String openid,Integer pageNum);
	int userAttentionListPageNum(String openid);
	
	int confirmUser(String menu, int desc_id,String openId, Date publishDate,Integer state);
	
	int updBase(String menu,int desc_id,Date publishDate,Integer base);
	
	List<GoodsDescVO> attentionList(String menu,Integer pageNum,String wx_name,String confirm);
	int attentionListPageNum(String menu,String wx_name,String confirm);
	
	int delGoodsDesc(String menu, int desc_id,Integer state);
	
	int edit(GoodsDescVO newInfo,MultipartFile[] newImages) throws IOException ;
}
