package com.esd.common.util;

import java.util.Calendar;

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
}
