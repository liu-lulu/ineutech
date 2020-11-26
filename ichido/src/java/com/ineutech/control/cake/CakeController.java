package com.ineutech.control.cake;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.ineutech.cons.Constans;
import com.ineutech.entity.KpiResult;
import com.ineutech.entity.LoginShop;
import com.ineutech.entity.cake.CakeEmployee;
import com.ineutech.entity.cake.CakeTransaction;
import com.ineutech.service.CakeService;
import com.ineutech.service.DeliveryService;
import com.ineutech.util.DateUtil;

/**
 * 蛋糕模块
 * 
 * @author liululu
 *
 */
@RequestMapping("cake")
@Controller
public class CakeController {

	private Logger logger = LoggerFactory.getLogger(CakeController.class);

	@Resource
	private CakeService cakeService;
	
	@RequestMapping("toLogin")
	public ModelAndView toDeliveryLogin(HttpSession session){
		session.removeAttribute("cake");
		return new ModelAndView("cake/login");
	}

	@RequestMapping("login")
	public ModelAndView login(@ModelAttribute CakeEmployee loginInfo, HttpSession session) {
		CakeEmployee cakeEmployee = cakeService.login(loginInfo);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (cakeEmployee == null||CakeEmployee.ROLE_EMPLOYEE==cakeEmployee.getRole()) {
			paramMap.put("e_number", loginInfo.getE_id());
			paramMap.put("pwd", loginInfo.getPwd());
			paramMap.put("msg", "用户名或密码错误");
			return new ModelAndView("cake/login", paramMap);
		}
		session.setAttribute("cake", cakeEmployee);
		
		paramMap.put("shops", cakeService.shops());
		if (cakeEmployee.getRole()==CakeEmployee.ROLE_ADMIN) {
			paramMap.put("managers", cakeService.getManager());
			return new ModelAndView("cake/cakeadmin",paramMap);
			//return new ModelAndView("redirect:shopSale.do");
		}
		return new ModelAndView("cake/caketrans",paramMap);
		
	}

	@RequestMapping("transactionList")
	public ModelAndView transactionList(@RequestParam(required = false) String shopCode,@RequestParam(required = false) String beginDate,@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String currentPageNO,HttpSession session) {
		
		CakeEmployee cakeEmployee=(CakeEmployee) session.getAttribute("cake");
		if (cakeEmployee==null) {
			return new ModelAndView("cake/login");
		}
		
		Date curDate=new Date();
		Date begin=DateUtil.getDayBegin(Optional.ofNullable(beginDate).map(u ->DateUtil.getDate(u)).orElse(curDate));
		Date end=DateUtil.getDayEnd(Optional.ofNullable(endDate).map(u ->DateUtil.getDate(u)).orElse(curDate));
		
		// 當前頁
		int curPage = StringUtils.isEmpty(currentPageNO) ? 1 : Integer
				.valueOf(currentPageNO);
				
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		// 每頁的行數
		int rowNum = Constans.PAGE_SIZE;
		
		int count = cakeService.transactionsRecordCount(shopCode, begin, end);
		// 頁數
		double pageNum = Math.ceil((double) count / rowNum);
		
		
		List<CakeTransaction> transactions=cakeService.transactionsRecord(shopCode, begin, end, curPage);
		paramMap.put("pageCount", pageNum);
		paramMap.put("rowNum", rowNum);
		paramMap.put("currentPageNO", curPage);
		paramMap.put("sizeOfTotalList", count);
		
		paramMap.put("shopCode", shopCode);
		paramMap.put("beginDate", DateUtil.formatDate(begin));
		paramMap.put("endDate", DateUtil.formatDate(end));
		paramMap.put("caketransactions", transactions);
		
		paramMap.put("shops", cakeService.shops());

		return new ModelAndView("cake/caketrans",paramMap);
		
	}
	
