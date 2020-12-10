package cn.kkbc.tpms.tcp.vo.req;

import java.util.Arrays;
import java.util.Date;

import cn.kkbc.tpms.tcp.vo.PackageData;

/**
 * 胎压数据透传
 * 
 * @author hylexus
 *
 */
public class TyrePressureUploadMsg extends PackageData {

	// 1. byte[0] 轮胎位置(BCD[1])
	private String positionField;
	// 第几个轴
	private int axisSerialNo;
	// 第几个轮胎
	private int tyreSerialNo;

	// 2. byte[1] 胎压(Byte) 1/10 公斤
	private int pressure;

	// 3. byte[2-3] 胎温(Word) (N-27300)/100 度
	private int temperature;
	// 4. byte[4] 状态(Byte)
	// bit[0] 0：关，1;开(传感器)
	// bit[1-7] 保留
	private int statusField;
	private boolean sensorOn = false;
	// 5. byte[5] 保留(Word(16))
	private int reversed_5;
	// 6. byte[7] 压力阀测试(Byte)
	private int pressureValve;
	// 7. byte[8-13] 时间(BCD[6]) YY-MM-DD-hh-mm-ss
	private Date date;
	// 8. byte[14-25] 轮胎胎号(BYTE[12])
	private String tyreId;
	// 9. byte[26-27] 电压(Word) 1/10V
	private int voltage;
	// 10. byte[28-39] 传感器编号(BYTE[12])
	private String sensorNo;

	public TyrePressureUploadMsg() {
	}

	public TyrePressureUploadMsg(PackageData packageData) {
		this();
		this.channel = packageData.getChannel();
		this.checkSum = packageData.getCheckSum();
		this.msgBodyBytes = packageData.getMsgBodyBytes();
		this.msgHeader = packageData.getMsgHeader();
	}

	public String getPositionField() {
		return positionField;
	}

	public void setPositionField(String positionField) {
		this.positionField = positionField;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getStatusField() {
		return statusField;
	}

	public void setStatusField(int statusField) {
		this.statusField = statusField;
	}

	public boolean isSensorOn() {
		return sensorOn;
	}

	public void setSensorOn(boolean sensorOn) {
		this.sensorOn = sensorOn;
	}

	@Deprecated
	public int getReversed_5() {
		return reversed_5;
	}

	@Deprecated
	public void setReversed_5(int reversed_5) {
		this.reversed_5 = reversed_5;
	}

	public int getPressureValve() {
		return pressureValve;
	}

	public void setPressureValve(int pressureValve) {
		this.pressureValve = pressureValve;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTyreId() {
		return tyreId;
	}

	public void setTyreId(String tyreNo) {
		this.tyreId = tyreNo;
	}

	public int getVoltage() {
		return voltage;
	}

	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}

	public String getSensorNo() {
		return sensorNo;
	}

	public void setSensorNo(String sensorNo) {
		this.sensorNo = sensorNo;
	}

	public int getAxisSerialNo() {
		return axisSerialNo;
	}

	public int getTyreSerialNo() {
		return tyreSerialNo;
	}

	public void setAxisSerialNo(int axisSerialNo) {
		this.axisSerialNo = axisSerialNo;
	}

	public void setTyreSerialNo(int tyreSerialNo) {
		this.tyreSerialNo = tyreSerialNo;
	}

	@Override
	public String toString() {
		return "TyrePressureUploadMsg [positionField=" + positionField + ", pressure=" + pressure + ", temperature="
				+ temperature + ", statusField=" + statusField + ", sensorOn=" + sensorOn + ", reversed_5=" + reversed_5
				+ ", pressureValve=" + pressureValve + ", date=" + date + ", tyreNo=" + tyreId + ", voltage=" + voltage
				+ ", sensorNo=" + sensorNo + ", msgHeader=" + msgHeader + ", msgBodyBytes="
				+ Arrays.toString(msgBodyBytes) + ", checkSum=" + checkSum + ", channel=" + channel + "]";
	}

}
