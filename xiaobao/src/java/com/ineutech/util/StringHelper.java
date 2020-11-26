package com.ineutech.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 
 * @name: StringHelper 
 * @description: 个人工具类
 * @date 2018年2月1日 下午4:18:15
 * @author liululu
 */
public final class StringHelper {

	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}
	
}
