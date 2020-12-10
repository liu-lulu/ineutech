package com.kkbc.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kkbc.constants.WechatConsts;
import com.kkbc.dao.GoodsDescDao;
import com.kkbc.entity.GoodsDesc;
import com.kkbc.vo.CustomBindingVO;
import com.kkbc.vo.GoodsDescVO;
import com.kkbc.vo.UserHeartVO;

public class GoodsDescDaoImpl extends BaseDaoImpl implements GoodsDescDao{

	@Override
	public int saveInfo(GoodsDescVO info) {
		return (int) getSqlMapClientTemplate().insert("GoodsDesc.save", info);
	}

	@Override
	public List<GoodsDescVO> getList(String tableName,String heartTableName,List<GoodsDescVO> descId,String openId,Integer goods_state,String goods_property,String brand,String category,String other,Integer startIndex,int pageSize) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("heartTableName", heartTableName);
		param.put("descIdList", descId);
		param.put("openId", openId);
		param.put("goods_state", goods_state);
		param.put("goods_property", goods_property);
		param.put("brand", brand);
		param.put("category", category);
		param.put("other", other);
		
		param.put("startIndex", startIndex);
		param.put("pageSize", pageSize);
		
		@SuppressWarnings("unchecked")
		List<GoodsDescVO> list=getSqlMapClientTemplate().queryForList("GoodsDesc.getInfo",param);
		
