package com.psylife.util;

import java.util.Date;

import net.sf.json.JsonConfig;

public class JsonConfigUtil extends JsonConfig {
	public static JsonConfig getJsonDateValueFormatConfig(){
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());  
		return jsonConfig;
	}
	
	public static JsonConfig getJsonFloatValueFormatConfig(){
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.registerJsonValueProcessor(Float.class, new JsonFloatValueProcessor());  
		return jsonConfig;
	}
}
