package com.kkbc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.kkbc.wechat.common.WechatConsts;
import com.kkbc.wechat.resp.vo.Article;
import com.kkbc.wechat.resp.vo.LinkMessage;
import com.kkbc.wechat.resp.vo.MusicMessage;
import com.kkbc.wechat.resp.vo.NewsMessage;
import com.kkbc.wechat.resp.vo.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/** 
 * 消息工具类 
 *  
 */  
public class MessageUtil {  
  
    /** 
     * 解析微信发来的请求（XML） 
     *  
     * @param request 
     * @return 
     * @throws Exception 
     */  
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();  
  
        // 从request中取得输入流  
        InputStream inputStream = request.getInputStream();  
        // 读取输入流  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(inputStream);  
        // 得到xml根元素  
        Element root = document.getRootElement();  
        // 得到根元素的所有子节点  
          
        @SuppressWarnings("unchecked")  
        List<Element> elementList = root.elements();  
  
        // 遍历所有子节点  
        for (Element e : elementList)  
            map.put(e.getName(), e.getText());  
  
        // 释放资源  
        inputStream.close();  
        inputStream = null;  
  
        return map;  
    }  
    
    /** 
     * 解析微信发来的请求（XML） 
     *  
     * @param request 
     * @return 
     * @throws Exception 
     */  
    public Map<String, String> parseXmlDigui(HttpServletRequest request) throws Exception {  
        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();  
  
        // 从request中取得输入流  
        InputStream inputStream = request.getInputStream();  
        // 读取输入流  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(inputStream);  
        // 得到xml根元素  
        Element root = document.getRootElement();  
        // 得到根元素的所有子节点  
          
        @SuppressWarnings("unchecked")  
        List<Element> elementList = new ArrayList<Element>();  
        
        getNodes(elementList,root);
  
        // 遍历所有子节点  
        for (Element e : elementList)  
            map.put(e.getName(), e.getText());  
  
        // 释放资源  
        inputStream.close();  
        inputStream = null;  
  
        return map;  
    }  
    
    public void getNodes(List<Element> elementList,Element node){  
//    	    System.out.println("--------------------");  
    	      
    	    //当前节点的名称、文本内容和属性  
//    	    System.out.println("当前节点名称："+node.getName());//当前节点名称  
//    	    System.out.println("当前节点的内容："+node.getTextTrim());//当前节点名称  
    	    List<Attribute> listAttr=node.attributes();//当前节点的所有属性的list  
    	    for(Attribute attr:listAttr){//遍历当前节点的所有属性  
    	        String name=attr.getName();//属性名称  
    	        String value=attr.getValue();//属性的值  
    	        System.out.println("属性名称："+name+"属性值："+value);  
    	    }  
    	      
    	    //递归遍历当前节点所有的子节点  
    	    List<Element> listElement=node.elements();//所有一级子节点的list  
    	    elementList.addAll(listElement);
    	    for(Element e:listElement){//遍历所有一级子节点  
    	        this.getNodes(elementList,e);//递归  
    	    }  
    	}  
  
    /** 
     * 文本消息对象转换成xml 
     *  
     * @param textMessage 文本消息对象 
     * @return xml 
     */  
    public static String textMessageToXml(TextMessage textMessage) {  
        xstream.alias("xml", textMessage.getClass());  
        return xstream.toXML(textMessage);  
    }  
  
    /** 
     * 音乐消息对象转换成xml 
     *  
     * @param musicMessage 音乐消息对象 
     * @return xml 
     */  
    public static String musicMessageToXml(MusicMessage musicMessage) {  
        xstream.alias("xml", musicMessage.getClass());  
        return xstream.toXML(musicMessage);  
    }  
    
    public static String linkMessageToXml(LinkMessage linkMessage) {  
        xstream.alias("xml", linkMessage.getClass());  
        return xstream.toXML(linkMessage);  
    }  
  
    /** 
     * 图文消息对象转换成xml 
     *  
     * @param newsMessage 图文消息对象 
     * @return xml 
     */  
    public static String newsMessageToXml(NewsMessage newsMessage) {  
    	
        xstream.alias("xml", newsMessage.getClass());  
        xstream.alias("item", new Article().getClass());  
        return xstream.toXML(newsMessage);  
    }  
  
    /** 
     * 扩展xstream，使其支持CDATA块 
     *  
     */  
    private static XStream xstream = new XStream(new XppDriver() {  
        public HierarchicalStreamWriter createWriter(Writer out) {  
            return new PrettyPrintWriter(out) {  
                // 对所有xml节点的转换都增加CDATA标记  
                boolean cdata = true;  
                protected void writeText(QuickWriter writer, String text) {  
                    if (cdata) {  
                        writer.write("<![CDATA[");  
                        writer.write(text);  
                        writer.write("]]>");  
                    } else {  
                        writer.write(text);  
                    }  
                }  
            };  
        }  
    });  
    
    public static String loadStringContentFromInputStream(InputStream in) {
		StringBuilder sb = new StringBuilder();
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {

			isr = new InputStreamReader(in, WechatConsts.CHARACTER_ENCODING);
			br = new BufferedReader(isr);
			String s = null;
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
      
}  
