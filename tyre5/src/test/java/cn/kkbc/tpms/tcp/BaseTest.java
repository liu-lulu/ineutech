package cn.kkbc.tpms.tcp;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.junit.Test;

import cn.kkbc.tpms.tcp.util.BCD8421Operater;
import cn.kkbc.tpms.tcp.util.BitOperator;
import cn.kkbc.tpms.tcp.util.HexStringUtils;

public class BaseTest {

	@Test
	public void test1() {
		byte[] bs = new byte[] { 0x38, (byte) 0xa3 };
		System.out.println(new String(bs));

		System.out.println(Arrays.toString("028".getBytes()));
	}

	@Test
	public void test2() throws UnsupportedEncodingException {
		String hex = "D4C14238383838";
		System.out.println(Arrays.toString(hex.getBytes()));
		byte[] bs = HexStringUtils.chars2Bytes(hex.toCharArray());
		System.out.println(Arrays.toString(bs));
		System.out.println(new String(bs, "GBK"));
	}

	@Test
	public void test3() {
		String str = "FE7E00035222";
		BCD8421Operater bcd8421Operater = new BCD8421Operater();
		String ret = bcd8421Operater.bcd2String(HexStringUtils.chars2Bytes(str.toCharArray()));
		System.out.println(ret);

		try {
			System.out.println(new SimpleDateFormat("yyMMddHHmmss").parse("000000003136"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test4() {
		long port = 65535;//4
		BitOperator bitOperator = new BitOperator();
		byte[] bs = bitOperator.longToBytes(port, 4);
		System.out.println(Arrays.toString(bs));
		System.out.println(bitOperator.bytes2Long(bs));
		System.out.println(bitOperator.bytes2Long(new byte[] { 0, 0, -1, -1 }));
		System.out.println(bitOperator.bytes2Long(new byte[] { 0, 0, 0, -1, -1 }));

		String ip = "120.132.13.158";// max=15
		bs = ip.getBytes(TPMSConsts.string_charset);
		System.out.println(Arrays.toString(bs));
		System.out.println(bs.length);
		System.out.println(new String(bs, TPMSConsts.string_charset));
		
		System.out.println("===========");
		String no="沪A12345";//8
		bs=no.getBytes(TPMSConsts.string_charset);
		System.out.println(Arrays.toString(bs));
		System.out.println(new String(bs,TPMSConsts.string_charset));
		
		System.out.println("============");
		
	}
}
