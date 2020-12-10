package com.kkbc.hardware.send;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.kkbc.hardware.DtuOperation;
import com.kkbc.hardware.HardwareElement;
import com.kkbc.hardware.UDPServerManager;

/**
 * 发送公共方法
 * @author xu
 *
 */
public class SendManager {

private static SendManager instance=null;
	
	private SendManager(){}
	
	
	public static SendManager getInstance(){
		if(instance==null){
			synchronized (SendManager.class) {
				if(instance==null){
					instance=new SendManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * 根据dtuId和包序号获取dtu操作
	 * @param dtuId
	 * @param no 包序号,命令帧序号
	 * @return
	 */
	public DtuOperation getDtuOperationByDtuIdNo(String dtuId,int no){
		if(dtuId==null){
			return null;
		}
		Map<String, DtuOperation> dtuOperationMap=UDPServerManager.getInstance().getDtuOperationMap();
		for(Iterator<Entry<String, DtuOperation>> 
	    ite = dtuOperationMap.entrySet().iterator(); ite.hasNext();){
			Entry<String, DtuOperation> entry = ite.next();  
			if(dtuId.equals(entry.getValue().getDtu_id())&&entry.getValue().getNo()==no){
				return entry.getValue();
			}
		}
		return null;
	}
	
	/**
	 * 根据dtuId和包序号获取dtu操作
	 * @param dtuId
	 * @param no 包序号,命令帧序号
	 * @return
	 */
	public HardwareElement getHardwareElementByCarNum(String carNum){
		if(carNum==null){
			return null;
		}
		Map<String, HardwareElement> channelElement=UDPServerManager.getInstance().getChannelElement();
		for(Iterator<Entry<String, HardwareElement>> 
	    ite = channelElement.entrySet().iterator(); ite.hasNext();){
			Entry<String, HardwareElement> entry = ite.next();  
			if(carNum.equals(entry.getValue().getCarNum())){
				return entry.getValue();
			}
		}
		return null;
	}
	
}
