package com.kkbc.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.kkbc.dao.DeviceDao;
import com.kkbc.entity.Hard;
import com.kkbc.entity.TestInfo;
import com.kkbc.entity.TestLineData;
import com.kkbc.entity.TestScoreVO;
import com.kkbc.entity.TestUser;

public class DeviceDaoImpl extends BaseDaoImpl implements DeviceDao{

	@Override
	public int saveData(TestScoreVO data) {
//		int a=(int) getSqlMapClientTemplate().insert("TestScoreVO.saveData", data);
//		int b=(int) getSqlMapClientTemplate().insert("TestScoreVO.saveToHistory", data);
//		if (a>0&&b>0) {
//			return 1;
//		}
//		return 0;
		getSqlMapClientTemplate().update("TestScoreVO.updateScoreFromNow", data);
		getSqlMapClientTemplate().insert("TestScoreVO.saveData", data);
		getSqlMapClientTemplate().insert("TestScoreVO.saveToHistory", data);
		return 1;
	}

	@Override
	public TestUser getUser(TestUser user) {
		return (TestUser) getSqlMapClientTemplate().queryForObject("TestScoreVO.getUserFromBind", user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestUser> getAllTestUser() {
		return getSqlMapClientTemplate().queryForList("TestScoreVO.getUser");
	}

	@Override
	public int saveHardInfo(Hard hard) {
		
		return (int) getSqlMapClientTemplate().insert("Hard.saveData", hard);
	}

	@Override
	public int updEle(Hard hard) {
		return (int) getSqlMapClientTemplate().update("Hard.updEle", hard);
	}

	@Override
	public Hard getByMac(String mac) {
		return (Hard) getSqlMapClientTemplate().queryForObject("Hard.getInfoByMac", mac);
	}

	@Override
	public int updateConfirmNameFlag(TestUser testUser) {
		return getSqlMapClientTemplate().update("TestScoreVO.updConfirmName", testUser);
	}

	@Override
	public int updateDeviceStatus(TestUser testUser) {
		return getSqlMapClientTemplate().update("TestScoreVO.updDeviceStatus", testUser);
	}

	@Override
	public int savePackageData(TestScoreVO data) {
//		return (int) getSqlMapClientTemplate().insert("TestScoreVO.saveToPackHistory", data);
		getSqlMapClientTemplate().insert("TestScoreVO.saveToPackHistory", data);
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TestUser> getTestUserFromBind(int testId) {
		return getSqlMapClientTemplate().queryForList("TestScoreVO.getTestUserFromBind",testId);
	}

	@Override
	public int updLastInfo(Hard hard) {
		return (int) getSqlMapClientTemplate().update("Hard.updLastInfo", hard);
	}

	@Override
	public TestUser getUserFromNow(String mac) {
		return (TestUser) getSqlMapClientTemplate().queryForObject("TestScoreVO.getUser", mac);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Hard> getTestDevice(int testId) {
		return getSqlMapClientTemplate().queryForList("Hard.getTestDevice",testId);
	}

	@Override
	public Object saveNowInfo(int testId) {
		return getSqlMapClientTemplate().insert("Hard.saveNowInfo", testId);
	}

	@Override
	public int deleteFromNow(int testId) {
		return (int) getSqlMapClientTemplate().delete("Hard.deleteFromNow", testId);
	}

	@Override
	public TestInfo getTestInfoById(int testId) {
		return (TestInfo) getSqlMapClientTemplate().queryForObject("TestScoreVO.getTestInfoById",testId);
	}

	@Transactional
	@Override
	public int changeDevice(Hard hard1, Hard hard2) {
		int hardId1=hard1.getHard_id();
		int hardId2=hard2.getHard_id();
		hard1.setHard_id(hardId2);
		hard2.setHard_id(hardId1);
		int r1=getSqlMapClientTemplate().update("Hard.updateWhenChange", hard1);
		int r2=getSqlMapClientTemplate().update("Hard.updateWhenChange", hard2);
		if (r1>0&&r2>0) {
			return 1;//交换成功
		}
		return 0;
	}

	@Override
	public int saveLineData(List<TestLineData> lineDatas) {
		getSqlMapClientTemplate().insert("TestScoreVO.saveLineData",lineDatas);
		return 1;
	}

	@Override
	public int updLastInfo(List<Hard> hards) {
//		return getSqlMapClientTemplate().u("Hard.updLastInfoByBatch", hards);
		try {
			getSqlMapClient().startBatch();
			for (Hard hard : hards) {
				if (StringUtils.isNotEmpty(hard.getMac())) {
					getSqlMapClient().update("Hard.updLastInfo", hard);
				}
			}
			getSqlMapClient().executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}

	@Override
	public Hard getById(int id) {
		return (Hard) getSqlMapClientTemplate().queryForObject("Hard.getInfoById", id);
	}
	
	

}
