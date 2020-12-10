package cn.kkbc.tpms.tcp.util;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.kkbc.entity.TestUser;

public class BmpFileUtil {
	//位图文件保存路径
	public static final String BASE_SRC="D:\\拨盘\\bmp\\";
	
	/** * 位图的宽 */ 
//	private static int width; 
	/** * 位图的高 */ 
//	private static int height;
	/** * 位图数据数组,即一个像素的三个分量的数据数组 */
	private static int[][] red, green, blue;
	Graphics g; 
	public static void main(String args[]) {
		BmpFileUtil bmp = new BmpFileUtil(); 
		
//		 String content = "是否是 姓名11 ?";
//		 String fileSrc="C:\\Users\\liululu\\Desktop\\硬件\\拨盘\\投票器功能样机演示资料\\bmp\\11.bmp";
//		writeBmp(fileSrc,content);
		String BASE_SRC1="D:\\拨盘2\\bmp\\";
		File file =new File(BASE_SRC1);    
		//如果文件夹不存在则创建    
		if  (!file .exists()  && !file .isDirectory())
		{       
		    System.out.println("//不存在");  
		    file.mkdirs();    
		} else   
		{  
		    System.out.println("//目录存在");  
		}  
		} 
	
	public byte[] getZimo(TestUser user){
		File dir =new File(BASE_SRC);    
		//如果文件夹不存在则创建    
		if  (!dir.exists()&& !dir.isDirectory())
		{
		    dir.mkdirs();
		}
		String fileSrc=BASE_SRC+user.getHUMAN_id()+".bmp";
		File file=new File(fileSrc);
		if (!file.exists()) {
			writeBmp(file.getPath(), "是否是 "+user.getUser_name()+"?");
		}
		
		return getZimo(fileSrc);
	}
	
