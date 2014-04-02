package com.esd.hesf.service;

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
	 * 根据 year和companyId 得到以公司分组后的accounts对象,其中的id为不可用的
	 * @param year
	 * @param companyId
	 * @return Accounts id - 不要使用, 不准确; totalMoney - 已经进行了合计, 为缴款总额; 
	 */
	Accounts getByYearAndCompany(String year,Integer companyId);
	
	
	/**
	 * 获取所有数据 测试用
	 * 
	 * @return List<Accounts>
	 */
	List<Accounts> getAll();

	/**
	 * 根据年份, 公司id, 审核id获得账目列表
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
	 * @param page
	 * @param pageSize
	 * @return PaginationRecordsAndNumber<Accounts, Number>
	 */
	PaginationRecordsAndNumber<Accounts, Number> getPaginationRecordsGroupByCompany(
			Accounts t, Integer page, Integer pageSize);
}
