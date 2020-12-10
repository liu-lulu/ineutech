package cn.ineutech.tpms.tcp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import cn.ineutech.tpms.tcp.PackageDataConstant;

/**
 * 
 * @name: PackageUtil 
 * @description: 解析脑电数据
 * @date 2018年2月1日 下午2:46:36
 * @author liululu
 */
public class PackageUtil {
	
	public byte[] getProData(byte[] data, byte[] shengyu){
		System.out.println(ArrayUtils.toString(data));
		shengyu = data;
		int proIndex=getIndex(data, PackageDataConstant.PRO_DATA_HEAD_ARRAY,0);
		if (proIndex!=-1&&proIndex+36<=data.length) {
			byte[] pro=new byte[36];
			System.arraycopy(data, proIndex, pro, 0, 36);
			
			byte[] shengyuData=new byte[data.length-proIndex-36];//剩余数据
			System.arraycopy(data, data.length-proIndex-36, shengyuData, 0, data.length-proIndex-36);
			shengyu = shengyuData;
			System.out.println("本次数据:"+HexStringUtils.toHexString(pro)+"--剩余数据:"+HexStringUtils.toHexString(shengyu));
			return pro;
		}
		return null;
	}
	
	public static void main(String[] args) {
		PackageUtil util=new PackageUtil();
		byte[] data= {(byte) 0xAA,(byte) 0xAA, 0x20, 0x02 ,0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0A,0x0B,0x0C,0x0D,0x0E,0x0F,0x10,0x20,0x30,0x40,0x50,0x60,0x70,(byte) 0x80,(byte) 0x90,(byte) 0xA0,(byte) 0xB0,(byte)0xC0,(byte)0xD0,(byte)0xE0,(byte)0xF0,(byte)0x11,0x20, 0x02 };
		byte[] shengyu = null;
		util.getProData(data, shengyu);
	}
	
	/**
	 * 从data中提炼出大包数据和小包数据
	 * @param data
	 * @return map中: key-->"1":小包  "2":大包
	 */
	public Map<String,List<byte[]>> getPackageData(byte[] data){
		Map<String,List<byte[]>> ret=new HashMap<String, List<byte[]>>();
		byte[] ori=null;
		
		int oriIndex;
		int startIndex=0;
		
		while ((oriIndex=getIndex(data, PackageDataConstant.ORI_DATA_HEAD_ARRAY,startIndex))!=-1&&data.length-oriIndex>8) {
			ori=new byte[8];
			System.arraycopy(data, oriIndex, ori, 0, 8);
			if (ret.get("1")==null) {
				List<byte[]> list=new ArrayList<byte[]>();
				list.add(ori);
				ret.put("1", list);
			}else {
				ret.get("1").add(ori);
			}
			
			startIndex+=8;
		}
		
		int proIndex=getIndex(data, PackageDataConstant.PRO_DATA_HEAD_ARRAY,0);
		if (proIndex!=-1&&proIndex+36<data.length) {
			byte[] pro=new byte[36];
			System.arraycopy(data, proIndex, pro, 0, 36);
			if (ret.get("2")==null) {
				List<byte[]> list=new ArrayList<byte[]>();
				list.add(pro);
				ret.put("2", list);
			}
		}
		
		return ret;
	}
	
	/**
	 * 从data中分离出一组数据，并获取剩余数据
	 * @param data
	 * @return map中: key-->"1":组数据  "2":剩余数据
	 */
	public Map<String, byte[]> getRowDataAndShengyu(byte[] data){
		Map<String, byte[]> ret=new HashMap<String, byte[]>();
		
		int oriIndex=0;
		int oriCount=0;
		while (oriCount<=511) {//获取第512个小包的索引
			int tempIndex=getIndex(data, PackageDataConstant.ORI_DATA_HEAD_ARRAY,oriIndex);
			if (tempIndex!=-1) {
				oriCount++;
				oriIndex=tempIndex+8;
			}else {
				oriIndex++;
			}
		}
		
		int proIndex=getIndex(data, PackageDataConstant.PRO_DATA_HEAD_ARRAY, 0);//获取大包的索引
		
		byte[] rowData=null;//获取一组数据(包括512个小包和1个大包)
		if (oriIndex>proIndex) {
			
			rowData=new byte[oriIndex];
			System.arraycopy(data, 0, rowData, 0, oriIndex);
			
		}else if (oriIndex<=proIndex) {
			rowData=new byte[proIndex+36];
			System.arraycopy(data, 0, rowData, 0, proIndex+36);
		}
		byte[] shengyuData=new byte[data.length-rowData.length];//剩余数据
		System.arraycopy(data, rowData.length, shengyuData, 0, data.length-rowData.length);
		
		ret.put("1", rowData);
		ret.put("2", shengyuData);
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
	
	/**
	 * 将rest中的数据按顺序添加到firse中
	 */
	public byte[] arrayAddToAnother(byte[] first, byte[][] rest) {
		int totalLength = first.length;
		byte[][] arrayOfByte = rest;

		int j = arrayOfByte.length;

		for (int i = 0; i < j; i++) {
			byte[] array = arrayOfByte[i];
			if (array != null) {
				totalLength += array.length;
			}
		}
		byte[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		// int first = rest.length;
		for (int i = 0; i < rest.length; i++) {
			byte[] array = rest[i];
			if (array != null) {
				System.arraycopy(array, 0, result, offset, array.length);
				offset += array.length;
			}
		}
		return result;
	}
	
	public static int getLastIndex(byte[] src , byte[] data){
		if (src.length==0||data.length==0) {
			return -1;
		}
		
		BitOperator operator=new BitOperator();
		
		String srcString=HexStringUtils.toHexString(src);
		String dataString=HexStringUtils.toHexString(data);
		int index=srcString.lastIndexOf(dataString);
		
		if (index!=-1) {

			index=index/2;
			if (src.length-index>=7) {//每条指令最小长度为7
				
				byte[] two=new byte[2];
				System.arraycopy(src, index+4, two, 0, 2);
				int dataLength=operator.twoBytesToInteger(two);
				
				if (src.length-index>=7+dataLength) {
					return index+7+dataLength;
				}
				
			}
		
		}
		
		return index;
		
	}
	
//	protected ByteBuf getByteBuf(byte[] arr) {
//		ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(arr.length);
//		byteBuf.writeBytes(arr);
//		return byteBuf;
//	}

}
