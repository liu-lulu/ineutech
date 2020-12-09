package com.kkbc.util;

public class LonLatUtil {
	
	
	public static final double R = 6371.004;//公里为单位
	
	/**
	 * 根据给定的两个经纬度计算两地之间的距离，单位km
	 * @param lon1	经度1
	 * @param lat1	纬度1
	 * @param lon2	经度2
	 * @param lat2	纬度2
	 * @return	两地距离
	 */
	public static double getDistance(double lon1, double lat1, double lon2, double lat2)
	{
		double x = changeToRad(lon1);
		double y = changeToRad(lat1);
		double a = changeToRad(lon2);
		double b = changeToRad(lat2);
		double rad = Math.acos(Math.cos(y) * Math.cos(b) * Math.cos(x - a) + Math.sin(y) * Math.sin(b));
		if (rad > Math.PI)
			rad = Math.PI * 2 - rad;
		return R * rad;
	}
	
	/**
	 * 
	 * @param lon1
	 * @param lontype1 0表示东经,1表示西经
	 * @param lat1
	 * @param lattype1 0表示北纬,1表示南纬
	 * @param lon2
	 * @param lontype2
	 * @param lat2
	 * @param lattype2
	 * @return
	 */
	public static double getDistance(double lon1,int lontype1, double lat1,int lattype1,
			double lon2,int lontype2, double lat2,int lattype2){
		if(lontype1==1){//西经
			lon1 *= -1; 
		}
		if(lattype1==1){//南纬
			lat1 *= -1; 
		}
		if(lontype2==1){//西经
			lon2 *= -1; 
		}
		if(lattype2==1){//南纬
			lat2 *= -1; 
		}
		return getDistance(lon1, lat1, lon2, lat2);
	}
	
	/**
	 * 将角度转化为弧度
	 * @param angle	角度
	 * @return	弧度
	 */
	public static double changeToRad(double angle)
	{
		return angle / 180 * Math.PI;
	}
	
	public static void main(String[] args)
	{
//		System.out.println(getDistance(151, -33, 120, 30));
		//System.out.println(getDistance(113.96195600d, 0, 22.58408600d, 0, 113.96249500d, 0, 22.58455300d, 0));
		System.out.println(getDistance(121.4381100d, 0, 31.15935000d, 0, 121.4381700d, 0, 31.15939000d, 0));
	}
}


