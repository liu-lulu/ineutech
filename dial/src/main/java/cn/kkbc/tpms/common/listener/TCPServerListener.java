package cn.kkbc.tpms.common.listener;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.kkbc.entity.TestScoreVO;
import com.kkbc.entity.TestUser;
import com.kkbc.service.DeviceService;
import com.kkbc.service.impl.DeviceServiceImpl;
import com.kkbc.util.SpringContextUtils;

import cn.kkbc.tpms.common.Consts;
import cn.kkbc.tpms.tcp.server.TCPServer;

//@WebListener
public class TCPServerListener implements ServletContextListener {

	private TCPServer server = null;

	public TCPServerListener() {
		super();
//		this.server = new TCPServer(Consts.tcp_port);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		if (this.server != null)
			this.server.stopServer();
	}

	public void contextInitialized(ServletContextEvent sce) {
		WebApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        SpringContextUtils.setApplicationContext(context);
//        DeviceService service=(DeviceServiceImpl) context.getBean("deviceService");
//        List<TestUser> users=service.getAllTestUser();
//        Random rd=new Random();
//        TestScoreVO vo=null;
//        while (true) {
//        	for (TestUser testUser : users) {
//        		vo=new TestScoreVO();
//        		vo.setUser_group(testUser.getUser_group());
//            	vo.setCreate_time(new Date());
//            	vo.setScore(rd.nextInt(100));
//            	vo.setModel(rd.nextInt(2)+1);
//            	vo.setHard_id(testUser.getHard_id());
//            	vo.setHuman_id(testUser.getHuman_id());
//            	vo.setMac(testUser.getMac());
//            	vo.setSeat_no(testUser.getSeat_no());
//            	vo.setTest_id(testUser.getTest_id());
//    			service.saveData(vo);
//			}
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
        
        this.server = new TCPServer(Consts.tcp_port);
		try {
			this.server.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
