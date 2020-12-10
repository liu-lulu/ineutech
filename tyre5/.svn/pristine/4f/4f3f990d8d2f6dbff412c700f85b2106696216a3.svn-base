package com.kkbc.control.truck;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kkbc.control.BaseController;
import com.kkbc.entity.Company;
import com.kkbc.entity.Specification;
import com.kkbc.entity.Trucks;
import com.kkbc.entity.TyreBase;
import com.kkbc.entity.TyreBrand;
import com.kkbc.entity.User;
import com.kkbc.service.CompanyService;
import com.kkbc.service.SpecificationService;
import com.kkbc.service.TrucksService;
import com.kkbc.service.TyreBaseService;
import com.kkbc.util.page.ListInfo;
import com.kkbc.vo.Passport;
import com.kkbc.vo.TrucksByAdminVO;
import com.kkbc.vo.TyreVO;
import com.kkbc.vo.web.TrucksWatchVO;
import com.psylife.util.Constants;
import com.psylife.util.RequestUtil;

/**
 * 区域
 * @author liululu
 *
 */
@Controller
@RequestMapping("/trucks")
public class TrucksController extends BaseController{
	
	@Resource
	private TrucksService trucksService;
	@Resource
	private CompanyService companyService;
	@Resource
	private SpecificationService specificationService;
	@Resource
	private TyreBaseService tyreBaseService;
	
	//1.车辆监控列表
	@RequestMapping("/watchlist")
	public ModelAndView watchlist(@RequestParam(value="keyWord",required = false) String keyWord,@RequestParam(value="currentPageNO",required = false) String currentPageNOs,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
//		String keyWord=request.getParameter("keyWord");//标题
//		String currentPageNOs=request.getParameter("currentPageNO");//标题
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		int currentPageNO = 1; // 当前页
		if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
			currentPageNO=Integer.valueOf(currentPageNOs);
		}
		paramaterMap.put("currentPageNO", currentPageNO);
		paramaterMap.put("pageSize",  Constants.PAGESIZE);
		paramaterMap.put("keyWord", keyWord);
		Passport passport=RequestUtil.getPassport(request);
		ListInfo<TrucksWatchVO> listInfo=trucksService.searchByWatchlist(passport.getCompanyId(), keyWord, currentPageNO, Constants.PAGESIZE);
		paramaterMap.put("listInfo", listInfo);
//		RequestUtil.parameterProccess(paramaterMap, request);
		
//		request.getRequestDispatcher("/views/trucks/watchlist.jsp").forward(request,response);
		
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("trucks/watchlist");
		return mv;
	}
	
	//2.车辆绑定dtu列表
	@RequestMapping("/truckslistbybind")
	public ModelAndView truckslistbybind(@RequestParam(value="keyWord",required = false) String keyWord,@RequestParam(value="currentPageNO",required = false) String currentPageNOs,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
//		String keyWord=request.getParameter("keyWord");//标题
//		String currentPageNOs=request.getParameter("currentPageNO");//标题
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		int currentPageNO = 1; // 当前页
		if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
			currentPageNO=Integer.valueOf(currentPageNOs);
		}
		paramaterMap.put("currentPageNO", currentPageNO);
		paramaterMap.put("pageSize",  Constants.PAGESIZE);
		paramaterMap.put("keyWord", keyWord);
		Passport passport=RequestUtil.getPassport(request);
		ListInfo<TrucksWatchVO> listInfo=trucksService.searchBylistBind(passport.getCompanyId(), keyWord, currentPageNO, Constants.PAGESIZE);
		paramaterMap.put("listInfo", listInfo);
		paramaterMap.put("dtu_id", request.getParameter("dtu_id"));
		paramaterMap.put("phone", request.getParameter("phone"));
