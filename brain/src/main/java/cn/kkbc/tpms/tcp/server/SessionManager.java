package cn.kkbc.tpms.tcp.server;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import cn.kkbc.tpms.tcp.vo.Session;

public class SessionManager {

	private static volatile SessionManager instance = null;
	// netty生成的sessionID和Session的对应关系
	private Map<String, Session> sessionIdMap;
	// MAC地址和netty生成的sessionID的对应关系
	private Map<String, String> shefenIdMap;
	
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

	public List<Session> toList() {
		return this.sessionIdMap.entrySet().stream().map(e -> e.getValue()).collect(Collectors.toList());
	}

	public ChannelGroup getChannelGroup() {
		return channelGroup;
	}

	public void setChannelGroup(ChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
	}

}