package com.esd.common.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {

	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static String getNowYear() {
		Calendar cal = Calendar.getInstance();// 使用日历类
		int year = cal.get(Calendar.YEAR);// 得到年
		return String.valueOf(year);
	}

	public static String getLastYear() {
		Calendar cal = Calendar.getInstance();// 使用日历类
		int year = cal.get(Calendar.YEAR - 1);// 得到年
		return String.valueOf(year);
	}

	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
			// System.out.println("相隔的天数="+day);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return day;
	}

	public static int getDaySub(Date beginDate, Date endDate) {
		Long day = 0L;
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		return day.intValue();
	}
}
