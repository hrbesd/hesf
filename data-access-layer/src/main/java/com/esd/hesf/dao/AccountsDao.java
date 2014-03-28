package com.esd.hesf.dao;

import java.util.List;

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
	 * 
	 * @return
	 */
	String[] retrieveAuditYears();

	/**
	 * 根据账目对象的各属性值 来查询相类似的账目列表
	 * 
	 * @param accounts
	 * @return
	 */
	List<Accounts> retrieveByAccounts(Accounts accounts);
}