		return list;
	}

	@Override
	public int updHeart(UserHeartVO info) {
		return getSqlMapClientTemplate().update("GoodsDesc.updHeart", info);
	}

	@Override
	public int saveHeartInfo(UserHeartVO info) {
		getSqlMapClientTemplate().insert("UserHeart.saveHeartInfo", info);
		return 1;
	}

	@Override
	public int saveInfo(List<GoodsDesc> list) {
		getSqlMapClientTemplate().insert("GoodsDesc.saveDatas", list);
		return 1;
	}

	@Override
	public GoodsDescVO getById(int desc_id) {
		return (GoodsDescVO) getSqlMapClientTemplate().queryForObject("GoodsDesc.getInfoById", desc_id);
	}

	@Override
	public int delInfo(int desc_id) {
		return getSqlMapClientTemplate().delete("GoodsDesc.delInfo", desc_id);
	}

	@Override
	public int savePublishInfo(GoodsDescVO info) {
		getSqlMapClientTemplate().insert("GoodsDesc.savePublishInfo", info);
		return 1;
	}
	
	@Override
	public int getOrderNum(String tableName) {
		return (int) getSqlMapClientTemplate().queryForObject("GoodsDesc.getOrderNum", tableName);
	}

	@Override
	public UserHeartVO getHeartInfo(UserHeartVO param) {
		return (UserHeartVO) getSqlMapClientTemplate().queryForObject("UserHeart.getInfo", param);
	}

	@Override
	public int updAttention(UserHeartVO param) {
		getSqlMapClientTemplate().update("UserHeart.updAttention", param);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsDesc> nopublishList(Integer pageNo,Date startTime,Date endTime,Integer type,String content) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("startIndex", ((pageNo-1)*WechatConsts.PAGE_SIZE));
		param.put("pageSize", WechatConsts.PAGE_SIZE);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("type", type);
		param.put("content", content);
		return getSqlMapClientTemplate().queryForList("GoodsDesc.nopublish", param);
	}

	@Override
	public GoodsDescVO getPublishById(String tableName,int desc_id) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("desc_id", desc_id);
		return (GoodsDescVO) getSqlMapClientTemplate().queryForObject("GoodsDesc.getPublishInfoById", param);
	}

	@Override
	public int saveDownInfo(GoodsDescVO info) {
		Integer ret=(Integer) getSqlMapClientTemplate().insert("GoodsDesc.saveById", info);
		return 1;
	}

	@Override
	public int delPublishInfo(String tableName,String heartTableName, int desc_id) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("heartTableName", heartTableName);
		param.put("desc_id", desc_id);
		Integer ret=getSqlMapClientTemplate().delete("GoodsDesc.delPublishInfo", param);
		return 1;
	}

	@Override
	public int saveTranferHeartInfo(String fromTable, String toTable,
			int desc_id) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("fromTable", fromTable);
		param.put("toTable", toTable);
		param.put("desc_id", desc_id);
		Integer ret=(Integer) getSqlMapClientTemplate().insert("UserHeart.saveTranfer", param);
		return 1;
	}

	@Override
	public int updOrderNum(GoodsDescVO info) {
		getSqlMapClientTemplate().update("GoodsDesc.updOrderNum", info);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomBindingVO> getHeartUser(String tableName, int desc_id,
			Integer pageNo) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("heartTableName", tableName);
		param.put("desc_id", desc_id);
		param.put("startIndex", ((pageNo-1)*WechatConsts.PAGE_SIZE));
		param.put("pageSize", WechatConsts.PAGE_SIZE);
		return getSqlMapClientTemplate().queryForList("UserHeart.getUserlist", param);
	}

	@Override
	public int getCount(String tableName,Date startTime,Date endTime,Integer type,String content) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("type", type);
		param.put("content", content);
		return (int) getSqlMapClientTemplate().queryForObject("GoodsDesc.count", param);
	}

	@Override
	public int getUserCount(String tableName, int desc_id) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("heartTableName", tableName);
		param.put("desc_id", desc_id);
		return (int) getSqlMapClientTemplate().queryForObject("UserHeart.getUserCount", param);
	}

	@Override
	public List<GoodsDescVO> webList(String tableName,String heartTableNamePre, Integer startIndex,
			Date startTime, Date endTime,String content,String confirm) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("startIndex", startIndex);
		param.put("pageSize", WechatConsts.PAGE_SIZE);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("content", content);
		param.put("confirm", confirm);
		param.put("heartTableNamePre", heartTableNamePre);
		@SuppressWarnings("unchecked")
		List<GoodsDescVO> list=getSqlMapClientTemplate().queryForList("GoodsDesc.weblist1",param);
		
		return list;
	}

	@Override
	public int webListCount(String tableName,String heartTableNamePre,Date startTime, Date endTime, String content,String confirm) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("content", content);
		param.put("confirm", confirm);
		param.put("heartTableNamePre", heartTableNamePre);
		return (int) getSqlMapClientTemplate().queryForObject("GoodsDesc.weblistCount",param);
	}

	@Override
	public GoodsDescVO getLastOrderInfo(String tableName, Integer orderNum,
			Date currentPublishDate) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("order_num", orderNum);
		param.put("create_time", currentPublishDate);
		return (GoodsDescVO) getSqlMapClientTemplate().queryForObject("GoodsDesc.getLastOrder",param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getProperty(String tableName) {
		return getSqlMapClientTemplate().queryForList("GoodsDesc.getproperty", tableName);
	}

	@Override
	public int updGoodsState(String tableName, int desc_id, int goodsState) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("desc_id", desc_id);
		param.put("goods_state", goodsState);
		return getSqlMapClientTemplate().update("GoodsDesc.updGoodsState", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsDescVO> getUserAttentionList(String openid,
			Integer startIndex) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("openId", openid);
		param.put("startIndex", startIndex);
		param.put("pageSize", WechatConsts.PAGE_SIZE);
		return getSqlMapClientTemplate().queryForList("UserHeart.userAttention", param);
	}

	@Override
	public int getUserAttentionCount(String openid) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("openId", openid);
		
		return (int) getSqlMapClientTemplate().queryForObject("UserHeart.userAttentionCount",param);
	}

	@Override
	public int updConfirm(UserHeartVO param) {
		getSqlMapClientTemplate().update("UserHeart.updConfirm", param);
		return 1;
	}

	@Override
	public int updBase(String tableName, int desc_id, Integer base) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("desc_id", desc_id);
		param.put("base", base);
		getSqlMapClientTemplate().update("GoodsDesc.updBase", param);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getBrand(String tableName) {
		return getSqlMapClientTemplate().queryForList("GoodsDesc.getbrand", tableName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCategory(String tableName) {
		return getSqlMapClientTemplate().queryForList("GoodsDesc.getcategory", tableName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getOther(String tableName) {
		return getSqlMapClientTemplate().queryForList("GoodsDesc.getother", tableName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsDescVO> attentionList(String tableName,
			String heartTableNamePre, Integer startIndex, String wx_name,
			String confirm) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("startIndex", startIndex);
		param.put("pageSize", WechatConsts.PAGE_SIZE);
		param.put("wx_name", wx_name);
		param.put("confirm", confirm);
		param.put("heartTableNamePre", heartTableNamePre);
		return getSqlMapClientTemplate().queryForList("UserHeart.attentionList",param);
	}

	@Override
	public int attentionListCount(String tableName, String heartTableNamePre,
			String wx_name, String confirm) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("wx_name", wx_name);
		param.put("confirm", confirm);
		param.put("heartTableNamePre", heartTableNamePre);
		return (int) getSqlMapClientTemplate().queryForObject("UserHeart.attentionListCount",param);
	}

	@Override
	public int delGoodsInfo(String tableName, String heartTableName, int desc_id) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("tableName", tableName);
		param.put("heartTableName", heartTableName);
		param.put("desc_id", desc_id);
		return (int) getSqlMapClientTemplate().delete("GoodsDesc.delGoodsInfo",param);
	}

	@Override
	public int updDescInfo(GoodsDescVO newInfo) {
		getSqlMapClientTemplate().update("GoodsDesc.updDescInfo", newInfo);
		return 1;
	}

	@Override
	public int delHeartInfo(String heartTableName, int desc_id) {
		Map<String, Object> param= new HashMap<String, Object>();
		param.put("heartTableName", heartTableName);
		param.put("desc_id", desc_id);
		return (int) getSqlMapClientTemplate().delete("UserHeart.delInfo",param);
	}


}
