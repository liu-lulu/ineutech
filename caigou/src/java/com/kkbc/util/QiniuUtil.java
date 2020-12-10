package com.kkbc.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;

public class QiniuUtil {
	private static Logger logger = LoggerFactory.getLogger(QiniuUtil.class);

    private static String bucketName = "caigou";//发布时的存储空间
//    private static String bucketName = "test";//本地测试的存储空间
    private static String ACCESS_KEY = "IXWGMxA3WCkLl6an6q4rzAMKu8neJiS-zr_h1OAg";
    private static String SECRET_KEY = "t9gOfbTtx51V4DSfFTxZMLlm1DKe1Cb_w8onHjrv";

    public static String upload(byte[] file, String key) {
        UploadManager uploadManager = new UploadManager();// 创建上传对象
        try {
            Response res = uploadManager.put(file, key, getUptoken());
            return res.bodyString();
        } catch (QiniuException e) {
        	e.printStackTrace();
            Response r = e.response;
            logger.error("上传七牛云异常:", r.toString());
            try {
            	logger.error("上传七牛云异常:", r.bodyString());
            } catch (QiniuException e1) {
            }
        }
        return null;
    }
    
    //路径下的文件全部保存到七牛
	public static String uploadFiles(String dirPath) {
		UploadManager uploadManager = new UploadManager();// 创建上传对象

		File dir = new File(dirPath);
		for (File f : dir.listFiles()) {
			FileInfo fileInfo = getFile(f.getName());
			if (fileInfo == null) {
				try {
					Response res = uploadManager.put(f, f.getName(), getUptoken());
//					return res.bodyString();
				} catch (QiniuException e) {
					e.printStackTrace();
					Response r = e.response;
					logger.error("上传七牛云异常:", r.toString());
					try {
						logger.error("上传七牛云异常:", r.bodyString());
					} catch (QiniuException e1) {
					}
				}
			}
		}

		return null;
	}
    
    public static String upload(File file, String key) {
      UploadManager uploadManager = new UploadManager();// 创建上传对象
      try {
          Response res = uploadManager.put(file, key, getUptoken());
          return res.bodyString();
      } catch (QiniuException e) {
      	e.printStackTrace();
          Response r = e.response;
          logger.error("上传七牛云异常:", r.toString());
          try {
          	logger.error("上传七牛云异常:", r.bodyString());
          } catch (QiniuException e1) {
          }
      }
      return null;
  }
    
    //获取文件信息
	public static FileInfo getFile(String key) {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth);
		FileInfo info = null;
		try {
			// 调用stat()方法获取文件的信息
			info = bucketManager.stat(bucketName, key);
			System.out.println(info.hash);
			System.out.println(info.key);
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());
		}
		return info;
	}
	
	//删除文件信息
	public static void delFile(String key) {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth);
		try {
			bucketManager.delete(bucketName, key);
		} catch (QiniuException e) {
			// 捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());
		}
	}
	
	 //抓取网络资源到七牛
	public static void fetchFileToQiniu(String originalUrl,String key) {
		Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth);
		try {
			 DefaultPutRet putret = bucketManager.fetch(originalUrl, bucketName, key);
			 System.out.println(putret.key);
		} catch (QiniuException e) {
			e.printStackTrace();
			// 捕获异常信息
			Response r = e.response;
			System.out.println(r.toString());
		}
	}
    
    private static String getUptoken() {
        Auth auth = Auth.create(ACCESS_KEY,SECRET_KEY);
        String uptoken = auth.uploadToken(bucketName);
        return uptoken;
    }
    
    public static void main(String[] args) {
    	
    	File dir=new File("D:\\img\\caigou_img");
    	 File[] filelist=dir.listFiles();
    	 for (File f : filelist) {
			FileInfo fileInfo=getFile(f.getName());
			if (fileInfo==null) {
				upload(f, f.getName());
			}
		}
	}

}
