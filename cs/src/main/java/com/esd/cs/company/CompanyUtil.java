package com.esd.cs.company;

import java.util.Date;

public class CompanyUtil {

	public static String getCompanyCode() {
		return String.valueOf(new Date().getTime());
	}
}
