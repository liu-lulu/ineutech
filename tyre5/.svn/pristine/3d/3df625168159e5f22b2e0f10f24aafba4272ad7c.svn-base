package com.kkbc.control.terminal;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.kkbc.tpms.common.exception.ParamSettingException;
import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.service.msg.TerminalParamSettingService;
import cn.kkbc.tpms.tcp.vo.Session;
import cn.kkbc.tpms.tcp.vo.XHRMap;

@Controller
@RequestMapping("/terminal")
public class ParamSettingController {

	private TerminalParamSettingService paramSettingService;

	public ParamSettingController() {
		this.paramSettingService = new TerminalParamSettingService();
	}

	@RequestMapping("/params-setting")
	public String toParamSettingsPage(Map<String, Object> map) {
		map.put("sessions", SessionManager.getInstance().toList());
		return "terminal/terminal-settings";
	}

	@ResponseBody
	@RequestMapping("/list")
	public List<Session> getSessionList() {
		return SessionManager.getInstance().toList();
	}

	@ResponseBody
	@RequestMapping(value = "/{phone}", method = RequestMethod.POST)
	public XHRMap updateTerminalParams(//
			@PathVariable("phone") String phone, //
			@RequestParam("id") String id, //
			@RequestParam("values") String values) {
		try {
			this.paramSettingService.processParamSettings(id, phone, values);
			return new XHRMap().success();
		} catch (ParamSettingException e) {
			return new XHRMap().failure().msg(e.getMessage());
		} catch (NumberFormatException e) {
			return new XHRMap().failure().msg("数字解析异常," + e.getMessage());
		} catch (Exception e) {
			return new XHRMap().failure().msg("发生错误:" + e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/params")
	public Object queryTerminalParam(@RequestParam("phone") String phone) {
		try {
			return new XHRMap().success().kv("params", this.paramSettingService.queryTerminalParam(phone));
		} catch (TimeoutException e) {
			return new XHRMap().failure().msg("超时");
		} catch (ParamSettingException e) {
			return new XHRMap().failure().msg(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return new XHRMap().failure().msg("服务器异常");
		}
	}
}
