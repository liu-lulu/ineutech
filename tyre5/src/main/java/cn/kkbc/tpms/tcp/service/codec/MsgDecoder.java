package cn.kkbc.tpms.tcp.service.codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.TPMSConsts;
import cn.kkbc.tpms.tcp.util.BCD8421Operater;
import cn.kkbc.tpms.tcp.util.BitOperator;
import cn.kkbc.tpms.tcp.util.HexStringUtils;
import cn.kkbc.tpms.tcp.vo.PackageData;
import cn.kkbc.tpms.tcp.vo.PackageData.MsgHeader;
import cn.kkbc.tpms.tcp.vo.QueueElement;
import cn.kkbc.tpms.tcp.vo.req.LocationInfoUploadMsg;
import cn.kkbc.tpms.tcp.vo.req.TerminalCommonRespMsg;
import cn.kkbc.tpms.tcp.vo.req.TerminalParamQueryReplyMsgItem;
import cn.kkbc.tpms.tcp.vo.req.TerminalRegisterMsg;
import cn.kkbc.tpms.tcp.vo.req.TerminalRegisterMsg.TerminalRegInfo;
import cn.kkbc.tpms.tcp.vo.req.TyrePressureUploadMsg;

public class MsgDecoder {

	private static final Logger log = LoggerFactory.getLogger(MsgDecoder.class);
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(TPMSConsts.date_pattern);

	private BitOperator bitOperator;
	private BCD8421Operater bcd8421Operater;

	public MsgDecoder() {
		this.bitOperator = new BitOperator();
		this.bcd8421Operater = new BCD8421Operater();
	}

	public PackageData queueElement2PackageData(QueueElement msg) {
		PackageData ret = new PackageData();

		// 去掉头尾的0x7e分隔符
		// byte[] data = new byte[msg.getData().length - 2];
		final byte[] data = msg.getData();
		// System.arraycopy(msg.getData(), 1, data, 0, msg.getData().length -
		// 2);

		// 0. 终端套接字地址信息
		ret.setChannel(msg.getChannel());

		// 1. 16byte 或 12byte 消息头
		MsgHeader msgHeader = this.parseMsgHeaderFromBytes(data);
		ret.setMsgHeader(msgHeader);

		int msgBodyByteStartIndex = 12;
		// 2. 消息体
		// 有子包信息,消息体起始字节后移四个字节:消息包总数(word(16))+包序号(word(16))
		if (msgHeader.isHasSubPackage()) {
			msgBodyByteStartIndex = 16;
		}

		byte[] tmp = new byte[msgHeader.getMsgBodyLength()];
		System.arraycopy(data, msgBodyByteStartIndex, tmp, 0, tmp.length);
		ret.setMsgBodyBytes(tmp);

		// 3. 去掉分隔符之后，最后一位就是校验码
		// int checkSumInPkg =
		// this.bitOperator.oneByteToInteger(data[data.length - 1]);
		int checkSumInPkg = data[data.length - 1];
		int calculatedCheckSum = this.bitOperator.getCheckSum4JT808(data, 0, data.length - 1);
		ret.setCheckSum(checkSumInPkg);
		if (checkSumInPkg != calculatedCheckSum) {
			log.warn("检验码不一致,msgid:{},pkg:{},calculated:{}", msgHeader.getMsgId(), checkSumInPkg, calculatedCheckSum);
		}
		return ret;
	}

