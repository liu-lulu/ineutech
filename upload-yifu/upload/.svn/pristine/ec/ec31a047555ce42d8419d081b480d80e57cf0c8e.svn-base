package com.kkbc.util;

import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestUtil {
	
	public static void parameterProccess(Map<String, Object> paramaterMap,HttpServletRequest request){
		if (paramaterMap != null) {
			for (String key : paramaterMap.keySet()) {
				request.setAttribute(key, paramaterMap.get(key));
			}
		}
	}
	
	public static void error404(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException{
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		request.getRequestDispatcher("/views/common/error.jsp").forward(request,response);
	}
	
	    /**       
	     * 描述:获取 request 中请求的内容 
	     * <pre> 
	     * 举例： 
	     * </pre> 
	     * @param request 
	     * @return 
	     * @throws IOException       
	     */  
	    public static String getRequestQueryString(HttpServletRequest request)  
	            throws IOException {  
	        String submitMehtod = request.getMethod();  
	        // GET  
	        if (submitMehtod.equals("GET")) {  
	            return new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8");  
	        // POST  
	        } else {  
	            return getRequestPostStr(request);  
	        }  
	    }  
	      
	    /*** 
	     * 获取 request 中 json 字符串的内容 
	     *  
	     * @param request 
	     * @return : <code>byte[]</code> 
	     * @throws IOException 
	     */  
	    public static String getRequestJsonString(HttpServletRequest request)  
	            throws IOException {  
	        String submitMehtod = request.getMethod();  
	        // GET  
	        if (submitMehtod.equals("GET")) {  
	            return new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8").replaceAll("%22", "\"");  
	        // POST  
	        } else {  
	            return getRequestPostStr(request);  
	        }  
	    }  
	         
	    /**       
	     * 描述:获取 post 请求的 byte[] 数组 
	     * <pre> 
	     * 举例： 
	     * </pre> 
	     * @param request 
	     * @return 
	     * @throws IOException       
	     */  
	    public static byte[] getRequestPostBytes(HttpServletRequest request)  
	            throws IOException {  
	        int contentLength = request.getContentLength();  
	        if(contentLength<0){  
	            return null;  
	        }  
	        byte buffer[] = new byte[contentLength];  
	        for (int i = 0; i < contentLength;) {  
	  
	            int readlen = request.getInputStream().read(buffer, i,  
	                    contentLength - i);  
	            if (readlen == -1) {  
	                break;  
	            }  
	            i += readlen;  
	        }  
	        return buffer;  
	    }  
	      
	    /**       
	     * 描述:获取 post 请求内容 
	     * <pre> 
	     * 举例： 
	     * </pre> 
	     * @param request 
	     * @return 
	     * @throws IOException       
	     */  
	    public static String getRequestPostStr(HttpServletRequest request)  
	            throws IOException {  
	        byte buffer[] = getRequestPostBytes(request);  
	        String charEncoding = request.getCharacterEncoding();  
	        if (charEncoding == null) {  
	            charEncoding = "UTF-8";  
	        }  
	        return new String(buffer, charEncoding);  
	    } 
	    
	    public static String buildOriginalURL(HttpServletRequest request) {
//	    	        StringBuffer originalURL = request.getRequestURL();
	    	        StringBuffer originalURL=new StringBuffer();
	    	        Map parameters = orderMapByKey(request.getParameterMap());
	    	        boolean flag=true;
	    	        if (parameters != null && parameters.size() > 0) {
//	    	            originalURL.append("?");
	    	            for (Iterator iter = parameters.keySet().iterator(); iter.hasNext();) {
	    	            	String key = (String) iter.next();
	    	                String[] values = (String[]) parameters.get(key);
	    	                if ("singal".equals(key)) {
								continue;
							}
	    	            	if (flag) {
	    	            		flag=false;
							}else{
								originalURL.append("&");
							}
	    	                for (int i = 0; i < values.length; i++) {
	    	                    originalURL.append(key).append("=").append(values[i]);
	    	                    if (i<values.length-1) {
	    	                    	originalURL.append("&");
								}
	    	                }
	    	            }
	    	        }
	    	        return originalURL.toString();
    	    }
	    
	    public static TreeMap orderMapByKey(Map<String, String[]> requestMap){
	    	 TreeMap<String, String[]> map = new TreeMap<String, String[]>(
	                 new Comparator<String>() {
	                     public int compare(String obj1, String obj2) {
	                         // 降序排序
	                         return obj2.compareTo(obj1);
	                     }
	                 });
	         
	    	 Set<String> keySet = requestMap.keySet();
	         Iterator<String> iter = keySet.iterator();
			while (iter.hasNext()) {
	             String key = iter.next();
	             map.put(key, requestMap.get(key));
	         }
	         
	         return map;
	    	
	    }
}
