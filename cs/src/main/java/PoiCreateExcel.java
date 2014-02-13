
import java.io.FileOutputStream;
import java.util.Date;

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

public class PoiCreateExcel {

	public static void main(String[] args) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("sheet1");
		// 设置excel每列宽度
		sheet.setColumnWidth(0, 4000);
		sheet.setColumnWidth(1, 3500);

		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);
//		 row.setHeight((short) 500);// 设定行的高度
		// 创建一个Excel的单元格
		HSSFCell cell = row.createCell(0);

		// 设置单元格的样式格式
		cell = row.createCell(0);
		cell.setCellValue(0000);
		cell = row.createCell(1);
		cell.setCellValue("百度");
		
		
		
		// 创建一个Excel的单元格
		HSSFRow row11= sheet.createRow(1);
		HSSFCell h = row11.createCell(1);
		// 设置单元格的样式格式
		h = row11.createCell(0);
		h.setCellValue(0000);
		h = row11.createCell(1);
		h.setCellValue("百度");
		
		
		

		FileOutputStream os = new FileOutputStream("D:\\workbook.xls");
		wb.write(os);
		os.close();
	}

}
