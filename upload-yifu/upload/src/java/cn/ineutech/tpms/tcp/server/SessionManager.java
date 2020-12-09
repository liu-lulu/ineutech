package cn.ineutech.tpms.tcp.server;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import cn.ineutech.tpms.tcp.vo.Session;

/**
 * 
 * @name: SessionManager 
 * @description: 测试时的信息以及tcp客户端的管理
 * @date 2018年2月1日 下午1:54:49
 * @author liululu
 */
public class SessionManager {

	private static volatile SessionManager instance = null;


	// 控制台播放器
	private Map<String, Session> sessionIdMap;


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
		this.channelGroup = new DefaultChannelGroup(
				GlobalEventExecutor.INSTANCE);
	}

	public synchronized Session putSession(String key, Session value) {
		return sessionIdMap.put(key, value);
	}

	public synchronized Session removeBySessionId(String sessionId) {
		if (sessionId == null) {
			return null;
		}
		Session session = sessionIdMap.remove(sessionId);
		if (session == null) {
			return null;
		}

		return session;
	}

	public List<Session> sessionToList() {
		return this.sessionIdMap.entrySet().stream().map(e -> e.getValue())
				.collect(Collectors.toList());
	}

	public ChannelGroup getChannelGroup() {
		return channelGroup;
	}

	public void setChannelGroup(ChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
	}

}