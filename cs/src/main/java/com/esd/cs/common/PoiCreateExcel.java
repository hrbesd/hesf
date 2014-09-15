/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import com.esd.cs.statistics.ReportModel;
import com.esd.hesf.model.Audit;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Payment;
import com.esd.hesf.model.Worker;
import com.esd.hesf.model.WorkerTemp;
import com.esd.hesf.viewmodels.ReportViewModel;

public class PoiCreateExcel {

	/**
	 * 创建导入残疾职工错误列表
	 * 
	 * @param FilePath
	 * @param workerList
	 * @return 导入成功返回true, 否则返回false
	 */
	public static boolean createExcel(String FilePath,
			List<WorkerTemp> workerList) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 设置excel每列宽度
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);

		// 创建一个头部Excel的单元格
		HSSFRow headRow = sheet.createRow(0);
		HSSFCell headell = headRow.createCell(0);
		// 设置单元格的样式格式
		headell = headRow.createCell(0);
		headell.setCellValue("姓名");
		headell = headRow.createCell(1);
		headell.setCellValue("残疾证号");
		sheet.setColumnWidth(1, 8000);
		headell = headRow.createCell(2);
		headell.setCellValue("失败原因");
		sheet.setColumnWidth(2, 13000);

		for (int i = 1; i <= workerList.size(); i++) {
			WorkerTemp worker = workerList.get(i - 1);
			// 创建一个Excel的单元格
			HSSFRow row = sheet.createRow(i);
			HSSFCell cell = row.createCell(0);
			// 设置单元格的样式格式
			cell = row.createCell(0);
			cell.setCellValue(worker.getWorkerName());
			cell = row.createCell(1);
			cell.setCellValue(worker.getWorkerHandicapCode());
			cell = row.createCell(2);
			cell.setCellValue(worker.getRemark());
		}
		try {
			FileOutputStream os = new FileOutputStream(FilePath);
			wb.write(os);
			os.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 导出单位信息
	 * 
	 * @param FilePath
	 * @param list
	 * @return
	 */
	public static boolean createCompanyExcel(String FilePath,
			List<Company> list) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 设置excel每列宽度
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);

		// 创建一个头部Excel的单元格
		HSSFRow headRow = sheet.createRow(0);
		HSSFCell headell = headRow.createCell(0);
		// 设置单元格的样式格式
		headell = headRow.createCell(0);
		headell.setCellValue("档案编码");
		headell = headRow.createCell(1);
		headell.setCellValue("税务编码");
		headell = headRow.createCell(2);
		headell.setCellValue("单位名称");
		sheet.setColumnWidth(2, 12000); // 设置第二列的宽度

		headell = headRow.createCell(3);
		headell.setCellValue("法人代表");
		headell = headRow.createCell(4);
		headell.setCellValue("联系人");

		headell = headRow.createCell(5);
		headell.setCellValue("电话号码");

		headell = headRow.createCell(6);
		headell.setCellValue("手机号码");

		headell = headRow.createCell(7);
		headell.setCellValue("邮编");
		headell = headRow.createCell(8);
		headell.setCellValue("单位地址");
		sheet.setColumnWidth(8, 12000);

		for (int i = 1; i <= list.size(); i++) {
			Company company = list.get(i - 1);
			// 创建一个Excel的单元格
			HSSFRow row = sheet.createRow(i);
			HSSFCell cell = row.createCell(0);
			// 设置单元格的样式格式
			// 档案编码
			cell = row.createCell(0);
			cell.setCellValue(company.getCompanyCode());
			// 税务编码
			cell = row.createCell(1);
			cell.setCellValue(company.getCompanyTaxCode());
			// 企业名称
			cell = row.createCell(2);
			cell.setCellValue(company.getCompanyName());
			// 法人代表
			cell = row.createCell(3);
			cell.setCellValue(company.getCompanyLegal());
			// 联系人
			cell = row.createCell(4);
			cell.setCellValue(company.getCompanyContactPerson());
			// 电话号码
			cell = row.createCell(5);
			cell.setCellValue(company.getCompanyPhone());
			// 手机号码
			cell = row.createCell(6);
			cell.setCellValue(company.getCompanyMobile());
			// 邮编
			cell = row.createCell(7);
			cell.setCellValue(company.getCompanyZipCode());
			// 单位地址
			cell = row.createCell(8);
			cell.setCellValue(company.getCompanyAddress());
		}
		try {
			FileOutputStream os = new FileOutputStream(FilePath);
			wb.write(os);
			os.flush();
			os.close();
			list.clear();
			list = null;
			os = null;
			wb = null;
			System.gc();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 导出审核信息
	 * @param FilePath
	 * @param list
	 * @return
	 */
	public static boolean createAuditExcel(String FilePath,
			List<Audit> list) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 设置excel每列宽度
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);

		// 创建一行头部Excel的单元格
		HSSFRow headRow = sheet.createRow(0);
		// 向第一行中的每一个列单元格填入内容
		HSSFCell rowCell = headRow.createCell(0);
	//	sheet.setColumnWidth(0, 2400);	//设置该列的宽度
		rowCell.setCellValue("审核年份");
		
		rowCell = headRow.createCell(1);
		rowCell.setCellValue("档案编码");

		rowCell = headRow.createCell(2);
		rowCell.setCellValue("单位名称");
		sheet.setColumnWidth(2, 12000); 
		
		rowCell = headRow.createCell(3);
		rowCell.setCellValue("联系人");
		
		rowCell = headRow.createCell(4);
		rowCell.setCellValue("联系电话");
		sheet.setColumnWidth(4, 3600);
		
		rowCell = headRow.createCell(5);
		rowCell.setCellValue("单位地址");
		sheet.setColumnWidth(5, 12000);
		
		rowCell = headRow.createCell(6);
		rowCell.setCellValue("员工总人数");
		sheet.setColumnWidth(6, 3000);

		rowCell = headRow.createCell(7);
		rowCell.setCellValue("残疾员工人数");
		sheet.setColumnWidth(7, 3300);
		
		rowCell = headRow.createCell(8);
		rowCell.setCellValue("应安排残疾人数");
		sheet.setColumnWidth(8, 4000);
		
		rowCell = headRow.createCell(9);
		rowCell.setCellValue("已安排残疾人数");
		sheet.setColumnWidth(9, 4000);
		
		rowCell = headRow.createCell(10);
		rowCell.setCellValue("应缴金额");
		sheet.setColumnWidth(10, 3000);
		
		rowCell = headRow.createCell(11);
		rowCell.setCellValue("减缴金额");
		sheet.setColumnWidth(11, 3000);
		
		rowCell = headRow.createCell(12);
		rowCell.setCellValue("补缴金额");
		sheet.setColumnWidth(12, 3000);
		
		rowCell = headRow.createCell(13);
		rowCell.setCellValue("滞纳金");
		sheet.setColumnWidth(13, 3000);
		
		rowCell = headRow.createCell(14);
		rowCell.setCellValue("实际应缴金额");
		sheet.setColumnWidth(14, 3300);
		
		rowCell = headRow.createCell(15);
		rowCell.setCellValue("是否减免滞纳金");
		sheet.setColumnWidth(15, 4000);
		
