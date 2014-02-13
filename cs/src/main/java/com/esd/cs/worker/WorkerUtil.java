package com.esd.cs.worker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esd.common.util.CalendarUtil;
import com.esd.hesf.model.Worker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkerUtil {
	public static Integer DISABILITYCARDLENGTH = 20;// 身份证位数
	private static final Logger logger = LoggerFactory.getLogger(WorkerUtil.class);

	public static Integer conversionAge(String param) {
		if (param != null) {
			if (!(param.length() < DISABILITYCARDLENGTH)) {
				try {
					// 初审日期
					Integer age = Integer.valueOf(param.substring(6, 10));
					// 当前年份
					Integer nowYear = Integer.valueOf(CalendarUtil.getNowYear());
					return (nowYear - age) + 1;
				} catch (Exception e) {
					logger.error("conversionAge:{}", e.getMessage());
				}
			}
		}
		logger.error("conversionAge:{}", -1);
		return -1;
	}
	
	


    private static HSSFWorkbook hWorkbook;// excel 2003之前的版本
    private static XSSFWorkbook xWorkbook;// excel 2007-2012

    /**
     * 检测excel版本:解析为两个版本2003以前用HSSFWorkbook,2007开始用XSSFWorkbook.
     * 银行：银行一般都是用2003以前的版本解析也就是HSSFWorkbook,在上传银行批处理时尽量用office2003 否则可能会失败
     * 
     * @param file
     * @return
     */
    public static Object hasParser(File file) {
        try {
            // 获取工作薄workbook
            hWorkbook = new HSSFWorkbook(new FileInputStream(file));
            logger.info("excel 97-2003");
            return hWorkbook;
        } catch (Exception e) {
            try {
                xWorkbook = new XSSFWorkbook(new FileInputStream(file));
                logger.info("excel 2007-2010");
                return xWorkbook;
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析成实体
     * 
     * @param row
     * @return
     */
    public static Worker parseRow(List<Object> row) {
        Worker callBack = new Worker();
        for (int j = 0; j < row.size(); j++) {
            Object obj = row.get(j);
            String value = String.valueOf(obj).trim();
            switch (j) {
                case 0:
                    // 姓名
                    callBack.setWorkerName(value);
                    break;

                case 1:
                	 // 残疾证号
                    callBack.setWorkerHandicapCode(value);
                    break;
//                case 2:
//                    // 收款账户
//                    callBack.setAccount(value);
//                    break;
//                case 3:
//                    // 收款名称
//                    callBack.setAccountName(value);
//                    break;
//                case 4:
//                    // 金额
//                    callBack.setAmount(new BigDecimal(value));
//                    break;
//                case 5:
//                    // 状态
//                    callBack.setStatus(value);
//                    break;
//                case 6:
//                    // 原因
//                    callBack.setReason(value);
//                    break;

            }
            logger.debug(callBack.toString());
        }
        return callBack;
    }

    /**
     * 解析成集合
     * 
     * @param file
     * @return
     */
    public static List<Worker> parse(File file, int sheetNumber) {
        List<Worker> list = null;
        // 判断excel版本
        Object obj = hasParser(file);
        if (obj == null) {
            return list;
        }
        list = new ArrayList<Worker>();

        if (obj instanceof HSSFWorkbook) {// 97-2003
            HExcelSheetParser parser = new HExcelSheetParser(file);
            List<List<Object>> datas = parser.getDatasInSheet(sheetNumber);
            for (int i = 1; i < datas.size(); i++) {
                List<Object> row = datas.get(i);
                list.add(parseRow(row));
            }
        } else if (obj instanceof XSSFWorkbook) { // 2003-2010
            XExcelSheetParser parser = new XExcelSheetParser(file);
            List<List<Object>> datas = parser.getDatasInSheet(sheetNumber);
            for (int i = 1; i < datas.size(); i++) {
                List<Object> row = datas.get(i);
                list.add(parseRow(row));
            }
        }

        return list;
    }
}
