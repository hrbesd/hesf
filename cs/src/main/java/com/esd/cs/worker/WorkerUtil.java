/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.worker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esd.common.util.CalendarUtil;
import com.esd.cs.common.HExcelSheetParser;
import com.esd.cs.common.XExcelSheetParser;
import com.esd.hesf.model.AuditParameter;
import com.esd.hesf.model.Worker;
import com.esd.hesf.model.WorkerHandicapLevel;
import com.esd.hesf.model.WorkerHandicapType;

public class WorkerUtil {
	public static Integer DnISABILITYCARDLENGTH = 20;// 身份证位数
	private static final Logger logger = LoggerFactory.getLogger(WorkerUtil.class);

	// 返回职工当前年龄--不是去年年龄
	public static Integer conversionAge(String param) {
		if (param != null) {
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
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static Object hasParser(File file) throws FileNotFoundException, IOException {
		try {
			// 获取工作薄workbook
			hWorkbook = new HSSFWorkbook(new FileInputStream(file));
			logger.info("excel 97-2003");
			return hWorkbook;
		} catch (Exception e) {
			xWorkbook = new XSSFWorkbook(new FileInputStream(file));
			logger.info("excel 2007-2010");
			return xWorkbook;

		}
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
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static List<Worker> parse(File file, int sheetNumber) throws FileNotFoundException, IOException {
		List<Worker> list = null;
		// 判断excel版本
		Object obj = hasParser(file);
		if (obj == null) {
			return list;
		}
		list = new ArrayList<Worker>();

		if (obj instanceof HSSFWorkbook) {// 97-2003
			logger.debug("excelFileType:{}", "97-2003");

			HExcelSheetParser parser = new HExcelSheetParser(file);
			List<List<Object>> datas = parser.getDatasInSheet(sheetNumber);
			for (int i = 1; i < datas.size(); i++) {
				List<Object> row = datas.get(i);
				list.add(parseRow(row));
			}
		} else if (obj instanceof XSSFWorkbook) { // 2003-2010
			logger.debug("excelFileType:{}", " 2003-2010");
			XExcelSheetParser parser = new XExcelSheetParser(file);
			List<List<Object>> datas = parser.getDatasInSheet(sheetNumber);
			for (int i = 1; i < datas.size(); i++) {
				List<Object> row = datas.get(i);
				list.add(parseRow(row));
			}
		}

		return list;
	}

	/**
	 * 根据残疾证号 组装职工对象
	 */
	public static Worker assembly(Worker w) {
		Worker worker = new Worker();
		// 残疾证号
		String handicapCode = w.getWorkerHandicapCode();
		worker.setWorkerName(w.getWorkerName());
		worker.setWorkerHandicapCode(handicapCode);
		// 身份证号
		worker.setWorkerIdCard(handicapCode.substring(0, 18));

		int age = Integer.valueOf(handicapCode.substring(16, 17));
		// 性别
		if (age % 2 == 0) {
			// 女性
			worker.setWorkerGender(0 + "");
		} else {
			// 男性
			worker.setWorkerGender(1 + "");
		}
		// 残疾类别
		int handicapType = Integer.valueOf(handicapCode.substring(18, 19));
		worker.setWorkerHandicapType(new WorkerHandicapType(handicapType));

		// 残疾等级
		int handicapLeve = Integer.valueOf(handicapCode.substring(19, 20));
		worker.setWorkerHandicapLevel(new WorkerHandicapLevel(handicapLeve));

		// 出生日期
		String year = handicapCode.substring(6, 10);// 年份
		String month = handicapCode.substring(10, 12);// 月
		String day = handicapCode.substring(12, 14);// 日

		worker.setWorkerBirth(year + "-" + month + "-" + day);
		// 出生年份--供后台查询使用
		worker.setWorkerBirthYear(year);
		logger.debug("assemblyWorker:{}", worker);

		return worker;

	}

	/**
	 * 年龄检测
	 * 
	 * @param workerHandicapCode
	 * @param param
	 * @return
	 */
	public List<String> ageVerifi(String workerHandicapCode, AuditParameter param) {
		int sex = Integer.valueOf(workerHandicapCode.substring(16, 17));
		List<String> result = new ArrayList<String>();

		if (param != null) {
			//计算去年年龄
			int age = conversionAge(workerHandicapCode)-1;
			// 性别
			if (sex % 2 == 0) {
				result.add("女性");
				result.add(age + "");
				// 女性 退休校验
				if (age >= param.getRetireAgeFemale()) {
					result.add("已超过退休年龄");
					return result;
				}
				// 是否达到工作年龄校验
				if (age <= 16) {
					result.add("年龄未达到工作年龄");
					return result;
				}
			} else {

				result.add("男性");
				result.add(age + "");
				// 男性
				if (age >= param.getRetireAgeMale()) {
					result.add("已超过退休年龄");
					return result;
				}
				// 是否达到工作年龄校验
				if (age <= 16) {
					result.add("年龄未达到工作年龄");
					return result;
				}
			}
		} else {
			logger.error("getLastYearERROR:{}");
		}
		return null;
	}
}
