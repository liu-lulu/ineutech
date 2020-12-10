package com.psylife.dao;

import java.sql.Connection;
import java.util.List;

import net.sf.json.JSONObject;

import com.psylife.entity.Trucks;
import com.psylife.entity.TyreBase;
import com.psylife.vo.TyreByAdminVO;
import com.psylife.vo.TyreCountVO;
import com.psylife.vo.TyreRemarkVO;
import com.psylife.vo.TyreVO;

 
/**
 * 轮胎基础
 * @author xu
 *
 */
public interface TyreBaseDao extends BaseDao {

	/**
	 * 批量保存轮胎基本信息
	 * @param tyreBases
	 * @param user_id
	 * @param isWorkOrder 是否需要产生工单
	 * @return
	 */
	int saveByList(List<TyreBase> tyreBases, Integer user_id,boolean isWorkOrder);
	
	/**
	 * 批量保存轮胎基本信息
	 * @param tyreBases
	 * @param user_id
	 * @param isWorkOrder 是否需要产生工单
	 * @param connection
	 * @return
	 */
	int saveByList(List<TyreBase> tyreBases,Trucks trucks, Integer user_id,Connection connection);

	/**
	 * 轮胎详情  根据轮胎号或车牌胎位置
	 * @param user_id 用户id
	 * @param tyre_id 轮胎号
	 * @param trucks_id 车牌
	 * @param tyre_where 位置
	 * @return
	 */
	TyreVO tyreDetial(Integer user_id, String tyre_id, String trucks_id,
			String tyre_where);

	/**
	 * 胎装载
	 * @param trucks_id 车牌号
	 * @param tyre_id 胎号
	 * @param tyre_where 位置
	 * @param user_id 用户id
	 * @param mabiao 码表数
	 * @return
	 */
	int tyreToTrucks(String trucks_id, String tyre_id, String tyre_where,
			Integer user_id,Double mabiao);

	/**
	 * 胎卸下
	 * @param trucks_id 车牌号
	 * @param tyre_id 胎号
	 * @param tyre_where 位置
	 * @param user_id 用户id
	 * @param mabiao 码表数
	 * @return
	 */
	int tyreDown(String trucks_id, String tyre_id, String tyre_where,
			Integer user_id,Double mabiao);

	/**
	 * 轮胎交换
	 * @param tyre_id1
	 * @param tyre_id2
	 * @param user_id
	 * @param mabiao1  胎号1的码表数
	 * @param mabiao2  胎号2的码表数
	 * @return
	 */
	int tyreExchange(String tyre_id1, String tyre_id2, Integer user_id,Double mabiao1,Double mabiao2);

	/**
	 * 胎推荐
	 * @param id 公司id
	 * @return
	 */
	List<JSONObject> tuiJian(int id);

	/**
	 * 根据用户获取胎汇总列表
	 * @param user_id
	 * @param tyre_brand 品牌
	 * @param tyre_type1 规格
	 * @param tyre_type3 花纹类型
	 * @param tyre_health 健康值 1是正常,0是不正常
	 * @param column 排序列
	 * @param order 排列顺序
	 * @return
	 */
	List<TyreCountVO> countTyreInfo(Integer user_id, String tyre_brand,
			String tyre_type1, String tyre_type3, Integer tyre_health,String column,String order);

	/**
	 * 根据用户品牌规格花纹获取胎列表
	 * @param pagenum
	 * @param user_id 用户id
	 * @param tyre_brand 品牌
	 * @param tyre_type1 规格
	 * @param tyre_type2 花纹代码
	 * @param tyre_type3 花纹
	 * @param tyre_flag 状态   1--装载，0--卸下
	 * @param tyre_health 健康值 1是正常,0是不正常
	 * @param keyWord 关键字
	 * @return
	 */
	List<TyreVO> getTyreList(int pagenum, Integer user_id, String tyre_brand,
			String tyre_type1, String tyre_type2, String tyre_type3,
			Integer tyre_flag, Integer tyre_health, String keyWord);

	/**
	 * 轮胎详情
	 * @param tyre_id
	 * @return
	 */
	TyreByAdminVO tyreDetialByAdmin(String tyre_id);

	/**
	 * 根据关键字获取胎列表
	 * @param pagenum
	 * @param user_id
	 * @param keyWord
	 * @return
	 */
	List<TyreVO> searchByKeyWord(int pagenum, Integer user_id, String keyWord);

	/**
	 * 小贴士列表-----废弃,已不用
	 * @param user_id
	 * @return
	 */
	List<TyreRemarkVO> tyreTips(Integer user_id);

	/**
	 * 胎,司机自行修补
	 * @param tyre_id
	 * @param user_id
	 * @return
	 */
	int tyreByDriverXiuBu(String tyre_id, Integer user_id);

	/**
	 * 根据关键字等获取胎库存列表
	 * @param pagenum
	 * @param user_id
	 * @param keyWord
	 * @param tyre_brand 品牌
	 * @param tyre_type1 规格
	 * @param tyre_type3 花纹类型
	 * @param state
	 * @param tyre_flag
	 * @param column 排序列
	 * @param order 排列顺序
	 * @return
	 */
	List<TyreVO> searchByKucun(int pagenum, Integer user_id, String keyWord, String tyre_brand,
			String tyre_type1, String tyre_type3,
			Integer state, Integer tyre_flag,String column,String order);

	/**
	 * 温馨提示列表
	 * @param user_id
	 * @param pagenum
	 * @return
	 */
	List<TyreRemarkVO> tyreTips(Integer user_id,int pagenum);

		
	
}
