package test;

import com.psylife.vo.dtu.DeviceFloatByDtuVO;
import com.psylife.vo.dtu.DeviceOffonByDtuVO;
import com.psylife.vo.dtu.DeviceStringByDtuVO;
import com.psylife.vo.dtu.TyreFloatWenduYaliVO;
import com.psylife.vo.dtu.TyreFloatYaliSetVO;
import com.psylife.vo.dtu.TyreOffonVO;
import com.psylife.vo.dtu.TyreStringVO;

public class Test6 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println("开始:开关量相等测试");
//		offonEquals();//开关量相等测试
//		System.out.println("结束:开关量相等测试");
		
//		System.out.println("开始:float包数据");
//		floatEquals();//float包数据
//		System.out.println("结束:float包数据");
		
		System.out.println("开始:字符串包数据");
		stringEquals();//float包数据
		System.out.println("结束:字符串包数据");
	}
	
	private static void stringEquals(){
		TyreStringVO tyreStringVO=new TyreStringVO();
		tyreStringVO.setFasheqiid("-99.99");
		TyreStringVO tyreStringVO2=new TyreStringVO();
		tyreStringVO2.setFasheqiid("-99.99");
		if(tyreStringVO.equalsValue(tyreStringVO2)){
			System.out.println("相等tyreStringVO");
		}else{
			System.out.println("不相等tyreStringVO");
		}
		DeviceStringByDtuVO deviceStringByDtuVO=new DeviceStringByDtuVO();
		deviceStringByDtuVO.setDtu_tpms_status(1);
		deviceStringByDtuVO.setDtu_trucks_id("-99.99");
		deviceStringByDtuVO.setLatitude(22.58427600);
		deviceStringByDtuVO.setLatitude_type(0);
		deviceStringByDtuVO.setLongitude(113.96245500);
		deviceStringByDtuVO.setLongitude_type(0);
		deviceStringByDtuVO.getFasheqi_ids_value().add(tyreStringVO);
		
		DeviceStringByDtuVO deviceStringByDtuVO2=new DeviceStringByDtuVO();
		deviceStringByDtuVO2.setDtu_tpms_status(1);
		deviceStringByDtuVO2.setDtu_trucks_id("-99.99");
		deviceStringByDtuVO2.setLatitude(22.58427600);
		deviceStringByDtuVO2.setLatitude_type(0);
		deviceStringByDtuVO2.setLongitude(113.96245500);
		deviceStringByDtuVO2.setLongitude_type(0);
		deviceStringByDtuVO2.getFasheqi_ids_value().add(tyreStringVO2);
		if(deviceStringByDtuVO.equalsValue(deviceStringByDtuVO2)){
			System.out.println("ds相等");
		}else{
			System.out.println("ds不相等");
		}
	}
	
	private static void floatEquals(){
		TyreFloatYaliSetVO floatYaliSetVO=new TyreFloatYaliSetVO();
		floatYaliSetVO.setH(2.282f);
		floatYaliSetVO.setL(1.192121f);
		TyreFloatYaliSetVO floatYaliSetVO2=new TyreFloatYaliSetVO();
		floatYaliSetVO2.setH(2.2121211f);
		floatYaliSetVO2.setL(1.198f);
		if(floatYaliSetVO.equalsValue(floatYaliSetVO2)){
			System.out.println("相等floatYaliSetVO");
		}else{
			System.out.println("不相等floatYaliSetVO");
		}
		
		TyreFloatWenduYaliVO floatWenduYaliVO=new TyreFloatWenduYaliVO();
		floatWenduYaliVO.setWendu(25f);
		floatWenduYaliVO.setYali(2.1f);
		
		TyreFloatWenduYaliVO floatWenduYaliVO2=new TyreFloatWenduYaliVO();
		floatWenduYaliVO2.setWendu(22f);
		floatWenduYaliVO2.setYali(2.1552f);
		if(floatWenduYaliVO.equalsValue(floatWenduYaliVO2)){
			System.out.println("相等floatWenduYaliVO");
		}else{
			System.out.println("不相等floatWenduYaliVO");
		}
		DeviceFloatByDtuVO deviceFloatByDtuVO=new DeviceFloatByDtuVO();
		deviceFloatByDtuVO.setDimian_hangxiang(0f);
		deviceFloatByDtuVO.setDimian_sulu(0f);
		deviceFloatByDtuVO.setDtu_tpms_status(1);
		deviceFloatByDtuVO.setGao_wen_bao_jing_set(1f);
		deviceFloatByDtuVO.setTpms_pinlu(2);
		deviceFloatByDtuVO.getWendu_yali_value().add(floatWenduYaliVO);
		deviceFloatByDtuVO.getYali_set().add(floatYaliSetVO);
		
		DeviceFloatByDtuVO deviceFloatByDtuVO2=new DeviceFloatByDtuVO();
		deviceFloatByDtuVO2.setDimian_hangxiang(0f);
		deviceFloatByDtuVO2.setDimian_sulu(0f);
		deviceFloatByDtuVO2.setDtu_tpms_status(1);
		deviceFloatByDtuVO2.setGao_wen_bao_jing_set(1f);
		deviceFloatByDtuVO2.setTpms_pinlu(2);
		deviceFloatByDtuVO2.getWendu_yali_value().add(floatWenduYaliVO2);
		deviceFloatByDtuVO2.getYali_set().add(floatYaliSetVO2);
		
		if(deviceFloatByDtuVO.equalsValue(deviceFloatByDtuVO2)){
			System.out.println("df相等");
		}else{
			System.out.println("df不相等");
		}
	}
	
	
	
	
	private static void offonEquals(){
		TyreOffonVO tyreOffonVO=new TyreOffonVO();
		tyreOffonVO.setDianchi(1);
		tyreOffonVO.setLouqi(1);
		tyreOffonVO.setGaoya(1);
		tyreOffonVO.setDiya(1);
		tyreOffonVO.setGaowen(1);
		tyreOffonVO.setFasheqidianchi(0);
		tyreOffonVO.setFasheqizhongduan(1);
		
		TyreOffonVO tyreOffonVO2=new TyreOffonVO();
		tyreOffonVO2.setDianchi(1);
		tyreOffonVO2.setLouqi(1);
		tyreOffonVO2.setGaoya(1);
		tyreOffonVO2.setDiya(1);
		tyreOffonVO2.setGaowen(1);
		tyreOffonVO2.setFasheqidianchi(0);
		tyreOffonVO2.setFasheqizhongduan(1);
		
		if(tyreOffonVO.equalsValue(tyreOffonVO2)){
			System.out.println("相等");
		}else{
			System.out.println("不相等");
		}
		DeviceOffonByDtuVO offonByDtuVO=new DeviceOffonByDtuVO();
		DeviceOffonByDtuVO offonByDtuVO2=new DeviceOffonByDtuVO();
		
		offonByDtuVO.setDtu_status(1);
		offonByDtuVO.setDtu_tpms_status(1);
		offonByDtuVO.setGps_status(1);
		offonByDtuVO.getOffon_value().add(tyreOffonVO);
		
		offonByDtuVO2.setDtu_status(1);
		offonByDtuVO2.setDtu_tpms_status(1);
		offonByDtuVO2.setGps_status(1);
		offonByDtuVO2.getOffon_value().add(tyreOffonVO2);
		
		if(offonByDtuVO.equalsValue(offonByDtuVO2)){
			System.out.println("d相等");
		}else{
			System.out.println("d不相等");
		}
	}

}
