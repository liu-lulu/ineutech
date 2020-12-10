package cn.kkbc.tpms.tcp.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import cn.kkbc.tpms.tcp.entity.LocationInfo;
import cn.kkbc.tpms.tcp.entity.Terminal;
import cn.kkbc.tpms.tcp.entity.TyreTemperatureAndPressure;

public class TpmsMapperTest {

	private SqlSessionFactory factory = new SqlSessionFactoryBuilder()
			.build(TpmsMapperTest.class.getClassLoader().getResourceAsStream("mybatis-config.xml"));

	@Test
	public void testFindTerminalByPhone() {
		try {
			SqlSession session = factory.openSession();
			TpmsMapper tpmsMapper = session.getMapper(TpmsMapper.class);
			Terminal terminal = tpmsMapper.findTerminalByPhone("123");
			System.out.println(terminal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDoCreateLocationInfo() {
		try {
			SqlSession session = factory.openSession();
			TpmsMapper tpmsMapper = session.getMapper(TpmsMapper.class);
			LocationInfo locationInfo = new LocationInfo();
			locationInfo.setAccStatus(1);
			locationInfo.setCaijiTime(new Date());
			locationInfo.setCreateTime(new Date());
			locationInfo.setDirection(22);
			locationInfo.setDtuId("ax456");
			locationInfo.setElevation(256);
			System.out.println(locationInfo);
			tpmsMapper.doCreateLocationInfo(locationInfo);
			System.out.println(locationInfo);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindLatestTemperatureAndPressure(){
		SqlSession session = factory.openSession();
		TpmsMapper tpmsMapper = session.getMapper(TpmsMapper.class);
		List<TyreTemperatureAndPressure> list = tpmsMapper.findLatestTemperatureAndPressure("123");
		System.out.println(list);
	}

}
