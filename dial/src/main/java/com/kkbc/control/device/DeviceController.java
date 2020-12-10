package com.kkbc.control.device;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.kkbc.tpms.tcp.TPMSConsts;
import cn.kkbc.tpms.tcp.entity.DialOperation;
import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.service.msg.BaseMsgProcessService;
import cn.kkbc.tpms.tcp.util.BmpFileUtil;
import cn.kkbc.tpms.tcp.vo.Session;

import com.kkbc.control.BaseController;
import com.kkbc.entity.Hard;
import com.kkbc.entity.TestInfo;
import com.kkbc.entity.TestUser;
import com.kkbc.service.DeviceService;

/**
 * dtu设备
 * @author liululu
 *
 */
@Controller
@RequestMapping("/device")
public class DeviceController extends BaseController{
	
	private SessionManager sessionManager=SessionManager.getInstance();
	private BaseMsgProcessService msgProcessService=new BaseMsgProcessService();
	
	@Resource
	private DeviceService deviceService;
	
	@RequestMapping("/selectTest")
	public void selectTest(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.err.println("-------------节目选择ID------"+request.getParameter("testId"));
		int testId=Integer.valueOf(request.getParameter("testId"));
		
		TestInfo testInfo=deviceService.getTestInfoById(testId);
		
		PrintWriter out=response.getWriter();
		JSONObject jsonObject=new JSONObject();
		
		
		List<Hard> testHards=deviceService.getTestDevice(testId);
		
		deviceService.saveNowInfo(testId);
		System.err.println("-------------now表添加数据------");
		List<Session> devices=sessionManager.toList();
		
		TestUser testUser=new TestUser();
		testUser.setTest_id(testId);
		testUser.setDevice_status(TestUser.DEVICE_STATUS_ONLINE);
		for (Hard hard : testHards) {//更新test_now里的设备状态:在线
			hard.setTest_name(testInfo.getTest_name());
			hard.setLast_time(new Date());
			hard.setStatus(Hard.STATUS_USING);
			
			testUser.setMac(hard.getMac());
			for (Session session : devices) {
				if (StringUtils.isNotEmpty(hard.getMac())&&hard.getMac().equals(session.getShefen_id())) {
					deviceService.updateDeviceStatus(testUser);
					break;
				}
			}
		}
		
		
		loadDeviceBind(testId);//获取绑定信息
		
//		for (Hard hard : testHards) {
//			hard.setTest_name(testInfo.getTest_name());
//			hard.setLast_time(new Date());
//			hard.setStatus(Hard.STATUS_USING);
//			deviceService.updLastInfo(hard);
//		}
		
		deviceService.updLastInfo(testHards);
		
		jsonObject.put("state", 2);//可以开启
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
		
	}
	
