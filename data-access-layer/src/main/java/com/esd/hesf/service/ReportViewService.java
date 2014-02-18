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
	 * 年审情况汇总(单位性质)
	 * 
	 * @param year
	 * @return
	 */
	public List<ReportViewModel> getByCompanyType(String year);

	/**
	 * 年审情况汇总(地区)
	 * 
	 * @param year
	 * @return
	 */
	public List<ReportViewModel> getByArea(String year);

	/**
	 * 年审情况汇总(经济类型)
	 * 
	 * @param year
	 * @return
	 */
	public List<ReportViewModel> getByCompanyEconomyType(String year);
}
