package com.psylife.dao;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.psylife.entity.Trucks;
import com.psylife.entity.TyreBase;
import com.psylife.util.page.ListInfo;
import com.psylife.util.push.Message;
import com.psylife.vo.TrucksByAdminVO;
import com.psylife.vo.TrucksVO;
import com.psylife.vo.TyreVO;
import com.psylife.vo.WarnMsgVO;
import com.psylife.vo.web.TrucksWatchVO;

/**
 * 车
 * @author xu
 *
 */
public interface TrucksDao {
	
	static final Logger logger = LoggerFactory.getLogger(TrucksDao.class);

	/**
	 * 根据用户获取车辆列表
	 * @param pagenum
	 * @param user_id
	 * @param trucks_flag 行使状态
	 * @param trucks_health 1是健康其他为不健康
	 * @param trucks_type 类型--主车/挂车
	 * @param keyWord 车牌
	 * @param transport_type 运输类型,分别：危险品、快递、公交、冷链、客运、其他
	 * @param column 排序列
	 * @param order 排列顺序
	 * @return
	 */
	List<TrucksVO> searchByList(int pagenum, Integer user_id,
			Integer trucks_flag, Integer trucks_health, String trucks_type,
			String keyWord, String transport_type,String column,String order);

	/**
	 * 根据车牌号获取车信息
	 * @param trucks_id 车牌号
	 * @return
	 */
	TrucksByAdminVO getByTrucks_id(String trucks_id);

	/**
	 * 花纹深度测试,车列表
	 * @param company_id
	 * @return
	 */
	List<Trucks> trucksListByPattern(int company_id);

	/**
	 * 
	 * 车辆监控列表
	 * @param company_id
	 * @param keyWord
	 * @param currentPageNO
	 * @param pageSize
	 * @return
	 */
	ListInfo<TrucksWatchVO> searchByWatchlist(Integer company_id,
			String keyWord, int currentPageNO, int pageSize);

	/**
	 * 绑定查找
	 * @param company_id
	 * @param keyWord
	 * @param currentPageNO
	 * @param pageSize
	 * @return
	 */
	ListInfo<TrucksWatchVO> searchBylistBind(Integer company_id,
			String keyWord, int currentPageNO, int pageSize);
	
	/**
	 * 车辆绑定dtu提交
	 * @param trucks_id 车牌号
	 * @param dtu_id dtu id
	 * @param phone dtu手机号
	 * @return
	 */
	boolean trucksDtuBind(String trucks_id, String dtu_id,String phone);

	/**
	 * 车辆解除绑定dtu提交
	 * @param dtu_id
	 * @return
	 */
	boolean removeBind(String dtu_id);

	/**
	 * 更新车码表数
	 * @param trucks_id
	 * @param mabiao
	 * @param connection
	 * @return
	 */
	int updateTrucksMabiao(String trucks_id, Double mabiao,
			Connection connection);

	/**
	 * 更新车码表数
	 * @param trucks_id
	 * @param user_id
	 * @param mabiao
	 * @return
	 */
	int goUpdateTrucksMabiao(String trucks_id, Integer user_id, Double mabiao);
	
	/**
	 * 车辆信息入库
	 * @param trucks 车辆信息
	 * @param tyres 车辆的轮胎信息
	 * @param userId
	 * @return
	 */
	int inTruckInfo(Trucks trucks, List<TyreBase> tyres,Integer userId);

	/**
	 * 更新车的健康度
	 * @param connection
	 * @param trucksId 车牌号
	 * @param flag 1:装载  2:卸下 3:交换 4:轮胎在车上,只是检测了花纹深度
	 * @param tyreInfo 轮胎信息
	 * @param tyre_where 轮位
	 * @return
	 */
	public int updateTrucksHealth(Connection connection,String trucksId,int flag,TyreVO tyreInfo,String tyre_where);
	
	/**
	 * 轮胎交换时，更新车的健康度
	 * @param tyre_id1
	 * @param tyre_id2
	 * @param user_id
	 * @return
	 */
	public int updateTrucksHealthWhenTyreChange(String tyre_id1,String tyre_id2);
	
	/**
	 * 驾驶甩挂时，更新主车的健康度
	 * @param trucks_id 主车车牌号
	 * @param onlyZhuche true:没有挂车,健康度只根据A,B轮位计算 false:换了挂车,健康度根据所有轮位计算
	 * @param guacheId 挂车车牌号
	 * @return
	 */
	public int updateTrucksHealthWhenDriving(String trucks_id,boolean onlyZhuche,String guacheId,Connection conn);
	
	/**
	 * 获取推送的报警信息
	 * @param company_id
	 * @param keyWord
	 * @param currentPageNO
	 * @param pageSize
	 * @return
	 */
	ListInfo<WarnMsgVO> searchWarnMsglist(Integer company_id,
			String keyWord, int currentPageNO, int pageSize);
	
	/**
	 * APP端获取报警信息
	 * @param user_id
	 * @param trucksId
	 * @param state 报警信息状态:已读,未读
	 * @param pagenum
	 * @return
	 */
	List<Message> warnMsgListByApp(Integer user_id,String trucksId,Integer state,int pagenum);

}
