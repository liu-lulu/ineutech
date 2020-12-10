package com.psylife.hardware.protocol.constant;

/**
 * NWX数据包头
 *
 */
public class PackageHeadConstant {
	
	/**
	 * NWX包头,NWX的ASCII值4e,57,58
	 */
	public static final int PACKAGE_HEAD_NWX = 0x004e5758;
	
	/**
	 * NWX包头，二进制数组
	 */
	public static final byte[] PACKAGE_HEAD_NWX_ARRAY = {(byte)0x4E,(byte)0x57,(byte)0x58};
	
	
}