	@RequestMapping("/enterTest")
	public void enterTest(HttpServletRequest request, HttpServletResponse response) throws IOException, NumberFormatException, InterruptedException{
		int success=0;
		int testId=Integer.valueOf(request.getParameter("testId"));
		String info = request.getParameter("mac");
		
		loadDeviceBind(testId);
		
		List<Session> processSessionList=getProcessSession(testId, info);
		
		int i=0;
		//开始测试之前删除拨盘的本地存储数据;发送2遍
		while (i<2) {
			for (Session session : processSessionList) {
				msgProcessService.send2Client(session.getChannel(), msgProcessService.getSendGetOperationDtuData(TPMSConsts.COMMAND_DELETE_FLASH));
			}
			i++;
		}
		
		i=0;
		while (i<2) {
			for (Session session : processSessionList) {
				if (session.getState()==Session.STATE_NO_START) {
				
					int ret=msgProcessService.send2Client(session.getChannel(), msgProcessService.getSendGetOperationDtuData(TPMSConsts.COMMAND_ENTER_TEST));
					if (ret!=0) {
						success+=1;
					}
				}
			}
			i++;
		}
		
		PrintWriter out=response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", processSessionList.size());
		jsonObject.put("success", success);
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/selectMode")
	public void selectMode(HttpServletRequest request, HttpServletResponse response) throws IOException, NumberFormatException, InterruptedException{
		int testId=Integer.valueOf(request.getParameter("testId"));
		String info = request.getParameter("mac");
		String modeString=request.getParameter("mode");
		
		int mode=3;
		if (StringUtils.isNotEmpty(modeString)) {
			mode=(modeString.equals("1")||modeString.equals("2")||modeString.equals("3"))?Integer.valueOf(modeString):mode;
		}
		
		List<Session> processSessionList=getProcessSession(testId, info);
		int success=0;
		int i=0;
		//发送2遍
		while (i<2) {
		for (Session session : processSessionList) {
//			if (session.getState()!=Session.STATE_NO_START) {
			System.out.println("---选择模式");
			int ret=msgProcessService.send2Client(session.getChannel(), msgProcessService.selectMode(mode));
			if (ret!=0) {
				success+=1;
			}
//			}
		}
		i++;
		}
		
		sessionManager.putTestLine(testId);
		
		PrintWriter out=response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", processSessionList.size());
		jsonObject.put("success", success);
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/changeMode")
	public void changeMode(HttpServletRequest request, HttpServletResponse response) throws IOException, NumberFormatException, InterruptedException{
		int testId=Integer.valueOf(request.getParameter("testId"));
		String info = request.getParameter("mac");
		
		List<Session> processSessionList=getProcessSession(testId, info);
		int success=0;
		for (Session session : processSessionList) {
//			if (session.getState()!=Session.STATE_NO_START) {
			System.out.println("---切换模式");
			int ret=msgProcessService.send2Client(session.getChannel(), msgProcessService.getSendGetOperationDtuData(TPMSConsts.COMMAND_CHANGE_MODE));
			if (ret!=0) {
				success+=1;
			}
//			}
		}
		
		PrintWriter out=response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", processSessionList.size());
		jsonObject.put("success", success);
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/delFlashData")
	public void delFlashData(HttpServletRequest request, HttpServletResponse response) throws IOException, NumberFormatException, InterruptedException{
		int testId=Integer.valueOf(request.getParameter("testId"));
		String info = request.getParameter("mac");
		
		List<Session> processSessionList=getProcessSession(testId, info);
		int success=0;
		for (Session session : processSessionList) {
			System.out.println("---flash数据删除");
			int ret=msgProcessService.send2Client(session.getChannel(), msgProcessService.getSendGetOperationDtuData(TPMSConsts.COMMAND_DELETE_FLASH));
			if (ret!=0) {
				success+=1;
			}
		}
		
		PrintWriter out=response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", processSessionList.size());
		jsonObject.put("success", success);
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/endTest")
	public void endTest(HttpServletRequest request, HttpServletResponse response) throws IOException, NumberFormatException, InterruptedException{
		int testId=Integer.valueOf(request.getParameter("testId"));
		String info = request.getParameter("mac");
		
		TestInfo testInfo=deviceService.getTestInfoById(testId);
		List<Hard> testHards=deviceService.getTestDevice(testId);
		
		List<Session> processSessionList=getProcessSession(testId, info);
		int success=0;
		int i=0;
		//发送2遍
//		while (i<2) {
			for (Session session : processSessionList) {
	//			if (session.getState()!=Session.STATE_NO_START) {
				int ret=msgProcessService.send2Client(session.getChannel(), msgProcessService.getSendGetOperationDtuData(TPMSConsts.COMMAND_TEST_COMPLET));
				if (ret!=0) {
					success+=1;
				}
	//			}
			}
//			i++;
//		}
		
		deviceService.deleteFromNow(testId);//test_now中删除已测试结束的信息
		
		for (Hard hard : testHards) {
			hard.setTest_name(testInfo.getTest_name());
			hard.setLast_time(new Date());
			hard.setStatus(Hard.STATUS_NOUSE);
//			deviceService.updLastInfo(hard);
		}
		
		deviceService.updLastInfo(testHards);
		
		sessionManager.getLineDataMap().remove(testId);
		PrintWriter out=response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", processSessionList.size());
		jsonObject.put("success", success);
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/shutDown")
	public void shutDown(HttpServletRequest request, HttpServletResponse response) throws IOException, NumberFormatException, InterruptedException{
		
		int success=0;
		for (Session session : sessionManager.toList()) {
			int ret=msgProcessService.send2Client(session.getChannel(), msgProcessService.getSendGetOperationDtuData(TPMSConsts.COMMAND_SHUTDOWN));
			if (ret!=0) {
				success+=1;
			}
		}
		
		PrintWriter out=response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
//		jsonObject.put("total", processSessionList.size());
		jsonObject.put("success", success);
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/getDeviceInfo")
	public void getDeviceInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException, InterruptedException {
		
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String mac=request.getParameter("mac");
		String command=request.getParameter("command");
		Session session=sessionManager.findByTerminalShefenId(mac);
		
		PrintWriter out = response.getWriter();
		if (session==null) {
			out.print("不在线");
		}else {
			if (StringUtils.isEmpty(command)){
				out.print("操作命令为空");
			}else {
				DialOperation operation=new DialOperation();
				operation.setMac(mac);
				operation.setCommand(Integer.valueOf(command));
				
				//数据库计入操作日志,获取日志ID
//				long id=1;
//				operation.setId(id);
				
				String uuid=UUID.randomUUID().toString();
				System.out.println("----uuid-----"+uuid);
				
				sessionManager.getOperationMap().put(mac+uuid, operation);
				
				msgProcessService.send2Client(session.getChannel(), msgProcessService.getSendGetOperationDtuData(Integer.valueOf(command)));
				
				int state=0;
				String msg="mac="+mac;
				int i=0;
				while(true){
//					System.err.println(new Date()+"========="+i);
					++i;
					try {
						if(operation.getType()==DialOperation.TYPE_FAIL){//操作失败
							msg+=",操作失败";
							break;
						}else if(operation.getType()==DialOperation.TYPE_SUCCESS){//操作成功
							state=1;
							msg+=",操作成功";
							break;
						}else if(System.currentTimeMillis()-operation.getTime()>DialOperation.INTERVAL_TIME){
							state=2;
							msg+=",操作超时了";
							operation.setType(DialOperation.TYPE_TIMEOUT);
							//--------更新操作日志状态
							break;
						}
						Thread.sleep(200);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				sessionManager.getOperationMap().remove(mac+uuid);
				
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("state", state);
				jsonObject.put("msg", msg);
				jsonObject.put("info", JSONArray.fromObject(session));
				
				out.print(jsonObject);
			}
			
		}
		
	
	}
	
	@RequestMapping("/pause")
	public void pause(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException, InterruptedException {
		
		int testId=Integer.valueOf(request.getParameter("testId"));
		String info = request.getParameter("mac");
		
		List<Session> processSessionList=getProcessSession(testId, info);
		int success=0;
		for (Session session : processSessionList) {
//			if (session.getState()==Session.STATE_TESTING) {
				int ret=msgProcessService.send2Client(session.getChannel(), msgProcessService.pause_data());
				if (ret!=0) {
					success+=1;
				}
//			}
		}
		
		PrintWriter out=response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", processSessionList.size());
		jsonObject.put("success", success);
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/cancelPause")
	public void cancelPause(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException, InterruptedException {
		
		int testId=Integer.valueOf(request.getParameter("testId"));
		String info = request.getParameter("mac");
		
		List<Session> processSessionList=getProcessSession(testId, info);
		int success=0;
		for (Session session : processSessionList) {
//			if (session.getState()==Session.STATE_PAUSE) {
				int ret=msgProcessService.send2Client(session.getChannel(), msgProcessService.cancelPause_data());
				if (ret!=0) {
					success+=1;
				}
//			}
		}
		
		PrintWriter out=response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", processSessionList.size());
		jsonObject.put("success", success);
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/setTime")
	public void setTime(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException, InterruptedException, ParseException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String mac=request.getParameter("mac");
		String time=request.getParameter("time");
		Session session=sessionManager.findByTerminalShefenId(mac);
		int success=0;
		if (session==null) {
			out.print("不在线");
		}else {
			int ret=msgProcessService.send2Client(session.getChannel(), msgProcessService.setTime_data(time));
			if (ret!=0) {
				success+=1;
			}
			JSONObject jsonObject=new JSONObject();
//			jsonObject.put("total", processSessionList);
			jsonObject.put("success", success);
			out.print(jsonObject.toString());
		}
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/sendName")
	public void sendName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException, InterruptedException {
		
		String info = request.getParameter("mac");
		
		int testId=Integer.valueOf(request.getParameter("testId"));
		
//		info=URLDecoder.decode(info, "utf-8");
//		JSONArray jsonArray=JSONArray.fromObject(info);
//		@SuppressWarnings("unchecked")
//		List<TestUser> testUsers=(List<TestUser>)JSONArray.toCollection(jsonArray, TestUser.class);
		
		loadDeviceBind(testId);
		
		TestUser testUser= new TestUser();
		testUser.setTest_id(testId);
		BmpFileUtil util=new BmpFileUtil();
		
		List<Session> processSessionList=getProcessSession(testId, info);
		int success=0;
		for (Session session : processSessionList) {
			testUser.setMac(session.getShefen_id());
			testUser=deviceService.getUser(testUser);
			session.setTestUser(testUser);
			byte zimo[]=util.getZimo(testUser);
			int ret=msgProcessService.send2Client(session.getChannel(), msgProcessService.sendName_data(zimo));
			if (ret!=0) {
				success+=1;
			}
		}
		
		PrintWriter out=response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", processSessionList.size());
		jsonObject.put("success", success);
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/sendNameTest")
	public void sendNameTest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException, InterruptedException {
		
		String info = request.getParameter("mac");
		
		for (String mac : info.split(",")) {
			Session session=sessionManager.findByTerminalShefenId(mac);
			
//			byte zimo[]=util.getZimo(baseSrc+session.getTestUser().getBmpFile());
//			msgProcessService.send2Client(session.getChannel(), msgProcessService.sendName_data(zimo));
			
//			byte zimo[]={(byte)0xF5,0x5F,0x01,0x06,0x01,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x3E,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x3E,0x00,0x00,0x01,0x23,0x22,0x26,0x2C,0x3C,0x3E,0x20,0x28,0x2C,0x24,0x22,0x02,0x00,0x00,0x3E,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x3E,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x13,0x12,0x1E,0x1E,0x01,0x3F,0x01,0x05,0x09,0x19,0x31,0x01,0x00,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x0E,0x0C,0x0C,0x0F,0x07,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x0E,0x0C,0x0C,0x0F,0x07,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,(byte) 0x80,(byte) 0x9C,(byte) 0xF8,(byte) 0xF8,(byte) 0x88,(byte) 0x8C,(byte) 0xFC,(byte) 0x94,(byte) 0x94,(byte) 0x94,(byte) 0x94,(byte) 0x94,(byte) 0x84,0x00,0x00,(byte) 0xFC,(byte) 0x88,(byte) 0x88,(byte) 0x88,(byte) 0x88,(byte) 0x88,(byte) 0x88,(byte) 0x88,(byte) 0xFC,0x00,0x00,(byte) 0x80,(byte) 0x9C,(byte) 0xF8,(byte) 0xF8,(byte) 0x88,(byte) 0x8C,(byte) 0xFC,(byte) 0x94,(byte) 0x94,(byte) 0x94,(byte) 0x94,(byte) 0x94,(byte) 0x84,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,(byte) 0xC4,0x44,0x7C,0x60,0x00,(byte) 0xFC,0x0C,(byte) 0x88,(byte) 0xE0,0x38,0x0C,0x00,0x04,0x04,(byte) 0x84,(byte) 0x84,(byte) 0x84,(byte) 0x84,(byte) 0x84,(byte) 0x84,(byte) 0x84,(byte) 0x84,(byte) 0x84,0x04,0x04,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x6C,(byte) 0xEC,(byte) 0xC0,(byte) 0x80,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x6C,(byte) 0xEC,(byte) 0xC0,(byte) 0x80,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x19};
			
			byte zimo[]={0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x3E,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x3E,0x00,0x00,0x01,0x23,0x22,0x26,0x2C,0x3C,0x3E,0x20,0x28,0x2C,0x24,0x22,0x02,0x00,0x00,0x3E,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x2A,0x3E,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x13,0x12,0x1E,0x1E,0x01,0x3F,0x01,0x05,0x09,0x19,0x31,0x01,0x00,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x08,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x0E,0x0C,0x0C,0x0F,0x07,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x0E,0x0C,0x0C,0x0F,0x07,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,(byte) 0x80,(byte) 0x9C,(byte) 0xF8,(byte) 0xF8,(byte) 0x88,(byte) 0x8C,(byte) 0xFC,(byte) 0x94,(byte) 0x94,(byte) 0x94,(byte) 0x94,(byte) 0x94,(byte) 0x84,0x00,0x00,(byte) 0xFC,(byte) 0x88,(byte) 0x88,(byte) 0x88,(byte) 0x88,(byte) 0x88,(byte) 0x88,(byte) 0x88,(byte) 0xFC,0x00,0x00,(byte) 0x80,(byte) 0x9C,(byte) 0xF8,(byte) 0xF8,(byte) 0x88,(byte) 0x8C,(byte) 0xFC,(byte) 0x94,(byte) 0x94,(byte) 0x94,(byte) 0x94,(byte) 0x94,(byte) 0x84,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,(byte) 0xC4,0x44,0x7C,0x60,0x00,(byte) 0xFC,0x0C,(byte) 0x88,(byte) 0xE0,0x38,0x0C,0x00,0x04,0x04,(byte) 0x84,(byte) 0x84,(byte) 0x84,(byte) 0x84,(byte) 0x84,(byte) 0x84,(byte) 0x84,(byte) 0x84,(byte) 0x84,0x04,0x04,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x6C,(byte) 0xEC,(byte) 0xC0,(byte) 0x80,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x6C,(byte) 0xEC,(byte) 0xC0,(byte) 0x80,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
			
			msgProcessService.send2Client(session.getChannel(), msgProcessService.sendName_data(zimo));
		}
	}
	
	@RequestMapping("/reloadDeviceInfo")
	public void reloadDeviceInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		

		String mac = request.getParameter("mac");
		int testId=Integer.valueOf(request.getParameter("testId"));
		
		TestUser testUser= new TestUser();
		testUser.setTest_id(testId);
		
		Session session=sessionManager.findByTerminalShefenId(mac);
		
		if (session==null) {
			out.print("不在线");
		}else {
			testUser.setMac(mac);
			session.setTestUser(testUser);
		}
	}
	
	@RequestMapping("changeDevice")
	public void changeDevice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InterruptedException {
		
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonObject=new JSONObject();
		
		String mac1 = request.getParameter("mac1");
		String mac2 = request.getParameter("mac2");
		if (StringUtils.isEmpty(mac1) || StringUtils.isEmpty(mac2)) {
			jsonObject.put("state", 0);
			out.print(jsonObject.toString());//参数mac不能为空
			
			out.flush();
			out.close();
			return;
		}
		
		Hard hard1=deviceService.getByMac(mac1);
		Hard hard2=deviceService.getByMac(mac2);
		
		if (hard1==null || hard2==null || hard1.getHard_id().intValue()==hard2.getHard_id()) {
			jsonObject.put("state", 0);
			out.print(jsonObject.toString());//参数mac输入有误，没有对应的设备
			
			out.flush();
			out.close();
			return;
		}
		
		int result=deviceService.changeDevice(hard1, hard2);
		if (result==0) {
			jsonObject.put("state", 0);
			out.print(jsonObject.toString());//交换失败
			
			out.flush();
			out.close();
			return;
		}
		
		//重新加载设备对应信息
		Session session1=sessionManager.findByTerminalShefenId(hard1.getMac());
		if (session1!=null) {
			reload(hard1.getMac());
			msgProcessService.send2Client(session1.getChannel(), msgProcessService.setIDData(session1.getHard_no()));
		}
		
		Session session2=sessionManager.findByTerminalShefenId(hard2.getMac());
		if (session2!=null) {
			reload(hard2.getMac());
			msgProcessService.send2Client(session2.getChannel(), msgProcessService.setIDData(session2.getHard_no()));
		}
		
		
		jsonObject.put("state", 1);
		jsonObject.put("mac", hard2.getMac());
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		return;
	}
	
	public void reload(String mac){
		Session session=sessionManager.findByTerminalShefenId(mac);
		if (session!=null) {
			Hard hard=deviceService.getByMac(mac);
			session.setDevice_id(hard.getHard_id());
			session.setHard_no(hard.getHard_no());
			
			session.setTestUser(deviceService.getUserFromNow(mac));
		}
		
	}
	
	@RequestMapping("/deviceStatus")
	public void getDeviceStatus(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int testId=Integer.valueOf(request.getParameter("testId"));
		
		TestUser testUser=new TestUser();
		testUser.setTest_id(testId);
		
		List<TestUser> testUsers=deviceService.getTestUserFromBind(testId);
		
		for (TestUser testUserTemp : testUsers) {
			if (sessionManager.findByTerminalShefenId(testUserTemp.getMac())!=null) {
				testUserTemp.setDevice_status(TestUser.DEVICE_STATUS_ONLINE);
			}else {
				testUserTemp.setDevice_status(TestUser.DEVICE_STATUS_OFFLINE);
			}
		}
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("info", testUsers);
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
		
	}
	
	@RequestMapping("/restart")
	public void restart(HttpServletRequest request, HttpServletResponse response) throws IOException, NumberFormatException, InterruptedException{
		int testId=Integer.valueOf(request.getParameter("testId"));
		String info = request.getParameter("mac");
		
		List<Session> processSessionList=getProcessSession(testId, info);
		int success=0;
		for (Session session : processSessionList) {
			int ret=msgProcessService.send2Client(session.getChannel(), msgProcessService.restartData());
			if (ret!=0) {
				success+=1;
			}
		}
		
		PrintWriter out=response.getWriter();
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("total", processSessionList.size());
		jsonObject.put("success", success);
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	@RequestMapping("/setDeviceID")
	public void setDeviceID(HttpServletRequest request, HttpServletResponse response) throws IOException, NumberFormatException, InterruptedException{
		String hardIdString = request.getParameter("hardId");
		System.err.println("---------------------  "+hardIdString+"  ---------------------");
		String[] hardIdArray=hardIdString.split(",");
		
		JSONObject jsonObject=new JSONObject();
		StringBuffer successId=new StringBuffer();
		for (String hardId : hardIdArray) {
			Hard hard=deviceService.getById(Integer.valueOf(hardId));
			
			Session session=sessionManager.findByTerminalShefenId(hard.getMac());
			if (session!=null) {
				int ret=msgProcessService.send2Client(session.getChannel(), msgProcessService.setIDData(hard.getHard_no()));
				if (ret!=0) {
					if (successId.toString().length()>0) {
						successId.append(",");
					}
					successId.append(hardId);//发送成功
				}
			}
		}
		
		
		jsonObject.put("info", successId.toString());
		PrintWriter out=response.getWriter();
		
		out.print(jsonObject.toString());
		
		out.flush();
		out.close();
	}
	
	private List<Session> getProcessSession(int testId,String macInfo){
		List<Session> processSessions;
		if (StringUtils.isEmpty(macInfo)) {
			processSessions=sessionManager.getByTestId(testId);
		}else {
			processSessions=new ArrayList<Session>();
			for (String mac : macInfo.split(",")) {
				for (Session session : sessionManager.getByTestId(testId)) {
					if (session.getShefen_id().equals(mac)) {
						processSessions.add(session);
						break;
					}
				}
			}
		}
		
		return processSessions;
		
	}
	
	private List<Session> getProcessSessionByMac(int testId,String macInfo){
		List<Session> processSessions;
		if (StringUtils.isEmpty(macInfo)) {
			processSessions=sessionManager.getByTestId(testId);
		}else {
			processSessions=new ArrayList<Session>();
			for (String mac : macInfo.split(",")) {
				for (Session session : sessionManager.getByTestId(testId)) {
					if (session.getShefen_id().equals(mac)) {
						processSessions.add(session);
						break;
					}
				}
			}
		}
		
		return processSessions;
		
	}
	
	private List<Session> getProcessSessionByHardNo(int testId,String hard_no){
		List<Session> processSessions;
		if (StringUtils.isEmpty(hard_no)) {
			processSessions=sessionManager.getByTestId(testId);
		}else {
			processSessions=new ArrayList<Session>();
			for (String hardNo : hard_no.split(",")) {
				for (Session session : sessionManager.getByTestId(testId)) {
					if (session.getHard_no().intValue()==Integer.valueOf(hardNo).intValue()) {
						processSessions.add(session);
						break;
					}
				}
			}
		}
		
		return processSessions;
		
	}
	
	private void loadDeviceBind(int testId){
		TestUser testUser=null;
		for (Session session : sessionManager.toList()) {
			testUser=new TestUser();
			testUser.setTest_id(testId);
			testUser.setMac(session.getShefen_id());
			session.setTestUser(deviceService.getUser(testUser));
		}
	}
}
