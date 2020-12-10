package com.psylife.util;

import java.util.Arrays;
import java.util.List;

import com.psylife.hardware.data.PackageData;

public class ToolsForByte {
	

	/**
	 * 把一个整形该为byte
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte integerToOneByte(int value) throws Exception {
		return (byte) (value & 0xFF);
	}

	/**
	 * 把一个整形该为1位的byte数组
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] integerToOneBytes(int value) throws Exception {
		byte[] result = new byte[1];
		result[0]=(byte) (value & 0xFF);
		return result;
	}
	
	/**
	 * 把一个整形改为2位的byte数组
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] integerToTwoBytes(int value) throws Exception {
		byte[] result = new byte[2];
		result[0] = (byte) ((value >>> 8) & 0xFF);
		result[1] = (byte) (value & 0xFF);
		return result;
	}
	
	/**
	 * 把一个整形改为3位的byte数组
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] integerToThreeFourBytes(int value) throws Exception {
		byte[] result = new byte[3];
//		if ((value > Math.pow(2, 63)) || (value < 0)) {
//			throw new Exception("Integer value " + value
//					+ " is larger than 2^63");
//		}
		result[0] = (byte) ((value >>> 16) & 0xFF);
		result[1] = (byte) ((value >>> 8) & 0xFF);
		result[2] = (byte) (value & 0xFF);
		return result;
	}

	/**
	 * 把一个整形改为4位的byte数组
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] integerToFourBytes(int value) throws Exception {
		byte[] result = new byte[4];
//		if ((value > Math.pow(2, 63)) || (value < 0)) {
//			throw new Exception("Integer value " + value
//					+ " is larger than 2^63");
//		}
		result[0] = (byte) ((value >>> 24) & 0xFF);
		result[1] = (byte) ((value >>> 16) & 0xFF);
		result[2] = (byte) ((value >>> 8) & 0xFF);
		result[3] = (byte) (value & 0xFF);
		return result;
	}
	
	/**
	 * 把byte[]转化位整形,通常为指令用
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public int byteToInteger(byte[] value) throws Exception {
		int result;
		if(value.length==1){
			result=oneByteToInteger(value[0]);
		}else if(value.length==2){
			result=twoBytesToInteger(value);
		}else if(value.length==3){
			result=threeBytesToInteger(value);
		}else if(value.length==4){
			result=fourBytesToInteger(value);
		}else{
			result=fourBytesToInteger(value);
		}
		return result;
	}
	

	/**
	 * 把一个byte转化位整形,通常为指令用
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public int oneByteToInteger(byte value) {
		return (int) value & 0xFF;
	}

	/**
	 * 把一个2位的数组转化位整形
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public int twoBytesToInteger(byte[] value) throws Exception {
//		if (value.length < 2) {
//			throw new Exception("Byte array too short!");
//		}
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		return ((temp0 << 8) + temp1);
	}
	
	/**
	 * 把一个3位的数组转化位整形
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public int threeBytesToInteger(byte[] value) throws Exception {
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		int temp2 = value[2] & 0xFF;
		return ((temp0 << 16) + (temp1 <<8 ) + temp2);
	}

	/**
	 * 把一个4位的数组转化位整形,通常为指令用
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public int fourBytesToInteger(byte[] value) throws Exception {
//		if (value.length < 4) {
//			throw new Exception("Byte array too short!");
//		}
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		int temp2 = value[2] & 0xFF;
		int temp3 = value[3] & 0xFF;
		return (( temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
	}
	
	/**
	 * 把一个4位的数组转化位整形
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long fourBytesToLong(byte[] value) throws Exception {
//		if (value.length < 4) {
//			throw new Exception("Byte array too short!");
//		}
		int temp0 = value[0] & 0xFF;
		int temp1 = value[1] & 0xFF;
		int temp2 = value[2] & 0xFF;
		int temp3 = value[3] & 0xFF;
		return (((long) temp0 << 24) + (temp1 << 16) + (temp2 << 8) + temp3);
	}
	/**
	 * 把一个数组转化长整形
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public long bytesToLong(byte[] value) throws Exception {
		long result=0;
		int len=value.length;
		int temp;
		for(int i=0;i<len;i++){
			temp=(len-1-i)*8;
			if(temp==0){
				result+=(value[i] & 0x0ff);
			}else{
				result+=(value[i] & 0x0ff)<<temp;
			}
		}
		return result;
	}
	
	/**
	 * 把一个长整形改为byte数组
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] longToBytes(long value) throws Exception {
		return longToBytes(value,8);
	}
	/**
	 * 把一个长整形改为byte数组
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public byte[] longToBytes(long value,int len) throws Exception {
		byte[] result = new byte[len];
		int temp;
		for(int i=0;i<len;i++){
			temp=(len-1-i)*8;
			if(temp==0){
				result[i]+=(value & 0x0ff);
			}else{
				result[i]+=(value>>> temp) & 0x0ff;
			}
		}
		return result;
	}

	/**
	 * 得到一个消息ID
	 * @return
	 * @throws Exception
	 */
	public byte[] generateTransactionID() throws Exception {
		byte[] id = new byte[16];
		System.arraycopy(integerToTwoBytes((int) (Math.random() * 65536)), 0,
				id, 0, 2);
		System.arraycopy(integerToTwoBytes((int) (Math.random() * 65536)), 0,
				id, 2, 2);
		System.arraycopy(integerToTwoBytes((int) (Math.random() * 65536)), 0,
				id, 4, 2);
		System.arraycopy(integerToTwoBytes((int) (Math.random() * 65536)), 0,
				id, 6, 2);
		System.arraycopy(integerToTwoBytes((int) (Math.random() * 65536)), 0,
				id, 8, 2);
		System.arraycopy(integerToTwoBytes((int) (Math.random() * 65536)), 0,
				id, 10, 2);
		System.arraycopy(integerToTwoBytes((int) (Math.random() * 65536)), 0,
				id, 12, 2);
		System.arraycopy(integerToTwoBytes((int) (Math.random() * 65536)), 0,
				id, 14, 2);
		return id;
	}

