package test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.psylife.entity.TyreBase;

public class Test4 {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws UnsupportedEncodingException {
		List<TyreBase> list=new ArrayList<TyreBase>();
		TyreBase tyreBase;
		for(int i=1;i<6;i++){
			tyreBase=new TyreBase();
			tyreBase.setTyre_brand("275/70R22.5");
			tyreBase.setTyre_flag(0);
			tyreBase.setTyre_id("ids"+i);
			tyreBase.setTyre_brand("固特异1");
			tyreBase.setTyre_type1("XMAD");
			tyreBase.setTyre_type2("XMAD");
			tyreBase.setTyre_type3("条纹");
			tyreBase.setTyre_type4(1);
			tyreBase.setTyre_type5(1);
			tyreBase.setTyre_type6(1);
			tyreBase.setTyre_type7(1);
			tyreBase.setTyre_type8("dsfsdf");
			list.add(tyreBase);
		}
		String tt=URLEncoder.encode(JSONArray.fromObject(list).toString(), "utf-8");
		System.out.println("URLEncoder="+tt);
		tt=URLDecoder.decode(tt, "utf-8");
		System.out.println("URLDecoder="+tt);
		JSONArray jsonArray=JSONArray.fromObject(tt);
		List<TyreBase> list2=(List<TyreBase>)JSONArray.toCollection(jsonArray, TyreBase.class);
		System.out.println(list2);
	}

}
