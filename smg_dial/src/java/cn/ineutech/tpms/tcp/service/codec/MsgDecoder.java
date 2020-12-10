package cn.ineutech.tpms.tcp.service.codec;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ineutech.tpms.tcp.PackageDataConstant;
import cn.ineutech.tpms.tcp.util.BitOperator;
import cn.ineutech.tpms.tcp.util.HexStringUtils;
import cn.ineutech.tpms.tcp.vo.BrainPackageData;

import com.ineutech.entity.DeviceData;

/**
 * 
 * @name: MsgDecoder 
 * @description: 解析脑电数据
 * @date 2018年2月1日 下午2:15:50
 * @author liululu
 */
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
		
		if (dataStr.contains(HexStringUtils.toHexString(PackageDataConstant.DATA_HEAD_ARRAY))) {
			if (dataStr.contains(HexStringUtils.toHexString(PackageDataConstant.PRO_DATA_HEAD_ARRAY))) {
				return 2;
			}
			
			if (dataStr.contains(HexStringUtils.toHexString(PackageDataConstant.ORI_DATA_HEAD_ARRAY))) {
				return 3;
			}
		}else if (dataStr.contains(HexStringUtils.toHexString(PackageDataConstant.CMD_HEAD_ARRAY))) {
			return 1;
		} else {
			log.info("数据包头没有匹配的"+dataStr);
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
				deviceData.setLowAlpha(bitOperator.byteToInteger(threeb));
				System.arraycopy(temp, 16, threeb, 0, 3);
				deviceData.setHighAlpha(bitOperator.byteToInteger(threeb));
				System.arraycopy(temp, 19, threeb, 0, 3);
				deviceData.setLowBeta(bitOperator.byteToInteger(threeb));
				System.arraycopy(temp, 22, threeb, 0, 3);
				deviceData.setHighBeta(bitOperator.byteToInteger(threeb));
				System.arraycopy(temp, 25, threeb, 0, 3);
				deviceData.setLowGamma(bitOperator.byteToInteger(threeb));
				System.arraycopy(temp, 28, threeb, 0, 3);
				deviceData.setMiddleGamma(bitOperator.byteToInteger(threeb));
				
				System.arraycopy(temp, 32, oneb, 0, 1);
				deviceData.setAttention(bitOperator.byteToInteger(oneb));
				System.arraycopy(temp, 34, oneb, 0, 1);
				deviceData.setMeditation(bitOperator.byteToInteger(oneb));
				
//				try {
					
					double ognition=(double)deviceData.getLowBeta()/(double)deviceData.getTheta();
					deviceData.setCognition(ognition>1d?1:ognition);
					double nervous=(double)deviceData.getLowAlpha()/((double)deviceData.getHighAlpha()+(double)deviceData.getLowAlpha());
					deviceData.setNervous(nervous);
					int fatigue=1+(deviceData.getTheta()/deviceData.getHighBeta());
					deviceData.setFatigue(fatigue>100?100:fatigue);
					
//				} catch (Exception e) {
//					e.printStackTrace();
//					log.error("----"+deviceData.toString());
//				}
				
				
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
	
	/**
	 * 从src中获取data第一次出现的索引
	 * @param src
	 * @param data
	 * @param startIndex
	 * @return
	 */
	public int getIndex(byte[] src,byte[] data,int startIndex){
		if (src==null||data==null) {
			return -1;
		}
		
		for (int i=startIndex;i<src.length;i++) {
			boolean flag=true;
			for (int j = 0; j < data.length; j++) {
				if (i+j>=src.length||(i+j<src.length&&data[j]!=src[i+j])) {
					flag=false;
					break;
				}
			}
			if (flag) {
				return i;
			}
		}
		
		return -1;
	}
	
	public Date byteToDate(byte[] time){
		if (time==null||time.length<8) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
//		cal.setFirstDayOfWeek(Calendar.MONDAY);
        
        cal.set(Calendar.HOUR_OF_DAY,time[0]);//时
        cal.set(Calendar.MINUTE,time[1]);//分
        cal.set(Calendar.SECOND,time[2]);//秒
        
        cal.set(Calendar.DAY_OF_WEEK, time[4]+1);
        
    	cal.set(Calendar.YEAR,time[7]+2000);      //年
		cal.set(Calendar.MONTH,time[5]-1);//月
        cal.set(Calendar.DATE,time[6]);       //日
        return cal.getTime();
		
	}

}
