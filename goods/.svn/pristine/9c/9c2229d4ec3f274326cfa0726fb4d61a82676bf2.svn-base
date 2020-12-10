package com.kkbc.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kkbc.constants.WechatConsts;
import com.kkbc.dao.GoodsDescDao;
import com.kkbc.entity.GoodsDesc;
import com.kkbc.service.GoodsDescService;
import com.kkbc.util.DateUtil;
import com.kkbc.util.QiniuUtil;
import com.kkbc.vo.CustomBindingVO;
import com.kkbc.vo.GoodsDescVO;
import com.kkbc.vo.GoodsReqParamVO;
import com.kkbc.vo.UserHeartVO;

public class GoodsDescServiceImpl implements GoodsDescService{
	
	@Resource
	private GoodsDescDao goodsDescDao;

	@Override
	public int saveInfo(GoodsDescVO info) {
		List<GoodsDesc> list=new ArrayList<GoodsDesc>();
		
		String[] shuxing=info.getShuxing();
		if (shuxing!=null&&shuxing.length>0) {
			for (String value:shuxing) {
				if (GoodsDesc.TYPE_GUONEI==Integer.valueOf(value)) {
					list.add(new GoodsDesc(info.getBrand(),info.getCategory(),info.getOther(),info.getContent(), info.getCreate_time(),info.getSmall_img1(),info.getSmall_img2(),info.getSmall_img3(),info.getSmall_img4(),info.getImg1(),info.getImg2(),info.getImg3(),info.getImg4(), info.getImg1_describe(), info.getImg2_describe(), info.getImg3_describe(), info.getImg4_describe(), info.getBase(), GoodsDesc.TYPE_GUONEI,GoodsDesc.STATE_NOPUBLISH,0,info.getGoods_property(),info.getGoods_state(),null));
				}else if (GoodsDesc.TYPE_SUQIANG==Integer.valueOf(value)) {
					list.add(new GoodsDesc(info.getBrand(),info.getCategory(),info.getOther(),info.getContent(), info.getCreate_time(),info.getSmall_img1(),info.getSmall_img2(),info.getSmall_img3(),info.getSmall_img4(),info.getImg1(),info.getImg2(),info.getImg3(),info.getImg4(), info.getImg1_describe(), info.getImg2_describe(), info.getImg3_describe(), info.getImg4_describe(), info.getBase(), GoodsDesc.TYPE_SUQIANG,GoodsDesc.STATE_NOPUBLISH,0,info.getGoods_property(),null,null));
				}else if (GoodsDesc.TYPE_MIAOSHA==Integer.valueOf(value)) {
					list.add(new GoodsDesc(info.getBrand(),info.getCategory(),info.getOther(),info.getContent(), info.getCreate_time(),info.getSmall_img1(),info.getSmall_img2(),info.getSmall_img3(),info.getSmall_img4(),info.getImg1(),info.getImg2(),info.getImg3(),info.getImg4(), info.getImg1_describe(), info.getImg2_describe(), info.getImg3_describe(), info.getImg4_describe(), info.getBase(), GoodsDesc.TYPE_MIAOSHA,GoodsDesc.STATE_NOPUBLISH,0,info.getGoods_property(),null,info.getUrl()));
				}
			}
		}else {
			list.add(new GoodsDesc(info.getBrand(),info.getCategory(),info.getOther(),info.getContent(), info.getCreate_time(),info.getSmall_img1(),info.getSmall_img2(),info.getSmall_img3(),info.getSmall_img4(),info.getImg1(),info.getImg2(),info.getImg3(),info.getImg4(), info.getImg1_describe(), info.getImg2_describe(), info.getImg3_describe(), info.getImg4_describe(), info.getBase(), null,GoodsDesc.STATE_NOPUBLISH,0,info.getGoods_property(),info.getGoods_state(),null));
		}
		
		if (list.size()>0) {
			return goodsDescDao.saveInfo(list);
		}
		
		return 0;
	}

