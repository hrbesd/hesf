package com.esd.hesf.dao;

import java.util.List;

import com.esd.hesf.model.Company;

/**
 * 公司信息 dao接口
 * 
 * @author Administrator
 * 
 */
public interface CompanyDao extends BaseDao<Company> {

	/**
	 * retrieveByPrimaryKey : 根据id, 查询一条数据
	 * 
	 * @param id
	 * @return
	 */
	Company retrieveByCompanyCode(String companyCode);

	/**
	 * 得到最新的档案号 xxx暂时不用
	 * 
	 * @return
	 */
	public String getLatestDocumentCode();

	/**
	 * 得到有公司数据的上一年份
	 * 
	 * @return
	 */
	public String retrieveLastYear(String thisYear);

	/**
	 * 根据组织机构代码查询公司对象是否存在
	 * 
	 * @param companyOrganizationCode
	 * @return
	 */
	public List<Company> retrieveByOrganizationCode(String companyOrganizationCode);

	/**
	 * 根据公司code查询存在几条数据
	 * 
	 * @param companyCode
	 * @return
	 */
	Integer retrieveCountByCompanyCode(String companyCode);
}