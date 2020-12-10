package cn.kkbc.tpms.tcp.service.msg;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.dao.TpmsMapper;
import cn.kkbc.tpms.tcp.entity.LocationInfo;
import cn.kkbc.tpms.tcp.entity.Terminal;
import cn.kkbc.tpms.tcp.entity.TyreTemperatureAndPressure;
import cn.kkbc.tpms.tcp.util.BitOperator;
import cn.kkbc.tpms.tcp.util.UUIDGenerator;
import cn.kkbc.tpms.tcp.vo.Session;
import cn.kkbc.tpms.tcp.vo.req.LocationInfoUploadMsg;
import cn.kkbc.tpms.tcp.vo.req.TerminalAuthenticationMsg;
import cn.kkbc.tpms.tcp.vo.req.TerminalRegisterMsg;
import cn.kkbc.tpms.tcp.vo.req.TerminalRegisterMsg.TerminalRegInfo;
import cn.kkbc.tpms.tcp.vo.req.TyrePressureUploadMsg;

public class DataBaseService {

	private static final Logger log = LoggerFactory.getLogger(DataBaseService.class);
	private static final Logger weblog = LoggerFactory.getLogger("weblog");

	private static SqlSessionFactory factory = new SqlSessionFactoryBuilder()
			.build(BaseMsgProcessService.class.getClassLoader().getResourceAsStream("mybatis-config.xml"));

