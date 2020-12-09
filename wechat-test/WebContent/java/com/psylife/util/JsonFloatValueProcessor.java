package com.psylife.util;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonFloatValueProcessor implements JsonValueProcessor {

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return arg0;
	}

	@Override
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {  
        
        if (value instanceof Float) {  
            return roundHalfUp((Float)value, 3);  
        }  
          
        return value;  
    }  
      
    /** 
     * 四舍五入。 
     *  
     * @param number 数值 
     * @return 舍入后的数值 
     * @see java.text.RoundingMode.HALF_UP 
     */  
    public String roundHalfUp(double number, int frac) {  
        NumberFormat fmt = NumberFormat.getInstance(Locale.CHINA);  
          
        fmt.setMaximumFractionDigits(frac);  
        fmt.setRoundingMode(RoundingMode.HALF_UP);  
          
        return fmt.format(number);  
    }  

}
