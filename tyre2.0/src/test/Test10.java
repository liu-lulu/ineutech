package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test10 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject j=JSONObject.fromObject(getURLResponse());
		JSONArray ja=j.getJSONObject("info").getJSONArray("workOrderRecords");
		if(ja==null){
			System.out.println("没有");
		}else{
			System.out.println("条数:"+ja.size());
		}
        
	}

	 private static String getURLResponse(){  
	        HttpURLConnection conn = null; //连接对象  
	        InputStream is = null;  
	        String resultData = "";  
	        try {  
	            URL url = new URL("http://192.168.1.192:4080/tyre2.0/ServletAll?action=getLastWorkOrder&user_id=5&flag=1"); //URL对象  
	            conn = (HttpURLConnection)url.openConnection(); //使用URL打开一个链接  
	            conn.setDoInput(true); //允许输入流，即允许下载  
	            conn.setDoOutput(true); //允许输出流，即允许上传  
	            conn.setUseCaches(false); //不使用缓冲  
	            conn.setRequestMethod("GET"); //使用get请求  
	            int code = conn.getResponseCode();
	            System.out.println("code"+code);
	            if(code !=200){
	            	return "";
	            }
	            is = conn.getInputStream();   //获取输入流，此时才真正建立链接  
	            InputStreamReader isr = new InputStreamReader(is);  
	            BufferedReader bufferReader = new BufferedReader(isr);  
	            String inputLine  = "";  
	            while((inputLine = bufferReader.readLine()) != null){  
	                resultData += inputLine;  
	            }  
	  
	        } catch (MalformedURLException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }finally{  
	            if(is != null){  
	                try {  
	                    is.close();  
	                } catch (IOException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                }  
	            }  
	            if(conn != null){  
	                conn.disconnect();  
	            }  
	        }  
	  System.out.println("resultData:"+resultData);
	        return resultData;  
	    }  

}
