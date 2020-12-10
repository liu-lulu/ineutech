package com.psylife.servlet.trucks;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.psylife.entity.Company;
import com.psylife.entity.Specification;
import com.psylife.entity.Trucks;
import com.psylife.entity.TyreBase;
import com.psylife.entity.TyreBrand;
import com.psylife.entity.User;
import com.psylife.service.CompanyService;
import com.psylife.service.SpecificationService;
import com.psylife.service.TrucksService;
import com.psylife.service.TyreBaseService;
import com.psylife.service.impl.CompanyServiceImpl;
import com.psylife.service.impl.SpecificationServiceImpl;
import com.psylife.service.impl.TrucksServiceImpl;
import com.psylife.service.impl.TyreBaseServiceImpl;
import com.psylife.servlet.BaseServlet;
import com.psylife.util.Constants;
import com.psylife.util.page.ListInfo;
import com.psylife.vo.Passport;
import com.psylife.vo.TrucksByAdminVO;
import com.psylife.vo.TyreVO;
import com.psylife.vo.WarnMsgVO;
import com.psylife.vo.web.TrucksWatchVO;

/**
 * 区域
 * @author xu
 *
 */
@WebServlet(urlPatterns = { "/trucks/*" }, loadOnStartup = 1)
public class TrucksServlet extends BaseServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2419813766071377829L;
	
	private TrucksService trucksService=new TrucksServiceImpl();
	
	private CompanyService companyService = new CompanyServiceImpl();
	
	private SpecificationService specificationService = new SpecificationServiceImpl();
	private TyreBaseService tyreBaseService = new TyreBaseServiceImpl();
	
	public TrucksServlet() {
		super();
	}
	
	/**
	 * 初始配置
	 */
	@Override
	protected void initConfig() {
		filterInterceptor(new String[]{});//不需要用户权限的action
	}
	
	@Override
	protected void doProccess(HttpServletRequest request,
			HttpServletResponse response,String action) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");// request.getRequestURL()
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		Passport passport=getPassport(request);
		//1.车辆监控列表
		if("watchlist".equals(action)){
			String keyWord=request.getParameter("keyWord");//标题
			String currentPageNOs=request.getParameter("currentPageNO");//标题
			Map<String, Object> paramaterMap=new HashMap<String, Object>();
			int currentPageNO = 1; // 当前页
			if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
				currentPageNO=Integer.valueOf(currentPageNOs);
			}
			paramaterMap.put("currentPageNO", currentPageNO);
			paramaterMap.put("pageSize",  Constants.PAGESIZE);
			paramaterMap.put("keyWord", keyWord);
			ListInfo<TrucksWatchVO> listInfo=trucksService.searchByWatchlist(passport.getCompanyId(), keyWord, currentPageNO, Constants.PAGESIZE);
			paramaterMap.put("listInfo", listInfo);
			parameterProccess(paramaterMap, request);
			request.getRequestDispatcher("/WEB-INF/jsp/trucks/watchlist.jsp").forward(request,response);
		}
		//2.车辆绑定dtu列表
		else if("truckslistbybind".equals(action)){
			String keyWord=request.getParameter("keyWord");//标题
			String currentPageNOs=request.getParameter("currentPageNO");//标题
			Map<String, Object> paramaterMap=new HashMap<String, Object>();
			int currentPageNO = 1; // 当前页
			if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
				currentPageNO=Integer.valueOf(currentPageNOs);
			}
			paramaterMap.put("currentPageNO", currentPageNO);
			paramaterMap.put("pageSize",  Constants.PAGESIZE);
			paramaterMap.put("keyWord", keyWord);
			ListInfo<TrucksWatchVO> listInfo=trucksService.searchBylistBind(passport.getCompanyId(), keyWord, currentPageNO, Constants.PAGESIZE);
			paramaterMap.put("listInfo", listInfo);
			paramaterMap.put("dtu_id", request.getParameter("dtu_id"));
			paramaterMap.put("phone", request.getParameter("phone"));
			parameterProccess(paramaterMap, request);
			request.getRequestDispatcher("/WEB-INF/jsp/trucks/truckslistbybind.jsp").forward(request,response);
		}
		
		//3.车辆绑定dtu提交
		else if("trucksbind".equals(action)){
			String dtu_id=request.getParameter("dtu_id");//dtu id
			String trucks_id=request.getParameter("trucks_id");//车牌
			String phone=request.getParameter("phone");//手机号
			if(trucksService.trucksDtuBind(trucks_id, dtu_id,phone)){
				response.sendRedirect("../trucks/watchlist.action");		
			}else{
				error404(request, response);
			}
		}
		
		//4.车辆解和绑定dtu提交
		else if("removebind".equals(action)){
			String dtu_id=request.getParameter("dtu_id");//标题
			String tag="0";
			if(trucksService.removeBind(dtu_id)){
				tag="1";
			}
			out.write("{\"tag\":"+tag+"}");
		}
		
		//5.录入基本信息
		else if("goBasicInfoJsp".equals(action)){
			List<Company> companylist = companyService.allCompanyList(passport.getCompanyId());
			List<TyreBrand> tyreBrandlist = specificationService.getTyreBrandList();
			List<Specification> specificationlist = specificationService.getSpecificationList();
			List<Specification> patternlist = specificationService.getPatternCodeList();
			Map<String, Object> paramaterMap=new HashMap<String, Object>();
			paramaterMap.put("companylist", companylist);
			paramaterMap.put("tyreBrandlist", tyreBrandlist);
			paramaterMap.put("specificationlist", specificationlist);
			paramaterMap.put("patternlist", patternlist);
			parameterProccess(paramaterMap, request);
			request.getRequestDispatcher("/WEB-INF/jsp/trucks/inTruckInfo.jsp").forward(request,response);
			
		}
		
		//6.基本信息入库
		else if("inBasicInfo".equals(action)){
			String truckId = request.getParameter("truckId");
			String truckBrand = request.getParameter("truckBrand");
			String truckStyle = request.getParameter("truckStyle");
			String truckType = request.getParameter("truckType");
			String company = request.getParameter("company");
//			String liCheng = request.getParameter("liCheng");
//			String liChengRun = request.getParameter("liChengRun");
			String mabiao = request.getParameter("mabiao");
//			String mabiaoRuku = request.getParameter("mabiaoRuku");
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
				//response.sendRedirect("../trucks/watchlist.action");
				jsonObject.put("state", "true");
			} else {

				//response.sendRedirect("../trucks/goBasicInfoJsp.action");
				jsonObject.put("state", "false");
			}
			out.print(jsonObject.toString());
		}
		else if("checkTrucks_id".equals(action)){//车牌号是否已存在
			String trucks_id=request.getParameter("trucks_id");
			TrucksByAdminVO trucks = trucksService.getByTrucks_id(trucks_id);
			if(trucks==null){
				out.write("true");
			}else{
				out.write("false");
			}
		}
		else if("checkTyre_id".equals(action)){//轮胎号是否已存在
			String tyre_id=request.getParameter("tyre_id");
			TyreVO tyre = tyreBaseService.tyreDetial(passport.getUserId(), tyre_id, null, null);
			JSONObject jsonObject=new JSONObject();
			
			if(tyre==null){
				jsonObject.put("state", "true");
			}else{
				jsonObject.put("state", "false");
			}
			out.print(jsonObject.toString());
		}
		//报警信息列表
		else if("warnMsglist".equals(action)){
			String keyWord=request.getParameter("keyWord");//标题
			String status=request.getParameter("status");//标题
			String currentPageNOs=request.getParameter("currentPageNO");//标题
			Map<String, Object> paramaterMap=new HashMap<String, Object>();
			int currentPageNO = 1; // 当前页
			if(currentPageNOs!=null&&!"".equals(currentPageNOs)){
				currentPageNO=Integer.valueOf(currentPageNOs);
			}
			paramaterMap.put("currentPageNO", currentPageNO);
			paramaterMap.put("pageSize",  Constants.PAGESIZE);
			paramaterMap.put("keyWord", keyWord);
			paramaterMap.put("status", status);
			paramaterMap.put("autoflush", request.getParameter("autoflush"));
			ListInfo<WarnMsgVO> listInfo=trucksService.searchWarnMsglist(passport.getCompanyId(), keyWord, currentPageNO, Constants.PAGESIZE);
			paramaterMap.put("listInfo", listInfo);
			parameterProccess(paramaterMap, request);
			request.getRequestDispatcher("/WEB-INF/jsp/trucks/warnMsglist.jsp").forward(request,response);
		}
		else if("test".equals(action)){
			out.write("{\"success\": true, \"msg\": \"required\"}");
		}
		
		else{
			error404(request, response);	
		}	
		
		out.flush();
		out.close();
	}
	
}
