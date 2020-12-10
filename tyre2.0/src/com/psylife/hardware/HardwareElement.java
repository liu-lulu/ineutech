package com.psylife.hardware;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.psylife.entity.Device;
import com.psylife.entity.DeviceDataBase;
import com.psylife.entity.DeviceDataDingwei;
import com.psylife.entity.DeviceDataHistory;
import com.psylife.entity.DeviceDataOffon;
import com.psylife.entity.DeviceDataYaliWendu;
import com.psylife.entity.DeviceFasheqi;
import com.psylife.vo.dtu.DeviceFloatByDtuVO;
import com.psylife.vo.dtu.DeviceOffonByDtuVO;
import com.psylife.vo.dtu.DeviceStringByDtuVO;

/**
 * 硬件元素
 * @author xu
 *
 */
public class HardwareElement {
	
	/**
	 * 若对应,车牌号不存在时,查询间隔时间
	 */
	public static final long CARNO_SEARCH_GAP=30*60*1000;
	
	/**
	 * 电话号码,手机号8位
	 */
	private String phone=null;
	
	/**
	 * 心跳时间
	 */
	private long lastConnectHeartTime=System.currentTimeMillis();
	
	/**
	 * 车牌号
	 */
	private String carNum;
	
	/**
	 * 车id
	 */
	private Integer carId;
	
	/**
	 * 车设备关系id
	 */
	private Long carDeviceId;
	
	/**
	 * 车型
	 */
	private String carStyle;
	
	/**
	 * udp地址信息
	 */
	private InetSocketAddress udpAddress;
	
	/**
	 * 包序号 2字节
	 */
	private int no=0;
	
	/**
	 * 发送心跳时间
	 */
	private long sendHeartTime=System.currentTimeMillis();
	
	/**
	 * 查询车牌号时间(当车和设备没绑定时，定时查询)
	 */
	private long carNoSearchTime=System.currentTimeMillis();
	
	/**
	 * 标识是否可以保存,开关量包数据
	 */
	private boolean flagOffon=false;
	
	/**
	 * 标识是否可以保存,float包数据
	 */
	private boolean flagFloat=false;
	/**
	 * 标识是否可以保存,字符串包数据
	 */
	private boolean flagString=false;
	
	/**
	 * 开关量包数据
	 */
	private DeviceOffonByDtuVO deviceOffonByDtuVO=new DeviceOffonByDtuVO();
	
	/**
	 * float包数据
	 */
	private DeviceFloatByDtuVO deviceFloatByDtuVO=new DeviceFloatByDtuVO();
	
	/**
	 * 字符串包数据
	 */
	private DeviceStringByDtuVO deviceStringByDtuVO=new DeviceStringByDtuVO();
	
	/**
	 * dtu设备
	 */
	private Device device=new Device();
	
	/**
	 * dtu设备,上次的
	 */
	private Device oldDevice=new Device();
	
	/**
	 * dtu设备数据基础
	 */
	private DeviceDataBase deviceDataBase=new DeviceDataBase();
	
	/**
	 * dtu设备数据基础,上次的
	 */
	private DeviceDataBase oldDeviceDataBase=new DeviceDataBase();
	
	/**
	 * dtu设备数据定位信息
	 */
	private DeviceDataDingwei deviceDataDingwei=new DeviceDataDingwei();
	
	/**
	 * dtu设备数据定位信息,上次的
	 */
	private DeviceDataDingwei oldDeviceDataDingwei=new DeviceDataDingwei();
	
	/**
	 * dtu设备数据开关量
	 */
	private List<DeviceDataOffon> deviceDataOffons=new ArrayList<DeviceDataOffon>();
	
	/**
	 * dtu设备数据开关量,上次的
	 */
	private List<DeviceDataOffon> oldDeviceDataOffons=new ArrayList<DeviceDataOffon>();
	
	/**
	 * dtu设备数据压力温度
	 */
	private List<DeviceDataYaliWendu> deviceDataYaliWendus=new ArrayList<DeviceDataYaliWendu>();
	
	/**
	 * dtu设备数据压力温度,上次的
	 */
	private List<DeviceDataYaliWendu> oldDeviceDataYaliWendus=new ArrayList<DeviceDataYaliWendu>();
	
	/**
	 * dtu设备数据历史记录
	 */
	private DeviceDataHistory deviceDataHistory=new DeviceDataHistory();
	
	/**
	 * 设备发射器
	 */
	private List<DeviceFasheqi> deviceFasheqis=new ArrayList<DeviceFasheqi>();
	
	/**
	 * 设备发射器,上次的
	 */
	private List<DeviceFasheqi> oldDeviceFasheqis=new ArrayList<DeviceFasheqi>();
	
	/**
	 * 行驶公里数
	 */
	private double liCheng=0d;
	
	/**
	 * 位置、胎号对应map
	 */
	private Map<String, String> mapPositionTyreId=new ConcurrentHashMap<String, String>();
	
	/**
	 * 是否行驶中,默认否
	 */
	private boolean runing=false;
	
	/**
	 * 获取包序号
	 * @return
	 */
	public int getPackageNo(){
		this.no++;
		if(this.no>65535){
			this.no=0;
		}
		return this.no;
	}