	@Override
	public List<GoodsDescVO> getList(String menu,Integer pageNum,int[] otherNumArr,String openId,Integer goods_state,String goods_property,String brand,String category,String other) {
		int otherNum=otherNumArr[0];
		List<GoodsDescVO> list=new ArrayList<GoodsDescVO>();
		Integer startIndex=((pageNum-1)*WechatConsts.PAGE_SIZE);
		
		String publishTableName=getTableName(GoodsDescVO.TABLE_PRE, Integer.valueOf(menu), "");
		String otherpublishTableName=publishTableName+GoodsDescVO.TABLE_POS;
		
		String twoheartTableName=getTableName(UserHeartVO.TABLE_PRE, Integer.valueOf(menu), UserHeartVO.TABLE_TWODAY);
		String fiveheartTableName=getTableName(UserHeartVO.TABLE_PRE, Integer.valueOf(menu), UserHeartVO.TABLE_FIVEDAY);
		String otherheartTableName=getTableName(UserHeartVO.TABLE_PRE, Integer.valueOf(menu), UserHeartVO.TABLE_OTHER);
		if (otherNum>0) {//从other表中查
			startIndex=startIndex+otherNum;
			list=goodsDescDao.getList(otherpublishTableName, otherheartTableName,null, openId,goods_state,goods_property,brand,category,other, startIndex, WechatConsts.PAGE_SIZE);
		}else {//在初始表中查
			list=goodsDescDao.getList(publishTableName,"",null,openId,goods_state,goods_property,brand,category,other, startIndex, WechatConsts.PAGE_SIZE);
			
			//初始表的关注信息在twoday,fiveday中
			List<GoodsDescVO> twoday=new ArrayList<GoodsDescVO>();
			List<GoodsDescVO> fiveday=new ArrayList<GoodsDescVO>();
			getDayList(list, twoday, fiveday);
			if (twoday.size()>0) {
				List<GoodsDescVO> twodayAttention=goodsDescDao.getList(publishTableName,twoheartTableName,twoday,openId,goods_state,goods_property,brand,category,other, null, 0);
				setAttention(list, twodayAttention);
			}
			if (fiveday.size()>0) {
				List<GoodsDescVO> fivedayAttention=goodsDescDao.getList(publishTableName,fiveheartTableName,fiveday,openId,goods_state,goods_property,brand,category,other, null, 0);
				setAttention(list, fivedayAttention);
			}
			
			if (list!=null&&list.size()<WechatConsts.PAGE_SIZE) {//条数不足,从other中查数据补足10条
				otherNum=WechatConsts.PAGE_SIZE-list.size();//需要补足的条数
				startIndex=0;
				List<GoodsDescVO> otherList=goodsDescDao.getList(otherpublishTableName,otherheartTableName,null,openId,goods_state,goods_property,brand,category,other, startIndex, otherNum);
				list.addAll(otherList);
				
				otherNum=otherList.size();//实际补的条数
			}
		}
		
		otherNumArr[0]=otherNum;
		
		return list;
	}

	@Transactional
	@Override
	public int heart(int desc_id,String openId ,Date publishDate,String menu) {
		
		String tableName=getPublishTable(publishDate, menu);
		String heartTableName=getHeartTable(publishDate, menu,null);
		
		UserHeartVO param =new UserHeartVO(desc_id, openId, new Date(),tableName,heartTableName,UserHeartVO.ATTENTION_YES);
		UserHeartVO heartInfo=goodsDescDao.getHeartInfo(param );
		if (heartInfo==null) {//第一次关注
			goodsDescDao.saveHeartInfo(param);
			goodsDescDao.updHeart(param);
		}else {
			if (heartInfo.getAttention()!=1) {//之前关注过，重新关注
				goodsDescDao.updAttention(param);
			}
		}
		
		return 1;
	}

	@Override
	public int publish(String desc_id) {
		if (StringUtils.isNotEmpty(desc_id)) {
			String[] descIds = desc_id.split(",");
			for (String descId : descIds) {
				publish(Integer.valueOf(descId));
			}
			return 1;
		}
		return 0;
	}
	
