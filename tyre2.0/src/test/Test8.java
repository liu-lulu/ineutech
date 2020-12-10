package test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.psylife.entity.TyrePattern2;

public class Test8 {

	public static void main(String[] args) throws UnsupportedEncodingException {

		List<TyrePattern2> list=new ArrayList<TyrePattern2>();
		TyrePattern2 tyreBase;
		for(int i=1;i<6;i++){
			tyreBase=new TyrePattern2();
			tyreBase.setDan_hao(12.0f);
			tyreBase.setTrucks_id("沪AK2233");
			tyreBase.setTyre_abnormal(0);
			tyreBase.setTyre_felloe(0);
			tyreBase.setTyre_id("");
			tyreBase.setTyre_paver("2.5");
			tyreBase.setTyre_trauma(0);
			tyreBase.setTyre_value(0);
			tyreBase.setTyrePosition(i+"");
			tyreBase.setUnit(0);
			list.add(tyreBase);
		}
		
		tyreBase=new TyrePattern2();
		tyreBase.setDan_hao(12.0f);
//		tyreBase.setTrucks_id("沪AK2233");
		tyreBase.setTyre_abnormal(0);
		tyreBase.setTyre_felloe(0);
		tyreBase.setTyre_id("LMD46");
		tyreBase.setTyre_paver("2.5");
		tyreBase.setTyre_trauma(0);
		tyreBase.setTyre_value(0);
		tyreBase.setTyrePosition(0+"");
		tyreBase.setUnit(0);
		list.add(tyreBase);
		System.out.println(JSONArray.fromObject(list).toString());
		String tt=URLEncoder.encode(JSONArray.fromObject(list).toString(), "utf-8");
		System.out.println("URLEncoder="+tt);
		tt=URLDecoder.decode(tt, "utf-8");
		System.out.println("URLDecoder="+tt);
		JSONArray jsonArray=JSONArray.fromObject(tt);
		List<TyrePattern2> list2=(List<TyrePattern2>)JSONArray.toCollection(jsonArray, TyrePattern2.class);
		System.out.println(list2);
	}

}