	private MsgHeader parseMsgHeaderFromBytes(byte[] data) {
		MsgHeader msgHeader = new MsgHeader();

		// 1. 消息ID word(16)
		// byte[] tmp = new byte[2];
		// System.arraycopy(data, 0, tmp, 0, 2);
		// msgHeader.setMsgId(this.bitOperator.twoBytesToInteger(tmp));
		msgHeader.setMsgId(this.parseIntFromBytes(data, 0, 2));

		// 2. 消息体属性 word(16)=================>
		// System.arraycopy(data, 2, tmp, 0, 2);
		// int msgBodyProps = this.bitOperator.twoBytesToInteger(tmp);
		int msgBodyProps = this.parseIntFromBytes(data, 2, 2);
		msgHeader.setMsgBodyPropsField(msgBodyProps);
		// [ 0-9 ] 0000,0011,1111,1111(3FF)(消息体长度)
		msgHeader.setMsgBodyLength(msgBodyProps & 0x1ff);
		// [10-12] 0001,1100,0000,0000(1C00)(加密类型)
		msgHeader.setEncryptionType((msgBodyProps & 0xe00) >> 10);
		// [ 13_ ] 0010,0000,0000,0000(2000)(是否有子包)
		msgHeader.setHasSubPackage(((msgBodyProps & 0x2000) >> 13) == 1);
		// [14-15] 1100,0000,0000,0000(C000)(保留位)
		msgHeader.setReservedBit(((msgBodyProps & 0xc000) >> 14) + "");
		// 消息体属性 word(16)<=================

		// 3. 终端手机号 bcd[6]
		// tmp = new byte[6];
		// System.arraycopy(data, 4, tmp, 0, 6);
		// msgHeader.setTerminalPhone(this.bcd8421Operater.bcd2String(tmp));
		msgHeader.setTerminalPhone(this.parseBcdStringFromBytes(data, 4, 6));

		// 4. 消息流水号 word(16) 按发送顺序从 0 开始循环累加
		// tmp = new byte[2];
		// System.arraycopy(data, 10, tmp, 0, 2);
		// msgHeader.setFlowId(this.bitOperator.twoBytesToInteger(tmp));
		msgHeader.setFlowId(this.parseIntFromBytes(data, 10, 2));

		// 5. 消息包封装项
		// 有子包信息
		if (msgHeader.isHasSubPackage()) {
			// 消息包封装项字段
			msgHeader.setPackageInfoField(this.parseIntFromBytes(data, 12, 4));
			// byte[0-1] 消息包总数(word(16))
			// tmp = new byte[2];
			// System.arraycopy(data, 12, tmp, 0, 2);
			// msgHeader.setTotalSubPackage(this.bitOperator.twoBytesToInteger(tmp));
			msgHeader.setTotalSubPackage(this.parseIntFromBytes(data, 12, 2));

			// byte[2-3] 包序号(word(16)) 从 1 开始
			// tmp = new byte[2];
			// System.arraycopy(data, 14, tmp, 0, 2);
			// msgHeader.setSubPackageSeq(this.bitOperator.twoBytesToInteger(tmp));
			msgHeader.setSubPackageSeq(this.parseIntFromBytes(data, 12, 2));
		}
		return msgHeader;
	}

	public TerminalRegisterMsg toTerminalRegisterMsg(PackageData packageData) {
		TerminalRegisterMsg ret = new TerminalRegisterMsg(packageData);
		byte[] data = ret.getMsgBodyBytes();

		TerminalRegInfo body = new TerminalRegInfo();

		// 1. byte[0-1] 省域ID(WORD)
		// 设备安装车辆所在的省域，省域ID采用GB/T2260中规定的行政区划代码6位中前两位
		// 0保留，由平台取默认值
		body.setProvinceId(this.parseIntFromBytes(data, 0, 2));

		// 2. byte[2-3] 设备安装车辆所在的市域或县域,市县域ID采用GB/T2260中规定的行 政区划代码6位中后四位
		// 0保留，由平台取默认值
		body.setCityId(this.parseIntFromBytes(data, 2, 2));

		// 3. byte[4-8] 制造商ID(BYTE[5]) 5 个字节，终端制造商编码
		// byte[] tmp = new byte[5];
		body.setManufacturerId(this.parseStringFromBytesSkipBlack(data, 4, 5));

		// 4. byte[9-16] 终端型号(BYTE[8]) 八个字节， 此终端型号 由制造商自行定义 位数不足八位的，补空格。
		body.setTerminalType(this.parseStringFromBytesSkipBlack(data, 9, 8));

		// 5. byte[17-23] 终端ID(BYTE[7]) 七个字节， 由大写字母 和数字组成， 此终端 ID由制造 商自行定义
		body.setTerminalId(this.parseStringFromBytesSkipBlack(data, 17, 7));

		// 6. byte[24] 车牌颜色(BYTE) 车牌颜 色按照JT/T415-2006 中5.4.12 的规定
		body.setLicensePlateColor(this.parseIntFromBytes(data, 24, 1));

		// 7. byte[25-x] 车牌(STRING) 公安交 通管理部门颁 发的机动车号牌
		body.setLicensePlate(this.parseStringFromBytesSkipBlack(data, 25, data.length - 25));

		ret.setTerminalRegInfo(body);
		return ret;
	}