	@Transactional
	public int publish(int desc_id) {
		GoodsDescVO info=goodsDescDao.getById(desc_id);
		if (info!=null) {
			//第一次发布时，在未发布表中将此条信息删去
			int newId=desc_id;
			if (info.getState()==GoodsDesc.STATE_OFFLINE) {//下架的信息重新发布时，要把此信息当作新的发布，此时要生成新的一条信息发布，原信息删除
				info.setHeart(null);
				//获取最新的desc_id
				newId=goodsDescDao.saveInfo(info);//在未发布表中生成新的一条信息,发布完成后此条信息删除
				info.setDesc_id(newId);
				
				delGoodsDesc(info.getType()+"", desc_id, GoodsDesc.STATE_OFFLINE);//已下架的信息重新发布时要删除原信息
			}
			
			if (info.getType()==GoodsDesc.TYPE_GUONEI) {
				info.setTableName(GoodsDescVO.TABLE_GUONEI);
			}else if (info.getType()==GoodsDesc.TYPE_MIAOSHA) {
				info.setTableName(GoodsDescVO.TABLE_MIAOSHA);
				info.setGoods_state(GoodsDescVO.GOODS_STATE_HAVE);
			}else if (info.getType()==GoodsDesc.TYPE_SUQIANG) {
				info.setTableName(GoodsDescVO.TABLE_SUQIANG);
			}
			
			info.setTableName(getTableName(GoodsDescVO.TABLE_PRE,info.getType(),""));
			info.setCreate_time(new Date());
			info.setOrder_num(goodsDescDao.getOrderNum(info.getTableName()));
			int ret=goodsDescDao.savePublishInfo(info);
			if (ret==1) {//发布成功，删除原信息
				goodsDescDao.delInfo(newId);
				return 1;
			}
		}
		return 0;
	}
	
	@Override
	public int noheart(int desc_id, String openId, Date publishDate, String menu) {
		String heartTableName=getHeartTable(publishDate, menu,null);
		
		UserHeartVO param =new UserHeartVO(desc_id, openId, new Date(),"",heartTableName,UserHeartVO.ATTENTION_NO);
		goodsDescDao.updAttention(param);
		return 1;
	}

	@Override
	public List<GoodsDesc> nopublishList(Integer pageNo,Date startTime,Date endTime,Integer type,String content) {
		return goodsDescDao.nopublishList(pageNo,startTime,endTime,type,content);
	}

