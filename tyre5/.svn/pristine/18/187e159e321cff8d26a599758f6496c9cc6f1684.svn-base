package cn.kkbc.tpms.tcp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.kkbc.tpms.tcp.entity.LocationInfo;
import cn.kkbc.tpms.tcp.entity.Terminal;
import cn.kkbc.tpms.tcp.entity.TyreTemperatureAndPressure;

public interface TpmsMapper {

	Terminal findTerminalByPhone(String phone);

	void doCreateTerminal(Terminal terminal);

	void updateLocationInfo(LocationInfo locationInfo);

	void updateLocationInfoTime(String phone);

	void doCreateLocationInfo(LocationInfo locationInfo);

	void updateTemperatureAndPressure(TyreTemperatureAndPressure info);

	void updateTemperatureAndPressureTime(@Param("terminalPhone") String phone,
			@Param("fasheqiId") String transmitterId);

	void doCreateTermperatureAndPressure(TyreTemperatureAndPressure info);

	List<TyreTemperatureAndPressure> findLatestTemperatureAndPressure(String phone);

	LocationInfo findLatestLocationInfo(String phone);

	void copyLocationInfo2History(String phone);

	void copyTemperatureAndPressureInfo2History(@Param("terminalPhone") String phone,
			@Param("fasheqiId") String transmitterId);
}