	public TerminalCommonRespMsg toTerminalCommonRespMsg(PackageData packageData) {
		TerminalCommonRespMsg ret = new TerminalCommonRespMsg(packageData);
		byte[] data = ret.getMsgBodyBytes();
		// 1. byte[0-1] 应答流水号 对应的平台消息的流水号
		byte[] tmp = new byte[2];
		System.arraycopy(data, 0, tmp, 0, 2);
		ret.setReplyFlowId(bitOperator.byteToInteger(tmp));

		// 2. byte[2-3] 应答ID 对应的平台消息的ID
		tmp = new byte[2];
		System.arraycopy(data, 2, tmp, 0, 2);
		ret.setReplyId(bitOperator.byteToInteger(tmp));
		// 3. byte[4] 结果
		tmp = new byte[1];
		System.arraycopy(data, 4, tmp, 0, 1);
		ret.setReplyCode(tmp[0]);
		return ret;
	}

	public LocationInfoUploadMsg toLocationInfoUploadMsg(PackageData packageData) {
		LocationInfoUploadMsg ret = new LocationInfoUploadMsg(packageData);
		final byte[] data = ret.getMsgBodyBytes();

		// 1. byte[0-3] 报警标志(DWORD(32))
		ret.setWarningFlagField(this.parseIntFromBytes(data, 0, 3));
		// 2. byte[4-7] 状态(DWORD(32))
		ret.setStatusField(this.parseIntFromBytes(data, 4, 4));
		// 3. byte[8-11] 纬度(DWORD(32)) 以度为单位的纬度值乘以10^6，精确到百万分之一度
		ret.setLatitude(this.parseFloatFromBytes(data, 8, 4));
		// 4. byte[12-15] 经度(DWORD(32)) 以度为单位的经度值乘以10^6，精确到百万分之一度
		ret.setLongitude(this.parseFloatFromBytes(data, 12, 4));
		// 5. byte[16-17] 高程(WORD(16)) 海拔高度，单位为米（ m）
		ret.setElevation(this.parseIntFromBytes(data, 16, 2));
		// byte[18-19] 速度(WORD) 1/10km/h
		ret.setSpeed(this.parseFloatFromBytes(data, 18, 2));
		// byte[20-21] 方向(WORD) 0-359，正北为 0，顺时针
		ret.setDirection(this.parseIntFromBytes(data, 20, 2));
		// byte[22-x] 时间(BCD[6]) YY-MM-DD-hh-mm-ss
		// GMT+8 时间，本标准中之后涉及的时间均采用此时区
		ret.setTime(this.parseDateFromBytes(data, 22, 6));
		// 2、消息上传时间：终端采集到的时间也不准确，以服务器收到数据包时间为准。
		ret.setTime(new Date());
		return ret;
	}

