package com.esd.hesf.dao;

import com.esd.hesf.model.AuditParameter;

/**
 * 年审参数dao接口
 * 
 * @author Administrator
 * 
 */
public interface AuditParameterDao extends BaseDao<AuditParameter> {

	/**
	 * 根据年份获得该年的年审参数
	 * 
	 * @param year
	 * @return
	 */
	AuditParameter retrieveByYear(String year);
	
	/**
	 * 得到最近一个审核参数的年份
	 * 
	 * @return
	 */
	public String retrieveLastestYear();
	
	/**
	 * 得到所有审核参数年份列表
	 * 
	 * @return
	 */
	String[] retrieveYears();
}