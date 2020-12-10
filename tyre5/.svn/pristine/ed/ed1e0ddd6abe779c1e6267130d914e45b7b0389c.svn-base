package com.psylife.util;

/**
 * 车型功能类
 * @author xu
 *
 */
public class CopyOfTrucksStyleUtil {
	
	
	/**
	 * 车型类型,4*2+3 
	 * 共18个轮胎,
	 * 位置为A1-A2、B1-B4、C1-C12
	 * 对应的发射器号为1-18
	 */
	public static final String STYLE_4_2_3="4*2+3";
	
	/**
	 * 车型类型,4*2+2 
	 * 共14个轮胎,
	 * 位置为A1-A2、B1-B4、C1-C8
	 * 对应的发射器号为1-14
	 */
	public static final String STYLE_4_2_2="4*2+2";
	
	/**
	 * 车型类型,8*4
	 * 共12个轮胎,
	 * 位置为A1-A4、B1-B8
	 * 对应的发射器号为1-12
	 */
	public static final String STYLE_8_4="8*4";
	
	/**
	 * 车型类型,6*2
	 * 共8个轮胎,
	 * 位置为A1-A4、B1-B4
	 * 对应的发射器号为1-8
	 */
	public static final String STYLE_6_2="6*2";
	
	
	/**
	 * 车型类型,4*2 
	 * 共6个轮胎,
	 * 位置为A1-A2、B1-B4
	 * 对应的发射器号为1-6
	 */
	public static final String STYLE_4_2="4*2";
	
	/**
	 * 对应发射器数组,车型类型,4*2 
	 * 共6个轮胎,
	 * 位置为A1-A2、B1-B4
	 * 对应的发射器号为1-6
	 */
	public static final int[] STYLE_4_2_ARR={2,1,6,5,4,3};
	
	/**
	 * 车型类型,6*4 
	 * 共10个轮胎,
	 * 位置为A1-A2、B1-B8
	 * 对应的发射器号为1-10
	 */
	public static final String STYLE_6_4="6*4";
	
	/**
	 * 车型类型,6*2+3 
	 * 共20个轮胎,
	 * 位置为A1-A4、B1-B4、C1-C12
	 * 对应的发射器号为1-20
	 */
	public static final String STYLE_6_2_3="6*2+3";
	
	/**
	 * 车型类型,6*4+3 
	 * 共22个轮胎,
	 * 位置为A1-A2、B1-B8、C1-C12
	 * 对应的发射器号为1-22
	 */
	public static final String STYLE_6_4_3="6*4+3";
	
	
	