	@ResponseBody
	@RequestMapping(value="transactionDetail",produces = "text/plain;charset=UTF-8")
	public String transactionDetail(@RequestParam(required = false) String shopCode,@RequestParam(required = false) String beginDate,@RequestParam(required = false) String endDate,@RequestParam String e_number,HttpSession session ){
		CakeEmployee cakeEmployee=(CakeEmployee) session.getAttribute("cake");
		if (cakeEmployee==null) {
			JSONObject ret=new JSONObject();
			ret.put("info", "login");
			return ret.toJSONString();
		}
		Date curDate=new Date();
		Date begin=DateUtil.getDayBegin(Optional.ofNullable(beginDate).map(u ->DateUtil.getDate(u)).orElse(curDate));
		Date end=DateUtil.getDayEnd(Optional.ofNullable(endDate).map(u ->DateUtil.getDate(u)).orElse(curDate));
		List<CakeTransaction> details=cakeService.transactions(shopCode,e_number, begin, end,null,null, null);
		
		return JSON.toJSONString(details);
	}
	
	@ResponseBody
	@RequestMapping("confirm")
	public String confirm(@RequestParam Integer[] cake_id,@RequestParam Integer type,HttpSession session ){
		CakeEmployee cakeEmployee=(CakeEmployee) session.getAttribute("cake");
		if (cakeEmployee==null) {
			return "login";
		}
		
		int flag=0;
		if (1==type) {//确认
			flag=1;
		}
		int ret=cakeService.confirm(Arrays.asList(cake_id), cakeEmployee.getE_id(), new Date(), flag);
		if (ret==0) {
			return "fail";
		}
		return "success";
	}
	
	@RequestMapping("managerRecord")
	public ModelAndView managerRecord(@RequestParam(required = false) Integer e_id,@RequestParam(required = false) String beginDate,@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String currentPageNO,HttpSession session) {
		
		CakeEmployee cakeEmployee=(CakeEmployee) session.getAttribute("cake");
		if (cakeEmployee==null) {
			return new ModelAndView("cake/login");
		}
		
		Date curDate=new Date();
		Date begin=DateUtil.getDayBegin(Optional.ofNullable(beginDate).map(u ->DateUtil.getDate(u)).orElse(curDate));
		Date end=DateUtil.getDayEnd(Optional.ofNullable(endDate).map(u ->DateUtil.getDate(u)).orElse(curDate));
		
		List<CakeTransaction> shopSales=cakeService.managerSale(e_id, 1, begin, end);
		// 當前頁
		int curPage = StringUtils.isEmpty(currentPageNO) ? 1 : Integer
				.valueOf(currentPageNO);
				
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		// 每頁的行數
		int rowNum = Constans.PAGE_SIZE;
		
		int count = cakeService.transactionsCount(null, null, begin, end, e_id, 1);
		// 頁數
		double pageNum = Math.ceil((double) count / rowNum);
		
		List<CakeTransaction> records=cakeService.transactions(null, null, begin, end, e_id, 1,curPage);
		paramMap.put("pageCount", pageNum);
		paramMap.put("rowNum", rowNum);
		paramMap.put("currentPageNO", curPage);
		paramMap.put("sizeOfTotalList", count);
		
		paramMap.put("e_id", e_id);
		paramMap.put("beginDate", DateUtil.formatDate(begin));
		paramMap.put("endDate", DateUtil.formatDate(end));
		paramMap.put("records", records);
		
		paramMap.put("managers", cakeService.getManager());
		
		paramMap.put("shopSales", shopSales);
		paramMap.put("totalSaleCount", shopSales.stream().mapToInt(CakeTransaction::getCake_num).sum());
		paramMap.put("totalSalePrice", Math.ceil(shopSales.stream().mapToDouble(CakeTransaction::getCake_price).sum()*10d)/10);

		return new ModelAndView("cake/cakeadmin",paramMap);
		
	}
	
