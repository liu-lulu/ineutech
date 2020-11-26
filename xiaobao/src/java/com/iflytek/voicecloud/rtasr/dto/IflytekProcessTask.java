package com.iflytek.voicecloud.rtasr.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IflytekProcessTask {
	
	private static volatile IflytekProcessTask instance = null;
	private Map<String, Integer> taskIdMap=new HashMap<String, Integer>();
    private IflytekProcessTask() { }
    public static IflytekProcessTask getInstance() {
        if (instance == null) {
            synchronized(IflytekProcessTask.class) {
                if (instance == null){
                    instance = new IflytekProcessTask();
                }
            }
        }
       return instance; 
    }
    
    public void putTaskId(String taskId,Integer id) {
    	taskIdMap.put(taskId, id);
	}
    public void putTaskId(List<String> taskIdList) {
    	for (String taskId : taskIdList) {
    		putTaskId(taskId,null);
		}
	}
	public Map<String, Integer> getTaskIdMap() {
		return taskIdMap;
	}
	public void setTaskIdMap(Map<String, Integer> taskIdMap) {
		this.taskIdMap = taskIdMap;
	}

}
