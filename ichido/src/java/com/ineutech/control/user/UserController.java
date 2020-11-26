package com.ineutech.control.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gexin.fastjson.JSONObject;
import com.ineutech.cons.Constans;
import com.ineutech.entity.KpiLevel;
import com.ineutech.entity.KpiResult;
import com.ineutech.entity.LoginShop;
import com.ineutech.entity.TransactionHistory;
import com.ineutech.service.UserService;
import com.ineutech.util.DateUtil;
import com.vdurmont.emoji.EmojiParser;

/**
 * 用户模块
 * 
 * @author liululu
 *
 */
@Controller
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserService userService;


	@RequestMapping("loginuser")
	public ModelAndView login(@RequestParam String username,
			@RequestParam String password, HttpSession session) {
		LoginShop shop = userService.getLoginShop(username, password);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (shop == null) {
			paramMap.put("username", username);
			paramMap.put("password", password);
			paramMap.put("msg", "用户名或密码错误");
			return new ModelAndView("login", paramMap);
		}
		session.setAttribute("shop", shop);
		if (shop.getUpdPwd()==0) {
			return new ModelAndView("pwd");
		}else {
			return new ModelAndView("redirect:kpi.do");
		}
		
	}

	@RequestMapping("detail")
	public ModelAndView detail(@RequestParam(required = false) String shop_code,
			@RequestParam(required = false) String beginDate,@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String currentPageNO,HttpSession session) {
		
		String shopCode=((LoginShop) session.getAttribute("shop")).getShop_code();
		
		LoginShop shop=(LoginShop) session.getAttribute("shop");
		Integer role=shop.getRole();
		if (role!=null&&role==1) {
			shopCode=shop_code;
		}
		
		// 當前頁
		int curPage = StringUtils.isEmpty(currentPageNO) ? 1 : Integer
				.valueOf(currentPageNO);
				
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		// 每頁的行數
		int rowNum = Constans.PAGE_SIZE;
		Date curdate=new Date();
		int count = userService.getHistoriesCount(shopCode, DateUtil.getDate(DateUtil.getMondy(curdate)), DateUtil.getDayEnd(curdate));
		// 頁數
		double pageNum = Math.ceil((double) count / rowNum);
		
		
		List<TransactionHistory> histories=userService.getHistories(shopCode, DateUtil.getDate(DateUtil.getMondy(curdate)), DateUtil.getDayEnd(curdate), curPage);
		paramMap.put("pageCount", pageNum);
		paramMap.put("rowNum", rowNum);
		paramMap.put("currentPageNO", curPage);
		paramMap.put("sizeOfTotalList", count);
		
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		paramMap.put("histories", histories);
		
		return new ModelAndView("detail",paramMap);
	}
	
	@RequestMapping("kpi")
	public ModelAndView kpi(@RequestParam(required = false) String shop_info,
			@RequestParam(required = false) String beginDate,@RequestParam(required = false) String endDate,@RequestParam(required = false) String order,
			@RequestParam(required = false) String currentPageNO,HttpSession session) {
		
		String shopCode=((LoginShop) session.getAttribute("shop")).getShop_code();
		LoginShop shop=(LoginShop) session.getAttribute("shop");
		Integer role=shop.getRole();
		if (role!=null&&role==1) {
			shopCode=shop_info;
		}
		
		// 當前頁
		int curPage = StringUtils.isEmpty(currentPageNO) ? 1 : Integer
				.valueOf(currentPageNO);
				
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		// 每頁的行數
		int rowNum = Constans.PAGE_SIZE;
		Date curdate=new Date();
		int count = userService.getKpiResultsCount(shopCode, DateUtil.getDate(DateUtil.getMondy(curdate)), DateUtil.getDayEnd(curdate));
		// 頁數
		double pageNum = Math.ceil((double) count / rowNum);
		List<KpiResult> kpis=userService.getKpiResults(shopCode, DateUtil.getDate(DateUtil.getMondy(curdate)), DateUtil.getDayEnd(curdate), curPage,order);
		KpiLevel kpiLevel=userService.getKpiLevel();
		
		paramMap.put("pageCount", pageNum);
		paramMap.put("rowNum", rowNum);
		paramMap.put("currentPageNO", curPage);
		paramMap.put("sizeOfTotalList", count);
		
		paramMap.put("shop_info", shop_info);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		paramMap.put("kpis", kpis);
		paramMap.put("kpiLevel", kpiLevel);
		
		paramMap.put("updDate", userService.getUpdDate(shopCode));
		if (role!=null&&role==1) {
			return new ModelAndView("kpi",paramMap);
		}else {
			return new ModelAndView("shopkpi",paramMap);
		}
		
		
	}

	@RequestMapping("exportKpi")
	public void exportRecord(HttpServletResponse response,HttpServletRequest request) throws IOException, InterruptedException, ServletException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		JSONObject jsonObject = new JSONObject();
		
		
		
		Date curdate=new Date();
		List<KpiResult> kpis=userService.getKpiResults(null, DateUtil.getDate(DateUtil.getMondy(curdate)), DateUtil.getDayEnd(curdate), null,null);
		String time=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int a = (int)(Math.random()*(9999-1000+1))+1000;
		String filePath = "D:\\"+"ichido"+"_"+time+"kpi"+a+".xlsx";
		String[] titles = {"门店编号","门店名称","品牌","会员贡献销售额kpi","会员贡献销售额kpi名次","会员成交笔数kpi","会员成交笔数kpi名次","微信买单金额kpi","微信买单金额kpi名次","会员消费记录均衡度kpi","会员消费记录均衡度kpi名次","评分","缺失"};
		
		try {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(0+"");
		XSSFRow row = sheet.createRow(0);
		for(int i=0;i< titles.length;i++){
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(titles[i]);
		}
		
		for (int i = 0; i < kpis.size(); i++) {
			XSSFRow newrow = sheet.createRow(i + 1);
			KpiResult result = kpis.get(i);
			XSSFCell cell1 = newrow.createCell(0);
			cell1.setCellValue(result.getShop_code());
			XSSFCell cell2 = newrow.createCell(1);
			cell2.setCellValue(result.getShop_name());
			XSSFCell cell3 = newrow.createCell(2);
			cell3.setCellValue(result.getShop_brand());
			XSSFCell cell4 = newrow.createCell(3);
			cell4.setCellValue(result.getMember_sales()==null?"":String.valueOf(result.getMember_sales()));
			XSSFCell cell5 = newrow.createCell(4);
			cell5.setCellValue(result.getMs_rk());
			XSSFCell cell6 = newrow.createCell(5);
			cell6.setCellValue(result.getMember_num()==null?"":String.valueOf(result.getMember_num()));
			XSSFCell cell7 = newrow.createCell(6);
			cell7.setCellValue(result.getMn_rk());
			XSSFCell cell8 = newrow.createCell(7);
			cell8.setCellValue(result.getWechat_money()==null?"":String.valueOf(result.getWechat_money()));
			XSSFCell cell9 = newrow.createCell(8);
			cell9.setCellValue(result.getWm_rk());
			XSSFCell cell10 = newrow.createCell(9);
			cell10.setCellValue(result.getFc()==null?"":String.valueOf(result.getFc()));
			XSSFCell cell11 = newrow.createCell(10);
			cell11.setCellValue(result.getFc_rk());
			XSSFCell cell12 = newrow.createCell(11);
			cell12.setCellValue(result.getScore()==null?"":String.valueOf(result.getScore()));
			XSSFCell cell13 = newrow.createCell(12);
			cell13.setCellValue(result.getMissing());
			
		}

		FileOutputStream outputStream = new FileOutputStream(filePath);
        wb.write(outputStream);
        outputStream.close();
        jsonObject.put("msg", filePath);
        //response.getWriter().print(filePath);
		}catch(Exception e){
			e.printStackTrace();
				jsonObject.put("msg", "导出失败！");
				//response.getWriter().print("导出失败！");
		}finally{
			response.getWriter().print(jsonObject.toString());
			response.getWriter().close();
		}
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
	
	@RequestMapping("toUpdPwd")
	public ModelAndView toUpdPwd(){
		return new ModelAndView("pwd2");
	}
	@ResponseBody
	@RequestMapping("updPwd")
	public String updPwd(String newPwd,HttpSession session){
		Integer shop_id=((LoginShop) session.getAttribute("shop")).getShop_id();
		LoginShop shopPwd=new LoginShop(shop_id, newPwd);
		int ret=userService.updPwd(shopPwd);
		if(ret==1){
			return "success";
		}else {
			return "fail";
		}
	}
	
	@RequestMapping("toImport")
	public ModelAndView toImport(){
		return new ModelAndView("import");
	}
	@ResponseBody
	@RequestMapping("uploadKpi")
	public String uploadKpi(MultipartFile kpiFile,HttpServletResponse response,HttpServletRequest request) throws Exception {
		JSONObject result=new JSONObject();
		List<KpiResult> kpis = new ArrayList<KpiResult>();
		Workbook wb = WorkbookFactory.create(kpiFile.getInputStream());
		try{
		if (wb != null) {
			if (wb.getNumberOfSheets()>=1) {
				Sheet sheet = wb.getSheetAt(0);
				int rows = sheet.getPhysicalNumberOfRows();
				if (rows>1) {
					for (int i = 8; i < rows-1; i++) {
						Row row = sheet.getRow(i);
						if (row.getPhysicalNumberOfCells()>=1) {
							row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
							
							String shopCode=row.getCell(0).getStringCellValue();
							if (StringUtils.isEmpty(shopCode)) {
								continue;
							}
							String shopName=row.getCell(1).getStringCellValue();
							String shopBrand=row.getCell(2).getStringCellValue();
							String date=row.getCell(3).getStringCellValue();
							
							String totalSalesString=row.getCell(4).getStringCellValue();
							Float totalSales=StringUtils.isEmpty(totalSalesString)?null:Float.valueOf(totalSalesString);
							
							String totalNumString=row.getCell(5).getStringCellValue();
							Integer totalNum=StringUtils.isEmpty(totalNumString)?null:Integer.valueOf(totalNumString);
							
							String memberSalesString=row.getCell(6).getStringCellValue();
							Float memberSales=StringUtils.isEmpty(memberSalesString)?null:Float.valueOf(memberSalesString);
							
							String memberNumString=row.getCell(7).getStringCellValue();
							Float memberNum=StringUtils.isEmpty(memberNumString)?null:Float.valueOf(memberNumString);
							
							String wechatMoneyString=row.getCell(8).getStringCellValue();
							Float wechatMoney=StringUtils.isEmpty(wechatMoneyString)?null:Float.valueOf(wechatMoneyString);
							
							KpiResult kpi = new KpiResult(shopCode,shopName,shopBrand,date,totalSales,totalNum,memberSales,memberNum,wechatMoney);

							kpis.add(kpi);
						}
						
					}
				}
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			return result.toJSONString();
		}
		
		
		if (kpis.size()>0) {
			int ret=userService.saveKpis(kpis);
			if (ret==1) {
				result.put("status", "success");
				return result.toJSONString();
				
			}
		}
		result.put("status", "error");
		return result.toJSONString();
	}
	
	@ResponseBody
	@RequestMapping("uploadDetail")
	public String uploadDetail(MultipartFile detailFile,HttpServletResponse response,HttpServletRequest request) throws Exception {
		JSONObject result=new JSONObject();
		List<TransactionHistory> histories = new ArrayList<TransactionHistory>();
		Workbook wb = WorkbookFactory.create(detailFile.getInputStream());
		
		try {
		if (wb != null) {
			if (wb.getNumberOfSheets()>=1) {
				Sheet sheet = wb.getSheetAt(0);
				int rows = sheet.getPhysicalNumberOfRows();
				if (rows>1) {
					for (int i = 4; i < rows-1; i++) {
						Row row = sheet.getRow(i);
						if (row.getPhysicalNumberOfCells()>=1) {
							row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
							row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
							row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
							
							String shopCode=row.getCell(0).getStringCellValue();
							if (StringUtils.isEmpty(shopCode)) {
								continue;
							}
							String shopName=row.getCell(1).getStringCellValue();
							String history_date=row.getCell(2).getStringCellValue();
							String history_time=row.getCell(3).getStringCellValue();
							String cashier_code=row.getCell(4).getStringCellValue();
							String member_code=row.getCell(5).getStringCellValue();
							String member_name=EmojiParser.parseToAliases(row.getCell(6).getStringCellValue());
							Float transaction_amount=(float) row.getCell(7).getNumericCellValue();
							Float discount_amount=(float) row.getCell(8).getNumericCellValue();
							Integer daily_num=(int) row.getCell(9).getNumericCellValue();
							Float daily_transactions=(float) row.getCell(10).getNumericCellValue();
							TransactionHistory history = new TransactionHistory(shopCode, shopName, history_date, history_time, cashier_code, member_code, member_name, transaction_amount, discount_amount, daily_num, daily_transactions);

							histories.add(history);
							
						}
					}
				}
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			return result.toJSONString();
		}
		
		if (histories.size()>0) {
			int ret=userService.saveDetail(histories);
			if (ret==1) {
				result.put("status", "success");
				return result.toJSONString();
			}
		}
		
		result.put("status", "error");
		return result.toJSONString();
	}
public static void main(String[] args) throws UnsupportedEncodingException {
	System.out.println(EmojiParser.parseToAliases("Eva🌴"));
}

}
