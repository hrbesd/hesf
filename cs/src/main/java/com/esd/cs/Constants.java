/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.esd.cs;

import java.math.BigDecimal;

public class Constants {
	public static final String VERSION = "1.0";
	public static final String PROJECT_NAME = "cs";// 项目名称
	public static final String USER_ID = "userId"; // 用户ID
	public static final String USER_NAME = "userName"; // 用户名
	public static final String USER_GROUP_ID = "userGroupId";
	public static final String USER_REAL_NAME = "userRealName"; // 真实姓名
	public static final String LOCAL_CODE = "10230000"; // 黑龙江默认编码
	public static final Integer PERCENTAGE = 100; // 百分比
	public static final String PLEASE_SELECT = "请选择";
	public static final Integer DEFAULT_TVALUE = 0;
	public static final BigDecimal ZERO = new BigDecimal("0.00");
	public static final String YEAR = "year";

	public static final Integer PROCESS_STATIC_WCS = 1;// 未初审
	public static final Integer PROCESS_STATIC_WFS = 2;// 已初审 未复审
	public static final Integer PROCESS_STATIC_WJK = 3;// 已复审 未缴款
	public static final Integer PROCESS_STATIC_BFJK = 4;// 部分缴款
	public static final Integer PROCESS_STATIC_YJK = 5;// 已缴款
	public static final Integer PROCESS_STATIC_OK = 6;// 达标
	public static final Integer PROCESS_STATIC_WTG = 7;// 已复审 未通过
	public static final Integer PROCESS_STATIC_WZS = 8;// 已复审 未终审
	public static final Integer PROCESS_STATIC_ZSWTG = 9;// 已终审 未通过
	public static final Integer PROCESS_STATIC_ZSOK = 10;// 终审OK
	

	public static final Integer PAYMENT_EXCEPTIONAL_NORMAL = 1;
	
	public static final String NOTICE = "notice";	//提示标识/文字
	
	public static final String LURU = "luru";	//录入标示符
	public static final String NOTYET = "暂未录入";

}
