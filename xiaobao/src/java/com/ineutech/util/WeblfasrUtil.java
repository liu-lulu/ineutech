package com.ineutech.util;


import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.iflytek.voicecloud.rtasr.dto.ApiResultDto;
import com.iflytek.voicecloud.rtasr.dto.IflytekProcessTask;
import com.iflytek.voicecloud.rtasr.util.EncryptUtil;
import com.iflytek.voicecloud.rtasr.util.HttpUtil;
import com.iflytek.voicecloud.rtasr.util.SliceIdGenerator;

public class WeblfasrUtil {

	public static final String LFASR_HOST = "http://raasr.xfyun.cn/api";

    public static final String APPID = "5f3364d7";
    public static final String SECRET_KEY = "08d28171da1f7af00175e8f64867c1a3";
    public static final String PREPARE = "/prepare";
    public static final String UPLOAD = "/upload";
    public static final String MERGE = "/merge";
    public static final String GET_RESULT = "/getResult";
    public static final String GET_PROGRESS = "/getProgress";

    /**
     * 文件分片大小,可根据实际情况调整
     */
    public static final int SLICE_SICE = 10485760;// 10M
    //public static final int SLICE_SICE = 10240;// 分片测试10kb
    
    public static String upload(MultipartFile file){
    	//File audio = new File("./resource/lfasr.wav");
        try (BufferedInputStream fis = new BufferedInputStream(file.getInputStream())) {
            // 预处理
            String taskId = prepare(file.getSize(),file.getOriginalFilename());

            // 分片上传文件
            int len = 0;
            byte[] slice = new byte[SLICE_SICE];
            SliceIdGenerator generator = new SliceIdGenerator();
            while ((len =fis.read(slice)) > 0) {
                // 上传分片
                if (fis.available() == 0) {
                    slice = Arrays.copyOfRange(slice, 0, len);
                }
                uploadSlice(taskId, generator.getNextSliceId(), slice);
                //System.out.println("upload...");
            }

            // 合并文件
            merge(taskId);

            IflytekProcessTask task=IflytekProcessTask.getInstance();
            task.putTaskId(taskId, null);
            
            return taskId;
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }catch (RuntimeException e1) {
            e1.printStackTrace();
        }
    	return null;
    }
    
    /**
     * 获取每个接口都必须的鉴权参数
     * 
     * @return
     * @throws SignatureException 
     */
    public static Map<String, String> getBaseAuthParam(String taskId) throws SignatureException {
        Map<String, String> baseParam = new HashMap<String, String>();
        String ts = String.valueOf(System.currentTimeMillis() / 1000L);
        baseParam.put("app_id", APPID);
        baseParam.put("ts", ts);
        baseParam.put("signa", EncryptUtil.HmacSHA1Encrypt(EncryptUtil.MD5(APPID + ts), SECRET_KEY));
        if (taskId != null) {
            baseParam.put("task_id", taskId);
        }

        return baseParam;
    }

    /**
     * 预处理
     * 
     * @param audio     需要转写的音频
     * @return
     * @throws SignatureException 
     */
    public static String prepare(long fileLenth,String fileName) throws SignatureException {
        Map<String, String> prepareParam = getBaseAuthParam(null);
//        long fileLenth = audio.length();

        prepareParam.put("file_len", fileLenth + "");
        prepareParam.put("file_name", fileName);
        prepareParam.put("slice_num", (fileLenth/SLICE_SICE) + (fileLenth % SLICE_SICE == 0 ? 0 : 1) + "");

        /********************TODO 可配置参数********************/
        // 转写类型
//        prepareParam.put("lfasr_type", "0");
        // 开启分词
//        prepareParam.put("has_participle", "true");
        // 说话人分离
//        prepareParam.put("has_seperate", "true");
        // 设置多候选词个数
//        prepareParam.put("max_alternatives", "2");
        // 是否进行敏感词检出
//        prepareParam.put("has_sensitive", "true");
        // 敏感词类型
//        prepareParam.put("sensitive_type", "1");
        // 关键词
//        prepareParam.put("keywords", "科大讯飞,中国");
        /****************************************************/

        String response = HttpUtil.post(LFASR_HOST + PREPARE, prepareParam);
        if (response == null) {
            throw new RuntimeException("预处理接口请求失败！");
        }
        ApiResultDto resultDto = JSON.parseObject(response, ApiResultDto.class);
        String taskId = resultDto.getData();
        if (resultDto.getOk() != 0 || taskId == null) {
            throw new RuntimeException("预处理失败！" + response);
        }

        System.out.println("预处理成功, taskid：" + taskId);
        return taskId;
    }

    /**
     * 分片上传
     * 
     * @param taskId        任务id
     * @param slice         分片的byte数组
     * @throws SignatureException 
     */
    public static void uploadSlice(String taskId, String sliceId, byte[] slice) throws SignatureException {
        Map<String, String> uploadParam = getBaseAuthParam(taskId);
        uploadParam.put("slice_id", sliceId);

        String response = HttpUtil.postMulti(LFASR_HOST + UPLOAD, uploadParam, slice);
        if (response == null) {
            throw new RuntimeException("分片上传接口请求失败！");
        }
        if (JSON.parseObject(response).getInteger("ok") == 0) {
            System.out.println("分片上传成功, sliceId: " + sliceId + ", sliceLen: " + slice.length);
            return;
        }

        System.out.println("params: " + JSON.toJSONString(uploadParam));
        throw new RuntimeException("分片上传失败！" + response + "|" + taskId);
    }

    /**
     * 文件合并
     * 
     * @param taskId        任务id
     * @throws SignatureException 
     */
    public static void merge(String taskId) throws SignatureException {
        String response = HttpUtil.post(LFASR_HOST + MERGE, getBaseAuthParam(taskId));
        if (response == null) {
            throw new RuntimeException("文件合并接口请求失败！");
        }
        if (JSON.parseObject(response).getInteger("ok") == 0) {
            System.out.println("文件合并成功, taskId: " + taskId);
            return;
        }

        throw new RuntimeException("文件合并失败！" + response);
    }

    /**
     * 获取任务进度
     * 
     * @param taskId        任务id
     * @throws SignatureException 
     */
    public static ApiResultDto getProgress(String taskId) throws SignatureException {
        String response = HttpUtil.post(LFASR_HOST + GET_PROGRESS, getBaseAuthParam(taskId));
        if (response == null) {
            throw new RuntimeException("获取任务进度接口请求失败！");
        }

        return JSON.parseObject(response, ApiResultDto.class);
    }

    /**
     * 获取转写结果
     * 
     * @param taskId
     * @return
     * @throws SignatureException 
     */
    public static String getResult(String taskId) throws SignatureException {
        String responseStr = HttpUtil.post(LFASR_HOST + GET_RESULT, getBaseAuthParam(taskId));
        if (responseStr == null) {
            throw new RuntimeException("获取结果接口请求失败！");
        }
        ApiResultDto response = JSON.parseObject(responseStr, ApiResultDto.class);
        if (response.getOk() != 0) {
            throw new RuntimeException("获取结果失败！" + responseStr);
        }

        return response.getData();
    }
    
}
