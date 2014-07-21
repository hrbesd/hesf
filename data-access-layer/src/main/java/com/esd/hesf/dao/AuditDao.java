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

	/**
	 * 根据audit对象中的公司id/code, 年(year), 审核状态(auditProcessStatus)来取得过去未审的列表
	 * 
	 * @param audit
	 * @return
	 */
	List<Audit> retireveUnauditByCompany(Audit audit);

	/**
	 * 查询审核表中已经存在的审核年份
	 * 
	 * @return
	 */
	String[] retrieveAuditYears();

	/**
	 * 根据审核年份, 查询存在多少条该年的审核数据
	 * 
	 * @param year
	 * @return
	 */
	Integer retrieveCountByYear(String year);

	/**
	 * 根据多个id查询年审信息列表, map中放入array数组, 或者list集合等 key值必须为: array
	 * 
	 * @param map
	 * @return
	 */
	List<Audit> retrieveByPrimaryKeys(Map<String, Object> map);
}