package com.ineutech.vo;

public class ResponseMsg {
	private final static String CODE_SUCESS="success";
	private final static String CODE_FAIL="fail";
	
	/* 状态码 */
    private String code;
    /* 提示信息 */
    private String message;
    /* 成功返回的数据 */
    private Object data;
    
    public ResponseMsg(){}
    public ResponseMsg(String code){
    	this.code=code;
    }
    public ResponseMsg(String code,String message,Object data){
    	this.code=code;
    	this.message=message;
    	this.data=data;
    }
    
    public static ResponseMsg success(){
    	return new ResponseMsg(CODE_SUCESS);
    }
    public static ResponseMsg success(Object data){
    	ResponseMsg responseMsg=new ResponseMsg(CODE_SUCESS);
    	responseMsg.setData(data);
    	return responseMsg;
    }
    
    public static ResponseMsg fail(){
    	return new ResponseMsg(CODE_FAIL);
    }
    public static ResponseMsg fail(String message){
    	ResponseMsg responseMsg=new ResponseMsg(CODE_FAIL);
    	responseMsg.setMessage(message);
    	return responseMsg;
    }
    
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
