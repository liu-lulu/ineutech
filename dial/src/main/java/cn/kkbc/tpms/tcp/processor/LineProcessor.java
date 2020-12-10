package cn.kkbc.tpms.tcp.processor;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.kkbc.tpms.tcp.server.SessionManager;
import cn.kkbc.tpms.tcp.vo.Session;

import com.kkbc.entity.TestLineData;
import com.kkbc.entity.TestUser;
import com.kkbc.service.DeviceService;
import com.kkbc.service.impl.DeviceServiceImpl;
import com.kkbc.util.SpringContextUtils;
import com.kkbc.util.StringHelper;

public class LineProcessor extends Thread implements Processor {

	private Logger log = LoggerFactory.getLogger(getClass());
	private Logger weblog = LoggerFactory.getLogger("weblog");

	private volatile boolean isRunning = false;
	private SessionManager sessionManager;
	private DeviceService service;


	public LineProcessor() {
		this.sessionManager = SessionManager.getInstance();
		this.service=(DeviceServiceImpl) SpringContextUtils.getContext().getBean("deviceService");
		this.setName("TCP-LineProcessor");
		this.setDaemon(true);
	}

	@Override
	public synchronized void startProcess() {
		if (this.isRunning) {
			throw new IllegalStateException(this.getName()
					+ " is already started .");
		}
		this.isRunning = true;
		super.start();
		this.log.info("计算平均值启动完毕...");
	}

	@Override
	public synchronized void stopProcess() {
		if (!this.isRunning) {
			throw new IllegalStateException(this.getName()
					+ " is not yet started .");
		}
		this.isRunning = false;
		this.interrupt();
		this.log.info("计算平均值服务已经停止...");
	}

	@Override
	public void run() {
		Map<Integer, Map<Integer, TestLineData>> lineDataMap;
		while (this.isRunning) {
			Date start=new Date();
			
			sessionManager.clearTestLineDatas();
			lineDataMap=sessionManager.getLineDataMap();
			
			//每秒统计一次
			long sleepTime = 1000L-100;

			Iterator<Entry<String, Session>> ite = sessionManager.entrySet()
					.iterator();
			try {
				if (lineDataMap.size()>0) {//有已开始的测试
					while (ite.hasNext()) {
						Session session=ite.next().getValue();
						if (session.getTestUser()!=null&session.getState()==Session.STATE_TESTING) {
							TestUser testUser=session.getTestUser();
							
							//统计所有拨盘的平均值
							TestLineData lineData=new TestLineData(testUser.getTest_id(), session.getScore());
							sessionManager.putLineDatas(testUser.getTest_id(), TestLineData.KEY_AVG, lineData);
							log.info(session.getHard_no()+"--sex:--"+testUser.getUser_sex()+"--score:--"+session.getScore());
							
							//统计男性打分值
							if (testUser.getUser_sex()==1) {
								TestLineData manData=new TestLineData(testUser.getTest_id(), session.getScore());
								sessionManager.putLineDatas(testUser.getTest_id(), TestLineData.KEY_MAN, manData);
							}else {//统计女性打分值
								TestLineData womanData=new TestLineData(testUser.getTest_id(), session.getScore());
								sessionManager.putLineDatas(testUser.getTest_id(), TestLineData.KEY_WOMAN, womanData);
							}
						}
					}
					
					//每秒钟的平均值入库
					service.saveLineData(sessionManager.getAllTestLineDatas());
					
					Date end=new Date();
					
					long diff=end.getTime()-start.getTime();
					
					if (diff<sleepTime) {
						Thread.sleep(sleepTime);
					}
					
				}else {//没有已开始的测试，等待5秒
					Thread.sleep(5000);
				}
				
				
			} catch (Exception e) {
				log.error("计算平均值出现异常:{}", StringHelper.getTrace(e));
				e.printStackTrace();
			}
	}
}
	
	public static void main(String[] args) throws InterruptedException {
		Date date=new Date();
		Thread.sleep(100);
		Date date2=new Date();
		System.out.println(date2.getTime()-date.getTime());
	}
}
