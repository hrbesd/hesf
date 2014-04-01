package com.esd.hesf.dao;

import java.util.List;
import java.util.Map;

import com.esd.hesf.model.Accounts;

/**
 * 缴款账目类 dao接口
 * 
 * @author Administrator
 * 
 */
public interface AccountsDao extends BaseDao<Accounts> {

	/**
	 * 根据 accounts的 year和其中的company.id 得到以公司分组后的accounts对象,其中的id为不可用的-为防止乱用,
	 * 写死为0
	 * 
	 * @param t
	 * @return
	 */
	Accounts retrieveByCompanyGroup(Accounts t);

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

	/**
	 * 运用的以公司分组的 账目分页查询
	 * 
	 * @param map
	 * @return List<Accounts>
	 */
	List<Accounts> retrievePageByCompanyGroup(Map<String, Object> map);

	/**
	 * 运用的以公司分组的 账目分页查询 返回总条数
	 * 
	 * @param map
	 * @return Integer
	 */
	Integer retrievePageByCompanyGroupCount(Map<String, Object> map);
}