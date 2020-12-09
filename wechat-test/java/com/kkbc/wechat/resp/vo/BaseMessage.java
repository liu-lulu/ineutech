package com.kkbc.wechat.resp.vo;

/** 
 * 消息基类（公众帐号 -> 用户） 
 */  
public class BaseMessage {  
      
    /** 
     * 接收方帐号（收到的OpenID） 
     */  
	protected String ToUserName;  
    /** 
     * 开发者微信号 
     */  
	protected String FromUserName;  
    /** 
     * 消息创建时间 （整型） 
     */  
	protected long CreateTime;  
      
    /** 
     * 消息类型 
     */  
	protected String MsgType;  
      
    /** 
     * 位0x0001被标志时，星标刚收到的消息 
     */  
	protected int FuncFlag;  
  
    public String getToUserName() {  
        return ToUserName;  
    }  
  
    public void setToUserName(String toUserName) {  
        ToUserName = toUserName;  
    }  
  
    public String getFromUserName() {  
        return FromUserName;  
    }  
  
    public void setFromUserName(String fromUserName) {  
        FromUserName = fromUserName;  
    }  
  
    public long getCreateTime() {  
        return CreateTime;  
    }  
  
    public void setCreateTime(long createTime) {  
        CreateTime = createTime;  
    }  
  
    public String getMsgType() {  
        return MsgType;  
    }  
  
    public void setMsgType(String msgType) {  
        MsgType = msgType;  
    }  
  
    public int getFuncFlag() {  
        return FuncFlag;  
    }  
  
    public void setFuncFlag(int funcFlag) {  
        FuncFlag = funcFlag;  
    }  
}  