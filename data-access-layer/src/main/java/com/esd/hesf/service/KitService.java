package com.esd.hesf.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 工具类--提供常用的方法
 * 
 * @author Administrator
 * 
 */
public class KitService {

	/**
	 * 获得一个UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		char[] ch = uuid.toString().toCharArray();
		String s = "";
		for (char c : ch) {
			if (c == '-') {
				continue;
			}
			s += c;
		}
		return s;
	}

	/**
	 * 处理传进来的地区code, 变成适用于sql语句使用的格式
	 * 
	 * @param code
	 * @return
	 */
	public static String areaCodeForSql(String code) {
		if (code == null || "".equals(code)) {
			return null;
		}
		String start = code.substring(0, 2);
		String mid;
		if ("10".equals(start)) {
			mid = code.substring(2, 4);
			code = "20" + mid + "____";
		}
		return code;
	}

	/**
	 * 将年龄转化为出生年字符串
	 * 
	 * @param age
	 * @return
	 */
	public static String getBirthFromAge(String age) {
		// 非空判断
		if (age == null || "".equals(age)) {
			return null;
		}
		int iAge = Integer.parseInt(age);
		// 获得今天日期
		Date today = new Date();
		// 格式或为标准格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayStr = sdf.format(today);
		// 获得出生年  =  当前年 - 年龄
		String birthYear = String.valueOf((Integer.parseInt(todayStr.substring(0, 4)) - iAge)+1);
		// 后面的 月日时分秒信息
//		String birthLast = todayStr.substring(4);
		// 年和月日等信息拼接起来
//		String birthStr = birthYear + birthLast;
//		// 再将其格式化为Date类型
//		Date birth = null;
//		try {
//			birth = sdf.parse(birthStr);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return birthYear;
	}

	public static void main(String[] args) {
		System.out.println(getBirthFromAge("15"));
	}
	
}
