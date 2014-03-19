/*
 * Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
 * 
 * HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.esd.cs.company;

import java.util.Date;

public class CompanyUtil {

	public static String getCompanyCode() {
		return String.valueOf(new Date().getTime());
	}
}
