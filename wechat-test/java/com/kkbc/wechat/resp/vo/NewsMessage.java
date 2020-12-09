package com.kkbc.wechat.resp.vo;

import java.util.List;

import com.kkbc.wechat.common.WechatConsts;

/** 
 * 多图文消息， 
 * 单图文的时候 Articles 只放一个就行了 
 * @author Caspar.chen 
 */  
public class NewsMessage extends BaseMessage {  
    /** 
     * 图文消息个数，限制为10条以内 
     */  
    private int ArticleCount;  
    /** 
     * 多条图文消息信息，默认第一个item为大图 
     */  
    private List<Article> Articles;  
    
    public NewsMessage() {
		this.MsgType=WechatConsts.RESP_MESSAGE_TYPE_NEWS;
	}
  
    public int getArticleCount() {  
        return ArticleCount;  
    }  
  
    public void setArticleCount(int articleCount) {  
        ArticleCount = articleCount;  
    }  
  
    public List<Article> getArticles() {  
        return Articles;  
    }  
  
    public void setArticles(List<Article> articles) {  
        Articles = articles;  
    }  
}  
