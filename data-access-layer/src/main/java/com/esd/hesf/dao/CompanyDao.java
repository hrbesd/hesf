package com.esd.hesf.dao;

import java.util.List;
import java.util.Map;

import com.esd.hesf.model.Company;

/**
 * 公司信息 dao接口
 * 
 * @author Administrator
 * 
 */
public interface CompanyDao extends BaseDao<Company> {

	/**
	 *  根据多个id查询公司列表, map中放入array数组, 或者list集合等
	 *  key值必须为: array 
	 * @param map
	 * @return
	 */
	List<Company> retrieveByPrimaryKeys(Map<String, Object> map);

	/**
	 * retrieveByCompanyCode : 根据公司档案号, 查询一条数据
	 * 
	 * @param companyCode
	 * @return
	 */
	Company retrieveByCompanyCode(String companyCode);

	
	/**
	 * retrieveByCompanyName : 根据公司名称, 查询一条数据
	 * 
	 * @param companyName
	 * @return
	 */
	Company retrieveByCompanyName(String companyName);
	
	/**
	 * 得到最新的档案号 xxx暂时不用
	 * 
	 * @return
	 */
	public String getLatestDocumentCode();

	/**
	 * 根据组织机构代码查询公司对象是否存在
	 * 
	 * @param companyOrganizationCode
	 * @return
	 */
	public Company retrieveByOrganizationCode(String companyOrganizationCode);

	/**
	 * 根据公司code查询存在几条数据
	 * 
	 * @param companyCode
	 * @return
	 */
	Integer retrieveCountByCompanyCode(String companyCode);

	/**
	 * 获得自动生成的下一个companyCode
	 * 
	 * @return
	 */
	String retrieveNextCompanyCode();
}