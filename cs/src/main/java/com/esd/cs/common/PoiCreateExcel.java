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

import com.esd.hesf.model.Worker;

public class PoiCreateExcel {

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
		headell = headRow.createCell(2);
		headell.setCellValue("失败原因");
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

}
