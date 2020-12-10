package cn.ineutech.tpms.tcp;

/**
 * 
 * @name: TPMSConsts 
 * @description: 拨盘数据常量
 * @date 2018年2月1日 上午11:37:10
 * @author liululu
 */
public class TPMSConsts {
	
	//上送数据格式(结尾是换行符)：HEAD,客户端类型,uuid(唯一标识),数据类型,数据,时间戳
	// 数据包包头
	public static final String HEAD = "smg";
	
	// 客户端类型:评分端
	public static final String CLIENT_SCORE = "1";
	// 客户端类型:监控端
	public static final String CLIENT_MONITOR = "2";
	// 客户端类型:监控端
	public static final String CLIENT_FACE = "3";
	
	// 上送类型:身份UUID
//	public static final String UP_COMMAND_UUID="1";
	// 上送类型:分数
	public static final String UP_COMMAND_SCORE="2";
	// 上送类型:登陆信息确认
	public static final String UP_COMMAND_INFO_CONFIRM="3";
	// 上送类型:登陆(设备和座位号的绑定信息)
	public static final String UP_COMMAND_BIND="4";
	// 上送类型:测前进度
	public static final String UP_COMMAND_BEFORE="5";
	// 上送类型:测前问卷完成
	public static final String UP_COMMAND_BEFORE_END="6";
	// 上送类型:拨盘调试完成
	public static final String UP_COMMAND_DIAL_END="7";
	// 上送类型:测后进度
	public static final String UP_COMMAND_AFTER="8";
	// 上送类型:测后问卷完成
	public static final String UP_COMMAND_AFTER_END="9";
	// 上送类型:数据打包上传完成
	public static final String UP_COMMAND_PACKAGE_END="10";
	// 上送类型:表情是否在线
	public static final String UP_COMMAND_FACE_STATUS="11";
	// 上送类型:掉线重连
	public static final String UP_COMMAND_RELOGIN="12";
	// 上送类型:阶段状态(暂停/继续)
	public static final String UP_COMMAND_PERIOD_STATE="13";
	// 上送类型:阶段状态切换成功(数据:1暂停 2继续)
	public static final String UP_COMMAND_PERIOD_STATE_SUCCESS="14";
	// 上送类型:座位号调换(数据:人1编号=新座位号;人2编号=新座位号)
	public static final String UP_COMMAND_SEATNO_CHANGE="15";
	// 上送类型:脑电绑定(数据:人1id;人编号;脑电编号)
	public static final String UP_COMMAND_BRAIN_BIND="16";
	// 上送类型:心跳包
	public static final String UP_COMMAND_HEART="100";
	
	//下发数据格式(结尾是换行符)：HEAD,数据类型,数据,备注
	// 下发命令:座位号登陆结果
	public static final String DOWN_COMMAND_LOGIN="1";
	// 下发命令:阶段
	public static final String DOWN_COMMAND_PERIOD="2";
	// 下发命令:人员信息
	public static final String DOWN_COMMAND_INFO="3";
	// 下发命令:阶段状态
	public static final String DOWN_COMMAND_PERIOD_STATE="4";
	// 下发命令:分数(数据:分数,备注:座位号)
	public static final String DOWN_COMMAND_SCORE="5";
	// 下发命令:进度(数据:进度,备注:座位号)
	public static final String DOWN_COMMAND_PROCESS="6";
	
	// 下发命令:脑电在线状态
	public static final String DOWN_COMMAND_BRAIN_STATUS="7";
	// 下发命令:脑电数据
	public static final String DOWN_COMMAND_BRAIN="8";
	
	// 下发命令:登陆信息确认(数据:座位号)
	public static final String DOWN_COMMAND_INFO_CONFIRM="9";
	// 下发命令:测前问卷完成(数据:座位号)
	public static final String DOWN_COMMAND_BEFORE_END="10";
	// 下发命令:拨盘调试完成(数据:座位号)
	public static final String DOWN_COMMAND_DIAL_END="11";
	// 下发命令:测后问卷完成(数据:座位号)
	public static final String DOWN_COMMAND_AFTER_END="12";
	// 下发命令:数据打包上传完成(数据:座位号)
	public static final String DOWN_COMMAND_PACKAGE_END="13";
	
	// 下发命令:表情在线状态(数据:设备号;备注:在线状态)
	public static final String DOWN_COMMAND_FACE_STATUS="14";
	
	// 下发命令:信息确认
	public static final String DOWN_COMMAND_CONFIRM="15";
	
	// 下发命令:心跳应答
	public static final String DOWN_COMMAND_HEART_ANSWER="16";
	
	// 下发命令:阶段状态切换成功(数据:1暂停 2继续)
	public static final String DOWN_COMMAND_PERIOD_STATE_SUCCESS="17";
	// 下发命令:座位号调换成功
	public static final String DOWN_COMMAND_SEATNO_CHANGE_SUCCESS="18";
	// 下发命令:脑电绑定结果(数据:绑定结果  成功备注:人1id;人编号;脑电编号)
	public static final String DOWN_COMMAND_BRAIN_BIND_RESULT="19";
	
	// 登陆结果:成功
	public static final String LOGIN_SUCC="1";
	// 登陆结果:失败
	public static final String LOGIN_FAIL="2";
	// 登陆失败情况:未选择测试
	public static final String LOGIN_FAIL_1="1";
	// 登陆失败情况:座位号已登陆
	public static final String LOGIN_FAIL_2="2";
	// 登陆失败情况:座位号输入错误(没有人员分配该座位号)
	public static final String LOGIN_FAIL_3="3";
	
	// 阶段:人员信息
	public static final String PERIOD_INFO="1";
	// 阶段:测前问答
	public static final String PERIOD_BEFORE="2";
	// 阶段:开始打分
	public static final String PERIOD_SCORE_START="3";
	// 阶段:打分结束
	public static final String PERIOD_SCORE_END="4";
	// 阶段:测后问答
	public static final String PERIOD_AFTER="5";
	// 阶段:结束测试
	public static final String PERIOD_END="6";
	// 阶段:拨盘引导
	public static final String PERIOD_DIAL="7";

	// 阶段状态:暂停
	public static final String STATUS_PAUSE="1";
	// 阶段状态:恢复
	public static final String STATUS_BACK="2";

	
	// 表情状态:在线
	public static final String FACE_STATUS_ONLINE="1";
	// 表情状态:离线
	public static final String FACE_STATUS_OFFLINE="2";
	
	// 脑电绑定结果:成功
	public static final String BRAIN_BIND_SUCC="1";
	// 脑电绑定结果:失败
	public static final String BRAIN_BIND_FAIL="2";
	// 脑电绑定结果失败情况:该用户已绑定脑电
	public static final String BRAIN_BIND_FAIL_1="1";
	// 脑电绑定结果失败情况:该编号没有对应的脑电
	public static final String BRAIN_BIND_FAIL_2="2";
	// 脑电绑定结果失败情况:该脑电已绑定到其他人员,不能重复绑定
	public static final String BRAIN_BIND_FAIL_3="3";
	
	//数据分隔符
	public static final String SPLIT = ",";
	//数据包结尾符
	public static final String END = "\r\n";
	
	//问卷完成
	public static final String FINISH = "1";

	//控制台的分屏播放器请求的固定数据
	public static final String CONSOLE="client";
	public static byte[] consoleData;
	public static final String ACTIVE="连接";
	//视频暂停
	public static final String VIDEO_PAUSE="暂停";
	//视频恢复
	public static final String VIDEO_BACK="继续";


}
