package com.esd.hesf.dao;

import java.util.Map;

import com.esd.hesf.model.Audit;

/**
 * 审核表 dao接口
 * 
 * @author Administrator
 * 
 */
public interface AuditDao extends BaseDao<Audit> {

	/**
	 * 复制去年的审核数据
	 * map中放入下面2个参数
	 * @param currentYear
	 * @param lastYear
	 * @return
	 */
	int insertLastYearData(Map<String, Object> map);

	/**
	 * 根据companycode和year获得一条审核数据 
	 * @param map
	 * @return
	 */
	Audit retrieveByYearAndCompanyCode(Map<String,Object> map);
	
	/**
	 * 根据公司档案号companyCode查询该公司未审核的年份
	 * @param map
	 * @return
	 */
	String[] retireveUnauditYearByCompanycode(Map<String, Object> map);
}