	public long getLastConnectHeartTime() {
		return lastConnectHeartTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setLastConnectHeartTime(long lastConnectHeartTime) {
		this.lastConnectHeartTime = lastConnectHeartTime;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public InetSocketAddress getUdpAddress() {
		return udpAddress;
	}

	public void setUdpAddress(InetSocketAddress udpAddress) {
		this.udpAddress = udpAddress;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public long getSendHeartTime() {
		return sendHeartTime;
	}

	public void setSendHeartTime(long sendHeartTime) {
		this.sendHeartTime = sendHeartTime;
	}

	public boolean isFlagOffon() {
		return flagOffon;
	}

	public void setFlagOffon(boolean flagOffon) {
		this.flagOffon = flagOffon;
	}

	public boolean isFlagFloat() {
		return flagFloat;
	}

	public void setFlagFloat(boolean flagFloat) {
		this.flagFloat = flagFloat;
	}

	public boolean isFlagString() {
		return flagString;
	}

	public void setFlagString(boolean flagString) {
		this.flagString = flagString;
	}

	public DeviceOffonByDtuVO getDeviceOffonByDtuVO() {
		return deviceOffonByDtuVO;
	}

	public void setDeviceOffonByDtuVO(DeviceOffonByDtuVO deviceOffonByDtuVO) {
		this.deviceOffonByDtuVO = deviceOffonByDtuVO;
	}

	public DeviceFloatByDtuVO getDeviceFloatByDtuVO() {
		return deviceFloatByDtuVO;
	}

	public void setDeviceFloatByDtuVO(DeviceFloatByDtuVO deviceFloatByDtuVO) {
		this.deviceFloatByDtuVO = deviceFloatByDtuVO;
	}

	public DeviceStringByDtuVO getDeviceStringByDtuVO() {
		return deviceStringByDtuVO;
	}

	public void setDeviceStringByDtuVO(DeviceStringByDtuVO deviceStringByDtuVO) {
		this.deviceStringByDtuVO = deviceStringByDtuVO;
	}

	public Long getCarDeviceId() {
		return carDeviceId;
	}

	public void setCarDeviceId(Long carDeviceId) {
		this.carDeviceId = carDeviceId;
	}

	public double getLiCheng() {
		return liCheng;
	}

	public void setLiCheng(double liCheng) {
		this.liCheng = liCheng;
	}

	public long getCarNoSearchTime() {
		return carNoSearchTime;
	}

	public void setCarNoSearchTime(long carNoSearchTime) {
		this.carNoSearchTime = carNoSearchTime;
	}

	public String getCarStyle() {
		return carStyle;
	}

	public void setCarStyle(String carStyle) {
		this.carStyle = carStyle;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public DeviceDataBase getDeviceDataBase() {
		return deviceDataBase;
	}

	public void setDeviceDataBase(DeviceDataBase deviceDataBase) {
		this.deviceDataBase = deviceDataBase;
	}

	public DeviceDataDingwei getDeviceDataDingwei() {
		return deviceDataDingwei;
	}

	public void setDeviceDataDingwei(DeviceDataDingwei deviceDataDingwei) {
		this.deviceDataDingwei = deviceDataDingwei;
	}

	public List<DeviceDataOffon> getDeviceDataOffons() {
		return deviceDataOffons;
	}

	public void setDeviceDataOffons(List<DeviceDataOffon> deviceDataOffons) {
		this.deviceDataOffons = deviceDataOffons;
	}

	public List<DeviceDataYaliWendu> getDeviceDataYaliWendus() {
		return deviceDataYaliWendus;
	}

	public void setDeviceDataYaliWendus(
			List<DeviceDataYaliWendu> deviceDataYaliWendus) {
		this.deviceDataYaliWendus = deviceDataYaliWendus;
	}

	public DeviceDataHistory getDeviceDataHistory() {
		return deviceDataHistory;
	}

	public void setDeviceDataHistory(DeviceDataHistory deviceDataHistory) {
		this.deviceDataHistory = deviceDataHistory;
	}

	public List<DeviceFasheqi> getDeviceFasheqis() {
		return deviceFasheqis;
	}

	public void setDeviceFasheqis(List<DeviceFasheqi> deviceFasheqis) {
		this.deviceFasheqis = deviceFasheqis;
	}

	public Device getOldDevice() {
		return oldDevice;
	}

	public void setOldDevice(Device oldDevice) {
		this.oldDevice = oldDevice;
	}

	public DeviceDataBase getOldDeviceDataBase() {
		return oldDeviceDataBase;
	}

	public void setOldDeviceDataBase(DeviceDataBase oldDeviceDataBase) {
		this.oldDeviceDataBase = oldDeviceDataBase;
	}

	public DeviceDataDingwei getOldDeviceDataDingwei() {
		return oldDeviceDataDingwei;
	}

	public void setOldDeviceDataDingwei(DeviceDataDingwei oldDeviceDataDingwei) {
		this.oldDeviceDataDingwei = oldDeviceDataDingwei;
	}

	public List<DeviceDataOffon> getOldDeviceDataOffons() {
		return oldDeviceDataOffons;
	}

	public void setOldDeviceDataOffons(List<DeviceDataOffon> oldDeviceDataOffons) {
		this.oldDeviceDataOffons = oldDeviceDataOffons;
	}

	public List<DeviceDataYaliWendu> getOldDeviceDataYaliWendus() {
		return oldDeviceDataYaliWendus;
	}

	public void setOldDeviceDataYaliWendus(
			List<DeviceDataYaliWendu> oldDeviceDataYaliWendus) {
		this.oldDeviceDataYaliWendus = oldDeviceDataYaliWendus;
	}

	public List<DeviceFasheqi> getOldDeviceFasheqis() {
		return oldDeviceFasheqis;
	}

	public void setOldDeviceFasheqis(List<DeviceFasheqi> oldDeviceFasheqis) {
		this.oldDeviceFasheqis = oldDeviceFasheqis;
	}

	public Map<String, String> getMapPositionTyreId() {
		return mapPositionTyreId;
	}

	public void setMapPositionTyreId(Map<String, String> mapPositionTyreId) {
		this.mapPositionTyreId = mapPositionTyreId;
	}

	public boolean isRuning() {
		return runing;
	}

	public void setRuning(boolean runing) {
		this.runing = runing;
	}
	
	

}
