package com.esd.hesf.service;

import java.util.List;

import com.esd.hesf.viewmodels.ReportViewModel;

/**
 * 统计报表 service接口
 * 
 * @author Administrator
 * 
 */
public interface ReportViewService {

	/**
	 * 当前查询的种类, 关键字
	 */
	public static final String SEARCH_KEY = "currentSearch";
	/**
	 * 年份key words
	 */
	public static final String SEARCH_KEY_YEAR = "year";
	public static final String SEARCH_KEY_COMPANY_TYPE = "companyType";
	public static final String SEARCH_KEY_COMPANY_ECONOMY_TYPE = "companyEconomyType";
	public static final String SEARCH_KEY_AREA = "area";

	/**
	 * 年审情况汇总(单位性质)
	 * 
	 * @param year
	 * @return
	 */
	List<ReportViewModel> getByCompanyType(String year);

	/**
	 * 年审情况汇总(地区)
	 * 
	 * @param year
	 * @return
	 */
	List<ReportViewModel> getByArea(String year);

	/**
	 * 年审情况汇总(经济类型)
	 * 
	 * @param year
	 * @return
	 */
	List<ReportViewModel> getByCompanyEconomyType(String year);
}
