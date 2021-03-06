package com.ineutech.service;

import java.util.List;

import cn.ineutech.tpms.tcp.vo.Session;

import com.ineutech.entity.DeviceData;
import com.ineutech.entity.Hard;
import com.ineutech.entity.TestInfo;
import com.ineutech.entity.TestScorePackage;
import com.ineutech.entity.TestScoreVO;
import com.ineutech.entity.TestUser;

/**
 * 
 * @name: DeviceService 
 * @description: 数据库操作服务
 * @date 2018年2月1日 下午5:30:49
 * @author liululu
 */
public interface DeviceService {
	
	/**
	 * 获取测试基本信息
	 * @param testId 测试id
	 * @return
	 */
	TestInfo getTestInfoById(int testId);
	
	/**
	 * 保存打分数据
	 * @param testInfo 测试信息
	 * @param session 拨盘
	 * @param data 人员的打分数据
	 * @return
	 */
	int saveData(TestInfo testInfo, Session session, TestScoreVO data);
	
	/**
	 * 保存脑电上送数据
	 * @param session 脑电信息
	 * @param data 脑电数据
	 * @return
	 */
	long saveBrainData(Session session,DeviceData data);
	
	/**
	 * 获取用户基本信息
	 * @param user 用户参数:测试id 座位号
	 * @return
	 */
	TestUser getUser(TestUser user);
	
	/**
	 * 获取该测试中设备的绑定用户
	 * @param testId 测试id
	 * @param mac 设备mac
	 * @return
	 */
	TestUser getUser(int testId,String mac);
	
	/**
	 * 测试过程中获取设备绑定的用户
	 * @param mac 设备mac
	 * @return
	 */
	TestUser getUserFromNow(String mac);
	
	/**
	 * 保存硬件设备信息
	 * @param hard 设备信息
	 * @return
	 */
	int saveHardInfo(Hard hard);
	
	/**
	 * 更新脑电电量
	 * @param hard 脑电信息
	 * @return
	 */
	int updEle(Hard hard);
	
	/**
	 * 根据设备mac获取设备信息
	 * @param mac
	 * @return
	 */
	Hard getByMac(String mac);
	
	/**
	 * 更新测试人员的设备状态
	 * @param testUser 用户信息:用户id 设备状态
	 * @return
	 */
	int updateDeviceStatus(TestUser testUser);
	
	/**
	 * 存储拨盘结束测试时上传的本地的大包数据
	 * @param data 数据
	 * @return
	 */
	int savePackageData(List<TestScorePackage> data);
	
	/**
	 * 获取测试人员
	 * @param testId 测试id
	 * @return
	 */
	List<TestUser> getTestUserFromBind(int testId);
	
	/**
	 * 获取测试设备
	 * @param testId 测试id
	 * @return
	 */
	List<Hard> getTestDevice(int testId);
	
	/**
	 * 当前测试信息添加到test_now中
	 * @param testId 测试id
	 * @return
	 */
	Object saveNowInfo(int testId);
	
	/**
	 * now表中更新设备状态和score
	 * @param data 数据
	 * @return
	 */
	int updScoreAndStatus(TestScoreVO data);
	
	/**
	 * 拨盘测试完成后统计男+男-女+女-的平均分
	 * @return
	 */
	int saveDialTotalScore();
	
	/**
	 * 测试过程中实时更新脑电数据
	 * @param data
	 * @return
	 */
	int updBrainInNow(DeviceData data);
	
	/**
	 * 获取用户绑定的脑电
	 * @param humanId 人员id
	 * @return
	 */
	Hard getUserBrain(int humanId);
	
	/**
	 * 用户绑定脑电
	 * @param humanId 人员id
	 * @param hard 脑电
	 * @return
	 */
	Integer userBindBrain(int humanId, Hard hard);
	
	/**
	 * 用户解除脑电绑定
	 * @param humanId 人员id
	 * @return
	 */
	int removeBind(int humanId);
	
	/**
	 * 获取编号对应的脑电设备
	 * @param no 脑电编号
	 * @return
	 */
	Hard getBrainByNo(int no);
	
}
