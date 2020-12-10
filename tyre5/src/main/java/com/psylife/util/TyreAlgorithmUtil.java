package com.psylife.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kkbc.vo.tuijian.TyreByTuiJianVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 轮胎算法
 * @author xu
 *
 */
public class TyreAlgorithmUtil {
	
	
	/**
	 * 算法调用
	 * @param aList
	 * @param bList
	 * @param cList
	 */
	public static JSONArray algorithm(List<TyreByTuiJianVO> aList,List<TyreByTuiJianVO> bList,List<TyreByTuiJianVO> cList){
		
		//a编号数组
		List<TyreByTuiJianVO> aListS=new ArrayList<TyreByTuiJianVO>();
		if(aList!=null){
			for(int i=0;i<aList.size();i++){
				aListS.add(aList.get(i));
			}
		}
		
		//b编号数组
		List<TyreByTuiJianVO> bListS=new ArrayList<TyreByTuiJianVO>();
		if(bList!=null){
			for(int i=0;i<bList.size();i++){
				bListS.add(bList.get(i));
			}
		}
		
		//c编号数组
		List<TyreByTuiJianVO> cListS=new ArrayList<TyreByTuiJianVO>();
		if(cList!=null){
			for(int i=0;i<cList.size();i++){
				cListS.add(cList.get(i));
			}
		}
		
		indexList(aList);//加索引
		indexList(bList);//加索引
		indexList(cList);//加索引
		
		
		sort(aList);//从小到大排序
		sort(bList);//从小到大排序
		sort(cList);//从小到大排序
		// ①
        //从C[]中选出值最大、“花纹=条纹” 的轮胎，与A[]中最小的相比
        //如果c>a，则交换。
        //以此类推。
		exchange(aList, cList);
		
		//②
		//从B[]中选出值最大、“花纹=条纹” 的轮胎，与A[]中最小的相比
		//如果b>a，则交换。
	    //以此类推。
		exchange(aList, bList);
		//③
		//从C[]中选出值最大、“花纹=块状” 的轮胎，与B[]中最小的相比
		//如果c>b，则交换。
	    //以此类推。
		//④
		//从C[]中选出值最大、“花纹=混合” 的轮胎，与B[]中最小的相比
		//如果c>b，则交换。
		//以此类推。
		exchange(aList, bList);		
		
		position(aList);//a元素恢复原来的位置
		position(bList);//b元素恢复原来的位置
		position(cList);//c元素恢复原来的位置
		
		JSONObject jsonObject;
		
		JSONObject resultJson=new JSONObject();
		
		JSONArray jsonArray=new JSONArray();
		JSONArray jsonArray2=new JSONArray();
		boolean flag=false;
		Map<String, Integer> map=new HashMap<String, Integer>();
		//a中交换信息得出
		if(aListS.size()>0){
			for(int i=0;i<aListS.size();i++){
				if(!aListS.get(i).getTyre_id().equals(aList.get(i).getTyre_id())){
					if(map.get(aListS.get(i).getTyre_id())!=null&&map.get(aList.get(i).getTyre_id())!=null){
						continue;
					}
					map.put(aListS.get(i).getTyre_id(),1);
					map.put(aList.get(i).getTyre_id(),1);
					jsonObject=new JSONObject();
					jsonObject.put("source", aListS.get(i));
					jsonObject.put("dest", aList.get(i));
					jsonArray.add(jsonObject);
					jsonArray2.add(jsonObject);
					flag=true;
				}
			}
		}
		resultJson.put("a", jsonArray);//a中交换信息
		
		//b中交换信息得出
		jsonArray=new JSONArray();
		if(bListS.size()>0){
			for(int i=0;i<bListS.size();i++){
				if(!bListS.get(i).getTyre_id().equals(bList.get(i).getTyre_id())){
					if(map.get(bListS.get(i).getTyre_id())!=null&&map.get(bList.get(i).getTyre_id())!=null){
						continue;
					}
					map.put(bListS.get(i).getTyre_id(),1);
					map.put(bList.get(i).getTyre_id(),1);
					jsonObject=new JSONObject();
					jsonObject.put("source", bListS.get(i));
					jsonObject.put("dest", bList.get(i));
					jsonArray.add(jsonObject);
					jsonArray2.add(jsonObject);
					flag=true;
				}
			}
		}
		resultJson.put("b", jsonArray);//b中交换信息
		
		//c中交换信息得出
		jsonArray=new JSONArray();
		if(cListS.size()>0){
			for(int i=0;i<cListS.size();i++){
				if(!cListS.get(i).getTyre_id().equals(cList.get(i).getTyre_id())){
					if(map.get(cListS.get(i).getTyre_id())!=null&&map.get(cList.get(i).getTyre_id())!=null){
						continue;
					}
					map.put(cListS.get(i).getTyre_id(),1);
					map.put(cList.get(i).getTyre_id(),1);
					jsonObject=new JSONObject();
					jsonObject.put("source", cListS.get(i));
					jsonObject.put("dest", cList.get(i));
					jsonArray.add(jsonObject);
					jsonArray2.add(jsonObject);
					flag=true;
				}
			}
		}
		resultJson.put("c", jsonArray);//c中交换信息
		
		//均值交换
		JSONObject resultJson2=new JSONObject();
		JSONArray aJ=average(aList);
		resultJson2.put("a", aJ);
		if(aJ!=null){
			flag=true;
			jsonArray2.addAll(aJ);
		}
		JSONArray bJ=average(bList);
		resultJson2.put("b", bJ);
		if(bJ!=null){
			flag=true;
			jsonArray2.addAll(bJ);
		}
		
		JSONArray cJ=average(cList);
		resultJson2.put("c", cJ);
		if(cJ!=null){
			flag=true;
			jsonArray2.addAll(cJ);
		}
		
		JSONObject json=new JSONObject();
		json.put("exchange", resultJson);//交换信息
		json.put("average", resultJson2);//匀值信息
		System.out.println("结论:\n"+json.toString());
		
		JSONObject json2=new JSONObject();
		json2.put("A", aList);
		json2.put("B", bList);
		json2.put("C", cList);
		System.out.println("最后数组:\n"+json2.toString()+"\n\n\n");
		if(flag==false){
			return null;
		}
		return jsonArray2;
	}
	