	/**
	 * 把IP拆分位int数组
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public int[] getIntIPValue(String ip) throws Exception {
		String[] sip = ip.split("[.]");
//		if (sip.length != 4) {
//			throw new Exception("error IPAddress");
//		}
		int[] intIP = { Integer.parseInt(sip[0]), Integer.parseInt(sip[1]),
				Integer.parseInt(sip[2]), Integer.parseInt(sip[3]) };
		return intIP;
	}

	/**
	 * 把byte类型IP地址转化位字符串
	 * @param address
	 * @return
	 * @throws Exception
	 */
	public String getStringIPValue(byte[] address) throws Exception {
		int first = this.oneByteToInteger(address[0]);
		int second = this.oneByteToInteger(address[1]);
		int third = this.oneByteToInteger(address[2]);
		int fourth = this.oneByteToInteger(address[3]);

		return first + "." + second + "." + third + "." + fourth;
	}
	/**
	 * 合并字节数组
	 * @param first
	 * @param rest
	 * @return
	 */
	public byte[] concatAll(byte[] first, byte[]... rest) {
		  int totalLength = first.length;
		  for (byte[] array : rest) {
			  if(array!=null){
				  totalLength += array.length; 
			  }
		  }
		  byte[] result = Arrays.copyOf(first, totalLength);
		  int offset = first.length;
		  for (byte[] array : rest) {
			  if(array!=null){
				  System.arraycopy(array, 0, result, offset, array.length);
				    offset += array.length;
			  }
		  }
		  return result;
	}
	
	/**
	 * 合并字节数组
	 * @param rest
	 * @return
	 */
	public byte[] concatAll(List<byte[]> rest) {
		  int totalLength = 0;
		  for (byte[] array : rest) {
			  if(array!=null){
				  totalLength += array.length; 
			  }
		  }
		  byte[] result =new  byte[totalLength];
		  int offset = 0;
		  for (byte[] array : rest) {
			  if(array!=null){
				  System.arraycopy(array, 0, result, offset, array.length);
				    offset += array.length;
			  }
		  }
		  return result;
	}
	private static ToolsForByte tools = new ToolsForByte();
	public static String packageData2HexString(PackageData data) throws Exception {
		byte[] bs = tools.concatAll(Arrays.asList(//
				tools.integerToThreeFourBytes(data.getHead()),//3 NWX
				new byte[]{tools.integerToOneByte(data.getCommand())},//1
				tools.integerToTwoBytes(data.getDataLen()),//2
				tools.integerToTwoBytes(data.getNo()),//2
				data.getPhone().getBytes("ASCII"),//8
				data.getData(),//data
				tools.integerToTwoBytes(data.getCheckSum())//
		));
		return HexStringUtil.encodeHexStr(bs, false);
	}
}

