package cn.ineutech.tpms.tcp.service.msg;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ineutech.tpms.tcp.TPMSConsts;
import cn.ineutech.tpms.tcp.server.SessionManager;
import cn.ineutech.tpms.tcp.vo.Session;

import com.ineutech.entity.DeviceData;
import com.ineutech.entity.TestInfo;
import com.ineutech.entity.TestUser;
import com.ineutech.vo.TestUserSessionVO;

/**
 * 
 * @name: BaseMsgProcessService 
 * @description: 给tcp客户端(拨盘用户端/检测端)发送数据
 * @date 2018年2月1日 下午2:16:18
 * @author liululu
 */
public class BaseMsgProcessService {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	private SessionManager sessionManager;

	public BaseMsgProcessService() {
		this.sessionManager = SessionManager.getInstance();
	}

	protected ByteBuf getByteBuf(byte[] arr) {
		ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(arr.length);
		byteBuf.writeBytes(arr);
		return byteBuf;
	}

	/**
	 * 给tcp客户端发送数据
	 * @param channel 发送管道
	 * @param arr 发送的数据
	 * @return
	 * @throws InterruptedException
	 */
	public int send2Client(Channel channel, byte[] arr) throws InterruptedException {
		try {
			log.info("服务器发送数据:{}",new String(arr, "UTF-8"));
			ChannelFuture future = channel.writeAndFlush(getByteBuf(arr)).sync();
			if (!future.isSuccess()) {
				log.error("发送数据出错:{}", future.cause());
				return 0;
			}
//			log.info("服务器发送数据结束:{}",new String(arr, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * 给所有的拨盘检测端发送数据
	 * @param arr 发送的数据
	 * @throws InterruptedException
	 */
	public void sendToMonitor(byte[] arr) throws InterruptedException{
		List<Session> monitors=sessionManager.getMonitorOrScore(TPMSConsts.CLIENT_MONITOR);
		for (Session monitor : monitors) {
			send2Client(monitor.getChannel(), arr);
		}
	}
	
	/**
	 * 登陆结果
	 * @param loginResult 登陆结果
	 * @param comment 注释
	 * @return
	 */
	public byte[] loginResult(String loginResult,String comment){
		return sendData(TPMSConsts.DOWN_COMMAND_LOGIN, loginResult,comment);
	}
	
	/**
	 * 测试阶段(字节数据)
	 * @param period 测试阶段
	 * @param comment 注释
	 * @return
	 */
	public byte[] testPeriod(String period,String comment){
		return sendData(TPMSConsts.DOWN_COMMAND_PERIOD, period,comment);
	}
	
	/**
	 * 测试阶段(字符串数据)
	 * @param period 测试阶段
	 * @param comment 注释
	 * @return
	 */
	public String testPeriodString(String period,String comment){
		return getSendData(TPMSConsts.DOWN_COMMAND_PERIOD, period,comment);
	}
	
	/**
	 * 发送人员信息(字节数据)
	 * @param info 人员信息
	 * @return
	 */
	public byte[] sendUserInfo(TestUser info){
		return sendData(TPMSConsts.DOWN_COMMAND_INFO, info.toString(),"");
	}
	
	/**
	 * 发送人员信息(字符串数据)
	 * @param info 人员信息
	 * @return
	 */
	public String sendUserInfoString(TestUserSessionVO info){
		return getSendData(TPMSConsts.DOWN_COMMAND_INFO, sessionManager.getTestInfo().toString()+info.toString(),"");
	}
	
	/**
	 * 阶段状态(字节数据)
	 * @param state 阶段状态
	 * @return
	 */
	public byte[] periodStatus(String state){
		return sendData(TPMSConsts.DOWN_COMMAND_PERIOD_STATE, state,"");
	}
	
	/**
	 * 阶段状态(字符串数据)
	 * @param state
	 * @return
	 */
	public String periodStatusString(String state){
		return getSendData(TPMSConsts.DOWN_COMMAND_PERIOD_STATE, state,"");
	}
	
	/**
	 * 给监控端发送分数
	 * @param score 分数
	 * @param seatNo 座位号
	 * @return
	 */
	public byte[] sendScoreToMonitor(String score,Integer seatNo){
		return sendData(TPMSConsts.DOWN_COMMAND_SCORE, score,seatNo+"");
	}
	
	/**
	 * 给监控端发送进度
	 * @param process 进度
	 * @param seatNo 座位号
	 * @return
	 */
	public byte[] sendProcessToMonitor(String process,Integer seatNo){
		return sendData(TPMSConsts.DOWN_COMMAND_PROCESS, process,seatNo+"");
	}
	
	/**
	 * 给监控端发送脑电数据
	 * @param brainData 脑电数据
	 * @param seatNo 座位号
	 * @return
	 */
	public byte[] sendBrainDataToMonitor(DeviceData brainData,Integer seatNo){
		return sendData(TPMSConsts.DOWN_COMMAND_BRAIN, brainData.toString(),seatNo+"");
	}
	
	/**
	 * 脑电登陆结果
	 * @param loginResult 登陆结果
	 * @param comment 注释(座位号)
	 * @return
	 */
	public byte[] brainLoginResult(String loginResult,String comment){
		return sendData(TPMSConsts.DOWN_COMMAND_BRAIN_STATUS, loginResult,comment);
	}
	
	/**
	 * 给监控端发送数据
	 * @param dataType 数据类型
	 * @param seatNo 座位号
	 * @return
	 */
	public byte[] sendDataToMonitor(String dataType,String seatNo,String commment){
		return sendData(dataType,seatNo,commment);
	}
	
	/**
	 * 信息确认
	 * @param dataType 数据类型
	 * @param seatNo 座位号
	 * @return
	 */
	public byte[] userConfirm(String seatNo){
		return sendData(TPMSConsts.DOWN_COMMAND_CONFIRM, seatNo, "");
	}
	
	/**
	 * 心跳包应答
	 * @return
	 */
	public byte[] heartAnswer(){
		return sendData(TPMSConsts.DOWN_COMMAND_HEART_ANSWER, "", "");
	}
	
	/**
	 * 登陆时根据当前的测试阶段发送对应的信息
	 * @param user 用户信息
	 * @return
	 */
	public byte[] getOnlineInfo(TestUserSessionVO user){
		TestInfo testInfo=sessionManager.getTestInfo();
		if (testInfo!=null) {
			StringBuffer content=new StringBuffer();
			String periodComment="";//如果在试卷阶段，则为试卷完成状态
			if (TPMSConsts.PERIOD_BEFORE.equals(testInfo.getPeriod())||TPMSConsts.PERIOD_AFTER.equals(testInfo.getPeriod())) {
				//获取之前的测试进度
				TestUserSessionVO before=sessionManager.getUserById(user.getUser().getHumanId());
				if (before!=null) {
					if (TPMSConsts.PERIOD_BEFORE.equals(testInfo.getPeriod())) {
						periodComment=before.getBeforeFinish();
					}else {
						periodComment=before.getAfterFinish();
					}
				}
			}
			content.append(testPeriodString(testInfo.getPeriod(), periodComment));
			
			if (TPMSConsts.PERIOD_BEFORE.equals(testInfo.getPeriod())||TPMSConsts.PERIOD_SCORE_START.equals(testInfo.getPeriod())||TPMSConsts.PERIOD_AFTER.equals(testInfo.getPeriod())) {
				if (TPMSConsts.STATUS_PAUSE.equals(sessionManager.getTestInfo().getStatus())) {//暂停状态
					content.append(periodStatusString(TPMSConsts.STATUS_PAUSE));
				}
			}
			content.append(sendUserInfoString(user));
			
			return content.toString().getBytes(CharsetUtil.UTF_8);
		}
		return null;
	}
	
	/**
	 * 组合发送的数据(字节数组类型)
	 * @param command 命令类型
	 * @param data 数据
	 * @param comment 注释
	 * @return
	 */
	private byte[] sendData(String command,String data,String comment){
		return getSendData(command, data, comment).getBytes(CharsetUtil.UTF_8);
	}
	
	
	/**
	 * 组合发送的数据(字符串类型)
	 * @param command 命令类型
	 * @param data 数据
	 * @param comment 注释
	 * @return
	 */
	private String getSendData(String command,String data,String comment){
		StringBuffer content=new StringBuffer(TPMSConsts.HEAD);
		content.append(TPMSConsts.SPLIT);
		content.append(command);
		content.append(TPMSConsts.SPLIT);
		if (StringUtils.isEmpty(data)) {
			data=" ";
		}
		content.append(data);
		content.append(TPMSConsts.SPLIT);
		if (StringUtils.isEmpty(comment)) {
			comment=" ";
		}
		content.append(comment);
		content.append(TPMSConsts.END);
		return content.toString();
	}
}
