package com.esd.hesf.service;

import java.math.BigDecimal;
import java.util.List;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.model.Accounts;

/**
 * 缴款账目 service接口
 * 
 * @author Administrator
 * 
 */
public interface AccountsService extends BaseService<Accounts> {

	/**
	 * 根据 账目年份year和companyId 得到以公司分组后的accounts对象,其中的id为不可用的
	 * 
	 * @param year
	 * @param companyId
	 * @return Accounts id - 不要使用, 不准确; totalMoney - 已经进行了合计, 为缴款总额;
	 */
	Accounts getByYearAndCompany(String year, Integer companyId);

	/**
	 * 获取所有数据 测试用
	 * 
	 * @return List<Accounts>
	 */
	List<Accounts> getAll();

	/**
	 * 根据出账年份, 公司id, 审核id获得账目列表
	 * 
	 * @param year
	 * @param companyId
	 * @param auditId
	 * @return List<Accounts>
	 */
	List<Accounts> getCompanyAccount(String year, Integer companyId);

	/**
	 * 根据年份, 公司档案code, 审核id获得账目列表
	 * 
	 * @param year
	 * @param companyCode
	 * @param auditId
	 * @return List<Accounts>
	 */
	List<Accounts> getCompanyAccount(String year, String companyCode);

	/**
	 * 获得缴款账目中, 年份列表
	 * 
	 * @return String[]
	 */
	String[] getAccountsYears();

	/**
	 * 根据年份和公司id获得账目列表
	 * 
	 * @param year
	 * @param companyId
	 * @return List<Accounts>
	 */
	List<Accounts> getByYearAndCompany(String year, Integer companyId,
			Integer auditProcessStatus);

	/**
	 * 采用了以公司分组的分页查询
	 * 
	 * @param t
	 * @param belongType	所属类型-是否以6字头开始的公司	
	 * @param page
	 * @param pageSize
	 * @return PaginationRecordsAndNumber<Accounts, Number>
	 */
	PaginationRecordsAndNumber<Accounts, Number> getPaginationRecordsGroupByCompany(
			Accounts t, String belongType,Integer page, Integer pageSize);

	/**
	 * 根据账目年份和公司id, 得到该公司当前缴款 包含的审核年度
	 * 
	 * @param accountsYear
	 *            缴款/出账年份
	 * @param companyId
	 *            公司id
	 * @return
	 */
	String[] getAuditYears(String accountsYear, Integer companyId);
	
	/**
	 * 根据审核年份, 账目年份, 公司id 得到该公司某年的总应交款数
	 * @param auditYear 审核年份(可为null)
	 * @param accountsYear	账目年份
	 * @param companyId	公司id
	 * @return	总应缴款数
	 */
	BigDecimal getCompanyAuditYear(String auditYear,String accountsYear,Integer companyId);
	
	/**
	 * 根据审核年份, 账目年份, 公司id 得到该公司某年的对应账目对象
	 * @param auditYear 审核年份
	 * @param accountsYear	账目年份
	 * @param companyCode	公司档案code
	 * @return	账目对象
	 */
	Accounts getOneByCompanyAuditYear(String auditYear,String accountsYear,String companyCode);
	
	/**
	 * 根据审核年份, 账目年份, 公司id 得到该公司某年的对应账目对象
	 * @param auditYear 审核年份
	 * @param accountsYear	账目年份
	 * @param companyId	公司id
	 * @return	账目对象
	 */
	Accounts getOneByCompanyAuditYear(String auditYear,String accountsYear,Integer companyId);
	
}