	/**
	 * 根据位置,获取发射器号
	 * @param trucksStyle 车型类型
	 * @param where  位置如A1,A2...B1,B2...C1,C2...
	 * @return
	 */
	public static int TyreNOByWhere(String trucksStyle,String where){
		if(trucksStyle==null||where==null||where.length()<2){
			return -1;
		}
		char t=where.charAt(0);
		int n=Integer.valueOf(where.substring(1));
		if(STYLE_4_2_3.equals(trucksStyle)){//车型类型,4*2+3 
			if(t=='A'){//A1--A2
				if(n<=2&&n>0){
					return n;
				}
			}else if(t=='B'){//B1--B4
				if(n<=4&&n>0){
					return n+2;
				}
			}else if(t=='C'){//C1--C12
				if(n<=12&&n>0){
					return n+6;
				}
			}
		}else if(STYLE_4_2_2.equals(trucksStyle)){//车型类型,4*2+2 
			if(t=='A'){//A1--A2
				if(n<=2&&n>0){
					return n;
				}
			}else if(t=='B'){//B1--B4
				if(n<=4&&n>0){
					return n+2;
				}
			}else if(t=='C'){//C1--C8
				if(n<=8&&n>0){
					return n+6;
				}
			}
		}else if(STYLE_8_4.equals(trucksStyle)){//车型类型,8*4
			if(t=='A'){//A1--A4
				if(n<=4&&n>0){
					return n;
				}
			}else if(t=='B'){//B1--B8
				if(n<=8&&n>0){
					return n+4;
				}
			}
		}else if(STYLE_6_2.equals(trucksStyle)){//车型类型,6*2
			if(t=='A'){//A1--A4
				if(n<=4&&n>0){
					return n;
				}
			}else if(t=='B'){//B1--B4
				if(n<=4&&n>0){
					return n+4;
				}
			}
		}else if(STYLE_4_2.equals(trucksStyle)){//车型类型,4*2 
			if(t=='A'){//A1--A2
				if(n<=2&&n>0){
					return n;
				}
			}else if(t=='B'){//B1--B4
				if(n<=4&&n>0){
					return n+2;
				}
			}
		}else if(STYLE_6_4.equals(trucksStyle)){//车型类型,6*4 
			if(t=='A'){//A1--A2
				if(n<=2&&n>0){
					return n;
				}
			}else if(t=='B'){//B1--B8
				if(n<=8&&n>0){
					return n+2;
				}
			}
		}else if(STYLE_6_2_3.equals(trucksStyle)){//车型类型,6*2+3 
			if(t=='A'){//A1--A4
				if(n<=4&&n>0){
					return n;
				}
			}else if(t=='B'){//B1--B4
				if(n<=4&&n>0){
					return n+4;
				}
			}else if(t=='C'){//C1--C12
				if(n<=12&&n>0){
					return n+8;
				}
			}
		}else if(STYLE_6_4_3.equals(trucksStyle)){//车型类型,6*4+3 
			if(t=='A'){//A1--A2
				if(n<=2&&n>0){
					return n;
				}
			}else if(t=='B'){//B1--B8
				if(n<=8&&n>0){
					return n+4;
				}
			}else if(t=='C'){//C1--C12
				if(n<=12&&n>0){
					return n+8;
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * 根据发射器号,获取位置
	 * @param trucksStyle 车型类型
	 * @param no  发射器号
	 * @return
	 */
	public static String TyreWhereByNo(String trucksStyle,int no){
		if(trucksStyle==null||no<0){
			return null;
		}
		if(STYLE_4_2_3.equals(trucksStyle)){//车型类型,4*2+3,胎个数等于18
			if(no<=18){
				if(no<=2){
					return "A"+no;
				}else if(no<=6){
					return "B"+(no-2);
				}else{
					return "C"+(no-6);
				}
			}
		}else if(STYLE_4_2_2.equals(trucksStyle)){//车型类型,4*2+2,胎个数等于14
			if(no<=14){
				if(no<=2){
					return "A"+no;
				}else if(no<=6){
					return "B"+(no-2);
				}else{
					return "C"+(no-6);
				}
			}
		}else if(STYLE_8_4.equals(trucksStyle)){//车型类型,8*4,胎个数等于12
			if(no<=12){
				if(no<=4){
					return "A"+no;
				}else{
					return "B"+(no-4);
				}
			}
		}else if(STYLE_6_2.equals(trucksStyle)){//车型类型,6*2,胎个数等于8
			if(no<=8){
				if(no<=4){
					return "A"+no;
				}else{
					return "B"+(no-4);
				}
			}
		}else if(STYLE_4_2.equals(trucksStyle)){//车型类型,4*2,胎个数等于6
			if(no<=6){
				if(no<=2){
					return "A"+no;
				}else{
					return "B"+(no-2);
				}
			}
		}else if(STYLE_6_4.equals(trucksStyle)){//车型类型,6*4,胎个数等于10
			if(no<=10){
				if(no<=2){
					return "A"+no;
				}else{
					return "B"+(no-2);
				}
			}
		}else if(STYLE_6_2_3.equals(trucksStyle)){//车型类型,6*2+3,胎个数等于20
			if(no<=20){
				if(no<=4){
					return "A"+no;
				}else if(no<=8){
					return "B"+(no-4);
				}else{
					return "C"+(no-8);
				}
			}
		}else if(STYLE_6_4_3.equals(trucksStyle)){//车型类型,6*4+3,胎个数等于22
			if(no<=22){
				if(no<=2){
					return "A"+no;
				}else if(no<=10){
					return "B"+(no-2);
				}else{
					return "C"+(no-10);
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据车型类型,获取最大发射器号
	 * @param trucksStyle 车型类型
	 * @param no  发射器号
	 * @return
	 */
	public static int TyreMaxNo(String trucksStyle){
		if(STYLE_4_2_3.equals(trucksStyle)){//车型类型,4*2+3,胎个数等于18
			return 18;
		}else if(STYLE_4_2_2.equals(trucksStyle)){//车型类型,4*2+2,胎个数等于14
			return 14;
		}else if(STYLE_8_4.equals(trucksStyle)){//车型类型,8*4,胎个数等于12
			return 12;
		}else if(STYLE_6_2.equals(trucksStyle)){//车型类型,6*2,胎个数等于8
			return 8;
		}else if(STYLE_4_2.equals(trucksStyle)){//车型类型,4*2,胎个数等于6
			return 6;
		}else if(STYLE_6_4.equals(trucksStyle)){//车型类型,6*4,胎个数等于10
			return 10;
		}else if(STYLE_6_2_3.equals(trucksStyle)){//车型类型,6*2+3,胎个数等于20
			return 20;
		}else if(STYLE_6_4_3.equals(trucksStyle)){//车型类型,6*2+3,胎个数等于22
			return 22;
		}
		return -1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
