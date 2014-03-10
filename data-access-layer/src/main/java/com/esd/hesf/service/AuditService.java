package com.esd.hesf.service;

import java.util.List;
import java.util.Map;

import com.esd.common.util.PaginationRecordsAndNumber;
import com.esd.hesf.model.Audit;

/**
 * 审核 service接口
 * 
 * @author Administrator
 * 
 */
public interface AuditService extends BaseService<Audit> {

	/**
	 * 测试方法
	 * 
	 * @return
	 */
	public List<Audit> getAll();

	/**
	 * 根据 年份year, 公司档案号companyCode 插入一条年审记录
	 * @param year
	 * @param companyCode
	 * @return
	 */
	boolean save(String year,String companyCode);
	
	/**
	 * 根据年份year, 公司id companyId获得一条审核数据
	 * 
	 * @param year
	 * @param companyId
	 * @return
	 */
	Audit getByPrimaryKey(String year, Integer companyId);

	/**
	 * 初始化审计参数--包括创建 年度审核信息
	 * 
	 * @param year 年度
	 * @return
	 */
	public boolean initAuditData(String year);

	/**
	 * 多条件查询 公司审核数据 数据
	 * 
	 * @param map
	 *            --map对象 参照 com.esd.hesf.ExampleMap 中的样例 测试样例见
	 *            com.esd.cs.TestController 的测试方法 23
	 * @return
	 */
	PaginationRecordsAndNumber<Audit, Number> getByMultiCondition(Map<String, Object> map);

}
