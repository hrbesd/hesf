package com.esd.hesf.dao;

import java.math.BigDecimal;
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
	
	/**
	 * 获得账目年限(已缴款年份列表)
	 * @return
	 */
	String[] retrieveAccountsYears();
	
	/**
	 * 根据账目年份和公司id, 得到该公司当前缴款 包含的审核年度
	 * @param map	包含accountsYear String 类型, companyId Integer 类型
	 * @return	String[]
	 */
	String[] retrieveAuditYears(Map<String,Object> map);
	
	/**
	 * 根据账目表中的    审核年份, 账目年份, 公司id 得到该公司某年的总应交款数
	 * @return	总应缴款数
	 */
	BigDecimal retrieveByCompanyAuditYear(Accounts accounts);
	
	/**
	 * 根据账目表中的    审核年份, 账目年份, 公司id 得到该公司某年的总应交款数
	 * @param map中包含auditYear String, accountsYear String 类型, companyId Integer 类型, companyCode String 类型 (companyId,companyCode 为二选一)
	 * @return	总应缴款数
	 */
	Accounts retrieveOneByCompanyAuditYear(Map<String,Object> map);
}