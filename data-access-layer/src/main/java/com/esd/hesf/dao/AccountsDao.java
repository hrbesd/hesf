package com.esd.hesf.dao;

import com.esd.hesf.model.Accounts;

/**
 * 缴款账目类 dao接口
 * 
 * @author Administrator
 * 
 */
public interface AccountsDao extends BaseDao<Accounts> {

	/**
	 * 获得缴款账目的年份列表
	 * @return
	 */
	String[] retrieveAuditYears();
}