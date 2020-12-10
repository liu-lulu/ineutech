package com.ineutech.control.device;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.ineutech.tpms.tcp.TPMSConsts;
import cn.ineutech.tpms.tcp.server.SessionManager;
import cn.ineutech.tpms.tcp.service.msg.BaseMsgProcessService;
import cn.ineutech.tpms.tcp.vo.Session;

import com.ineutech.entity.TestScorePackage;
import com.ineutech.service.DeviceService;
import com.ineutech.service.impl.TestServiceImpl;
import com.ineutech.util.StringHelper;

/**
 * 
 * @name: DeviceController 
 * @description: 接口
 * @date 2018年2月1日 下午3:21:56
 * @author liululu
 */
@Controller
@RequestMapping("/device")
public class DeviceController {

	private SessionManager sessionManager = SessionManager.getInstance();
	private BaseMsgProcessService msgProcessService = new BaseMsgProcessService();
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private DeviceService deviceService;
	@Resource
	private TestServiceImpl testService;

	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex, HttpServletResponse response)
			throws IOException {

		PrintWriter out = response.getWriter();
		logger.error(StringHelper.getTrace(ex));

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("state", 100);

		out.print(jsonObject.toString());

		out.flush();
		out.close();
	}

	@RequestMapping("/selectTest")
	public void selectTest(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			InterruptedException {
		int testId = Integer.valueOf(request.getParameter("testId"));
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		testService.selectTest(testId);

		jsonObject.put("state", 2);// 可以开启

		out.print(jsonObject.toString());

		out.flush();
		out.close();

	}

	@RequestMapping("/testPeriodChange")
	public void testPeriodChange(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			NumberFormatException, InterruptedException {
		int success = 0;
		String period = request.getParameter("period");

		List<Session> processSessionList = getProcessSession();

		int i = 0;
		while (i < 2) {
			for (Session session : processSessionList) {
				int ret = msgProcessService.send2Client(session.getChannel(),
						msgProcessService.testPeriod(period, ""));
				if (ret != 0) {
					success += 1;
				}
			}
			i++;
		}

		sessionManager.getTestInfo().setPeriod(period);
		sessionManager.getTestInfo().setStatus(TPMSConsts.STATUS_BACK);

		if (TPMSConsts.PERIOD_END.equals(period)) {
			sessionManager.clearInfo();
		}
		if (TPMSConsts.PERIOD_AFTER.equals(period)) {
			deviceService.saveDialTotalScore();
		}

		PrintWriter out = response.getWriter();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", processSessionList.size());
		jsonObject.put("success", success);
		out.print(jsonObject.toString());

		out.flush();
		out.close();
	}

	@RequestMapping("/stateChange")
	public void stateChange(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			NumberFormatException, InterruptedException {
		int success = 0;
		String periodStatus = request.getParameter("periodStatus");

		List<Session> processSessionList = getProcessSession();

		int i = 0;
		while (i < 2) {
			for (Session session : processSessionList) {
				int ret = msgProcessService.send2Client(session.getChannel(),
						msgProcessService.periodStatus(periodStatus));
				if (ret != 0) {
					success += 1;
				}
			}
			i++;
		}

		sessionManager.getTestInfo().setStatus(periodStatus);

		PrintWriter out = response.getWriter();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total", processSessionList.size());
		jsonObject.put("success", success);
		out.print(jsonObject.toString());

		out.flush();
		out.close();
	}

	@RequestMapping("/score")
	public void score(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");

		PrintWriter out = response.getWriter();

		JSONObject jsonObject = new JSONObject();
		if (sessionManager.getTestInfo() != null) {
			jsonObject.put("user_info", testService.getUserLoginInfo());

			jsonObject.put("test_info", sessionManager.getTestInfo());
		}
		System.out.println(jsonObject.toString());
		out.print(jsonObject.toString());

		out.flush();
		out.close();

	}

	@RequestMapping("/scorePackage")
	public void scorePackage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");

		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();

		String scorePackage = request.getParameter("scorePackage");
		if (StringUtils.isNotEmpty(scorePackage)) {
			String info = URLDecoder.decode(scorePackage, "utf-8");
			JSONArray jsonarray = JSONArray.fromObject(info);
			@SuppressWarnings("unchecked")
			List<TestScorePackage> scoreList = (List<TestScorePackage>) JSONArray
					.toCollection(jsonarray, TestScorePackage.class);
			if (scoreList != null && scoreList.size() > 0) {
				deviceService.savePackageData(scoreList);
				jsonObject.put("state", 1);
			}
		} else {
			jsonObject.put("state", 0);
		}

		out.print(jsonObject.toString());

		out.flush();
		out.close();

	}
	
	
/*	@RequestMapping("/userBrain")
	public void userBrain(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");

		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();

		String humanIdString = request.getParameter("humanId");
		
		Hard hard = deviceService.getUserBrain(Integer.valueOf(humanIdString));
		if (hard == null || StringUtils.isEmpty(hard.getMac())) {
			jsonObject.put("state", 1);
		} else {
			jsonObject.put("state", 2);
		}
		
		out.print(jsonObject.toString());

		out.flush();
		out.close();

	}
	
	@RequestMapping("/bindBrain")
	public void bindBrain(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");

		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();

		String humanIdString = request.getParameter("humanId");
		String brainNoString = request.getParameter("brainNo");
		
		jsonObject.put("state", deviceService.userBindBrain(Integer.valueOf(humanIdString), Integer.valueOf(brainNoString)));
		
		out.print(jsonObject.toString());

		out.flush();
		out.close();

	}
	
	@RequestMapping("/removeBind")
	public void removeBind(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");

		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();

		String humanIdString = request.getParameter("humanId");
		
		jsonObject.put("state", deviceService.removeBind(Integer.valueOf(humanIdString)));
		
		out.print(jsonObject.toString());

		out.flush();
		out.close();

	}*/


	private List<Session> getProcessSession() {
		return sessionManager.padToList();
	}

}
