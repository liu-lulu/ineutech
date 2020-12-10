package cn.kkbc.tpms.tcp.server;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import cn.kkbc.tpms.tcp.entity.DialOperation;
import cn.kkbc.tpms.tcp.vo.Session;

import com.kkbc.entity.TestLineData;

public class SessionManager {

	private static volatile SessionManager instance = null;
	// netty生成的sessionID和Session的对应关系
	private Map<String, Session> sessionIdMap;
	// MAC地址和netty生成的sessionID的对应关系
	private Map<String, String> shefenIdMap;
	
	//需要实时计算平均值的测试
	private Map<Integer, Map<Integer, TestLineData>> lineDataMap;
	
	/**
	 * 拨盘操作队列数据,key为MAC+&+操作日志id
	 */
	private Map<String, DialOperation> operationMap;
	
	private ChannelGroup channelGroup;

	public static SessionManager getInstance() {
		if (instance == null) {
			synchronized (SessionManager.class) {
				if (instance == null) {
					instance = new SessionManager();
				}
			}
		}
		return instance;
	}

	public SessionManager() {
		this.sessionIdMap = new ConcurrentHashMap<>();
		this.shefenIdMap = new ConcurrentHashMap<>();
		this.channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
		this.operationMap=new ConcurrentHashMap<>();
		this.lineDataMap=new ConcurrentHashMap<>();
	}

	public boolean containsKey(String sessionId) {
		return sessionIdMap.containsKey(sessionId);
	}

	public boolean containsSession(Session session) {
		return sessionIdMap.containsValue(session);
	}

	public Session findBySessionId(String id) {
		return sessionIdMap.get(id);
	}

	public Session findByTerminalShefenId(String shefenId) {
		String sessionId = this.shefenIdMap.get(shefenId);
		if (sessionId == null)
			return null;
		return this.findBySessionId(sessionId);
	}

	public synchronized Session put(String key, Session value) {
		if (StringUtils.isNotEmpty(value.getShefen_id())) {
			this.shefenIdMap.put(value.getShefen_id(), value.getId());
		}
		return sessionIdMap.put(key, value);
	}

	public synchronized Session removeBySessionId(String sessionId) {
		if (sessionId == null)
			return null;
		Session session = sessionIdMap.remove(sessionId);
		if (session == null)
			return null;
		if (session.getShefen_id() != null)
			this.shefenIdMap.remove(session.getShefen_id());
		return session;
	}
	
	public synchronized Map<Integer, TestLineData> putTestLine(int testId){
		if (lineDataMap.containsKey(testId)) {
			return lineDataMap.get(testId);
		}
		Map<Integer, TestLineData> avgData=new HashMap<Integer, TestLineData>();
		return lineDataMap.put(testId, avgData);
	}
	
	public TestLineData putLineDatas(int testId,int key,TestLineData data){
		Map<Integer, TestLineData> avgData=lineDataMap.get(testId);
		if (avgData!=null) {
			if (avgData.containsKey(key)) {
				TestLineData lineData=avgData.get(key);
				lineData.setCount(lineData.getCount()+1);
				lineData.setScore(lineData.getScore()+data.getScore());
				return avgData.put(key, lineData);
			}else {
				data.setCount(1);
				data.setKey(key);
				data.setTest_id(testId);
				return avgData.put(key, data);
			}
		}
		return null;
	}
	
	public List<TestLineData> getAllTestLineDatas(){
		List<TestLineData> datas=new ArrayList<TestLineData>();
		for (Integer testId : lineDataMap.keySet()) {
			for (Iterator<Entry<Integer, TestLineData>> ite =  lineDataMap.get(testId).entrySet().iterator(); ite.hasNext(); ) {
				Entry<Integer, TestLineData> data=ite.next();
				if (data.getValue().getCount()!=0) {
					datas.add(data.getValue());
				}
			}
		}
		
		return datas;
	}
	
	public void clearTestLineDatas(){
		for (Integer testId : lineDataMap.keySet()) {
			for (Iterator<Entry<Integer, TestLineData>> ite =  lineDataMap.get(testId).entrySet().iterator(); ite.hasNext(); ) {
				Entry<Integer, TestLineData> entry=ite.next();
				TestLineData data=entry.getValue();
				data.setScore(0);
				data.setCount(0);
				entry.setValue(data);
			}
		}
		
	}

	public Set<String> keySet() {
		return sessionIdMap.keySet();
	}

	public void forEach(BiConsumer<? super String, ? super Session> action) {
		sessionIdMap.forEach(action);
	}

	public Set<Entry<String, Session>> entrySet() {
		return sessionIdMap.entrySet();
	}

	public List<Session> toList() {
		return this.sessionIdMap.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
	}

	public ChannelGroup getChannelGroup() {
		return channelGroup;
	}

	public void setChannelGroup(ChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
	}

	public Map<String, String> getShefenIdMap() {
		return shefenIdMap;
	}

	public void setShefenIdMap(Map<String, String> shefenIdMap) {
		this.shefenIdMap = shefenIdMap;
	}

	public Map<String, DialOperation> getOperationMap() {
		return operationMap;
	}

	public void setOperationMap(Map<String, DialOperation> operationMap) {
		this.operationMap = operationMap;
	}
	
	public DialOperation findOperateInfo(String mac,int command){
		if(mac==null){
			return null;
		}
		for(Iterator<Entry<String, DialOperation>> 
	    ite = operationMap.entrySet().iterator(); ite.hasNext();){
			Entry<String, DialOperation> entry = ite.next();  
			if(mac.equals(entry.getValue().getMac())&&entry.getValue().getCommand()==command&&entry.getValue().getType()==DialOperation.TYPE_SEND){
				System.out.println("---key----"+entry.getKey());
				return entry.getValue();
			}
		}
		return null;
		
	}
	
	
	public Map<Integer, Map<Integer, TestLineData>> getLineDataMap() {
		return lineDataMap;
	}

	public void setLineDataMap(Map<Integer, Map<Integer, TestLineData>> lineDataMap) {
		this.lineDataMap = lineDataMap;
	}

	public List<Session> getByTestId(int testId){
		List<Session> testDevice=new ArrayList<Session>();
		
		for(Iterator<Entry<String, Session>> 
	    ite = sessionIdMap.entrySet().iterator(); ite.hasNext();){
			Entry<String, Session> entry = ite.next();  
			if(entry.getValue().getTestUser()!=null&&entry.getValue().getTestUser().getTest_id()==testId){
				testDevice.add(entry.getValue());
			}
		}
		return testDevice;
		
	}

}