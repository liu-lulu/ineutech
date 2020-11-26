package com.ineutech.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.nlp.v20190408.NlpClient;
import com.tencentcloudapi.nlp.v20190408.models.AutoSummarizationRequest;
import com.tencentcloudapi.nlp.v20190408.models.AutoSummarizationResponse;
import com.tencentcloudapi.nlp.v20190408.models.Keyword;
import com.tencentcloudapi.nlp.v20190408.models.KeywordsExtractionRequest;
import com.tencentcloudapi.nlp.v20190408.models.KeywordsExtractionResponse;

public class TencentNLPUtil {
	
	public static final String TENCENT_SECRETID="AKIDc8WJ4tp9W6Zv8sLqAQYYnZGRRK6i9gyA";
	
	public static final String TENCENT_SECRETKEY="EIh8XHxUFoGoS1HAwdDcvCLMtKOMF3gD";
	
	private static Credential cred;
	private static HttpProfile httpProfile;
	private static ClientProfile clientProfile;
	private static NlpClient client;
	
	static{
		cred = new Credential(TENCENT_SECRETID, TENCENT_SECRETKEY);
        
        httpProfile = new HttpProfile();
        httpProfile.setEndpoint("nlp.tencentcloudapi.com");

        clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        
        client = new NlpClient(cred, "ap-guangzhou", clientProfile);
	}
	
	public static List<String> getKeyWords(String content){
		
		try{
            JSONObject params=new JSONObject();
            params.put("Text", content);
            params.put("Num", 10);
            KeywordsExtractionRequest req = KeywordsExtractionRequest.fromJsonString(params.toString(), KeywordsExtractionRequest.class);
            
            KeywordsExtractionResponse resp = client.KeywordsExtraction(req);
            List<Keyword> keywords=Arrays.asList(resp.getKeywords());
            List<String> ret=keywords.stream().map(Keyword::getWord).collect(Collectors.toList());
            return ret;
            
        } catch (TencentCloudSDKException e) {
           e.printStackTrace();
        }
		
		return null;
	}
	
	public static String getSummary(String content){
		
		try{
            JSONObject params=new JSONObject();
            params.put("Text", content);
            params.put("Length", 50);
            AutoSummarizationRequest req = AutoSummarizationRequest.fromJsonString(params.toString(), AutoSummarizationRequest.class);
            
            AutoSummarizationResponse resp = client.AutoSummarization(req);
            return resp.getSummary();
            
        } catch (TencentCloudSDKException e) {
        	e.printStackTrace();
        }
		
		return null;
	}
	
	public static void main(String[] args) {
		List<String> keywords=TencentNLPUtil.getKeyWords("今天天气很好");
		for (String keyword : keywords) {
			System.out.println(keyword);
		}
		System.out.println("----摘要----");
		System.out.println(TencentNLPUtil.getSummary("今天天气很好"));
	}

}
