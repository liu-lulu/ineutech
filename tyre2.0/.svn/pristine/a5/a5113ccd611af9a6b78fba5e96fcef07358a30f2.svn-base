package test;

import com.psylife.util.ToolsForByte;

public class Test3 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 ToolsForByte toolsForByte=new ToolsForByte();
		 byte pData[] = { (byte)0XEE, (byte)0XFA, (byte)0XC7, (byte)0XC2 };
//		 byte pData[] = {  (byte)0XC2, (byte)0XC7, (byte)0XFA,(byte)0XEE };
		 
		  System.out.println(Float.intBitsToFloat((( (pData[3] & 0xFF) << 24) + ((pData[2] & 0xFF) << 16) + ((pData[1] & 0xFF) << 8) + (pData[0] & 0xFF))));
	}

}