	/**
	 * 均值
	 * @param list
	 */
	private static JSONArray average(List<TyreByTuiJianVO> list){
		JSONArray jsonArray=new JSONArray();
		if(list==null||list.size()<4){
			return null;
		}
		TyreByTuiJianVO temp,temp2;
		JSONObject jsonObject;
		int k;
		boolean flag=false;
		for(int i=0;i+4<=list.size();i+=4){//B1>B3,B1>B4,B2>B3,B2>B4 或 B1<B3,B1<B4,B2<B3,B2<B4
			if(list.get(i).getTyre_paver().floatValue()<list.get(i+2).getTyre_paver().floatValue()&&list.get(i).getTyre_paver().floatValue()<list.get(i+2).getTyre_paver().floatValue()&&
					list.get(i+1).getTyre_paver().floatValue()<list.get(i+2).getTyre_paver().floatValue()&&list.get(i+1).getTyre_paver().floatValue()<list.get(i+2).getTyre_paver().floatValue()){
				temp=list.get(i+1);
				temp2=list.get(i+2);
				k=temp.getIndex();
				temp.setIndex(temp2.getIndex());
				temp2.setIndex(k);
				list.set(i+1, temp2);
				list.set(i+2, temp);
				jsonObject=new JSONObject();
				jsonObject.put("source", temp);
				jsonObject.put("dest", temp2);
				jsonArray.add(jsonObject);
				flag=true;
			}else if(list.get(i).getTyre_paver().floatValue()>list.get(i+2).getTyre_paver().floatValue()&&list.get(i).getTyre_paver().floatValue()>list.get(i+2).getTyre_paver().floatValue()&&
					list.get(i+1).getTyre_paver().floatValue()>list.get(i+2).getTyre_paver().floatValue()&&list.get(i+1).getTyre_paver().floatValue()>list.get(i+2).getTyre_paver().floatValue()){
				temp=list.get(i+1);
				temp2=list.get(i+2);
				k=temp.getIndex();
				temp.setIndex(temp2.getIndex());
				temp2.setIndex(k);
				list.set(i+1, temp2);
				list.set(i+2, temp);
				jsonObject=new JSONObject();
				jsonObject.put("source", temp);
				jsonObject.put("dest", temp2);
				jsonArray.add(jsonObject);
				flag=true;
			}
		}
		if(flag==false){
			return null;
		}
		return jsonArray;		
	}
	
	
	/**
	 * 加索引
	 * @param list
	 */
	private static void indexList(List<TyreByTuiJianVO> list){
		if(list==null){
			return;
		}
		for(int i=0;i<list.size();i++){
			list.get(i).setIndex(i);
		}
	}
	
