package com.psylife.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 校验和
 * @author Administrator
 *
 */
public class CheckSumUtil {

	/**
	 * 一般通信数据校验和，nwx采用，脑波除传数据包外采用
	 * @param dest
	 * @return
	 */
	public int getCheckSumByCmd(byte[] dest){
		return getCheckSum(dest,4,dest.length-3);
	}
	
	
	public int getCheckSum(byte[] dest,int beginIndex,int endIndex){
		int CRC = 0x0000ffff;
	    int i,temp;
	    for(int j=beginIndex;j<=endIndex;j++)
	    {
	        CRC = CRC^((int)dest[j]&0xff);
	        for(i=0;i<8;i++)
	        {
	        	if((CRC&0x0001)==0){
	        		CRC>>=1;
	        	}else{
	        		CRC = (CRC>>1)^0xa001;
	        	}
	        }
	    }
	    temp = CRC&0xff;
	    CRC = ((CRC>>8)&0xff)+(temp<<8);     
	    return(CRC);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {//            344+128+58   530
		String str="4E57580200FE00483030303030343930000112313630353139313433323036010000803F0000A04000000000000000009A994141CDCCDC409A994141CDCCDC409A994141CDCCDC409A994141CDCCDC4000008C42ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C26666B6400000C841ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C2ElFAC7C22528";
		for(int i=0;i<str.length();i+=2){
			System.out.print(",(byte)0X"+str.charAt(i)+str.charAt(i+1));
		}
		
		
//		byte[] bb=new byte[]{(byte)0X4E ,(byte)0X57 ,(byte)0X58 ,(byte)0X0E ,(byte)0X00, (byte)0x0A ,(byte)0X00,(byte)0X46,(byte)0X30,(byte)0X30,(byte)0X30,(byte)0X30,(byte)0X30,(byte)0X34,(byte)0X39,(byte)0X30,(byte)0X75,(byte)0XD0};// 43 
		byte[] bb=new byte[]{(byte)0X4E,(byte)0X57,(byte)0X58,(byte)0X02,(byte)0X00,(byte)0XFE,(byte)0X00,(byte)0X48,(byte)0X30,(byte)0X30,(byte)0X30,(byte)0X30,(byte)0X30,(byte)0X34,(byte)0X39,(byte)0X30,(byte)0X00,(byte)0X01,(byte)0X12,(byte)0X31,(byte)0X36,(byte)0X30,(byte)0X35,(byte)0X31,(byte)0X39,(byte)0X31,(byte)0X34,(byte)0X33,(byte)0X32,(byte)0X30,(byte)0X36,(byte)0X01,(byte)0X00,(byte)0X00,(byte)0X80,(byte)0X3F,(byte)0X00,(byte)0X00,(byte)0XA0,(byte)0X40,(byte)0X00,(byte)0X00,(byte)0X00,(byte)0X00,(byte)0X00,(byte)0X00,(byte)0X00,(byte)0X00,(byte)0X9A,(byte)0X99,(byte)0X41,(byte)0X41,(byte)0XCD,(byte)0XCC,(byte)0XDC,(byte)0X40,(byte)0X9A,(byte)0X99,(byte)0X41,(byte)0X41,(byte)0XCD,(byte)0XCC,(byte)0XDC,(byte)0X40,(byte)0X9A,(byte)0X99,(byte)0X41,(byte)0X41,(byte)0XCD,(byte)0XCC,(byte)0XDC,(byte)0X40,(byte)0X9A,(byte)0X99,(byte)0X41,(byte)0X41,(byte)0XCD,(byte)0XCC,(byte)0XDC,(byte)0X40,(byte)0X00,(byte)0X00,(byte)0X8C,(byte)0X42,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0X66,(byte)0X66,(byte)0XB6,(byte)0X40,(byte)0X00,(byte)0X00,(byte)0XC8,(byte)0X41,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0XEl,(byte)0XFA,(byte)0XC7,(byte)0XC2,(byte)0X25,(byte)0X28};
		System.out.println("数据长度:"+bb.length);
		CheckSumUtil checkSumUtil=new CheckSumUtil();
		int dd=checkSumUtil.getCheckSumByCmd(bb);
		ToolsForByte tool=new ToolsForByte();
		try {
			byte[] t=tool.integerToTwoBytes(dd);
			String tt=HexStringUtil.encodeHexStr(t, false);
			String dates="\n"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			System.out.println("\n检验输出:"+tt+dates);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dd);
	}
	
	
}
