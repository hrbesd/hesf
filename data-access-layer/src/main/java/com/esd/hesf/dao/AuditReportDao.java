package com.esd.hesf.dao;

import java.util.List;
import java.util.Map;

import com.esd.hesf.viewmodels.AuditTempModel;
import com.esd.hesf.viewmodels.ReportViewModel;

/**
 * 年审 统计报表视图 查询dao接口
 * 
 * @author Administrator
 * 
 */
public interface AuditReportDao {

	/**
	 * 根据年份, (公司类型id, 地区code, 公司经济类型id)三选一得到对应不同类型/地区/经济类型的公司数量
	 * 
	 * @param map中放入year
	 *            -String, companyType-Integer, companyEconomyType-Integer,
	 *            areaCode-String
	 * @return
	 */
	List<ReportViewModel> retrieveReport(Map<String, Object> map);


	/**
	 * 根据年份, (公司类型id, 地区code, 公司经济类型id)三选一得到对应不同类型/地区/经济类型的公司的已交款总数
	 * 
	 * @param map中放入year
	 *            -String, companyType-Integer, companyEconomyType-Integer,
	 *            areaCode-String
	 * @return
	 */
	List<ReportViewModel> retrieveAlreadyPay(Map<String,Object> map);
	
	
	
	
	
	/**
	 * 根据年份, (公司类型id, 地区code, 公司经济类型id)三选一得到对应不同审核状态的公司数量
	 * 
	 * @param map中放入year
	 *            -String, companyType-Integer, companyEconomyType-Integer,
	 *            areaCode-String
	 * @return
	 */
	List<AuditTempModel> retrieveAupCount(Map<String, Object> map);

	// /**
	// * 查询审核报表情况--按公司类型查
	// *
	// * @return
	// */
	// ReportViewModel retrieveReportByCompanyType(String year);
	//
	// /**
	// * 查询审核报表情况--按公司经济类型查
	// *
	// * @return
	// */
	// List<ReportViewModel> retrieveReportByCompanyEconomyType(String year);
	//
	// /**
	// * 查询审核报表情况--按地区查
	// *
	// * @return
	// */
	// List<ReportViewModel> retrieveReportByArea(String year);

}
