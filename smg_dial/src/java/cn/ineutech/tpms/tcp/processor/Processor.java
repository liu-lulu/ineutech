package cn.ineutech.tpms.tcp.processor;

/**
 * 
 * @name: Processor 
 * @description: 消息处理
 * @date 2018年2月1日 上午11:39:58
 * @author liululu
 */
public interface Processor {

	/**
	 * 开始处理数据
	 */
	void startProcess();

	/**
	 * 结束处理数据
	 */
	void stopProcess();

}