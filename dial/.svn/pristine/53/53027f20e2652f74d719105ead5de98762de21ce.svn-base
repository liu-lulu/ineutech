package cn.kkbc.tpms.tcp;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.junit.Test;

import cn.kkbc.tpms.tcp.util.HexStringUtils;

public class BaseTest {

	@Test
	public void test1(){
		byte[]bs=new byte[]{0x38,(byte) 0xa3};
		System.out.println(new String(bs));
		
		System.out.println(Arrays.toString("028".getBytes()));
	}
	
	@Test
	public void test2() throws UnsupportedEncodingException{
		String hex="38613937663334613539";
		System.out.println(Arrays.toString(hex.getBytes()));
		byte[] bs = HexStringUtils.chars2Bytes(hex.toCharArray());
		System.out.println(Arrays.toString(bs));
		System.out.println(new String(bs,"UTF-8"));
	}
}