	@SuppressWarnings("deprecation")
	public TyrePressureUploadMsg toTyrePressureUploadMsg(PackageData packageData) {
		TyrePressureUploadMsg ret = new TyrePressureUploadMsg(packageData);
		final byte[] data = packageData.getMsgBodyBytes();
		// 1. byte[0] 轮胎位置(BCD[1])
		ret.setPositionField(this.parseBcdStringFromBytes(data, 0, 1));
		// 2. byte[1] 胎压(Byte) 1/10 公斤
		ret.setPressure(this.parseIntFromBytes(data, 1, 1));
		// 3. byte[2-3] 胎温(Word) (N-27300)/100 度
		ret.setTemperature(calculateTemperature(this.parseIntFromBytes(data, 2, 2)));
		// 4. byte[4] 状态(Byte)
		// bit[0] 0：关，1;开(传感器)
		// bit[1-7] 保留
		final int statusField = this.parseIntFromBytes(data, 4, 1);
		ret.setStatusField(statusField);
		ret.setSensorOn(bitOperator.getBitAt(statusField, 0) == 1);
		// 5. byte[5] 保留(Word(16))
		ret.setReversed_5(this.parseIntFromBytes(data, 5, 2));
		// 6. byte[7] 压力阀测试(Byte)
		ret.setPressureValve(this.parseIntFromBytes(data, 7, 1));
		// 7. byte[8-13] 时间(BCD[6]) YY-MM-DD-hh-mm-ss
		ret.setDate(this.parseDateFromBytes(data, 8, 6));
		// 2、消息上传时间：终端采集到的时间也不准确，以服务器收到数据包时间为准。
		ret.setDate(new Date());
		// 8. byte[14-25] 轮胎胎号(BYTE[12])
		// ret.setTyreId(this.parseStringFromBytesSkipBlack(data, 14, 12));
		ret.setTyreId(String.valueOf(this.parseIntFromBytes(data, 14, 12)));
		// 9. byte[26-27] 电压(Word) 1/10V
		ret.setVoltage(this.parseIntFromBytes(data, 26, 2));
		// 10. byte[28-39] 传感器编号(BYTE[12])
		ret.setSensorNo(String.valueOf(this.parseIntFromBytes(data, 28, 12)));
		return ret;
	}