	public void processLocationDataChanged(LocationInfoUploadMsg req, Session session) {
		SqlSession sqlSession = factory.openSession();
		TpmsMapper tpmsMapper = sqlSession.getMapper(TpmsMapper.class);
		try {
			// 第一次,直接新增数据
			if (session.getLastLocationInfoMsg() == null) {
				tpmsMapper.doCreateLocationInfo(toEntity(req, session));
				tpmsMapper.copyLocationInfo2History(session.getTerminalPhone());
				log.info("位置信息更新完毕[新增]");
			} else {
				// 数据有变化
				if (this.isDataChanged(req, session.getLastLocationInfoMsg())) {
					tpmsMapper.updateLocationInfo(toEntity(req, session));
					tpmsMapper.copyLocationInfo2History(session.getTerminalPhone());
					log.info("位置信息更新完毕[更新数据]");
				}
				// 无变化,只更新时间
				else {
					tpmsMapper.updateLocationInfoTime(session.getTerminalPhone());
					log.info("位置信息更新完毕[只更新时间]");
				}
			}
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
			log.error("数据库处理出错[位置信息]:{}", e.getMessage());
			e.printStackTrace();
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
	}

	/**
	 * @param req
	 * @param last
	 * @return true if data changed
	 */
	private boolean isDataChanged(LocationInfoUploadMsg req, LocationInfoUploadMsg last) {

		if (req == null || last == null)
			return true;

		// byte[0-3] 报警标志(DWORD(32))
		if (req.getWarningFlagField() != last.getWarningFlagField())
			return true;
		// byte[4-7] 状态(DWORD(32))
		if (req.getStatusField() != last.getStatusField())
			return true;
		// byte[8-11] 纬度(DWORD(32))
		if (!doubleEquals(req.getLatitude(), last.getLatitude()))
			return true;
		// byte[12-15] 经度(DWORD(32))
		if (!doubleEquals(req.getLongitude(), last.getLongitude()))
			return true;
		// byte[16-17] 高程(WORD(16))
		if (req.getElevation() != last.getElevation())
			return true;
		// byte[18-19] 速度(WORD)
		if (!floatEquals(req.getSpeed(), last.getSpeed()))
			return true;
		// byte[20-21] 方向(WORD)
		if (req.getDirection() != last.getDirection())
			return true;

		return false;
	}

	private static boolean floatEquals(Float f1, Float f2) {
		if (f1 == null || f2 == null)
			return false;
		return Math.abs(f1 - f2) <= 0.0999;
	}

	private static boolean doubleEquals(Double f1, Double f2) {
		if (f1 == null || f2 == null)
			return false;
		return Math.abs(f1 - f2) <= 0.0999;
	}

	private LocationInfo toEntity(LocationInfoUploadMsg req, Session session) {
		BitOperator bitOperator = new BitOperator();
		LocationInfo ret = new LocationInfo();
		final int statusField = req.getStatusField();

		ret.setAccStatus(bitOperator.getBitAt(statusField, 0));
		ret.setCaijiTime(req.getTime());
		ret.setCreateTime(new Date());
		ret.setDirection(req.getDirection());
		ret.setDtuId(session.getTerminalId());
		ret.setElevation(req.getElevation());
		ret.setEncrypt(bitOperator.getBitAt(statusField, 5));
		ret.setLatitude(req.getLatitude());
		ret.setLatitudeType(bitOperator.getBitAt(statusField, 2));
		ret.setLocationStatus(bitOperator.getBitAt(statusField, 1));
		ret.setLongitude(req.getLongitude());
		ret.setLongitudeType(bitOperator.getBitAt(statusField, 3));
		ret.setOperationStatus(bitOperator.getBitAt(statusField, 4));
		ret.setSpeed((int) req.getSpeed());
		ret.setStatusField(statusField);
		ret.setTerminalPhone(req.getMsgHeader().getTerminalPhone());
		ret.setTrucksCircuit(bitOperator.getBitAt(statusField, 11));
		ret.setTrucksDoorlock(bitOperator.getBitAt(statusField, 12));
		ret.setTrucksOilway(bitOperator.getBitAt(statusField, 10));
		ret.setWarnMark(req.getWarningFlagField());

		return ret;
	}

	public void processTemperatureAndPressureDataChanged(TyrePressureUploadMsg req, Session session) {
		Map<String, TyrePressureUploadMsg> map = session.getLastTyrePressureMsgs();
		SqlSession sqlSession = factory.openSession();
		TpmsMapper tpmsMapper = sqlSession.getMapper(TpmsMapper.class);

		try {

			if (map == null) {
				map = new HashMap<>();
				map.put(req.getSensorNo(), req);
				session.setLastTyrePressureMsgs(map);
			}

			TyrePressureUploadMsg last = map.get(req.getSensorNo());
			if (last == null) {
				// 第一次,直接新增
				tpmsMapper.doCreateTermperatureAndPressure(toEntity(req, session));
				tpmsMapper.copyTemperatureAndPressureInfo2History(session.getTerminalPhone(), req.getSensorNo());
				map.put(req.getSensorNo(), req);
				session.setLastTyrePressureMsgs(map);
				log.info("胎温胎压更新完毕[新增数据],sensorNo={}", req.getSensorNo());
			} else {
				// 有变化
				if (this.isDataChanged(req, session)) {
					tpmsMapper.updateTemperatureAndPressure(toEntity(req, session));
					tpmsMapper.copyTemperatureAndPressureInfo2History(session.getTerminalPhone(), req.getSensorNo());
					log.info("胎温胎压更新完毕[只更新时间],sensorNo={}", req.getSensorNo());
				}
				// 无变化,只更新时间
				else {
					tpmsMapper.updateTemperatureAndPressureTime(session.getTerminalPhone(), req.getSensorNo());
					log.info("胎温胎压更新完毕[只更新时间,sensorNo={}]", req.getSensorNo());
				}
			}
			sqlSession.commit();
		} catch (Exception e) {
			sqlSession.rollback();
			log.error("数据库处理出错[胎温胎压]:{}", e.getMessage());
			e.printStackTrace();
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
	}

	private TyreTemperatureAndPressure toEntity(TyrePressureUploadMsg req, Session session) {
		TyreTemperatureAndPressure ret = new TyreTemperatureAndPressure();
		BitOperator bitOperator = new BitOperator();
		ret.setAxisNo(req.getAxisSerialNo());
		ret.setCaijiTime(req.getDate());
		int valve = req.getPressureValve();
		ret.setChaodiya(bitOperator.getBitAt(valve, 2));
		ret.setChaoya(bitOperator.getBitAt(valve, 0));
		ret.setCreateTime(new Date());
		ret.setDianchiqianya(bitOperator.getBitAt(valve, 6));
		ret.setDianya(req.getVoltage());
		ret.setDiya(bitOperator.getBitAt(valve, 1));
		ret.setDtuId(session.getTerminalId());
		ret.setErrorInfo(bitOperator.getBitAt(valve, 3));
		ret.setFasheqiId(req.getSensorNo());
		ret.setGaowen(bitOperator.getBitAt(valve, 5));
		ret.setPressureValve(valve);
		ret.setSelfcheck(bitOperator.getBitAt(valve, 4));
		ret.setStatusField(req.getStatusField());
		ret.setStatusOn(bitOperator.getBitAt(req.getStatusField(), 0));
		ret.setTerminalPhone(req.getMsgHeader().getTerminalPhone());
		ret.setTyreId(req.getTyreId());
		ret.setTyreNo(req.getTyreSerialNo());
		ret.setWendu(req.getTemperature());
		ret.setYali(req.getPressure());

		return ret;
	}

	private boolean isDataChanged(TyrePressureUploadMsg req, Session session) {
		Map<String, TyrePressureUploadMsg> map = session.getLastTyrePressureMsgs();
		if (map == null || map.isEmpty()) {
			map = new HashMap<>();
			map.put(req.getSensorNo(), req);
			session.setLastTyrePressureMsgs(map);
			log.info("胎温胎压更新完毕[新增数据],sensorNo={}", req.getSensorNo());
			return true;
		}

		TyrePressureUploadMsg last = map.get(req.getSensorNo());
		if (last == null) {
			map.put(req.getSensorNo(), req);
			session.setLastTyrePressureMsgs(map);
			return true;
		}

		return this.isDataChanged(req, last);
	}

	private boolean isDataChanged(TyrePressureUploadMsg req, TyrePressureUploadMsg last) {
		// byte[0] 轮胎位置(BCD[1])---ignore
		// if (!req.getPositionField().equals(last.getPositionField()))
		// return true;
		// byte[1] 胎压(Byte)
		if (req.getPressure() != last.getPressure())
			return true;
		// byte[2-3] 胎温(Word)
		if (req.getTemperature() != last.getTemperature())
			return true;
		// byte[4] 状态(Byte)
		if (req.getStatusField() != last.getStatusField())
			return true;
		// byte[5-6] 保留(Word(16))---ignore
		// byte[7] 压力阀测试(Byte)
		if (req.getPressureValve() != last.getPressureValve())
			return true;
		// byte[14-25] 轮胎胎号(BYTE[12])---ignore
		// if (!req.getTyreNo().equals(last.getTyreNo()))
		// return true;
		// byte[26-27] 电压(Word) 1/10V---ignore
		if (req.getVoltage() != last.getVoltage())
			return true;
		// byte[28-39] 传感器编号(BYTE[12])---ignore
		// if (!req.getSensorNo().equals(last.getSensorNo()))
		// return true;
		return false;
	}

	public Session loadLatestData4Session(Session session) {
		SqlSession sqlSession = factory.openSession(true);
		TpmsMapper tpmsMapper = sqlSession.getMapper(TpmsMapper.class);
		try {
			LocationInfo info = tpmsMapper.findLatestLocationInfo(session.getTerminalPhone());
			if (info != null) {
				LocationInfoUploadMsg msg = new LocationInfoUploadMsg();
				msg.setWarningFlagField(info.getWarnMark());
				msg.setStatusField(info.getStatusField());
				msg.setLatitude(info.getLatitude());
				msg.setLongitude(info.getLongitude());
				msg.setElevation(info.getElevation());
				msg.setSpeed(info.getSpeed());
				msg.setDirection(info.getDirection());
				msg.setTime(info.getCaijiTime());

				session.setLastLocationInfoMsg(msg);
			}
			log.info("最近一次设备数据加载完成--位置信息[{}]", info == null ? "NO" : "OK");

			List<TyreTemperatureAndPressure> list = tpmsMapper
					.findLatestTemperatureAndPressure(session.getTerminalPhone());
			Map<String, TyrePressureUploadMsg> lastTyrePressureMsgs = new HashMap<>();
			if (list != null && list.size() > 0) {
				for (TyreTemperatureAndPressure e : list) {
					if (e == null)
						continue;
					TyrePressureUploadMsg ret = new TyrePressureUploadMsg();
					ret.setAxisSerialNo(e.getAxisNo());
					ret.setTyreSerialNo(e.getTyreNo());
					ret.setPositionField(e.getAxisNo() + "" + e.getTyreNo());
					ret.setPressure(e.getYali());
					ret.setTemperature(e.getWendu());
					ret.setStatusField(e.getStatusField());
					ret.setPressureValve(e.getPressureValve());
					ret.setDate(e.getCaijiTime());
					ret.setTyreId(e.getTyreId());
					ret.setVoltage(e.getDianya());
					ret.setSensorNo(e.getFasheqiId());

					lastTyrePressureMsgs.put(ret.getSensorNo(), ret);
				}
			}
			log.info("最近一次设备数据加载完成--胎温胎压[{}]", lastTyrePressureMsgs.size());
			session.setLastTyrePressureMsgs(lastTyrePressureMsgs);
		} catch (Exception e) {
			log.error("数据库处理出错[查询最近一次设备数据]:{}", e.getMessage());
			e.printStackTrace();
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
		return session;
	}

	public Terminal processRegisterMsg(TerminalRegisterMsg msg, Session session) {
		SqlSession sqlSession = factory.openSession();
		TpmsMapper tpmsMapper = sqlSession.getMapper(TpmsMapper.class);

		try {
			Terminal terminal = tpmsMapper.findTerminalByPhone(session.getTerminalPhone());
			if (terminal == null) {
				final TerminalRegInfo info = msg.getTerminalRegInfo();
				terminal = new Terminal();
				terminal.setAuthenticationkey(UUIDGenerator.generateAuthKey());
				terminal.setCaijiTime(new Date());
				terminal.setCity(info.getCityId());
				terminal.setCreateTime(new Date());
				terminal.setDtuId(info.getTerminalId());
				terminal.setDtuStyle(info.getTerminalType());
				terminal.setMaker(info.getManufacturerId());
				terminal.setProvince(info.getProvinceId());
				terminal.setReversedFlag(0);
				terminal.setTerminalPhone(session.getTerminalPhone());
				terminal.setTrucksColor(info.getLicensePlateColor());
				terminal.setTrucksId(info.getLicensePlate());
				tpmsMapper.doCreateTerminal(terminal);
			}
			sqlSession.commit();
			return terminal;
		} catch (Exception e) {
			sqlSession.rollback();
			log.error("数据库操作出错[终端注册]:{}", e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
	}

	public boolean processAuth(TerminalAuthenticationMsg msg, Session session) {
		SqlSession sqlSession = factory.openSession(true);
		TpmsMapper tpmsMapper = sqlSession.getMapper(TpmsMapper.class);

		try {
			Terminal terminal = tpmsMapper.findTerminalByPhone(session.getTerminalPhone());
			if (terminal == null) {
				log.error("终端鉴权失败[设备不存在],phone={}", session.getTerminalPhone());
				weblog.error("终端鉴权失败[设备不存在],phone={}", session.getTerminalPhone());
				return false;
			}
			if (!msg.getAuthCode().equals(terminal.getAuthenticationkey())) {
				log.error("终端鉴权失败[鉴权码错误],phone={},authCode={},clientAuthCode={}", session.getTerminalPhone(),
						terminal.getAuthenticationkey(), msg.getAuthCode());
				weblog.error("终端鉴权失败[鉴权码错误],phone={},authCode={},clientAuthCode={}", session.getTerminalPhone(),
						terminal.getAuthenticationkey(), msg.getAuthCode());
				return false;
			}
			session.setTerminalId(terminal.getDtuId());
			return true;
		} catch (Exception e) {
			log.error("数据库操作出错[终端鉴权]:{}", e.getMessage());
			e.printStackTrace();
			return false;
		} finally {
			if (sqlSession != null)
				sqlSession.close();
		}
	}
}
