package test;

import com.psylife.vo.TyreVO;

import net.sf.json.JSONObject;

public class Test7 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", 0);
		TyreVO t=null;
		jsonObject.put("info", JSONObject.fromObject(t));
		System.out.println(jsonObject.toString());
	}

}
