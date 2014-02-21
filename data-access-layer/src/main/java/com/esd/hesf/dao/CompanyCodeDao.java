package com.esd.hesf.dao;

/**
 * 生成公司档案号dao接口
 * 
 * @author Administrator
 * 
 */
public interface CompanyCodeDao {

	/**
	 * updateCompanyCode : 更新公司档案号
	 * 
	 * @param t
	 * @return
	 */
	int update(Integer companyCode);

	/**
	 * retrieveCompanyCode : 查询公司档案号
	 * 
	 * @param id
	 * @return
	 */
	Integer retrieveCompanyCode();
}