//		rowCell = headRow.createCell(16);
//		rowCell.setCellValue("初审人");
//		sheet.setColumnWidth(16, 2000);
//		
//		rowCell = headRow.createCell(17);
//		rowCell.setCellValue("初审时间");
//		sheet.setColumnWidth(17, 2738);
//		
//		rowCell = headRow.createCell(18);
//		rowCell.setCellValue("复审人");
//		sheet.setColumnWidth(18, 2000);
//		
//		rowCell = headRow.createCell(19);
//		rowCell.setCellValue("复审时间");
//		sheet.setColumnWidth(19, 2738);
		
		rowCell = headRow.createCell(20);
		rowCell.setCellValue("审核状态");
		sheet.setColumnWidth(20, 2738);
		
		rowCell = headRow.createCell(21);
		rowCell.setCellValue("备注");
		
		for (int i = 1; i <= list.size(); i++) {
			Audit audit = list.get(i-1);
			Company company = audit.getCompany();
			// 创建一个Excel的单元格
			HSSFRow loopRow = sheet.createRow(i);
			HSSFCell loopCell  = loopRow.createCell(0);
			loopCell.setCellValue(audit.getYear());
			
			loopCell = loopRow.createCell(1);
			loopCell.setCellValue(company.getCompanyCode());

			loopCell = loopRow.createCell(2);
			loopCell.setCellValue(company.getCompanyName());
			
			loopCell = loopRow.createCell(3);
			loopCell.setCellValue(company.getCompanyContactPerson());
			
			loopCell = loopRow.createCell(4);
			loopCell.setCellValue(company.getCompanyMobile());
			
			loopCell = loopRow.createCell(5);
			loopCell.setCellValue(company.getCompanyAddress());
			
			loopCell = loopRow.createCell(6);
			loopCell.setCellValue(audit.getCompanyEmpTotal());

			loopCell = loopRow.createCell(7);
			loopCell.setCellValue(audit.getCompanyHandicapTotal());
			
			loopCell = loopRow.createCell(8);
			loopCell.setCellValue(audit.getCompanyShouldTotal().setScale(2, BigDecimal.ROUND_CEILING).toString());
			
			loopCell = loopRow.createCell(9);
			loopCell.setCellValue(audit.getCompanyAlreadyTotal());
			
			loopCell = loopRow.createCell(10);
			loopCell.setCellValue(audit.getAmountPayable().setScale(2, BigDecimal.ROUND_CEILING).toString());
			
			loopCell = loopRow.createCell(11);
			loopCell.setCellValue(audit.getReductionAmount().setScale(2, BigDecimal.ROUND_CEILING).toString());
			
			loopCell = loopRow.createCell(12);
			loopCell.setCellValue(audit.getComplementAmount().setScale(2, BigDecimal.ROUND_CEILING).toString());
			
			loopCell = loopRow.createCell(13);
			loopCell.setCellValue(audit.getDelayPayAmount().setScale(2, BigDecimal.ROUND_CEILING).toString());
			
			loopCell = loopRow.createCell(14);
			loopCell.setCellValue(audit.getPayAmount().setScale(2, BigDecimal.ROUND_CEILING).toString());
			
			loopCell = loopRow.createCell(15);
			if(audit.getIsDelayPay()){
				loopCell.setCellValue("是");
			}else{
				loopCell.setCellValue("否");
			}
			
//			loopCell = loopRow.createCell(16);
//			loopCell.setCellValue(audit.getInitAuditUser().getUserRealName());
//			
//			loopCell = loopRow.createCell(17);
//			loopCell.setCellValue(audit.getInitAuditDate());
//			
//			loopCell = loopRow.createCell(18);
//			loopCell.setCellValue(audit.getVerifyAuditUser().getUserRealName());
//			
//			loopCell = loopRow.createCell(19);
//			loopCell.setCellValue(audit.getVerifyAuditDate());
			
			loopCell = loopRow.createCell(20);
			loopCell.setCellValue(audit.getAuditProcessStatus().getAuditProcessStatus());
			
			loopCell = loopRow.createCell(21);
			loopCell.setCellValue(audit.getRemark());
		}
		try {
			FileOutputStream os = new FileOutputStream(FilePath);
			wb.write(os);
			os.flush();
			os.close();
			list.clear();
			list = null;
			os = null;
			wb = null;
			System.gc();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 导出残疾人信息
	 * 
	 * @param FilePath
	 * @param companyList
	 * @return
	 */
	public static boolean createWorkerExcel(String FilePath, List<Worker> list) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 设置excel每列宽度
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);

		// 创建一个头部Excel的单元格
		HSSFRow headRow = sheet.createRow(0);
		HSSFCell headell = headRow.createCell(0);
		// 设置单元格的样式格式
		headell = headRow.createCell(0);
		headell.setCellValue("姓名");
		headell = headRow.createCell(1);
		headell.setCellValue("性别");
		headell = headRow.createCell(2);
		headell.setCellValue("出生日期");
		// sheet.setColumnWidth(2, 12000); // 设置第二列的宽度

		headell = headRow.createCell(3);
		headell.setCellValue("身份证号");
		headell = headRow.createCell(4);
		headell.setCellValue("联系电话");

		headell = headRow.createCell(5);
		headell.setCellValue("联系地址");

		headell = headRow.createCell(6);
		headell.setCellValue("残疾证号");

		headell = headRow.createCell(7);
		headell.setCellValue("残疾类别");
		headell = headRow.createCell(8);
		headell.setCellValue("残疾等级");

		for (int i = 1; i <= list.size(); i++) {
			Worker t = list.get(i - 1);
			// 创建一个Excel的单元格
			HSSFRow row = sheet.createRow(i);
			HSSFCell cell = row.createCell(0);
			// 设置单元格的样式格式
			// 姓名
			cell = row.createCell(0);
			cell.setCellValue(t.getWorkerName());
			// 性别
			cell = row.createCell(1);
			if ("0".equals(t.getWorkerGender())) {
				cell.setCellValue("女");
			} else {
				cell.setCellValue("男");
			}
			// 出生日期
			cell = row.createCell(2);
			cell.setCellValue(t.getWorkerBirth());
			// 身份证
			cell = row.createCell(3);
			cell.setCellValue(t.getWorkerIdCard());
			// 电话
			cell = row.createCell(4);
			cell.setCellValue(t.getPhone());
			// 联系地址
			cell = row.createCell(5);
			cell.setCellValue(t.getWorkerAddress());
			// 残疾证号
			cell = row.createCell(6);
			cell.setCellValue(t.getWorkerHandicapCode());
			// 残疾类别
			cell = row.createCell(7);
			cell.setCellValue(t.getWorkerHandicapType().getHandicapType());
			// 残疾等级
			cell = row.createCell(8);
			cell.setCellValue(t.getWorkerHandicapType().getHandicapType());
		}
		try {
			FileOutputStream os = new FileOutputStream(FilePath);
			wb.write(os);
			os.flush();
			os.close();
			list.clear();
			list = null;
			os = null;
			wb = null;
			System.gc();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 导出付款记录
	 * @param FilePath
	 * @param list
	 * @return
	 */
	public static boolean createPaymentExcel(String FilePath,
			List<Payment> list) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 设置excel每列宽度
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);

		// 创建一个头部Excel的单元格
		HSSFRow headRow = sheet.createRow(0);
		HSSFCell headell = headRow.createCell(0);
		// 设置单元格的样式格式
		headell = headRow.createCell(0);
		headell.setCellValue("档案编码");
		headell = headRow.createCell(1);
		headell.setCellValue("税务编码");
		headell = headRow.createCell(2);
		headell.setCellValue("单位名称");
		sheet.setColumnWidth(2, 12000); // 设置第二列的宽度

		headell = headRow.createCell(3);
		headell.setCellValue("法人代表");
		headell = headRow.createCell(4);
		headell.setCellValue("联系人");

		headell = headRow.createCell(5);
		headell.setCellValue("电话号码");

		headell = headRow.createCell(6);
		headell.setCellValue("手机号码");

		headell = headRow.createCell(7);
		headell.setCellValue("邮编");
		headell = headRow.createCell(8);
		headell.setCellValue("单位地址");
		sheet.setColumnWidth(8, 12000);

		for (int i = 1; i <= list.size(); i++) {
			Payment payment = list.get(i-1);
			Company company = payment.getPaymentCompany();
			// 创建一个Excel的单元格
			HSSFRow row = sheet.createRow(i);
			HSSFCell cell = row.createCell(0);
			// 设置单元格的样式格式
			// 档案编码
			cell = row.createCell(0);
			cell.setCellValue(company.getCompanyCode());
			// 税务编码
			cell = row.createCell(1);
			cell.setCellValue(company.getCompanyTaxCode());
			// 企业名称
			cell = row.createCell(2);
			cell.setCellValue(company.getCompanyName());
			// 法人代表
			cell = row.createCell(3);
			cell.setCellValue(company.getCompanyLegal());
			// 联系人
			cell = row.createCell(4);
			cell.setCellValue(company.getCompanyContactPerson());
			// 电话号码
			cell = row.createCell(5);
			cell.setCellValue(company.getCompanyPhone());
			// 手机号码
			cell = row.createCell(6);
			cell.setCellValue(company.getCompanyMobile());
			// 邮编
			cell = row.createCell(7);
			cell.setCellValue(company.getCompanyZipCode());
			// 单位地址
			cell = row.createCell(8);
			cell.setCellValue(company.getCompanyAddress());
		}
		try {
			FileOutputStream os = new FileOutputStream(FilePath);
			wb.write(os);
			os.flush();
			os.close();
			list.clear();
			list = null;
			os = null;
			wb = null;
			System.gc();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	/**
	 * 导出报表
	 * 
	 * @param FilePath
	 * @param companyList
	 * @return
	 */
	public static boolean createRepeaExcel(String FilePath,
			List<ReportViewModel> companyList, ReportModel model) {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 设置excel每列宽度
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);

		// 创建头部Excel的单元格
		HSSFRow headRow0 = sheet.createRow(0);
		HSSFCell headCell = headRow0.createCell(0);
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 13));// 四个参数分别是：起始行，起始列，结束行，结束列
		headCell = headRow0.createCell(0);
		// 设置文本
		headCell.setCellValue(model.getTitle());
		// 设置样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setFillBackgroundColor(HSSFColor.GREEN.index);
		style.setAlignment(CellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
		// 设置字体
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体增粗
		style.setFont(font);
		headCell.setCellStyle(style);

		// 单元格 制表单位
		HSSFRow RowTow = sheet.createRow(1);
		HSSFCell CellTow = headRow0.createCell(1);
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));// 四个参数分别是：起始行，起始列，结束行，结束列
		CellTow = RowTow.createCell(0);
		// 设置文本
		CellTow.setCellValue(model.getCreateCompany());

		// 单元格 制表日期
		// 合并单元格
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 6, 13));// 四个参数分别是：起始行，起始列，结束行，结束列
		CellTow = RowTow.createCell(6);
		HSSFCellStyle style1 = wb.createCellStyle();
		style1.setFillBackgroundColor(HSSFColor.GREEN.index);
		style1.setAlignment(CellStyle.ALIGN_RIGHT);// 水平居右
		CellTow.setCellStyle(style1);
		// 设置文本
		CellTow.setCellValue(model.getCreateData());

		// 数据主体部分
		HSSFRow headRow = sheet.createRow(2);
		HSSFCell headell = headRow.createCell(2);
		// 设置单元格的样式格式
		headell = headRow.createCell(0);
		headell.setCellValue(model.getType());

		headell = headRow.createCell(1);
		headell.setCellValue("单位总数");

		headell = headRow.createCell(2);
		headell.setCellValue("单位总人数");
		sheet.setColumnWidth(2, 3000); // 设置宽度

		headell = headRow.createCell(3);
		headell.setCellValue("待初单位数");
		sheet.setColumnWidth(3, 3000); // 设置宽度

		headell = headRow.createCell(4);
		headell.setCellValue("待复审单位数");
		sheet.setColumnWidth(4, 4000); // 设置宽度

		headell = headRow.createCell(5);
		headell.setCellValue("已复核达标单位数");
		sheet.setColumnWidth(5, 4000); // 设置宽度

		headell = headRow.createCell(6);
		headell.setCellValue("已复核未达标单位数");
		sheet.setColumnWidth(6, 4500); // 设置宽度

		headell = headRow.createCell(7);
		headell.setCellValue("应安排人数");
		sheet.setColumnWidth(8, 4000);

		headell = headRow.createCell(8);
		headell.setCellValue("已安排人数");
		sheet.setColumnWidth(8, 4000);

		headell = headRow.createCell(9);
		headell.setCellValue("少安排人数");
		sheet.setColumnWidth(9, 4000);

		headell = headRow.createCell(10);
		headell.setCellValue("应缴金额");
		sheet.setColumnWidth(10, 4000);

		headell = headRow.createCell(11);
		headell.setCellValue("减免金额");

		headell = headRow.createCell(12);
		headell.setCellValue("实缴金额");

		headell = headRow.createCell(13);
		headell.setCellValue("已缴金额");

		for (int i = 0; i < companyList.size(); i++) {
			ReportViewModel company = companyList.get(i);
			// 创建一个Excel的单元格
			HSSFRow row = sheet.createRow(i + 3);
			HSSFCell cell = row.createCell(i + 3);
			// 设置单元格的样式格式
			// 报表依据类型名
			cell = row.createCell(0);
			cell.setCellValue(company.getReportName());
			// 单位总数
			cell = row.createCell(1);
			cell.setCellValue(company.getUnitNum());
			// 单位总人数
			cell = row.createCell(2);
			cell.setCellValue(company.getEmpTotal());

			// 待初审单位数
			cell = row.createCell(3);
			cell.setCellValue(company.getUnAudit());

			// 已初审, 待复核单位数
			cell = row.createCell(4);
			cell.setCellValue(company.getUnReAudit());

			// 已复核, 达标单位数
			cell = row.createCell(5);
			cell.setCellValue(company.getAuditOk());

			// 已复核, 未达标单位数
			cell = row.createCell(6);
			cell.setCellValue(company.getUnauditOk());

			// 应安排总人数
			cell = row.createCell(7);
			cell.setCellValue(company.getShouldTotal().toString());

			// 已经安排人数
			cell = row.createCell(8);
			cell.setCellValue(company.getAlreadyTotal().toString());

			// 少安排人数
			cell = row.createCell(9);
			cell.setCellValue(company.getLessTotal().toString());
			// 应缴金额
			cell = row.createCell(10);
			cell.setCellValue(company.getAmountPayable().toString());
			// 减免金额
			cell = row.createCell(11);
			cell.setCellValue(company.getReductionAmount().toString());
			// 实际应缴金额
			cell = row.createCell(12);
			cell.setCellValue(company.getActualAmount().toString());
			// 已缴金额
			cell = row.createCell(13);
			cell.setCellValue(company.getAlreadyAmount().toString());
		}

		// 单元格 尾部
		HSSFRow row = sheet.createRow(companyList.size() + 3);
		HSSFCell cell = row.createCell(companyList.size() + 3);
		// 设置单元格的样式格式
		// 报表依据类型名
		sheet.addMergedRegion(new CellRangeAddress(companyList.size() + 3,
				companyList.size() + 3, 0, 13));// 四个参数分别是：起始行，起始列，结束行，结束列
		cell = row.createCell(0);
		// 设置样式
		HSSFCellStyle styleFoot = wb.createCellStyle();
		styleFoot.setAlignment(CellStyle.ALIGN_RIGHT);// 水平居右
		cell.setCellStyle(styleFoot);
		// 设置文本
		cell.setCellValue(model.getCreatePeople());

		try {
			FileOutputStream os = new FileOutputStream(FilePath);
			wb.write(os);
			os.flush();
			os.close();
			companyList.clear();
			companyList = null;
			os = null;
			wb = null;
			System.gc();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