//		RequestUtil.parameterProccess(paramaterMap, request);
//		request.getRequestDispatcher("/views/trucks/truckslistbybind.jsp").forward(request,response);
		
		ModelAndView mv=new ModelAndView();
		mv.addAllObjects(paramaterMap);
		mv.setViewName("trucks/truckslistbybind");
		return mv;
	}
	
	//3.车辆绑定dtu提交
	@RequestMapping("/trucksbind")
	public void trucksbind(@RequestParam(value="dtu_id",required = false) String dtu_id,@RequestParam(value="trucks_id",required = false) String trucks_id,@RequestParam(value="phone",required = false) String phone,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
//		String dtu_id=request.getParameter("dtu_id");//dtu id
//		String trucks_id=request.getParameter("trucks_id");//车牌
//		String phone=request.getParameter("phone");//手机号
		if(trucksService.trucksDtuBind(trucks_id, dtu_id,phone)){
			response.sendRedirect("../trucks/watchlist.do");		
		}else{
			RequestUtil.error404(request, response);
		}
	}
	
	//4.车辆解和绑定dtu提交
	@RequestMapping("/removebind")
	public void removebind(@RequestParam("dtu_id") String dtu_id,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
//		String dtu_id=request.getParameter("dtu_id");//标题
		String tag="0";
		if(trucksService.removeBind(dtu_id)){
			tag="1";
		}
		PrintWriter out = response.getWriter();
		out.write("{\"tag\":"+tag+"}");
		out.flush();
		out.close();
	}
	
	//5.录入基本信息
	@RequestMapping("/goBasicInfoJsp")
	public void goBasicInfoJsp(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		Passport passport=RequestUtil.getPassport(request);
		List<Company> companylist = companyService.allCompanyList(passport.getCompanyId());
		List<TyreBrand> tyreBrandlist = specificationService.getTyreBrandList();
		List<Specification> specificationlist = specificationService.getSpecificationList();
		List<Specification> patternlist = specificationService.getPatternCodeList();
		Map<String, Object> paramaterMap=new HashMap<String, Object>();
		paramaterMap.put("companylist", companylist);
		paramaterMap.put("tyreBrandlist", tyreBrandlist);
		paramaterMap.put("specificationlist", specificationlist);
		paramaterMap.put("patternlist", patternlist);
		RequestUtil.parameterProccess(paramaterMap, request);
		request.getRequestDispatcher("/views/trucks/inTruckInfo.jsp").forward(request,response);
		
	}
	
	//6.基本信息入库
	@RequestMapping("/inBasicInfo")
	public void inBasicInfo(
//			@RequestParam("truckId") String truckId,@RequestParam("truckBrand") String truckBrand,
//			@RequestParam("truckStyle") String truckStyle,@RequestParam("truckType") String truckType,
//			@RequestParam("company") String company,@RequestParam("mabiao") String mabiao,
//			@RequestParam("transportType") String transportType,@RequestParam("guacheTrucksId") String guacheTrucksId,
//			@RequestParam("trucksMode") String trucksMode,@RequestParam("guacheSaveFlag") String guacheSaveFlag,@RequestParam("dtuMultiFlag") String dtuMultiFlag,
//			@RequestParam("tyreId") String[] tyreIds,@RequestParam("tyreBrand") String[] tyreBrands,
//			@RequestParam("tyreType1") String[] tyreType1s,@RequestParam("tyreType2") String[] tyreType2s,
//			@RequestParam("tyreType3") String[] tyreType3s,@RequestParam("tyreType4") String[] tyreType4s,
//			@RequestParam("tyreType5") String[] tyreType5s,@RequestParam("tyreType6") String[] tyreType6s,
//			@RequestParam("tyreType7") String[] tyreType7s,@RequestParam("tyreWhere") String[] tyreWheres,@RequestParam("tyreDepth") String[] tyreDepths,
			HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String truckId = request.getParameter("truckId");
		String truckBrand = request.getParameter("truckBrand");
		String truckStyle = request.getParameter("truckStyle");
		String truckType = request.getParameter("truckType");
		String company = request.getParameter("company");
		String mabiao = request.getParameter("mabiao");
		String transportType = request.getParameter("transportType");
		String guacheTrucksId = StringUtils.isEmpty(request.getParameter("guacheTrucksId"))?"挂-"+truckId:request.getParameter("guacheTrucksId");
		String trucksMode = request.getParameter("trucksMode");
		String guacheSaveFlag = request.getParameter("guacheSaveFlag");
		String dtuMultiFlag = request.getParameter("dtuMultiFlag");
		
		String[] tyreIds = request.getParameterValues("tyreId");
		String[] tyreBrands = request.getParameterValues("tyreBrand");
		String[] tyreType1s = request.getParameterValues("tyreType1");
		String[] tyreType2s = request.getParameterValues("tyreType2");
		String[] tyreType3s = request.getParameterValues("tyreType3");
		String[]tyreType4s = request.getParameterValues("tyreType4");
		String[] tyreType5s = request.getParameterValues("tyreType5");
		String[] tyreType6s = request.getParameterValues("tyreType6");
		String[] tyreType7s = request.getParameterValues("tyreType7");
		String[] tyreWheres = request.getParameterValues("tyreWhere");
		String[] tyreDepths = request.getParameterValues("tyreDepth");
		
		Passport passport=RequestUtil.getPassport(request);
		Trucks trucks = new Trucks();
		trucks.setTrucks_id(truckId);
		trucks.setTrucks_brand(truckBrand);
		trucks.setTrucks_style(truckStyle);
		trucks.setTrucks_type(truckType);
		trucks.setMabiao(Double.valueOf(mabiao));
		trucks.setMabiao_ruku(Double.valueOf(mabiao));
		trucks.setTransport_type(transportType);
		trucks.setGuache_trucks_id("挂车".equals(truckType)?"":(0<truckStyle.indexOf("+")&&truckStyle.substring(truckStyle.indexOf("+")+1).length()>0?guacheTrucksId:""));
		trucks.setTrucks_mode(trucksMode);
		trucks.setLi_cheng(0.0);
		trucks.setLi_cheng_run(0.0);
		if (guacheSaveFlag!=null&&"是".equals(guacheSaveFlag)) {
			guacheSaveFlag="0";
		} else {
			guacheSaveFlag="1";
		}
		trucks.setGuache_save_flag("挂车".equals(truckType)||(0<truckStyle.indexOf("+")&&truckStyle.substring(truckStyle.indexOf("+")+1).length()==0)?1:(0<truckStyle.indexOf("+")?Integer.valueOf(guacheSaveFlag):0));
		if ("一个".equals(dtuMultiFlag)) {
			dtuMultiFlag="0";
		} else {
			dtuMultiFlag="1";
		}
		trucks.setDtu_multi_flag(truckStyle.indexOf("+")<0||("主车".equals(truckType)&&0<truckStyle.indexOf("+")&&truckStyle.substring(truckStyle.indexOf("+")+1).length()==0)?0:Integer.valueOf(dtuMultiFlag));
		User user=companyService.getCompanyId(company, passport.getCompanyId());
		trucks.setCompany(user.getUser_company());
		trucks.setCompany_id(user.getUser_company_id());
		
		List<TyreBase> tyres = new ArrayList<TyreBase>();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = formatter.format(new Date());
		for(int i=0;i<tyreIds.length;i++){
			TyreBase tyreBase=new TyreBase();
			tyreBase.setTyre_brand(tyreBrands[i]);
			tyreBase.setTyre_depth(Float.valueOf(tyreDepths[i]));
			tyreBase.setTyre_id(tyreIds[i]);
			tyreBase.setTyre_type1(tyreType1s[i]);
			tyreBase.setTyre_type2(tyreType2s[i]);
			tyreBase.setTyre_type3(tyreType3s[i]);
			if ("无".equals(tyreType4s[i])) {
				tyreType4s[i]="0";
			} else {
				tyreType4s[i]="1";
			}
			tyreBase.setTyre_type4(Integer.valueOf(tyreType4s[i]));
			if ("无".equals(tyreType5s[i])) {
				tyreType5s[i]="0";
			} else {
				tyreType5s[i]="1";
			}
			tyreBase.setTyre_type5(Integer.valueOf(tyreType5s[i]));
			if ("无".equals(tyreType6s[i])) {
				tyreType6s[i]="0";
			} else {
				tyreType6s[i]="1";
			}
			tyreBase.setTyre_type6(Integer.valueOf(tyreType6s[i]));
			if ("半钢".equals(tyreType7s[i])) {
				tyreType7s[i]="0";
			} else {
				tyreType7s[i]="1";
			}
			tyreBase.setTyre_type7(Integer.valueOf(tyreType7s[i]));
			tyreBase.setTyre_type8(passport.getTrueName());
			tyreBase.setTyre_type9(currentTime);
			tyreBase.setTyre_where(tyreWheres[i]);
			tyreBase.setTyre_flag(1);
			tyreBase.setUser_id(user.getUser_id());
			tyres.add(tyreBase);
		}

		int result = trucksService.inTruckInfo(trucks, tyres, passport.getUserId());
		JSONObject jsonObject=new JSONObject();
		if (result==35) {
			jsonObject.put("state", "true");
		} else {

			jsonObject.put("state", "false");
		}
		PrintWriter out = response.getWriter();
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}
	
	//车牌号是否已存在
	@RequestMapping("/checkTrucks_id")
	public void checkTrucks_id(@RequestParam("trucks_id") String trucks_id,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
//		String trucks_id=request.getParameter("trucks_id");
		TrucksByAdminVO trucks = trucksService.getByTrucks_id(trucks_id);
		PrintWriter out = response.getWriter();
		if(trucks==null){
			out.write("true");
		}else{
			out.write("false");
		}
		out.flush();
		out.close();
	}
	
	//轮胎号是否已存在
	@RequestMapping("/checkTyre_id")
	public void checkTyre_id(@RequestParam("tyre_id") String tyre_id,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
//		String tyre_id=request.getParameter("tyre_id");
		Passport passport=RequestUtil.getPassport(request);
		TyreVO tyre = tyreBaseService.tyreDetial(passport.getUserId(), tyre_id, null, null);
		JSONObject jsonObject=new JSONObject();
		PrintWriter out = response.getWriter();
		if(tyre==null){
			jsonObject.put("state", "true");
		}else{
			jsonObject.put("state", "false");
		}
		out.print(jsonObject.toString());
		out.flush();
		out.close();
	}

}
