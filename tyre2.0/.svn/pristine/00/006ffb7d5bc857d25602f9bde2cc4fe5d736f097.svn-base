package com.psylife.hardware.queue;

import java.util.LinkedList;

/**
 * 消息队列
 * @author Administrator
 *
 */
public class MessageQueue {

	/**
	 * 请求消息数组
	 */
	private LinkedList requestList = new LinkedList();
	
	private boolean isNotifyAllReq=false;
	
	
	/**
	 * 插入请求消息
	 * @param requestMsg
	 */
	public void pushRequest(Object requestMsg) {
	    synchronized (requestList) {
	      requestList.add(requestMsg);
	      requestList.notifyAll();
	    }
	  }
	
	/**
	 * 取出请求消息，并从队列中移除
	 * @return
	 */
	public Object removeFirst() {
	    synchronized (requestList) {
	      // 如果没有任务，就锁定在这里
	      while (requestList.isEmpty()) {
	        try {
	        	//System.out.println("removeReqFirst");
	          requestList.wait(); //等待解锁
	          if(isNotifyAllReq){
	        	  isNotifyAllReq=false;
	        	  return null;
	          }
	        } catch (InterruptedException ie) {
	          ie.printStackTrace();
	        }
	      }
	      return requestList.removeFirst();
	    }
	  }

	public void notfiAllObject(){
		isNotifyAllReq=true;
		if(requestList.isEmpty()){
			 synchronized (requestList) {
				 requestList.notifyAll();
			 }	
		}
	}
}
