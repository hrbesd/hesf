package com.esd.hesf.dao;

import java.util.List;
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
	 * 根据年份穿件审核数据
	 * 
	 * @param year
	 * @return
	 */
	int insertLastYearData(String year);

	/**
	 * 得到已有的年度列表
	 * 
	 * @return
	 */
	public String[] retrieveYears();

	/**
	 * 根据公司id, 得到历年的审核信息列表
	 * 
	 * @param map
	 * @return
	 */
	List<Audit> retrieveByCompany(Integer companyId);

	/**
	 * 根据公司档案号companyCode查询该公司未审核的年份
	 * 
	 * @param map
	 * @return
	 */
	String[] retireveUnauditYearByCompanycode(Map<String, Object> map);

	/**
	 * 根据年份, 公司id查询一条审核数据
	 * 
	 * @param map
	 *            中放入 year, companyId
	 * @return
	 */
	Audit retrieveByYearAndCompanyId(Map<String, Object> map);
}