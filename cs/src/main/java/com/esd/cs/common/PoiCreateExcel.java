package com.esd.cs.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.esd.hesf.model.Company;
import com.esd.hesf.model.Worker;

public class PoiCreateExcel {

	/**
	 * 创建导入残疾职工错误列表
	 * 
	 * @param FilePath
	 * @param workerList
	 * @return
	 */
	public static boolean createExcel(String FilePath, List<Worker> workerList) {
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
			Worker worker = workerList.get(i - 1);
			// 创建一个Excel的单元格
			HSSFRow row = sheet.createRow(i);
			HSSFCell cell = row.createCell(i);
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 导出单位信息
	 * @param FilePath
	 * @param companyList
	 * @return
	 */
	public static boolean createComapnyExcel(String FilePath, List<Company> companyList) {
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

		for (int i = 1; i <=companyList.size(); i++) {
			Company company=companyList.get(i-1);
			// 创建一个Excel的单元格
			HSSFRow row = sheet.createRow(i);
			HSSFCell cell = row.createCell(i);
			// 设置单元格的样式格式
			
			//档案编码
			cell = row.createCell(0);
			cell.setCellValue(company.getCompanyCode());
			//税务编码
			cell = row.createCell(1);
			cell.setCellValue(company.getCompanyTaxCode());
			//企业名称
			cell = row.createCell(2);
			cell.setCellValue(company.getCompanyName());
			//法人代表
			cell = row.createCell(3);
			cell.setCellValue(company.getCompanyLegal());
			cell = row.createCell(4);
			//联系人
			cell.setCellValue(company.getCompanyContactPerson());
			cell = row.createCell(5);
			//电话号码
			cell.setCellValue(company.getCompanyPhone());
			cell = row.createCell(6);
			
			//手机号码
			cell.setCellValue(company.getCompanyMobile());
			cell = row.createCell(7);
			
			//邮编
			cell.setCellValue(company.getCompanyZipCode());
			cell = row.createCell(8);
			//单位地址
			cell.setCellValue(company.getCompanyAddress());
			cell = row.createCell(9);
		}
		try {
			FileOutputStream os = new FileOutputStream(FilePath);
			wb.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
