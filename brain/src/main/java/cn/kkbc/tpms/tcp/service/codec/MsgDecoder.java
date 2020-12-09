package cn.kkbc.tpms.tcp.service.codec;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.constant.PackageDataConstant;
import cn.kkbc.tpms.tcp.util.BitOperator;
import cn.kkbc.tpms.tcp.util.HexStringUtils;
import cn.kkbc.tpms.tcp.vo.BrainPackageData;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.kkbc.entity.DeviceData;

public class MsgDecoder {

	private static final Logger log = LoggerFactory.getLogger(MsgDecoder.class);

	private BitOperator bitOperator;

	public MsgDecoder() {
		this.bitOperator = new BitOperator();
	}
	
	/**
	 * 根据包头分析上传的数据类型
	 * @param data 上传数据
	 * @return 1：命令型数据  2：处理后的数据包  3：原始数据包
	 */
	public int analyzeData(byte[] data){
		String dataStr=HexStringUtils.toHexString(data);
		
		if (dataStr.contains(HexStringUtils.toHexString(PackageDataConstant.DATA_HEAD_ARRAY))&&data.length>=4132) {
			if (dataStr.contains(HexStringUtils.toHexString(PackageDataConstant.PRO_DATA_HEAD_ARRAY))) {
				return 2;
			}
			
			if (dataStr.contains(HexStringUtils.toHexString(PackageDataConstant.ORI_DATA_HEAD_ARRAY))) {
				return 3;
			}
		}else if (dataStr.contains(HexStringUtils.toHexString(PackageDataConstant.CMD_HEAD_ARRAY))) {
			return 1;
		} else {
			log.info("数据包头没有匹配的");
		}
		
		return -1;
	}
	
	public List<BrainPackageData> parse2CmdData(byte[] data){
		
		List<BrainPackageData> cmdDatas=new ArrayList<BrainPackageData>();
		
		for (int i = 0; i < data.length; i++) {
			if (data[i]==(byte)0xA5&&data[i+1]==(byte)0x5A) {
				BrainPackageData brainPackageData=new BrainPackageData();
				brainPackageData.setCommand(data[i+2]);
				brainPackageData.setDataLen(data[i+3]);
				brainPackageData.setData(getByteFromToEnd(data, i+4, i+4+brainPackageData.getDataLen()));
				brainPackageData.setCheckSum(data[i+4+brainPackageData.getDataLen()]);
				
				cmdDatas.add(brainPackageData);
				
				i=i+4+brainPackageData.getDataLen();
				
			}
			
		}
		
		return cmdDatas;
	}
	public DeviceData parse2Data1(byte[] data) {
		DeviceData deviceData=new DeviceData();
		if (data.length<36) {
			return null;
		}
		for (int i = 0; i < data.length; i++) {
			if (data[i]==(byte)0xAA&&data[i+1]==(byte)0xAA&&data[i+2]==(byte)0x20&&data[i+3]==(byte)0x02) {
				byte[] temp=new byte[36];
				System.arraycopy(data, i, temp, 0, 36);
				
				byte[] oneb=new byte[1];
				byte[] twob=new byte[2];
				
				System.arraycopy(temp, 4, oneb, 0, 1);
				deviceData.setSignal(bitOperator.byteToInteger(oneb));
				System.arraycopy(temp, 7, twob, 0, 2);
				deviceData.setDelta(bitOperator.byteToInteger(twob));
				System.arraycopy(temp, 10, twob, 0, 2);
				deviceData.setTheta(bitOperator.byteToInteger(twob));
				System.arraycopy(temp, 13, twob, 0, 2);
				deviceData.setLow_alpha(bitOperator.byteToInteger(twob));
				System.arraycopy(temp, 16, twob, 0, 2);
				deviceData.setHigh_alpha(bitOperator.byteToInteger(twob));
				System.arraycopy(temp, 19, twob, 0, 2);
				deviceData.setLow_beta(bitOperator.byteToInteger(twob));
				System.arraycopy(temp, 22, twob, 0, 2);
				deviceData.setHigh_beta(bitOperator.byteToInteger(twob));
				System.arraycopy(temp, 25, twob, 0, 2);
				deviceData.setLow_gamma(bitOperator.byteToInteger(twob));
				System.arraycopy(temp, 28, twob, 0, 2);
				deviceData.setMiddle_gamma(bitOperator.byteToInteger(twob));
				
				System.arraycopy(temp, 32, oneb, 0, 1);
				deviceData.setAttention(bitOperator.byteToInteger(oneb));
				System.arraycopy(temp, 34, oneb, 0, 1);
				deviceData.setMeditation(bitOperator.byteToInteger(oneb));
				
				break;
			}
		}
		
		return deviceData;
	}
	public DeviceData parse2Data(byte[] data) {
		DeviceData deviceData=new DeviceData();
		if (data.length<36) {
			return null;
		}
		for (int i = 0; i < data.length; i++) {
			if (data[i]==(byte)0xAA&&data[i+1]==(byte)0xAA&&data[i+2]==(byte)0x20&&data[i+3]==(byte)0x02) {
				byte[] temp=new byte[36];
				System.arraycopy(data, i, temp, 0, 36);
				
				byte[] oneb=new byte[1];
				byte[] threeb=new byte[3];
				
				System.arraycopy(temp, 4, oneb, 0, 1);
				deviceData.setSignal(bitOperator.byteToInteger(oneb));
				System.arraycopy(temp, 7, threeb, 0, 3);
				deviceData.setDelta(bitOperator.byteToInteger(threeb));
				System.arraycopy(temp, 10, threeb, 0, 3);
				deviceData.setTheta(bitOperator.byteToInteger(threeb));
				System.arraycopy(temp, 13, threeb, 0, 3);
				deviceData.setLow_alpha(bitOperator.byteToInteger(threeb));
				System.arraycopy(temp, 16, threeb, 0, 3);
				deviceData.setHigh_alpha(bitOperator.byteToInteger(threeb));
				System.arraycopy(temp, 19, threeb, 0, 3);
				deviceData.setLow_beta(bitOperator.byteToInteger(threeb));
				System.arraycopy(temp, 22, threeb, 0, 3);
				deviceData.setHigh_beta(bitOperator.byteToInteger(threeb));
				System.arraycopy(temp, 25, threeb, 0, 3);
				deviceData.setLow_gamma(bitOperator.byteToInteger(threeb));
				System.arraycopy(temp, 28, threeb, 0, 3);
				deviceData.setMiddle_gamma(bitOperator.byteToInteger(threeb));
				
				System.arraycopy(temp, 32, oneb, 0, 1);
				deviceData.setAttention(bitOperator.byteToInteger(oneb));
				System.arraycopy(temp, 34, oneb, 0, 1);
				deviceData.setMeditation(bitOperator.byteToInteger(oneb));
				
				break;
			}
		}
		
		return deviceData;
	}
	
	public static byte[] getByteFromToEnd(byte[] data,int startIndex,int endIndex){
		byte[] ret=new byte[endIndex-startIndex];
		if (startIndex>data.length||endIndex>data.length) {
			return null;
		}
		for (int i = startIndex; i < endIndex; i++) {
			ret[i-startIndex]=data[i];
		}
		
		return ret;
	}

}
