package com.kkbc.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kkbc.cons.Constans;
import com.kkbc.dao.UserDao;
import com.kkbc.entity.ConvertHistory;
import com.kkbc.entity.PrizeHistory;
import com.kkbc.entity.TransferHistory;
import com.kkbc.entity.User;
import com.kkbc.util.page.ListInfo;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {
	static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	@Override
	public User login(String loginName, String pwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loginName", loginName);
		map.put("pwd", pwd);
		map.put("status", Constans.STATUS_NORMAL);
		User user = (User) getSqlMapClientTemplate().queryForObject("User.login", map);
		
		return user;
	}
	
	@Override
	public User valiLoginName(String loginName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("loginName", loginName);
		map.put("status", Constans.STATUS_NORMAL);
		User user = (User) getSqlMapClientTemplate().queryForObject("User.valiLoginName", map);
		
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserByUserNameAndArea(String userName, String area) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_name", userName);
		map.put("area", area);
		return getSqlMapClientTemplate().queryForList("User.getSonUser", map);
	}

	@Override
	public List<User> getAllSon(String userName) {
		List<User> users=new ArrayList<User>();
		return getAllSonList(users, userName);
//		return null;
	}
	
	private List<User> getAllSonList(List<User> users,String userName){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_name", userName);
		@SuppressWarnings("unchecked")
		List<User> sons = getSqlMapClientTemplate().queryForList("User.getSonUser", map);
		for (User user : sons) {
			users.add(user);
			getAllSonList(users, user.getUser_name());
		}
		return users;
	}
	
	/**
	 * 获取userName下截至到layer层的所有子节点
	 * @param userName
	 * @param layer
	 * @return
	 */
	public List<User> getAllSon_toLayer(String userName,int layer) {
		List<User> users=new ArrayList<User>();
		return getAllSonList_toLayer(users, userName,layer);
//		return null;
	}
	
	private List<User> getAllSonList_toLayer(List<User> users,String userName,int layer){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_name", userName);
		@SuppressWarnings("unchecked")
		List<User> sons = getSqlMapClientTemplate().queryForList("User.getSonUser", map);
		for (User user : sons) {
			users.add(user);
		}
		layer--;
		if (layer!=0) {
			for (User user : sons) {
				getAllSonList(users, user.getUser_name());
			}
		}
		
		return users;
	}

	@Override
	public User getByPwd2(int userId, String pwd2) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_id", userId);
		map.put("pwd2", pwd2);
		return (User) getSqlMapClientTemplate().queryForObject("User.pwd2Check", map);
	}

	@Override
	public int coinConvert(String user_name, float convertMoney) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_name", user_name);
		map.put("money", convertMoney);
		int a=getSqlMapClientTemplate().update("User.coinConvert", map);
		if (a>0) {
			logger.info("奖金币转换成报单币成功:"+user_name);
			ConvertHistory history=new ConvertHistory();
			history.setCreate_time(new Date());
			history.setMoney(convertMoney);
			history.setType(ConvertHistory.TYPE);
			history.setUser(user_name);
			getSqlMapClientTemplate().insert("User.saveConvertHistory", history);
			return 1;
		}
		return 0;
	}

	@Override
	public ListInfo<ConvertHistory> convertHistories(String user_name,
			int currentPageNO, int pageSize) {
		ListInfo<ConvertHistory> listInfo = new ListInfo<ConvertHistory>(currentPageNO, pageSize);
		
		@SuppressWarnings("unchecked")
		List<ConvertHistory> histories=getSqlMapClientTemplate().queryForList("User.convertRecord",user_name, (currentPageNO-1)*pageSize, pageSize);
		
		listInfo.setCurrentList(histories);
		listInfo.setSizeOfTotalList(0);
		
		@SuppressWarnings("unchecked")
		List<ConvertHistory> totalHistories=getSqlMapClientTemplate().queryForList("User.convertRecord",user_name);
		if (totalHistories!=null) {
			listInfo.setSizeOfTotalList(totalHistories.size());
		}
		logger.info("币种转换记录获取成功:"+user_name);
		return listInfo;
	}

	@Override
	public int updPass(int userid, String type, String newPassword) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_id", userid);
		map.put("type", type);
		map.put("password", newPassword);
		return getSqlMapClientTemplate().update("User.updPass", map);
	}

	@Override
	public int updInfo(User userinfo) {
		
		return getSqlMapClientTemplate().update("User.updInfo", userinfo);
	}

	@Transactional
	@Override
	public int registerUser(User userInfo) {
		int result=(int) getSqlMapClientTemplate().insert("User.register", userInfo);
		if (result>0) {
			Date now=new Date();
			PrizeHistory history=new PrizeHistory();
			history.setCreateTime(now);
			history.setTrigger_user_name(userInfo.getUser_name());
			history.setType(PrizeHistory.ADD_TYPE);
			history.setUser_name(userInfo.getReferral());
			history.setPointPrize(PrizeHistory.POINT_PRIZE);//点碰奖
			
			float addMoney=PrizeHistory.POINT_PRIZE*0.9f;
			
//			if (getAllSon(userInfo.getReferral()).size()%2==0) {
//				history.setAmountPrize(PrizeHistory.AMOUNT_PRIZE);//量碰奖
//			}
//			
			int layer=getNumByLayer(userInfo.getReferral(), userInfo.getUser_name());
			if (getAllSon_toLayer(userInfo.getReferral(),layer).size()==2*((1-Math.pow(2, layer))/(1-2))) {//利用等比公式求和
				history.setAmountPrize(PrizeHistory.LAYER_PRIZE);//层碰奖
				addMoney+=PrizeHistory.LAYER_PRIZE*0.9;
			}
			int historyResult=(int) getSqlMapClientTemplate().insert("User.savePrizeHistory", history);
			
			User refferUser=new User();
			refferUser.setUser_name(userInfo.getReferral());
			refferUser.setAwardCoins(addMoney);
			int a=(int) getSqlMapClientTemplate().update("User.uptAwardCoins", refferUser);
			
			if (a>0&&historyResult>0) {
				return 1;
			}else {
				return 0;
			}
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListInfo<User> getUserByStatus(String referral, String status,int currentPageNO, int pageSize) {
//		int layer=getNumByLayer(referral, "9882226");
//		System.out.println("layer----"+layer);
		ListInfo<User> listInfo = new ListInfo<User>(currentPageNO, pageSize);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_name", referral);
		map.put("status", status);
		
		List<User> users=getSqlMapClientTemplate().queryForList("User.getUser", map, (currentPageNO-1)*pageSize, pageSize);
		listInfo.setCurrentList(users);
		listInfo.setSizeOfTotalList(0);
		
		List<User> totalusers=getSqlMapClientTemplate().queryForList("User.getUser", map);
		if (totalusers!=null) {
			listInfo.setSizeOfTotalList(totalusers.size());
		}
		
		return listInfo;
	}

	@Transactional
	@Override
	public int transfer(String from_user_name, String to_user_name,
			String coin_type, float money) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("from_user_name", from_user_name);
		map.put("to_user_name", to_user_name);
		map.put("coin_type", coin_type);
		map.put("money", money);
		int from=getSqlMapClientTemplate().update("User.transferFrom", map);
		if (from==1) {
			int to=getSqlMapClientTemplate().update("User.transferTo", map);
			if (to==1) {
				TransferHistory history=new TransferHistory();
				history.setCoin_type(coin_type);
				history.setCreate_time(new Date());
				history.setFrom(from_user_name);
				history.setMoney(money);
				history.setTo(to_user_name);
				history.setType(TransferHistory.OUT);
				history.setRemark("");
				getSqlMapClientTemplate().insert("User.saveTransferHistory", history);
				logger.info("转账成功:"+from_user_name);
				return 1;
			}
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListInfo<TransferHistory> transferHistories(String userName,
			int currentPageNO, int pageSize) {
		ListInfo<TransferHistory> listInfo = new ListInfo<TransferHistory>(currentPageNO, pageSize);
		
		List<TransferHistory> histories=getSqlMapClientTemplate().queryForList("User.transferRecord", userName, (currentPageNO-1)*pageSize, pageSize);
		listInfo.setCurrentList(histories);
		listInfo.setSizeOfTotalList(0);
		
		List<TransferHistory> totalHistories=getSqlMapClientTemplate().queryForList("User.transferRecord", userName);
		if (totalHistories!=null) {
			listInfo.setSizeOfTotalList(totalHistories.size());
		}
		logger.info("转账历史记录获取成功:"+userName);
		return listInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ListInfo<PrizeHistory> prizeHistories(String userName,
			int currentPageNO, int pageSize) {
		ListInfo<PrizeHistory> listInfo = new ListInfo<PrizeHistory>(currentPageNO, pageSize);
		
		List<PrizeHistory> histories=getSqlMapClientTemplate().queryForList("User.prizeRecord", userName, (currentPageNO-1)*pageSize, pageSize);
		listInfo.setCurrentList(histories);
		listInfo.setSizeOfTotalList(0);
		
		List<PrizeHistory> totalHistories=getSqlMapClientTemplate().queryForList("User.prizeRecord", userName);
		if (totalHistories!=null) {
			listInfo.setSizeOfTotalList(totalHistories.size());
		}
		logger.info("奖金记录获取成功:"+userName);
		return listInfo;
	}
	
	/**
	 * user_name作为父节点，获取son_user_name所在的层数
	 * @param user_name
	 * @param son_user_name
	 * @return
	 */
	public int getNumByLayer(String user_name,String son_user_name){
		
		int layer=0;
		
		
		return getLayer( user_name, son_user_name, layer);
	}
	
	public int getLayer(String user_name,String son_user_name,int layer){
		
		List<User> layerSon=getUserByUserNameAndArea(user_name, null);
		
		if (layerSon!=null&&layerSon.size()>0) {
			boolean flag=false;
			for (User son:layerSon) {
				if (son.getUser_name().equals(son_user_name)) {
					layer++;
					flag=true;
					return layer;
				}
			}
			if (!flag) {
				layer++;
				for (User son:layerSon) {
					return getLayer(son.getUser_name(), son_user_name, layer);
				}
			}
		}
		return layer;
		
	}

}
