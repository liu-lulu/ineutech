package com.psylife.dao;

import java.util.List;

import net.sf.json.JSONArray;

import com.psylife.entity.TyrePattern;
import com.psylife.entity.TyrePattern2;
import com.psylife.vo.TreadPattern;

 
/**
 * 轮胎花纹
 * @author xu
 *
 */
public interface TyrePatternDao extends BaseDao {

	/**
	 * 录入轮胎花纹深度
	 * @param tyrePattern
	 * @return
	 */
	int saveTyrePattern(TyrePattern tyrePattern);

	/**
	 * 批量录入轮胎花纹深度及其他信息
	 * @param list
	 * @param user_id
	 * @param flag 是否是序号，还是A1A2....
	 * @return
	 */
	JSONArray saveTyrePatternList(List<TyrePattern2> list, Integer user_id,int flag);

	/**
	 * 保存花纹信息及检测项
	 * @param tyrePattern2
	 * @param user_id
	 * @param flag
	 * @param tyre_id
	 * @param item
	 * @param remark
	 * @param repaircontent 修补详情(检测项中含"修补"时的详情)
	 * @param mabiao 码表数
	 * @return
	 */
	int saveTyrePatternItem(TyrePattern2 tyrePattern2, Integer user_id,
			int flag, String tyre_id, String item, String remark,
			String repaircontent, Double mabiao);	
	
	/**
	 * 花纹尺上传数据更新至轮胎
	 * @param patternList
	 * @return
	 */
	JSONArray saveTyrePatternListByTool(List<TreadPattern> patternList);
	
	//胎工端更新码表数
	int mabiaoWork(Integer user_id,Double mabiao,String trucksId);
}
