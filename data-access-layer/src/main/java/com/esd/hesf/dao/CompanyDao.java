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
	 * delete : 按主键删除一条数据
	 * 
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * retrieveByPrimaryKey : 根据id, 查询一条数据
	 * 
	 * @param id
	 * @return
	 */
	Company retrieveByPrimaryKey(String id);

	/**
	 * 根据year和companyCode得到公司对象
	 * 
	 * @param map
	 * @return
	 */
	Company retrieveByYearAndCode(Map<String, Object> map);

	/**
	 * 得到最新的档案号 xxx暂时不用
	 * 
	 * @return
	 */
	public String getLatestDocumentCode();

	/**
	 * 得到已有的年度列表
	 * 
	 * @return
	 */
	public String[] retrieveYears();

	/**
	 * 得到有公司数据的上一年份
	 * 
	 * @return
	 */
	public String retrieveLastYear();

	/**
	 * 根据组织机构代码查询公司对象是否存在
	 * 
	 * @param companyOrganizationCode
	 * @return
	 */
	public List<Company> retrieveByOrganizationCode(String companyOrganizationCode);

	/**
	 * 复制上一年的公司信息
	 * 
	 * @param map
	 * @return
	 */
	public int insertLastYearData(Map<String, String> map);
}