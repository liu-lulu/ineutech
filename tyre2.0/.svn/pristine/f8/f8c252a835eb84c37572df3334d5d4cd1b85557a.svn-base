package test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test5 {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		float f1=5227.2f;
		float f2=5226.2f;
		if(Math.abs(f1-f2)<1.1){
			System.out.println("相等");
		}else{
			System.out.println("不相等");
		}
		System.out.println(Math.abs(f1-f2));
		Date d=new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("时间2: " + formatter.format(d)); 
		Thread.sleep(1000);
		d.setTime(System.currentTimeMillis());
		System.out.println("时间2: " + formatter.format(d)); 
		
		Integer t=256898111,t2=256898111;
		if(t.equals(t2)){
			System.out.println("ddddddddddddddd");
		}
		
	}

}