	private Date parseDateFromBytes(byte[] data, int start, int length) {
		String string = null;
		try {
			string = this.parseBcdStringFromBytes(data, start, length);
			return DATE_FORMAT.parse(string);
		} catch (ParseException e) {
			log.error("日期解析错误:{}", string);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * (N-27300)/100 度
	 * 
	 * @param temperature
	 * @return
	 */
	private int calculateTemperature(int temperature) {
		System.out.println(temperature + "__________");
		return (temperature - 27300) / 100;
	}

	private String parseStringFromBytesSkipBlack(byte[] data, int startIndex, int lenth) {
		return this.parseStringFromBytesSkipBlack(data, startIndex, lenth, null);
	}

	private String parseStringFromBytesSkipBlack(byte[] data, int startIndex, int lenth, String defaultVal) {
		if (startIndex >= data.length)
			throw new IndexOutOfBoundsException("bytes len = " + data.length + ", index = " + startIndex);
		if (startIndex + lenth > data.length)
			throw new IndexOutOfBoundsException("bytes len = " + data.length + ", index = " + startIndex + lenth);
		ByteArrayOutputStream baos = null;
		try {
			final int limit = startIndex + lenth;
			baos = new ByteArrayOutputStream();
			for (int i = startIndex; i < limit; i++) {
				if (data[i] != 0) {
					baos.write(data[i]);
				}
			}

			return new String(baos.toByteArray(), TPMSConsts.string_charset);
		} catch (Exception e) {
			log.error("解析字符串出错:{}", e.getMessage());
			e.printStackTrace();
			return defaultVal;
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected String toHexStringFromBytes(byte[] data, int startIndex, int length) {
		return this.toHexStringFromBytes(data, startIndex, length, null);
	}

	protected String toHexStringFromBytes(byte[] data, int startIndex, int length, String defaultVal) {
		try {
			byte[] tmp = new byte[length];
			System.arraycopy(data, startIndex, tmp, 0, length);
			return HexStringUtils.toHexString(tmp);
		} catch (Exception e) {
			log.error("解析字符串出错:{}", e.getMessage());
			e.printStackTrace();
			return defaultVal;
		}
	}

	protected String parseStringFromBytes(byte[] data, int startIndex, int lenth) {
		return this.parseStringFromBytes(data, startIndex, lenth, null);
	}

	private String parseStringFromBytes(byte[] data, int startIndex, int lenth, String defaultVal) {
		try {
			byte[] tmp = new byte[lenth];
			System.arraycopy(data, startIndex, tmp, 0, lenth);
			return new String(tmp, TPMSConsts.string_charset);
		} catch (Exception e) {
			log.error("解析字符串出错:{}", e.getMessage());
			e.printStackTrace();
			return defaultVal;
		}
	}

	private String parseBcdStringFromBytes(byte[] data, int startIndex, int lenth) {
		return this.parseBcdStringFromBytes(data, startIndex, lenth, null);
	}

	private String parseBcdStringFromBytes(byte[] data, int startIndex, int lenth, String defaultVal) {
		try {
			byte[] tmp = new byte[lenth];
			System.arraycopy(data, startIndex, tmp, 0, lenth);
			return this.bcd8421Operater.bcd2String(tmp);
		} catch (Exception e) {
			log.error("解析BCD(8421码)出错:{}", e.getMessage());
			e.printStackTrace();
			return defaultVal;
		}
	}

	private int parseIntFromBytes(byte[] data, int startIndex, int length) {
		return this.parseIntFromBytes(data, startIndex, length, 0);
	}

	private int parseIntFromBytes(byte[] data, int startIndex, int length, int defaultVal) {
		try {
			// 字节数大于4,从起始索引开始向后处理4个字节,其余超出部分丢弃
			final int len = length > 4 ? 4 : length;
			byte[] tmp = new byte[len];
			System.arraycopy(data, startIndex, tmp, 0, len);
			return bitOperator.byteToInteger(tmp);
		} catch (Exception e) {
			log.error("解析整数出错:{}", e.getMessage());
			e.printStackTrace();
			return defaultVal;
		}
	}

	private float parseFloatFromBytes(byte[] data, int startIndex, int length) {
		return this.parseFloatFromBytes(data, startIndex, length, 0f);
	}

	private float parseFloatFromBytes(byte[] data, int startIndex, int length, float defaultVal) {
		try {
			// 字节数大于4,从起始索引开始向后处理4个字节,其余超出部分丢弃
			final int len = length > 4 ? 4 : length;
			byte[] tmp = new byte[len];
			System.arraycopy(data, startIndex, tmp, 0, len);
			return bitOperator.byte2Float(tmp);
		} catch (Exception e) {
			log.error("解析浮点数出错:{}", e.getMessage());
			e.printStackTrace();
			return defaultVal;
		}
	}

	public List<TerminalParamQueryReplyMsgItem> toParamQueryReplyMsg(PackageData packageData) {
		byte[] data = new byte[packageData.getMsgBodyBytes().length - 3];
		System.arraycopy(packageData.getMsgBodyBytes(), 3, data, 0, data.length);
		System.out.println(HexStringUtils.toHexString(data));
		List<TerminalParamQueryReplyMsgItem> ret = new ArrayList<>();
		int i = 0;
		while (i < data.length) {
			TerminalParamQueryReplyMsgItem item = new TerminalParamQueryReplyMsgItem();

			// 参数ID
			final int id = this.parseIntFromBytes(data, i, 4);
			item.setCmd(this.toHexStringFromBytes(data, i, 4));
			i += 4;

			// 参数长度
			final int len = this.parseIntFromBytes(data, i, 1);
			i += 1;

			// 参数值
			byte[] valBs = new byte[len];
			System.arraycopy(data, i, valBs, 0, valBs.length);
			i += len;
			item.setHexValue(HexStringUtils.toHexString(valBs));
			// String
			if (id == 0x0013 || id == 0x0612 || id == 0x0701) {
				item.setValue(new String(valBs, TPMSConsts.string_charset));
			}
			// DWord
			else if (id == 0x0601 || id == 0x0602 || id == 0x0603 || id == 0x0610 || id == 0x0018) {
				item.setValue(this.parseIntFromBytes(valBs, 0, valBs.length));
			}
			// Byte[8]
			else if (id == 0x0611) {
				item.setValue(this.parseIntFromBytes(valBs, 0, valBs.length));
			}
			ret.add(item);
		}
		return ret;
	}

}
