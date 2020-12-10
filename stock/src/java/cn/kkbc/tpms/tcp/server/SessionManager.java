package cn.kkbc.tpms.tcp.server;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import org.apache.commons.lang.StringUtils;

import cn.kkbc.tpms.tcp.TPMSConsts;
import cn.kkbc.tpms.tcp.vo.Session;

public class SessionManager {

	private static volatile SessionManager instance = null;
	// netty生成的sessionID和Session的对应关系
	private Map<String, Session> sessionIdMap;
	// MAC地址和netty生成的sessionID的对应关系
	private Map<String, String> shefenIdMap;
	
	private ChannelGroup channelGroup;
	
	private Integer type;
	
	private Integer type_way=TPMSConsts.WAY_SCAN;

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
	

	public Set<String> keySet() {
		return sessionIdMap.keySet();
	}

	public void forEach(BiConsumer<? super String, ? super Session> action) {
		sessionIdMap.forEach(action);
	}

	public Set<Entry<String, Session>> entrySet() {
		return sessionIdMap.entrySet();
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
	
	public List<Session> toList() {
		List<Session> list=new ArrayList<Session>();
		
		for(Iterator<Entry<String, Session>> 
	    ite = sessionIdMap.entrySet().iterator(); ite.hasNext();){
			Entry<String, Session> entry = ite.next();  
			list.add(entry.getValue());
		}
		return list;
	}
	
	public Session getBindInfoByDeviceNo(String deviceNo){
		
		for(Iterator<Entry<String, Session>> 
	    ite = sessionIdMap.entrySet().iterator(); ite.hasNext();){
			Entry<String, Session> entry = ite.next();  
			Session session=entry.getValue();
			if (session.getDeviceNo().equals(deviceNo)) {
				return session;
			}
		}
		return null;
	}
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType_way() {
		return type_way;
	}

	public void setType_way(Integer type_way) {
		this.type_way = type_way;
	}
}