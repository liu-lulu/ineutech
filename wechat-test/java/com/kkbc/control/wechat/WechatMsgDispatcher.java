package com.kkbc.control.wechat;

import io.netty.util.internal.StringUtil;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kkbc.control.BaseController;
import com.kkbc.service.UserService;
import com.kkbc.util.CheckUtil;
import com.kkbc.wechat.common.WechatConsts;
import com.kkbc.wechat.process.WechatProcess;

@Controller
public class WechatMsgDispatcher extends BaseController{
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value="/wechat",method=RequestMethod.GET)
	public void check(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// 接收微信服务器以Get请求发送的4个参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        log.info("signature:"+signature+"------timestamp:"+timestamp+"------nonce:"+nonce+"------echostr:"+echostr);
        
        if (StringUtils.isEmpty(echostr)) {
			log.error("msg signature validation failure({}).", request.getRemoteAddr());
			return;
		}
         
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
//            out.print(echostr);        // 校验通过，原样返回echostr参数内容
            response.getOutputStream().write(echostr.getBytes(WechatConsts.CHARACTER_ENCODING));
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }

	}
	
	@RequestMapping(value="/wechat",method=RequestMethod.POST)
	private void process(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
        response.setContentType("text/xml;charset=utf-8");
        
        log.info("开始处理微信请求:{}",request.toString());
        WechatProcess process=new WechatProcess();
        String resp=process.processRequest(request);
        
        System.out.println("resp:"+resp);
        if (StringUtils.isNotEmpty(resp)) {
        	response.getOutputStream().write(resp.getBytes(WechatConsts.CHARACTER_ENCODING));
            response.getOutputStream().flush();
            response.getOutputStream().close();
		}
        
        

	}
	

}