	/**
	 * 恢复位置
	 * @param list
	 */
	private static void position(List<TyreByTuiJianVO> list){
		if(list==null){
			return;
		}
		TyreByTuiJianVO temp;
		TyreByTuiJianVO temp1;
		for(int i=0;i<list.size();i++){
			if(i!=list.get(i).getIndex()){
				temp=list.get(i);
				temp1=list.get(temp.getIndex());
				list.set(i, temp1);
				list.set(temp.getIndex(), temp);
			}
		}
	}
	
	/**
	 * 交换,最大的都放进maxList中,务件为规格相同,花纹相同,轮胎花纹值
	 * @param maxList 
	 * @param minList
	 */
	public static void exchange(List<TyreByTuiJianVO> maxList,List<TyreByTuiJianVO> minList){
		if(maxList==null||minList==null){
			return;
		}
		TyreByTuiJianVO max,min,temp;
		int j,k,t;
		for(int i=0;i<maxList.size();i++){
			max=maxList.get(i);
			temp=max;
			k=-1;
			for(j=minList.size()-1;j>=0;j--){
				min=minList.get(j);
				if(temp.getStandard().equals(min.getStandard())){//规格相同
					if(temp.getPattern().equals(min.getPattern())){//花纹相同
						if(temp.getTyre_paver().floatValue()<min.getTyre_paver().floatValue()){
							temp=min;
							k=j;
						}
					}
				}
			}
			if(k!=-1){
				t=temp.getIndex();
				temp.setIndex(max.getIndex());
				max.setIndex(t);
				maxList.set(i, temp);
				minList.set(k, max);
			}
		}
	}
	
	/**
	 * 从小到大排序
	 * @param list
	 */
	public static void sort(List<TyreByTuiJianVO> list){
		if(list==null||list.size()==0){
			return;
		}
		Collections.sort(list, new Comparator<TyreByTuiJianVO>() {
			public int compare(TyreByTuiJianVO obj1, TyreByTuiJianVO obj2) {
				if(obj1!=null&&obj2!=null){
					float t=obj1.getTyre_paver().floatValue()-obj2.getTyre_paver().floatValue();
					if(t>0){
						return 1;
					}else if(t==0){
						return 0;
					}else{
						return -1;
					}
				}else if(obj1==null&&obj2!=null){
					return -1;
				}else if(obj1==null&&obj2==null){
					return 0;
				}else if(obj1!=null){
					return 1;
				}
				return 0;
			};
		});
	
	}

	public static void main(String[] args) {
		String [] tt={"条纹","混合","块状"};
//		String [] tt={"条纹"};
		TyreByTuiJianVO t;
		int k=0;
		
		
		//A
		List<TyreByTuiJianVO> aList=new ArrayList<TyreByTuiJianVO>();
		for(int i=0;i<2;i++){
			t=new TyreByTuiJianVO();
			t.setPattern(tt[0]);//花纹
			t.setStandard("275/70R22."+i%4);//规格
			t.setTyre_id("LMD2774"+(k++));//编号
			t.setTyre_paver((float)(Math.random()*1000*1d));//值
			aList.add(t);
		}

		
		//B
		List<TyreByTuiJianVO> bList=new ArrayList<TyreByTuiJianVO>();
		for(int i=0;i<4;i++){
			t=new TyreByTuiJianVO();
			t.setPattern(tt[i%2]);//花纹
			t.setStandard("275/70R22."+i%4);//规格
			t.setTyre_id("LMD2774"+(k++));//编号
			t.setTyre_paver((float)(Math.random()*1000*1d));//值
			bList.add(t);
		}
		
		//C
		List<TyreByTuiJianVO> cList=new ArrayList<TyreByTuiJianVO>();
		for(int i=0;i<10;i++){
			t=new TyreByTuiJianVO();
			t.setPattern(tt[i%3]);//花纹
			t.setStandard("275/70R22."+i%4);//规格
			t.setTyre_id("LMD2774"+(k++));//编号
			t.setTyre_paver((float)(Math.random()*1000*1d));//值
			cList.add(t);
		}
		
		JSONObject json=new JSONObject();
		json.put("A", aList);
		json.put("B", bList);
		json.put("C", cList);
		System.out.println("原始:"+json.toString()+"\n\n\n");
		algorithm(aList, null, cList);

	}

}