	/**
	 * 获取位图图片对应的字模数据
	 * @param fileSrc 位图图片路径
	 */
	public byte[] getZimo(String fileSrc) {
		byte[] zimo=null;
		try { 
			// 通过bmp文件地址创建文件输入流对象 
			File file=new File(fileSrc);
			FileInputStream fin = new FileInputStream(fileSrc); 
			// 根据文件输入流对象创建原始数据输入对象 
			// 这里既可以用原始数据输入流来读取数据，也可以用缓冲输入流来读取，后者速度相比较快点。
			// java.io.DataInputStream bis = new java.io.DataInputStream(fin); 
			java.io.BufferedInputStream bis = new java.io.BufferedInputStream( fin);
			// 建立两个字节数组来得到文件头和信息头的数据
			byte[] array1 = new byte[14]; bis.read(array1, 0, 14);
			byte[] array2 = new byte[40]; bis.read(array2, 0, 40); 
			
			
			// 翻译bmp文件的数据，即将字节数据转化为int数据 // 通过翻译得到位图数据的宽和高 
			int width = ChangeInt(array2, 7);
			int height = ChangeInt(array2, 11); 
			
			//跳过调色板信息，读取位图数据
			bis.skip(file.length()-14-40-width*height/8); //bmp文件头:14 byte;位图信息头:40 byte;调色板;位图信息
			
			byte[] dataAll = new byte[width*height/8]; bis.read(dataAll, 0, width*height/8);
//			System.out.println("-------"+HexStringUtils.toHexString(dataAll));
			
			//将位图数据转化为二维数组
			byte[][] data = new byte[height][width/8];
			for (int i = 0; i < data.length; i++) {
				for (int j = 0; j < width/8; j++){
					data[i][j]=dataAll[i*data.length+j];
				}
//				System.out.println(HexStringUtils.toHexString(data[i]));
			}
			
			//将二维数组的位图数据转化为点阵数据(0 1)
			byte[][] dataBit = new byte[height][width];
			for (int i = 0; i < dataBit.length; i++) {
				
				for (int j = 0; j < data[i].length; j++){
					byte[] bit=getBooleanArray(data[i][j]);
					
					System.arraycopy(bit, 0, dataBit[i], j*8, 8);
					
				}
//				System.out.println(Arrays.toString(dataBit[i]));
			}
			
			//位图数据中height的值是正数的话
			//那么数据就是按从下到上，从左到右的顺序来保存。这个称之为倒向位图
			//反之就是按从上到下，从左到右的顺序来保存。这个则称之为正向位图
			
			zimo = new byte[width*height/8];
			//获取字模
			int index=0;
			for (int i = data.length-1; i > 0; i=i-8) {//数据是按从下到上，从左到右的顺序来保存
//				System.out.println(i);
				for (int j = 0; j < width; j++) {// 从第一列开始向下取8个点作为一个字节，然后从第二列开始向下取8个点作为第二个字节...依此类推。
					StringBuffer oneZimo=new StringBuffer();
					for (int p = i; p >i-8; p--) {
//						oneZimo.append(dataBit[p][j]==0?"1":"0");//白底黑字
						oneZimo.append(dataBit[p][j]==0?"0":"1");//黑低白字
					}
					zimo[index*width+j]=(byte) Integer.parseInt(oneZimo.toString(), 2);
					
				}
				index++;
			}
			
			System.out.println(HexStringUtils.toHexString(zimo));
			
			// 调用可以将整个位图数据读取成byte数组的方法
//			getInf(bis); 
//			System.err.println("---------red-----------"+red.length);
//			for (int[] r : red) {
//				System.out.println(Arrays.toString(r)+"----"+r.length);
//			}
//			System.err.println("---------green-----------"+green.length);
//			for (int[] g : green) {
//				System.out.println(Arrays.toString(g)+"----"+g.length);
//					}
//			System.err.println("---------blue-----------"+blue.length);
//			for (int[] b : blue) {
//				System.out.println(Arrays.toString(b)+"----"+b.length);
//			}
			
			
			
			
			fin.close();
			bis.close(); 
			// 创建BMP对象来显示图画 showUI(); 
			} catch (Exception e) {
				e.printStackTrace(); 
			} 
		
		return zimo;
		} 
	private void getInf(BufferedInputStream bis) {
		int width = 128;
		int height = 16;
		red=new int[height][width];
		green=new int[height][width];
		blue=new int[height][width];
		
		//通过计算得到每行需要填充的字符数
		//为什么要填充？这是因为windows系统在扫描数据的时候，每行都是按照4个字节的倍数来读取的
		//因为图片是由每个像素点组成。而每个像素点都是由三个颜色分量来构成的，而每个分量占据1个字节
		//因此在内存存储中实际图片数据每行的长度是width*3
		int skip_width=0;
		int m=width*3%4;
		if (m!=0) {
			skip_width=4-m;
		}
		
		//通过遍历给数组填值
		//这里需要注意，因为根据bmp的保存格式
		//位图数据中height的值是正数的话
		//那么数据就是按从下到上，从左到右的顺序来保存。这个称之为倒向位图
		//反之就是按从上到下，从左到右的顺序来保存。这个则称之为正向位图
		for (int i = height-1; i >= 0; i--) {
			for (int j = 0; j < width; j++) {
				try {
					//这里遍历的时候，一定要注意本来像素是有RGB来表示
					//但是在存储的时候由于windows是小段存储，所以内存中是BGR顺序
					blue[i][j]=bis.read();
					green[i][j]=bis.read();
					red[i][j]=bis.read();
					
					//这里一定要知道，其实系统在给位图数据中填充0的时候，都是加在每行的最后
					//但是我们在使用dis.skipBytes()这个方法的时候，却不一定要在最后一列
					//系统在填充数据的时候，在数据上加了标记
					//所以dis.skipBytes()这个方法只要调用了，那么系统就会自动不读取填充数据
					if (j==0) {
						bis.skip(skip_width);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	/**
	 *  实现可将四个字节翻译int数据的方法 * 
	 *  @param array2 * 存储字节的字节数组
	 *  @param start * 起始字节
	 *   @return 返回翻译后的int数据 
	 */ 
	public int ChangeInt(byte[] array2, int start) {
		int i=(int)((array2[start]&0xFF)<<24)|((array2[start-1]&0xFF)<<16)|((array2[start-2]&0xFF)<<8)|(array2[start-3]&0xFF);
		return i;
	}
	
	/** 
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit 
     */  
    public static byte[] getBooleanArray(byte b) {  
        byte[] array = new byte[8];  
        for (int i = 7; i >= 0; i--) {  
            array[i] = (byte)(b & 1);  
            b = (byte) (b >> 1);  
        }  
        return array;  
    }  
    
    /**
     * 生成位图图片
     * @param fileSrc 位图图片路径
     * @param content 位图图片的文字内容
     */
    public static void writeBmp(String fileSrc,String content){

		// String content = "是否是 张三 ??";
		// String fileSrc="C:\\Users\\liululu\\Desktop\\硬件\\拨盘\\投票器功能样机演示资料\\11.bmp";
		int width = 128, height = 16;
		File file = new File(fileSrc); // 设置图片保存位置

		Font font = new Font("Serif", Font.BOLD, 10);
		// 创建一个画布
		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_BINARY);
		// 获取画布的画笔
		Graphics2D g2 = bi.createGraphics();
		g2.drawString(content, 20, 10); // 画出文字

		try {
			// 将生成的图片保存为jpg格式的文件。ImageIO支持jpg、png、gif等格式
			ImageIO.write(bi, "bmp", file);
		} catch (IOException e) {
			System.out.println("生成图片出错........");
			e.printStackTrace();
		}

    }
    
    
}
