package com.iflytek.voicecloud.rtasr;

import java.util.Iterator;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.voicecloud.rtasr.dto.ApiResultDto;
import com.iflytek.voicecloud.rtasr.dto.IflytekProcessTask;
import com.ineutech.entity.VisitVoice;
import com.ineutech.service.UserService;
import com.ineutech.service.impl.UserServiceImpl;
import com.ineutech.util.SpringContextUtils;
import com.ineutech.util.WeblfasrUtil;

public class IflytekTaskThread extends Thread{
	
	private volatile boolean isRunning = false;
	private UserService userService;
	
	public IflytekTaskThread(){
		this.userService=(UserServiceImpl) SpringContextUtils.getContext().getBean("userService");
	}

	@Override
	public synchronized void start() {
		isRunning=true;
		super.start();
	}

	@Override
	public void interrupt() {
		isRunning=false;
		super.interrupt();
	}

	@Override
	public void run() {

		IflytekProcessTask task=IflytekProcessTask.getInstance();
		task.putTaskId(userService.taskIdNoVoiceContent());
		
		// 轮询获取任务结果
        while (isRunning) {
        	Iterator<String> taskIdIterator=task.getTaskIdMap().keySet().iterator();
            try {
                
                while (taskIdIterator.hasNext() ) {
    				String taskId=taskIdIterator.next();
    				
    				ApiResultDto taskProgress = WeblfasrUtil.getProgress(taskId);
                    if (taskProgress.getOk() == 0) {
                        if (taskProgress.getErr_no() != 0) {
                            System.out.println("任务失败：" + JSON.toJSONString(taskProgress));
                        }

                        String taskStatus = taskProgress.getData();
                        System.out.println("任务处理中：" + taskStatus);
                        
                        if (JSON.parseObject(taskStatus).getInteger("status") == 9) {
                            System.out.println("任务完成！");
                            // 获取结果
                            String result=WeblfasrUtil.getResult(taskId);
                            
                            StringBuffer voiceResult=new StringBuffer();
                            JSONArray resultArray=JSONArray.parseArray(result);
                            resultArray.stream().forEach(jsonObject -> voiceResult.append(((JSONObject) jsonObject).get("onebest")));
                            
                            System.out.println("转写结果: " + voiceResult.toString());
                            taskIdIterator.remove();
                            
                            userService.updVoiceContent(new VisitVoice(taskId,voiceResult.toString()));
                        }

                    } else {
                        System.out.println(taskId+":获取任务进度失败！"+taskProgress.getFailed());
                        if (taskProgress.getErr_no()==26602) {//任务ID不存在
                        	taskIdIterator.remove();
						}
                    }
    			}
                
                System.out.println("sleep a while Zzz" );
                //Thread.sleep(1000*60*10);//每隔10分钟轮询一次
                Thread.sleep(1000*60);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
	
	}
	
}
