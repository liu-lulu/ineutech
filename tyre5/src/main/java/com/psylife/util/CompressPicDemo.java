package com.psylife.util;

import java.io.File;
import java.awt.Image;   
import java.awt.image.BufferedImage;   
import java.io.FileOutputStream;   
import java.io.IOException;   

import javax.imageio.ImageIO;   

import com.sun.image.codec.jpeg.JPEGCodec;   
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class CompressPicDemo {
	 private File file = null; // 文件对象    
     private String inputDir; // 输入图路径   
     private String outputDir; // 输出图路径   
     private String inputFileName; // 输入图文件名   
     private String outputFileName; // 输出图文件名   
     private int outputWidth = 100; // 默认输出图片宽   
     private int outputHeight = 100; // 默认输出图片高   
     private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)   
     public CompressPicDemo() { // 初始化变量   
         inputDir = "";    
         outputDir = "";    
         inputFileName = "";    
         outputFileName = "";    
         outputWidth = 100;    
         outputHeight = 100;    
     }    
     public void setInputDir(String inputDir) {    
         this.inputDir = inputDir;    
     }    
     public void setOutputDir(String outputDir) {    
         this.outputDir = outputDir;    
     }    
     public void setInputFileName(String inputFileName) {    
         this.inputFileName = inputFileName;   
     }    
     public void setOutputFileName(String outputFileName) {    
         this.outputFileName = outputFileName;    
     }    
     public void setOutputWidth(int outputWidth) {   
         this.outputWidth = outputWidth;    
     }    
     public void setOutputHeight(int outputHeight) {    
         this.outputHeight = outputHeight;    
     }    
     public void setWidthAndHeight(int width, int height) {    
         this.outputWidth = width;   
         this.outputHeight = height;    
     }    
        
     /*   
      * 获得图片大小   
      * 传入参数 String path ：图片路径   
      */    
     public long getPicSize(String path) {    
         file = new File(path);    
         return file.length();    
     }   
        
     // 图片处理    
     public String compressPic() {    
         try {    
             //获得源文件    
             file = new File(inputDir + inputFileName);    
             if (!file.exists()) {    
                 return "";    
             }  
             Image img = ImageIO.read(file);    //这里是从本地读图片文件，如果是执行上传图片的话，  Formfile formfile=获得表单提交的Formfile ,然后 ImageIO.read 方法里参数放 formfile.getInputStream()
             // 判断图片格式是否正确    
             if (img.getWidth(null) == -1) {   
                 System.out.println(" can't read,retry!" + "<BR>");    
                 return "no";    
             } else {    
                 int newWidth; int newHeight;    
                 // 判断是否是等比缩放    
                 if (this.proportion == true) {    
                     // 为等比缩放计算输出的图片宽度及高度    
                     double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;    
                     double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;    
                     // 根据缩放比率大的进行缩放控制    
                     double rate = rate1 > rate2 ? rate1 : rate2;    
                     newWidth = (int) (((double) img.getWidth(null)) / rate);    
                     newHeight = (int) (((double) img.getHeight(null)) / rate);    
                 } else {    
                     newWidth = outputWidth; // 输出的图片宽度    
                     newHeight = outputHeight; // 输出的图片高度    
                 }    
                BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);    
                   
                /*  
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的  
                 * 优先级比速度高 生成的图片质量比较好 但速度慢  
                 */    
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);   
                FileOutputStream out = new FileOutputStream(outputDir + outputFileName);   
                // JPEGImageEncoder可适用于其他图片类型的转换    
//                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
//                encoder.encode(tag);    
                ImageIO.write(tag, "jpg", out);
                out.close();    
             }    
         } catch (IOException ex) {    
             ex.printStackTrace();    
         }    
         return "ok";    
    }    
     
     // 图片处理    
     public String compressPicToPng() {    
         try {    
             //获得源文件    
             file = new File(inputDir + inputFileName);    
             if (!file.exists()) {    
                 return "";    
             }  
             Image img = ImageIO.read(file);    //这里是从本地读图片文件，如果是执行上传图片的话，  Formfile formfile=获得表单提交的Formfile ,然后 ImageIO.read 方法里参数放 formfile.getInputStream()
             // 判断图片格式是否正确    
             if (img.getWidth(null) == -1) {   
                 System.out.println(" can't read,retry!" + "<BR>");    
                 return "no";    
             } else {    
                 int newWidth; int newHeight;    
//                 // 判断是否是等比缩放    
//                 if (this.proportion == true) {    
//                     // 为等比缩放计算输出的图片宽度及高度    
//                     double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;    
//                     double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;    
//                     // 根据缩放比率大的进行缩放控制    
//                     double rate = rate1 > rate2 ? rate1 : rate2;    
//                     newWidth = (int) (((double) img.getWidth(null)) / rate);    
//                     newHeight = (int) (((double) img.getHeight(null)) / rate);    
//                 } else {    
//                     newWidth = outputWidth; // 输出的图片宽度    
//                     newHeight = outputHeight; // 输出的图片高度    
//                 }    
                 
                 newHeight = img.getHeight(null)*500/img.getWidth(null);
                 
                 
                 
                BufferedImage tag = new BufferedImage(500, (int) newHeight, BufferedImage.TYPE_INT_RGB);    
                   
                /*  
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的  
                 * 优先级比速度高 生成的图片质量比较好 但速度慢  
                 */    
                tag.getGraphics().drawImage(img.getScaledInstance(500, newHeight, Image.SCALE_SMOOTH), 0, 0, null);   
                FileOutputStream out = new FileOutputStream(outputDir + outputFileName);   
                // JPEGImageEncoder可适用于其他图片类型的转换    
//                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
//                encoder.encode(tag);  
                ImageIO.write(tag, "jpg", out);
                out.close();    
             }    
         } catch (IOException ex) {    
             ex.printStackTrace();    
         }    
         return "ok";    
    }  
     
  // 原始长宽
     public String compressPicToSourceWH() {    
         try {    
             //获得源文件    
             file = new File(inputDir + inputFileName);    
             if (!file.exists()) {    
                 return "";    
             }  
             Image img = ImageIO.read(file);    //这里是从本地读图片文件，如果是执行上传图片的话，  Formfile formfile=获得表单提交的Formfile ,然后 ImageIO.read 方法里参数放 formfile.getInputStream()
             // 判断图片格式是否正确    
             if (img.getWidth(null) == -1) {   
                 System.out.println(" can't read,retry!" + "<BR>");    
                 return "no";    
             } else {                     
                BufferedImage tag = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);    
                tag.getGraphics().drawImage(img.getScaledInstance(img.getWidth(null), img.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);   
                FileOutputStream out = new FileOutputStream(outputDir + outputFileName);   
                // JPEGImageEncoder可适用于其他图片类型的转换    
//                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);    
//                encoder.encode(tag);   
                ImageIO.write(tag, "jpg", out);
                out.close();    
             }    
         } catch (IOException ex) {    
             ex.printStackTrace();    
         }    
         return "ok";    
    }
     
     
     
    public String compressPic (String inputDir, String outputDir, String inputFileName, String outputFileName) {    
        // 输入图路径    
        this.inputDir = inputDir;    
        // 输出图路径    
        this.outputDir = outputDir;    
        // 输入图文件名    
        this.inputFileName = inputFileName;    
        // 输出图文件名   
        this.outputFileName = outputFileName;    
        return compressPic();    
    }    
    public String compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName, int width, int height, boolean gp) {    
        // 输入图路径    
        this.inputDir = inputDir;    
        // 输出图路径    
        this.outputDir = outputDir;    
        // 输入图文件名    
        this.inputFileName = inputFileName;    
        // 输出图文件名    
        this.outputFileName = outputFileName;    
        // 设置图片长宽   
        setWidthAndHeight(width, height);    
        // 是否是等比缩放 标记    
        this.proportion = gp;    
        return compressPic();    
    }    
    
    public String compressPng(String inputDir, String outputDir, String inputFileName, String outputFileName, int width, int height, boolean gp) {    
        // 输入图路径    
        this.inputDir = inputDir;    
        // 输出图路径    
        this.outputDir = outputDir;    
        // 输入图文件名    
        this.inputFileName = inputFileName;    
        // 输出图文件名    
        this.outputFileName = outputFileName;    
        // 设置图片长宽   
        setWidthAndHeight(width, height);    
        // 是否是等比缩放 标记    
        this.proportion = gp;    
        return this.compressPicToPng();
    } 
    
    /**
     * 只是压缩存储大小，不变长宽
     * @return
     */
    public String compressPicToSourceWH(String inputDir, String outputDir, String inputFileName, String outputFileName, int width, int height, boolean gp) {    
        // 输入图路径    
        this.inputDir = inputDir;    
        // 输出图路径    
        this.outputDir = outputDir;    
        // 输入图文件名    
        this.inputFileName = inputFileName;    
        // 输出图文件名    
        this.outputFileName = outputFileName;    
        // 设置图片长宽   
        setWidthAndHeight(width, height);    
        // 是否是等比缩放 标记    
        this.proportion = gp;    
        return this.compressPicToSourceWH();
    } 
    
    
    
//    public static void main(String[] args)
// {
//     CompressPicDemo mypic = new CompressPicDemo();    
//        System.out.println("输入的图片大小：" + mypic.getPicSize("F:\\chris.png")/1024 + "KB");    
//        int count = 0; // 记录全部图片压缩所用时间   
//        mypic.compressPic("F:\\", "F:\\test\\", "chris.png", "chris"+0+".png", 200, 200, true); 
//        System.out.println("输出的图片大小：" + mypic.getPicSize("F:\\test\\png"+0+".jpg")/1024 + "KB");    
////        for (int i = 0; i < 3; i++) {    
////            int start = (int) System.currentTimeMillis();   // 开始时间    
////            mypic.compressPic("F:\\", "F:\\test\\", "chris.jpg", "chris"+i+".jpg", 200, 200, true);    
////            int end = (int) System.currentTimeMillis(); // 结束时间    
////            int re = end-start; // 但图片生成处理时间    
////            count += re; System.out.println("第" + (i+1) + "张图片压缩处理使用了: " + re + "毫秒");    
////        }   
//        System.out.println("总共用了：" + count + "毫秒");    
// }
    
}