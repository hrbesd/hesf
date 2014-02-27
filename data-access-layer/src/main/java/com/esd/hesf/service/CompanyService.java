package com.esd.hesf.service;

import java.util.List;
import java.util.Map;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.model.Company;
import com.esd.hesf.model.Worker;

/**
 * 公司信息 service接口
 * 
 * @author Administrator
 * 
 */
public interface CompanyService extends BaseService<Company> {

	// 按ID数组查找多个企业列表
	List<Company> getMultiById(int[] arr);

	// 按ID数组删除多个企业
	boolean deleteMultiById(int[] arr);

	// 自动生成最新档案号
	public String getDocumentCode();

	/**
	 * delete : 根据id删除
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(String id);

	/**
	 * getByPrimaryKey : 根据主键id查询一个公司
	 * 
	 * @param id
	 * @return
	 */
	Company getByPrimaryKey(String id);

	/**
	 * 根据 年份和公司code获得一个公司对象
	 * 
	 * @param year
	 * @param companyCode
	 * @return
	 */
	Company getByYearAndCode(String year, String companyCode);

	/**
	 * 得到所有审核年度列表
	 * 
	 * @return
	 */
	public String[] getYears();

	/**
	 * 得到有公司数据的最近一个年份
	 * 
	 * @return
	 */
	public String getLastYear(String thisYear);

	/**
	 * 修改测试数据用--暂时勿删
	 * 
	 * @param size
	 * @return
	 */
	List<Company> getAll(int size);

	/**
	 * 检查组织机构代码是否存在
	 * 
	 * @param companyOrganizationCode
	 * @return
	 */
	public Company getCompanyByOrganizationCode(String companyOrganizationCode);

	// /**
	// * 复制上一年的信息
	// *
	// * @param currentYear
	// * 本年度
	// * @param lastYear
	// * 上年度
	// * @return
	// */
	// public boolean copyLatYearData(String currentYear, String lastYear);

	/**
	 * 从一个企业中删除一条员工数据,三个参数都不能为空
	 * 
	 * @param year
	 *            年度
	 * @param companyCode
	 *            公司档案code
	 * @param workerId
	 *            员工id
	 * @return
	 */
	public boolean deleteWorkerFromCompany(String year, String companyCode, int workerId);

	/**
	 * 多条件查询 公司 数据
	 * 
	 * @param map
	 *            --map对象 参照 com.esd.hesf.ExampleMap 中的example_company()样例 测试样例见
	 *            com.esd.cs.TestController 的测试方法 26
	 * @return
	 */
	PaginationRecordsAndNumber<Company, Number> getByMultiCondition(Map<String, Object> map);

	/**
	 * 查询 指定公司指定年度残疾职工人数
	 * 
	 * @param companyCode
	 * @param year
	 * @return
	 */
	int getWorkerHandicapTotal(String companyCode, String year);

	/**
	 * 获得该企业年龄达到退休标准的残疾职工列表--方法①
	 * 
	 * @param year
	 *            --年份
	 * @param companyCode
	 *            --公司档案code
	 * @param page
	 *            --起始页
	 * @param pageSize
	 *            --返回量
	 * @return
	 */
	PaginationRecordsAndNumber<Worker, Number> getOverproofAge(String year, String companyCode, Integer page, Integer pageSize);

	/**
	 * 获得该企业年龄达到退休标准的残疾职工列表--方法②
	 * 
	 * @param companyId
	 *            --公司Id
	 * @param page
	 *            --起始页
	 * @param pageSize
	 *            --返回量
	 * @return
	 */
	PaginationRecordsAndNumber<Worker, Number> getOverproofAge(String companyId, Integer page, Integer pageSize);

	/**
	 * 根据公司档案号和当前年份得到该公司过去为审核的年份列表
	 * 
	 * @param companyCode
	 *            --公司档案号 year--当前审核年份
	 * @return
	 */
	String[] getUnauditYearByCompanycode(String companyCode, String year);

	/**
	 * 获得一个自增长的公司档案号
	 * 
	 * @return
	 */
	Integer gotCompanyCode();

	/**
	 * 根据公司档案号检查公司是否存在
	 * 
	 * @param companyCode
	 * @return true-存在 false-不存在
	 */
	boolean checkCompanyCode(String companyCode);

}
