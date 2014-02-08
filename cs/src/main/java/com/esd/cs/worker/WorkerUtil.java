package com.esd.cs.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esd.common.util.CalendarUtil;

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
}