	@Override
	public int down(List<GoodsReqParamVO> downInfo) {
		if (downInfo!=null && downInfo.size()>0) {
			for (GoodsReqParamVO goodsInfo : downInfo) {
				try {
					down(goodsInfo.getDesc_id(), DateUtil.stringToDate(goodsInfo.getPublishDate()), goodsInfo.getMenu());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			return 1;
		}
		return 0;
	}
	@Transactional
	public int down(int desc_id, Date publishDate, String type) {
		String tableName=getPublishTable(publishDate, type);
		String heartTableName=getHeartTable(publishDate, type,null);
		String otherHeartTableName=getTableName(UserHeartVO.TABLE_PRE, Integer.valueOf(type), UserHeartVO.TABLE_OTHER);
		
		GoodsDescVO info=goodsDescDao.getPublishById(tableName, desc_id);
		if (info!=null) {//将信息转移到原始表之后，删除发布表的信息
			info.setState(GoodsDesc.STATE_OFFLINE);
			info.setType(Integer.valueOf(type));
			info.setCreate_time(new Date());
			goodsDescDao.saveDownInfo(info);//转移到原始表
			
			int day=DateUtil.differentDaysByMillisecond(publishDate, new Date());
			if (day>=7) {//如果发布时间超过7天,则点赞信息已经在other表里,此时点赞信息不需要转移,只需删除发布信息即可
				goodsDescDao.delGoodsInfo(tableName, null, desc_id);
			}else {
				goodsDescDao.saveTranferHeartInfo(heartTableName, otherHeartTableName, desc_id);//将点赞信息转移到other表
				
				goodsDescDao.delPublishInfo(tableName,heartTableName, desc_id);//删除发布信息和点赞信息
			}
			
			return 1;
			
		}
		return 0;
	}
	
	@Override
	public List<GoodsDescVO> webList(String menu, Integer pageNum,Date startTime,Date endTime,String content,String confirm) {
		String tableName=getTableName(GoodsDescVO.TABLE_PRE, Integer.valueOf(menu), "");
		Integer startIndex=((pageNum-1)*WechatConsts.PAGE_SIZE);
		String heartTableName=getTableName(UserHeartVO.TABLE_PRE, Integer.valueOf(menu), "");
		return goodsDescDao.webList(tableName,heartTableName, startIndex,startTime,endTime,content,confirm);
	}
	
	@Transactional
	@Override
	public int changeOrder(String menu, int desc_id, Date publishDate) {
		String tableName=getPublishTable(publishDate, menu);
		GoodsDescVO info=goodsDescDao.getPublishById(tableName, desc_id);
		if (info!=null) {
			GoodsDescVO lastOrderInfo=goodsDescDao.getLastOrderInfo(tableName, info.getOrder_num(), info.getCreate_time());
			if (lastOrderInfo!=null) {
				
				int tempOrderNum=info.getOrder_num();
				info.setOrder_num(lastOrderInfo.getOrder_num());
				lastOrderInfo.setOrder_num(tempOrderNum);
				
				info.setTableName(tableName);
				goodsDescDao.updOrderNum(info);
				
				lastOrderInfo.setTableName(tableName);
				goodsDescDao.updOrderNum(lastOrderInfo);
				return 1;
				
			}else {//该条信息已经置顶了
				return 2;
			}
		}
		return 0;
	}

	@Override
	public List<CustomBindingVO> heartUser(String menu, int desc_id,
			Date publishDate, Integer pageNo,Integer state) {
		String heartTableName=getHeartTable(publishDate, menu,state);
		return goodsDescDao.getHeartUser(heartTableName, desc_id, pageNo);
	}
	
	@Override
	public int userCount(String menu, int desc_id,
			Date publishDate,Integer state) {
		String heartTableName=getHeartTable(publishDate, menu,state);
		return goodsDescDao.getUserCount(heartTableName, desc_id);
	}
	
	@Override
	public int getPageNum(String tableName,Date startTime,Date endTime,Integer type,String content) {
		int totalCount=goodsDescDao.getCount(tableName,startTime,endTime,type,content);
		int pageNum=(totalCount%WechatConsts.PAGE_SIZE)==0?(totalCount/WechatConsts.PAGE_SIZE):(totalCount/WechatConsts.PAGE_SIZE)+1;
		return pageNum;
	}

	@Override
	public String getPublishTable(String menu,String type){//type:1:近7天 2:7天以外
		String table_pos="";
		if (GoodsDescVO.TYPE_SEVEN_OTHER.equals(type)) {
			table_pos=GoodsDescVO.TABLE_POS;
		}
		return getTableName(GoodsDescVO.TABLE_PRE, Integer.valueOf(menu), table_pos);
	}

	private void setAttention(List<GoodsDescVO> list,List<GoodsDescVO> attentionInfolist){
		for (GoodsDescVO info:list) {
			for (GoodsDescVO attentionInfo : attentionInfolist) {
				if (info.getDesc_id().intValue()==attentionInfo.getDesc_id()) {
					info.setOpenId(attentionInfo.getOpenId());
					info.setAttention(attentionInfo.getAttention());
					break;
				}
			}
		}
	}
	
	private void getDayList(List<GoodsDescVO> list,List<GoodsDescVO> twoDayList,List<GoodsDescVO> fiveDayList){
		for (GoodsDescVO info:list) {
			int day=DateUtil.differentDaysByMillisecond(info.getCreate_time(), new Date());
			if (day<=1) {//发布第1~2天之内
				twoDayList.add(info);
			}else if (day<=6) {//发布第3~7天(5天)之内
				fiveDayList.add(info);
			}
		}
	}
	
	private String getHeartTable(Date publishDate,String menu,Integer state){
		String table_pos="";
		if (state!=null&&state.intValue()==GoodsDesc.STATE_OFFLINE) {//已经下架的信息
			table_pos=UserHeartVO.TABLE_OTHER;
		}else {
			int day=DateUtil.differentDaysByMillisecond(publishDate, new Date());
			if (day<=1) {//发布第1~2天之内
				table_pos=UserHeartVO.TABLE_TWODAY;
			}else if (day<=6) {//发布第3~7天(5天)之内
				table_pos=UserHeartVO.TABLE_FIVEDAY;
			}else {
				table_pos=UserHeartVO.TABLE_OTHER;
			}
		}
		
		return getTableName(UserHeartVO.TABLE_PRE, Integer.valueOf(menu), table_pos);
	}
	
	private String getPublishTable(Date publishDate,String menu){
		String table_pos="";
		int day=DateUtil.differentDaysByMillisecond(publishDate, new Date());
		if (day>=7) {
			table_pos=GoodsDescVO.TABLE_POS;
		}
		return getTableName(GoodsDescVO.TABLE_PRE, Integer.valueOf(menu), table_pos);
	}

	private String getTableName(String table_pre,int type,String table_pos){
		String table_mid="";
		if (type==GoodsDesc.TYPE_GUONEI) {
			table_mid= GoodsDescVO.TABLE_GUONEI;
		}else if (type==GoodsDesc.TYPE_MIAOSHA) {
			table_mid= GoodsDescVO.TABLE_MIAOSHA;
		}else if (type==GoodsDesc.TYPE_SUQIANG) {
			table_mid= GoodsDescVO.TABLE_SUQIANG;
		}
		return table_pre+table_mid+table_pos;
	}

	@Override
	public int webListPageNum(String menu,Date startTime,Date endTime,String content,String confirm) {
		String tableName=getTableName(GoodsDescVO.TABLE_PRE, Integer.valueOf(menu), "");
		String heartTableName=getTableName(UserHeartVO.TABLE_PRE, Integer.valueOf(menu), "");
		int totalCount=goodsDescDao.webListCount(tableName,heartTableName, startTime, endTime, content,confirm);
		int pageNum=(totalCount%WechatConsts.PAGE_SIZE)==0?(totalCount/WechatConsts.PAGE_SIZE):(totalCount/WechatConsts.PAGE_SIZE)+1;
		return pageNum;
	}

	@Override
	public List<String> getProperty(String menu) {
		String tableName=getTableName(GoodsDescVO.TABLE_PRE, Integer.valueOf(menu), "");
		return goodsDescDao.getProperty(tableName);
	}

	@Override
	public int miaoshaEnd(int desc_id, Date publishDate) {
		String tableName=getPublishTable(publishDate, GoodsDesc.TYPE_MIAOSHA+"");
		goodsDescDao.updGoodsState(tableName, desc_id, GoodsDescVO.GOODS_STATE_NO);
		return 1;
	}

	@Override
	public List<GoodsDescVO> getUserAttentionList(String openid, Integer pageNum) {
		Integer startIndex=((pageNum-1)*WechatConsts.PAGE_SIZE);
		return goodsDescDao.getUserAttentionList(openid, startIndex);
	}

	@Override
	public int userAttentionListPageNum(String openid) {
		int totalCount=goodsDescDao.getUserAttentionCount(openid);
		int pageNum=(totalCount%WechatConsts.PAGE_SIZE)==0?(totalCount/WechatConsts.PAGE_SIZE):(totalCount/WechatConsts.PAGE_SIZE)+1;
		return pageNum;
	}

	@Override
	public int confirmUser(String menu, int desc_id, String openId,
			Date publishDate,Integer state) {
		String heartTableName=getHeartTable(publishDate, menu,state);
		UserHeartVO param =new UserHeartVO(desc_id, openId, new Date(),"",heartTableName,null);
		param.setConfirm(UserHeartVO.CONFIRM_YES);
		goodsDescDao.updConfirm(param);
		return 1;
	}

	@Override
	public int updBase(String menu, int desc_id, Date publishDate, Integer base) {
		String tableName=getPublishTable(publishDate, menu);
		return goodsDescDao.updBase(tableName, desc_id, base);
	}

	@Override
	public List<String> getBrand(String menu) {
		String tableName=getTableName(GoodsDescVO.TABLE_PRE, Integer.valueOf(menu), "");
		return goodsDescDao.getBrand(tableName);
	}

	@Override
	public List<String> getCategory(String menu) {
		String tableName=getTableName(GoodsDescVO.TABLE_PRE, Integer.valueOf(menu), "");
		return goodsDescDao.getCategory(tableName);
	}

	@Override
	public List<String> getOther(String menu) {
		String tableName=getTableName(GoodsDescVO.TABLE_PRE, Integer.valueOf(menu), "");
		return goodsDescDao.getOther(tableName);
	}

	@Override
	public List<GoodsDescVO> attentionList(String menu, Integer pageNum,
			String wx_name, String confirm) {
		String tableName=getTableName(GoodsDescVO.TABLE_PRE, Integer.valueOf(menu), "");
		Integer startIndex=((pageNum-1)*WechatConsts.PAGE_SIZE);
		String heartTableName=getTableName(UserHeartVO.TABLE_PRE, Integer.valueOf(menu), "");
		return goodsDescDao.attentionList(tableName, heartTableName, startIndex, wx_name, confirm);
	}

	@Override
	public int attentionListPageNum(String menu, String wx_name,
			String confirm) {
		String tableName=getTableName(GoodsDescVO.TABLE_PRE, Integer.valueOf(menu), "");
		String heartTableName=getTableName(UserHeartVO.TABLE_PRE, Integer.valueOf(menu), "");
		int totalCount=goodsDescDao.attentionListCount(tableName, heartTableName, wx_name, confirm);
		int pageNum=(totalCount%WechatConsts.PAGE_SIZE)==0?(totalCount/WechatConsts.PAGE_SIZE):(totalCount/WechatConsts.PAGE_SIZE)+1;
		return pageNum;
	}

	@Override
	public int delGoodsDesc(String menu, int desc_id,Integer state) {
		String heartTableName=null;
		String tableName=GoodsDescVO.TABLE_NOPUBLISH;
		if (state.intValue()==GoodsDesc.STATE_OFFLINE) {
			heartTableName=getHeartTable(null, menu, state);
		}
		if (goodsDescDao.delGoodsInfo(tableName, heartTableName, desc_id)>0) {
			return 1;
		}
		return 0;
	}

	@Transactional
	@Override
	public int edit(GoodsDescVO newInfo,MultipartFile[] newImages) throws IOException {
		Integer desc_id=newInfo.getDesc_id();
		GoodsDescVO oldInfo=goodsDescDao.getById(desc_id);
		
		if (newImages != null && newImages.length > 0) {
			for (int i = 0; i < newImages.length; i++) {
				MultipartFile file=newImages[i];
				if (!file.isEmpty()) {//图片有修改
//					String fileName=file.getOriginalFilename();
					if (i==0) {
//						if (!fileName.equals(oldInfo.getImg1())) {
//							fileName=uploadNewImage(oldInfo.getImg1(), file);
//						}
						newInfo.setImg1(uploadNewImage(oldInfo.getImg1(), file));
					}else if (i==1) {
//						if (!fileName.equals(oldInfo.getImg2())) {
//							fileName=uploadNewImage(oldInfo.getImg2(), file);
//						}
						newInfo.setImg2(uploadNewImage(oldInfo.getImg2(), file));
					}else if (i==2) {
//						if (!fileName.equals(oldInfo.getImg3())) {
//							fileName=uploadNewImage(oldInfo.getImg3(), file);
//						}
						newInfo.setImg3(uploadNewImage(oldInfo.getImg3(), file));
					}else if (i==3) {
//						if (!fileName.equals(oldInfo.getImg4())) {
//							fileName=uploadNewImage(oldInfo.getImg4(), file);
//						}
						newInfo.setImg4(uploadNewImage(oldInfo.getImg4(), file));
					}
					
				}else {//图片没修改
					if (i==0) {
						newInfo.setImg1(oldInfo.getImg1());
					}else if (i==1) {
						newInfo.setImg2(oldInfo.getImg2());
					}else if (i==2) {
						newInfo.setImg3(oldInfo.getImg3());
					}else if (i==3) {
						newInfo.setImg4(oldInfo.getImg4());
					}
				}
			}
		}
		
		goodsDescDao.updDescInfo(newInfo);
		
		if (oldInfo.getState()==GoodsDesc.STATE_OFFLINE&&oldInfo.getType().intValue()!=newInfo.getType().intValue()) {//点赞信息转移
			String oldHeartTable=getHeartTable(null, oldInfo.getType()+"", GoodsDesc.STATE_OFFLINE);
			String newHeartTable=getHeartTable(null, newInfo.getType()+"", GoodsDesc.STATE_OFFLINE);
			goodsDescDao.saveTranferHeartInfo(oldHeartTable, newHeartTable, desc_id);
			goodsDescDao.delHeartInfo(oldHeartTable, desc_id);
			
		}
		
		return 1;
	}
	
	public String uploadNewImage(String oldImage,MultipartFile file) throws IOException{
		String newImage=file.getOriginalFilename();
		// 文件名+UUID
		String fileNameUUID = newImage.substring(0,newImage.lastIndexOf("."))+"_"+UUID.randomUUID().toString();
		
		BufferedImage bi =ImageIO.read(file.getInputStream());
		byte[] imageByte=file.getBytes();
		if (file.getSize()>160*1024) {//压缩图片上传(超过160KB)
			bi=Thumbnails.of(file.getInputStream()).scale(QiniuUtil.getScale(file.getSize())).asBufferedImage();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			Thumbnails.of(file.getInputStream()).scale(QiniuUtil.getScale(file.getSize())).toOutputStream(os);
			imageByte=os.toByteArray();
		}
		String uploadKey="460"+"*"+((int)((460f/bi.getWidth())*bi.getHeight()))+"_"+fileNameUUID+newImage.substring(newImage.lastIndexOf("."));
		QiniuUtil.upload(imageByte, uploadKey);
		QiniuUtil.delFile(oldImage);
		return uploadKey;
	}

}