	@ResponseBody
	@RequestMapping("add")
	public String add(@ModelAttribute CakeEmployee info,HttpSession session ){
		CakeEmployee cakeEmployee=(CakeEmployee) session.getAttribute("cake");
		if (cakeEmployee==null) {
			return "login";
		}
		
		info.setPwd(info.getE_number());
		int ret=cakeService.addEmployee(info);
		if (ret==0) {
			return "fail";
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("del")
	public String del(@RequestParam Integer e_id,HttpSession session ){
		CakeEmployee cakeEmployee=(CakeEmployee) session.getAttribute("cake");
		if (cakeEmployee==null) {
			return "login";
		}
		
		int ret=cakeService.delEmployee(e_id);
		if (ret==0) {
			return "fail";
		}
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value="getEmployee",produces = "text/plain;charset=UTF-8")
	public String getEmployee(@RequestParam String e_info,HttpSession session ){
		CakeEmployee cakeEmployee=(CakeEmployee) session.getAttribute("cake");
		if (cakeEmployee==null) {
			JSONObject ret=new JSONObject();
			ret.put("info", "login");
			return ret.toJSONString();
		}
		
		List<CakeEmployee> employees=cakeService.getEmployees(e_info);
		
		return JSON.toJSONString(employees);
	}
	
	@ResponseBody
	@RequestMapping(value="export",produces = "text/plain;charset=UTF-8")
	public String export(@RequestParam(required = false) String shopCode,@RequestParam(required = false) String beginDate,@RequestParam(required = false) String endDate) {
		
		JSONObject jsonObject = new JSONObject();
		
		Date curDate=new Date();
		Date begin=DateUtil.getDayBegin(Optional.ofNullable(beginDate).map(u ->DateUtil.getDate(u)).orElse(curDate));
		Date end=DateUtil.getDayEnd(Optional.ofNullable(endDate).map(u ->DateUtil.getDate(u)).orElse(curDate));
		
		List<CakeTransaction> sales=cakeService.employeeSale(null, begin, end);
		
		String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int a = (int)(Math.random()*(9999-1000+1))+1000;
		String filePath = "C:\\"+"ichido"+"_"+time+"cakesale"+a+".xlsx";
		String[] titles = {"工号","姓名","总数","金额"};
		
		try {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("员工蛋糕销售统计");
		XSSFRow row = sheet.createRow(0);
		for(int i=0;i< titles.length;i++){
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(titles[i]);
		}
		
		for (int i = 0; i < sales.size(); i++) {
			XSSFRow newrow = sheet.createRow(i + 1);
			CakeTransaction result = sales.get(i);
			XSSFCell cell1 = newrow.createCell(0);
			cell1.setCellValue(result.getE_number());
			XSSFCell cell2 = newrow.createCell(1);
			cell2.setCellValue(result.getE_name());
			XSSFCell cell3 = newrow.createCell(2);
			cell3.setCellValue(result.getCake_num());
			XSSFCell cell4 = newrow.createCell(3);
			cell4.setCellValue(result.getCake_price());
		}

		FileOutputStream outputStream = new FileOutputStream(filePath);
        wb.write(outputStream);
        outputStream.close();
        jsonObject.put("msg", filePath);
		}catch(Exception e){
			e.printStackTrace();
			jsonObject.put("msg", "导出失败！");
		}
		return jsonObject.toJSONString();
	}
	
	/**
	 * 下载文件到指定目录
	 * @return
	 */
	@RequestMapping("xiazai")
	public void xiazai(HttpServletResponse response,HttpServletRequest request){
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String realPath= request.getParameter("realPath");
			String fileName = realPath.substring(realPath.lastIndexOf("\\") + 1);
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			InputStream in = new FileInputStream(realPath);
	        int len = 0;
	        // 5.创建数据缓冲区
	        byte[] buffer = new byte[1024];
	        // 6.通过response对象获取OutputStream流
	        OutputStream out = response.getOutputStream();
	        // 7.将FileInputStream流写入到buffer缓冲区
	        while ((len = in.read(buffer)) > 0) {
	            // 8.使用OutputStream将缓冲区的数据输出到客户端浏览器
	            out.write(buffer, 0, len);
	        }
	        in.close();
	        out.flush();  
	        out.close();  
	        out=null;
	        File file = new File(realPath);
	        file.delete();
	        realPath = "";
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="numberValid",produces = "text/plain;charset=UTF-8")
	public String numberValid(@RequestParam String e_number) {
		
		JSONObject jsonObject = new JSONObject();
		CakeEmployee info=cakeService.login(new CakeEmployee(e_number));
		if (info==null) {
			jsonObject.put("valid", true);
		}else {
			jsonObject.put("valid", false);
		}
		return jsonObject.toJSONString();
	}
	@ResponseBody
	@RequestMapping("updPwd")
	public String updPwd(String newPwd,HttpSession session){
		CakeEmployee cakeEmployee=(CakeEmployee) session.getAttribute("cake");
		if (cakeEmployee==null) {
			return "login";
		}
		int ret=cakeService.updPwd(cakeEmployee.getE_id(),newPwd);
		if(ret==1){
			return "success";
		}else {
			return "fail";
		}
	}
	public static void main(String[] args) {
		
	}

}
